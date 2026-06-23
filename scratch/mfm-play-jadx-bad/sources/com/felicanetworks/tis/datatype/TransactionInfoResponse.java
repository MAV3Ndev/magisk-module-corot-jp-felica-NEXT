package com.felicanetworks.tis.datatype;

import com.felicanetworks.tis.util.ByteBufferMgr;
import java.util.Arrays;

/* JADX INFO: loaded from: classes3.dex */
public class TransactionInfoResponse {
    private static final int BYTE_LENGTH_OF_LENGTH = 1;
    private static final int BYTE_LENGTH_OF_SW = 2;
    private static final int BYTE_LENGTH_OF_TAG = 2;
    private static final byte[] SW_DATA_AVAILABLE = {-112, 0};
    private static final byte[] SW_NO_DATA = {106, -126};
    private boolean mIsTimeOut;
    private int mLength;
    private byte[] mSW;
    private int mTag;
    private TransactionInfo mTransactionInfo;

    public TransactionInfoResponse(byte[] bArr) {
        this.mIsTimeOut = false;
        try {
            byte[] bArr2 = SW_DATA_AVAILABLE;
            if (Arrays.equals(bArr, bArr2)) {
                this.mIsTimeOut = Arrays.equals(bArr, bArr2);
                return;
            }
            byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, bArr.length - 2, bArr.length);
            this.mSW = bArrCopyOfRange;
            if (Arrays.equals(bArrCopyOfRange, bArr2)) {
                this.mTag = ByteBufferMgr.getValueFromByteArray(false, bArr, 0, 2);
                int valueFromByteArray = ByteBufferMgr.getValueFromByteArray(false, bArr, 2, 1);
                this.mLength = valueFromByteArray;
                int i = valueFromByteArray + 3;
                if (i > bArr.length) {
                    throw new IllegalArgumentException();
                }
                if (valueFromByteArray > 0) {
                    this.mTransactionInfo = new TransactionInfo(Arrays.copyOfRange(bArr, 3, i));
                } else {
                    this.mTransactionInfo = null;
                }
            }
        } catch (Exception unused) {
            throw new IllegalArgumentException("Invalid byte data");
        }
    }

    public int getTag() {
        if (!this.mIsTimeOut) {
            return this.mTag;
        }
        throw new IllegalArgumentException("The response has no tag");
    }

    public int getLength() {
        if (!this.mIsTimeOut) {
            return this.mLength;
        }
        throw new IllegalArgumentException("The response has no length");
    }

    public TransactionInfo getTransactionInfo() {
        if (!this.mIsTimeOut) {
            return this.mTransactionInfo;
        }
        throw new IllegalArgumentException("The response has no transaction info");
    }

    public boolean isDataExist() {
        if (this.mIsTimeOut) {
            return false;
        }
        return Arrays.equals(this.mSW, SW_DATA_AVAILABLE);
    }

    public boolean isTimeOut() {
        return this.mIsTimeOut;
    }
}
