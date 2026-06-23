package com.felicanetworks.mfm.main.presenter.internal.rwp2p;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent;

/* JADX INFO: loaded from: classes3.dex */
public class NfcStatusChangeReceiver extends BroadcastReceiver {
    private static CentralFuncAgent.ChangeDataListener _changeDataListener;
    private static NfcStatusChangeReceiver instance;
    private static int previousNfcAdapterState;

    private static synchronized void registerNfcStatusChangeReceiver(Context context) {
        Settings.FelicaChipVersion felicaChipVersion;
        if (instance == null && (Settings.FelicaChipVersion.FAVER_GP_4_0 == (felicaChipVersion = Settings.getFelicaChipVersion()) || Settings.FelicaChipVersion.FAVER_GP_4_1 == felicaChipVersion)) {
            instance = new NfcStatusChangeReceiver();
            context.registerReceiver(instance, new IntentFilter("android.nfc.action.ADAPTER_STATE_CHANGED"));
        }
    }

    private static synchronized void unregisterNfcStatusChangeReceiver(Context context) {
        NfcStatusChangeReceiver nfcStatusChangeReceiver = instance;
        if (nfcStatusChangeReceiver != null) {
            context.unregisterReceiver(nfcStatusChangeReceiver);
            instance = null;
        }
    }

    public static void setChangeDataListener(Context context, CentralFuncAgent.ChangeDataListener listener) {
        _changeDataListener = listener;
        if (listener != null) {
            registerNfcStatusChangeReceiver(context);
        } else {
            unregisterNfcStatusChangeReceiver(context);
        }
    }

    public static CentralFuncAgent.ChangeDataListener getChangeDataListener() {
        return _changeDataListener;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        try {
            if (getChangeDataListener() == null || !"android.nfc.action.ADAPTER_STATE_CHANGED".equals(intent.getAction())) {
                return;
            }
            boolean z = true;
            int intExtra = intent.getIntExtra("android.nfc.extra.ADAPTER_STATE", 1);
            if (previousNfcAdapterState == intExtra) {
                return;
            }
            previousNfcAdapterState = intExtra;
            if (intExtra == 1) {
                z = false;
            } else if (intExtra != 3) {
                return;
            }
            if (getChangeDataListener() != null) {
                getChangeDataListener().onNfcSettingChange(z);
            }
        } catch (Exception e) {
            LogUtil.warning(new MfmException(getClass(), 370, e));
        }
    }
}
