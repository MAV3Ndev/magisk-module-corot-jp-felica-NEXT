package com.felicanetworks.mfw.i.fbl;

/* JADX INFO: loaded from: classes.dex */
public class ResData {
    private String mResId;

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ResData resId = " + this.mResId);
        return stringBuffer.toString();
    }

    public void setResId(String str) {
        this.mResId = str;
    }

    public String getResId() {
        return this.mResId;
    }
}
