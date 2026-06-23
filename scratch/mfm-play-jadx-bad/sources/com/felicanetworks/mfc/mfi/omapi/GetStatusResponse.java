package com.felicanetworks.mfc.mfi.omapi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
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

    public GetStatusResponse(final byte[] response) throws IllegalArgumentException {
        this.mTLVResponse = null;
        this.mSWCode = 0;
        if (response == null || response.length < 2) {
            throw new IllegalArgumentException();
        }
        this.mSWCode = getSWCode(response);
        List<Byte> byteList = TLVResponse.toByteList(response);
        this.mTLVResponse = new TLVResponse(byteList.subList(0, byteList.size() - 2));
    }

    public List<byte[]> getApplicationRelatedDataList() throws IllegalArgumentException {
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null) {
            return null;
        }
        return tLVResponse.getValueList(APPLICATION_RELATED_DATA);
    }

    public List<byte[]> getApplicationDataList(byte tag) throws IllegalArgumentException {
        if (this.mTLVResponse == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<byte[]> it = this.mTLVResponse.getValueList(APPLICATION_RELATED_DATA).iterator();
        while (it.hasNext()) {
            byte[] value = new TLVResponse(TLVResponse.toByteList(it.next())).getValue(tag);
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
            if (value != null && value.length == 2 && (value[1] & 1) != 0) {
                arrayList.add(tLVResponse.getValue(APPLICATION_AID));
            }
        }
        return arrayList;
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
