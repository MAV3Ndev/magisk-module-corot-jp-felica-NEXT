package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.presenter.structure.TutorialSettingsDrawStructure;

/* JADX INFO: loaded from: classes3.dex */
public class TutorialPushReceivingFragment extends BaseFragment {
    private Action action;
    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback() { // from class: com.felicanetworks.mfm.main.view.views.TutorialPushReceivingFragment$$ExternalSyntheticLambda0
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
        @Override // androidx.activity.result.ActivityResultCallback
        public final void onActivityResult(Object obj) {
            this.f$0.m421xa894fa7f((Boolean) obj);
        }
    });

    public interface Action {
        void onCompleteTutorialPushReceiving(boolean enabled);
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
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_01_12, new Object[0]);
        return inflater.inflate(R.layout.fragment_tutorial_push_receiving, container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final CompoundButton compoundButton = (CompoundButton) view.findViewById(R.id.toggle);
        if (getStructure() instanceof TutorialSettingsDrawStructure) {
            compoundButton.setChecked(true);
            view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.TutorialPushReceivingFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (TutorialPushReceivingFragment.this.action == null) {
                        return;
                    }
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_BUTTON_NEXT_AT_W1_01_12, Boolean.valueOf(compoundButton.isChecked()));
                    if (!(compoundButton.isChecked() ? TutorialPushReceivingFragment.this.checkNotificationPermission() : false)) {
                        TutorialPushReceivingFragment.this.action.onCompleteTutorialPushReceiving(compoundButton.isChecked());
                    } else if (Build.VERSION.SDK_INT >= 33) {
                        TutorialPushReceivingFragment.this.requestPermissionLauncher.launch("android.permission.POST_NOTIFICATIONS");
                    }
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$new$0$com-felicanetworks-mfm-main-view-views-TutorialPushReceivingFragment, reason: not valid java name */
    /* synthetic */ void m421xa894fa7f(Boolean bool) {
        this.action.onCompleteTutorialPushReceiving(true);
    }
}
