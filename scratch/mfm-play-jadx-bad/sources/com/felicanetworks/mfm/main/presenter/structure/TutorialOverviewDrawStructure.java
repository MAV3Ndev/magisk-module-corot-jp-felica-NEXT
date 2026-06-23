package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes3.dex */
public class TutorialOverviewDrawStructure extends CloseDrawStructure {
    public TutorialOverviewDrawStructure() {
        super(StructureType.TUTORIAL_OVERVIEW);
    }

    public void actStartService() {
        StateController.postStateEvent(StateMachine.Event.EV_SERVICE_START, this, new Object[0]);
    }
}
