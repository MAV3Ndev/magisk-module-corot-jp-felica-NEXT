package com.felicanetworks.mfc.mfi.http;

import java.io.IOException;
import java.net.HttpURLConnection;

/* JADX INFO: loaded from: classes.dex */
public interface IRequest {
    void send(HttpURLConnection httpURLConnection) throws IOException, HttpException;
}
