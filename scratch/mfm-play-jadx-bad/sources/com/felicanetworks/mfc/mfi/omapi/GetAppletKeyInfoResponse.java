package com.felicanetworks.mfc.mfi.omapi;

import java.util.Arrays;

/* JADX INFO: loaded from: classes3.dex */
public class GetAppletKeyInfoResponse {
    private static final int KCV_LENGTH = 3;
    private static final int KEY_VERSION_LENGTH = 2;
    private static final int LENGTH_SIZE = 1;
    private static final int P1P2_0200 = 512;
    private static final int P1P2_0201 = 513;
    private static final int P1P2_0202 = 514;
    private static final byte RES_LENGTH_P1P2_0200 = 3;
    private static final byte RES_LENGTH_P1P2_0201_0202 = 5;
    private static final int SW_CODE_LENGTH = 2;
    private static final int SW_SUCCESS = 36864;
    private static final int TAG_LENGTH = 2;
    private int mP1P2;
    private byte[] mResponse;
    private int mSWCode;

    public GetAppletKeyInfoResponse(final byte[] response) throws IllegalArgumentException {
        this.mSWCode = 0;
        this.mP1P2 = 0;
        if (response == null || response.length < 2) {
            throw new IllegalArgumentException();
        }
        this.mResponse = response;
        this.mSWCode = getSWCode();
        if (isStatusSuccess()) {
            int i = ((response[0] & 255) << 8) | (response[1] & 255);
            switch (i) {
                case 512:
                    if (response.length != 8 || response[2] != 3) {
                        throw new IllegalArgumentException();
                    }
                    break;
                case 513:
                case P1P2_0202 /* 514 */:
                    if (response.length != 10 || response[2] != 5) {
                        throw new IllegalArgumentException();
                    }
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            this.mP1P2 = i;
        }
    }

    public byte[] getKeyVersion() {
        int i = this.mP1P2;
        if (i == 513 || i == P1P2_0202) {
            return Arrays.copyOfRange(this.mResponse, 3, 5);
        }
        return null;
    }

    public byte[] getKeyCheckValue() {
        switch (this.mP1P2) {
            case 512:
            case P1P2_0202 /* 514 */:
                return Arrays.copyOfRange(this.mResponse, 3, 6);
            case 513:
                return Arrays.copyOfRange(this.mResponse, 5, 8);
            default:
                return null;
        }
    }

    public boolean isStatusSuccess() throws IllegalArgumentException {
        return this.mSWCode == SW_SUCCESS;
    }

    private int getSWCode() throws IllegalArgumentException {
        byte[] bArr = this.mResponse;
        if (bArr == null || bArr.length < 2) {
            return 0;
        }
        return (bArr[bArr.length - 1] & 255) | ((bArr[bArr.length - 2] & 255) << 8);
    }

    public byte[] getSw() throws IllegalArgumentException {
        int i = this.mSWCode;
        if (i == 0) {
            return new byte[]{0, 0};
        }
        return new byte[]{(byte) ((65280 & i) >> 8), (byte) (i & 255)};
    }
}
