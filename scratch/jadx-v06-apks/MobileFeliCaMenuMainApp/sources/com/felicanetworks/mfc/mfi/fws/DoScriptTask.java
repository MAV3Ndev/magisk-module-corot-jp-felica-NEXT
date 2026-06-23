package com.felicanetworks.mfc.mfi.fws;

import android.os.Looper;
import com.felicanetworks.mfc.FSCEventListener;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.MfiControlInfoCache;
import com.felicanetworks.mfc.mfi.fws.AccessFwsTask;
import com.felicanetworks.mfc.mfi.fws.json.GetMfiControlInfoResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.GetScriptResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.TcapResultJson;
import com.felicanetworks.mfc.mfi.omapi.ApduCommandManager;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
abstract class DoScriptTask<Response extends GetScriptResponseJson> extends AccessFwsTask<Response> implements FSCEventListener {
    String mAccessToken;
    JSONObject mApduResult;
    private final MfiChipHolder mChipHolder;
    private boolean mDoingTcap;
    private String mErrorMessage;
    private boolean mErrorOccured;
    private int mErrorType;
    protected final ExecutorService mExecutor;
    private GpController mGpController;
    private boolean mIsRetry;
    final String mOperationId;
    private int mRetryHttpAccessCount;
    int mSeqNum;
    TcapResultJson mTcapResult;

    protected boolean isEnableRetryHttpAccess() {
        return false;
    }

    protected abstract boolean isTimedRetrievable();

    abstract void onErrorScript(int i, String str);

    abstract void onSuccessScript(Response response);

    DoScriptTask(int i, FwsClient fwsClient, MfiChipHolder mfiChipHolder, ExecutorService executorService) {
        this(i, fwsClient, mfiChipHolder, executorService, FwsParamCreator.createOperationId());
    }

    DoScriptTask(int i, FwsClient fwsClient, MfiChipHolder mfiChipHolder, ExecutorService executorService, String str) {
        super(i, fwsClient);
        this.mSeqNum = 1;
        this.mAccessToken = null;
        this.mTcapResult = null;
        this.mApduResult = null;
        this.mDoingTcap = false;
        this.mErrorOccured = false;
        this.mGpController = null;
        this.mIsRetry = false;
        this.mRetryHttpAccessCount = 0;
        this.mChipHolder = mfiChipHolder;
        this.mExecutor = executorService;
        this.mOperationId = str;
    }

    private void successScript(Response response) {
        LogMgr.log(6, "000");
        try {
            closeFelicaDriver();
            onSuccessScript(response);
        } catch (Exception e) {
            LogMgr.log(2, "700", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
            errorScript(200, ObfuscatedMsgUtil.exExecutionPoint(e));
        }
        LogMgr.log(6, "999");
    }

    private void errorScript(int i, String str) {
        LogMgr.log(6, "000");
        try {
            closeFelicaDriver();
            onErrorScript(i, str);
        } catch (Exception e) {
            LogMgr.log(1, "700 " + e.getClass().getSimpleName() + " " + e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        LogMgr.log(6, "999");
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask, com.felicanetworks.mfc.mfi.fws.StoppableTaskBase, com.felicanetworks.mfc.mfi.fws.TaskBase
    public void start() {
        LogMgr.log(5, "000");
        if (this.mIsRetry) {
            this.mIsRetry = false;
            super.retryStart(this.mRetryHttpAccessCount);
        } else {
            super.start();
        }
        if (isStopped()) {
            closeFelicaDriver();
            LogMgr.log(1, "800 Already has stopped.");
            errorScript(215, null);
            return;
        }
        AccessFwsTask.Result result = getResult2();
        if (isEnableRetryHttpAccess()) {
            if (!result.isSuccess && 205 == result.errType) {
                try {
                    int[] retryTimesDelayMillisList = new GetMfiControlInfoResponseJson(MfiControlInfoCache.getInstance().getInfoCache()).getRetryTimesDelayMillisList();
                    if (this.mRetryHttpAccessCount < retryTimesDelayMillisList.length) {
                        LogMgr.log(6, "001 Retry(" + (this.mRetryHttpAccessCount + 1) + ")");
                        retryDelayedMillis((long) retryTimesDelayMillisList[this.mRetryHttpAccessCount]);
                        this.mRetryHttpAccessCount = this.mRetryHttpAccessCount + 1;
                        return;
                    }
                    LogMgr.log(6, "002 Retry limit exceeded.");
                } catch (JSONException unused) {
                    LogMgr.log(1, "801 failed to parse MfiControlInfoCache data.");
                    errorScript(200, ObfuscatedMsgUtil.executionPoint());
                    return;
                }
            }
            this.mRetryHttpAccessCount = 0;
        }
        if (this.mErrorOccured) {
            errorScript(this.mErrorType, this.mErrorMessage);
            return;
        }
        if (!result.isSuccess) {
            if (isTimedRetrievable() && result.response != 0 && ((GetScriptResponseJson) result.response).isTimedRetryRequest()) {
                retryDelayed(((GetScriptResponseJson) result.response).optRetryAfter());
                return;
            } else {
                errorScript(result.errType, result.errMsg);
                return;
            }
        }
        if (this.mSeqNum == 1) {
            try {
                GpController gpController = this.mChipHolder.getGpController();
                if (gpController != null) {
                    gpController.closeChannel();
                }
            } catch (GpException e) {
                errorScript(e.getType(), e.getMessage());
                return;
            }
        }
        if (((GetScriptResponseJson) result.response).isSuccess()) {
            successScript((GetScriptResponseJson) result.response);
        } else if (((GetScriptResponseJson) result.response).isContinue()) {
            this.mAccessToken = ((GetScriptResponseJson) result.response).optNextAccessToken();
            if (((GetScriptResponseJson) result.response).hasPayload()) {
                if (((GetScriptResponseJson) result.response).isTCAPRequest()) {
                    LogMgr.log(6, "NAME_TCAP_REQUEST_JSON found.");
                    startTcap(((GetScriptResponseJson) result.response).optUrl());
                } else if (((GetScriptResponseJson) result.response).isAPDURequest()) {
                    LogMgr.log(6, "NAME_COMMAND_APDU_INFO found.");
                    doApdu(((GetScriptResponseJson) result.response).optCommandApduInfo());
                } else {
                    LogMgr.log(6, "null command received.");
                    nextStart();
                }
            } else {
                LogMgr.log(1, "ResultCode is continue, however No payload.");
                errorScript(202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            }
        } else {
            LogMgr.log(2, "700 Unexpected result code " + ((GetScriptResponseJson) result.response).optResultCode());
            errorScript(200, ObfuscatedMsgUtil.executionPoint());
        }
        LogMgr.log(5, "999");
    }

    private void retryDelayed(int i) {
        retryDelayedMillis(((long) i) * 1000);
    }

    private void retryDelayedMillis(long j) {
        LogMgr.log(6, "000");
        LogMgr.log(6, "001 delay[ms]=" + j);
        if (j >= 0) {
            try {
                Thread.sleep(j);
            } catch (InterruptedException unused) {
            }
            this.mIsRetry = true;
            executeStart();
            LogMgr.log(6, "999");
            return;
        }
        LogMgr.log(1, "delay time is invalid value.");
        errorScript(202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0119  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void startTcap(java.lang.String r11) {
        /*
            Method dump skipped, instruction units count: 293
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.fws.DoScriptTask.startTcap(java.lang.String):void");
    }

    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    public synchronized void stop() {
        LogMgr.log(5, "000");
        super.stop();
        if (this.mDoingTcap || this.mGpController != null) {
            LogMgr.log(2, "700 Stopped.");
            closeFelicaDriver();
            errorScript(215, null);
        }
        LogMgr.log(5, "999");
    }

    protected void onFinishTcap(TcapResultJson tcapResultJson) {
        LogMgr.log(5, "000");
        this.mTcapResult = tcapResultJson;
        this.mSeqNum++;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            start();
        } else {
            LogMgr.log(6, "001 In main thread.");
            executeStart();
        }
        LogMgr.log(5, "999");
    }

    @Override // com.felicanetworks.mfc.FSCEventListener
    public void finished(int i) {
        LogMgr.log(4, "000 status= " + i);
        closeFelicaDriver();
        if (isStopped()) {
            LogMgr.log(2, "700 Already has stopped.");
            errorScript(215, null);
            return;
        }
        try {
            TcapResultJson tcapResultJson = new TcapResultJson();
            tcapResultJson.setResultCode("0000");
            tcapResultJson.setReturnCode(i);
            onFinishTcap(tcapResultJson);
        } catch (JSONException e) {
            LogMgr.log(2, "701 " + e.getClass().getSimpleName() + e.getMessage());
            errorScript(200, ObfuscatedMsgUtil.exExecutionPoint(e));
            LogMgr.printStackTrace(7, e);
        } catch (Exception e2) {
            LogMgr.log(2, "702 " + e2.getClass().getSimpleName() + e2.getMessage());
            errorScript(200, ObfuscatedMsgUtil.exExecutionPoint(e2));
            LogMgr.printStackTrace(7, e2);
        }
        LogMgr.log(4, "999");
    }

    @Override // com.felicanetworks.mfc.FSCEventListener
    public void errorOccurred(int i, String str) {
        LogMgr.log(4, "000 type= " + i + "msg= " + str);
        closeFelicaDriver();
        if (isStopped()) {
            LogMgr.log(2, "700 Already has stopped.");
            errorScript(215, null);
            return;
        }
        try {
            TcapResultJson tcapResultJson = new TcapResultJson();
            String str2 = "9000";
            if (4 == i) {
                str2 = "4000";
            } else if (3 == i) {
                str2 = "9001";
            }
            tcapResultJson.setResultCode(str2);
            onFinishTcap(tcapResultJson);
        } catch (JSONException e) {
            LogMgr.log(2, "701 " + e.getClass().getSimpleName() + e.getMessage());
            errorScript(200, ObfuscatedMsgUtil.exExecutionPoint(e));
            LogMgr.printStackTrace(7, e);
        } catch (Exception e2) {
            LogMgr.log(2, "702 " + e2.getClass().getSimpleName() + e2.getMessage());
            errorScript(200, ObfuscatedMsgUtil.exExecutionPoint(e2));
            LogMgr.printStackTrace(7, e2);
        }
        LogMgr.log(4, "999");
    }

    @Override // com.felicanetworks.mfc.FSCEventListener
    public byte[] operationRequested(int i, String str, byte[] bArr) throws Exception {
        LogMgr.log(4, "000");
        LogMgr.log(4, "999");
        throw new RuntimeException();
    }

    private void closeFelicaSilently() {
        try {
            synchronized (this) {
                this.mDoingTcap = false;
                this.mChipHolder.getFelica().close();
                LogMgr.log(7, "001 : FSC#start finish");
            }
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
    }

    protected void nextStart() {
        LogMgr.log(6, "000");
        this.mSeqNum++;
        executeStart();
        LogMgr.log(6, "999");
    }

    protected void executeStart() {
        try {
            this.mExecutor.submit(new Runnable() { // from class: com.felicanetworks.mfc.mfi.fws.DoScriptTask.1
                @Override // java.lang.Runnable
                public void run() {
                    DoScriptTask.this.start();
                }
            });
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + e.getMessage());
            LogMgr.printStackTrace(7, e);
            errorScript(200, ObfuscatedMsgUtil.exExecutionPoint(e));
        }
    }

    private void doApdu(JSONObject jSONObject) {
        ApduCommandManager apduCommandManager;
        LogMgr.log(5, "000");
        if (jSONObject == null) {
            LogMgr.log(1, "800 commandApduInfo is null.");
            errorScript(202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            return;
        }
        closeFelicaSilently();
        ApduCommandManager apduCommandManager2 = null;
        if (isStopped()) {
            LogMgr.log(1, "801 Already has stopped.");
            errorScript(215, null);
            return;
        }
        this.mErrorOccured = false;
        this.mErrorType = 0;
        this.mErrorMessage = null;
        LogMgr.log(6, "001 ApduCommandManager assigned.");
        try {
            try {
                LogMgr.log(6, "002 send APDU Command to APDU process.");
                apduCommandManager = new ApduCommandManager(this.mChipHolder);
            } catch (GpException e) {
                e = e;
            }
            try {
                this.mGpController = this.mChipHolder.getGpController();
                apduCommandManager.sendCommand(jSONObject);
            } catch (GpException e2) {
                e = e2;
                apduCommandManager2 = apduCommandManager;
                LogMgr.log(1, "804 : GpException");
                this.mErrorOccured = true;
                this.mErrorType = e.getType();
                this.mErrorMessage = e.getMessage();
                apduCommandManager = apduCommandManager2;
            }
            if (apduCommandManager != null) {
                try {
                    this.mApduResult = apduCommandManager.getApduResponse();
                } catch (IllegalArgumentException e3) {
                    LogMgr.log(1, "806 : IllegalArgumentException");
                    errorScript(200, ObfuscatedMsgUtil.exExecutionPoint(e3));
                    return;
                }
            }
            this.mSeqNum++;
            start();
            LogMgr.log(5, "999");
        } catch (IllegalArgumentException e4) {
            LogMgr.log(1, "802 : IllegalArgumentException");
            errorScript(200, ObfuscatedMsgUtil.exExecutionPoint(e4));
        } catch (InterruptedException unused) {
            LogMgr.log(1, "803 : cancel occured.");
            errorScript(215, null);
        } catch (JSONException e5) {
            LogMgr.printStackTrace(7, e5);
            errorScript(202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
        } catch (Exception e6) {
            LogMgr.log(1, "805 : " + e6.getClass().getSimpleName() + ":" + e6.getMessage());
            LogMgr.printStackTrace(7, e6);
            errorScript(200, ObfuscatedMsgUtil.exExecutionPoint(e6));
        }
    }

    private void closeFelicaDriver() {
        LogMgr.log(6, "000");
        try {
            if (this.mGpController != null) {
                this.mGpController.closeChannel();
                LogMgr.log(6, "001 Channel closed.");
            }
            closeFelicaSilently();
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        LogMgr.log(6, "999");
    }
}
