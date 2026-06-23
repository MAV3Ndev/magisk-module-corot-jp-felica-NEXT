package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class BlockCountInformation implements Parcelable {
    public static final Parcelable.Creator<BlockCountInformation> CREATOR = new Parcelable.Creator<BlockCountInformation>() { // from class: com.felicanetworks.mfc.BlockCountInformation.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BlockCountInformation createFromParcel(Parcel in) {
            LogMgr.log(4, "%s : in = %s", "000", in);
            LogMgr.log(4, "%s", "999");
            return new BlockCountInformation(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BlockCountInformation[] newArray(int size) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(size));
            LogMgr.log(4, "%s", "999");
            return new BlockCountInformation[size];
        }
    };
    protected int assignedBlocks;
    protected int emptyBlocks;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BlockCountInformation(int assignedBlocks, int emptyBlocks) throws IllegalArgumentException {
        if (assignedBlocks < 0 || assignedBlocks > 65535) {
            throw new IllegalArgumentException();
        }
        if (emptyBlocks < 0 || emptyBlocks > 65535) {
            throw new IllegalArgumentException();
        }
        this.assignedBlocks = assignedBlocks;
        this.emptyBlocks = emptyBlocks;
    }

    public int getAssignedBlocks() {
        return this.assignedBlocks;
    }

    public void setAssignedBlocks(int assignedBlocks) throws IllegalArgumentException {
        if (assignedBlocks < 0 || assignedBlocks > 65535) {
            throw new IllegalArgumentException();
        }
        this.assignedBlocks = assignedBlocks;
    }

    public int getFreeBlocks() {
        return this.emptyBlocks;
    }

    public void setFreeBlocks(int emptyBlocks) throws IllegalArgumentException {
        if (emptyBlocks < 0 || emptyBlocks > 65535) {
            throw new IllegalArgumentException();
        }
        this.emptyBlocks = emptyBlocks;
    }

    protected void readFromParcel(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        this.assignedBlocks = in.readInt();
        this.emptyBlocks = in.readInt();
        LogMgr.log(7, "001 assignedBlocks=%d emptyBlocks=%d", Integer.valueOf(this.assignedBlocks), Integer.valueOf(this.emptyBlocks));
        LogMgr.log(7, "999");
    }

    private BlockCountInformation(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        readFromParcel(in);
        LogMgr.log(6, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", out, Integer.valueOf(flag));
        LogMgr.log(7, "001 assignedBlocks=%d emptyBlocks=%d", Integer.valueOf(this.assignedBlocks), Integer.valueOf(this.emptyBlocks));
        out.writeInt(this.assignedBlocks);
        out.writeInt(this.emptyBlocks);
        LogMgr.log(7, "999");
    }
}
