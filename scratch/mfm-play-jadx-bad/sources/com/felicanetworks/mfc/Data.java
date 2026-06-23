package com.felicanetworks.mfc;

import android.os.Parcelable;

/* JADX INFO: loaded from: classes3.dex */
public abstract class Data implements Parcelable {
    private static final int BYTES_LENGTH = 16;
    private static final String EXC_LENGTH = "The length must be 16.";
    private static final String EXC_NULL = "null is not allowed.";

    public abstract void checkFormat() throws IllegalArgumentException;

    protected int getBytesLength() {
        return 16;
    }

    public abstract int getType();

    final void checkBytes(byte[] bytes) throws IllegalArgumentException {
        if (bytes == null) {
            throw new IllegalArgumentException(EXC_NULL);
        }
        if (bytes.length != 16) {
            throw new IllegalArgumentException(EXC_LENGTH);
        }
    }
}
