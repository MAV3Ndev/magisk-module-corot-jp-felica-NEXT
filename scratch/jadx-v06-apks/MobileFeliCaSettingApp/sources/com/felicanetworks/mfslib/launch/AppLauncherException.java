package com.felicanetworks.mfslib.launch;

import com.felicanetworks.common.cmnlib.CommonAppException;

/* JADX INFO: loaded from: classes.dex */
public class AppLauncherException extends CommonAppException {
    public static final int ID_APP_START_ERROR = 0;
    public static final int ID_OTHER_ERROR = 1;
    private static final long serialVersionUID = 5491786012789300575L;
    private int errorId;

    @Override // com.felicanetworks.common.cmnlib.ExceptionCodeInterface
    public int getExceptionCode() {
        return 259;
    }

    public AppLauncherException(int i, String str) {
        super(str);
        this.errorId = 1;
        this.errorId = i;
    }

    public AppLauncherException(Throwable th, String str, int i) {
        super(th);
        this.errorId = 1;
        this.errorId = i;
        setErrIdentifierCode(str);
    }

    public int getErrorId() {
        return this.errorId;
    }
}
