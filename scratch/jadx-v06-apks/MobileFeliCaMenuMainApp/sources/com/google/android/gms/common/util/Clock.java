package com.google.android.gms.common.util;

/* JADX INFO: loaded from: classes2.dex */
public interface Clock {
    long currentThreadTimeMillis();

    long currentTimeMillis();

    long elapsedRealtime();

    long nanoTime();
}
