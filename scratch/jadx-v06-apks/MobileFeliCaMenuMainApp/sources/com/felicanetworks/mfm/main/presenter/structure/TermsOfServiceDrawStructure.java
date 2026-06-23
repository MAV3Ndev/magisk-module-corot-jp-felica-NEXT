package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.agent.TermsOfServiceFuncAgent;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes.dex */
public class TermsOfServiceDrawStructure extends CloseDrawStructure {
    private final TermsOfServiceFuncAgent agent;

    public TermsOfServiceDrawStructure(TermsOfServiceFuncAgent termsOfServiceFuncAgent) {
        super(StructureType.TERMS_OF_SERVICE);
        this.agent = termsOfServiceFuncAgent;
    }

    public int getTosVersion() {
        return this.agent.getTosVersion();
    }

    public void actServiceStart() {
        StateController.postStateEvent(StateMachine.Event.EV_CONSENT_TERMS_OF_SERVICE, this, new Object[0]);
    }

    public void actLoadingFailed(MfmException mfmException) {
        LogUtil.warning(mfmException);
        StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, this, mfmException, 8);
    }

    public void actServerError(MfmException mfmException) {
        LogUtil.warning(mfmException);
        StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, this, mfmException, 9);
    }
}
