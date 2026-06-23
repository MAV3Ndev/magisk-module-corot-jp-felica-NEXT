package com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data;

/* JADX INFO: loaded from: classes.dex */
public class ClientInfo {
    public String code;
    public String id;
    public String token;

    public ClientInfo(String str, String str2) {
        this.id = str;
        this.code = str2;
    }

    public ClientInfo(String str, String str2, String str3) {
        this.id = str;
        this.code = str2;
        this.token = str3;
    }
}
