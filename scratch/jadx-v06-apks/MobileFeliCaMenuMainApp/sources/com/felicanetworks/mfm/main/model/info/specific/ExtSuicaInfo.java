package com.felicanetworks.mfm.main.model.info.specific;

import com.felicanetworks.mfm.main.model.info.ExtIcCardInfo;
import com.felicanetworks.mfm.main.model.info.Linkage;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.policy.sg.Sg;

/* JADX INFO: loaded from: classes.dex */
public class ExtSuicaInfo extends ExtIcCardInfo implements ServiceInfo.PrepaidEmoney {
    private int _balance;

    @Override // com.felicanetworks.mfm.main.model.info.ExtIcCardInfo, com.felicanetworks.mfm.main.model.info.ServiceInfo
    public String toString() {
        return "ExtSuicaInfo{_balance=" + this._balance + "} " + super.toString();
    }

    public ExtSuicaInfo(String str, String str2, String str3, String str4, Linkage linkage, int i, DatabaseExpert databaseExpert, boolean z) {
        super(str, str2, str3, str4, linkage, databaseExpert, z);
        this._balance = i;
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo
    public String getName() {
        return (String) Sg.getValue(Sg.Key.SETTING_TRANSPORTATION_IC_CARD_NAME);
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.PrepaidEmoney
    public int getBalance() {
        return this._balance;
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.PrepaidEmoney
    public void setBalance(int i) {
        this._balance = i;
    }
}
