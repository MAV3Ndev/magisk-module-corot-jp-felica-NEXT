package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.presenter.action.IActionAsk;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AskDrawStructure extends DrawStructure implements IActionAsk {
    protected AskDrawStructure(StructureType type) {
        super(type);
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionAsk
    public final void actPositive() {
        StateController.postStateEvent(StateMachine.Event.EV_POSITIVE, this, new Object[0]);
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionAsk
    public final void actNegative() {
        StateController.postStateEvent(StateMachine.Event.EV_NEGATIVE, this, new Object[0]);
    }
}
