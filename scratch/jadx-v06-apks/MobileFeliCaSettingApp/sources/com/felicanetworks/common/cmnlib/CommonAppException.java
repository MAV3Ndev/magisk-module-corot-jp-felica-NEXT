package com.felicanetworks.common.cmnlib;

/* JADX INFO: loaded from: classes.dex */
public abstract class CommonAppException extends Exception implements ExceptionCodeInterface {
    String errIdentifiercode;

    public CommonAppException() {
        this.errIdentifiercode = null;
    }

    public CommonAppException(String str, Throwable th) {
        super(str, th);
        this.errIdentifiercode = null;
    }

    public CommonAppException(String str) {
        super(str);
        this.errIdentifiercode = null;
    }

    public CommonAppException(Throwable th) {
        super(th);
        this.errIdentifiercode = null;
    }

    @Override // com.felicanetworks.common.cmnlib.ExceptionCodeInterface
    public String getErrIdentifierCode() {
        return this.errIdentifiercode;
    }

    public void setErrIdentifierCode(String str) {
        this.errIdentifiercode = str;
    }
}
