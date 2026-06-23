package com.felicanetworks.mfc.tcap.impl;

/* JADX INFO: loaded from: classes.dex */
public class ReturnCodeMessage extends Message {
    private static final int LENGTH = 4;

    @Override // com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateData() {
        return true;
    }

    public ReturnCodeMessage(Message message) {
        super(message);
    }

    @Override // com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateFormat() {
        return this.mLength == 4;
    }

    public int getReturnCode() {
        return (this.mData[this.mOffset + 6 + 3] & 255) | ((this.mData[this.mOffset + 6] & 255) << 24) | ((this.mData[(this.mOffset + 6) + 1] & 255) << 16) | ((this.mData[(this.mOffset + 6) + 2] & 255) << 8);
    }
}
