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
import com.felicanetworks.semc.sws.json.RequestJson;
import com.felicanetworks.semc.sws.json.UpgradeAppletRequestJson;
import com.felicanetworks.semc.sws.json.UpgradeAppletResponseJson;
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
public class UpgradeAppletTask extends AsyncParentTaskBase<UpgradeAppletResponseJson> {
    private static final String CLIENT_CONTROL_INFO_MASTER_KEY_RETRY_INTERVAL = "retry.upgradeApplet";
    private static final Map<PlayIntegrityAgentErrorType.Error, Integer> CONVERT_PIA_ERROR_MAP;
    private static final List<String> VALID_RESULT_CODE_LIST_UPGRADE_APPLET;
    private final DataManager mDataManager;
    private int mGetPlayIntegrityTokenErrType;
    private CountDownLatch mGetPlayIntegrityTokenLatch;
    private boolean mIsGetPlayIntegrityTokenSuccess;
    private final String mNewSpAppletVersion;
    private final String mOldSpAppletVersion;
    private final String mOperationId;
    private final String mOperationType;
    private UpgradeAppletResponseJson mResponse;
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
        VALID_RESULT_CODE_LIST_UPGRADE_APPLET = arrayList;
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

    UpgradeAppletTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, String str, String str2, String str3, String str4, String str5, SwsClient swsClient, DataManager dataManager, String str6, String str7) {
        super(i, executorService, listener);
        this.mGetPlayIntegrityTokenLatch = null;
        this.mIsGetPlayIntegrityTokenSuccess = false;
        this.mGetPlayIntegrityTokenErrType = 0;
        this.mServiceId = str;
        this.mSpAppId = str2;
        this.mSpAppletId = str3;
        this.mOldSpAppletVersion = str4;
        this.mNewSpAppletVersion = str5;
        this.mSwsClient = swsClient;
        this.mDataManager = dataManager;
        this.mOperationType = str6;
        this.mOperationId = str7;
    }

    public String getLinkageData() {
        LogMgr.log(8, "000");
        String linkageData = null;
        try {
            UpgradeAppletResponseJson upgradeAppletResponseJson = this.mResponse;
            if (upgradeAppletResponseJson != null) {
                linkageData = upgradeAppletResponseJson.getLinkageData();
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
            UpgradeAppletResponseJson upgradeAppletResponseJson = this.mResponse;
            if (upgradeAppletResponseJson != null) {
                processId = upgradeAppletResponseJson.getProcessId();
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
        UpgradeAppletSubTask upgradeAppletSubTask = new UpgradeAppletSubTask(this.mSwsClient);
        setStoppableSubTask(upgradeAppletSubTask);
        upgradeAppletSubTask.start();
        LogMgr.log(8, "999");
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.semc.sws.AsyncTaskBase
    public void setResult(UpgradeAppletResponseJson upgradeAppletResponseJson) {
        this.mResponse = upgradeAppletResponseJson;
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.felicanetworks.semc.sws.AsyncParentTaskBase
    public synchronized UpgradeAppletResponseJson getResult() {
        return this.mResponse;
    }

    @Override // com.felicanetworks.semc.sws.AsyncTaskBase
    protected void onFinished(boolean z, int i, String str, String str2) {
        LogMgr.log(8, "000");
        super.onFinished(z, i, str, str2);
        LogMgr.log(8, "999");
    }

    private class UpgradeAppletSubTask extends AccessSwsTask<UpgradeAppletResponseJson> {
        private static final String API_NAME = "upgradeApplet";

        UpgradeAppletSubTask(SwsClient swsClient) {
            super(0, swsClient, UpgradeAppletTask.this.mDataManager.getIsCalledFromSemcBackground());
            LogMgr.log(8, "000");
            Map<String, String> clientControlInfo = UpgradeAppletTask.this.mDataManager.getClientControlInfo();
            if (clientControlInfo == null) {
                setRetryInterval(null, FlavorConst.DEFAULT_RETRY_UPGRADE_APPLET_INTERVAL);
            } else {
                setRetryInterval(clientControlInfo.get(UpgradeAppletTask.CLIENT_CONTROL_INFO_MASTER_KEY_RETRY_INTERVAL), FlavorConst.DEFAULT_RETRY_UPGRADE_APPLET_INTERVAL);
            }
            setClientId(UpgradeAppletTask.this.mDataManager.getClientId());
            LogMgr.log(8, "999");
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/semc/sws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        public UpgradeAppletResponseJson convertResponse(String str) throws JSONException {
            return new UpgradeAppletResponseJson(str);
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        protected List<String> getValidResultCodeList() {
            return UpgradeAppletTask.VALID_RESULT_CODE_LIST_UPGRADE_APPLET;
        }

        void onSuccess(UpgradeAppletResponseJson upgradeAppletResponseJson) {
            UpgradeAppletTask.this.setResult(upgradeAppletResponseJson);
            UpgradeAppletTask.this.onFinished(true, 0, "", "");
        }

        void onError(int i, String str) {
            UpgradeAppletTask.this.onFinished(false, i, "", str);
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        protected RequestJson createRequestJson() throws JSONException {
            LogMgr.log(8, "000");
            UpgradeAppletRequestJson upgradeAppletRequestJson = new UpgradeAppletRequestJson();
            upgradeAppletRequestJson.setRequestId(createRequestId());
            upgradeAppletRequestJson.setClientId(getClientId());
            upgradeAppletRequestJson.setOperationType(UpgradeAppletTask.this.mOperationType);
            upgradeAppletRequestJson.setOperationId(UpgradeAppletTask.this.mOperationId);
            if (UpgradeAppletTask.this.mDataManager == null || UpgradeAppletTask.this.mServiceId == null || UpgradeAppletTask.this.mSpAppId == null || UpgradeAppletTask.this.mSpAppletId == null || UpgradeAppletTask.this.mOldSpAppletVersion == null || UpgradeAppletTask.this.mNewSpAppletVersion == null) {
                if (UpgradeAppletTask.this.mDataManager != null) {
                    if (UpgradeAppletTask.this.mServiceId != null) {
                        if (UpgradeAppletTask.this.mSpAppId != null) {
                            if (UpgradeAppletTask.this.mSpAppletId != null) {
                                if (UpgradeAppletTask.this.mOldSpAppletVersion == null) {
                                    LogMgr.log(2, "704 mOldSpAppletVersion is null.");
                                    throw new JSONException(ObfuscatedMsgUtil.executionPoint());
                                }
                                LogMgr.log(2, "705 mNewSpAppletVersion is null.");
                                throw new JSONException(ObfuscatedMsgUtil.executionPoint());
                            }
                            LogMgr.log(2, "703 mSpAppletId is null.");
                            throw new JSONException(ObfuscatedMsgUtil.executionPoint());
                        }
                        LogMgr.log(2, "702 mSpAppId is null.");
                        throw new JSONException(ObfuscatedMsgUtil.executionPoint());
                    }
                    LogMgr.log(2, "701 mServiceId is null.");
                    throw new JSONException(ObfuscatedMsgUtil.executionPoint());
                }
                LogMgr.log(2, "700 mDataManager is null.");
                throw new JSONException(ObfuscatedMsgUtil.executionPoint());
            }
            upgradeAppletRequestJson.setPayload(UpgradeAppletTask.this.mServiceId, UpgradeAppletTask.this.mSpAppId, UpgradeAppletTask.this.mDataManager.getSeid(), UpgradeAppletTask.this.mSpAppletId, UpgradeAppletTask.this.mOldSpAppletVersion, UpgradeAppletTask.this.mNewSpAppletVersion);
            LogMgr.log(8, "999");
            return upgradeAppletRequestJson;
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        protected String callSws(String str, String str2) throws HttpException, SemClientException {
            LogMgr.log(8, "000");
            String strCreateUrl = this.mSwsClient.createUrl(str, UpgradeAppletTask.this.mDataManager.getSeid(), UpgradeAppletTask.this.mServiceId, "upgradeApplet");
            UpgradeAppletTask.this.mGetPlayIntegrityTokenLatch = new CountDownLatch(1);
            try {
                Context applicationContext = SemClientAdapter.getInstance().getApplicationContext();
                String str3 = UpgradeAppletTask.this.mDataManager.getIntegrityVerificationUniqueValue() + str2;
                UpgradeAppletTask.this.mIsGetPlayIntegrityTokenSuccess = false;
                LogMgr.log(9, "001");
                if (isStopped()) {
                    LogMgr.log(1, "800 Already has stopped.");
                    throw new SemClientException(901, ObfuscatedMsgUtil.executionPoint());
                }
                PlayIntegrityAgent.getPlayIntegrityToken(applicationContext, str3, new PlayIntegrityAgent.PlayIntegrityTokenEventListener() { // from class: com.felicanetworks.semc.sws.UpgradeAppletTask.UpgradeAppletSubTask.1
                    @Override // com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityAgent.PlayIntegrityTokenEventListener
                    public void onSuccess(String str4) {
                        LogMgr.log(8, "000");
                        UpgradeAppletTask.this.mDataManager.setPlayIntegrityToken(str4);
                        UpgradeAppletTask.this.mIsGetPlayIntegrityTokenSuccess = true;
                        UpgradeAppletTask.this.mGetPlayIntegrityTokenLatch.countDown();
                        LogMgr.log(8, "999");
                    }

                    @Override // com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityAgent.PlayIntegrityTokenEventListener
                    public void onError(PlayIntegrityAgentErrorType.Error error, String str4) {
                        LogMgr.log(8, "000");
                        UpgradeAppletTask.this.mIsGetPlayIntegrityTokenSuccess = false;
                        UpgradeAppletTask.this.mGetPlayIntegrityTokenErrType = UpgradeAppletSubTask.this.convertPiaErrorType(error);
                        LogMgr.log(8, "001 message = " + str4);
                        UpgradeAppletTask.this.mGetPlayIntegrityTokenLatch.countDown();
                        LogMgr.log(8, "999");
                    }
                });
                try {
                    UpgradeAppletTask.this.mGetPlayIntegrityTokenLatch.await();
                    if (!UpgradeAppletTask.this.mIsGetPlayIntegrityTokenSuccess) {
                        LogMgr.log(1, "804 Failed to get play Integrity Token.");
                        throw new SemClientException(UpgradeAppletTask.this.mGetPlayIntegrityTokenErrType, ObfuscatedMsgUtil.executionPoint());
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
            Integer num = (Integer) UpgradeAppletTask.CONVERT_PIA_ERROR_MAP.get(error);
            if (num == null) {
                num = 900;
            }
            LogMgr.log(8, "999 type = " + num);
            return num.intValue();
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask, com.felicanetworks.semc.sws.StoppableTaskBase, com.felicanetworks.semc.sws.TaskBase
        public void start() {
            LogMgr.log(7, "000");
            AccessSwsTask<UpgradeAppletResponseJson>.Result resultCommunicateWithSWS = communicateWithSWS();
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
                if (((UpgradeAppletResponseJson) resultCommunicateWithSWS.response).isSuccess()) {
                    onSuccess((UpgradeAppletResponseJson) resultCommunicateWithSWS.response);
                } else {
                    String resultCode = ((UpgradeAppletResponseJson) resultCommunicateWithSWS.response).getResultCode();
                    LogMgr.log(2, "700 Unexpected result code " + resultCode);
                    Integer num = ERROR_CODE_MAP.get(resultCode);
                    onError(super.checkAndConvertNoSendingServerLogErrorTypeIfNeeded(UpgradeAppletTask.this.mDataManager.getIsCalledFromSemcBackground(), resultCode, num != null ? num.intValue() : 900), ObfuscatedMsgUtil.executionPoint());
                }
            } catch (JSONException e) {
                LogMgr.log(2, "701 Response data is invalid.");
                onError(SemClientCallbackConst.TYPE_HTTP_PROTOCOL_ERROR, ObfuscatedMsgUtil.executionPoint(e));
            }
            LogMgr.log(7, "999");
        }

        public AccessSwsTask<UpgradeAppletResponseJson>.Result communicateWithSWS() {
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
