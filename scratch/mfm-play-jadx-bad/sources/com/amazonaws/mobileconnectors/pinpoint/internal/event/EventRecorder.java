package com.amazonaws.mobileconnectors.pinpoint.internal.event;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.Uri;
import androidx.credentials.exceptions.publickeycredential.DomExceptionUtils;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsEvent;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.PinpointContext;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.system.AndroidAppDetails;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.amazonaws.mobileconnectors.pinpoint.internal.event.EventTable;
import com.amazonaws.mobileconnectors.pinpoint.targeting.endpointProfile.EndpointProfile;
import com.amazonaws.services.pinpoint.model.EndpointDemographic;
import com.amazonaws.services.pinpoint.model.EndpointItemResponse;
import com.amazonaws.services.pinpoint.model.EndpointLocation;
import com.amazonaws.services.pinpoint.model.EndpointUser;
import com.amazonaws.services.pinpoint.model.Event;
import com.amazonaws.services.pinpoint.model.EventItemResponse;
import com.amazonaws.services.pinpoint.model.EventsBatch;
import com.amazonaws.services.pinpoint.model.EventsRequest;
import com.amazonaws.services.pinpoint.model.ItemResponse;
import com.amazonaws.services.pinpoint.model.PublicEndpoint;
import com.amazonaws.services.pinpoint.model.PutEventsRequest;
import com.amazonaws.services.pinpoint.model.PutEventsResult;
import com.amazonaws.services.pinpoint.model.Session;
import com.amazonaws.util.DateUtils;
import com.amazonaws.util.VersionInfoUtils;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class EventRecorder {
    static final String DATABASE_ID_KEY = "databaseId";
    static final long DEFAULT_MAX_PENDING_SIZE = 5242880;
    static final int DEFAULT_MAX_SUBMISSIONS_ALLOWED = 3;
    static final long DEFAULT_MAX_SUBMISSION_SIZE = 102400;
    static final String EVENT_ID = "event_id";
    static final String KEY_MAX_PENDING_SIZE = "maxPendingSize";
    static final String KEY_MAX_SUBMISSIONS_ALLOWED = "maxSubmissionAllowed";
    static final String KEY_MAX_SUBMISSION_SIZE = "maxSubmissionSize";
    private static final int MAX_EVENT_OPERATIONS = 1000;
    private static final long MINIMUM_PENDING_SIZE = 16384;
    static final int SERVICE_DEFINED_MAX_EVENTS_PER_BATCH = 100;
    private final PinpointDBUtil dbUtil;
    private final PinpointContext pinpointContext;
    private final ExecutorService submissionRunnableQueue;
    private static final String USER_AGENT = PinpointManager.class.getName() + DomExceptionUtils.SEPARATOR + VersionInfoUtils.getVersion();
    private static int clippedEventLength = 10;
    private static final Log log = LogFactory.getLog((Class<?>) EventRecorder.class);
    private static final int JSON_COLUMN_INDEX = EventTable.COLUMN_INDEX.JSON.getValue();
    private static final int ID_COLUMN_INDEX = EventTable.COLUMN_INDEX.ID.getValue();
    private static final int SIZE_COLUMN_INDEX = EventTable.COLUMN_INDEX.SIZE.getValue();

    private boolean isRetryable(int i) {
        return i >= 500 && i <= 599;
    }

    EventRecorder(PinpointContext pinpointContext, PinpointDBUtil pinpointDBUtil, ExecutorService executorService) {
        this.pinpointContext = pinpointContext;
        this.dbUtil = pinpointDBUtil;
        this.submissionRunnableQueue = executorService;
    }

    public static EventRecorder newInstance(PinpointContext pinpointContext) {
        return newInstance(pinpointContext, new PinpointDBUtil(pinpointContext.getApplicationContext().getApplicationContext()));
    }

    public static EventRecorder newInstance(PinpointContext pinpointContext, PinpointDBUtil pinpointDBUtil) {
        return new EventRecorder(pinpointContext, pinpointDBUtil, new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(1000), new ThreadPoolExecutor.DiscardPolicy()));
    }

    public static void setClippedEventLength(int i) {
        clippedEventLength = i;
    }

    public void closeDB() {
        this.dbUtil.closeDB();
    }

    public Uri recordEvent(AnalyticsEvent analyticsEvent) {
        Throwable th;
        Cursor cursorQueryOldestEvents;
        if (analyticsEvent == null) {
            log.warn("Event cannot be null. Pass in a valid non-null event.");
            return null;
        }
        Log log2 = log;
        log2.info(String.format("Event Recorded to database with EventType: %s", StringUtil.clipString(analyticsEvent.getEventType(), clippedEventLength, true)));
        long jLongValue = this.pinpointContext.getConfiguration().optLong(KEY_MAX_PENDING_SIZE, Long.valueOf(DEFAULT_MAX_PENDING_SIZE)).longValue();
        if (jLongValue < MINIMUM_PENDING_SIZE) {
            jLongValue = 16384;
        }
        Uri uriSaveEvent = this.dbUtil.saveEvent(analyticsEvent);
        if (uriSaveEvent == null) {
            log2.warn(String.format("Event: '%s' failed to record to local database.", StringUtil.clipString(analyticsEvent.getEventType(), clippedEventLength, true)));
            return null;
        }
        while (this.dbUtil.getTotalSize() > jLongValue) {
            try {
                cursorQueryOldestEvents = this.dbUtil.queryOldestEvents(5);
                while (this.dbUtil.getTotalSize() > jLongValue && cursorQueryOldestEvents.moveToNext()) {
                    try {
                        this.dbUtil.deleteEvent(cursorQueryOldestEvents.getInt(EventTable.COLUMN_INDEX.ID.getValue()), Integer.valueOf(cursorQueryOldestEvents.getInt(EventTable.COLUMN_INDEX.SIZE.getValue())));
                    } catch (Throwable th2) {
                        th = th2;
                        if (cursorQueryOldestEvents != null) {
                            cursorQueryOldestEvents.close();
                        }
                        throw th;
                    }
                }
                if (cursorQueryOldestEvents != null) {
                    cursorQueryOldestEvents.close();
                }
            } catch (Throwable th3) {
                th = th3;
                cursorQueryOldestEvents = null;
            }
        }
        return uriSaveEvent;
    }

    JSONObject readEventFromCursor(Cursor cursor, HashMap<Integer, Integer> map) throws Throwable {
        Integer num;
        Integer numValueOf;
        Integer numValueOf2;
        JSONObject jSONObject;
        Integer num2 = null;
        JSONObject jSONObject2 = null;
        try {
            try {
                int i = ID_COLUMN_INDEX;
                if (cursor.isNull(i)) {
                    log.error("Column 'ID' for event was NULL.");
                    return null;
                }
                numValueOf = Integer.valueOf(cursor.getInt(i));
                try {
                    int i2 = SIZE_COLUMN_INDEX;
                    if (cursor.isNull(i2)) {
                        log.error("Column 'SIZE' for event was NULL.");
                        numValueOf2 = null;
                    } else {
                        numValueOf2 = Integer.valueOf(cursor.getInt(i2));
                    }
                    try {
                        int i3 = JSON_COLUMN_INDEX;
                        if (cursor.isNull(i3)) {
                            log.error(String.format(Locale.US, "Event from DB with ID=%d and SiZE=%d contained a NULL message.", numValueOf, numValueOf2));
                        } else {
                            String string = cursor.getString(i3);
                            try {
                                jSONObject = new JSONObject(string);
                                try {
                                    jSONObject.put(DATABASE_ID_KEY, numValueOf);
                                } catch (JSONException unused) {
                                    log.error(String.format(Locale.US, "Unable to deserialize event JSON for event with ID=%d.", numValueOf));
                                }
                            } catch (JSONException unused2) {
                                jSONObject = null;
                            }
                            if (numValueOf2 != null && string.length() != numValueOf2.intValue()) {
                                log.warn(String.format(Locale.US, "Message with ID=%d has a size mismatch. DBMsgSize=%d DBSizeCol=%d", numValueOf, Integer.valueOf(string.length()), numValueOf2));
                                numValueOf2 = null;
                            }
                            jSONObject2 = jSONObject;
                        }
                        if (numValueOf != null && map != null) {
                            map.put(numValueOf, numValueOf2);
                        }
                        return jSONObject2;
                    } catch (Exception e) {
                        e = e;
                        log.error("Failed accessing cursor to get next event.", e);
                        if (numValueOf != null && map != null) {
                            map.put(numValueOf, numValueOf2);
                        }
                        return null;
                    }
                } catch (Exception e2) {
                    e = e2;
                    numValueOf2 = null;
                } catch (Throwable th) {
                    th = th;
                    num = null;
                    num2 = numValueOf;
                    if (num2 != null && map != null) {
                        map.put(num2, num);
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e3) {
            e = e3;
            numValueOf = null;
            numValueOf2 = null;
        } catch (Throwable th3) {
            th = th3;
            num = null;
            if (num2 != null) {
                map.put(num2, num);
            }
            throw th;
        }
    }

    public void submitEvents() {
        this.submissionRunnableQueue.execute(new Runnable() { // from class: com.amazonaws.mobileconnectors.pinpoint.internal.event.EventRecorder.1
            @Override // java.lang.Runnable
            public void run() {
                EventRecorder eventRecorder = EventRecorder.this;
                if (!eventRecorder.isNetworkAvailable(eventRecorder.pinpointContext.getApplicationContext())) {
                    EventRecorder.log.warn("Device is offline, skipping submitting events to Pinpoint");
                } else {
                    EventRecorder.this.processEvents();
                }
            }
        });
    }

    public Future<List<AnalyticsEvent>> submitEventsWithResult() {
        return this.submissionRunnableQueue.submit(new Callable<List<AnalyticsEvent>>() { // from class: com.amazonaws.mobileconnectors.pinpoint.internal.event.EventRecorder.2
            /* JADX DEBUG: Method merged with bridge method: call()Ljava/lang/Object; */
            @Override // java.util.concurrent.Callable
            public List<AnalyticsEvent> call() throws Exception {
                EventRecorder eventRecorder = EventRecorder.this;
                if (!eventRecorder.isNetworkAvailable(eventRecorder.pinpointContext.getApplicationContext())) {
                    EventRecorder.log.warn("Device is offline, skipping submitting events to Pinpoint");
                    return Collections.EMPTY_LIST;
                }
                return EventRecorder.this.processEvents();
            }
        });
    }

    JSONArray getBatchOfEvents(Cursor cursor, HashMap<Integer, Integer> map) throws Throwable {
        JSONArray jSONArray = new JSONArray();
        long jLongValue = this.pinpointContext.getConfiguration().optLong(KEY_MAX_SUBMISSION_SIZE, Long.valueOf(DEFAULT_MAX_SUBMISSION_SIZE)).longValue();
        long length = 0;
        do {
            JSONObject eventFromCursor = readEventFromCursor(cursor, map);
            if (eventFromCursor != null) {
                length += (long) eventFromCursor.length();
                jSONArray.put(eventFromCursor);
            }
            if (length > jLongValue || jSONArray.length() >= 100) {
                break;
            }
        } while (cursor.moveToNext());
        return jSONArray;
    }

    public List<JSONObject> getAllEvents() throws Throwable {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            Cursor cursorQueryAllEvents = this.dbUtil.queryAllEvents();
            while (cursorQueryAllEvents.moveToNext()) {
                try {
                    JSONObject eventFromCursor = readEventFromCursor(cursorQueryAllEvents, null);
                    if (eventFromCursor != null) {
                        arrayList.add(eventFromCursor);
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor = cursorQueryAllEvents;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (cursorQueryAllEvents != null) {
                cursorQueryAllEvents.close();
            }
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, INVOKE] complete} */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00e4 A[PHI: r7 r16
  0x00e4: PHI (r7v1 android.database.Cursor) = (r7v2 android.database.Cursor), (r7v3 android.database.Cursor) binds: [B:39:0x00e2, B:29:0x00ce] A[DONT_GENERATE, DONT_INLINE]
  0x00e4: PHI (r16v2 char) = (r16v3 char), (r16v4 char) binds: [B:39:0x00e2, B:29:0x00ce] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    List<AnalyticsEvent> processEvents() {
        char c;
        long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
        ArrayList arrayList = new ArrayList();
        Cursor cursorQueryAllEvents = null;
        try {
            try {
                cursorQueryAllEvents = this.dbUtil.queryAllEvents();
            } finally {
                if (0 != 0) {
                    cursorQueryAllEvents.close();
                }
            }
        } catch (JSONException e) {
            e = e;
            c = 0;
        }
        if (!cursorQueryAllEvents.moveToFirst()) {
            log.info("No events available to submit.");
            return arrayList;
        }
        long jIntValue = this.pinpointContext.getConfiguration().optInt(KEY_MAX_SUBMISSIONS_ALLOWED, 3).intValue();
        int i = 0;
        while (true) {
            HashMap<Integer, Integer> map = new HashMap<>();
            JSONArray batchOfEvents = getBatchOfEvents(cursorQueryAllEvents, map);
            if (map.size() > 0) {
                submitEventsAndEndpoint(batchOfEvents, map);
                i++;
            }
            int i2 = i;
            arrayList.addAll(getSuccessfullySyncedEvents(batchOfEvents, map));
            for (Integer num : map.keySet()) {
                try {
                    this.dbUtil.deleteEvent(num.intValue(), map.get(num));
                } catch (IllegalArgumentException e2) {
                    Log log2 = log;
                    StringBuilder sb = new StringBuilder();
                    c = 0;
                    try {
                        sb.append("Failed to delete event: ");
                        sb.append(num);
                        log2.error(sb.toString(), e2);
                    } catch (JSONException e3) {
                        e = e3;
                        log.error("Failed to parse to event object", e);
                        if (cursorQueryAllEvents != null) {
                        }
                        Log log3 = log;
                        Object[] objArr = new Object[1];
                        objArr[c] = Integer.valueOf(arrayList.size());
                        log3.info(String.format("Submitted %s events", objArr));
                        return arrayList;
                    }
                }
            }
            c = 0;
            if (i2 >= jIntValue || !cursorQueryAllEvents.moveToNext()) {
                break;
            }
            i = i2;
        }
        log.info(String.format(Locale.US, "Time of attemptDelivery: %d", Long.valueOf(TimeUnit.NANOSECONDS.toMillis(System.nanoTime()) - millis)));
        if (cursorQueryAllEvents != null) {
            cursorQueryAllEvents.close();
        }
        Log log32 = log;
        Object[] objArr2 = new Object[1];
        objArr2[c] = Integer.valueOf(arrayList.size());
        log32.info(String.format("Submitted %s events", objArr2));
        return arrayList;
    }

    private List<AnalyticsEvent> getSuccessfullySyncedEvents(JSONArray jSONArray, HashMap<Integer, Integer> map) throws JSONException {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            if (map.containsKey(Integer.valueOf(jSONArray.getJSONObject(i).getInt(DATABASE_ID_KEY)))) {
                arrayList.add(AnalyticsEvent.translateToEvent(jSONArray.getJSONObject(i)));
            }
        }
        return arrayList;
    }

    private void submitEventsAndEndpoint(JSONArray jSONArray, HashMap<Integer, Integer> map) {
        submitEventsAndEndpoint(jSONArray, map, this.pinpointContext.getTargetingClient().currentEndpoint());
    }

    private void submitEventsAndEndpoint(JSONArray jSONArray, HashMap<Integer, Integer> map, EndpointProfile endpointProfile) {
        if (endpointProfile == null) {
            log.warn("Endpoint profile is null, failed to submit events.");
            map.clear();
            return;
        }
        PutEventsRequest putEventsRequestCreateRecordEventsRequest = createRecordEventsRequest(jSONArray, endpointProfile);
        putEventsRequestCreateRecordEventsRequest.getRequestClientOptions().appendUserAgent(USER_AGENT);
        try {
            PutEventsResult putEventsResultPutEvents = this.pinpointContext.getPinpointServiceClient().putEvents(putEventsRequestCreateRecordEventsRequest);
            processEndpointResponse(endpointProfile, putEventsResultPutEvents);
            processEventsResponse(jSONArray, endpointProfile, putEventsResultPutEvents, map);
            log.info(String.format(Locale.getDefault(), "Successful submission of %d events.", Integer.valueOf(map.size())));
        } catch (AmazonServiceException e) {
            Log log2 = log;
            log2.error("AmazonServiceException occurred during send of put event ", e);
            int statusCode = e.getStatusCode();
            if (isRetryable(statusCode)) {
                log2.error(String.format("AmazonServiceException: Unable to successfully deliver events to server. Events will be saved, error is likely recoverable. Response Status code: %s, Response Error Code: %s", Integer.valueOf(statusCode), e.getErrorCode()), e);
                map.clear();
                return;
            }
            log2.error(String.format(Locale.getDefault(), "Failed to submit events to EventService: statusCode: " + statusCode + " errorCode: ", e.getErrorCode()), e);
            log2.error(String.format(Locale.getDefault(), "Failed submission of %d events, events will be removed from the local database. ", Integer.valueOf(jSONArray.length())), e);
        } catch (AmazonClientException e2) {
            if (isClientExceptionRetryable(e2) || isClientExceptionRetryable(e2.getCause())) {
                log.error("AmazonClientException: Unable to successfully deliver events to server. Events will be saved, error likely recoverable." + e2.getMessage(), e2);
                map.clear();
                return;
            }
            log.error(String.format(Locale.getDefault(), "AmazonClientException: Failed submission of %d events, events will be removed from the local database. ", Integer.valueOf(jSONArray.length())), e2);
        }
    }

    private void processEndpointResponse(EndpointProfile endpointProfile, PutEventsResult putEventsResult) {
        Map<String, ItemResponse> results = putEventsResult.getEventsResponse().getResults();
        if (results == null || results.isEmpty()) {
            log.error("PutEventsResult is empty!");
            return;
        }
        if (endpointProfile.getEndpointId().isEmpty()) {
            log.error("EndpointId is missing!");
            return;
        }
        EndpointItemResponse endpointItemResponse = results.get(endpointProfile.getEndpointId()).getEndpointItemResponse();
        if (endpointItemResponse == null) {
            log.error("EndPointItemResponse is null!");
            return;
        }
        if (202 == endpointItemResponse.getStatusCode().intValue()) {
            log.info("EndpointProfile updated successfully.");
            return;
        }
        log.error("AmazonServiceException occurred during endpoint update: " + endpointItemResponse.getMessage());
    }

    private void processEventsResponse(JSONArray jSONArray, EndpointProfile endpointProfile, PutEventsResult putEventsResult, Map<Integer, Integer> map) {
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                String string = jSONArray.getJSONObject(i).getString("event_id");
                EventItemResponse eventItemResponse = putEventsResult.getEventsResponse().getResults().get(endpointProfile.getEndpointId()).getEventsItemResponse().get(string);
                if (eventItemResponse.getMessage().equalsIgnoreCase("Accepted")) {
                    log.info(String.format("Successful submit event with event id %s", string));
                } else if (isRetryable(eventItemResponse.getStatusCode().intValue())) {
                    log.warn(String.format("Unable to successfully deliver event to server. Event will be saved. Event id %s", string));
                    map.remove(Integer.valueOf(jSONArray.getJSONObject(i).getInt(DATABASE_ID_KEY)));
                } else {
                    log.error(String.format("Failed to submitEvents to EventService: statusCode: %s Status Message: %s", eventItemResponse.getStatusCode(), eventItemResponse.getMessage()));
                }
            } catch (JSONException e) {
                log.error("Failed to get event id while processing event item response.", e);
            }
        }
    }

    private boolean isClientExceptionRetryable(Throwable th) {
        if (!isNetworkAvailable(this.pinpointContext.getApplicationContext())) {
            return true;
        }
        if (th == null || th.getCause() == null) {
            return false;
        }
        return (th.getCause() instanceof UnknownHostException) || (th.getCause() instanceof SocketException);
    }

    private PutEventsRequest createRecordEventsRequest(JSONArray jSONArray, EndpointProfile endpointProfile) {
        PutEventsRequest putEventsRequestWithApplicationId = new PutEventsRequest().withApplicationId(endpointProfile.getApplicationId());
        String endpointId = endpointProfile.getEndpointId();
        HashMap map = new HashMap();
        EventsBatch eventsBatch = new EventsBatch();
        PublicEndpoint publicEndpoint = new PublicEndpoint();
        HashMap map2 = new HashMap();
        buildEndpointPayload(endpointProfile, publicEndpoint);
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                AnalyticsEvent analyticsEventTranslateToEvent = AnalyticsEvent.translateToEvent(jSONArray.getJSONObject(i));
                Event event = new Event();
                buildEventPayload(analyticsEventTranslateToEvent, event);
                map2.put(analyticsEventTranslateToEvent.getEventId(), event);
            } catch (JSONException e) {
                log.error("Stored event was invalid JSON.", e);
            }
        }
        buildRequestPayload(putEventsRequestWithApplicationId, endpointId, map, eventsBatch, publicEndpoint, map2);
        return putEventsRequestWithApplicationId;
    }

    private void buildRequestPayload(PutEventsRequest putEventsRequest, String str, Map<String, EventsBatch> map, EventsBatch eventsBatch, PublicEndpoint publicEndpoint, Map<String, Event> map2) {
        eventsBatch.withEndpoint(publicEndpoint).withEvents(map2);
        map.put(str, eventsBatch);
        EventsRequest eventsRequest = new EventsRequest();
        eventsRequest.withBatchItem(map);
        putEventsRequest.withEventsRequest(eventsRequest);
    }

    private void buildEndpointPayload(EndpointProfile endpointProfile, PublicEndpoint publicEndpoint) {
        publicEndpoint.withChannelType(endpointProfile.getChannelType()).withAddress(endpointProfile.getAddress()).withLocation(new EndpointLocation().withLatitude(endpointProfile.getLocation().getLatitude()).withLongitude(endpointProfile.getLocation().getLongitude()).withPostalCode(endpointProfile.getLocation().getPostalCode()).withCity(endpointProfile.getLocation().getCity()).withRegion(endpointProfile.getLocation().getRegion()).withCountry(endpointProfile.getLocation().getCountry())).withDemographic(new EndpointDemographic().withAppVersion(endpointProfile.getDemographic().getAppVersion()).withLocale(endpointProfile.getDemographic().getLocale().toString()).withTimezone(endpointProfile.getDemographic().getTimezone()).withMake(endpointProfile.getDemographic().getMake()).withModel(endpointProfile.getDemographic().getModel()).withPlatform(endpointProfile.getDemographic().getPlatform()).withPlatformVersion(endpointProfile.getDemographic().getPlatformVersion())).withEffectiveDate(DateUtils.formatISO8601Date(new Date(endpointProfile.getEffectiveDate()))).withOptOut(endpointProfile.getOptOut()).withAttributes(endpointProfile.getAllAttributes()).withMetrics(endpointProfile.getAllMetrics()).withUser(endpointProfile.getUser().getUserId() == null ? null : new EndpointUser().withUserId(endpointProfile.getUser().getUserId()).withUserAttributes(endpointProfile.getUser().getUserAttributes()));
    }

    void buildEventPayload(AnalyticsEvent analyticsEvent, Event event) {
        Session session = new Session();
        session.withId(analyticsEvent.getSession().getSessionId());
        session.withStartTimestamp(DateUtils.formatISO8601Date(new Date(analyticsEvent.getSession().getSessionStart().longValue())));
        if (analyticsEvent.getSession().getSessionStop() != null && analyticsEvent.getSession().getSessionStop().longValue() != 0) {
            session.withStopTimestamp(DateUtils.formatISO8601Date(new Date(analyticsEvent.getSession().getSessionStop().longValue())));
        }
        if (analyticsEvent.getSession().getSessionDuration() != null && analyticsEvent.getSession().getSessionDuration().longValue() != 0) {
            session.withDuration(Integer.valueOf(analyticsEvent.getSession().getSessionDuration().intValue()));
        }
        AndroidAppDetails appDetails = analyticsEvent.getAppDetails();
        event.withAppPackageName(appDetails.packageName()).withAppTitle(appDetails.getAppTitle()).withAppVersionCode(appDetails.versionCode()).withAttributes(analyticsEvent.getAllAttributes()).withClientSdkVersion(analyticsEvent.getSdkVersion()).withEventType(analyticsEvent.getEventType()).withMetrics(analyticsEvent.getAllMetrics()).withSdkName(analyticsEvent.getSdkName()).withSession(session).withTimestamp(DateUtils.formatISO8601Date(new Date(analyticsEvent.getEventTimestamp().longValue())));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (networkCapabilities != null) {
                if (!networkCapabilities.hasTransport(0) && !networkCapabilities.hasTransport(1)) {
                    if (!networkCapabilities.hasTransport(3)) {
                        return false;
                    }
                }
                return true;
            }
        } catch (Exception unused) {
        }
        return false;
    }
}
