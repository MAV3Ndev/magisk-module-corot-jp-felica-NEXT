package com.felicanetworks.mfm.main.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.ServiceListActivity;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.PresenterData;
import com.felicanetworks.mfm.main.presenter.RequestCaster;
import com.felicanetworks.mfm.main.presenter.action.IActionAppResult;
import com.felicanetworks.mfm.main.presenter.structure.CloseDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.CompleteTutorialDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.DismissStructure;
import com.felicanetworks.mfm.main.presenter.structure.FatalErrorDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.RebootStructure;
import com.felicanetworks.mfm.main.presenter.structure.RequireAppUpgradeDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.RequireMissingAppInstallDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.presenter.structure.StructureType;
import com.felicanetworks.mfm.main.view.RequestManager;
import com.felicanetworks.mfm.main.view.views.CustomDialogFragment;
import com.felicanetworks.mfm.main.view.views.DialogFactory;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BaseActivity extends AppCompatActivity implements RequestManager.RequestListener {
    public static String EXTRA_KEY_CALLING_ACTIVITY = "com.felicanetworks.mfm.main.calling.activity";
    public static String EXTRA_KEY_REBOOT = "com.felicanetworks.mfm.main.reboot";
    public static String TAG_FATAL_ERROR_DIALOG = "FatalError";
    public static String TAG_NORMAL_DIALOG = "Normal";
    public static String TAG_WARNING_DIALOG = "Warning";
    private static boolean mCorrectSequence = false;
    private boolean mFlagFirst;
    private ActivityStatus mActivityStatus = ActivityStatus.IDLE;
    private CustomDialogFragment mDialogFragment = null;
    private boolean mWaitingActivityResult = false;
    private boolean mIsReturnFromOtherScreen = false;
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity$$ExternalSyntheticLambda1
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
        @Override // androidx.activity.result.ActivityResultCallback
        public final void onActivityResult(Object obj) {
            this.f$0.m411x4dec8ac4((ActivityResult) obj);
        }
    });
    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity$$ExternalSyntheticLambda2
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
        @Override // androidx.activity.result.ActivityResultCallback
        public final void onActivityResult(Object obj) {
            this.f$0.m412x4f22dda3((Boolean) obj);
        }
    });

    public enum ActivityStatus {
        IDLE,
        BACKGROUND,
        FOREGROUND
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isFinishing()) {
            return;
        }
        boolean z = true;
        if (isTaskRoot()) {
            try {
                RequestManager.getInstance().initialize();
                mCorrectSequence = true;
            } catch (MfmException e) {
                handleInitializeError(e);
            }
        } else if (!mCorrectSequence) {
            finish();
            return;
        }
        RequestManager.getInstance().registerRequestListener(this);
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(z) { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.1
            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                BaseActivity.this.onBackPressedDispatch();
            }
        });
    }

    public void handleInitializeError(Exception ex) {
        notifyException(ex);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        RequestManager.getInstance().registerRequestListener(this);
        setActivityStatus(ActivityStatus.FOREGROUND);
        if (!this.mFlagFirst) {
            dispatchStructureOnResume(getCurrentRequest());
        } else {
            this.mFlagFirst = false;
        }
        CustomDialogFragment customDialogFragment = this.mDialogFragment;
        if (customDialogFragment != null) {
            showDialog(customDialogFragment);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        setActivityStatus(ActivityStatus.BACKGROUND);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        setActivityStatus(ActivityStatus.BACKGROUND);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        setActivityStatus(ActivityStatus.BACKGROUND);
        RequestManager.getInstance().unregisterRequestListener(this);
    }

    public void onBackPressedDispatch() {
        backPressedDispatch();
    }

    protected void backPressedDispatch() {
        try {
            Structure currentRequest = getCurrentRequest();
            if (currentRequest instanceof CloseDrawStructure) {
                ((CloseDrawStructure) currentRequest).actClose();
            }
        } catch (Exception e) {
            notifyException(e);
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        try {
            super.onNewIntent(intent);
            if (!intent.hasExtra(EXTRA_KEY_CALLING_ACTIVITY) || !intent.getStringExtra(EXTRA_KEY_CALLING_ACTIVITY).equals(NoticeListActivity.class.getName())) {
                setReturnFromOtherScreen(true);
            }
            Structure currentRequest = getCurrentRequest();
            if (currentRequest != null) {
                currentRequest.detectOnNewIntent(intent);
            }
            setIntent(intent);
        } catch (Exception e) {
            notifyException(e);
        }
    }

    public boolean isWaitingActivityResult() {
        return this.mWaitingActivityResult;
    }

    private void setWaitingActivityResult(boolean flag) {
        this.mWaitingActivityResult = flag;
    }

    /* JADX INFO: renamed from: lambda$new$0$com-felicanetworks-mfm-main-view-activity-BaseActivity, reason: not valid java name */
    /* synthetic */ void m411x4dec8ac4(ActivityResult activityResult) {
        try {
            if (isWaitingActivityResult()) {
                setWaitingActivityResult(false);
                Object currentRequest = getCurrentRequest();
                if (currentRequest instanceof IActionAppResult) {
                    ((IActionAppResult) currentRequest).actResult(activityResult.getResultCode(), activityResult.getData());
                }
            }
        } catch (Exception e) {
            notifyException(e);
        }
    }

    protected void activityResultLaunch(Intent intent) {
        try {
            this.activityResultLauncher.launch(intent);
            setWaitingActivityResult(true);
        } catch (Exception unused) {
            CustomDialogFragment customDialogFragmentShowWarningDialog = showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
            customDialogFragmentShowWarningDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.2
                @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                public boolean onClick() {
                    try {
                        Object currentRequest = BaseActivity.this.getCurrentRequest();
                        if (!(currentRequest instanceof IActionAppResult)) {
                            return true;
                        }
                        ((IActionAppResult) currentRequest).actResult(0, null);
                        return true;
                    } catch (Exception e) {
                        BaseActivity.this.notifyException(e);
                        return true;
                    }
                }
            });
        }
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
        RequestManager.getInstance().unregisterRequestListener(this);
    }

    public void setActivityStatus(ActivityStatus activityStatus) {
        this.mActivityStatus = activityStatus;
    }

    public ActivityStatus getActivityStatus() {
        return this.mActivityStatus;
    }

    public Structure getCurrentRequest() {
        return RequestCaster.getLastRequestStructure();
    }

    public void appEnd(final DismissStructure structure) {
        finish();
        if (isTaskRoot()) {
            structure.actCompleted();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v6, resolved type: T */
    /* JADX WARN: Multi-variable type inference failed */
    public void appReboot(final RebootStructure structure) {
        RequestManager.getInstance().unregisterAllRequestListener();
        Intent intent = new Intent(this, (Class<?>) ServiceListActivity.class);
        intent.setFlags(268468224);
        if (structure.getExtraInfo() != null) {
            if (PresenterData.EXTRA_KEY_SPECIFIC_RECOMMEND.equals(structure.getExtraInfo().key)) {
                intent.putExtra(structure.getExtraInfo().key, ((Boolean) structure.getExtraInfo().value).booleanValue());
            } else if (structure.getExtraInfo().value instanceof Parcelable) {
                intent.putExtra(structure.getExtraInfo().key, (Parcelable) structure.getExtraInfo().value);
            }
        }
        if (structure.getAction() != null) {
            intent.setAction(structure.getAction());
        }
        intent.putExtra(EXTRA_KEY_REBOOT, true);
        startActivity(intent);
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(0, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        } else {
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
    }

    public CustomDialogFragment showWarningDialog(int resId) {
        return showWarningDialog(getString(resId), "");
    }

    public CustomDialogFragment showWarningDialog(String message, String errorCode) {
        CustomDialogFragment customDialogFragmentCreateSingleChoiceDialog = DialogFactory.createSingleChoiceDialog(getApplicationContext());
        customDialogFragmentCreateSingleChoiceDialog.setText(message);
        customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_WARNING_DIALOG);
        customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.3
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                return true;
            }
        });
        showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
        LogUtil.inquiryLog(getApplicationContext(), errorCode);
        return customDialogFragmentCreateSingleChoiceDialog;
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x02b8  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x02ce  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x02e5  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0305  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x031f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void showFatalError(final FatalErrorDrawStructure structure) {
        final String str;
        boolean z;
        String errorCode;
        CustomDialogFragment customDialogFragmentCreateSingleChoiceDialog = DialogFactory.createSingleChoiceDialog(getApplicationContext());
        StringBuilder sb = new StringBuilder();
        switch (AnonymousClass11.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[structure.getErrorType().ordinal()]) {
            case 1:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_unsupported_device));
                str = "dlg_error_unsupported_device";
                z = false;
                if (!z) {
                    AnalysisManager.stamp(MfmStamp.Event.SCREEN_FATAL, str, structure.getMfmException());
                }
                if (structure.withErrorCode()) {
                    errorCode = "";
                } else {
                    sb.append(getString(R.string.dlg_error_code, new Object[]{structure.getErrorCode()}));
                    errorCode = structure.getErrorCode();
                }
                if (structure.hasMfcErrorCode()) {
                    sb.append(getString(R.string.dlg_error_code_mfc, new Object[]{structure.getMfcErrorCode()}));
                }
                if (sb.length() > 0) {
                    customDialogFragmentCreateSingleChoiceDialog.setText(sb.toString());
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                    customDialogFragmentCreateSingleChoiceDialog.setNegativeText(getString(R.string.button_text_retry));
                    customDialogFragmentCreateSingleChoiceDialog.setNegativeButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.5
                        @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                        public boolean onClick() {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_RETRY, str);
                            structure.actRetry();
                            return true;
                        }
                    });
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 2:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_exhaustion_of_data_drive));
                str = "dlg_error_exhaustion_of_data_drive";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 3:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_failed_to_confirm_app_upgrade));
                str = "dlg_error_failed_to_confirm_app_upgrade";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 4:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_fatal_error));
                str = "dlg_error_fatal_error";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 5:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_no_permission_to_activate_mfc));
                str = "dlg_error_no_permission_to_activate_mfc";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 6:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_felica_chip_time_out));
                str = "dlg_error_felica_chip_time_out";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 7:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_unknown_mfc_error));
                str = "dlg_error_unknown_mfc_error";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 8:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_unknown_database_error));
                str = "dlg_error_unknown_database_error";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 9:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_locked_felica_chip));
                sb.append(getString(R.string.dlg_error_locked_felica_chip));
                str = "dlg_error_locked_felica_chip";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 10:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_used_mfc_by_other_app));
                str = "dlg_error_used_mfc_by_other_app";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 11:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_running_by_mfic));
                str = "dlg_error_running_by_mfic";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 12:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_felica_open));
                str = "dlg_error_felica_open";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 13:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_felica_invalid_response));
                str = "dlg_error_felica_invalid_response";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 14:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_sg_load_failed));
                str = "dlg_error_sg_load_failed";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 15:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_felica_http_error));
                str = "dlg_error_felica_http_error";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 16:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_mfc_disabled_error));
                str = "dlg_error_mfc_disabled_error";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 17:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_mfs_disabled_error));
                str = "dlg_error_mfs_disabled_error";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 18:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_mfs_signature_unmatched_error));
                str = "dlg_error_mfs_signature_unmatched_error";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 19:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_incompatible_destination_error));
                sb.append(getString(R.string.dlg_error_incompatible_destination_error));
                str = "dlg_error_incompatible_destination_error";
                z = true;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 20:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_policy_site_error));
                sb.append(getString(R.string.dlg_error_policy_site_error));
                str = "dlg_error_policy_site_error";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 21:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_policy_site_server_error));
                sb.append(getString(R.string.dlg_error_policy_site_server_error));
                str = "dlg_error_policy_site_server_error";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 22:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_invalid_token_request));
                str = "dlg_error_invalid_token_request";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 23:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_text_mfi_server_maintenance));
                str = "dlg_error_text_mfi_server_maintenance";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 24:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_text_network_failed));
                str = "dlg_error_text_network_failed";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 25:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_text_pia_playservice_error));
                str = "dlg_error_text_pia_playservice_error";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 26:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_text_pia_playstore_error));
                str = "dlg_error_text_pia_playstore_error";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 27:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_text_pia_integrity_error));
                str = "dlg_error_text_pia_integrity_error";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 28:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_text_unsupported_device));
                str = "dlg_error_text_unsupported_device";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 29:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_text_memory_clear_unsupported_os));
                str = "dlg_error_text_memory_clear_unsupported_os";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            case 30:
                customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
                sb.append(getString(R.string.dlg_error_text_memory_clear_running));
                str = "dlg_error_text_memory_clear_running";
                z = false;
                if (!z) {
                }
                if (structure.withErrorCode()) {
                }
                if (structure.hasMfcErrorCode()) {
                }
                if (sb.length() > 0) {
                }
                customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
                customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.4
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        if (structure.isRetry()) {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_DIALOG_CLOSE, str);
                        }
                        structure.actClose();
                        return true;
                    }
                });
                if (structure.isRetry()) {
                }
                showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
                LogUtil.inquiryLog(getApplicationContext(), errorCode);
                return;
            default:
                throw new IllegalArgumentException("ErrorType is not correct value :" + structure.getErrorType());
        }
    }

    public void dismissDialog(String tag) {
        CustomDialogFragment customDialogFragment = (CustomDialogFragment) getSupportFragmentManager().findFragmentByTag(tag);
        if (customDialogFragment != null) {
            customDialogFragment.dismiss();
        }
    }

    public void dismissAllDialog() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof DialogFragment) {
                ((DialogFragment) fragment).dismiss();
            }
        }
    }

    public boolean isShowFragment() {
        return getSupportFragmentManager().getFragments().size() > 0;
    }

    public void notifyException(Exception ex) {
        Structure currentRequest = getCurrentRequest();
        if (currentRequest != null) {
            currentRequest.detectException(ex);
        }
    }

    public void showErrorDialog(CustomDialogFragment dialogFragment) {
        CustomDialogFragment customDialogFragment = (CustomDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_NORMAL_DIALOG);
        if (customDialogFragment != null && dialogFragment.getTargetTag().equals(TAG_FATAL_ERROR_DIALOG)) {
            customDialogFragment.dismiss();
        }
        CustomDialogFragment customDialogFragment2 = (CustomDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_WARNING_DIALOG);
        if (customDialogFragment2 != null) {
            if (dialogFragment.getTargetTag().equals(TAG_WARNING_DIALOG)) {
                return;
            } else {
                customDialogFragment2.dismiss();
            }
        }
        CustomDialogFragment customDialogFragment3 = (CustomDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_FATAL_ERROR_DIALOG);
        if (customDialogFragment3 != null) {
            if (!dialogFragment.getTargetTag().equals(TAG_FATAL_ERROR_DIALOG)) {
                return;
            } else {
                customDialogFragment3.dismiss();
            }
        }
        showDialog(dialogFragment);
    }

    public void showNormalDialog(CustomDialogFragment dialogFragment) {
        CustomDialogFragment customDialogFragment = (CustomDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_NORMAL_DIALOG);
        if (customDialogFragment != null) {
            customDialogFragment.dismiss();
        }
        showDialog(dialogFragment);
    }

    public void showDialog(CustomDialogFragment dialogFragment) {
        if (getActivityStatus() == ActivityStatus.FOREGROUND) {
            if (!dialogFragment.isDismissed()) {
                FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransactionBeginTransaction.add(dialogFragment, dialogFragment.getTargetTag());
                fragmentTransactionBeginTransaction.commitAllowingStateLoss();
            }
            this.mDialogFragment = null;
            return;
        }
        this.mDialogFragment = dialogFragment;
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.activity.BaseActivity$11, reason: invalid class name */
    static /* synthetic */ class AnonymousClass11 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType;

        static {
            int[] iArr = new int[StructureType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType = iArr;
            try {
                iArr[StructureType.DISMISS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.REBOOT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.FATAL_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.APP_SETUP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[FatalErrorDrawStructure.ErrorType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType = iArr2;
            try {
                iArr2[FatalErrorDrawStructure.ErrorType.UNSUPPORTED_DEVICE.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.EXHAUSTION_OF_DATA_DRIVE.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.FAILED_TO_CONFIRM_APP_UPGRADE.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.UNKNOWN_ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.NO_PERMISSION_TO_ACTIVATE_MFC.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.FELICA_CHIP_TIME_OUT.ordinal()] = 6;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.UNKNOWN_MFC_ERROR.ordinal()] = 7;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.UNKNOWN_DATABASE_ERROR.ordinal()] = 8;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.LOCKED_FELICA_CHIP.ordinal()] = 9;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.USED_MFC_BY_OTHER_APP.ordinal()] = 10;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.RUNNING_BY_MFIC.ordinal()] = 11;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.FELICA_OPEN_ERROR.ordinal()] = 12;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.FELICA_INVALID_RESPONSE_ERROR.ordinal()] = 13;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.SG_LOAD_ERROR.ordinal()] = 14;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.FELICA_HTTP_ERROR.ordinal()] = 15;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.MFC_DISABLED_ERROR.ordinal()] = 16;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.MFS_DISABLED_ERROR.ordinal()] = 17;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.MFS_SIGNATURE_UNMATCHED_ERROR.ordinal()] = 18;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.INBOUND_INCOMPATIBLE_ERROR.ordinal()] = 19;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.TERMS_OF_SERVICE_LOADING_ERROR.ordinal()] = 20;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.TERMS_OF_SERVICE_SERVER_ERROR.ordinal()] = 21;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.INVALID_REQUEST_TOKEN_ERROR.ordinal()] = 22;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.MFIC_SERVER_MAINTENANCE_ERROR.ordinal()] = 23;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.NETWORK_FAILED.ordinal()] = 24;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_SERVICE.ordinal()] = 25;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_STORE.ordinal()] = 26;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.PIA_PLAY_INTEGRITY_NONRECOVERABLE_FAILED.ordinal()] = 27;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.MFIC_UNSUPPORTED_DEVICE_ERROR.ordinal()] = 28;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.MEMORY_CLEAR_NONSUPPORT_ERROR.ordinal()] = 29;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FatalErrorDrawStructure$ErrorType[FatalErrorDrawStructure.ErrorType.MEMORY_CLEAR_RUNNING_ERROR.ordinal()] = 30;
            } catch (NoSuchFieldError unused34) {
            }
        }
    }

    protected void dispatchStructureOnResume(Structure structure) {
        if (structure != null && AnonymousClass11.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()] == 1) {
            appEnd((DismissStructure) structure);
        }
    }

    protected void dispatchLatestStructure(Structure structure) {
        dispatchStructure(structure);
    }

    protected void dispatchStructure(Structure structure) {
        if (structure == null) {
            return;
        }
        int i = AnonymousClass11.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()];
        if (i == 1) {
            appEnd((DismissStructure) structure);
            return;
        }
        if (i == 2) {
            appReboot((RebootStructure) structure);
            return;
        }
        if (i == 3) {
            showFatalError((FatalErrorDrawStructure) structure);
        } else if (i == 4) {
            appReboot(new RebootStructure());
        } else {
            throw new IllegalStateException("Not support StructureType : " + structure.getType().name());
        }
    }

    @Override // com.felicanetworks.mfm.main.view.RequestManager.RequestListener
    public void onRequest(Structure structure) {
        try {
            dispatchStructure(structure);
        } catch (Exception e) {
            structure.detectException(e);
        }
    }

    public boolean isReturnFromOtherScreen() {
        return this.mIsReturnFromOtherScreen;
    }

    public void setReturnFromOtherScreen(boolean flag) {
        this.mIsReturnFromOtherScreen = flag;
    }

    public void showRequireAppUpgrade(final RequireAppUpgradeDrawStructure requireAppUpgradeDrawStructure) {
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_01_04, new Object[0]);
        CustomDialogFragment customDialogFragmentCreateSelectDialog = DialogFactory.createSelectDialog(getApplicationContext());
        customDialogFragmentCreateSelectDialog.setTargetTag(TAG_NORMAL_DIALOG);
        customDialogFragmentCreateSelectDialog.setTitle(getString(R.string.dlg_title_version_up_confirmation));
        customDialogFragmentCreateSelectDialog.setText(getString(R.string.dlg_text_version_up_confirmation));
        customDialogFragmentCreateSelectDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.6
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_REQUEST_APP_UPDATE_YES, new Object[0]);
                try {
                    BaseActivity.this.startActivity(requireAppUpgradeDrawStructure.getDefaultIntent());
                    requireAppUpgradeDrawStructure.actPositive();
                    return true;
                } catch (Exception e) {
                    requireAppUpgradeDrawStructure.detectException(e);
                    return true;
                }
            }
        });
        customDialogFragmentCreateSelectDialog.setNegativeButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.7
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_REQUEST_APP_UPDATE_NO, new Object[0]);
                try {
                    requireAppUpgradeDrawStructure.actNegative();
                    return true;
                } catch (Exception e) {
                    requireAppUpgradeDrawStructure.detectException(e);
                    return true;
                }
            }
        });
        showNormalDialog(customDialogFragmentCreateSelectDialog);
    }

    public void showRequireAppInstall(final RequireMissingAppInstallDrawStructure structure) {
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_01_08, structure);
        CustomDialogFragment customDialogFragmentCreateSelectDialog = DialogFactory.createSelectDialog(getApplicationContext());
        customDialogFragmentCreateSelectDialog.setTargetTag(TAG_NORMAL_DIALOG);
        customDialogFragmentCreateSelectDialog.setTitle(getString(R.string.dlg_title_version_up_confirmation));
        int missingApp = structure.getMissingApp();
        if (missingApp == 1) {
            customDialogFragmentCreateSelectDialog.setText(getString(R.string.dlg_text_install_mfc_confirmation));
        } else if (missingApp == 2) {
            customDialogFragmentCreateSelectDialog.setText(getString(R.string.dlg_text_install_mfs_confirmation));
        } else {
            structure.detectException(new Exception());
        }
        customDialogFragmentCreateSelectDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.8
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_REQUEST_APP_INSTALL, structure, true);
                try {
                    BaseActivity.this.startActivity(structure.getMissingAppIntent());
                    structure.actPositive();
                } catch (Exception e) {
                    structure.detectException(e);
                }
                return true;
            }
        });
        customDialogFragmentCreateSelectDialog.setNegativeButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.9
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_REQUEST_APP_INSTALL, structure, false);
                try {
                    structure.actNegative();
                } catch (Exception e) {
                    structure.detectException(e);
                }
                return true;
            }
        });
        showNormalDialog(customDialogFragmentCreateSelectDialog);
    }

    protected boolean checkNotificationPermission() {
        return Build.VERSION.SDK_INT >= 33 && checkSelfPermission("android.permission.POST_NOTIFICATIONS") == -1;
    }

    public void showRequestNotificationPermissionDialog() {
        CustomDialogFragment customDialogFragmentCreateSingleChoiceDialog = DialogFactory.createSingleChoiceDialog(getApplicationContext());
        customDialogFragmentCreateSingleChoiceDialog.setText(getString(R.string.dlg_text_request_notification_permission));
        customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_NORMAL_DIALOG);
        customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.10
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                if (Build.VERSION.SDK_INT < 33) {
                    return true;
                }
                BaseActivity.this.requestPermissionLauncher.launch("android.permission.POST_NOTIFICATIONS");
                return true;
            }
        });
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_01_16, new Object[0]);
        showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
    }

    /* JADX INFO: renamed from: lambda$new$1$com-felicanetworks-mfm-main-view-activity-BaseActivity, reason: not valid java name */
    /* synthetic */ void m412x4f22dda3(Boolean bool) {
        Structure currentRequest = getCurrentRequest();
        if (currentRequest instanceof CompleteTutorialDrawStructure) {
            ((CompleteTutorialDrawStructure) currentRequest).actCompleted();
        }
    }
}
