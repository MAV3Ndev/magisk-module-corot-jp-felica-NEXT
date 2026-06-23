package com.felicanetworks.mfm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes3.dex */
public class LockStatusReceiver extends BroadcastReceiver {
    private static boolean received = false;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        AnalysisManager.stampReceive(context);
        setReceived();
        if (StateController.getAppPhase() == StateController.AppPhase.FOR_GROUND) {
            StateController.postStateEvent(StateMachine.Event.EI_LOCK_STATE_CHANGE, null, new Object[0]);
        }
    }

    public static synchronized boolean isReceived() {
        return received;
    }

    static synchronized void setReceived() {
        received = true;
    }

    public static synchronized void resetReceived() {
        received = false;
    }
}
