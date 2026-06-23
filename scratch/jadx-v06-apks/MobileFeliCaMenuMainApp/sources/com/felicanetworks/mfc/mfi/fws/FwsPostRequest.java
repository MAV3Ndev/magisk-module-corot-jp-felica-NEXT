package com.felicanetworks.mfc.mfi.fws;

import com.amazonaws.http.HttpHeader;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.R;
import com.felicanetworks.mfc.mfi.http.HttpCommunicationAgent;
import com.felicanetworks.mfc.mfi.http.IRequest;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Calendar;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public class FwsPostRequest implements IRequest {
    private String mData;
    private int mRetryCount;
    private String mWalletAppId;

    @Override // com.felicanetworks.mfc.mfi.http.IRequest
    public void send(HttpURLConnection httpURLConnection) throws Throwable {
        String string = FelicaAdapter.getInstance().getString(R.string.mfi_client_version);
        BufferedOutputStream bufferedOutputStream = null;
        try {
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty(HttpHeader.CONTENT_TYPE, HttpCommunicationAgent.FWS_CONTENT_TYPE_REQ);
            httpURLConnection.setRequestProperty("X-MFIC-Version", string);
            httpURLConnection.setRequestProperty("X-MFIC-Retry", String.valueOf(this.mRetryCount));
            httpURLConnection.setRequestProperty("X-MFIC-WalletAppId", this.mWalletAppId);
            httpURLConnection.setRequestProperty("X-MFIC-TimeZone", TimeZone.getDefault().getID());
            httpURLConnection.setRequestProperty("X-MFIC-UTC", String.valueOf(Calendar.getInstance().getTimeInMillis()));
            if (this.mData != null) {
                byte[] uTF8ByteArrays = StringUtil.toUTF8ByteArrays(this.mData);
                httpURLConnection.setFixedLengthStreamingMode(uTF8ByteArrays.length);
                BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(httpURLConnection.getOutputStream());
                try {
                    bufferedOutputStream2.write(uTF8ByteArrays, 0, uTF8ByteArrays.length);
                    bufferedOutputStream = bufferedOutputStream2;
                } catch (Throwable th) {
                    th = th;
                    bufferedOutputStream = bufferedOutputStream2;
                    if (bufferedOutputStream != null) {
                        try {
                            bufferedOutputStream.close();
                        } catch (IOException unused) {
                        }
                    }
                    throw th;
                }
            }
            httpURLConnection.connect();
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException unused2) {
                }
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public void setPostData(String str) {
        this.mData = str;
    }

    public void setRetryCount(int i) {
        this.mRetryCount = i;
    }

    public void setWalletAppId(String str) {
        this.mWalletAppId = str;
    }
}
