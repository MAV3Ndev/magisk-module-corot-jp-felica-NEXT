package com.felicanetworks.mnlib.felica;

import android.util.Log;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class BlockList {
    private static final String TAG = "NFC";
    private Vector<Block> mBlockList = new Vector<>();

    public int add(Block block) throws IllegalArgumentException {
        return add(block, true);
    }

    int add(Block block, boolean z) throws IllegalArgumentException {
        ServiceUtil.checkBlock(block, z);
        this.mBlockList.addElement(block);
        return this.mBlockList.size() - 1;
    }

    public void add(int i, Block block) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        add(i, block, true);
    }

    void add(int i, Block block, boolean z) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (i < 0 || i > this.mBlockList.size()) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        ServiceUtil.checkBlock(block, z);
        this.mBlockList.insertElementAt(block, i);
    }

    public Block get(int i) throws ArrayIndexOutOfBoundsException {
        return this.mBlockList.elementAt(i);
    }

    public Block remove(int i) throws ArrayIndexOutOfBoundsException {
        Block blockElementAt = this.mBlockList.elementAt(i);
        this.mBlockList.removeElementAt(i);
        return blockElementAt;
    }

    public void clear() {
        this.mBlockList.removeAllElements();
    }

    public int size() {
        return this.mBlockList.size();
    }

    public void checkFormat() throws IllegalArgumentException {
        for (int i = 0; i < this.mBlockList.size(); i++) {
            Block blockElementAt = this.mBlockList.elementAt(i);
            if (blockElementAt == null) {
                Log.w(TAG, "checkFormat  : blockList[" + i + "] is null");
                throw new IllegalArgumentException();
            }
            blockElementAt.checkFormat();
            ServiceUtil.checkBlock(blockElementAt, true);
        }
    }
}
