package com.felicanetworks.mfc.tcap.impl;

import com.felicanetworks.mfc.tcap.InternalDevice;
import com.felicanetworks.tcap.IFelicaDevice;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes.dex */
public class InternalDeviceList {
    private static final int MAX_CHAR = 126;
    public static final int MAX_ID = 65534;
    public static final int MAX_ID_BUILTIN = 4095;
    private static final int MAX_LENGTH = 255;
    public static final int MAX_SIZE = 64;
    private static final int MIN_CHAR = 33;
    public static final int MIN_ID = 4096;
    public static final int MIN_ID_BUILTIN = 1;
    private static final int MIN_LENGTH = 1;
    private DeviceWrapper[] mDevices = new DeviceWrapper[64];
    private int mSize;

    InternalDeviceList() {
    }

    DeviceWrapper add(InternalDevice internalDevice, int i, IFelicaDevice iFelicaDevice) {
        if (this.mSize == 64 || internalDevice == null || !checkString(internalDevice.getType()) || !checkString(internalDevice.getName())) {
            return null;
        }
        for (int i2 = 0; i2 < this.mSize; i2++) {
            if (this.mDevices[i2].getId() == i || this.mDevices[i2].compare(internalDevice)) {
                return null;
            }
        }
        return addList(internalDevice, i, iFelicaDevice);
    }

    private DeviceWrapper addList(InternalDevice internalDevice, int i, IFelicaDevice iFelicaDevice) {
        DeviceWrapper feliCaChipWrapper;
        int i2 = 0;
        while (true) {
            if (i2 >= this.mSize) {
                break;
            }
            int id = this.mDevices[i2].getId();
            if (id < i) {
                i2++;
            } else if (id <= i) {
                return null;
            }
        }
        if (iFelicaDevice == null) {
            feliCaChipWrapper = new DeviceWrapper(i, internalDevice);
        } else {
            feliCaChipWrapper = new FeliCaChipWrapper(i, internalDevice, iFelicaDevice);
        }
        for (int i3 = this.mSize; i3 > i2; i3--) {
            DeviceWrapper[] deviceWrapperArr = this.mDevices;
            deviceWrapperArr[i3] = deviceWrapperArr[i3 - 1];
        }
        DeviceWrapper[] deviceWrapperArr2 = this.mDevices;
        deviceWrapperArr2[i2] = feliCaChipWrapper;
        this.mSize++;
        return deviceWrapperArr2[i2];
    }

    public DeviceWrapper get(int i) {
        if (i < 0 || i >= this.mSize) {
            return null;
        }
        return this.mDevices[i];
    }

    public DeviceWrapper get(String str, String str2) {
        for (int i = 0; i < this.mSize; i++) {
            if (this.mDevices[i].compare(str, str2)) {
                return this.mDevices[i];
            }
        }
        return null;
    }

    public DeviceWrapper getById(int i) {
        for (int i2 = 0; i2 < this.mSize; i2++) {
            if (this.mDevices[i2].getId() == i) {
                return this.mDevices[i2];
            }
        }
        return null;
    }

    public DeviceWrapper remove(String str, String str2) {
        int i;
        int i2 = this.mSize;
        int i3 = 0;
        int i4 = 0;
        DeviceWrapper deviceWrapper = null;
        while (true) {
            i = this.mSize;
            if (i3 >= i) {
                break;
            }
            if (this.mDevices[i3].compare(str, str2)) {
                DeviceWrapper[] deviceWrapperArr = this.mDevices;
                DeviceWrapper deviceWrapper2 = deviceWrapperArr[i3];
                deviceWrapperArr[i3] = null;
                i2--;
                deviceWrapper = deviceWrapper2;
            } else {
                DeviceWrapper[] deviceWrapperArr2 = this.mDevices;
                deviceWrapperArr2[i4] = deviceWrapperArr2[i3];
                i4++;
            }
            i3++;
        }
        if (i2 != i) {
            this.mSize = i2;
        }
        return deviceWrapper;
    }

    public void clear() {
        for (int i = 0; i < this.mSize; i++) {
            this.mDevices[i] = null;
        }
        this.mSize = 0;
    }

    public int size() {
        return this.mSize;
    }

    private boolean checkString(String str) {
        try {
            ByteBuffer byteBufferEncode = StandardCharsets.ISO_8859_1.newEncoder().encode(CharBuffer.wrap(str));
            byte[] bArr = new byte[byteBufferEncode.limit()];
            byteBufferEncode.get(bArr);
            if (bArr.length >= 1 && bArr.length <= 255) {
                for (byte b : bArr) {
                    int i = b & 255;
                    if (i < 33 || i > MAX_CHAR) {
                        return false;
                    }
                }
                return true;
            }
        } catch (Exception unused) {
        }
        return false;
    }
}
