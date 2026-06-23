package com.felicanetworks.mfm.main.model.internal.main.mfc;

/* JADX INFO: loaded from: classes.dex */
public class DcmxMiniDataItem {
    public String cardAplArea;
    public String enableFlag;
    public int issureCode;
    public String latestOfflineDate_YYMMDD;
    public String monthlyClearDate_DD;
    public String offlineLimitValue;
    public String offlineUseValue;
    public int patternNoCode;

    DcmxMiniDataItem(int i, int i2, String str, String str2, String str3, String str4, String str5, String str6) {
        this.issureCode = i;
        this.patternNoCode = i2;
        this.cardAplArea = str;
        this.monthlyClearDate_DD = str2;
        this.offlineLimitValue = str3;
        this.offlineUseValue = str4;
        this.enableFlag = str5;
        this.latestOfflineDate_YYMMDD = str6;
    }

    public String toString() {
        return "DcmxMiniDataItem[" + this.issureCode + ", " + this.patternNoCode + ", " + this.cardAplArea + ", " + this.monthlyClearDate_DD + ", " + this.offlineLimitValue + ", " + this.offlineUseValue + ", " + this.enableFlag + ", " + this.latestOfflineDate_YYMMDD + "]";
    }
}
