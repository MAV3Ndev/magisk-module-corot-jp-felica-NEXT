package com.felicanetworks.mfm;

import android.content.Context;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.presenter.internal.notification.AccountSwitchingNotificationController;

/* JADX INFO: loaded from: classes3.dex */
public class MficStatusChangeReceiverWorker extends Worker {
    public MficStatusChangeReceiverWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
    }

    public static void startAccountSwitchingNotificationService(Context context) {
        WorkManager.getInstance(context).enqueue(new OneTimeWorkRequest.Builder((Class<? extends ListenableWorker>) MficStatusChangeReceiverWorker.class).build());
    }

    @Override // androidx.work.Worker
    public ListenableWorker.Result doWork() {
        try {
            executeAccountSwitchingNotification();
            return ListenableWorker.Result.success();
        } catch (Exception unused) {
            return ListenableWorker.Result.failure();
        }
    }

    private void executeAccountSwitchingNotification() throws MfmException {
        try {
            AccountSwitchingNotificationController.getInstance(getApplicationContext()).notifyNotification();
        } catch (Exception e) {
            throw new MfmException(getClass(), 2, e);
        }
    }
}
