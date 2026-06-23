package com.felicanetworks.common.cmnctrl.net;

import com.felicanetworks.common.cmnlib.AppContext;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;

/* JADX INFO: loaded from: classes.dex */
public class NetworkAccess implements FunctionCodeInterface {
    private static final String CHARCODE = "UTF-8";
    private static final String CONNECT_TYPE_GET = "GET";
    private static final String CONTENT_LENGTH_KEY = "content-length";
    public static final String CONTENT_TYPE_KEY = "content-type";
    private AppContext context;
    private boolean isCancel = false;
    private HttpURLConnection urlconn = null;

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 3;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 4;
    }

    public NetworkAccess(AppContext appContext) {
        this.context = appContext;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:119:0x01f3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:122:0x01ea A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:126:0x01ef A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:130:0x01e5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v15, types: [java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v18 */
    /* JADX WARN: Type inference failed for: r7v19 */
    /* JADX WARN: Type inference failed for: r7v20 */
    /* JADX WARN: Type inference failed for: r7v21 */
    /* JADX WARN: Type inference failed for: r7v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v8, types: [java.io.InputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.felicanetworks.common.cmnctrl.net.NetworkAccessResponseData connect(com.felicanetworks.common.cmnctrl.net.NetworkAccessRequestData r13) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 515
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.common.cmnctrl.net.NetworkAccess.connect(com.felicanetworks.common.cmnctrl.net.NetworkAccessRequestData):com.felicanetworks.common.cmnctrl.net.NetworkAccessResponseData");
    }

    private NetworkAccessException createNetworkAccessException(Exception exc) {
        if (exc instanceof InterruptedException) {
            return new NetworkAccessException(exc, this.context.logMgr.out(LogMgr.CatExp.WAR, this, exc), 5);
        }
        if (exc instanceof SocketTimeoutException) {
            return new NetworkAccessException(exc, this.context.logMgr.out(LogMgr.CatExp.WAR, this, exc), 3);
        }
        return new NetworkAccessException(exc, this.context.logMgr.out(LogMgr.CatExp.WAR, this, exc), 4);
    }

    public void cancel() {
        this.isCancel = true;
        synchronized (this) {
            if (this.urlconn != null) {
                this.urlconn.disconnect();
                this.urlconn = null;
            }
        }
    }
}
