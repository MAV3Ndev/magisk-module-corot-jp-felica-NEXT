package com.felicanetworks.mfm.main.policy.err.legacy;

/* JADX INFO: loaded from: classes3.dex */
public class ErrorLogData {
    public String date;
    public String idm;
    public String log;

    public ErrorLogData(String date, String log, String idm) {
        this.date = date;
        this.log = log;
        this.idm = idm;
    }

    public String toString() {
        return "ErrorLogData[" + this.date + ", " + this.log + ", " + this.idm + "]";
    }
}
