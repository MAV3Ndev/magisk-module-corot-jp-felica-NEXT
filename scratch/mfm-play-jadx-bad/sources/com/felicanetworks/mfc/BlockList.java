package com.felicanetworks.mfc;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.Vector;

/* JADX INFO: loaded from: classes3.dex */
public class BlockList implements Parcelable {
    public static final Parcelable.Creator<BlockList> CREATOR = new Parcelable.Creator<BlockList>() { // from class: com.felicanetworks.mfc.BlockList.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BlockList createFromParcel(Parcel in) {
            LogMgr.log(4, "%s : in = %s", "000", in);
            LogMgr.log(4, "%s", "999");
            return new BlockList(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BlockList[] newArray(int size) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(size));
            LogMgr.log(4, "%s", "999");
            return new BlockList[size];
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

    int add(Block block, boolean checkType) throws IllegalArgumentException {
        ServiceUtil.getInstance().checkBlock(block, checkType);
        this.blockList.addElement(block);
        return this.blockList.size() - 1;
    }

    public void add(int index, Block block) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        add(index, block, true);
    }

    void add(int index, Block block, boolean checkType) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (index < 0 || index > this.blockList.size()) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        ServiceUtil.getInstance().checkBlock(block, checkType);
        this.blockList.insertElementAt(block, index);
    }

    public Block get(int index) throws ArrayIndexOutOfBoundsException {
        return this.blockList.elementAt(index);
    }

    public Block remove(int index) throws ArrayIndexOutOfBoundsException {
        Block blockElementAt = this.blockList.elementAt(index);
        this.blockList.removeElementAt(index);
        return blockElementAt;
    }

    public void clear() {
        this.blockList.removeAllElements();
    }

    public int size() {
        return this.blockList.size();
    }

    private void readFromParcel(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        if (Build.VERSION.SDK_INT >= 33) {
            in.readList(this.blockList, Block.class.getClassLoader(), Block.class);
        } else {
            in.readList(this.blockList, Block.class.getClassLoader());
        }
        LogMgr.log(6, "001 blockList len=%d", Integer.valueOf(this.blockList.size()));
        LogMgr.log(6, "999");
    }

    private BlockList(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        this.blockList = new Vector<>();
        readFromParcel(in);
        LogMgr.log(6, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", out, Integer.valueOf(flags));
        LogMgr.log(6, "001 blockList len=%d", Integer.valueOf(this.blockList.size()));
        out.writeList(this.blockList);
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
