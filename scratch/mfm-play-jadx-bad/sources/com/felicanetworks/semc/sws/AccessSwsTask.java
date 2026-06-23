package com.felicanetworks.semc.sws;

import com.felicanetworks.semc.SemClientCallbackConst;
import com.felicanetworks.semc.SemClientException;
import com.felicanetworks.semc.http.HttpException;
import com.felicanetworks.semc.sws.json.RequestJson;
import com.felicanetworks.semc.sws.json.ResponseJson;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import com.felicanetworks.semc.util.Processor;
import com.felicanetworks.semc.util.SettingInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
abstract class AccessSwsTask<Response extends ResponseJson> extends StoppableTaskBase {
    protected static final HashMap<String, Integer> ERROR_CODE_MAP;
    private static final int MAX_CLIENT_CONTROL_INFO_VALUE_LENGTH = 1024;
    private static final String URL_PROTOCOL = "https://";
    private final Object LOCK_CLIENT_ID;
    private final Object LOCK_REQUEST_ID;
    private String mClientId;
    protected final String mConnectionUrl;
    private boolean mIsCalledFromSemcBackground;
    private String mRequestId;
    private AccessSwsTask<Response>.Result mResult;
    protected List<Integer> mRetryInterval;
    protected final SwsClient mSwsClient;

    protected abstract String callSws(String str, String str2) throws HttpException, SemClientException;

    protected abstract Response convertResponse(String str) throws JSONException;

    protected abstract RequestJson createRequestJson() throws JSONException;

    protected abstract List<String> getValidResultCodeList();

    static {
        HashMap<String, Integer> map = new HashMap<>();
        ERROR_CODE_MAP = map;
        map.put("1000", Integer.valueOf(SemClientCallbackConst.TYPE_SERVER_RESPONSE_RETRY_REQUEST));
        map.put("2000", Integer.valueOf(SemClientCallbackConst.TYPE_SERVER_RESPONSE_ILLEGAL_ARGUMENT_ERROR));
        map.put("3000", 900);
        map.put("4000", 900);
        map.put("9000", 400);
        map.put("9001", 401);
        map.put("9005", 400);
    }

    class Result {
        boolean isSuccess = false;
        boolean isRetry = false;
        boolean isStopped = false;
        Response response = null;
        int errType = 0;
        String additionalErrorInfo = null;
        String errMsg = null;

        Result() {
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    @Override // com.felicanetworks.semc.sws.StoppableTaskBase, com.felicanetworks.semc.sws.TaskBase
    public void start() {
        String strProcess;
        LogMgr.log(7, "000");
        this.mResult = new Result();
        try {
            RequestJson requestJsonCreateRequestJson = createRequestJson();
            LogMgr.log(7, "001 before processed request=" + requestJsonCreateRequestJson);
            if (requestJsonCreateRequestJson != null) {
                Processor processor = new Processor();
                processor.loadWithCheck();
                strProcess = processor.process(requestJsonCreateRequestJson);
                if (strProcess == null) {
                    throw new JSONException("processedRequestData is null");
                }
            } else {
                strProcess = null;
            }
            if (isStopped()) {
                setStoppedResult();
                return;
            }
            try {
                LogMgr.log(5, "Call SWS : task=" + getClass().getSimpleName() + ", requestId=" + getRequestId() + ", clientId=" + getClientId());
                String strCallSws = callSws(this.mConnectionUrl, strProcess);
                if (isStopped()) {
                    setStoppedResult();
                } else {
                    swsResponseCheck(strCallSws);
                    LogMgr.log(7, "999");
                }
            } catch (SemClientException e) {
                LogMgr.log(2, "702 : SemClientException:" + e.getMessage());
                LogMgr.printStackTrace(8, e);
                this.mResult.errType = e.getErrorCode();
                this.mResult.additionalErrorInfo = "";
                this.mResult.errMsg = e.getMessage();
            } catch (HttpException e2) {
                LogMgr.log(2, "701 : HttpException:" + e2.getMessage());
                LogMgr.printStackTrace(8, e2);
                if (e2.getType() == 500) {
                    this.mResult.isRetry = true;
                }
                this.mResult.errType = e2.getType();
                this.mResult.additionalErrorInfo = "";
                this.mResult.errMsg = ObfuscatedMsgUtil.executionPoint(e2);
            }
        } catch (JSONException e3) {
            LogMgr.log(2, "700 : JSONException:" + e3.getMessage());
            LogMgr.printStackTrace(8, e3);
            this.mResult.errType = 900;
            this.mResult.additionalErrorInfo = "";
            this.mResult.errMsg = ObfuscatedMsgUtil.executionPoint(e3);
        }
    }

    AccessSwsTask(int i, SwsClient swsClient, boolean z) {
        super(i);
        this.mRetryInterval = new ArrayList();
        this.LOCK_REQUEST_ID = new Object();
        this.LOCK_CLIENT_ID = new Object();
        this.mResult = new Result();
        LogMgr.log(7, "000 taskId[" + i + "]");
        this.mSwsClient = swsClient;
        this.mConnectionUrl = URL_PROTOCOL + SettingInfo.getServerConnectionUrl();
        this.mIsCalledFromSemcBackground = z;
        LogMgr.log(7, "999");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    private void swsResponseCheck(String str) {
        LogMgr.log(7, "000");
        try {
            this.mResult.response = (Response) convertResponse(str);
            if (this.mResult.response == null) {
                LogMgr.log(2, "701 Response is null");
                this.mResult.errType = 900;
                this.mResult.additionalErrorInfo = "";
                this.mResult.errMsg = ObfuscatedMsgUtil.executionPoint();
                return;
            }
            try {
                this.mResult.response.checkHeaderMembers();
                try {
                    String resultCode = this.mResult.response.getResultCode();
                    boolean zIsSuccessType = this.mResult.response.isSuccessType();
                    if (zIsSuccessType) {
                        try {
                            this.mResult.response.checkMembers();
                        } catch (JSONException e) {
                            LogMgr.log(2, "702 JSONException : " + e.getMessage());
                            LogMgr.printStackTrace(8, e);
                            this.mResult.errType = SemClientCallbackConst.TYPE_HTTP_PROTOCOL_ERROR;
                            this.mResult.additionalErrorInfo = "";
                            this.mResult.errMsg = ObfuscatedMsgUtil.executionPoint(e);
                            return;
                        }
                    }
                    if (!getValidResultCodeList().contains(resultCode)) {
                        LogMgr.log(2, "704 Invalid result code : " + resultCode);
                        this.mResult.errType = SemClientCallbackConst.TYPE_SERVER_ILLEGAL_RESPONSE_ERROR;
                        this.mResult.additionalErrorInfo = "";
                        this.mResult.errMsg = ObfuscatedMsgUtil.executionPoint();
                        return;
                    }
                    if (zIsSuccessType) {
                        this.mResult.isSuccess = true;
                        LogMgr.log(7, "999");
                        return;
                    }
                    try {
                        HashMap<String, Integer> map = ERROR_CODE_MAP;
                        if (map.containsKey(resultCode)) {
                            if (resultCode.equals("1000")) {
                                this.mResult.isRetry = true;
                                LogMgr.log(2, "705 retry call sws");
                            }
                            Integer num = map.get(resultCode);
                            if (num == null) {
                                throw new Exception(ObfuscatedMsgUtil.executionPoint());
                            }
                            int iCheckAndConvertNoSendingServerLogErrorTypeIfNeeded = checkAndConvertNoSendingServerLogErrorTypeIfNeeded(this.mIsCalledFromSemcBackground, resultCode, num.intValue());
                            Integer numValueOf = Integer.valueOf(iCheckAndConvertNoSendingServerLogErrorTypeIfNeeded);
                            LogMgr.log(2, "706 errorCode from result code : " + numValueOf);
                            AccessSwsTask<Response>.Result result = this.mResult;
                            numValueOf.getClass();
                            result.errType = iCheckAndConvertNoSendingServerLogErrorTypeIfNeeded;
                        } else {
                            LogMgr.log(2, "707 Invalid result code : " + resultCode);
                            this.mResult.errType = SemClientCallbackConst.TYPE_SERVER_ILLEGAL_RESPONSE_ERROR;
                        }
                        this.mResult.additionalErrorInfo = "";
                        this.mResult.errMsg = ObfuscatedMsgUtil.executionPoint();
                    } catch (Exception e2) {
                        LogMgr.log(2, "708 Exception : " + e2.getMessage());
                        LogMgr.printStackTrace(8, e2);
                        this.mResult.errType = 900;
                        this.mResult.additionalErrorInfo = "";
                        this.mResult.errMsg = ObfuscatedMsgUtil.executionPoint(e2);
                    }
                } catch (JSONException e3) {
                    LogMgr.log(2, "703 JSONException : " + e3.getMessage());
                    LogMgr.printStackTrace(8, e3);
                    this.mResult.errType = SemClientCallbackConst.TYPE_HTTP_PROTOCOL_ERROR;
                    this.mResult.additionalErrorInfo = "";
                    this.mResult.errMsg = ObfuscatedMsgUtil.executionPoint(e3);
                }
            } catch (JSONException e4) {
                LogMgr.log(2, "702 JSONException : " + e4.getMessage());
                LogMgr.printStackTrace(8, e4);
                this.mResult.errType = SemClientCallbackConst.TYPE_HTTP_PROTOCOL_ERROR;
                this.mResult.additionalErrorInfo = "";
                this.mResult.errMsg = ObfuscatedMsgUtil.executionPoint(e4);
            }
        } catch (JSONException e5) {
            LogMgr.log(2, "700 JSONException:" + e5.getMessage());
            LogMgr.printStackTrace(8, e5);
            this.mResult.errType = SemClientCallbackConst.TYPE_HTTP_PROTOCOL_ERROR;
            this.mResult.additionalErrorInfo = "";
            this.mResult.errMsg = ObfuscatedMsgUtil.executionPoint(e5);
        }
    }

    private void setStoppedResult() {
        LogMgr.log(8, "000");
        LogMgr.log(8, "001 Already has stopped.");
        this.mResult.errType = 901;
        this.mResult.additionalErrorInfo = "";
        this.mResult.errMsg = ObfuscatedMsgUtil.executionPoint();
        LogMgr.log(8, "999");
    }

    public AccessSwsTask<Response>.Result getResult() {
        LogMgr.log(7, "000");
        LogMgr.log(7, "999");
        return this.mResult;
    }

    public void retryStart(int i, boolean z) {
        LogMgr.log(7, "000");
        this.mResult = new Result();
        if (isStopped()) {
            setStoppedResult();
            return;
        }
        try {
            LogMgr.log(5, "Call SWS : task=" + getClass().getSimpleName() + ", requestId=" + getRequestId());
            String strRetryWebApi = this.mSwsClient.retryWebApi(i, z);
            if (isStopped()) {
                setStoppedResult();
            } else {
                swsResponseCheck(strRetryWebApi);
                LogMgr.log(7, "999");
            }
        } catch (HttpException e) {
            LogMgr.log(2, "700 HttpException : " + e.getMessage());
            LogMgr.printStackTrace(8, e);
            if (e.getType() == 500) {
                this.mResult.isRetry = true;
            }
            this.mResult.errType = e.getType();
            this.mResult.additionalErrorInfo = "";
            this.mResult.errMsg = ObfuscatedMsgUtil.executionPoint();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x004e A[LOOP:1: B:18:0x004c->B:19:0x004e, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setRetryInterval(String str, int[] iArr) {
        int i;
        LogMgr.log(7, "000");
        this.mRetryInterval.clear();
        if (str != null && str.length() <= 1024) {
            StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
            while (stringTokenizer.hasMoreTokens()) {
                try {
                    i = Integer.parseInt(stringTokenizer.nextToken());
                } catch (NumberFormatException unused) {
                    LogMgr.log(8, "001 Failed to parse int");
                    this.mRetryInterval.clear();
                }
                if (i >= 0) {
                    this.mRetryInterval.add(Integer.valueOf(i));
                } else {
                    this.mRetryInterval.clear();
                    for (int i2 : iArr) {
                        this.mRetryInterval.add(Integer.valueOf(i2));
                    }
                }
            }
        } else {
            while (i < r5) {
            }
        }
        LogMgr.log(7, "999 retryCount=" + this.mRetryInterval.size());
    }

    protected String createRequestId() {
        String str;
        synchronized (this.LOCK_REQUEST_ID) {
            LogMgr.log(7, "000");
            this.mRequestId = SwsParamCreator.createRequestId();
            LogMgr.log(7, "999 mRequestId[" + this.mRequestId + "]");
            str = this.mRequestId;
        }
        return str;
    }

    protected void setClientId(String str) {
        synchronized (this.LOCK_CLIENT_ID) {
            LogMgr.log(7, "000");
            this.mClientId = str;
            LogMgr.log(7, "999 mClientId[" + this.mClientId + "]");
        }
    }

    protected String getRequestId() {
        String str;
        synchronized (this.LOCK_REQUEST_ID) {
            LogMgr.log(7, "000");
            LogMgr.log(7, "999");
            str = this.mRequestId;
        }
        return str;
    }

    protected String getClientId() {
        String str;
        synchronized (this.LOCK_CLIENT_ID) {
            LogMgr.log(7, "000");
            LogMgr.log(7, "999");
            str = this.mClientId;
        }
        return str;
    }

    protected int checkAndConvertNoSendingServerLogErrorTypeIfNeeded(boolean z, String str, int i) {
        LogMgr.log(7, "000");
        LogMgr.log(8, "isCalledFromSemcBackground=" + z + ", resultCode=" + str + ", currentErrorType=" + i);
        if (!z) {
            LogMgr.log(8, "Not called from semc background. No conversion.");
            return i;
        }
        if (str == null || str.isEmpty()) {
            LogMgr.log(8, "No result code. No conversion.");
            return i;
        }
        if ("2000".equals(str) || "3000".equals(str) || "4000".equals(str)) {
            LogMgr.log(8, "Convert error type to no sending server log.");
            i = 602;
        } else {
            LogMgr.log(8, "Not target result code. No conversion");
        }
        LogMgr.log(7, "999 retErrType=" + i);
        return i;
    }
}
