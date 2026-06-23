package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class BlockData implements Parcelable {
    public static final Parcelable.Creator<BlockData> CREATOR = new Parcelable.Creator<BlockData>() { // from class: com.felicanetworks.mfc.BlockData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BlockData createFromParcel(Parcel parcel) {
            LogMgr.log(4, "%s : in = %s", "000", parcel);
            LogMgr.log(4, "%s", "999");
            return new BlockData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BlockData[] newArray(int i) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(i));
            LogMgr.log(4, "%s", "999");
            return new BlockData[i];
        }
    };
    private Block block;
    private Data data;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BlockData(Block block, Data data) throws IllegalArgumentException {
        set(block, data);
    }

    public Block getBlock() {
        return this.block;
    }

    public void setBlock(Block block) throws IllegalArgumentException {
        ServiceUtil.getInstance().checkBlockData(block, this.data);
        this.block = block;
    }

    public Data getData() {
        return this.data;
    }

    public void setData(Data data) throws IllegalArgumentException {
        ServiceUtil.getInstance().checkBlockData(this.block, data);
        this.data = data;
    }

    public void set(Block block, Data data) throws IllegalArgumentException {
        ServiceUtil.getInstance().checkBlockData(block, data);
        this.block = block;
        this.data = data;
    }

    void setWithoutValidation(Block block, Data data) {
        this.block = block;
        this.data = data;
    }

    private void readFromParcel(Parcel parcel) {
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        this.block = (Block) parcel.readParcelable(Block.class.getClassLoader());
        this.data = (Data) parcel.readParcelable(Data.class.getClassLoader());
        LogMgr.log(6, "999");
    }

    private BlockData(Parcel parcel) {
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        readFromParcel(parcel);
        LogMgr.log(6, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        parcel.writeParcelable(this.block, i);
        parcel.writeParcelable(this.data, i);
        LogMgr.log(4, "999");
    }

    public void checkFormat() throws IllegalArgumentException {
        LogMgr.log(4, "000");
        Block block = this.block;
        if (block == null || this.data == null) {
            LogMgr.log(1, "800");
            throw new IllegalArgumentException();
        }
        block.checkFormat();
        this.data.checkFormat();
        ServiceUtil.getInstance().checkBlockData(this.block, this.data);
        LogMgr.log(4, "999");
    }
}
