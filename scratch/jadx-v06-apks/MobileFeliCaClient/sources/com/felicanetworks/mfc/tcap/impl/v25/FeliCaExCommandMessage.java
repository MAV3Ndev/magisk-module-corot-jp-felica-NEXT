package com.felicanetworks.mfc.tcap.impl.v25;

import com.felicanetworks.mfc.tcap.impl.Message;

/* JADX INFO: loaded from: classes.dex */
class FeliCaExCommandMessage extends Message {
    private static final int MAX_LENGTH = 257;
    private static final int MIN_LENGTH = 3;

    @Override // com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateData() {
        return true;
    }

    FeliCaExCommandMessage(Message message) {
        super(message);
    }

    @Override // com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateFormat() {
        return this.mLength >= 3 && this.mLength <= MAX_LENGTH;
    }

    int getParamId() {
        return this.mData[this.mOffset + 6] & 255;
    }

    int getParamPos() {
        return this.mData[this.mOffset + 6 + 1] & 255;
    }

    byte[] getCommand(byte[] bArr) {
        byte[] bArr2 = new byte[this.mLength - 2];
        int paramPos = getParamPos();
        System.arraycopy(this.mData, this.mOffset + 6 + 2, bArr2, 0, this.mLength - 2);
        System.arraycopy(bArr, 0, bArr2, paramPos, bArr.length);
        return bArr2;
    }
}
