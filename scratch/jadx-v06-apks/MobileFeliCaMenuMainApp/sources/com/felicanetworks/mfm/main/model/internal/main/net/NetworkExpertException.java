package com.felicanetworks.mfm.main.model.internal.main.net;

import com.felicanetworks.mfm.main.policy.err.MfmException;

/* JADX INFO: loaded from: classes.dex */
public class NetworkExpertException extends MfmException {
    private Type errorType;

    public enum Type {
        ID_TIMEOUT,
        ID_CANCELED,
        ID_COMMUNICATION_ERROR,
        ID_LENGTH_ERROR,
        ID_ATTRIBUTE_ERROR,
        ID_COMMUNICATION_DATA_MAXIMUM_SIZE_ORVER_ERROR,
        ID_JSON_ERROR,
        ID_OTHER_ERROR
    }

    public NetworkExpertException(Class cls, int i, Exception exc, String str, Type type) {
        super(cls, i, exc, str);
        this.errorType = type;
    }

    public NetworkExpertException(Class cls, int i, String str, Type type) {
        super(cls, i, str);
        this.errorType = type;
    }

    public Type getErrorType() {
        return this.errorType;
    }
}
