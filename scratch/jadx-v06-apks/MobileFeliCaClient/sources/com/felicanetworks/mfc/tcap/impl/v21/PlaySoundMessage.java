package com.felicanetworks.mfc.tcap.impl.v21;

import com.felicanetworks.mfc.tcap.impl.Message;

/* JADX INFO: loaded from: classes.dex */
class PlaySoundMessage extends Message {
    @Override // com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateData() {
        return true;
    }

    @Override // com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateFormat() {
        return true;
    }

    PlaySoundMessage(Message message) {
        super(message);
    }

    byte[] getSound() {
        if (this.mLength <= 0) {
            return null;
        }
        byte[] bArr = new byte[this.mLength];
        System.arraycopy(this.mData, this.mOffset + 6, bArr, 0, this.mLength);
        return bArr;
    }
}
