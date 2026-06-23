package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class MfiFelicaException extends Exception {
    private static final long serialVersionUID = 855775570982296439L;
    private int type;

    public MfiFelicaException(int type, String message) {
        super(message);
        LogMgr.log(5, "%s type=%d message=%s", "000", Integer.valueOf(type), message);
        this.type = type;
        LogMgr.log(5, "%s", "999");
    }

    public int getType() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s type=%d", "999", Integer.valueOf(this.type));
        return this.type;
    }
}
