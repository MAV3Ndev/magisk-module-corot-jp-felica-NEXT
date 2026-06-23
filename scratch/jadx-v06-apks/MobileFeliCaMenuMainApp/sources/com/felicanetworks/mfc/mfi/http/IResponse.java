package com.felicanetworks.mfc.mfi.http;

import java.io.IOException;
import java.net.HttpURLConnection;

/* JADX INFO: loaded from: classes.dex */
public interface IResponse {
    void checkContentType(String str) throws HttpException;

    String getData();

    void receive(HttpURLConnection httpURLConnection) throws ProtocolException, IOException, HttpException;
}
