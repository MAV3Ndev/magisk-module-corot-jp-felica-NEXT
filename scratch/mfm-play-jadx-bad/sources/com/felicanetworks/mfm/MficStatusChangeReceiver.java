package com.felicanetworks.mfm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import com.felicanetworks.mfm.main.model.internal.main.UpdateCardInfoWorker;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.presenter.internal.ServicePreference;

/* JADX INFO: loaded from: classes3.dex */
public class MficStatusChangeReceiver extends BroadcastReceiver {
    private static final int CARD_ID_LENGTH = 63;
    private static final String EVENT_NAME_ACCOUNT_CHANGE = "MFIC_EVENT_ACCOUNT_CHANGE";
    private static final String EVENT_NAME_CARD_ISSUE = "MFIC_EVENT_CARD_ISSUE";
    private static final String EVENT_NAME_NEW_ACCOUNT_LOGIN = "MFIC_EVENT_NEW_ACCOUNT_LOGIN";
    private static final String EXTRA_KEY_CID = "cid";
    private static final String EXTRA_KEY_EVENT_NAME = "eventName";
    private static final String EXTRA_KEY_IS_START = "isStart";
    private static final String EXTRA_KEY_IS_SUCCESSFUL = "isSuccessful";
    private static final String EXTRA_KEY_PACKAGE_NAME = "callerPackageName";
    private static final String EXTRA_KEY_SERVICE_ID = "serviceId";
    private static final String[] PACKAGE_GOOGLE_WALLETS = {"com.google.android.gms", "com.google.android.apps.walletnfcrel", "com.google.android.gms.pay.sidecar"};
    private static boolean update = false;

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
            if (EVENT_NAME_CARD_ISSUE.equals(intent.getStringExtra(EXTRA_KEY_EVENT_NAME))) {
                startUpdateCardInfoWorker(context, intent);
            } else {
                accountChange(context, intent);
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

    private void accountChange(Context context, Intent intent) {
        if (context == null || intent == null) {
            return;
        }
        try {
            if (intent.getExtras() == null) {
                return;
            }
            String string = "";
            String string2 = "";
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
                if (EXTRA_KEY_PACKAGE_NAME.equals(str)) {
                    string2 = intent.getExtras().getString(str);
                }
            }
            if ((EVENT_NAME_ACCOUNT_CHANGE.equals(string) || EVENT_NAME_NEW_ACCOUNT_LOGIN.equals(string)) && !z && z2) {
                for (String str2 : PACKAGE_GOOGLE_WALLETS) {
                    if (str2.equals(string2)) {
                        if (ServicePreference.getInstance().loadAccountChangeHistoryNotificationSetting(context) && string.equals(EVENT_NAME_ACCOUNT_CHANGE)) {
                            MficStatusChangeReceiverWorker.startAccountSwitchingNotificationService(context);
                        }
                        ServicePreference.getInstance().saveAccountChangeHistoryList(context, "02");
                        return;
                    }
                }
                if ("com.felicanetworks.mfm.main".equals(string2)) {
                    ServicePreference.getInstance().saveAccountChangeHistoryList(context, "01");
                } else {
                    ServicePreference.getInstance().saveAccountChangeHistoryList(context, "03");
                }
            }
        } catch (Exception unused) {
        }
    }

    private void startUpdateCardInfoWorker(Context context, Intent intent) {
        if (context == null || intent == null) {
            return;
        }
        try {
            if (intent.getExtras() == null) {
                return;
            }
            String string = "";
            String string2 = "";
            String string3 = string2;
            boolean z = false;
            boolean z2 = false;
            for (String str : intent.getExtras().keySet()) {
                if (EXTRA_KEY_IS_START.equals(str)) {
                    z = intent.getExtras().getBoolean(str);
                }
                if (EXTRA_KEY_IS_SUCCESSFUL.equals(str)) {
                    z2 = intent.getExtras().getBoolean(str);
                }
                if (EXTRA_KEY_CID.equals(str)) {
                    string2 = intent.getExtras().getString(str);
                }
                if (EXTRA_KEY_PACKAGE_NAME.equals(str)) {
                    string3 = intent.getExtras().getString(str);
                }
                if ("serviceId".equals(str)) {
                    string = intent.getExtras().getString(str);
                }
            }
            if (string != null && !string.isEmpty()) {
                DataCheckerUtil.checkByteLength(string2, 63, true);
                DataCheckerUtil.checkAlphaNumberFormat(string2);
                if (z || !z2 || "com.felicanetworks.mfm.main".equals(string3)) {
                    return;
                }
                for (String str2 : (String[]) Sg.getValue(Sg.Key.SETTING_TARGET_SERVICE_SPECIAL_CARD_FACE)) {
                    if (str2.equals(string)) {
                        UpdateCardInfoWorker.requestWork(context, string2);
                        return;
                    }
                }
            }
        } catch (Exception unused) {
        }
    }
}
