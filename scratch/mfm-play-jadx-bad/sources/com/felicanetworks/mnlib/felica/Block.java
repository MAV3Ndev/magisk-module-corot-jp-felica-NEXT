package com.felicanetworks.mnlib.felica;

/* JADX INFO: loaded from: classes3.dex */
public class Block {
    public static final int TYPE_CYCLIC = 1;
    public static final int TYPE_CYCLIC_READ_ONLY = 2;
    public static final int TYPE_PURSE = 3;
    public static final int TYPE_PURSE_CASH_BACK = 4;
    public static final int TYPE_PURSE_DECREMENT = 5;
    public static final int TYPE_PURSE_READ_ONLY = 6;
    public static final int TYPE_RANDOM = 7;
    public static final int TYPE_RANDOM_READ_ONLY = 8;
    private int mBlockNo;
    private int mServiceCode;
    private int mType;

    public Block(int i, int i2) throws IllegalArgumentException {
        setServiceCode(i);
        setBlockNo(i2);
    }

    public Block(int i) throws IllegalArgumentException {
        setServiceCode(i);
        setBlockNo(0);
    }

    public int getServiceCode() {
        return this.mServiceCode;
    }

    public void setServiceCode(int i) throws IllegalArgumentException {
        this.mType = ServiceUtil.getBlockType(i);
        this.mServiceCode = i;
    }

    public int getType() {
        return this.mType;
    }

    public int getBlockNo() {
        return this.mBlockNo;
    }

    public void setBlockNo(int i) throws IllegalArgumentException {
        ServiceUtil.checkBlockNo(i);
        this.mBlockNo = i;
    }

    public void checkFormat() throws IllegalArgumentException {
        int blockType = ServiceUtil.getBlockType(this.mServiceCode);
        ServiceUtil.checkBlockNo(this.mBlockNo);
        if (this.mType != blockType) {
            throw new IllegalArgumentException();
        }
    }
}
