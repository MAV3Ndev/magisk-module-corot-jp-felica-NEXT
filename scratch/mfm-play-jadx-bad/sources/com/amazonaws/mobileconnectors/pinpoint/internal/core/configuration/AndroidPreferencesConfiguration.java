package com.amazonaws.mobileconnectors.pinpoint.internal.core.configuration;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.PinpointContext;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.system.AndroidPreferences;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.Preconditions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AndroidPreferencesConfiguration {
    private static final String CONFIG_KEY = "configuration";
    private static final Log log = LogFactory.getLog((Class<?>) AndroidPreferencesConfiguration.class);
    private final PinpointContext context;
    private Map<String, String> properties = new ConcurrentHashMap();

    AndroidPreferencesConfiguration(PinpointContext pinpointContext) {
        String string;
        Preconditions.checkNotNull(pinpointContext);
        this.context = pinpointContext;
        AndroidPreferences preferences = getContext().getSystem().getPreferences();
        JSONObject jSONObject = null;
        if (preferences != null && (string = preferences.getString(CONFIG_KEY, null)) != null) {
            try {
                jSONObject = new JSONObject(string);
            } catch (JSONException unused) {
                log.error("Could not create Json object of Config.");
            }
        }
        updateMappings(jSONObject);
    }

    public static AndroidPreferencesConfiguration newInstance(PinpointContext pinpointContext) {
        return new AndroidPreferencesConfiguration(pinpointContext);
    }

    public Long getLong(String str) {
        String str2 = this.properties.get(str);
        if (str2 == null) {
            return null;
        }
        try {
            return Long.decode(str2);
        } catch (Exception unused) {
            log.error("Could not get Long for property.");
            return null;
        }
    }

    public String getString(String str) {
        return this.properties.get(str);
    }

    public Integer getInt(String str) {
        String str2 = this.properties.get(str);
        if (str2 == null) {
            return null;
        }
        try {
            return Integer.decode(str2);
        } catch (Exception unused) {
            log.error("Could not get Integer for property.");
            return null;
        }
    }

    public Double getDouble(String str) {
        String str2 = this.properties.get(str);
        if (str2 == null) {
            return null;
        }
        try {
            return Double.valueOf(Double.parseDouble(str2));
        } catch (Exception unused) {
            log.error("Could not get Double for property.");
            return null;
        }
    }

    public Boolean getBoolean(String str) {
        String str2 = this.properties.get(str);
        if (str2 == null) {
            return null;
        }
        try {
            return Boolean.valueOf(Boolean.parseBoolean(str2));
        } catch (Exception unused) {
            log.error("Could not get Boolean for property.");
            return null;
        }
    }

    public Short getShort(String str) {
        String str2 = this.properties.get(str);
        if (str2 == null) {
            return null;
        }
        try {
            if (this.properties.containsKey(str)) {
                return Short.decode(str2);
            }
            return null;
        } catch (Exception unused) {
            log.error("Could not get Short for property.");
            return null;
        }
    }

    public Long optLong(String str, Long l) {
        Long l2 = getLong(str);
        return l2 != null ? l2 : l;
    }

    public String optString(String str, String str2) {
        String string = getString(str);
        return string != null ? string : str2;
    }

    public Integer optInt(String str, Integer num) {
        Integer num2 = getInt(str);
        return num2 != null ? num2 : num;
    }

    public Short optShort(String str, Short sh) {
        Short sh2 = getShort(str);
        return sh2 != null ? sh2 : sh;
    }

    public Double optDouble(String str, Double d) {
        Double d2 = getDouble(str);
        return d2 != null ? d2 : d;
    }

    public Boolean optBoolean(String str, Boolean bool) {
        Boolean bool2 = getBoolean(str);
        return bool2 != null ? bool2 : bool;
    }

    private void updateMappings(JSONObject jSONObject) {
        HashMap map = new HashMap();
        if (jSONObject != null) {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                try {
                    map.put(next, jSONObject.getString(next));
                } catch (JSONException unused) {
                    log.error("Could not update property mappings.");
                }
            }
        }
        this.properties.putAll(map);
    }

    private PinpointContext getContext() {
        return this.context;
    }
}
