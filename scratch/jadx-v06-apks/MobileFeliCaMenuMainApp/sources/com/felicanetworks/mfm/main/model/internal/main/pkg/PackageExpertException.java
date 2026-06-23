package com.felicanetworks.mfm.main.model.internal.main.pkg;

import com.felicanetworks.mfm.main.policy.err.MfmException;

/* JADX INFO: loaded from: classes.dex */
public class PackageExpertException extends MfmException {
    private Type errorType;

    public enum Type {
        ID_PKG_NOTFOUND_ERROR,
        ID_OTHER_ERROR
    }

    public PackageExpertException(Class cls, int i, Exception exc, String str, Type type) {
        super(cls, i, exc, str);
        this.errorType = type;
    }

    public Type getErrorType() {
        return this.errorType;
    }
}
