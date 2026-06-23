package com.felicanetworks.mfc;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.Enumeration;
import java.util.Hashtable;

/* JADX INFO: loaded from: classes3.dex */
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
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceList createFromParcel(Parcel in) {
            LogMgr.log(4, "%s : in = %s", "000", in);
            LogMgr.log(4, "%s", "999");
            return new DeviceList(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceList[] newArray(int size) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(size));
            LogMgr.log(4, "%s", "999");
            return new DeviceList[size];
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

    public synchronized int add(String type, String name) throws IllegalStateException, IllegalArgumentException {
        Device device = new Device(type, name);
        Integer id = getID(type, name);
        if (id == null) {
            if (this.deviceList.size() == 64) {
                throw new IllegalStateException(EXC_MAX_SIZE);
            }
            int i = this.nextID;
            if (i > MAX_ID) {
                throw new IllegalStateException(EXC_ID_GEN_FAILED);
            }
            this.deviceList.put(Integer.valueOf(i), device);
            int i2 = this.nextID;
            this.nextID = i2 + 1;
            return i2;
        }
        return id.intValue();
    }

    public Device get(int id) {
        return this.deviceList.get(Integer.valueOf(id));
    }

    public synchronized Device remove(int id) {
        if (id == 1 || id == 2) {
            return null;
        }
        return this.deviceList.remove(Integer.valueOf(id));
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
        this.deviceList.put(1, DEVICE_FELICA);
        this.deviceList.put(2, DEVICE_DISPLAY);
        this.nextID = 4096;
    }

    private Integer getID(String type, String name) {
        if (type == null || name == null) {
            return null;
        }
        Enumeration<Integer> enumerationKeys = this.deviceList.keys();
        while (enumerationKeys.hasMoreElements()) {
            Integer numNextElement = enumerationKeys.nextElement();
            if (this.deviceList.get(numNextElement).getName().equals(name)) {
                return numNextElement;
            }
        }
        return null;
    }

    /* JADX DEBUG: Move duplicate insns, count: 1 to block B:8:0x003f */
    private void readFromParcel(Parcel in) {
        Object parcelable;
        LogMgr.log(6, "%s : in = %s", "000", in);
        this.deviceList = new Hashtable<>();
        int i = in.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            Integer numValueOf = Integer.valueOf(in.readInt());
            if (Build.VERSION.SDK_INT >= 33) {
                parcelable = in.readParcelable(Device.class.getClassLoader(), Device.class);
            } else {
                parcelable = in.readParcelable(Device.class.getClassLoader());
            }
            this.deviceList.put(numValueOf, (Device) parcelable);
        }
        this.nextID = in.readInt();
        LogMgr.log(6, "001 deviceList len=%d", Integer.valueOf(this.deviceList.size()));
        LogMgr.log(6, "999");
    }

    private DeviceList(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        readFromParcel(in);
        LogMgr.log(6, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", out, Integer.valueOf(flags));
        LogMgr.log(6, "001 deviceList len=%d", Integer.valueOf(this.deviceList.size()));
        out.writeInt(this.deviceList.size());
        Enumeration<Integer> enumerationKeys = this.deviceList.keys();
        while (enumerationKeys.hasMoreElements()) {
            Integer numNextElement = enumerationKeys.nextElement();
            Device device = this.deviceList.get(numNextElement);
            out.writeInt(numNextElement.intValue());
            out.writeParcelable(device, flags);
        }
        out.writeInt(this.nextID);
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
        if (device != null) {
            String type = device.getType();
            Device device2 = DEVICE_FELICA;
            if (type.equals(device2.getType()) && device.getName().equals(device2.getName())) {
                Device device3 = get(2);
                if (device3 != null) {
                    String type2 = device3.getType();
                    Device device4 = DEVICE_DISPLAY;
                    if (type2.equals(device4.getType()) && device3.getName().equals(device4.getName())) {
                        int size = this.deviceList.size();
                        int[] iArr = new int[size];
                        Enumeration<Integer> enumerationKeys = this.deviceList.keys();
                        int i3 = 0;
                        while (enumerationKeys.hasMoreElements()) {
                            iArr[i3] = enumerationKeys.nextElement().intValue();
                            i3++;
                        }
                        while (i < size) {
                            Device device5 = this.deviceList.get(Integer.valueOf(iArr[i]));
                            device5.checkFormat();
                            i++;
                            for (int i4 = i; i4 < size; i4++) {
                                if (device5.getName().equals(this.deviceList.get(Integer.valueOf(iArr[i4])).getName())) {
                                    LogMgr.log(1, "%s There are plural same devices", "806");
                                    throw new IllegalArgumentException();
                                }
                            }
                        }
                        LogMgr.log(4, "%s", "999");
                        return;
                    }
                }
                LogMgr.log(1, "%s Device_Display is invalid data", "804");
                throw new IllegalArgumentException();
            }
        }
        LogMgr.log(1, "%s Device_FeliCa is invalid data", "803");
        throw new IllegalArgumentException();
    }
}
