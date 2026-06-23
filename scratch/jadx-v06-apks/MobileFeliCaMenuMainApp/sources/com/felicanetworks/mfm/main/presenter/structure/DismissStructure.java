package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes.dex */
public class DismissStructure extends Structure {
    public DismissStructure() {
        super(StructureType.DISMISS);
    }

    public void actCompleted() {
        StateController.postStateEvent(StateMachine.Event.EV_END, this, new Object[0]);
    }
}
