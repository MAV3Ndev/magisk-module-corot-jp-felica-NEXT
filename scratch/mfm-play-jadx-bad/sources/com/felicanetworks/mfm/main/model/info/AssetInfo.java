package com.felicanetworks.mfm.main.model.info;

import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
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

    public AssetInfo(String serviceId, String cid, int balanceValue, int balanceLimit, int point1, int point2, Date date1, Date date2, boolean isDcardMini) {
        this.serviceId = serviceId;
        this.cardId = cid;
        this.balanceValue = balanceValue;
        this.balanceLimit = balanceLimit;
        this.point1 = point1;
        this.point2 = point2;
        this.date1 = date1 != null ? (Date) date1.clone() : null;
        this.date2 = date2 != null ? (Date) date2.clone() : null;
        this.isDcardMini = isDcardMini;
        this.historyDataList = new ArrayList();
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public void setBalanceValue(int balanceValue) {
        this.balanceValue = balanceValue;
    }

    public void setBalanceLimit(int balanceLimit) {
        this.balanceLimit = balanceLimit;
    }

    public void setPoint1(int point1) {
        this.point1 = point1;
    }

    public void setPoint2(int point2) {
        this.point2 = point2;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public void setIsDcardMini(boolean isDcardMini) {
        this.isDcardMini = isDcardMini;
    }

    public void setHistoryDataList(List<ServiceInfo.History.HistoryData> historyDataList) {
        this.historyDataList = historyDataList;
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
