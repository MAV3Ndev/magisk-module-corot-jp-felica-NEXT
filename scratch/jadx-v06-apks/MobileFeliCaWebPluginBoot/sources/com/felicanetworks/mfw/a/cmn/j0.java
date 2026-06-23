package com.felicanetworks.mfw.a.cmn;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: NwConInstance.java */
/* JADX INFO: loaded from: classes.dex */
public class j0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private List f159a = new ArrayList();

    public void a(HttpURLConnection httpURLConnection) {
        this.f159a.add(httpURLConnection);
    }

    public void b() {
        this.f159a.clear();
    }

    public List c() {
        return this.f159a;
    }
}
