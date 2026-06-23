package com.felicanetworks.mfc.tcap.impl;

import com.felicanetworks.mfc.tcap.InternalDevice;

/* JADX INFO: loaded from: classes.dex */
public class DeviceWrapper {
    private InternalDevice mDevice;
    private int mId;

    DeviceWrapper(int i, InternalDevice internalDevice) {
        this.mId = i;
        this.mDevice = internalDevice;
    }

    public final int getId() {
        return this.mId;
    }

    public final InternalDevice getDevice() {
        return this.mDevice;
    }

    final boolean compare(InternalDevice internalDevice) {
        if (internalDevice == null) {
            return false;
        }
        return compare(internalDevice.getType(), internalDevice.getName());
    }

    final boolean compare(String str, String str2) {
        return this.mDevice.getType().equals(str) && this.mDevice.getName().equals(str2);
    }

    public final String getType() {
        return this.mDevice.getType();
    }

    public final String getName() {
        return this.mDevice.getName();
    }

    public final boolean isBuiltin() {
        return this.mDevice.isBuiltin();
    }

    public final byte[] operate(String str, byte[] bArr) throws Exception {
        return ((TcapDevice) this.mDevice).operate(this.mId, str, bArr);
    }
}
