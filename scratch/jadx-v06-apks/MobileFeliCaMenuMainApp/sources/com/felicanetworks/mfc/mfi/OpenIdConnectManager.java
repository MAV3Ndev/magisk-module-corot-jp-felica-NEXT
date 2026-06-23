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

/* JADX INFO: loaded from: classes.dex */
public class OpenIdConnectManager {
    private static final HashMap<Integer, Integer> ERR_TYPE_MAP;
    public static final HashMap<String, String> ISSUER_MAP;
    private final Listener mListener;
    private Messenger mReplyMessenger;
    private Messenger mRequestMessenger = null;
    private ServiceConnection mConnection = new ServiceConnection() { // from class: com.felicanetworks.mfc.mfi.OpenIdConnectManager.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogMgr.log(4, "%s", "000");
            if (iBinder == null) {
                OpenIdConnectManager.this.onError(200, ObfuscatedMsgUtil.executionPoint());
                return;
            }
            OpenIdConnectManager.this.mRequestMessenger = new Messenger(iBinder);
            OpenIdConnectManager.this.mReplyMessenger = new Messenger(new ReplyHandler(Looper.myLooper(), OpenIdConnectManager.this));
            OpenIdConnectManager.this.onConnected();
            LogMgr.log(4, "%s", "999");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
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

        void onError(int i, String str);

        void onGetAuthCode(String str, String str2, String str3);
    }

    static {
        HashMap<String, String> map = new HashMap<>();
        ISSUER_MAP = map;
        map.put("Google", "https://accounts.google.com");
        HashMap<Integer, Integer> map2 = new HashMap<>();
        ERR_TYPE_MAP = map2;
        map2.put(1, 200);
        ERR_TYPE_MAP.put(2, 217);
        ERR_TYPE_MAP.put(3, 216);
        ERR_TYPE_MAP.put(4, 218);
        ERR_TYPE_MAP.put(7, Integer.valueOf(MfiClientCallbackConst.TYPE_SIGN_IN_REQUIRED));
        ERR_TYPE_MAP.put(5, 205);
        ERR_TYPE_MAP.put(6, 219);
    }

    private static class ReplyHandler extends Handler {
        private final WeakReference<OpenIdConnectManager> mOuterReference;

        private ReplyHandler(Looper looper, OpenIdConnectManager openIdConnectManager) {
            super(looper);
            this.mOuterReference = new WeakReference<>(openIdConnectManager);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            String string;
            String string2;
            String string3;
            LogMgr.log(4, "%s :%d", "000", Integer.valueOf(message.what));
            OpenIdConnectManager openIdConnectManager = this.mOuterReference.get();
            int i = 1;
            if (openIdConnectManager == null) {
                LogMgr.log(1, "%s WeakReference has cleared.");
                return;
            }
            if (message.what == 257) {
                Bundle data = message.getData();
                String string4 = null;
                if (message.arg1 == 0) {
                    if (data != null) {
                        string4 = data.getString(OpenIdConnectConst.MSG_DATA_AUTH_CODE);
                        string2 = data.getString("account_issuer");
                        string3 = data.getString(OpenIdConnectConst.MSG_DATA_ACCOUNT_NAME);
                    } else {
                        string2 = null;
                        string3 = null;
                    }
                    openIdConnectManager.onGetAuthCode(string4, string2, string3);
                } else {
                    if (data != null) {
                        string4 = data.getString("account_issuer");
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

    public void getAuthCode(String str, String str2, boolean z, boolean z2, boolean z3) {
        try {
            Message messageObtain = Message.obtain((Handler) null, 2);
            Bundle bundle = new Bundle();
            bundle.putString("account_issuer", str);
            bundle.putString(OpenIdConnectConst.MSG_DATA_ACCOUNT_NAME, str2);
            bundle.putBoolean(OpenIdConnectConst.MSG_DATA_ACCOUNT_FORCE, z);
            bundle.putBoolean(OpenIdConnectConst.MSG_DATA_IS_SILENT_START, z2);
            bundle.putBoolean(OpenIdConnectConst.MSG_DATA_SHOULD_SKIP_AGREEMENT_PAGE, z3);
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
    public void onGetAuthCode(String str, String str2, String str3) {
        Listener listener = this.mListener;
        if (listener == null) {
            LogMgr.log(1, "%s Listener is null.", "800");
            return;
        }
        if (str == null || str2 == null || str3 == null) {
            LogMgr.log(2, "%s Parameter is null. authCode=%s,issuer=%s,name=%s", "700", str, str2, str3);
            onError(200, ObfuscatedMsgUtil.executionPoint());
        } else {
            listener.onGetAuthCode(str, str2, str3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onOpenIdConnectError(String str, int i, String str2) {
        LogMgr.log(6, "000 issuer=" + str + ",type=" + i + ",msg=" + str2);
        int iIntValue = ERR_TYPE_MAP.containsKey(Integer.valueOf(i)) ? ERR_TYPE_MAP.get(Integer.valueOf(i)).intValue() : 200;
        if (ISSUER_MAP.containsValue(str)) {
            Iterator<Map.Entry<String, String>> it = ISSUER_MAP.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry<String, String> next = it.next();
                if (next.getValue().equals(str)) {
                    str = next.getKey();
                    break;
                }
            }
        }
        if (200 != iIntValue) {
            if (216 == iIntValue) {
                str2 = String.format(MfiClientCallbackConst.MSG_FMT_OP_ERROR, str);
            } else if (219 == iIntValue) {
                str2 = String.format(MfiClientCallbackConst.MSG_FMT_OP_ERROR_MSG_ONLY, str2);
            } else {
                str2 = (218 == iIntValue || 205 == iIntValue) ? null : String.format(MfiClientCallbackConst.MSG_FMT_OP_ERROR_WITH_MSG, str, str2);
            }
        }
        onError(iIntValue, str2);
        LogMgr.log(6, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onError(int i, String str) {
        LogMgr.log(6, "000 type=" + i + ",msg=" + str);
        Listener listener = this.mListener;
        if (listener != null) {
            listener.onError(i, str);
        } else {
            LogMgr.log(1, "%s Listener is null.", "800");
        }
        LogMgr.log(6, "999");
    }
}
