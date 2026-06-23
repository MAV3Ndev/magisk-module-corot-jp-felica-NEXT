package com.felicanetworks.mfc.mfi.omapi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class GetTypeFCurrentProtocolDataResponse {
    private static final byte[] COMMAND_TAG = {3, 0};
    private static final int DEFAULT_LENGTH_SIZE = 1;
    private static final int LENGTH_INDEX = 2;
    private static final byte LENGTH_MASK = 127;
    private static final byte LONG_LENGTH_IDENTIFIER = -128;
    private static final int MAX_BYTE_LENGTH = 2;
    private static final byte PROTOCOL_PARAMETER_DATA = -96;
    private static final int SW_CODE_LENGTH = 2;
    private static final int SW_SUCCESS = 36864;
    private static final int SYSTEMCODE_LENGTH = 2;
    private static final byte SYSTEM_CODE = -128;
    private static final byte TYPE_F_ANTI_COLLISION_PARAMETERS_ENTRY = -96;
    private int mSWCode;
    private TLVResponse mTLVResponse;

    public GetTypeFCurrentProtocolDataResponse(byte[] bArr) throws IllegalArgumentException {
        int i;
        this.mTLVResponse = null;
        this.mSWCode = 0;
        if (bArr == null || bArr.length < 2) {
            throw new IllegalArgumentException();
        }
        this.mSWCode = getSWCode(bArr);
        if (isStatusSuccess()) {
            int length = bArr.length;
            byte[] bArr2 = COMMAND_TAG;
            int i2 = 1;
            if (length < bArr2.length + 1 + 2 || bArr[0] != bArr2[0] || bArr[1] != bArr2[1]) {
                throw new IllegalArgumentException();
            }
            if ((bArr[2] & (-128)) == -128) {
                int i3 = ((byte) (bArr[2] & LENGTH_MASK)) + 1;
                if (i3 != 2) {
                    throw new IllegalArgumentException();
                }
                i = bArr[(i3 + 2) - 1] & 255;
                i2 = i3;
            } else {
                i = bArr[2] & 255;
            }
            if (bArr.length > i2 + 4) {
                int i4 = i2 + 2;
                this.mTLVResponse = new TLVResponse(TLVResponse.toByteList(bArr).subList(i4, i + i4));
            }
        }
    }

    public int[] getActivatedSystemCodeList() throws IllegalArgumentException {
        ArrayList arrayList = new ArrayList();
        List<byte[]> arrayList2 = new ArrayList<>();
        try {
            if (this.mTLVResponse != null) {
                arrayList2 = this.mTLVResponse.getValueList(-96, -96);
            }
        } catch (IllegalArgumentException unused) {
        }
        Iterator<byte[]> it = arrayList2.iterator();
        while (it.hasNext()) {
            byte[] value = new TLVResponse(TLVResponse.toByteList(it.next())).getValue(-128);
            if (value != null && value.length == 2) {
                arrayList.add(Integer.valueOf((value[1] & 255) | ((value[0] & 255) << 8)));
            }
        }
        int[] iArr = new int[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            iArr[i] = ((Integer) arrayList.get(i)).intValue();
        }
        return iArr;
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
