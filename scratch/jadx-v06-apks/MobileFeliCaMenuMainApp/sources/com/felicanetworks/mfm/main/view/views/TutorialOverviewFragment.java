package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.felicanetworks.mfm.main.R;

/* JADX INFO: loaded from: classes.dex */
public class TutorialOverviewFragment extends BaseFragment {
    private Action action;

    public interface Action {
        void onCompleteTutorialOverview();
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
        return layoutInflater.inflate(R.layout.fragment_tutorial_overview, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.TutorialOverviewFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (TutorialOverviewFragment.this.action == null) {
                    return;
                }
                view2.setEnabled(false);
                TutorialOverviewFragment.this.action.onCompleteTutorialOverview();
            }
        });
    }
}
