package com.felicanetworks.mfm.main.presenter.agent;

/* JADX INFO: loaded from: classes3.dex */
public class HistoryAgent {
    private String _date;
    private boolean _isplus;
    private int _money;
    private UseType _usetype;

    public enum UseType {
        CHARGE,
        PAYMENT,
        TRAFFIC,
        OTHER
    }

    public String toString() {
        return "HistoryData{_date=" + this._date + ", _usetype=" + this._usetype + ", _money=" + this._money + ", _isplus=" + this._isplus + '}';
    }

    public HistoryAgent(String date, UseType usetype, int money, boolean isplus) {
        this._date = date;
        this._usetype = usetype;
        this._money = money;
        this._isplus = isplus;
    }

    public String getDate() {
        return this._date;
    }

    public UseType getUseType() {
        return this._usetype;
    }

    public int getMoney() {
        return this._money;
    }

    public boolean getIsPlus() {
        return this._isplus;
    }
}
