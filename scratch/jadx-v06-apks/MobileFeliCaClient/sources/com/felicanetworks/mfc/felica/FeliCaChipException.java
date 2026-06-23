package com.felicanetworks.mfc.felica;

/* JADX INFO: loaded from: classes.dex */
public class FeliCaChipException extends Exception {
    public static final String FELICA_CHIP_RW_STOP_MESSAGE = "RW_STOP_ERROR";
    public static final int TYPE_CANCELED = 32;
    public static final int TYPE_ILLEGAL_SEQUENCE = 21;
    public static final int TYPE_INVALID_COMMAND = 22;
    public static final int TYPE_INVALID_RESPONSE = 23;
    public static final int TYPE_IO_ERROR = 11;
    public static final int TYPE_NOT_INITIALIZED = 1;
    public static final int TYPE_NOT_OPENED = 2;
    public static final int TYPE_TIMEDOUT = 31;
    public static final int TYPE_UNKNOWN_ERROR = 0;
    private static final long serialVersionUID = -4591315655542888498L;
    private int mType;

    public FeliCaChipException(int i) {
        this.mType = i;
    }

    public FeliCaChipException(String str) {
        super(str);
        this.mType = 0;
    }

    public FeliCaChipException(int i, String str) {
        super(str);
        this.mType = i;
    }

    public int getType() {
        return this.mType;
    }
}
