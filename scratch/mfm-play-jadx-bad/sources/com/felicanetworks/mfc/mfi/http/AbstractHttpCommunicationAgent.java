package com.felicanetworks.mfc.mfi.http;

import com.felicanetworks.mfc.util.LogMgr;
import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AbstractHttpCommunicationAgent {
    private static final int CONNECT_TIMEOUT = 60000;
    public static final String FWS_CONTENT_TYPE_APP = "application/json";
    public static final String FWS_CONTENT_TYPE_CHR = "charset=utf-8";
    public static final String FWS_CONTENT_TYPE_REQ = "application/json; charset=UTF-8";
    public static final String FWS_CONTENT_TYPE_STREAM = "application/octet-stream";
    private static final int READ_TIMEOUT = 36000;
    private volatile boolean mAborted;
    protected HttpURLConnection mHttpConnection;
    private Thread mThread;
    private String mUserAgent = null;

    protected abstract void setAuthorizationProperty() throws JSONException, IOException;

    public AbstractHttpCommunicationAgent() {
        init();
    }

    private void init() {
        synchronized (this) {
            this.mAborted = false;
        }
    }

    public void abort() {
        synchronized (this) {
            Thread thread = this.mThread;
            if (thread != null) {
                try {
                    thread.interrupt();
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

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public void doTransaction(String url, IRequest request, IResponse response) throws ProtocolException, HttpException {
        synchronized (this) {
            this.mThread = Thread.currentThread();
        }
        try {
            try {
                if (isAborted()) {
                    LogMgr.log(1, "%s", "700");
                    throw new HttpException(215, null, true);
                }
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(new URI(url).toASCIIString()).openConnection();
                this.mHttpConnection = httpURLConnection;
                httpURLConnection.setRequestProperty("User-Agent", this.mUserAgent);
                this.mHttpConnection.setRequestProperty(HttpHeaders.ACCEPT_ENCODING, "identity");
                setAuthorizationProperty();
                this.mHttpConnection.setConnectTimeout(60000);
                this.mHttpConnection.setReadTimeout(READ_TIMEOUT);
                this.mHttpConnection.setInstanceFollowRedirects(false);
                LogMgr.performanceIn("SERVER", "Request", "send", "url = " + url);
                request.send(this.mHttpConnection);
                LogMgr.performanceOut("SERVER", "Request", "send");
                LogMgr.performanceIn("SERVER", "Response", "receive");
                response.receive(this.mHttpConnection);
                LogMgr.performanceOut("SERVER", "Response", "receive");
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
                synchronized (this) {
                    this.mThread = null;
                }
            } catch (Throwable th) {
                HttpURLConnection httpURLConnection3 = this.mHttpConnection;
                if (httpURLConnection3 != null) {
                    try {
                        httpURLConnection3.disconnect();
                        this.mHttpConnection = null;
                    } catch (Exception e2) {
                        LogMgr.log(1, "Fail to disconnect HttpsURLConnection", "706");
                        LogMgr.printStackTrace(7, e2);
                    }
                }
                synchronized (this) {
                    this.mThread = null;
                    throw th;
                }
            }
        } catch (HttpException e3) {
            LogMgr.log(1, "%s HTTPException:%s", "701", e3.getMessage());
            throw e3;
        } catch (ProtocolException e4) {
            LogMgr.log(1, "%s ProtocolException:%s", "702", e4.getMessage());
            throw e4;
        } catch (Exception e5) {
            LogMgr.log(1, "%s %s:%s", "703", e5.getClass().getSimpleName(), e5.getMessage());
            LogMgr.printStackTrace(7, e5);
            if (isAborted()) {
                LogMgr.log(1, "%s", "704");
                throw new HttpException(215, null, true);
            }
            LogMgr.log(1, "%s", "705");
            throw new HttpException(205, null);
        }
    }

    public void setUserAgent(String userAgent) {
        this.mUserAgent = userAgent;
    }

    private boolean isAborted() {
        boolean z;
        synchronized (this) {
            z = this.mAborted;
        }
        return z;
    }
}
