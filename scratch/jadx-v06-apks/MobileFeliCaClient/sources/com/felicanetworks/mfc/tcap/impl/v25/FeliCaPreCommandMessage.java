package com.felicanetworks.mfc.tcap.impl.v25;

import com.felicanetworks.mfc.tcap.impl.Message;

/* JADX INFO: loaded from: classes.dex */
class FeliCaPreCommandMessage extends Message {
    private static final int MAX_LENGTH = 258;
    private static final int MIN_LENGTH = 4;

    @Override // com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateData() {
        return true;
    }

    FeliCaPreCommandMessage(Message message) {
        super(message);
    }

    @Override // com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateFormat() {
        return this.mLength >= 4 && this.mLength <= MAX_LENGTH;
    }

    int getParamId() {
        return this.mData[this.mOffset + 6] & 255;
    }

    int getParamPos() {
        return this.mData[this.mOffset + 6 + 1] & 255;
    }

    int getParamLen() {
        return this.mData[this.mOffset + 6 + 2] & 255;
    }

    byte[] getCommand() {
        byte[] bArr = new byte[this.mLength - 3];
        System.arraycopy(this.mData, this.mOffset + 6 + 3, bArr, 0, this.mLength - 3);
        return bArr;
    }
}
