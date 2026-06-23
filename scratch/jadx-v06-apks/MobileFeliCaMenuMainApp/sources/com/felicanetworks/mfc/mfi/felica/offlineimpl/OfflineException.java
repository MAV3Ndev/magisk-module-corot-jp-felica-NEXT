package com.felicanetworks.mfc.mfi.felica.offlineimpl;

/* JADX INFO: loaded from: classes.dex */
public class OfflineException extends Exception {
    public static final String LA_ERROR_MESSAGE = "Cannot change discovery-state.";
    public static final int TYPE_CANCELED = 8;
    public static final int TYPE_CHIP_LOCKED = 7;
    public static final int TYPE_CONFLICT_NFC = 9;
    public static final int TYPE_DATA_GET_FAILED = 3;
    public static final int TYPE_DATA_SET_FAILED = 2;
    public static final int TYPE_INVALID_PARAMETER = 1;
    public static final int TYPE_INVALID_RESPONSE = 4;
    public static final int TYPE_INVALID_STATUS_FLAG = 6;
    public static final int TYPE_TIMEOUT_OCCURRED = 5;
    public static final int TYPE_UNKNOWN_ERROR = 0;
    private static final long serialVersionUID = -9088221524652378021L;
    private int mStatusFlag1;
    private int mStatusFlag2;
    private int mType;

    OfflineException() {
    }

    OfflineException(int i) {
        this.mType = i;
    }

    OfflineException(int i, String str) {
        super(str);
        this.mType = i;
    }

    OfflineException(int i, int i2, int i3) {
        this.mType = i;
        this.mStatusFlag1 = i2;
        this.mStatusFlag2 = i3;
    }

    public int getType() {
        return this.mType;
    }

    public int getStatusFlag1() {
        return this.mStatusFlag1;
    }

    public int getStatusFlag2() {
        return this.mStatusFlag2;
    }
}
