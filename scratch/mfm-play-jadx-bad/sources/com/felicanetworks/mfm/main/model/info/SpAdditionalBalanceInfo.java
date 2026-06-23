package com.felicanetworks.mfm.main.model.info;

/* JADX INFO: loaded from: classes3.dex */
public class SpAdditionalBalanceInfo {
    private String cardBalance;
    private String currencyCode;
    private String saveDate;

    SpAdditionalBalanceInfo(String cardBalance, String currencyCode, String saveDate) {
        this.cardBalance = cardBalance;
        this.currencyCode = currencyCode;
        this.saveDate = saveDate;
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
