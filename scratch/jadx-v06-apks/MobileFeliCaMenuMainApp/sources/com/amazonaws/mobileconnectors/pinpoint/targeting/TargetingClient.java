package com.amazonaws.mobileconnectors.pinpoint.targeting;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.PinpointContext;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.Preconditions;
import com.amazonaws.mobileconnectors.pinpoint.targeting.endpointProfile.EndpointProfile;
import com.amazonaws.services.pinpoint.model.EndpointDemographic;
import com.amazonaws.services.pinpoint.model.EndpointLocation;
import com.amazonaws.services.pinpoint.model.EndpointRequest;
import com.amazonaws.services.pinpoint.model.EndpointUser;
import com.amazonaws.services.pinpoint.model.UpdateEndpointRequest;
import com.amazonaws.util.DateUtils;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.VersionInfoUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TargetingClient {
    private static final String CUSTOM_ATTRIBUTES_KEY = "ENDPOINT_PROFILE_CUSTOM_ATTRIBUTES";
    private static final String CUSTOM_METRICS_KEY = "ENDPOINT_PROFILE_CUSTOM_METRICS";
    private static final int MAX_EVENT_OPERATIONS = 1000;
    private static final String USER_AGENT = PinpointManager.class.getName() + "/" + VersionInfoUtils.getVersion();
    private static final Log log = LogFactory.getLog((Class<?>) TargetingClient.class);
    private final PinpointContext context;
    private final EndpointProfile endpointProfile;
    private final ExecutorService endpointRunnableQueue;
    private final Map<String, List<String>> globalAttributes;
    private final Map<String, Double> globalMetrics;

    public TargetingClient(PinpointContext pinpointContext, ExecutorService executorService) {
        Preconditions.checkNotNull(pinpointContext, "A valid pinpointContext must be provided");
        this.endpointRunnableQueue = executorService;
        this.context = pinpointContext;
        this.endpointProfile = new EndpointProfile(this.context);
        this.globalAttributes = loadAttributes();
        this.globalMetrics = loadMetrics();
    }

    public TargetingClient(PinpointContext pinpointContext) {
        this(pinpointContext, new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(1000), new ThreadPoolExecutor.DiscardPolicy()));
    }

    public EndpointProfile currentEndpoint() {
        if (!this.globalAttributes.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : this.globalAttributes.entrySet()) {
                this.endpointProfile.addAttribute(entry.getKey(), entry.getValue());
            }
        }
        if (!this.globalMetrics.isEmpty()) {
            for (Map.Entry<String, Double> entry2 : this.globalMetrics.entrySet()) {
                this.endpointProfile.addMetric(entry2.getKey(), entry2.getValue());
            }
        }
        return this.endpointProfile;
    }

    public void updateEndpointProfile() {
        executeUpdate(currentEndpoint());
    }

    public void updateEndpointProfile(EndpointProfile endpointProfile) {
        if (!this.globalAttributes.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : this.globalAttributes.entrySet()) {
                endpointProfile.addAttribute(entry.getKey(), entry.getValue());
            }
        }
        if (!this.globalMetrics.isEmpty()) {
            for (Map.Entry<String, Double> entry2 : this.globalMetrics.entrySet()) {
                endpointProfile.addMetric(entry2.getKey(), entry2.getValue());
            }
        }
        executeUpdate(endpointProfile);
    }

    private void executeUpdate(EndpointProfile endpointProfile) {
        if (endpointProfile == null) {
            log.error("EndpointProfile is null, failed to update profile.");
            return;
        }
        final UpdateEndpointRequest updateEndpointRequestWithEndpointRequest = new UpdateEndpointRequest().withApplicationId(endpointProfile.getApplicationId()).withEndpointId(endpointProfile.getEndpointId()).withEndpointRequest(new EndpointRequest().withChannelType(endpointProfile.getChannelType()).withAddress(endpointProfile.getAddress()).withLocation(new EndpointLocation().withLatitude(endpointProfile.getLocation().getLatitude()).withLongitude(endpointProfile.getLocation().getLongitude()).withPostalCode(endpointProfile.getLocation().getPostalCode()).withCity(endpointProfile.getLocation().getCity()).withRegion(endpointProfile.getLocation().getRegion()).withCountry(endpointProfile.getLocation().getCountry())).withDemographic(new EndpointDemographic().withAppVersion(endpointProfile.getDemographic().getAppVersion()).withLocale(endpointProfile.getDemographic().getLocale().toString()).withTimezone(endpointProfile.getDemographic().getTimezone()).withMake(endpointProfile.getDemographic().getMake()).withModel(endpointProfile.getDemographic().getModel()).withPlatform(endpointProfile.getDemographic().getPlatform()).withPlatformVersion(endpointProfile.getDemographic().getPlatformVersion())).withEffectiveDate(DateUtils.formatISO8601Date(new Date(endpointProfile.getEffectiveDate()))).withOptOut(endpointProfile.getOptOut()).withAttributes(endpointProfile.getAllAttributes()).withMetrics(endpointProfile.getAllMetrics()).withUser(endpointProfile.getUser().getUserId() == null ? null : new EndpointUser().withUserId(endpointProfile.getUser().getUserId()).withUserAttributes(endpointProfile.getUser().getUserAttributes())));
        updateEndpointRequestWithEndpointRequest.getRequestClientOptions().appendUserAgent(USER_AGENT);
        this.endpointRunnableQueue.execute(new Runnable() { // from class: com.amazonaws.mobileconnectors.pinpoint.targeting.TargetingClient.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    TargetingClient.log.info("Updating EndpointProfile.");
                    TargetingClient.this.context.getPinpointServiceClient().updateEndpoint(updateEndpointRequestWithEndpointRequest);
                    TargetingClient.log.info("EndpointProfile updated successfully.");
                } catch (AmazonServiceException e) {
                    TargetingClient.log.error("AmazonServiceException occurred during endpoint update:", e);
                } catch (AmazonClientException e2) {
                    TargetingClient.log.info("AmazonClientException occurred during endpoint update:", e2);
                }
            }
        });
    }

    private void saveAttributes() {
        this.context.getSystem().getPreferences().putString(CUSTOM_ATTRIBUTES_KEY, new JSONObject(this.globalAttributes).toString());
    }

    private Map<String, List<String>> loadAttributes() {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        String string = this.context.getSystem().getPreferences().getString(CUSTOM_ATTRIBUTES_KEY, null);
        if (StringUtils.isBlank(string)) {
            return concurrentHashMap;
        }
        try {
            JSONObject jSONObject = new JSONObject(string);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                JSONArray jSONArray = jSONObject.getJSONArray(next);
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(jSONArray.getString(i));
                }
                concurrentHashMap.put(next, arrayList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return concurrentHashMap;
    }

    private void saveMetrics() {
        this.context.getSystem().getPreferences().putString(CUSTOM_METRICS_KEY, new JSONObject(this.globalMetrics).toString());
    }

    private Map<String, Double> loadMetrics() {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        String string = this.context.getSystem().getPreferences().getString(CUSTOM_METRICS_KEY, null);
        if (StringUtils.isBlank(string)) {
            return concurrentHashMap;
        }
        try {
            JSONObject jSONObject = new JSONObject(string);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                concurrentHashMap.put(next, Double.valueOf(jSONObject.getDouble(next)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return concurrentHashMap;
    }

    public void addAttribute(String str, List<String> list) {
        if (str == null) {
            log.debug("Null attribute name provided to addGlobalAttribute.");
        } else if (list == null) {
            log.debug("Null attribute values provided to addGlobalAttribute.");
        } else {
            this.globalAttributes.put(str, list);
            saveAttributes();
        }
    }

    public void removeAttribute(String str) {
        if (str == null) {
            log.warn("Null attribute name provided to removeGlobalAttribute.");
            return;
        }
        this.endpointProfile.addAttribute(str, null);
        this.globalAttributes.remove(str);
        saveAttributes();
    }

    public void addMetric(String str, Double d) {
        if (str == null) {
            log.warn("Null metric name provided to addGlobalMetric.");
        } else if (d == null) {
            log.warn("Null metric value provided to addGlobalMetric.");
        } else {
            this.globalMetrics.put(str, d);
            saveMetrics();
        }
    }

    public void removeMetric(String str) {
        if (str == null) {
            log.warn("Null metric name provided to removeGlobalMetric.");
            return;
        }
        this.endpointProfile.addMetric(str, null);
        this.globalMetrics.remove(str);
        saveMetrics();
    }
}
