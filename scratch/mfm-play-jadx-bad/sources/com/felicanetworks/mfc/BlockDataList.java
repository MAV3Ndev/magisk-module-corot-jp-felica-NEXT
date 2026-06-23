package com.felicanetworks.mfc;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.Vector;

/* JADX INFO: loaded from: classes3.dex */
public class BlockDataList implements Parcelable {
    public static final Parcelable.Creator<BlockDataList> CREATOR = new Parcelable.Creator<BlockDataList>() { // from class: com.felicanetworks.mfc.BlockDataList.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BlockDataList createFromParcel(Parcel in) {
            LogMgr.log(4, "%s : in = %s", "000", in);
            LogMgr.log(4, "%s", "999");
            return new BlockDataList(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BlockDataList[] newArray(int size) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(size));
            LogMgr.log(4, "%s", "999");
            return new BlockDataList[size];
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

    public void add(int index, BlockData blockData) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (index < 0 || index > this.blockDataList.size()) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        if (blockData == null) {
            throw new IllegalArgumentException(EXC_INVALID_BLOCKDATA);
        }
        this.blockDataList.insertElementAt(blockData, index);
    }

    public BlockData get(int index) throws ArrayIndexOutOfBoundsException {
        return this.blockDataList.elementAt(index);
    }

    public BlockData remove(int index) throws ArrayIndexOutOfBoundsException {
        BlockData blockDataElementAt = this.blockDataList.elementAt(index);
        this.blockDataList.removeElementAt(index);
        return blockDataElementAt;
    }

    public void clear() {
        this.blockDataList.removeAllElements();
    }

    public int size() {
        return this.blockDataList.size();
    }

    void set(int index, BlockData blockData) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (index < 0 || index >= this.blockDataList.size()) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        if (blockData == null) {
            throw new IllegalArgumentException(EXC_INVALID_BLOCKDATA);
        }
        this.blockDataList.setElementAt(blockData, index);
    }

    private void readFromParcel(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        if (Build.VERSION.SDK_INT >= 33) {
            in.readList(this.blockDataList, BlockData.class.getClassLoader(), BlockData.class);
        } else {
            in.readList(this.blockDataList, BlockData.class.getClassLoader());
        }
        LogMgr.log(7, "001 blockDataList len=%d", Integer.valueOf(this.blockDataList.size()));
        LogMgr.log(6, "999");
    }

    private BlockDataList(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        this.blockDataList = new Vector<>();
        readFromParcel(in);
        LogMgr.log(6, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", out, Integer.valueOf(flags));
        LogMgr.log(6, "001 blockDataList len=%d", Integer.valueOf(this.blockDataList.size()));
        out.writeList(this.blockDataList);
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
