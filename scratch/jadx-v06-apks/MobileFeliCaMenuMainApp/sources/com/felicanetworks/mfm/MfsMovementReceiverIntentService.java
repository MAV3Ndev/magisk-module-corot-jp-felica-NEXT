package com.felicanetworks.mfm;

import android.content.Context;
import android.content.Intent;
import androidx.core.app.JobIntentService;
import com.felicanetworks.mfm.main.model.PushGateway;
import com.felicanetworks.mfm.main.model.info.NoticeInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeManager;
import com.felicanetworks.mfm.main.model.internal.main.ModelContext;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.VersionUpProtocol;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.presenter.internal.ServicePreference;
import com.felicanetworks.mfm.main.presenter.internal.notification.NotificationController;

/* JADX INFO: loaded from: classes.dex */
public class MfsMovementReceiverIntentService extends JobIntentService {
    private static final String ACTION_CHECK_OPERABLE = "com.felicanetworks.mfm.main.view.legacy.view.action.CHECK_OPERABLE";
    private static final String ACTION_SEND_LOGS = "com.felicanetworks.mfm.main.view.legacy.view.action.SEND_LOGS";
    private static final String ACTION_SET_PUSH = "com.felicanetworks.mfm.main.view.legacy.view.action.SET_PUSH";
    private static final int JOB_ID = 1;
    static final String LOCALPUSH_NOTICE_ID = "0";
    private static OperableChecker _checker;

    private static class OperableChecker {
        private Context _context;
        private boolean _isDoneCheck = false;
        private VersionUpProtocol.Result _result;

        public OperableChecker(Context context) {
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
        Intent intent = new Intent(context, (Class<?>) MfsMovementReceiverIntentService.class);
        intent.setAction(ACTION_CHECK_OPERABLE);
        enqueueWork(context, (Class<?>) MfsMovementReceiverIntentService.class, 1, intent);
    }

    public static void startActionSetPush(Context context) {
        Intent intent = new Intent(context, (Class<?>) MfsMovementReceiverIntentService.class);
        intent.setAction(ACTION_SET_PUSH);
        enqueueWork(context, (Class<?>) MfsMovementReceiverIntentService.class, 1, intent);
    }

    public static void startActionSendLogs(Context context) {
        Intent intent = new Intent(context, (Class<?>) MfsMovementReceiverIntentService.class);
        intent.setAction(ACTION_SEND_LOGS);
        enqueueWork(context, (Class<?>) MfsMovementReceiverIntentService.class, 1, intent);
    }

    public MfsMovementReceiverIntentService() {
        if (_checker == null) {
            _checker = new OperableChecker(this);
        }
    }

    @Override // androidx.core.app.JobIntentService
    protected void onHandleWork(Intent intent) {
        try {
            String action = intent.getAction();
            if (ACTION_SET_PUSH.equals(action)) {
                executeLocalPush();
            }
            if (_checker.isOperableMfm() && ACTION_SEND_LOGS.equals(action) && _checker.isOperableSendLog()) {
                handleActionSendLogs();
            }
        } catch (Exception unused) {
        }
    }

    private void executeLocalPush() throws MfmException {
        try {
            Context context = _checker._context;
            ServicePreference.getInstance().setup(context);
            if (ServicePreference.getInstance().loadTutorial(context)) {
                NotificationController.getInstance(context).notifyNotification(new NoticeInfo(NoticeManager.getInstance(context).getNoticeData("0"), new NetworkExpert(new ModelContext(context))));
            }
        } catch (Exception e) {
            throw new MfmException(getClass(), 2, e);
        }
    }

    private void handleActionSendLogs() {
        AnalysisManager.sendLogs();
    }
}
