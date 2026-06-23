package com.felicanetworks.mfm.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.felicanetworks.mfm.main.model.info.NoticeInfo;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.fix.MfiLoginResultCode;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.internal.notification.NotificationController;
import com.felicanetworks.mfm.main.presenter.structure.AppSetupDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.AskAppUpgradeDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.CloseDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.CompleteTutorialDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.MfiLoginDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.MfsAppStructure;
import com.felicanetworks.mfm.main.presenter.structure.RebootStructure;
import com.felicanetworks.mfm.main.presenter.structure.RequireAppUpgradeDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.RequireMissingAppInstallDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.presenter.structure.StructureType;
import com.felicanetworks.mfm.main.presenter.structure.TermsOfServiceDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.TutorialOverviewDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.TutorialSettingsDrawStructure;
import com.felicanetworks.mfm.main.view.activity.BaseActivity;
import com.felicanetworks.mfm.main.view.activity.CardDetailActivity;
import com.felicanetworks.mfm.main.view.activity.CentralActivity;
import com.felicanetworks.mfm.main.view.activity.DeleteCardListActivity;
import com.felicanetworks.mfm.main.view.activity.ExtIcCardReaderActivity;
import com.felicanetworks.mfm.main.view.activity.NoticeDetailActivity;
import com.felicanetworks.mfm.main.view.activity.NoticeListActivity;
import com.felicanetworks.mfm.main.view.activity.RecommendDetailActivity;
import com.felicanetworks.mfm.main.view.views.CustomDialogFragment;
import com.felicanetworks.mfm.main.view.views.DialogFactory;
import com.felicanetworks.mfm.main.view.views.ImitatedCentralFragment;
import com.felicanetworks.mfm.main.view.views.TermsOfServiceFragment;
import com.felicanetworks.mfm.main.view.views.TutorialAccountFragment;
import com.felicanetworks.mfm.main.view.views.TutorialCardReadingFragment;
import com.felicanetworks.mfm.main.view.views.TutorialOverviewFragment;
import com.felicanetworks.mfm.main.view.views.TutorialPushReceivingFragment;
import com.felicanetworks.mfm.main.view.widget.SplashView;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public class ServiceListActivity extends BaseActivity implements TutorialOverviewFragment.Action, TermsOfServiceFragment.Action, TutorialAccountFragment.Action, TutorialCardReadingFragment.Action, TutorialPushReceivingFragment.Action {
    private static int mInstanceCounter;
    private CustomDialogFragment mCheckVersionUpDialog;
    private SplashView splashView;
    private boolean mIsFinishOperation = false;
    private Intent mIntent = null;

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        try {
            try {
                mInstanceCounter++;
                if ((getIntent().getFlags() & 32768) == 0 && (!isTaskRoot() || 1 < mInstanceCounter)) {
                    finish();
                    return;
                }
                super.onCreate(savedInstanceState);
                this.mIntent = getIntent();
                Structure currentRequest = getCurrentRequest();
                if (currentRequest != null) {
                    dispatchLatestStructure(currentRequest);
                }
                setContentView(R.layout.initialize_activity);
                CustomDialogFragment customDialogFragmentCreateCircleProgressDialog = DialogFactory.createCircleProgressDialog(null);
                this.mCheckVersionUpDialog = customDialogFragmentCreateCircleProgressDialog;
                customDialogFragmentCreateCircleProgressDialog.setCancelListener(new CustomDialogFragment.OnCancelListener() { // from class: com.felicanetworks.mfm.main.ServiceListActivity.1
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnCancelListener
                    public boolean onCancel() {
                        Structure currentRequest2 = ServiceListActivity.this.getCurrentRequest();
                        if (!(currentRequest2 instanceof CloseDrawStructure)) {
                            return true;
                        }
                        ((CloseDrawStructure) currentRequest2).actClose();
                        return true;
                    }
                });
                this.mCheckVersionUpDialog.setOnActivityCreatedListener(new CustomDialogFragment.OnActivityCreatedListener() { // from class: com.felicanetworks.mfm.main.ServiceListActivity.2
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnActivityCreatedListener
                    public void onActivityCreated() {
                        if (ServiceListActivity.this.getCurrentRequest() instanceof AppSetupDrawStructure) {
                            return;
                        }
                        ServiceListActivity.this.mCheckVersionUpDialog.dismiss();
                    }
                });
                if (isShowSplash(this.mIntent)) {
                    SplashView splashView = new SplashView(this);
                    this.splashView = splashView;
                    splashView.startSplash(new SplashView.Listener() { // from class: com.felicanetworks.mfm.main.ServiceListActivity.3
                        @Override // com.felicanetworks.mfm.main.view.widget.SplashView.Listener
                        public void onFinish() {
                            if (ServiceListActivity.this.isShowFragment() || !(ServiceListActivity.this.getCurrentRequest() instanceof AppSetupDrawStructure)) {
                                return;
                            }
                            ServiceListActivity serviceListActivity = ServiceListActivity.this;
                            serviceListActivity.showDialog(serviceListActivity.mCheckVersionUpDialog);
                            AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_01_14, new Object[0]);
                        }
                    });
                }
            } finally {
                super.onCreate(savedInstanceState);
            }
        } catch (Exception e) {
            Structure currentRequest2 = getCurrentRequest();
            if (currentRequest2 != null) {
                currentRequest2.detectException(e);
            } else {
                showInitializeFatalError(R.string.dlg_error_fatal_error_before_initialization);
                AnalysisManager.stamp(MfmStamp.Event.SCREEN_FATAL, "dlg_error_fatal_error_before_initialization", new MfmException(ServiceListActivity.class, 1, e));
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this.mIntent != null) {
            AnalysisManager.stamp(MfmStamp.Event.AUTO_DUMP_LAUNCH, this.mIntent, getApplicationContext());
            this.mIntent = null;
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        SplashView splashView = this.splashView;
        if (splashView != null) {
            splashView.abort();
        }
        if (isFinishing()) {
            finishOperation();
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        finishOperation();
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        String stringExtra = intent.getStringExtra(EXTRA_KEY_CALLING_ACTIVITY);
        if (stringExtra != null && (stringExtra.equals(NoticeDetailActivity.class.getName()) || stringExtra.equals(CardDetailActivity.class.getName()) || stringExtra.equals(DeleteCardListActivity.class.getName()))) {
            super.onNewIntent(intent);
            return;
        }
        Intent intent2 = new Intent(this, (Class<?>) ServiceListActivity.class);
        intent2.setFlags(268468224);
        if (intent.hasExtra(NoticeInfo.INTENT_EXTRAS_KEY_MESSAGE_BODY)) {
            intent2.putExtra(NoticeInfo.INTENT_EXTRAS_KEY_MESSAGE_BODY, intent.getStringExtra(NoticeInfo.INTENT_EXTRAS_KEY_MESSAGE_BODY));
        }
        if (intent.hasExtra(NoticeInfo.INTENT_EXTRAS_KEY_MESSAGE_EXTRA)) {
            intent2.putExtra(NoticeInfo.INTENT_EXTRAS_KEY_MESSAGE_EXTRA, intent.getStringExtra(NoticeInfo.INTENT_EXTRAS_KEY_MESSAGE_EXTRA));
        }
        if (intent.hasExtra(NoticeInfo.INTENT_EXTRAS_KEY_LINKAGE_SCHEME)) {
            intent2.putExtra(NoticeInfo.INTENT_EXTRAS_KEY_LINKAGE_SCHEME, intent.getStringExtra(NoticeInfo.INTENT_EXTRAS_KEY_LINKAGE_SCHEME));
        }
        if (intent.hasExtra(NoticeInfo.INTENT_EXTRAS_KEY_IMAGE_PATH)) {
            intent2.putExtra(NoticeInfo.INTENT_EXTRAS_KEY_IMAGE_PATH, intent.getStringExtra(NoticeInfo.INTENT_EXTRAS_KEY_IMAGE_PATH));
        }
        startActivity(intent2);
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    public void onBackPressedDispatch() {
        Structure currentRequest = getCurrentRequest();
        if (currentRequest instanceof MfiLoginDrawStructure) {
            ((MfiLoginDrawStructure) currentRequest).actPressBackKey();
        } else {
            super.onBackPressedDispatch();
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.ServiceListActivity$8, reason: invalid class name */
    static /* synthetic */ class AnonymousClass8 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType;

        static {
            int[] iArr = new int[StructureType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType = iArr;
            try {
                iArr[StructureType.APP_SETUP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.FELICA_FORMAT_CHECKING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.DATA_LOADING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.MFI_DATA_LOADING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.MFI_LOGIN_READYING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.MFI_LOGIN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.REBOOT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.ASK_APP_UPGRADE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.REQUIRE_APP_UPGRADE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.MFS_APP.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.TUTORIAL_OVERVIEW.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.TERMS_OF_SERVICE.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.TUTORIAL_SETTINGS.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.CARD_RECOVERY.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.CENTRAL.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.RECOMMEND_DETAIL.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.NOTICE_LIST.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.NOTICE_DETAIL.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.READER.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.REQUIRE_MISSING_APP_INSTALL.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.CARD_DETAIL.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.DELETE_CARD_LIST.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.COMPLETE_TUTORIAL.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    protected void dispatchStructureOnResume(Structure structure) {
        if (structure == null) {
        }
        switch (AnonymousClass8.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()]) {
            case 1:
                showAppSetup();
                break;
            case 2:
                showFeliCaFormatChecking();
                break;
            case 3:
                showDataLoading();
                break;
            case 4:
                showMfiDataLoading();
                break;
            case 5:
                removeFragments();
                showDialog(DialogFactory.createCircleProgressDialog(this));
                break;
            case 6:
                showFragmentWithFade(new TutorialAccountFragment());
                break;
            default:
                super.dispatchStructureOnResume(structure);
                break;
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    public void dispatchLatestStructure(Structure structure) {
        if (AnonymousClass8.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()] == 7) {
            ((RebootStructure) structure).actCompleted();
        } else {
            super.dispatchLatestStructure(structure);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    public void dispatchStructure(Structure structure) {
        switch (AnonymousClass8.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()]) {
            case 1:
                showAppSetup();
                break;
            case 2:
                showFeliCaFormatChecking();
                break;
            case 3:
                dismissAllDialog();
                showDataLoading();
                break;
            case 4:
                dismissAllDialog();
                showMfiDataLoading();
                break;
            case 5:
                removeFragments();
                showDialog(DialogFactory.createCircleProgressDialog(this));
                break;
            case 6:
                showFragmentWithSlide(new TutorialAccountFragment());
                break;
            case 7:
            default:
                super.dispatchStructure(structure);
                break;
            case 8:
                dismissAllDialog();
                showAskAppUpgrade((AskAppUpgradeDrawStructure) structure);
                break;
            case 9:
                dismissAllDialog();
                showRequireAppUpgrade((RequireAppUpgradeDrawStructure) structure);
                break;
            case 10:
                MfsAppStructure mfsAppStructure = (MfsAppStructure) structure;
                if (mfsAppStructure.confirmExistMfs(getPackageManager()) && mfsAppStructure.confirmSignMfs(getPackageManager())) {
                    activityResultLaunch(mfsAppStructure.getDefaultIntent());
                    break;
                }
                break;
            case 11:
                AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_10_10, new Object[0]);
                showFragmentWithSlide(new TutorialOverviewFragment());
                break;
            case 12:
                showFragmentWithSlide(new TermsOfServiceFragment());
                break;
            case 13:
                showFragmentWithSlide(new TutorialPushReceivingFragment());
                break;
            case 14:
                showCardRecovery();
                break;
            case 15:
                startActivity(new Intent(this, (Class<?>) CentralActivity.class));
                if (Build.VERSION.SDK_INT >= 34) {
                    overrideActivityTransition(0, 0, R.anim.fade_out);
                } else {
                    overridePendingTransition(0, R.anim.fade_out);
                }
                dismissAllDialog();
                break;
            case 16:
                startActivity(new Intent(this, (Class<?>) RecommendDetailActivity.class));
                dismissAllDialog();
                break;
            case 17:
                startActivity(new Intent(this, (Class<?>) NoticeListActivity.class));
                if (Build.VERSION.SDK_INT >= 34) {
                    overrideActivityTransition(0, 0, R.anim.fade_out);
                } else {
                    overridePendingTransition(0, R.anim.fade_out);
                }
                dismissAllDialog();
                break;
            case 18:
                startActivity(new Intent(this, (Class<?>) NoticeDetailActivity.class));
                if (Build.VERSION.SDK_INT >= 34) {
                    overrideActivityTransition(0, 0, R.anim.fade_out);
                } else {
                    overridePendingTransition(0, R.anim.fade_out);
                }
                dismissAllDialog();
                break;
            case 19:
                startActivity(new Intent(this, (Class<?>) ExtIcCardReaderActivity.class));
                if (Build.VERSION.SDK_INT >= 34) {
                    overrideActivityTransition(0, 0, R.anim.fade_out);
                } else {
                    overridePendingTransition(0, R.anim.fade_out);
                }
                dismissAllDialog();
                break;
            case 20:
                showRequireAppInstall((RequireMissingAppInstallDrawStructure) structure);
                break;
            case 21:
                super.setReturnFromOtherScreen(true);
                startActivity(new Intent(this, (Class<?>) CardDetailActivity.class));
                if (Build.VERSION.SDK_INT >= 34) {
                    overrideActivityTransition(0, 0, R.anim.fade_out);
                } else {
                    overridePendingTransition(0, R.anim.fade_out);
                }
                break;
            case 22:
                super.setReturnFromOtherScreen(true);
                startActivity(new Intent(this, (Class<?>) DeleteCardListActivity.class));
                if (Build.VERSION.SDK_INT >= 34) {
                    overrideActivityTransition(0, 0, R.anim.fade_out);
                } else {
                    overridePendingTransition(0, R.anim.fade_out);
                }
                break;
            case 23:
                if (shouldShowRequestNotificationPermissionRationale()) {
                    dismissAllDialog();
                    showRequestNotificationPermissionDialog();
                } else {
                    ((CompleteTutorialDrawStructure) structure).actCompleted();
                }
                break;
        }
    }

    private boolean isShowSplash(Intent intent) {
        return (intent.hasExtra(NotificationController.NOTIFICATION_ID_KEY) || intent.hasExtra(NoticeInfo.INTENT_EXTRAS_KEY_MESSAGE_BODY) || intent.hasExtra(EXTRA_KEY_REBOOT)) ? false : true;
    }

    private void showAppSetup() {
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_01_02, new Object[0]);
        SplashView splashView = this.splashView;
        if ((splashView == null || !splashView.isRunning()) && !this.mCheckVersionUpDialog.getShowsDialog()) {
            showDialog(this.mCheckVersionUpDialog);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_01_14, new Object[0]);
        }
    }

    private void showFeliCaFormatChecking() {
        SplashView splashView = this.splashView;
        if (splashView == null || !splashView.isRunning()) {
            removeFragments();
            showDialog(DialogFactory.createCircleProgressDialog(this));
        }
    }

    private void showAskAppUpgrade(final AskAppUpgradeDrawStructure askAppUpgradeDrawStructure) {
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_01_03, new Object[0]);
        CustomDialogFragment customDialogFragmentCreateSelectDialog = DialogFactory.createSelectDialog(getApplicationContext());
        customDialogFragmentCreateSelectDialog.setTargetTag(TAG_NORMAL_DIALOG);
        customDialogFragmentCreateSelectDialog.setTitle(getString(R.string.dlg_title_version_up_confirmation));
        customDialogFragmentCreateSelectDialog.setText(getString(R.string.dlg_text_version_up_confirmation));
        customDialogFragmentCreateSelectDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.ServiceListActivity.4
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_REQUEST_APP_UPDATE_YES, new Object[0]);
                try {
                    ServiceListActivity.this.startActivity(askAppUpgradeDrawStructure.getDefaultIntent());
                    askAppUpgradeDrawStructure.actPositive();
                    return true;
                } catch (Exception e) {
                    askAppUpgradeDrawStructure.detectException(e);
                    return true;
                }
            }
        });
        customDialogFragmentCreateSelectDialog.setNegativeButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.ServiceListActivity.5
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_REQUEST_APP_UPDATE_NO, new Object[0]);
                try {
                    askAppUpgradeDrawStructure.actNegative();
                    return true;
                } catch (Exception e) {
                    askAppUpgradeDrawStructure.detectException(e);
                    return true;
                }
            }
        });
        showNormalDialog(customDialogFragmentCreateSelectDialog);
    }

    private void showDataLoading() {
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_01_05, new Object[0]);
        showFragmentWithFade(new ImitatedCentralFragment());
    }

    private void showMfiDataLoading() {
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_01_06, new Object[0]);
        showFragmentWithFade(new ImitatedCentralFragment());
    }

    private void showCardRecovery() {
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_01_13, new Object[0]);
        showNormalDialog(DialogFactory.createCircleProgressDialog(getApplicationContext(), getString(R.string.text_card_recovery)));
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    public void handleInitializeError(Exception ex) {
        showInitializeFatalError(R.string.dlg_error_fatal_error_before_initialization);
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_FATAL, "dlg_error_fatal_error_before_initialization", new MfmException(ServiceListActivity.class, 2, ex));
    }

    private void finishOperation() {
        if (this.mIsFinishOperation) {
            return;
        }
        mInstanceCounter--;
        this.mIsFinishOperation = true;
    }

    public void showInitializeFatalError(int stringResId) {
        CustomDialogFragment customDialogFragmentCreateSingleChoiceDialog = DialogFactory.createSingleChoiceDialog(getApplicationContext());
        customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.dlg_title_error));
        customDialogFragmentCreateSingleChoiceDialog.setText(getString(stringResId));
        customDialogFragmentCreateSingleChoiceDialog.setTargetTag(TAG_FATAL_ERROR_DIALOG);
        customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.ServiceListActivity.6
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                ServiceListActivity.this.finish();
                return true;
            }
        });
        showErrorDialog(customDialogFragmentCreateSingleChoiceDialog);
        LogUtil.inquiryLog(getApplicationContext(), "");
    }

    private void removeFragments() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        Iterator<Fragment> it = fragments.iterator();
        while (it.hasNext()) {
            fragmentTransactionBeginTransaction.remove(it.next());
        }
        fragmentTransactionBeginTransaction.commit();
    }

    private void showFragmentWithSlide(Fragment fragment) {
        if (isShownFragment(fragment)) {
            return;
        }
        dismissAllDialog();
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        fragmentTransactionBeginTransaction.replace(R.id.mainFrame, fragment, fragment.getClass().getSimpleName());
        fragmentTransactionBeginTransaction.commit();
    }

    private void showFragmentWithFade(Fragment fragment) {
        if (isShownFragment(fragment)) {
            return;
        }
        dismissAllDialog();
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransactionBeginTransaction.replace(R.id.mainFrame, fragment, fragment.getClass().getSimpleName());
        fragmentTransactionBeginTransaction.commit();
    }

    private boolean isShownFragment(Fragment fragment) {
        return getSupportFragmentManager().findFragmentByTag(fragment.getClass().getSimpleName()) != null;
    }

    private boolean shouldShowRequestNotificationPermissionRationale() {
        if (Build.VERSION.SDK_INT >= 33) {
            return shouldShowRequestPermissionRationale("android.permission.POST_NOTIFICATIONS");
        }
        return false;
    }

    @Override // com.felicanetworks.mfm.main.view.views.TutorialOverviewFragment.Action
    public void onCompleteTutorialOverview() {
        AnalysisManager.stamp(MfmStamp.Event.ACTION_BUTTON_NEXT, new Object[0]);
        if (!(getCurrentRequest() instanceof TutorialOverviewDrawStructure)) {
            finish();
        } else {
            ((TutorialOverviewDrawStructure) getCurrentRequest()).actStartService();
        }
    }

    @Override // com.felicanetworks.mfm.main.view.views.TermsOfServiceFragment.Action
    public void onCompleteTermsOfService() {
        if (!(getCurrentRequest() instanceof TermsOfServiceDrawStructure)) {
            finish();
        } else {
            ((TermsOfServiceDrawStructure) getCurrentRequest()).actServiceStart();
        }
    }

    @Override // com.felicanetworks.mfm.main.view.views.TermsOfServiceFragment.Action
    public void onFailedLoading(MfmException e) {
        if (!(getCurrentRequest() instanceof TermsOfServiceDrawStructure)) {
            finish();
        } else {
            ((TermsOfServiceDrawStructure) getCurrentRequest()).actLoadingFailed(e);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.views.TermsOfServiceFragment.Action
    public void onServerError(MfmException e) {
        if (!(getCurrentRequest() instanceof TermsOfServiceDrawStructure)) {
            finish();
        } else {
            ((TermsOfServiceDrawStructure) getCurrentRequest()).actServerError(e);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.views.TutorialAccountFragment.Action
    public void onCompleteTutorialAccount() {
        if (!(getCurrentRequest() instanceof MfiLoginDrawStructure)) {
            finish();
            return;
        }
        final MfiLoginDrawStructure mfiLoginDrawStructure = (MfiLoginDrawStructure) getCurrentRequest();
        Intent defaultIntent = mfiLoginDrawStructure.getDefaultIntent();
        try {
            Intent intent = new Intent(defaultIntent.getAction());
            intent.setComponent(defaultIntent.getComponent());
            Set<String> categories = defaultIntent.getCategories();
            if (categories != null) {
                Iterator<String> it = categories.iterator();
                while (it.hasNext()) {
                    intent.addCategory(it.next());
                }
            }
            Bundle extras = defaultIntent.getExtras();
            if (extras != null) {
                intent.putExtras(extras);
            }
            activityResultLaunch(intent);
            mfiLoginDrawStructure.setLoginErrorResultListener(new MfiLoginDrawStructure.LoginErrorResultListener() { // from class: com.felicanetworks.mfm.main.ServiceListActivity.7
                @Override // com.felicanetworks.mfm.main.presenter.structure.MfiLoginDrawStructure.LoginErrorResultListener
                public void onErrorResult(final MfiLoginResultCode code, final Intent data) {
                    String str = String.format(Locale.US, "%n[%s.%02d.%03d]", "04", Integer.valueOf(code.getCode()), Integer.valueOf(data == null ? 0 : data.getIntExtra("code", 0)));
                    String string = ServiceListActivity.this.getString(R.string.dlg_text_warning_login_failed);
                    ServiceListActivity.this.showWarningDialog(string + str, "").setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.ServiceListActivity.7.1
                        @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                        public boolean onClick() {
                            mfiLoginDrawStructure.actCloseErrorDialog();
                            return true;
                        }
                    });
                }
            });
        } catch (Exception e) {
            mfiLoginDrawStructure.detectException(e);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.views.TutorialCardReadingFragment.Action
    public void onCompleteTutorialCardReading(boolean enabled) {
        if (!(getCurrentRequest() instanceof TutorialSettingsDrawStructure)) {
            finish();
        } else {
            ((TutorialSettingsDrawStructure) getCurrentRequest()).setEnableCardReading(enabled);
            showFragmentWithSlide(new TutorialPushReceivingFragment());
        }
    }

    @Override // com.felicanetworks.mfm.main.view.views.TutorialPushReceivingFragment.Action
    public void onCompleteTutorialPushReceiving(boolean enabled) {
        if (!(getCurrentRequest() instanceof TutorialSettingsDrawStructure)) {
            finish();
        } else {
            ((TutorialSettingsDrawStructure) getCurrentRequest()).setEnablePushReceiving(enabled);
            ((TutorialSettingsDrawStructure) getCurrentRequest()).actCompleted();
        }
    }

    public static boolean getRunning() {
        return mInstanceCounter > 0;
    }
}
