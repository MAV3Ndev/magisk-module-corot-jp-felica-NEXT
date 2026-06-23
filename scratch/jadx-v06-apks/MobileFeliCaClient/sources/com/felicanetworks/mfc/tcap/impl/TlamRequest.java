package com.felicanetworks.mfc.tcap.impl;

import com.felicanetworks.mfc.felica.access_control.AccessControlManager;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

/* JADX INFO: loaded from: classes.dex */
public class TlamRequest {
    byte[] mPostdata;

    public void send(HttpURLConnection httpURLConnection) throws Throwable {
        BufferedOutputStream bufferedOutputStream = null;
        try {
            try {
                httpURLConnection.setRequestProperty("Accept", "*, */*");
                if (this.mPostdata != null) {
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setFixedLengthStreamingMode(this.mPostdata.length);
                    httpURLConnection.setRequestProperty("Content-Type", "application/x-tlam");
                    BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(httpURLConnection.getOutputStream());
                    try {
                        bufferedOutputStream2.write(this.mPostdata, 0, this.mPostdata.length);
                        bufferedOutputStream = bufferedOutputStream2;
                    } catch (Exception unused) {
                        bufferedOutputStream = bufferedOutputStream2;
                        LogMgr.log(1, "%s Exception", "700");
                        throw new HttpException(AccessControlManager.ERROR_MSG_HTTP_COMMUNICATION_ERROR);
                    } catch (Throwable th) {
                        th = th;
                        bufferedOutputStream = bufferedOutputStream2;
                        if (bufferedOutputStream != null) {
                            try {
                                bufferedOutputStream.close();
                            } catch (IOException unused2) {
                            }
                        }
                        throw th;
                    }
                }
                if (bufferedOutputStream != null) {
                    try {
                        bufferedOutputStream.close();
                    } catch (IOException unused3) {
                    }
                }
            } catch (Exception unused4) {
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    void setPostData(byte[] bArr) {
        this.mPostdata = bArr;
    }
}
