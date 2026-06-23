package com.felicanetworks.mfm;

import android.content.Context;
import android.content.Intent;
import androidx.core.app.JobIntentService;
import com.felicanetworks.mfm.main.model.internal.main.ModelContext;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.VersionUpProtocol;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.sg.Sg;

/* JADX INFO: loaded from: classes.dex */
public class AppMovementReceiverIntentService extends JobIntentService {
    private static final int JOB_ID = 1;
    private static OperableChecker _checker;

    private static class OperableChecker {
        private Context _context;
        private boolean _isDoneCheck = false;
        private VersionUpProtocol.Result _result;

        OperableChecker(Context context) {
            this._context = context;
        }

        private void startVersionUpCheck() {
            try {
                int iIntValue = Integer.valueOf((String) Sg.getValue(Sg.Key.NET_AIM_VERSION_DEFAULT_PARAM_FACT_COUNT)).intValue();
                int iIntValue2 = Integer.valueOf((String) Sg.getValue(Sg.Key.NET_AIM_VERSION_DEFAULT_PARAM_MAX_COUNT)).intValue();
                int iIntValue3 = Integer.valueOf((String) Sg.getValue(Sg.Key.NET_AIM_VERSION_DEFAULT_PARAM_FACT_INTERVAL)).intValue();
                int iIntValue4 = Integer.valueOf((String) Sg.getValue(Sg.Key.NET_AIM_VERSION_DEFAULT_PARAM_INTERVAL)).intValue();
                NetworkExpert networkExpert = new NetworkExpert(new ModelContext(this._context));
                VersionUpProtocol versionUpProtocol = networkExpert.getVersionUpProtocol();
                VersionUpProtocol.Result result = versionUpProtocol.parse(networkExpert.connect(versionUpProtocol.create(new VersionUpProtocol.Parameter(iIntValue, iIntValue2, iIntValue4, iIntValue3))));
                this._result = result;
                AnalysisManager.setEnabled(!result.isPreventLogs());
                this._isDoneCheck = true;
            } catch (Exception unused) {
            }
        }

        boolean isOperableMfm() {
            if (!this._isDoneCheck) {
                startVersionUpCheck();
            }
            return Integer.valueOf("1").intValue() != this._result.versionupInfo;
        }

        boolean isOperableSendLog() {
            if (!this._isDoneCheck) {
                startVersionUpCheck();
            }
            return !this._result.isPreventLogs();
        }
    }

    public static void startActionCheckOperable(Context context) {
        enqueueWork(context, (Class<?>) AppMovementReceiverIntentService.class, 1, new Intent(context, (Class<?>) AppMovementReceiverIntentService.class));
    }

    public AppMovementReceiverIntentService() {
        if (_checker == null) {
            _checker = new OperableChecker(this);
        }
    }

    @Override // androidx.core.app.JobIntentService
    protected void onHandleWork(Intent intent) {
        try {
            if (_checker.isOperableMfm() && _checker.isOperableSendLog()) {
                handleActionSendLogs();
            }
        } catch (Exception unused) {
        }
    }

    private void handleActionSendLogs() {
        AnalysisManager.sendLogs();
    }
}
