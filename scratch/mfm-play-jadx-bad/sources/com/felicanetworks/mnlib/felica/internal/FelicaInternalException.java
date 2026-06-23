package com.felicanetworks.mnlib.felica.internal;

/* JADX INFO: loaded from: classes3.dex */
public class FelicaInternalException extends Exception {
    public static final int TYPE_DATA_GET_FAILED = 2;
    public static final int TYPE_DATA_SET_FAILED = 1;
    public static final int TYPE_INVALID_PARAMETER = 0;
    public static final int TYPE_INVALID_RESPONSE = 3;
    public static final int TYPE_INVALID_STATUS_FLAG = 4;
    private static final long serialVersionUID = -5853085680327051800L;
    private int mStatusFlag1;
    private int mStatusFlag2;
    private int mType;

    FelicaInternalException(int i) {
        this.mType = i;
    }

    FelicaInternalException(int i, int i2, int i3) {
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
