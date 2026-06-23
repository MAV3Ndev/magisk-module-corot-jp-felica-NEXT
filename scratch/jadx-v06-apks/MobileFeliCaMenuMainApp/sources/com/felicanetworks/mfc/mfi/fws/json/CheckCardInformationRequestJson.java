package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.mfi.SeInfo;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class CheckCardInformationRequestJson extends RequestJson {
    private static final String NAME_LANGUAGE_CODE = "languageCode";
    private static final String NAME_LINKAGE_DATA = "linkageData";

    public void setSeInfo(SeInfo seInfo, String str) throws JSONException {
        SeInfoJson seInfoJson = new SeInfoJson();
        seInfoJson.setSeInfo(seInfo, str);
        putPayloadMember(seInfoJson.getName(), seInfoJson);
    }

    public void setLinkageData(String str) throws JSONException {
        putPayloadMember(NAME_LINKAGE_DATA, str);
    }

    public void setLanguageCode(String str) throws JSONException {
        putPayloadMember(NAME_LANGUAGE_CODE, str);
    }

    public void setDeiveInfo(String str, String str2, String str3, String str4) throws JSONException {
        DeviceInfoJson deviceInfoJson = new DeviceInfoJson();
        deviceInfoJson.setDeviceName(str);
        deviceInfoJson.setDevicePlatformName(str2);
        deviceInfoJson.setDevicePlatformVersion(str3);
        deviceInfoJson.setDeviceType(str4);
        putPayloadMember(deviceInfoJson.getName(), deviceInfoJson);
    }
}
