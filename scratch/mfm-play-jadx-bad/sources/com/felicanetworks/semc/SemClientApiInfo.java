package com.felicanetworks.semc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.semc.util.LogMgrUtil;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class SemClientApiInfo implements Parcelable {
    public static final Parcelable.Creator<SemClientApiInfo> CREATOR = new Parcelable.Creator<SemClientApiInfo>() { // from class: com.felicanetworks.semc.SemClientApiInfo.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemClientApiInfo createFromParcel(Parcel parcel) {
            LogMgrUtil.log(6, "000");
            LogMgrUtil.log(6, "999 : in = " + parcel);
            return new SemClientApiInfo(parcel);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemClientApiInfo[] newArray(int i) {
            LogMgrUtil.log(6, "000");
            LogMgrUtil.log(6, "999 : size = " + i);
            return new SemClientApiInfo[i];
        }
    };
    public static final String METHOD_NAME_ACTIVATE_CONTACTLESS_INTERFACE = "activateContactlessInterface";
    public static final String METHOD_NAME_CONNECT = "connect";
    public static final String METHOD_NAME_DEACTIVATE_CONTACTLESS_INTERFACE = "deactivateContactlessInterface";
    public static final String METHOD_NAME_DELETE_APPLET = "deleteApplet";
    public static final String METHOD_NAME_DISCONNECT = "disconnect";
    public static final String METHOD_NAME_GET_APPLET_STATUS = "getAppletStatus";
    public static final String METHOD_NAME_GET_CONTACTLESS_INTERFACE_STATUS = "getContactlessInterfaceStatus";
    public static final String METHOD_NAME_GET_SEID = "getSeid";
    public static final String METHOD_NAME_GET_SE_READER_NAME = "getSeReaderName";
    public static final String METHOD_NAME_INSTALL_APPLET = "installApplet";
    public static final String METHOD_NAME_NOTIFY_CLIENT_EVENT = "notifyClientEvent";
    public static final String METHOD_NAME_START_TSM_SEQUENCE = "startTsmSequence";
    public static final String METHOD_NAME_UPGRADE_APPLET = "upgradeApplet";
    private static final String NAME_ARGUMENTS = "arguments";
    public static final String NAME_ARGUMENTS_ACCESS_ALLOWED_SP_APP_ID_LIST = "accessAllowedSpAppIdList";
    private static final String NAME_ARGUMENTS_AID = "aid";
    private static final String NAME_ARGUMENTS_EVENT_TYPE = "eventType";
    private static final String NAME_ARGUMENTS_IS_CALLED_FROM_INTERNAL = "isCalledFromInternal";
    private static final String NAME_ARGUMENTS_IS_CHECK_SYSTEM_USER = "isCheckSystemUser";
    private static final String NAME_ARGUMENTS_IS_MODE_EXISTS = "isModeExists";
    private static final String NAME_ARGUMENTS_LINKAGE_DATA = "linkageData";
    private static final String NAME_ARGUMENTS_MODE = "mode";
    public static final String NAME_ARGUMENTS_NEW_SP_APPLET_VERSION = "newSpAppletVersion";
    public static final String NAME_ARGUMENTS_OLD_SP_APPLET_VERSION = "oldSpAppletVersion";
    public static final String NAME_ARGUMENTS_OPERATION_ID = "operationId";
    public static final String NAME_ARGUMENTS_OPERATION_TYPE = "operationType";
    private static final String NAME_ARGUMENTS_PACKAGE_NAME = "packageName";
    private static final String NAME_ARGUMENTS_RECONNECT_SEMC_APP = "isReConnectSEMCApp";
    public static final String NAME_ARGUMENTS_SERVICE_ID = "serviceId";
    public static final String NAME_ARGUMENTS_SP_APPLET_ID = "spAppletId";
    public static final String NAME_ARGUMENTS_SP_APPLET_VERSION = "spAppletVersion";
    public static final String NAME_ARGUMENTS_SP_SD_KEY_VERSION = "spSdKeyVersion";
    private static final String NAME_METHOD = "method";
    public static final int SUPORT_SEMC_MAJOR_VERSION_FOR_24_SECOND_DCK = 33;
    public static final int SUPORT_SEMC_MINOR_VERSION_FOR_24_SECOND_DCK = 23;
    private String mMethodInfo;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemClientApiInfo(String str) throws SemClientException {
        LogMgrUtil.log(5, "000 method:" + str);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("method", str);
            this.mMethodInfo = jSONObject.toString();
            LogMgrUtil.log(5, "999");
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    public void setIsCheckSystemUser(boolean z) throws SemClientException {
        LogMgrUtil.log(5, "000 isCheckSystemUser:" + z);
        try {
            putArgumentsMember(NAME_ARGUMENTS_IS_CHECK_SYSTEM_USER, z);
            LogMgrUtil.log(5, "999");
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to put isCheckSystemUser");
            throw e;
        }
    }

    public void setIsCalledFromInternal(boolean z) throws SemClientException {
        LogMgrUtil.log(5, "000 isCalledFromInternal:" + z);
        try {
            putArgumentsMember(NAME_ARGUMENTS_IS_CALLED_FROM_INTERNAL, z);
            LogMgrUtil.log(5, "999");
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to put isCalledFromInternal");
            throw e;
        }
    }

    public void setIsModeExists(boolean z) throws SemClientException {
        LogMgrUtil.log(5, "000 isModeExists:" + z);
        try {
            putArgumentsMember(NAME_ARGUMENTS_IS_MODE_EXISTS, z);
            LogMgrUtil.log(5, "999");
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to put isModeExists");
            throw e;
        }
    }

    public void setMode(int i) throws SemClientException {
        LogMgrUtil.log(5, "000 mode:" + i);
        try {
            putArgumentsMember(NAME_ARGUMENTS_MODE, i);
            LogMgrUtil.log(5, "999");
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to put mode");
            throw e;
        }
    }

    public void setIsReconnectSEMCApp(boolean z) throws SemClientException {
        LogMgrUtil.log(5, "000 isReConnectSEMCApp:" + z);
        try {
            putArgumentsMember(NAME_ARGUMENTS_RECONNECT_SEMC_APP, z);
            LogMgrUtil.log(5, "999");
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to put isReConnectSEMCApp");
            throw e;
        }
    }

    public void setLinkageData(String str) throws SemClientException {
        LogMgrUtil.log(5, "000 linkageData:" + str);
        try {
            putArgumentsMember("linkageData", str);
            LogMgrUtil.log(5, "999");
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to put linkageData");
            throw e;
        }
    }

    public void setAid(String str) throws SemClientException {
        LogMgrUtil.log(5, "000 aid:" + str);
        try {
            putArgumentsMember(NAME_ARGUMENTS_AID, str);
            LogMgrUtil.log(5, "999");
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to put AID");
            throw e;
        }
    }

    public void setPackageName(String str) throws SemClientException {
        LogMgrUtil.log(5, "000 packageName:" + str);
        try {
            putArgumentsMember("packageName", str);
            LogMgrUtil.log(5, "999");
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to put packageName");
            throw e;
        }
    }

    public void setEventType(String str) throws SemClientException {
        LogMgrUtil.log(5, "000 eventType:" + str);
        try {
            putArgumentsMember("eventType", str);
            LogMgrUtil.log(5, "999");
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to put eventType");
            throw e;
        }
    }

    public void setServiceId(String str) throws SemClientException {
        LogMgrUtil.log(5, "000 serviceId:" + str);
        try {
            putArgumentsMember("serviceId", str);
            LogMgrUtil.log(5, "999");
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to put serviceId");
            throw e;
        }
    }

    public void setSpAppletId(String str) throws SemClientException {
        LogMgrUtil.log(5, "000 spAppletId:" + str);
        try {
            putArgumentsMember(NAME_ARGUMENTS_SP_APPLET_ID, str);
            LogMgrUtil.log(5, "999");
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to put spAppletId");
            throw e;
        }
    }

    public void setSpAppletVersion(String str) throws SemClientException {
        LogMgrUtil.log(5, "000 spAppletVersion:" + str);
        try {
            putArgumentsMember(NAME_ARGUMENTS_SP_APPLET_VERSION, str);
            LogMgrUtil.log(5, "999");
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to put spAppletVersion");
            throw e;
        }
    }

    public void setSpSdKeyVersion(String str) throws SemClientException {
        LogMgrUtil.log(5, "000 spSdKeyVersion:" + str);
        if (str == null) {
            str = "";
        }
        try {
            putArgumentsMember(NAME_ARGUMENTS_SP_SD_KEY_VERSION, str);
            LogMgrUtil.log(5, "999");
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to put spSdKeyVersion");
            throw e;
        }
    }

    public void setAccessAllowedSpAppIdList(String[] strArr) throws SemClientException {
        LogMgrUtil.log(5, "000 accessAllowedSpAppIdList:" + Arrays.toString(strArr));
        try {
            putArgumentsMemberArray(NAME_ARGUMENTS_ACCESS_ALLOWED_SP_APP_ID_LIST, strArr);
            LogMgrUtil.log(5, "999");
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to put accessAllowedSpAppIdList");
            throw e;
        }
    }

    public void setOperationType(String str) throws SemClientException {
        LogMgrUtil.log(5, "000 operationType:" + str);
        try {
            putArgumentsMember(NAME_ARGUMENTS_OPERATION_TYPE, str);
            LogMgrUtil.log(5, "999");
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to put operationType");
            throw e;
        }
    }

    public void setOperationId(String str) throws SemClientException {
        LogMgrUtil.log(5, "000 operationId:" + str);
        try {
            putArgumentsMember(NAME_ARGUMENTS_OPERATION_ID, str);
            LogMgrUtil.log(5, "999");
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to put operationId");
            throw e;
        }
    }

    public void setOldAppletVersion(String str) throws SemClientException {
        LogMgrUtil.log(5, "000 oldSpAppletVersion:" + str);
        try {
            putArgumentsMember(NAME_ARGUMENTS_OLD_SP_APPLET_VERSION, str);
            LogMgrUtil.log(5, "999");
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to put oldSpAppletVersion");
            throw e;
        }
    }

    public void setNewAppletVersion(String str) throws SemClientException {
        LogMgrUtil.log(5, "000 newSpAppletVersion:" + str);
        try {
            putArgumentsMember(NAME_ARGUMENTS_NEW_SP_APPLET_VERSION, str);
            LogMgrUtil.log(5, "999");
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to put newSpAppletVersion");
            throw e;
        }
    }

    public String getMethodName() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            String string = new JSONObject(this.mMethodInfo).getString("method");
            LogMgrUtil.log(5, "999 method:" + string);
            return string;
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    public boolean getIsCheckSystemUser() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            boolean argumentsMemberBoolean = getArgumentsMemberBoolean(NAME_ARGUMENTS_IS_CHECK_SYSTEM_USER, true);
            LogMgrUtil.log(5, "999 isCheckSystemUser:" + argumentsMemberBoolean);
            return argumentsMemberBoolean;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to get systemUser check flag");
            throw e;
        }
    }

    public boolean getIsCalledFromInternal() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            boolean argumentsMemberBoolean = getArgumentsMemberBoolean(NAME_ARGUMENTS_IS_CALLED_FROM_INTERNAL, false);
            LogMgrUtil.log(5, "999 isCalledFromInternal:" + argumentsMemberBoolean);
            return argumentsMemberBoolean;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to get called from internal flag");
            throw e;
        }
    }

    public boolean getIsModeExists() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            boolean argumentsMemberBoolean = getArgumentsMemberBoolean(NAME_ARGUMENTS_IS_MODE_EXISTS, false);
            LogMgrUtil.log(5, "999 mode:" + argumentsMemberBoolean);
            return argumentsMemberBoolean;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to get mode");
            throw e;
        }
    }

    public int getMode() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            int argumentsMemberInt = getArgumentsMemberInt(NAME_ARGUMENTS_MODE, 0);
            LogMgrUtil.log(5, "999 mode:" + argumentsMemberInt);
            return argumentsMemberInt;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to get mode");
            throw e;
        }
    }

    public boolean getIsReConnectSEMCApp() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            boolean argumentsMemberBoolean = getArgumentsMemberBoolean(NAME_ARGUMENTS_RECONNECT_SEMC_APP, false);
            LogMgrUtil.log(5, "999 isReConnectSEMCApp:" + argumentsMemberBoolean);
            return argumentsMemberBoolean;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to get isReConnectSEMCApp");
            throw e;
        }
    }

    public String getLinkageData() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            String argumentsMember = getArgumentsMember("linkageData");
            LogMgrUtil.log(5, "999 linkageData:" + argumentsMember);
            return argumentsMember;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to get linkageData");
            throw e;
        }
    }

    public String getAid() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            String argumentsMember = getArgumentsMember(NAME_ARGUMENTS_AID);
            LogMgrUtil.log(5, "999 aid:" + argumentsMember);
            return argumentsMember;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to get AID");
            throw e;
        }
    }

    public String getPackageName() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            String argumentsMember = getArgumentsMember("packageName");
            LogMgrUtil.log(5, "999 packageName:" + argumentsMember);
            return argumentsMember;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to get packageName");
            throw e;
        }
    }

    public String getEventType() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            String argumentsMember = getArgumentsMember("eventType");
            LogMgrUtil.log(5, "999 eventType:" + argumentsMember);
            return argumentsMember;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to get eventType");
            throw e;
        }
    }

    public String getServiceId() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            String argumentsMember = getArgumentsMember("serviceId");
            LogMgrUtil.log(5, "999 serviceId:" + argumentsMember);
            return argumentsMember;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to get serviceId");
            throw e;
        }
    }

    public String getSpAppletId() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            String argumentsMember = getArgumentsMember(NAME_ARGUMENTS_SP_APPLET_ID);
            LogMgrUtil.log(5, "999 spAppletId:" + argumentsMember);
            return argumentsMember;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to get spAppletId");
            throw e;
        }
    }

    public String getSpAppletVersion() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            String argumentsMember = getArgumentsMember(NAME_ARGUMENTS_SP_APPLET_VERSION);
            LogMgrUtil.log(5, "999 spAppletVersion:" + argumentsMember);
            return argumentsMember;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to get spAppletVersion");
            throw e;
        }
    }

    public String getSpSdKeyVersion() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            String argumentsMember = getArgumentsMember(NAME_ARGUMENTS_SP_SD_KEY_VERSION);
            LogMgrUtil.log(5, "999 spSdKeyVersion:" + argumentsMember);
            return argumentsMember;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to get spSdKeyVersion");
            throw e;
        }
    }

    public String[] getAccessAllowedSpAppIdList() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            String[] argumentsMemberArray = getArgumentsMemberArray(NAME_ARGUMENTS_ACCESS_ALLOWED_SP_APP_ID_LIST);
            LogMgrUtil.log(5, "999 accessAllowedSpAppIdList:" + Arrays.toString(argumentsMemberArray));
            return argumentsMemberArray;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to get accessAllowedSpAppIdList");
            throw e;
        }
    }

    public String getOperationType() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            String argumentsMember = getArgumentsMember(NAME_ARGUMENTS_OPERATION_TYPE);
            LogMgrUtil.log(5, "999 operationType:" + argumentsMember);
            return argumentsMember;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to get operationType");
            throw e;
        }
    }

    public String getOperationId() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            String argumentsMember = getArgumentsMember(NAME_ARGUMENTS_OPERATION_ID);
            LogMgrUtil.log(5, "999 operationId:" + argumentsMember);
            return argumentsMember;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to get operationId");
            throw e;
        }
    }

    public String getOldSpAppletVersion() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            String argumentsMember = getArgumentsMember(NAME_ARGUMENTS_OLD_SP_APPLET_VERSION);
            LogMgrUtil.log(5, "999 oldSpAppletVersion:" + argumentsMember);
            return argumentsMember;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to get oldSpAppletVersion");
            throw e;
        }
    }

    public String getNewSpAppletVersion() throws SemClientException {
        LogMgrUtil.log(5, "000");
        try {
            String argumentsMember = getArgumentsMember(NAME_ARGUMENTS_NEW_SP_APPLET_VERSION);
            LogMgrUtil.log(5, "999 newSpAppletVersion:" + argumentsMember);
            return argumentsMember;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 Failed to get newSpAppletVersion");
            throw e;
        }
    }

    protected void putArgumentsMember(String str, String str2) throws SemClientException {
        LogMgrUtil.log(8, "000 name[" + str + "] value[" + str2 + "]");
        try {
            JSONObject jSONObject = new JSONObject(this.mMethodInfo);
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(NAME_ARGUMENTS);
            if (jSONObjectOptJSONObject == null) {
                jSONObjectOptJSONObject = new JSONObject();
            }
            jSONObjectOptJSONObject.put(str, str2);
            jSONObject.put(NAME_ARGUMENTS, jSONObjectOptJSONObject);
            this.mMethodInfo = jSONObject.toString();
            LogMgrUtil.log(8, "999");
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    protected void putArgumentsMemberArray(String str, String[] strArr) throws SemClientException {
        LogMgrUtil.log(8, "000 name[" + str + "] value[" + Arrays.toString(strArr) + "]");
        try {
            JSONObject jSONObject = new JSONObject(this.mMethodInfo);
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(NAME_ARGUMENTS);
            if (jSONObjectOptJSONObject == null) {
                jSONObjectOptJSONObject = new JSONObject();
            }
            JSONArray jSONArray = new JSONArray();
            for (String str2 : strArr) {
                jSONArray.put(str2);
            }
            jSONObjectOptJSONObject.put(str, jSONArray);
            jSONObject.put(NAME_ARGUMENTS, jSONObjectOptJSONObject);
            this.mMethodInfo = jSONObject.toString();
            LogMgrUtil.log(8, "999");
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    protected void putArgumentsMember(String str, boolean z) throws SemClientException {
        LogMgrUtil.log(8, "000 name[" + str + "] value[" + z + "]");
        try {
            JSONObject jSONObject = new JSONObject(this.mMethodInfo);
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(NAME_ARGUMENTS);
            if (jSONObjectOptJSONObject == null) {
                jSONObjectOptJSONObject = new JSONObject();
            }
            jSONObjectOptJSONObject.put(str, z);
            jSONObject.put(NAME_ARGUMENTS, jSONObjectOptJSONObject);
            this.mMethodInfo = jSONObject.toString();
            LogMgrUtil.log(8, "999");
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    protected void putArgumentsMember(String str, int i) throws SemClientException {
        LogMgrUtil.log(8, "000 name[" + str + "] value[" + i + "]");
        try {
            JSONObject jSONObject = new JSONObject(this.mMethodInfo);
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(NAME_ARGUMENTS);
            if (jSONObjectOptJSONObject == null) {
                jSONObjectOptJSONObject = new JSONObject();
            }
            jSONObjectOptJSONObject.put(str, i);
            jSONObject.put(NAME_ARGUMENTS, jSONObjectOptJSONObject);
            this.mMethodInfo = jSONObject.toString();
            LogMgrUtil.log(8, "999");
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    protected String getArgumentsMember(String str) throws SemClientException {
        LogMgrUtil.log(8, "000 name[" + str + "]");
        try {
            JSONObject jSONObjectOptJSONObject = new JSONObject(this.mMethodInfo).optJSONObject(NAME_ARGUMENTS);
            String str2 = null;
            if (jSONObjectOptJSONObject != null) {
                String strOptString = jSONObjectOptJSONObject.optString(str);
                if (!strOptString.isEmpty()) {
                    str2 = strOptString;
                }
            }
            LogMgrUtil.log(8, "999 value=" + str2);
            return str2;
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    protected String[] getArgumentsMemberArray(String str) throws SemClientException {
        String[] strArr;
        LogMgrUtil.log(8, "000 name[" + str + "]");
        try {
            JSONObject jSONObjectOptJSONObject = new JSONObject(this.mMethodInfo).optJSONObject(NAME_ARGUMENTS);
            JSONArray jSONArray = null;
            String[] strArr2 = null;
            if (jSONObjectOptJSONObject == null || str == null) {
                strArr = null;
            } else {
                JSONArray jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray(str);
                if (jSONArrayOptJSONArray != null) {
                    strArr2 = new String[jSONArrayOptJSONArray.length()];
                    for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                        String strOptString = jSONArrayOptJSONArray.optString(i);
                        if (!strOptString.isEmpty()) {
                            strArr2[i] = strOptString;
                        }
                    }
                }
                String[] strArr3 = strArr2;
                jSONArray = jSONArrayOptJSONArray;
                strArr = strArr3;
            }
            LogMgrUtil.log(8, "999 value=" + jSONArray);
            return strArr;
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    protected boolean getArgumentsMemberBoolean(String str, boolean z) throws SemClientException {
        LogMgrUtil.log(8, "000 name[" + str + "]");
        try {
            JSONObject jSONObjectOptJSONObject = new JSONObject(this.mMethodInfo).optJSONObject(NAME_ARGUMENTS);
            boolean zOptBoolean = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optBoolean(str, z) : false;
            LogMgrUtil.log(8, "999 value=" + zOptBoolean);
            return zOptBoolean;
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    protected boolean isArgumentsMemberIntExist(String str) throws SemClientException {
        LogMgrUtil.log(8, "000 name[" + str + "]");
        try {
            if (new JSONObject(this.mMethodInfo).optJSONObject(NAME_ARGUMENTS) != null) {
                return !r0.isNull(str);
            }
            return false;
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    protected int getArgumentsMemberInt(String str, int i) throws SemClientException {
        LogMgrUtil.log(8, "000 name[" + str + "]");
        try {
            JSONObject jSONObjectOptJSONObject = new JSONObject(this.mMethodInfo).optJSONObject(NAME_ARGUMENTS);
            if (jSONObjectOptJSONObject != null) {
                i = jSONObjectOptJSONObject.optInt(str, i);
            }
            LogMgrUtil.log(8, "999 value=" + i);
            return i;
        } catch (JSONException e) {
            LogMgrUtil.log(2, "700 JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    private void readFromParcel(Parcel parcel) {
        LogMgrUtil.log(8, "000 : in = " + parcel);
        this.mMethodInfo = parcel.readString();
        LogMgrUtil.log(8, "999 : methodInfo = " + this.mMethodInfo);
    }

    private SemClientApiInfo(Parcel parcel) {
        LogMgrUtil.log(8, "000 : in = " + parcel);
        readFromParcel(parcel);
        LogMgrUtil.log(8, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        LogMgrUtil.log(6, "000 : out = " + parcel + " flag = " + i);
        parcel.writeString(this.mMethodInfo);
        LogMgrUtil.log(6, "999");
    }
}
