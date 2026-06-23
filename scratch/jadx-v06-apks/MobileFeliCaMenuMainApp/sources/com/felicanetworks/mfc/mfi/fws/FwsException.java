package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class FwsException extends Exception {
    private static final long serialVersionUID = 8591008301591306102L;
    private int type;

    public FwsException(int i, String str) {
        super(str);
        LogMgr.log(5, "%s type=%d message=%s", "000", Integer.valueOf(i), str);
        this.type = i;
        LogMgr.log(5, "%s", "999");
    }

    public int getType() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s type=%d", "999", Integer.valueOf(this.type));
        return this.type;
    }
}
