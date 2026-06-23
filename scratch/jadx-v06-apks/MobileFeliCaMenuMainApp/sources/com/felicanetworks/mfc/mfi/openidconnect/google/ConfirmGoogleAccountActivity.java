package com.felicanetworks.mfc.mfi.openidconnect.google;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.felicanetworks.mfc.mfi.R;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectService;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class ConfirmGoogleAccountActivity extends FragmentActivity {
    private boolean mIsBindService = false;
    private boolean mIsNotifiedResult = false;
    private Messenger mService = null;
    private boolean mIsWaitOnConnectedToFinish = false;
    private boolean mWaitResult = false;
    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private ConfirmGoogleAccountFragment mFragment = new ConfirmGoogleAccountFragment();
    private OpenIdConnectActivity.OnConfirmListener mConfirmListener = new OpenIdConnectActivity.OnConfirmListener() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountActivity.1
        @Override // com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity.OnConfirmListener
        public void onConfirm(int i) {
            LogMgr.log(6, "000 confirm=" + i);
            if (i == 0) {
                ConfirmGoogleAccountActivity.this.notifyResult(true);
            } else if (i == 4) {
                ConfirmGoogleAccountActivity.this.notifyResult(false);
            } else {
                LogMgr.log(2, "700 Unexpected confirm value.");
                ConfirmGoogleAccountActivity.this.notifyResult(false);
            }
            LogMgr.log(6, "999");
        }
    };
    private ServiceConnection mConnection = new ServiceConnection() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountActivity.2
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogMgr.log(4, "%s", "000");
            ConfirmGoogleAccountActivity.this.mService = new Messenger(iBinder);
            if (!ConfirmGoogleAccountActivity.this.mIsWaitOnConnectedToFinish) {
                ConfirmGoogleAccountActivity.this.mFragment.enableAgreeCheckBox();
            } else {
                ConfirmGoogleAccountActivity confirmGoogleAccountActivity = ConfirmGoogleAccountActivity.this;
                confirmGoogleAccountActivity.notifyResult(confirmGoogleAccountActivity.mWaitResult);
                ConfirmGoogleAccountActivity.this.mIsWaitOnConnectedToFinish = false;
            }
            LogMgr.log(4, "%s", "999");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            LogMgr.log(4, "%s", "000");
            ConfirmGoogleAccountActivity.this.mService = null;
            ConfirmGoogleAccountActivity.this.mFragment.disableAgreeCheckBox();
            if (!ConfirmGoogleAccountActivity.this.mIsNotifiedResult) {
                LogMgr.log(1, "%s Have not notified result yet.", "800");
            }
            LogMgr.log(4, "%s", "999");
        }
    };

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        LogMgr.log(4, "%s", "000");
        super.onCreate(bundle);
        setContentView(R.layout.mfi_openidconnect_activity);
        FragmentTransaction fragmentTransactionBeginTransaction = this.mFragmentManager.beginTransaction();
        fragmentTransactionBeginTransaction.replace(R.id.openid_connect_container, this.mFragment);
        fragmentTransactionBeginTransaction.commit();
        this.mFragment.setListener(this.mConfirmListener);
        LogMgr.log(4, "%s", "999");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        LogMgr.log(4, "%s", "000");
        super.onStart();
        LogMgr.log(6, "bindService OpenIdConnectService");
        bindService(new Intent(this, (Class<?>) OpenIdConnectService.class), this.mConnection, 1);
        this.mIsBindService = true;
        LogMgr.log(4, "%s", "999");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        LogMgr.log(4, "%s", "000");
        super.onResume();
        if (this.mService == null) {
            this.mFragment.disableAgreeCheckBox();
        }
        LogMgr.log(4, "%s", "999");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        LogMgr.log(4, "%s", "000");
        super.onPause();
        LogMgr.log(4, "%s", "999");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        LogMgr.log(4, "%s", "000");
        super.onStop();
        LogMgr.log(4, "%s", "999");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        LogMgr.log(4, "%s", "000");
        super.onDestroy();
        if (!this.mIsNotifiedResult) {
            notifyResult(false);
        }
        if (this.mIsBindService) {
            LogMgr.log(6, "unbindService OpenIdConnectService");
            unbindService(this.mConnection);
            this.mIsBindService = false;
        }
        LogMgr.log(4, "%s", "999");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        LogMgr.log(4, "%s", "000");
        super.onConfigurationChanged(configuration);
        LogMgr.log(4, "%s", "999");
    }

    @Override // android.app.Activity
    public void finish() {
        LogMgr.log(4, "%s", "000");
        finishAndRemoveTask();
        LogMgr.log(4, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyResult(boolean z) {
        LogMgr.log(6, "%s", "000");
        if (this.mIsNotifiedResult) {
            LogMgr.log(1, "%s Already have notified.", "800");
            return;
        }
        if (this.mService == null) {
            if (this.mIsBindService) {
                LogMgr.log(2, "%s Wait onBind...", "700");
                this.mIsWaitOnConnectedToFinish = true;
                this.mWaitResult = z;
                return;
            }
            LogMgr.log(1, "%s Service not found.", "801");
            return;
        }
        try {
            Message messageObtain = Message.obtain((Handler) null, 131072);
            messageObtain.arg1 = z ? 0 : 1;
            this.mService.send(messageObtain);
            this.mIsNotifiedResult = true;
        } catch (RemoteException e) {
            LogMgr.log(1, "%s RemoteException:%s", "802", e.getMessage());
            LogMgr.printStackTrace(7, e);
        } catch (Exception e2) {
            LogMgr.log(2, "%s $s:$s", "701", e2.getClass().getSimpleName(), e2.getMessage());
            LogMgr.printStackTrace(7, e2);
            try {
                this.mService.send(Message.obtain((Handler) null, 65536));
                this.mIsNotifiedResult = true;
            } catch (Exception e3) {
                LogMgr.log(2, "%s $s:$s", "803", e3.getClass().getSimpleName(), e3.getMessage());
                LogMgr.printStackTrace(7, e3);
            }
        }
        finish();
        LogMgr.log(6, "%s", "999");
    }
}
