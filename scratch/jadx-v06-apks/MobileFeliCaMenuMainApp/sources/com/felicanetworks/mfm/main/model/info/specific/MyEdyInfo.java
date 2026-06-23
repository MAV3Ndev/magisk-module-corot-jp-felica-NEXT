package com.felicanetworks.mfm.main.model.info.specific;

import com.felicanetworks.mfm.main.model.info.AssetInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MyEdyInfo extends MyServiceInfo implements ServiceInfo.PrepaidEmoney, ServiceInfo.History {
    public static final String SERVICE_ID = "SV000013";
    private int _balance;
    private List<ServiceInfo.History.HistoryData> _historyDataList;

    @Override // com.felicanetworks.mfm.main.model.info.MyServiceInfo, com.felicanetworks.mfm.main.model.info.ServiceInfo
    public String toString() {
        return "MyEdyInfo{_balance=" + this._balance + ",_historyDataList=" + this._historyDataList + "} " + super.toString();
    }

    public MyEdyInfo(MyServiceInfo myServiceInfo, AssetInfo assetInfo) {
        super(myServiceInfo);
        this._balance = -1;
        this._historyDataList = new ArrayList();
        if (assetInfo == null) {
            return;
        }
        setBalance(assetInfo.getBalanceValue());
        setHistoryDataList(assetInfo.getHistoryDataList());
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.PrepaidEmoney
    public int getBalance() {
        return this._balance;
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.PrepaidEmoney
    public void setBalance(int i) {
        this._balance = i;
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.History
    public List<ServiceInfo.History.HistoryData> getHistoryDataList() {
        return this._historyDataList;
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.History
    public void setHistoryDataList(List<ServiceInfo.History.HistoryData> list) {
        this._historyDataList.clear();
        if (list == null) {
            return;
        }
        this._historyDataList.addAll(list);
    }
}
