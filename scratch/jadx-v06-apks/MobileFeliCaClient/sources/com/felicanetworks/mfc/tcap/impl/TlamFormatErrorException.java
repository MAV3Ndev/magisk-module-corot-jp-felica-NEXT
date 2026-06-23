package com.felicanetworks.mfc.tcap.impl;

/* JADX INFO: loaded from: classes.dex */
public class TlamFormatErrorException extends Exception {
    private static final String MESSAGE = "Communication initiation error.";
    private static final long serialVersionUID = 4194463508925764550L;

    public TlamFormatErrorException() {
        super(MESSAGE);
    }
}
