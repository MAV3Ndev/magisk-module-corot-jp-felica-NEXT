package com.felicanetworks.semc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.semc.SemClient;
import com.felicanetworks.semc.util.LogMgrUtil;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class SemClientNotifyEventInfo implements Parcelable {
    private static final String ACCESS_ALLOWED_SP_APP_ID_LIST = "accessAllowedSpAppIdList";
    private static final String APPLET_STATUS_LIST = "appletStatusList";
    public static final Parcelable.Creator<SemClientNotifyEventInfo> CREATOR = new Parcelable.Creator<SemClientNotifyEventInfo>() { // from class: com.felicanetworks.semc.SemClientNotifyEventInfo.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemClientNotifyEventInfo createFromParcel(Parcel parcel) {
            LogMgrUtil.log(6, "000");
            LogMgrUtil.log(6, "999 : in = " + parcel);
            return new SemClientNotifyEventInfo(parcel);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemClientNotifyEventInfo[] newArray(int i) {
            LogMgrUtil.log(6, "000");
            LogMgrUtil.log(6, "999 : size = " + i);
            return new SemClientNotifyEventInfo[i];
        }
    };
    public static final String METHOD_NAME_CONNECT_LISTENER_ON_CONNECT = "OnConnectedListener#onConnected";
    public static final String METHOD_NAME_CONNECT_LISTENER_ON_ERROR = "OnConnectedListener#onError";
    public static final String METHOD_NAME_DELETE_APPLET_ON_ERROR = "OnDeleteAppletListener#onError";
    public static final String METHOD_NAME_DELETE_APPLET_ON_FINISHED = "OnDeleteAppletListener#onFinished";
    public static final String METHOD_NAME_GET_APPLET_STATUS_ON_ERROR = "OnGetAppletStatusListener#onError";
    public static final String METHOD_NAME_GET_APPLET_STATUS_ON_FINISHED = "OnGetAppletStatusListener#onFinished";
    public static final String METHOD_NAME_GET_CLIENT_CONFIGURATION_LISTENER_ON_FINISHED = "OnGetClientConfigurationListener#onFinished";
    public static final String METHOD_NAME_GET_PROCESS_STATUS_LISTENER_ON_ERROR = "OnGetProcessStatusListener#onError";
    public static final String METHOD_NAME_GET_PROCESS_STATUS_LISTENER_ON_FINISHED = "OnGetProcessStatusListener#onFinished";
    public static final String METHOD_NAME_GET_UNIQUE_VALUE_LISTENER_ON_ERROR = "OnGetUniqueValueListener#onError";
    public static final String METHOD_NAME_GET_UNIQUE_VALUE_LISTENER_ON_FINISHED = "OnGetUniqueValueListener#onFinished";
    public static final String METHOD_NAME_INSTALL_APPLET_ON_ERROR = "OnInstallAppletListener#onError";
    public static final String METHOD_NAME_INSTALL_APPLET_ON_FINISHED = "OnInstallAppletListener#onFinished";
    public static final String METHOD_NAME_NOTIFY_CLIENT_EVENT_LISTENER_ON_ERROR = "OnNotifyClientEventListener#onError";
    public static final String METHOD_NAME_NOTIFY_CLIENT_EVENT_LISTENER_ON_NOTIFIED = "OnNotifyClientEventListener#onNotified";
    public static final String METHOD_NAME_ON_ERROR = "onError";
    public static final String METHOD_NAME_ON_ERROR_AND_DO_WORK_MANAGER_RETRY = "onErrorAndDoWorkManagerRetry";
    public static final String METHOD_NAME_REGISTER_DEVICE_TOKEN_LISTENER_ON_FINISHED = "OnRegisterDeviceTokenListener#onFinished";
    public static final String METHOD_NAME_TSM_SEQUENCE_ON_ERROR = "OnTsmSequenceListener#onError";
    public static final String METHOD_NAME_TSM_SEQUENCE_ON_ERROR_AND_DO_WORK_MANAGER_RETRY = "OnTsmSequenceListener#onErrorAndDoWorkManagerRetry";
    public static final String METHOD_NAME_TSM_SEQUENCE_ON_FINISHED = "OnTsmSequenceListener#onFinished";
    public static final String METHOD_NAME_UPGRADE_APPLET_ON_ERROR = "OnUpgradeAppletListener#onError";
    public static final String METHOD_NAME_UPGRADE_APPLET_ON_FINISHED = "OnUpgradeAppletListener#onFinished";
    private static final String NAME_CALLBACK = "callback";
    private static final String NAME_ERROR_INFO = "errorInfo";
    private static final String NAME_ERROR_INFO_ADDITIONAL_INFORMATION = "additionalErrorInformation";
    private static final String NAME_ERROR_INFO_CODE = "errorCode";
    private static final String NAME_ERROR_INFO_MESSAGE = "message";
    private static final String NAME_ERROR_NEED_BIND_FOR_SEMC_APP = "isNeedBindForSEMCApp";
    private static final String NEW_SP_APPLET_VERSION = "newSpAppletVersion";
    private static final String OLD_SP_APPLET_VERSION = "oldSpAppletVersion";
    private static final String PROCESS_RESULT_CODE = "processResultCode";
    private static final String PROCESS_RESULT_DETAIL_CODE = "processResultDetailCode";
    private static final String PROCESS_RESULT_MESSAGE_STRING = "processResultMessageString";
    private static final String PROCESS_STATUS = "processStatus";
    private static final String REGISTERED_SP_APPLET_VERSION_LIST = "registeredSpAppletVersionList";
    private static final String SD_KEY_DERIVATION_DATA_CASD_CERTIFICATE = "casdCertificate";
    private static final String SD_KEY_DERIVATION_DATA_CRT = "crt";
    private static final String SD_KEY_DERIVATION_DATA_DR = "dr";
    private static final String SD_KEY_DERIVATION_DATA_LIST = "sdKeyDerivationDataList";
    private static final String SD_KEY_DERIVATION_DATA_RECEIPT = "receipt";
    private static final String SD_KEY_DERIVATION_DATA_SD_AID = "sdAid";
    private static final String SP_APPLET_VERSION = "spAppletVersion";
    private static final String SP_APPLET_VERSION_STATUS = "status";
    private String mCallbackInfo;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemClientNotifyEventInfo(String str) throws SemClientException {
        LogMgrUtil.log(5, "000 callback:" + str);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(NAME_CALLBACK, str);
            this.mCallbackInfo = jSONObject.toString();
            LogMgrUtil.log(5, "999");
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    public SemClientNotifyEventInfo(String str, String str2, String str3, String str4, String str5) throws SemClientException {
        LogMgrUtil.log(5, "000 callback:" + str);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(NAME_CALLBACK, str);
            if (str2 != null) {
                jSONObject.put(PROCESS_STATUS, str2);
            }
            if (str3 != null) {
                jSONObject.put(PROCESS_RESULT_CODE, str3);
            }
            if (str4 != null) {
                jSONObject.put(PROCESS_RESULT_DETAIL_CODE, str4);
            }
            if (str5 != null) {
                jSONObject.put(PROCESS_RESULT_MESSAGE_STRING, str5);
            }
            this.mCallbackInfo = jSONObject.toString();
            LogMgrUtil.log(5, "999");
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    public SemClientNotifyEventInfo(String str, String str2, String str3, String str4, String str5, JSONArray jSONArray) throws SemClientException {
        this(str, str2, str3, str4, str5);
        LogMgrUtil.log(5, "000 callback:" + str);
        try {
            JSONObject jSONObject = new JSONObject(this.mCallbackInfo);
            if (jSONArray != null) {
                jSONObject.put(SD_KEY_DERIVATION_DATA_LIST, jSONArray);
            }
            this.mCallbackInfo = jSONObject.toString();
            LogMgrUtil.log(5, "999");
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    public SemClientNotifyEventInfo(String str, String str2, String str3, String str4, String str5, JSONArray jSONArray, JSONArray jSONArray2, JSONArray jSONArray3) throws SemClientException {
        this(str, str2, str3, str4, str5);
        LogMgrUtil.log(5, "000 callback:" + str);
        try {
            JSONObject jSONObject = new JSONObject(this.mCallbackInfo);
            if (jSONArray != null) {
                jSONObject.put(REGISTERED_SP_APPLET_VERSION_LIST, jSONArray);
            }
            if (jSONArray2 != null) {
                jSONObject.put(APPLET_STATUS_LIST, jSONArray2);
            }
            if (jSONArray3 != null) {
                jSONObject.put("accessAllowedSpAppIdList", jSONArray3);
            }
            this.mCallbackInfo = jSONObject.toString();
            LogMgrUtil.log(5, "999");
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    public SemClientNotifyEventInfo(String str, int i, String str2, String str3, boolean z) throws SemClientException {
        LogMgrUtil.log(5, "000 callback:" + str + " errorCode:" + i + " additionalErrorInformation:" + str2 + " message:" + str3 + "isNeedBindForSEMCApp:" + z);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(NAME_CALLBACK, str);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("errorCode", i);
            if (str2 != null) {
                jSONObject2.put(NAME_ERROR_INFO_ADDITIONAL_INFORMATION, str2);
            }
            if (str3 != null) {
                jSONObject2.put(NAME_ERROR_INFO_MESSAGE, str3);
            }
            jSONObject2.put(NAME_ERROR_NEED_BIND_FOR_SEMC_APP, z);
            jSONObject.put(NAME_ERROR_INFO, jSONObject2);
            this.mCallbackInfo = jSONObject.toString();
            LogMgrUtil.log(5, "999");
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    public SemClientNotifyEventInfo(String str, int i, String str2, String str3) throws SemClientException {
        this(str, i, str2, str3, false);
    }

    public String getProcessStatus() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            String string = new JSONObject(this.mCallbackInfo).getString(PROCESS_STATUS);
            LogMgrUtil.log(5, "999 callback:" + string);
            return string;
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    public String getProcessResultCode() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            String strOptString = new JSONObject(this.mCallbackInfo).optString(PROCESS_RESULT_CODE);
            LogMgrUtil.log(5, "999 callback:" + strOptString);
            return strOptString;
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    public String getProcessResultDetailCode() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            String strOptString = new JSONObject(this.mCallbackInfo).optString(PROCESS_RESULT_DETAIL_CODE);
            LogMgrUtil.log(5, "999 callback:" + strOptString);
            return strOptString;
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    public String getProcessResultMessageString() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            String strOptString = new JSONObject(this.mCallbackInfo).optString(PROCESS_RESULT_MESSAGE_STRING);
            LogMgrUtil.log(5, "999 callback:" + strOptString);
            return strOptString;
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    public SemClient.SdKeyDerivationData[] getSdKeyDerivationDataList() throws SemClientException {
        SemClient.SdKeyDerivationData[] sdKeyDerivationDataArr;
        LogMgrUtil.log(5, "000");
        try {
            JSONArray jSONArrayOptJSONArray = new JSONObject(this.mCallbackInfo).optJSONArray(SD_KEY_DERIVATION_DATA_LIST);
            if (jSONArrayOptJSONArray == null) {
                LogMgrUtil.log(8, "001 sdKeyDerivationDataList is NOT set in resp data.");
                sdKeyDerivationDataArr = null;
            } else {
                LogMgrUtil.log(8, "002 sdKeyDerivationDataList is set in resp data.");
                SemClient.SdKeyDerivationData[] sdKeyDerivationDataArr2 = new SemClient.SdKeyDerivationData[jSONArrayOptJSONArray.length()];
                for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                    sdKeyDerivationDataArr2[i] = new SemClient.SdKeyDerivationData(jSONArrayOptJSONArray.getJSONObject(i).getString(SD_KEY_DERIVATION_DATA_SD_AID), jSONArrayOptJSONArray.getJSONObject(i).getString(SD_KEY_DERIVATION_DATA_DR), jSONArrayOptJSONArray.getJSONObject(i).getString(SD_KEY_DERIVATION_DATA_CRT), jSONArrayOptJSONArray.getJSONObject(i).getString(SD_KEY_DERIVATION_DATA_RECEIPT), jSONArrayOptJSONArray.getJSONObject(i).getString(SD_KEY_DERIVATION_DATA_CASD_CERTIFICATE));
                }
                sdKeyDerivationDataArr = sdKeyDerivationDataArr2;
            }
            LogMgrUtil.log(5, "999 callback:" + Arrays.toString(sdKeyDerivationDataArr));
            return sdKeyDerivationDataArr;
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    public String[] getRegisteredSpAppletVersionList() throws SemClientException {
        String[] strArr;
        LogMgrUtil.log(5, "000");
        try {
            JSONArray jSONArrayOptJSONArray = new JSONObject(this.mCallbackInfo).optJSONArray(REGISTERED_SP_APPLET_VERSION_LIST);
            if (jSONArrayOptJSONArray == null) {
                LogMgrUtil.log(8, "001 registeredSpAppletVersionList is NOT set in resp data.");
                strArr = null;
            } else {
                LogMgrUtil.log(8, "002 registeredSpAppletVersionList is set in resp data.");
                String[] strArr2 = new String[jSONArrayOptJSONArray.length()];
                for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                    strArr2[i] = jSONArrayOptJSONArray.getString(i);
                }
                strArr = strArr2;
            }
            LogMgrUtil.log(5, "999 callback:" + Arrays.toString(strArr));
            return strArr;
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    public SemClient.AppletStatus[] getAppletStatusList() throws SemClientException {
        SemClient.AppletStatus[] appletStatusArr;
        LogMgrUtil.log(5, "000");
        try {
            JSONArray jSONArrayOptJSONArray = new JSONObject(this.mCallbackInfo).optJSONArray(APPLET_STATUS_LIST);
            if (jSONArrayOptJSONArray == null) {
                LogMgrUtil.log(8, "001 appletStatusList is NOT set in resp data.");
                appletStatusArr = null;
            } else {
                LogMgrUtil.log(8, "002 appletStatusList is set in resp data.");
                SemClient.AppletStatus[] appletStatusArr2 = new SemClient.AppletStatus[jSONArrayOptJSONArray.length()];
                for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                    appletStatusArr2[i] = new SemClient.AppletStatus(jSONArrayOptJSONArray.getJSONObject(i).optString("spAppletVersion"), jSONArrayOptJSONArray.getJSONObject(i).optString("oldSpAppletVersion"), jSONArrayOptJSONArray.getJSONObject(i).optString("newSpAppletVersion"), jSONArrayOptJSONArray.getJSONObject(i).getString("status"));
                }
                appletStatusArr = appletStatusArr2;
            }
            LogMgrUtil.log(5, "999 callback:" + Arrays.toString(appletStatusArr));
            return appletStatusArr;
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    public String[] getAccessAllowedSpAppIdList() throws SemClientException {
        String[] strArr;
        LogMgrUtil.log(5, "000");
        try {
            JSONArray jSONArrayOptJSONArray = new JSONObject(this.mCallbackInfo).optJSONArray("accessAllowedSpAppIdList");
            if (jSONArrayOptJSONArray == null) {
                LogMgrUtil.log(8, "001 accessAllowedSpAppIdList is NOT set in resp data.");
                strArr = null;
            } else {
                LogMgrUtil.log(8, "002 accessAllowedSpAppIdList is set in resp data.");
                String[] strArr2 = new String[jSONArrayOptJSONArray.length()];
                for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                    strArr2[i] = jSONArrayOptJSONArray.getString(i);
                }
                strArr = strArr2;
            }
            LogMgrUtil.log(5, "999 callback:" + Arrays.toString(strArr));
            return strArr;
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    public String getCallbackName() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            String string = new JSONObject(this.mCallbackInfo).getString(NAME_CALLBACK);
            LogMgrUtil.log(5, "999 callback:" + string);
            return string;
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    public int getErrorCode() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            int intErrorInfoMember = getIntErrorInfoMember("errorCode");
            LogMgrUtil.log(5, "999 error code[" + intErrorInfoMember + "]");
            return intErrorInfoMember;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to get error code.");
            throw e;
        }
    }

    public boolean getIsNeedBindForSEMCApp() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            boolean booleanErrorInfoMember = getBooleanErrorInfoMember(NAME_ERROR_NEED_BIND_FOR_SEMC_APP);
            LogMgrUtil.log(5, "999 error isNeedBindForSEMCApp[" + booleanErrorInfoMember + "]");
            return booleanErrorInfoMember;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to get error isNeedBindForSEMCApp");
            throw e;
        }
    }

    public String getErrorAdditionalInformation() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            String stringErrorInfoMember = getStringErrorInfoMember(NAME_ERROR_INFO_ADDITIONAL_INFORMATION);
            LogMgrUtil.log(5, "999 error additional information[" + stringErrorInfoMember + "]");
            return stringErrorInfoMember;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to get error additional information");
            throw e;
        }
    }

    public String getErrorMessage() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            String stringErrorInfoMember = getStringErrorInfoMember(NAME_ERROR_INFO_MESSAGE);
            LogMgrUtil.log(5, "999 error message[" + stringErrorInfoMember + "]");
            return stringErrorInfoMember;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to get error message");
            throw e;
        }
    }

    protected int getIntErrorInfoMember(String str) throws SemClientException {
        LogMgrUtil.log(8, "000 name[" + str + "]");
        try {
            JSONObject jSONObjectOptJSONObject = new JSONObject(this.mCallbackInfo).optJSONObject(NAME_ERROR_INFO);
            if (jSONObjectOptJSONObject == null) {
                LogMgrUtil.log(2, "701 No member(errorInfois in CallbackInfo(" + this.mCallbackInfo + ")");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            int iOptInt = jSONObjectOptJSONObject.optInt(str);
            LogMgrUtil.log(8, "999 value=" + iOptInt);
            return iOptInt;
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    protected String getStringErrorInfoMember(String str) throws SemClientException {
        LogMgrUtil.log(8, "000 name[" + str + "]");
        try {
            JSONObject jSONObjectOptJSONObject = new JSONObject(this.mCallbackInfo).optJSONObject(NAME_ERROR_INFO);
            if (jSONObjectOptJSONObject == null) {
                LogMgrUtil.log(2, "701 No member(errorInfois in CallbackInfo(" + this.mCallbackInfo + ")");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            String strOptString = jSONObjectOptJSONObject.optString(str);
            LogMgrUtil.log(8, "999 value=" + strOptString);
            return strOptString;
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    protected boolean getBooleanErrorInfoMember(String str) throws SemClientException {
        LogMgrUtil.log(8, "000 name[" + str + "]");
        try {
            JSONObject jSONObjectOptJSONObject = new JSONObject(this.mCallbackInfo).optJSONObject(NAME_ERROR_INFO);
            if (jSONObjectOptJSONObject == null) {
                LogMgrUtil.log(2, "701 No member(errorInfois in CallbackInfo(" + this.mCallbackInfo + ")");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            boolean zOptBoolean = jSONObjectOptJSONObject.optBoolean(str);
            LogMgrUtil.log(8, "999 value=" + zOptBoolean);
            return zOptBoolean;
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    private void readFromParcel(Parcel parcel) {
        LogMgrUtil.log(8, "000 : in = " + parcel);
        this.mCallbackInfo = parcel.readString();
        LogMgrUtil.log(8, "999 : errorInfo = " + this.mCallbackInfo);
    }

    private SemClientNotifyEventInfo(Parcel parcel) {
        LogMgrUtil.log(8, "000 : in = " + parcel);
        readFromParcel(parcel);
        LogMgrUtil.log(8, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        LogMgrUtil.log(6, "000 : out = " + parcel + " flag = " + i);
        parcel.writeString(this.mCallbackInfo);
        LogMgrUtil.log(6, "999");
    }
}
