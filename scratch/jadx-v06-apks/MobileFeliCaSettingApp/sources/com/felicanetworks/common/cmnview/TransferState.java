package com.felicanetworks.common.cmnview;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;

/* JADX INFO: loaded from: classes.dex */
public class TransferState implements FunctionCodeInterface {
    private static TransferStateHandler _handler = new TransferStateHandler();

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 0;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 0;
    }

    private TransferState() {
    }

    public static void refreshHandler() {
        _handler._listener = null;
        _handler = new TransferStateHandler();
    }

    public static void setTransferStateListener(TransferStateListener transferStateListener) {
        _handler._listener = transferStateListener;
    }

    public static void transferState(int i, TransferStateData transferStateData, TransferSenderInfo transferSenderInfo) {
        TransferStateHandler transferStateHandler = _handler;
        transferStateHandler.sendMessage(transferStateHandler.obtainMessage(i, new TransferData(transferStateData, transferSenderInfo)));
    }

    public static void transferFatalError(String str, TransferSenderInfo transferSenderInfo) {
        TransferStateData transferStateData = new TransferStateData();
        transferStateData.errorId = str;
        transferState(TransferStateId.COMMANDID_TRANS_FATALERROR, transferStateData, transferSenderInfo);
    }

    public static void transferState(int i) {
        transferState(i, null, null);
    }

    public static void transferState(int i, TransferStateData transferStateData) {
        transferState(i, transferStateData, null);
    }

    public static void transferFatalError(String str) {
        transferFatalError(str, null);
    }

    private static class TransferStateHandler extends Handler {
        public TransferStateListener _listener;

        public TransferStateHandler() {
            super(Looper.getMainLooper());
            this._listener = null;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            TransferSenderInfo transferSenderInfo;
            super.handleMessage(message);
            int i = message.what;
            Object obj = message.obj;
            TransferStateData transferStateData = null;
            if (obj == null || !(obj instanceof TransferData)) {
                transferSenderInfo = null;
            } else {
                TransferData transferData = (TransferData) obj;
                transferStateData = transferData.data;
                transferSenderInfo = transferData.senderInfo;
            }
            TransferStateListener transferStateListener = this._listener;
            if (transferStateListener != null) {
                transferStateListener.transferState(i, transferStateData, transferSenderInfo);
            }
        }
    }
}
