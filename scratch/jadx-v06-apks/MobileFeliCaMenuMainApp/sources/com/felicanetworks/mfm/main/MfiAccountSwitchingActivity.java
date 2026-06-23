package com.felicanetworks.mfm.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import androidx.appcompat.app.AppCompatActivity;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDataManager;
import com.felicanetworks.mfm.main.model.internal.main.ModelContext;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.presenter.internal.AppObserver;
import com.felicanetworks.mfm.main.presenter.internal.MfiPreference;
import com.felicanetworks.mfm.main.presenter.internal.ServicePreference;
import com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess;
import com.felicanetworks.mfm.main.view.views.DialogFactory;
import com.felicanetworks.mfm.main.view.views.MfiDialogFragment;
import java.util.Locale;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes.dex */
public class MfiAccountSwitchingActivity extends AppCompatActivity {
    private static final int DELAY = 1000;
    private static final int END_DIALOG_FAILURE = 5;
    private static final int END_DIALOG_FAILURE_MULTIPLE = 6;
    private static final int END_DIALOG_SUCCESS = 4;
    private static final int ERR_INDEX_MULTIPLE = 1;
    public static int MFIC_RESULT_SUCCESS = 1;
    private static final int PROCESSING_DIALOG = 3;
    public static int REQUEST_CODE_MFIC_REQUEST_ACTIVITY = 9000;
    public static final int RESULT_MFIC_VERSION_ERROR = 3;
    public static final int RESULT_MULTIPLE_ACTIVATION = 2;
    public static final int RESULT_SWITCHING_CANCEL = 0;
    public static final int RESULT_SWITCHING_FAILED = 1;
    public static final int RESULT_SWITCHING_SUCCESS = -1;
    private static final int START_DIALOG_HAS_ACCOUNT = 1;
    private static final int START_DIALOG_NO_ACCOUNT = 2;
    private static final String TAG_MFI_DIALOG = "mfiAccountSwitchingActivityDialog";
    private static int _staticCnt;
    private String _accountName;
    private Activity _activityForAnalysis;
    private String _callingPackage;
    private Context _context;
    private MfiDialogFragment _dialog;
    private MyHandler _handler;
    private Thread _mfiRunner;
    private boolean _isFinishOperation = false;
    CountDownLatch latchForSilentStart = null;
    CountDownLatch latchForStartActivityForResult = null;
    boolean isRequestActivity = false;
    int codeForSecondSilentStart = 0;
    MfcException mfcExceptionWithSilentStart = null;
    Intent intentWithSilentStart = null;
    int resultCodeWithMficRequestActivity = 0;
    String mficLoginErrorInfo = null;

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        try {
            try {
                setResult(0);
                this._handler = new MyHandler();
                int i = _staticCnt + 1;
                _staticCnt = i;
                if (1 < i) {
                    setResult(2);
                    showDialogFailure(new MfmException(MfiAccountSwitchingActivity.class, 1, new IllegalStateException("Failed to start due to multiple activation")));
                    return;
                }
                this._context = getApplicationContext();
                this._activityForAnalysis = this;
                this._callingPackage = getCallingPackage();
                super.onCreate(bundle);
                if (hasAccount()) {
                    showDialogHasAccount();
                } else {
                    showDialogNoAccount();
                }
            } finally {
                super.onCreate(bundle);
            }
        } catch (Exception e) {
            setResult(1);
            showDialogFailure(new MfmException(MfiAccountSwitchingActivity.class, 2, e));
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        this._handler.resume();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        this._handler.pause();
        if (isFinishing()) {
            finishOperation();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        finishOperation();
    }

    public boolean isSingleActivation() {
        return 1 >= _staticCnt;
    }

    public void initialize() {
        Thread thread = this._mfiRunner;
        if (thread == null || !thread.isAlive()) {
            return;
        }
        mfiTreadInterrupt();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mfiTreadInterrupt() {
        this._mfiRunner.interrupt();
        CountDownLatch countDownLatch = this.latchForSilentStart;
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    private void showDialogHasAccount() {
        MfiDialogFragment mfiDialogFragmentCreateSelectMfiDialog = DialogFactory.createSelectMfiDialog(this);
        this._dialog = mfiDialogFragmentCreateSelectMfiDialog;
        mfiDialogFragmentCreateSelectMfiDialog.setMfiDialog();
        this._dialog.setTitle(getString(R.string.text_title_login));
        this._dialog.setMfiCharWithDesctiption2(getString(R.string.text_account_login), this._accountName, null, getString(R.string.text_account_login_description));
        this._dialog.setPositiveButtonEnable(true);
        this._dialog.setPositiveButtonWrapSize(true);
        this._dialog.setPositiveText(getString(R.string.button_change_login));
        this._dialog.setPositiveButtonListener(new MfiDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.MfiAccountSwitchingActivity.1
            @Override // com.felicanetworks.mfm.main.view.views.MfiDialogFragment.OnClickListener
            public boolean onClick() {
                MfiAccountSwitchingActivity.this.startMfi();
                return true;
            }
        });
        this._dialog.setNegativeButtonEnable(true);
        this._dialog.setNegativeText(getString(R.string.button_back_login));
        this._dialog.setNegativeButtonListener(new MfiDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.MfiAccountSwitchingActivity.2
            @Override // com.felicanetworks.mfm.main.view.views.MfiDialogFragment.OnClickListener
            public boolean onClick() {
                MfiAccountSwitchingActivity.this.finish();
                return true;
            }
        });
        MyHandler myHandler = this._handler;
        myHandler.sendMessageDelayed(myHandler.obtainMessage(1, new SendData(this._dialog, this, getCallingPackage())), 1000L);
    }

    private void showDialogNoAccount() {
        MfiDialogFragment mfiDialogFragmentCreateSelectMfiDialog = DialogFactory.createSelectMfiDialog(this);
        this._dialog = mfiDialogFragmentCreateSelectMfiDialog;
        mfiDialogFragmentCreateSelectMfiDialog.setMfiDialog();
        this._dialog.setTitle(getString(R.string.text_title_login));
        this._dialog.setMfiChar(null, null, getString(R.string.text_not_login));
        this._dialog.setPositiveButtonEnable(true);
        this._dialog.setPositiveButtonWrapSize(true);
        this._dialog.setPositiveText(getString(R.string.button_connect_login));
        this._dialog.setPositiveButtonListener(new MfiDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.MfiAccountSwitchingActivity.3
            @Override // com.felicanetworks.mfm.main.view.views.MfiDialogFragment.OnClickListener
            public boolean onClick() {
                MfiAccountSwitchingActivity.this.startMfi();
                return true;
            }
        });
        this._dialog.setNegativeButtonEnable(true);
        this._dialog.setNegativeText(getString(R.string.button_back_login));
        this._dialog.setNegativeButtonListener(new MfiDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.MfiAccountSwitchingActivity.4
            @Override // com.felicanetworks.mfm.main.view.views.MfiDialogFragment.OnClickListener
            public boolean onClick() {
                MfiAccountSwitchingActivity.this.finish();
                return true;
            }
        });
        MyHandler myHandler = this._handler;
        myHandler.sendMessageDelayed(myHandler.obtainMessage(2, new SendData(this._dialog, this, getCallingPackage())), 1000L);
    }

    private void showDialogProcessing() {
        MfiDialogFragment mfiDialogFragmentCreateCancelMfiDialog = DialogFactory.createCancelMfiDialog(this);
        this._dialog = mfiDialogFragmentCreateCancelMfiDialog;
        mfiDialogFragmentCreateCancelMfiDialog.setMfiCancelChar(getString(R.string.dlg_text_processing_login), getString(R.string.button_text_login_cancel));
        this._dialog.setCancelMfiButtonListener(new MfiDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.MfiAccountSwitchingActivity.5
            @Override // com.felicanetworks.mfm.main.view.views.MfiDialogFragment.OnClickListener
            public boolean onClick() {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_CANCEL_SWITCH_ACCOUNT, MfiAccountSwitchingActivity.this._callingPackage, MfiAccountSwitchingActivity.this._activityForAnalysis);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.felicanetworks.mfm.main.MfiAccountSwitchingActivity.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        MfiAccountSwitchingActivity.this.mfiTreadInterrupt();
                    }
                }, 1000L);
                return true;
            }
        });
        MyHandler myHandler = this._handler;
        myHandler.sendMessageDelayed(myHandler.obtainMessage(3, new SendData(this._dialog, this, getCallingPackage())), 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDialogSuccess() {
        MfiDialogFragment mfiDialogFragmentCreateSelectMfiDialog = DialogFactory.createSelectMfiDialog(this);
        this._dialog = mfiDialogFragmentCreateSelectMfiDialog;
        mfiDialogFragmentCreateSelectMfiDialog.setMfiDialog();
        this._dialog.setTitle(getString(R.string.text_title_login));
        this._dialog.setMfiChar(getString(R.string.text_switched_account_login), MfiPreference.getInstance(this).loadMfiAccountName(), null);
        this._dialog.setPositiveButtonEnable(true);
        this._dialog.setPositiveButtonWrapSize(false);
        this._dialog.setPositiveText(getString(R.string.button_confirmation_login));
        this._dialog.setPositiveButtonListener(new MfiDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.MfiAccountSwitchingActivity.6
            @Override // com.felicanetworks.mfm.main.view.views.MfiDialogFragment.OnClickListener
            public boolean onClick() {
                MfiAccountSwitchingActivity.this.finish();
                return true;
            }
        });
        this._dialog.setNegativeButtonEnable(false);
        MyHandler myHandler = this._handler;
        myHandler.sendMessageDelayed(myHandler.obtainMessage(4, new SendData(this._dialog, this, getCallingPackage())), 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDialogFailure(MfmException mfmException) {
        Object errorCode = mfmException.getErrorCode();
        String mfcErrorCode = mfmException.getFirstCaughtException() instanceof MfcException ? ((MfcException) mfmException.getFirstCaughtException()).getMfcErrorCode() : null;
        MfiDialogFragment mfiDialogFragmentCreateSelectMfiDialog = DialogFactory.createSelectMfiDialog(this);
        this._dialog = mfiDialogFragmentCreateSelectMfiDialog;
        mfiDialogFragmentCreateSelectMfiDialog.setTitle(getString(R.string.text_title_login));
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.dlg_warning_login));
        sb.append(getString(R.string.dlg_warning_code_login, new Object[]{errorCode}));
        if (!TextUtils.isEmpty(mfcErrorCode)) {
            sb.append(getString(R.string.dlg_warning_code_mfi_login, new Object[]{mfcErrorCode}));
        }
        this._dialog.setText(sb);
        this._dialog.setPositiveButtonEnable(true);
        this._dialog.setPositiveButtonWrapSize(false);
        this._dialog.setPositiveText(getString(R.string.button_confirmation_login));
        this._dialog.setPositiveButtonListener(new MfiDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.MfiAccountSwitchingActivity.7
            @Override // com.felicanetworks.mfm.main.view.views.MfiDialogFragment.OnClickListener
            public boolean onClick() {
                MfiAccountSwitchingActivity.this.finish();
                return true;
            }
        });
        this._dialog.setNegativeButtonEnable(false);
        if (1 == mfmException.getIndex()) {
            MyHandler myHandler = this._handler;
            myHandler.sendMessageDelayed(myHandler.obtainMessage(6, new SendData(this._dialog, this, getCallingPackage())), 1000L);
        } else {
            MyHandler myHandler2 = this._handler;
            myHandler2.sendMessageDelayed(myHandler2.obtainMessage(5, new SendData(this._dialog, this, getCallingPackage())), 1000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDialogFailure(String str) {
        MfiDialogFragment mfiDialogFragmentCreateSelectMfiDialog = DialogFactory.createSelectMfiDialog(this);
        this._dialog = mfiDialogFragmentCreateSelectMfiDialog;
        mfiDialogFragmentCreateSelectMfiDialog.setTitle(getString(R.string.text_title_login));
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.dlg_warning_login));
        sb.append(str);
        this._dialog.setText(sb);
        this._dialog.setPositiveButtonEnable(true);
        this._dialog.setPositiveButtonWrapSize(false);
        this._dialog.setPositiveText(getString(R.string.button_confirmation_login));
        this._dialog.setPositiveButtonListener(new MfiDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.MfiAccountSwitchingActivity.8
            @Override // com.felicanetworks.mfm.main.view.views.MfiDialogFragment.OnClickListener
            public boolean onClick() {
                MfiAccountSwitchingActivity.this.finish();
                return true;
            }
        });
        this._dialog.setNegativeButtonEnable(false);
        MyHandler myHandler = this._handler;
        myHandler.sendMessageDelayed(myHandler.obtainMessage(5, new SendData(this._dialog, this, getCallingPackage())), 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDialogMFCDisabled() {
        MfiDialogFragment mfiDialogFragmentCreateSelectMfiDialog = DialogFactory.createSelectMfiDialog(this);
        this._dialog = mfiDialogFragmentCreateSelectMfiDialog;
        mfiDialogFragmentCreateSelectMfiDialog.setTitle(getString(R.string.dlg_title_error));
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.dlg_error_mfc_disabled_error));
        this._dialog.setText(sb);
        this._dialog.setPositiveButtonEnable(true);
        this._dialog.setPositiveButtonWrapSize(false);
        this._dialog.setPositiveText(this._context.getString(R.string.button_text_close));
        this._dialog.setPositiveButtonListener(new MfiDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.MfiAccountSwitchingActivity.9
            @Override // com.felicanetworks.mfm.main.view.views.MfiDialogFragment.OnClickListener
            public boolean onClick() {
                MfiAccountSwitchingActivity.this.finish();
                return true;
            }
        });
        this._dialog.setNegativeButtonEnable(false);
        MyHandler myHandler = this._handler;
        myHandler.sendMessageDelayed(myHandler.obtainMessage(5, new SendData(this._dialog, this, getCallingPackage())), 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDialogMFCUninstalled() {
        MfiDialogFragment mfiDialogFragmentCreateSelectMfiDialog = DialogFactory.createSelectMfiDialog(this);
        this._dialog = mfiDialogFragmentCreateSelectMfiDialog;
        mfiDialogFragmentCreateSelectMfiDialog.setTitle(getString(R.string.dlg_title_version_up_confirmation));
        this._dialog.setText(getString(R.string.dlg_text_install_mfc_confirmation));
        this._dialog.setPositiveButtonEnable(true);
        this._dialog.setNegativeButtonEnable(true);
        this._dialog.setPositiveButtonListener(new MfiDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.MfiAccountSwitchingActivity.10
            @Override // com.felicanetworks.mfm.main.view.views.MfiDialogFragment.OnClickListener
            public boolean onClick() {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_REQUEST_APP_UPDATE_YES, new Object[0]);
                try {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse(String.format("market://details?id=%s", (String) Sg.getValue(Sg.Key.SETTING_MFC_PACKAGE_NAME))));
                    intent.setPackage((String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_APP_PKG_PLAY_STORE));
                    MfiAccountSwitchingActivity.this.startActivity(intent);
                } catch (Exception unused) {
                } catch (Throwable th) {
                    MfiAccountSwitchingActivity.this.finish();
                    throw th;
                }
                MfiAccountSwitchingActivity.this.finish();
                return true;
            }
        });
        this._dialog.setNegativeButtonListener(new MfiDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.MfiAccountSwitchingActivity.11
            @Override // com.felicanetworks.mfm.main.view.views.MfiDialogFragment.OnClickListener
            public boolean onClick() {
                MfiAccountSwitchingActivity.this.finish();
                return false;
            }
        });
        MyHandler myHandler = this._handler;
        myHandler.sendMessageDelayed(myHandler.obtainMessage(5, new SendData(this._dialog, this, getCallingPackage())), 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startMfi() {
        setResult(1);
        showDialogProcessing();
        try {
            Thread thread = new Thread(new Runnable() { // from class: com.felicanetworks.mfm.main.MfiAccountSwitchingActivity.12
                @Override // java.lang.Runnable
                public void run() {
                    ModelContext modelContext = new ModelContext(MfiAccountSwitchingActivity.this._context);
                    final MfcExpert mfcExpert = new MfcExpert(modelContext);
                    try {
                        try {
                            try {
                                int i = AnonymousClass13.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcExpert$MfcStatus[mfcExpert.confirmExistMfc(MfiAccountSwitchingActivity.this._context.getPackageManager()).ordinal()];
                                if (i == 1) {
                                    MfiAccountSwitchingActivity.this.showDialogMFCDisabled();
                                } else if (i == 2) {
                                    MfiAccountSwitchingActivity.this.showDialogMFCUninstalled();
                                } else if (i == 3) {
                                    mfcExpert.initialize();
                                    mfcExpert.setSilentStartCode(0);
                                    MfiAccountSwitchingActivity.this.clearAccountInfo(modelContext);
                                    boolean z = true;
                                    while (true) {
                                        MfiAccountSwitchingActivity.this.latchForSilentStart = new CountDownLatch(1);
                                        MfiAccountSwitchingActivity.this.isRequestActivity = false;
                                        MfiAccountSwitchingActivity.this.mfcExceptionWithSilentStart = null;
                                        MfiAccountSwitchingActivity.this.intentWithSilentStart = null;
                                        mfcExpert.mfiAccountClearStart(z, TextUtils.equals(MfiAccountSwitchingActivity.this._callingPackage, MfiAccountSwitchingActivity.this.getPackageName()), new MfcExpert.MfiStartListener() { // from class: com.felicanetworks.mfm.main.MfiAccountSwitchingActivity.12.1
                                            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                                            public void onSuccessNoLogin() {
                                            }

                                            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                                            public void onSuccess(boolean z2) {
                                                MfiAccountSwitchingActivity.this.latchForSilentStart.countDown();
                                            }

                                            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                                            public void onRequestActivity(Intent intent) {
                                                MfiAccountSwitchingActivity.this.isRequestActivity = true;
                                                MfiAccountSwitchingActivity.this.intentWithSilentStart = intent;
                                                mfcExpert.finishFeliCaAccess();
                                                MfiAccountSwitchingActivity.this.latchForSilentStart.countDown();
                                            }

                                            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                                            public void errorResult(MfcException mfcException) {
                                                MfiAccountSwitchingActivity.this.mfcExceptionWithSilentStart = mfcException;
                                                MfiAccountSwitchingActivity.this.latchForSilentStart.countDown();
                                            }
                                        });
                                        try {
                                            MfiAccountSwitchingActivity.this.latchForSilentStart.await();
                                        } catch (InterruptedException unused) {
                                        }
                                        if (MfiAccountSwitchingActivity.this.isRequestActivity) {
                                            MfiAccountSwitchingActivity.this.latchForStartActivityForResult = new CountDownLatch(1);
                                            try {
                                                MfiAccountSwitchingActivity.this.startActivityForResult(MfiAccountSwitchingActivity.this.intentWithSilentStart, MfiAccountSwitchingActivity.REQUEST_CODE_MFIC_REQUEST_ACTIVITY);
                                                try {
                                                    MfiAccountSwitchingActivity.this.latchForStartActivityForResult.await();
                                                } catch (InterruptedException unused2) {
                                                }
                                                if (MfiAccountSwitchingActivity.this.resultCodeWithMficRequestActivity != MfiAccountSwitchingActivity.MFIC_RESULT_SUCCESS) {
                                                    MfiAccountSwitchingActivity.this.mficLoginErrorInfo = String.format(Locale.US, "%n[%s.%02d.%03d]", "04", Integer.valueOf(MfiAccountSwitchingActivity.this.resultCodeWithMficRequestActivity), Integer.valueOf(MfiAccountSwitchingActivity.this.codeForSecondSilentStart));
                                                    break;
                                                } else {
                                                    mfcExpert.setSilentStartCode(MfiAccountSwitchingActivity.this.codeForSecondSilentStart);
                                                    z = false;
                                                }
                                            } catch (Exception e) {
                                                MfiAccountSwitchingActivity.this.latchForStartActivityForResult.countDown();
                                                throw e;
                                            }
                                        } else if (MfiAccountSwitchingActivity.this.mfcExceptionWithSilentStart != null) {
                                            throw MfiAccountSwitchingActivity.this.mfcExceptionWithSilentStart;
                                        }
                                    }
                                    if (MfiAccountSwitchingActivity.this.mficLoginErrorInfo != null) {
                                        MfiAccountSwitchingActivity.this.setResult(1);
                                        MfiAccountSwitchingActivity.this.showDialogFailure(MfiAccountSwitchingActivity.this.mficLoginErrorInfo);
                                    } else if (mfcExpert.isAvailable()) {
                                        if (MfiPreference.getInstance(MfiAccountSwitchingActivity.this._context).loadMfiAccountName() != null) {
                                            ServicePreference.getInstance().saveAccountChangeHistoryList(MfiAccountSwitchingActivity.this._context, "01");
                                            MfiAccountSwitchingActivity.this.setResult(-1);
                                            MfiAccountSwitchingActivity.this.showDialogSuccess();
                                        } else {
                                            MfiAccountSwitchingActivity.this.setResult(1);
                                            MfiAccountSwitchingActivity.this.showDialogFailure(new MfmException(MfiAccountSwitchingActivity.class, 258, new IllegalStateException("no account")));
                                        }
                                    } else {
                                        MfiAccountSwitchingActivity.this.setResult(1);
                                        MfiAccountSwitchingActivity.this.showDialogFailure(new MfmException(MfiAccountSwitchingActivity.class, 257, new InterruptedException()));
                                    }
                                }
                            } catch (Exception e2) {
                                MfiAccountSwitchingActivity.this.setResult(1);
                                MfiAccountSwitchingActivity.this.showDialogFailure(new MfmException(MfiAccountSwitchingActivity.class, 262, e2));
                            }
                        } catch (DatabaseException e3) {
                            MfiAccountSwitchingActivity.this.setResult(1);
                            MfiAccountSwitchingActivity.this.showDialogFailure(new MfmException(MfiAccountSwitchingActivity.class, 263, e3, e3.getMessage()));
                        } catch (MfcException e4) {
                            MfiAccountSwitchingActivity.this.showDialogFailure(new MfmException(MfiAccountSwitchingActivity.class, 259, e4));
                            if (e4.isRequiredUpgrade()) {
                                MfiAccountSwitchingActivity.this.setResult(3);
                            }
                        }
                    } finally {
                        mfcExpert.finishFeliCaAccess();
                        mfcExpert.deinitialize();
                    }
                }
            });
            this._mfiRunner = thread;
            thread.start();
        } catch (Exception e) {
            showDialogFailure(new MfmException(MfiAccountSwitchingActivity.class, 261, e));
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.MfiAccountSwitchingActivity$13, reason: invalid class name */
    static /* synthetic */ class AnonymousClass13 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcExpert$MfcStatus;

        static {
            int[] iArr = new int[MfcExpert.MfcStatus.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcExpert$MfcStatus = iArr;
            try {
                iArr[MfcExpert.MfcStatus.MFC_DISABLED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcExpert$MfcStatus[MfcExpert.MfcStatus.MFC_UNINSTALLED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcExpert$MfcStatus[MfcExpert.MfcStatus.MFC_ENABLED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private void finishOperation() {
        if (this._isFinishOperation) {
            return;
        }
        _staticCnt--;
        this._isFinishOperation = true;
        Thread thread = this._mfiRunner;
        if (thread == null || !thread.isAlive()) {
            return;
        }
        mfiTreadInterrupt();
    }

    private boolean hasAccount() throws Throwable {
        String strLoadMfiAccountName = MfiPreference.getInstance(this).loadMfiAccountName();
        this._accountName = strLoadMfiAccountName;
        return (strLoadMfiAccountName == null || strLoadMfiAccountName.isEmpty()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAccountInfo(ModelContext modelContext) throws DatabaseException {
        DatabaseExpert databaseExpert = new DatabaseExpert(modelContext);
        databaseExpert.open().deleteAccountRelatedInfo();
        MyServiceDatabaseAccess.getInstance().deleteRelatedToAccount();
        NoticeDataManager.getInstance(this._context).invalidateDispService();
        if (!((AppObserver) getApplication()).existServiceListActivity()) {
            databaseExpert.close();
        }
        ServicePreference.getInstance().saveAccountChangeHistoryReceiveCount(modelContext.getAndroidContext(), 0);
    }

    private static class SendData {
        private final MfiAccountSwitchingActivity activity;
        private final String callingPackage;
        private final MfiDialogFragment mfiDialogFragment;

        SendData(MfiDialogFragment mfiDialogFragment, MfiAccountSwitchingActivity mfiAccountSwitchingActivity, String str) {
            this.mfiDialogFragment = mfiDialogFragment;
            this.activity = mfiAccountSwitchingActivity;
            this.callingPackage = str;
        }

        public String toString() {
            return "SendData{mfiDialogFragment=" + this.mfiDialogFragment + ", activity=" + this.activity + ", callingPackage='" + this.callingPackage + "'}";
        }

        MfiDialogFragment getMfiDialogFragment() {
            return this.mfiDialogFragment;
        }

        MfiAccountSwitchingActivity getActivity() {
            return this.activity;
        }

        String getCallingPackage() {
            return this.callingPackage;
        }
    }

    private static class MyHandler extends Handler {
        private final Vector<Message> messageQueueBuffer;
        private boolean paused;

        MyHandler() {
            super(Looper.getMainLooper());
            this.paused = true;
            this.messageQueueBuffer = new Vector<>();
        }

        final void processMessage(Message message) {
            if (message.obj instanceof SendData) {
                SendData sendData = (SendData) message.obj;
                MfiAccountSwitchingActivity activity = sendData.getActivity();
                String callingPackage = sendData.getCallingPackage();
                MfiDialogFragment mfiDialogFragment = sendData.getMfiDialogFragment();
                if (activity.isDestroyed() || activity.isFinishing()) {
                    return;
                }
                MfmStamp.Event event = null;
                switch (message.what) {
                    case 1:
                    case 2:
                        event = MfmStamp.Event.SCREEN_W1_16_01;
                        break;
                    case 3:
                        event = MfmStamp.Event.SCREEN_W1_16_03;
                        break;
                    case 4:
                    case 5:
                        event = MfmStamp.Event.SCREEN_W1_16_02;
                        break;
                    case 6:
                        event = MfmStamp.Event.SCREEN_W1_16_02_MULTIPLE;
                        break;
                }
                AnalysisManager.stamp(event, callingPackage, activity);
                MfiDialogFragment mfiDialogFragment2 = (MfiDialogFragment) activity.getSupportFragmentManager().findFragmentByTag(MfiAccountSwitchingActivity.TAG_MFI_DIALOG);
                if (mfiDialogFragment2 != null) {
                    mfiDialogFragment2.dismiss();
                }
                mfiDialogFragment.setTargetTag(MfiAccountSwitchingActivity.TAG_MFI_DIALOG);
                mfiDialogFragment.show(activity.getSupportFragmentManager(), mfiDialogFragment.getTargetTag());
            }
        }

        final void resume() {
            this.paused = false;
            while (this.messageQueueBuffer.size() > 0) {
                Message messageLastElement = this.messageQueueBuffer.lastElement();
                this.messageQueueBuffer.removeAllElements();
                sendMessage(messageLastElement);
            }
        }

        final void pause() {
            this.paused = true;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (this.paused) {
                Message message2 = new Message();
                message2.copyFrom(message);
                this.messageQueueBuffer.add(message2);
                return;
            }
            processMessage(message);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == REQUEST_CODE_MFIC_REQUEST_ACTIVITY) {
            this.resultCodeWithMficRequestActivity = i2;
            this.codeForSecondSilentStart = intent.getIntExtra("code", 0);
        }
        CountDownLatch countDownLatch = this.latchForStartActivityForResult;
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }
}
