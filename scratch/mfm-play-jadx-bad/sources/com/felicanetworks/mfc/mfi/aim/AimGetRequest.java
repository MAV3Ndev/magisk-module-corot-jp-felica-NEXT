package com.felicanetworks.mfc.mfi.aim;

import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.IRequest;
import com.felicanetworks.mfc.mfi.util.SettingInfo;
import com.felicanetworks.semc.sws.SwsRequest;
import java.io.IOException;
import java.net.HttpURLConnection;

/* JADX INFO: loaded from: classes3.dex */
public class AimGetRequest implements IRequest {
    @Override // com.felicanetworks.mfc.mfi.http.IRequest
    public void send(HttpURLConnection urlConnection) throws IOException, HttpException {
        urlConnection.setRequestMethod(SwsRequest.HTTP_METHOD_GET);
        String profileId = SettingInfo.getProfileId();
        if (!profileId.isEmpty()) {
            urlConnection.setRequestProperty("X-MFIC-ProfileId", profileId);
        }
        urlConnection.connect();
    }
}
