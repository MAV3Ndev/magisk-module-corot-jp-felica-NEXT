package com.felicanetworks.mfc.tcap.impl.v25;

import com.felicanetworks.mfc.tcap.impl.FeliCaCommandMessage;
import com.felicanetworks.mfc.tcap.impl.Message;
import com.felicanetworks.mfc.tcap.impl.PacketFormatErrorException;

/* JADX INFO: loaded from: classes.dex */
class FeliCaCommandThruRwMessage extends FeliCaCommandMessage {
    private static final int MAX_LENGTH = 250;
    private static final int MIN_LENGTH = 1;

    FeliCaCommandThruRwMessage(Message message) throws PacketFormatErrorException {
        super(message);
    }

    @Override // com.felicanetworks.mfc.tcap.impl.FeliCaCommandMessage, com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateFormat() {
        return this.mLength >= 1 && this.mLength <= MAX_LENGTH;
    }
}
