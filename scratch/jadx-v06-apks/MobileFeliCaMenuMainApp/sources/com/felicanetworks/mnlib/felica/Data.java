package com.felicanetworks.mnlib.felica;

/* JADX INFO: loaded from: classes.dex */
public abstract class Data {
    private static final int BYTES_LENGTH = 16;
    private static final String EXC_LENGTH = "The length must be 16.";
    private static final String EXC_NULL = "null is not allowed.";

    public abstract void checkFormat() throws IllegalArgumentException;

    protected int getBytesLength() {
        return 16;
    }

    public abstract int getType();

    final void checkBytes(byte[] bArr) throws IllegalArgumentException {
        if (bArr == null) {
            throw new IllegalArgumentException(EXC_NULL);
        }
        if (bArr.length != 16) {
            throw new IllegalArgumentException(EXC_LENGTH);
        }
    }
}
