package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class JsonUtil {
    private static final String EXC_FORMAT_LENGTH_ERR = "Data length of %s is invalid.";
    private static final String EXC_FORMAT_MAX_ERR = "Data %s is too big.";
    private static final String EXC_FORMAT_MIN_ERR = "Data %s is too small.";
    private static final String EXC_FORMAT_NULL = "%s is null.";

    public static JSONObject checkObject(JSONObject jSONObject, String str, boolean z) throws JSONException {
        if (!jSONObject.has(str) || jSONObject.isNull(str)) {
            if (z) {
                throw new JSONException(String.format(EXC_FORMAT_NULL, str));
            }
            return null;
        }
        JSONObject jSONObject2 = jSONObject.getJSONObject(str);
        if (jSONObject2 != null) {
            return jSONObject2;
        }
        if (z) {
            throw new JSONException(String.format(EXC_FORMAT_NULL, str));
        }
        return null;
    }

    public static JSONArray checkArray(JSONObject jSONObject, String str, boolean z) throws JSONException {
        if (!jSONObject.has(str) || jSONObject.isNull(str)) {
            if (z) {
                throw new JSONException(String.format(EXC_FORMAT_NULL, str));
            }
            return null;
        }
        JSONArray jSONArray = jSONObject.getJSONArray(str);
        if (jSONArray != null) {
            return jSONArray;
        }
        if (z) {
            throw new JSONException(String.format(EXC_FORMAT_NULL, str));
        }
        return null;
    }

    public static String checkString(JSONObject jSONObject, String str, boolean z, int i) throws JSONException {
        if (!jSONObject.has(str) || jSONObject.isNull(str)) {
            if (z) {
                throw new JSONException(String.format(EXC_FORMAT_NULL, str));
            }
            return null;
        }
        String string = jSONObject.getString(str);
        if (string == null) {
            if (z) {
                throw new JSONException(String.format(EXC_FORMAT_NULL, str));
            }
            return null;
        }
        if (i <= 0 || string.length() == i) {
            return string;
        }
        throw new JSONException(String.format(EXC_FORMAT_LENGTH_ERR, str));
    }

    public static int checkInt(JSONObject jSONObject, String str, boolean z, int i, boolean z2, int i2, boolean z3, int i3) throws JSONException {
        if (!jSONObject.has(str) || jSONObject.isNull(str)) {
            if (z) {
                throw new JSONException(String.format(EXC_FORMAT_NULL, str));
            }
            return i;
        }
        int i4 = jSONObject.getInt(str);
        if (z2 && i4 < i2) {
            throw new JSONException(String.format(EXC_FORMAT_MIN_ERR, str));
        }
        if (!z3 || i4 <= i3) {
            return i4;
        }
        throw new JSONException(String.format(EXC_FORMAT_MAX_ERR, str));
    }

    public static JSONObject checkObject(JSONArray jSONArray, int i, boolean z) throws JSONException {
        if (jSONArray.length() <= i || jSONArray.isNull(i)) {
            if (z) {
                throw new JSONException(String.format(EXC_FORMAT_NULL, Integer.valueOf(i)));
            }
            return null;
        }
        JSONObject jSONObject = jSONArray.getJSONObject(i);
        if (jSONObject != null) {
            return jSONObject;
        }
        if (z) {
            throw new JSONException(String.format(EXC_FORMAT_NULL, Integer.valueOf(i)));
        }
        return null;
    }

    public static String checkString(JSONArray jSONArray, int i, boolean z, int i2) throws JSONException {
        if (jSONArray.length() <= i || jSONArray.isNull(i)) {
            if (z) {
                throw new JSONException(String.format(EXC_FORMAT_NULL, Integer.valueOf(i)));
            }
            return null;
        }
        String string = jSONArray.getString(i);
        if (string == null) {
            if (z) {
                throw new JSONException(String.format(EXC_FORMAT_NULL, Integer.valueOf(i)));
            }
            return null;
        }
        if (i2 <= 0 || string.length() == i2) {
            return string;
        }
        throw new JSONException(String.format(EXC_FORMAT_LENGTH_ERR, Integer.valueOf(i)));
    }

    public static boolean checkBoolean(JSONObject jSONObject, String str, boolean z) throws JSONException {
        if (jSONObject.has(str) && !jSONObject.isNull(str)) {
            return jSONObject.getBoolean(str);
        }
        if (z) {
            throw new JSONException(String.format(EXC_FORMAT_NULL, str));
        }
        return false;
    }

    public static String checkString(JSONObject jSONObject, String str, boolean z, int i, int i2) throws JSONException {
        if (!jSONObject.has(str) || jSONObject.isNull(str)) {
            if (z) {
                throw new JSONException(String.format(EXC_FORMAT_NULL, str));
            }
            return null;
        }
        String string = jSONObject.getString(str);
        if (string == null) {
            if (z) {
                throw new JSONException(String.format(EXC_FORMAT_NULL, str));
            }
            return null;
        }
        if (string.length() < i) {
            throw new JSONException(String.format(EXC_FORMAT_LENGTH_ERR, str));
        }
        if (string.length() <= i2) {
            return string;
        }
        throw new JSONException(String.format(EXC_FORMAT_LENGTH_ERR, str));
    }
}
