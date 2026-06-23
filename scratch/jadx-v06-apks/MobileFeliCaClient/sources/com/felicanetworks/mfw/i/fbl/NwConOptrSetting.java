package com.felicanetworks.mfw.i.fbl;

/* JADX INFO: loaded from: classes.dex */
public class NwConOptrSetting {
    private String mData;
    private String mId;
    private String mUrl;

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("NwConOptrSetting id = " + this.mId);
        stringBuffer.append(", data = " + this.mData);
        stringBuffer.append(", url = " + this.mUrl);
        return stringBuffer.toString();
    }

    public void setId(String str) {
        this.mId = str;
    }

    public String getId() {
        return this.mId;
    }

    public void setData(String str) {
        this.mData = str;
    }

    public String getData() {
        return this.mData;
    }

    public void setUrl(String str) {
        this.mUrl = str;
    }

    public String getUrl() {
        return this.mUrl;
    }
}
