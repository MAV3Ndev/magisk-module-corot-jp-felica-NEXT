package com.felicanetworks.mfw.i.fbl;

/* JADX INFO: loaded from: classes.dex */
public class ResDataStartParam extends ResData {
    private int mStartParamSize = -1;
    private String mStartParam = null;
    private String mBeforeStartDate = null;

    @Override // com.felicanetworks.mfw.i.fbl.ResData
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ResDataStartParam startParamSize = " + this.mStartParamSize);
        stringBuffer.append(", startParam = " + this.mStartParam);
        stringBuffer.append(", beforeStartDate = " + this.mBeforeStartDate);
        return stringBuffer.toString();
    }

    public void setStartParam(String str) {
        this.mStartParam = str;
    }

    public String getStartParam() {
        return this.mStartParam;
    }

    public void setStartParamSize(int i) {
        this.mStartParamSize = i;
    }

    public int getStartParamSize() {
        return this.mStartParamSize;
    }

    public void setBeforeStartDate(String str) {
        this.mBeforeStartDate = str;
    }

    public String getBeforeStartDate() {
        return this.mBeforeStartDate;
    }
}
