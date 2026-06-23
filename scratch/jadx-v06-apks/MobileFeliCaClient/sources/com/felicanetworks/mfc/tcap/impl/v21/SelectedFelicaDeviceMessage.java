package com.felicanetworks.mfc.tcap.impl.v21;

import com.felicanetworks.mfc.tcap.impl.Message;

/* JADX INFO: loaded from: classes.dex */
class SelectedFelicaDeviceMessage extends Message {
    static final byte FELICA_TYPE_EXTERNAL = 2;
    static final byte FELICA_TYPE_INTERNAL = 1;
    static final byte FELICA_TYPE_SECRET = 3;

    SelectedFelicaDeviceMessage(int i, byte b) {
        init((byte) 1, i, (byte) 3, 1);
        this.mData[6] = b;
    }
}
