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
import com.felicanetworks.mfc.mfi.LogSender;
import com.felicanetworks.mfc.mfi.PreAccountCache;
import com.felicanetworks.mfc.mfi.R;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectConst;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectService;
import com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/* JADX INFO: loaded from: classes.dex */
public class GoogleSignInActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final int REQ_CODE_SIGN_IN = 1;
    private GoogleApiWrapper mGoogleApi;
    private boolean mIsBindService = false;
    private boolean mStarted = false;
    private boolean mIsNotifiedResult = false;
    private Messenger mService = null;
    private Message mMessageWaitOnConnected = null;
    private ServiceConnection mConnection = new ServiceConnection() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.GoogleSignInActivity.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogMgr.log(4, "%s", "000");
            GoogleSignInActivity.this.mService = new Messenger(iBinder);
            if (GoogleSignInActivity.this.mMessageWaitOnConnected == null) {
                if (!GoogleSignInActivity.this.mStarted) {
                    GoogleSignInActivity.this.mStarted = true;
                    GoogleSignInActivity.this.signIn();
                }
            } else {
                GoogleSignInActivity googleSignInActivity = GoogleSignInActivity.this;
                googleSignInActivity.notify(googleSignInActivity.mMessageWaitOnConnected);
                GoogleSignInActivity.this.mMessageWaitOnConnected = null;
            }
            LogMgr.log(4, "%s", "999");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            LogMgr.log(4, "%s", "000");
            GoogleSignInActivity.this.mService = null;
            if (!GoogleSignInActivity.this.mIsNotifiedResult) {
                LogMgr.log(1, "%s Have not notified result yet.", "800");
            }
            LogMgr.log(4, "%s", "999");
        }
    };

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        LogMgr.log(4, "%s", "000");
        super.onCreate(bundle);
        LogMgr.log(6, "bindService OpenIdConnectService");
        bindService(new Intent(this, (Class<?>) OpenIdConnectService.class), this.mConnection, 1);
        this.mIsBindService = true;
        Bundle extras = getIntent().getExtras();
        String string = extras != null ? extras.getString(OpenIdConnectConst.MSG_DATA_ACCOUNT_NAME) : null;
        GoogleApiWrapper googleApiWrapper = new GoogleApiWrapper();
        this.mGoogleApi = googleApiWrapper;
        googleApiWrapper.connect(this, string, this, this);
        LogMgr.log(4, "%s", "999");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        LogMgr.log(4, "%s", "000");
        super.onStart();
        LogMgr.log(4, "%s", "999");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        LogMgr.log(4, "%s", "000");
        super.onResume();
        int accountCodeCache = PreAccountCache.getInstance().getAccountCodeCache();
        Bundle bundle = new Bundle();
        bundle.putString("code", String.valueOf(accountCodeCache));
        bundle.putString(LogSender.EXTRA_KEY_MFIC_APP_VER, getString(R.string.mfi_client_version));
        LogSender.send(this, LogSender.EXTRA_VALUE_EVENT_NAME_SHOW_SIGN_IN, LogSender.EXTRA_VALUE_LOG_TYPE_SCREEN, bundle);
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
            notifyError(1, ObfuscatedMsgUtil.executionPoint());
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

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        LogMgr.log(4, "%s RequestCode=%d, ResultCode=%d", "000", Integer.valueOf(i), Integer.valueOf(i2));
        if (i == 1) {
            handleSignInResult(intent);
        }
        LogMgr.log(4, "%s", "999");
    }

    @Override // android.app.Activity
    public void finish() {
        LogMgr.log(4, "%s", "000");
        finishAndRemoveTask();
        LogMgr.log(4, "%s", "999");
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
    public void onConnectionFailed(ConnectionResult connectionResult) {
        LogMgr.log(4, "%s", "000");
        int errorCode = connectionResult.getErrorCode();
        notifyError(GoogleAccountIssuerClient.LIB_UNAVAILABLE_CONNECTION_RESULT_LIST.contains(Integer.valueOf(errorCode)) ? 6 : 2, String.format(OpenIdConnectConst.ERROR_MSG_FMT_CONNECTION_FAILD, Integer.valueOf(errorCode), connectionResult.getErrorMessage()));
        LogMgr.log(4, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void signIn() {
        LogMgr.log(6, "%s", "000");
        this.mGoogleApi.signIn(this, 1);
        LogMgr.log(6, "%s", "999");
    }

    private void handleSignInResult(Intent intent) {
        LogMgr.log(6, "%s", "000");
        try {
            this.mGoogleApi.onSignIn(Auth.GoogleSignInApi.getSignInResultFromIntent(intent), new GoogleApiWrapper.OnSignInListener() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.GoogleSignInActivity.2
                @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnSignInListener
                public void onSuccess(String str, String str2) {
                    GoogleSignInActivity.this.notifySuccess(str, str2);
                }

                @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnSignInListener
                public void onFailed(int i, String str) {
                    GoogleSignInActivity.this.notifyFail(i, str);
                }

                @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnSignInListener
                public void onError(int i, String str) {
                    GoogleSignInActivity.this.notifyError(i, str);
                }
            });
            if (this.mIsBindService) {
                LogMgr.log(6, "unbindService OpenIdConnectService");
                unbindService(this.mConnection);
                this.mIsBindService = false;
            }
        } catch (Exception e) {
            LogMgr.log(2, "%s %s:%S", "70", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
            notifyError(1, ObfuscatedMsgUtil.exExecutionPoint(e));
        }
        LogMgr.log(6, "%s", "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifySuccess(String str, String str2) {
        LogMgr.log(6, "%s", "000");
        Message messageObtain = Message.obtain((Handler) null, 65536);
        messageObtain.arg1 = 0;
        Bundle bundle = new Bundle();
        bundle.putString(OpenIdConnectConst.MSG_DATA_AUTH_CODE, str);
        bundle.putString(OpenIdConnectConst.MSG_DATA_ACCOUNT_NAME, str2);
        messageObtain.setData(bundle);
        notify(messageObtain);
        LogMgr.log(6, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyFail(int i, String str) {
        LogMgr.log(6, "%s", "000");
        Message messageObtain = Message.obtain((Handler) null, 65536);
        messageObtain.arg1 = 1;
        Bundle bundle = new Bundle();
        bundle.putInt("status_code", i);
        bundle.putString("status_message", str);
        messageObtain.setData(bundle);
        notify(messageObtain);
        LogMgr.log(6, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyError(int i, String str) {
        LogMgr.log(6, "%s", "000");
        Message messageObtain = Message.obtain((Handler) null, 65536);
        messageObtain.arg1 = -1;
        Bundle bundle = new Bundle();
        bundle.putInt(OpenIdConnectConst.MSG_DATA_ERROR_TYPE, i);
        bundle.putString(OpenIdConnectConst.MSG_DATA_ERROR_MSG, str);
        messageObtain.setData(bundle);
        notify(messageObtain);
        LogMgr.log(6, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notify(Message message) {
        LogMgr.log(6, "%s", "000");
        if (this.mIsNotifiedResult) {
            LogMgr.log(1, "%s Already have notified.", "800");
            return;
        }
        Messenger messenger = this.mService;
        if (messenger == null) {
            if (this.mIsBindService) {
                LogMgr.log(2, "%s Wait onBind...", "700");
                this.mMessageWaitOnConnected = message;
                return;
            } else {
                LogMgr.log(1, "%s Service not found.", "801");
                return;
            }
        }
        try {
            messenger.send(message);
            this.mIsNotifiedResult = true;
        } catch (RemoteException e) {
            LogMgr.log(1, "%s RemoteException:%s", "802", e.getMessage());
            LogMgr.printStackTrace(7, e);
        } catch (Exception e2) {
            LogMgr.log(2, "%s $s:$s", "700", e2.getClass().getSimpleName(), e2.getMessage());
            LogMgr.printStackTrace(7, e2);
            try {
                Message messageObtain = Message.obtain((Handler) null, 65536);
                messageObtain.arg1 = -1;
                Bundle bundle = new Bundle();
                bundle.putInt(OpenIdConnectConst.MSG_DATA_ERROR_TYPE, 1);
                bundle.putString(OpenIdConnectConst.MSG_DATA_ERROR_MSG, ObfuscatedMsgUtil.exExecutionPoint(e2));
                message.setData(bundle);
                this.mService.send(messageObtain);
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
