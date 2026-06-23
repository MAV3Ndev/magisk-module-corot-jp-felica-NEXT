package com.felicanetworks.mfc.mfi.openidconnect.google;

import android.app.Activity;
import android.content.Context;
import androidx.fragment.app.FragmentActivity;
import com.felicanetworks.mfc.mfi.R;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
class GoogleApiWrapper {
    private GoogleApiClient mGoogleApiClient;

    interface OnSignInListener {
        void onError(int i, String str);

        void onFailed(int i, String str);

        void onSuccess(String str, String str2);
    }

    interface OnSignOutListener {
        void onError(int i, String str);

        void onFinish(Status status);
    }

    GoogleApiWrapper() {
    }

    boolean connect(Context context, String str, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        LogMgr.log(5, "%s", "000");
        LogMgr.log(7, "%s accountName=%s", "001", str);
        if (this.mGoogleApiClient != null) {
            disconnect();
        }
        GoogleApiClient googleApiClientBuild = new GoogleApiClient.Builder(context).addConnectionCallbacks(connectionCallbacks).addOnConnectionFailedListener(onConnectionFailedListener).addApi(Auth.GOOGLE_SIGN_IN_API, getGoogleSignInOptions(context, str)).build();
        this.mGoogleApiClient = googleApiClientBuild;
        boolean z = false;
        if (googleApiClientBuild.isConnected()) {
            z = true;
        } else if (!this.mGoogleApiClient.isConnecting()) {
            LogMgr.log(7, "002 call GoogleApiClient#connect");
            this.mGoogleApiClient.connect();
        }
        LogMgr.log(5, "%s", "999");
        return z;
    }

    void connect(Context context, String str, FragmentActivity fragmentActivity, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        LogMgr.log(5, "%s", "000");
        LogMgr.log(7, "%s accountName=%s", "100", str);
        if (this.mGoogleApiClient != null) {
            disconnect();
        }
        this.mGoogleApiClient = new GoogleApiClient.Builder(context).enableAutoManage(fragmentActivity, onConnectionFailedListener).addApi(Auth.GOOGLE_SIGN_IN_API, getGoogleSignInOptions(context, str)).build();
        LogMgr.log(5, "%s", "999");
    }

    private GoogleSignInOptions getGoogleSignInOptions(Context context, String str) {
        String string = context.getString(R.string.openidconnect_server_client_id);
        if (str != null) {
            return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestServerAuthCode(string).requestEmail().setAccountName(str).build();
        }
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestServerAuthCode(string).requestEmail().build();
    }

    void disconnect() {
        LogMgr.log(5, "%s", "000");
        GoogleApiClient googleApiClient = this.mGoogleApiClient;
        if (googleApiClient == null) {
            LogMgr.log(2, "%s GoogleApiClient is null.", "700");
            return;
        }
        if (googleApiClient.isConnected() || this.mGoogleApiClient.isConnecting()) {
            LogMgr.log(7, "002 call GoogleApiClient#disconnect");
            this.mGoogleApiClient.disconnect();
            this.mGoogleApiClient = null;
        }
        LogMgr.log(5, "%s", "999");
    }

    void signIn(Activity activity, int i) {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_UI, "GoogleApiWrapper", "signIn");
        LogMgr.log(5, "%s", "000");
        LogMgr.log(7, "001 call Auth$GoogleSignInApi#getSignInIntent");
        activity.startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(this.mGoogleApiClient), i);
        LogMgr.log(5, "%s", "999");
        LogMgr.performanceOut(LogMgr.PERFORMANCE_UI, "GoogleApiWrapper", "signIn");
    }

    void silentSignIn(final OnSignInListener onSignInListener) {
        LogMgr.log(5, "%s", "000");
        LogMgr.log(7, "001 call Auth$GoogleSignInApi#silentSignIn");
        OptionalPendingResult<GoogleSignInResult> optionalPendingResultSilentSignIn = Auth.GoogleSignInApi.silentSignIn(this.mGoogleApiClient);
        if (optionalPendingResultSilentSignIn.isDone()) {
            LogMgr.log(7, "%s is done", "100");
            onSignIn((GoogleSignInResult) optionalPendingResultSilentSignIn.get(), onSignInListener);
        } else {
            LogMgr.log(7, "%s is not done", "200");
            optionalPendingResultSilentSignIn.setResultCallback(new ResultCallback<GoogleSignInResult>() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.1
                @Override // com.google.android.gms.common.api.ResultCallback
                public void onResult(GoogleSignInResult googleSignInResult) {
                    LogMgr.log(4, "%s", "000");
                    GoogleApiWrapper.this.onSignIn(googleSignInResult, onSignInListener);
                    LogMgr.log(4, "%s", "999");
                }
            });
        }
        LogMgr.log(5, "%s", "999");
    }

    public void onSignIn(GoogleSignInResult googleSignInResult, OnSignInListener onSignInListener) {
        LogMgr.log(5, "%s", "000");
        try {
        } catch (Exception e) {
            LogMgr.log(2, "%s %s:%s", "700", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
            try {
                onSignInListener.onError(1, ObfuscatedMsgUtil.exExecutionPoint(e));
            } catch (Exception e2) {
                LogMgr.log(1, "%s %s:%s", "800", e2.getClass().getSimpleName(), e2.getMessage());
                LogMgr.printStackTrace(7, e2);
            }
        }
        if (googleSignInResult == null) {
            onSignInListener.onError(2, ObfuscatedMsgUtil.executionPoint());
            return;
        }
        Status status = googleSignInResult.getStatus();
        int statusCode = status.getStatusCode();
        LogMgr.log(3, "%s Sign-in Status=%d:%s", "001", Integer.valueOf(statusCode), status.getStatusMessage());
        if (statusCode == 0 || -1 == statusCode) {
            GoogleSignInAccount signInAccount = googleSignInResult.getSignInAccount();
            if (signInAccount != null) {
                String serverAuthCode = signInAccount.getServerAuthCode();
                String email = signInAccount.getEmail();
                onSignInListener.onSuccess(serverAuthCode, email);
                LogMgr.log(7, "%s authCode=%s", "100", serverAuthCode);
                LogMgr.log(7, "%s accountName=%s", "101", email);
            } else {
                onSignInListener.onFailed(statusCode, status.getStatusMessage());
                LogMgr.log(7, "%s", "200");
            }
        } else {
            onSignInListener.onFailed(statusCode, status.getStatusMessage());
        }
        LogMgr.log(5, "%s", "999");
    }

    void signOut(final OnSignOutListener onSignOutListener) {
        LogMgr.log(5, "%s", "000");
        LogMgr.log(7, "001 call Auth$GoogleSignInApi#signOut");
        Auth.GoogleSignInApi.signOut(this.mGoogleApiClient).setResultCallback(new ResultCallback<Status>() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.2
            @Override // com.google.android.gms.common.api.ResultCallback
            public void onResult(Status status) {
                LogMgr.log(4, "%s", "000");
                GoogleApiWrapper.this.onSignOut(status, onSignOutListener);
                LogMgr.log(4, "%s", "999");
            }
        });
        LogMgr.log(5, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSignOut(Status status, OnSignOutListener onSignOutListener) {
        try {
            LogMgr.log(3, "%s Sign-out Status=%d:%s", "001", Integer.valueOf(status.getStatusCode()), status.getStatusMessage());
            onSignOutListener.onFinish(status);
        } catch (Exception e) {
            LogMgr.log(2, "%s %s:%s", "700", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
            try {
                onSignOutListener.onError(1, ObfuscatedMsgUtil.exExecutionPoint(e));
            } catch (Exception e2) {
                LogMgr.log(1, "%s %s:%s", "800", e2.getClass().getSimpleName(), e2.getMessage());
                LogMgr.printStackTrace(7, e2);
            }
        }
    }
}
