package com.felicanetworks.mfc.tcap.impl.v25;

import com.felicanetworks.mfc.tcap.impl.Message;

/* JADX INFO: loaded from: classes.dex */
class FeliCaPreCommandThruRwMessage extends FeliCaPreCommandMessage {
    private static final int MAX_LENGTH = 253;
    private static final int MIN_LENGTH = 4;

    FeliCaPreCommandThruRwMessage(Message message) {
        super(message);
    }

    @Override // com.felicanetworks.mfc.tcap.impl.v25.FeliCaPreCommandMessage, com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateFormat() {
        return this.mLength >= 4 && this.mLength <= MAX_LENGTH;
    }
}
