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

/* JADX INFO: loaded from: classes.dex */
public class OpenIdConnectService extends Service {
    private IAccountIssuerClient mClient = null;
    private final Messenger mMessenger = new Messenger(new MessengerHandler(Looper.myLooper(), this));

    private static class MessengerHandler extends Handler {
        private final WeakReference<OpenIdConnectService> mServiceRef;

        MessengerHandler(Looper looper, OpenIdConnectService openIdConnectService) {
            super(looper);
            this.mServiceRef = new WeakReference<>(openIdConnectService);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            String string;
            boolean z;
            boolean z2;
            boolean z3;
            LogMgr.log(4, "%s :%d", "000", Integer.valueOf(message.what));
            OpenIdConnectService openIdConnectService = this.mServiceRef.get();
            if (openIdConnectService == null) {
                LogMgr.log(1, "%s WeakReference has cleared.");
                return;
            }
            int i = message.what;
            String string2 = null;
            if (i != 1) {
                if (i != 2) {
                    if (openIdConnectService.mClient != null) {
                        openIdConnectService.mClient.handleMessage(message);
                    }
                } else {
                    Bundle data = message.getData();
                    if (data != null) {
                        string = data.getString("account_issuer");
                        string2 = data.getString(OpenIdConnectConst.MSG_DATA_ACCOUNT_NAME);
                        z = data.getBoolean(OpenIdConnectConst.MSG_DATA_ACCOUNT_FORCE);
                        boolean z4 = data.getBoolean(OpenIdConnectConst.MSG_DATA_IS_SILENT_START);
                        z3 = data.getBoolean(OpenIdConnectConst.MSG_DATA_SHOULD_SKIP_AGREEMENT_PAGE);
                        z2 = z4;
                    } else {
                        string = null;
                        z = false;
                        z2 = false;
                        z3 = false;
                    }
                    openIdConnectService.getAuthCode(string, string2, z, message.replyTo, z2, z3);
                }
            } else if (openIdConnectService.mClient != null) {
                openIdConnectService.mClient = null;
            }
            LogMgr.log(4, "%s", "999");
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
    public void getAuthCode(String str, String str2, boolean z, final Messenger messenger, boolean z2, boolean z3) {
        try {
            if (messenger == null) {
                LogMgr.log(4, "%s replyTo is null.", "800");
                return;
            }
            if (this.mClient != null) {
                notifyOnErrorGetAuthCode(messenger, str, 1, ObfuscatedMsgUtil.executionPoint());
                return;
            }
            if ((str == null && str2 == null) || "https://accounts.google.com".equals(str)) {
                this.mClient = new GoogleAccountIssuerClient(this, z2, z3);
                this.mClient.requestAuthCode(str2, z, new IAccountIssuerClient.OnGetAuthCodeListener() { // from class: com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectService.1
                    @Override // com.felicanetworks.mfc.mfi.openidconnect.IAccountIssuerClient.OnGetAuthCodeListener
                    public void onGetAuthCode(String str3, String str4, String str5) {
                        OpenIdConnectService.this.notifyOnGetAuthCode(messenger, str3, str4, str5);
                    }

                    @Override // com.felicanetworks.mfc.mfi.openidconnect.IAccountIssuerClient.OnGetAuthCodeListener
                    public void onError(String str3, String str4, int i, String str5) {
                        OpenIdConnectService.this.notifyOnErrorGetAuthCode(messenger, str3, i, str5);
                    }
                });
            } else {
                notifyOnErrorGetAuthCode(messenger, str, 1, ObfuscatedMsgUtil.executionPoint());
            }
        } catch (Exception e) {
            LogMgr.log(2, "%s %s:%s", "800", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
            notifyOnErrorGetAuthCode(messenger, str, 1, ObfuscatedMsgUtil.executionPoint());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyOnErrorGetAuthCode(Messenger messenger, String str, int i, String str2) {
        try {
            this.mClient = null;
            Message messageObtain = Message.obtain((Handler) null, 257);
            messageObtain.arg1 = 1;
            Bundle bundle = new Bundle();
            bundle.putString("account_issuer", str);
            bundle.putInt(OpenIdConnectConst.MSG_DATA_ERROR_TYPE, i);
            bundle.putString(OpenIdConnectConst.MSG_DATA_ERROR_MSG, str2);
            messageObtain.setData(bundle);
            messenger.send(messageObtain);
        } catch (Exception e) {
            LogMgr.log(1, "%s %s:%s", "800", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyOnGetAuthCode(Messenger messenger, String str, String str2, String str3) {
        try {
            this.mClient = null;
            Message messageObtain = Message.obtain((Handler) null, 257);
            messageObtain.arg1 = 0;
            Bundle bundle = new Bundle();
            bundle.putString(OpenIdConnectConst.MSG_DATA_AUTH_CODE, str);
            bundle.putString("account_issuer", str2);
            bundle.putString(OpenIdConnectConst.MSG_DATA_ACCOUNT_NAME, str3);
            messageObtain.setData(bundle);
            messenger.send(messageObtain);
        } catch (Exception e) {
            LogMgr.log(1, "%s %s:%s", "800", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
    }
}
