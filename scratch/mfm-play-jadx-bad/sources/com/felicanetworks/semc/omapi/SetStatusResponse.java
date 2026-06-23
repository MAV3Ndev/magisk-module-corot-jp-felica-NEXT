package com.felicanetworks.semc.omapi;

import com.felicanetworks.semc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class SetStatusResponse {
    private static final byte APPLICATION_AID = 79;
    private static final byte APPLICATION_RELATED_DATA = 97;
    private static final int SW_ACTIVATION_CONFLICT = 25392;
    private static final int SW_CODE_LENGTH = 2;
    private static final int SW_SUCCESS = 36864;
    private int mSWCode;

    public SetStatusResponse(byte[] bArr) throws IllegalArgumentException {
        LogMgr.log(6, "000");
        if (bArr == null || bArr.length < 2) {
            LogMgr.log(1, "800 response == null || response.length < SW_CODE_LENGTH. ");
            throw new IllegalArgumentException();
        }
        this.mSWCode = getSWCode(bArr);
        LogMgr.log(6, "999");
    }

    public boolean isStatusSuccess() {
        return this.mSWCode == SW_SUCCESS;
    }

    public boolean isStatusActivationConflict() {
        return this.mSWCode == SW_ACTIVATION_CONFLICT;
    }

    private int getSWCode(byte[] bArr) {
        LogMgr.log(6, "000");
        if (bArr == null || bArr.length < 2) {
            LogMgr.log(6, "998 response == null || response.length < SW_CODE_LENGTH");
            return 0;
        }
        int i = (bArr[bArr.length - 1] & 255) | ((bArr[bArr.length - 2] & 255) << 8);
        LogMgr.log(6, "999 swCode=[" + Integer.toHexString(i) + "]");
        return i;
    }

    public byte[] getSw() {
        LogMgr.log(6, "000");
        if (this.mSWCode == 0) {
            LogMgr.log(6, "998mSWCode == 0");
            return new byte[]{0, 0};
        }
        LogMgr.log(6, "999");
        int i = this.mSWCode;
        return new byte[]{(byte) ((65280 & i) >> 8), (byte) (i & 255)};
    }
}
