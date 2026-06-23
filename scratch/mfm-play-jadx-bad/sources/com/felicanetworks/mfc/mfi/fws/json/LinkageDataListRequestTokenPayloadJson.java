package com.felicanetworks.mfc.mfi.fws.json;

import android.util.SparseArray;
import com.felicanetworks.mfc.mfi.SeInfo;
import com.felicanetworks.mfc.mfi.fws.FwsConst;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.FelicaInstanceInfo;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
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
    private static final String NAME_READ_SE_RESULT_LIST = "readSeResultList";
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
        sparseArray.put(2, FwsConst.ActionType.DELETE_CARDS);
        sparseArray.put(3, "InitializeChip");
        sparseArray.put(4, "DeleteSystem");
        sparseArray.put(5, "PermanentDeleteCards");
        sparseArray.put(6, "ResetChip");
    }

    public JSONObject createSeInfoJson(SeInfo seInfo) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(NAME_SEPID, seInfo.getSepId());
        jSONObject.put(NAME_SETYPE, seInfo.getSeType());
        jSONObject.put(NAME_SEID, seInfo.getSeId());
        return jSONObject;
    }

    public JSONObject createContainerInfoJson(String icCode, String containerId, String containerIssueInfo) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(NAME_IC_CODE, icCode);
        jSONObject.put(NAME_CONTAINER_ID, containerId);
        jSONObject.put(NAME_CONTAINER_ISSUE_INFO, containerIssueInfo);
        return jSONObject;
    }

    public JSONObject createAppletInfoJson(int seType, String binaryVersion, String appletVersion, String packageKeyVersion, String applicationLifeCycleState) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", seType);
        jSONObject.put(NAME_BINARY_VERSION, binaryVersion);
        jSONObject.put(NAME_APPLET_VERSION, appletVersion);
        jSONObject.put(NAME_PACKAGE_KEY_VERSION, packageKeyVersion);
        jSONObject.put(NAME_APPLICATION_LIFE_CYCLE_STATE, applicationLifeCycleState);
        return jSONObject;
    }

    public JSONObject createSdInfoJson(int sdType, String keyVersion, String sequenceCounter) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", sdType);
        jSONObject.put(NAME_KEY_VERSION, keyVersion);
        jSONObject.put(NAME_SEQUENCE_COUNTER, sequenceCounter);
        return jSONObject;
    }

    public JSONObject createDeviceInfoJson(String deviceType, String deviceName, String platformType, String carrierId, String deviceId) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(NAME_DEVICE_TYPE, deviceType);
        jSONObject.put(NAME_DEVICE_NAME, deviceName);
        jSONObject.put(NAME_PLATFORM_TYPE, platformType);
        jSONObject.put(NAME_CARRIER_ID, carrierId);
        jSONObject.put(NAME_DEVICE_ID, deviceId);
        return jSONObject;
    }

    private JSONObject createAppletInfoJson(FelicaInstanceInfo appletInfo) throws JSONException {
        if (appletInfo == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(NAME_APPLET_INFO_AID, StringUtil.bytesToHexString(appletInfo.getAid()));
        jSONObject.put("idm", StringUtil.bytesToHexString(appletInfo.getIdm()));
        jSONObject.put(NAME_APPLET_INFO_SYSTEM_CODE, StringUtil.intToHexString(appletInfo.getSystemCode(), 4));
        return jSONObject;
    }

    public void setCnonce(String value) throws JSONException {
        put(NAME_CNONCE, value);
    }

    public void setActionTypeCidList(int actionType, String[] cidList, List<ReadSeResultJson> readSeResultJsonList) throws JSONException {
        setActionTypeCidList(actionType, cidList, null, readSeResultJsonList);
    }

    public void setActionTypeCidList(int actionType, String[] cidList) throws JSONException {
        setActionTypeCidList(actionType, cidList, null, null);
    }

    public void setActionTypeCidList(int actionType, String[] cidList, FelicaInstanceInfo appletInfo) throws JSONException {
        setActionTypeCidList(actionType, cidList, appletInfo, null);
    }

    private void setActionTypeCidList(int actionType, String[] cidList, FelicaInstanceInfo appletInfo, List<ReadSeResultJson> readSeResultJsonList) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        String str = ACTION_TYPE_MAP.get(actionType);
        if (str != null) {
            jSONObject.put(NAME_ACTION_TYPE, str);
            if (cidList != null) {
                JSONArray jSONArray = new JSONArray();
                for (String str2 : cidList) {
                    jSONArray.put(str2);
                }
                jSONObject.put(NAME_CID_LIST, jSONArray);
            }
            JSONObject jSONObjectCreateAppletInfoJson = createAppletInfoJson(appletInfo);
            if (jSONObjectCreateAppletInfoJson != null) {
                jSONObject.put(NAME_APPLET_INFO, jSONObjectCreateAppletInfoJson);
            }
            if (actionType == 5 && readSeResultJsonList != null) {
                JSONArray jSONArray2 = new JSONArray();
                Iterator<ReadSeResultJson> it = readSeResultJsonList.iterator();
                while (it.hasNext()) {
                    jSONArray2.put(it.next());
                }
                jSONObject.put(NAME_READ_SE_RESULT_LIST, jSONArray2);
            }
            JSONArray jSONArray3 = new JSONArray();
            jSONArray3.put(jSONObject);
            put(NAME_ACTION_TYPE_CIDLIST, jSONArray3);
            return;
        }
        throw new JSONException("Unable to put actionType.");
    }

    public void setSeInfo(SeInfo seInfo) throws JSONException {
        SeInfoJson seInfoJson = new SeInfoJson();
        seInfoJson.setSeInfo(seInfo, Property.getSeType());
        put(NAME_SEINFO, seInfoJson);
    }

    public void setSeInfo(JSONObject seInfoJson) throws JSONException {
        put(NAME_SEINFO, seInfoJson);
    }

    public void setDeviceInfo(String deviceType, String deviceName, String deviceManufacturer, String deviceIdentificationData, String platformType, String carrierId, String deviceId) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(NAME_DEVICE_TYPE, deviceType);
        jSONObject.put(NAME_DEVICE_NAME, deviceName);
        jSONObject.put(NAME_DEVICE_MANUFACTURER, deviceManufacturer);
        jSONObject.put(NAME_DEVICE_IDENTIFICATION_DATA, deviceIdentificationData);
        jSONObject.put(NAME_PLATFORM_TYPE, platformType);
        jSONObject.put(NAME_CARRIER_ID, carrierId);
        jSONObject.put(NAME_DEVICE_ID, deviceId);
        put(NAME_DEVICE_INFO, jSONObject);
    }

    public void setDeviceInfo(JSONObject deviceInfoJson) throws JSONException {
        put(NAME_DEVICE_INFO, deviceInfoJson);
    }

    public void setSeDetailInfo(String tsmConfiguration, String idm, JSONObject containerInfo, JSONArray appletInfoListJson, JSONArray sdInfoListJson) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (tsmConfiguration != null) {
            jSONObject.put(NAME_TSM_CONFIGURATION, tsmConfiguration);
        }
        if (idm != null) {
            jSONObject.put("idm", idm);
        }
        if (containerInfo != null) {
            jSONObject.put(NAME_CONTAINER_INFO, containerInfo);
        }
        if (appletInfoListJson != null) {
            jSONObject.put(NAME_APPLET_INFO_LIST, appletInfoListJson);
        }
        if (sdInfoListJson != null) {
            jSONObject.put(NAME_SD_INFO_LIST, sdInfoListJson);
        }
        put(NAME_SE_DETAIL_INFO, jSONObject);
    }
}
