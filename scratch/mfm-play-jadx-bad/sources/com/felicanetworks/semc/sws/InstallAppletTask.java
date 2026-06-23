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
import com.felicanetworks.semc.sws.json.InstallAppletRequestJson;
import com.felicanetworks.semc.sws.json.InstallAppletResponseJson;
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
public class InstallAppletTask extends AsyncParentTaskBase<InstallAppletResponseJson> {
    private static final String CLIENT_CONTROL_INFO_MASTER_KEY_RETRY_INTERVAL = "retry.installApplet";
    private static final Map<PlayIntegrityAgentErrorType.Error, Integer> CONVERT_PIA_ERROR_MAP;
    private static final List<String> VALID_RESULT_CODE_LIST_INSTALL_APPLET;
    private final String[] mAccessAllowedSpAppIdList;
    private final DataManager mDataManager;
    private int mGetPlayIntegrityTokenErrType;
    private CountDownLatch mGetPlayIntegrityTokenLatch;
    private boolean mIsGetPlayIntegrityTokenSuccess;
    private final String mOperationId;
    private final String mOperationType;
    private InstallAppletResponseJson mResponse;
    private final String mServiceId;
    private final String mSpAppId;
    private final String mSpAppletId;
    private final String mSpAppletVersion;
    private final String mSpSdKeyVersion;
    private final SwsClient mSwsClient;

    @Override // com.felicanetworks.semc.sws.AsyncParentTaskBase, com.felicanetworks.semc.sws.StoppableTaskBase
    public /* bridge */ /* synthetic */ void stop() {
        super.stop();
    }

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_INSTALL_APPLET = arrayList;
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

    InstallAppletTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, String str, String str2, String str3, String str4, String str5, String[] strArr, SwsClient swsClient, DataManager dataManager, String str6, String str7) {
        super(i, executorService, listener);
        this.mGetPlayIntegrityTokenLatch = null;
        this.mIsGetPlayIntegrityTokenSuccess = false;
        this.mGetPlayIntegrityTokenErrType = 0;
        this.mServiceId = str;
        this.mSpAppId = str2;
        this.mSpAppletId = str3;
        this.mSpAppletVersion = str4;
        this.mSpSdKeyVersion = str5;
        this.mAccessAllowedSpAppIdList = strArr;
        this.mSwsClient = swsClient;
        this.mDataManager = dataManager;
        this.mOperationType = str6;
        this.mOperationId = str7;
    }

    public String getLinkageData() {
        LogMgr.log(8, "000");
        String linkageData = null;
        try {
            InstallAppletResponseJson installAppletResponseJson = this.mResponse;
            if (installAppletResponseJson != null) {
                linkageData = installAppletResponseJson.getLinkageData();
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
            InstallAppletResponseJson installAppletResponseJson = this.mResponse;
            if (installAppletResponseJson != null) {
                processId = installAppletResponseJson.getProcessId();
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
        InstallAppletSubTask installAppletSubTask = new InstallAppletSubTask(this.mSwsClient);
        setStoppableSubTask(installAppletSubTask);
        installAppletSubTask.start();
        LogMgr.log(8, "999");
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.semc.sws.AsyncTaskBase
    public void setResult(InstallAppletResponseJson installAppletResponseJson) {
        this.mResponse = installAppletResponseJson;
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.felicanetworks.semc.sws.AsyncParentTaskBase
    public synchronized InstallAppletResponseJson getResult() {
        return this.mResponse;
    }

    @Override // com.felicanetworks.semc.sws.AsyncTaskBase
    protected void onFinished(boolean z, int i, String str, String str2) {
        LogMgr.log(8, "000");
        super.onFinished(z, i, str, str2);
        LogMgr.log(8, "999");
    }

    private class InstallAppletSubTask extends AccessSwsTask<InstallAppletResponseJson> {
        private static final String API_NAME = "installApplet";

        InstallAppletSubTask(SwsClient swsClient) {
            super(0, swsClient, InstallAppletTask.this.mDataManager.getIsCalledFromSemcBackground());
            LogMgr.log(8, "000");
            Map<String, String> clientControlInfo = InstallAppletTask.this.mDataManager.getClientControlInfo();
            if (clientControlInfo == null) {
                setRetryInterval(null, FlavorConst.DEFAULT_RETRY_INSTALL_APPLET_INTERVAL);
            } else {
                setRetryInterval(clientControlInfo.get(InstallAppletTask.CLIENT_CONTROL_INFO_MASTER_KEY_RETRY_INTERVAL), FlavorConst.DEFAULT_RETRY_INSTALL_APPLET_INTERVAL);
            }
            setClientId(InstallAppletTask.this.mDataManager.getClientId());
            LogMgr.log(8, "999");
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/semc/sws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        public InstallAppletResponseJson convertResponse(String str) throws JSONException {
            return new InstallAppletResponseJson(str);
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        protected List<String> getValidResultCodeList() {
            return InstallAppletTask.VALID_RESULT_CODE_LIST_INSTALL_APPLET;
        }

        void onSuccess(InstallAppletResponseJson installAppletResponseJson) {
            InstallAppletTask.this.setResult(installAppletResponseJson);
            InstallAppletTask.this.onFinished(true, 0, "", "");
        }

        void onError(int i, String str) {
            InstallAppletTask.this.onFinished(false, i, "", str);
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        protected RequestJson createRequestJson() throws JSONException {
            LogMgr.log(8, "000");
            InstallAppletRequestJson installAppletRequestJson = new InstallAppletRequestJson();
            installAppletRequestJson.setRequestId(createRequestId());
            installAppletRequestJson.setClientId(getClientId());
            installAppletRequestJson.setOperationType(InstallAppletTask.this.mOperationType);
            installAppletRequestJson.setOperationId(InstallAppletTask.this.mOperationId);
            if (InstallAppletTask.this.mDataManager == null || InstallAppletTask.this.mServiceId == null || InstallAppletTask.this.mSpAppId == null || InstallAppletTask.this.mSpAppletId == null || InstallAppletTask.this.mSpAppletVersion == null || InstallAppletTask.this.mAccessAllowedSpAppIdList == null) {
                if (InstallAppletTask.this.mDataManager != null) {
                    if (InstallAppletTask.this.mServiceId != null) {
                        if (InstallAppletTask.this.mSpAppId != null) {
                            if (InstallAppletTask.this.mSpAppletId != null) {
                                if (InstallAppletTask.this.mSpAppletVersion == null) {
                                    LogMgr.log(2, "704 mSpAppletVersion is null.");
                                    throw new JSONException(ObfuscatedMsgUtil.executionPoint());
                                }
                                LogMgr.log(2, "705 mAccessAllowedSpAppIdList is null.");
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
            installAppletRequestJson.setPayload(InstallAppletTask.this.mServiceId, InstallAppletTask.this.mSpAppId, InstallAppletTask.this.mDataManager.getSeid(), InstallAppletTask.this.mSpAppletId, InstallAppletTask.this.mSpAppletVersion, InstallAppletTask.this.mSpSdKeyVersion, InstallAppletTask.this.mAccessAllowedSpAppIdList);
            LogMgr.log(8, "999");
            return installAppletRequestJson;
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask
        protected String callSws(String str, String str2) throws HttpException, SemClientException {
            LogMgr.log(8, "000");
            String strCreateUrl = this.mSwsClient.createUrl(str, InstallAppletTask.this.mDataManager.getSeid(), InstallAppletTask.this.mServiceId, "installApplet");
            InstallAppletTask.this.mGetPlayIntegrityTokenLatch = new CountDownLatch(1);
            try {
                Context applicationContext = SemClientAdapter.getInstance().getApplicationContext();
                String str3 = InstallAppletTask.this.mDataManager.getIntegrityVerificationUniqueValue() + str2;
                InstallAppletTask.this.mIsGetPlayIntegrityTokenSuccess = false;
                LogMgr.log(9, "001");
                if (isStopped()) {
                    LogMgr.log(1, "800 Already has stopped.");
                    throw new SemClientException(901, ObfuscatedMsgUtil.executionPoint());
                }
                PlayIntegrityAgent.getPlayIntegrityToken(applicationContext, str3, new PlayIntegrityAgent.PlayIntegrityTokenEventListener() { // from class: com.felicanetworks.semc.sws.InstallAppletTask.InstallAppletSubTask.1
                    @Override // com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityAgent.PlayIntegrityTokenEventListener
                    public void onSuccess(String str4) {
                        LogMgr.log(8, "000");
                        InstallAppletTask.this.mDataManager.setPlayIntegrityToken(str4);
                        InstallAppletTask.this.mIsGetPlayIntegrityTokenSuccess = true;
                        InstallAppletTask.this.mGetPlayIntegrityTokenLatch.countDown();
                        LogMgr.log(8, "999");
                    }

                    @Override // com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityAgent.PlayIntegrityTokenEventListener
                    public void onError(PlayIntegrityAgentErrorType.Error error, String str4) {
                        LogMgr.log(8, "000");
                        InstallAppletTask.this.mIsGetPlayIntegrityTokenSuccess = false;
                        InstallAppletTask.this.mGetPlayIntegrityTokenErrType = InstallAppletSubTask.this.convertPiaErrorType(error);
                        LogMgr.log(8, "001 message = " + str4);
                        InstallAppletTask.this.mGetPlayIntegrityTokenLatch.countDown();
                        LogMgr.log(8, "999");
                    }
                });
                try {
                    InstallAppletTask.this.mGetPlayIntegrityTokenLatch.await();
                    if (!InstallAppletTask.this.mIsGetPlayIntegrityTokenSuccess) {
                        LogMgr.log(1, "804 Failed to get play Integrity Token.");
                        throw new SemClientException(InstallAppletTask.this.mGetPlayIntegrityTokenErrType, ObfuscatedMsgUtil.executionPoint());
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
            Integer num = (Integer) InstallAppletTask.CONVERT_PIA_ERROR_MAP.get(error);
            if (num == null) {
                num = 900;
            }
            LogMgr.log(8, "999 type = " + num);
            return num.intValue();
        }

        @Override // com.felicanetworks.semc.sws.AccessSwsTask, com.felicanetworks.semc.sws.StoppableTaskBase, com.felicanetworks.semc.sws.TaskBase
        public void start() {
            LogMgr.log(7, "000");
            AccessSwsTask<InstallAppletResponseJson>.Result resultCommunicateWithSWS = communicateWithSWS();
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
                if (((InstallAppletResponseJson) resultCommunicateWithSWS.response).isSuccess()) {
                    onSuccess((InstallAppletResponseJson) resultCommunicateWithSWS.response);
                } else {
                    String resultCode = ((InstallAppletResponseJson) resultCommunicateWithSWS.response).getResultCode();
                    LogMgr.log(2, "700 Unexpected result code " + resultCode);
                    Integer num = ERROR_CODE_MAP.get(resultCode);
                    onError(super.checkAndConvertNoSendingServerLogErrorTypeIfNeeded(InstallAppletTask.this.mDataManager.getIsCalledFromSemcBackground(), resultCode, num != null ? num.intValue() : 900), ObfuscatedMsgUtil.executionPoint());
                }
            } catch (JSONException e) {
                LogMgr.log(2, "701 Response data is invalid.");
                onError(SemClientCallbackConst.TYPE_HTTP_PROTOCOL_ERROR, ObfuscatedMsgUtil.executionPoint(e));
            }
            LogMgr.log(7, "999");
        }

        public AccessSwsTask<InstallAppletResponseJson>.Result communicateWithSWS() {
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
