package com.felicanetworks.mfm.main.model.info;

/* JADX INFO: loaded from: classes.dex */
public class SpAdditionalBalanceInfo {
    private String cardBalance;
    private String currencyCode;
    private String saveDate;

    SpAdditionalBalanceInfo(String str, String str2, String str3) {
        this.cardBalance = str;
        this.currencyCode = str2;
        this.saveDate = str3;
    }

    public String toString() {
        return "SpAdditionalBalanceInfo{cardBlance=" + this.cardBalance + ", currencyCode=" + this.currencyCode + ", saveDate=" + this.saveDate + '}';
    }

    public String getCardBalance() {
        return this.cardBalance;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public String getSaveDate() {
        return this.saveDate;
    }
}
