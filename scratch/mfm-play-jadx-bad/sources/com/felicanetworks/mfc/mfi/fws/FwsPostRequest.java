package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.R;
import com.felicanetworks.mfc.mfi.http.AbstractHttpCommunicationAgent;
import com.felicanetworks.mfc.mfi.http.IRequest;
import com.felicanetworks.mfc.mfi.util.SettingInfo;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.semc.sws.SwsRequest;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Calendar;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes3.dex */
public class FwsPostRequest implements IRequest {
    private String mData;
    private int mRetryCount;
    private String mWalletAppId;

    @Override // com.felicanetworks.mfc.mfi.http.IRequest
    public void send(HttpURLConnection urlConnection) throws Throwable {
        String string = FelicaAdapter.getInstance().getString(R.string.mfi_client_version);
        BufferedOutputStream bufferedOutputStream = null;
        try {
            urlConnection.setRequestMethod(SwsRequest.HTTP_METHOD_POST);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", AbstractHttpCommunicationAgent.FWS_CONTENT_TYPE_REQ);
            urlConnection.setRequestProperty("X-MFIC-Version", string);
            urlConnection.setRequestProperty("X-MFIC-Retry", String.valueOf(this.mRetryCount));
            urlConnection.setRequestProperty("X-MFIC-WalletAppId", this.mWalletAppId);
            urlConnection.setRequestProperty("X-MFIC-TimeZone", TimeZone.getDefault().getID());
            urlConnection.setRequestProperty("X-MFIC-UTC", String.valueOf(Calendar.getInstance().getTimeInMillis()));
            String profileId = SettingInfo.getProfileId();
            if (!profileId.isEmpty()) {
                urlConnection.setRequestProperty("X-MFIC-ProfileId", profileId);
            }
            String str = this.mData;
            if (str != null) {
                byte[] uTF8ByteArrays = StringUtil.toUTF8ByteArrays(str);
                urlConnection.setFixedLengthStreamingMode(uTF8ByteArrays.length);
                BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(urlConnection.getOutputStream());
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
            urlConnection.connect();
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

    public void setPostData(String data) {
        this.mData = data;
    }

    public void setRetryCount(int retryCount) {
        this.mRetryCount = retryCount;
    }

    public void setWalletAppId(String walletAppId) {
        this.mWalletAppId = walletAppId;
    }
}
