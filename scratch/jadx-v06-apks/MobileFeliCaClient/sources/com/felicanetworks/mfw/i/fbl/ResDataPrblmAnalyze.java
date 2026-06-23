package com.felicanetworks.mfw.i.fbl;

/* JADX INFO: loaded from: classes.dex */
public class ResDataPrblmAnalyze extends ResData {
    private int mMgmtFlag = -1;
    private String mAddInfo = null;

    @Override // com.felicanetworks.mfw.i.fbl.ResData
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ResDataPrblmAnalyze mgmtFlag = " + this.mMgmtFlag);
        stringBuffer.append(", addInfo = " + this.mAddInfo);
        return stringBuffer.toString();
    }

    public void setMgmtFlag(int i) {
        this.mMgmtFlag = i;
    }

    public int getMgmtFlag() {
        return this.mMgmtFlag;
    }

    public void setAddInfo(String str) {
        this.mAddInfo = str;
    }

    public String getAddInfo() {
        return this.mAddInfo;
    }
}
