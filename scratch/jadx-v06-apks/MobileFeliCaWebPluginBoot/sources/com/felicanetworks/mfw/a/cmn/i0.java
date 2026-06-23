package com.felicanetworks.mfw.a.cmn;

/* JADX INFO: compiled from: Item.java */
/* JADX INFO: loaded from: classes.dex */
public class i0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private String f157a;
    private String b;

    public i0(String str, String str2) {
        this.f157a = str;
        this.b = str2;
    }

    public String a() {
        return this.f157a;
    }

    public String b() {
        return this.b;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Item key = " + this.f157a);
        stringBuffer.append(", value = " + this.b);
        return stringBuffer.toString();
    }
}
