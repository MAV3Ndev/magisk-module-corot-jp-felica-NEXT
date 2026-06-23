package com.felicanetworks.mnlib.felica;

import android.util.Log;

/* JADX INFO: loaded from: classes3.dex */
public class BlockData {
    private static final String TAG = "NFC";
    private Block mBlock;
    private Data mData;

    public BlockData(Block block, Data data) throws IllegalArgumentException {
        set(block, data);
    }

    public Block getBlock() {
        return this.mBlock;
    }

    public void setBlock(Block block) throws IllegalArgumentException {
        ServiceUtil.checkBlockData(block, this.mData);
        this.mBlock = block;
    }

    public Data getData() {
        return this.mData;
    }

    public void setData(Data data) throws IllegalArgumentException {
        ServiceUtil.checkBlockData(this.mBlock, data);
        this.mData = data;
    }

    public void set(Block block, Data data) throws IllegalArgumentException {
        ServiceUtil.checkBlockData(block, data);
        this.mBlock = block;
        this.mData = data;
    }

    void setWithoutValidation(Block block, Data data) {
        this.mBlock = block;
        this.mData = data;
    }

    public void checkFormat() throws IllegalArgumentException {
        Block block = this.mBlock;
        if (block == null || this.mData == null) {
            Log.w(TAG, "checkFormat  : block or data is null");
            throw new IllegalArgumentException();
        }
        block.checkFormat();
        this.mData.checkFormat();
        ServiceUtil.checkBlockData(this.mBlock, this.mData);
    }
}
