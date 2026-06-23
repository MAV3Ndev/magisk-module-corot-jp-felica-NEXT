package com.felicanetworks.semc.sws;

import android.os.SystemClock;
import androidx.credentials.exceptions.publickeycredential.DomExceptionUtils;
import com.felicanetworks.semc.DataManager;
import com.felicanetworks.semc.FlavorConst;
import com.felicanetworks.semc.SemClientCallbackConst;
import com.felicanetworks.semc.http.HttpException;
import com.felicanetworks.semc.sws.AccessSwsTask;
import com.felicanetworks.semc.sws.AsyncTaskBase;
import com.felicanetworks.semc.sws.json.GetProcessStatusRequestJson;
import com.felicanetworks.semc.sws.json.GetProcessStatusResponseJson;
import com.felicanetworks.semc.sws.json.RequestJson;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class GetProcessStatusTask extends AsyncParentTaskBase<GetProcessStatusResponseJson> {
    private static final String CLIENT_CONTROL_INFO_MASTER_KEY_RETRY_INTERVAL = "retry.getProcessStatus";
    private static final List<String> VALID_RESULT_CODE_LIST_GET_PROCESS_STATUS;
    private final DataManager mDataManager;
    private final String mOperationId;
    private final String mOperationType;
    private final String mProcessId;
    private GetProcessStatusResponseJson mResponse;
    private final String mServiceId;
    private final SwsClient mSwsClient;

    @Override // com.felicanetworks.semc.sws.AsyncParentTaskBase, com.felicanetworks.semc.sws.StoppableTaskBase
    public /* bridge */ /* synthetic */ void stop() {
        super.stop();
    }

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_PROCESS_STATUS = arrayList;
        arrayList.add("0000");
        arrayList.add("1000");
        arrayList.add("2000");
        arrayList.add("3000");
        arrayList.add("4000");
        arrayList.add("9000");
        arrayList.add("9001");
        arrayList.add("9005");
    }

    GetProcessStatusTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, String str, String str2, SwsClient swsClient, DataManager dataManager, String str3, String str4) {
        super(i, executorService, listener);
        this.mServiceId = str;
        this.mProcessId = str2;
        this.mSwsClient = swsClient;
        this.mDataManager = dataManager;
        this.mOperationType = str3;
        this.mOperationId = str4;
    }

    public String getProcessStatus() {
        LogMgr.log(8, "000");
        String processStatus = null;
        try {
            GetProcessStatusResponseJson getProcessStatusResponseJson = this.mResponse;
            if (getProcessStatusResponseJson != null) {
                processStatus = getProcessStatusResponseJson.getProcessStatus();
            }
        } catch (JSONException unused) {
            LogMgr.log(2, "700 Failed to get Process Status");
        }
        LogMgr.log(8, "999 ret=" + processStatus);
        return processStatus;
    }

    public String getProcessResultCode() throws JSONException {
        LogMgr.log(8, "000");
        try {
            GetProcessStatusResponseJson getProcessStatusResponseJson = this.mResponse;
            String processResultCode = getProcessStatusResponseJson != null ? getProcessStatusResponseJson.getProcessResultCode() : null;
            LogMgr.log(8, "999 ret=" + processResultCode);
            return processResultCode;
        } catch (JSONException e) {
            LogMgr.log(2, "700 Failed to get Process Result Code");
            throw e;
        }
    }

    public String getProcessResultDetailCode() throws JSONException {
        LogMgr.log(8, "000");
        try {
            GetProcessStatusResponseJson getProcessStatusResponseJson = this.mResponse;
            String processResultDetailCode = getProcessStatusResponseJson != null ? getProcessStatusResponseJson.getProcessResultDetailCode() : null;
            LogMgr.log(8, "999 ret=" + processResultDetailCode);
            return processResultDetailCode;
        } catch (JSONException e) {
            LogMgr.log(2, "700 Failed to get Process Result Detail Code");
            throw e;
        }
    }

    public String getProcessResultMessage() throws JSONException {
        LogMgr.log(8, "000");
        try {
            GetProcessStatusResponseJson getProcessStatusResponseJson = this.mResponse;
            String processResultMessage = getProcessStatusResponseJson != null ? getProcessStatusResponseJson.getProcessResultMessage() : null;
            LogMgr.log(8, "999 ret=" + processResultMessage);
            return processResultMessage;
        } catch (JSONException e) {
            LogMgr.log(2, "700 Failed to get Process Result Message");
            throw e;
        }
    }

    public JSONArray getSdKeyDerivationDataList() throws JSONException {
        LogMgr.log(8, "000");
        try {
            GetProcessStatusResponseJson getProcessStatusResponseJson = this.mResponse;
            JSONArray sdKeyDerivationDataList = getProcessStatusResponseJson != null ? getProcessStatusResponseJson.getSdKeyDerivationDataList() : null;
            LogMgr.log(8, "999 ret=" + sdKeyDerivationDataList);
            return sdKeyDerivationDataList;
        } catch (JSONException e) {
            LogMgr.log(2, "700 Failed to get Sd Key Derivation Data List");
            throw e;
        }
    }

    public JSONObject getAppletStatus() throws JSONException {
        LogMgr.log(8, "000");
        try {
            GetProcessStatusResponseJson getProcessStatusResponseJson = this.mResponse;
            JSONObject appletStatus = getProcessStatusResponseJson != null ? getProcessStatusResponseJson.getAppletStatus() : null;
            LogMgr.log(8, "999 ret=" + appletStatus);
            return appletStatus;
        } catch (JSONException e) {
            LogMgr.log(2, "700 Failed to get Applet Status");
            throw e;
        }
    }

    @Override // com.felicanetworks.semc.sws.AsyncTaskBase
    protected void run() {
        LogMgr.log(8, "000");
        GetProcessStatusSubTask getProcessStatusSubTask = new GetProcessStatusSubTask(this.mSwsClient);
        setStoppableSubTask(getProcessStatusSubTask);
        getProcessStatusSubTask.start();
        LogMgr.log(8, "999");
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.semc.sws.AsyncTaskBase
    public void setResult(GetProcessStatusResponseJson getProcessStatusResponseJson) {
        this.mResponse = getProcessStatusResponseJson;
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.felicanetworks.semc.sws.AsyncParentTaskBase
    public synchronized GetProcessStatusResponseJson getResult() {
        return this.mResponse;
    }

    @Override // com.felicanetworks.semc.sws.AsyncTaskBase
    protected void onFinished(boolean z, int i, String str, String str2) {
        LogMgr.log(8, "000");
        super.onFinished(z, i, str, str2);
        LogMgr.log(8, "999");
    }

    private class GetProcessStatusSubTask extends AccessSwsTask<GetProcessStatusResponseJson> {
        private static final String API_NAME = "getProcessStatus";

        GetProcessStatusSubTask(SwsClient swsClient) {
            super(0, swsClient, GetProcessStatusTask.this.mDataManager.getIsCalledFromSemcBackground());
            Map<String, String> clientControlInfo = GetProcessStatusTask.this.mDataManager.getClientControlInfo();
            if (clientControlInfo == null) {
                setRetryInterval(null, FlavorConst.DEFAULT_RETRY_GET_PROCESS_STATUS_INTERVAL);
            } else {
                setRetryInterval(clientControlInfo.get(GetProcessStatusTask.CLIENT_CONTROL_INFO_MASTER_KEY_RETRY_INTERVAL), FlavorConst.DEFAULT_RETRY_GET_PROCESS_STATUS_INTERVAL);
            }
            setClientId(GetProcessStatusTask.this.mDataManager.getClientId());
            LogMgr.log(8, "999");
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/semc/sws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        public GetProcessStatusResponseJson convertResponse(String str) throws JSONException {
            return new GetProcessStatusResponseJson(str);
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        protected List<String> getValidResultCodeList() {
            return GetProcessStatusTask.VALID_RESULT_CODE_LIST_GET_PROCESS_STATUS;
        }

        void onSuccess(GetProcessStatusResponseJson getProcessStatusResponseJson) {
            GetProcessStatusTask.this.setResult(getProcessStatusResponseJson);
            GetProcessStatusTask.this.onFinished(true, 0, "", "");
        }

        void onError(int i, String str) {
            GetProcessStatusTask.this.onFinished(false, i, "", str);
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        protected RequestJson createRequestJson() throws JSONException {
            LogMgr.log(8, "000");
            GetProcessStatusRequestJson getProcessStatusRequestJson = new GetProcessStatusRequestJson();
            getProcessStatusRequestJson.setRequestId(createRequestId());
            getProcessStatusRequestJson.setClientId(getClientId());
            getProcessStatusRequestJson.setOperationType(GetProcessStatusTask.this.mOperationType);
            getProcessStatusRequestJson.setOperationId(GetProcessStatusTask.this.mOperationId);
            if (GetProcessStatusTask.this.mProcessId != null) {
                getProcessStatusRequestJson.setPayload(GetProcessStatusTask.this.mProcessId);
                LogMgr.log(8, "999");
                return getProcessStatusRequestJson;
            }
            LogMgr.log(2, "700 mProcessId is null.");
            throw new JSONException(ObfuscatedMsgUtil.executionPoint());
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        protected String callSws(String str, String str2) throws HttpException {
            return this.mSwsClient.runWebApi(str2, this.mSwsClient.createUrl(str, GetProcessStatusTask.this.mDataManager.getSeid(), GetProcessStatusTask.this.mServiceId, API_NAME), SwsRequest.HTTP_METHOD_POST, true, false);
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask, com.felicanetworks.semc.sws.StoppableTaskBase, com.felicanetworks.semc.sws.TaskBase
        public void start() {
            LogMgr.log(7, "000");
            AccessSwsTask<GetProcessStatusResponseJson>.Result resultCommunicateWithSWS = communicateWithSWS();
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
                if (((GetProcessStatusResponseJson) resultCommunicateWithSWS.response).isSuccess()) {
                    onSuccess((GetProcessStatusResponseJson) resultCommunicateWithSWS.response);
                } else {
                    String resultCode = ((GetProcessStatusResponseJson) resultCommunicateWithSWS.response).getResultCode();
                    LogMgr.log(2, "700 Unexpected result code " + resultCode);
                    Integer num = ERROR_CODE_MAP.get(resultCode);
                    onError(super.checkAndConvertNoSendingServerLogErrorTypeIfNeeded(GetProcessStatusTask.this.mDataManager.getIsCalledFromSemcBackground(), resultCode, num != null ? num.intValue() : 900), ObfuscatedMsgUtil.executionPoint());
                }
            } catch (JSONException e) {
                LogMgr.log(2, "701 Response data is invalid.");
                onError(SemClientCallbackConst.TYPE_HTTP_PROTOCOL_ERROR, ObfuscatedMsgUtil.executionPoint(e));
            }
            LogMgr.log(7, "999");
        }

        public AccessSwsTask<GetProcessStatusResponseJson>.Result communicateWithSWS() {
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
