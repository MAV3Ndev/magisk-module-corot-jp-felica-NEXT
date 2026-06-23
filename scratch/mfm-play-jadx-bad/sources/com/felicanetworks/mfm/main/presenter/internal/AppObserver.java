package com.felicanetworks.mfm.main.presenter.internal;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import androidx.work.Configuration;
import com.felicanetworks.mfm.LockStatusReceiver;
import com.felicanetworks.mfm.main.LaunchManagementActivity;
import com.felicanetworks.mfm.main.MfiAccountSwitchingActivity;
import com.felicanetworks.mfm.main.ReceiveNfcTagActivity;
import com.felicanetworks.mfm.main.ServiceListActivity;
import com.felicanetworks.mfm.main.policy.PolicyManager;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.presenter.PresenterData;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;
import com.felicanetworks.mfm.main.view.activity.AccountChangeHistoryActivity;
import com.felicanetworks.mfm.main.view.activity.CardDetailActivity;
import com.felicanetworks.mfm.main.view.activity.CentralActivity;
import com.felicanetworks.mfm.main.view.activity.DeleteCardListActivity;
import com.felicanetworks.mfm.main.view.activity.ExtIcCardReaderActivity;
import com.felicanetworks.mfm.main.view.activity.FaqActivity;
import com.felicanetworks.mfm.main.view.activity.FpServiceListActivity;
import com.felicanetworks.mfm.main.view.activity.MemoryUsageActivity;
import com.felicanetworks.mfm.main.view.activity.NoticeDetailActivity;
import com.felicanetworks.mfm.main.view.activity.NoticeListActivity;
import com.felicanetworks.mfm.main.view.activity.RWSettingActivity;
import com.felicanetworks.mfm.main.view.activity.RecommendDetailActivity;
import com.felicanetworks.mfm.main.view.activity.SettingListActivity;
import com.felicanetworks.mfm.main.view.activity.SupportMenuActivity;
import com.felicanetworks.mfm.main.view.activity.TutorialActivity;
import com.felicanetworks.mfm.memory_clear.MemoryClearActivity;
import com.google.android.gms.security.ProviderInstaller;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class AppObserver extends Application implements Application.ActivityLifecycleCallbacks, Configuration.Provider {
    private MfmException _createException;
    private int _densityDpi;
    private int _fontWeightAdjustment;
    private Locale _locale;
    private ReaderModeManager _readerModeManager;
    private Activity _root;
    private final List<String> activityList = new ArrayList(Arrays.asList(ServiceListActivity.class.getName(), CentralActivity.class.getName(), TutorialActivity.class.getName(), RecommendDetailActivity.class.getName(), NoticeListActivity.class.getName(), NoticeDetailActivity.class.getName(), SupportMenuActivity.class.getName(), SettingListActivity.class.getName(), ExtIcCardReaderActivity.class.getName(), MemoryUsageActivity.class.getName(), AccountChangeHistoryActivity.class.getName(), FaqActivity.class.getName(), FpServiceListActivity.class.getName(), ReceiveNfcTagActivity.class.getName(), MfiAccountSwitchingActivity.class.getName(), CardDetailActivity.class.getName(), LaunchManagementActivity.class.getName(), RWSettingActivity.class.getName(), DeleteCardListActivity.class.getName()));
    private final List<Activity> initActivityList = new ArrayList();

    public boolean existServiceListActivity() {
        return this._root instanceof ServiceListActivity;
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (StateController.getAppPhase() == StateController.AppPhase.FOR_GROUND) {
            if (newConfig.densityDpi != this._densityDpi) {
                this._densityDpi = newConfig.densityDpi;
                StateController.postStateEvent(StateMachine.Event.EI_CONFIG_CHANGE, new Object[0]);
            } else if (Build.VERSION.SDK_INT >= 31 && newConfig.fontWeightAdjustment != this._fontWeightAdjustment) {
                this._fontWeightAdjustment = newConfig.fontWeightAdjustment;
                StateController.postStateEvent(StateMachine.Event.EI_CONFIG_CHANGE, new Object[0]);
            } else if (Build.VERSION.SDK_INT < 24) {
                checkChangeLocale(newConfig.locale);
            } else {
                checkChangeLocale(newConfig.getLocales().get(0));
            }
        }
    }

    private void checkChangeLocale(Locale newLocale) {
        try {
            Locale locale = this._locale;
            if (locale == null || newLocale == null || locale.equals(newLocale)) {
                return;
            }
            this._locale = newLocale;
            Sg.load(PresenterData.getInstance().getContext());
            StateController.postStateEvent(StateMachine.Event.EI_CONFIG_CHANGE, new Object[0]);
        } catch (Exception e) {
            MfmException mfmException = new MfmException(AppObserver.class, 131, e);
            LogUtil.error(mfmException);
            StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, mfmException);
        }
    }

    private void checkLockStatusChange() {
        if (LockStatusReceiver.isReceived()) {
            StateController.postStateEvent(StateMachine.Event.EI_LOCK_STATE_CHANGE, new Object[0]);
        }
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        try {
            ProviderInstaller.installIfNeededAsync(getApplicationContext(), new ProviderInstaller.ProviderInstallListener() { // from class: com.felicanetworks.mfm.main.presenter.internal.AppObserver.1
                @Override // com.google.android.gms.security.ProviderInstaller.ProviderInstallListener
                public void onProviderInstallFailed(int i, Intent intent) {
                }

                @Override // com.google.android.gms.security.ProviderInstaller.ProviderInstallListener
                public void onProviderInstalled() {
                }
            });
            if (Build.VERSION.SDK_INT < 24) {
                this._locale = getResources().getConfiguration().locale;
            } else {
                this._locale = getResources().getConfiguration().getLocales().get(0);
            }
            this._densityDpi = getResources().getConfiguration().densityDpi;
            if (Build.VERSION.SDK_INT >= 31) {
                this._fontWeightAdjustment = getResources().getConfiguration().fontWeightAdjustment;
            }
            PolicyManager.setup(getApplicationContext());
            ServicePreference.getInstance().setup(getApplicationContext());
        } catch (IllegalStateException e) {
            if (e.getCause() instanceof JSONException) {
                this._createException = new MfmException(StateController.class, 297, (JSONException) e.getCause());
            } else {
                this._createException = new MfmException(StateController.class, 296, e);
            }
        }
        registerActivityLifecycleCallbacks(this);
        StateController.initialize(this);
        StateController.postAppPhase(StateController.AppPhase.STOPPED, new Object[0]);
        PresenterData.getInstance().setContext(getApplicationContext());
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: com.felicanetworks.mfm.main.presenter.internal.AppObserver.2
            final Thread.UncaughtExceptionHandler savedUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

            @Override // java.lang.Thread.UncaughtExceptionHandler
            public void uncaughtException(Thread t, Throwable e2) {
                try {
                    LogUtil.uncaughtExceptionLog(new MfmException(Class.forName(e2.getStackTrace()[0].getClassName()), 4095, (Exception) e2, e2.toString()));
                } catch (Exception unused) {
                } catch (Throwable th) {
                    this.savedUncaughtExceptionHandler.uncaughtException(t, e2);
                    throw th;
                }
                this.savedUncaughtExceptionHandler.uncaughtException(t, e2);
            }
        });
        this._readerModeManager = new ReaderModeManager(this, this._createException);
    }

    @Override // android.app.Application
    public void onTerminate() {
        super.onTerminate();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        Locale locale;
        if ((activity instanceof ReceiveNfcTagActivity) || (activity instanceof LaunchManagementActivity) || !isTargetActivity(activity)) {
            return;
        }
        boolean z = activity instanceof ServiceListActivity;
        if (!z && !(activity instanceof MfiAccountSwitchingActivity) && this._root == null) {
            activity.finish();
            return;
        }
        AnalysisManager.stampCreate(activity);
        setInitActivityList(activity);
        if (((activity.getIntent().getFlags() & 32768) != 0 ? true : activity.isTaskRoot()) && z) {
            if (NfcAdapter.getDefaultAdapter(this) == null) {
                PresenterData.getInstance().setHasNfc(false);
            }
            this._root = activity;
            LockStatusReceiver.resetReceived();
            if (MemoryClearActivity.getRunning()) {
                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, new MfmException(AppObserver.class, 1), 10);
                return;
            }
            if (this._createException == null) {
                if (getResources().getConfiguration().densityDpi != this._densityDpi) {
                    this._densityDpi = getResources().getConfiguration().densityDpi;
                }
                if (Build.VERSION.SDK_INT >= 31 && getResources().getConfiguration().fontWeightAdjustment != this._fontWeightAdjustment) {
                    this._fontWeightAdjustment = getResources().getConfiguration().fontWeightAdjustment;
                }
                if (Build.VERSION.SDK_INT < 24) {
                    locale = getResources().getConfiguration().locale;
                } else {
                    locale = getResources().getConfiguration().getLocales().get(0);
                }
                if (!this._locale.equals(locale)) {
                    this._locale = locale;
                    Sg.load(PresenterData.getInstance().getContext());
                }
                if (PresenterData.getPossibleInterruptAppStart()) {
                    PresenterData.setPossibleInterruptAppStart(false);
                    initActivity();
                    StateController.initialize(activity.getApplicationContext());
                    try {
                        if (new Settings().isCheckInbound(getApplicationContext())) {
                            PresenterData.setPossibleInterruptAppStart(true);
                            StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, new MfmException(StateController.class, 817), 7);
                            return;
                        } else if (StateController.setupPreference()) {
                            StateController.postAppPhase(StateController.AppPhase.START, activity.getIntent());
                            return;
                        } else {
                            PresenterData.setPossibleInterruptAppStart(true);
                            StateController.postStateEvent(StateMachine.Event.EP_OUT_OF_MEMORY_ERROR, new MfmException(StateController.class, 536), 2);
                            return;
                        }
                    } catch (IllegalStateException e) {
                        PresenterData.setPossibleInterruptAppStart(true);
                        StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, new MfmException(StateController.class, 818, e), 7);
                        return;
                    }
                }
                return;
            }
            StateController.initialize(activity.getApplicationContext());
            if (this._createException.getFirstCaughtException() instanceof JSONException) {
                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, this._createException);
            } else {
                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, this._createException, 3);
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        if (isTargetActivity(activity)) {
            AnalysisManager.stampStart(activity);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        Locale locale;
        if (isTargetActivity(activity)) {
            AnalysisManager.stampResume(activity);
            if (activity instanceof ExtIcCardReaderActivity) {
                this._readerModeManager.disableForegroundDispatch(activity);
                this._readerModeManager.enableReaderMode(activity);
            } else if (isTargetOfEnableForegroundDispatchActivity(activity) && !ServicePreference.getInstance().loadRestrictCardReadSetting(getApplicationContext())) {
                if (activity instanceof FpServiceListActivity) {
                    this._readerModeManager.enableForegroundDispatchTimerStart(activity);
                } else {
                    this._readerModeManager.enableForegroundDispatch(activity);
                }
            }
            StateController.postAppPhase(StateController.AppPhase.FOR_GROUND, activity);
            if (getResources().getConfiguration().densityDpi != this._densityDpi) {
                this._densityDpi = getResources().getConfiguration().densityDpi;
                StateController.postStateEvent(StateMachine.Event.EI_CONFIG_CHANGE, new Object[0]);
            } else if (Build.VERSION.SDK_INT >= 31 && getResources().getConfiguration().fontWeightAdjustment != this._fontWeightAdjustment) {
                this._fontWeightAdjustment = getResources().getConfiguration().fontWeightAdjustment;
                StateController.postStateEvent(StateMachine.Event.EI_CONFIG_CHANGE, new Object[0]);
            } else {
                if (Build.VERSION.SDK_INT < 24) {
                    locale = getResources().getConfiguration().locale;
                } else {
                    locale = getResources().getConfiguration().getLocales().get(0);
                }
                checkChangeLocale(locale);
            }
            checkLockStatusChange();
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        if (isTargetActivity(activity)) {
            AnalysisManager.stampPause(activity);
            if (activity instanceof ExtIcCardReaderActivity) {
                this._readerModeManager.disableReaderMode(activity);
            }
            if (isTargetOfEnableForegroundDispatchActivity(activity)) {
                this._readerModeManager.disableForegroundDispatch(activity);
            }
            StateController.postAppPhase(StateController.AppPhase.BACK_GROUND, activity);
            if (activity.isFinishing()) {
                onFinished(activity);
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        if (isTargetActivity(activity)) {
            AnalysisManager.stampStop(activity);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        isTargetActivity(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        if (isTargetActivity(activity)) {
            AnalysisManager.stampDestroy(activity);
            onFinished(activity);
        }
    }

    private void onFinished(Activity activity) {
        if (activity.equals(this._root)) {
            this._root = null;
            StateController.initialize(this);
        }
    }

    public void onActivityNewIntent(Intent intent) {
        Tag tag;
        if (intent != null) {
            if (Build.VERSION.SDK_INT >= 33) {
                tag = (Tag) intent.getParcelableExtra("android.nfc.extra.TAG", Tag.class);
            } else {
                tag = (Tag) intent.getParcelableExtra("android.nfc.extra.TAG");
            }
            if (tag != null) {
                AnalysisManager.stamp(MfmStamp.Event.AUTO_DUMP_RECEIVE_TAG, tag);
                onTagDiscovered(tag);
            }
        }
    }

    public void onTagDiscovered(Tag tag) {
        StateController.tagDiscovered(tag);
    }

    private boolean isTargetOfEnableForegroundDispatchActivity(Activity activity) {
        return ((activity instanceof MfiAccountSwitchingActivity) || (activity instanceof ReceiveNfcTagActivity) || (activity instanceof LaunchManagementActivity)) ? false : true;
    }

    private boolean isTargetActivity(Activity activity) {
        return this.activityList.contains(activity.getClass().getName());
    }

    private void setInitActivityList(Activity activity) {
        if ((activity instanceof MfiAccountSwitchingActivity) && getPackageName().equals(activity.getCallingPackage())) {
            this.initActivityList.add(activity);
        }
    }

    private void initActivity() {
        for (Activity activity : this.initActivityList) {
            if (activity instanceof MfiAccountSwitchingActivity) {
                ((MfiAccountSwitchingActivity) activity).initialize();
            }
        }
        this.initActivityList.clear();
    }

    @Override // androidx.work.Configuration.Provider
    public Configuration getWorkManagerConfiguration() {
        return new Configuration.Builder().build();
    }
}
