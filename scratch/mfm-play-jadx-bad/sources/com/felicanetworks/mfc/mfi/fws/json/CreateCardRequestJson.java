package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.mfi.CardIdentifiableBlockData;
import com.felicanetworks.mfc.mfi.SeInfo;
import com.felicanetworks.mfc.mfi.SeInfoEx;
import java.util.List;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class CreateCardRequestJson extends GetScriptRequestJson {
    private static final String NAME_LANGUAGE_CODE = "languageCode";
    private static final String NAME_LINKAGE_DATA = "linkageData";
    private static final String NAME_OTP = "otp";
    private static final String NAME_SEPARATE_CONDITION = "separateCondition";
    private static final String NAME_SEQUENCE_COUNTER = "sequenceCounter";

    @Override // com.felicanetworks.mfc.mfi.fws.json.GetScriptRequestJson
    public void setLinkageData(String linkageData) throws JSONException {
        putPayloadMember("linkageData", linkageData);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.json.GetScriptRequestJson
    public void setReadSeResult(boolean availableArea, List<CardIdentifiableBlockData> list) throws JSONException {
        ReadSeResultJson readSeResultJson = new ReadSeResultJson();
        readSeResultJson.setAvailableArea(availableArea);
        if (availableArea) {
            readSeResultJson.setIdentifiableBlockDataList(list);
        }
        putPayloadMember(readSeResultJson.getName(), readSeResultJson);
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

    public void setSeInfo(SeInfo seInfo, SeInfoEx seInfoEx, String seType, String idm) throws JSONException {
        SeInfoJson seInfoJson = new SeInfoJson();
        seInfoJson.setSeInfo(seInfo, seInfoEx, seType, idm);
        putPayloadMember(seInfoJson.getName(), seInfoJson);
    }

    public void setAppletInstanceInfo(String appletInstanceAid, String binaryVersion, String appletVersion, String packageKeyVersion, Boolean hasCid) throws JSONException {
        AppletInstanceInfoJson appletInstanceInfoJson = new AppletInstanceInfoJson();
        appletInstanceInfoJson.setAppletInstanceInfo(appletInstanceAid, binaryVersion, appletVersion, packageKeyVersion);
        appletInstanceInfoJson.setHasCid(hasCid);
        putPayloadMember(appletInstanceInfoJson.getName(), appletInstanceInfoJson);
    }

    public void setSequenceCounter(String sequenceCounter) throws JSONException {
        putPayloadMember(NAME_SEQUENCE_COUNTER, sequenceCounter);
    }

    public void setOtp(String otp) throws JSONException {
        putPayloadMember(NAME_OTP, otp);
    }

    public void setSeparateCondition(boolean separateCondition) throws JSONException {
        putPayloadMember(NAME_SEPARATE_CONDITION, separateCondition);
    }

    public void setDivideParameter(String carrierId, String deviceId) throws JSONException {
        DivideParameterJson divideParameterJson = new DivideParameterJson();
        divideParameterJson.setDivideParameter(carrierId, deviceId);
        putPayloadMember(divideParameterJson.getName(), divideParameterJson);
    }
}
