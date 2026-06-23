package com.felicanetworks.mfm.main.model.internal.main.mfc;

/* JADX INFO: loaded from: classes.dex */
public class AreaItemFelica {
    public final String areaCode;
    public final String systemCode;

    AreaItemFelica(String str, String str2) {
        this.systemCode = str;
        this.areaCode = str2;
    }

    public String toString() {
        return "AreaItemFelica[" + this.systemCode + ", " + this.areaCode + "]";
    }
}
