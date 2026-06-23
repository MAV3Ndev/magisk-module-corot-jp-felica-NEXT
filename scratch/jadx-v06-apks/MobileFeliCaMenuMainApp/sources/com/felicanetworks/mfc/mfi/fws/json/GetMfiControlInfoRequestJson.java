package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class GetMfiControlInfoRequestJson extends RequestJson {
    private static final String NAME_CONTENT_ID = "contentId";
    private static final String NAME_DEVICE_NAME = "deviceName";
    private static final String NAME_DEVICE_PLATFORM_VERSION = "devicePlatformVersion";
    private static final String NAME_MFIC_APP_VER = "mficAppVer";
    private static final String NAME_SEP_ID = "sepId";
    private static final String NAME_SE_TYPE = "seType";

    public void setContentId(String str) throws JSONException {
        put(NAME_CONTENT_ID, str);
    }

    public void setMficAppVer(String str) throws JSONException {
        put(NAME_MFIC_APP_VER, str);
    }

    public void setDevicePlatformVersion(String str) throws JSONException {
        put(NAME_DEVICE_PLATFORM_VERSION, str);
    }

    public void setSeType(String str) throws JSONException {
        put(NAME_SE_TYPE, str);
    }

    public void setSepId(String str) throws JSONException {
        put(NAME_SEP_ID, str);
    }

    public void setDeviceName(String str) throws JSONException {
        put(NAME_DEVICE_NAME, str);
    }
}
