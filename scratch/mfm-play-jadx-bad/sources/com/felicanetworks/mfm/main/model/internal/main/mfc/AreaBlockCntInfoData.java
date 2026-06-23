package com.felicanetworks.mfm.main.model.internal.main.mfc;

/* JADX INFO: loaded from: classes3.dex */
public class AreaBlockCntInfoData {
    public int systemNum;
    public int totalFreeBlocks;
    public int totalUsedBlocks;

    AreaBlockCntInfoData(int totalUsedBlocks, int totalFreeBlocks, int systemNum) {
        this.totalUsedBlocks = totalUsedBlocks;
        this.totalFreeBlocks = totalFreeBlocks;
        this.systemNum = systemNum;
    }

    public String toString() {
        return "AreaBlockCntInfoData[" + this.totalUsedBlocks + ", " + this.totalFreeBlocks + ", " + this.systemNum + "]";
    }
}
