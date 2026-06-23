package com.felicanetworks.common.cmnctrl.packages;

import com.felicanetworks.common.cmnlib.CommonAppException;

/* JADX INFO: loaded from: classes.dex */
public class PackageAccessException extends CommonAppException {
    public static final int ID_OTHER_ERROR = 1;
    public static final int ID_PKG_NOTFOUND_ERROR = 0;
    private static final long serialVersionUID = -5871352138468117458L;
    private int errorId;

    @Override // com.felicanetworks.common.cmnlib.ExceptionCodeInterface
    public int getExceptionCode() {
        return 1;
    }

    public PackageAccessException(Throwable th, String str, int i) {
        super(th);
        this.errorId = 1;
        this.errorId = i;
        setErrIdentifierCode(str);
    }

    public int getErrorId() {
        return this.errorId;
    }
}
