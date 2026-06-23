package com.felicanetworks.common.cmnview;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractTransferStateCommon implements TransferStateListener {
    protected abstract ViewLayer getViewLayer();

    protected abstract boolean isActivityAlive();

    protected abstract boolean isFatalErrorScreen(BaseView baseView);

    protected abstract void onTransferState(int i, TransferStateData transferStateData, TransferSenderInfo transferSenderInfo);

    @Override // com.felicanetworks.common.cmnview.TransferStateListener
    public final void transferState(int i, TransferStateData transferStateData, TransferSenderInfo transferSenderInfo) {
        onTransferState(i, transferStateData, transferSenderInfo);
    }
}
