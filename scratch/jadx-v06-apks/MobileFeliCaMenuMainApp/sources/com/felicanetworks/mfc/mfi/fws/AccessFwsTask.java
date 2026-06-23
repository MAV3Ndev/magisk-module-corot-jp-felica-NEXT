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

/* JADX INFO: loaded from: classes.dex */
abstract class AccessFwsTask<Response extends ResponseJson> extends StoppableTaskBase<Result> {
    private static final HashMap<String, Integer> ERROR_CODE_MAP;
    private final Object LOCK_REQUEST_ID;
    protected final FwsClient mFwsClient;
    private String mRequestId;
    private AccessFwsTask<Response>.Result mResult;

    protected abstract String callFws(String str) throws ProtocolException, HttpException;

    protected abstract Response convertResponse(String str) throws JSONException;

    protected abstract RequestJson createRequestJson() throws JSONException;

    protected abstract String getApiHash();

    protected abstract List<String> getValidResultCodeList();

    static {
        HashMap<String, Integer> map = new HashMap<>();
        ERROR_CODE_MAP = map;
        map.put(FwsConst.RESULT_ILLEGAL_LINKAGE_DATA, 209);
        ERROR_CODE_MAP.put(FwsConst.RESULT_EXPIRED_LOGIN_TOKEN_ID, 207);
        ERROR_CODE_MAP.put(FwsConst.RESULT_EXPIRED_LINKAGE_DATA, 210);
        ERROR_CODE_MAP.put(FwsConst.RESULT_INVALID_REQUEST_TOKEN, 226);
        ERROR_CODE_MAP.put(FwsConst.RESULT_INVALID_MESSAGE_ID, -1);
        ERROR_CODE_MAP.put(FwsConst.RESULT_NOT_ACTIVE_CARD, 211);
        ERROR_CODE_MAP.put(FwsConst.RESULT_NOT_EXIST_CARD, 208);
        ERROR_CODE_MAP.put(FwsConst.RESULT_USED_LINKAGE_DATA, 214);
        ERROR_CODE_MAP.put(FwsConst.RESULT_INVALID_ISSUE_INFORMATION, 231);
        ERROR_CODE_MAP.put(FwsConst.RESULT_INVALID_OTP, 232);
        ERROR_CODE_MAP.put(FwsConst.RESULT_NOT_REISSUABLE, 233);
        ERROR_CODE_MAP.put(FwsConst.RESULT_READ_CONDITION_ERROR, 229);
        ERROR_CODE_MAP.put(FwsConst.RESULT_EXIST_UNKNOWN_CARD, 212);
        ERROR_CODE_MAP.put(FwsConst.RESULT_ISSUE_LIMIT_EXCEEDED, 213);
        ERROR_CODE_MAP.put(FwsConst.RESULT_ACCESS_FAILED, 228);
        ERROR_CODE_MAP.put(FwsConst.RESULT_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED, 234);
        ERROR_CODE_MAP.put(FwsConst.RESULT_ISSUE_LIMIT_PER_DEVICE_EXCEEDED, 235);
        ERROR_CODE_MAP.put(FwsConst.RESULT_INSUFFICIENT_CHIP_SPACE, 227);
        ERROR_CODE_MAP.put(FwsConst.RESULT_OTHER_SP_CARD_EXIST, 236);
        ERROR_CODE_MAP.put(FwsConst.RESULT_INITIALIZE_UNAVAILABLE, 239);
        ERROR_CODE_MAP.put(FwsConst.RESULT_NOT_SUPPORTED_DEVICE, 240);
        ERROR_CODE_MAP.put("9000", 204);
        ERROR_CODE_MAP.put(FwsConst.RESULT_CONGESTED, 204);
        ERROR_CODE_MAP.put(FwsConst.RESULT_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED_BY_MFI, 243);
    }

    class Result {
        boolean isSuccess = false;
        Response response = null;
        int errType = 0;
        String errMsg = null;

        Result() {
        }
    }

    AccessFwsTask(int i, FwsClient fwsClient) {
        super(i);
        this.LOCK_REQUEST_ID = new Object();
        this.mResult = new Result();
        this.mFwsClient = fwsClient;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase, com.felicanetworks.mfc.mfi.fws.TaskBase
    public void start() {
        String strCallFws;
        LogMgr.log(5, "%s", "000");
        this.mResult = new Result();
        try {
            RequestJson requestJsonCreateRequestJson = createRequestJson();
            if (isStopped()) {
                LogMgr.log(2, "700 Already has stopped.");
                this.mResult.errType = 215;
                this.mResult.errMsg = null;
                return;
            }
            boolean z = false;
            try {
                LogMgr.log(3, "Call FWS : task=" + getClass().getSimpleName() + ", requestId=" + getRequestId());
                strCallFws = callFws(requestJsonCreateRequestJson.toString());
            } catch (HttpException e) {
                this.mResult.errType = e.getType();
                this.mResult.errMsg = createErrMsg(getApiHash(), getRequestId(), null, this.mResult.errType, e.getMessage());
                strCallFws = null;
                z = true;
            } catch (ProtocolException e2) {
                this.mResult.errType = 202;
                this.mResult.errMsg = e2.getMessage();
                strCallFws = null;
                z = true;
            }
            if (z) {
                return;
            }
            if (isStopped()) {
                LogMgr.log(2, "701 Already has stopped.");
                this.mResult.errType = 215;
                this.mResult.errMsg = null;
            } else {
                fwsPostCheck(strCallFws);
                LogMgr.log(5, "999");
            }
        } catch (JSONException e3) {
            LogMgr.log(2, "%s : JSONException:%s", "700");
            this.mResult.errType = 200;
            this.mResult.errMsg = ObfuscatedMsgUtil.exExecutionPoint(e3);
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
        if (FwsConst.RESULT_EXPIRED_LOGIN_TOKEN_ID.equals(strOptResultCode)) {
            AccountCache.getInstance().clearLoginTokenCache();
        }
        if (!getValidResultCodeList().contains(strOptResultCode)) {
            LogMgr.log(2, "705 Invalid result code : " + strOptResultCode);
            this.mResult.errType = 206;
            this.mResult.errMsg = createErrMsg(getApiHash(), getRequestId(), this.mResult.response, this.mResult.errType, null);
            return;
        }
        if (!this.mResult.response.isSuccessType()) {
            if (ERROR_CODE_MAP.containsKey(strOptResultCode)) {
                this.mResult.errType = ERROR_CODE_MAP.get(strOptResultCode).intValue();
            } else {
                this.mResult.errType = 206;
            }
            this.mResult.errMsg = createErrMsg(getApiHash(), getRequestId(), this.mResult.response, this.mResult.errType, null);
            return;
        }
        this.mResult.isSuccess = true;
        LogMgr.log(5, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult, reason: merged with bridge method [inline-methods] */
    public Result getResult2() {
        return this.mResult;
    }

    public void retryStart(int i) {
        String strRetryFwsPost;
        LogMgr.log(5, "000");
        this.mResult = new Result();
        if (isStopped()) {
            LogMgr.log(2, "700 Already has stopped.");
            this.mResult.errType = 215;
            this.mResult.errMsg = null;
            return;
        }
        boolean z = false;
        try {
            LogMgr.log(3, "Call FWS : task=" + getClass().getSimpleName() + ", requestId=" + getRequestId());
            strRetryFwsPost = this.mFwsClient.retryFwsPost(i);
        } catch (HttpException e) {
            this.mResult.errType = e.getType();
            this.mResult.errMsg = createErrMsg(getApiHash(), getRequestId(), null, this.mResult.errType, e.getMessage());
            strRetryFwsPost = null;
            z = true;
        } catch (ProtocolException e2) {
            this.mResult.errType = 202;
            this.mResult.errMsg = e2.getMessage();
            strRetryFwsPost = null;
            z = true;
        }
        if (z) {
            return;
        }
        if (isStopped()) {
            LogMgr.log(2, "701 Already has stopped.");
            this.mResult.errType = 215;
            this.mResult.errMsg = null;
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

    private String createErrMsg(String str, String str2, Response response, int i, String str3) {
        String string;
        String strOptResultMessage;
        if (204 != i && 206 != i && !ERROR_CODE_MAP.containsValue(Integer.valueOf(i))) {
            return str3;
        }
        if (str3 != null && str3.startsWith(MfiClientCallbackConst.MSG_INVALID_RESPONSE_CODE)) {
            string = str3.substring(22);
        } else {
            string = Integer.toString(200);
        }
        String strOptResultCode = null;
        if (response != null) {
            strOptResultCode = response.optResultCode();
            strOptResultMessage = response.optResultMessage();
        } else {
            strOptResultMessage = null;
        }
        if (StringUtil.isEmpty(strOptResultCode) && StringUtil.isEmpty(strOptResultMessage)) {
            return String.format(MfiClientCallbackConst.MSG_FMT_FWS_ERROR, str, str2, string);
        }
        return String.format(MfiClientCallbackConst.MSG_FMT_FWS_ERROR_WITH_RESULT_CODE, str, str2, string, strOptResultCode, strOptResultMessage);
    }
}
