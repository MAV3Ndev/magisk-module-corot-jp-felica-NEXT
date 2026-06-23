package com.felicanetworks.mfc.tcap.impl;

import com.felicanetworks.mfc.felica.access_control.AccessControlManager;
import com.felicanetworks.mfc.util.LogMgr;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/* JADX INFO: loaded from: classes.dex */
public class TcapCommunicationAgent {
    private static final long DEFAULT_TIMEOUT = 60000;
    private static final long MAX_TIMEOUT = 120000;
    static final String TCAP_CONTENT_TYPE = "application/x-tcap";
    static final String TLAM_CONTENT_TYPE = "application/x-tlam";
    private volatile boolean mAborted;
    private HttpURLConnection mHttpConnection;
    private Thread mThread;
    private long mTimeout;
    private String mUserAgent = null;

    TcapCommunicationAgent() {
        init();
    }

    void init() {
        this.mTimeout = DEFAULT_TIMEOUT;
        this.mAborted = false;
    }

    void abort() {
        Thread thread = this.mThread;
        if (thread != null) {
            try {
                thread.interrupt();
                this.mHttpConnection.disconnect();
            } catch (Exception e) {
                LogMgr.log(1, "%s %s", "700", e.getClass().getSimpleName());
            }
        }
        this.mAborted = true;
    }

    public void doTlamTransaction(String str, TlamRequest tlamRequest, TlamResponse tlamResponse) throws HttpException {
        this.mThread = Thread.currentThread();
        try {
            try {
                if (this.mAborted) {
                    LogMgr.log(1, "%s", "700");
                    throw new HttpException("Interrupted.", true);
                }
                this.mHttpConnection = (HttpURLConnection) new URL(new URI(str).toASCIIString()).openConnection();
                this.mHttpConnection.setRequestProperty("User-Agent", this.mUserAgent);
                this.mHttpConnection.setRequestProperty("Accept-Encoding", "identity");
                this.mHttpConnection.setConnectTimeout((int) this.mTimeout);
                this.mHttpConnection.setReadTimeout((int) this.mTimeout);
                this.mHttpConnection.setInstanceFollowRedirects(false);
                LogMgr.performanceIn(LogMgr.PERFORMANCE_SERVER, "TlamRequest", "send", "url = " + str);
                tlamRequest.send(this.mHttpConnection);
                LogMgr.performanceOut(LogMgr.PERFORMANCE_SERVER, "TlamRequest", "send");
                LogMgr.performanceIn(LogMgr.PERFORMANCE_SERVER, "TlamResponse", "receive");
                tlamResponse.receive(this.mHttpConnection);
                LogMgr.performanceOut(LogMgr.PERFORMANCE_SERVER, "TlamResponse", "receive");
            } catch (HttpException e) {
                LogMgr.log(1, "%s HTTPException", "701");
                throw e;
            } catch (Exception unused) {
                LogMgr.log(1, "%s Exception", "702");
                if (this.mAborted) {
                    LogMgr.log(1, "%s", "702");
                    throw new HttpException("Interrupted.", true);
                }
                LogMgr.log(1, "%s", "702");
                throw new HttpException(AccessControlManager.ERROR_MSG_HTTP_COMMUNICATION_ERROR);
            }
        } finally {
            HttpURLConnection httpURLConnection = this.mHttpConnection;
            if (httpURLConnection != null) {
                try {
                    httpURLConnection.disconnect();
                } catch (Exception unused2) {
                }
            }
            this.mThread = null;
        }
    }

    public void doTcapTransaction(String str, TcapResponse tcapResponse, TcapRequest tcapRequest) throws HttpException {
        this.mThread = Thread.currentThread();
        try {
            try {
                try {
                    if (this.mAborted) {
                        LogMgr.log(1, "%s", "700");
                        throw new HttpException("Interrupted.", true);
                    }
                    this.mHttpConnection = (HttpURLConnection) new URL(new URI(str).toASCIIString()).openConnection();
                    this.mHttpConnection.setRequestProperty("User-Agent", this.mUserAgent);
                    this.mHttpConnection.setRequestProperty("Accept-Encoding", "identity");
                    this.mHttpConnection.setConnectTimeout((int) this.mTimeout);
                    this.mHttpConnection.setReadTimeout((int) this.mTimeout);
                    this.mHttpConnection.setInstanceFollowRedirects(false);
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_SERVER, "TcapResponse", "send", "url = " + str);
                    tcapResponse.send(this.mHttpConnection);
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_SERVER, "TcapResponse", "send");
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_SERVER, "TcapRequest", "receive");
                    tcapRequest.receive(this.mHttpConnection);
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_SERVER, "TcapRequest", "receive");
                } finally {
                    HttpURLConnection httpURLConnection = this.mHttpConnection;
                    if (httpURLConnection != null) {
                        try {
                            httpURLConnection.disconnect();
                        } catch (Exception unused) {
                        }
                    }
                    this.mThread = null;
                }
            } catch (Exception unused2) {
                LogMgr.log(1, "%s Exception", "702");
                if (this.mAborted) {
                    LogMgr.log(1, "%s", "703");
                    throw new HttpException("Interrupted.", true);
                }
                LogMgr.log(1, "%s", "704");
                throw new HttpException(AccessControlManager.ERROR_MSG_HTTP_COMMUNICATION_ERROR);
            }
        } catch (HttpException e) {
            LogMgr.log(1, "%s HTTPException", "701");
            throw e;
        }
    }

    void setUserAgent(String str) {
        this.mUserAgent = str;
    }

    String getUserAgent() {
        return this.mUserAgent;
    }

    public void setTimeout(long j) {
        if (j < 0) {
            j = 0;
        }
        if (j > MAX_TIMEOUT) {
            j = 120000;
        }
        this.mTimeout = j;
    }
}
