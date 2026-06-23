package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class BlockCountInformation implements Parcelable {
    public static final Parcelable.Creator<BlockCountInformation> CREATOR = new Parcelable.Creator<BlockCountInformation>() { // from class: com.felicanetworks.mfc.BlockCountInformation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BlockCountInformation createFromParcel(Parcel parcel) {
            LogMgr.log(4, "%s : in = %s", "000", parcel);
            LogMgr.log(4, "%s", "999");
            return new BlockCountInformation(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BlockCountInformation[] newArray(int i) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(i));
            LogMgr.log(4, "%s", "999");
            return new BlockCountInformation[i];
        }
    };
    protected int assignedBlocks;
    protected int emptyBlocks;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BlockCountInformation(int i, int i2) throws IllegalArgumentException {
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException();
        }
        if (i2 < 0 || i2 > 65535) {
            throw new IllegalArgumentException();
        }
        this.assignedBlocks = i;
        this.emptyBlocks = i2;
    }

    public int getAssignedBlocks() {
        return this.assignedBlocks;
    }

    public void setAssignedBlocks(int i) throws IllegalArgumentException {
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException();
        }
        this.assignedBlocks = i;
    }

    public int getFreeBlocks() {
        return this.emptyBlocks;
    }

    public void setFreeBlocks(int i) throws IllegalArgumentException {
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException();
        }
        this.emptyBlocks = i;
    }

    protected void readFromParcel(Parcel parcel) {
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        this.assignedBlocks = parcel.readInt();
        this.emptyBlocks = parcel.readInt();
        LogMgr.log(7, "001 assignedBlocks=%d emptyBlocks=%d", Integer.valueOf(this.assignedBlocks), Integer.valueOf(this.emptyBlocks));
        LogMgr.log(7, "999");
    }

    private BlockCountInformation(Parcel parcel) {
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        readFromParcel(parcel);
        LogMgr.log(6, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        LogMgr.log(7, "001 assignedBlocks=%d emptyBlocks=%d", Integer.valueOf(this.assignedBlocks), Integer.valueOf(this.emptyBlocks));
        parcel.writeInt(this.assignedBlocks);
        parcel.writeInt(this.emptyBlocks);
        LogMgr.log(7, "999");
    }
}
