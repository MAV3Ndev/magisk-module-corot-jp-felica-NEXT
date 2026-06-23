package com.felicanetworks.tis.datatype;

import com.felicanetworks.tis.util.ByteBufferMgr;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class TransactionDetectingEvent {
    private static final int BYTE_LENGTH_OF_FORMAT_VERSION = 2;
    private static final int BYTE_LENGTH_OF_ID_CODE = 1;
    private static final int BYTE_LENGTH_OF_LENGTH = 1;
    private static final int BYTE_LENGTH_OF_TAG = 1;
    private static final int END_TRANSACTION_COUNTER_MAX = 32767;
    private static final int END_TRANSACTION_COUNTER_MIN = 0;
    private static final int TAG_END_TRANSACTION_COUNTER = 132;
    private static final int TAG_IDM = 129;
    private static final int TAG_SYSTEM_CODE = 128;
    private static final int TRANSACTION_DETECTING_EVENT_ID = 1;
    private int mIdCode;
    private List<TLVData> mSubTLVDataList;
    private TLVData mWholeTLVData;

    public TransactionDetectingEvent(byte[] bArr) {
        try {
            this.mIdCode = ByteBufferMgr.getValueFromByteArray(false, bArr, 0, 1);
            TLVData tLVData = new TLVData(Arrays.copyOfRange(bArr, 3, bArr.length));
            this.mWholeTLVData = tLVData;
            this.mSubTLVDataList = splitTLVData(tLVData.getValue());
        } catch (Exception unused) {
            throw new IllegalArgumentException("Invalid byte data");
        }
    }

    private List<TLVData> splitTLVData(byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i != bArr.length) {
            try {
                int valueFromByteArray = ByteBufferMgr.getValueFromByteArray(false, bArr, i + 1, 1) + 2 + i;
                arrayList.add(new TLVData(Arrays.copyOfRange(bArr, i, valueFromByteArray)));
                i = valueFromByteArray;
            } catch (Exception unused) {
                throw new IllegalArgumentException("Invalid byte data");
            }
        }
        return arrayList;
    }

    public TLVData getWholeTLVData() {
        return this.mWholeTLVData;
    }

    public int getSystemCode() {
        for (TLVData tLVData : this.mSubTLVDataList) {
            if (tLVData.getTag() == 128) {
                return ByteBufferMgr.getValueFromByteArray(false, tLVData.getValue());
            }
        }
        return -1;
    }

    public byte[] getIdm() {
        for (TLVData tLVData : this.mSubTLVDataList) {
            if (tLVData.getTag() == TAG_IDM) {
                return tLVData.getValue();
            }
        }
        return null;
    }

    public int getEndTransactionCounter() {
        for (TLVData tLVData : this.mSubTLVDataList) {
            if (tLVData.getTag() == TAG_END_TRANSACTION_COUNTER) {
                return ByteBufferMgr.getValueFromByteArray(false, tLVData.getValue());
            }
        }
        return -1;
    }

    public boolean isEventValid() {
        int endTransactionCounter = getEndTransactionCounter();
        return this.mIdCode == 1 && endTransactionCounter >= 0 && endTransactionCounter <= END_TRANSACTION_COUNTER_MAX;
    }
}
