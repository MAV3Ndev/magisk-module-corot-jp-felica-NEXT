package com.felicanetworks.mfm.main.model.internal.main.db;

import com.felicanetworks.mfm.main.policy.err.MfmException;

/* JADX INFO: loaded from: classes.dex */
public class DatabaseException extends MfmException {
    public DatabaseException(Class cls, int i, Exception exc) {
        super(cls, i, exc);
    }

    public DatabaseException(Class cls, int i, String str) {
        super(cls, i, str);
    }
}
