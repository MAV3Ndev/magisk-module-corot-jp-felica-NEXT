package com.felicanetworks.mfw.i.fbl;

/* JADX INFO: loaded from: classes.dex */
public interface VeriferUtilListener {
    public static final int VERIFY_RESULT_NG = 1;
    public static final int VERIFY_RESULT_OK = 0;

    void ntfyVerificationEnd(int i);

    void resKeyValue(byte[] bArr, int i);
}
