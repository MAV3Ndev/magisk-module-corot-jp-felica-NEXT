package com.felicanetworks.mfm.main.view.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.presenter.structure.ExtIcCardDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.PrimaryDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.view.activity.BaseActivity;
import com.felicanetworks.mfm.main.view.views.CustomDialogFragment;

/* JADX INFO: loaded from: classes.dex */
public class ExtIcCardReadingFragment extends BaseFragment {
    private boolean mIsOnDiscoverNotified = false;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_06_01, new Object[0]);
        View viewInflate = layoutInflater.inflate(R.layout.fragment_card_reader, viewGroup, false);
        try {
            viewInflate.findViewById(R.id.tv_guide_text).setVisibility(0);
            viewInflate.findViewById(R.id.pb_progress).setVisibility(8);
            viewInflate.findViewById(R.id.b_notes_button).setEnabled(true);
            ((TextView) viewInflate.findViewById(R.id.b_notes_button)).setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.ExtIcCardReadingFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_NOTES, new Object[0]);
                    try {
                        try {
                            Structure structure = ExtIcCardReadingFragment.this.getStructure();
                            if (structure instanceof ExtIcCardDrawStructure) {
                                ExtIcCardReadingFragment.this.startActivity(((ExtIcCardDrawStructure) structure).getDefaultIntent(PrimaryDrawStructure.LinkType.NOTES_SITE));
                                return;
                            }
                            return;
                        } catch (Exception unused) {
                            ExtIcCardReadingFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                            return;
                        }
                    } catch (Exception e) {
                        ExtIcCardReadingFragment.this.notifyException(e);
                    }
                    ExtIcCardReadingFragment.this.notifyException(e);
                }
            });
        } catch (Exception e) {
            notifyException(e);
        }
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        try {
            Structure structure = getStructure();
            if (structure == null || !(structure instanceof ExtIcCardDrawStructure)) {
                return;
            }
            final ExtIcCardDrawStructure extIcCardDrawStructure = (ExtIcCardDrawStructure) structure;
            if (!extIcCardDrawStructure.isEnableRWP2P()) {
                CustomDialogFragment customDialogFragmentCreateSelectDialog = DialogFactory.createSelectDialog(getActivity().getApplicationContext());
                customDialogFragmentCreateSelectDialog.setTargetTag(BaseActivity.TAG_NORMAL_DIALOG);
                customDialogFragmentCreateSelectDialog.setTitle(getString(R.string.dlg_title_nfc_invalid));
                customDialogFragmentCreateSelectDialog.setText(getString(R.string.dlg_warning_nfc_invalid));
                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_nfc_invalid");
                customDialogFragmentCreateSelectDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.ExtIcCardReadingFragment.2
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        AnalysisManager.stamp(MfmStamp.Event.ACTION_NFC_SETTINGS_YES, new Object[0]);
                        try {
                            try {
                                ExtIcCardReadingFragment.this.startActivity(extIcCardDrawStructure.getDefaultIntent(PrimaryDrawStructure.LinkType.RW_P2P_SETTING));
                            } catch (Exception e) {
                                ExtIcCardReadingFragment.this.notifyException(e);
                            }
                        } catch (Exception unused) {
                            ExtIcCardReadingFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                        }
                        return true;
                    }
                });
                customDialogFragmentCreateSelectDialog.setNegativeButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.ExtIcCardReadingFragment.3
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        AnalysisManager.stamp(MfmStamp.Event.ACTION_NFC_SETTINGS_NO, new Object[0]);
                        try {
                            extIcCardDrawStructure.actClose();
                            return true;
                        } catch (Exception e) {
                            ExtIcCardReadingFragment.this.notifyException(e);
                            return true;
                        }
                    }
                });
                showNormalDialog(customDialogFragmentCreateSelectDialog);
            } else {
                extIcCardDrawStructure.getExIcCardFunc().declareReady();
            }
            if (this.mIsOnDiscoverNotified) {
                switchProcessing(true);
                this.mIsOnDiscoverNotified = false;
            }
        } catch (Exception e) {
            notifyException(e);
        }
    }

    public void switchProcessing(boolean z) {
        View view = getView();
        if (view == null) {
            this.mIsOnDiscoverNotified = z;
            return;
        }
        if (z) {
            view.findViewById(R.id.tv_guide_text).setVisibility(8);
            view.findViewById(R.id.pb_progress).setVisibility(0);
            view.findViewById(R.id.b_notes_button).setEnabled(false);
        } else {
            view.findViewById(R.id.tv_guide_text).setVisibility(0);
            view.findViewById(R.id.pb_progress).setVisibility(8);
            view.findViewById(R.id.b_notes_button).setEnabled(true);
        }
    }
}
