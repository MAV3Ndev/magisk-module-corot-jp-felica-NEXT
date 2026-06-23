package com.amazonaws.mobileconnectors.pinpoint.internal.event;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.Preconditions;
import com.amazonaws.mobileconnectors.pinpoint.targeting.endpointProfile.EndpointProfileDemographic;
import com.felicanetworks.mfc.mfi.LogSender;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ClientContext {
    public static final String APP_ID_KEY = "app_id";
    private static final String CLIENT_OBJECT_KEY = "client";
    private static final String CUSTOM_OBJECT_KEY = "custom";
    private static final String ENVIRONMENT_OBJECT_KEY = "env";
    private static final String MOBILE_ANALYTICS_KEY = "mobile_analytics";
    private static final String SERVICES_OBJECT_KEY = "services";
    private static final Log log = LogFactory.getLog((Class<?>) ClientContext.class);
    private String appId;
    private String appPackageName;
    private String appTitle;
    private String appVersionCode;
    private String appVersionName;
    private String carrier;
    private Map<String, String> custom;
    private String locale;
    private String make;
    private String model;
    private String networkType;
    private String platform;
    private String platformVersion;
    private String uniqueId;

    private ClientContext() {
        this.appPackageName = "";
        this.appTitle = "";
        this.appVersionName = "";
        this.appVersionCode = "";
        this.uniqueId = "";
        this.model = "";
        this.make = "";
        this.platform = EndpointProfileDemographic.ENDPOINT_PLATFORM;
        this.platformVersion = "";
        this.locale = "en-US";
        this.networkType = "";
        this.carrier = "";
        this.custom = new HashMap();
        this.appId = "";
    }

    public JSONObject toJSONObject() {
        HashMap map = new HashMap();
        map.put("app_package_name", this.appPackageName);
        map.put("app_title", this.appTitle);
        map.put("app_version_name", this.appVersionName);
        map.put("app_version_code", this.appVersionCode);
        map.put("client_id", this.uniqueId);
        HashMap map2 = new HashMap();
        map2.put(LogSender.EXTRA_KEY_MODEL, this.model);
        map2.put("make", this.make);
        map2.put("platform", this.platform);
        map2.put("platform_version", this.platformVersion);
        map2.put("locale", this.locale);
        map2.put("carrier", this.carrier);
        map2.put("networkType", this.networkType);
        HashMap map3 = new HashMap();
        HashMap map4 = new HashMap();
        map4.put(APP_ID_KEY, this.appId);
        map3.put(MOBILE_ANALYTICS_KEY, new JSONObject(map4));
        JSONObject jSONObject = new JSONObject(map);
        JSONObject jSONObject2 = new JSONObject(map2);
        JSONObject jSONObject3 = new JSONObject(this.custom);
        JSONObject jSONObject4 = new JSONObject(map3);
        JSONObject jSONObject5 = new JSONObject();
        try {
            jSONObject5.put(CLIENT_OBJECT_KEY, jSONObject);
            jSONObject5.put(ENVIRONMENT_OBJECT_KEY, jSONObject2);
            jSONObject5.put(CUSTOM_OBJECT_KEY, jSONObject3);
            jSONObject5.put(SERVICES_OBJECT_KEY, jSONObject4);
            return jSONObject5;
        } catch (JSONException unused) {
            log.error("Error creating clientContextJSON.");
            return jSONObject5;
        }
    }

    public String getNetworkType() {
        return this.networkType;
    }

    public void setNetworkType(String str) {
        this.networkType = str;
    }

    public String getCarrier() {
        return this.carrier;
    }

    public void setCarrier(String str) {
        this.carrier = str;
    }

    public String getAppPackageName() {
        return this.appPackageName;
    }

    public void setAppPackageName(String str) {
        this.appPackageName = str;
    }

    public String getAppTitle() {
        return this.appTitle;
    }

    public void setAppTitle(String str) {
        this.appTitle = str;
    }

    public String getAppVersionName() {
        return this.appVersionName;
    }

    public void setAppVersionName(String str) {
        this.appVersionName = str;
    }

    public String getAppVersionCode() {
        return this.appVersionCode;
    }

    public void setAppVersionCode(String str) {
        this.appVersionCode = str;
    }

    public String getUniqueId() {
        return this.uniqueId;
    }

    public void setUniqueId(String str) {
        this.uniqueId = str;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String str) {
        this.model = str;
    }

    public String getMake() {
        return this.make;
    }

    public void setMake(String str) {
        this.make = str;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String str) {
        this.platform = str;
    }

    public String getPlatformVersion() {
        return this.platformVersion;
    }

    public void setPlatformVersion(String str) {
        this.platformVersion = str;
    }

    public String getLocale() {
        return this.locale;
    }

    public void setLocale(String str) {
        this.locale = str;
    }

    public Map<String, String> getCustom() {
        return this.custom;
    }

    public void setCustom(Map<String, String> map) {
        this.custom = map;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public static class ClientContextBuilder {
        private final String platform = EndpointProfileDemographic.ENDPOINT_PLATFORM;
        private String appPackageName = "";
        private String appTitle = "";
        private String appVersionName = "";
        private String appVersionCode = "";
        private String uniqueId = "";
        private String model = "";
        private String make = "";
        private String platformVersion = "";
        private String locale = "";
        private String networkType = "";
        private String carrier = "";
        private Map<String, String> custom = new HashMap();
        private String appId = "";

        public ClientContext build() {
            ClientContext clientContext = new ClientContext();
            clientContext.setAppPackageName(this.appPackageName);
            clientContext.setAppTitle(this.appTitle);
            clientContext.setAppVersionName(this.appVersionName);
            clientContext.setAppVersionCode(this.appVersionCode);
            clientContext.setUniqueId(this.uniqueId);
            clientContext.setMake(this.make);
            clientContext.setModel(this.model);
            clientContext.setPlatform(EndpointProfileDemographic.ENDPOINT_PLATFORM);
            clientContext.setPlatformVersion(this.platformVersion);
            clientContext.setLocale(this.locale);
            clientContext.setNetworkType(this.networkType);
            clientContext.setCarrier(this.carrier);
            clientContext.setCustom(this.custom);
            clientContext.setAppId(this.appId);
            return clientContext;
        }

        public ClientContextBuilder withCarrier(String str) {
            this.carrier = (String) Preconditions.checkNotNull(str);
            return this;
        }

        public ClientContextBuilder withNetworkType(String str) {
            this.networkType = (String) Preconditions.checkNotNull(str);
            return this;
        }

        public ClientContextBuilder withAppPackageName(String str) {
            this.appPackageName = (String) Preconditions.checkNotNull(str);
            return this;
        }

        public ClientContextBuilder withAppTitle(String str) {
            this.appTitle = (String) Preconditions.checkNotNull(str);
            return this;
        }

        public ClientContextBuilder withAppVersionName(String str) {
            this.appVersionName = (String) Preconditions.checkNotNull(str);
            return this;
        }

        public ClientContextBuilder withAppVersionCode(String str) {
            this.appVersionCode = (String) Preconditions.checkNotNull(str);
            return this;
        }

        public ClientContextBuilder withUniqueId(String str) {
            this.uniqueId = (String) Preconditions.checkNotNull(str);
            return this;
        }

        public ClientContextBuilder withModel(String str) {
            this.model = (String) Preconditions.checkNotNull(str);
            return this;
        }

        public ClientContextBuilder withMake(String str) {
            this.make = (String) Preconditions.checkNotNull(str);
            return this;
        }

        public ClientContextBuilder withPlatformVersion(String str) {
            this.platformVersion = (String) Preconditions.checkNotNull(str);
            return this;
        }

        public ClientContextBuilder withCustomFields(Map<String, String> map) {
            this.custom = (Map) Preconditions.checkNotNull(map);
            return this;
        }

        public ClientContextBuilder withLocale(String str) {
            this.locale = (String) Preconditions.checkNotNull(str);
            return this;
        }

        public ClientContextBuilder withAppId(String str) {
            this.appId = str;
            return this;
        }
    }
}
