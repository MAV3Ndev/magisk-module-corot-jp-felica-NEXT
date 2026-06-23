package com.felicanetworks.mfm.main.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.ServiceListActivity;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.presenter.PresenterData;
import com.felicanetworks.mfm.main.presenter.RequestCaster;
import com.felicanetworks.mfm.main.presenter.action.IActionAppResult;
import com.felicanetworks.mfm.main.presenter.structure.CloseDrawStructure;
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

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseActivity extends AppCompatActivity implements RequestManager.RequestListener {
    public static String EXTRA_KEY_CALLING_ACTIVITY = "com.felicanetworks.mfm.main.calling.activity";
    public static String EXTRA_KEY_REBOOT = "com.felicanetworks.mfm.main.reboot";
    public static int REQUEST_CODE_FELICA_LOCK_APP = 1000;
    public static int REQUEST_CODE_MFI_ACCOUNT_SWITCHING = 3000;
    public static int REQUEST_CODE_MFI_LOGIN = 4000;
    public static int REQUEST_CODE_MFS_APP = 2000;
    public static String TAG_FATAL_ERROR_DIALOG = "FatalError";
    public static String TAG_NORMAL_DIALOG = "Normal";
    public static String TAG_WARNING_DIALOG = "Warning";
    private static boolean mCorrectSequence = false;
    private boolean mFlagFirst;
    private ActivityStatus mActivityStatus = ActivityStatus.IDLE;
    private CustomDialogFragment mDialogFragment = null;
    private boolean mWaitingActivityResult = false;
    private boolean mIsReturnFromOtherScreen = false;

    public enum ActivityStatus {
        IDLE,
        BACKGROUND,
        FOREGROUND
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (isFinishing()) {
            return;
        }
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
    }

    public void handleInitializeError(Exception exc) {
        notifyException(exc);
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

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        try {
            Structure currentRequest = getCurrentRequest();
            if (currentRequest == null || !(currentRequest instanceof CloseDrawStructure)) {
                return;
            }
            ((CloseDrawStructure) currentRequest).actClose();
        } catch (Exception e) {
            notifyException(e);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
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

    private void setWaitingActivityResult(boolean z) {
        this.mWaitingActivityResult = z;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        try {
            super.onActivityResult(i, i2, intent);
            if (isWaitingActivityResult()) {
                setWaitingActivityResult(false);
                int i3 = i & 65535;
                Object currentRequest = getCurrentRequest();
                if ((i3 == REQUEST_CODE_FELICA_LOCK_APP || i3 == REQUEST_CODE_MFS_APP || i3 == REQUEST_CODE_MFI_ACCOUNT_SWITCHING || i3 == REQUEST_CODE_MFI_LOGIN) && (currentRequest instanceof IActionAppResult)) {
                    ((IActionAppResult) currentRequest).actResult(i2, intent);
                }
            }
        } catch (Exception e) {
            notifyException(e);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void startActivityForResult(Intent intent, int i, Bundle bundle) {
        if (i >= 0) {
            try {
                super.startActivityForResult(intent, i, bundle);
                setWaitingActivityResult(true);
                return;
            } catch (Exception unused) {
                CustomDialogFragment customDialogFragmentShowWarningDialog = showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                customDialogFragmentShowWarningDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.1
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
                return;
            }
        }
        super.startActivityForResult(intent, i, bundle);
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

    public void appEnd(DismissStructure dismissStructure) {
        finish();
        if (isTaskRoot()) {
            dismissStructure.actCompleted();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void appReboot(RebootStructure rebootStructure) {
        RequestManager.getInstance().unregisterAllRequestListener();
        Intent intent = new Intent(this, (Class<?>) ServiceListActivity.class);
        intent.setFlags(268468224);
        if (rebootStructure.getExtraInfo() != null) {
            if (PresenterData.EXTRA_KEY_SPECIFIC_RECOMMEND.equals(rebootStructure.getExtraInfo().key)) {
                intent.putExtra(rebootStructure.getExtraInfo().key, ((Boolean) rebootStructure.getExtraInfo().value).booleanValue());
            } else if (rebootStructure.getExtraInfo().value instanceof Parcelable) {
                intent.putExtra(rebootStructure.getExtraInfo().key, (Parcelable) rebootStructure.getExtraInfo().value);
            }
        }
        if (rebootStructure.getAction() != null) {
            intent.setAction(rebootStructure.getAction());
        }
        intent.putExtra(EXTRA_KEY_REBOOT, true);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public CustomDialogFragment showWarningDialog(int i) {
        return showWarningDialog(getString(i));
    }

    public CustomDialogFragment showWarningDialog(String str) {
        CustomDialogFragment customDialogFragmentCreateSingleChoiceDialog = DialogFactory.createSingleChoiceDialog(getApplicationContext());
        customDialogFragmentCreateSingleChoiceDialog.setText(str);
        customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_WARNING_DIALOG);
        customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.2
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                return true;
            }
        });
        showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
        return customDialogFragmentCreateSingleChoiceDialog;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0213  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0241  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0259  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0273  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void showFatalError(final com.felicanetworks.mfm.main.presenter.structure.FatalErrorDrawStructure r9) {
        /*
            Method dump skipped, instruction units count: 698
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.view.activity.BaseActivity.showFatalError(com.felicanetworks.mfm.main.presenter.structure.FatalErrorDrawStructure):void");
    }

    public void dismissDialog(String str) {
        CustomDialogFragment customDialogFragment = (CustomDialogFragment) getSupportFragmentManager().findFragmentByTag(str);
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

    public void notifyException(Exception exc) {
        Structure currentRequest = getCurrentRequest();
        if (currentRequest != null) {
            currentRequest.detectException(exc);
        }
    }

    public void showErrorDialog(CustomDialogFragment customDialogFragment) {
        CustomDialogFragment customDialogFragment2 = (CustomDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_NORMAL_DIALOG);
        if (customDialogFragment2 != null && customDialogFragment.getTargetTag().equals(TAG_FATAL_ERROR_DIALOG)) {
            customDialogFragment2.dismiss();
        }
        CustomDialogFragment customDialogFragment3 = (CustomDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_WARNING_DIALOG);
        if (customDialogFragment3 != null) {
            if (customDialogFragment.getTargetTag().equals(TAG_WARNING_DIALOG)) {
                return;
            } else {
                customDialogFragment3.dismiss();
            }
        }
        CustomDialogFragment customDialogFragment4 = (CustomDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_FATAL_ERROR_DIALOG);
        if (customDialogFragment4 != null) {
            if (!customDialogFragment.getTargetTag().equals(TAG_FATAL_ERROR_DIALOG)) {
                return;
            } else {
                customDialogFragment4.dismiss();
            }
        }
        showDialog(customDialogFragment);
    }

    public void showNormalDialog(CustomDialogFragment customDialogFragment) {
        CustomDialogFragment customDialogFragment2 = (CustomDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_NORMAL_DIALOG);
        if (customDialogFragment2 != null) {
            customDialogFragment2.dismiss();
        }
        showDialog(customDialogFragment);
    }

    public void showDialog(CustomDialogFragment customDialogFragment) {
        if (getActivityStatus() == ActivityStatus.FOREGROUND) {
            if (!customDialogFragment.isDismissed()) {
                FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransactionBeginTransaction.add(customDialogFragment, customDialogFragment.getTargetTag());
                fragmentTransactionBeginTransaction.commitAllowingStateLoss();
            }
            this.mDialogFragment = null;
            return;
        }
        this.mDialogFragment = customDialogFragment;
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.activity.BaseActivity$9, reason: invalid class name */
    static /* synthetic */ class AnonymousClass9 {
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
        }
    }

    protected void dispatchStructureOnResume(Structure structure) {
        if (structure != null && AnonymousClass9.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()] == 1) {
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
        int i = AnonymousClass9.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()];
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
        } else {
            if (i == 4) {
                appReboot(new RebootStructure());
                return;
            }
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

    public void setReturnFromOtherScreen(boolean z) {
        this.mIsReturnFromOtherScreen = z;
    }

    public void showRequireAppUpgrade(final RequireAppUpgradeDrawStructure requireAppUpgradeDrawStructure) {
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_01_04, new Object[0]);
        CustomDialogFragment customDialogFragmentCreateSelectDialog = DialogFactory.createSelectDialog(getApplicationContext());
        customDialogFragmentCreateSelectDialog.setTargetTag(TAG_NORMAL_DIALOG);
        customDialogFragmentCreateSelectDialog.setTitle(getString(R.string.dlg_title_version_up_confirmation));
        customDialogFragmentCreateSelectDialog.setText(getString(R.string.dlg_text_version_up_confirmation));
        customDialogFragmentCreateSelectDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.5
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
        customDialogFragmentCreateSelectDialog.setNegativeButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.6
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

    public void showRequireAppInstall(final RequireMissingAppInstallDrawStructure requireMissingAppInstallDrawStructure) {
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_01_08, requireMissingAppInstallDrawStructure);
        CustomDialogFragment customDialogFragmentCreateSelectDialog = DialogFactory.createSelectDialog(getApplicationContext());
        customDialogFragmentCreateSelectDialog.setTargetTag(TAG_NORMAL_DIALOG);
        customDialogFragmentCreateSelectDialog.setTitle(getString(R.string.dlg_title_version_up_confirmation));
        int missingApp = requireMissingAppInstallDrawStructure.getMissingApp();
        if (missingApp == 1) {
            customDialogFragmentCreateSelectDialog.setText(getString(R.string.dlg_text_install_mfc_confirmation));
        } else if (missingApp == 2) {
            customDialogFragmentCreateSelectDialog.setText(getString(R.string.dlg_text_install_mfs_confirmation));
        } else {
            requireMissingAppInstallDrawStructure.detectException(new Exception());
        }
        customDialogFragmentCreateSelectDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.7
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_REQUEST_APP_INSTALL, requireMissingAppInstallDrawStructure, true);
                try {
                    BaseActivity.this.startActivity(requireMissingAppInstallDrawStructure.getMissingAppIntent());
                    requireMissingAppInstallDrawStructure.actPositive();
                } catch (Exception e) {
                    requireMissingAppInstallDrawStructure.detectException(e);
                }
                return true;
            }
        });
        customDialogFragmentCreateSelectDialog.setNegativeButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.BaseActivity.8
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_REQUEST_APP_INSTALL, requireMissingAppInstallDrawStructure, false);
                try {
                    requireMissingAppInstallDrawStructure.actNegative();
                } catch (Exception e) {
                    requireMissingAppInstallDrawStructure.detectException(e);
                }
                return true;
            }
        });
        showNormalDialog(customDialogFragmentCreateSelectDialog);
    }
}
