package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class ProfileDataJson extends JSONObject {
    private static final String NAME_ENABLE_LOG = "enableLog";
    private static final String NAME_ENABLE_PERFORMANCE_LOG = "enablePerformanceLog";
    private static final String NAME_JWS_SIGNATURE_KEY_FOR_CLIENT_CONFIG = "jwsSignatureKeyForClientConfig";
    private static final String NAME_JWS_SIGNATURE_KEY_FOR_LINKAGE_DATA = "jwsSignatureKeyForLinkageData";
    private static final String NAME_JWS_SIGNATURE_KEY_ID_FOR_CLIENT_CONFIG = "jwsSignatureKeyIdForClientConfig";
    private static final String NAME_JWS_SIGNATURE_KEY_ID_FOR_LINKAGE_DATA = "jwsSignatureKeyIdForLinkageData";
    private static final String NAME_LOG_LEVEL = "logLevel";
    private static final String NAME_PERMITTED_SIGNING_CERT_HASH = "permittedSigningCertHash";
    private static final String NAME_PERMITTED_SP_PACKAGE_NAME = "permittedSpPackageName";
    private static final String NAME_PROFILE_ID = "profileId";
    private static final String NAME_SERVER_CONNECTION_URL = "serverConnectionUrl";

    public ProfileDataJson(String str) throws JSONException {
        super(str);
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
    }

    public void checkProfileData() throws JSONException {
        LogMgr.log(6, "000");
        checkAndGetServerConnectionUrl();
        checkAndGetJwsSignatureKeyIdForLinkageData();
        checkAndGetJwsSignatureKeyForLinkageData();
        checkAndGetJwsSignatureKeyIdForClientConfig();
        checkAndGetJwsSignatureKeyForClientConfig();
        checkAndGetPermittedSpPackageName();
        checkAndGetPermittedSigningCertHash();
        checkAndGetLogLevel();
        LogMgr.log(6, "999");
    }

    public String checkAndGetProfileId() throws JSONException {
        LogMgr.log(6, "000");
        String strCheckString = JsonUtil.checkString(this, NAME_PROFILE_ID, true, 60);
        LogMgr.log(6, "999 ret=[" + strCheckString + "]");
        return strCheckString;
    }

    public String checkAndGetServerConnectionUrl() throws JSONException {
        LogMgr.log(6, "000");
        String strCheckString = JsonUtil.checkString(this, NAME_SERVER_CONNECTION_URL, true, 0);
        LogMgr.log(6, "999 ret=[" + strCheckString + "]");
        return strCheckString;
    }

    public String checkAndGetJwsSignatureKeyIdForLinkageData() throws JSONException {
        LogMgr.log(6, "000");
        String strCheckString = JsonUtil.checkString(this, NAME_JWS_SIGNATURE_KEY_ID_FOR_LINKAGE_DATA, true, 4);
        LogMgr.log(6, "999 ret=[" + strCheckString + "]");
        return strCheckString;
    }

    public String checkAndGetJwsSignatureKeyForLinkageData() throws JSONException {
        LogMgr.log(6, "000");
        String strCheckString = JsonUtil.checkString(this, NAME_JWS_SIGNATURE_KEY_FOR_LINKAGE_DATA, true, 0);
        LogMgr.log(6, "999 ret=[" + strCheckString + "]");
        return strCheckString;
    }

    public String checkAndGetJwsSignatureKeyIdForClientConfig() throws JSONException {
        LogMgr.log(6, "000");
        String strCheckString = JsonUtil.checkString(this, NAME_JWS_SIGNATURE_KEY_ID_FOR_CLIENT_CONFIG, true, 4);
        LogMgr.log(6, "999 ret=[" + strCheckString + "]");
        return strCheckString;
    }

    public String checkAndGetJwsSignatureKeyForClientConfig() throws JSONException {
        LogMgr.log(6, "000");
        String strCheckString = JsonUtil.checkString(this, NAME_JWS_SIGNATURE_KEY_FOR_CLIENT_CONFIG, true, 0);
        LogMgr.log(6, "999 ret=[" + strCheckString + "]");
        return strCheckString;
    }

    public String checkAndGetPermittedSpPackageName() throws JSONException {
        LogMgr.log(6, "000");
        String strCheckString = JsonUtil.checkString(this, NAME_PERMITTED_SP_PACKAGE_NAME, true, 1, 1024);
        LogMgr.log(6, "999 ret=[" + strCheckString + "]");
        return strCheckString;
    }

    public String checkAndGetPermittedSigningCertHash() throws JSONException {
        LogMgr.log(6, "000");
        String strCheckString = JsonUtil.checkString(this, NAME_PERMITTED_SIGNING_CERT_HASH, true, 64);
        LogMgr.log(6, "999 ret=[" + strCheckString + "]");
        return strCheckString;
    }

    public boolean checkAndGetEnableLog() throws JSONException {
        LogMgr.log(6, "000");
        boolean zCheckBoolean = JsonUtil.checkBoolean(this, NAME_ENABLE_LOG, true);
        LogMgr.log(6, "999 ret=[" + zCheckBoolean + "]");
        return zCheckBoolean;
    }

    public boolean checkAndGetEnablePerformanceLog() throws JSONException {
        LogMgr.log(6, "000");
        boolean zCheckBoolean = JsonUtil.checkBoolean(this, NAME_ENABLE_PERFORMANCE_LOG, true);
        LogMgr.log(6, "999 ret=[" + zCheckBoolean + "]");
        return zCheckBoolean;
    }

    public int checkAndGetLogLevel() throws JSONException {
        LogMgr.log(6, "000");
        int iCheckInt = JsonUtil.checkInt(this, NAME_LOG_LEVEL, false, 1, true, 0, true, 9);
        LogMgr.log(6, "999 ret=[" + iCheckInt + "]");
        return iCheckInt;
    }
}
