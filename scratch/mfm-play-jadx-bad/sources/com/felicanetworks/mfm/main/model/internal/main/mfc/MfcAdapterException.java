package com.felicanetworks.mfm.main.model.internal.main.mfc;

import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;

/* JADX INFO: loaded from: classes3.dex */
public class MfcAdapterException extends MfcException {
    private MfcException.Type type;

    public MfcAdapterException(Class cls, int index, Exception e, MfcException.Type type) {
        super(cls, index, e);
        this.type = type;
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException
    public MfcException.Type getErrorType() {
        return this.type;
    }
}
