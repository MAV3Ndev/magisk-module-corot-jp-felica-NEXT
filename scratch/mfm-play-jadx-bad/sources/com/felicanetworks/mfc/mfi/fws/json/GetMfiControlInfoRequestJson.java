package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class GetMfiControlInfoRequestJson extends RequestJson {
    private static final String NAME_CONTENT_ID = "contentId";
    private static final String NAME_DEVICE_NAME = "deviceName";
    private static final String NAME_DEVICE_PLATFORM_VERSION = "devicePlatformVersion";
    private static final String NAME_MFIC_APP_VER = "mficAppVer";
    private static final String NAME_SEP_ID = "sepId";
    private static final String NAME_SE_TYPE = "seType";

    public void setContentId(String contentId) throws JSONException {
        put(NAME_CONTENT_ID, contentId);
    }

    public void setMficAppVer(String mficAppVer) throws JSONException {
        put(NAME_MFIC_APP_VER, mficAppVer);
    }

    public void setDevicePlatformVersion(String devicePlatformVersion) throws JSONException {
        put(NAME_DEVICE_PLATFORM_VERSION, devicePlatformVersion);
    }

    public void setSeType(String seType) throws JSONException {
        put(NAME_SE_TYPE, seType);
    }

    public void setSepId(String sepId) throws JSONException {
        put(NAME_SEP_ID, sepId);
    }

    public void setDeviceName(String deviceName) throws JSONException {
        put(NAME_DEVICE_NAME, deviceName);
    }
}
