package com.felicanetworks.mfm.main.presenter.structure;

import android.content.Intent;
import android.net.Uri;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.ExtIcCardFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.FelicaPocketFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.IFuncFelicaPocket;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes3.dex */
public class FpServiceListDrawStructure extends CloseDrawStructure implements IFuncFelicaPocket {
    private static FelicaPocketFuncAgent _felicaPocketFuncAgent;
    private FelicaPocketFuncAgent.CompiledFpState _compiledFpState;
    private boolean _needRecommend;

    public enum LinkType {
        FP_SITE
    }

    public FpServiceListDrawStructure(CentralFuncAgent centralFuncAgent) {
        super(StructureType.FELICA_POCKET);
        this._needRecommend = false;
        FelicaPocketFuncAgent felicaPocketFuncAgent = centralFuncAgent.getFelicaPocketFuncAgent();
        _felicaPocketFuncAgent = felicaPocketFuncAgent;
        this._compiledFpState = felicaPocketFuncAgent.getCompiledFpState();
    }

    public FpServiceListDrawStructure(ExtIcCardFuncAgent extIcCardFuncAgent) {
        super(StructureType.FELICA_POCKET);
        this._needRecommend = true;
        FelicaPocketFuncAgent felicaPocketFuncAgent = extIcCardFuncAgent.getFelicaPocketFuncAgent();
        _felicaPocketFuncAgent = felicaPocketFuncAgent;
        this._compiledFpState = felicaPocketFuncAgent.getCompiledFpState();
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.IFuncFelicaPocket
    public FelicaPocketFuncAgent getFelicaPocketFunc() {
        return _felicaPocketFuncAgent;
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.IFuncFelicaPocket
    public boolean needRecommend() {
        return this._needRecommend;
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.IFuncFelicaPocket
    public FelicaPocketFuncAgent.CompiledFpState getCompiledFpState() {
        return this._compiledFpState;
    }

    public void actRecommend() {
        StateController.postStateEvent(StateMachine.Event.EV_RECOMMEND, this, new Object[0]);
    }

    public Intent getDefaultIntent(LinkType linkType) {
        try {
            String str = linkType.ordinal() != 0 ? null : (String) Sg.getValue(Sg.Key.NET_FP_WEB_SITE_LINK_URL);
            if (str != null) {
                return new Intent("android.intent.action.VIEW", Uri.parse(str));
            }
            return null;
        } catch (Exception e) {
            LogUtil.warning(e);
            return null;
        }
    }
}
