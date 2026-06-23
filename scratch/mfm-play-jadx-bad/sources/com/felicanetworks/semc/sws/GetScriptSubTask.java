package com.felicanetworks.semc.sws;

import android.os.SystemClock;
import androidx.credentials.exceptions.publickeycredential.DomExceptionUtils;
import com.felicanetworks.semc.DataManager;
import com.felicanetworks.semc.FlavorConst;
import com.felicanetworks.semc.SemChipHolder;
import com.felicanetworks.semc.SemClientCallbackConst;
import com.felicanetworks.semc.http.HttpException;
import com.felicanetworks.semc.omapi.ApduCommandManager;
import com.felicanetworks.semc.omapi.GpController;
import com.felicanetworks.semc.omapi.GpException;
import com.felicanetworks.semc.sws.AccessSwsTask;
import com.felicanetworks.semc.sws.json.GetScriptRequestJson;
import com.felicanetworks.semc.sws.json.GetScriptResponseJson;
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
abstract class GetScriptSubTask<Response extends GetScriptResponseJson> extends AccessSwsTask<Response> {
    private static final String API_NAME = "getScript";
    private static final String CLIENT_CONTROL_INFO_MASTER_KEY_RETRY_INTERVAL = "retry.getScript";
    private static final List<String> VALID_RESULT_CODE_LIST_GET_SCRIPT;
    private static final List<String> VALID_SCRIPT_CODE_LIST;
    String mAccessToken;
    private String mApduAdditionalErrorInfo;
    JSONArray mApduCommandInfoList;
    private String mApduErrorMsg;
    private boolean mApduErrorOccurred;
    private int mApduErrorType;
    JSONArray mApduResponseInfoList;
    private final SemChipHolder mChipHolder;
    protected DataManager mDataManager;
    protected final ExecutorService mExecutor;
    private GpController mGpController;
    boolean mIsCalledFromBackground;
    JSONObject mProcessTraceInfo;
    int mSeqNum;
    String mServiceID;

    abstract void onErrorScript(int i, String str, String str2);

    abstract void onSuccessScript(GetScriptResponseJson getScriptResponseJson);

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_SCRIPT = arrayList;
        arrayList.add("0000");
        arrayList.add("1000");
        arrayList.add("2000");
        arrayList.add("3000");
        arrayList.add("4000");
        arrayList.add("9000");
        arrayList.add("9001");
        arrayList.add("9005");
        ArrayList arrayList2 = new ArrayList();
        VALID_SCRIPT_CODE_LIST = arrayList2;
        arrayList2.add("0000");
        arrayList2.add(SwsConst.SCRIPT_CODE_CONTINUE_C_APDU);
        arrayList2.add("5081");
        arrayList2.add(SwsConst.SCRIPT_CODE_INVALID_SYSTEM_CONDITION);
    }

    GetScriptSubTask(int i, SwsClient swsClient, SemChipHolder semChipHolder, ExecutorService executorService, DataManager dataManager, JSONObject jSONObject, String str, JSONArray jSONArray, String str2, boolean z) {
        super(i, swsClient, dataManager.getIsCalledFromSemcBackground());
        this.mSeqNum = 1;
        this.mApduResponseInfoList = null;
        this.mApduErrorOccurred = false;
        this.mGpController = null;
        LogMgr.log(7, "000");
        this.mChipHolder = semChipHolder;
        this.mExecutor = executorService;
        this.mDataManager = dataManager;
        this.mProcessTraceInfo = jSONObject;
        this.mAccessToken = str;
        this.mApduCommandInfoList = jSONArray;
        this.mServiceID = str2;
        this.mIsCalledFromBackground = z;
        Map<String, String> clientControlInfo = dataManager.getClientControlInfo();
        if (clientControlInfo == null) {
            setRetryInterval(null, FlavorConst.DEFAULT_RETRY_GET_SCRIPT_INTERVAL);
        } else {
            setRetryInterval(clientControlInfo.get(CLIENT_CONTROL_INFO_MASTER_KEY_RETRY_INTERVAL), FlavorConst.DEFAULT_RETRY_GET_SCRIPT_INTERVAL);
        }
        setClientId(this.mDataManager.getClientId());
        LogMgr.log(7, "999");
    }

    private void successScript(Response response) {
        LogMgr.log(8, "000");
        try {
            closeGpControllerChannel();
            onSuccessScript(response);
        } catch (Exception e) {
            LogMgr.log(2, "700 e=" + e.getClass().getSimpleName() + " " + e.getMessage());
            errorScript(900, "", ObfuscatedMsgUtil.executionPoint(e));
        }
        LogMgr.log(8, "999");
    }

    private void errorScript(int i, String str, String str2) {
        LogMgr.log(8, "000");
        try {
            closeGpControllerChannel();
            onErrorScript(i, str, str2);
        } catch (Exception e) {
            LogMgr.log(1, "800 " + e.getClass().getSimpleName() + " " + e.getMessage());
        }
        LogMgr.log(8, "999");
    }

    @Override // com.felicanetworks.semc.sws.AccessSwsTask, com.felicanetworks.semc.sws.StoppableTaskBase, com.felicanetworks.semc.sws.TaskBase
    public void start() {
        LogMgr.log(6, "000");
        if (this.mSeqNum == 1) {
            try {
                GpController gpController = this.mChipHolder.getGpController();
                if (gpController != null) {
                    gpController.closeChannel();
                }
            } catch (GpException e) {
                LogMgr.log(1, "800 failed to get Gp Controller");
                errorScript(e.getType(), "", ObfuscatedMsgUtil.executionPoint(e));
                return;
            }
        }
        if (this.mApduCommandInfoList != null) {
            LogMgr.log(8, "001 linkageData has CommandApduInfo. Exec doApduInner.");
            doApduInner(this.mApduCommandInfoList);
            this.mApduCommandInfoList = null;
        }
        AccessSwsTask<Response>.Result resultCommunicateWithSWS = communicateWithSWS();
        if (resultCommunicateWithSWS.isStopped) {
            LogMgr.log(1, "801 Already has stopped.");
            errorScript(901, "", ObfuscatedMsgUtil.executionPoint());
            return;
        }
        if (this.mApduErrorOccurred) {
            LogMgr.log(1, "802 Run Apdu Command Error.");
            errorScript(this.mApduErrorType, this.mApduAdditionalErrorInfo, this.mApduErrorMsg);
            return;
        }
        if (!resultCommunicateWithSWS.isSuccess) {
            LogMgr.log(1, "803 Response code From SWS is Not Success.");
            errorScript(resultCommunicateWithSWS.errType, resultCommunicateWithSWS.additionalErrorInfo, resultCommunicateWithSWS.errMsg);
            return;
        }
        try {
            if (((GetScriptResponseJson) resultCommunicateWithSWS.response).isSuccess()) {
                String scriptCode = ((GetScriptResponseJson) resultCommunicateWithSWS.response).getScriptCode();
                if (VALID_SCRIPT_CODE_LIST.contains(scriptCode)) {
                    if (((GetScriptResponseJson) resultCommunicateWithSWS.response).isScriptCodeProcessFinished()) {
                        successScript((GetScriptResponseJson) resultCommunicateWithSWS.response);
                    }
                    if (((GetScriptResponseJson) resultCommunicateWithSWS.response).isScriptCodeContinue()) {
                        this.mAccessToken = ((GetScriptResponseJson) resultCommunicateWithSWS.response).getAccessToken();
                        doApdu(((GetScriptResponseJson) resultCommunicateWithSWS.response).getApduCommandInfoList());
                    }
                    if (((GetScriptResponseJson) resultCommunicateWithSWS.response).isScriptCodeNotSupportedDevice()) {
                        errorScript(201, "", ObfuscatedMsgUtil.executionPoint());
                    }
                    if (((GetScriptResponseJson) resultCommunicateWithSWS.response).isScriptCodeCondition()) {
                        errorScript(205, "", ObfuscatedMsgUtil.executionPoint());
                    }
                } else {
                    LogMgr.log(1, "804 Unexpected script code " + scriptCode);
                    errorScript(900, "", ObfuscatedMsgUtil.executionPoint());
                }
            } else {
                LogMgr.log(1, "805 Unexpected result code " + ((GetScriptResponseJson) resultCommunicateWithSWS.response).getResultCode());
                errorScript(900, "", ObfuscatedMsgUtil.executionPoint());
            }
        } catch (JSONException e2) {
            LogMgr.log(2, "702 Response data is invalid.");
            errorScript(SemClientCallbackConst.TYPE_HTTP_PROTOCOL_ERROR, "", ObfuscatedMsgUtil.executionPoint(e2));
        }
        LogMgr.log(6, "999");
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x009d, code lost:
    
        com.felicanetworks.semc.util.LogMgr.log(8, "999");
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00a2, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public AccessSwsTask<Response>.Result communicateWithSWS() {
        LogMgr.log(8, "000");
        AccessSwsTask<Response>.Result result = new AccessSwsTask.Result();
        int size = this.mRetryInterval.size();
        int i = 0;
        boolean z = this.mIsCalledFromBackground && this.mSeqNum == 1;
        while (true) {
            if (i > size) {
                break;
            }
            if (i == 0) {
                super.start();
            } else {
                super.retryStart(i, z);
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
                if (z) {
                    int i3 = result.errType;
                    if (i3 == -409 || i3 == 400) {
                        result.errType = 600;
                    } else if (i3 == 500) {
                        result.errType = 601;
                    }
                }
            } else {
                SystemClock.sleep(this.mRetryInterval.get(i).intValue());
                LogMgr.log(8, "004 (retry count / max retry count)=(" + i2 + DomExceptionUtils.SEPARATOR + size + ")");
                i = i2;
            }
        }
    }

    @Override // com.felicanetworks.semc.sws.StoppableTaskBase
    public synchronized void stop() {
        LogMgr.log(6, "000");
        super.stop();
        closeGpControllerChannel();
        LogMgr.log(6, "999");
    }

    @Override // com.felicanetworks.semc.sws.AccessSwsTask
    protected RequestJson createRequestJson() throws JSONException {
        LogMgr.log(8, "000");
        GetScriptRequestJson getScriptRequestJson = new GetScriptRequestJson();
        getScriptRequestJson.setRequestId(createRequestId());
        getScriptRequestJson.setClientId(getClientId());
        getScriptRequestJson.setPayload(this.mDataManager.getCarrierId(), this.mDataManager.getSepId(), this.mDataManager.getDeviceName(), this.mDataManager.getDeviceManufacturer(), this.mDataManager.getDeviceIdentificationData(), this.mProcessTraceInfo, this.mAccessToken, this.mApduResponseInfoList);
        this.mAccessToken = null;
        this.mApduResponseInfoList = null;
        LogMgr.log(8, "999");
        return getScriptRequestJson;
    }

    @Override // com.felicanetworks.semc.sws.AccessSwsTask
    protected String callSws(String str, String str2) throws HttpException {
        LogMgr.log(8, "000");
        String strCreateUrl = this.mSwsClient.createUrl(str, this.mDataManager.getSeid(), this.mServiceID, API_NAME);
        LogMgr.log(8, "999");
        return this.mSwsClient.runWebApi(str2, strCreateUrl, SwsRequest.HTTP_METHOD_POST, true, Boolean.valueOf(this.mIsCalledFromBackground && this.mSeqNum == 1));
    }

    /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/semc/sws/json/ResponseJson; */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.semc.sws.AccessSwsTask
    public Response convertResponse(String str) throws JSONException {
        LogMgr.log(8, "000");
        LogMgr.log(8, "999");
        return (Response) new GetScriptResponseJson(str);
    }

    @Override // com.felicanetworks.semc.sws.AccessSwsTask
    protected List<String> getValidResultCodeList() {
        LogMgr.log(8, "000");
        LogMgr.log(8, "999");
        return VALID_RESULT_CODE_LIST_GET_SCRIPT;
    }

    private void doApdu(JSONArray jSONArray) {
        LogMgr.log(8, "000");
        doApduInner(jSONArray);
        this.mSeqNum++;
        start();
        LogMgr.log(8, "999");
    }

    private void doApduInner(JSONArray jSONArray) {
        LogMgr.log(8, "000");
        if (isStopped()) {
            LogMgr.log(1, "800 Already has stopped.");
            errorScript(901, "", ObfuscatedMsgUtil.executionPoint());
            return;
        }
        this.mApduErrorOccurred = false;
        this.mApduErrorType = 0;
        ApduCommandManager apduCommandManager = null;
        this.mApduAdditionalErrorInfo = null;
        this.mApduErrorMsg = null;
        LogMgr.log(8, "001 ApduCommandManager assigned.");
        try {
            try {
                LogMgr.log(8, "002 send APDU Command to APDU process.");
                ApduCommandManager apduCommandManager2 = new ApduCommandManager(this.mChipHolder);
                try {
                    this.mGpController = this.mChipHolder.getGpController();
                    apduCommandManager2.sendCommand(jSONArray);
                    this.mApduResponseInfoList = apduCommandManager2.getApduResponse();
                } catch (GpException e) {
                    e = e;
                    apduCommandManager = apduCommandManager2;
                    LogMgr.log(1, "803 : GpException");
                    this.mApduErrorOccurred = true;
                    this.mApduErrorType = e.getType();
                    this.mApduAdditionalErrorInfo = "";
                    this.mApduErrorMsg = ObfuscatedMsgUtil.executionPoint(e);
                    this.mApduResponseInfoList = apduCommandManager.getApduResponse();
                }
            } catch (GpException e2) {
                e = e2;
            }
        } catch (IllegalArgumentException e3) {
            LogMgr.log(1, "801 : IllegalArgumentException");
            errorScript(900, "", ObfuscatedMsgUtil.executionPoint(e3));
        } catch (InterruptedException e4) {
            LogMgr.log(1, "802 : cancel occurred.");
            errorScript(901, "", ObfuscatedMsgUtil.executionPoint(e4));
        } catch (JSONException e5) {
            LogMgr.log(1, "804 : JSONException");
            errorScript(900, "", ObfuscatedMsgUtil.executionPoint(e5));
        } catch (Exception e6) {
            LogMgr.log(1, "805 : " + e6.getClass().getSimpleName() + ":" + e6.getMessage());
            errorScript(900, "", ObfuscatedMsgUtil.executionPoint(e6));
        }
    }

    private void closeGpControllerChannel() {
        LogMgr.log(8, "000");
        try {
            GpController gpController = this.mGpController;
            if (gpController != null) {
                gpController.closeChannel();
                LogMgr.log(8, "001 Channel closed.");
            }
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
        }
        LogMgr.log(8, "999");
    }
}
