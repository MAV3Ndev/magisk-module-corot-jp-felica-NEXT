package com.felicanetworks.semc.http;

import com.felicanetworks.semc.sws.SwsRequest;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import com.google.common.net.HttpHeaders;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class HttpCommunicationAgent {
    private static final int CONNECT_TIMEOUT = 60000;
    private static final String KEY_HEADER = "header";
    private static final String KEY_REQUEST_ID = "requestId";
    private static final int READ_TIMEOUT = 30000;
    public static final String SWS_CONTENT_TYPE_APP = "application/json";
    public static final String SWS_CONTENT_TYPE_CHR = "charset=utf-8";
    private volatile boolean mAborted;
    private HttpURLConnection mHttpConnection;
    private Thread mThread;

    public HttpCommunicationAgent() {
        init();
    }

    private void init() {
        LogMgr.log(8, "000");
        synchronized (this) {
            this.mAborted = false;
        }
        LogMgr.log(8, "999");
    }

    public void abort() {
        LogMgr.log(6, "000");
        synchronized (this) {
            Thread thread = this.mThread;
            if (thread != null) {
                try {
                    thread.interrupt();
                    this.mHttpConnection.disconnect();
                } catch (Exception e) {
                    LogMgr.log(2, "700 " + e.getClass().getSimpleName());
                }
                this.mAborted = true;
            } else {
                this.mAborted = true;
            }
        }
        LogMgr.log(6, "999");
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [161=4] */
    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public void doTransaction(String str, IRequest iRequest, IResponse iResponse) throws HttpException {
        LogMgr.log(6, "000");
        synchronized (this) {
            this.mThread = Thread.currentThread();
        }
        try {
            try {
                if (isAborted()) {
                    LogMgr.log(2, "700 isAborted is " + isAborted());
                    throw new HttpException(901, ObfuscatedMsgUtil.executionPoint());
                }
                URL url = new URL(new URI(str).toASCIIString());
                LogMgr.log(4, "001 [access-server] URL.openConnection() in url=" + str);
                this.mHttpConnection = (HttpURLConnection) url.openConnection();
                LogMgr.log(4, "002 [access-server] URL.openConnection() out");
                this.mHttpConnection.setRequestProperty(HttpHeaders.ACCEPT_ENCODING, "identity");
                this.mHttpConnection.setConnectTimeout(60000);
                this.mHttpConnection.setReadTimeout(READ_TIMEOUT);
                this.mHttpConnection.setInstanceFollowRedirects(false);
                LogMgr.performanceIn("SERVER", "Request", "send", "requestId = " + getRequestIdFromRequestData(iRequest));
                iRequest.send(this.mHttpConnection);
                LogMgr.performanceOut("SERVER", "Request", "send");
                LogMgr.performanceIn("SERVER", "Response", "receive");
                iResponse.receive(this.mHttpConnection);
                LogMgr.performanceOut("SERVER", "Response", "receive");
                HttpURLConnection httpURLConnection = this.mHttpConnection;
                if (httpURLConnection != null) {
                    try {
                        httpURLConnection.disconnect();
                        this.mHttpConnection = null;
                    } catch (Exception e) {
                        LogMgr.log(2, "703Fail to disconnect HttpsURLConnection");
                        LogMgr.printStackTrace(9, e);
                    }
                }
                synchronized (this) {
                    this.mThread = null;
                }
                LogMgr.log(6, "999");
            } catch (HttpException e2) {
                LogMgr.log(2, "701 HTTPException=[" + e2.getMessage() + "]");
                throw e2;
            } catch (Exception e3) {
                LogMgr.log(2, "702" + e3.getClass().getSimpleName() + ":" + e3.getMessage() + "]");
                LogMgr.printStackTrace(9, e3);
                boolean zIsAborted = isAborted();
                StringBuilder sb = new StringBuilder("003 isAborted is ");
                sb.append(zIsAborted);
                LogMgr.log(9, sb.toString());
                if (!zIsAborted) {
                    throw new HttpException(300, ObfuscatedMsgUtil.executionPoint());
                }
                throw new HttpException(901, ObfuscatedMsgUtil.executionPoint());
            }
        } catch (Throwable th) {
            HttpURLConnection httpURLConnection2 = this.mHttpConnection;
            if (httpURLConnection2 != null) {
                try {
                    httpURLConnection2.disconnect();
                    this.mHttpConnection = null;
                } catch (Exception e4) {
                    LogMgr.log(2, "703Fail to disconnect HttpsURLConnection");
                    LogMgr.printStackTrace(9, e4);
                }
            }
            synchronized (this) {
                this.mThread = null;
                throw th;
            }
        }
    }

    private boolean isAborted() {
        boolean z;
        LogMgr.log(8, "000");
        synchronized (this) {
            LogMgr.log(8, "999");
            z = this.mAborted;
        }
        return z;
    }

    private String getRequestIdFromRequestData(IRequest iRequest) throws JSONException {
        String requestData;
        LogMgr.log(8, "000");
        String string = "";
        if (iRequest == null) {
            LogMgr.log(2, "700 request data is null.");
            return "";
        }
        if (iRequest instanceof SwsRequest) {
            SwsRequest swsRequest = (SwsRequest) iRequest;
            if (swsRequest.getHttpMethod().equals(SwsRequest.HTTP_METHOD_GET)) {
                LogMgr.log(8, "001 GET request has not request body.");
                return "";
            }
            requestData = swsRequest.getRequestData();
        } else {
            requestData = null;
        }
        if (requestData == null || requestData.isEmpty()) {
            LogMgr.log(2, "701 Request data is null or empty.");
            return "";
        }
        try {
            JSONObject jSONObject = new JSONObject(requestData);
            JSONObject jSONObject2 = jSONObject.has(KEY_HEADER) ? jSONObject.getJSONObject(KEY_HEADER) : null;
            if (jSONObject2 != null && jSONObject2.has(KEY_REQUEST_ID)) {
                string = jSONObject2.getString(KEY_REQUEST_ID);
            }
        } catch (Exception unused) {
        }
        LogMgr.log(8, "999 requestId[" + string + "]");
        return string;
    }
}
