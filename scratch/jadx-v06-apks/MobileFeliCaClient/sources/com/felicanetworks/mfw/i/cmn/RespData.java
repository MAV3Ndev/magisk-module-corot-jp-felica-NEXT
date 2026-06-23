package com.felicanetworks.mfw.i.cmn;

/* JADX INFO: loaded from: classes.dex */
public class RespData {
    private String mContentType;
    private String mMessageBody;
    private int mStatusCode;

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("RespData statusCode = " + this.mStatusCode);
        stringBuffer.append(", messageBody = " + this.mMessageBody);
        stringBuffer.append(", contentType = " + this.mContentType);
        return stringBuffer.toString();
    }

    public void setStatusCode(int i) {
        this.mStatusCode = i;
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }

    public void setMessageBody(String str) {
        this.mMessageBody = str;
    }

    public String getMessageBody() {
        return this.mMessageBody;
    }

    public void setContentType(String str) {
        this.mContentType = str;
    }

    public String getContentType() {
        return this.mContentType;
    }
}
