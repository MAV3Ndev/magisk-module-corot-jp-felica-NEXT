package com.felicanetworks.mfc.mfi.openidconnect.google;

import android.app.PendingIntent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import com.felicanetworks.mfc.mfi.PreAccountCache;
import com.felicanetworks.mfc.mfi.R;
import com.felicanetworks.mfc.mfi.openidconnect.IAccountIssuerClient;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectConst;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectFragment;
import com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.EventListener;

/* JADX INFO: loaded from: classes3.dex */
public class GoogleSignInFragment extends OpenIdConnectFragment {
    public static final String KEY_SHOULD_SKIP_AGREEMENT_PAGE = "shouldSkipAgreementPage";
    private ActivityResultLauncher<IntentSenderRequest> mLauncher;
    private boolean mStarted = false;
    OpenIdConnectActivity.OnSignInListener mListener = null;
    private GoogleApiWrapper mGoogleApi = new GoogleApiWrapper();
    private String mEmail = null;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mfi_openidconnect_activity_google_signin_fragment, container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        LogMgr.log(4, "000");
        super.onStart();
        LogMgr.log(4, "999");
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        LogMgr.log(4, "000");
        super.onResume();
        if (!this.mStarted) {
            this.mStarted = true;
            silentSignIn();
        }
        LogMgr.log(4, "999");
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        LogMgr.log(4, "000");
        super.onPause();
        LogMgr.log(4, "999");
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        LogMgr.log(4, "000");
        super.onStop();
        LogMgr.log(4, "999");
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        LogMgr.log(4, "000");
        super.onDestroy();
        LogMgr.log(4, "999");
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        LogMgr.log(4, "000");
        super.onConfigurationChanged(newConfig);
        LogMgr.log(4, "999");
    }

    @Override // com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectFragment
    public void setListener(EventListener listener) {
        this.mListener = (OpenIdConnectActivity.OnSignInListener) listener;
    }

    @Override // com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectFragment
    public void setActivityResultLauncher(ActivityResultLauncher<IntentSenderRequest> launcher) {
        this.mLauncher = launcher;
    }

    @Override // com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectFragment
    public void onBackPressed() {
        this.mListener.onError(OpenIdConnectConst.ACCOUNT_ISSUER_GOOGLE, 1, ObfuscatedMsgUtil.executionPoint());
    }

    public void handleSignInResult(ActivityResult result) throws Throwable {
        LogMgr.log(5, "000");
        this.mGoogleApi.authorizeFromIntent(result, getActivity(), new GoogleApiWrapper.OnAuthorizeListener() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.GoogleSignInFragment.1
            @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnAuthorizeListener
            public void onSuccess(String authCode, String accountName) {
                LogMgr.log(5, "000");
                GoogleSignInFragment.this.mListener.onSignInSuccess(authCode, OpenIdConnectConst.ACCOUNT_ISSUER_GOOGLE, accountName);
                LogMgr.log(5, "999");
            }

            @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnAuthorizeListener
            public void onNeedShowingAuthorizeScreen(PendingIntent pendingIntent, String accountName) {
                LogMgr.log(5, "000");
                GoogleSignInFragment.this.mListener.onError(OpenIdConnectConst.ACCOUNT_ISSUER_GOOGLE, 1, ObfuscatedMsgUtil.executionPoint());
                LogMgr.log(5, "999");
            }

            @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnAuthorizeListener
            public void onFailed(int statusCode, String statusMessage) {
                LogMgr.log(5, "000");
                GoogleSignInFragment.this.mListener.onError(OpenIdConnectConst.ACCOUNT_ISSUER_GOOGLE, 2, GoogleSignInFragment.this.createErrMsg(statusCode, statusMessage));
                LogMgr.log(5, "999");
            }

            @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnAuthorizeListener
            public void onError(int type, String msg) {
                LogMgr.log(5, "000");
                GoogleSignInFragment.this.mListener.onError(OpenIdConnectConst.ACCOUNT_ISSUER_GOOGLE, type, msg);
                LogMgr.log(5, "999");
            }
        }, this.mEmail);
        LogMgr.log(5, "999");
    }

    private void silentSignIn() {
        LogMgr.log(6, "000");
        PreAccountCache preAccountCache = PreAccountCache.getInstance();
        String accountNameCache = preAccountCache.getAccountNameCache();
        String accountIssuerCache = preAccountCache.getAccountIssuerCache();
        boolean accountForce = preAccountCache.getAccountForce();
        Bundle arguments = getArguments();
        boolean z = false;
        if (arguments != null) {
            z = arguments.getBoolean(KEY_SHOULD_SKIP_AGREEMENT_PAGE, false);
        } else {
            LogMgr.log(2, "700 Arguments is null.");
        }
        LogMgr.log(6, "001 shouldSkipAgreementPage=" + z);
        if ((accountIssuerCache == null && accountNameCache == null) || OpenIdConnectConst.ACCOUNT_ISSUER_GOOGLE.equals(accountIssuerCache)) {
            GoogleAccountIssuerClient googleAccountIssuerClient = new GoogleAccountIssuerClient(getContext(), getActivity(), z, this.mLauncher);
            googleAccountIssuerClient.requestAuthCode(accountNameCache, accountForce, new IAccountIssuerClient.OnGetAuthCodeListener() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.GoogleSignInFragment.2
                @Override // com.felicanetworks.mfc.mfi.openidconnect.IAccountIssuerClient.OnGetAuthCodeListener
                public void onGetAuthCode(String authCode, String issuer, String name) {
                    LogMgr.log(5, "000");
                    GoogleSignInFragment.this.mListener.onSignInSuccess(authCode, issuer, name);
                    LogMgr.log(5, "999");
                }

                @Override // com.felicanetworks.mfc.mfi.openidconnect.IAccountIssuerClient.OnGetAuthCodeListener
                public void onShowingAuthorizeScreen(String name) {
                    LogMgr.log(5, "999");
                    GoogleSignInFragment.this.mEmail = name;
                    LogMgr.log(5, "999");
                }

                @Override // com.felicanetworks.mfc.mfi.openidconnect.IAccountIssuerClient.OnGetAuthCodeListener
                public void onError(String issuer, String account, int type, String msg) {
                    LogMgr.log(5, "000 issuer = " + issuer + " ,account = " + account + " ,type = " + type + " ,msg = " + msg);
                    GoogleSignInFragment.this.mListener.onError(issuer, type, msg);
                    LogMgr.log(5, "999");
                }
            });
            LogMgr.log(6, "999");
            return;
        }
        this.mListener.onError(accountIssuerCache, 1, ObfuscatedMsgUtil.executionPoint());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String createErrMsg(int statusCode, String statusMessage) {
        return String.format(OpenIdConnectConst.ERROR_MSG_FMT_INVALID_STATUS_CODE, Integer.valueOf(statusCode), statusMessage);
    }
}
