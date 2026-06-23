package com.felicanetworks.mfm.main.presenter.agent;

import android.os.Handler;
import android.os.Looper;

/* JADX INFO: loaded from: classes3.dex */
public class AgentUtil {
    public static void runMainThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    public static void runWorkerThread(Runnable runnable) {
        new Thread(runnable).start();
    }
}
