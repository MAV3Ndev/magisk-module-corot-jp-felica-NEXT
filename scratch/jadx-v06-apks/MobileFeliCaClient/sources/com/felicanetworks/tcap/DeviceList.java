package com.felicanetworks.tcap;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.Enumeration;
import java.util.Hashtable;

/* JADX INFO: loaded from: classes.dex */
public class DeviceList implements Parcelable {
    public static final Parcelable.Creator<DeviceList> CREATOR = new Parcelable.Creator<DeviceList>() { // from class: com.felicanetworks.tcap.DeviceList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceList createFromParcel(Parcel parcel) {
            return new DeviceList(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceList[] newArray(int i) {
            return new DeviceList[i];
        }
    };
    private static final String EXC_ID_GEN_FAILED = "Failed to generate a new id.";
    private static final String EXC_MAX_SIZE = "The size of this list exceeds the maximum value.";
    public static final int ID_DISPLAY = 2;
    public static final int ID_FELICA = 1;
    private static final int ID_OFFSET = 1;
    private static final int MAX_ID = 65534;
    public static final int MAX_SIZE = 64;
    private Hashtable<Integer, Device> mDeviceList;
    private int mNextId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DeviceList() {
        this.mDeviceList = new Hashtable<>();
        reset();
    }

    public synchronized int add(String str, String str2) throws IllegalStateException, IllegalArgumentException {
        Device device = new Device(str, str2);
        Integer id = getId(str, str2);
        if (id == null) {
            if (this.mDeviceList.size() == 64) {
                throw new IllegalStateException(EXC_MAX_SIZE);
            }
            if (this.mNextId > 65534) {
                throw new IllegalStateException(EXC_ID_GEN_FAILED);
            }
            this.mDeviceList.put(new Integer(this.mNextId), device);
            int i = this.mNextId;
            this.mNextId = i + 1;
            return i;
        }
        return id.intValue();
    }

    public synchronized int add(String str, String str2, int i) throws IllegalStateException, IllegalArgumentException {
        Device device = new Device(str, str2);
        Integer id = getId(str, str2);
        if (id == null) {
            if (this.mDeviceList.size() == 64) {
                throw new IllegalStateException(EXC_MAX_SIZE);
            }
            if (i > 65534 || get(i) != null) {
                throw new IllegalArgumentException(EXC_ID_GEN_FAILED);
            }
            this.mDeviceList.put(new Integer(i), device);
            if (this.mNextId >= i) {
                return i;
            }
            this.mNextId = i;
            int i2 = this.mNextId;
            this.mNextId = i2 + 1;
            return i2;
        }
        return id.intValue();
    }

    public Device get(int i) {
        return this.mDeviceList.get(new Integer(i));
    }

    public synchronized Device remove(int i) {
        return this.mDeviceList.remove(new Integer(i));
    }

    public synchronized void clear() {
        reset();
    }

    public int size() {
        return this.mDeviceList.size();
    }

    public synchronized Hashtable<Integer, Device> getDevices() {
        Hashtable<Integer, Device> hashtable;
        hashtable = new Hashtable<>();
        Enumeration<Integer> enumerationKeys = this.mDeviceList.keys();
        while (enumerationKeys.hasMoreElements()) {
            Integer numNextElement = enumerationKeys.nextElement();
            hashtable.put(numNextElement, this.mDeviceList.get(numNextElement));
        }
        return hashtable;
    }

    private void reset() {
        this.mDeviceList.clear();
        this.mNextId = 1;
    }

    private Integer getId(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        Enumeration<Integer> enumerationKeys = this.mDeviceList.keys();
        while (enumerationKeys.hasMoreElements()) {
            Integer numNextElement = enumerationKeys.nextElement();
            if (this.mDeviceList.get(numNextElement).getName().equals(str2)) {
                return numNextElement;
            }
        }
        return null;
    }

    private void readFromParcel(Parcel parcel) {
        this.mDeviceList = new Hashtable<>();
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            this.mDeviceList.put(Integer.valueOf(parcel.readInt()), (Device) parcel.readParcelable(Device.class.getClassLoader()));
        }
        this.mNextId = parcel.readInt();
    }

    private DeviceList(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mDeviceList.size());
        Enumeration<Integer> enumerationKeys = this.mDeviceList.keys();
        while (enumerationKeys.hasMoreElements()) {
            Integer numNextElement = enumerationKeys.nextElement();
            Device device = this.mDeviceList.get(numNextElement);
            parcel.writeInt(numNextElement.intValue());
            parcel.writeParcelable(device, i);
        }
        parcel.writeInt(this.mNextId);
    }

    public void checkFormat() throws IllegalArgumentException {
        Hashtable<Integer, Device> hashtable = this.mDeviceList;
        if (hashtable == null || hashtable.size() < 1 || this.mDeviceList.size() > 64) {
            LogMgr.log(1, "%s DeviceList is invalid data", "800");
            throw new IllegalArgumentException();
        }
        int i = 0;
        if (this.mDeviceList.containsKey(0) || this.mDeviceList.containsKey(65535)) {
            LogMgr.log(1, "%s DeviceID is invalid data", "801");
            throw new IllegalArgumentException();
        }
        int[] iArr = new int[this.mDeviceList.size()];
        Enumeration<Integer> enumerationKeys = this.mDeviceList.keys();
        int i2 = 0;
        while (enumerationKeys.hasMoreElements()) {
            iArr[i2] = enumerationKeys.nextElement().intValue();
            i2++;
        }
        while (i < iArr.length) {
            Device device = this.mDeviceList.get(Integer.valueOf(iArr[i]));
            device.checkFormat();
            i++;
            for (int i3 = i; i3 < iArr.length; i3++) {
                if (device.getName().equals(this.mDeviceList.get(Integer.valueOf(iArr[i3])).getName())) {
                    LogMgr.log(1, "%s There are plural same devices", "802");
                    throw new IllegalArgumentException();
                }
            }
        }
    }
}
