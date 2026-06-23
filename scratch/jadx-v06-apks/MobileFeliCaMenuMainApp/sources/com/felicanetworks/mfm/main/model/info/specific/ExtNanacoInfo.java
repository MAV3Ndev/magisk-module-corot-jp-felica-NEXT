package com.felicanetworks.mfm.main.model.info.specific;

import com.felicanetworks.mfm.main.model.info.ExtIcCardInfo;
import com.felicanetworks.mfm.main.model.info.Linkage;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class ExtNanacoInfo extends ExtIcCardInfo implements ServiceInfo.PrepaidEmoney, ServiceInfo.Point {
    private int _balance;
    private List<ServiceInfo.Point.PointData> _points;

    @Override // com.felicanetworks.mfm.main.model.info.ExtIcCardInfo, com.felicanetworks.mfm.main.model.info.ServiceInfo
    public String toString() {
        return "ExtNanacoInfo{_balance=" + this._balance + ", _points=" + this._points + "} " + super.toString();
    }

    public ExtNanacoInfo(String str, String str2, String str3, String str4, Linkage linkage, int i, List<ServiceInfo.Point.PointData> list, DatabaseExpert databaseExpert, boolean z) {
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
        boolean z = false;
        int point = 0;
        for (ServiceInfo.Point.PointData pointData : this._points) {
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
