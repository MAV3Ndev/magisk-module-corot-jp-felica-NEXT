package com.felicanetworks.mfm.main.model.info.specific;

import com.felicanetworks.mfm.main.model.info.AssetInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MyWaonInfo extends MyServiceInfo implements ServiceInfo.PrepaidEmoney, ServiceInfo.Point, ServiceInfo.History {
    public static final String SERVICE_ID = "SV000011";
    private int _balance;
    private List<ServiceInfo.History.HistoryData> _historyDataList;
    private List<ServiceInfo.Point.PointData> _points;

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo
    public ServiceInfo.Point getPoint() {
        return null;
    }

    @Override // com.felicanetworks.mfm.main.model.info.MyServiceInfo, com.felicanetworks.mfm.main.model.info.ServiceInfo
    public String toString() {
        return "MyWaonInfo{_balance=" + this._balance + ", _points=" + this._points + ", _historyDataList=" + this._historyDataList + "} " + super.toString();
    }

    public MyWaonInfo(MyServiceInfo myServiceInfo, AssetInfo assetInfo) {
        super(myServiceInfo);
        this._balance = -1;
        this._points = new ArrayList();
        this._historyDataList = new ArrayList();
        if (assetInfo == null) {
            return;
        }
        setBalance(assetInfo.getBalanceValue());
        setPointDataList(Arrays.asList(new ServiceInfo.Point.PointData(assetInfo.getPoint1(), assetInfo.getDate1()), new ServiceInfo.Point.PointData(assetInfo.getPoint2(), assetInfo.getDate2())));
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

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.Point
    public List<ServiceInfo.Point.PointData> getPointDataList() {
        return this._points;
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.Point
    public void setPointDataList(List<ServiceInfo.Point.PointData> list) {
        this._points.clear();
        if (list == null) {
            return;
        }
        this._points.addAll(list);
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.Point
    public int getValidPoint() {
        return getValidPoint(this._points);
    }

    public static int getValidPoint(List<ServiceInfo.Point.PointData> list) {
        Iterator<ServiceInfo.Point.PointData> it = list.iterator();
        boolean z = false;
        int point = 0;
        while (it.hasNext()) {
            point += it.next().getPoint();
            z = true;
        }
        if (z) {
            return point;
        }
        return -1;
    }
}
