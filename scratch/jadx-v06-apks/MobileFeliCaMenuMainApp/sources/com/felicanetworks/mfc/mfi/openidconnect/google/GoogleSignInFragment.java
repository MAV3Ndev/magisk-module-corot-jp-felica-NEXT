package com.felicanetworks.mfc.mfi.openidconnect.google;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentActivity;
import com.felicanetworks.mfc.mfi.LogSender;
import com.felicanetworks.mfc.mfi.PreAccountCache;
import com.felicanetworks.mfc.mfi.R;
import com.felicanetworks.mfc.mfi.openidconnect.IAccountIssuerClient;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectConst;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectFragment;
import com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.EventListener;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class GoogleSignInFragment extends OpenIdConnectFragment implements GoogleApiClient.OnConnectionFailedListener {
    public static final String KEY_SHOULD_SKIP_AGREEMENT_PAGE = "shouldSkipAgreementPage";
    private boolean mStarted = false;
    OpenIdConnectActivity.OnSignInListener mListener = null;
    private GoogleApiWrapper mGoogleApi = new GoogleApiWrapper();

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.mfi_openidconnect_activity_google_signin_fragment, viewGroup, false);
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
        this.mGoogleApi.disconnect();
        LogMgr.log(4, "999");
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        LogMgr.log(4, "000");
        super.onConfigurationChanged(configuration);
        LogMgr.log(4, "999");
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
    public void onConnectionFailed(ConnectionResult connectionResult) {
        LogMgr.log(4, "000");
        int errorCode = connectionResult.getErrorCode();
        this.mListener.onSignInFailed("https://accounts.google.com", GoogleAccountIssuerClient.LIB_UNAVAILABLE_CONNECTION_RESULT_LIST.contains(Integer.valueOf(errorCode)) ? 6 : 2, String.format(Locale.getDefault(), OpenIdConnectConst.ERROR_MSG_FMT_CONNECTION_FAILD, Integer.valueOf(errorCode), connectionResult.getErrorMessage()));
        LogMgr.log(4, "999");
    }

    @Override // com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectFragment
    public void setListener(EventListener eventListener) {
        this.mListener = (OpenIdConnectActivity.OnSignInListener) eventListener;
    }

    @Override // com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectFragment
    public void onBackPressed() {
        this.mListener.onError("https://accounts.google.com", 1, ObfuscatedMsgUtil.executionPoint());
    }

    public void handleSignInResult(Intent intent) {
        LogMgr.log(6, "000");
        try {
            this.mGoogleApi.onSignIn(Auth.GoogleSignInApi.getSignInResultFromIntent(intent), new GoogleApiWrapper.OnSignInListener() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.GoogleSignInFragment.1
                @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnSignInListener
                public void onSuccess(String str, String str2) {
                    GoogleSignInFragment.this.mListener.onSignInSuccess(str, "https://accounts.google.com", str2);
                }

                @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnSignInListener
                public void onFailed(int i, String str) {
                    GoogleSignInFragment.this.mListener.onSignInFailed("https://accounts.google.com", i, str);
                }

                @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnSignInListener
                public void onError(int i, String str) {
                    GoogleSignInFragment.this.mListener.onError("https://accounts.google.com", i, str);
                }
            });
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            this.mListener.onError("https://accounts.google.com", 1, ObfuscatedMsgUtil.exExecutionPoint(e));
        }
        LogMgr.log(6, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectAndSignIn(String str) {
        Context context = getContext();
        FragmentActivity activity = getActivity();
        if (context == null || activity == null) {
            return;
        }
        int accountCodeCache = PreAccountCache.getInstance().getAccountCodeCache();
        Bundle bundle = new Bundle();
        bundle.putString("code", String.valueOf(accountCodeCache));
        bundle.putString(LogSender.EXTRA_KEY_MFIC_APP_VER, getString(R.string.mfi_client_version));
        LogSender.send(context, LogSender.EXTRA_VALUE_EVENT_NAME_SHOW_SIGN_IN, LogSender.EXTRA_VALUE_LOG_TYPE_SCREEN, bundle);
        this.mGoogleApi.connect(context, str, activity, this);
        this.mGoogleApi.signIn(getActivity(), 1);
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
        if ((accountIssuerCache == null && accountNameCache == null) || "https://accounts.google.com".equals(accountIssuerCache)) {
            GoogleAccountIssuerClient googleAccountIssuerClient = new GoogleAccountIssuerClient(getContext(), true, z);
            googleAccountIssuerClient.requestAuthCode(accountNameCache, accountForce, new IAccountIssuerClient.OnGetAuthCodeListener() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.GoogleSignInFragment.2
                @Override // com.felicanetworks.mfc.mfi.openidconnect.IAccountIssuerClient.OnGetAuthCodeListener
                public void onGetAuthCode(String str, String str2, String str3) {
                    GoogleSignInFragment.this.mListener.onSignInSuccess(str, str2, str3);
                }

                @Override // com.felicanetworks.mfc.mfi.openidconnect.IAccountIssuerClient.OnGetAuthCodeListener
                public void onError(String str, String str2, int i, String str3) {
                    if (i == 7) {
                        GoogleSignInFragment.this.connectAndSignIn(str2);
                    } else {
                        GoogleSignInFragment.this.mListener.onError(str, i, str3);
                    }
                }
            });
            LogMgr.log(6, "999");
            return;
        }
        this.mListener.onError(accountIssuerCache, 1, ObfuscatedMsgUtil.executionPoint());
    }
}
