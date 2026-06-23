package com.felicanetworks.tis.datatype;

import com.felicanetworks.tis.util.ByteBufferMgr;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class TransactionInfo extends TLVData {
    private static final int ACCESS_MODE_MASK = 7;
    private static final int BYTE_LENGTH_OF_BLOCK_DATA = 16;
    private static final int BYTE_LENGTH_OF_BLOCK_NUMBER = 2;
    private static final int BYTE_LENGTH_OF_CID = 63;
    private static final int BYTE_LENGTH_OF_IDM = 8;
    private static final int BYTE_LENGTH_OF_READ_WRITE_TYPE = 1;
    private static final int BYTE_LENGTH_OF_SERVICE_CODE = 2;
    private static final int BYTE_LENGTH_OF_SYSTEM_CODE = 2;
    private static final int BYTE_LENGTH_OF_TRANSACTION_COUNTER = 2;
    private static final int COMMAND_SEPARATOR_MASK = 128;
    private static final int TAG_READING_INFO_WIRED = 146;
    private static final int TAG_READING_INFO_WIRELESS = 130;
    private static final int TAG_SYSTEM_INFO_WIRED = 144;
    private static final int TAG_SYSTEM_INFO_WIRELESS = 128;
    private static final int TAG_TRANSACTION_DETECTING_WIRED = 147;
    private static final int TAG_TRANSACTION_DETECTING_WIRELESS = 131;
    private static final int TAG_WRITING_INFO_WIRED = 145;
    private static final int TAG_WRITING_INFO_WIRELESS = 129;
    private static final int TRANSACTION_COUNTER_MAX = 32767;
    private static final int TRANSACTION_COUNTER_MIN = 0;

    @Override // com.felicanetworks.tis.datatype.TLVData
    public /* bridge */ /* synthetic */ int getLength() {
        return super.getLength();
    }

    @Override // com.felicanetworks.tis.datatype.TLVData
    public /* bridge */ /* synthetic */ int getTag() {
        return super.getTag();
    }

    @Override // com.felicanetworks.tis.datatype.TLVData
    public /* bridge */ /* synthetic */ byte[] getValue() {
        return super.getValue();
    }

    public TransactionInfo(byte[] bArr) {
        super(bArr);
    }

    public boolean isSystemInfo() {
        return this.mTag == 128 || this.mTag == TAG_SYSTEM_INFO_WIRED;
    }

    public boolean isWritingInfo() {
        return this.mTag == TAG_WRITING_INFO_WIRELESS || this.mTag == TAG_WRITING_INFO_WIRED;
    }

    public boolean isReadingInfo() {
        return this.mTag == 130 || this.mTag == TAG_READING_INFO_WIRED;
    }

    public boolean isTransactionDetectingInfo() {
        return this.mTag == TAG_TRANSACTION_DETECTING_WIRELESS || this.mTag == TAG_TRANSACTION_DETECTING_WIRED;
    }

    public boolean isWirelessData() {
        return this.mTag == 128 || this.mTag == TAG_WRITING_INFO_WIRELESS || this.mTag == 130 || this.mTag == TAG_TRANSACTION_DETECTING_WIRELESS;
    }

    public boolean isTransactionCounterValid() {
        int transactionCounter = getTransactionCounter();
        return transactionCounter >= 0 && transactionCounter <= TRANSACTION_COUNTER_MAX;
    }

    public int getTransactionCounter() throws IllegalStateException {
        if (isSystemInfo() || isWritingInfo() || isReadingInfo() || isTransactionDetectingInfo()) {
            return ByteBufferMgr.getValueFromByteArray(false, Arrays.copyOfRange(this.mValue, 0, 2));
        }
        throw new IllegalStateException("No transaction counter in this data.");
    }

    public int getSystemCode() throws IllegalStateException {
        if (isSystemInfo()) {
            return ByteBufferMgr.getValueFromByteArray(false, this.mValue, 2, 2);
        }
        throw new IllegalStateException("No system code in this data.");
    }

    public int getServiceCode() throws IllegalStateException {
        if (isWritingInfo() || isReadingInfo()) {
            return ByteBufferMgr.getValueFromByteArray(false, this.mValue, 2, 2);
        }
        throw new IllegalStateException("No service code in this data.");
    }

    public byte[] getIdm() throws IllegalStateException {
        if (isSystemInfo()) {
            return Arrays.copyOfRange(this.mValue, 4, 12);
        }
        throw new IllegalStateException("No IDm in this data.");
    }

    public byte[] getCid() {
        if (isSystemInfo()) {
            if (this.mValue.length > 12) {
                return Arrays.copyOfRange(this.mValue, 12, 75);
            }
            return null;
        }
        throw new IllegalStateException("No CID in this data.");
    }

    public int getReadWriteType() throws IllegalStateException {
        if (isWritingInfo() || isReadingInfo()) {
            return ByteBufferMgr.getValueFromByteArray(false, this.mValue, 4, 1);
        }
        throw new IllegalStateException("No read write type in this data.");
    }

    public int getAccessMode() throws IllegalStateException {
        return getReadWriteType() & 7;
    }

    public int getBlockNumber() throws IllegalStateException {
        if (isWritingInfo() || isReadingInfo()) {
            return ByteBufferMgr.getValueFromByteArray(false, this.mValue, 5, 2);
        }
        throw new IllegalStateException("No block number in this data.");
    }

    public byte[] getBlockData() throws IllegalStateException {
        if (isWritingInfo() || isReadingInfo()) {
            return Arrays.copyOfRange(this.mValue, 7, 23);
        }
        throw new IllegalStateException("No block number in this data.");
    }

    public boolean isStartOfCommand() throws IllegalStateException {
        return (getReadWriteType() & 128) == 128;
    }
}
