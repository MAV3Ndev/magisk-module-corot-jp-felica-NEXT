package com.felicanetworks.common.cmnview;

import android.app.Activity;
import android.content.res.Configuration;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseView implements FunctionCodeInterface {
    public abstract void onActivityDestroy();

    public void onActivityResume() {
    }

    public abstract void onConfigurationChanged(Configuration configuration);

    public BaseView(Activity activity) {
    }

    public void transferState(int i, TransferStateData transferStateData) {
        TransferState.transferState(i, transferStateData, createTransferSenderInfo());
    }

    public void transferState(int i) {
        TransferState.transferState(i, null, createTransferSenderInfo());
    }

    public synchronized void transferFatalError(String str) {
        TransferState.transferFatalError(str, createTransferSenderInfo());
    }

    protected TransferSenderInfo createTransferSenderInfo() {
        TransferSenderInfo transferSenderInfo = new TransferSenderInfo();
        transferSenderInfo.senderInfo = getSenderId();
        return transferSenderInfo;
    }

    public int getSenderId() {
        return getClassCode();
    }
}
