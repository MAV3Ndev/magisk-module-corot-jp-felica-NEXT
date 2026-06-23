package com.felicanetworks.mfm.main.model.info.specific;

import com.felicanetworks.mfm.main.model.info.Linkage;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;

/* JADX INFO: loaded from: classes3.dex */
public class MyQUICPayInfo extends MyServiceInfo {
    private QPType qpType;

    public enum QPType {
        UNKNOWN(0),
        QP_MOBILE(1),
        QP_LOCAL(2),
        QP_MFI(3);

        final int value;

        QPType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    @Override // com.felicanetworks.mfm.main.model.info.MyServiceInfo, com.felicanetworks.mfm.main.model.info.ServiceInfo
    public String toString() {
        return "MyQUICPayInfo{qpType=" + this.qpType + "} " + super.toString();
    }

    public MyQUICPayInfo(MyServiceInfo service, QPType qpType) {
        super(service);
        this.qpType = qpType;
    }

    public MyQUICPayInfo(MyServiceInfo service, QPType qpType, Linkage linkage) {
        super(service, linkage);
        this.qpType = qpType;
    }

    public QPType getQPType() {
        return this.qpType;
    }

    public void setQPType(QPType value) {
        this.qpType = value;
    }
}
