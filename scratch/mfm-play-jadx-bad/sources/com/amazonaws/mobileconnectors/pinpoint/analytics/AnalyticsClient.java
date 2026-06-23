package com.amazonaws.mobileconnectors.pinpoint.analytics;

import androidx.core.util.Consumer;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.PinpointContext;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.JSONBuilder;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.JSONSerializable;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.Preconditions;
import com.amazonaws.mobileconnectors.pinpoint.internal.event.EventRecorder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AnalyticsClient implements JSONSerializable {
    private static final String ANALYTICS_ENABLED = "isAnalyticsEnabled";
    private static final int INDENTATION = 4;
    private static final int MAX_EVENT_TYPE_LENGTH = 50;
    private static final Log log = LogFactory.getLog((Class<?>) AnalyticsClient.class);
    private final PinpointContext context;
    private EventRecorder eventRecorder;
    private String sessionId;
    private long sessionStartTime;
    private final Map<String, String> globalAttributes = new ConcurrentHashMap();
    private final Map<String, Double> globalMetrics = new ConcurrentHashMap();
    private final Map<String, Map<String, String>> eventTypeAttributes = new ConcurrentHashMap();
    private final Map<String, Map<String, Double>> eventTypeMetrics = new ConcurrentHashMap();
    private final Map<String, String> eventSourceAttributes = new ConcurrentHashMap();

    public AnalyticsClient(PinpointContext pinpointContext) {
        Preconditions.checkNotNull(pinpointContext, "A valid pinpointContext must be provided");
        this.context = pinpointContext;
        this.eventRecorder = EventRecorder.newInstance(pinpointContext);
    }

    public AnalyticsEvent createEvent(String str) {
        if (str == null) {
            log.error("Null eventType provided to addGlobalAttribute.");
            throw new IllegalArgumentException("The eventType passed into create event was null");
        }
        if (str.length() > 50) {
            log.error("The event type is too long, the max event type length is 50 characters.");
            throw new IllegalArgumentException("The eventType passed into create event was too long");
        }
        return createEvent(str, this.sessionStartTime, null, null);
    }

    protected AnalyticsEvent createEvent(String str, long j, Long l, Long l2) {
        AnalyticsEvent analyticsEventNewInstance = AnalyticsEvent.newInstance(this.context, this.sessionId, Long.valueOf(j), l, l2, System.currentTimeMillis(), str);
        for (Map.Entry<String, String> entry : this.globalAttributes.entrySet()) {
            analyticsEventNewInstance.addAttribute(entry.getKey(), entry.getValue());
        }
        if (this.eventTypeAttributes.containsKey(analyticsEventNewInstance.getEventType())) {
            for (Map.Entry<String, String> entry2 : this.eventTypeAttributes.get(analyticsEventNewInstance.getEventType()).entrySet()) {
                analyticsEventNewInstance.addAttribute(entry2.getKey(), entry2.getValue());
            }
        }
        for (Map.Entry<String, Double> entry3 : this.globalMetrics.entrySet()) {
            analyticsEventNewInstance.addMetric(entry3.getKey(), entry3.getValue());
        }
        if (this.eventTypeMetrics.containsKey(analyticsEventNewInstance.getEventType())) {
            for (Map.Entry<String, Double> entry4 : this.eventTypeMetrics.get(analyticsEventNewInstance.getEventType()).entrySet()) {
                analyticsEventNewInstance.addMetric(entry4.getKey(), entry4.getValue());
            }
        }
        return analyticsEventNewInstance;
    }

    public void recordEvent(AnalyticsEvent analyticsEvent) {
        if (analyticsEvent == null) {
            log.info("The provided event was null.");
        } else {
            this.eventRecorder.recordEvent(AnalyticsEvent.createFromEvent(this.context, this.sessionId, System.currentTimeMillis(), analyticsEvent));
        }
    }

    public void submitEvents() {
        log.info("Submitting events.");
        this.eventRecorder.submitEvents();
    }

    public void submitEvents(Consumer<List<AnalyticsEvent>> consumer, Consumer<Exception> consumer2) {
        log.info("Submitting events.");
        try {
            consumer.accept(this.eventRecorder.submitEventsWithResult().get());
        } catch (InterruptedException | ExecutionException e) {
            consumer2.accept(e);
        }
    }

    public void addGlobalAttribute(String str, String str2) {
        if (str == null) {
            log.warn("Null attribute name provided to addGlobalAttribute.");
        } else if (str2 == null) {
            log.warn("Null attribute value provided to addGlobalAttribute.");
        } else {
            this.globalAttributes.put(str, str2);
        }
    }

    public void addGlobalAttribute(String str, String str2, String str3) {
        if (str == null) {
            log.warn("Null eventType provided to addGlobalAttribute.");
            return;
        }
        if (str2 == null) {
            log.warn("Null attribute name provided to addGlobalAttribute. eventType:" + str);
            return;
        }
        if (str3 == null) {
            log.warn("Null value provided to addGlobalAttribute. eventType:" + str);
            return;
        }
        Map<String, String> concurrentHashMap = this.eventTypeAttributes.get(str);
        if (concurrentHashMap == null) {
            concurrentHashMap = new ConcurrentHashMap<>();
            this.eventTypeAttributes.put(str, concurrentHashMap);
        }
        concurrentHashMap.put(str2, str3);
    }

    public void addGlobalMetric(String str, Double d) {
        if (str == null) {
            log.warn("Null metric name provided to addGlobalMetric.");
        } else if (d == null) {
            log.warn("Null metric value provided to addGlobalMetric.");
        } else {
            this.globalMetrics.put(str, d);
        }
    }

    public void addGlobalMetric(String str, String str2, Double d) {
        if (str == null) {
            log.warn("Null eventType provided to addGlobalMetric.");
            return;
        }
        if (str2 == null) {
            log.warn("Null metric name provided to addGlobalMetric. eventType:" + str);
            return;
        }
        if (d == null) {
            log.warn("Null metric value provided to addGlobalMetric. eventType:" + str);
            return;
        }
        Map<String, Double> concurrentHashMap = this.eventTypeMetrics.get(str);
        if (concurrentHashMap == null) {
            concurrentHashMap = new ConcurrentHashMap<>();
            this.eventTypeMetrics.put(str, concurrentHashMap);
        }
        concurrentHashMap.put(str2, d);
    }

    public void removeGlobalAttribute(String str) {
        if (str == null) {
            log.warn("Null attribute name provided to removeGlobalAttribute.");
        } else {
            this.globalAttributes.remove(str);
        }
    }

    public void removeGlobalAttribute(String str, String str2) {
        if (str == null) {
            log.warn("Null eventType provided to removeGlobalAttribute.");
            return;
        }
        if (str2 == null) {
            log.warn("Null attribute name provided to removeGlobalAttribute.");
            return;
        }
        Map<String, String> map = this.eventTypeAttributes.get(str);
        if (map != null) {
            map.remove(str2);
        }
    }

    public void removeGlobalMetric(String str) {
        if (str == null) {
            log.warn("Null metric name provided to removeGlobalMetric.");
        } else {
            this.globalMetrics.remove(str);
        }
    }

    public void removeGlobalMetric(String str, String str2) {
        if (str == null) {
            log.warn("Null eventType provided to removeGlobalMetric.");
            return;
        }
        if (str2 == null) {
            log.warn("Null metric name provided to removeGlobalMetric.");
            return;
        }
        Map<String, Double> map = this.eventTypeMetrics.get(str);
        if (map != null) {
            map.remove(str2);
        }
    }

    @Deprecated
    public void setCampaignAttributes(Map<String, String> map) {
        updateEventSourceGlobally(map);
    }

    public void updateEventSourceGlobally(Map<String, String> map) {
        clearEventSourceAttributes();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                addGlobalAttribute(entry.getKey(), entry.getValue());
            }
        }
        this.eventSourceAttributes.putAll(map);
    }

    void clearEventSourceAttributes() {
        Iterator<String> it = this.eventSourceAttributes.keySet().iterator();
        while (it.hasNext()) {
            removeGlobalAttribute(it.next());
        }
        this.eventSourceAttributes.clear();
    }

    public String toString() {
        JSONObject jSONObject = toJSONObject();
        try {
            return jSONObject.toString(4);
        } catch (JSONException unused) {
            return jSONObject.toString();
        }
    }

    @Override // com.amazonaws.mobileconnectors.pinpoint.internal.core.util.JSONSerializable
    public JSONObject toJSONObject() {
        JSONArray jSONArray = new JSONArray();
        JSONArray jSONArray2 = new JSONArray();
        Map<String, String> map = this.globalAttributes;
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(entry.getKey(), entry.getValue());
                    jSONArray2.put(jSONObject);
                } catch (JSONException unused) {
                    log.error("Error parsing Global Attributes.");
                }
            }
        }
        JSONArray jSONArray3 = new JSONArray();
        Map<String, Double> map2 = this.globalMetrics;
        if (map2 != null) {
            for (Map.Entry<String, Double> entry2 : map2.entrySet()) {
                try {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put(entry2.getKey(), entry2.getValue());
                    jSONArray3.put(jSONObject2);
                } catch (JSONException unused2) {
                    log.error("Error parsing Global Metrics.");
                }
            }
        }
        JSONObject jSONObject3 = new JSONObject();
        Map<String, Map<String, String>> map3 = this.eventTypeAttributes;
        if (map3 != null) {
            for (Map.Entry<String, Map<String, String>> entry3 : map3.entrySet()) {
                JSONArray jSONArray4 = new JSONArray();
                try {
                    for (Map.Entry<String, String> entry4 : entry3.getValue().entrySet()) {
                        JSONObject jSONObject4 = new JSONObject();
                        jSONObject4.put(entry4.getKey(), entry4.getValue());
                        jSONArray4.put(jSONObject4);
                    }
                    jSONObject3.put(entry3.getKey(), jSONArray4);
                } catch (JSONException unused3) {
                    log.error("Error parsing Event Type Attributes.");
                }
            }
        }
        JSONObject jSONObject5 = new JSONObject();
        Map<String, Map<String, Double>> map4 = this.eventTypeMetrics;
        if (map4 != null) {
            for (Map.Entry<String, Map<String, Double>> entry5 : map4.entrySet()) {
                JSONArray jSONArray5 = new JSONArray();
                try {
                    for (Map.Entry<String, Double> entry6 : entry5.getValue().entrySet()) {
                        JSONObject jSONObject6 = new JSONObject();
                        jSONObject6.put(entry6.getKey(), entry6.getValue());
                        jSONArray5.put(jSONObject6);
                    }
                    jSONObject5.put(entry5.getKey(), jSONArray5);
                } catch (JSONException unused4) {
                    log.error("Error parsing Event Type Metrics.");
                }
            }
        }
        return new JSONBuilder(this).withAttribute("uniqueId", this.context.getUniqueId()).withAttribute("observers", jSONArray).withAttribute("globalAttributes", jSONArray2).withAttribute("globalMetrics", jSONArray3).withAttribute("eventTypeAttributes", jSONObject3).withAttribute("eventTypeMetrics", jSONObject5).toJSONObject();
    }

    public void closeDB() {
        this.eventRecorder.closeDB();
    }

    public List<JSONObject> getAllEvents() {
        return this.eventRecorder.getAllEvents();
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public long getSessionStartTime() {
        return this.sessionStartTime;
    }

    public void setSessionStartTime(long j) {
        this.sessionStartTime = j;
    }

    void setEventRecorder(EventRecorder eventRecorder) {
        this.eventRecorder = eventRecorder;
    }
}
