package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class JsonUtil {
    private static final String EXC_FORMAT_LENGTH_ERR = "Data length of %s is invalid.";
    private static final String EXC_FORMAT_MAX_ERR = "Data %s is too big.";
    private static final String EXC_FORMAT_MIN_ERR = "Data %s is too small.";
    private static final String EXC_FORMAT_NULL = "%s is null.";

    public static JSONObject checkObject(JSONObject json, String name, boolean checkExist) throws JSONException {
        if (!json.has(name) || json.isNull(name)) {
            if (checkExist) {
                throw new JSONException(String.format(EXC_FORMAT_NULL, name));
            }
            return null;
        }
        JSONObject jSONObject = json.getJSONObject(name);
        if (jSONObject != null) {
            return jSONObject;
        }
        if (checkExist) {
            throw new JSONException(String.format(EXC_FORMAT_NULL, name));
        }
        return null;
    }

    public static JSONArray checkArray(JSONObject json, String name, boolean checkExist) throws JSONException {
        if (!json.has(name) || json.isNull(name)) {
            if (checkExist) {
                throw new JSONException(String.format(EXC_FORMAT_NULL, name));
            }
            return null;
        }
        JSONArray jSONArray = json.getJSONArray(name);
        if (jSONArray != null) {
            return jSONArray;
        }
        if (checkExist) {
            throw new JSONException(String.format(EXC_FORMAT_NULL, name));
        }
        return null;
    }

    public static String checkString(JSONObject json, String name, boolean checkExist, int length) throws JSONException {
        if (!json.has(name) || json.isNull(name)) {
            if (checkExist) {
                throw new JSONException(String.format(EXC_FORMAT_NULL, name));
            }
            return null;
        }
        String string = json.getString(name);
        if (string == null) {
            if (checkExist) {
                throw new JSONException(String.format(EXC_FORMAT_NULL, name));
            }
            return null;
        }
        if (length <= 0 || string.length() == length) {
            return string;
        }
        throw new JSONException(String.format(EXC_FORMAT_LENGTH_ERR, name));
    }

    public static int checkInt(JSONObject json, String name, boolean checkExist, int defaultValue, boolean checkMin, int minValue, boolean checkMax, int maxValue) throws JSONException {
        if (!json.has(name) || json.isNull(name)) {
            if (checkExist) {
                throw new JSONException(String.format(EXC_FORMAT_NULL, name));
            }
            return defaultValue;
        }
        int i = json.getInt(name);
        if (checkMin && i < minValue) {
            throw new JSONException(String.format(EXC_FORMAT_MIN_ERR, name));
        }
        if (!checkMax || i <= maxValue) {
            return i;
        }
        throw new JSONException(String.format(EXC_FORMAT_MAX_ERR, name));
    }

    public static JSONObject checkObject(JSONArray json, int index, boolean checkExist) throws JSONException {
        if (json.length() <= index || json.isNull(index)) {
            if (checkExist) {
                throw new JSONException(String.format(EXC_FORMAT_NULL, Integer.valueOf(index)));
            }
            return null;
        }
        JSONObject jSONObject = json.getJSONObject(index);
        if (jSONObject != null) {
            return jSONObject;
        }
        if (checkExist) {
            throw new JSONException(String.format(EXC_FORMAT_NULL, Integer.valueOf(index)));
        }
        return null;
    }

    public static String checkString(JSONArray json, int index, boolean checkExist, int length) throws JSONException {
        if (json.length() <= index || json.isNull(index)) {
            if (checkExist) {
                throw new JSONException(String.format(EXC_FORMAT_NULL, Integer.valueOf(index)));
            }
            return null;
        }
        String string = json.getString(index);
        if (string == null) {
            if (checkExist) {
                throw new JSONException(String.format(EXC_FORMAT_NULL, Integer.valueOf(index)));
            }
            return null;
        }
        if (length <= 0 || string.length() == length) {
            return string;
        }
        throw new JSONException(String.format(EXC_FORMAT_LENGTH_ERR, Integer.valueOf(index)));
    }

    public static boolean checkBoolean(JSONObject json, String name, boolean checkExist) throws JSONException {
        if (json.has(name) && !json.isNull(name)) {
            return json.getBoolean(name);
        }
        if (checkExist) {
            throw new JSONException(String.format(EXC_FORMAT_NULL, name));
        }
        return false;
    }

    public static String checkString(JSONObject json, String name, boolean checkExist, int minLength, int maxLength) throws JSONException {
        if (!json.has(name) || json.isNull(name)) {
            if (checkExist) {
                throw new JSONException(String.format(EXC_FORMAT_NULL, name));
            }
            return null;
        }
        String string = json.getString(name);
        if (string == null) {
            if (checkExist) {
                throw new JSONException(String.format(EXC_FORMAT_NULL, name));
            }
            return null;
        }
        if (string.length() < minLength) {
            throw new JSONException(String.format(EXC_FORMAT_LENGTH_ERR, name));
        }
        if (string.length() <= maxLength) {
            return string;
        }
        throw new JSONException(String.format(EXC_FORMAT_LENGTH_ERR, name));
    }
}
