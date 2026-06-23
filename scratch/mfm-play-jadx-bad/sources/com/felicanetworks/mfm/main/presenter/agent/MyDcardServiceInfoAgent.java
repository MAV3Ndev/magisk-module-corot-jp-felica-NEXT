package com.felicanetworks.mfm.main.presenter.agent;

import android.graphics.Bitmap;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.specific.MyDcardInfo;
import com.felicanetworks.mfm.main.presenter.agent.ServiceInfoAgent;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class MyDcardServiceInfoAgent extends MyServiceInfoAgent {
    private final MyDcardInfo client;

    MyDcardServiceInfoAgent(MyDcardInfo service, List<MyCardInfo> cards, boolean isMfiLoggedIn, boolean isChaced) {
        super(service, cards, isMfiLoggedIn, isChaced);
        this.client = service;
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent
    public boolean hasMoreInformation() {
        if (isIllegalDcard()) {
            return false;
        }
        return super.hasMoreInformation();
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.ServiceInfoAgent
    public ServiceInfoAgent.PostpayEmoney getPostpayEmoney() {
        if (getActiveDcardMiniCard() == null) {
            return null;
        }
        return super.getPostpayEmoney();
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent
    public boolean isActiveService() {
        if (isIllegalDcard()) {
            return true;
        }
        return super.isActiveService();
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent, com.felicanetworks.mfm.main.presenter.agent.ServiceInfoAgent
    public LinkageAgent getLinkage() {
        if (isIllegalDcard()) {
            return this._linkage;
        }
        return super.getLinkage();
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent
    public String getServiceName() {
        MyDcardCardInfoAgent activeDcardMiniCard = getActiveDcardMiniCard();
        if (activeDcardMiniCard != null) {
            return activeDcardMiniCard.getDcardMiniName();
        }
        return super.getServiceName();
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent
    public Bitmap getCardFaceImage() {
        MyDcardCardInfoAgent activeDcardMiniCard = getActiveDcardMiniCard();
        if (activeDcardMiniCard != null) {
            return activeDcardMiniCard.getDcardMiniFaceImage();
        }
        return super.getCardFaceImage();
    }

    private boolean isLegacy() {
        return this.client.isAreaDetect() && this.cards.isEmpty();
    }

    private boolean isIllegalDcard() {
        if (isLegacy()) {
            return true;
        }
        if (!this.client.isAreaDetect()) {
            return false;
        }
        Iterator<MyCardInfoAgent> it = this.cards.iterator();
        while (it.hasNext()) {
            if (!it.next().isActiveBackground()) {
                return false;
            }
        }
        return true;
    }

    private MyDcardCardInfoAgent getActiveDcardMiniCard() {
        MyCardInfoAgent mainMyCardInfoAgent = getMainMyCardInfoAgent();
        if (!(mainMyCardInfoAgent instanceof MyDcardCardInfoAgent)) {
            return null;
        }
        MyDcardCardInfoAgent myDcardCardInfoAgent = (MyDcardCardInfoAgent) mainMyCardInfoAgent;
        if (myDcardCardInfoAgent.isActiveForeground() && myDcardCardInfoAgent.isDcardMini()) {
            return myDcardCardInfoAgent;
        }
        return null;
    }
}
