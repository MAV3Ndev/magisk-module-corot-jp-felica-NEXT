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

/* JADX INFO: loaded from: classes.dex */
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

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.presenter.structure.FpServiceListDrawStructure$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FpServiceListDrawStructure$LinkType;

        static {
            int[] iArr = new int[LinkType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FpServiceListDrawStructure$LinkType = iArr;
            try {
                iArr[LinkType.FP_SITE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public Intent getDefaultIntent(LinkType linkType) {
        try {
            String str = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$FpServiceListDrawStructure$LinkType[linkType.ordinal()] != 1 ? null : (String) Sg.getValue(Sg.Key.NET_FP_WEB_SITE_LINK_URL);
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
