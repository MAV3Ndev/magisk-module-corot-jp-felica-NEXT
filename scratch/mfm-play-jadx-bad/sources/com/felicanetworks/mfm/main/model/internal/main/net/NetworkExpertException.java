package com.felicanetworks.mfm.main.model.internal.main.net;

import com.felicanetworks.mfm.main.policy.err.MfmException;

/* JADX INFO: loaded from: classes3.dex */
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

    public NetworkExpertException(Class cls, int index, Exception e, String msg, Type errorType) {
        super(cls, index, e, msg);
        this.errorType = errorType;
    }

    public NetworkExpertException(Class cls, int index, String msg, Type errorType) {
        super(cls, index, msg);
        this.errorType = errorType;
    }

    public Type getErrorType() {
        return this.errorType;
    }
}
