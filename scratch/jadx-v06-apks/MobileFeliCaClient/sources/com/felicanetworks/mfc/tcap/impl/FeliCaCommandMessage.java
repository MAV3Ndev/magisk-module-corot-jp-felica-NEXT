package com.felicanetworks.mfc.tcap.impl;

/* JADX INFO: loaded from: classes.dex */
public class FeliCaCommandMessage extends Message {
    private static final int MAX_LENGTH = 255;
    private static final int MIN_LENGTH = 1;

    @Override // com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateData() {
        return true;
    }

    public FeliCaCommandMessage(Message message) {
        super(message);
    }

    @Override // com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateFormat() {
        return this.mLength >= 1 && this.mLength <= 255;
    }

    public byte[] getCommand() {
        byte[] bArr = new byte[this.mLength];
        System.arraycopy(this.mData, this.mOffset + 6, bArr, 0, this.mLength);
        return bArr;
    }
}
