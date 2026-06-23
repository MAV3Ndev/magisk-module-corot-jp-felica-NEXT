package com.amazonaws.mobile.client;

import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import androidx.browser.customtabs.CustomTabsCallback;
import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsServiceConnection;
import androidx.browser.customtabs.CustomTabsSession;
import com.amazonaws.mobile.client.internal.oauth2.OAuth2Client;
import com.amazonaws.mobileconnectors.cognitoauth.util.Pkce;
import com.felicanetworks.semc.util.SharedPrefsProvider;
import com.google.firebase.messaging.Constants;
import java.util.Map;

/* JADX INFO: compiled from: AWSMobileClient.java */
/* JADX INFO: loaded from: classes.dex */
class OAuth2Utils {
    private CustomTabsCallback customTabsCallback = new CustomTabsCallback();
    private final Context mContext;
    private CustomTabsClient mCustomTabsClient;
    private CustomTabsServiceConnection mCustomTabsServiceConnection;
    private CustomTabsSession mCustomTabsSession;
    private String mError;
    private String mErrorDescription;
    private final Uri mSignInRedirectUri;
    private String mState;

    Uri exchangeCode(String str) {
        return null;
    }

    OAuth2Utils(Context context, Uri uri) {
        this.mContext = context;
        this.mSignInRedirectUri = uri;
    }

    void preWarm() {
        CustomTabsServiceConnection customTabsServiceConnection = new CustomTabsServiceConnection() { // from class: com.amazonaws.mobile.client.OAuth2Utils.1
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                OAuth2Utils.this.mCustomTabsClient = customTabsClient;
                OAuth2Utils.this.mCustomTabsClient.warmup(0L);
                OAuth2Utils oAuth2Utils = OAuth2Utils.this;
                oAuth2Utils.mCustomTabsSession = oAuth2Utils.mCustomTabsClient.newSession(OAuth2Utils.this.customTabsCallback);
            }

            public void onServiceDisconnected(ComponentName componentName) {
                OAuth2Utils.this.mCustomTabsClient = null;
            }
        };
        this.mCustomTabsServiceConnection = customTabsServiceConnection;
        CustomTabsClient.bindCustomTabsService(this.mContext, OAuth2Client.CUSTOM_TABS_PACKAGE_NAME, customTabsServiceConnection);
    }

    void authorize(String str, String str2, Map<String, String> map) {
        this.mState = Pkce.generateRandom();
        Uri.Builder builderBuildUpon = Uri.parse(str).buildUpon();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builderBuildUpon.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        if (!map.containsKey("code")) {
            builderBuildUpon.appendQueryParameter("response_type", "code");
        }
        if (!map.containsKey(SharedPrefsProvider.NotifyClientLogContents.Setting.CLIENT_ID)) {
            if (str2 != null) {
                builderBuildUpon.appendQueryParameter(SharedPrefsProvider.NotifyClientLogContents.Setting.CLIENT_ID, str2);
            } else {
                throw new IllegalArgumentException("Client id must be specified for an authorization request.");
            }
        }
        builderBuildUpon.appendQueryParameter("state", this.mState);
        navigate(builderBuildUpon.build());
    }

    void navigate(Uri uri) {
        CustomTabsIntent customTabsIntentBuild = new CustomTabsIntent.Builder(this.mCustomTabsSession).build();
        customTabsIntentBuild.intent.setPackage(OAuth2Client.CUSTOM_TABS_PACKAGE_NAME);
        customTabsIntentBuild.intent.addFlags(1073741824);
        customTabsIntentBuild.intent.addFlags(268435456);
        customTabsIntentBuild.launchUrl(this.mContext, uri);
    }

    boolean parse(Uri uri) {
        if (uri.getScheme().equals(this.mSignInRedirectUri.getScheme()) && uri.getAuthority().equals(this.mSignInRedirectUri.getAuthority()) && uri.getPath().equals(this.mSignInRedirectUri.getPath()) && uri.getQueryParameterNames().containsAll(this.mSignInRedirectUri.getQueryParameterNames())) {
            uri.getQueryParameter("code");
            if (!this.mState.equals(uri.getQueryParameter("state"))) {
                return false;
            }
            this.mError = uri.getQueryParameter(Constants.IPC_BUNDLE_KEY_SEND_ERROR);
            this.mErrorDescription = uri.getQueryParameter("error_description");
            if (this.mError != null) {
                return true;
            }
        }
        return false;
    }
}
