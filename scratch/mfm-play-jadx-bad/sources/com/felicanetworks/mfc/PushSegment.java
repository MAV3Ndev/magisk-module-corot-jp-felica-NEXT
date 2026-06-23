package com.felicanetworks.mfc;

import android.os.Parcelable;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PushSegment implements Parcelable {
    public static Parcelable.Creator<PushSegment> CREATOR;
    protected int activateType;
    protected byte[] pushData;

    public abstract void checkFormat() throws IllegalArgumentException;

    public int getType() {
        return this.activateType;
    }
}
