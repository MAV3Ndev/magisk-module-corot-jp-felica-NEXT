package com.felicanetworks.mfc.mfi.omapi;

/* JADX INFO: loaded from: classes.dex */
public class ResponseAnalyzer {
    private static final int SW_CODE_LENGTH = 2;
    private static final int SW_SUCCESS = 36864;
    private int mSWCode;

    public ResponseAnalyzer(byte[] bArr) throws IllegalArgumentException {
        this.mSWCode = 0;
        if (bArr == null || bArr.length < 2) {
            throw new IllegalArgumentException();
        }
        this.mSWCode = getSWCode(bArr);
    }

    public boolean isStatusSuccess() throws IllegalArgumentException {
        return this.mSWCode == SW_SUCCESS;
    }

    private int getSWCode(byte[] bArr) {
        if (bArr == null || bArr.length < 2) {
            return 0;
        }
        return (bArr[bArr.length - 1] & 255) | ((bArr[bArr.length - 2] & 255) << 8);
    }

    public byte[] getSw() throws IllegalArgumentException {
        int i = this.mSWCode;
        return i == 0 ? new byte[]{0, 0} : new byte[]{(byte) ((65280 & i) >> 8), (byte) (i & 255)};
    }
}
