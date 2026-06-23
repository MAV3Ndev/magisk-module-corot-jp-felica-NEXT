package com.felicanetworks.mfc.felica.omapi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class TLVData {
    private static final byte INDEFINITE_FORM = 0;
    private static final byte LENGTH_MASK = 127;
    private static final byte LONG_LENGTH_IDENTIFIER = -128;
    private static final int MAX_SUPPORT_LENGTH_BYTE_SIZE = 4;
    private static final byte RESERVED_FORM = 127;
    private boolean mHasChild;
    private int mLengthSize;
    private TLVType mType;
    private List<Byte> mValue;
    private int mLength = 0;
    private List<TLVData> mChildList = null;
    private int mTerminalLength = 0;

    TLVData(List<Byte> list) throws IllegalArgumentException {
        this.mType = null;
        this.mValue = null;
        this.mHasChild = false;
        try {
            this.mType = new TLVType(list);
            calculateLength(this.mType.size(), list);
            this.mValue = list.subList(this.mType.size() + this.mLengthSize, this.mType.size() + this.mLengthSize + this.mLength);
            if (this.mType.isConstructed()) {
                this.mHasChild = true;
            }
        } catch (Exception unused) {
            throw new IllegalArgumentException();
        }
    }

    private void calculateLength(int i, List<Byte> list) {
        try {
            Byte b = list.get(i);
            if ((b.byteValue() & (-128)) == -128) {
                int iByteValue = b.byteValue() & 127;
                if (iByteValue != 0) {
                    if (iByteValue == 127) {
                        throw new IllegalArgumentException();
                    }
                    this.mLengthSize = iByteValue + 1;
                    this.mLength = getMultiByteLength(i + 1, iByteValue, list);
                    return;
                }
                if (this.mType.isConstructed()) {
                    this.mLengthSize = 1;
                    this.mLength = getIndefiniteLength(i + 1, list);
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

    private int getIndefiniteLength(int i, List<Byte> list) {
        Iterator<Byte> it = list.iterator();
        boolean z = false;
        int i2 = 0;
        byte bByteValue = 1;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Byte next = it.next();
            if (i2 >= i) {
                if (next.byteValue() == 0 && bByteValue == 0) {
                    z = true;
                    break;
                }
                bByteValue = next.byteValue();
            }
            i2++;
        }
        if (z) {
            return (i2 - i) - 1;
        }
        throw new IllegalArgumentException();
    }

    private int getMultiByteLength(int i, int i2, List<Byte> list) {
        if (i2 > 4) {
            throw new IllegalArgumentException();
        }
        int iByteValue = 0;
        for (int i3 = 0; i3 < i2; i3++) {
            iByteValue += (list.get(i + i3).byteValue() & 255) << (((i2 - i3) - 1) * 8);
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

    protected void addChild(TLVData tLVData) {
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
