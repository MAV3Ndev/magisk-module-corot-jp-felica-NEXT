package com.felicanetworks.mfc.tcap.impl;

/* JADX INFO: loaded from: classes.dex */
public class IllegalStateErrorException extends TcapException {
    private static final String MESSAGE = "Illegal state error.";
    public static final byte TYPE_ILLEGAL_STATE = -128;
    private static final long serialVersionUID = 5811781276129885155L;
    private String mDescription;

    public IllegalStateErrorException(byte b) {
        super(b, MESSAGE);
        this.mDescription = formatErrorDescription(b, null);
    }

    public String getErrorDescription() {
        return this.mDescription;
    }
}
