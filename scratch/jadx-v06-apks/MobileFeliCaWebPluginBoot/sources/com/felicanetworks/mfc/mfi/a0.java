package com.felicanetworks.mfc.mfi;

import org.json.JSONObject;

/* JADX INFO: compiled from: LocalPartialCardInfoJson.java */
/* JADX INFO: loaded from: classes.dex */
public class a0 extends JSONObject {
    public a0(String str) {
        super(str);
    }

    public z a() {
        return new z(optString("mCid", null), getString("mIdm"), getInt("mPosition"), getString("mServiceId"));
    }
}
