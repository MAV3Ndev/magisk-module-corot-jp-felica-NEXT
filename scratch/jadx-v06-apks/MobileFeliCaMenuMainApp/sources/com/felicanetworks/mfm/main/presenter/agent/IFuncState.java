package com.felicanetworks.mfm.main.presenter.agent;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public interface IFuncState {
    boolean hasNFC();

    boolean hasNeverLoggedIn();

    boolean isEnoughExtCardServiceInfo();

    boolean isFelicaLock();

    boolean isScreenLock(Context context);
}
