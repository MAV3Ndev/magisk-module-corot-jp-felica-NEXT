package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class DeviceInfoJson extends JSONObject implements IJsonMember {
    private static final String NAME = "deviceInfo";
    private static final String NAME_DEVICE_NAME = "deviceName";
    private static final String NAME_DEVICE_PLATDOEM_NAME = "devicePlatformName";
    private static final String NAME_DEVICE_PLATDOEM_VERSION = "devicePlatformVersion";
    private static final String NAME_DEVICE_TYPE = "deviceType";

    @Override // com.felicanetworks.mfc.mfi.fws.json.IJsonMember
    public String getName() {
        return NAME;
    }

    public void setDeviceName(String str) throws JSONException {
        put(NAME_DEVICE_NAME, str);
    }

    public void setDevicePlatformName(String str) throws JSONException {
        put(NAME_DEVICE_PLATDOEM_NAME, str);
    }

    public void setDevicePlatformVersion(String str) throws JSONException {
        put(NAME_DEVICE_PLATDOEM_VERSION, str);
    }

    public void setDeviceType(String str) throws JSONException {
        put(NAME_DEVICE_TYPE, str);
    }
}
