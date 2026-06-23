package com.felicanetworks.mfm.main.presenter.agent;

/* JADX INFO: loaded from: classes.dex */
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

    public HistoryAgent(String str, UseType useType, int i, boolean z) {
        this._date = str;
        this._usetype = useType;
        this._money = i;
        this._isplus = z;
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
