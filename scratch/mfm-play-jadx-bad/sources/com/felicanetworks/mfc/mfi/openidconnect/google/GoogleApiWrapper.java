package com.felicanetworks.mfc.mfi.openidconnect.google;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.Context;
import android.os.CancellationSignal;
import androidx.activity.result.ActivityResult;
import androidx.credentials.ClearCredentialStateRequest;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CredentialOption;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.ClearCredentialException;
import androidx.credentials.exceptions.GetCredentialException;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import com.google.android.gms.auth.api.identity.AuthorizationClient;
import com.google.android.gms.auth.api.identity.AuthorizationRequest;
import com.google.android.gms.auth.api.identity.AuthorizationResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.AvailabilityException;
import com.google.android.gms.common.api.HasApiKey;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes3.dex */
class GoogleApiWrapper {
    static final ArrayList<Integer> LIB_UNAVAILABLE_CONNECTION_RESULT_LIST;

    interface OnAuthorizeListener {
        void onError(int type, String msg);

        void onFailed(int statusCode, String statusMessage);

        void onNeedShowingAuthorizeScreen(PendingIntent pendingIntent, String accountName);

        void onSuccess(String authCode, String accountName);
    }

    public interface OnAvailabilityListener {
        void Available();

        void Unavailable(int type, String msg);
    }

    interface OnClearCredentialListener {
        void onError(int type, String msg);

        void onSuccess();
    }

    interface OnGetCredentialListener {
        void onError(int type, String msg);

        void onSuccess(String email);
    }

    GoogleApiWrapper() {
    }

    static {
        ArrayList<Integer> arrayList = new ArrayList<>();
        LIB_UNAVAILABLE_CONNECTION_RESULT_LIST = arrayList;
        arrayList.add(3);
        arrayList.add(1);
        arrayList.add(2);
    }

    public void clearCredential(Context context, ExecutorService executor, final OnClearCredentialListener listener) {
        LogMgr.log(5, "000");
        try {
            CredentialManager.INSTANCE.create(context).clearCredentialStateAsync(new ClearCredentialStateRequest(), new CancellationSignal(), executor, new CredentialManagerCallback<Void, ClearCredentialException>() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.1
                /* JADX DEBUG: Method merged with bridge method: onResult(Ljava/lang/Object;)V */
                @Override // androidx.credentials.CredentialManagerCallback
                public void onResult(Void unused) {
                    LogMgr.log(4, "000");
                    listener.onSuccess();
                    LogMgr.log(4, "999");
                }

                /* JADX DEBUG: Method merged with bridge method: onError(Ljava/lang/Object;)V */
                @Override // androidx.credentials.CredentialManagerCallback
                public void onError(ClearCredentialException e) {
                    LogMgr.log(4, "000");
                    LogMgr.log(1, "800 class = " + e.getClass());
                    LogMgr.log(1, "801 errMsg = " + e.getMessage());
                    listener.onError(2, ObfuscatedMsgUtil.exExecutionPoint(e));
                    LogMgr.log(4, "999");
                }
            });
        } catch (Exception e) {
            LogMgr.log(1, "800 class = " + e.getClass());
            LogMgr.log(1, "801 errMsg = " + e.getMessage());
            listener.onError(1, ObfuscatedMsgUtil.exExecutionPoint(e));
        }
        LogMgr.log(5, "999");
    }

    public void getCredential(Context activityContext, ExecutorService executor, final OnGetCredentialListener listener, String serverClientId) {
        CredentialOption credentialOptionBuild;
        GetCredentialRequest getCredentialRequestBuild;
        LogMgr.log(5, "000");
        try {
            if (isSmartWatch()) {
                LogMgr.log(6, "001 is SmartWatch");
                credentialOptionBuild = new GetGoogleIdOption.Builder().setFilterByAuthorizedAccounts(false).setServerClientId(serverClientId).setAutoSelectEnabled(false).build();
            } else {
                LogMgr.log(6, "002 is Phone");
                credentialOptionBuild = new GetSignInWithGoogleOption.Builder(serverClientId).build();
            }
            LogMgr.log(6, "003");
            getCredentialRequestBuild = new GetCredentialRequest.Builder().addCredentialOption(credentialOptionBuild).build();
        } catch (Exception e) {
            LogMgr.log(1, "801 class = " + e.getClass());
            LogMgr.log(1, "802 errMsg = " + e.getMessage());
            listener.onError(1, ObfuscatedMsgUtil.exExecutionPoint(e));
        }
        if (activityContext == null) {
            LogMgr.log(5, "998");
            LogMgr.log(1, "800 Activity is already disappeared.");
            listener.onError(1, ObfuscatedMsgUtil.executionPoint());
        } else {
            CredentialManager credentialManagerCreate = CredentialManager.INSTANCE.create(activityContext);
            LogMgr.log(6, "003");
            credentialManagerCreate.getCredentialAsync(activityContext, getCredentialRequestBuild, new CancellationSignal(), executor, new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.2
                /* JADX DEBUG: Method merged with bridge method: onResult(Ljava/lang/Object;)V */
                @Override // androidx.credentials.CredentialManagerCallback
                public void onResult(GetCredentialResponse result) {
                    LogMgr.log(4, "000");
                    GoogleApiWrapper.this.handleGetCredentialAsync(result, listener);
                    LogMgr.log(4, "999");
                }

                /* JADX DEBUG: Method merged with bridge method: onError(Ljava/lang/Object;)V */
                @Override // androidx.credentials.CredentialManagerCallback
                public void onError(GetCredentialException e2) {
                    LogMgr.log(4, "000");
                    LogMgr.log(1, "800 class = " + e2.getClass());
                    LogMgr.log(1, "801 errMsg = " + e2.getMessage());
                    listener.onError(2, ObfuscatedMsgUtil.exExecutionPoint(e2));
                    LogMgr.log(4, "999");
                }
            });
            LogMgr.log(5, "999");
        }
    }

    private boolean isSmartWatch() {
        LogMgr.log(6, "000");
        boolean zHasSystemFeature = FelicaAdapter.getInstance().getPackageManager().hasSystemFeature("android.hardware.type.watch");
        LogMgr.log(6, "999");
        return zHasSystemFeature;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleGetCredentialAsync(GetCredentialResponse result, OnGetCredentialListener listener) {
        LogMgr.log(6, "000");
        Credential credential = result.getCredential();
        if (credential instanceof CustomCredential) {
            LogMgr.log(6, "001");
            if (GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL.equals(credential.getType())) {
                String id = GoogleIdTokenCredential.createFrom(credential.getData()).getZza();
                LogMgr.log(6, "002 googleIdTokenCredential.getId() = " + id);
                listener.onSuccess(id);
            } else {
                LogMgr.log(1, "800");
                listener.onError(1, ObfuscatedMsgUtil.executionPoint());
            }
        } else {
            LogMgr.log(1, "801");
            listener.onError(1, ObfuscatedMsgUtil.executionPoint());
        }
        LogMgr.log(6, "999");
    }

    public void authorizeAccount(Context context, OnAuthorizeListener listener, final String accountName, String serverClientId) throws Throwable {
        final OnAuthorizeListener onAuthorizeListener;
        Exception exc;
        final AuthorizationRequest authorizationRequest;
        LogMgr.log(5, "000");
        try {
            authorizationRequest = getAuthorizationRequest(accountName, serverClientId);
        } catch (Exception e) {
            e = e;
            onAuthorizeListener = listener;
        }
        if (authorizationRequest == null) {
            try {
                LogMgr.log(2, "700");
                listener.onError(1, ObfuscatedMsgUtil.executionPoint());
                return;
            } catch (Exception e2) {
                exc = e2;
                onAuthorizeListener = listener;
            }
        } else {
            LogMgr.log(6, "001");
            final AuthorizationClient authorizationClient = Identity.getAuthorizationClient(context);
            onAuthorizeListener = listener;
            try {
                checkAuthorizeApiAvailability(authorizationClient, new OnAvailabilityListener() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.3
                    @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnAvailabilityListener
                    public void Available() throws Throwable {
                        LogMgr.log(5, "000");
                        Task<AuthorizationResult> taskAuthorize = authorizationClient.authorize(authorizationRequest);
                        if (taskAuthorize.isSuccessful()) {
                            LogMgr.log(6, "001");
                            GoogleApiWrapper.this.handleAuthorizationTaskResult(taskAuthorize, onAuthorizeListener, accountName);
                        } else {
                            taskAuthorize.addOnCompleteListener(new OnCompleteListener<AuthorizationResult>() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.3.1
                                @Override // com.google.android.gms.tasks.OnCompleteListener
                                public void onComplete(Task<AuthorizationResult> task) throws Throwable {
                                    LogMgr.log(4, "000");
                                    GoogleApiWrapper.this.handleAuthorizationTaskResult(task, onAuthorizeListener, accountName);
                                    LogMgr.log(4, "999");
                                }
                            });
                        }
                        LogMgr.log(5, "999");
                    }

                    @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnAvailabilityListener
                    public void Unavailable(int type, String msg) {
                        LogMgr.log(5, "000");
                        onAuthorizeListener.onError(type, msg);
                        LogMgr.log(5, "999");
                    }
                });
            } catch (Exception e3) {
                e = e3;
                exc = e;
                LogMgr.log(1, "800 class = " + exc.getClass());
                LogMgr.log(1, "801 errMsg = " + exc.getMessage());
                onAuthorizeListener.onError(1, ObfuscatedMsgUtil.exExecutionPoint(exc));
            }
            LogMgr.log(5, "999");
        }
        exc = e;
        LogMgr.log(1, "800 class = " + exc.getClass());
        LogMgr.log(1, "801 errMsg = " + exc.getMessage());
        onAuthorizeListener.onError(1, ObfuscatedMsgUtil.exExecutionPoint(exc));
        LogMgr.log(5, "999");
    }

    private AuthorizationRequest getAuthorizationRequest(String accountName, String serverClientId) {
        AuthorizationRequest authorizationRequestBuild;
        LogMgr.log(6, "000 account name = " + accountName);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Scope("email"));
        if (accountName != null) {
            LogMgr.log(6, "001");
            authorizationRequestBuild = AuthorizationRequest.builder().setAccount(new Account(accountName, "com.google")).setRequestedScopes(arrayList).requestOfflineAccess(serverClientId).build();
        } else {
            LogMgr.log(2, "700 accountName is null");
            authorizationRequestBuild = null;
        }
        if (authorizationRequestBuild != null && authorizationRequestBuild.getAccount() != null) {
            LogMgr.log(6, "002 account = " + authorizationRequestBuild.getAccount().name);
            LogMgr.log(6, "003 account = " + authorizationRequestBuild.getAccount().type);
        }
        LogMgr.log(6, "999");
        return authorizationRequestBuild;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAuthorizationTaskResult(Task<AuthorizationResult> task, OnAuthorizeListener listener, String accountName) throws Throwable {
        LogMgr.log(6, "000");
        try {
            AuthorizationResult result = task.getResult(ApiException.class);
            Iterator<String> it = result.getGrantedScopes().iterator();
            while (it.hasNext()) {
                LogMgr.log(6, "001 scope = " + it.next());
            }
            LogMgr.log(6, "002 authCode = " + result.getServerAuthCode());
            if (result.hasResolution()) {
                LogMgr.log(6, "003");
                listener.onNeedShowingAuthorizeScreen(result.getPendingIntent(), accountName);
            } else {
                LogMgr.log(6, "004");
                String serverAuthCode = result.getServerAuthCode();
                LogMgr.log(6, "005 authCode = " + serverAuthCode);
                listener.onSuccess(serverAuthCode, accountName);
            }
        } catch (ApiException e) {
            LogMgr.log(1, "800 class = " + e.getClass());
            LogMgr.log(1, "801 Status = " + e.getStatusCode());
            LogMgr.log(1, "802 errMsg = " + e.getMessage());
            listener.onFailed(e.getStatusCode(), e.getMessage());
        } catch (Exception e2) {
            LogMgr.log(1, "803");
            LogMgr.log(1, "804 class = " + e2.getClass());
            LogMgr.log(1, "805 errMsg = " + e2.getMessage());
            listener.onError(1, ObfuscatedMsgUtil.exExecutionPoint(e2));
        }
        LogMgr.log(6, "999");
    }

    public void authorizeFromIntent(final ActivityResult result, Context activityContext, OnAuthorizeListener listener, final String accountName) throws Throwable {
        final OnAuthorizeListener onAuthorizeListener;
        final AuthorizationClient authorizationClient;
        try {
            LogMgr.log(5, "000");
            authorizationClient = Identity.getAuthorizationClient(activityContext);
            onAuthorizeListener = listener;
        } catch (Exception e) {
            e = e;
            onAuthorizeListener = listener;
        }
        try {
            checkAuthorizeApiAvailability(authorizationClient, new OnAvailabilityListener() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.4
                @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnAvailabilityListener
                public void Available() {
                    LogMgr.log(5, "000");
                    try {
                        String serverAuthCode = authorizationClient.getAuthorizationResultFromIntent(result.getData()).getServerAuthCode();
                        LogMgr.log(6, "001 authCode = " + serverAuthCode);
                        onAuthorizeListener.onSuccess(serverAuthCode, accountName);
                    } catch (ApiException e2) {
                        LogMgr.log(1, "800 class = " + e2.getClass());
                        LogMgr.log(1, "801 Status = " + e2.getStatusCode());
                        LogMgr.log(1, "802 errMsg = " + e2.getMessage());
                        onAuthorizeListener.onFailed(e2.getStatusCode(), e2.getMessage());
                    } catch (Exception e3) {
                        LogMgr.log(1, "803 class = " + e3.getClass());
                        LogMgr.log(1, "804 errMsg = " + e3.getMessage());
                        onAuthorizeListener.onError(1, ObfuscatedMsgUtil.exExecutionPoint(e3));
                    }
                    LogMgr.log(5, "999");
                }

                @Override // com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.OnAvailabilityListener
                public void Unavailable(int type, String msg) {
                    LogMgr.log(5, "000");
                    onAuthorizeListener.onError(type, msg);
                    LogMgr.log(5, "999");
                }
            });
        } catch (Exception e2) {
            e = e2;
            Exception exc = e;
            LogMgr.log(1, "800 class = " + exc.getClass());
            LogMgr.log(1, "801 errMsg = " + exc.getMessage());
            onAuthorizeListener.onError(1, ObfuscatedMsgUtil.exExecutionPoint(exc));
        }
        LogMgr.log(5, "999");
    }

    private void checkAuthorizeApiAvailability(final AuthorizationClient authorizationClient, final OnAvailabilityListener listener) throws Throwable {
        LogMgr.log(6, "000");
        try {
            GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
            LogMgr.log(7, "001 call GoogleApiAvailability#checkApiAvailability");
            Task<Void> taskCheckApiAvailability = googleApiAvailability.checkApiAvailability(authorizationClient, new HasApiKey[0]);
            if (taskCheckApiAvailability.isSuccessful()) {
                handleApiAvailability(taskCheckApiAvailability, authorizationClient, listener);
            } else {
                taskCheckApiAvailability.addOnCompleteListener(new OnCompleteListener<Void>() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.GoogleApiWrapper.5
                    @Override // com.google.android.gms.tasks.OnCompleteListener
                    public void onComplete(Task<Void> task) throws Throwable {
                        LogMgr.log(4, "000");
                        GoogleApiWrapper.handleApiAvailability(task, authorizationClient, listener);
                        LogMgr.log(4, "999");
                    }
                });
            }
        } catch (Exception e) {
            LogMgr.log(2, "700" + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            listener.Unavailable(1, ObfuscatedMsgUtil.exExecutionPoint(e));
        }
        LogMgr.log(6, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void handleApiAvailability(Task<Void> completedTask, AuthorizationClient authorizationClient, OnAvailabilityListener listener) throws Throwable {
        LogMgr.log(6, "000");
        int i = 2;
        try {
            completedTask.getResult(AvailabilityException.class);
            LogMgr.log(7, "001");
            listener.Available();
        } catch (AvailabilityException e) {
            ConnectionResult connectionResult = e.getConnectionResult(authorizationClient);
            int errorCode = connectionResult.getErrorCode();
            String errorMessage = connectionResult.getErrorMessage();
            LogMgr.log(7, "002 statusCode=" + errorCode);
            LogMgr.log(7, "003 message=" + errorMessage);
            if (LIB_UNAVAILABLE_CONNECTION_RESULT_LIST.contains(Integer.valueOf(errorCode))) {
                LogMgr.log(7, "004");
                i = 6;
            }
            listener.Unavailable(i, errorMessage);
        } catch (Exception e2) {
            LogMgr.log(2, "700" + e2.getClass().getSimpleName() + ":" + e2.getMessage());
            LogMgr.printStackTrace(7, e2);
            listener.Unavailable(1, ObfuscatedMsgUtil.exExecutionPoint(e2));
        }
        LogMgr.log(6, "999");
    }
}
