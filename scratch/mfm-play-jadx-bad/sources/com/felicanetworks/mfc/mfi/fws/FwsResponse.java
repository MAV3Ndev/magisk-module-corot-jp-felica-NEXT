package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.IResponse;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Locale;

/* JADX INFO: loaded from: classes3.dex */
public class FwsResponse implements IResponse {
    private static final int READ_BUFFER_SIZE = 4096;
    private byte[] mByteData;
    private String mData = null;

    public void reset() {
        this.mData = null;
    }

    @Override // com.felicanetworks.mfc.mfi.http.IResponse
    public void checkContentType(String contentType) throws HttpException {
        if (contentType.toLowerCase(Locale.ENGLISH).contains("application/json") && contentType.toLowerCase(Locale.ENGLISH).contains("charset=utf-8")) {
            return;
        }
        String str = "Invalid content-type: " + contentType;
        LogMgr.log(2, "700 HttpException: " + str);
        throw new HttpException(203, str);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    @Override // com.felicanetworks.mfc.mfi.http.IResponse
    public void receive(HttpURLConnection urlConnection) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        byte[] bArr = new byte[4096];
        reset();
        BufferedInputStream bufferedInputStream = null;
        try {
            int responseCode = urlConnection.getResponseCode();
            if (responseCode != 200) {
                String str = "Invalid response code:" + responseCode;
                LogMgr.log(2, "%s HttpException:%s", "700", str);
                if (500 <= responseCode && responseCode <= 599) {
                    throw new HttpException(204, str);
                }
                throw new HttpException(203, str);
            }
            String contentType = urlConnection.getContentType();
            if (contentType == null) {
                LogMgr.log(2, "%s HttpException:%s", "702", "Invalid content-type: null");
                throw new HttpException(203, "Invalid content-type: null");
            }
            checkContentType(contentType);
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(urlConnection.getInputStream());
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
                        if (byteArrayOutputStream == null) {
                            throw th;
                        }
                        try {
                            byteArrayOutputStream.close();
                            throw th;
                        } catch (IOException unused2) {
                            throw th;
                        }
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
