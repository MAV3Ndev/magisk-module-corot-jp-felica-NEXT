package com.felicanetworks.semc.sws;

import android.os.SystemClock;
import androidx.credentials.exceptions.publickeycredential.DomExceptionUtils;
import com.felicanetworks.semc.DataManager;
import com.felicanetworks.semc.FlavorConst;
import com.felicanetworks.semc.SemClientCallbackConst;
import com.felicanetworks.semc.http.HttpException;
import com.felicanetworks.semc.sws.AccessSwsTask;
import com.felicanetworks.semc.sws.AsyncTaskBase;
import com.felicanetworks.semc.sws.json.GetClientConfigurationResponseJson;
import com.felicanetworks.semc.sws.json.RequestJson;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class GetClientConfigurationTask extends AsyncParentTaskBase<GetClientConfigurationResponseJson> {
    private static final String CLIENT_CONTROL_INFO_MASTER_KEY_RETRY_INTERVAL = "retry.getClientConfiguration";
    private static final String SEID_FOR_GET_CLIENT_CONFIGURATION = "FFFFFFFFFFFFFFFF";
    private static final List<String> VALID_RESULT_CODE_LIST_GET_CLIENT_CONFIGURATION;
    private final DataManager mDataManager;
    private GetClientConfigurationResponseJson mResponse;
    private final SwsClient mSwsClient;

    @Override // com.felicanetworks.semc.sws.AsyncParentTaskBase, com.felicanetworks.semc.sws.StoppableTaskBase
    public /* bridge */ /* synthetic */ void stop() {
        super.stop();
    }

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_CLIENT_CONFIGURATION = arrayList;
        arrayList.add("0000");
        arrayList.add("1000");
        arrayList.add("2000");
        arrayList.add("3000");
        arrayList.add("4000");
        arrayList.add("9000");
        arrayList.add("9001");
        arrayList.add("9005");
    }

    GetClientConfigurationTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, DataManager dataManager, SwsClient swsClient) {
        super(i, executorService, listener);
        this.mDataManager = dataManager;
        this.mSwsClient = swsClient;
    }

    String getClientConfiguration() {
        LogMgr.log(8, "000");
        GetClientConfigurationResponseJson getClientConfigurationResponseJson = this.mResponse;
        String clientConfiguration = getClientConfigurationResponseJson != null ? getClientConfigurationResponseJson.getClientConfiguration() : null;
        LogMgr.log(8, "999 ret=" + clientConfiguration);
        return clientConfiguration;
    }

    @Override // com.felicanetworks.semc.sws.AsyncTaskBase
    protected void run() {
        LogMgr.log(8, "000");
        GetClientConfigurationSubTask getClientConfigurationSubTask = new GetClientConfigurationSubTask(this.mSwsClient);
        setStoppableSubTask(getClientConfigurationSubTask);
        getClientConfigurationSubTask.start();
        LogMgr.log(8, "999");
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.semc.sws.AsyncTaskBase
    public void setResult(GetClientConfigurationResponseJson getClientConfigurationResponseJson) {
        LogMgr.log(8, "000");
        this.mResponse = getClientConfigurationResponseJson;
        LogMgr.log(8, "999");
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.felicanetworks.semc.sws.AsyncParentTaskBase
    public synchronized GetClientConfigurationResponseJson getResult() {
        return this.mResponse;
    }

    @Override // com.felicanetworks.semc.sws.AsyncTaskBase
    protected void onFinished(boolean z, int i, String str, String str2) {
        LogMgr.log(8, "000");
        super.onFinished(z, i, str, str2);
        LogMgr.log(8, "999");
    }

    private class GetClientConfigurationSubTask extends AccessSwsTask<GetClientConfigurationResponseJson> {
        private static final String API_NAME = "getClientConfiguration";

        GetClientConfigurationSubTask(SwsClient swsClient) {
            super(0, swsClient, GetClientConfigurationTask.this.mDataManager.getIsCalledFromSemcBackground());
            LogMgr.log(8, "000");
            Map<String, String> clientControlInfo = GetClientConfigurationTask.this.mDataManager.getClientControlInfo();
            if (clientControlInfo == null) {
                setRetryInterval(null, FlavorConst.DEFAULT_RETRY_GET_CLIENT_CONFIGURATION_INTERVAL);
            } else {
                setRetryInterval(clientControlInfo.get(GetClientConfigurationTask.CLIENT_CONTROL_INFO_MASTER_KEY_RETRY_INTERVAL), FlavorConst.DEFAULT_RETRY_GET_CLIENT_CONFIGURATION_INTERVAL);
            }
            LogMgr.log(8, "999");
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/semc/sws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        public GetClientConfigurationResponseJson convertResponse(String str) throws JSONException {
            return new GetClientConfigurationResponseJson(str);
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        protected List<String> getValidResultCodeList() {
            return GetClientConfigurationTask.VALID_RESULT_CODE_LIST_GET_CLIENT_CONFIGURATION;
        }

        void onSuccess(GetClientConfigurationResponseJson getClientConfigurationResponseJson) {
            GetClientConfigurationTask.this.setResult(getClientConfigurationResponseJson);
            GetClientConfigurationTask.this.onFinished(true, 0, "", "");
        }

        void onError(int i, String str) {
            GetClientConfigurationTask.this.onFinished(false, i, "", str);
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        protected RequestJson createRequestJson() {
            LogMgr.log(8, "000");
            LogMgr.log(8, "999");
            return null;
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        protected String callSws(String str, String str2) throws HttpException {
            return this.mSwsClient.runWebApi(str2, this.mSwsClient.createUrl(str, "FFFFFFFFFFFFFFFF", SwsClient.URL_SERVICE_ID, API_NAME), SwsRequest.HTTP_METHOD_GET, false, false);
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask, com.felicanetworks.semc.sws.StoppableTaskBase, com.felicanetworks.semc.sws.TaskBase
        public void start() {
            LogMgr.log(7, "000");
            AccessSwsTask<GetClientConfigurationResponseJson>.Result resultCommunicateWithSWS = communicateWithSWS();
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
                if (((GetClientConfigurationResponseJson) resultCommunicateWithSWS.response).isSuccess()) {
                    onSuccess((GetClientConfigurationResponseJson) resultCommunicateWithSWS.response);
                } else {
                    String resultCode = ((GetClientConfigurationResponseJson) resultCommunicateWithSWS.response).getResultCode();
                    LogMgr.log(2, "700 Unexpected result code " + resultCode);
                    Integer num = ERROR_CODE_MAP.get(resultCode);
                    onError(super.checkAndConvertNoSendingServerLogErrorTypeIfNeeded(GetClientConfigurationTask.this.mDataManager.getIsCalledFromSemcBackground(), resultCode, num != null ? num.intValue() : 900), ObfuscatedMsgUtil.executionPoint());
                }
            } catch (JSONException e) {
                LogMgr.log(2, "701 Response data is invalid.");
                onError(SemClientCallbackConst.TYPE_HTTP_PROTOCOL_ERROR, ObfuscatedMsgUtil.executionPoint(e));
            }
            LogMgr.log(7, "999");
        }

        @Override // com.felicanetworks.semc.sws.StoppableTaskBase
        public synchronized void stop() {
            LogMgr.log(7, "000");
            super.stop();
            LogMgr.log(7, "999");
        }

        public AccessSwsTask<GetClientConfigurationResponseJson>.Result communicateWithSWS() {
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
    }
}
