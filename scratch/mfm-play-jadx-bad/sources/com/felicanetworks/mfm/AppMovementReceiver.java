package com.felicanetworks.mfm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;

/* JADX INFO: loaded from: classes3.dex */
public class AppMovementReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        try {
            AnalysisManager.stampReceive(context);
            AnalysisManager.stamp(MfmStamp.Event.APP_MOVEMENT, intent);
            AppMovementReceiverWorker.startActionCheckOperable(context);
        } catch (Exception e) {
            LogUtil.warning(new MfmException(AppMovementReceiver.class, 259, e));
        }
    }
}
