package com.felicanetworks.mfw.i.fbl;

/* JADX INFO: loaded from: classes.dex */
public class ResDataVerUpConfir extends ResData {
    private String mVerUpConfirDate = null;
    private int mOfflineVerNumLimit = -1;
    private int mOfflineVerNum = -1;
    private int mOfflineVerData = -1;
    private String mOfflineVerUpReqDate = null;

    @Override // com.felicanetworks.mfw.i.fbl.ResData
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ResDataVerUpConfir verUpConfirDate = " + this.mVerUpConfirDate);
        stringBuffer.append(", offlineVerNumLimit = " + this.mOfflineVerNumLimit);
        stringBuffer.append(", offlineVerNum = " + this.mOfflineVerNum);
        stringBuffer.append(", offlineVerData = " + this.mOfflineVerData);
        stringBuffer.append(", offlineVerUpReqDate = " + this.mOfflineVerUpReqDate);
        return stringBuffer.toString();
    }

    public void setVerUpConfirDate(String str) {
        this.mVerUpConfirDate = str;
    }

    public String getVerUpConfirDate() {
        return this.mVerUpConfirDate;
    }

    public void setOfflineVerNumLimit(int i) {
        this.mOfflineVerNumLimit = i;
    }

    public int getOfflineVerNumLimit() {
        return this.mOfflineVerNumLimit;
    }

    public void setOfflineVerNum(int i) {
        this.mOfflineVerNum = i;
    }

    public int getOfflineVerNum() {
        return this.mOfflineVerNum;
    }

    public void setOfflineVerData(int i) {
        this.mOfflineVerData = i;
    }

    public int getOfflineVerData() {
        return this.mOfflineVerData;
    }

    public void setOfflineVerUpReqDate(String str) {
        this.mOfflineVerUpReqDate = str;
    }

    public String getOfflineVerUpReqDate() {
        return this.mOfflineVerUpReqDate;
    }
}
