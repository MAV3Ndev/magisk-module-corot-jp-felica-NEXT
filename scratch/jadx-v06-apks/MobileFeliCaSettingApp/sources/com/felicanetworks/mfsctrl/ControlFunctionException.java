package com.felicanetworks.mfsctrl;

import androidx.core.view.InputDeviceCompat;
import com.felicanetworks.common.cmnlib.CommonAppException;

/* JADX INFO: loaded from: classes.dex */
public class ControlFunctionException extends CommonAppException {

    @Deprecated
    public static final int ERROR_CODE_BADPARAM = 1;

    @Deprecated
    public static final int ERROR_CODE_BADSTATUS = 2;
    public static final int ERROR_CODE_FATAL = 3;
    private static final long serialVersionUID = -377375146110623863L;
    private int _errorCode;

    @Override // com.felicanetworks.common.cmnlib.ExceptionCodeInterface
    public int getExceptionCode() {
        return InputDeviceCompat.SOURCE_DPAD;
    }

    public ControlFunctionException(String str, String str2, int i) {
        super(str);
        this._errorCode = 0;
        super.setErrIdentifierCode(str2);
        this._errorCode = i;
    }

    public ControlFunctionException(String str, int i) {
        this(str, null, i);
    }

    public int getErrorCode() {
        return this._errorCode;
    }
}
