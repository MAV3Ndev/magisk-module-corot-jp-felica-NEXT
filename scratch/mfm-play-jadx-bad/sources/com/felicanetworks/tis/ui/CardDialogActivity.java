package com.felicanetworks.tis.ui;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import com.felicanetworks.tis.util.LogMgr;
import java.lang.Thread;

/* JADX INFO: loaded from: classes3.dex */
public class CardDialogActivity extends FragmentActivity {
    private static final int NOTIFICATION_ID = 100;
    private Activity mActivity;
    private CardDialogFragment mFragment;
    private Thread mThread = new Thread() { // from class: com.felicanetworks.tis.ui.CardDialogActivity.1
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException unused) {
                LogMgr.log(2, "700");
            }
            LogMgr.log(6, "001 close card dialog");
            if (CardDialogActivity.this.mActivity.isFinishing()) {
                return;
            }
            CardDialogActivity.this.mActivity.finish();
        }
    };

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LogMgr.log(6, "000");
        this.mActivity = this;
        this.mFragment = new CardDialogFragment();
        init(getIntent());
        LogMgr.log(6, "999");
    }

    private void init(Intent intent) {
        LogMgr.log(6, "000");
        if (intent != null && intent.getIntExtra("tis_pattern", 0) != 0) {
            int intExtra = intent.getIntExtra("tis_pattern", 0);
            String stringExtra = intent.getStringExtra("tis_service");
            int intExtra2 = intent.getIntExtra("tis_amount", 0);
            int intExtra3 = intent.getIntExtra("tis_balance", 0);
            String stringExtra2 = intent.getStringExtra("tis_log_uuid");
            String stringExtra3 = intent.getStringExtra("tis_cid");
            int intExtra4 = intent.getIntExtra("tis_title_resource_id", 0);
            int intExtra5 = intent.getIntExtra("tis_card_image_resource_id", 0);
            boolean booleanExtra = intent.getBooleanExtra("tis_traffic_service", false);
            boolean booleanExtra2 = intent.getBooleanExtra("tis_ShowOnLock", false);
            LogMgr.log(6, "001 pattern = " + intExtra + ", serviceId = " + stringExtra + ", amount = " + intExtra2 + ", balance = " + intExtra3 + ", uuid = " + stringExtra2 + ", cid = " + stringExtra3 + ", titleResId = " + intExtra4 + ", cardImageResId = " + intExtra5 + ", isTrafficService = " + booleanExtra + ", isShowOnLock = " + booleanExtra2);
            LogMgr.log(6, "002 create card dialog");
            this.mFragment.setupDialog(this.mActivity, intExtra, stringExtra, intExtra2, intExtra3, stringExtra2, stringExtra3, intExtra4, intExtra5, booleanExtra);
            if (booleanExtra2) {
                LogMgr.log(6, "004 show card dialog on Lock screen");
                setShowWhenLocked(true);
                setTurnScreenOn(true);
                if (!this.mFragment.isAdded()) {
                    this.mFragment.show(getSupportFragmentManager(), "show_on_lock");
                }
                if (this.mThread.getState() == Thread.State.NEW) {
                    LogMgr.log(6, "005 CardDialog will be closed after 5000 msec");
                    this.mThread.start();
                    return;
                }
                return;
            }
            LogMgr.log(6, "006 tapped notification");
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService("keyguard");
            if (keyguardManager != null && !keyguardManager.isKeyguardLocked()) {
                setShowWhenLocked(false);
                setTurnScreenOn(true);
                NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
                if (notificationManager != null) {
                    LogMgr.log(6, "007 cancel notification");
                    notificationManager.cancel(100);
                }
                LogMgr.log(6, "008 show card dialog");
                if (this.mFragment.isAdded()) {
                    return;
                }
                this.mFragment.show(getSupportFragmentManager(), "show_on_lock");
                return;
            }
            if (this.mFragment.isAdded()) {
                return;
            }
            finish();
            return;
        }
        LogMgr.log(2, "700 Undefined card dialog pattern");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        LogMgr.log(6, "000");
        super.onResume();
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService("keyguard");
        if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
            getWindow().addFlags(1048576);
        } else {
            getWindow().clearFlags(1048576);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        LogMgr.log(6, "000");
        super.onDestroy();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        LogMgr.log(6, "000");
        super.onNewIntent(intent);
        finish();
        startActivity(intent);
    }
}
