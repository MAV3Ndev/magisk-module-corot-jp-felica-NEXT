package com.felicanetworks.semc.permit;

import android.content.Context;
import com.felicanetworks.semc.SemClientException;
import com.felicanetworks.semc.config.AccessConfig;
import com.felicanetworks.semc.permit.SpAppInfo;
import com.felicanetworks.semc.sws.json.SpAppInfoJsonArray;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import com.felicanetworks.semc.util.SharedPrefsUtil;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class SpAppInfoListChecker {
    public static void checkSpAppInfoList(Context context, String str, String str2) throws SemClientException {
        JSONObject appletStatusCacheListJson;
        String strOptString;
        LogMgr.log(6, "000 serviceId=[" + str + "]");
        try {
            SharedPrefsUtil sharedPrefsUtil = new SharedPrefsUtil(context);
            ArrayList<SpAppInfo> spAppInfoList = new SpAppInfoJsonArray(sharedPrefsUtil.readSpAppInfoList()).getSpAppInfoList();
            if (spAppInfoList == null) {
                LogMgr.log(2, "700 spAppInfoList is null.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            boolean z = false;
            boolean z2 = false;
            for (int i = 0; i < spAppInfoList.size(); i++) {
                for (SpAppInfo.AllowedServiceList allowedServiceList : spAppInfoList.get(i).mAllowedServiceLists) {
                    if (str.equals(allowedServiceList.serviceId)) {
                        String chipIssuerId = AccessConfig.getChipIssuerId();
                        if (allowedServiceList.sepIdList == null || !allowedServiceList.sepIdList.contains(chipIssuerId)) {
                            z = true;
                        } else {
                            z = true;
                            z2 = true;
                        }
                    }
                }
            }
            if (!z) {
                LogMgr.log(2, "701 serviceId not included in list.");
                throw new SemClientException(100, ObfuscatedMsgUtil.executionPoint());
            }
            if (!z2) {
                LogMgr.log(2, "702 chipIssureId not included in list.");
                throw new SemClientException(100, ObfuscatedMsgUtil.executionPoint());
            }
            if (str2 != null && (appletStatusCacheListJson = sharedPrefsUtil.getAppletStatusCacheListJson()) != null && (strOptString = appletStatusCacheListJson.optString(str2)) != null && !strOptString.isEmpty()) {
                sharedPrefsUtil.removeAppletStatusCache(str2);
            }
            LogMgr.log(6, "999");
        } catch (JSONException e) {
            LogMgr.log(2, "703 : JSONException:" + e.getMessage());
            LogMgr.printStackTrace(8, e);
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        }
    }
}
