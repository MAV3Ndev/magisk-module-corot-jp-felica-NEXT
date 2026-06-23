package com.felicanetworks.semc.http;

import java.io.IOException;
import java.net.HttpURLConnection;

/* JADX INFO: loaded from: classes3.dex */
public interface IResponse {
    void checkContentType(String str) throws HttpException;

    String getData();

    void receive(HttpURLConnection httpURLConnection) throws HttpException, IOException;
}
