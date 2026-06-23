package com.felicanetworks.mfm.main.model.internal.main.mfc;

/* JADX INFO: loaded from: classes3.dex */
public class DcmxMiniDataItem {
    public String cardAplArea;
    public String enableFlag;
    public int issureCode;
    public String latestOfflineDate_YYMMDD;
    public String monthlyClearDate_DD;
    public String offlineLimitValue;
    public String offlineUseValue;
    public int patternNoCode;

    DcmxMiniDataItem(int issureCode, int patternNoCode, String cardAplArea, String monthlyClearDate_DD, String offlineLimitValue, String offlineUseValue, String enableFlag, String latestOfflineDate_YYMMDD) {
        this.issureCode = issureCode;
        this.patternNoCode = patternNoCode;
        this.cardAplArea = cardAplArea;
        this.monthlyClearDate_DD = monthlyClearDate_DD;
        this.offlineLimitValue = offlineLimitValue;
        this.offlineUseValue = offlineUseValue;
        this.enableFlag = enableFlag;
        this.latestOfflineDate_YYMMDD = latestOfflineDate_YYMMDD;
    }

    public String toString() {
        return "DcmxMiniDataItem[" + this.issureCode + ", " + this.patternNoCode + ", " + this.cardAplArea + ", " + this.monthlyClearDate_DD + ", " + this.offlineLimitValue + ", " + this.offlineUseValue + ", " + this.enableFlag + ", " + this.latestOfflineDate_YYMMDD + "]";
    }
}
