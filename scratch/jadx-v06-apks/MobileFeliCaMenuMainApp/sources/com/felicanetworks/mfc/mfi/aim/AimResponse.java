package com.felicanetworks.mfc.mfi.aim;

import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.IResponse;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

/* JADX INFO: loaded from: classes.dex */
public class AimResponse implements IResponse {
    private static final String CONTENT_TYPE = "text/plain";
    private static final int READ_BUFFER_SIZE = 4096;
    private String mData = null;

    public void reset() {
        this.mData = null;
    }

    @Override // com.felicanetworks.mfc.mfi.http.IResponse
    public void checkContentType(String str) throws HttpException {
        if (str.contains(CONTENT_TYPE)) {
            return;
        }
        LogMgr.log(2, "700 HttpException: Invalid content-type: ContentType=" + str);
        throw new HttpException(MfiClientCallbackConst.MSG_INVALID_CONTENT_TYPE);
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
                LogMgr.log(2, "%s HttpException:%s ResponseCode=%d", "701", MfiClientCallbackConst.MSG_INVALID_RESPONSE_CODE, Integer.valueOf(responseCode));
                throw new HttpException(MfiClientCallbackConst.MSG_INVALID_RESPONSE_CODE);
            }
            String contentType = httpURLConnection.getContentType();
            if (contentType == null) {
                LogMgr.log(2, "%s HttpException:%s ContentType=null", "702", MfiClientCallbackConst.MSG_INVALID_CONTENT_TYPE_NULL);
                throw new HttpException(MfiClientCallbackConst.MSG_INVALID_CONTENT_TYPE_NULL);
            }
            checkContentType(contentType);
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(httpURLConnection.getInputStream());
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                while (true) {
                    try {
                        int i = bufferedInputStream2.read(bArr);
                        if (i <= 0) {
                            break;
                        } else {
                            byteArrayOutputStream.write(bArr, 0, i);
                        }
                    } catch (Throwable th) {
                        th = th;
                        bufferedInputStream = bufferedInputStream2;
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
                this.mData = StringUtil.toUTF8String(byteArrayOutputStream.toByteArray());
                try {
                    bufferedInputStream2.close();
                } catch (IOException unused3) {
                }
                try {
                    byteArrayOutputStream.close();
                } catch (IOException unused4) {
                }
            } catch (Throwable th2) {
                th = th2;
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
}
