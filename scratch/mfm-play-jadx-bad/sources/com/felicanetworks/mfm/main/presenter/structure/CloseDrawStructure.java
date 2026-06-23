package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.presenter.action.IActionClose;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes3.dex */
public abstract class CloseDrawStructure extends DrawStructure implements IActionClose {
    protected CloseDrawStructure(StructureType type) {
        super(type);
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionClose
    public final void actClose() {
        StateController.postStateEvent(StateMachine.Event.EV_CLOSE, this, new Object[0]);
    }
}
