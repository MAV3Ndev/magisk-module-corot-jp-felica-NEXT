package com.felicanetworks.mfc.mfi.omapi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
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

    public GetTypeFCurrentProtocolDataResponse(final byte[] response) throws IllegalArgumentException {
        int i;
        this.mTLVResponse = null;
        this.mSWCode = 0;
        if (response == null || response.length < 2) {
            throw new IllegalArgumentException();
        }
        this.mSWCode = getSWCode(response);
        if (isStatusSuccess()) {
            int length = response.length;
            byte[] bArr = COMMAND_TAG;
            if (length >= bArr.length + 3 && response[0] == bArr[0]) {
                int i2 = 1;
                if (response[1] == bArr[1]) {
                    byte b = response[2];
                    if ((b & (-128)) == -128) {
                        byte b2 = (byte) (b & 127);
                        int i3 = b2 + 1;
                        if (i3 != 2) {
                            throw new IllegalArgumentException();
                        }
                        i = response[b2 + 2] & 255;
                        i2 = i3;
                    } else {
                        i = b & 255;
                    }
                    if (response.length > i2 + 4) {
                        int i4 = i2 + 2;
                        this.mTLVResponse = new TLVResponse(TLVResponse.toByteList(response).subList(i4, i + i4));
                        return;
                    }
                    return;
                }
            }
            throw new IllegalArgumentException();
        }
    }

    public int[] getActivatedSystemCodeList() throws IllegalArgumentException {
        int i;
        ArrayList arrayList = new ArrayList();
        List<byte[]> arrayList2 = new ArrayList<>();
        try {
            TLVResponse tLVResponse = this.mTLVResponse;
            if (tLVResponse != null) {
                arrayList2 = tLVResponse.getValueList(-96, -96);
            }
        } catch (IllegalArgumentException unused) {
        }
        Iterator<byte[]> it = arrayList2.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            byte[] value = new TLVResponse(TLVResponse.toByteList(it.next())).getValue(-128);
            if (value != null && value.length == 2) {
                arrayList.add(Integer.valueOf((value[1] & 255) | ((value[0] & 255) << 8)));
            }
        }
        int[] iArr = new int[arrayList.size()];
        for (i = 0; i < arrayList.size(); i++) {
            iArr[i] = ((Integer) arrayList.get(i)).intValue();
        }
        return iArr;
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
