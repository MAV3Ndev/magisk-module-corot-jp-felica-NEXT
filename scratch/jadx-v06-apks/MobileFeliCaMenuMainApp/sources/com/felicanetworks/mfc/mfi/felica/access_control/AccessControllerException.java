package com.felicanetworks.mfc.mfi.felica.access_control;

/* JADX INFO: loaded from: classes.dex */
public class AccessControllerException extends Exception {
    public static final int TYPE_ILLEGAL_METHOD_CALL = 1;
    public static final int TYPE_ILLEGAL_NODECODE = 0;
    public static final int TYPE_ILLEGAL_SERVICEID = 3;
    public static final int TYPE_ILLEGAL_SYSTEMCODE = 2;
    private static final long serialVersionUID = 4051851972155985117L;
    private int mType;

    public AccessControllerException(int i) {
        this.mType = i;
    }

    public int getType() {
        return this.mType;
    }
}
