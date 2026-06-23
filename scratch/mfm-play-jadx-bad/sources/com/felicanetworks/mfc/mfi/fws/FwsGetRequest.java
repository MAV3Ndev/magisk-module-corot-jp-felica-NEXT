package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.R;
import com.felicanetworks.mfc.mfi.http.AbstractHttpCommunicationAgent;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.IRequest;
import com.felicanetworks.mfc.mfi.util.SettingInfo;
import com.felicanetworks.semc.sws.SwsRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Calendar;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes3.dex */
public class FwsGetRequest implements IRequest {
    private String mWalletAppId;

    @Override // com.felicanetworks.mfc.mfi.http.IRequest
    public void send(HttpURLConnection urlConnection) throws IOException, HttpException {
        String string = FelicaAdapter.getInstance().getString(R.string.mfi_client_version);
        urlConnection.setRequestMethod(SwsRequest.HTTP_METHOD_GET);
        urlConnection.setRequestProperty("Content-Type", AbstractHttpCommunicationAgent.FWS_CONTENT_TYPE_REQ);
        urlConnection.setRequestProperty("X-MFIC-Version", string);
        urlConnection.setRequestProperty("X-MFIC-Retry", "0");
        urlConnection.setRequestProperty("X-MFIC-WalletAppId", this.mWalletAppId);
        urlConnection.setRequestProperty("X-MFIC-TimeZone", TimeZone.getDefault().getID());
        urlConnection.setRequestProperty("X-MFIC-UTC", String.valueOf(Calendar.getInstance().getTimeInMillis()));
        String profileId = SettingInfo.getProfileId();
        if (!profileId.isEmpty()) {
            urlConnection.setRequestProperty("X-MFIC-ProfileId", profileId);
        }
        urlConnection.connect();
    }

    public void setWalletAppId(String walletAppId) {
        this.mWalletAppId = walletAppId;
    }
}
