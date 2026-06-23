package com.felicanetworks.mfc.mfi.omapi;

/* JADX INFO: loaded from: classes3.dex */
public class ResponseAnalyzer {
    private static final int SW_CODE_LENGTH = 2;
    private static final int SW_SUCCESS = 36864;
    private int mSWCode;

    public ResponseAnalyzer(final byte[] response) throws IllegalArgumentException {
        this.mSWCode = 0;
        if (response == null || response.length < 2) {
            throw new IllegalArgumentException();
        }
        this.mSWCode = getSWCode(response);
    }

    public boolean isStatusSuccess() throws IllegalArgumentException {
        return this.mSWCode == SW_SUCCESS;
    }

    private int getSWCode(byte[] response) {
        if (response == null || response.length < 2) {
            return 0;
        }
        return (response[response.length - 1] & 255) | ((response[response.length - 2] & 255) << 8);
    }

    public byte[] getSw() throws IllegalArgumentException {
        int i = this.mSWCode;
        if (i == 0) {
            return new byte[]{0, 0};
        }
        return new byte[]{(byte) ((65280 & i) >> 8), (byte) (i & 255)};
    }
}
