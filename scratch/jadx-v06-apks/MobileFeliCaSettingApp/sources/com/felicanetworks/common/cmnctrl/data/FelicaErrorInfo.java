package com.felicanetworks.common.cmnctrl.data;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class FelicaErrorInfo implements Serializable {
    public static final String EX_TYPE_VALUE = "01";
    public static final int INIT_VALUE = 0;
    public static final String LS_TYPE_VALUE = "02";
    public int errorID;
    public int errorType;
    public String genTypeValue;
    public String message;
    public int statusFlg1;
    public int statusFlg2;

    public FelicaErrorInfo(String str, int i, int i2, int i3, int i4, String str2) {
        this.genTypeValue = "";
        this.errorID = 0;
        this.errorType = 0;
        this.statusFlg1 = 0;
        this.statusFlg2 = 0;
        this.message = "";
        this.genTypeValue = str;
        this.errorID = i;
        this.errorType = i2;
        this.statusFlg1 = i3;
        this.statusFlg2 = i4;
        this.message = str2;
    }

    public String toString() {
        return "FelicaErrorInfo {class=" + this.genTypeValue + " id=" + this.errorID + " type=" + this.errorType + " flg1=" + this.statusFlg1 + " flg2=" + this.statusFlg2 + " message=" + this.message + "}";
    }
}
