package com.felicanetworks.common.cmnlib.sg;

import com.felicanetworks.common.cmnlib.ExceptionCodeInterface;

/* JADX INFO: loaded from: classes.dex */
public class SgMgrException extends Exception implements ExceptionCodeInterface {
    String errIdentifierCode;

    @Override // com.felicanetworks.common.cmnlib.ExceptionCodeInterface
    public int getExceptionCode() {
        return 8;
    }

    public SgMgrException() {
        this.errIdentifierCode = null;
    }

    public SgMgrException(String str, Throwable th) {
        super(str, th);
        this.errIdentifierCode = null;
    }

    public SgMgrException(String str) {
        super(str);
        this.errIdentifierCode = null;
    }

    public SgMgrException(Throwable th) {
        super(th);
        this.errIdentifierCode = null;
    }

    @Override // com.felicanetworks.common.cmnlib.ExceptionCodeInterface
    public String getErrIdentifierCode() {
        return this.errIdentifierCode;
    }

    public void setErrIdentifierCode(String str) {
        this.errIdentifierCode = str;
    }
}
