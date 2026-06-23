package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.mfi.CardIdentifiableBlockData;
import com.felicanetworks.mfc.mfi.SeInfo;
import com.felicanetworks.mfc.mfi.SeInfoEx;
import java.util.List;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class CreateCardRequestJson extends GetScriptRequestJson {
    private static final String NAME_LANGUAGE_CODE = "languageCode";
    private static final String NAME_LINKAGE_DATA = "linkageData";
    private static final String NAME_OTP = "otp";
    private static final String NAME_SEPARATE_CONDITION = "separateCondition";
    private static final String NAME_SEQUENCE_COUNTER = "sequenceCounter";

    @Override // com.felicanetworks.mfc.mfi.fws.json.GetScriptRequestJson
    public void setLinkageData(String str) throws JSONException {
        putPayloadMember(NAME_LINKAGE_DATA, str);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.json.GetScriptRequestJson
    public void setReadSeResult(boolean z, List<CardIdentifiableBlockData> list) throws JSONException {
        ReadSeResultJson readSeResultJson = new ReadSeResultJson();
        readSeResultJson.setAvailableArea(z);
        if (z) {
            readSeResultJson.setIdentifiableBlockDataList(list);
        }
        putPayloadMember(readSeResultJson.getName(), readSeResultJson);
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

    public void setSeInfo(SeInfo seInfo, SeInfoEx seInfoEx, String str, String str2) throws JSONException {
        SeInfoJson seInfoJson = new SeInfoJson();
        seInfoJson.setSeInfo(seInfo, seInfoEx, str, str2);
        putPayloadMember(seInfoJson.getName(), seInfoJson);
    }

    public void setAppletInstanceInfo(String str, String str2, String str3, String str4, Boolean bool) throws JSONException {
        AppletInstanceInfoJson appletInstanceInfoJson = new AppletInstanceInfoJson();
        appletInstanceInfoJson.setAppletInstanceInfo(str, str2, str3, str4);
        appletInstanceInfoJson.setHasCid(bool);
        putPayloadMember(appletInstanceInfoJson.getName(), appletInstanceInfoJson);
    }

    public void setSequenceCounter(String str) throws JSONException {
        putPayloadMember(NAME_SEQUENCE_COUNTER, str);
    }

    public void setOtp(String str) throws JSONException {
        putPayloadMember(NAME_OTP, str);
    }

    public void setSeparateCondition(boolean z) throws JSONException {
        putPayloadMember(NAME_SEPARATE_CONDITION, z);
    }

    public void setDivideParameter(String str, String str2) throws JSONException {
        DivideParameterJson divideParameterJson = new DivideParameterJson();
        divideParameterJson.setDivideParameter(str, str2);
        putPayloadMember(divideParameterJson.getName(), divideParameterJson);
    }
}
