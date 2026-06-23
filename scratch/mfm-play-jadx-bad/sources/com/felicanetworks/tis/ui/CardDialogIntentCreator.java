package com.felicanetworks.tis.ui;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.view.accessibility.AccessibilityEventCompat;

/* JADX INFO: loaded from: classes3.dex */
public class CardDialogIntentCreator {
    public static Intent createStartIntent(Context context, int i, String str, int i2, int i3, int i4, int i5, boolean z, String str2) {
        Intent intent = new Intent(context, (Class<?>) CardDialogActivity.class);
        intent.setAction("android.intent.action.MAIN");
        intent.putExtra("tis_pattern", i);
        intent.putExtra("tis_service", str);
        intent.putExtra("tis_amount", i2);
        intent.putExtra("tis_balance", i3);
        intent.putExtra("tis_traffic_service", z);
        intent.putExtra("tis_title_resource_id", i4);
        intent.putExtra("tis_card_image_resource_id", i5);
        intent.putExtra("tis_cid", str2);
        intent.putExtra("tis_ShowOnLock", true);
        intent.addFlags(268435456);
        intent.addFlags(65536);
        intent.addFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        return intent;
    }

    public static PendingIntent createContextIntent(Context context, Intent intent) {
        intent.putExtra("tis_ShowOnLock", false);
        return PendingIntent.getActivity(context, 0, intent, 201326592);
    }

    public static void setExtraUuid(Intent intent, String str) {
        if (intent != null) {
            intent.putExtra("tis_log_uuid", str);
        }
    }
}
