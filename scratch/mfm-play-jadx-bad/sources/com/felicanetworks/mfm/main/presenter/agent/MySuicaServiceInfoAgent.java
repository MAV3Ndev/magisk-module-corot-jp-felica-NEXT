package com.felicanetworks.mfm.main.presenter.agent;

import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.specific.MySuicaInfo;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class MySuicaServiceInfoAgent extends MyServiceInfoAgent {
    private final MySuicaInfo client;

    MySuicaServiceInfoAgent(MySuicaInfo service, List<MyCardInfo> cards, boolean isMfiLoggedIn, boolean isChaced) {
        super(service, cards, isMfiLoggedIn, isChaced);
        this.client = service;
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent
    boolean hasActiveForegroundCard() {
        if (getMainMyCardInfoAgent() == null) {
            return this.client.isActive();
        }
        return super.hasActiveForegroundCard();
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent
    public boolean isActiveService() {
        MyCardInfoAgent mainMyCardInfoAgent = getMainMyCardInfoAgent();
        if (mainMyCardInfoAgent == null) {
            return this.client.isActive();
        }
        return mainMyCardInfoAgent.isActiveForeground();
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent
    public MyServiceInfoAgent.LeadType getLeadType() {
        if (isNoRegister()) {
            if (this.isMfiLoggedIn) {
                return MyServiceInfoAgent.LeadType.REGISTER_SERVICE;
            }
            if (!this.isChaced) {
                return MyServiceInfoAgent.LeadType.MFI_LOGIN;
            }
            return MyServiceInfoAgent.LeadType.REGISTER_SERVICE;
        }
        if (!this.isMfiLoggedIn) {
            if (this.client.getPosition().equals(MySuicaInfo.Position.DISABLE)) {
                return MyServiceInfoAgent.LeadType.MFI_LOGIN_FOR_SUICA;
            }
            if (this.client.getPosition().equals(MySuicaInfo.Position.ENABLE)) {
                return MyServiceInfoAgent.LeadType.NONE;
            }
            if (!this.isChaced) {
                return MyServiceInfoAgent.LeadType.MFI_LOGIN;
            }
        }
        return MyServiceInfoAgent.LeadType.NONE;
    }
}
