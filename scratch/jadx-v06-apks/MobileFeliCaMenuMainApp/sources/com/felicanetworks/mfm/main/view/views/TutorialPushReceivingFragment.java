package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.presenter.structure.TutorialSettingsDrawStructure;

/* JADX INFO: loaded from: classes.dex */
public class TutorialPushReceivingFragment extends BaseFragment {
    private Action action;

    public interface Action {
        void onCompleteTutorialPushReceiving(boolean z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Action) {
            this.action = (Action) context;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        this.action = null;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_01_12, new Object[0]);
        return layoutInflater.inflate(R.layout.fragment_tutorial_push_receiving, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        final CompoundButton compoundButton = (CompoundButton) view.findViewById(R.id.toggle);
        Structure structure = getStructure();
        if (structure instanceof TutorialSettingsDrawStructure) {
            compoundButton.setChecked(((TutorialSettingsDrawStructure) structure).isEnableNoticeSetting());
            view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.TutorialPushReceivingFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (TutorialPushReceivingFragment.this.action == null) {
                        return;
                    }
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_BUTTON_NEXT_AT_W1_01_12, Boolean.valueOf(compoundButton.isChecked()));
                    TutorialPushReceivingFragment.this.action.onCompleteTutorialPushReceiving(compoundButton.isChecked());
                }
            });
        }
    }
}
