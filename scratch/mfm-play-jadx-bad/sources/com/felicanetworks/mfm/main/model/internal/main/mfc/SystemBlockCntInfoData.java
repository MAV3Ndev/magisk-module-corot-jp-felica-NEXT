package com.felicanetworks.mfm.main.model.internal.main.mfc;

/* JADX INFO: loaded from: classes3.dex */
public class SystemBlockCntInfoData implements Comparable<SystemBlockCntInfoData> {
    public int freeBlocks;
    public int systemCode;
    public int systemNum;
    public int usedBlocks;

    SystemBlockCntInfoData(int usedBlocks, int freeBlocks, int systemNum, int systemCode) {
        this.usedBlocks = usedBlocks;
        this.freeBlocks = freeBlocks;
        this.systemNum = systemNum;
        this.systemCode = systemCode;
    }

    public String toString() {
        return "SystemBlockCntInfoData[" + this.usedBlocks + ", " + this.freeBlocks + ", " + this.systemNum + ", " + this.systemCode + "]";
    }

    /* JADX DEBUG: Method merged with bridge method: compareTo(Ljava/lang/Object;)I */
    @Override // java.lang.Comparable
    public int compareTo(SystemBlockCntInfoData another) {
        int i = this.systemNum - another.systemNum;
        if (i > 0) {
            return 1;
        }
        return i < 0 ? -1 : 0;
    }

    public SystemBlockCntInfoData() {
    }
}
