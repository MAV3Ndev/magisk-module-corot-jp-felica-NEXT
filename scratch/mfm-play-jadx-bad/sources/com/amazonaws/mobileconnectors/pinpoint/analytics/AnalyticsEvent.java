package com.amazonaws.mobileconnectors.pinpoint.analytics;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.PinpointContext;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.system.AndroidAppDetails;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.system.AndroidDeviceDetails;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.JSONBuilder;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.JSONSerializable;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.SDKInfo;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.amazonaws.mobileconnectors.pinpoint.internal.event.ClientContext;
import com.amazonaws.mobileconnectors.pinpoint.internal.event.EventTable;
import com.felicanetworks.mfc.mfi.LogSender;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AnalyticsEvent implements JSONSerializable {
    private static final int INDENTATION = 4;
    static final int MAX_EVENT_ATTRIBUTE_METRIC_KEY_LENGTH = 50;
    static final int MAX_EVENT_ATTRIBUTE_VALUE_LENGTH = 1000;
    static final int MAX_NUM_OF_METRICS_AND_ATTRIBUTES = 50;
    private static final Log log = LogFactory.getLog((Class<?>) AnalyticsEvent.class);
    private final AndroidAppDetails appDetails;
    private final Map<String, String> attributes;
    private final AtomicInteger currentNumOfAttributesAndMetrics;
    private final AndroidDeviceDetails deviceDetails;
    private final String eventId;
    private final String eventType;
    private final Map<String, Double> metrics;
    private final String sdkName;
    private final String sdkVersion;
    private final PinpointSession session;
    private final Long timestamp;
    private final String uniqueId;

    AnalyticsEvent(String str, Map<String, String> map, Map<String, Double> map2, SDKInfo sDKInfo, String str2, long j, Long l, Long l2, long j2, String str3, AndroidAppDetails androidAppDetails, AndroidDeviceDetails androidDeviceDetails) {
        this(UUID.randomUUID().toString(), str, map, map2, sDKInfo, str2, j, l, l2, j2, str3, androidAppDetails, androidDeviceDetails);
    }

    private AnalyticsEvent(String str, String str2, Map<String, String> map, Map<String, Double> map2, SDKInfo sDKInfo, String str3, long j, Long l, Long l2, long j2, String str4, AndroidAppDetails androidAppDetails, AndroidDeviceDetails androidDeviceDetails) {
        this.attributes = new ConcurrentHashMap();
        this.metrics = new ConcurrentHashMap();
        this.currentNumOfAttributesAndMetrics = new AtomicInteger(0);
        this.eventId = str;
        this.sdkName = sDKInfo.getName();
        this.sdkVersion = sDKInfo.getVersion();
        this.session = new PinpointSession(str3, Long.valueOf(j), l, l2);
        this.timestamp = Long.valueOf(j2);
        this.uniqueId = str4;
        this.eventType = str2;
        this.appDetails = androidAppDetails;
        this.deviceDetails = androidDeviceDetails;
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                addAttribute(entry.getKey(), entry.getValue());
            }
        }
        if (map2 != null) {
            for (Map.Entry<String, Double> entry2 : map2.entrySet()) {
                addMetric(entry2.getKey(), entry2.getValue());
            }
        }
    }

    public static AnalyticsEvent createFromEvent(PinpointContext pinpointContext, String str, long j, AnalyticsEvent analyticsEvent) {
        return new AnalyticsEvent(analyticsEvent.getEventId(), analyticsEvent.getEventType(), analyticsEvent.getAllAttributes(), analyticsEvent.getAllMetrics(), pinpointContext.getSDKInfo(), str, analyticsEvent.getSession().getSessionStart().longValue(), analyticsEvent.getSession().getSessionStop(), analyticsEvent.getSession().getSessionDuration(), j, pinpointContext.getUniqueId(), pinpointContext.getSystem().getAppDetails(), pinpointContext.getSystem().getDeviceDetails());
    }

    public static AnalyticsEvent newInstance(PinpointContext pinpointContext, String str, Long l, Long l2, Long l3, long j, String str2) {
        return new AnalyticsEvent(str2, null, null, pinpointContext.getSDKInfo(), str, l.longValue(), l2, l3, j, pinpointContext.getUniqueId(), pinpointContext.getSystem().getAppDetails(), pinpointContext.getSystem().getDeviceDetails());
    }

    public static AnalyticsEvent newInstance(String str, String str2, Map<String, String> map, Map<String, Double> map2, SDKInfo sDKInfo, String str3, Long l, Long l2, Long l3, long j, String str4, AndroidAppDetails androidAppDetails, AndroidDeviceDetails androidDeviceDetails) {
        return new AnalyticsEvent(str, str2, map, map2, sDKInfo, str3, l.longValue(), l2, l3, j, str4, androidAppDetails, androidDeviceDetails);
    }

    private static String processAttributeMetricKey(String str) {
        String strClipString = StringUtil.clipString(str, 50, false);
        if (strClipString.length() < str.length()) {
            log.warn("The attribute key has been trimmed to a length of 50 characters.");
        }
        return strClipString;
    }

    private static String processAttributeValue(String str) {
        String strClipString = StringUtil.clipString(str, 1000, false);
        if (strClipString.length() < str.length()) {
            log.warn("The attribute value has been trimmed to a length of 1000 characters.");
        }
        return strClipString;
    }

    public static JSONObject translateFromEvent(AnalyticsEvent analyticsEvent) {
        if (analyticsEvent == null) {
            log.warn("The Event provided was null");
            return new JSONObject();
        }
        JSONObject jSONObject = analyticsEvent.toJSONObject();
        if (jSONObject.has("class")) {
            jSONObject.remove("class");
        }
        if (jSONObject.has("hashCode")) {
            jSONObject.remove("hashCode");
        }
        return jSONObject;
    }

    public static AnalyticsEvent translateToEvent(JSONObject jSONObject) throws JSONException {
        SDKInfo sDKInfo;
        String string;
        Long lValueOf;
        Long lValueOf2;
        Long lValueOf3;
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        AndroidAppDetails androidAppDetails = new AndroidAppDetails(jSONObject.optString("app_package_name"), jSONObject.optString("app_version_code"), jSONObject.optString("app_version_name"), jSONObject.optString("app_title"), jSONObject.optString(ClientContext.APP_ID_KEY));
        SDKInfo sDKInfo2 = new SDKInfo(jSONObject.optString("sdk_name"), jSONObject.optString("sdk_version"));
        AndroidDeviceDetails androidDeviceDetails = new AndroidDeviceDetails(jSONObject.optString("carrier"));
        String string2 = jSONObject.getString(EventTable.COLUMN_ID);
        String string3 = jSONObject.getString("event_type");
        long j = jSONObject.getLong("timestamp");
        Long lValueOf4 = Long.valueOf(j);
        String string4 = jSONObject.getString("unique_id");
        JSONObject jSONObject2 = jSONObject.getJSONObject("session");
        if (jSONObject2 == null) {
            sDKInfo = sDKInfo2;
            string = "";
            lValueOf = null;
            lValueOf2 = null;
            lValueOf3 = null;
        } else {
            string = jSONObject2.getString("id");
            lValueOf = Long.valueOf(jSONObject2.getLong("startTimestamp"));
            lValueOf3 = Long.valueOf(jSONObject2.optLong("stopTimestamp"));
            sDKInfo = sDKInfo2;
            lValueOf2 = Long.valueOf(jSONObject2.optLong(TypedValues.TransitionType.S_DURATION));
        }
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("attributes");
        if (jSONObjectOptJSONObject != null) {
            Iterator<String> itKeys = jSONObjectOptJSONObject.keys();
            while (itKeys.hasNext()) {
                AndroidAppDetails androidAppDetails2 = androidAppDetails;
                String next = itKeys.next();
                map.put(next, jSONObjectOptJSONObject.optString(next));
                string2 = string2;
                androidAppDetails = androidAppDetails2;
            }
        }
        AndroidAppDetails androidAppDetails3 = androidAppDetails;
        String str = string2;
        JSONObject jSONObjectOptJSONObject2 = jSONObject.optJSONObject("metrics");
        if (jSONObjectOptJSONObject2 != null) {
            Iterator<String> itKeys2 = jSONObjectOptJSONObject2.keys();
            while (itKeys2.hasNext()) {
                String next2 = itKeys2.next();
                try {
                    map2.put(next2, Double.valueOf(jSONObjectOptJSONObject2.getDouble(next2)));
                } catch (JSONException unused) {
                    log.error("Failed to convert metric back to double from JSON value.");
                }
            }
        }
        lValueOf4.getClass();
        return newInstance(str, string3, map, map2, sDKInfo, string, lValueOf, lValueOf3, lValueOf2, j, string4, androidAppDetails3, androidDeviceDetails);
    }

    public String getEventId() {
        return this.eventId;
    }

    public void addAttribute(String str, String str2) {
        if (str == null) {
            return;
        }
        if (str2 != null) {
            if (this.currentNumOfAttributesAndMetrics.get() < 50) {
                this.attributes.put(processAttributeMetricKey(str), processAttributeValue(str2));
                this.currentNumOfAttributesAndMetrics.incrementAndGet();
                return;
            } else {
                log.warn("Max number of attributes/metrics reached(50).");
                return;
            }
        }
        this.attributes.remove(str);
    }

    public boolean hasAttribute(String str) {
        if (str == null) {
            return false;
        }
        return this.attributes.containsKey(str);
    }

    public void addMetric(String str, Double d) {
        if (str == null) {
            return;
        }
        if (d != null) {
            if (this.currentNumOfAttributesAndMetrics.get() < 50) {
                this.metrics.put(processAttributeMetricKey(str), d);
                this.currentNumOfAttributesAndMetrics.incrementAndGet();
                return;
            } else {
                log.warn("Max number of attributes/metrics reached(50).");
                return;
            }
        }
        this.metrics.remove(str);
    }

    public boolean hasMetric(String str) {
        if (str == null) {
            return false;
        }
        return this.metrics.containsKey(str);
    }

    public String getEventType() {
        return this.eventType;
    }

    public String getAttribute(String str) {
        if (str == null) {
            return null;
        }
        return this.attributes.get(str);
    }

    public Double getMetric(String str) {
        if (str == null) {
            return null;
        }
        return this.metrics.get(str);
    }

    public PinpointSession getSession() {
        return this.session;
    }

    public Long getEventTimestamp() {
        return this.timestamp;
    }

    public String getUniqueId() {
        return this.uniqueId;
    }

    public String getSdkName() {
        return this.sdkName;
    }

    public String getSdkVersion() {
        return this.sdkVersion;
    }

    public AnalyticsEvent withAttribute(String str, String str2) {
        addAttribute(str, str2);
        return this;
    }

    public AnalyticsEvent withMetric(String str, Double d) {
        addMetric(str, d);
        return this;
    }

    public Map<String, String> getAllAttributes() {
        return Collections.unmodifiableMap(this.attributes);
    }

    public Map<String, Double> getAllMetrics() {
        return Collections.unmodifiableMap(this.metrics);
    }

    public AndroidAppDetails getAppDetails() {
        return this.appDetails;
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
        Locale locale = this.deviceDetails.locale();
        String string = locale != null ? locale.toString() : "UNKNOWN";
        JSONBuilder jSONBuilder = new JSONBuilder(this);
        jSONBuilder.withAttribute(EventTable.COLUMN_ID, getEventId());
        jSONBuilder.withAttribute("event_type", getEventType());
        jSONBuilder.withAttribute("unique_id", getUniqueId());
        jSONBuilder.withAttribute("timestamp", getEventTimestamp());
        jSONBuilder.withAttribute("platform", this.deviceDetails.platform());
        jSONBuilder.withAttribute("platform_version", this.deviceDetails.platformVersion());
        jSONBuilder.withAttribute("make", this.deviceDetails.manufacturer());
        jSONBuilder.withAttribute(LogSender.EXTRA_KEY_MODEL, this.deviceDetails.model());
        jSONBuilder.withAttribute("locale", string);
        jSONBuilder.withAttribute("carrier", this.deviceDetails.carrier());
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", this.session.getSessionId());
            if (this.session.getSessionStart() != null) {
                jSONObject.put("startTimestamp", this.session.getSessionStart());
            }
            if (this.session.getSessionStop() != null) {
                jSONObject.put("stopTimestamp", this.session.getSessionStop());
            }
            if (this.session.getSessionDuration() != null) {
                jSONObject.put(TypedValues.TransitionType.S_DURATION, this.session.getSessionDuration().longValue());
            }
        } catch (JSONException e) {
            log.error("Error serializing session information", e);
        }
        jSONBuilder.withAttribute("session", jSONObject);
        jSONBuilder.withAttribute("sdk_version", this.sdkVersion);
        jSONBuilder.withAttribute("sdk_name", this.sdkName);
        jSONBuilder.withAttribute("app_version_name", this.appDetails.versionName());
        jSONBuilder.withAttribute("app_version_code", this.appDetails.versionCode());
        jSONBuilder.withAttribute("app_package_name", this.appDetails.packageName());
        jSONBuilder.withAttribute("app_title", this.appDetails.getAppTitle());
        jSONBuilder.withAttribute(ClientContext.APP_ID_KEY, this.appDetails.getAppId());
        JSONObject jSONObject2 = new JSONObject();
        for (Map.Entry<String, String> entry : getAllAttributes().entrySet()) {
            try {
                jSONObject2.put(entry.getKey(), entry.getValue());
            } catch (JSONException unused) {
                log.error("Error serializing attribute for eventType: " + this.eventType);
            }
        }
        JSONObject jSONObject3 = new JSONObject();
        for (Map.Entry<String, Double> entry2 : getAllMetrics().entrySet()) {
            try {
                jSONObject3.put(entry2.getKey(), entry2.getValue());
            } catch (JSONException unused2) {
                log.error("Error serializing metric for eventType: " + this.eventType);
            }
        }
        if (jSONObject2.length() > 0) {
            jSONBuilder.withAttribute("attributes", jSONObject2);
        }
        if (jSONObject3.length() > 0) {
            jSONBuilder.withAttribute("metrics", jSONObject3);
        }
        return jSONBuilder.toJSONObject();
    }

    public ClientContext createClientContext(String str) {
        ClientContext.ClientContextBuilder clientContextBuilder = new ClientContext.ClientContextBuilder();
        clientContextBuilder.withAppPackageName(this.appDetails.packageName()).withAppVersionCode(this.appDetails.versionCode()).withAppVersionName(this.appDetails.versionName()).withLocale(this.deviceDetails.locale().toString()).withMake(this.deviceDetails.manufacturer()).withModel(this.deviceDetails.model()).withPlatformVersion(this.deviceDetails.platformVersion()).withUniqueId(this.uniqueId).withAppTitle(this.appDetails.getAppTitle()).withNetworkType(str).withCarrier(this.deviceDetails.carrier()).withAppId(this.appDetails.getAppId());
        return clientContextBuilder.build();
    }
}
