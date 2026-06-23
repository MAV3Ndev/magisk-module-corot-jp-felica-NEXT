package com.felicanetworks.mfc.tcap;

/* JADX INFO: loaded from: classes.dex */
public class FeliCaClientException extends Exception {
    public static final int TYPE_CURRENTLY_ONLINE = 11;
    public static final int TYPE_DEVICE_NOT_SET = 3;
    public static final int TYPE_LISTENER_NOT_SET = 2;
    public static final int TYPE_UNKNOWN_ERROR = 0;
    public static final int TYPE_URL_NOT_SET = 1;
    private static final long serialVersionUID = 3820995465915673208L;
    private int mType;

    public FeliCaClientException(int i) {
        this.mType = i;
    }

    public FeliCaClientException(String str) {
        super(str);
        this.mType = 0;
    }

    public FeliCaClientException(int i, String str) {
        super(str);
        this.mType = i;
    }

    public int getType() {
        return this.mType;
    }
}
