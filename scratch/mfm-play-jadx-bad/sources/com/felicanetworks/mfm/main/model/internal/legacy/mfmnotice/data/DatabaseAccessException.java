package com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data;

import com.felicanetworks.mfm.main.policy.err.MfmException;

/* JADX INFO: loaded from: classes3.dex */
public class DatabaseAccessException extends MfmException {
    public static final int ID_OTHER_FACTOR = 2;
    private int errorId;

    public DatabaseAccessException(Class cls, int index, Exception e) {
        super(cls, index, e);
        this.errorId = 2;
    }

    public DatabaseAccessException(Class cls, int index, Exception e, int errorId) {
        super(cls, index, e);
        this.errorId = errorId;
    }
}
