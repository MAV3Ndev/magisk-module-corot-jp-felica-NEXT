package com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.data;

/* JADX INFO: loaded from: classes3.dex */
public class FelicaErrorInfo {
    public static final int INIT_VALUE = 0;
    public int errorID;
    public int errorType;
    public String genTypeValue;
    public String message;
    public int statusFlg1;
    public int statusFlg2;

    public FelicaErrorInfo(String genTypeValue, int errorID, int errorType, int statusFlg1, int statusFlg2, String message) {
        this.genTypeValue = genTypeValue;
        this.errorID = errorID;
        this.errorType = errorType;
        this.statusFlg1 = statusFlg1;
        this.statusFlg2 = statusFlg2;
        this.message = message;
    }

    public String toString() {
        return "FelicaErrorInfo {class=" + this.genTypeValue + " id=" + this.errorID + " type=" + this.errorType + " flg1=" + this.statusFlg1 + " flg2=" + this.statusFlg2 + " message=" + this.message + "}";
    }
}
