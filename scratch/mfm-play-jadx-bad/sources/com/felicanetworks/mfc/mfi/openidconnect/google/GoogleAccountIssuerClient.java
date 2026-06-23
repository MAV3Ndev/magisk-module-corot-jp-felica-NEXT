package com.felicanetworks.mfc.mfi.openidconnect.google;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.LogSender;
import com.felicanetworks.mfc.mfi.PreAccountCache;
import com.felicanetworks.mfc.mfi.R;
import com.felicanetworks.mfc.mfi.openidconnect.IAccountIssuerClient;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectConst;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectSharedPreferences;
import com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes3.dex */
public class GoogleAccountIssuerClient implements IAccountIssuerClient {
    static final int MSG_ARGS_ERROR = -1;
    static final int MSG_ARGS_NG = 1;
    static final int MSG_ARGS_OK = 0;
    static final String MSG_DATA_STATUS_CODE = "status_code";
    static final String MSG_DATA_STATUS_MSG = "status_message";
    static final int MSG_WHAT_RET_CONFIRM = 131072;
    static final int MSG_WHAT_RET_SIGN_IN = 65536;
    private final Context mActivityContext;
    private final Context mContext;
    private final ActivityResultLauncher<IntentSenderRequest> mLauncher;
    private final boolean mShouldSkipAgreementPage;
    private String mAccountName = null;
    private boolean mForce = false;
    private boolean mCleared = false;
    private Process mProcess = Process.NONE;
    private IAccountIssuerClient.OnGetAuthCodeListener mOnGetAuthCodeListener = null;
    protected final ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private final String mServerClientId = FelicaAdapter.getInstance().getString(R.string.openidconnect_server_client_id);
    private GoogleApiWrapper mGoogleApi = new GoogleApiWrapper();
    private boolean mIsSentAALog = false;

    private enum Process {
        NONE,
        CLEAR_CREDENTIAL,
        AUTHORIZE,
        GET_CREDENTIAL
    }

    public GoogleAccountIssuerClient(Context context, Context activityContext, boolean shouldSkipAgreementPage, ActivityResultLauncher<IntentSenderRequest> launcher) {
        this.mContext = context;
        this.mShouldSkipAgreementPage = shouldSkipAgreementPage;
        this.mActivityContext = activityContext;
        this.mLauncher = launcher;
    }

    @Override // com.felicanetworks.mfc.mfi.openidconnect.IAccountIssuerClient
    public void requestAuthCode(String accountName, boolean force, IAccountIssuerClient.OnGetAuthCodeListener listener) throws Throwable {
        LogMgr.log(4, "%s", "000");
        if (this.mProcess != Process.NONE) {
            LogMgr.log(2, "%s do %s.", "700", this.mProcess);
            listener.onError(getIssuerName(), getAccountName(), 1, ObfuscatedMsgUtil.executionPoint());
        } else {
            if (this.mOnGetAuthCodeListener != null) {
                LogMgr.log(2, "%s it have requested already.", "701");
                listener.onError(getIssuerName(), getAccountName(), 1, ObfuscatedMsgUtil.executionPoint());
                return;
            }
            this.mAccountName = accountName;
            this.mForce = force;
            this.mOnGetAuthCodeListener = listener;
            confirm();
            LogMgr.log(4, "%s", "999");
        }
    }

    @Override // com.felicanetworks.mfc.mfi.openidconnect.IAccountIssuerClient
    public String getIssuerName() {
        return OpenIdConnectConst.ACCOUNT_ISSUER_GOOGLE;
    }

    private String getAccountName() {
        return this.mAccountName;
    }

    private void confirm() throws Throwable {
        LogMgr.log(6, "%s", "000");
        try {
            doConfirm();
        } catch (Exception e) {
            LogMgr.log(2, "%s %s:%S", "701", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
            onError(1, ObfuscatedMsgUtil.exExecutionPoint(e));
        }
        LogMgr.log(6, "%s", "999");
    }

    private void doConfirm() throws Throwable {
        LogMgr.log(6, "000");
        if (OpenIdConnectSharedPreferences.isAgreeCooperateAccountV2(this.mContext) || this.mShouldSkipAgreementPage) {
            LogMgr.log(7, "001");
            getAuthCode();
        } else {
            LogMgr.log(7, "002");
            this.mProcess = Process.NONE;
            onError(4, null);
        }
        LogMgr.log(6, "999");
    }

    private void getAuthCode() throws Throwable {
        LogMgr.log(6, "%s", "000");
        try {
            doGetAuthCode();
        } catch (Exception e) {
            LogMgr.log(2, "%s %s:%S", "701", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
            onError(1, ObfuscatedMsgUtil.exExecutionPoint(e));
        }
        LogMgr.log(6, "%s", "999");
    }

    private void doGetAuthCode() throws Throwable {
        LogMgr.log(6, "000");
        this.mCleared = false;
        if (this.mAccountName == null) {
            LogMgr.log(7, "001");
            this.mProcess = Process.CLEAR_CREDENTIAL;
            clearCredential();
        } else {
            LogMgr.log(7, "002");
            this.mProcess = Process.AUTHORIZE;
            authorizeAccount();
        }
        LogMgr.log(6, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onGetAuthCode(String authCode, String name) {
        LogMgr.log(6, "000");
        this.mProcess = Process.NONE;
        IAccountIssuerClient.OnGetAuthCodeListener onGetAuthCodeListener = this.mOnGetAuthCodeListener;
        if (onGetAuthCodeListener != null) {
            onGetAuthCodeListener.onGetAuthCode(authCode, OpenIdConnectConst.ACCOUNT_ISSUER_GOOGLE, name);
            this.mOnGetAuthCodeListener = null;
        }
        LogMgr.log(6, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onShowingAuthorizeScreen(String name) {
        LogMgr.log(6, "000");
        this.mProcess = Process.NONE;
        IAccountIssuerClient.OnGetAuthCodeListener onGetAuthCodeListener = this.mOnGetAuthCodeListener;
        if (onGetAuthCodeListener != null) {
            onGetAuthCodeListener.onShowingAuthorizeScreen(name);
            this.mOnGetAuthCodeListener = null;
        }
        LogMgr.log(6, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onError(int type, String msg) {
        LogMgr.log(6, "000");
        this.mProcess = Process.NONE;
        IAccountIssuerClient.OnGetAuthCodeListener onGetAuthCodeListener = this.mOnGetAuthCodeListener;
        if (onGetAuthCodeListener != null) {
            onGetAuthCodeListener.onError(getIssuerName(), getAccountName(), type, msg);
            this.mOnGetAuthCodeListener = null;
        }
        LogMgr.log(6, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearCredential() {
        LogMgr.log(6, "000");
        this.mGoogleApi.clearCredential(this.mContext, this.mExecutor, new GoogleApiWrapper.OnClearCredentialListener() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.GoogleAccountIssuerClient.1
            @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnClearCredentialListener
            public void onSuccess() {
                LogMgr.log(5, "000");
                GoogleAccountIssuerClient.this.mCleared = true;
                if (Process.CLEAR_CREDENTIAL != GoogleAccountIssuerClient.this.mProcess) {
                    GoogleAccountIssuerClient.this.onError(1, ObfuscatedMsgUtil.executionPoint());
                } else {
                    GoogleAccountIssuerClient.this.mProcess = Process.GET_CREDENTIAL;
                    GoogleAccountIssuerClient.this.getCredential();
                }
                LogMgr.log(5, "999");
            }

            @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnClearCredentialListener
            public void onError(int type, String msg) {
                LogMgr.log(5, "000");
                GoogleAccountIssuerClient.this.onError(type, msg);
                LogMgr.log(5, "999");
            }
        });
        LogMgr.log(6, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCredential() {
        LogMgr.log(6, "000");
        sendAALog();
        this.mGoogleApi.getCredential(this.mActivityContext, this.mExecutor, new GoogleApiWrapper.OnGetCredentialListener() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.GoogleAccountIssuerClient.2
            @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnGetCredentialListener
            public void onSuccess(String email) throws Throwable {
                LogMgr.log(5, "000");
                GoogleAccountIssuerClient.this.mAccountName = email;
                GoogleAccountIssuerClient.this.mProcess = Process.AUTHORIZE;
                GoogleAccountIssuerClient.this.authorizeAccount();
                LogMgr.log(5, "999");
            }

            @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnGetCredentialListener
            public void onError(int type, String msg) {
                LogMgr.log(5, "000");
                GoogleAccountIssuerClient.this.onError(type, msg);
                LogMgr.log(5, "999");
            }
        }, this.mServerClientId);
        LogMgr.log(6, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void authorizeAccount() throws Throwable {
        LogMgr.log(6, "000");
        this.mGoogleApi.authorizeAccount(this.mContext, new GoogleApiWrapper.OnAuthorizeListener() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.GoogleAccountIssuerClient.3
            @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnAuthorizeListener
            public void onSuccess(String authCode, String accountName) {
                LogMgr.log(5, "000 authCode = " + authCode);
                GoogleAccountIssuerClient.this.onGetAuthCode(authCode, accountName);
                LogMgr.log(5, "999");
            }

            @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnAuthorizeListener
            public void onNeedShowingAuthorizeScreen(PendingIntent pendingIntent, String accountName) {
                LogMgr.log(5, "000");
                if (GoogleAccountIssuerClient.this.mActivityContext != null) {
                    if (GoogleAccountIssuerClient.this.mLauncher != null) {
                        LogMgr.log(6, "001");
                        GoogleAccountIssuerClient.this.sendAALog();
                        IntentSenderRequest intentSenderRequestBuild = new IntentSenderRequest.Builder(pendingIntent).build();
                        GoogleAccountIssuerClient.this.onShowingAuthorizeScreen(accountName);
                        LogMgr.performanceIn("UI", "GoogleAccountIssuerClient", "authorize");
                        GoogleAccountIssuerClient.this.mLauncher.launch(intentSenderRequestBuild);
                        LogMgr.performanceOut("UI", "GoogleAccountIssuerClient", "authorize");
                    } else {
                        LogMgr.log(6, "002");
                        GoogleAccountIssuerClient.this.onError(1, ObfuscatedMsgUtil.executionPoint());
                    }
                } else {
                    LogMgr.log(6, "003");
                    GoogleAccountIssuerClient.this.onError(7, null);
                }
                LogMgr.log(5, "999");
            }

            @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnAuthorizeListener
            public void onFailed(int statusCode, String statusMessage) {
                LogMgr.log(5, "000");
                if (!GoogleAccountIssuerClient.this.mForce && !GoogleAccountIssuerClient.this.mCleared) {
                    LogMgr.log(6, "001 mForce = " + GoogleAccountIssuerClient.this.mForce + ", mCleared = " + GoogleAccountIssuerClient.this.mCleared);
                    if (GoogleAccountIssuerClient.this.mActivityContext != null) {
                        LogMgr.log(6, "002");
                        GoogleAccountIssuerClient.this.mAccountName = null;
                        GoogleAccountIssuerClient.this.mCleared = true;
                        GoogleAccountIssuerClient.this.mProcess = Process.CLEAR_CREDENTIAL;
                        GoogleAccountIssuerClient.this.clearCredential();
                        LogMgr.log(6, "997");
                        return;
                    }
                    LogMgr.log(6, "003");
                    GoogleAccountIssuerClient.this.onError(7, null);
                    LogMgr.log(6, "998");
                    return;
                }
                GoogleAccountIssuerClient googleAccountIssuerClient = GoogleAccountIssuerClient.this;
                googleAccountIssuerClient.onError(2, googleAccountIssuerClient.createErrMsg(statusCode, statusMessage));
                LogMgr.log(5, "999");
            }

            @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnAuthorizeListener
            public void onError(int type, String msg) {
                LogMgr.log(5, "000");
                GoogleAccountIssuerClient.this.onError(type, msg);
                LogMgr.log(5, "999");
            }
        }, this.mAccountName, this.mServerClientId);
        LogMgr.log(6, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAALog() {
        LogMgr.log(6, "000");
        try {
            if (!this.mIsSentAALog) {
                LogMgr.log(7, "001");
                int accountCodeCache = PreAccountCache.getInstance().getAccountCodeCache();
                Bundle bundle = new Bundle();
                bundle.putString("code", String.valueOf(accountCodeCache));
                bundle.putString(LogSender.EXTRA_KEY_MFIC_APP_VER, FelicaAdapter.getInstance().getString(R.string.mfi_client_version));
                LogSender.send(this.mContext, LogSender.EXTRA_VALUE_EVENT_NAME_SHOW_SIGN_IN, LogSender.EXTRA_VALUE_LOG_TYPE_SCREEN, bundle);
            }
        } catch (Exception e) {
            LogMgr.log(2, "800 : " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        this.mIsSentAALog = true;
        LogMgr.log(6, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String createErrMsg(int statusCode, String statusMessage) {
        return String.format(OpenIdConnectConst.ERROR_MSG_FMT_INVALID_STATUS_CODE, Integer.valueOf(statusCode), statusMessage);
    }
}
