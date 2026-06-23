package com.felicanetworks.semc.omapi;

import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class GetDataResponse {
    private static final byte SD_DESTINATION_DATA = -128;
    private static final byte SD_MANAGEMENT_DATA = -125;
    private static final byte SD_RECONFIGURATION_DATA = 115;
    private static final int SW_62XX = 25088;
    private static final int SW_63XX = 25344;
    private static final int SW_9000 = 36864;
    private static final int SW_CODE_LENGTH = 2;
    private final byte[] mParameters;
    private final int mSWCode;
    private final TLVResponse mTLVResponse;

    public GetDataResponse(byte[] bArr, byte b, byte b2) throws IllegalArgumentException {
        LogMgr.log(6, "000");
        if (bArr == null || bArr.length < 2) {
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
        }
        this.mParameters = new byte[]{b, b2};
        this.mSWCode = getSWCode(bArr);
        List<Byte> byteList = TLVResponse.toByteList(bArr);
        this.mTLVResponse = new TLVResponse(byteList.subList(0, byteList.size() - 2));
        LogMgr.log(6, "999");
    }

    public byte[] getDestinationData() throws IllegalArgumentException {
        byte[] bArr;
        LogMgr.log(6, "000");
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null || (bArr = this.mParameters) == null) {
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
        }
        byte b = bArr[0];
        byte[] value = b == 0 ? tLVResponse.getValue(bArr[1], SD_RECONFIGURATION_DATA, -128) : tLVResponse.getValue(b, bArr[1], SD_RECONFIGURATION_DATA, -128);
        if (value == null) {
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
        }
        LogMgr.log(6, "999");
        return value;
    }

    public byte[] getManagementData() throws IllegalArgumentException {
        byte[] bArr;
        LogMgr.log(6, "000");
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null || (bArr = this.mParameters) == null) {
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
        }
        byte b = bArr[0];
        byte[] value = b == 0 ? tLVResponse.getValue(bArr[1], SD_RECONFIGURATION_DATA, SD_MANAGEMENT_DATA) : tLVResponse.getValue(b, bArr[1], SD_RECONFIGURATION_DATA, SD_MANAGEMENT_DATA);
        if (value == null) {
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
        }
        LogMgr.log(6, "999");
        return value;
    }

    public byte[] getParameterData() throws IllegalArgumentException {
        byte[] bArr;
        LogMgr.log(6, "000");
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null || (bArr = this.mParameters) == null) {
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
        }
        byte b = bArr[0];
        byte[] value = b == 0 ? tLVResponse.getValue(bArr[1]) : tLVResponse.getValue(b, bArr[1]);
        if (value == null) {
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
        }
        LogMgr.log(6, "999");
        return value;
    }

    public boolean isStatusSuccess() {
        int i = this.mSWCode;
        return i == SW_9000 || (i & 65280) == SW_62XX || (i & 65280) == SW_63XX;
    }

    private int getSWCode(byte[] bArr) {
        LogMgr.log(6, "000");
        if (bArr == null || bArr.length < 2) {
            return 0;
        }
        int i = (bArr[bArr.length - 1] & 255) | ((bArr[bArr.length - 2] & 255) << 8);
        LogMgr.log(6, "999 swCode=[" + Integer.toHexString(i) + "]");
        return i;
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
}
