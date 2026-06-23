package com.felicanetworks.tcap;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.tcap.IFelicaDevice;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class FelicaDeviceList implements Parcelable {
    public static final Parcelable.Creator<FelicaDeviceList> CREATOR = new Parcelable.Creator<FelicaDeviceList>() { // from class: com.felicanetworks.tcap.FelicaDeviceList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaDeviceList createFromParcel(Parcel parcel) {
            return new FelicaDeviceList(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaDeviceList[] newArray(int i) {
            return new FelicaDeviceList[i];
        }
    };
    private static final String EXC_INVALID_DATA = "The specified FelicaDevice is null.";
    private Vector<IBinder> mFelicaDeviceList;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FelicaDeviceList() {
        this.mFelicaDeviceList = new Vector<>();
    }

    public int add(IFelicaDevice iFelicaDevice) throws IllegalArgumentException {
        if (iFelicaDevice == null) {
            throw new IllegalArgumentException(EXC_INVALID_DATA);
        }
        this.mFelicaDeviceList.addElement(iFelicaDevice.asBinder());
        return this.mFelicaDeviceList.size() - 1;
    }

    public void add(int i, IFelicaDevice iFelicaDevice) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (i < 0 || i > this.mFelicaDeviceList.size()) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        if (iFelicaDevice == null) {
            throw new IllegalArgumentException(EXC_INVALID_DATA);
        }
        this.mFelicaDeviceList.insertElementAt(iFelicaDevice.asBinder(), i);
    }

    public IFelicaDevice get(int i) throws ArrayIndexOutOfBoundsException {
        return IFelicaDevice.Stub.asInterface(this.mFelicaDeviceList.elementAt(i));
    }

    public IFelicaDevice remove(int i) throws ArrayIndexOutOfBoundsException {
        IFelicaDevice iFelicaDeviceAsInterface = IFelicaDevice.Stub.asInterface(this.mFelicaDeviceList.elementAt(i));
        this.mFelicaDeviceList.removeElementAt(i);
        return iFelicaDeviceAsInterface;
    }

    public void clear() {
        this.mFelicaDeviceList.removeAllElements();
    }

    public int size() {
        return this.mFelicaDeviceList.size();
    }

    private void readFromParcel(Parcel parcel) {
        parcel.readBinderList(this.mFelicaDeviceList);
    }

    private FelicaDeviceList(Parcel parcel) {
        this.mFelicaDeviceList = new Vector<>();
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBinderList(this.mFelicaDeviceList);
    }
}
