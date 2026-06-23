package com.felicanetworks.tis.ui;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
public class CardDialogIntentCreator {
    public static Intent createStartIntent(Context context, int i, String str, int i2, int i3) {
        Intent intent = new Intent(context, (Class<?>) CardDialogActivity.class);
        intent.putExtra("tis_pattern", i);
        intent.putExtra("tis_service", str);
        intent.putExtra("tis_amount", i2);
        intent.putExtra("tis_balance", i3);
        intent.putExtra("tis_ShowOnLock", true);
        intent.addFlags(268435456);
        intent.addFlags(65536);
        intent.addFlags(67108864);
        return intent;
    }

    public static PendingIntent createContextIntent(Context context, Intent intent) {
        intent.putExtra("tis_ShowOnLock", false);
        return PendingIntent.getActivity(context, 0, intent, 134217728);
    }

    public static void setExtraUuid(Intent intent, String str) {
        if (intent != null) {
            intent.putExtra("tis_log_uuid", str);
        }
    }
}
