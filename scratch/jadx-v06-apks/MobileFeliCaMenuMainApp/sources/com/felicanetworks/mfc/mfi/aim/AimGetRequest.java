package com.felicanetworks.mfc.mfi.aim;

import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.IRequest;
import java.io.IOException;
import java.net.HttpURLConnection;

/* JADX INFO: loaded from: classes.dex */
public class AimGetRequest implements IRequest {
    @Override // com.felicanetworks.mfc.mfi.http.IRequest
    public void send(HttpURLConnection httpURLConnection) throws IOException, HttpException {
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
    }
}
