package com.felicanetworks.mfm.main.model.internal.main.pkg;

import com.felicanetworks.mfm.main.policy.err.MfmException;

/* JADX INFO: loaded from: classes3.dex */
public class PackageExpertException extends MfmException {
    private Type errorType;

    public enum Type {
        ID_PKG_NOTFOUND_ERROR,
        ID_OTHER_ERROR
    }

    public PackageExpertException(Class cls, int index, Exception e, String msg, Type errorType) {
        super(cls, index, e, msg);
        this.errorType = errorType;
    }

    public Type getErrorType() {
        return this.errorType;
    }
}
