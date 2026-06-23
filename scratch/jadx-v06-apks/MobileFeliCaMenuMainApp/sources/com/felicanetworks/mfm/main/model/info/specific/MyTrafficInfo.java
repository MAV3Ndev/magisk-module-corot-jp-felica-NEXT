package com.felicanetworks.mfm.main.model.info.specific;

import com.felicanetworks.mfm.main.model.info.AssetInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MyTrafficInfo extends MyServiceInfo implements ServiceInfo.PrepaidEmoney, ServiceInfo.History {
    private int _balance;
    private List<ServiceInfo.History.HistoryData> _historyDataList;

    @Override // com.felicanetworks.mfm.main.model.info.MyServiceInfo, com.felicanetworks.mfm.main.model.info.ServiceInfo
    public String toString() {
        return "MyTrafficInfo{_balance=" + this._balance + ", _id='" + this._id + "', _version='" + this._version + "', _name='" + this._name + "', _provider='" + this._provider + "', _linkage=" + this._linkage + ", _db=" + this._db + ", _historyDataList=" + this._historyDataList + "} " + super.toString();
    }

    public MyTrafficInfo(MyServiceInfo myServiceInfo, AssetInfo assetInfo) {
        super(myServiceInfo);
        this._balance = -1;
        this._historyDataList = new ArrayList();
        if (assetInfo == null) {
            return;
        }
        setBalance(assetInfo.getBalanceValue());
        setHistoryDataList(assetInfo.getHistoryDataList());
    }

    @Override // com.felicanetworks.mfm.main.model.info.MyServiceInfo
    public MyServiceInfo.RegisterState getRegisterState() {
        MyServiceInfo.DetectionResult detectionResult = this._detection.get(MyServiceInfo.DetectionType.CARD);
        if (MyServiceInfo.DetectionResult.DETECTION == detectionResult) {
            return MyServiceInfo.RegisterState.REGISTERED;
        }
        if (MyServiceInfo.DetectionResult.INVALID == detectionResult) {
            return MyServiceInfo.RegisterState.NONE;
        }
        return MyServiceInfo.RegisterState.NO_REGISTER;
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
