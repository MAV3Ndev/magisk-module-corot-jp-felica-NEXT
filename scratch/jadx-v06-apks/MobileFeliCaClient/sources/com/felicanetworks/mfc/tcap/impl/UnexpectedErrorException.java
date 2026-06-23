package com.felicanetworks.mfc.tcap.impl;

/* JADX INFO: loaded from: classes.dex */
public class UnexpectedErrorException extends TcapException {
    public static final String EMSG_INTERRUPTED_BY_USER = "Interrupted.";
    public static final byte TYPE_HANDSHAKE_ERROR = 3;
    public static final byte TYPE_INTERRUPTED_BY_USER = 1;
    public static final byte TYPE_OPERATE_DEVICE_FAILED = 2;
    public static final byte TYPE_PRE_EXCOMMAND_ERROR = 4;
    public static final byte TYPE_UNEXPECTED_ERROR = 0;
    private static final long serialVersionUID = -7042704277843166470L;
    private String mDescription;

    public UnexpectedErrorException(byte b, String str) {
        super(b, str);
        this.mDescription = formatErrorDescription(b, null);
        if (str == null || b == 1) {
            return;
        }
        this.mDescription += " " + str;
    }

    public String getErrorDescription() {
        return this.mDescription;
    }
}
