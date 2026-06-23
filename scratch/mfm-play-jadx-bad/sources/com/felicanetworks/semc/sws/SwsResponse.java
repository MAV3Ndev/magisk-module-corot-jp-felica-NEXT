package com.felicanetworks.semc.sws;

import com.felicanetworks.semc.SemClientCallbackConst;
import com.felicanetworks.semc.http.HttpException;
import com.felicanetworks.semc.http.IResponse;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import com.felicanetworks.semc.util.StringUtil;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Locale;

/* JADX INFO: loaded from: classes3.dex */
public class SwsResponse implements IResponse {
    private static final int READ_BUFFER_SIZE = 4096;
    private byte[] mByteData;
    private String mData;
    private boolean mIsRemotelyStarted;

    public SwsResponse(String str) {
        LogMgr.log(6, "000");
        this.mData = null;
        this.mIsRemotelyStarted = false;
        LogMgr.log(6, "999");
    }

    public void reset() {
        LogMgr.log(6, "000");
        this.mData = null;
        LogMgr.log(6, "999");
    }

    @Override // com.felicanetworks.semc.http.IResponse
    public void checkContentType(String str) throws HttpException {
        LogMgr.log(6, "000 contentType=[" + str + "]");
        if (str == null) {
            LogMgr.log(2, "700 HttpExceptionInvalid content-type: null");
            throw new HttpException(SemClientCallbackConst.TYPE_HTTP_ERROR, ObfuscatedMsgUtil.executionPoint());
        }
        if (!str.toLowerCase(Locale.ENGLISH).contains("application/json") || !str.toLowerCase(Locale.ENGLISH).contains("charset=utf-8")) {
            LogMgr.log(2, "701 HttpException: " + ("Invalid content-type: " + str));
            throw new HttpException(SemClientCallbackConst.TYPE_HTTP_ERROR, ObfuscatedMsgUtil.executionPoint());
        }
        LogMgr.log(6, "999");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    @Override // com.felicanetworks.semc.http.IResponse
    public void receive(HttpURLConnection httpURLConnection) throws Throwable {
        BufferedInputStream bufferedInputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        BufferedInputStream bufferedInputStream2;
        ByteArrayOutputStream byteArrayOutputStream2;
        LogMgr.log(6, "000");
        byte[] bArr = new byte[4096];
        StringBuilder sb = new StringBuilder("000 urlConnection is null=[");
        sb.append(httpURLConnection == null);
        sb.append("]");
        LogMgr.log(6, sb.toString());
        reset();
        if (httpURLConnection != null) {
            try {
                try {
                    LogMgr.log(4, "001 [access-server] HttpURLConnection.getResponseCode() in");
                    int responseCode = httpURLConnection.getResponseCode();
                    LogMgr.log(4, "002 [access-server] HttpURLConnection.getResponseCode() out result=" + responseCode);
                    if (responseCode != 200) {
                        LogMgr.log(2, "701 HttpException:" + ("Invalid response code:" + responseCode));
                        if (500 <= responseCode && responseCode <= 599 && responseCode != 503) {
                            LogMgr.log(2, "702 responseCode is 500 series(Not 503).");
                            throw new HttpException(500, ObfuscatedMsgUtil.executionPoint(String.valueOf(responseCode)));
                        }
                        if (responseCode != 503) {
                            LogMgr.log(2, "704 otherwise.");
                            throw new HttpException(SemClientCallbackConst.TYPE_HTTP_ERROR, ObfuscatedMsgUtil.executionPoint(String.valueOf(responseCode)));
                        }
                        LogMgr.log(2, "703 responseCode is 503.");
                        if (!this.mIsRemotelyStarted) {
                            throw new HttpException(400, ObfuscatedMsgUtil.executionPoint(String.valueOf(responseCode)));
                        }
                        throw new HttpException(600, ObfuscatedMsgUtil.executionPoint(String.valueOf(responseCode)));
                    }
                    try {
                        LogMgr.log(4, "003 [access-server] HttpURLConnection.getContentType() in");
                        String contentType = httpURLConnection.getContentType();
                        LogMgr.log(4, "004 [access-server] HttpURLConnection.getContentType() out result=" + contentType);
                        checkContentType(contentType);
                        try {
                            LogMgr.log(4, "005 [access-server] HttpURLConnection.getInputStream() in");
                            BufferedInputStream bufferedInputStream3 = new BufferedInputStream(httpURLConnection.getInputStream());
                            try {
                                try {
                                    LogMgr.log(4, "006 [access-server] HttpURLConnection.getInputStream() out");
                                    ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
                                    while (true) {
                                        try {
                                            int i = bufferedInputStream3.read(bArr);
                                            if (i <= 0) {
                                                break;
                                            } else {
                                                byteArrayOutputStream3.write(bArr, 0, i);
                                            }
                                        } catch (Throwable th) {
                                            th = th;
                                            byteArrayOutputStream2 = byteArrayOutputStream3;
                                            bufferedInputStream2 = bufferedInputStream3;
                                        }
                                    }
                                    byte[] byteArray = byteArrayOutputStream3.toByteArray();
                                    this.mByteData = byteArray;
                                    this.mData = StringUtil.toUTF8String(byteArray);
                                    LogMgr.log(4, "007 [access-server] responseData=" + this.mData);
                                    byteArrayOutputStream = byteArrayOutputStream3;
                                    bufferedInputStream = bufferedInputStream3;
                                } catch (Exception e) {
                                    e = e;
                                    LogMgr.log(2, "706" + e.getClass().getSimpleName() + ":" + e.getMessage() + "]");
                                    throw new HttpException(500, ObfuscatedMsgUtil.executionPoint());
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                bufferedInputStream2 = bufferedInputStream3;
                                byteArrayOutputStream2 = null;
                            }
                        } catch (Exception e2) {
                            e = e2;
                        }
                    } catch (Exception e3) {
                        LogMgr.log(2, "705" + e3.getClass().getSimpleName() + ":" + e3.getMessage() + "]");
                        throw new HttpException(500, ObfuscatedMsgUtil.executionPoint());
                    }
                } catch (Exception e4) {
                    LogMgr.log(2, "700" + e4.getClass().getSimpleName() + ":" + e4.getMessage() + "]");
                    throw new HttpException(500, ObfuscatedMsgUtil.executionPoint());
                }
            } catch (Throwable th3) {
                th = th3;
                bufferedInputStream2 = null;
            }
            byteArrayOutputStream2 = null;
            if (bufferedInputStream2 != null) {
                try {
                    bufferedInputStream2.close();
                } catch (IOException unused) {
                }
            }
            if (byteArrayOutputStream2 == null) {
                throw th;
            }
            try {
                byteArrayOutputStream2.close();
                throw th;
            } catch (IOException unused2) {
                throw th;
            }
        }
        bufferedInputStream = null;
        byteArrayOutputStream = null;
        if (bufferedInputStream != null) {
            try {
                bufferedInputStream.close();
            } catch (IOException unused3) {
            }
        }
        if (byteArrayOutputStream != null) {
            try {
                byteArrayOutputStream.close();
            } catch (IOException unused4) {
            }
        }
        LogMgr.log(6, "999");
    }

    @Override // com.felicanetworks.semc.http.IResponse
    public String getData() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mData;
    }

    public void setIsRemotelyStarted(boolean z) {
        LogMgr.log(6, "000 isRemotelyStarted=" + z);
        this.mIsRemotelyStarted = z;
        LogMgr.log(6, "999");
    }
}
