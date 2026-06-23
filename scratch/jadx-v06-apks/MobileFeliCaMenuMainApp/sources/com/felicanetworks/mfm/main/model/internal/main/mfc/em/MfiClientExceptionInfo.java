package com.felicanetworks.mfm.main.model.internal.main.mfc.em;

import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;

/* JADX INFO: loaded from: classes.dex */
public class MfiClientExceptionInfo extends CausedErrorInfo {
    private final MfiClientException exception;

    public MfiClientExceptionInfo(MfiClientException mfiClientException) {
        super(CauseLocation.CAUGHT_EXCEPTION);
        this.exception = mfiClientException;
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    protected int getCauseId() {
        return this.exception.getID();
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    protected int getCauseType() {
        return this.exception.getType();
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    public Exception getException() {
        return this.exception;
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    public MfcException.Type getType() {
        return MficErrorMapping.get(this.exception.getType());
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    public String getMessage() {
        return this.exception.getMessage();
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    public int getExtra() {
        return (this.exception.getID() * 256) + this.exception.getType();
    }
}
