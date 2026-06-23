package com.felicanetworks.mfc.mfi.http;

import java.io.IOException;
import java.net.HttpURLConnection;

/* JADX INFO: loaded from: classes3.dex */
public interface IRequest {
    void send(HttpURLConnection urlConnection) throws IOException, HttpException;
}
