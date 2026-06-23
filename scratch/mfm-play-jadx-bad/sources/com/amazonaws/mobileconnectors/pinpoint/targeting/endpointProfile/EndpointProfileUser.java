package com.amazonaws.mobileconnectors.pinpoint.targeting.endpointProfile;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.JSONBuilder;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.JSONSerializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class EndpointProfileUser implements JSONSerializable {
    private static final Log log = LogFactory.getLog((Class<?>) EndpointProfileUser.class);
    private Map<String, List<String>> userAttributes;
    private String userId;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public Map<String, List<String>> getUserAttributes() {
        return this.userAttributes;
    }

    public void setUserAttributes(Map<String, List<String>> map) {
        this.userAttributes = map;
    }

    public EndpointProfileUser addUserAttribute(String str, List<String> list) {
        if (this.userAttributes == null) {
            this.userAttributes = new ConcurrentHashMap();
        }
        this.userAttributes.put(str, list);
        return this;
    }

    @Override // com.amazonaws.mobileconnectors.pinpoint.internal.core.util.JSONSerializable
    public JSONObject toJSONObject() {
        JSONBuilder jSONBuilder = new JSONBuilder(null);
        jSONBuilder.withAttribute("UserId", getUserId());
        if (getUserAttributes() != null) {
            JSONObject jSONObject = new JSONObject();
            for (Map.Entry<String, List<String>> entry : getUserAttributes().entrySet()) {
                try {
                    jSONObject.put(entry.getKey(), new JSONArray((Collection) entry.getValue()));
                } catch (JSONException unused) {
                    log.warn("Error serializing user attributes.");
                }
            }
            if (jSONObject.length() > 0) {
                jSONBuilder.withAttribute("UserAttributes", jSONObject);
            }
        }
        return jSONBuilder.toJSONObject();
    }
}
