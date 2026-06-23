package com.felicanetworks.mfm;

import android.content.Context;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.felicanetworks.mfm.main.model.internal.main.ModelContext;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.VersionUpProtocol;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.sg.Sg;

/* JADX INFO: loaded from: classes3.dex */
public class AppMovementReceiverWorker extends Worker {
    private static OperableChecker _checker;

    private static class OperableChecker {
        private Context _context;
        private boolean _isDoneCheck = false;
        private VersionUpProtocol.Result _result;

        OperableChecker(Context c) {
            this._context = c;
        }

        private void startVersionUpCheck() {
            try {
                int iIntValue = Integer.valueOf((String) Sg.getValue(Sg.Key.NET_AIM_VERSION_DEFAULT_PARAM_FACT_COUNT)).intValue();
                int iIntValue2 = Integer.valueOf((String) Sg.getValue(Sg.Key.NET_AIM_VERSION_DEFAULT_PARAM_MAX_COUNT)).intValue();
                int iIntValue3 = Integer.valueOf((String) Sg.getValue(Sg.Key.NET_AIM_VERSION_DEFAULT_PARAM_FACT_INTERVAL)).intValue();
                int iIntValue4 = Integer.valueOf((String) Sg.getValue(Sg.Key.NET_AIM_VERSION_DEFAULT_PARAM_INTERVAL)).intValue();
                NetworkExpert networkExpert = new NetworkExpert(new ModelContext(this._context));
                VersionUpProtocol versionUpProtocol = networkExpert.getVersionUpProtocol();
                this._result = versionUpProtocol.parse(networkExpert.connect(versionUpProtocol.create(new VersionUpProtocol.Parameter(iIntValue, iIntValue2, iIntValue4, iIntValue3))));
                AnalysisManager.setEnabled(!r0.isPreventLogs());
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
        WorkManager.getInstance(context).enqueue(new OneTimeWorkRequest.Builder((Class<? extends ListenableWorker>) AppMovementReceiverWorker.class).build());
    }

    public AppMovementReceiverWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
        if (_checker == null) {
            _checker = new OperableChecker(context);
        }
    }

    @Override // androidx.work.Worker
    public ListenableWorker.Result doWork() {
        try {
            if (_checker.isOperableMfm() && _checker.isOperableSendLog()) {
                handleActionSendLogs();
            }
            return ListenableWorker.Result.success();
        } catch (Exception unused) {
            return ListenableWorker.Result.failure();
        }
    }

    private void handleActionSendLogs() {
        AnalysisManager.sendLogs();
    }
}
