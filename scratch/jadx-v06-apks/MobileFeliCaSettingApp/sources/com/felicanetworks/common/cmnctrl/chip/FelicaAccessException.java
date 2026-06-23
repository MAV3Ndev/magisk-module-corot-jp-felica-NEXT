package com.felicanetworks.common.cmnctrl.chip;

import com.felicanetworks.common.cmnctrl.data.FelicaErrorInfo;
import com.felicanetworks.common.cmnlib.CommonAppException;

/* JADX INFO: loaded from: classes.dex */
public class FelicaAccessException extends CommonAppException {
    public static final int TYPE_FELICA_TIMEOUT_ERROR = 2;
    public static final int TYPE_MFC_OTHER_ERROR = 3;
    public static final int TYPE_MFIC_NOT_FOUND = 4;
    public static final int TYPE_NOT_SYSTEM_ERROR = 0;
    public static final int TYPE_OTHER_ERROR = 1;
    private static final long serialVersionUID = -410721103973217394L;
    private int errorId;
    private FelicaErrorInfo felicaErrInfo;

    @Override // com.felicanetworks.common.cmnlib.ExceptionCodeInterface
    public int getExceptionCode() {
        return 3;
    }

    public FelicaAccessException(String str) {
        super(str);
        this.errorId = 1;
        this.felicaErrInfo = null;
    }

    public FelicaAccessException(String str, Throwable th) {
        super(str, th);
        this.errorId = 1;
        this.felicaErrInfo = null;
    }

    public FelicaAccessException(Throwable th, String str, int i) {
        super(th);
        this.errorId = 1;
        this.felicaErrInfo = null;
        super.setErrIdentifierCode(str);
        this.errorId = i;
    }

    public FelicaAccessException(int i, String str) {
        super(str);
        this.errorId = 1;
        this.felicaErrInfo = null;
        this.errorId = i;
    }

    public FelicaAccessException(Throwable th, String str, int i, FelicaErrorInfo felicaErrorInfo) {
        super(th);
        this.errorId = 1;
        this.felicaErrInfo = null;
        super.setErrIdentifierCode(str);
        this.errorId = i;
        this.felicaErrInfo = felicaErrorInfo;
    }

    public FelicaErrorInfo getFelicaErrInfo() {
        return this.felicaErrInfo;
    }

    public int getErrorId() {
        return this.errorId;
    }
}
