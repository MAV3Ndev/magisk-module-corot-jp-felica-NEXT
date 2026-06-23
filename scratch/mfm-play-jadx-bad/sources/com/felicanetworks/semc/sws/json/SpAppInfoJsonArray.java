package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.SemClientException;
import com.felicanetworks.semc.permit.SpAppInfo;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class SpAppInfoJsonArray extends JSONArray {
    private static final String KEY_AID_LIST_FOR_CRS_OPERATION = "aidListForCrsOperation";
    private static final String KEY_ALLOWED_SERVICE_LIST = "allowedServiceList";
    private static final String KEY_NEEDS_NOTIFY_UNINSTALLATION = "needsNotifyUninstallation";
    private static final String KEY_PACKAGE_NAME = "packageName";
    private static final String KEY_SEP_ID_LIST = "sepIdList";
    private static final String KEY_SERVICE_ID = "serviceId";
    private static final String KEY_SIGNING_CERT_HASH = "signingCertHash";
    private static final String KEY_SP_APP_ID = "spAppId";
    private static final String KEY_USE_CASE_LIST = "usecaseList";

    public SpAppInfoJsonArray(String str) throws JSONException {
        super(str);
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
    }

    protected void checkMembers() throws JSONException {
        LogMgr.log(8, "000");
        for (int i = 0; i < length(); i++) {
            JSONObject jSONObject = getJSONObject(i);
            JsonUtil.checkString(jSONObject, KEY_SP_APP_ID, true, 8);
            JsonUtil.checkString(jSONObject, "packageName", true, 1, 1024);
            JsonUtil.checkString(jSONObject, KEY_SIGNING_CERT_HASH, true, 64);
            JsonUtil.checkBoolean(jSONObject, KEY_NEEDS_NOTIFY_UNINSTALLATION, true);
            JSONArray jSONArrayCheckArray = JsonUtil.checkArray(jSONObject, KEY_AID_LIST_FOR_CRS_OPERATION, false);
            if (jSONArrayCheckArray != null) {
                for (int i2 = 0; i2 < jSONArrayCheckArray.length(); i2++) {
                    String string = jSONArrayCheckArray.getString(i2);
                    if (string.length() < 10 || string.length() > 32) {
                        throw new JSONException(ObfuscatedMsgUtil.executionPoint());
                    }
                }
            }
            JSONArray jSONArrayCheckArray2 = JsonUtil.checkArray(jSONObject, KEY_ALLOWED_SERVICE_LIST, false);
            if (jSONArrayCheckArray2 != null) {
                for (int i3 = 0; i3 < jSONArrayCheckArray2.length(); i3++) {
                    JSONObject jSONObject2 = jSONArrayCheckArray2.getJSONObject(i3);
                    JsonUtil.checkString(jSONObject2, "serviceId", true, 8);
                    JSONArray jSONArrayCheckArray3 = JsonUtil.checkArray(jSONObject2, KEY_SEP_ID_LIST, true);
                    if (jSONArrayCheckArray3 != null) {
                        for (int i4 = 0; i4 < jSONArrayCheckArray3.length(); i4++) {
                            if (jSONArrayCheckArray3.getString(i4).length() != 6) {
                                throw new JSONException(ObfuscatedMsgUtil.executionPoint(KEY_SEP_ID_LIST));
                            }
                        }
                    }
                    JsonUtil.checkArray(jSONObject2, KEY_USE_CASE_LIST, false);
                }
            }
        }
        LogMgr.log(8, "999");
    }

    public String getSpAppId(int i) throws SemClientException {
        LogMgr.log(7, "000");
        try {
            if (i >= length()) {
                LogMgr.log(2, "700 Index is out of bounds.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            String string = getJSONObject(i).getString(KEY_SP_APP_ID);
            LogMgr.log(7, "999 ret=" + string);
            return string;
        } catch (JSONException e) {
            LogMgr.log(2, "701 Failed to get PackageName(" + i + "). JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
    }

    public String getPackageName(int i) throws SemClientException {
        LogMgr.log(7, "000");
        try {
            if (i >= length()) {
                LogMgr.log(2, "700 Index is out of bounds.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            String string = getJSONObject(i).getString("packageName");
            LogMgr.log(7, "999 ret=" + string);
            return string;
        } catch (JSONException e) {
            LogMgr.log(2, "701 Failed to get PackageName(" + i + "). JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
    }

    public boolean getNeedsNotifyUninstallation(int i) throws SemClientException {
        LogMgr.log(7, "000");
        try {
            if (i >= length()) {
                LogMgr.log(2, "700 Index is out of bounds.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            boolean z = getJSONObject(i).getBoolean(KEY_NEEDS_NOTIFY_UNINSTALLATION);
            LogMgr.log(7, "999 ret=" + z);
            return z;
        } catch (JSONException e) {
            LogMgr.log(2, "701 Failed to get PackageName(" + i + "). JSONException:" + e.getMessage());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
    }

    public ArrayList<SpAppInfo> getSpAppInfoList(String str) throws JSONException, SemClientException {
        LogMgr.log(7, "000 packageName=[" + str + "]");
        try {
            if (length() <= 0) {
                LogMgr.log(2, "700 App cert hash data is null or nothing.");
                throw new SemClientException(100, ObfuscatedMsgUtil.executionPoint());
            }
            ArrayList<SpAppInfo> arrayList = new ArrayList<>();
            for (int i = 0; i < length(); i++) {
                JSONObject jSONObject = getJSONObject(i);
                if (jSONObject.getString("packageName").equals(str)) {
                    LogMgr.log(7, "998 match packageName at [" + i + "]");
                    arrayList.add(createSpAppInfo(jSONObject));
                }
            }
            if (arrayList.size() <= 0) {
                LogMgr.log(2, "701 Failed to get SigningCertHash list. Not permitted.");
                return null;
            }
            LogMgr.log(7, "999");
            return arrayList;
        } catch (JSONException e) {
            LogMgr.log(2, "702 Failed to get SigningCertHash list. JSONException:" + e.getMessage());
            throw e;
        }
    }

    public ArrayList<SpAppInfo> getSpAppInfoListBySpAppId(String str) throws JSONException, SemClientException {
        LogMgr.log(7, "000 spAppId=[" + str + "]");
        try {
            if (length() <= 0) {
                LogMgr.log(2, "700 App cert hash data is null or nothing.");
                throw new SemClientException(100, ObfuscatedMsgUtil.executionPoint());
            }
            ArrayList<SpAppInfo> arrayList = new ArrayList<>();
            for (int i = 0; i < length(); i++) {
                JSONObject jSONObject = getJSONObject(i);
                if (jSONObject.getString(KEY_SP_APP_ID).equals(str)) {
                    LogMgr.log(7, "998 match spAppId at [" + i + "]");
                    arrayList.add(createSpAppInfo(jSONObject));
                }
            }
            if (arrayList.size() <= 0) {
                LogMgr.log(2, "701 Failed to get SigningCertHash list. Not permitted.");
                return null;
            }
            LogMgr.log(7, "999");
            return arrayList;
        } catch (JSONException e) {
            LogMgr.log(2, "702 Failed to get SigningCertHash list. JSONException:" + e.getMessage());
            throw e;
        }
    }

    public ArrayList<SpAppInfo> getSpAppInfoList() throws JSONException, SemClientException {
        LogMgr.log(7, "000");
        try {
            if (length() <= 0) {
                LogMgr.log(2, "700 spAppInfoList data is null or nothing.");
                throw new SemClientException(100, ObfuscatedMsgUtil.executionPoint());
            }
            ArrayList<SpAppInfo> arrayList = new ArrayList<>();
            for (int i = 0; i < length(); i++) {
                arrayList.add(createSpAppInfo(getJSONObject(i)));
            }
            if (arrayList.size() <= 0) {
                LogMgr.log(2, "701 Failed to get spAppInfoList list. Not permitted.");
                return null;
            }
            LogMgr.log(7, "999");
            return arrayList;
        } catch (JSONException e) {
            LogMgr.log(2, "702 Failed to get spAppInfoList list. JSONException:" + e.getMessage());
            throw e;
        }
    }

    private SpAppInfo createSpAppInfo(JSONObject jSONObject) throws JSONException {
        LogMgr.log(8, "000");
        SpAppInfo spAppInfo = new SpAppInfo();
        spAppInfo.mSpAppId = jSONObject.getString(KEY_SP_APP_ID);
        spAppInfo.mSigningCertHash = jSONObject.getString(KEY_SIGNING_CERT_HASH);
        spAppInfo.mAllowedServiceLists = new ArrayList();
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(KEY_ALLOWED_SERVICE_LIST);
        if (jSONArrayOptJSONArray != null && jSONArrayOptJSONArray.length() > 0) {
            LogMgr.log(8, "allowedServiceList data is available.");
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                SpAppInfo.AllowedServiceList allowedServiceList = new SpAppInfo.AllowedServiceList();
                JSONObject jSONObject2 = jSONArrayOptJSONArray.getJSONObject(i);
                allowedServiceList.serviceId = jSONObject2.getString("serviceId");
                LogMgr.log(8, "Add serviceId[" + allowedServiceList.serviceId + "]");
                allowedServiceList.sepIdList = new ArrayList();
                JSONArray jSONArrayOptJSONArray2 = jSONObject2.optJSONArray(KEY_SEP_ID_LIST);
                if (jSONArrayOptJSONArray2 != null) {
                    for (int i2 = 0; i2 < jSONArrayOptJSONArray2.length(); i2++) {
                        String string = jSONArrayOptJSONArray2.getString(i2);
                        allowedServiceList.sepIdList.add(string);
                        LogMgr.log(8, "Add sepId[" + string + "]");
                    }
                }
                allowedServiceList.useCaseList = new ArrayList();
                JSONArray jSONArrayOptJSONArray3 = jSONObject2.optJSONArray(KEY_USE_CASE_LIST);
                if (jSONArrayOptJSONArray3 != null) {
                    for (int i3 = 0; i3 < jSONArrayOptJSONArray3.length(); i3++) {
                        String string2 = jSONArrayOptJSONArray3.getString(i3);
                        allowedServiceList.useCaseList.add(string2);
                        LogMgr.log(8, "Add useCase[" + string2 + "]");
                    }
                }
                spAppInfo.mAllowedServiceLists.add(allowedServiceList);
            }
        }
        spAppInfo.mAidListForCrsOperation = new ArrayList();
        JSONArray jSONArrayOptJSONArray4 = jSONObject.optJSONArray(KEY_AID_LIST_FOR_CRS_OPERATION);
        if (jSONArrayOptJSONArray4 == null || jSONArrayOptJSONArray4.length() == 0) {
            LogMgr.log(8, "997");
            return spAppInfo;
        }
        for (int i4 = 0; i4 < jSONArrayOptJSONArray4.length(); i4++) {
            spAppInfo.mAidListForCrsOperation.add(jSONArrayOptJSONArray4.getString(i4));
        }
        LogMgr.log(8, "999");
        return spAppInfo;
    }
}
