package com.felicanetworks.mfc.mfi.omapi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class GetStatusResponse {
    private static final byte ACTIVATE_MASK = 1;
    private static final byte APPLICATION_AID = 79;
    private static final byte APPLICATION_RELATED_DATA = 97;
    private static final byte[] LIFECYCLE_STATE = {-97, 112};
    private static final int LIFECYCLE_STATE_LENGTH = 2;
    private static final int SW_CODE_LENGTH = 2;
    private static final int SW_SUCCESS = 36864;
    private int mSWCode;
    private TLVResponse mTLVResponse;

    public GetStatusResponse(byte[] bArr) throws IllegalArgumentException {
        this.mTLVResponse = null;
        this.mSWCode = 0;
        if (bArr == null || bArr.length < 2) {
            throw new IllegalArgumentException();
        }
        this.mSWCode = getSWCode(bArr);
        List<Byte> byteList = TLVResponse.toByteList(bArr);
        this.mTLVResponse = new TLVResponse(byteList.subList(0, byteList.size() - 2));
    }

    public List<byte[]> getApplicationRelatedDataList() throws IllegalArgumentException {
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null) {
            return null;
        }
        return tLVResponse.getValueList(APPLICATION_RELATED_DATA);
    }

    public List<byte[]> getApplicationDataList(byte b) throws IllegalArgumentException {
        if (this.mTLVResponse == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<byte[]> it = this.mTLVResponse.getValueList(APPLICATION_RELATED_DATA).iterator();
        while (it.hasNext()) {
            byte[] value = new TLVResponse(TLVResponse.toByteList(it.next())).getValue(b);
            if (value != null) {
                arrayList.add(value);
            }
        }
        return arrayList;
    }

    public List<byte[]> getActivatedAidList() throws IllegalArgumentException {
        if (this.mTLVResponse == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<byte[]> it = this.mTLVResponse.getValueList(APPLICATION_RELATED_DATA).iterator();
        while (it.hasNext()) {
            TLVResponse tLVResponse = new TLVResponse(TLVResponse.toByteList(it.next()));
            byte[] bArr = LIFECYCLE_STATE;
            byte[] value = tLVResponse.getValue(bArr[0], bArr[1]);
            if (value != null && value.length == 2 && (value[1] & ACTIVATE_MASK) != 0) {
                arrayList.add(tLVResponse.getValue(APPLICATION_AID));
            }
        }
        return arrayList;
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
