package com.felicanetworks.mfm.main.model.info.specific;

import com.felicanetworks.mfm.main.model.info.AssetInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;

/* JADX INFO: loaded from: classes3.dex */
public class MyDcardInfo extends MyServiceInfo implements ServiceInfo.PostpayEmoney {
    private int _available;
    private int _limit;
    private DcardType dcardType;

    public enum DcardType {
        UNKNOWN(0),
        DCARD_MINI(1),
        OTHER(2);

        final int value;

        DcardType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static DcardType resoleve(int value) {
            for (DcardType dcardType : values()) {
                if (dcardType.value == value) {
                    return dcardType;
                }
            }
            return UNKNOWN;
        }
    }

    @Override // com.felicanetworks.mfm.main.model.info.MyServiceInfo, com.felicanetworks.mfm.main.model.info.ServiceInfo
    public String toString() {
        return "MyDcardInfo{_limit=" + this._limit + ", _available=" + this._available + ", dcardType=" + this.dcardType + "} " + super.toString();
    }

    public MyDcardInfo(MyServiceInfo service, AssetInfo assetInfo) {
        super(service);
        this.dcardType = DcardType.UNKNOWN;
        if (assetInfo == null) {
            return;
        }
        setCreditLimit(assetInfo.getBalanceLimit());
        setAvailableCredit(assetInfo.getBalanceValue());
        setDcardType(Boolean.valueOf(assetInfo.getIsDcardMini()));
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.PostpayEmoney
    public int getCreditLimit() {
        return this._limit;
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.PostpayEmoney
    public void setCreditLimit(int value) {
        this._limit = value;
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.PostpayEmoney
    public int getAvailableCredit() {
        return this._available;
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.PostpayEmoney
    public void setAvailableCredit(int value) {
        this._available = value;
    }

    public DcardType getDcardType() {
        return this.dcardType;
    }

    public void setDcardType(Boolean value) {
        DcardType dcardType;
        if (value.booleanValue()) {
            dcardType = DcardType.DCARD_MINI;
        } else {
            dcardType = DcardType.OTHER;
        }
        this.dcardType = dcardType;
    }
}
