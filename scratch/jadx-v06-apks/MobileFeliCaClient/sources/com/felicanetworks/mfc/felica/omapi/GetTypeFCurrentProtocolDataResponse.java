package com.felicanetworks.mfc.felica.omapi;

import com.felicanetworks.mfc.tcap.impl.IllegalStateErrorException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class GetTypeFCurrentProtocolDataResponse {
    private int mSWCode;
    private TLVResponse mTLVResponse;
    private final int SW_SUCCESS = 36864;
    private final byte[] COMMAND_TAG = {3, 0};
    private final byte PROTOCOL_PARAMETER_DATA = -96;
    private final byte TYPE_F_ANTI_COLLISION_PARAMETERS_ENTRY = -96;
    private final byte SYSTEM_CODE = IllegalStateErrorException.TYPE_ILLEGAL_STATE;
    private final byte LONG_LENGTH_IDENTIFIER = IllegalStateErrorException.TYPE_ILLEGAL_STATE;
    private final byte LENGTH_MASK = 127;
    private final int SW_CODE_LENGTH = 2;
    private final int DEFAULT_LENGTH_SIZE = 1;
    private final int MAX_BYTE_LENGTH = 2;
    private final int LENGTH_INDEX = 2;

    public GetTypeFCurrentProtocolDataResponse(byte[] bArr) throws IllegalArgumentException {
        int i;
        int i2;
        this.mTLVResponse = null;
        this.mSWCode = 0;
        if (bArr != null) {
            int length = bArr.length;
            byte[] bArr2 = this.COMMAND_TAG;
            if (length >= bArr2.length + 1 + 2 && bArr[0] == bArr2[0] && bArr[1] == bArr2[1]) {
                if ((bArr[2] & IllegalStateErrorException.TYPE_ILLEGAL_STATE) == -128) {
                    i2 = ((byte) (bArr[2] & 127)) + 1;
                    if (i2 != 2) {
                        throw new IllegalArgumentException();
                    }
                    i = bArr[(i2 + 2) - 1] & 255;
                } else {
                    i = bArr[2] & 255;
                    i2 = 1;
                }
                this.mSWCode = getSWCode(bArr);
                if (bArr.length > i2 + 4) {
                    int i3 = i2 + 2;
                    this.mTLVResponse = new TLVResponse(TLVResponse.toByteList(bArr).subList(i3, i + i3));
                    return;
                }
                return;
            }
        }
        throw new IllegalArgumentException();
    }

    public int[] getActivatedSystemCodeList() {
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
            byte[] value = new TLVResponse(TLVResponse.toByteList(it.next())).getValue(IllegalStateErrorException.TYPE_ILLEGAL_STATE);
            arrayList.add(Integer.valueOf((value[1] & 255) | ((value[0] & 255) << 8)));
        }
        int[] iArr = new int[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            iArr[i] = ((Integer) arrayList.get(i)).intValue();
        }
        return iArr;
    }

    public boolean isStatusSuccess() throws IllegalArgumentException {
        return this.mSWCode == 36864;
    }

    private int getSWCode(byte[] bArr) {
        if (bArr == null || bArr.length < 2) {
            return 0;
        }
        return ((bArr[bArr.length - 2] & 255) << 8) | (bArr[bArr.length - 1] & 255);
    }
}
