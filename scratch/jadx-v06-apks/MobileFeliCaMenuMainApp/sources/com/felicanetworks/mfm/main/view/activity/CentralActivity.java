package com.felicanetworks.mfm.main.view.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.FelicaPocketFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.MfiCardFuncAgent;
import com.felicanetworks.mfm.main.presenter.structure.CentralDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.FeliCaLockAppStructure;
import com.felicanetworks.mfm.main.presenter.structure.NoticeListDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.PrimaryDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.RequireAppUpgradeDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.presenter.structure.StructureType;
import com.felicanetworks.mfm.main.view.views.ContentTabsFragment;
import com.felicanetworks.mfm.main.view.views.DrawerContentsLayout;

/* JADX INFO: loaded from: classes.dex */
public class CentralActivity extends DrawerBaseActivity {
    private ContentTabsFragment mContentTabsFragment;

    @Override // com.felicanetworks.mfm.main.view.activity.DrawerBaseActivity, com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Structure currentRequest = getCurrentRequest();
        if (currentRequest != null && currentRequest.getType() == StructureType.CENTRAL) {
            dispatchLatestStructure(currentRequest);
        } else {
            finish();
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.mContentTabsFragment = null;
    }

    @Override // com.felicanetworks.mfm.main.view.activity.DrawerBaseActivity, com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        try {
            super.onResume();
            int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$view$views$ContentTabsFragment$TabType[this.mContentTabsFragment.getCurrentTab().ordinal()];
            if (i == 1) {
                this.mToolbar.setTitle(R.string.toolbar_title_myservice);
            } else if (i == 2) {
                this.mToolbar.setTitle(R.string.toolbar_title_recommend);
            }
        } catch (Exception e) {
            notifyException(e);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        ContentTabsFragment contentTabsFragment = this.mContentTabsFragment;
        if (contentTabsFragment != null && contentTabsFragment.getCurrentTab() == ContentTabsFragment.TabType.RECOMMEND) {
            this.mContentTabsFragment.setCurrentTab(ContentTabsFragment.TabType.MYSERVICE);
        } else {
            super.onBackPressed();
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    public void dispatchLatestStructure(Structure structure) {
        if (AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()] == 1) {
            showPriorityWarningDialog((CentralDrawStructure) structure);
            showContentView(structure);
        } else {
            super.dispatchLatestStructure(structure);
        }
    }

    private void showPriorityWarningDialog(CentralDrawStructure centralDrawStructure) {
        String str;
        String mfcErrorCode;
        CentralFuncAgent.CompiledState compiledState = centralDrawStructure.getCentralFunc().getCompiledState();
        FelicaPocketFuncAgent.CompiledFpState compiledFpState = centralDrawStructure.getCentralFunc().getFelicaPocketFuncAgent().getCompiledFpState();
        int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CentralFuncAgent$CompiledState$FelicaState[compiledState.getFelicaState().ordinal()];
        if (i == 1) {
            showWarningDialog(R.string.dlg_warning_felica_timeout);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_felica_timeout");
            return;
        }
        if (i == 2) {
            showWarningDialog(R.string.dlg_warning_used_by_other_app);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_used_by_other_app");
            return;
        }
        if (i == 3) {
            showWarningDialog(R.string.dlg_warning_felica_open);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_felica_open");
            return;
        }
        if (i == 4) {
            showWarningDialog(R.string.dlg_warning_running_by_mfic);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_running_by_mfic");
            return;
        }
        int i2 = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$FelicaPocketFuncAgent$CompiledFpState$FpState[compiledFpState.getFpState().ordinal()];
        if (i2 == 1) {
            showWarningDialog(R.string.dlg_warning_felica_timeout);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_felica_timeout");
            return;
        }
        if (i2 == 2) {
            showWarningDialog(R.string.dlg_warning_used_by_other_app);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_used_by_other_app");
            return;
        }
        if (i2 == 3) {
            showWarningDialog(R.string.dlg_warning_running_by_mfic);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_running_by_mfic");
            return;
        }
        if (i2 == 4) {
            showWarningDialog(R.string.dlg_warning_felica_open);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_felica_open");
            return;
        }
        MfiCardFuncAgent.CompiledState compiledState2 = centralDrawStructure.getMfiCardFunc().getCompiledState();
        int i3 = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MfiCardFuncAgent$CompiledState$MfiCardState[compiledState2.getMfiCardState().ordinal()];
        if (i3 == 1) {
            showWarningDialog(R.string.dlg_warning_used_by_other_app);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_used_by_other_app");
            return;
        }
        if (i3 == 2) {
            showWarningDialog(R.string.dlg_warning_running_by_mfic);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_running_by_mfic");
            return;
        }
        if (centralDrawStructure.isWarningNotEnoughExtCardServiceInfo()) {
            showWarningDialog(R.string.dlg_warning_not_enough_extcard_service_info);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_not_enough_extcard_service_info");
        }
        if (centralDrawStructure.isWarningNotLogined()) {
            showWarningDialog(R.string.dlg_text_warning_direct_card_detail_none_login);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_text_warning_direct_card_detail_none_login");
            return;
        }
        if (centralDrawStructure.isWarningLackServiceInfo()) {
            showWarningDialog(R.string.dlg_text_warning_direct_card_detail_none_service);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_text_warning_direct_card_detail_none_service");
            return;
        }
        if (centralDrawStructure.isWarningLackCardInfo()) {
            showWarningDialog(R.string.dlg_text_warning_direct_card_detail_none_card);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_text_warning_direct_card_detail_none_card");
            return;
        }
        if (compiledState.isConditionalComplete()) {
            showWarningDialog(R.string.dlg_warning_myservices_refresh_failed);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_myservices_refresh_failed");
            return;
        }
        str = "";
        switch (compiledState2.getMfiCardState()) {
            case MFIC_NETWORK_ERROR:
                showWarningDialog(R.string.dlg_warning_mfiservices_network_failed);
                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_mfiservices_network_failed");
                break;
            case MFIC_JSON_ERROR_NO_CASHE:
            case MFIC_JSON_ERROR_USE_CACHE:
                showWarningDialog(R.string.dlg_warning_myservices_refresh_failed);
                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_myservices_refresh_failed");
                break;
            case MFIC_LOCK_ERROR:
                if (compiledState.getFelicaState() != CentralFuncAgent.CompiledState.FelicaState.LOCK_ERROR) {
                    showWarningDialog(R.string.dlg_warning_mfiservices_felica_lock_failed);
                    AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_mfiservices_felica_lock_failed");
                }
                break;
            case MFIC_OPEN_ERROR:
                showWarningDialog(R.string.dlg_warning_mfiservices_felica_open_failed);
                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_mfiservices_felica_open_failed");
                break;
            case MFIC_FELICA_HTTP_ERROR:
                showWarningDialog(R.string.dlg_warning_mfiservices_felica_http_error);
                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_mfiservices_felica_http_error");
                break;
            case MFIC_LIB_UNAVAILABLE:
                showWarningDialog(R.string.dlg_warning_mfiservices_lib_unavailable);
                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_mfiservices_lib_unavailable");
                break;
            case MFIC_SERVER_MAINTENANCE_ERROR:
                showWarningDialog(R.string.dlg_text_warning_mfi_server_maintenance);
                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_text_warning_mfi_server_maintenance");
                break;
            case MFIC_LOGIN_ERROR:
                String string = getString(R.string.dlg_warning_mfiservice_login_failed);
                String errorCode = compiledState2.getMficWarningState().getErrorCode();
                String string2 = errorCode != null ? getString(R.string.dlg_error_code, new Object[]{errorCode}) : "";
                String mfcErrorCode2 = compiledState2.getMficWarningState().getMfcErrorCode();
                showWarningDialog(string + string2 + (mfcErrorCode2 != null ? getString(R.string.dlg_error_code_mfc, new Object[]{mfcErrorCode2}) : ""));
                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_mfiservice_login_failed");
                break;
            case MFIC_OTHER_ERROR:
                String string3 = getString(R.string.dlg_warning_mfiservices_mfc_other_failed);
                String errorCode2 = compiledState2.getMficWarningState().getErrorCode();
                String string4 = errorCode2 != null ? getString(R.string.dlg_error_code, new Object[]{errorCode2}) : "";
                String mfcErrorCode3 = compiledState2.getMficWarningState().getMfcErrorCode();
                showWarningDialog(string3 + string4 + (mfcErrorCode3 != null ? getString(R.string.dlg_error_code_mfc, new Object[]{mfcErrorCode3}) : ""));
                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_mfiservices_mfc_other_failed");
                break;
            default:
                if (centralDrawStructure.getMfiCardFunc().hasUnrecoveredCard()) {
                    if (centralDrawStructure.getMfiCardFunc().isInsideTransitGateRecoveryFailed()) {
                        showWarningDialog(R.string.dlg_text_warning_inside_transit_gate_recovery_failed);
                        AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_text_warning_inside_transit_gate_recovery_failed");
                    } else {
                        ModelErrorInfo recoveryErrorInfo = centralDrawStructure.getMfiCardFunc().getRecoveryErrorInfo();
                        if (recoveryErrorInfo != null) {
                            String errorCode3 = recoveryErrorInfo.getErrorCode();
                            if (errorCode3 != null) {
                                errorCode3 = getString(R.string.dlg_error_code, new Object[]{errorCode3});
                            }
                            str = errorCode3;
                            mfcErrorCode = recoveryErrorInfo.getMfcErrorCode();
                            if (mfcErrorCode != null) {
                                mfcErrorCode = getString(R.string.dlg_error_code_mfc, new Object[]{mfcErrorCode});
                            }
                        } else {
                            mfcErrorCode = "";
                        }
                        showWarningDialog(getString(R.string.dlg_text_warning_recovery_failed) + str + mfcErrorCode);
                        AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_text_warning_recovery_failed");
                    }
                }
                break;
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.DrawerBaseActivity, com.felicanetworks.mfm.main.view.activity.BaseActivity
    protected void dispatchStructure(Structure structure) {
        switch (AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()]) {
            case 1:
                break;
            case 2:
                startActivity(new Intent(this, (Class<?>) RecommendDetailActivity.class));
                break;
            case 3:
                if (structure instanceof NoticeListDrawStructure) {
                    ((NoticeListDrawStructure) structure).setCentralTabType(this.mContentTabsFragment.getCurrentTab());
                }
                startActivity(new Intent(this, (Class<?>) NoticeListActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, (Class<?>) ExtIcCardReaderActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, (Class<?>) FaqActivity.class));
                break;
            case 6:
                startActivity(new Intent(this, (Class<?>) SupportMenuActivity.class));
                break;
            case 7:
                startActivity(new Intent(this, (Class<?>) SettingListActivity.class));
                break;
            case 8:
                startActivityForResult(((FeliCaLockAppStructure) structure).getDefaultIntent(), REQUEST_CODE_FELICA_LOCK_APP);
                break;
            case 9:
                startActivity(new Intent(this, (Class<?>) FpServiceListActivity.class));
                break;
            case 10:
                showRequireAppUpgrade((RequireAppUpgradeDrawStructure) structure);
                break;
            case 11:
                startActivity(new Intent(this, (Class<?>) CardDetailActivity.class));
                break;
            case 12:
                startActivity(new Intent(this, (Class<?>) DeleteCardListActivity.class));
                break;
            case 13:
            case 14:
                finish();
                overridePendingTransition(0, R.anim.fade_out);
                break;
            default:
                super.dispatchStructure(structure);
                break;
        }
    }

    private void showContentView(Structure structure) {
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        ContentTabsFragment contentTabsFragment = new ContentTabsFragment();
        this.mContentTabsFragment = contentTabsFragment;
        fragmentTransactionBeginTransaction.replace(R.id.fl_content_fragment, contentTabsFragment);
        fragmentTransactionBeginTransaction.commit();
    }

    @Override // com.felicanetworks.mfm.main.view.activity.DrawerBaseActivity, com.felicanetworks.mfm.main.view.views.DrawerContentsLayout.OnSelectDrawerItemListener
    public DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior onSelect(DrawerContentsLayout.DrawerItemType drawerItemType) {
        DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior drawerBehaviorOnSelect;
        DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior drawerBehavior = DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.KEEP;
        try {
            Structure currentRequest = getCurrentRequest();
            if (currentRequest != null && (currentRequest instanceof CentralDrawStructure)) {
                CentralDrawStructure centralDrawStructure = (CentralDrawStructure) currentRequest;
                switch (AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[drawerItemType.ordinal()]) {
                    case 1:
                        this.mContentTabsFragment.setCurrentTab(ContentTabsFragment.TabType.MYSERVICE);
                        centralDrawStructure.actMyService();
                        drawerBehaviorOnSelect = DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
                        break;
                    case 2:
                        this.mContentTabsFragment.setCurrentTab(ContentTabsFragment.TabType.RECOMMEND);
                        centralDrawStructure.actRecommend();
                        drawerBehaviorOnSelect = DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
                        break;
                    case 3:
                        centralDrawStructure.actNotice();
                        drawerBehaviorOnSelect = DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
                        break;
                    case 4:
                        centralDrawStructure.actReader();
                        drawerBehaviorOnSelect = DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
                        break;
                    case 5:
                        centralDrawStructure.actFaq();
                        drawerBehaviorOnSelect = DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
                        break;
                    case 6:
                        startActivity(centralDrawStructure.getDefaultIntent(PrimaryDrawStructure.LinkType.CHANGE_MODEL));
                        drawerBehaviorOnSelect = DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
                        break;
                    case 7:
                        centralDrawStructure.actSupportMenu();
                        drawerBehaviorOnSelect = DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
                        break;
                    case 8:
                        centralDrawStructure.actSetting();
                        drawerBehaviorOnSelect = DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
                        break;
                    default:
                        drawerBehaviorOnSelect = super.onSelect(drawerItemType);
                        break;
                }
                return drawerBehaviorOnSelect;
            }
            return drawerBehavior;
        } catch (Exception e) {
            notifyException(e);
            return drawerBehavior;
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.activity.CentralActivity$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CentralFuncAgent$CompiledState$FelicaState;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$FelicaPocketFuncAgent$CompiledFpState$FpState;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$view$views$ContentTabsFragment$TabType;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType;

        static {
            int[] iArr = new int[DrawerContentsLayout.DrawerItemType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType = iArr;
            try {
                iArr[DrawerContentsLayout.DrawerItemType.MYSERVICE_ID.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[DrawerContentsLayout.DrawerItemType.RECOMMEND_ID.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[DrawerContentsLayout.DrawerItemType.NOTICE_ID.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[DrawerContentsLayout.DrawerItemType.CARD_ID.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[DrawerContentsLayout.DrawerItemType.FAQ_ID.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[DrawerContentsLayout.DrawerItemType.MODEL_CHANGE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[DrawerContentsLayout.DrawerItemType.SUPPORT_ID.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[DrawerContentsLayout.DrawerItemType.SETUP_ID.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            int[] iArr2 = new int[MfiCardFuncAgent.CompiledState.MfiCardState.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MfiCardFuncAgent$CompiledState$MfiCardState = iArr2;
            try {
                iArr2[MfiCardFuncAgent.CompiledState.MfiCardState.MFIC_USED_BY_OTHER_APP.ordinal()] = 1;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MfiCardFuncAgent$CompiledState$MfiCardState[MfiCardFuncAgent.CompiledState.MfiCardState.MFIC_RUNNING_BY_ITSELF.ordinal()] = 2;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MfiCardFuncAgent$CompiledState$MfiCardState[MfiCardFuncAgent.CompiledState.MfiCardState.MFIC_NETWORK_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MfiCardFuncAgent$CompiledState$MfiCardState[MfiCardFuncAgent.CompiledState.MfiCardState.MFIC_JSON_ERROR_NO_CASHE.ordinal()] = 4;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MfiCardFuncAgent$CompiledState$MfiCardState[MfiCardFuncAgent.CompiledState.MfiCardState.MFIC_JSON_ERROR_USE_CACHE.ordinal()] = 5;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MfiCardFuncAgent$CompiledState$MfiCardState[MfiCardFuncAgent.CompiledState.MfiCardState.MFIC_LOCK_ERROR.ordinal()] = 6;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MfiCardFuncAgent$CompiledState$MfiCardState[MfiCardFuncAgent.CompiledState.MfiCardState.MFIC_OPEN_ERROR.ordinal()] = 7;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MfiCardFuncAgent$CompiledState$MfiCardState[MfiCardFuncAgent.CompiledState.MfiCardState.MFIC_FELICA_HTTP_ERROR.ordinal()] = 8;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MfiCardFuncAgent$CompiledState$MfiCardState[MfiCardFuncAgent.CompiledState.MfiCardState.MFIC_LIB_UNAVAILABLE.ordinal()] = 9;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MfiCardFuncAgent$CompiledState$MfiCardState[MfiCardFuncAgent.CompiledState.MfiCardState.MFIC_SERVER_MAINTENANCE_ERROR.ordinal()] = 10;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MfiCardFuncAgent$CompiledState$MfiCardState[MfiCardFuncAgent.CompiledState.MfiCardState.MFIC_LOGIN_ERROR.ordinal()] = 11;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MfiCardFuncAgent$CompiledState$MfiCardState[MfiCardFuncAgent.CompiledState.MfiCardState.MFIC_OTHER_ERROR.ordinal()] = 12;
            } catch (NoSuchFieldError unused20) {
            }
            int[] iArr3 = new int[FelicaPocketFuncAgent.CompiledFpState.FpState.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$FelicaPocketFuncAgent$CompiledFpState$FpState = iArr3;
            try {
                iArr3[FelicaPocketFuncAgent.CompiledFpState.FpState.TIMEOUT_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$FelicaPocketFuncAgent$CompiledFpState$FpState[FelicaPocketFuncAgent.CompiledFpState.FpState.USED_BY_OTHER_APP.ordinal()] = 2;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$FelicaPocketFuncAgent$CompiledFpState$FpState[FelicaPocketFuncAgent.CompiledFpState.FpState.RUNNING_BY_MFIC.ordinal()] = 3;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$FelicaPocketFuncAgent$CompiledFpState$FpState[FelicaPocketFuncAgent.CompiledFpState.FpState.OPEN_ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused24) {
            }
            int[] iArr4 = new int[CentralFuncAgent.CompiledState.FelicaState.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CentralFuncAgent$CompiledState$FelicaState = iArr4;
            try {
                iArr4[CentralFuncAgent.CompiledState.FelicaState.TIMEOUT_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CentralFuncAgent$CompiledState$FelicaState[CentralFuncAgent.CompiledState.FelicaState.USED_BY_OTHER_APP.ordinal()] = 2;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CentralFuncAgent$CompiledState$FelicaState[CentralFuncAgent.CompiledState.FelicaState.OPEN_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CentralFuncAgent$CompiledState$FelicaState[CentralFuncAgent.CompiledState.FelicaState.RUNNING_BY_MFIC.ordinal()] = 4;
            } catch (NoSuchFieldError unused28) {
            }
            int[] iArr5 = new int[StructureType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType = iArr5;
            try {
                iArr5[StructureType.CENTRAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.RECOMMEND_DETAIL.ordinal()] = 2;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.NOTICE_LIST.ordinal()] = 3;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.READER.ordinal()] = 4;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.FAQ.ordinal()] = 5;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.SUPPORT_MENU.ordinal()] = 6;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.SETTING.ordinal()] = 7;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.FELICA_LOCK_APP.ordinal()] = 8;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.FELICA_POCKET.ordinal()] = 9;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.REQUIRE_APP_UPGRADE.ordinal()] = 10;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.CARD_DETAIL.ordinal()] = 11;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.DELETE_CARD_LIST.ordinal()] = 12;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.DATA_LOADING.ordinal()] = 13;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.MFI_DATA_LOADING.ordinal()] = 14;
            } catch (NoSuchFieldError unused42) {
            }
            int[] iArr6 = new int[ContentTabsFragment.TabType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$view$views$ContentTabsFragment$TabType = iArr6;
            try {
                iArr6[ContentTabsFragment.TabType.MYSERVICE.ordinal()] = 1;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$ContentTabsFragment$TabType[ContentTabsFragment.TabType.RECOMMEND.ordinal()] = 2;
            } catch (NoSuchFieldError unused44) {
            }
        }
    }
}
