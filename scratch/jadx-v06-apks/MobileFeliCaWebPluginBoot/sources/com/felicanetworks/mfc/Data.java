package com.felicanetworks.mfc;

import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public abstract class Data implements Parcelable {
    final void a(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("null is not allowed.");
        }
        if (bArr.length != 16) {
            throw new IllegalArgumentException("The length must be 16.");
        }
    }

    protected int b() {
        return 16;
    }

    public abstract int c();
}
