package com.felicanetworks.mfc;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.Vector;

/* JADX INFO: loaded from: classes3.dex */
public class DataList implements Parcelable {
    public static final Parcelable.Creator<DataList> CREATOR = new Parcelable.Creator<DataList>() { // from class: com.felicanetworks.mfc.DataList.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataList createFromParcel(Parcel in) {
            LogMgr.log(4, "%s : in = %s", "000", in);
            LogMgr.log(4, "%s", "999");
            return new DataList(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataList[] newArray(int size) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(size));
            LogMgr.log(4, "%s", "999");
            return new DataList[size];
        }
    };
    private static final String EXC_INVALID_DATA = "The specified Data is null.";
    public static final int MAX_SIZE = 14;
    private Vector<Data> dataList;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DataList() {
        this.dataList = new Vector<>();
    }

    public int add(Data data) throws IllegalStateException, IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException(EXC_INVALID_DATA);
        }
        this.dataList.addElement(data);
        return this.dataList.size() - 1;
    }

    public void add(int index, Data data) throws IllegalStateException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (index < 0 || index > this.dataList.size()) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        if (data == null) {
            throw new IllegalArgumentException(EXC_INVALID_DATA);
        }
        this.dataList.insertElementAt(data, index);
    }

    public Data get(int index) throws ArrayIndexOutOfBoundsException {
        return this.dataList.elementAt(index);
    }

    public Data remove(int index) throws ArrayIndexOutOfBoundsException {
        Data dataElementAt = this.dataList.elementAt(index);
        this.dataList.removeElementAt(index);
        return dataElementAt;
    }

    public void clear() {
        this.dataList.removeAllElements();
    }

    public int size() {
        return this.dataList.size();
    }

    void set(int index, Data data) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (index < 0 || index >= this.dataList.size()) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        if (data == null) {
            throw new IllegalArgumentException(EXC_INVALID_DATA);
        }
        this.dataList.setElementAt(data, index);
    }

    public void readFromParcel(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        if (Build.VERSION.SDK_INT >= 33) {
            in.readList(this.dataList, Data.class.getClassLoader(), Data.class);
        } else {
            in.readList(this.dataList, Data.class.getClassLoader());
        }
        LogMgr.log(6, "001 dataList len=%d", Integer.valueOf(this.dataList.size()));
        LogMgr.log(6, "999");
    }

    private DataList(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        this.dataList = new Vector<>();
        readFromParcel(in);
        LogMgr.log(6, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", out, Integer.valueOf(flags));
        LogMgr.log(6, "001 dataList len=%d", Integer.valueOf(this.dataList.size()));
        out.writeList(this.dataList);
        LogMgr.log(4, "999");
    }
}
