package com.felicanetworks.mfc.mfi.util;

import android.content.Context;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.mfi.fws.json.JsonUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class ProfileJson extends JSONObject {
    private static final String EXC_FORMAT_ARRAY_LENGTH_ERR = "Array length of %s is invalid.";
    private static final String EXC_FORMAT_REGEX_ERR = "Data type of %s is invalid.";
    private static final String NAME_AIM_SERVER_APPLICATION_ID_FOR_CHECK_UPDATE = "aimServerApplicationIdForCheckUpdate";
    private static final String NAME_AIM_SERVER_CHECK_UPDATE_URL = "aimServerCheckUpdateUrl";
    private static final String NAME_FWS_SERVER_HOST = "fwsServerHost";
    private static final String NAME_JWS_SIGNATURE_KEY_FOR_CONTROL_INFO = "jwsSignatureKeyForControlInfo";
    private static final String NAME_JWS_SIGNATURE_KEY_FOR_FWS_SERVER_LIST = "jwsSignatureKeyForFwsServerList";
    private static final String NAME_KEY_ID = "keyId";
    private static final String NAME_KEY_VALUE = "keyValue";
    private static final String NAME_PROFILE_ID = "profileId";
    private static final int PROFILE_ID_ELEMENT_NUMBER = 5;
    private static final String PROFILE_ID_HEADER = "MCPF";
    private static final int PROFILE_ID_INDEX_ENV = 1;
    private static final int PROFILE_ID_INDEX_HEADER = 0;
    private static final int PROFILE_ID_INDEX_RANDOM = 4;
    private static final int PROFILE_ID_INDEX_SEQ = 3;
    private static final int PROFILE_ID_INDEX_SP_APP_ID = 2;
    private static final String PROFILE_ID_SEPARATOR = "_";
    private static final String REGEX_PROFILE_ID_ENV_NAME = "^[\\w]{4}$";
    private static final String REGEX_PROFILE_ID_RANDOM = "^[0-9A-F]{32}$";
    private static final String REGEX_PROFILE_ID_SEQ = "^[0-9]{8}$";
    private static final String REGEX_PROFILE_ID_SP_APP_ID = "^SPA.{5}$";

    public ProfileJson(String profileJsonStr, Context context, String callerPackageName) throws JSONException, FelicaException {
        super(profileJsonStr);
        checkFormat();
    }

    private void checkFormat() throws JSONException {
        LogMgr.log(6, "000");
        try {
            JsonUtil.checkString((JSONObject) this, NAME_PROFILE_ID, true, 60);
            checkProfileIdFormat();
            JsonUtil.checkString((JSONObject) this, NAME_FWS_SERVER_HOST, true, 0);
            JsonUtil.checkString((JSONObject) this, NAME_AIM_SERVER_CHECK_UPDATE_URL, true, 0);
            JsonUtil.checkString((JSONObject) this, NAME_AIM_SERVER_APPLICATION_ID_FOR_CHECK_UPDATE, true, 4);
            checkJwsSignatureKeyForFwsServerListFormat();
            JsonUtil.checkString((JSONObject) this, NAME_JWS_SIGNATURE_KEY_FOR_CONTROL_INFO, true, 0);
            LogMgr.log(6, "999");
        } catch (JSONException e) {
            LogMgr.log(1, "800 JSONException occurred in checkFormat.");
            LogMgr.log(1, "801 msg = " + e.getMessage());
            throw e;
        }
    }

    private void checkProfileIdFormat() throws JSONException {
        LogMgr.log(6, "000");
        String[] strArrSplit = getString(NAME_PROFILE_ID).split("_");
        if (strArrSplit.length != 5) {
            LogMgr.log(1, "800 Profile ID (Element number) is invalid. Failed to connect.");
            throw new JSONException(String.format(EXC_FORMAT_REGEX_ERR, NAME_PROFILE_ID));
        }
        if (!PROFILE_ID_HEADER.equals(strArrSplit[0])) {
            LogMgr.log(1, "801 Profile ID (Header) is invalid. Failed to connect.");
            throw new JSONException(String.format(EXC_FORMAT_REGEX_ERR, NAME_PROFILE_ID));
        }
        if (!StringUtil.checkPattern(strArrSplit[1], REGEX_PROFILE_ID_ENV_NAME)) {
            LogMgr.log(1, "802 Profile ID (Environment) is invalid. Failed to connect.");
            throw new JSONException(String.format(EXC_FORMAT_REGEX_ERR, NAME_PROFILE_ID));
        }
        if (!StringUtil.checkPattern(strArrSplit[2], REGEX_PROFILE_ID_SP_APP_ID)) {
            LogMgr.log(1, "803 Profile ID (Sp App ID) is invalid. Failed to connect.");
            throw new JSONException(String.format(EXC_FORMAT_REGEX_ERR, NAME_PROFILE_ID));
        }
        if (!StringUtil.checkPattern(strArrSplit[3], REGEX_PROFILE_ID_SEQ)) {
            LogMgr.log(1, "804 Profile ID (Sequence) is invalid. Failed to connect.");
            throw new JSONException(String.format(EXC_FORMAT_REGEX_ERR, NAME_PROFILE_ID));
        }
        if (!StringUtil.checkPattern(strArrSplit[4], REGEX_PROFILE_ID_RANDOM)) {
            LogMgr.log(1, "805 Profile ID (Random) is invalid. Failed to connect.");
            throw new JSONException(String.format(EXC_FORMAT_REGEX_ERR, NAME_PROFILE_ID));
        }
        LogMgr.log(6, "999");
    }

    private void checkJwsSignatureKeyForFwsServerListFormat() throws JSONException {
        LogMgr.log(6, "000");
        try {
            JSONArray jSONArrayCheckArray = JsonUtil.checkArray(this, NAME_JWS_SIGNATURE_KEY_FOR_FWS_SERVER_LIST, true);
            if (jSONArrayCheckArray == null || jSONArrayCheckArray.length() == 0) {
                LogMgr.log(1, "801 jwsSignatureKeyForFwsServerList is invalid format.");
                throw new JSONException(String.format(EXC_FORMAT_ARRAY_LENGTH_ERR, NAME_JWS_SIGNATURE_KEY_FOR_FWS_SERVER_LIST));
            }
            for (int i = 0; i < jSONArrayCheckArray.length(); i++) {
                JSONObject jSONObject = jSONArrayCheckArray.getJSONObject(i);
                JsonUtil.checkString(jSONObject, NAME_KEY_ID, true, 4);
                JsonUtil.checkString(jSONObject, NAME_KEY_VALUE, true, 0);
            }
            LogMgr.log(6, "999");
        } catch (JSONException e) {
            LogMgr.log(1, "800 jwsSignatureKeyForFwsServerList is invalid format.");
            throw e;
        }
    }

    public String getProfileId() {
        String string;
        LogMgr.log(5, "000");
        try {
            string = getString(NAME_PROFILE_ID);
        } catch (JSONException unused) {
            string = null;
        }
        LogMgr.log(5, "999 profileId = " + string);
        return string;
    }

    public String getFwsServerHost() {
        String string;
        LogMgr.log(5, "000");
        try {
            string = getString(NAME_FWS_SERVER_HOST);
        } catch (JSONException unused) {
            string = null;
        }
        LogMgr.log(5, "999 fwsHost = " + string);
        return string;
    }

    public String getAimServerCheckUpdateUrl() {
        String string;
        LogMgr.log(5, "000");
        try {
            string = getString(NAME_AIM_SERVER_CHECK_UPDATE_URL);
        } catch (JSONException unused) {
            string = null;
        }
        LogMgr.log(5, "999 aimServerCheckUpdateUrl = " + string);
        return string;
    }

    public String getAimServerApplicationIdForCheckUpdate() {
        String string;
        LogMgr.log(5, "000");
        try {
            string = getString(NAME_AIM_SERVER_APPLICATION_ID_FOR_CHECK_UPDATE);
        } catch (JSONException unused) {
            string = null;
        }
        LogMgr.log(5, "999 aimServerApplicationIdForCheckUpdate = " + string);
        return string;
    }

    public Map<String, String> getJwsSignatureKeyForFwsServerList() {
        HashMap map;
        LogMgr.log(5, "000");
        HashMap map2 = null;
        try {
            JSONArray jSONArray = getJSONArray(NAME_JWS_SIGNATURE_KEY_FOR_FWS_SERVER_LIST);
            map = new HashMap();
            for (int i = 0; i < jSONArray.length(); i++) {
                try {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    map.put(jSONObject.getString(NAME_KEY_ID), jSONObject.getString(NAME_KEY_VALUE));
                } catch (JSONException unused) {
                    map2 = map;
                    map = map2;
                }
            }
        } catch (JSONException unused2) {
        }
        LogMgr.log(5, "999 jwsSignatureKeyForFwsServerList = " + map);
        return map;
    }

    public String getJwsSignatureKeyForControlInfo() {
        String string;
        LogMgr.log(5, "000");
        try {
            string = getString(NAME_JWS_SIGNATURE_KEY_FOR_CONTROL_INFO);
        } catch (JSONException unused) {
            string = null;
        }
        LogMgr.log(5, "999 jwsSignatureKeyForControlInfo = " + string);
        return string;
    }
}
