package com.felicanetworks.mfc.mfi.openidconnect;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import com.felicanetworks.mfc.mfi.openidconnect.IAccountIssuerClient;
import com.felicanetworks.mfc.mfi.openidconnect.google.GoogleAccountIssuerClient;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes3.dex */
public class OpenIdConnectService extends Service {
    private IAccountIssuerClient mClient = null;
    private final Messenger mMessenger = new Messenger(new MessengerHandler(Looper.myLooper(), this));

    private static class MessengerHandler extends Handler {
        private final WeakReference<OpenIdConnectService> mServiceRef;

        MessengerHandler(Looper looper, OpenIdConnectService service) {
            super(looper);
            this.mServiceRef = new WeakReference<>(service);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            boolean z;
            String str;
            boolean z2;
            boolean z3;
            LogMgr.log(4, "000 :+", Integer.valueOf(msg.what));
            OpenIdConnectService openIdConnectService = this.mServiceRef.get();
            if (openIdConnectService == null) {
                LogMgr.log(1, "%s WeakReference has cleared.");
                return;
            }
            int i = msg.what;
            String str2 = null;
            if (i == 1) {
                LogMgr.log(7, "001");
                if (openIdConnectService.mClient != null) {
                    openIdConnectService.mClient = null;
                }
            } else if (i == 2) {
                LogMgr.log(7, "002");
                Bundle data = msg.getData();
                if (data != null) {
                    LogMgr.log(7, "003");
                    String string = data.getString(OpenIdConnectConst.MSG_DATA_ACCOUNT_ISSUER);
                    String string2 = data.getString(OpenIdConnectConst.MSG_DATA_ACCOUNT_NAME);
                    z = data.getBoolean(OpenIdConnectConst.MSG_DATA_ACCOUNT_FORCE);
                    boolean z4 = data.getBoolean(OpenIdConnectConst.MSG_DATA_IS_SILENT_START);
                    str2 = string2;
                    str = string;
                    z3 = data.getBoolean(OpenIdConnectConst.MSG_DATA_SHOULD_SKIP_AGREEMENT_PAGE);
                    z2 = z4;
                } else {
                    z = false;
                    str = null;
                    z2 = false;
                    z3 = false;
                }
                openIdConnectService.getAuthCode(str, str2, z, msg.replyTo, z2, z3);
            } else {
                LogMgr.log(7, "004");
            }
            LogMgr.log(4, "999");
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        LogMgr.log(4, "%s", "000");
        super.onCreate();
        LogMgr.log(4, "%s", "999");
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        LogMgr.log(4, "%s", "000");
        IBinder binder = this.mMessenger.getBinder();
        LogMgr.log(4, "%s", "999");
        return binder;
    }

    @Override // android.app.Service
    public void onRebind(Intent intent) {
        LogMgr.log(4, "%s", "000");
        LogMgr.log(4, "%s", "999");
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        LogMgr.log(4, "%s", "000");
        LogMgr.log(4, "%s", "999");
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getAuthCode(String accountIssuer, String accountName, boolean force, final Messenger replyTo, boolean isSilentStart, boolean shouldSkipAgreementPage) {
        LogMgr.log(6, "000");
        try {
        } catch (Exception e) {
            LogMgr.log(1, "803 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            notifyOnErrorGetAuthCode(replyTo, accountIssuer, 1, ObfuscatedMsgUtil.executionPoint());
        }
        if (replyTo == null) {
            LogMgr.log(4, "800 replyTo is null.");
            return;
        }
        if (this.mClient != null) {
            LogMgr.log(1, "801");
            notifyOnErrorGetAuthCode(replyTo, accountIssuer, 1, ObfuscatedMsgUtil.executionPoint());
            return;
        }
        if (!isSilentStart) {
            LogMgr.log(1, "802 is not called by silentStartTask.");
            return;
        }
        if (accountIssuer == null && accountName == null) {
            LogMgr.log(7, "001");
            notifyOnErrorGetAuthCode(replyTo, accountIssuer, 7, null);
        } else {
            if (OpenIdConnectConst.ACCOUNT_ISSUER_GOOGLE.equals(accountIssuer)) {
                LogMgr.log(7, "002");
                GoogleAccountIssuerClient googleAccountIssuerClient = new GoogleAccountIssuerClient(this, null, shouldSkipAgreementPage, null);
                this.mClient = googleAccountIssuerClient;
                googleAccountIssuerClient.requestAuthCode(accountName, force, new IAccountIssuerClient.OnGetAuthCodeListener() { // from class: com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectService.1
                    @Override // com.felicanetworks.mfc.mfi.openidconnect.IAccountIssuerClient.OnGetAuthCodeListener
                    public void onGetAuthCode(String authCode, String issuer, String name) {
                        LogMgr.log(7, "000");
                        OpenIdConnectService.this.notifyOnGetAuthCode(replyTo, authCode, issuer, name);
                        LogMgr.log(7, "999");
                    }

                    @Override // com.felicanetworks.mfc.mfi.openidconnect.IAccountIssuerClient.OnGetAuthCodeListener
                    public void onShowingAuthorizeScreen(String name) {
                        LogMgr.log(7, "000");
                        OpenIdConnectService.this.notifyOnErrorGetAuthCode(replyTo, OpenIdConnectConst.ACCOUNT_ISSUER_GOOGLE, 1, ObfuscatedMsgUtil.executionPoint());
                        LogMgr.log(7, "999");
                    }

                    @Override // com.felicanetworks.mfc.mfi.openidconnect.IAccountIssuerClient.OnGetAuthCodeListener
                    public void onError(String issuer, String account, int type, String msg) {
                        LogMgr.log(7, "000");
                        OpenIdConnectService.this.notifyOnErrorGetAuthCode(replyTo, issuer, type, msg);
                        LogMgr.log(7, "999");
                    }
                });
                LogMgr.log(6, "999");
                return;
            }
            LogMgr.log(7, "003");
            notifyOnErrorGetAuthCode(replyTo, accountIssuer, 1, ObfuscatedMsgUtil.executionPoint());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyOnErrorGetAuthCode(Messenger replyTo, String issuer, int errType, String errMsg) {
        try {
            this.mClient = null;
            Message messageObtain = Message.obtain((Handler) null, 257);
            messageObtain.arg1 = 1;
            Bundle bundle = new Bundle();
            bundle.putString(OpenIdConnectConst.MSG_DATA_ACCOUNT_ISSUER, issuer);
            bundle.putInt(OpenIdConnectConst.MSG_DATA_ERROR_TYPE, errType);
            bundle.putString(OpenIdConnectConst.MSG_DATA_ERROR_MSG, errMsg);
            messageObtain.setData(bundle);
            replyTo.send(messageObtain);
        } catch (Exception e) {
            LogMgr.log(1, "%s %s:%s", "800", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyOnGetAuthCode(Messenger replyTo, String authCode, String issuer, String name) {
        try {
            this.mClient = null;
            Message messageObtain = Message.obtain((Handler) null, 257);
            messageObtain.arg1 = 0;
            Bundle bundle = new Bundle();
            bundle.putString("auth_code", authCode);
            bundle.putString(OpenIdConnectConst.MSG_DATA_ACCOUNT_ISSUER, issuer);
            bundle.putString(OpenIdConnectConst.MSG_DATA_ACCOUNT_NAME, name);
            messageObtain.setData(bundle);
            replyTo.send(messageObtain);
        } catch (Exception e) {
            LogMgr.log(1, "%s %s:%s", "800", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
    }
}
