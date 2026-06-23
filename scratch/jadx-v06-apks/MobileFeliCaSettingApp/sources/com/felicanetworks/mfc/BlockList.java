package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class BlockList implements Parcelable {
    public static final Parcelable.Creator<BlockList> CREATOR = new Parcelable.Creator<BlockList>() { // from class: com.felicanetworks.mfc.BlockList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BlockList createFromParcel(Parcel parcel) {
            LogMgr.log(4, "%s : in = %s", "000", parcel);
            LogMgr.log(4, "%s", "999");
            return new BlockList(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BlockList[] newArray(int i) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(i));
            LogMgr.log(4, "%s", "999");
            return new BlockList[i];
        }
    };
    private Vector<Block> blockList;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BlockList() {
        this.blockList = new Vector<>();
    }

    public int add(Block block) throws IllegalArgumentException {
        return add(block, true);
    }

    int add(Block block, boolean z) throws IllegalArgumentException {
        ServiceUtil.getInstance().checkBlock(block, z);
        this.blockList.addElement(block);
        return this.blockList.size() - 1;
    }

    public void add(int i, Block block) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        add(i, block, true);
    }

    void add(int i, Block block, boolean z) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (i < 0 || i > this.blockList.size()) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        ServiceUtil.getInstance().checkBlock(block, z);
        this.blockList.insertElementAt(block, i);
    }

    public Block get(int i) throws ArrayIndexOutOfBoundsException {
        return this.blockList.elementAt(i);
    }

    public Block remove(int i) throws ArrayIndexOutOfBoundsException {
        Block blockElementAt = this.blockList.elementAt(i);
        this.blockList.removeElementAt(i);
        return blockElementAt;
    }

    public void clear() {
        this.blockList.removeAllElements();
    }

    public int size() {
        return this.blockList.size();
    }

    private void readFromParcel(Parcel parcel) {
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        parcel.readList(this.blockList, Block.class.getClassLoader());
        LogMgr.log(6, "001 blockList len=%d", Integer.valueOf(this.blockList.size()));
        LogMgr.log(6, "999");
    }

    private BlockList(Parcel parcel) {
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        this.blockList = new Vector<>();
        readFromParcel(parcel);
        LogMgr.log(6, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        LogMgr.log(6, "001 blockList len=%d", Integer.valueOf(this.blockList.size()));
        parcel.writeList(this.blockList);
        LogMgr.log(4, "999");
    }

    public void checkFormat() throws IllegalArgumentException {
        LogMgr.log(4, "000");
        for (int i = 0; i < this.blockList.size(); i++) {
            Block blockElementAt = this.blockList.elementAt(i);
            if (blockElementAt == null) {
                LogMgr.log(1, "801 blockList[%d] is null", Integer.valueOf(i));
                throw new IllegalArgumentException();
            }
            blockElementAt.checkFormat();
            ServiceUtil.getInstance().checkBlock(blockElementAt, true);
        }
        LogMgr.log(4, "999");
    }
}
