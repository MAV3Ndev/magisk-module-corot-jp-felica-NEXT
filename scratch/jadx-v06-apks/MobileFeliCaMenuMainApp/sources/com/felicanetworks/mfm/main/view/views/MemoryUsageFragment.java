package com.felicanetworks.mfm.main.view.views;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.presenter.agent.MemoryUsageFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.MemoryUsageInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.MemoryUsageInfosAgent;
import com.felicanetworks.mfm.main.presenter.structure.MemoryDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.view.activity.MemoryUsageActivity;
import com.felicanetworks.mfm.main.view.views.CustomDialogFragment;

/* JADX INFO: loaded from: classes.dex */
public class MemoryUsageFragment extends BaseFragment {
    private static final String MEMORY_USAGE_SYSTEM_CODE_COMMON_REGION = String.format("%04X", 65024);
    private static Toast mCopyToast;
    private LayoutInflater mInflater;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        final CustomDialogFragment customDialogFragmentShowProgressDialog;
        Structure structure;
        final String str = String.format("%04X", 3);
        stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_12_01, new Object[0]);
        View viewInflate = layoutInflater.inflate(R.layout.fragment_memory_usage, viewGroup, false);
        this.mInflater = layoutInflater;
        try {
            customDialogFragmentShowProgressDialog = ((MemoryUsageActivity) getActivity()).showProgressDialog();
            structure = getStructure();
        } catch (Exception e) {
            notifyException(e);
        }
        if (structure != null && (structure instanceof MemoryDrawStructure)) {
            ((MemoryDrawStructure) structure).getMemoryUsageFunc().orderInfoList(new MemoryUsageFuncAgent.MemoryUsageListener() { // from class: com.felicanetworks.mfm.main.view.views.MemoryUsageFragment.1
                @Override // com.felicanetworks.mfm.main.presenter.agent.MemoryUsageFuncAgent.MemoryUsageListener
                public void onComplete(MemoryUsageInfosAgent memoryUsageInfosAgent, MemoryUsageFuncAgent.MemoryUsageListener.CompleteState completeState) {
                    String str2;
                    int i;
                    int i2;
                    int i3;
                    try {
                        String string = null;
                        if (completeState.getFelicaState() == MemoryUsageFuncAgent.MemoryUsageListener.CompleteState.FelicaState.NO_PROBLEM) {
                            boolean zIsDisplayInfoList = memoryUsageInfosAgent.isDisplayInfoList();
                            int i4 = R.id.ll_item_memusage;
                            if (zIsDisplayInfoList) {
                                for (MemoryUsageInfoAgent memoryUsageInfoAgent : memoryUsageInfosAgent.getMemoryUsageInfoAgentList()) {
                                    View viewInflate2 = MemoryUsageFragment.this.mInflater.inflate(R.layout.list_item_memory_usage, (ViewGroup) null, false);
                                    LinearLayout linearLayout = (LinearLayout) MemoryUsageFragment.this.getView().findViewById(i4);
                                    String systemCode = memoryUsageInfoAgent.getSystemCode();
                                    TextView textView = (TextView) viewInflate2.findViewById(R.id.tv_mem_sys);
                                    if (MemoryUsageFragment.MEMORY_USAGE_SYSTEM_CODE_COMMON_REGION.equals(systemCode)) {
                                        textView.setText(R.string.text_common_region);
                                    } else if (str.equals(systemCode)) {
                                        textView.setText(R.string.text_cybernetics_region);
                                    }
                                    int usedBlock = memoryUsageInfoAgent.getUsedBlock();
                                    int assignedBlock = memoryUsageInfoAgent.getAssignedBlock();
                                    if (assignedBlock == 0 || assignedBlock < usedBlock) {
                                        assignedBlock = 0;
                                        i = 1;
                                        i2 = 0;
                                        i3 = 1;
                                    } else {
                                        i2 = usedBlock;
                                        i3 = i2;
                                        i = assignedBlock;
                                    }
                                    ((TextView) viewInflate2.findViewById(R.id.tv_mem_usage)).setText(MemoryUsageFragment.this.getResources().getString(R.string.text_memory_usage_block, Integer.valueOf(i2), Integer.valueOf(assignedBlock)));
                                    ProgressBar progressBar = (ProgressBar) viewInflate2.findViewById(R.id.pb_mem_prog);
                                    progressBar.setMax(i);
                                    progressBar.setProgress(i3);
                                    progressBar.invalidate();
                                    linearLayout.addView(viewInflate2);
                                    i4 = R.id.ll_item_memusage;
                                }
                            } else {
                                View viewInflate3 = MemoryUsageFragment.this.mInflater.inflate(R.layout.fragment_memory_usage_gp, (ViewGroup) null, false);
                                LinearLayout linearLayout2 = (LinearLayout) MemoryUsageFragment.this.getView().findViewById(R.id.ll_item_memusage);
                                TextView textView2 = (TextView) viewInflate3.findViewById(R.id.tv_felica_usage);
                                if (memoryUsageInfosAgent.getMemoryUsage()) {
                                    textView2.setText(R.string.text_memory_in_use);
                                } else {
                                    textView2.setText(R.string.text_memory_not_in_use);
                                }
                                linearLayout2.addView(viewInflate3);
                            }
                            View view = MemoryUsageFragment.this.getView();
                            if (view != null) {
                                ((TextView) view.findViewById(R.id.tv_seid)).setText(TextUtils.isEmpty(memoryUsageInfosAgent.getSeid()) ? "" : memoryUsageInfosAgent.getSeid());
                                ((Button) view.findViewById(R.id.seid_copy_button)).setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.MemoryUsageFragment.1.1
                                    @Override // android.view.View.OnClickListener
                                    public void onClick(View view2) {
                                        try {
                                            AnalysisManager.stamp(MfmStamp.Event.ACTION_COPY_SEID, new Object[0]);
                                            FragmentActivity activity = MemoryUsageFragment.this.getActivity();
                                            if (activity != null) {
                                                String string2 = ((TextView) MemoryUsageFragment.this.getView().findViewById(R.id.tv_seid)).getText().toString();
                                                ClipboardManager clipboardManager = (ClipboardManager) activity.getSystemService("clipboard");
                                                if (!clipboardManager.hasPrimaryClip() || !clipboardManager.getPrimaryClipDescription().hasMimeType("text/plain") || !string2.equals(clipboardManager.getPrimaryClip().getItemAt(0).getText())) {
                                                    clipboardManager.setPrimaryClip(new ClipData(new ClipDescription("text_data", new String[]{"text/plain"}), new ClipData.Item(string2)));
                                                }
                                                if (MemoryUsageFragment.mCopyToast != null) {
                                                    MemoryUsageFragment.mCopyToast.cancel();
                                                }
                                                Toast unused = MemoryUsageFragment.mCopyToast = Toast.makeText(activity, R.string.text_copy_complete, 0);
                                                MemoryUsageFragment.mCopyToast.show();
                                            }
                                        } catch (Exception e2) {
                                            MemoryUsageFragment.this.notifyException(e2);
                                        }
                                    }
                                });
                            }
                            customDialogFragmentShowProgressDialog.dismiss();
                            MemoryUsageFragment.this.stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_12_02, new Object[0]);
                            return;
                        }
                        int i5 = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MemoryUsageFuncAgent$MemoryUsageListener$CompleteState$FelicaState[completeState.getFelicaState().ordinal()];
                        if (i5 == 1) {
                            string = MemoryUsageFragment.this.getString(R.string.dlg_text_warning_mfi_server_maintenance);
                            str2 = "dlg_text_warning_mfi_server_maintenance";
                        } else if (i5 == 2) {
                            string = MemoryUsageFragment.this.getString(R.string.dlg_warning_mfiservices_network_failed);
                            str2 = "dlg_warning_mfiservices_network_failed";
                        } else if (i5 != 3) {
                            str2 = null;
                        } else {
                            String string2 = MemoryUsageFragment.this.getString(R.string.dlg_text_warning_memory);
                            String errorCode = completeState.getErrorCode();
                            String string3 = errorCode != null ? MemoryUsageFragment.this.getString(R.string.dlg_error_code, errorCode) : "";
                            String warnCode = completeState.getWarnCode();
                            string = string2 + string3 + (warnCode != null ? MemoryUsageFragment.this.getString(R.string.dlg_error_code_mfc, warnCode) : "");
                            str2 = "dlg_text_warning_memory";
                        }
                        if (string != null) {
                            MemoryUsageFragment.this.stampWithJudgeActivityState(MfmStamp.Event.SCREEN_WARNING, str2);
                            CustomDialogFragment customDialogFragmentShowWarningDialog = MemoryUsageFragment.this.showWarningDialog(string);
                            if (customDialogFragmentShowWarningDialog == null) {
                                return;
                            }
                            customDialogFragmentShowWarningDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.MemoryUsageFragment.1.2
                                @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                                public boolean onClick() {
                                    try {
                                        Structure structure2 = MemoryUsageFragment.this.getStructure();
                                        if (!(structure2 instanceof MemoryDrawStructure)) {
                                            return false;
                                        }
                                        ((MemoryDrawStructure) structure2).actClose();
                                        return true;
                                    } catch (Exception e2) {
                                        MemoryUsageFragment.this.notifyException(e2);
                                        return true;
                                    }
                                }
                            });
                        }
                    } catch (Exception e2) {
                        MemoryUsageFragment.this.notifyException(e2);
                    }
                }
            });
            return viewInflate;
        }
        return viewInflate;
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.views.MemoryUsageFragment$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MemoryUsageFuncAgent$MemoryUsageListener$CompleteState$FelicaState;

        static {
            int[] iArr = new int[MemoryUsageFuncAgent.MemoryUsageListener.CompleteState.FelicaState.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MemoryUsageFuncAgent$MemoryUsageListener$CompleteState$FelicaState = iArr;
            try {
                iArr[MemoryUsageFuncAgent.MemoryUsageListener.CompleteState.FelicaState.SERVER_MAINTENANCE_WARNING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MemoryUsageFuncAgent$MemoryUsageListener$CompleteState$FelicaState[MemoryUsageFuncAgent.MemoryUsageListener.CompleteState.FelicaState.NETWORK_WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MemoryUsageFuncAgent$MemoryUsageListener$CompleteState$FelicaState[MemoryUsageFuncAgent.MemoryUsageListener.CompleteState.FelicaState.PROCESS_FAILURE_WARNING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }
}
