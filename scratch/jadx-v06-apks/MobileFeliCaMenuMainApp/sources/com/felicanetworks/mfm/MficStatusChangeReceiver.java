package com.felicanetworks.mfm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.presenter.internal.ServicePreference;

/* JADX INFO: loaded from: classes.dex */
public class MficStatusChangeReceiver extends BroadcastReceiver {
    private static final String EVENT_NAME_ACCOUNT_CHANGE = "MFIC_EVENT_ACCOUNT_CHANGE";
    private static final String EXTRA_KEY_EVENT_NAME = "eventName";
    private static final String EXTRA_KEY_IS_START = "isStart";
    private static final String EXTRA_KEY_IS_SUCCESSFUL = "isSuccessful";
    private static boolean update = false;
    private int receivingTimes;

    public static synchronized boolean isUpdate() {
        return update;
    }

    static synchronized void setUpdate() {
        update = true;
    }

    public static synchronized void resetUpdate() {
        update = false;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        try {
            AnalysisManager.stampReceive(context);
            setUpdate();
            if (isAccountSwitching(intent)) {
                this.receivingTimes = ServicePreference.getInstance().loadAccountChangeHistoryReceiveCount(context);
                ServicePreference.getInstance().saveAccountChangeHistoryReceiveCount(context, this.receivingTimes + 1);
                AnalysisManager.stamp(MfmStamp.Event.AUTO_DUMP_RECEIVE_AC, Integer.valueOf(this.receivingTimes + 1));
                if (this.receivingTimes == 0) {
                    return;
                }
                if (ServicePreference.getInstance().loadAccountChangeHistoryNotificationSetting(context)) {
                    MficStatusChangeReceiverIntentService.startAccountSwitchingNotificationService(context);
                }
                ServicePreference.getInstance().saveAccountChangeHistoryList(context, "02");
            }
        } catch (Exception unused) {
        }
    }

    public static void sendMficStatusChangeByMySelf(Context context) {
        try {
            context.sendBroadcast(new Intent(context, (Class<?>) MficStatusChangeReceiver.class));
        } catch (Exception unused) {
        }
    }

    private boolean isAccountSwitching(Intent intent) {
        try {
            String string = "";
            boolean z = false;
            boolean z2 = false;
            for (String str : intent.getExtras().keySet()) {
                if (EXTRA_KEY_EVENT_NAME.equals(str)) {
                    string = intent.getExtras().getString(str);
                }
                if (EXTRA_KEY_IS_START.equals(str)) {
                    z = intent.getExtras().getBoolean(str);
                }
                if (EXTRA_KEY_IS_SUCCESSFUL.equals(str)) {
                    z2 = intent.getExtras().getBoolean(str);
                }
            }
            return string.equals(EVENT_NAME_ACCOUNT_CHANGE) && !z && z2;
        } catch (Exception unused) {
            return false;
        }
    }
}
