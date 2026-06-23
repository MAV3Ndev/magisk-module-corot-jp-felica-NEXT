package com.felicanetworks.mfm.main.model.info;

/* JADX INFO: loaded from: classes.dex */
public class AccountChangeHistoryInfo {
    private String _account;
    private String _changeAppId;
    private String _changeDate;

    public AccountChangeHistoryInfo(String str, String str2, String str3) {
        this._account = str;
        this._changeDate = str2;
        this._changeAppId = str3;
    }

    public String getAccount() {
        return this._account;
    }

    public String getChangeDate() {
        return this._changeDate;
    }

    public String getChangeAppId() {
        return this._changeAppId;
    }
}
