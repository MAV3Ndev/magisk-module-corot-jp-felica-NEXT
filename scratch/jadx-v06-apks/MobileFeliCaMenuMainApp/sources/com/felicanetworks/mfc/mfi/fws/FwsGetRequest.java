package com.felicanetworks.mfc.mfi.fws;

import com.amazonaws.http.HttpHeader;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.R;
import com.felicanetworks.mfc.mfi.http.HttpCommunicationAgent;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.IRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Calendar;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public class FwsGetRequest implements IRequest {
    private String mWalletAppId;

    @Override // com.felicanetworks.mfc.mfi.http.IRequest
    public void send(HttpURLConnection httpURLConnection) throws IOException, HttpException {
        String string = FelicaAdapter.getInstance().getString(R.string.mfi_client_version);
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty(HttpHeader.CONTENT_TYPE, HttpCommunicationAgent.FWS_CONTENT_TYPE_REQ);
        httpURLConnection.setRequestProperty("X-MFIC-Version", string);
        httpURLConnection.setRequestProperty("X-MFIC-Retry", "0");
        httpURLConnection.setRequestProperty("X-MFIC-WalletAppId", this.mWalletAppId);
        httpURLConnection.setRequestProperty("X-MFIC-TimeZone", TimeZone.getDefault().getID());
        httpURLConnection.setRequestProperty("X-MFIC-UTC", String.valueOf(Calendar.getInstance().getTimeInMillis()));
        httpURLConnection.connect();
    }

    public void setWalletAppId(String str) {
        this.mWalletAppId = str;
    }
}
