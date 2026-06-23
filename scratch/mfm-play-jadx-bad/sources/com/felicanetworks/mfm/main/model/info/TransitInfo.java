package com.felicanetworks.mfm.main.model.info;

import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class TransitInfo {
    public CardNumber cardNumber;
    public List<TransitPassInfo> transitPassInfoList;

    public TransitInfo(CardNumber cardNumber, List<TransitPassInfo> transitPassInfoList) {
        this.cardNumber = cardNumber;
        this.transitPassInfoList = transitPassInfoList;
    }

    public String toString() {
        return "TransitInfo{cardNumber=" + this.cardNumber + ", transitPassInformationList=" + this.transitPassInfoList + '}';
    }

    public static class CardNumber {
        public String displayName;
        public String value;

        public CardNumber(String displayName, String value) {
            this.displayName = displayName;
            this.value = value;
        }

        public String toString() {
            return "CardNumber{displayName=" + this.displayName + ", value=" + this.value + '}';
        }
    }
}
