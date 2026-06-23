package com.felicanetworks.mfm;

import android.content.Context;
import android.content.Intent;
import androidx.core.app.JobIntentService;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.presenter.internal.notification.AccountSwitchingNotificationController;

/* JADX INFO: loaded from: classes.dex */
public class MficStatusChangeReceiverIntentService extends JobIntentService {
    private static final int JOB_ID = 1;

    public static void startAccountSwitchingNotificationService(Context context) {
        enqueueWork(context, (Class<?>) MficStatusChangeReceiverIntentService.class, 1, new Intent(context, (Class<?>) MficStatusChangeReceiverIntentService.class));
    }

    @Override // androidx.core.app.JobIntentService
    protected void onHandleWork(Intent intent) {
        try {
            executeAccountSwitchingNotification();
        } catch (Exception unused) {
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
