package com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util;

import com.felicanetworks.mfm.main.policy.err.MfmException;

/* JADX INFO: loaded from: classes.dex */
public class DataCheckerException extends MfmException {
    public static final int ID_ATTRIBUTE_ERROR = 1;
    public static final int ID_LENGTH_ERROR = 0;
    private int id;

    public DataCheckerException(Class cls, int i, int i2, String str) {
        super(cls, i, str);
        this.id = -1;
        this.id = i2;
    }

    public DataCheckerException(Class cls, int i, int i2) {
        super(cls, i);
        this.id = -1;
        this.id = i2;
    }

    public int getErrorId() {
        return this.id;
    }
}
