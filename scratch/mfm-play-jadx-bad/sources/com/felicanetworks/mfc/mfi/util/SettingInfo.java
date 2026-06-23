package com.felicanetworks.mfc.mfi.util;

import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class SettingInfo {
    private static String sAimServerApplicationIdForCheckUpdate = "0210";
    private static String sAimServerCheckUpdateUrl = "https://ms.fnopf.jp/ws/ex_version";
    private static String sFwsServerHost = "mfi-device02.fnopf.jp";
    private static String sProfileId = "";
    private static Map<String, String> sJwsSignatureKeyForFwsServerList = FlavorConst.SERVER_PUBLIC_KEYS;
    private static String sJwsSignatureKeyForControlInfo = FlavorConst.DEFINE_3;

    private SettingInfo() {
    }

    public static void setProfileValue(ProfileJson profileJson) {
        LogMgr.log(5, "000");
        sProfileId = profileJson.getProfileId();
        sFwsServerHost = profileJson.getFwsServerHost();
        sAimServerCheckUpdateUrl = profileJson.getAimServerCheckUpdateUrl();
        sAimServerApplicationIdForCheckUpdate = profileJson.getAimServerApplicationIdForCheckUpdate();
        sJwsSignatureKeyForFwsServerList = profileJson.getJwsSignatureKeyForFwsServerList();
        sJwsSignatureKeyForControlInfo = profileJson.getJwsSignatureKeyForControlInfo();
        LogMgr.log(5, "999");
    }

    public static void initializeValue() {
        LogMgr.log(5, "000");
        sProfileId = "";
        sFwsServerHost = FlavorConst.FWS_HOST;
        sAimServerCheckUpdateUrl = FlavorConst.CHECK_UPDATE_URL;
        sAimServerApplicationIdForCheckUpdate = FlavorConst.APPLICATION_ID_FOR_CHECK_UPDATE;
        sJwsSignatureKeyForFwsServerList = FlavorConst.SERVER_PUBLIC_KEYS;
        sJwsSignatureKeyForControlInfo = FlavorConst.DEFINE_3;
        LogMgr.log(5, "999");
    }

    public static String getProfileId() {
        LogMgr.log(4, "000");
        LogMgr.log(4, "999 profileId = " + sProfileId);
        return sProfileId;
    }

    public static String getFwsServerHost() {
        LogMgr.log(4, "000");
        LogMgr.log(4, "999 fwsHost = " + sFwsServerHost);
        return sFwsServerHost;
    }

    public static String getAimServerCheckUpdateUrl() {
        LogMgr.log(4, "000");
        LogMgr.log(4, "999 aimServerCheckUpdateUrl = " + sAimServerCheckUpdateUrl);
        return sAimServerCheckUpdateUrl;
    }

    public static String getAimServerApplicationIdForCheckUpdate() {
        LogMgr.log(4, "000");
        LogMgr.log(4, "999 aimServerApplicationIdForCheckUpdate = " + sAimServerApplicationIdForCheckUpdate);
        return sAimServerApplicationIdForCheckUpdate;
    }

    public static Map<String, String> getJwsSignatureKeyForFwsServerList() {
        LogMgr.log(4, "000");
        LogMgr.log(4, "999 jwsSignatureKeyForFwsServerList = " + sJwsSignatureKeyForFwsServerList);
        return sJwsSignatureKeyForFwsServerList;
    }

    public static String getJwsSignatureKeyForControlInfo() {
        LogMgr.log(4, "000");
        LogMgr.log(4, "999 jwsSignatureKeyForControlInfo = " + sJwsSignatureKeyForControlInfo);
        return sJwsSignatureKeyForControlInfo;
    }
}
