package com.felicanetworks.mfm.main.model.internal.main.mfc.em;

import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;

/* JADX INFO: loaded from: classes3.dex */
public class MficApiCallbackInfo extends CausedErrorInfo {
    private final String message;
    private final int type;

    public MficApiCallbackInfo(int type, String message) {
        super(CauseLocation.MFIC_API_CALLBACK);
        this.type = type;
        this.message = message;
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    protected int getCauseType() {
        return this.type;
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    public String getMessage() {
        return this.message;
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    public MfcException.Type getType() {
        return MficErrorMapping.get(this.type);
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    public int getExtra() {
        return this.type;
    }
}
