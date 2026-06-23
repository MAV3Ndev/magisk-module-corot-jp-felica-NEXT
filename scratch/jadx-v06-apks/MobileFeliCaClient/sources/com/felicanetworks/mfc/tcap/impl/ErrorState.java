package com.felicanetworks.mfc.tcap.impl;

/* JADX INFO: loaded from: classes.dex */
public class ErrorState extends TcapState {
    private static ErrorState sInstance;

    public static ErrorState getInstance() {
        if (sInstance == null) {
            sInstance = new ErrorState();
        }
        return sInstance;
    }

    private ErrorState() {
    }

    @Override // com.felicanetworks.mfc.tcap.impl.TcapState
    public void doExecution(TcapContext tcapContext) throws HttpException, TcapException {
        tcapContext.setState(null);
    }
}
