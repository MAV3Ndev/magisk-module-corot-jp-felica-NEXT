package com.felicanetworks.mfm.main.view.views;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.ReceiveNfcTagActivity;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.presenter.PresenterData;

/* JADX INFO: loaded from: classes3.dex */
public class TutorialCardReadingFragment extends BaseFragment {
    private Action action;

    public interface Action {
        void onCompleteTutorialCardReading(boolean enabled);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: android.content.Context */
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_01_11, new Object[0]);
        return inflater.inflate(R.layout.fragment_tutorial_card_reading, container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final CompoundButton compoundButton = (CompoundButton) view.findViewById(R.id.toggle);
        if (PresenterData.getInstance().getContext().getPackageManager().getComponentEnabledSetting(new ComponentName(PresenterData.getInstance().getContext(), (Class<?>) ReceiveNfcTagActivity.class)) == 1) {
            compoundButton.setChecked(true);
        } else {
            compoundButton.setChecked(false);
        }
        view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.TutorialCardReadingFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (TutorialCardReadingFragment.this.action == null) {
                    return;
                }
                AnalysisManager.stamp(MfmStamp.Event.ACTION_BUTTON_NEXT_AT_W1_01_11, Boolean.valueOf(compoundButton.isChecked()));
                TutorialCardReadingFragment.this.action.onCompleteTutorialCardReading(compoundButton.isChecked());
            }
        });
    }
}
