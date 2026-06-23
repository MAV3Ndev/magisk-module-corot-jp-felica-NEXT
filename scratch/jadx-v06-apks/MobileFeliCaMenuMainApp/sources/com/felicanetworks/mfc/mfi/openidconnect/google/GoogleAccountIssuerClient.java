package com.felicanetworks.mfc.mfi.openidconnect.google;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import com.felicanetworks.mfc.mfi.openidconnect.IAccountIssuerClient;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectConst;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectSharedPreferences;
import com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class GoogleAccountIssuerClient implements IAccountIssuerClient, GoogleApiClient.OnConnectionFailedListener {
    static final ArrayList<Integer> LIB_UNAVAILABLE_CONNECTION_RESULT_LIST;
    static final int MSG_ARGS_ERROR = -1;
    static final int MSG_ARGS_NG = 1;
    static final int MSG_ARGS_OK = 0;
    static final String MSG_DATA_STATUS_CODE = "status_code";
    static final String MSG_DATA_STATUS_MSG = "status_message";
    static final int MSG_WHAT_RET_CONFIRM = 131072;
    static final int MSG_WHAT_RET_SIGN_IN = 65536;
    private final Context mContext;
    private boolean mIsSilentStart;
    private final boolean mShouldSkipAgreementPage;
    private String mAccountName = null;
    private boolean mForce = false;
    private boolean mCleared = false;
    private Process mProcess = Process.NONE;
    private IAccountIssuerClient.OnGetAuthCodeListener mOnGetAuthCodeListener = null;
    private GoogleApiWrapper mGoogleApi = new GoogleApiWrapper();

    private enum Process {
        NONE,
        CONFIRM,
        CLEAR_DEFAULT,
        SILENT_SIGN_IN,
        SIGN_IN
    }

    @Override // com.felicanetworks.mfc.mfi.openidconnect.IAccountIssuerClient
    public String getIssuerName() {
        return "https://accounts.google.com";
    }

    static {
        ArrayList<Integer> arrayList = new ArrayList<>();
        LIB_UNAVAILABLE_CONNECTION_RESULT_LIST = arrayList;
        arrayList.add(3);
        LIB_UNAVAILABLE_CONNECTION_RESULT_LIST.add(1);
        LIB_UNAVAILABLE_CONNECTION_RESULT_LIST.add(2);
    }

    public GoogleAccountIssuerClient(Context context, boolean z, boolean z2) {
        this.mContext = context;
        this.mIsSilentStart = z;
        this.mShouldSkipAgreementPage = z2;
    }

    @Override // com.felicanetworks.mfc.mfi.openidconnect.IAccountIssuerClient
    public void handleMessage(Message message) {
        LogMgr.log(4, "%s :%d", "000", Integer.valueOf(message.what));
        int i = message.what;
        if (i == 65536) {
            onSignIn(message);
        } else if (i == 131072) {
            onConfirm(message.arg1 == 0);
        }
        LogMgr.log(4, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.mfi.openidconnect.IAccountIssuerClient
    public void requestAuthCode(String str, boolean z, IAccountIssuerClient.OnGetAuthCodeListener onGetAuthCodeListener) {
        LogMgr.log(4, "%s", "000");
        if (this.mProcess != Process.NONE) {
            LogMgr.log(2, "%s do %s.", "700", this.mProcess);
            onGetAuthCodeListener.onError(getIssuerName(), getAccountName(), 1, ObfuscatedMsgUtil.executionPoint());
        } else {
            if (this.mOnGetAuthCodeListener != null) {
                LogMgr.log(2, "%s it have requested already.", "701");
                onGetAuthCodeListener.onError(getIssuerName(), getAccountName(), 1, ObfuscatedMsgUtil.executionPoint());
                return;
            }
            this.mAccountName = str;
            this.mForce = z;
            this.mOnGetAuthCodeListener = onGetAuthCodeListener;
            confirm();
            LogMgr.log(4, "%s", "999");
        }
    }

    private String getAccountName() {
        return this.mAccountName;
    }

    private void confirm() {
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

    private void doConfirm() {
        boolean zIsAgreeCooperateAccount;
        if (!this.mIsSilentStart) {
            zIsAgreeCooperateAccount = OpenIdConnectSharedPreferences.isAgreeCooperateAccount(this.mContext);
        } else {
            zIsAgreeCooperateAccount = OpenIdConnectSharedPreferences.isAgreeCooperateAccountV2(this.mContext) || this.mShouldSkipAgreementPage;
        }
        if (zIsAgreeCooperateAccount) {
            getAuthCode();
            return;
        }
        if (!this.mIsSilentStart) {
            this.mProcess = Process.CONFIRM;
            Intent intent = new Intent(this.mContext, (Class<?>) ConfirmGoogleAccountActivity.class);
            intent.setFlags(268435456);
            this.mContext.startActivity(intent);
            return;
        }
        this.mProcess = Process.NONE;
        onError(4, null);
    }

    private void onConfirm(boolean z) {
        LogMgr.log(6, "%s isConfirmed ? %s", "000", Boolean.valueOf(z));
        if (z) {
            OpenIdConnectSharedPreferences.agreeCooperateAccountAll(this.mContext);
            getAuthCode();
        } else {
            onError(4, null);
        }
        LogMgr.log(6, "%s", "999");
    }

    private void getAuthCode() {
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

    private void doGetAuthCode() {
        this.mCleared = false;
        if (this.mAccountName == null) {
            this.mProcess = Process.CLEAR_DEFAULT;
        } else {
            this.mProcess = Process.SILENT_SIGN_IN;
        }
        connect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onGetAuthCode(String str, String str2) {
        LogMgr.log(6, "%s", "000");
        this.mProcess = Process.NONE;
        this.mGoogleApi.disconnect();
        IAccountIssuerClient.OnGetAuthCodeListener onGetAuthCodeListener = this.mOnGetAuthCodeListener;
        if (onGetAuthCodeListener != null) {
            onGetAuthCodeListener.onGetAuthCode(str, "https://accounts.google.com", str2);
            this.mOnGetAuthCodeListener = null;
        }
        LogMgr.log(6, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onError(int i, String str) {
        LogMgr.log(6, "%s", "000");
        this.mProcess = Process.NONE;
        this.mGoogleApi.disconnect();
        IAccountIssuerClient.OnGetAuthCodeListener onGetAuthCodeListener = this.mOnGetAuthCodeListener;
        if (onGetAuthCodeListener != null) {
            onGetAuthCodeListener.onError(getIssuerName(), getAccountName(), i, str);
            this.mOnGetAuthCodeListener = null;
        }
        LogMgr.log(6, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connect() {
        LogMgr.log(6, "%s", "000");
        if (this.mGoogleApi.connect(this.mContext, this.mAccountName, new GoogleApiClient.ConnectionCallbacks() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.GoogleAccountIssuerClient.1
            @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
            public void onConnected(Bundle bundle) {
                LogMgr.log(4, "%s", "000");
                GoogleAccountIssuerClient.this.onConnectedGoogleApiClient();
                LogMgr.log(4, "%s", "999");
            }

            @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
            public void onConnectionSuspended(int i) {
                LogMgr.log(4, "%s cause=%d", "000", Integer.valueOf(i));
                GoogleAccountIssuerClient.this.onError(2, String.format(OpenIdConnectConst.ERROR_MSG_FMT_CONNECTION_SUSPENDED, Integer.valueOf(i)));
                LogMgr.log(4, "%s", "999");
            }
        }, this)) {
            onConnectedGoogleApiClient();
        }
        LogMgr.log(6, "%s", "999");
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
    public void onConnectionFailed(ConnectionResult connectionResult) {
        LogMgr.log(4, "%s %s:%s", "000", Integer.valueOf(connectionResult.getErrorCode()), connectionResult.getErrorMessage());
        int errorCode = connectionResult.getErrorCode();
        onError(LIB_UNAVAILABLE_CONNECTION_RESULT_LIST.contains(Integer.valueOf(errorCode)) ? 6 : 2, String.format(OpenIdConnectConst.ERROR_MSG_FMT_CONNECTION_FAILD, Integer.valueOf(errorCode), connectionResult.getErrorMessage()));
        LogMgr.log(4, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onConnectedGoogleApiClient() {
        LogMgr.log(6, "%s", "000");
        if (Process.CLEAR_DEFAULT == this.mProcess) {
            signOut();
        } else if (Process.SILENT_SIGN_IN == this.mProcess) {
            silentSignIn();
        }
        LogMgr.log(6, "%s", "999");
    }

    private void signOut() {
        LogMgr.log(6, "%s", "000");
        try {
            this.mGoogleApi.signOut(new GoogleApiWrapper.OnSignOutListener() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.GoogleAccountIssuerClient.2
                @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnSignOutListener
                public void onFinish(Status status) {
                    LogMgr.log(6, "%s", "000");
                    if (status == null) {
                        GoogleAccountIssuerClient.this.onError(2, ObfuscatedMsgUtil.executionPoint());
                        return;
                    }
                    int statusCode = status.getStatusCode();
                    if (statusCode == 0 || -1 == statusCode || 4 == statusCode) {
                        GoogleAccountIssuerClient.this.mCleared = true;
                        if (Process.CLEAR_DEFAULT == GoogleAccountIssuerClient.this.mProcess) {
                            GoogleAccountIssuerClient.this.mProcess = Process.SILENT_SIGN_IN;
                            GoogleAccountIssuerClient.this.silentSignIn();
                        }
                    } else if (7 == statusCode) {
                        GoogleAccountIssuerClient googleAccountIssuerClient = GoogleAccountIssuerClient.this;
                        googleAccountIssuerClient.onError(5, googleAccountIssuerClient.createErrMsg(statusCode, status.getStatusMessage()));
                    } else {
                        GoogleAccountIssuerClient googleAccountIssuerClient2 = GoogleAccountIssuerClient.this;
                        googleAccountIssuerClient2.onError(2, googleAccountIssuerClient2.createErrMsg(statusCode, status.getStatusMessage()));
                    }
                    LogMgr.log(6, "%s", "999");
                }

                @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnSignOutListener
                public void onError(int i, String str) {
                    GoogleAccountIssuerClient.this.onError(i, str);
                }
            });
        } catch (Exception e) {
            LogMgr.log(2, "%s %s:%S", "702", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
            onError(1, ObfuscatedMsgUtil.exExecutionPoint(e));
        }
        LogMgr.log(6, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void silentSignIn() {
        LogMgr.log(6, "%s", "000");
        try {
            this.mGoogleApi.silentSignIn(new GoogleApiWrapper.OnSignInListener() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.GoogleAccountIssuerClient.3
                @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnSignInListener
                public void onSuccess(String str, String str2) {
                    LogMgr.log(6, "%s", "000");
                    GoogleAccountIssuerClient.this.onGetAuthCode(str, str2);
                    LogMgr.log(6, "%s", "999");
                }

                @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnSignInListener
                public void onFailed(int i, String str) {
                    LogMgr.log(6, "%s", "000");
                    if (4 == i) {
                        if (!GoogleAccountIssuerClient.this.mIsSilentStart) {
                            GoogleAccountIssuerClient.this.mProcess = Process.SIGN_IN;
                            GoogleAccountIssuerClient googleAccountIssuerClient = GoogleAccountIssuerClient.this;
                            googleAccountIssuerClient.requestSignIn(googleAccountIssuerClient.mAccountName);
                        } else {
                            GoogleAccountIssuerClient.this.mProcess = Process.NONE;
                            onError(7, null);
                        }
                    } else if (5 == i) {
                        if (!GoogleAccountIssuerClient.this.mForce && !GoogleAccountIssuerClient.this.mCleared) {
                            GoogleAccountIssuerClient.this.mAccountName = null;
                            GoogleAccountIssuerClient.this.mCleared = true;
                            GoogleAccountIssuerClient.this.mProcess = Process.CLEAR_DEFAULT;
                            GoogleAccountIssuerClient.this.connect();
                        } else {
                            GoogleAccountIssuerClient googleAccountIssuerClient2 = GoogleAccountIssuerClient.this;
                            googleAccountIssuerClient2.onError(3, googleAccountIssuerClient2.createErrMsg(i, str));
                        }
                    } else if (7 == i) {
                        GoogleAccountIssuerClient googleAccountIssuerClient3 = GoogleAccountIssuerClient.this;
                        googleAccountIssuerClient3.onError(5, googleAccountIssuerClient3.createErrMsg(i, str));
                    } else {
                        GoogleAccountIssuerClient googleAccountIssuerClient4 = GoogleAccountIssuerClient.this;
                        googleAccountIssuerClient4.onError(2, googleAccountIssuerClient4.createErrMsg(i, str));
                    }
                    LogMgr.log(6, "%s", "999");
                }

                @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnSignInListener
                public void onError(int i, String str) {
                    GoogleAccountIssuerClient.this.onError(i, str);
                }
            });
        } catch (Exception e) {
            LogMgr.log(2, "%s %s:%S", "702", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
            onError(1, ObfuscatedMsgUtil.exExecutionPoint(e));
        }
        LogMgr.log(6, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestSignIn(String str) {
        LogMgr.log(6, "%s", "000");
        Intent intent = new Intent(this.mContext, (Class<?>) GoogleSignInActivity.class);
        intent.setFlags(268435456);
        intent.putExtra(OpenIdConnectConst.MSG_DATA_ACCOUNT_NAME, str);
        this.mContext.startActivity(intent);
        LogMgr.log(6, "%s", "999");
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0054 A[Catch: Exception -> 0x005c, TRY_LEAVE, TryCatch #0 {Exception -> 0x005c, blocks: (B:3:0x0002, B:5:0x000a, B:21:0x0054, B:7:0x001b, B:9:0x001f, B:16:0x0035, B:17:0x003d, B:19:0x0042), top: B:26:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void onSignIn(android.os.Message r5) {
        /*
            r4 = this;
            r0 = 0
            r1 = 1
            android.os.Bundle r2 = r5.getData()     // Catch: java.lang.Exception -> L5c
            int r3 = r5.arg1     // Catch: java.lang.Exception -> L5c
            if (r3 != 0) goto L1b
            java.lang.String r5 = "auth_code"
            java.lang.String r5 = r2.getString(r5)     // Catch: java.lang.Exception -> L5c
            java.lang.String r0 = "account_name"
            java.lang.String r0 = r2.getString(r0)     // Catch: java.lang.Exception -> L5c
            r4.onGetAuthCode(r5, r0)     // Catch: java.lang.Exception -> L5c
        L19:
            r0 = 1
            goto L52
        L1b:
            int r3 = r5.arg1     // Catch: java.lang.Exception -> L5c
            if (r1 != r3) goto L3d
            java.lang.String r5 = "status_code"
            int r5 = r2.getInt(r5)     // Catch: java.lang.Exception -> L5c
            java.lang.String r0 = "status_message"
            java.lang.String r0 = r2.getString(r0)     // Catch: java.lang.Exception -> L5c
            r2 = 5
            if (r2 != r5) goto L30
            r2 = 3
            goto L35
        L30:
            r3 = 7
            if (r3 != r5) goto L34
            goto L35
        L34:
            r2 = 2
        L35:
            java.lang.String r5 = r4.createErrMsg(r5, r0)     // Catch: java.lang.Exception -> L5c
            r4.onError(r2, r5)     // Catch: java.lang.Exception -> L5c
            goto L19
        L3d:
            r3 = -1
            int r5 = r5.arg1     // Catch: java.lang.Exception -> L5c
            if (r3 != r5) goto L52
            java.lang.String r5 = "error_type"
            int r5 = r2.getInt(r5)     // Catch: java.lang.Exception -> L5c
            java.lang.String r0 = "error_message"
            java.lang.String r0 = r2.getString(r0)     // Catch: java.lang.Exception -> L5c
            r4.onError(r5, r0)     // Catch: java.lang.Exception -> L5c
            goto L19
        L52:
            if (r0 != 0) goto L64
            java.lang.String r5 = com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil.executionPoint()     // Catch: java.lang.Exception -> L5c
            r4.onError(r1, r5)     // Catch: java.lang.Exception -> L5c
            goto L64
        L5c:
            r5 = move-exception
            java.lang.String r5 = com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil.exExecutionPoint(r5)
            r4.onError(r1, r5)
        L64:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.openidconnect.google.GoogleAccountIssuerClient.onSignIn(android.os.Message):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String createErrMsg(int i, String str) {
        return String.format(OpenIdConnectConst.ERROR_MSG_FMT_INVALID_STATUS_CODE, Integer.valueOf(i), str);
    }
}
