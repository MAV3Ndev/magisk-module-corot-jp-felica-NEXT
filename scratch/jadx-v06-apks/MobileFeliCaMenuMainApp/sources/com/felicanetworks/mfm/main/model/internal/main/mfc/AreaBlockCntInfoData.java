package com.felicanetworks.mfm.main.model.internal.main.mfc;

/* JADX INFO: loaded from: classes.dex */
public class AreaBlockCntInfoData {
    public int systemNum;
    public int totalFreeBlocks;
    public int totalUsedBlocks;

    AreaBlockCntInfoData(int i, int i2, int i3) {
        this.totalUsedBlocks = i;
        this.totalFreeBlocks = i2;
        this.systemNum = i3;
    }

    public String toString() {
        return "AreaBlockCntInfoData[" + this.totalUsedBlocks + ", " + this.totalFreeBlocks + ", " + this.systemNum + "]";
    }
}
