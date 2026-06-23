package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.Enumeration;
import java.util.Hashtable;

/* JADX INFO: loaded from: classes.dex */
public class DeviceList implements Parcelable {
    private static final String EXC_ID_GEN_FAILED = "Failed to generate a new id.";
    private static final String EXC_MAX_SIZE = "The size of this list exceeds the maximum value.";
    public static final int ID_DISPLAY = 2;
    public static final int ID_FELICA = 1;
    private static final int ID_OFFSET = 4096;
    private static final int MAX_ID = 65534;
    public static final int MAX_SIZE = 64;
    private Hashtable<Integer, Device> deviceList;
    private int nextID;
    private static final Device DEVICE_FELICA = new Device("FeliCa", "R/W");
    private static final Device DEVICE_DISPLAY = new Device("Generic", "Display");
    public static final Parcelable.Creator<DeviceList> CREATOR = new Parcelable.Creator<DeviceList>() { // from class: com.felicanetworks.mfc.DeviceList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceList createFromParcel(Parcel parcel) {
            LogMgr.log(4, "%s : in = %s", "000", parcel);
            LogMgr.log(4, "%s", "999");
            return new DeviceList(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceList[] newArray(int i) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(i));
            LogMgr.log(4, "%s", "999");
            return new DeviceList[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DeviceList() {
        this.deviceList = new Hashtable<>();
        reset();
    }

    public synchronized int add(String str, String str2) throws IllegalStateException, IllegalArgumentException {
        Device device = new Device(str, str2);
        Integer id = getID(str, str2);
        if (id == null) {
            if (this.deviceList.size() == 64) {
                throw new IllegalStateException(EXC_MAX_SIZE);
            }
            if (this.nextID > 65534) {
                throw new IllegalStateException(EXC_ID_GEN_FAILED);
            }
            this.deviceList.put(new Integer(this.nextID), device);
            int i = this.nextID;
            this.nextID = i + 1;
            return i;
        }
        return id.intValue();
    }

    public Device get(int i) {
        return this.deviceList.get(new Integer(i));
    }

    public synchronized Device remove(int i) {
        if (i == 1 || i == 2) {
            return null;
        }
        return this.deviceList.remove(new Integer(i));
    }

    public synchronized void clear() {
        reset();
    }

    public int size() {
        return this.deviceList.size();
    }

    synchronized Hashtable<Integer, Device> getDevices() {
        Hashtable<Integer, Device> hashtable;
        hashtable = new Hashtable<>();
        Enumeration<Integer> enumerationKeys = this.deviceList.keys();
        while (enumerationKeys.hasMoreElements()) {
            Integer numNextElement = enumerationKeys.nextElement();
            hashtable.put(numNextElement, this.deviceList.get(numNextElement));
        }
        return hashtable;
    }

    private void reset() {
        this.deviceList.clear();
        this.deviceList.put(new Integer(1), DEVICE_FELICA);
        this.deviceList.put(new Integer(2), DEVICE_DISPLAY);
        this.nextID = 4096;
    }

    private Integer getID(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        Enumeration<Integer> enumerationKeys = this.deviceList.keys();
        while (enumerationKeys.hasMoreElements()) {
            Integer numNextElement = enumerationKeys.nextElement();
            if (this.deviceList.get(numNextElement).getName().equals(str2)) {
                return numNextElement;
            }
        }
        return null;
    }

    private void readFromParcel(Parcel parcel) {
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        this.deviceList = new Hashtable<>();
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            this.deviceList.put(Integer.valueOf(parcel.readInt()), (Device) parcel.readParcelable(Device.class.getClassLoader()));
        }
        this.nextID = parcel.readInt();
        LogMgr.log(6, "001 deviceList len=%d", Integer.valueOf(this.deviceList.size()));
        LogMgr.log(6, "999");
    }

    private DeviceList(Parcel parcel) {
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        readFromParcel(parcel);
        LogMgr.log(6, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        LogMgr.log(6, "001 deviceList len=%d", Integer.valueOf(this.deviceList.size()));
        parcel.writeInt(this.deviceList.size());
        Enumeration<Integer> enumerationKeys = this.deviceList.keys();
        while (enumerationKeys.hasMoreElements()) {
            Integer numNextElement = enumerationKeys.nextElement();
            Device device = this.deviceList.get(numNextElement);
            parcel.writeInt(numNextElement.intValue());
            parcel.writeParcelable(device, i);
        }
        parcel.writeInt(this.nextID);
        LogMgr.log(6, "999");
    }

    public void checkFormat() throws IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        Hashtable<Integer, Device> hashtable = this.deviceList;
        if (hashtable == null || hashtable.size() < 2 || this.deviceList.size() > 64) {
            LogMgr.log(1, "%s DeviceList is invalid data", "800");
            throw new IllegalArgumentException();
        }
        int i = 0;
        if (this.deviceList.containsKey(0) || this.deviceList.containsKey(65535)) {
            LogMgr.log(1, "%s DeviceID is invalid data", "801");
            throw new IllegalArgumentException();
        }
        for (int i2 = 3; i2 < 4096; i2++) {
            if (this.deviceList.containsKey(Integer.valueOf(i2))) {
                LogMgr.log(1, "%s DeviceID is invalid data", "802");
                throw new IllegalArgumentException();
            }
        }
        Device device = get(1);
        if (device == null || !device.getType().equals(DEVICE_FELICA.getType()) || !device.getName().equals(DEVICE_FELICA.getName())) {
            LogMgr.log(1, "%s Device_FeliCa is invalid data", "803");
            throw new IllegalArgumentException();
        }
        Device device2 = get(2);
        if (device2 == null || !device2.getType().equals(DEVICE_DISPLAY.getType()) || !device2.getName().equals(DEVICE_DISPLAY.getName())) {
            LogMgr.log(1, "%s Device_Display is invalid data", "804");
            throw new IllegalArgumentException();
        }
        int[] iArr = new int[this.deviceList.size()];
        Enumeration<Integer> enumerationKeys = this.deviceList.keys();
        int i3 = 0;
        while (enumerationKeys.hasMoreElements()) {
            iArr[i3] = enumerationKeys.nextElement().intValue();
            i3++;
        }
        while (i < iArr.length) {
            Device device3 = this.deviceList.get(Integer.valueOf(iArr[i]));
            device3.checkFormat();
            i++;
            for (int i4 = i; i4 < iArr.length; i4++) {
                if (device3.getName().equals(this.deviceList.get(Integer.valueOf(iArr[i4])).getName())) {
                    LogMgr.log(1, "%s There are plural same devices", "806");
                    throw new IllegalArgumentException();
                }
            }
        }
        LogMgr.log(4, "%s", "999");
    }
}
