package com.felicanetworks.mfm.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes3.dex */
public class ReceiveNfcTagActivity extends Activity {
    private static long lastStartTimeMills;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - lastStartTimeMills > 3000) {
                startMenuApp(getIntent());
            }
            lastStartTimeMills = jCurrentTimeMillis;
        } catch (Exception unused) {
        } catch (Throwable th) {
            finish();
            super.onCreate(savedInstanceState);
            throw th;
        }
        finish();
        super.onCreate(savedInstanceState);
    }

    private void startMenuApp(Intent intent) {
        if (intent.getAction() == null || !intent.getAction().equals("android.nfc.action.TECH_DISCOVERED")) {
            return;
        }
        Intent intent2 = new Intent(this, (Class<?>) ServiceListActivity.class);
        intent2.setAction("android.nfc.action.TECH_DISCOVERED");
        intent2.setFlags(268468224);
        if (Build.VERSION.SDK_INT >= 33) {
            intent2.putExtra("android.nfc.extra.TAG", (Parcelable) intent.getParcelableExtra("android.nfc.extra.TAG", Parcelable.class));
        } else {
            intent2.putExtra("android.nfc.extra.TAG", intent.getParcelableExtra("android.nfc.extra.TAG"));
        }
        startActivity(intent2);
    }
}
