package com.felicanetworks.mfc.mfi.http;

import com.amazonaws.http.HttpHeader;
import com.felicanetworks.mfc.util.LogMgr;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/* JADX INFO: loaded from: classes.dex */
public class HttpCommunicationAgent {
    private static final int CONNECT_TIMEOUT = 60000;
    public static final String FWS_CONTENT_TYPE_APP = "application/json";
    public static final String FWS_CONTENT_TYPE_CHR = "charset=utf-8";
    public static final String FWS_CONTENT_TYPE_REQ = "application/json; charset=UTF-8";
    public static final String FWS_CONTENT_TYPE_STREAM = "application/octet-stream";
    private static final int READ_TIMEOUT = 36000;
    private volatile boolean mAborted;
    private HttpURLConnection mHttpConnection;
    private Thread mThread;
    private String mUserAgent = null;

    public HttpCommunicationAgent() {
        init();
    }

    private void init() {
        synchronized (this) {
            this.mAborted = false;
        }
    }

    public void abort() {
        synchronized (this) {
            if (this.mThread != null) {
                try {
                    this.mThread.interrupt();
                    this.mHttpConnection.disconnect();
                } catch (Exception e) {
                    LogMgr.log(1, "%s %s", "700", e.getClass().getSimpleName());
                }
                this.mAborted = true;
            } else {
                this.mAborted = true;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void doTransaction(String str, IRequest iRequest, IResponse iResponse) throws ProtocolException, HttpException {
        synchronized (this) {
            this.mThread = Thread.currentThread();
        }
        try {
            try {
                try {
                    if (isAborted()) {
                        LogMgr.log(1, "%s", "700");
                        throw new HttpException(215, null, true);
                    }
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(new URI(str).toASCIIString()).openConnection();
                    this.mHttpConnection = httpURLConnection;
                    httpURLConnection.setRequestProperty(HttpHeader.USER_AGENT, this.mUserAgent);
                    this.mHttpConnection.setRequestProperty("Accept-Encoding", "identity");
                    this.mHttpConnection.setConnectTimeout(60000);
                    this.mHttpConnection.setReadTimeout(READ_TIMEOUT);
                    this.mHttpConnection.setInstanceFollowRedirects(false);
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_SERVER, "Request", "send", "url = " + str);
                    iRequest.send(this.mHttpConnection);
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_SERVER, "Request", "send");
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_SERVER, "Response", "receive");
                    iResponse.receive(this.mHttpConnection);
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_SERVER, "Response", "receive");
                } finally {
                    HttpURLConnection httpURLConnection2 = this.mHttpConnection;
                    if (httpURLConnection2 != null) {
                        try {
                            httpURLConnection2.disconnect();
                            this.mHttpConnection = null;
                        } catch (Exception e) {
                            LogMgr.log(1, "Fail to disconnect HttpsURLConnection", "706");
                            LogMgr.printStackTrace(7, e);
                        }
                    }
                    this.mThread = null;
                }
            } catch (ProtocolException e2) {
                LogMgr.log(1, "%s ProtocolException:%s", "702", e2.getMessage());
                throw e2;
            }
        } catch (HttpException e3) {
            LogMgr.log(1, "%s HTTPException:%s", "701", e3.getMessage());
            throw e3;
        } catch (Exception e4) {
            LogMgr.log(1, "%s %s:%s", "703", e4.getClass().getSimpleName(), e4.getMessage());
            LogMgr.printStackTrace(7, e4);
            if (isAborted()) {
                LogMgr.log(1, "%s", "704");
                throw new HttpException(215, null, true);
            }
            LogMgr.log(1, "%s", "705");
            throw new HttpException(205, null);
        }
    }

    public void setUserAgent(String str) {
        this.mUserAgent = str;
    }

    private boolean isAborted() {
        boolean z;
        synchronized (this) {
            z = this.mAborted;
        }
        return z;
    }
}
