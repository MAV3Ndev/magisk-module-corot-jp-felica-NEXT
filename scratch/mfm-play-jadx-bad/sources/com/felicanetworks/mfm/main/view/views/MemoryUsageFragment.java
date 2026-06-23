package com.felicanetworks.mfm.main.view.views;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

/* JADX INFO: loaded from: classes3.dex */
public class MemoryUsageFragment extends BaseFragment {
    private static final String MEMORY_USAGE_SYSTEM_CODE_COMMON_REGION = String.format("%04X", 65024);
    private static Toast mCopyToast;
    private LayoutInflater mInflater;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final String str = String.format("%04X", 3);
        stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_12_01, new Object[0]);
        View viewInflate = inflater.inflate(R.layout.fragment_memory_usage, container, false);
        this.mInflater = inflater;
        try {
            final CustomDialogFragment customDialogFragmentShowProgressDialog = ((MemoryUsageActivity) getActivity()).showProgressDialog();
            Structure structure = getStructure();
            if (structure != null && (structure instanceof MemoryDrawStructure)) {
                ((MemoryDrawStructure) structure).getMemoryUsageFunc().orderInfoList(new MemoryUsageFuncAgent.MemoryUsageListener() { // from class: com.felicanetworks.mfm.main.view.views.MemoryUsageFragment.1
                    @Override // com.felicanetworks.mfm.main.presenter.agent.MemoryUsageFuncAgent.MemoryUsageListener
                    public void onComplete(MemoryUsageInfosAgent result, MemoryUsageFuncAgent.MemoryUsageListener.CompleteState state) {
                        String str2;
                        String str3;
                        int i;
                        int i2;
                        int i3;
                        try {
                            str2 = "";
                            String string = null;
                            if (state.getFelicaState() == MemoryUsageFuncAgent.MemoryUsageListener.CompleteState.FelicaState.NO_PROBLEM) {
                                if (result.isDisplayInfoList()) {
                                    for (MemoryUsageInfoAgent memoryUsageInfoAgent : result.getMemoryUsageInfoAgentList()) {
                                        View viewInflate2 = MemoryUsageFragment.this.mInflater.inflate(R.layout.list_item_memory_usage, (ViewGroup) null, false);
                                        LinearLayout linearLayout = (LinearLayout) MemoryUsageFragment.this.getView().findViewById(R.id.ll_item_memusage);
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
                                            i = 1;
                                            i2 = 1;
                                            assignedBlock = 0;
                                            i3 = 0;
                                        } else {
                                            i3 = usedBlock;
                                            i2 = i3;
                                            i = assignedBlock;
                                        }
                                        ((TextView) viewInflate2.findViewById(R.id.tv_mem_usage)).setText(MemoryUsageFragment.this.getResources().getString(R.string.text_memory_usage_block, Integer.valueOf(i3), Integer.valueOf(assignedBlock)));
                                        ProgressBar progressBar = (ProgressBar) viewInflate2.findViewById(R.id.pb_mem_prog);
                                        progressBar.setMax(i);
                                        progressBar.setProgress(i2);
                                        progressBar.invalidate();
                                        linearLayout.addView(viewInflate2);
                                    }
                                } else {
                                    View viewInflate3 = MemoryUsageFragment.this.mInflater.inflate(R.layout.fragment_memory_usage_gp, (ViewGroup) null, false);
                                    LinearLayout linearLayout2 = (LinearLayout) MemoryUsageFragment.this.getView().findViewById(R.id.ll_item_memusage);
                                    TextView textView2 = (TextView) viewInflate3.findViewById(R.id.tv_felica_usage);
                                    if (result.getMemoryUsage()) {
                                        textView2.setText(R.string.text_memory_in_use);
                                    } else {
                                        textView2.setText(R.string.text_memory_not_in_use);
                                    }
                                    linearLayout2.addView(viewInflate3);
                                }
                                View view = MemoryUsageFragment.this.getView();
                                if (view != null) {
                                    ((TextView) view.findViewById(R.id.tv_seid)).setText(TextUtils.isEmpty(result.getSeid()) ? "" : result.getSeid());
                                    final Button button = (Button) view.findViewById(R.id.seid_copy_button);
                                    button.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.MemoryUsageFragment.1.1
                                        @Override // android.view.View.OnClickListener
                                        public void onClick(View v) {
                                            try {
                                                AnalysisManager.stamp(MfmStamp.Event.ACTION_COPY_SEID, new Object[0]);
                                                FragmentActivity activity = MemoryUsageFragment.this.getActivity();
                                                if (activity != null) {
                                                    String string2 = ((TextView) MemoryUsageFragment.this.getView().findViewById(R.id.tv_seid)).getText().toString();
                                                    ClipboardManager clipboardManager = (ClipboardManager) activity.getSystemService("clipboard");
                                                    button.setEnabled(false);
                                                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.felicanetworks.mfm.main.view.views.MemoryUsageFragment.1.1.1
                                                        @Override // java.lang.Runnable
                                                        public void run() {
                                                            button.setEnabled(true);
                                                        }
                                                    }, 2000L);
                                                    clipboardManager.setPrimaryClip(new ClipData(new ClipDescription("text_data", new String[]{"text/plain"}), new ClipData.Item(string2)));
                                                    if (MemoryUsageFragment.mCopyToast != null) {
                                                        MemoryUsageFragment.mCopyToast.cancel();
                                                    }
                                                    Toast unused = MemoryUsageFragment.mCopyToast = Toast.makeText(activity, R.string.text_copy_complete, 0);
                                                    MemoryUsageFragment.mCopyToast.show();
                                                }
                                            } catch (Exception e) {
                                                MemoryUsageFragment.this.notifyException(e);
                                            }
                                        }
                                    });
                                }
                                customDialogFragmentShowProgressDialog.dismiss();
                                MemoryUsageFragment.this.stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_12_02, new Object[0]);
                                return;
                            }
                            int i4 = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MemoryUsageFuncAgent$MemoryUsageListener$CompleteState$FelicaState[state.getFelicaState().ordinal()];
                            if (i4 == 1) {
                                string = MemoryUsageFragment.this.getString(R.string.dlg_text_warning_mfi_server_maintenance);
                                str3 = "dlg_text_warning_mfi_server_maintenance";
                            } else if (i4 == 2) {
                                string = MemoryUsageFragment.this.getString(R.string.dlg_warning_mfiservices_network_failed);
                                str3 = "dlg_warning_mfiservices_network_failed";
                            } else if (i4 != 3) {
                                str3 = null;
                            } else {
                                String string2 = MemoryUsageFragment.this.getString(R.string.dlg_text_warning_memory);
                                String errorCode = state.getErrorCode();
                                String string3 = errorCode != null ? MemoryUsageFragment.this.getString(R.string.dlg_error_code, errorCode) : "";
                                String warnCode = state.getWarnCode();
                                string = string2 + string3 + (warnCode != null ? MemoryUsageFragment.this.getString(R.string.dlg_error_code_mfc, warnCode) : "");
                                str3 = "dlg_text_warning_memory";
                                str2 = errorCode;
                            }
                            if (string != null) {
                                MemoryUsageFragment.this.stampWithJudgeActivityState(MfmStamp.Event.SCREEN_WARNING, str3);
                                CustomDialogFragment customDialogFragmentShowWarningDialog = MemoryUsageFragment.this.showWarningDialog(string, str2);
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
                                        } catch (Exception e) {
                                            MemoryUsageFragment.this.notifyException(e);
                                            return true;
                                        }
                                    }
                                });
                            }
                        } catch (Exception e) {
                            MemoryUsageFragment.this.notifyException(e);
                        }
                    }
                });
                adjustViewToNavigationBar(viewInflate.findViewById(R.id.ll_seid_area), false);
                return viewInflate;
            }
        } catch (Exception e) {
            notifyException(e);
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

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        View view = getView();
        if (view != null) {
            adjustViewToNavigationBar(view.findViewById(R.id.ll_seid_area), false);
        }
    }
}
