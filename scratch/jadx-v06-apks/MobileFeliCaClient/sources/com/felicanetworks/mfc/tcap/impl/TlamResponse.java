package com.felicanetworks.mfc.tcap.impl;

import com.felicanetworks.mfc.felica.access_control.AccessControlManager;
import com.felicanetworks.mfc.felica.util.ByteBuffer;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Hashtable;

/* JADX INFO: loaded from: classes.dex */
public class TlamResponse {
    private static final int MAX_BUFFER_SIZE = 65540;
    private static final int READ_BUFFER_SIZE = 4096;
    private static final int TLAM_EQUAL_WAIT = 2;
    private static final int TLAM_KEYWORD_WAIT = 1;
    private static final int TLAM_SKIP_TO_EOL = 4;
    private static final int TLAM_VALUE_WAIT = 3;
    private static StringBuffer sBuf = new StringBuffer();
    private String mCookie;
    private ByteBuffer mBuffer = new ByteBuffer(MAX_BUFFER_SIZE);
    private Hashtable<String, String> mValueTable = new Hashtable<>();

    public void reset() {
        this.mBuffer.setLength(0);
        this.mValueTable.clear();
    }

    void receive(HttpURLConnection httpURLConnection) throws Throwable {
        byte[] bArr = new byte[4096];
        BufferedInputStream bufferedInputStream = null;
        try {
            try {
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode != 200) {
                    LogMgr.log(1, "%s", "700");
                    throw new HttpException("Invalid response code: " + responseCode);
                }
                String contentType = httpURLConnection.getContentType();
                if (contentType == null || contentType.indexOf("application/x-tlam") < 0) {
                    LogMgr.log(1, "%s", "703");
                    throw new HttpException("Invalid content-type: " + contentType);
                }
                this.mCookie = httpURLConnection.getHeaderField("Set-Cookie");
                BufferedInputStream bufferedInputStream2 = new BufferedInputStream(httpURLConnection.getInputStream());
                while (true) {
                    try {
                        int i = bufferedInputStream2.read(bArr);
                        if (i > 0) {
                            this.mBuffer.append(bArr, 0, i);
                        } else {
                            try {
                                bufferedInputStream2.close();
                                return;
                            } catch (IOException unused) {
                                return;
                            }
                        }
                    } catch (HttpException e) {
                        e = e;
                        LogMgr.log(1, "%s HTTPException", "705");
                        throw e;
                    } catch (Exception unused2) {
                        LogMgr.log(1, "%s Exception", "706");
                        throw new HttpException(AccessControlManager.ERROR_MSG_HTTP_COMMUNICATION_ERROR);
                    } catch (Throwable th) {
                        th = th;
                        bufferedInputStream = bufferedInputStream2;
                        if (bufferedInputStream != null) {
                            try {
                                bufferedInputStream.close();
                            } catch (IOException unused3) {
                            }
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (HttpException e2) {
            e = e2;
        } catch (Exception unused4) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:83:0x00d5, code lost:
    
        r8 = r8 + 1;
     */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0159 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void parse() {
        /*
            Method dump skipped, instruction units count: 352
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.tcap.impl.TlamResponse.parse():void");
    }

    public String getValue(String str) {
        return this.mValueTable.get(str);
    }

    public String getCookie() {
        return this.mCookie;
    }
}
