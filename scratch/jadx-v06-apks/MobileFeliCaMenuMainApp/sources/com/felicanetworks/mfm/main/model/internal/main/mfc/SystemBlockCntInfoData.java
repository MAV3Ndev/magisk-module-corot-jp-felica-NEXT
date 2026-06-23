package com.felicanetworks.mfm.main.model.internal.main.mfc;

/* JADX INFO: loaded from: classes.dex */
public class SystemBlockCntInfoData implements Comparable<SystemBlockCntInfoData> {
    public int freeBlocks;
    public int systemCode;
    public int systemNum;
    public int usedBlocks;

    SystemBlockCntInfoData(int i, int i2, int i3, int i4) {
        this.usedBlocks = i;
        this.freeBlocks = i2;
        this.systemNum = i3;
        this.systemCode = i4;
    }

    public String toString() {
        return "SystemBlockCntInfoData[" + this.usedBlocks + ", " + this.freeBlocks + ", " + this.systemNum + ", " + this.systemCode + "]";
    }

    @Override // java.lang.Comparable
    public int compareTo(SystemBlockCntInfoData systemBlockCntInfoData) {
        int i = this.systemNum - systemBlockCntInfoData.systemNum;
        if (i > 0) {
            return 1;
        }
        return i < 0 ? -1 : 0;
    }

    public SystemBlockCntInfoData() {
    }
}
