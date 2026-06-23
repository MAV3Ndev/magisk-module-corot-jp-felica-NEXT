package com.felicanetworks.mfm;

import android.content.Context;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.felicanetworks.mfm.main.model.PushGateway;
import com.felicanetworks.mfm.main.model.internal.main.ModelContext;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.VersionUpProtocol;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.sg.Sg;

/* JADX INFO: loaded from: classes3.dex */
public class MfsMovementReceiverWorker extends Worker {
    private static final String ACTION_CHECK_OPERABLE = "com.felicanetworks.mfm.main.view.legacy.view.action.CHECK_OPERABLE";
    private static final String ACTION_SEND_LOGS = "com.felicanetworks.mfm.main.view.legacy.view.action.SEND_LOGS";
    private static OperableChecker _checker;

    private enum Key {
        KEY_ACTION
    }

    private static class OperableChecker {
        private Context _context;
        private boolean _isDoneCheck = false;
        private VersionUpProtocol.Result _result;

        public OperableChecker(Context c) {
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
                VersionUpProtocol.Result result = versionUpProtocol.parse(networkExpert.connect(versionUpProtocol.create(new VersionUpProtocol.Parameter(iIntValue, iIntValue2, iIntValue4, iIntValue3))));
                this._result = result;
                PushGateway.enableSendingMode(this._context, result.isPreventPush());
                AnalysisManager.setEnabled(!this._result.isPreventLogs());
                this._isDoneCheck = true;
            } catch (Exception unused) {
            }
        }

        public boolean isOperableMfm() {
            if (!this._isDoneCheck) {
                startVersionUpCheck();
            }
            return Integer.valueOf("1").intValue() != this._result.versionupInfo;
        }

        public boolean isOperableSendLog() {
            if (!this._isDoneCheck) {
                startVersionUpCheck();
            }
            return !this._result.isPreventLogs();
        }

        public boolean isOperableSetPush() {
            if (!this._isDoneCheck) {
                startVersionUpCheck();
            }
            return !this._result.isPreventPush();
        }
    }

    public static void startActionCheckOperable(Context context) {
        _checker = null;
        enqueue(context, ACTION_CHECK_OPERABLE);
    }

    public static void startActionSendLogs(Context context) {
        enqueue(context, ACTION_SEND_LOGS);
    }

    private static void enqueue(Context context, String action) {
        OneTimeWorkRequest.Builder builder = new OneTimeWorkRequest.Builder((Class<? extends ListenableWorker>) MfsMovementReceiverWorker.class);
        Data.Builder builder2 = new Data.Builder();
        builder2.putString(Key.KEY_ACTION.name(), action);
        builder.setInputData(builder2.build());
        WorkManager.getInstance(context).enqueue(builder.build());
    }

    public MfsMovementReceiverWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
        if (_checker == null) {
            _checker = new OperableChecker(context);
        }
    }

    @Override // androidx.work.Worker
    public ListenableWorker.Result doWork() {
        try {
            String string = getInputData().getString(Key.KEY_ACTION.name());
            if (_checker.isOperableMfm() && ACTION_SEND_LOGS.equals(string) && _checker.isOperableSendLog()) {
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
