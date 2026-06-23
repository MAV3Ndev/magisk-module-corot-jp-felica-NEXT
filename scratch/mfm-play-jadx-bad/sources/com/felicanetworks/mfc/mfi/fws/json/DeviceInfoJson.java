package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class DeviceInfoJson extends JSONObject implements IJsonMember {
    private static final String NAME = "deviceInfo";
    private static final String NAME_CARRIER_ID = "carrierId";
    private static final String NAME_DEVICE_ID = "deviceId";
    private static final String NAME_DEVICE_IDENTIFICATION_DATA = "deviceIdentificationData";
    private static final String NAME_DEVICE_MANUFACTURER = "deviceManufacturer";
    private static final String NAME_DEVICE_NAME = "deviceName";
    private static final String NAME_DEVICE_PLATDOEM_NAME = "devicePlatformName";
    private static final String NAME_DEVICE_PLATDOEM_VERSION = "devicePlatformVersion";
    private static final String NAME_DEVICE_TYPE = "deviceType";
    private static final String NAME_PLATFORM_TYPE = "platformType";

    @Override // com.felicanetworks.mfc.mfi.fws.json.IJsonMember
    public String getName() {
        return NAME;
    }

    public void setDeviceName(String name) throws JSONException {
        put(NAME_DEVICE_NAME, name);
    }

    public void setDevicePlatformName(String platformName) throws JSONException {
        put(NAME_DEVICE_PLATDOEM_NAME, platformName);
    }

    public void setDevicePlatformVersion(String platformVersion) throws JSONException {
        put(NAME_DEVICE_PLATDOEM_VERSION, platformVersion);
    }

    public void setDeviceType(String type) throws JSONException {
        put(NAME_DEVICE_TYPE, type);
    }

    public void setDeviceManufacturer(String deviceManufacturer) throws JSONException {
        put(NAME_DEVICE_MANUFACTURER, deviceManufacturer);
    }

    public void setDeviceIdentificationData(String deviceIdentificationData) throws JSONException {
        put(NAME_DEVICE_IDENTIFICATION_DATA, deviceIdentificationData);
    }

    public void setPlatformType(String platformType) throws JSONException {
        put(NAME_PLATFORM_TYPE, platformType);
    }

    public void setCarrierId(String carrierId) throws JSONException {
        put(NAME_CARRIER_ID, carrierId);
    }

    public void setDeviceId(String deviceId) throws JSONException {
        put(NAME_DEVICE_ID, deviceId);
    }
}
