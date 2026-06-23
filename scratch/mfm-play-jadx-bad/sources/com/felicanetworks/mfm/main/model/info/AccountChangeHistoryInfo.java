package com.felicanetworks.mfm.main.model.info;

/* JADX INFO: loaded from: classes3.dex */
public class AccountChangeHistoryInfo {
    private String _account;
    private String _changeAppId;
    private String _changeDate;

    public AccountChangeHistoryInfo(String account, String changeDate, String changeAppId) {
        this._account = account;
        this._changeDate = changeDate;
        this._changeAppId = changeAppId;
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
