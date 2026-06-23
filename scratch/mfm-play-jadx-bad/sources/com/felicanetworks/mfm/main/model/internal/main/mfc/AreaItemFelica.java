package com.felicanetworks.mfm.main.model.internal.main.mfc;

/* JADX INFO: loaded from: classes3.dex */
public class AreaItemFelica {
    public final String areaCode;
    public final String systemCode;

    public AreaItemFelica(String systemCode, String areaCode) {
        this.systemCode = systemCode;
        this.areaCode = areaCode;
    }

    public String toString() {
        return "AreaItemFelica[" + this.systemCode + ", " + this.areaCode + "]";
    }
}
