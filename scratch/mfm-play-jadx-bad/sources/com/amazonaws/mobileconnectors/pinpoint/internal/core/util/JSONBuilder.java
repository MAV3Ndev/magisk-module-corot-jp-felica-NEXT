package com.amazonaws.mobileconnectors.pinpoint.internal.core.util;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class JSONBuilder implements JSONSerializable {
    private static final int INDENTATION = 4;
    private static final Log LOGGER = LogFactory.getLog((Class<?>) JSONBuilder.class);
    private final JSONObject json = new JSONObject();

    public JSONBuilder(Object obj) {
        if (obj != null) {
            withAttribute("class", obj.getClass().getName());
            withAttribute("hashCode", Integer.toHexString(obj.hashCode()));
        }
    }

    public JSONBuilder withAttribute(String str, Object obj) {
        if (obj instanceof JSONSerializable) {
            obj = ((JSONSerializable) obj).toJSONObject();
        }
        try {
            this.json.putOpt(str, obj);
            return this;
        } catch (JSONException e) {
            LOGGER.warn("error parsing json", e);
            return this;
        }
    }

    @Override // com.amazonaws.mobileconnectors.pinpoint.internal.core.util.JSONSerializable
    public JSONObject toJSONObject() {
        return this.json;
    }

    public String toString() {
        try {
            JSONObject jSONObject = this.json;
            if (jSONObject != null) {
                return jSONObject.toString(4);
            }
            return "";
        } catch (JSONException unused) {
            return this.json.toString();
        }
    }
}
