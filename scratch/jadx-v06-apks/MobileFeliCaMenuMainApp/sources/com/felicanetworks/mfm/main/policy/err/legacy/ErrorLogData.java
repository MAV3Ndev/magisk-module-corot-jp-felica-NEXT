package com.felicanetworks.mfm.main.policy.err.legacy;

/* JADX INFO: loaded from: classes.dex */
public class ErrorLogData {
    public String date;
    public String idm;
    public String log;

    public ErrorLogData(String str, String str2, String str3) {
        this.date = str;
        this.log = str2;
        this.idm = str3;
    }

    public String toString() {
        return "ErrorLogData[" + this.date + ", " + this.log + ", " + this.idm + "]";
    }
}
