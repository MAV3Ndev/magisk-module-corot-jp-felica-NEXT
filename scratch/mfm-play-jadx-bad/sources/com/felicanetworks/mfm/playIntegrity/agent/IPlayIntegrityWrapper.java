package com.felicanetworks.mfm.playIntegrity.agent;

import android.content.Context;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.integrity.IntegrityTokenResponse;

/* JADX INFO: loaded from: classes3.dex */
interface IPlayIntegrityWrapper {

    public interface TokenListener {
        void onError(int i, String str);

        void onError(String str);

        void onSuccess(String str);
    }

    Task<IntegrityTokenResponse> createPlayIntegrityTask(Context context, String str);

    void getToken(Task<IntegrityTokenResponse> task, Context context, TokenListener tokenListener);
}
