package com.felicanetworks.mfc.mfi;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectConst;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class OpenIdConnectManager {
    private static final HashMap<Integer, Integer> ERR_TYPE_MAP;
    public static final HashMap<String, String> ISSUER_MAP;
    private final Listener mListener;
    private Messenger mReplyMessenger;
    private Messenger mRequestMessenger = null;
    private ServiceConnection mConnection = new ServiceConnection() { // from class: com.felicanetworks.mfc.mfi.OpenIdConnectManager.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
            LogMgr.log(4, "%s", "000");
            if (service == null) {
                OpenIdConnectManager.this.onError(200, ObfuscatedMsgUtil.executionPoint());
                return;
            }
            OpenIdConnectManager.this.mRequestMessenger = new Messenger(service);
            OpenIdConnectManager.this.mReplyMessenger = new Messenger(new ReplyHandler(Looper.myLooper(), OpenIdConnectManager.this));
            OpenIdConnectManager.this.onConnected();
            LogMgr.log(4, "%s", "999");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
            LogMgr.log(4, "%s", "000");
            if (OpenIdConnectManager.this.mConnected) {
                OpenIdConnectManager.this.onError(200, ObfuscatedMsgUtil.executionPoint());
                OpenIdConnectManager.this.disconnect();
            }
            LogMgr.log(4, "%s", "999");
        }
    };
    private boolean mConnected = false;

    public interface Listener {
        void onConnected();

        void onError(int type, String msg);

        void onGetAuthCode(String authCode, String issuer, String name);
    }

    static {
        HashMap<String, String> map = new HashMap<>();
        ISSUER_MAP = map;
        map.put("Google", OpenIdConnectConst.ACCOUNT_ISSUER_GOOGLE);
        HashMap<Integer, Integer> map2 = new HashMap<>();
        ERR_TYPE_MAP = map2;
        map2.put(1, 200);
        map2.put(2, 217);
        map2.put(4, 218);
        map2.put(7, Integer.valueOf(MfiClientCallbackConst.TYPE_SIGN_IN_REQUIRED));
        map2.put(6, 219);
    }

    private static class ReplyHandler extends Handler {
        private final WeakReference<OpenIdConnectManager> mOuterReference;

        private ReplyHandler(Looper looper, OpenIdConnectManager instance) {
            super(looper);
            this.mOuterReference = new WeakReference<>(instance);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            String string;
            String string2;
            String string3;
            LogMgr.log(4, "%s :%d", "000", Integer.valueOf(msg.what));
            OpenIdConnectManager openIdConnectManager = this.mOuterReference.get();
            int i = 1;
            if (openIdConnectManager == null) {
                LogMgr.log(1, "%s WeakReference has cleared.");
                return;
            }
            if (msg.what == 257) {
                Bundle data = msg.getData();
                String string4 = null;
                if (msg.arg1 == 0) {
                    if (data != null) {
                        string4 = data.getString("auth_code");
                        string2 = data.getString(OpenIdConnectConst.MSG_DATA_ACCOUNT_ISSUER);
                        string3 = data.getString(OpenIdConnectConst.MSG_DATA_ACCOUNT_NAME);
                    } else {
                        string2 = null;
                        string3 = null;
                    }
                    openIdConnectManager.onGetAuthCode(string4, string2, string3);
                } else {
                    if (data != null) {
                        string4 = data.getString(OpenIdConnectConst.MSG_DATA_ACCOUNT_ISSUER);
                        i = data.getInt(OpenIdConnectConst.MSG_DATA_ERROR_TYPE);
                        string = data.getString(OpenIdConnectConst.MSG_DATA_ERROR_MSG);
                    } else {
                        string = null;
                    }
                    openIdConnectManager.onOpenIdConnectError(string4, i, string);
                }
            }
            LogMgr.log(4, "%s", "999");
        }
    }

    public OpenIdConnectManager(Listener listener) {
        this.mListener = listener;
    }

    public void connect() {
        try {
            LogMgr.log(6, "bindService com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectService");
            Context applicationContext = FelicaAdapter.getInstance().getApplicationContext();
            Intent intent = new Intent();
            intent.setClassName(applicationContext, OpenIdConnectConst.SERVICE_NAME);
            applicationContext.bindService(intent, this.mConnection, 1);
            this.mConnected = true;
        } catch (Exception e) {
            LogMgr.log(2, "%s %s:%s", "700", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
            onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
        }
    }

    public void disconnect() {
        try {
            if (this.mRequestMessenger != null) {
                this.mRequestMessenger.send(Message.obtain((Handler) null, 1));
            }
            LogMgr.log(6, "unbindService com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectService");
            FelicaAdapter.getInstance().getApplicationContext().unbindService(this.mConnection);
            this.mRequestMessenger = null;
            this.mReplyMessenger = null;
            this.mConnected = false;
        } catch (Exception e) {
            LogMgr.log(2, "%s %s:%s", "700", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
            onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
        }
    }

    public void getAuthCode(String accountIssuer, String accountName, boolean force, boolean isSilentStart, boolean shouldSkipAgreementPage) {
        try {
            Message messageObtain = Message.obtain((Handler) null, 2);
            Bundle bundle = new Bundle();
            bundle.putString(OpenIdConnectConst.MSG_DATA_ACCOUNT_ISSUER, accountIssuer);
            bundle.putString(OpenIdConnectConst.MSG_DATA_ACCOUNT_NAME, accountName);
            bundle.putBoolean(OpenIdConnectConst.MSG_DATA_ACCOUNT_FORCE, force);
            bundle.putBoolean(OpenIdConnectConst.MSG_DATA_IS_SILENT_START, isSilentStart);
            bundle.putBoolean(OpenIdConnectConst.MSG_DATA_SHOULD_SKIP_AGREEMENT_PAGE, shouldSkipAgreementPage);
            messageObtain.setData(bundle);
            messageObtain.replyTo = this.mReplyMessenger;
            this.mRequestMessenger.send(messageObtain);
        } catch (Exception e) {
            LogMgr.log(2, "%s %s:%s", "700", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
            onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onConnected() {
        Listener listener = this.mListener;
        if (listener != null) {
            listener.onConnected();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onGetAuthCode(String authCode, String issuer, String name) {
        Listener listener = this.mListener;
        if (listener == null) {
            LogMgr.log(1, "%s Listener is null.", "800");
            return;
        }
        if (authCode == null || issuer == null || name == null) {
            LogMgr.log(2, "%s Parameter is null. authCode=%s,issuer=%s,name=%s", "700", authCode, issuer, name);
            onError(200, ObfuscatedMsgUtil.executionPoint());
        } else {
            listener.onGetAuthCode(authCode, issuer, name);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onOpenIdConnectError(String issuer, int type, String msg) {
        LogMgr.log(6, "000 issuer=" + issuer + ",type=" + type + ",msg=" + msg);
        HashMap<Integer, Integer> map = ERR_TYPE_MAP;
        int iIntValue = map.containsKey(Integer.valueOf(type)) ? map.get(Integer.valueOf(type)).intValue() : 200;
        HashMap<String, String> map2 = ISSUER_MAP;
        if (map2.containsValue(issuer)) {
            Iterator<Map.Entry<String, String>> it = map2.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry<String, String> next = it.next();
                if (next.getValue().equals(issuer)) {
                    issuer = next.getKey();
                    break;
                }
            }
        }
        if (200 != iIntValue) {
            if (216 == iIntValue) {
                msg = String.format(MfiClientCallbackConst.MSG_FMT_OP_ERROR, issuer);
            } else if (219 == iIntValue) {
                msg = String.format(MfiClientCallbackConst.MSG_FMT_OP_ERROR_MSG_ONLY, msg);
            } else {
                msg = (218 == iIntValue || 205 == iIntValue) ? null : String.format(MfiClientCallbackConst.MSG_FMT_OP_ERROR_WITH_MSG, issuer, msg);
            }
        }
        onError(iIntValue, msg);
        LogMgr.log(6, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onError(int type, String msg) {
        LogMgr.log(6, "000 type=" + type + ",msg=" + msg);
        Listener listener = this.mListener;
        if (listener != null) {
            listener.onError(type, msg);
        } else {
            LogMgr.log(1, "%s Listener is null.", "800");
        }
        LogMgr.log(6, "999");
    }
}
