package com.felicanetworks.mfm.main.view.activity;

import android.os.Bundle;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.presenter.structure.StructureType;
import com.felicanetworks.mfm.main.presenter.structure.TutorialAnytimeDrawStructure;
import com.felicanetworks.mfm.main.view.views.TutorialOverviewFragment;

/* JADX INFO: loaded from: classes3.dex */
public class TutorialActivity extends BaseActivity implements TutorialOverviewFragment.Action {
    private TutorialAnytimeDrawStructure structure;

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_activity);
        if (!(getCurrentRequest() instanceof TutorialAnytimeDrawStructure)) {
            finish();
        } else {
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_11_10, new Object[0]);
            this.structure = (TutorialAnytimeDrawStructure) getCurrentRequest();
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.activity.TutorialActivity$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType;

        static {
            int[] iArr = new int[StructureType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType = iArr;
            try {
                iArr[StructureType.SUPPORT_MENU.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    protected void dispatchStructure(Structure structure) {
        if (AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()] == 1) {
            finish();
        } else {
            super.dispatchStructure(structure);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.views.TutorialOverviewFragment.Action
    public void onCompleteTutorialOverview() {
        finish();
        this.structure.actClose();
    }
}
