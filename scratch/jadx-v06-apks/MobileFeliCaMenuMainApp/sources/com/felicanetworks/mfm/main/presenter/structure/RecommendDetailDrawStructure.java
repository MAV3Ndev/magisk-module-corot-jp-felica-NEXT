package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.RecommendInfoAgent;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes.dex */
public class RecommendDetailDrawStructure extends CloseDrawStructure {
    private CentralFuncAgent _func;
    private RecommendInfoAgent _info;

    public RecommendDetailDrawStructure(RecommendInfoAgent recommendInfoAgent, CentralFuncAgent centralFuncAgent) {
        super(StructureType.RECOMMEND_DETAIL);
        this._info = recommendInfoAgent;
        this._func = centralFuncAgent;
    }

    public void actServiceStartSuccess() {
        StateController.postStateEvent(StateMachine.Event.EV_SERVICE_START_SUCCESS, this, this._info);
    }

    public RecommendInfoAgent getRecommendInfo() {
        return this._info;
    }

    public CentralFuncAgent getCentralFunc() {
        return this._func;
    }
}
