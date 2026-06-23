package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes3.dex */
public class DataLoadingDrawStructure extends CloseDrawStructure {
    private CentralFuncAgent _centralFuncAgent;

    public DataLoadingDrawStructure(CentralFuncAgent centralFuncAgent) {
        super(StructureType.DATA_LOADING);
        this._centralFuncAgent = centralFuncAgent;
    }

    public void setProgressListener(CentralFuncAgent.ProgressListener listener) {
        try {
            this._centralFuncAgent.setCompileProgressListener(listener);
        } catch (Exception e) {
            MfmException mfmException = new MfmException(getClass(), 1, e);
            LogUtil.error(mfmException);
            StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, this, mfmException);
        }
    }
}
