package com.felicanetworks.semc.sws;

import android.os.SystemClock;
import androidx.credentials.exceptions.publickeycredential.DomExceptionUtils;
import com.felicanetworks.semc.DataManager;
import com.felicanetworks.semc.FlavorConst;
import com.felicanetworks.semc.SemClientCallbackConst;
import com.felicanetworks.semc.http.HttpException;
import com.felicanetworks.semc.sws.AccessSwsTask;
import com.felicanetworks.semc.sws.AsyncTaskBase;
import com.felicanetworks.semc.sws.json.NotifyClientEventRequestJson;
import com.felicanetworks.semc.sws.json.NotifyClientEventResponseJson;
import com.felicanetworks.semc.sws.json.RequestJson;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class NotifyClientEventTask extends AsyncParentTaskBase<NotifyClientEventResponseJson> {
    private static final String CLIENT_CONTROL_INFO_MASTER_KEY_RETRY_INTERVAL = "retry.notifyClientEvent";
    private static final List<String> VALID_RESULT_CODE_LIST_NOTIFY_CLIENT_EVENT;
    private final DataManager mDataManager;
    private final String mEventType;
    private NotifyClientEventResponseJson mResponse;
    private final String mSpAppId;
    private final SwsClient mSwsClient;

    @Override // com.felicanetworks.semc.sws.AsyncParentTaskBase, com.felicanetworks.semc.sws.StoppableTaskBase
    public /* bridge */ /* synthetic */ void stop() {
        super.stop();
    }

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_NOTIFY_CLIENT_EVENT = arrayList;
        arrayList.add("0000");
        arrayList.add("1000");
        arrayList.add("2000");
        arrayList.add("3000");
        arrayList.add("4000");
        arrayList.add("9000");
        arrayList.add("9001");
        arrayList.add("9005");
    }

    NotifyClientEventTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, String str, SwsClient swsClient, DataManager dataManager) {
        super(i, executorService, listener);
        this.mEventType = str;
        this.mSpAppId = dataManager.getSpAppIdForNotify();
        this.mSwsClient = swsClient;
        this.mDataManager = dataManager;
    }

    @Override // com.felicanetworks.semc.sws.AsyncTaskBase
    protected void run() {
        LogMgr.log(8, "000");
        NotifyClientEventSubTask notifyClientEventSubTask = new NotifyClientEventSubTask(this.mSwsClient);
        setStoppableSubTask(notifyClientEventSubTask);
        notifyClientEventSubTask.start();
        LogMgr.log(8, "999");
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.semc.sws.AsyncTaskBase
    public void setResult(NotifyClientEventResponseJson notifyClientEventResponseJson) {
        this.mResponse = notifyClientEventResponseJson;
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.felicanetworks.semc.sws.AsyncParentTaskBase
    public synchronized NotifyClientEventResponseJson getResult() {
        return this.mResponse;
    }

    @Override // com.felicanetworks.semc.sws.AsyncTaskBase
    protected void onFinished(boolean z, int i, String str, String str2) {
        LogMgr.log(8, "000");
        super.onFinished(z, i, str, str2);
        LogMgr.log(8, "999");
    }

    private class NotifyClientEventSubTask extends AccessSwsTask<NotifyClientEventResponseJson> {
        private static final String API_NAME = "notifyClientEvent";

        NotifyClientEventSubTask(SwsClient swsClient) {
            super(0, swsClient, NotifyClientEventTask.this.mDataManager.getIsCalledFromSemcBackground());
            LogMgr.log(8, "000");
            Map<String, String> clientControlInfo = NotifyClientEventTask.this.mDataManager.getClientControlInfo();
            if (clientControlInfo == null) {
                setRetryInterval(null, FlavorConst.DEFAULT_RETRY_NOTIFY_CLIENT_EVENT_INTERVAL);
            } else {
                setRetryInterval(clientControlInfo.get(NotifyClientEventTask.CLIENT_CONTROL_INFO_MASTER_KEY_RETRY_INTERVAL), FlavorConst.DEFAULT_RETRY_NOTIFY_CLIENT_EVENT_INTERVAL);
            }
            setClientId(NotifyClientEventTask.this.mDataManager.getClientId());
            LogMgr.log(8, "999");
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/semc/sws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        public NotifyClientEventResponseJson convertResponse(String str) throws JSONException {
            return new NotifyClientEventResponseJson(str);
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        protected List<String> getValidResultCodeList() {
            return NotifyClientEventTask.VALID_RESULT_CODE_LIST_NOTIFY_CLIENT_EVENT;
        }

        void onSuccess(NotifyClientEventResponseJson notifyClientEventResponseJson) {
            NotifyClientEventTask.this.setResult(notifyClientEventResponseJson);
            NotifyClientEventTask.this.onFinished(true, 0, "", "");
        }

        void onError(int i, String str) {
            NotifyClientEventTask.this.onFinished(false, i, "", str);
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        protected RequestJson createRequestJson() throws JSONException {
            LogMgr.log(8, "000");
            NotifyClientEventRequestJson notifyClientEventRequestJson = new NotifyClientEventRequestJson();
            notifyClientEventRequestJson.setRequestId(createRequestId());
            notifyClientEventRequestJson.setClientId(getClientId());
            if (NotifyClientEventTask.this.mDataManager != null && NotifyClientEventTask.this.mEventType != null && NotifyClientEventTask.this.mSpAppId != null) {
                notifyClientEventRequestJson.setPayload(NotifyClientEventTask.this.mDataManager.getSeid(), NotifyClientEventTask.this.mEventType, NotifyClientEventTask.this.mSpAppId);
                LogMgr.log(8, "999");
                return notifyClientEventRequestJson;
            }
            LogMgr.log(2, "700 mDataManager or mEventType or mSpAppId is null.");
            throw new JSONException(ObfuscatedMsgUtil.executionPoint());
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        protected String callSws(String str, String str2) throws HttpException {
            return this.mSwsClient.runWebApi(str2, this.mSwsClient.createUrl(str, NotifyClientEventTask.this.mDataManager.getSeid(), SwsClient.URL_SERVICE_ID, "notifyClientEvent"), SwsRequest.HTTP_METHOD_POST, true, false);
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask, com.felicanetworks.semc.sws.StoppableTaskBase, com.felicanetworks.semc.sws.TaskBase
        public void start() {
            LogMgr.log(7, "000");
            AccessSwsTask<NotifyClientEventResponseJson>.Result resultCommunicateWithSWS = communicateWithSWS();
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
                if (((NotifyClientEventResponseJson) resultCommunicateWithSWS.response).isSuccess()) {
                    onSuccess((NotifyClientEventResponseJson) resultCommunicateWithSWS.response);
                } else {
                    String resultCode = ((NotifyClientEventResponseJson) resultCommunicateWithSWS.response).getResultCode();
                    LogMgr.log(2, "700 Unexpected result code " + resultCode);
                    Integer num = ERROR_CODE_MAP.get(resultCode);
                    onError(super.checkAndConvertNoSendingServerLogErrorTypeIfNeeded(NotifyClientEventTask.this.mDataManager.getIsCalledFromSemcBackground(), resultCode, num != null ? num.intValue() : 900), ObfuscatedMsgUtil.executionPoint());
                }
            } catch (JSONException e) {
                LogMgr.log(2, "701 Response data is invalid.");
                onError(SemClientCallbackConst.TYPE_HTTP_PROTOCOL_ERROR, ObfuscatedMsgUtil.executionPoint(e));
            }
            LogMgr.log(7, "999");
        }

        public AccessSwsTask<NotifyClientEventResponseJson>.Result communicateWithSWS() {
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
