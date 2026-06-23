package com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data;

import com.felicanetworks.mfm.main.policy.err.MfmException;

/* JADX INFO: loaded from: classes.dex */
public class DatabaseAccessException extends MfmException {
    public static final int ID_OTHER_FACTOR = 2;
    private int errorId;

    public DatabaseAccessException(Class cls, int i, Exception exc) {
        super(cls, i, exc);
        this.errorId = 2;
    }

    public DatabaseAccessException(Class cls, int i, Exception exc, int i2) {
        super(cls, i, exc);
        this.errorId = 2;
        this.errorId = i2;
    }
}
