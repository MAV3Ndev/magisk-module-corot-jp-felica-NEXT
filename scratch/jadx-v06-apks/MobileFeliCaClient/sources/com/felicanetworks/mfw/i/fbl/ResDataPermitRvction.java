package com.felicanetworks.mfw.i.fbl;

/* JADX INFO: loaded from: classes.dex */
public class ResDataPermitRvction extends ResData {
    private int mOfflineRvctionNumLimit = -1;
    private int mOfflineRvctionNum = -1;
    private String mUsedDate = null;
    private int mOfflineRvctionTerm = -1;
    private String mUpdateDate = null;
    private int mRvctionPointSize = -1;
    private String mRvctionPoint = null;
    private int mSerialNumCount = -1;
    private String[] mRvctionList = new String[0];
    private long mPosInFile = 0;

    @Override // com.felicanetworks.mfw.i.fbl.ResData
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ResDataPermitRvction offlineRvctionNumLimit = " + this.mOfflineRvctionNumLimit);
        stringBuffer.append(", offlineRvctionNum = " + this.mOfflineRvctionNum);
        stringBuffer.append(", offlineRvctionTerm = " + this.mOfflineRvctionTerm);
        stringBuffer.append(", updateDate = " + this.mUpdateDate);
        stringBuffer.append(", rvctionPointSize = " + this.mRvctionPointSize);
        stringBuffer.append(", rvctionPoint = " + this.mRvctionPoint);
        stringBuffer.append(", serialNumCount = " + this.mSerialNumCount);
        for (int i = 0; i < this.mRvctionList.length; i++) {
            stringBuffer.append(", rvctionList[" + i + "] = " + this.mRvctionList[i]);
        }
        return stringBuffer.toString();
    }

    public void setOfflineRvctionNumLimit(int i) {
        this.mOfflineRvctionNumLimit = i;
    }

    public int getOfflineRvctionNumLimit() {
        return this.mOfflineRvctionNumLimit;
    }

    public void setOfflineRvctionNum(int i) {
        this.mOfflineRvctionNum = i;
    }

    public int getOfflineRvctionNum() {
        return this.mOfflineRvctionNum;
    }

    public void setUsedDate(String str) {
        this.mUsedDate = str;
    }

    public String getUsedDate() {
        return this.mUsedDate;
    }

    public void setOfflineRvctionTerm(int i) {
        this.mOfflineRvctionTerm = i;
    }

    public int getOfflineRvctionTerm() {
        return this.mOfflineRvctionTerm;
    }

    public void setUpdateDate(String str) {
        this.mUpdateDate = str;
    }

    public String getUpdateDate() {
        return this.mUpdateDate;
    }

    public void setRvctionPointSize(int i) {
        this.mRvctionPointSize = i;
    }

    public int getRvctionPointSize() {
        return this.mRvctionPointSize;
    }

    public void setRvctionPoint(String str) {
        this.mRvctionPoint = str;
    }

    public String getRvctionPoint() {
        return this.mRvctionPoint;
    }

    public void setSerialNumCount(int i) {
        this.mSerialNumCount = i;
    }

    public int getSerialNumCount() {
        return this.mSerialNumCount;
    }

    public void setRvctionList(String[] strArr) {
        this.mRvctionList = strArr;
    }

    public String[] getRvctionList() {
        return this.mRvctionList;
    }

    public void setPosInFile(long j) {
        this.mPosInFile = j;
    }

    public long getPosInFile() {
        return this.mPosInFile;
    }
}
