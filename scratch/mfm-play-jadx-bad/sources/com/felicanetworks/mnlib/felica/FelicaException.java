package com.felicanetworks.mnlib.felica;

/* JADX INFO: loaded from: classes3.dex */
public class FelicaException extends Exception {
    public static final int ID_GET_KEY_VERSION_ERROR = 4;
    public static final int ID_ILLEGAL_STATE_ERROR = 1;
    public static final int ID_IO_ERROR = 2;
    public static final int ID_READ_ERROR = 3;
    public static final int ID_WRITE_ERROR = 5;
    public static final int TYPE_INVALID_RESPONSE = 1;
    public static final int TYPE_NOT_SELECTED = 3;
    public static final int TYPE_READ_FAILED = 4;
    public static final int TYPE_SERVICE_NOT_FOUND = 11;
    public static final int TYPE_TIMEOUT_OCCURRED = 7;
    public static final int TYPE_WRITE_FAILED = 5;
    private static final long serialVersionUID = -5401414127132212590L;
    private int mId;
    private int mStatusFlag1;
    private int mStatusFlag2;
    private int mType;

    public FelicaException(int i, int i2) {
        this.mId = i;
        this.mType = i2;
    }

    public FelicaException(int i, int i2, int i3, int i4) {
        this.mId = i;
        this.mType = i2;
        this.mStatusFlag1 = i3;
        this.mStatusFlag2 = i4;
    }

    public int getID() {
        return this.mId;
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
