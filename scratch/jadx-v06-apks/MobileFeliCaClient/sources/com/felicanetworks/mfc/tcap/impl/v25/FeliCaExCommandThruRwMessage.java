package com.felicanetworks.mfc.tcap.impl.v25;

import com.felicanetworks.mfc.tcap.impl.Message;

/* JADX INFO: loaded from: classes.dex */
class FeliCaExCommandThruRwMessage extends FeliCaExCommandMessage {
    private static final int MAX_LENGTH = 252;
    private static final int MIN_LENGTH = 3;

    FeliCaExCommandThruRwMessage(Message message) {
        super(message);
    }

    @Override // com.felicanetworks.mfc.tcap.impl.v25.FeliCaExCommandMessage, com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateFormat() {
        return this.mLength >= 3 && this.mLength <= MAX_LENGTH;
    }
}
