package com.felicanetworks.semc.sws;

import com.felicanetworks.semc.DataManager;
import com.felicanetworks.semc.http.HttpCommunicationAgent;
import com.felicanetworks.semc.http.HttpException;
import com.felicanetworks.semc.http.IRequest;
import com.felicanetworks.semc.http.IResponse;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
class SwsClient {
    private static final String KEY_CLIENT_ID = "clientId";
    private static final String KEY_HEADER = "header";
    private static final String SEM_CLIENT_APP_NAME = "SemClient";
    public static final String SWS_CONTENT_TYPE_REQ = "application/jose; charset=UTF-8";
    private static final String URL_CL = "cl";
    public static final String URL_SERVICE_ID = "00000000";
    private static final String URL_V1 = "v1";
    private HttpCommunicationAgent mAgent;
    private DataManager mDataManager;
    private String mApiName = null;
    private String mLastUrl = null;
    private String mLastHttpMethod = null;
    private SwsRequestHeader mLastRequestHeader = null;
    private String mLastRequestData = null;
    private boolean mIsStopped = false;

    SwsClient(DataManager dataManager) {
        LogMgr.log(7, "000");
        this.mDataManager = dataManager;
        LogMgr.log(7, "999");
    }

    public void stop() {
        LogMgr.log(7, "000");
        synchronized (this) {
            this.mIsStopped = true;
            if (this.mAgent != null) {
                LogMgr.log(8, "001 mAgent is not null.");
                this.mAgent.abort();
            }
        }
        LogMgr.log(7, "999");
    }

    public String createUrl(String str, String str2, String str3, String str4) {
        LogMgr.log(7, "000 connectionUrl=[" + str + "] seId=[" + str2 + "]serviceId=[" + str3 + "] apiName=[" + str4 + "]");
        LogMgr.log(7, "999");
        this.mApiName = str4;
        return String.format(Locale.getDefault(), "%s/%s/%s/%s/%s/%s", str, URL_CL, URL_V1, str2, str3, str4);
    }

    public String runWebApi(String str, String str2, String str3, Boolean bool, Boolean bool2) throws HttpException {
        LogMgr.log(7, "000 requestData=[" + str + "] url=[" + str2 + "] httpMethod=[" + str3 + "] needSetContentType=[" + bool + "]isRemotelyStarted=[" + bool2 + "]");
        SwsRequestHeader swsRequestHeaderCreateRequestHeader = createRequestHeader(bool);
        LogMgr.log(7, "999");
        return doRunWebApi(str2, str3, swsRequestHeaderCreateRequestHeader, str, true, 0, bool2.booleanValue());
    }

    public String retryWebApi(int i, boolean z) throws HttpException {
        LogMgr.log(7, "000 retryCount=[" + i + "]isRemotelyStarted=[" + z + "]");
        LogMgr.log(7, "999");
        return doRunWebApi(this.mLastUrl, this.mLastHttpMethod, this.mLastRequestHeader, this.mLastRequestData, false, i, z);
    }

    private String doRunWebApi(String str, String str2, SwsRequestHeader swsRequestHeader, String str3, Boolean bool, int i, boolean z) throws Exception {
        LogMgr.log(8, "000");
        if (bool.booleanValue()) {
            this.mLastUrl = str;
            this.mLastHttpMethod = str2;
            this.mLastRequestHeader = swsRequestHeader;
            this.mLastRequestData = str3;
        }
        try {
            SwsRequest swsRequest = new SwsRequest(this.mLastHttpMethod);
            this.mLastRequestHeader.setRetryCount(i);
            this.mLastRequestHeader.setRemotelyStarted(z);
            swsRequest.setRequestHeader(this.mLastRequestHeader);
            if (this.mLastHttpMethod.equals(SwsRequest.HTTP_METHOD_POST)) {
                swsRequest.setRequestData(this.mLastRequestData);
            } else {
                swsRequest.setRequestData(null);
            }
            SwsResponse swsResponse = new SwsResponse(this.mApiName);
            swsResponse.setIsRemotelyStarted(z);
            String strAccessSws = accessSws(this.mLastUrl, swsRequest, swsResponse);
            LogMgr.log(8, "999");
            return strAccessSws;
        } catch (HttpException e) {
            LogMgr.log(2, "700 HTTPException=[" + e.getMessage() + "]");
            throw e;
        } catch (Exception e2) {
            LogMgr.log(2, "701 Exception Error." + e2.getMessage());
            throw e2;
        }
    }

    private SwsRequestHeader createRequestHeader(Boolean bool) {
        LogMgr.log(8, "000");
        SwsRequestHeader swsRequestHeader = new SwsRequestHeader();
        swsRequestHeader.setUserAgent(createUserAgent());
        swsRequestHeader.setContentType(getContentType(bool));
        swsRequestHeader.setClientId(this.mDataManager.getClientId());
        swsRequestHeader.setClientVersion(this.mDataManager.getSemClientVersion());
        swsRequestHeader.setSemClientVersionAdditionalInfo(this.mDataManager.getSemClientVersionAdditionalInfo());
        swsRequestHeader.setProfileId(this.mDataManager.getProfileId());
        swsRequestHeader.setIntegrityVerificationToken(this.mDataManager.getPlayIntegrityToken());
        swsRequestHeader.setIntegrityVerificationUniqueValue(this.mDataManager.getIntegrityVerificationUniqueValue());
        swsRequestHeader.setPackageName(this.mDataManager.getPackageName());
        LogMgr.log(8, "999");
        return swsRequestHeader;
    }

    private String createUserAgent() {
        LogMgr.log(8, "000");
        LogMgr.log(8, "999");
        if (this.mDataManager.getSemClientVersionAdditionalInfo() != null && !this.mDataManager.getSemClientVersionAdditionalInfo().isEmpty()) {
            return String.format("%s/%s.%s (Android %s;%s;%s)", SEM_CLIENT_APP_NAME, this.mDataManager.getSemClientVersion(), this.mDataManager.getSemClientVersionAdditionalInfo(), this.mDataManager.getAndroidOsVersion(), this.mDataManager.getSepId(), this.mDataManager.getDeviceName());
        }
        return String.format("%s/%s (Android %s;%s;%s)", SEM_CLIENT_APP_NAME, this.mDataManager.getSemClientVersion(), this.mDataManager.getAndroidOsVersion(), this.mDataManager.getSepId(), this.mDataManager.getDeviceName());
    }

    private String getContentType(Boolean bool) {
        LogMgr.log(8, "000 needContentType=[" + bool + "]");
        String str = bool.booleanValue() ? "application/jose; charset=UTF-8" : null;
        LogMgr.log(8, "999");
        return str;
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [317=4] */
    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    private String accessSws(String str, IRequest iRequest, IResponse iResponse) throws HttpException {
        HttpCommunicationAgent httpCommunicationAgent;
        LogMgr.log(8, "000");
        synchronized (this) {
            if (this.mIsStopped) {
                LogMgr.log(2, "700 Already has stopped.");
                throw new HttpException(901, ObfuscatedMsgUtil.executionPoint());
            }
            if (this.mAgent != null) {
                LogMgr.log(2, "701 Running operation.");
                throw new HttpException(900, ObfuscatedMsgUtil.executionPoint());
            }
            httpCommunicationAgent = new HttpCommunicationAgent();
            this.mAgent = httpCommunicationAgent;
        }
        try {
            try {
                try {
                    httpCommunicationAgent.doTransaction(str, iRequest, iResponse);
                    synchronized (this) {
                        this.mAgent = null;
                    }
                    LogMgr.log(8, "999");
                    return iResponse.getData();
                } catch (Exception e) {
                    LogMgr.log(2, "703 Exception Error." + e.getMessage());
                    throw e;
                }
            } catch (HttpException e2) {
                LogMgr.log(2, "702 HTTPException=[" + e2.getMessage() + "]");
                throw e2;
            }
        } catch (Throwable th) {
            synchronized (this) {
                this.mAgent = null;
                throw th;
            }
        }
    }

    private String getClientIdFromRequestData(String str) throws JSONException {
        LogMgr.log(8, "000");
        String string = null;
        if (str == null || str.isEmpty()) {
            LogMgr.log(8, "001 Request data is null or empty.");
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObject2 = jSONObject.has(KEY_HEADER) ? jSONObject.getJSONObject(KEY_HEADER) : null;
            if (jSONObject2 != null && jSONObject2.has("clientId")) {
                string = jSONObject2.getString("clientId");
            }
        } catch (Exception e) {
            LogMgr.log(8, "002 Failed to get clientId from request data. e[" + e + "]");
        }
        LogMgr.log(8, "999");
        return string;
    }
}
