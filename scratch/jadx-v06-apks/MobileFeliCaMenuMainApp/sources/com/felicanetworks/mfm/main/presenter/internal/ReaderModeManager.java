package com.felicanetworks.mfm.main.presenter.internal;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Looper;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
public class ReaderModeManager {
    private static final int ENABLE_FOREGROUNDDISPATCH_WAIT_TIMER = 3000;
    private static final int TIMEOUT = 30000;
    private final Context mContext;
    private final MfmException mCreateException;
    private EnableForegroundDispatchWaitTimer mEnableForegroundDispatchWaitTimer;
    private ReaderModeAutoDisableTimer mTimer;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final Handler mEnableForegroundDispatchWaitHandler = new Handler(Looper.getMainLooper());

    public ReaderModeManager(Context context, MfmException mfmException) {
        this.mContext = context;
        this.mCreateException = mfmException;
    }

    public void enableForegroundDispatch(Activity activity) {
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(this.mContext);
        if (defaultAdapter != null) {
            try {
                defaultAdapter.enableForegroundDispatch(activity, PendingIntent.getActivity(this.mContext.getApplicationContext(), 2000, new Intent(activity.getApplicationContext(), activity.getClass()), 268435456), null, (String[][]) null);
            } catch (Exception e) {
                MfmException mfmException = new MfmException(ReaderModeManager.class, 121, e);
                LogUtil.error(mfmException);
                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, mfmException);
            }
        }
    }

    public void disableForegroundDispatch(Activity activity) {
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(this.mContext);
        if (defaultAdapter != null) {
            try {
                defaultAdapter.disableForegroundDispatch(activity);
                if (this.mEnableForegroundDispatchWaitTimer != null) {
                    this.mEnableForegroundDispatchWaitTimer.cancel();
                    this.mEnableForegroundDispatchWaitTimer = null;
                }
            } catch (Exception e) {
                MfmException mfmException = new MfmException(ReaderModeManager.class, 276, e);
                LogUtil.error(mfmException);
                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, mfmException);
            }
        }
    }

    public void enableReaderMode(Activity activity) {
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(this.mContext);
        if (defaultAdapter == null) {
            return;
        }
        try {
            defaultAdapter.enableReaderMode(activity, new NfcAdapter.ReaderCallback() { // from class: com.felicanetworks.mfm.main.presenter.internal.ReaderModeManager.1
                @Override // android.nfc.NfcAdapter.ReaderCallback
                public void onTagDiscovered(Tag tag) {
                    if (tag != null) {
                        StateController.tagDiscovered(tag);
                    }
                }
            }, 4, null);
            ReaderModeAutoDisableTimer readerModeAutoDisableTimer = new ReaderModeAutoDisableTimer(activity);
            this.mTimer = readerModeAutoDisableTimer;
            readerModeAutoDisableTimer.schedule(new ReaderModeAutoDisableTimerTask(), 30000L);
        } catch (Exception e) {
            MfmException mfmException = new MfmException(ReaderModeManager.class, 355, e);
            LogUtil.error(mfmException);
            StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, mfmException);
        }
    }

    public void disableReaderMode(Activity activity) {
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(this.mContext);
        if (defaultAdapter == null) {
            return;
        }
        try {
            defaultAdapter.disableReaderMode(activity);
            this.mTimer.cancel();
        } catch (Exception e) {
            MfmException mfmException = new MfmException(ReaderModeManager.class, TypedValues.Cycle.TYPE_CURVE_FIT, e);
            LogUtil.error(mfmException);
            StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, mfmException);
        }
    }

    public void enableForegroundDispatchTimerStart(Activity activity) {
        try {
            EnableForegroundDispatchWaitTimer enableForegroundDispatchWaitTimer = new EnableForegroundDispatchWaitTimer(activity);
            this.mEnableForegroundDispatchWaitTimer = enableForegroundDispatchWaitTimer;
            enableForegroundDispatchWaitTimer.schedule(new EnableForegroundDispatchWaitTimerTask(), 3000L);
        } catch (Exception e) {
            MfmException mfmException = new MfmException(ReaderModeManager.class, 515, e);
            LogUtil.error(mfmException);
            StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, mfmException);
        }
    }

    private static class ReaderModeAutoDisableTimer extends Timer {
        private final Activity mActivity;

        public ReaderModeAutoDisableTimer(Activity activity) {
            super(ReaderModeAutoDisableTimer.class.getSimpleName(), false);
            this.mActivity = activity;
        }

        public Activity getActivity() {
            return this.mActivity;
        }
    }

    private class ReaderModeAutoDisableTimerTask extends TimerTask {
        private ReaderModeAutoDisableTimerTask() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            ReaderModeManager.this.mHandler.post(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.internal.ReaderModeManager.ReaderModeAutoDisableTimerTask.1
                @Override // java.lang.Runnable
                public void run() {
                    Activity activity = ReaderModeManager.this.mTimer.getActivity();
                    if (activity != null) {
                        ReaderModeManager.this.disableReaderMode(activity);
                        ReaderModeManager.this.enableForegroundDispatch(activity);
                    }
                    ReaderModeManager.this.mTimer.cancel();
                }
            });
        }
    }

    private static class EnableForegroundDispatchWaitTimer extends Timer {
        private final Activity mActivity;

        public EnableForegroundDispatchWaitTimer(Activity activity) {
            super(EnableForegroundDispatchWaitTimer.class.getSimpleName(), false);
            this.mActivity = activity;
        }

        public Activity getActivity() {
            return this.mActivity;
        }
    }

    private class EnableForegroundDispatchWaitTimerTask extends TimerTask {
        private EnableForegroundDispatchWaitTimerTask() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            ReaderModeManager.this.mEnableForegroundDispatchWaitHandler.post(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.internal.ReaderModeManager.EnableForegroundDispatchWaitTimerTask.1
                @Override // java.lang.Runnable
                public void run() {
                    if (ReaderModeManager.this.mEnableForegroundDispatchWaitTimer == null) {
                        return;
                    }
                    Activity activity = ReaderModeManager.this.mEnableForegroundDispatchWaitTimer.getActivity();
                    if (activity != null) {
                        ReaderModeManager.this.enableForegroundDispatch(activity);
                    }
                    ReaderModeManager.this.mEnableForegroundDispatchWaitTimer.cancel();
                    ReaderModeManager.this.mEnableForegroundDispatchWaitTimer = null;
                }
            });
        }
    }
}
