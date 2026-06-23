package com.felicanetworks.mfm.main.model.info;

import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AssetInfo {
    private int balanceLimit;
    private int balanceValue;
    private String cardId;
    private Date date1;
    private Date date2;
    private List<ServiceInfo.History.HistoryData> historyDataList;
    private boolean isDcardMini;
    private int point1;
    private int point2;
    private String serviceId;

    public AssetInfo(String str, String str2, int i, int i2, int i3, int i4, Date date, Date date2, boolean z) {
        this.serviceId = str;
        this.cardId = str2;
        this.balanceValue = i;
        this.balanceLimit = i2;
        this.point1 = i3;
        this.point2 = i4;
        this.date1 = date != null ? (Date) date.clone() : null;
        this.date2 = date2 != null ? (Date) date2.clone() : null;
        this.isDcardMini = z;
        this.historyDataList = new ArrayList();
    }

    public void setServiceId(String str) {
        this.serviceId = str;
    }

    public void setCardId(String str) {
        this.cardId = str;
    }

    public void setBalanceValue(int i) {
        this.balanceValue = i;
    }

    public void setBalanceLimit(int i) {
        this.balanceLimit = i;
    }

    public void setPoint1(int i) {
        this.point1 = i;
    }

    public void setPoint2(int i) {
        this.point2 = i;
    }

    public void setDate1(Date date) {
        this.date1 = date;
    }

    public void setDate2(Date date) {
        this.date2 = date;
    }

    public void setIsDcardMini(boolean z) {
        this.isDcardMini = z;
    }

    public void setHistoryDataList(List<ServiceInfo.History.HistoryData> list) {
        this.historyDataList = list;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public String getCardId() {
        return this.cardId;
    }

    public int getBalanceValue() {
        return this.balanceValue;
    }

    public int getBalanceLimit() {
        return this.balanceLimit;
    }

    public int getPoint1() {
        return this.point1;
    }

    public int getPoint2() {
        return this.point2;
    }

    public Date getDate1() {
        return this.date1;
    }

    public Date getDate2() {
        return this.date2;
    }

    public boolean getIsDcardMini() {
        return this.isDcardMini;
    }

    public List<ServiceInfo.History.HistoryData> getHistoryDataList() {
        return this.historyDataList;
    }

    public String toString() {
        return "Asset{serviceId='" + this.serviceId + "', cardId='" + this.cardId + ", balanceValue=" + this.balanceValue + ", balanceLimit=" + this.balanceLimit + ", point1=" + this.point1 + ", point2=" + this.point2 + ", date1=" + this.date1 + ", date2=" + this.date2 + ", historyDataList=" + this.historyDataList + '}';
    }
}
