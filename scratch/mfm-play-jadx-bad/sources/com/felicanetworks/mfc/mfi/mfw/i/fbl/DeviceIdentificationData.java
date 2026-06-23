package com.felicanetworks.mfc.mfi.mfw.i.fbl;

import com.felicanetworks.mfc.mfi.BadPropertyException;

/* JADX INFO: loaded from: classes3.dex */
public class DeviceIdentificationData extends AbstructDeviceIdentificationData {
    @Override // com.felicanetworks.mfc.mfi.mfw.i.fbl.AbstructDeviceIdentificationData
    public String get() throws BadPropertyException {
        return getFromProvider();
    }
}
