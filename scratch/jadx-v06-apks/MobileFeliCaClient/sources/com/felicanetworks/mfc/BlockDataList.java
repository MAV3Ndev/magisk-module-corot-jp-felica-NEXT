package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class BlockDataList implements Parcelable {
    public static final Parcelable.Creator<BlockDataList> CREATOR = new Parcelable.Creator<BlockDataList>() { // from class: com.felicanetworks.mfc.BlockDataList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BlockDataList createFromParcel(Parcel parcel) {
            LogMgr.log(4, "%s : in = %s", "000", parcel);
            LogMgr.log(4, "%s", "999");
            return new BlockDataList(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BlockDataList[] newArray(int i) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(i));
            LogMgr.log(4, "%s", "999");
            return new BlockDataList[i];
        }
    };
    private static final String EXC_INVALID_BLOCKDATA = "The specified BlockData is null.";
    private Vector<BlockData> blockDataList;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BlockDataList() {
        this.blockDataList = new Vector<>();
    }

    public int add(BlockData blockData) throws IllegalArgumentException {
        if (blockData == null) {
            throw new IllegalArgumentException(EXC_INVALID_BLOCKDATA);
        }
        this.blockDataList.addElement(blockData);
        return this.blockDataList.size() - 1;
    }

    public void add(int i, BlockData blockData) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (i < 0 || i > this.blockDataList.size()) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        if (blockData == null) {
            throw new IllegalArgumentException(EXC_INVALID_BLOCKDATA);
        }
        this.blockDataList.insertElementAt(blockData, i);
    }

    public BlockData get(int i) throws ArrayIndexOutOfBoundsException {
        return this.blockDataList.elementAt(i);
    }

    public BlockData remove(int i) throws ArrayIndexOutOfBoundsException {
        BlockData blockDataElementAt = this.blockDataList.elementAt(i);
        this.blockDataList.removeElementAt(i);
        return blockDataElementAt;
    }

    public void clear() {
        this.blockDataList.removeAllElements();
    }

    public int size() {
        return this.blockDataList.size();
    }

    void set(int i, BlockData blockData) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (i < 0 || i >= this.blockDataList.size()) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        if (blockData == null) {
            throw new IllegalArgumentException(EXC_INVALID_BLOCKDATA);
        }
        this.blockDataList.setElementAt(blockData, i);
    }

    private void readFromParcel(Parcel parcel) {
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        parcel.readList(this.blockDataList, BlockData.class.getClassLoader());
        LogMgr.log(7, "001 blockDataList len=%d", Integer.valueOf(this.blockDataList.size()));
        LogMgr.log(6, "999");
    }

    private BlockDataList(Parcel parcel) {
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        this.blockDataList = new Vector<>();
        readFromParcel(parcel);
        LogMgr.log(6, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        LogMgr.log(6, "001 blockDataList len=%d", Integer.valueOf(this.blockDataList.size()));
        parcel.writeList(this.blockDataList);
        LogMgr.log(4, "999");
    }

    public void checkFormat() throws IllegalArgumentException {
        LogMgr.log(4, "000");
        for (int i = 0; i < this.blockDataList.size(); i++) {
            BlockData blockDataElementAt = this.blockDataList.elementAt(i);
            if (blockDataElementAt == null) {
                LogMgr.log(1, "801");
                throw new IllegalArgumentException();
            }
            blockDataElementAt.checkFormat();
        }
        LogMgr.log(4, "999");
    }
}
