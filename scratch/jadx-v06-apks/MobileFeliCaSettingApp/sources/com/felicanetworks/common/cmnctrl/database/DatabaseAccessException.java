package com.felicanetworks.common.cmnctrl.database;

import com.felicanetworks.common.cmnlib.CommonAppException;
import com.felicanetworks.common.cmnlib.log.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class DatabaseAccessException extends CommonAppException {
    public static final int ID_DBFILE_DAMAGE = 1;
    public static final int ID_NO_FREE_SPACE = 0;
    public static final int ID_OTHER_FACTOR = 2;
    private int errorId;

    @Override // com.felicanetworks.common.cmnlib.ExceptionCodeInterface
    public int getExceptionCode() {
        return 6;
    }

    public DatabaseAccessException(int i, String str) {
        super(str);
        this.errorId = 2;
        this.errorId = i;
    }

    public DatabaseAccessException(Throwable th, int i) {
        super(th);
        this.errorId = 2;
        this.errorId = i;
    }

    public DatabaseAccessException(Throwable th, String str, int i) {
        super(th);
        this.errorId = 2;
        this.errorId = i;
        setErrIdentifierCode(str);
    }

    public int getErrorId() {
        return this.errorId;
    }

    public LogMgr.CatExp getCatExp() {
        return this.errorId == 2 ? LogMgr.CatExp.ERR : LogMgr.CatExp.WAR;
    }
}
