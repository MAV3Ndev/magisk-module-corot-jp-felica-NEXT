package com.felicanetworks.mfc.mfi.omapi;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
class TLVData {
    private static final byte INDEFINITE_FORM = 0;
    private static final byte LENGTH_MASK = 127;
    private static final byte LONG_LENGTH_IDENTIFIER = -128;
    private static final int MAX_SUPPORT_LENGTH_BYTE_SIZE = 4;
    private static final byte RESERVED_FORM = 127;
    private boolean mHasChild;
    private int mLength;
    private int mLengthSize;
    private TLVType mType;
    private List<Byte> mValue;
    private List<TLVData> mChildList = null;
    private int mTerminalLength = 0;

    TLVData(final List<Byte> data) throws IllegalArgumentException {
        this.mHasChild = false;
        try {
            TLVType tLVType = new TLVType(data);
            this.mType = tLVType;
            calculateLength(tLVType.size(), data);
            this.mValue = data.subList(this.mType.size() + this.mLengthSize, this.mType.size() + this.mLengthSize + this.mLength);
            if (this.mType.isConstructed()) {
                this.mHasChild = true;
            }
        } catch (Exception unused) {
            throw new IllegalArgumentException();
        }
    }

    private void calculateLength(int offset, List<Byte> data) {
        try {
            Byte b = data.get(offset);
            if ((b.byteValue() & (-128)) == -128) {
                int iByteValue = b.byteValue() & 127;
                if (iByteValue != 0) {
                    if (iByteValue == 127) {
                        throw new IllegalArgumentException();
                    }
                    this.mLengthSize = iByteValue + 1;
                    this.mLength = getMultiByteLength(offset + 1, iByteValue, data);
                    return;
                }
                if (this.mType.isConstructed()) {
                    this.mLengthSize = 1;
                    this.mLength = getIndefiniteLength(offset + 1, data);
                    this.mTerminalLength = 2;
                    return;
                }
                throw new IllegalArgumentException();
            }
            this.mLengthSize = 1;
            this.mLength = b.byteValue() & 127;
        } catch (Exception unused) {
            throw new IllegalArgumentException();
        }
    }

    private int getIndefiniteLength(int offset, List<Byte> data) {
        int i = 0;
        byte bByteValue = 1;
        for (Byte b : data) {
            if (i >= offset) {
                if (b.byteValue() == 0 && bByteValue == 0) {
                    return (i - offset) - 1;
                }
                bByteValue = b.byteValue();
            }
            i++;
        }
        throw new IllegalArgumentException();
    }

    private int getMultiByteLength(int offset, int number, List<Byte> data) {
        if (number > 4) {
            throw new IllegalArgumentException();
        }
        int iByteValue = 0;
        for (int i = 0; i < number; i++) {
            iByteValue += (data.get(offset + i).byteValue() & 255) << (((number - i) - 1) * 8);
        }
        if (iByteValue >= 0) {
            return iByteValue;
        }
        throw new IllegalArgumentException();
    }

    public final TLVType getType() {
        return this.mType;
    }

    public final int getLength() {
        return this.mLength;
    }

    public final List<Byte> getValue() {
        return this.mValue;
    }

    public final boolean hasChild() {
        return this.mHasChild;
    }

    protected void addChild(final TLVData tLVData) {
        if (this.mChildList == null) {
            this.mChildList = new ArrayList();
        }
        this.mChildList.add(tLVData);
    }

    protected List<TLVData> getChildDataList() {
        return this.mChildList;
    }

    protected int nextTLVEntryOffset() throws IllegalArgumentException {
        try {
            if (this.mHasChild) {
                return this.mType.size() + this.mLengthSize;
            }
            return this.mType.size() + this.mLengthSize + getLength() + this.mTerminalLength;
        } catch (NullPointerException unused) {
            throw new IllegalArgumentException();
        }
    }
}
