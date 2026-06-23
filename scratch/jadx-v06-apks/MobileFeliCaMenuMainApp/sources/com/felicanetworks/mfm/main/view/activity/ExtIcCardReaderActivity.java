package com.felicanetworks.mfm.main.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.presenter.agent.ExtIcCardFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.ExtIcCardInfoAgent;
import com.felicanetworks.mfm.main.presenter.structure.CloseDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.ExtIcCardDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.presenter.structure.StructureType;
import com.felicanetworks.mfm.main.view.views.CustomDialogFragment;
import com.felicanetworks.mfm.main.view.views.ExtIcCardReadResultFragment;
import com.felicanetworks.mfm.main.view.views.ExtIcCardReadingFragment;

/* JADX INFO: loaded from: classes.dex */
public class ExtIcCardReaderActivity extends BranchBaseActivity {
    ExtIcCardFuncAgent.DiscoverListener mDiscoverListener = new ExtIcCardFuncAgent.DiscoverListener() { // from class: com.felicanetworks.mfm.main.view.activity.ExtIcCardReaderActivity.1
        @Override // com.felicanetworks.mfm.main.presenter.agent.ExtIcCardFuncAgent.DiscoverListener
        public void onDiscover() {
            try {
                ExtIcCardReaderActivity.this.dismissDialog();
                Fragment fragmentFindFragmentById = ExtIcCardReaderActivity.this.getSupportFragmentManager().findFragmentById(R.id.fl_content_fragment);
                if (fragmentFindFragmentById instanceof ExtIcCardReadingFragment) {
                    ((ExtIcCardReadingFragment) fragmentFindFragmentById).switchProcessing(true);
                } else if (fragmentFindFragmentById instanceof ExtIcCardReadResultFragment) {
                    FragmentTransaction fragmentTransactionBeginTransaction = ExtIcCardReaderActivity.this.getSupportFragmentManager().beginTransaction();
                    ExtIcCardReadingFragment extIcCardReadingFragment = new ExtIcCardReadingFragment();
                    extIcCardReadingFragment.switchProcessing(true);
                    fragmentTransactionBeginTransaction.replace(R.id.fl_content_fragment, extIcCardReadingFragment);
                    fragmentTransactionBeginTransaction.commitAllowingStateLoss();
                }
            } catch (Exception e) {
                ExtIcCardReaderActivity.this.notifyException(e);
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.agent.ExtIcCardFuncAgent.DiscoverListener
        public void onResolved(ExtIcCardInfoAgent extIcCardInfoAgent, ExtIcCardFuncAgent.DiscoverListener.CompleteState completeState) {
            int i;
            try {
                ExtIcCardReaderActivity.this.dismissDialog();
                ((ExtIcCardReadingFragment) ExtIcCardReaderActivity.this.getSupportFragmentManager().findFragmentById(R.id.fl_content_fragment)).switchProcessing(false);
                if (completeState.getFelicaState() == ExtIcCardFuncAgent.DiscoverListener.CompleteState.FelicaState.NO_PROBLEM) {
                    FragmentTransaction fragmentTransactionBeginTransaction = ExtIcCardReaderActivity.this.getSupportFragmentManager().beginTransaction();
                    fragmentTransactionBeginTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    ExtIcCardReadResultFragment extIcCardReadResultFragment = new ExtIcCardReadResultFragment();
                    extIcCardReadResultFragment.setCardInfoAgent(extIcCardInfoAgent);
                    fragmentTransactionBeginTransaction.replace(R.id.fl_content_fragment, extIcCardReadResultFragment);
                    fragmentTransactionBeginTransaction.addToBackStack(null);
                    fragmentTransactionBeginTransaction.commitAllowingStateLoss();
                    return;
                }
                if (completeState.getFelicaState() == ExtIcCardFuncAgent.DiscoverListener.CompleteState.FelicaState.ONLY_FELICA_POCKET_SUPPORTED) {
                    try {
                        Structure currentRequest = ExtIcCardReaderActivity.this.getCurrentRequest();
                        if (currentRequest instanceof ExtIcCardDrawStructure) {
                            ((ExtIcCardDrawStructure) currentRequest).actFpServiceList();
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        ExtIcCardReaderActivity.this.notifyException(e);
                        return;
                    }
                }
                int i2 = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$ExtIcCardFuncAgent$DiscoverListener$CompleteState$FelicaState[completeState.getFelicaState().ordinal()];
                if (i2 == 1) {
                    i = R.string.dlg_warning_unsupported_card;
                    AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_unsupported_card");
                } else if (i2 == 2) {
                    i = R.string.dlg_warning_felica_timeout;
                    AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_felica_timeout");
                } else if (i2 != 3) {
                    if (i2 == 5) {
                        AnalysisManager.stamp(MfmStamp.Event.AUTO_DUMP_NFC_IO_ERROR, new Object[0]);
                    } else if (i2 == 6) {
                        AnalysisManager.stamp(MfmStamp.Event.AUTO_DUMP_NFC_ILLEGALSTATE_ERROR, new Object[0]);
                    }
                    i = 0;
                } else {
                    i = R.string.dlg_warning_unknown_felica_error;
                    MfmStamp.Event event = MfmStamp.Event.SCREEN_WARNING;
                    Object[] objArr = new Object[2];
                    objArr[0] = "dlg_warning_unknown_felica_error";
                    objArr[1] = completeState.hasMfcErrorCode() ? completeState.getMfcErrorCode() : null;
                    AnalysisManager.stamp(event, objArr);
                }
                if (i != 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(ExtIcCardReaderActivity.this.getString(i));
                    if (completeState.withErrorCode()) {
                        sb.append(ExtIcCardReaderActivity.this.getString(R.string.dlg_error_code, new Object[]{completeState.getErrorCode()}));
                    }
                    if (completeState.hasMfcErrorCode()) {
                        sb.append(ExtIcCardReaderActivity.this.getString(R.string.dlg_error_code_mfc, new Object[]{completeState.getMfcErrorCode()}));
                    }
                    CustomDialogFragment customDialogFragmentShowWarningDialog = ExtIcCardReaderActivity.this.showWarningDialog(sb.toString());
                    if (customDialogFragmentShowWarningDialog == null) {
                        Structure currentRequest2 = ExtIcCardReaderActivity.this.getCurrentRequest();
                        if (currentRequest2 instanceof ExtIcCardDrawStructure) {
                            ((ExtIcCardDrawStructure) currentRequest2).getExIcCardFunc().registerDiscoverListener(ExtIcCardReaderActivity.this.mDiscoverListener);
                            return;
                        }
                        return;
                    }
                    customDialogFragmentShowWarningDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.ExtIcCardReaderActivity.1.1
                        @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                        public boolean onClick() {
                            Structure currentRequest3 = ExtIcCardReaderActivity.this.getCurrentRequest();
                            if (!(currentRequest3 instanceof ExtIcCardDrawStructure)) {
                                return false;
                            }
                            ((ExtIcCardDrawStructure) currentRequest3).getExIcCardFunc().registerDiscoverListener(ExtIcCardReaderActivity.this.mDiscoverListener);
                            return true;
                        }
                    });
                }
            } catch (Exception e2) {
                ExtIcCardReaderActivity.this.notifyException(e2);
            }
        }
    };

    @Override // com.felicanetworks.mfm.main.view.activity.BranchBaseActivity, com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getSupportActionBar().setTitle(R.string.toolbar_title_card_reader);
        try {
            dispatchLatestStructure(getCurrentRequest());
        } catch (Exception e) {
            notifyException(e);
        }
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        boolean zOnOptionsItemSelected = true;
        try {
            if (menuItem.getItemId() == 16908332) {
                if (getSupportFragmentManager().findFragmentById(R.id.fl_content_fragment) instanceof ExtIcCardReadResultFragment) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    Structure currentRequest = getCurrentRequest();
                    if (currentRequest instanceof CloseDrawStructure) {
                        ((CloseDrawStructure) currentRequest).actClose();
                    }
                }
            } else {
                zOnOptionsItemSelected = super.onOptionsItemSelected(menuItem);
            }
        } catch (Exception e) {
            notifyException(e);
        }
        return zOnOptionsItemSelected;
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentById(R.id.fl_content_fragment) instanceof ExtIcCardReadResultFragment) {
            FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransactionBeginTransaction.replace(R.id.fl_content_fragment, new ExtIcCardReadingFragment());
            fragmentTransactionBeginTransaction.commit();
            return;
        }
        super.onBackPressed();
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    protected void dispatchLatestStructure(Structure structure) {
        try {
            if (structure.getType() == StructureType.READER) {
                showContentView();
                ((ExtIcCardDrawStructure) structure).getExIcCardFunc().registerDiscoverListener(this.mDiscoverListener);
            } else {
                super.dispatchLatestStructure(structure);
            }
        } catch (Exception e) {
            notifyException(e);
        }
    }

    private void showContentView() {
        if (getSupportFragmentManager().findFragmentById(R.id.fl_content_fragment) instanceof ExtIcCardReadResultFragment) {
            getSupportFragmentManager().popBackStack();
            return;
        }
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.replace(R.id.fl_content_fragment, new ExtIcCardReadingFragment());
        fragmentTransactionBeginTransaction.commit();
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.activity.ExtIcCardReaderActivity$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$ExtIcCardFuncAgent$DiscoverListener$CompleteState$FelicaState;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType;

        static {
            int[] iArr = new int[StructureType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType = iArr;
            try {
                iArr[StructureType.CENTRAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.RECOMMEND_DETAIL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.FELICA_POCKET.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.RW_SETTING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[ExtIcCardFuncAgent.DiscoverListener.CompleteState.FelicaState.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$ExtIcCardFuncAgent$DiscoverListener$CompleteState$FelicaState = iArr2;
            try {
                iArr2[ExtIcCardFuncAgent.DiscoverListener.CompleteState.FelicaState.UNSUPPORTED_CARD.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$ExtIcCardFuncAgent$DiscoverListener$CompleteState$FelicaState[ExtIcCardFuncAgent.DiscoverListener.CompleteState.FelicaState.TIMEOUT_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$ExtIcCardFuncAgent$DiscoverListener$CompleteState$FelicaState[ExtIcCardFuncAgent.DiscoverListener.CompleteState.FelicaState.FELICA_OTHER_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$ExtIcCardFuncAgent$DiscoverListener$CompleteState$FelicaState[ExtIcCardFuncAgent.DiscoverListener.CompleteState.FelicaState.OPEN_ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$ExtIcCardFuncAgent$DiscoverListener$CompleteState$FelicaState[ExtIcCardFuncAgent.DiscoverListener.CompleteState.FelicaState.NFC_IO_ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$ExtIcCardFuncAgent$DiscoverListener$CompleteState$FelicaState[ExtIcCardFuncAgent.DiscoverListener.CompleteState.FelicaState.NFC_ILLEGALSTATE_ERROR.ordinal()] = 6;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    protected void dispatchStructure(Structure structure) {
        int i = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()];
        if (i == 1) {
            Intent intent = new Intent(this, (Class<?>) CentralActivity.class);
            intent.setFlags(603979776);
            startActivity(intent);
        } else if (i == 2) {
            Intent intent2 = new Intent(this, (Class<?>) RecommendDetailActivity.class);
            intent2.setFlags(603979776);
            startActivity(intent2);
        } else if (i == 3) {
            startActivity(new Intent(this, (Class<?>) FpServiceListActivity.class));
        } else if (i == 4) {
            startActivity(new Intent(this, (Class<?>) RWSettingActivity.class));
        } else {
            super.dispatchStructure(structure);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        try {
            super.onResume();
            dismissDialog(TAG_NORMAL_DIALOG);
            if (isReturnFromOtherScreen()) {
                setReturnFromOtherScreen(false);
                dispatchLatestStructure(getCurrentRequest());
            }
        } catch (Exception e) {
            notifyException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissDialog() {
        dismissDialog(TAG_NORMAL_DIALOG);
        dismissDialog(TAG_WARNING_DIALOG);
    }
}
