package com.felicanetworks.mfc.tcap.impl.v25;

import com.felicanetworks.mfc.tcap.impl.Message;

/* JADX INFO: loaded from: classes.dex */
class RequestIdMessage extends Message {
    private static final int LENGTH = 2;

    @Override // com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateData() {
        return true;
    }

    RequestIdMessage(Message message) {
        super(message);
    }

    RequestIdMessage(int i) {
        init((byte) 0, 0, (byte) 48, 2);
        this.mData[6] = (byte) ((65280 & i) >> 8);
        this.mData[7] = (byte) (i & 255);
    }

    @Override // com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateFormat() {
        return this.mLength == 2;
    }

    int getRequestId() {
        return (this.mData[this.mOffset + 6 + 1] & 255) | ((this.mData[(this.mOffset + 6) + 0] & 255) << 8);
    }
}
