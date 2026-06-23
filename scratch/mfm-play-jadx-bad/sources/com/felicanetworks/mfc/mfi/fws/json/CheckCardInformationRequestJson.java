package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.mfi.SeInfo;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class CheckCardInformationRequestJson extends RequestJson {
    private static final String NAME_LANGUAGE_CODE = "languageCode";
    private static final String NAME_LINKAGE_DATA = "linkageData";

    public void setSeInfo(SeInfo seInfo, String seType) throws JSONException {
        SeInfoJson seInfoJson = new SeInfoJson();
        seInfoJson.setSeInfo(seInfo, seType);
        putPayloadMember(seInfoJson.getName(), seInfoJson);
    }

    public void setLinkageData(String linkageData) throws JSONException {
        putPayloadMember("linkageData", linkageData);
    }

    public void setLanguageCode(String languageCode) throws JSONException {
        putPayloadMember(NAME_LANGUAGE_CODE, languageCode);
    }

    public void setDeiveInfo(String name, String platformName, String platformVer, String type) throws JSONException {
        DeviceInfoJson deviceInfoJson = new DeviceInfoJson();
        deviceInfoJson.setDeviceName(name);
        deviceInfoJson.setDevicePlatformName(platformName);
        deviceInfoJson.setDevicePlatformVersion(platformVer);
        deviceInfoJson.setDeviceType(type);
        putPayloadMember(deviceInfoJson.getName(), deviceInfoJson);
    }
}
