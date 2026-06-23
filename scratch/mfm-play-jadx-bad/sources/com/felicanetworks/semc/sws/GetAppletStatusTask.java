package com.felicanetworks.semc.sws;

import android.os.SystemClock;
import androidx.credentials.exceptions.publickeycredential.DomExceptionUtils;
import com.felicanetworks.semc.DataManager;
import com.felicanetworks.semc.FlavorConst;
import com.felicanetworks.semc.SemClientCallbackConst;
import com.felicanetworks.semc.http.HttpException;
import com.felicanetworks.semc.sws.AccessSwsTask;
import com.felicanetworks.semc.sws.AsyncTaskBase;
import com.felicanetworks.semc.sws.json.GetAppletStatusRequestJson;
import com.felicanetworks.semc.sws.json.GetAppletStatusResponseJson;
import com.felicanetworks.semc.sws.json.RequestJson;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class GetAppletStatusTask extends AsyncParentTaskBase<GetAppletStatusResponseJson> {
    private static final String CLIENT_CONTROL_INFO_MASTER_KEY_RETRY_INTERVAL = "retry.getAppletStatus";
    private static final List<String> VALID_RESULT_CODE_LIST_GET_APPLET_STATUS;
    private final DataManager mDataManager;
    private final String mOperationId;
    private final String mOperationType;
    private GetAppletStatusResponseJson mResponse;
    private final String mServiceId;
    private final String mSpAppId;
    private final String mSpAppletId;
    private final SwsClient mSwsClient;

    @Override // com.felicanetworks.semc.sws.AsyncParentTaskBase, com.felicanetworks.semc.sws.StoppableTaskBase
    public /* bridge */ /* synthetic */ void stop() {
        super.stop();
    }

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_APPLET_STATUS = arrayList;
        arrayList.add("0000");
        arrayList.add("1000");
        arrayList.add("2000");
        arrayList.add("3000");
        arrayList.add("4000");
        arrayList.add("9000");
        arrayList.add("9001");
        arrayList.add("9005");
    }

    GetAppletStatusTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, String str, String str2, String str3, SwsClient swsClient, DataManager dataManager, String str4, String str5) {
        super(i, executorService, listener);
        this.mServiceId = str;
        this.mSpAppId = str2;
        this.mSpAppletId = str3;
        this.mSwsClient = swsClient;
        this.mDataManager = dataManager;
        this.mOperationType = str4;
        this.mOperationId = str5;
    }

    public String getLinkageData() {
        LogMgr.log(8, "000");
        String linkageData = null;
        try {
            GetAppletStatusResponseJson getAppletStatusResponseJson = this.mResponse;
            if (getAppletStatusResponseJson != null) {
                linkageData = getAppletStatusResponseJson.getLinkageData();
            }
        } catch (JSONException unused) {
            LogMgr.log(2, "700 Failed to get LinkageData");
        }
        LogMgr.log(8, "999 ret=" + linkageData);
        return linkageData;
    }

    public String getProcessId() {
        LogMgr.log(8, "000");
        String processId = null;
        try {
            GetAppletStatusResponseJson getAppletStatusResponseJson = this.mResponse;
            if (getAppletStatusResponseJson != null) {
                processId = getAppletStatusResponseJson.getProcessId();
            }
        } catch (JSONException unused) {
            LogMgr.log(2, "700 Failed to get Process Id");
        }
        LogMgr.log(8, "999 ret=" + processId);
        return processId;
    }

    @Override // com.felicanetworks.semc.sws.AsyncTaskBase
    protected void run() {
        LogMgr.log(8, "000");
        GetAppletStatusSubTask getAppletStatusSubTask = new GetAppletStatusSubTask(this.mSwsClient);
        setStoppableSubTask(getAppletStatusSubTask);
        getAppletStatusSubTask.start();
        LogMgr.log(8, "999");
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.semc.sws.AsyncTaskBase
    public void setResult(GetAppletStatusResponseJson getAppletStatusResponseJson) {
        this.mResponse = getAppletStatusResponseJson;
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.felicanetworks.semc.sws.AsyncParentTaskBase
    public synchronized GetAppletStatusResponseJson getResult() {
        return this.mResponse;
    }

    @Override // com.felicanetworks.semc.sws.AsyncTaskBase
    protected void onFinished(boolean z, int i, String str, String str2) {
        LogMgr.log(8, "000");
        super.onFinished(z, i, str, str2);
        LogMgr.log(8, "999");
    }

    private class GetAppletStatusSubTask extends AccessSwsTask<GetAppletStatusResponseJson> {
        private static final String API_NAME = "getAppletStatus";

        GetAppletStatusSubTask(SwsClient swsClient) {
            super(0, swsClient, GetAppletStatusTask.this.mDataManager.getIsCalledFromSemcBackground());
            LogMgr.log(8, "000");
            Map<String, String> clientControlInfo = GetAppletStatusTask.this.mDataManager.getClientControlInfo();
            if (clientControlInfo == null) {
                setRetryInterval(null, FlavorConst.DEFAULT_RETRY_GET_APPLET_STATUS_INTERVAL);
            } else {
                setRetryInterval(clientControlInfo.get(GetAppletStatusTask.CLIENT_CONTROL_INFO_MASTER_KEY_RETRY_INTERVAL), FlavorConst.DEFAULT_RETRY_GET_APPLET_STATUS_INTERVAL);
            }
            setClientId(GetAppletStatusTask.this.mDataManager.getClientId());
            LogMgr.log(8, "999");
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/semc/sws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        public GetAppletStatusResponseJson convertResponse(String str) throws JSONException {
            return new GetAppletStatusResponseJson(str);
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        protected List<String> getValidResultCodeList() {
            return GetAppletStatusTask.VALID_RESULT_CODE_LIST_GET_APPLET_STATUS;
        }

        void onSuccess(GetAppletStatusResponseJson getAppletStatusResponseJson) {
            GetAppletStatusTask.this.setResult(getAppletStatusResponseJson);
            GetAppletStatusTask.this.onFinished(true, 0, "", "");
        }

        void onError(int i, String str) {
            GetAppletStatusTask.this.onFinished(false, i, "", str);
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        protected RequestJson createRequestJson() throws JSONException {
            LogMgr.log(8, "000");
            GetAppletStatusRequestJson getAppletStatusRequestJson = new GetAppletStatusRequestJson();
            getAppletStatusRequestJson.setRequestId(createRequestId());
            getAppletStatusRequestJson.setClientId(getClientId());
            getAppletStatusRequestJson.setOperationType(GetAppletStatusTask.this.mOperationType);
            getAppletStatusRequestJson.setOperationId(GetAppletStatusTask.this.mOperationId);
            if (GetAppletStatusTask.this.mDataManager != null && GetAppletStatusTask.this.mServiceId != null && GetAppletStatusTask.this.mSpAppId != null && GetAppletStatusTask.this.mSpAppletId != null) {
                getAppletStatusRequestJson.setPayload(GetAppletStatusTask.this.mServiceId, GetAppletStatusTask.this.mSpAppId, GetAppletStatusTask.this.mSpAppletId, GetAppletStatusTask.this.mDataManager.getSeid());
                LogMgr.log(8, "999");
                return getAppletStatusRequestJson;
            }
            LogMgr.log(2, "700 mDataManager or mServiceId or mSpAppId or mSpAppletId is null.");
            throw new JSONException(ObfuscatedMsgUtil.executionPoint());
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        protected String callSws(String str, String str2) throws HttpException {
            return this.mSwsClient.runWebApi(str2, this.mSwsClient.createUrl(str, GetAppletStatusTask.this.mDataManager.getSeid(), GetAppletStatusTask.this.mServiceId, "getAppletStatus"), SwsRequest.HTTP_METHOD_POST, true, false);
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask, com.felicanetworks.semc.sws.StoppableTaskBase, com.felicanetworks.semc.sws.TaskBase
        public void start() {
            LogMgr.log(7, "000");
            AccessSwsTask<GetAppletStatusResponseJson>.Result resultCommunicateWithSWS = communicateWithSWS();
            if (isStopped()) {
                LogMgr.log(1, "800 Already has stopped.");
                onError(901, ObfuscatedMsgUtil.executionPoint());
                return;
            }
            if (!resultCommunicateWithSWS.isSuccess) {
                onError(resultCommunicateWithSWS.errType, resultCommunicateWithSWS.errMsg);
                return;
            }
            try {
                if (((GetAppletStatusResponseJson) resultCommunicateWithSWS.response).isSuccess()) {
                    onSuccess((GetAppletStatusResponseJson) resultCommunicateWithSWS.response);
                } else {
                    String resultCode = ((GetAppletStatusResponseJson) resultCommunicateWithSWS.response).getResultCode();
                    LogMgr.log(2, "700 Unexpected result code " + resultCode);
                    Integer num = ERROR_CODE_MAP.get(resultCode);
                    onError(super.checkAndConvertNoSendingServerLogErrorTypeIfNeeded(GetAppletStatusTask.this.mDataManager.getIsCalledFromSemcBackground(), resultCode, num != null ? num.intValue() : 900), ObfuscatedMsgUtil.executionPoint());
                }
            } catch (JSONException e) {
                LogMgr.log(2, "701 Response data is invalid.");
                onError(SemClientCallbackConst.TYPE_HTTP_PROTOCOL_ERROR, ObfuscatedMsgUtil.executionPoint(e));
            }
            LogMgr.log(7, "999");
        }

        public AccessSwsTask<GetAppletStatusResponseJson>.Result communicateWithSWS() {
            LogMgr.log(8, "000");
            AccessSwsTask<Response>.Result result = (AccessSwsTask<Response>.Result) new AccessSwsTask.Result();
            int size = this.mRetryInterval.size();
            int i = 0;
            while (true) {
                if (i > size) {
                    break;
                }
                if (i == 0) {
                    super.start();
                } else {
                    super.retryStart(i, false);
                }
                if (isStopped()) {
                    result.isStopped = true;
                    return result;
                }
                result = getResult();
                if (!result.isRetry) {
                    LogMgr.log(8, "001 there was no retry request");
                    break;
                }
                if (size < 1) {
                    LogMgr.log(8, "002 there was no retry settings");
                    break;
                }
                int i2 = i + 1;
                if (i2 > size) {
                    LogMgr.log(8, "003 retry count was over");
                    break;
                }
                SystemClock.sleep(this.mRetryInterval.get(i).intValue());
                LogMgr.log(8, "004 (retry count / max retry count)=(" + i2 + DomExceptionUtils.SEPARATOR + size + ")");
                i = i2;
            }
            LogMgr.log(8, "999");
            return result;
        }

        @Override // com.felicanetworks.semc.sws.StoppableTaskBase
        public synchronized void stop() {
            LogMgr.log(7, "000");
            super.stop();
            LogMgr.log(7, "999");
        }
    }
}
