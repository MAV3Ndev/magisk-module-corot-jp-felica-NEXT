package com.felicanetworks.tis.datatype;

/* JADX INFO: loaded from: classes3.dex */
public class NotificationInfoConst {

    public enum TapType {
        PAY("PAYMENT"),
        CHARGE("TOPUP"),
        TAPPED("TAPPED"),
        TAPPED_PAY("PAYMENT"),
        IN_ONLY("TAPPED"),
        IN_NO_PAY("TAPPED"),
        IN_PAY("PAYMENT"),
        IN_CHARGE("TOPUP"),
        OUT_NO_PAY("TAPPED"),
        OUT_PAY("PAYMENT"),
        OUT_CHARGE("TOPUP"),
        GIFT("TOPUP_SPECIAL"),
        TEST_PAY("TAP_TYPE_UNKNOWN"),
        UNDEFINED("TAP_TYPE_UNKNOWN");

        private final String text;

        TapType(String str) {
            this.text = str;
        }

        public String getString() {
            return this.text;
        }
    }

    public enum CurrencyCode {
        YEN("JPY");

        private final String text;

        CurrencyCode(String str) {
            this.text = str;
        }

        public String getString() {
            return this.text;
        }
    }
}
