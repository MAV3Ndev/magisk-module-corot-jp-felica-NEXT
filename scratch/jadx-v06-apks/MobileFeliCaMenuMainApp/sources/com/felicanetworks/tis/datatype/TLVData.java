package com.felicanetworks.tis.datatype;

import com.felicanetworks.tis.util.ByteBufferMgr;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
class TLVData {
    protected static final int BYTE_LENGTH_OF_LENGTH = 1;
    protected static final int BYTE_LENGTH_OF_TAG = 1;
    protected int mLength;
    protected int mTag;
    protected byte[] mValue;

    TLVData(byte[] bArr) {
        try {
            this.mTag = ByteBufferMgr.getValueFromByteArray(false, Arrays.copyOfRange(bArr, 0, 1));
            int valueFromByteArray = ByteBufferMgr.getValueFromByteArray(false, Arrays.copyOfRange(bArr, 1, 2));
            this.mLength = valueFromByteArray;
            int i = valueFromByteArray + 2;
            if (i > bArr.length) {
                throw new IllegalArgumentException();
            }
            this.mValue = Arrays.copyOfRange(bArr, 2, i);
        } catch (Exception unused) {
            throw new IllegalArgumentException("Invalid byte data");
        }
    }

    public int getTag() {
        return this.mTag;
    }

    public int getLength() {
        return this.mLength;
    }

    public byte[] getValue() {
        return this.mValue;
    }
}
