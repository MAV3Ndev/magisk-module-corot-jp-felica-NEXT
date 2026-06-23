package com.felicanetworks.mfm.main.model.info.specific;

import com.felicanetworks.mfm.main.model.info.AssetInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class MySuicaInfo extends MyServiceInfo implements ServiceInfo.PrepaidEmoney, ServiceInfo.History {
    private int _balance;
    private List<ServiceInfo.History.HistoryData> _historyDataList;
    private final Position position;

    public enum Position {
        NONE,
        ENABLE,
        DISABLE,
        UNKNOWN
    }

    @Override // com.felicanetworks.mfm.main.model.info.MyServiceInfo, com.felicanetworks.mfm.main.model.info.ServiceInfo
    public String toString() {
        return "MySuicaInfo{_balance=" + this._balance + ",_historyDataList=" + this._historyDataList + "} " + super.toString();
    }

    public MySuicaInfo(MyServiceInfo service, AssetInfo assetInfo, Position position) {
        super(service);
        this._balance = -1;
        this._historyDataList = new ArrayList();
        this.position = position;
        if (assetInfo == null) {
            return;
        }
        setBalance(assetInfo.getBalanceValue());
        setHistoryDataList(assetInfo.getHistoryDataList());
    }

    @Override // com.felicanetworks.mfm.main.model.info.MyServiceInfo
    public MyServiceInfo.RegisterState getRegisterState() {
        MyServiceInfo.DetectionResult detectionResult = this._detection.get(MyServiceInfo.DetectionType.CARD);
        Position position = getPosition();
        if (MyServiceInfo.DetectionResult.DETECTION == detectionResult || Position.ENABLE == position || Position.DISABLE == position) {
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
    public void setBalance(int value) {
        this._balance = value;
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

    public boolean isActive() {
        return this.position == Position.ENABLE;
    }

    public Position getPosition() {
        return this.position;
    }
}
