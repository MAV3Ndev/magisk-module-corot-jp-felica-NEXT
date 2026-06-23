package com.felicanetworks.mfsctrl.chip;

import com.felicanetworks.common.cmnctrl.data.FelicaErrorInfo;
import com.felicanetworks.common.cmnlib.CommonAppException;

/* JADX INFO: loaded from: classes.dex */
public class MfsFelicaAccessException extends CommonAppException {
    public static final int TYPE_FELICA_TIMEOUT_ERROR = 4;
    public static final int TYPE_ILLEGALARGUMENT = 2;
    public static final int TYPE_MFC_OTHER_ERROR = 5;
    public static final int TYPE_OPARATIONREQUESTED = 3;
    public static final int TYPE_OTHER_ERROR = 1;
    private static final long serialVersionUID = -410721103973217394L;
    private int errorId;
    private FelicaErrorInfo felicaErrInfo;

    @Override // com.felicanetworks.common.cmnlib.ExceptionCodeInterface
    public int getExceptionCode() {
        return 514;
    }

    public MfsFelicaAccessException(int i) {
        this.felicaErrInfo = null;
        this.errorId = i;
    }

    public MfsFelicaAccessException(Throwable th, String str, int i) {
        super("ErrorId : " + i, th);
        this.felicaErrInfo = null;
        super.setErrIdentifierCode(str);
        this.errorId = i;
    }

    public MfsFelicaAccessException(String str, Throwable th) {
        super(str, th);
        this.felicaErrInfo = null;
    }

    public MfsFelicaAccessException(Throwable th, String str, int i, FelicaErrorInfo felicaErrorInfo) {
        super(th);
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
