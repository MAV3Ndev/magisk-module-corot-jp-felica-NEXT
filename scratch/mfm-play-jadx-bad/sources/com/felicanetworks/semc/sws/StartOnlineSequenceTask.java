package com.felicanetworks.semc.sws;

import android.content.Context;
import com.felicanetworks.semc.DataManager;
import com.felicanetworks.semc.SemChipHolder;
import com.felicanetworks.semc.SemClientException;
import com.felicanetworks.semc.permit.SpAppInfo;
import com.felicanetworks.semc.permit.SpAppInfoListChecker;
import com.felicanetworks.semc.sws.AsyncTaskBase;
import com.felicanetworks.semc.sws.json.GetScriptResponseJson;
import com.felicanetworks.semc.sws.json.LinkageDataJson;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import com.felicanetworks.semc.util.SharedPrefsUtil;
import java.util.concurrent.ExecutorService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
class StartOnlineSequenceTask extends AsyncParentTaskBase<GetScriptResponseJson> {
    private final SemChipHolder mChipHolder;
    private final Context mContext;
    private final DataManager mDataManager;
    private final boolean mIsCalledFromSpApp;
    private String mLinkageData;
    private GetScriptResponseJson mResponse;
    private final SwsClient mSwsClient;

    StartOnlineSequenceTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, String str, SwsClient swsClient, SemChipHolder semChipHolder, DataManager dataManager, boolean z, Context context) {
        super(i, executorService, listener);
        LogMgr.log(7, "000");
        this.mLinkageData = str;
        this.mSwsClient = swsClient;
        this.mChipHolder = semChipHolder;
        this.mDataManager = dataManager;
        this.mIsCalledFromSpApp = z;
        this.mContext = context;
        LogMgr.log(7, "999");
    }

    @Override // com.felicanetworks.semc.sws.AsyncTaskBase
    protected void run() throws Throwable {
        LogMgr.log(8, "000");
        startSwsSEAccessSubTask();
        LogMgr.log(8, "999");
    }

    private void startSwsSEAccessSubTask() throws Throwable {
        LogMgr.log(8, "000");
        if (this.mLinkageData != null) {
            try {
                LinkageDataJson linkageDataJson = new LinkageDataJson(this.mLinkageData);
                String serviceId = linkageDataJson.getServiceId();
                String spAppId = this.mIsCalledFromSpApp ? linkageDataJson.getSpAppId() : null;
                String seid = linkageDataJson.getSeid();
                JSONObject processTraceInfo = linkageDataJson.getProcessTraceInfo();
                String accessToken = linkageDataJson.getAccessToken();
                JSONArray jSONArrayOptApduCommandInfoList = linkageDataJson.optApduCommandInfoList();
                String spAppletId = linkageDataJson.getSpAppletId();
                checkLinkageDataValidity(spAppId, seid);
                if (this.mIsCalledFromSpApp) {
                    SpAppInfoListChecker.checkSpAppInfoList(this.mContext, serviceId, spAppletId);
                }
                SwsSEAccessSubTask swsSEAccessSubTask = new SwsSEAccessSubTask(0, this.mSwsClient, this.mChipHolder, processTraceInfo, accessToken, jSONArrayOptApduCommandInfoList, serviceId);
                setStoppableSubTask(swsSEAccessSubTask);
                swsSEAccessSubTask.start();
            } catch (SemClientException e) {
                onFinished(false, e.getErrorCode(), "", e.getMessage());
            } catch (JSONException unused) {
                LogMgr.log(2, "700 linkageData is invalid. Failed to start SwsSEAccessSubTask.");
                onFinished(false, 102, "", ObfuscatedMsgUtil.executionPoint());
            }
        } else {
            LogMgr.log(2, "701 mLinkageData is null. Failed to start SwsSEAccessSubTask.");
            onFinished(false, 102, "", ObfuscatedMsgUtil.executionPoint());
        }
        LogMgr.log(8, "999");
    }

    private void checkLinkageDataValidity(String str, String str2) throws Throwable {
        LogMgr.log(8, "000 spAppId=[" + str + "] seId=[" + str2 + "]");
        if (this.mIsCalledFromSpApp) {
            SpAppInfo callerSpAppInfo = this.mDataManager.getCallerSpAppInfo();
            if (callerSpAppInfo == null) {
                LogMgr.log(2, "700 spAppInfo is null.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            if (!str.equals(callerSpAppInfo.mSpAppId)) {
                LogMgr.log(2, "701 spAppId does not match.");
                throw new JSONException(ObfuscatedMsgUtil.executionPoint());
            }
        }
        SharedPrefsUtil sharedPrefsUtil = new SharedPrefsUtil(this.mContext);
        String strDoGetSeId = this.mChipHolder.doGetSeId();
        if (!strDoGetSeId.equals(this.mDataManager.getSeid())) {
            sharedPrefsUtil.removeDeviceToken();
            sharedPrefsUtil.writeSeId(strDoGetSeId);
        }
        if (!str2.equals(strDoGetSeId)) {
            LogMgr.log(2, "702 seId does not match.");
            throw new JSONException(ObfuscatedMsgUtil.executionPoint());
        }
        LogMgr.log(8, "999 Linkage Data is valid");
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.semc.sws.AsyncTaskBase
    public void setResult(GetScriptResponseJson getScriptResponseJson) {
        LogMgr.log(8, "000");
        this.mResponse = getScriptResponseJson;
        LogMgr.log(8, "999");
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.felicanetworks.semc.sws.AsyncParentTaskBase
    public synchronized GetScriptResponseJson getResult() {
        LogMgr.log(7, "000");
        LogMgr.log(7, "999");
        return this.mResponse;
    }

    @Override // com.felicanetworks.semc.sws.AsyncTaskBase
    protected void onFinished(boolean z, int i, String str, String str2) {
        LogMgr.log(8, "000");
        super.onFinished(z, i, str, str2);
        LogMgr.log(8, "999");
    }

    private class SwsSEAccessSubTask<T> extends GetScriptSubTask<GetScriptResponseJson> {
        SwsSEAccessSubTask(int i, SwsClient swsClient, SemChipHolder semChipHolder, JSONObject jSONObject, String str, JSONArray jSONArray, String str2) {
            super(i, swsClient, semChipHolder, StartOnlineSequenceTask.this.mExecutor, StartOnlineSequenceTask.this.mDataManager, jSONObject, str, jSONArray, str2, !StartOnlineSequenceTask.this.mIsCalledFromSpApp);
            LogMgr.log(7, "000");
            LogMgr.log(7, "999");
        }

        @Override // com.felicanetworks.semc.sws.GetScriptSubTask
        void onSuccessScript(GetScriptResponseJson getScriptResponseJson) {
            LogMgr.log(7, "000");
            StartOnlineSequenceTask.this.setResult(getScriptResponseJson);
            StartOnlineSequenceTask.this.onFinished(true, 0, "", "");
            LogMgr.log(7, "999");
        }

        @Override // com.felicanetworks.semc.sws.GetScriptSubTask
        void onErrorScript(int i, String str, String str2) {
            LogMgr.log(7, "000");
            StartOnlineSequenceTask.this.onFinished(false, i, str, str2);
            LogMgr.log(7, "999");
        }
    }
}
