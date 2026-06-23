package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.IFuncCardDetail;

/* JADX INFO: loaded from: classes.dex */
public class CardDetailDrawStructure extends CloseDrawStructure implements IFuncCardDetail {
    private final CardDetailFuncAgent agent;

    public CardDetailDrawStructure(CardDetailFuncAgent cardDetailFuncAgent) {
        super(StructureType.CARD_DETAIL);
        this.agent = cardDetailFuncAgent;
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.IFuncCardDetail
    public CardDetailFuncAgent getCardDetailFunc() {
        return this.agent;
    }

    public String getFocusSid() {
        return this.agent.getServiceInfo().getId();
    }

    public String getFocusCid() {
        return this.agent.getCardInfo() == null ? "" : this.agent.getCardInfo().getCid();
    }
}
