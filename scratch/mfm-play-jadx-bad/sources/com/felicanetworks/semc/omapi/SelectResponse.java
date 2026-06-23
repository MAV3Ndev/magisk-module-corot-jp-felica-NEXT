package com.felicanetworks.semc.omapi;

import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;

/* JADX INFO: loaded from: classes3.dex */
public class SelectResponse {
    private static final int SW_62XX = 25088;
    private static final int SW_63XX = 25344;
    private static final int SW_9000 = 36864;
    private static final int SW_CODE_LENGTH = 2;
    private int mSWCode;

    public SelectResponse(byte[] bArr) throws IllegalArgumentException {
        LogMgr.log(6, "000");
        if (bArr == null || bArr.length < 2) {
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
        }
        this.mSWCode = getSWCode(bArr);
        LogMgr.log(6, "999");
    }

    public byte[] getSw() throws IllegalArgumentException {
        LogMgr.log(6, "000");
        if (this.mSWCode == 0) {
            return new byte[]{0, 0};
        }
        LogMgr.log(6, "999");
        int i = this.mSWCode;
        return new byte[]{(byte) ((65280 & i) >> 8), (byte) (i & 255)};
    }

    public boolean isStatusFailed() throws IllegalArgumentException {
        int i = this.mSWCode;
        return ((i & 65280) == SW_62XX || (65280 & i) == SW_63XX || i == SW_9000) ? false : true;
    }

    public boolean isStatusFailedInNotOnline() throws IllegalArgumentException {
        return (this.mSWCode & 65280) != SW_9000;
    }

    private int getSWCode(byte[] bArr) {
        LogMgr.log(6, "000");
        if (bArr == null || bArr.length < 2) {
            return 0;
        }
        int i = bArr[bArr.length - 2] & 255;
        int i2 = bArr[bArr.length - 1] & 255;
        LogMgr.log(6, "999");
        return i2 | (i << 8);
    }
}
