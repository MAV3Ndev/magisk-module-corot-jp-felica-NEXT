package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.AccountCache;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.fws.json.RequestJson;
import com.felicanetworks.mfc.mfi.fws.json.ResponseJson;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
abstract class AccessFwsTask<Response extends ResponseJson> extends StoppableTaskBase<Result> {
    private static final HashMap<String, Integer> ERROR_CODE_MAP;
    private final Object LOCK_REQUEST_ID;
    protected final FwsClient mFwsClient;
    private String mRequestId;
    private AccessFwsTask<Response>.Result mResult;

    protected abstract String callFws(String request) throws ProtocolException, HttpException;

    protected abstract Response convertResponse(String json) throws JSONException;

    protected abstract RequestJson createRequestJson() throws JSONException;

    protected abstract String getApiHash();

    protected abstract List<String> getValidResultCodeList();

    static {
        HashMap<String, Integer> map = new HashMap<>();
        ERROR_CODE_MAP = map;
        map.put(FwsConst.RESULT_ILLEGAL_LINKAGE_DATA, 209);
        map.put("3001", 207);
        map.put(FwsConst.RESULT_EXPIRED_LINKAGE_DATA, 210);
        map.put(FwsConst.RESULT_INVALID_REQUEST_TOKEN, 226);
        map.put(FwsConst.RESULT_INVALID_MESSAGE_ID, -1);
        map.put(FwsConst.RESULT_INVALID_PLAY_INTEGRITY_APP_TOKEN, 226);
        map.put(FwsConst.RESULT_INVALID_PLAY_INTEGRITY_API, 249);
        map.put(FwsConst.RESULT_NOT_ACTIVE_CARD, 211);
        map.put(FwsConst.RESULT_NOT_EXIST_CARD, 208);
        map.put(FwsConst.RESULT_USED_LINKAGE_DATA, 214);
        map.put(FwsConst.RESULT_INVALID_ISSUE_INFORMATION, 231);
        map.put(FwsConst.RESULT_INVALID_OTP, 232);
        map.put(FwsConst.RESULT_NOT_REISSUABLE, 233);
        map.put(FwsConst.RESULT_READ_CONDITION_ERROR, 229);
        map.put(FwsConst.RESULT_EXIST_UNKNOWN_CARD, 212);
        map.put(FwsConst.RESULT_ISSUE_LIMIT_EXCEEDED, 213);
        map.put(FwsConst.RESULT_ACCESS_FAILED, 228);
        map.put(FwsConst.RESULT_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED, 234);
        map.put(FwsConst.RESULT_ISSUE_LIMIT_PER_DEVICE_EXCEEDED, 235);
        map.put(FwsConst.RESULT_INSUFFICIENT_CHIP_SPACE, 227);
        map.put(FwsConst.RESULT_OTHER_SP_CARD_EXIST, 236);
        map.put(FwsConst.RESULT_CANNOT_MEMORY_CLEAR_BY_NOT_INITIALIZE, 247);
        map.put("5081", 239);
        map.put(FwsConst.RESULT_NOT_SUPPORTED_DEVICE, 240);
        map.put(FwsConst.RESULT_NOT_APPLICABLE_DEVICE_FOR_MEMORY_CLEAR, 240);
        map.put("9000", 204);
        map.put("9005", 204);
        map.put(FwsConst.RESULT_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED_BY_MFI, 243);
    }

    class Result {
        boolean isSuccess = false;
        Response response = null;
        int errType = 0;
        String errMsg = null;

        Result() {
        }
    }

    AccessFwsTask(int taskId, FwsClient fwsClient) {
        super(taskId);
        this.LOCK_REQUEST_ID = new Object();
        this.mResult = new Result();
        this.mFwsClient = fwsClient;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase, com.felicanetworks.mfc.mfi.fws.TaskBase
    public void start() {
        AccessFwsTask<Response> accessFwsTask;
        String strCallFws;
        LogMgr.log(5, "%s", "000");
        this.mResult = new Result();
        try {
            RequestJson requestJsonCreateRequestJson = createRequestJson();
            if (isStopped()) {
                LogMgr.log(2, "702 Already has stopped.");
                this.mResult.errType = 215;
                this.mResult.errMsg = null;
                return;
            }
            boolean z = true;
            try {
                LogMgr.log(3, "Call FWS : task=" + getClass().getSimpleName() + ", requestId=" + getRequestId());
                strCallFws = callFws(requestJsonCreateRequestJson.toString());
                z = false;
                accessFwsTask = this;
            } catch (HttpException e) {
                this.mResult.errType = e.getType();
                accessFwsTask = this;
                this.mResult.errMsg = accessFwsTask.createErrMsg(getApiHash(), getRequestId(), null, this.mResult.errType, e.getMessage());
                strCallFws = null;
            } catch (ProtocolException e2) {
                this.mResult.errType = 202;
                this.mResult.errMsg = e2.getMessage();
                accessFwsTask = this;
                strCallFws = null;
            } catch (Exception e3) {
                this.mResult.errType = 200;
                this.mResult.errMsg = ObfuscatedMsgUtil.exExecutionPoint(e3);
                accessFwsTask = this;
                strCallFws = null;
            }
            if (z) {
                return;
            }
            if (isStopped()) {
                LogMgr.log(2, "701 Already has stopped.");
                accessFwsTask.mResult.errType = 215;
                accessFwsTask.mResult.errMsg = null;
            } else {
                fwsPostCheck(strCallFws);
                LogMgr.log(5, "999");
            }
        } catch (JSONException e4) {
            LogMgr.log(2, "700 JSONException:" + e4.getMessage());
            this.mResult.errType = 200;
            this.mResult.errMsg = ObfuscatedMsgUtil.exExecutionPoint(e4);
        } catch (Exception e5) {
            LogMgr.log(2, "701 Exception:" + e5.getClass().getSimpleName(), e5.getMessage());
            this.mResult.errType = 200;
            this.mResult.errMsg = ObfuscatedMsgUtil.exExecutionPoint(e5);
        }
    }

    private void fwsPostCheck(String str) {
        boolean z;
        LogMgr.log(5, "000");
        try {
            this.mResult.response = (Response) convertResponse(str);
            z = false;
        } catch (JSONException e) {
            LogMgr.log(2, "%s JSONException", "703");
            LogMgr.printStackTrace(7, e);
            this.mResult.errType = 202;
            this.mResult.errMsg = MfiClientCallbackConst.MSG_COMMUNICATION_ERROR;
            z = true;
        }
        if (z) {
            return;
        }
        if (this.mResult.response == null) {
            this.mResult.errType = 200;
            this.mResult.errMsg = "Unknown error.";
            return;
        }
        try {
            this.mResult.response.checkMembers();
        } catch (JSONException e2) {
            LogMgr.log(2, "%s JSONException : %s", "704", e2.getMessage());
            LogMgr.printStackTrace(7, e2);
            this.mResult.errType = 202;
            this.mResult.errMsg = MfiClientCallbackConst.MSG_FORMAT_ERROR;
            z = true;
        }
        if (z) {
            return;
        }
        String strOptResultCode = this.mResult.response.optResultCode();
        if ("3001".equals(strOptResultCode)) {
            AccountCache.getInstance().clearLoginTokenCache();
        }
        if (!getValidResultCodeList().contains(strOptResultCode)) {
            LogMgr.log(2, "705 Invalid result code : " + strOptResultCode);
            this.mResult.errType = 206;
            this.mResult.errMsg = createErrMsg(getApiHash(), getRequestId(), this.mResult.response, this.mResult.errType, null);
            return;
        }
        if (!this.mResult.response.isSuccessType()) {
            HashMap<String, Integer> map = ERROR_CODE_MAP;
            if (map.containsKey(strOptResultCode)) {
                this.mResult.errType = map.get(strOptResultCode).intValue();
            } else {
                this.mResult.errType = 206;
            }
            this.mResult.errMsg = createErrMsg(getApiHash(), getRequestId(), this.mResult.response, this.mResult.errType, null);
            return;
        }
        this.mResult.isSuccess = true;
        LogMgr.log(5, "%s", "999");
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    /* JADX DEBUG: Return type fixed from 'com.felicanetworks.mfc.mfi.fws.AccessFwsTask<Response>$Result' to match base method */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult, reason: merged with bridge method [inline-methods] */
    public Result getResult2() {
        return this.mResult;
    }

    public void retryStart(int retryCount) {
        AccessFwsTask<Response> accessFwsTask;
        String strRetryFwsPost;
        LogMgr.log(5, "000");
        this.mResult = new Result();
        if (isStopped()) {
            LogMgr.log(2, "700 Already has stopped.");
            this.mResult.errType = 215;
            this.mResult.errMsg = null;
            return;
        }
        boolean z = true;
        try {
            LogMgr.log(3, "Call FWS : task=" + getClass().getSimpleName() + ", requestId=" + getRequestId());
            strRetryFwsPost = this.mFwsClient.retryFwsPost(retryCount);
            z = false;
            accessFwsTask = this;
        } catch (HttpException e) {
            this.mResult.errType = e.getType();
            accessFwsTask = this;
            this.mResult.errMsg = accessFwsTask.createErrMsg(getApiHash(), getRequestId(), null, this.mResult.errType, e.getMessage());
            strRetryFwsPost = null;
        } catch (ProtocolException e2) {
            this.mResult.errType = 202;
            this.mResult.errMsg = e2.getMessage();
            accessFwsTask = this;
            strRetryFwsPost = null;
        }
        if (z) {
            return;
        }
        if (isStopped()) {
            LogMgr.log(2, "701 Already has stopped.");
            accessFwsTask.mResult.errType = 215;
            accessFwsTask.mResult.errMsg = null;
        } else {
            fwsPostCheck(strRetryFwsPost);
            LogMgr.log(5, "999");
        }
    }

    protected String createRequestId() {
        String strCreateRequestId;
        synchronized (this.LOCK_REQUEST_ID) {
            strCreateRequestId = FwsParamCreator.createRequestId();
            this.mRequestId = strCreateRequestId;
        }
        return strCreateRequestId;
    }

    protected String getRequestId() {
        String str;
        synchronized (this.LOCK_REQUEST_ID) {
            str = this.mRequestId;
        }
        return str;
    }

    private String createErrMsg(String api, String reqId, Response response, int errType, String errMsg) {
        String string;
        String strOptResultCode;
        String strOptResultMessage;
        if (204 != errType && 206 != errType && 205 != errType && !ERROR_CODE_MAP.containsValue(Integer.valueOf(errType))) {
            return errMsg;
        }
        if (errMsg != null && errMsg.startsWith("Invalid response code:")) {
            string = errMsg.substring(22);
        } else {
            string = Integer.toString(200);
        }
        if (response != null) {
            strOptResultCode = response.optResultCode();
            strOptResultMessage = response.optResultMessage();
        } else {
            strOptResultCode = null;
            strOptResultMessage = null;
        }
        return (StringUtil.isEmpty(strOptResultCode) && StringUtil.isEmpty(strOptResultMessage)) ? String.format(MfiClientCallbackConst.MSG_FMT_FWS_ERROR, api, reqId, string) : String.format(MfiClientCallbackConst.MSG_FMT_FWS_ERROR_WITH_RESULT_CODE, api, reqId, string, strOptResultCode, strOptResultMessage);
    }
}
