package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class DataList implements Parcelable {
    public static final Parcelable.Creator<DataList> CREATOR = new Parcelable.Creator<DataList>() { // from class: com.felicanetworks.mfc.DataList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataList createFromParcel(Parcel parcel) {
            LogMgr.log(4, "%s : in = %s", "000", parcel);
            LogMgr.log(4, "%s", "999");
            return new DataList(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataList[] newArray(int i) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(i));
            LogMgr.log(4, "%s", "999");
            return new DataList[i];
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

    public void add(int i, Data data) throws IllegalStateException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (i < 0 || i > this.dataList.size()) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        if (data == null) {
            throw new IllegalArgumentException(EXC_INVALID_DATA);
        }
        this.dataList.insertElementAt(data, i);
    }

    public Data get(int i) throws ArrayIndexOutOfBoundsException {
        return this.dataList.elementAt(i);
    }

    public Data remove(int i) throws ArrayIndexOutOfBoundsException {
        Data dataElementAt = this.dataList.elementAt(i);
        this.dataList.removeElementAt(i);
        return dataElementAt;
    }

    public void clear() {
        this.dataList.removeAllElements();
    }

    public int size() {
        return this.dataList.size();
    }

    void set(int i, Data data) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (i < 0 || i >= this.dataList.size()) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        if (data == null) {
            throw new IllegalArgumentException(EXC_INVALID_DATA);
        }
        this.dataList.setElementAt(data, i);
    }

    public void readFromParcel(Parcel parcel) {
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        parcel.readList(this.dataList, Data.class.getClassLoader());
        LogMgr.log(6, "001 dataList len=%d", Integer.valueOf(this.dataList.size()));
        LogMgr.log(6, "999");
    }

    private DataList(Parcel parcel) {
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        this.dataList = new Vector<>();
        readFromParcel(parcel);
        LogMgr.log(6, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        LogMgr.log(6, "001 dataList len=%d", Integer.valueOf(this.dataList.size()));
        parcel.writeList(this.dataList);
        LogMgr.log(4, "999");
    }
}
