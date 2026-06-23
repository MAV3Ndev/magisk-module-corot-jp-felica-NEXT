package com.felicanetworks.mfm.main.model.info.specific;

import com.felicanetworks.mfm.main.model.info.AssetInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class MyNanacoInfo extends MyServiceInfo implements ServiceInfo.PrepaidEmoney, ServiceInfo.Point, ServiceInfo.History {
    public static final String SERVICE_ID = "SV000075";
    private int _balance;
    private List<ServiceInfo.History.HistoryData> _historyDataList;
    private List<ServiceInfo.Point.PointData> _points;

    @Override // com.felicanetworks.mfm.main.model.info.MyServiceInfo, com.felicanetworks.mfm.main.model.info.ServiceInfo
    public String toString() {
        return "MyNanacoInfo{_balance=" + this._balance + ", _points=" + this._points + ",_historyDataList=" + this._historyDataList + "} " + super.toString();
    }

    public MyNanacoInfo(MyServiceInfo myServiceInfo, AssetInfo assetInfo) {
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
        boolean z = false;
        int point = 0;
        for (ServiceInfo.Point.PointData pointData : list) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
            String str = simpleDateFormat.format(new Date());
            Date expiration = pointData.getExpiration();
            if (expiration != null && simpleDateFormat.format(expiration).compareTo(str) >= 0) {
                point += pointData.getPoint();
                z = true;
            }
        }
        if (z) {
            return point;
        }
        return -1;
    }
}
