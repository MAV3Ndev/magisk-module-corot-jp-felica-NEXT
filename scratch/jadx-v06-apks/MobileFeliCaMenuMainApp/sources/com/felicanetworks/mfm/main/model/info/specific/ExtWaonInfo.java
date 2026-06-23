package com.felicanetworks.mfm.main.model.info.specific;

import com.felicanetworks.mfm.main.model.info.ExtIcCardInfo;
import com.felicanetworks.mfm.main.model.info.Linkage;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ExtWaonInfo extends ExtIcCardInfo implements ServiceInfo.PrepaidEmoney, ServiceInfo.Point {
    private int _balance;
    private List<ServiceInfo.Point.PointData> _points;

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo
    public ServiceInfo.Point getPoint() {
        return null;
    }

    @Override // com.felicanetworks.mfm.main.model.info.ExtIcCardInfo, com.felicanetworks.mfm.main.model.info.ServiceInfo
    public String toString() {
        return "ExtWaonInfo{_balance=" + this._balance + ", _points=" + this._points + "} " + super.toString();
    }

    public ExtWaonInfo(String str, String str2, String str3, String str4, Linkage linkage, int i, List<ServiceInfo.Point.PointData> list, DatabaseExpert databaseExpert, boolean z) {
        super(str, str2, str3, str4, linkage, databaseExpert, z);
        this._balance = i;
        this._points = list;
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.PrepaidEmoney
    public int getBalance() {
        return this._balance;
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.PrepaidEmoney
    public void setBalance(int i) {
        this._balance = i;
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.Point
    public List<ServiceInfo.Point.PointData> getPointDataList() {
        return this._points;
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.Point
    public void setPointDataList(List<ServiceInfo.Point.PointData> list) {
        this._points = list;
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.Point
    public int getValidPoint() {
        Iterator<ServiceInfo.Point.PointData> it = this._points.iterator();
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
