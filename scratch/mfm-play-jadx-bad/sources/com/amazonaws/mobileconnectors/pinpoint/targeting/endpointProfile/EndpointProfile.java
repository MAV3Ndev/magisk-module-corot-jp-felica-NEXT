package com.amazonaws.mobileconnectors.pinpoint.targeting.endpointProfile;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.PinpointContext;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.DateUtil;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.JSONBuilder;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.JSONSerializable;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.Preconditions;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class EndpointProfile implements JSONSerializable {
    private static final int JSON_INDENTATION = 4;
    static final int MAX_ENDPOINT_ATTRIBUTE_METRIC_KEY_LENGTH = 50;
    static final int MAX_ENDPOINT_ATTRIBUTE_VALUES = 50;
    static final int MAX_ENDPOINT_ATTRIBUTE_VALUE_LENGTH = 100;
    static final int MAX_NUM_OF_METRICS_AND_ATTRIBUTES = 20;
    private static final Log log = LogFactory.getLog((Class<?>) EndpointProfile.class);
    private EndpointProfileDemographic demographic;
    private Long effectiveDate;
    private EndpointProfileLocation location;
    private String optOut;
    private final PinpointContext pinpointContext;
    private EndpointProfileUser user;
    private final Map<String, List<String>> attributes = new ConcurrentHashMap();
    private final Map<String, Double> metrics = new ConcurrentHashMap();
    private final AtomicInteger currentNumOfAttributesAndMetrics = new AtomicInteger(0);

    public EndpointProfile(PinpointContext pinpointContext) {
        Preconditions.checkNotNull(pinpointContext, "A valid pinpointContext must be provided.");
        this.pinpointContext = pinpointContext;
        this.effectiveDate = Long.valueOf(DateUtil.getCorrectedDate().getTime());
        this.demographic = new EndpointProfileDemographic(pinpointContext);
        this.location = new EndpointProfileLocation(pinpointContext);
        this.user = new EndpointProfileUser();
    }

    private static String processAttributeMetricKey(String str) {
        String strClipString = StringUtil.clipString(str, 50, false);
        if (strClipString.length() < str.length()) {
            log.warn("The attribute key has been trimmed to a length of 50 characters.");
        }
        return strClipString;
    }

    private static List<String> processAttributeValues(List<String> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = list.iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String next = it.next();
            String strClipString = StringUtil.clipString(next, 100, false);
            if (strClipString.length() < next.length()) {
                log.warn("The attribute value has been trimmed to a length of 100 characters.");
            }
            arrayList.add(strClipString);
            i++;
            if (i >= 50) {
                log.warn("The attribute values has been reduced to50 values.");
                break;
            }
        }
        return arrayList;
    }

    public String getApplicationId() {
        return this.pinpointContext.getSystem().getAppDetails().getAppId();
    }

    public String getEndpointId() {
        return this.pinpointContext.getUniqueId();
    }

    public String getChannelType() {
        return this.pinpointContext.getNotificationClient().getChannelType();
    }

    public String getAddress() {
        return this.pinpointContext.getNotificationClient().getDeviceToken();
    }

    public EndpointProfileDemographic getDemographic() {
        return this.demographic;
    }

    public void setDemographic(EndpointProfileDemographic endpointProfileDemographic) {
        this.demographic = endpointProfileDemographic;
    }

    public EndpointProfileLocation getLocation() {
        return this.location;
    }

    public void setLocation(EndpointProfileLocation endpointProfileLocation) {
        this.location = endpointProfileLocation;
    }

    public long getEffectiveDate() {
        return this.effectiveDate.longValue();
    }

    public void setEffectiveDate(long j) {
        this.effectiveDate = Long.valueOf(j);
    }

    public String getOptOut() {
        return (!this.pinpointContext.getNotificationClient().areAppNotificationsEnabled() || StringUtil.isBlank(this.pinpointContext.getNotificationClient().getDeviceToken())) ? "ALL" : "NONE";
    }

    public void addAttribute(String str, List<String> list) {
        if (str == null) {
            return;
        }
        if (list != null) {
            if (this.currentNumOfAttributesAndMetrics.get() < 20) {
                String strProcessAttributeMetricKey = processAttributeMetricKey(str);
                if (!this.attributes.containsKey(strProcessAttributeMetricKey)) {
                    this.currentNumOfAttributesAndMetrics.incrementAndGet();
                }
                this.attributes.put(strProcessAttributeMetricKey, processAttributeValues(list));
                return;
            }
            log.warn("Max number of attributes/metrics reached(20).");
            return;
        }
        if (this.attributes.remove(str) != null) {
            this.currentNumOfAttributesAndMetrics.decrementAndGet();
        }
    }

    public boolean hasAttribute(String str) {
        if (str == null) {
            return false;
        }
        return this.attributes.containsKey(str);
    }

    public List<String> getAttribute(String str) {
        if (str == null) {
            return null;
        }
        return this.attributes.get(str);
    }

    public EndpointProfile withAttribute(String str, List<String> list) {
        addAttribute(str, list);
        return this;
    }

    public Map<String, List<String>> getAllAttributes() {
        return Collections.unmodifiableMap(this.attributes);
    }

    public void addMetric(String str, Double d) {
        if (str == null) {
            return;
        }
        if (d != null) {
            if (this.currentNumOfAttributesAndMetrics.get() < 20) {
                String strProcessAttributeMetricKey = processAttributeMetricKey(str);
                if (!this.metrics.containsKey(strProcessAttributeMetricKey)) {
                    this.currentNumOfAttributesAndMetrics.incrementAndGet();
                }
                this.metrics.put(strProcessAttributeMetricKey, d);
                return;
            }
            log.warn("Max number of attributes/metrics reached(20).");
            return;
        }
        if (this.metrics.remove(str) != null) {
            this.currentNumOfAttributesAndMetrics.decrementAndGet();
        }
    }

    public boolean hasMetric(String str) {
        if (str == null) {
            return false;
        }
        return this.metrics.containsKey(str);
    }

    public Double getMetric(String str) {
        if (str == null) {
            return null;
        }
        return this.metrics.get(str);
    }

    public EndpointProfile withMetric(String str, Double d) {
        addMetric(str, d);
        return this;
    }

    public Map<String, Double> getAllMetrics() {
        return Collections.unmodifiableMap(this.metrics);
    }

    public EndpointProfileUser getUser() {
        return this.user;
    }

    public void setUser(EndpointProfileUser endpointProfileUser) {
        this.user = endpointProfileUser;
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
        JSONBuilder jSONBuilder = new JSONBuilder(null);
        jSONBuilder.withAttribute("ApplicationId", getApplicationId());
        jSONBuilder.withAttribute("EndpointId", getEndpointId());
        jSONBuilder.withAttribute("ChannelType", getChannelType());
        jSONBuilder.withAttribute("Address", getAddress());
        jSONBuilder.withAttribute("Location", getLocation().toJSONObject());
        jSONBuilder.withAttribute("Demographic", getDemographic().toJSONObject());
        jSONBuilder.withAttribute("EffectiveDate", DateUtil.isoDateFromMillis(getEffectiveDate()));
        jSONBuilder.withAttribute("OptOut", getOptOut());
        JSONObject jSONObject = new JSONObject();
        for (Map.Entry<String, List<String>> entry : getAllAttributes().entrySet()) {
            try {
                jSONObject.put(entry.getKey(), new JSONArray((Collection) entry.getValue()));
            } catch (JSONException unused) {
                log.warn("Error serializing attributes.");
            }
        }
        if (jSONObject.length() > 0) {
            jSONBuilder.withAttribute("Attributes", jSONObject);
        }
        JSONObject jSONObject2 = new JSONObject();
        for (Map.Entry<String, Double> entry2 : getAllMetrics().entrySet()) {
            try {
                jSONObject2.put(entry2.getKey(), entry2.getValue());
            } catch (JSONException unused2) {
                log.error("Error serializing metric.");
            }
        }
        if (jSONObject2.length() > 0) {
            jSONBuilder.withAttribute("Metrics", jSONObject2);
        }
        jSONBuilder.withAttribute("User", getUser().toJSONObject());
        return jSONBuilder.toJSONObject();
    }
}
