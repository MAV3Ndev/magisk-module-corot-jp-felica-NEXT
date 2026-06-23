package com.felicanetworks.common.cmnview;

/* JADX INFO: loaded from: classes.dex */
public class TransferData {
    TransferStateData data;
    TransferSenderInfo senderInfo;

    public TransferData(TransferStateData transferStateData, TransferSenderInfo transferSenderInfo) {
        this.data = null;
        this.senderInfo = null;
        this.data = transferStateData;
        this.senderInfo = transferSenderInfo;
    }

    public String toString() {
        return "[data=" + this.data + ",senderInfo=" + this.senderInfo + "]";
    }
}
