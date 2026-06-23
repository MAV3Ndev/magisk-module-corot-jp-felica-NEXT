package com.felicanetworks.semc.omapi;

import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;

/* JADX INFO: loaded from: classes3.dex */
public class ResponseAnalyzer {
    private static final int SW_62XX = 25088;
    private static final int SW_63XX = 25344;
    private static final int SW_9000 = 36864;
    private static final int SW_CODE_LENGTH = 2;
    private int mSWCode;

    public ResponseAnalyzer(byte[] bArr) throws IllegalArgumentException {
        LogMgr.log(6, "000");
        if (bArr == null || bArr.length < 2) {
            LogMgr.log(6, "998");
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
        }
        LogMgr.log(6, "999");
        this.mSWCode = getSWCode(bArr);
    }

    public boolean isStatusFailed() throws IllegalArgumentException {
        int i = this.mSWCode;
        return ((i & 65280) == SW_62XX || (65280 & i) == SW_63XX || i == SW_9000) ? false : true;
    }

    public boolean isStatusFailedInConnect() throws IllegalArgumentException {
        return (this.mSWCode & 65280) != SW_9000;
    }

    private int getSWCode(byte[] bArr) {
        LogMgr.log(6, "000");
        if (bArr == null || bArr.length < 2) {
            return 0;
        }
        int i = (bArr[bArr.length - 1] & 255) | ((bArr[bArr.length - 2] & 255) << 8);
        LogMgr.log(6, "999");
        return i;
    }

    public byte[] getSw() throws IllegalArgumentException {
        LogMgr.log(6, "000");
        if (this.mSWCode == 0) {
            LogMgr.log(6, "998");
            return new byte[]{0, 0};
        }
        LogMgr.log(6, "999");
        int i = this.mSWCode;
        return new byte[]{(byte) ((65280 & i) >> 8), (byte) (i & 255)};
    }
}
