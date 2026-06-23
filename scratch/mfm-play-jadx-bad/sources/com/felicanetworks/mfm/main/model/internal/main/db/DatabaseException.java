package com.felicanetworks.mfm.main.model.internal.main.db;

import com.felicanetworks.mfm.main.policy.err.MfmException;

/* JADX INFO: loaded from: classes3.dex */
public class DatabaseException extends MfmException {
    public DatabaseException(Class cls, int index, Exception e) {
        super(cls, index, e);
    }

    public DatabaseException(Class cls, int index, String msg) {
        super(cls, index, msg);
    }

    public DatabaseException(Class cls, int index, Exception e, int ex) {
        super(cls, index, e, ex);
    }
}
