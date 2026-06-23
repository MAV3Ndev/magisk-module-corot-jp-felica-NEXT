package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class Block implements Parcelable {
    public static final Parcelable.Creator<Block> CREATOR = new Parcelable.Creator<Block>() { // from class: com.felicanetworks.mfc.Block.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Block createFromParcel(Parcel in) {
            LogMgr.log(4, "%s : in = %s", "000", in);
            LogMgr.log(4, "%s", "999");
            return new Block(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Block[] newArray(int size) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(size));
            LogMgr.log(4, "%s", "999");
            return new Block[size];
        }
    };
    public static final int TYPE_CYCLIC = 3;
    public static final int TYPE_CYCLIC_READ_ONLY = 4;
    public static final int TYPE_PIN_FOR_AREA = 10;
    public static final int TYPE_PIN_FOR_AREA_APPENDABLE = 9;
    public static final int TYPE_PIN_FOR_CYCLIC = 16;
    public static final int TYPE_PIN_FOR_CYCLIC_ENCRYPTED = 15;
    public static final int TYPE_PIN_FOR_CYCLIC_READ_ONLY = 18;
    public static final int TYPE_PIN_FOR_CYCLIC_READ_ONLY_ENCRYPTED = 17;
    public static final int TYPE_PIN_FOR_PURSE = 20;
    public static final int TYPE_PIN_FOR_PURSE_CASH_BACK = 22;
    public static final int TYPE_PIN_FOR_PURSE_CASH_BACK_ENCRYPTED = 21;
    public static final int TYPE_PIN_FOR_PURSE_DECREMENT = 24;
    public static final int TYPE_PIN_FOR_PURSE_DECREMENT_ENCRYPTED = 23;
    public static final int TYPE_PIN_FOR_PURSE_ENCRYPTED = 19;
    public static final int TYPE_PIN_FOR_PURSE_READ_ONLY = 26;
    public static final int TYPE_PIN_FOR_PURSE_READ_ONLY_ENCRYPTED = 25;
    public static final int TYPE_PIN_FOR_RANDOM = 12;
    public static final int TYPE_PIN_FOR_RANDOM_ENCRYPTED = 11;
    public static final int TYPE_PIN_FOR_RANDOM_READ_ONLY = 14;
    public static final int TYPE_PIN_FOR_RANDOM_READ_ONLY_ENCRYPTED = 13;
    public static final int TYPE_PURSE = 5;
    public static final int TYPE_PURSE_CASH_BACK = 6;
    public static final int TYPE_PURSE_DECREMENT = 7;
    public static final int TYPE_PURSE_READ_ONLY = 8;
    public static final int TYPE_RANDOM = 1;
    public static final int TYPE_RANDOM_READ_ONLY = 2;
    private int blockNo;
    private int serviceCode;
    private int type;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Block(int serviceCode, int blockNo) throws IllegalArgumentException {
        setServiceCode(serviceCode);
        setBlockNo(blockNo);
    }

    public Block(int serviceCode) throws IllegalArgumentException {
        setServiceCode(serviceCode);
        setBlockNo(0);
    }

    public int getServiceCode() {
        return this.serviceCode;
    }

    public void setServiceCode(int serviceCode) throws IllegalArgumentException {
        this.type = ServiceUtil.getInstance().getBlockType(serviceCode);
        this.serviceCode = serviceCode;
    }

    public int getType() {
        return this.type;
    }

    public int getBlockNo() {
        return this.blockNo;
    }

    public void setBlockNo(int blockNo) throws IllegalArgumentException {
        ServiceUtil.getInstance().checkBlockNo(blockNo);
        this.blockNo = blockNo;
    }

    private void readFromParcel(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        this.blockNo = in.readInt();
        this.serviceCode = in.readInt();
        this.type = in.readInt();
        LogMgr.log(6, "001 serviceCode=%d type=%d blockNo=%d", Integer.valueOf(this.serviceCode), Integer.valueOf(this.type), Integer.valueOf(this.blockNo));
        LogMgr.log(6, "999");
    }

    private Block(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        readFromParcel(in);
        LogMgr.log(6, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", out, Integer.valueOf(flag));
        LogMgr.log(6, "001 serviceCode=%d type=%d blockNo=%d", Integer.valueOf(this.serviceCode), Integer.valueOf(this.type), Integer.valueOf(this.blockNo));
        out.writeInt(this.blockNo);
        out.writeInt(this.serviceCode);
        out.writeInt(this.type);
        LogMgr.log(4, "999");
    }

    public void checkFormat() throws IllegalArgumentException {
        LogMgr.log(4, "000");
        int blockType = ServiceUtil.getInstance().getBlockType(this.serviceCode);
        ServiceUtil.getInstance().checkBlockNo(this.blockNo);
        if (this.type != blockType) {
            LogMgr.log(7, "800 type is invalid " + this.type + " expected " + blockType);
            throw new IllegalArgumentException();
        }
        LogMgr.log(4, "999");
    }
}
