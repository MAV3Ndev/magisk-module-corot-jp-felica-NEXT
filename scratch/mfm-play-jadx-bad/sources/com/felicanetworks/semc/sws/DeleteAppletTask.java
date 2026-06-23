package com.felicanetworks.semc.sws;

import android.content.Context;
import android.os.SystemClock;
import androidx.credentials.exceptions.publickeycredential.DomExceptionUtils;
import com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityAgent;
import com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityAgentErrorType;
import com.felicanetworks.semc.DataManager;
import com.felicanetworks.semc.FlavorConst;
import com.felicanetworks.semc.SemClientAdapter;
import com.felicanetworks.semc.SemClientCallbackConst;
import com.felicanetworks.semc.SemClientException;
import com.felicanetworks.semc.http.HttpException;
import com.felicanetworks.semc.sws.AccessSwsTask;
import com.felicanetworks.semc.sws.AsyncTaskBase;
import com.felicanetworks.semc.sws.json.DeleteAppletRequestJson;
import com.felicanetworks.semc.sws.json.DeleteAppletResponseJson;
import com.felicanetworks.semc.sws.json.RequestJson;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class DeleteAppletTask extends AsyncParentTaskBase<DeleteAppletResponseJson> {
    private static final String CLIENT_CONTROL_INFO_MASTER_KEY_RETRY_INTERVAL = "retry.deleteApplet";
    private static final Map<PlayIntegrityAgentErrorType.Error, Integer> CONVERT_PIA_ERROR_MAP;
    private static final String PROCESSING_MODE = "PULL";
    private static final List<String> VALID_RESULT_CODE_LIST_DELETE_APPLET;
    private final DataManager mDataManager;
    private int mGetPlayIntegrityTokenErrType;
    private CountDownLatch mGetPlayIntegrityTokenLatch;
    private boolean mIsGetPlayIntegrityTokenSuccess;
    private final String mOperationId;
    private final String mOperationType;
    private DeleteAppletResponseJson mResponse;
    private final String mServiceId;
    private final String mSpAppId;
    private final String mSpAppletId;
    private final String mSpAppletVersion;
    private final SwsClient mSwsClient;

    @Override // com.felicanetworks.semc.sws.AsyncParentTaskBase, com.felicanetworks.semc.sws.StoppableTaskBase
    public /* bridge */ /* synthetic */ void stop() {
        super.stop();
    }

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_DELETE_APPLET = arrayList;
        arrayList.add("0000");
        arrayList.add("1000");
        arrayList.add("2000");
        arrayList.add("3000");
        arrayList.add("4000");
        arrayList.add("9000");
        arrayList.add("9001");
        arrayList.add("9005");
        HashMap map = new HashMap();
        CONVERT_PIA_ERROR_MAP = map;
        map.put(PlayIntegrityAgentErrorType.Error.TYPE_NETWORK_UNAVAILABLE, 300);
        map.put(PlayIntegrityAgentErrorType.Error.TYPE_PLAY_INTEGRITY_NONRECOVERABLE_FAILED, 900);
        map.put(PlayIntegrityAgentErrorType.Error.TYPE_PLAY_INTEGRITY_RECOVERABLE_FAILED, 300);
        map.put(PlayIntegrityAgentErrorType.Error.TYPE_PLAY_SERVICE_ERROR, 205);
        map.put(PlayIntegrityAgentErrorType.Error.TYPE_PLAY_STORE_ERROR, 205);
        map.put(PlayIntegrityAgentErrorType.Error.TYPE_UNKNOWN_ERROR, 900);
    }

    DeleteAppletTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, String str, String str2, String str3, String str4, SwsClient swsClient, DataManager dataManager, String str5, String str6) {
        super(i, executorService, listener);
        this.mGetPlayIntegrityTokenLatch = null;
        this.mIsGetPlayIntegrityTokenSuccess = false;
        this.mGetPlayIntegrityTokenErrType = 0;
        this.mServiceId = str;
        this.mSpAppId = str2;
        this.mSpAppletId = str3;
        this.mSpAppletVersion = str4;
        this.mSwsClient = swsClient;
        this.mDataManager = dataManager;
        this.mOperationType = str5;
        this.mOperationId = str6;
    }

    public String getLinkageData() {
        LogMgr.log(8, "000");
        String linkageData = null;
        try {
            DeleteAppletResponseJson deleteAppletResponseJson = this.mResponse;
            if (deleteAppletResponseJson != null) {
                linkageData = deleteAppletResponseJson.getLinkageData();
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
            DeleteAppletResponseJson deleteAppletResponseJson = this.mResponse;
            if (deleteAppletResponseJson != null) {
                processId = deleteAppletResponseJson.getProcessId();
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
        DeleteAppletSubTask deleteAppletSubTask = new DeleteAppletSubTask(this.mSwsClient);
        setStoppableSubTask(deleteAppletSubTask);
        deleteAppletSubTask.start();
        LogMgr.log(8, "999");
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.semc.sws.AsyncTaskBase
    public void setResult(DeleteAppletResponseJson deleteAppletResponseJson) {
        this.mResponse = deleteAppletResponseJson;
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.felicanetworks.semc.sws.AsyncParentTaskBase
    public synchronized DeleteAppletResponseJson getResult() {
        return this.mResponse;
    }

    @Override // com.felicanetworks.semc.sws.AsyncTaskBase
    protected void onFinished(boolean z, int i, String str, String str2) {
        LogMgr.log(8, "000");
        super.onFinished(z, i, str, str2);
        LogMgr.log(8, "999");
    }

    private class DeleteAppletSubTask extends AccessSwsTask<DeleteAppletResponseJson> {
        private static final String API_NAME = "deleteApplet";

        DeleteAppletSubTask(SwsClient swsClient) {
            super(0, swsClient, DeleteAppletTask.this.mDataManager.getIsCalledFromSemcBackground());
            LogMgr.log(8, "000");
            Map<String, String> clientControlInfo = DeleteAppletTask.this.mDataManager.getClientControlInfo();
            if (clientControlInfo == null) {
                setRetryInterval(null, FlavorConst.DEFAULT_RETRY_DELETE_APPLET_INTERVAL);
            } else {
                setRetryInterval(clientControlInfo.get(DeleteAppletTask.CLIENT_CONTROL_INFO_MASTER_KEY_RETRY_INTERVAL), FlavorConst.DEFAULT_RETRY_DELETE_APPLET_INTERVAL);
            }
            setClientId(DeleteAppletTask.this.mDataManager.getClientId());
            LogMgr.log(8, "999");
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/semc/sws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        public DeleteAppletResponseJson convertResponse(String str) throws JSONException {
            return new DeleteAppletResponseJson(str);
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        protected List<String> getValidResultCodeList() {
            return DeleteAppletTask.VALID_RESULT_CODE_LIST_DELETE_APPLET;
        }

        void onSuccess(DeleteAppletResponseJson deleteAppletResponseJson) {
            DeleteAppletTask.this.setResult(deleteAppletResponseJson);
            DeleteAppletTask.this.onFinished(true, 0, "", "");
        }

        void onError(int i, String str) {
            DeleteAppletTask.this.onFinished(false, i, "", str);
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        protected RequestJson createRequestJson() throws JSONException {
            LogMgr.log(8, "000");
            DeleteAppletRequestJson deleteAppletRequestJson = new DeleteAppletRequestJson();
            deleteAppletRequestJson.setRequestId(createRequestId());
            deleteAppletRequestJson.setClientId(getClientId());
            deleteAppletRequestJson.setOperationType(DeleteAppletTask.this.mOperationType);
            deleteAppletRequestJson.setOperationId(DeleteAppletTask.this.mOperationId);
            if (DeleteAppletTask.this.mDataManager == null || DeleteAppletTask.this.mServiceId == null || DeleteAppletTask.this.mSpAppletId == null) {
                if (DeleteAppletTask.this.mDataManager != null) {
                    if (DeleteAppletTask.this.mServiceId == null) {
                        LogMgr.log(2, "701 mServiceId is null.");
                        throw new JSONException(ObfuscatedMsgUtil.executionPoint());
                    }
                    LogMgr.log(2, "702 mSpAppletId is null.");
                    throw new JSONException(ObfuscatedMsgUtil.executionPoint());
                }
                LogMgr.log(2, "700 mDataManager is null.");
                throw new JSONException(ObfuscatedMsgUtil.executionPoint());
            }
            deleteAppletRequestJson.setPayload(DeleteAppletTask.this.mServiceId, DeleteAppletTask.this.mSpAppId, DeleteAppletTask.this.mDataManager.getSeid(), DeleteAppletTask.this.mSpAppletId, DeleteAppletTask.this.mSpAppletVersion, DeleteAppletTask.PROCESSING_MODE);
            LogMgr.log(8, "999");
            return deleteAppletRequestJson;
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        protected String callSws(String str, String str2) throws HttpException, SemClientException {
            LogMgr.log(8, "000");
            String strCreateUrl = this.mSwsClient.createUrl(str, DeleteAppletTask.this.mDataManager.getSeid(), DeleteAppletTask.this.mServiceId, "deleteApplet");
            DeleteAppletTask.this.mGetPlayIntegrityTokenLatch = new CountDownLatch(1);
            try {
                Context applicationContext = SemClientAdapter.getInstance().getApplicationContext();
                String str3 = DeleteAppletTask.this.mDataManager.getIntegrityVerificationUniqueValue() + str2;
                DeleteAppletTask.this.mIsGetPlayIntegrityTokenSuccess = false;
                LogMgr.log(9, "001");
                if (isStopped()) {
                    LogMgr.log(1, "800 Already has stopped.");
                    throw new SemClientException(901, ObfuscatedMsgUtil.executionPoint());
                }
                PlayIntegrityAgent.getPlayIntegrityToken(applicationContext, str3, new PlayIntegrityAgent.PlayIntegrityTokenEventListener() { // from class: com.felicanetworks.semc.sws.DeleteAppletTask.DeleteAppletSubTask.1
                    @Override // com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityAgent.PlayIntegrityTokenEventListener
                    public void onSuccess(String str4) {
                        LogMgr.log(8, "000");
                        DeleteAppletTask.this.mDataManager.setPlayIntegrityToken(str4);
                        DeleteAppletTask.this.mIsGetPlayIntegrityTokenSuccess = true;
                        DeleteAppletTask.this.mGetPlayIntegrityTokenLatch.countDown();
                        LogMgr.log(8, "999");
                    }

                    @Override // com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityAgent.PlayIntegrityTokenEventListener
                    public void onError(PlayIntegrityAgentErrorType.Error error, String str4) {
                        LogMgr.log(8, "000");
                        DeleteAppletTask.this.mIsGetPlayIntegrityTokenSuccess = false;
                        DeleteAppletTask.this.mGetPlayIntegrityTokenErrType = DeleteAppletSubTask.this.convertPiaErrorType(error);
                        LogMgr.log(8, "001 message = " + str4);
                        DeleteAppletTask.this.mGetPlayIntegrityTokenLatch.countDown();
                        LogMgr.log(8, "999");
                    }
                });
                try {
                    DeleteAppletTask.this.mGetPlayIntegrityTokenLatch.await();
                    if (!DeleteAppletTask.this.mIsGetPlayIntegrityTokenSuccess) {
                        LogMgr.log(1, "804 Failed to get play Integrity Token.");
                        throw new SemClientException(DeleteAppletTask.this.mGetPlayIntegrityTokenErrType, ObfuscatedMsgUtil.executionPoint());
                    }
                    LogMgr.log(8, "999");
                    return this.mSwsClient.runWebApi(str2, strCreateUrl, SwsRequest.HTTP_METHOD_POST, true, false);
                } catch (InterruptedException e) {
                    LogMgr.log(1, "803 InterruptedException");
                    throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
                }
            } catch (IllegalArgumentException e2) {
                LogMgr.log(1, "801 Parameter is Invalid.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e2));
            } catch (Exception e3) {
                LogMgr.log(1, "802 Unknown error occurred.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e3));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int convertPiaErrorType(PlayIntegrityAgentErrorType.Error error) {
            LogMgr.log(8, "000");
            Integer num = (Integer) DeleteAppletTask.CONVERT_PIA_ERROR_MAP.get(error);
            if (num == null) {
                num = 900;
            }
            LogMgr.log(8, "999 type = " + num);
            return num.intValue();
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask, com.felicanetworks.semc.sws.StoppableTaskBase, com.felicanetworks.semc.sws.TaskBase
        public void start() {
            LogMgr.log(7, "000");
            AccessSwsTask<DeleteAppletResponseJson>.Result resultCommunicateWithSWS = communicateWithSWS();
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
                if (((DeleteAppletResponseJson) resultCommunicateWithSWS.response).isSuccess()) {
                    onSuccess((DeleteAppletResponseJson) resultCommunicateWithSWS.response);
                } else {
                    String resultCode = ((DeleteAppletResponseJson) resultCommunicateWithSWS.response).getResultCode();
                    LogMgr.log(2, "700 Unexpected result code " + resultCode);
                    Integer num = ERROR_CODE_MAP.get(resultCode);
                    onError(super.checkAndConvertNoSendingServerLogErrorTypeIfNeeded(DeleteAppletTask.this.mDataManager.getIsCalledFromSemcBackground(), resultCode, num != null ? num.intValue() : 900), ObfuscatedMsgUtil.executionPoint());
                }
            } catch (JSONException e) {
                LogMgr.log(2, "701 Response data is invalid.");
                onError(SemClientCallbackConst.TYPE_HTTP_PROTOCOL_ERROR, ObfuscatedMsgUtil.executionPoint(e));
            }
            LogMgr.log(7, "999");
        }

        public AccessSwsTask<DeleteAppletResponseJson>.Result communicateWithSWS() {
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
            try {
                PlayIntegrityAgent.stop();
            } catch (Exception e) {
                LogMgr.log(1, "800 Unknown Error occurred.");
                LogMgr.printStackTrace(8, e);
            }
            LogMgr.log(7, "999");
        }
    }
}
