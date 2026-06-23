package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.presenter.action.IActionCardDetail;
import com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.IFuncCardDetail;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceGroupInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes3.dex */
public class CardDetailDrawStructure extends CloseDrawStructure implements IFuncCardDetail, IActionCardDetail {
    private final CardDetailFuncAgent agent;

    public CardDetailDrawStructure(CardDetailFuncAgent agent) {
        super(StructureType.CARD_DETAIL);
        this.agent = agent;
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.IFuncCardDetail
    public CardDetailFuncAgent getCardDetailFunc() {
        return this.agent;
    }

    public String getFocusSid() {
        return this.agent.getServiceInfo().getId();
    }

    public String getFocusCid() {
        if (this.agent.getCardInfo() == null) {
            return "";
        }
        return this.agent.getCardInfo().getCid();
    }

    public boolean isRunning() {
        return this.agent.isRunning();
    }

    public boolean isUpdateCacheError() {
        return this.agent.isUpdateCacheError();
    }

    public void updateErrorInitialization() {
        this.agent.updateErrorInitialization();
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionCardDetail
    public void actCardDetail(MyServiceGroupInfoAgent groupInfo, MyServiceInfoAgent serviceInfo) {
        StateController.postStateEvent(StateMachine.Event.EV_CARD_DETAIL, this, groupInfo, serviceInfo);
    }
}
