package com.felicanetworks.common.cmnctrl.chip;

import com.felicanetworks.common.cmnlib.CommonAppException;
import com.felicanetworks.common.cmnlib.ExceptionCodeInterface;

/* JADX INFO: loaded from: classes.dex */
public class BindException extends CommonAppException implements ExceptionCodeInterface {
    private static final long serialVersionUID = 3874777629232853908L;

    @Override // com.felicanetworks.common.cmnlib.ExceptionCodeInterface
    public int getExceptionCode() {
        return 2;
    }

    public BindException(Throwable th, String str) {
        super(th);
        setErrIdentifierCode(str);
    }
}
