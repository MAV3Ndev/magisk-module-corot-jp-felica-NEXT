package com.felicanetworks.mfc.mfi.fws.json;

import android.util.SparseArray;
import com.felicanetworks.mfc.mfi.SeInfo;
import com.felicanetworks.mfc.mfi.fws.FwsConst;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.FelicaInstanceInfo;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class LinkageDataListRequestTokenPayloadJson extends JSONObject {
    private static final SparseArray<String> ACTION_TYPE_MAP;
    private static final String NAME_ACTION_TYPE = "actionType";
    private static final String NAME_ACTION_TYPE_CIDLIST = "actionTypeCidList";
    private static final String NAME_APPLET_INFO = "appletInfo";
    private static final String NAME_APPLET_INFO_AID = "aid";
    private static final String NAME_APPLET_INFO_IDM = "idm";
    private static final String NAME_APPLET_INFO_LIST = "appletInfoList";
    private static final String NAME_APPLET_INFO_SYSTEM_CODE = "systemCode";
    private static final String NAME_APPLET_TYPE = "type";
    private static final String NAME_APPLET_VERSION = "appletVersion";
    private static final String NAME_APPLICATION_LIFE_CYCLE_STATE = "applicationLifeCycleState";
    private static final String NAME_BINARY_VERSION = "binaryVersion";
    private static final String NAME_CARRIER_ID = "carrierId";
    private static final String NAME_CID_LIST = "cidList";
    private static final String NAME_CNONCE = "cnonce";
    private static final String NAME_CONTAINER_ID = "containerId";
    private static final String NAME_CONTAINER_INFO = "containerInfo";
    private static final String NAME_CONTAINER_ISSUE_INFO = "containerIssueInfo";
    private static final String NAME_DEVICE_ID = "deviceId";
    private static final String NAME_DEVICE_IDENTIFICATION_DATA = "deviceIdentificationData";
    private static final String NAME_DEVICE_INFO = "deviceInfo";
    private static final String NAME_DEVICE_MANUFACTURER = "deviceManufacturer";
    private static final String NAME_DEVICE_NAME = "deviceName";
    private static final String NAME_DEVICE_TYPE = "deviceType";
    private static final String NAME_IC_CODE = "icCode";
    private static final String NAME_IDM = "idm";
    private static final String NAME_KEY_VERSION = "keyVersion";
    private static final String NAME_PACKAGE_KEY_VERSION = "packageKeyVersion";
    private static final String NAME_PLATFORM_TYPE = "platformType";
    private static final String NAME_SD_INFO_LIST = "sdInfoList";
    private static final String NAME_SD_TYPE = "type";
    private static final String NAME_SEID = "seId";
    private static final String NAME_SEINFO = "seInfo";
    private static final String NAME_SEPID = "sepId";
    private static final String NAME_SEQUENCE_COUNTER = "sequenceCounter";
    private static final String NAME_SETYPE = "seType";
    private static final String NAME_SE_DETAIL_INFO = "seDetailInfo";
    private static final String NAME_TSM_CONFIGURATION = "tsmConfiguration";
    private static final int SYSTEM_CODE_LEN = 4;

    static {
        SparseArray<String> sparseArray = new SparseArray<>();
        ACTION_TYPE_MAP = sparseArray;
        sparseArray.put(1, "ReissueCards");
        ACTION_TYPE_MAP.put(2, FwsConst.ActionType.DELETE_CARDS);
        ACTION_TYPE_MAP.put(3, "InitializeChip");
        ACTION_TYPE_MAP.put(4, "DeleteSystem");
    }

    public JSONObject createSeInfoJson(SeInfo seInfo) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(NAME_SEPID, seInfo.getSepId());
        jSONObject.put(NAME_SETYPE, seInfo.getSeType());
        jSONObject.put(NAME_SEID, seInfo.getSeId());
        return jSONObject;
    }

    public JSONObject createContainerInfoJson(String str, String str2, String str3) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(NAME_IC_CODE, str);
        jSONObject.put(NAME_CONTAINER_ID, str2);
        jSONObject.put(NAME_CONTAINER_ISSUE_INFO, str3);
        return jSONObject;
    }

    public JSONObject createAppletInfoJson(int i, String str, String str2, String str3, String str4) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", i);
        jSONObject.put(NAME_BINARY_VERSION, str);
        jSONObject.put(NAME_APPLET_VERSION, str2);
        jSONObject.put(NAME_PACKAGE_KEY_VERSION, str3);
        jSONObject.put(NAME_APPLICATION_LIFE_CYCLE_STATE, str4);
        return jSONObject;
    }

    public JSONObject createSdInfoJson(int i, String str, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", i);
        jSONObject.put(NAME_KEY_VERSION, str);
        jSONObject.put(NAME_SEQUENCE_COUNTER, str2);
        return jSONObject;
    }

    public JSONObject createDeviceInfoJson(String str, String str2, String str3, String str4, String str5) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(NAME_DEVICE_TYPE, str);
        jSONObject.put(NAME_DEVICE_NAME, str2);
        jSONObject.put(NAME_PLATFORM_TYPE, str3);
        jSONObject.put(NAME_CARRIER_ID, str4);
        jSONObject.put(NAME_DEVICE_ID, str5);
        return jSONObject;
    }

    private JSONObject createAppletInfoJson(FelicaInstanceInfo felicaInstanceInfo) throws JSONException {
        if (felicaInstanceInfo == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(NAME_APPLET_INFO_AID, StringUtil.bytesToHexString(felicaInstanceInfo.getAid()));
        jSONObject.put("idm", StringUtil.bytesToHexString(felicaInstanceInfo.getIdm()));
        jSONObject.put(NAME_APPLET_INFO_SYSTEM_CODE, StringUtil.intToHexString(felicaInstanceInfo.getSystemCode(), 4));
        return jSONObject;
    }

    public void setCnonce(String str) throws JSONException {
        put(NAME_CNONCE, str);
    }

    public void setActionTypeCidList(int i, String[] strArr) throws JSONException {
        setActionTypeCidList(i, strArr, null);
    }

    public void setActionTypeCidList(int i, String[] strArr, FelicaInstanceInfo felicaInstanceInfo) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        String str = ACTION_TYPE_MAP.get(i);
        if (str != null) {
            jSONObject.put(NAME_ACTION_TYPE, str);
            if (strArr != null) {
                JSONArray jSONArray = new JSONArray();
                for (String str2 : strArr) {
                    jSONArray.put(str2);
                }
                jSONObject.put(NAME_CID_LIST, jSONArray);
            }
            JSONObject jSONObjectCreateAppletInfoJson = createAppletInfoJson(felicaInstanceInfo);
            if (jSONObjectCreateAppletInfoJson != null) {
                jSONObject.put(NAME_APPLET_INFO, jSONObjectCreateAppletInfoJson);
            }
            JSONArray jSONArray2 = new JSONArray();
            jSONArray2.put(jSONObject);
            put(NAME_ACTION_TYPE_CIDLIST, jSONArray2);
            return;
        }
        throw new JSONException("Unable to put actionType.");
    }

    public void setSeInfo(SeInfo seInfo) throws JSONException {
        SeInfoJson seInfoJson = new SeInfoJson();
        seInfoJson.setSeInfo(seInfo, Property.getSeType());
        put(NAME_SEINFO, seInfoJson);
    }

    public void setSeInfo(JSONObject jSONObject) throws JSONException {
        put(NAME_SEINFO, jSONObject);
    }

    public void setDeviceInfo(String str, String str2, String str3, String str4, String str5, String str6, String str7) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(NAME_DEVICE_TYPE, str);
        jSONObject.put(NAME_DEVICE_NAME, str2);
        jSONObject.put(NAME_DEVICE_MANUFACTURER, str3);
        jSONObject.put(NAME_DEVICE_IDENTIFICATION_DATA, str4);
        jSONObject.put(NAME_PLATFORM_TYPE, str5);
        jSONObject.put(NAME_CARRIER_ID, str6);
        jSONObject.put(NAME_DEVICE_ID, str7);
        put(NAME_DEVICE_INFO, jSONObject);
    }

    public void setDeviceInfo(JSONObject jSONObject) throws JSONException {
        put(NAME_DEVICE_INFO, jSONObject);
    }

    public void setSeDetailInfo(String str, String str2, JSONObject jSONObject, JSONArray jSONArray, JSONArray jSONArray2) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        if (str != null) {
            jSONObject2.put(NAME_TSM_CONFIGURATION, str);
        }
        if (str2 != null) {
            jSONObject2.put("idm", str2);
        }
        if (jSONObject != null) {
            jSONObject2.put(NAME_CONTAINER_INFO, jSONObject);
        }
        if (jSONArray != null) {
            jSONObject2.put(NAME_APPLET_INFO_LIST, jSONArray);
        }
        if (jSONArray2 != null) {
            jSONObject2.put(NAME_SD_INFO_LIST, jSONArray2);
        }
        put(NAME_SE_DETAIL_INFO, jSONObject2);
    }
}
