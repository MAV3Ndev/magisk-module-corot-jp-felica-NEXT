package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class GetProcessStatusResponseJson extends ResponseJson {
    private static final int ACCESS_ALLOWED_SP_APP_ID_LENGTH = 8;
    private static final String APPLET_STATUS_INSTALLED = "Installed";
    private static final String APPLET_STATUS_INSTALLING_OR_DELETING = "InstallingOrDeleting";
    private static final String APPLET_STATUS_UPGRADING = "Upgrading";
    private static final String APPLET_STATUS_UPGRADING_WAITING_FOR_DELETION = "Upgrading-WaitingForDeletion";
    private static final String APPLET_STATUS_UPGRADING_WAITING_FOR_RECOVERY = "Upgrading-WaitingForRecovery";
    private static final String KEY_ACCESS_ALLOWED_SP_APP_ID_LIST = "accessAllowedSpAppIdList";
    private static final String KEY_APPLET_STATUS_LIST = "appletStatusList";
    private static final String KEY_CASD_CERTIFICATE = "casdCertificate";
    private static final String KEY_CRT = "crt";
    private static final String KEY_DR = "dr";
    private static final int KEY_DR_LENGTH = 32;
    private static final String KEY_NEW_SP_APPLET_VERSION = "newSpAppletVersion";
    private static final String KEY_OLD_SP_APPLET_VERSION = "oldSpAppletVersion";
    private static final String KEY_RECEIPT = "receipt";
    private static final int KEY_RECEIPT_LENGTH = 32;
    private static final String KEY_SD_AID = "sdAid";
    private static final int KEY_SD_AID_MAX_LENGTH = 32;
    private static final int KEY_SD_AID_MIN_LENGTH = 10;
    private static final String KEY_SP_APPLET_VERSION = "spAppletVersion";
    private static final String KEY_STATUS = "status";
    private static final String NAME_APPLET_STATUS = "appletStatus";
    private static final String NAME_PROCESS_RESULT_CODE = "processResultCode";
    private static final String NAME_PROCESS_RESULT_DETAIL_CODE = "processResultDetailCode";
    private static final String NAME_PROCESS_RESULT_MESSAGE = "processResultMessage";
    private static final String NAME_PROCESS_STATUS = "processStatus";
    private static final String NAME_SD_KEY_DERIVATION_DATA_LIST = "sdKeyDerivationDataList";
    private static final int NEW_SP_APPLET_VERSION_LENGTH = 4;
    private static final int OLD_SP_APPLET_VERSION_LENGTH = 4;
    private static final String PROCESS_RESULT_CODE_FAILURE = "4000";
    private static final String PROCESS_RESULT_CODE_START_FAILURE = "3000";
    private static final String PROCESS_RESULT_CODE_SUCCESS = "0";
    private static final String PROCESS_RESULT_CODE_UNKNOWN = "4001";
    private static final String PROCESS_RESULT_DETAIL_CODE_ACTIVATE_CONTACTLESS_FAILURE = "1001";
    private static final String PROCESS_RESULT_DETAIL_CODE_DELETED_APPLET = "F060";
    private static final String PROCESS_RESULT_DETAIL_CODE_DELETE_NOT_SUPPORTED_APPLET = "F040";
    private static final String PROCESS_RESULT_DETAIL_CODE_INSTALLED_APPLET = "F050";
    private static final String PROCESS_RESULT_DETAIL_CODE_INSTALL_NOT_SUPPORTED_APPLET = "F030";
    private static final String PROCESS_RESULT_DETAIL_CODE_INSUFFICIENT_FREE_SPACE_SE_MEMORY = "B020";
    private static final int PROCESS_RESULT_MESSAGE_MAX_LENGTH = 1024;
    private static final int PROCESS_RESULT_MESSAGE_MIN_LENGTH = 1;
    private static final String PROCESS_STATUS_FINISHED = "Finished";
    private static final String PROCESS_STATUS_NOT_STARTED = "NotStarted";
    private static final String PROCESS_STATUS_PROCESSING = "Processing";
    private static final int SP_APPLET_VERSION_LENGTH = 4;

    public GetProcessStatusResponseJson(String str) throws JSONException {
        super(str);
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
    }

    @Override // com.felicanetworks.semc.sws.json.ResponseJson
    protected void checkPayloadMembers() throws JSONException {
        LogMgr.log(6, "000");
        JSONObject jSONObjectCheckObject = JsonUtil.checkObject(this, "payload", true);
        if (jSONObjectCheckObject != null) {
            JsonUtil.checkString(jSONObjectCheckObject, NAME_PROCESS_STATUS, true, 0);
            if (isProcessStatusFinished()) {
                JsonUtil.checkString(jSONObjectCheckObject, NAME_PROCESS_RESULT_CODE, true, 0);
            }
            JsonUtil.checkString(jSONObjectCheckObject, NAME_PROCESS_RESULT_DETAIL_CODE, false, 0);
            if (jSONObjectCheckObject.has(NAME_PROCESS_RESULT_MESSAGE) && !jSONObjectCheckObject.isNull(NAME_PROCESS_RESULT_MESSAGE)) {
                JsonUtil.checkString(jSONObjectCheckObject, NAME_PROCESS_RESULT_MESSAGE, true, 1, 1024);
            }
            JSONArray jSONArrayCheckArray = JsonUtil.checkArray(jSONObjectCheckObject, NAME_SD_KEY_DERIVATION_DATA_LIST, false);
            if (jSONArrayCheckArray != null && jSONArrayCheckArray.length() > 0) {
                checkSdKeyDerivationDataListPayloadMembers();
            }
            checkAppletStatusPayloadMembers();
            LogMgr.log(6, "999");
            return;
        }
        LogMgr.log(1, "801 no payload.");
        throw new JSONException(ObfuscatedMsgUtil.executionPoint());
    }

    public String getProcessStatus() throws JSONException {
        LogMgr.log(6, "000");
        JSONObject jSONObjectCheckObject = JsonUtil.checkObject(this, "payload", true);
        if (jSONObjectCheckObject == null) {
            throw new JSONException(ObfuscatedMsgUtil.executionPoint());
        }
        String strCheckString = JsonUtil.checkString(jSONObjectCheckObject, NAME_PROCESS_STATUS, true, 0);
        LogMgr.log(6, "999 ret[" + strCheckString + "]");
        return strCheckString;
    }

    public String getProcessResultCode() throws JSONException {
        LogMgr.log(6, "000");
        JSONObject jSONObjectCheckObject = JsonUtil.checkObject(this, "payload", true);
        if (jSONObjectCheckObject == null) {
            throw new JSONException(ObfuscatedMsgUtil.executionPoint());
        }
        String strCheckString = JsonUtil.checkString(jSONObjectCheckObject, NAME_PROCESS_RESULT_CODE, false, 0);
        LogMgr.log(6, "999 ret[" + strCheckString + "]");
        return strCheckString;
    }

    public String getProcessResultDetailCode() throws JSONException {
        LogMgr.log(6, "000");
        JSONObject jSONObjectCheckObject = JsonUtil.checkObject(this, "payload", true);
        if (jSONObjectCheckObject == null) {
            throw new JSONException(ObfuscatedMsgUtil.executionPoint());
        }
        String strCheckString = JsonUtil.checkString(jSONObjectCheckObject, NAME_PROCESS_RESULT_DETAIL_CODE, false, 0);
        LogMgr.log(6, "999 ret[" + strCheckString + "]");
        return strCheckString;
    }

    public String getProcessResultMessage() throws JSONException {
        LogMgr.log(6, "000");
        JSONObject jSONObjectCheckObject = JsonUtil.checkObject(this, "payload", true);
        if (jSONObjectCheckObject == null) {
            throw new JSONException(ObfuscatedMsgUtil.executionPoint());
        }
        String strCheckString = JsonUtil.checkString(jSONObjectCheckObject, NAME_PROCESS_RESULT_MESSAGE, false, 1, 1024);
        LogMgr.log(6, "999 ret[" + strCheckString + "]");
        return strCheckString;
    }

    public JSONArray getSdKeyDerivationDataList() throws JSONException {
        LogMgr.log(6, "000");
        JSONObject jSONObjectCheckObject = JsonUtil.checkObject(this, "payload", true);
        if (jSONObjectCheckObject == null) {
            throw new JSONException(ObfuscatedMsgUtil.executionPoint());
        }
        JSONArray jSONArrayCheckArray = JsonUtil.checkArray(jSONObjectCheckObject, NAME_SD_KEY_DERIVATION_DATA_LIST, false);
        LogMgr.log(6, "999 ret[" + jSONArrayCheckArray + "]");
        return jSONArrayCheckArray;
    }

    public JSONObject getAppletStatus() throws JSONException {
        LogMgr.log(6, "000");
        JSONObject jSONObjectCheckObject = JsonUtil.checkObject(this, "payload", true);
        if (jSONObjectCheckObject == null) {
            throw new JSONException(ObfuscatedMsgUtil.executionPoint());
        }
        JSONObject jSONObjectCheckObject2 = JsonUtil.checkObject(jSONObjectCheckObject, NAME_APPLET_STATUS, false);
        LogMgr.log(6, "999 ret[" + jSONObjectCheckObject2 + "]");
        return jSONObjectCheckObject2;
    }

    public void checkSdKeyDerivationDataListPayloadMembers() throws JSONException {
        LogMgr.log(8, "000");
        JSONArray sdKeyDerivationDataList = getSdKeyDerivationDataList();
        if (sdKeyDerivationDataList != null) {
            for (int i = 0; i < sdKeyDerivationDataList.length(); i++) {
                JSONObject jSONObject = sdKeyDerivationDataList.getJSONObject(i);
                JsonUtil.checkString(jSONObject, KEY_SD_AID, true, 10, 32);
                JsonUtil.checkString(jSONObject, KEY_DR, true, 32);
                JsonUtil.checkString(jSONObject, KEY_CRT, true, 0);
                JsonUtil.checkString(jSONObject, KEY_RECEIPT, true, 32);
                JsonUtil.checkString(jSONObject, KEY_CASD_CERTIFICATE, true, 0);
            }
        }
        LogMgr.log(8, "999");
    }

    public void checkAppletStatusPayloadMembers() throws JSONException {
        LogMgr.log(8, "000");
        JSONObject appletStatus = getAppletStatus();
        if (appletStatus != null) {
            checkAppletStatusListPayloadMembers(appletStatus);
            checkAccessAllowedSpAppIdListPayloadMembers(appletStatus);
        }
        LogMgr.log(8, "999");
    }

    public void checkAppletStatusListPayloadMembers(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2;
        LogMgr.log(8, "000");
        JSONArray jSONArrayCheckArray = JsonUtil.checkArray(jSONObject, KEY_APPLET_STATUS_LIST, false);
        if (jSONArrayCheckArray != null) {
            for (int i = 0; i < jSONArrayCheckArray.length(); i++) {
                jSONObject2 = jSONArrayCheckArray.getJSONObject(i);
                String str = (String) Objects.requireNonNull(JsonUtil.checkString(jSONObject2, "status", true, 0));
                str.hashCode();
                switch (str) {
                    case "InstallingOrDeleting":
                    case "Installed":
                        JsonUtil.checkString(jSONObject2, "spAppletVersion", true, 4);
                        break;
                    case "Upgrading-WaitingForDeletion":
                    case "Upgrading":
                    case "Upgrading-WaitingForRecovery":
                        JsonUtil.checkString(jSONObject2, "oldSpAppletVersion", true, 4);
                        JsonUtil.checkString(jSONObject2, "newSpAppletVersion", true, 4);
                        break;
                }
            }
        }
        LogMgr.log(8, "999");
    }

    public void checkAccessAllowedSpAppIdListPayloadMembers(JSONObject jSONObject) throws JSONException {
        LogMgr.log(8, "000");
        JSONArray jSONArrayCheckArray = JsonUtil.checkArray(jSONObject, "accessAllowedSpAppIdList", false);
        if (jSONArrayCheckArray != null) {
            for (int i = 0; i < jSONArrayCheckArray.length(); i++) {
                if (jSONArrayCheckArray.getString(i).length() != 8) {
                    throw new JSONException(ObfuscatedMsgUtil.executionPoint());
                }
            }
        }
        LogMgr.log(8, "999");
    }

    public boolean isProcessStatusNotStarted() throws JSONException {
        LogMgr.log(6, "000");
        boolean zEquals = "NotStarted".equals(getProcessStatus());
        LogMgr.log(6, "999 ret[" + zEquals + "]");
        return zEquals;
    }

    public boolean isProcessStatusProcessing() throws JSONException {
        LogMgr.log(6, "000");
        boolean zEquals = "Processing".equals(getProcessStatus());
        LogMgr.log(6, "999 ret[" + zEquals + "]");
        return zEquals;
    }

    public boolean isProcessStatusFinished() throws JSONException {
        LogMgr.log(6, "000");
        boolean zEquals = "Finished".equals(getProcessStatus());
        LogMgr.log(6, "999 ret[" + zEquals + "]");
        return zEquals;
    }

    public boolean isProcessResultCodeSuccess() throws JSONException {
        LogMgr.log(6, "000");
        boolean zEquals = "0".equals(getProcessResultCode());
        LogMgr.log(6, "999 ret[" + zEquals + "]");
        return zEquals;
    }

    public boolean isProcessResultCodeStartFailure() throws JSONException {
        LogMgr.log(6, "000");
        boolean zEquals = "3000".equals(getProcessResultCode());
        LogMgr.log(6, "999 ret[" + zEquals + "]");
        return zEquals;
    }

    public boolean isProcessResultCodeFailure() throws JSONException {
        LogMgr.log(6, "000");
        boolean zEquals = "4000".equals(getProcessResultCode());
        LogMgr.log(6, "999 ret[" + zEquals + "]");
        return zEquals;
    }

    public boolean isProcessResultCodeUnknown() throws JSONException {
        LogMgr.log(6, "000");
        boolean zEquals = "4001".equals(getProcessResultCode());
        LogMgr.log(6, "999 ret[" + zEquals + "]");
        return zEquals;
    }

    public boolean isProcessResultDetailCodeActiveContactlessFailure() throws JSONException {
        LogMgr.log(6, "000");
        boolean zEquals = "1001".equals(getProcessResultDetailCode());
        LogMgr.log(6, "999 ret[" + zEquals + "]");
        return zEquals;
    }

    public boolean isProcessResultDetailCodeInsufficientFreeSpaceSeMemory() throws JSONException {
        LogMgr.log(6, "000");
        boolean zEquals = PROCESS_RESULT_DETAIL_CODE_INSUFFICIENT_FREE_SPACE_SE_MEMORY.equals(getProcessResultDetailCode());
        LogMgr.log(6, "999 ret[" + zEquals + "]");
        return zEquals;
    }

    public boolean isProcessResultDetailCodeInstallNotSupportedApplet() throws JSONException {
        LogMgr.log(6, "000");
        boolean zEquals = PROCESS_RESULT_DETAIL_CODE_INSTALL_NOT_SUPPORTED_APPLET.equals(getProcessResultDetailCode());
        LogMgr.log(6, "999 ret[" + zEquals + "]");
        return zEquals;
    }

    public boolean isProcessResultDetailCodeDeleteNotSupportedApplet() throws JSONException {
        LogMgr.log(6, "000");
        boolean zEquals = PROCESS_RESULT_DETAIL_CODE_DELETE_NOT_SUPPORTED_APPLET.equals(getProcessResultDetailCode());
        LogMgr.log(6, "999 ret[" + zEquals + "]");
        return zEquals;
    }

    public boolean isProcessResultDetailCodeInstalledApplet() throws JSONException {
        LogMgr.log(6, "000");
        boolean zEquals = PROCESS_RESULT_DETAIL_CODE_INSTALLED_APPLET.equals(getProcessResultDetailCode());
        LogMgr.log(6, "999 ret[" + zEquals + "]");
        return zEquals;
    }

    public boolean isProcessResultDetailCodeDeletedApplet() throws JSONException {
        LogMgr.log(6, "000");
        boolean zEquals = PROCESS_RESULT_DETAIL_CODE_DELETED_APPLET.equals(getProcessResultDetailCode());
        LogMgr.log(6, "999 ret[" + zEquals + "]");
        return zEquals;
    }

    public boolean isAppletStatusUpgrading() throws JSONException {
        LogMgr.log(6, "000");
        JSONArray jSONArrayOptJSONArray = getAppletStatus().optJSONArray(KEY_APPLET_STATUS_LIST);
        boolean z = false;
        if (jSONArrayOptJSONArray != null) {
            boolean zEquals = false;
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                zEquals = "Upgrading".equals(jSONArrayOptJSONArray.getJSONObject(i).getString("status"));
            }
            z = zEquals;
        }
        LogMgr.log(6, "999 ret[" + z + "]");
        return z;
    }

    public boolean isAppletStatusUpgradingWaitingForDeletion() throws JSONException {
        LogMgr.log(6, "000");
        JSONArray jSONArrayOptJSONArray = getAppletStatus().optJSONArray(KEY_APPLET_STATUS_LIST);
        boolean z = false;
        if (jSONArrayOptJSONArray != null) {
            boolean zEquals = false;
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                zEquals = "Upgrading-WaitingForDeletion".equals(jSONArrayOptJSONArray.getJSONObject(i).getString("status"));
            }
            z = zEquals;
        }
        LogMgr.log(6, "999 ret[" + z + "]");
        return z;
    }

    public boolean isAppletStatusUpgradingWaitingForRecovery() throws JSONException {
        LogMgr.log(6, "000");
        JSONArray jSONArrayOptJSONArray = getAppletStatus().optJSONArray(KEY_APPLET_STATUS_LIST);
        boolean z = false;
        if (jSONArrayOptJSONArray != null) {
            boolean zEquals = false;
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                zEquals = "Upgrading-WaitingForRecovery".equals(jSONArrayOptJSONArray.getJSONObject(i).getString("status"));
            }
            z = zEquals;
        }
        LogMgr.log(6, "999 ret[" + z + "]");
        return z;
    }

    public boolean isAppletStatusInstallingOrDeleting() throws JSONException {
        LogMgr.log(6, "000");
        JSONArray jSONArrayOptJSONArray = getAppletStatus().optJSONArray(KEY_APPLET_STATUS_LIST);
        boolean z = false;
        if (jSONArrayOptJSONArray != null) {
            boolean zEquals = false;
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                zEquals = "InstallingOrDeleting".equals(jSONArrayOptJSONArray.getJSONObject(i).getString("status"));
            }
            z = zEquals;
        }
        LogMgr.log(6, "999 ret[" + z + "]");
        return z;
    }

    public boolean isAppletStatusInstalled() throws JSONException {
        LogMgr.log(6, "000");
        JSONArray jSONArrayOptJSONArray = getAppletStatus().optJSONArray(KEY_APPLET_STATUS_LIST);
        boolean z = false;
        if (jSONArrayOptJSONArray != null) {
            boolean zEquals = false;
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                zEquals = "Installed".equals(jSONArrayOptJSONArray.getJSONObject(i).getString("status"));
            }
            z = zEquals;
        }
        LogMgr.log(6, "999 ret[" + z + "]");
        return z;
    }
}
