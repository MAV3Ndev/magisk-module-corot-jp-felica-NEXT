package com.felicanetworks.mfw.i.cmn;

/* JADX INFO: loaded from: classes.dex */
public class Item {
    private String mKey;
    private String mValue;

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Item key = " + this.mKey);
        stringBuffer.append(", value = " + this.mValue);
        return stringBuffer.toString();
    }

    public Item(String str, String str2) {
        this.mKey = str;
        this.mValue = str2;
    }

    public String getKey() {
        return this.mKey;
    }

    public String getValue() {
        return this.mValue;
    }
}
