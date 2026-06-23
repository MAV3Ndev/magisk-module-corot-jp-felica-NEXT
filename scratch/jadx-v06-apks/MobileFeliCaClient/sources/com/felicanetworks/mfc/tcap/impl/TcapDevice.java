package com.felicanetworks.mfc.tcap.impl;

import com.felicanetworks.mfc.tcap.InternalDevice;
import com.felicanetworks.mfc.tcap.TcapClientEventListener;

/* JADX INFO: loaded from: classes.dex */
public class TcapDevice implements InternalDevice {
    private boolean mIsbuiltin;
    private TcapClientEventListener mListener;
    private String mName;
    private String mType;

    public TcapDevice(String str, String str2, TcapClientEventListener tcapClientEventListener, boolean z) {
        this.mType = str;
        this.mName = str2;
        this.mListener = tcapClientEventListener;
        this.mIsbuiltin = z;
    }

    @Override // com.felicanetworks.mfc.tcap.InternalDevice
    public String getType() {
        return this.mType;
    }

    @Override // com.felicanetworks.mfc.tcap.InternalDevice
    public String getName() {
        return this.mName;
    }

    @Override // com.felicanetworks.mfc.tcap.InternalDevice
    public boolean isBuiltin() {
        return this.mIsbuiltin;
    }

    public byte[] operate(int i, String str, byte[] bArr) throws Exception {
        try {
            return this.mListener.operationRequested(i, str, bArr);
        } catch (Exception e) {
            throw e;
        }
    }
}
