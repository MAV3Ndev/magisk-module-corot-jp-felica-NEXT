package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.http.HttpCommunicationAgent;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.IResponse;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class FwsResponse implements IResponse {
    private static final int READ_BUFFER_SIZE = 4096;
    private byte[] mByteData;
    private String mData = null;

    public void reset() {
        this.mData = null;
    }

    @Override // com.felicanetworks.mfc.mfi.http.IResponse
    public void checkContentType(String str) throws HttpException {
        if (str.toLowerCase(Locale.ENGLISH).contains("application/json") && str.toLowerCase(Locale.ENGLISH).contains(HttpCommunicationAgent.FWS_CONTENT_TYPE_CHR)) {
            return;
        }
        String str2 = MfiClientCallbackConst.MSG_INVALID_CONTENT_TYPE + str;
        LogMgr.log(2, "700 HttpException: " + str2);
        throw new HttpException(203, str2);
    }

    @Override // com.felicanetworks.mfc.mfi.http.IResponse
    public void receive(HttpURLConnection httpURLConnection) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        byte[] bArr = new byte[4096];
        reset();
        BufferedInputStream bufferedInputStream = null;
        try {
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode != 200) {
                String str = MfiClientCallbackConst.MSG_INVALID_RESPONSE_CODE + responseCode;
                LogMgr.log(2, "%s HttpException:%s", "700", str);
                if (500 <= responseCode && responseCode <= 599) {
                    throw new HttpException(204, str);
                }
                throw new HttpException(203, str);
            }
            String contentType = httpURLConnection.getContentType();
            if (contentType == null) {
                LogMgr.log(2, "%s HttpException:%s", "702", MfiClientCallbackConst.MSG_INVALID_CONTENT_TYPE_NULL);
                throw new HttpException(203, MfiClientCallbackConst.MSG_INVALID_CONTENT_TYPE_NULL);
            }
            checkContentType(contentType);
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(httpURLConnection.getInputStream());
            try {
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                while (true) {
                    try {
                        int i = bufferedInputStream2.read(bArr);
                        if (i <= 0) {
                            break;
                        } else {
                            byteArrayOutputStream2.write(bArr, 0, i);
                        }
                    } catch (Throwable th) {
                        bufferedInputStream = bufferedInputStream2;
                        byteArrayOutputStream = byteArrayOutputStream2;
                        th = th;
                        if (bufferedInputStream != null) {
                            try {
                                bufferedInputStream.close();
                            } catch (IOException unused) {
                            }
                        }
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                                throw th;
                            } catch (IOException unused2) {
                                throw th;
                            }
                        }
                        throw th;
                    }
                }
                byte[] byteArray = byteArrayOutputStream2.toByteArray();
                this.mByteData = byteArray;
                this.mData = StringUtil.toUTF8String(byteArray);
                try {
                    bufferedInputStream2.close();
                } catch (IOException unused3) {
                }
                try {
                    byteArrayOutputStream2.close();
                } catch (IOException unused4) {
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedInputStream = bufferedInputStream2;
                byteArrayOutputStream = null;
            }
        } catch (Throwable th3) {
            th = th3;
            byteArrayOutputStream = null;
        }
    }

    @Override // com.felicanetworks.mfc.mfi.http.IResponse
    public String getData() {
        return this.mData;
    }

    public byte[] getByteData() {
        return this.mByteData;
    }
}
