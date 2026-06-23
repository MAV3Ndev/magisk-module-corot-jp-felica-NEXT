package com.felicanetworks.mfc.tcap.impl;

import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes.dex */
public class WarningMessage extends Message {
    private static final String MSG_CHARSET = "ISO-8859-1";

    @Override // com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateData() {
        return true;
    }

    @Override // com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateFormat() {
        return true;
    }

    public WarningMessage(Message message) {
        super(message);
    }

    public String getMessage() {
        if (this.mLength > 0) {
            try {
                return new String(this.mData, this.mOffset + 6, this.mLength, MSG_CHARSET);
            } catch (UnsupportedEncodingException unused) {
            }
        }
        return null;
    }
}
