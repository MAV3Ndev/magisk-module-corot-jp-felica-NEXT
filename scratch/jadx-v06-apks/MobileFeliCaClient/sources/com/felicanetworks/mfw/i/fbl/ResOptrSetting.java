package com.felicanetworks.mfw.i.fbl;

/* JADX INFO: loaded from: classes.dex */
public class ResOptrSetting {
    private String mId;
    private boolean mIsBuffered;
    private boolean mIsRead;
    private ResData mResData;

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ResOptrSetting id = " + this.mId);
        stringBuffer.append(", isRead = " + this.mIsRead);
        stringBuffer.append(", isBuffered = " + this.mIsBuffered);
        stringBuffer.append(", resData = " + this.mResData);
        return stringBuffer.toString();
    }

    public void setId(String str) {
        this.mId = str;
    }

    public String getId() {
        return this.mId;
    }

    public void setRead(boolean z) {
        this.mIsRead = z;
    }

    public boolean isRead() {
        return this.mIsRead;
    }

    public void setResData(ResData resData) {
        this.mResData = resData;
    }

    public ResData getResData() {
        return this.mResData;
    }

    public void setBuffered(boolean z) {
        this.mIsBuffered = z;
    }

    public boolean isBuffered() {
        return this.mIsBuffered;
    }
}
