package com.felicanetworks.mfm.main.model.info;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TransitInfo {
    public CardNumber cardNumber;
    public List<TransitPassInfo> transitPassInfoList;

    public TransitInfo(CardNumber cardNumber, List<TransitPassInfo> list) {
        this.cardNumber = cardNumber;
        this.transitPassInfoList = list;
    }

    public String toString() {
        return "TransitInfo{cardNumber=" + this.cardNumber + ", transitPassInformationList=" + this.transitPassInfoList + '}';
    }

    public static class CardNumber {
        public String displayName;
        public String value;

        public CardNumber(String str, String str2) {
            this.displayName = str;
            this.value = str2;
        }

        public String toString() {
            return "CardNumber{displayName=" + this.displayName + ", value=" + this.value + '}';
        }
    }
}
