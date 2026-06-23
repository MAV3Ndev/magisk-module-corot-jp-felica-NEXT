package com.felicanetworks.mfw.a.cmn;

import java.net.HttpURLConnection;
import java.util.concurrent.Callable;

/* JADX INFO: compiled from: NwConUtilWithHttpURLConnection.java */
/* JADX INFO: loaded from: classes.dex */
class m0 implements Callable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private HttpURLConnection f165a;

    public m0(HttpURLConnection httpURLConnection) {
        this.f165a = httpURLConnection;
    }

    @Override // java.util.concurrent.Callable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Integer call() {
        this.f165a.connect();
        return 0;
    }
}
