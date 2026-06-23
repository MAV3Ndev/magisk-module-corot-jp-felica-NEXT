package com.felicanetworks.semc.sws;

import com.felicanetworks.semc.http.HttpException;
import com.felicanetworks.semc.http.IRequest;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import com.felicanetworks.semc.util.StringUtil;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

/* JADX INFO: loaded from: classes3.dex */
public class SwsRequest implements IRequest {
    public static final String HTTP_METHOD_GET = "GET";
    public static final String HTTP_METHOD_POST = "POST";
    private String mData;
    private SwsRequestHeader mHeader = null;
    private String mHttpMethod;

    SwsRequest(String str) {
        LogMgr.log(6, "000 method=[" + str + "]");
        this.mHttpMethod = str;
        LogMgr.log(6, "999");
    }

    @Override // com.felicanetworks.semc.http.IRequest
    public void send(HttpURLConnection httpURLConnection) throws Throwable {
        BufferedOutputStream bufferedOutputStream;
        StringBuilder sb = new StringBuilder("000 urlConnection is null=[");
        sb.append(httpURLConnection == null);
        sb.append("]");
        LogMgr.log(6, sb.toString());
        BufferedOutputStream bufferedOutputStream2 = null;
        if (httpURLConnection != null) {
            try {
                httpURLConnection.setRequestMethod(this.mHttpMethod);
                httpURLConnection.setRequestProperty("User-Agent", this.mHeader.getUserAgent());
                if (this.mHeader.getContentType() != null) {
                    httpURLConnection.setRequestProperty("Content-Type", this.mHeader.getContentType());
                }
                httpURLConnection.setRequestProperty("X-Semc-ClientId", this.mHeader.getClientId());
                if (this.mHeader.getSemClientVersionAdditionalInfo() != null && !this.mHeader.getSemClientVersionAdditionalInfo().isEmpty()) {
                    httpURLConnection.setRequestProperty("X-Semc-Version", this.mHeader.getClientVersion() + "." + this.mHeader.getSemClientVersionAdditionalInfo());
                } else {
                    httpURLConnection.setRequestProperty("X-Semc-Version", this.mHeader.getClientVersion());
                }
                if (this.mHeader.getProfileId() != null && !this.mHeader.getProfileId().isEmpty()) {
                    httpURLConnection.setRequestProperty("X-Semc-ProfileId", this.mHeader.getProfileId());
                }
                httpURLConnection.setRequestProperty("X-Semc-Retry", String.valueOf(this.mHeader.getRetryCount()));
                if (this.mHeader.getRemotelyStarted()) {
                    httpURLConnection.setRequestProperty("X-Semc-RemotelyStarted", String.valueOf(this.mHeader.getRemotelyStarted()));
                }
                if (this.mHeader.getIntegrityVerificationToken() != null && !this.mHeader.getIntegrityVerificationToken().isEmpty()) {
                    httpURLConnection.setRequestProperty("X-Semc-IntegrityVerification-Token", this.mHeader.getIntegrityVerificationToken());
                }
                if (this.mHeader.getIntegrityVerificationUniqueValue() != null && !this.mHeader.getIntegrityVerificationUniqueValue().isEmpty()) {
                    httpURLConnection.setRequestProperty("X-Semc-IntegrityVerification-UniqueValue", this.mHeader.getIntegrityVerificationUniqueValue());
                }
                if (this.mHeader.getPackageName() != null && !this.mHeader.getPackageName().isEmpty()) {
                    httpURLConnection.setRequestProperty("X-Semc-PackageName", this.mHeader.getPackageName());
                }
                if (this.mHttpMethod.equals(HTTP_METHOD_POST)) {
                    httpURLConnection.setDoOutput(true);
                }
                if (this.mData != null) {
                    LogMgr.log(4, "001 [access-server] requestData=" + this.mData);
                    byte[] uTF8ByteArrays = StringUtil.toUTF8ByteArrays(this.mData);
                    httpURLConnection.setFixedLengthStreamingMode(uTF8ByteArrays.length);
                    try {
                        LogMgr.log(4, "002 [access-server] HttpURLConnection.getOutputStream() in");
                        bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                    } catch (Exception e) {
                        e = e;
                    }
                    try {
                        try {
                            LogMgr.log(4, "003 [access-server] HttpURLConnection.getOutputStream() out");
                            bufferedOutputStream.write(uTF8ByteArrays, 0, uTF8ByteArrays.length);
                            bufferedOutputStream2 = bufferedOutputStream;
                        } catch (Throwable th) {
                            th = th;
                            bufferedOutputStream2 = bufferedOutputStream;
                            if (bufferedOutputStream2 != null) {
                                try {
                                    bufferedOutputStream2.close();
                                } catch (IOException unused) {
                                }
                            }
                            throw th;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        LogMgr.log(2, "700" + e.getClass().getSimpleName() + ":" + e.getMessage() + "]");
                        throw new HttpException(500, ObfuscatedMsgUtil.executionPoint());
                    }
                }
                try {
                    LogMgr.log(4, "004 [access-server] HttpURLConnection.connect() in");
                    httpURLConnection.connect();
                    LogMgr.log(4, "005 [access-server] HttpURLConnection.connect() out");
                } catch (Exception e3) {
                    LogMgr.log(2, "701" + e3.getClass().getSimpleName() + ":" + e3.getMessage() + "]");
                    throw new HttpException(500, ObfuscatedMsgUtil.executionPoint());
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        if (bufferedOutputStream2 != null) {
            try {
                bufferedOutputStream2.close();
            } catch (IOException unused2) {
            }
        }
        LogMgr.log(6, "999");
    }

    public String getHttpMethod() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999 Http method=" + this.mHttpMethod);
        return this.mHttpMethod;
    }

    public void setRequestHeader(SwsRequestHeader swsRequestHeader) {
        LogMgr.log(6, "000");
        this.mHeader = swsRequestHeader;
        LogMgr.log(6, "999");
    }

    public void setRequestData(String str) {
        LogMgr.log(6, "000 data=[" + str + "]");
        this.mData = str;
        LogMgr.log(6, "999");
    }

    public String getRequestData() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mData;
    }
}
