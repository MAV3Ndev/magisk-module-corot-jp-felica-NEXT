package com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util;

import com.felicanetworks.mfm.main.policy.err.MfmException;

/* JADX INFO: loaded from: classes3.dex */
public class DataCheckerException extends MfmException {
    public static final int ID_ATTRIBUTE_ERROR = 1;
    public static final int ID_LENGTH_ERROR = 0;
    private int id;

    public DataCheckerException(Class cls, int index, int id, String msg) {
        super(cls, index, msg);
        this.id = id;
    }

    public DataCheckerException(Class cls, int index, int id) {
        super(cls, index);
        this.id = id;
    }

    public int getErrorId() {
        return this.id;
    }
}
