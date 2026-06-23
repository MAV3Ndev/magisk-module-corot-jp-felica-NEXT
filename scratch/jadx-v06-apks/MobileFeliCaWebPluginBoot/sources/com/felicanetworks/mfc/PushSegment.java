package com.felicanetworks.mfc;

import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public abstract class PushSegment implements Parcelable {
    public static Parcelable.Creator CREATOR;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected int f88a;
    protected byte[] b;

    public int a() {
        return this.f88a;
    }
}
