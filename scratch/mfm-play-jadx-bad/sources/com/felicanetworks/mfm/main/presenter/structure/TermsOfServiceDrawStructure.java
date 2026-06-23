package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.agent.TermsOfServiceFuncAgent;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes3.dex */
public class TermsOfServiceDrawStructure extends CloseDrawStructure {
    private final TermsOfServiceFuncAgent agent;

    public TermsOfServiceDrawStructure(TermsOfServiceFuncAgent agent) {
        super(StructureType.TERMS_OF_SERVICE);
        this.agent = agent;
    }

    public int getTosVersion() {
        return this.agent.getTosVersion();
    }

    public void actServiceStart() {
        StateController.postStateEvent(StateMachine.Event.EV_CONSENT_TERMS_OF_SERVICE, this, new Object[0]);
    }

    public void actLoadingFailed(MfmException e) {
        LogUtil.warning(e);
        StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, this, e, 8);
    }

    public void actServerError(MfmException e) {
        LogUtil.warning(e);
        StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, this, e, 9);
    }
}
