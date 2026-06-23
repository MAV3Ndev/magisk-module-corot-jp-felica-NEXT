package com.felicanetworks.semc.http;

import com.felicanetworks.semc.SemClientException;
import java.io.IOException;
import java.net.HttpURLConnection;

/* JADX INFO: loaded from: classes3.dex */
public interface IRequest {
    void send(HttpURLConnection httpURLConnection) throws HttpException, IOException, SemClientException;
}
