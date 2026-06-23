package com.felicanetworks.mfc.mfi.omapi;

import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class GetDataResponse {
    private static final byte SD_DESTINATION_DATA = -128;
    private static final byte SD_MANAGEMENT_DATA = -125;
    private static final byte SD_RECONFIGURATION_DATA = 115;
    private static final int SW_CODE_LENGTH = 2;
    private static final int SW_SUCCESS = 36864;
    private byte[] mParameters;
    private int mSWCode;
    private TLVResponse mTLVResponse;

    public GetDataResponse(final byte[] response, byte p1, byte p2) throws IllegalArgumentException {
        this.mTLVResponse = null;
        this.mSWCode = 0;
        if (response == null || response.length < 2) {
            throw new IllegalArgumentException();
        }
        this.mParameters = new byte[]{p1, p2};
        this.mSWCode = getSWCode(response);
        List<Byte> byteList = TLVResponse.toByteList(response);
        this.mTLVResponse = new TLVResponse(byteList.subList(0, byteList.size() - 2));
    }

    public byte[] getDestinationData() throws IllegalArgumentException {
        byte[] bArr;
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null || (bArr = this.mParameters) == null) {
            throw new IllegalArgumentException();
        }
        byte b = bArr[0];
        byte[] value = b == 0 ? tLVResponse.getValue(bArr[1], SD_RECONFIGURATION_DATA, -128) : tLVResponse.getValue(b, bArr[1], SD_RECONFIGURATION_DATA, -128);
        if (value != null) {
            return value;
        }
        throw new IllegalArgumentException();
    }

    public byte[] getManagementData() throws IllegalArgumentException {
        byte[] bArr;
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null || (bArr = this.mParameters) == null) {
            throw new IllegalArgumentException();
        }
        byte b = bArr[0];
        byte[] value = b == 0 ? tLVResponse.getValue(bArr[1], SD_RECONFIGURATION_DATA, SD_MANAGEMENT_DATA) : tLVResponse.getValue(b, bArr[1], SD_RECONFIGURATION_DATA, SD_MANAGEMENT_DATA);
        if (value != null) {
            return value;
        }
        throw new IllegalArgumentException();
    }

    public byte[] getParameterData() throws IllegalArgumentException {
        byte[] bArr;
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null || (bArr = this.mParameters) == null) {
            throw new IllegalArgumentException();
        }
        byte b = bArr[0];
        byte[] value = b == 0 ? tLVResponse.getValue(bArr[1]) : tLVResponse.getValue(b, bArr[1]);
        if (value != null) {
            return value;
        }
        throw new IllegalArgumentException();
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
