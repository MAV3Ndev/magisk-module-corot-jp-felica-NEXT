package com.felicanetworks.mfc.mfi.http;

import java.io.IOException;
import java.net.HttpURLConnection;

/* JADX INFO: loaded from: classes3.dex */
public interface IResponse {
    void checkContentType(String contentType) throws HttpException;

    String getData();

    void receive(HttpURLConnection urlConnection) throws ProtocolException, IOException, HttpException;
}
