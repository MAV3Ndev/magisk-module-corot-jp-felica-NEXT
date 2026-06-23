package com.felicanetworks.mfc.tcap;

/* JADX INFO: loaded from: classes.dex */
public interface TcapClientEventListener {
    void errorOccurred(int i, String str);

    void finished(int i);

    byte[] operationRequested(int i, String str, byte[] bArr) throws Exception;
}
