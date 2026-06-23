package com.felicanetworks.semc.omapi;

import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
class TLVData {
    private static final byte INDEFINITE_FORM = 0;
    private static final byte LENGTH_MASK = 127;
    private static final byte LONG_LENGTH_IDENTIFIER = -128;
    private static final int MAX_SUPPORT_LENGTH_BYTE_SIZE = 4;
    private static final byte RESERVED_FORM = 127;
    protected List<TLVData> mChildList;
    protected boolean mHasChild;
    protected int mLength;
    protected int mLengthSize;
    protected int mTerminalLength;
    protected TLVType mType;
    protected List<Byte> mValue;

    TLVData(List<Byte> list) throws IllegalArgumentException {
        this.mChildList = null;
        this.mHasChild = false;
        this.mTerminalLength = 0;
        LogMgr.log(7, "000");
        try {
            TLVType tLVType = new TLVType(list);
            this.mType = tLVType;
            calculateLength(tLVType.size(), list);
            this.mValue = list.subList(this.mType.size() + this.mLengthSize, this.mType.size() + this.mLengthSize + this.mLength);
            if (this.mType.isConstructed()) {
                this.mHasChild = true;
            }
            LogMgr.log(7, "999");
        } catch (Exception e) {
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    protected TLVData() {
        this.mChildList = null;
        this.mHasChild = false;
        this.mTerminalLength = 0;
    }

    protected void calculateLength(int i, List<Byte> list) {
        LogMgr.log(7, "000");
        try {
            Byte b = list.get(i);
            if ((b.byteValue() & (-128)) == -128) {
                int iByteValue = b.byteValue() & 127;
                if (iByteValue != 0) {
                    if (iByteValue == 127) {
                        throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
                    }
                    this.mLengthSize = iByteValue + 1;
                    this.mLength = getMultiByteLength(i + 1, iByteValue, list);
                } else if (this.mType.isConstructed()) {
                    this.mLengthSize = 1;
                    this.mLength = getIndefiniteLength(i + 1, list);
                    this.mTerminalLength = 2;
                } else {
                    throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
                }
            } else {
                this.mLengthSize = 1;
                this.mLength = b.byteValue() & 127;
            }
            LogMgr.log(7, "999");
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint(e));
        } catch (Exception unused) {
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
        }
    }

    protected int getIndefiniteLength(int i, List<Byte> list) {
        int i2 = 0;
        byte bByteValue = 1;
        for (Byte b : list) {
            if (i2 >= i) {
                if (b.byteValue() == 0 && bByteValue == 0) {
                    return (i2 - i) - 1;
                }
                bByteValue = b.byteValue();
            }
            i2++;
        }
        throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
    }

    protected int getMultiByteLength(int i, int i2, List<Byte> list) {
        LogMgr.log(6, "000");
        if (i2 > 4) {
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
        }
        int iByteValue = 0;
        for (int i3 = 0; i3 < i2; i3++) {
            iByteValue += (list.get(i + i3).byteValue() & 255) << (((i2 - i3) - 1) * 8);
        }
        if (iByteValue < 0) {
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
        }
        LogMgr.log(6, "999");
        return iByteValue;
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
        LogMgr.log(7, "000");
        if (this.mChildList == null) {
            this.mChildList = new ArrayList();
        }
        this.mChildList.add(tLVData);
        LogMgr.log(7, "999");
    }

    protected List<TLVData> getChildDataList() {
        return this.mChildList;
    }

    protected int nextTLVEntryOffset() throws IllegalArgumentException {
        LogMgr.log(7, "000");
        try {
            if (this.mHasChild) {
                LogMgr.log(7, "998");
                return this.mType.size() + this.mLengthSize;
            }
            LogMgr.log(7, "999");
            return this.mType.size() + this.mLengthSize + getLength() + this.mTerminalLength;
        } catch (NullPointerException e) {
            LogMgr.log(2, "700 NullPointerException" + e.getMessage());
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint(e));
        }
    }
}
