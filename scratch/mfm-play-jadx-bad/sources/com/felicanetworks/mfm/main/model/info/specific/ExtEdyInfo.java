package com.felicanetworks.mfm.main.model.info.specific;

import com.felicanetworks.mfm.main.model.info.ExtIcCardInfo;
import com.felicanetworks.mfm.main.model.info.Linkage;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;

/* JADX INFO: loaded from: classes3.dex */
public class ExtEdyInfo extends ExtIcCardInfo implements ServiceInfo.PrepaidEmoney {
    private int _balance;

    @Override // com.felicanetworks.mfm.main.model.info.ExtIcCardInfo, com.felicanetworks.mfm.main.model.info.ServiceInfo
    public String toString() {
        return "ExtEdyInfo{_balance=" + this._balance + "} " + super.toString();
    }

    public ExtEdyInfo(String id, String version, String name, String provider, Linkage linkage, int balance, DatabaseExpert db, boolean hasFP) {
        super(id, version, name, provider, linkage, db, hasFP);
        this._balance = balance;
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.PrepaidEmoney
    public int getBalance() {
        return this._balance;
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.PrepaidEmoney
    public void setBalance(int value) {
        this._balance = value;
    }
}
