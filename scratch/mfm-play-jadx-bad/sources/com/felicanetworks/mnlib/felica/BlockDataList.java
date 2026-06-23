package com.felicanetworks.mnlib.felica;

import android.util.Log;
import java.util.Vector;

/* JADX INFO: loaded from: classes3.dex */
public class BlockDataList {
    private static final String EXC_INVALID_BLOCK_DATA = "The specified BlockData is null.";
    private static final String TAG = "NFC";
    private Vector<BlockData> mBlockDataList = new Vector<>();

    public int add(BlockData blockData) throws IllegalArgumentException {
        if (blockData == null) {
            throw new IllegalArgumentException(EXC_INVALID_BLOCK_DATA);
        }
        this.mBlockDataList.addElement(blockData);
        return this.mBlockDataList.size() - 1;
    }

    public void add(int i, BlockData blockData) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (i < 0 || i > this.mBlockDataList.size()) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        if (blockData == null) {
            throw new IllegalArgumentException(EXC_INVALID_BLOCK_DATA);
        }
        this.mBlockDataList.insertElementAt(blockData, i);
    }

    public BlockData get(int i) throws ArrayIndexOutOfBoundsException {
        return this.mBlockDataList.elementAt(i);
    }

    public BlockData remove(int i) throws ArrayIndexOutOfBoundsException {
        BlockData blockDataElementAt = this.mBlockDataList.elementAt(i);
        this.mBlockDataList.removeElementAt(i);
        return blockDataElementAt;
    }

    public void clear() {
        this.mBlockDataList.removeAllElements();
    }

    public int size() {
        return this.mBlockDataList.size();
    }

    void set(int i, BlockData blockData) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (i < 0 || i >= this.mBlockDataList.size()) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        if (blockData == null) {
            throw new IllegalArgumentException(EXC_INVALID_BLOCK_DATA);
        }
        this.mBlockDataList.setElementAt(blockData, i);
    }

    public void checkFormat() throws IllegalArgumentException {
        for (int i = 0; i < this.mBlockDataList.size(); i++) {
            BlockData blockDataElementAt = this.mBlockDataList.elementAt(i);
            if (blockDataElementAt == null) {
                Log.w(TAG, "checkFormat  : blockDataList[" + i + "] is null");
                throw new IllegalArgumentException();
            }
            blockDataElementAt.checkFormat();
        }
    }
}
