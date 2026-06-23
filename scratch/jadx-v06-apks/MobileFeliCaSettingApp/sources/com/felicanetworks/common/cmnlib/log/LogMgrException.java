package com.felicanetworks.common.cmnlib.log;

import com.felicanetworks.common.cmnctrl.database.DatabaseAccessException;
import com.felicanetworks.common.cmnlib.CommonAppException;
import com.felicanetworks.common.cmnlib.log.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class LogMgrException extends CommonAppException {
    public static final int ID_DBFILE_DAMAGE = 1;
    public static final int ID_NO_FREE_SPACE = 0;
    public static final int ID_OTHER_FACTOR = -1;
    int errId;

    @Override // com.felicanetworks.common.cmnlib.ExceptionCodeInterface
    public int getExceptionCode() {
        return 7;
    }

    public LogMgrException() {
        this.errId = -1;
    }

    public LogMgrException(String str, Throwable th) {
        super(str, th);
        this.errId = -1;
    }

    public LogMgrException(String str) {
        super(str);
        this.errId = -1;
    }

    public LogMgrException(Throwable th) {
        super(th);
        this.errId = -1;
    }

    public LogMgrException(DatabaseAccessException databaseAccessException, String str) {
        super(databaseAccessException);
        this.errId = -1;
        int errorId = databaseAccessException.getErrorId();
        if (errorId == 0) {
            this.errId = 0;
        } else if (errorId == 1) {
            this.errId = 1;
        } else {
            this.errId = -1;
        }
    }

    public LogMgrException(Exception exc, String str) {
        super(exc);
        this.errId = -1;
        super.setErrIdentifierCode(str);
    }

    public LogMgr.CatExp getCatExp() {
        return this.errId == -1 ? LogMgr.CatExp.ERR : LogMgr.CatExp.WAR;
    }

    public int getErrorId() {
        return this.errId;
    }
}
