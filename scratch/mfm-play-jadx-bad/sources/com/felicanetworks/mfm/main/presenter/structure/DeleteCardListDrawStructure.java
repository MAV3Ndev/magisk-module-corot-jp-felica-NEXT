package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.presenter.agent.DeleteCardListFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.IFuncDeleteCardList;

/* JADX INFO: loaded from: classes3.dex */
public class DeleteCardListDrawStructure extends CloseDrawStructure implements IFuncDeleteCardList {
    private final DeleteCardListFuncAgent agent;

    public DeleteCardListDrawStructure(DeleteCardListFuncAgent agent) {
        super(StructureType.DELETE_CARD_LIST);
        this.agent = agent;
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.IFuncDeleteCardList
    public DeleteCardListFuncAgent getDeleteCardListFunc() {
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
}
