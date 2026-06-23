package com.felicanetworks.mfm.main.presenter.agent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.felicanetworks.mfm.main.model.info.GeneralInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.info.specific.MyDcardInfo;
import com.felicanetworks.mfm.main.model.info.specific.MyQUICPayInfo;
import com.felicanetworks.mfm.main.model.info.specific.MySuicaInfo;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.agent.LinkageAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyCardInfoAgent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public abstract class MyServiceInfoAgent extends ServiceInfoAgent {
    protected final List<MyCardInfoAgent> cards;
    private final MyServiceInfo client;
    private boolean hidden;
    protected boolean isChaced;
    protected boolean isMfiLoggedIn;

    public enum LeadType {
        NONE,
        REGISTER_SERVICE,
        MFI_LOGIN,
        MFI_LOGIN_FOR_SUICA
    }

    public String toString() {
        return "MyServiceInfoAgent{client=" + this.client + ", cards=" + this.cards + '}';
    }

    MyServiceInfoAgent(MyServiceInfo service, List<MyCardInfo> cards, boolean isMfiLoggedIn, boolean isChaced) {
        super(service);
        this.cards = new ArrayList();
        this.hidden = false;
        this.client = service;
        if (cards != null) {
            Iterator<MyCardInfo> it = cards.iterator();
            while (it.hasNext()) {
                this.cards.add(MyCardInfoAgent.Factory.create(service, it.next()));
            }
        }
        this.isMfiLoggedIn = isMfiLoggedIn;
        this.isChaced = isChaced;
    }

    List<MyCardInfoAgent> getCards() {
        return this.cards;
    }

    public Map<Integer, String> getCardImageUrls(String cardId) {
        HashMap map = new HashMap();
        Iterator<MyCardInfoAgent> it = this.cards.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            MyCardInfoAgent next = it.next();
            if (TextUtils.equals(cardId, next.getCid())) {
                map.put(Integer.valueOf(GeneralInfo.CARD_ART_PRIORITIZATION.DEFAULT.ordinal()), next.getCardFaceImageUrl());
                map.put(Integer.valueOf(GeneralInfo.CARD_ART_PRIORITIZATION.SPECIAL.ordinal()), next.getSpecialCardFaceImageUrl());
                break;
            }
        }
        return map;
    }

    public MyServiceInfo.MfiServiceFlag getMfiServiceFlag() {
        return this.client.getMfiServiceFlag();
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.ServiceInfoAgent
    public LinkageAgent getLinkage() {
        MyCardInfoAgent mainMyCardInfoAgent = getMainMyCardInfoAgent();
        if (mainMyCardInfoAgent == null || mainMyCardInfoAgent.getLinkage(0) == null) {
            return super.getLinkage();
        }
        return mainMyCardInfoAgent.getLinkage(0);
    }

    public boolean isActiveService() {
        MyCardInfoAgent mainMyCardInfoAgent = getMainMyCardInfoAgent();
        if (mainMyCardInfoAgent == null) {
            return true;
        }
        return mainMyCardInfoAgent.isActiveForeground();
    }

    void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public LinkageAgent.LinkageInfo getLinkageInfo() {
        LinkageAgent linkage = getLinkage();
        if (linkage == null) {
            return null;
        }
        return linkage.getLinkageInfo();
    }

    public String getServiceName() {
        Iterator<MyCardInfoAgent> it = this.cards.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            MyCardInfoAgent next = it.next();
            if (next.isActiveForeground()) {
                if (!TextUtils.isEmpty(next.getCardName())) {
                    return next.getCardName();
                }
            }
        }
        return getName();
    }

    public String getCardName(String cardId) {
        String textFromAdditionalInfo = getTextFromAdditionalInfo(cardId);
        return textFromAdditionalInfo == null ? this.client.getName() : textFromAdditionalInfo;
    }

    public Bitmap getCardFaceImage() {
        try {
            byte[] cardFaceImage = this.client.getService().getCardFaceImage();
            if (cardFaceImage != null) {
                return BitmapFactory.decodeByteArray(cardFaceImage, 0, cardFaceImage.length);
            }
            return null;
        } catch (Throwable th) {
            LogUtil.warning(th);
            return null;
        }
    }

    public Bitmap getCardFaceImage(String cardId) {
        MyCardInfoAgent myCardInfoAgentFindCard = findCard(cardId);
        Bitmap cardFaceImage = myCardInfoAgentFindCard != null ? myCardInfoAgentFindCard.getCardFaceImage() : null;
        return cardFaceImage != null ? cardFaceImage : getCardFaceImage();
    }

    public String getCardId() {
        for (MyCardInfoAgent myCardInfoAgent : this.cards) {
            if (myCardInfoAgent.isActiveForeground()) {
                return myCardInfoAgent.getCid();
            }
        }
        return null;
    }

    public String getContactName(String cardId) {
        for (MyCardInfoAgent myCardInfoAgent : this.cards) {
            if (TextUtils.equals(cardId, myCardInfoAgent.getCid())) {
                return myCardInfoAgent.getContactName();
            }
        }
        return this.client.getProvider();
    }

    public List<LinkageAgent> getLinkageAppList(String cardId) {
        ArrayList arrayList = new ArrayList();
        Iterator<MyCardInfoAgent> it = this.cards.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            MyCardInfoAgent next = it.next();
            if (TextUtils.equals(cardId, next.getCid())) {
                if (next.getLinkageAppList().size() > 0) {
                    return next.getLinkageAppList();
                }
            }
        }
        return arrayList;
    }

    public List<LinkageAgent> getLinkageOtherList(String cardId) {
        ArrayList arrayList = new ArrayList();
        for (MyCardInfoAgent myCardInfoAgent : this.cards) {
            if (TextUtils.equals(cardId, myCardInfoAgent.getCid())) {
                return myCardInfoAgent.getLinkageOtherList();
            }
        }
        return arrayList;
    }

    public int getPointValue() {
        if (getPoint() == null || getPoint().getPointDataList() == null || getPoint().getPointDataList().isEmpty()) {
            return -1;
        }
        return getPoint().getPointDataList().get(0).getPoint();
    }

    public boolean hasMoreInformation() {
        if (getLeadType() != LeadType.NONE) {
            return false;
        }
        return this.client.isShowDetailIcon();
    }

    public boolean hasDetailInformation() {
        return this.client.isShowDetailIcon();
    }

    public MyCardInfoAgent findCard(String cardId) {
        for (MyCardInfoAgent myCardInfoAgent : this.cards) {
            if (TextUtils.equals(cardId, myCardInfoAgent.getCid())) {
                return myCardInfoAgent;
            }
        }
        return null;
    }

    public List<MyCardInfoAgent> getMyCardInfoAgentList() {
        return this.cards;
    }

    public boolean isType2() {
        return this.client.isType2();
    }

    public boolean isMfiService() {
        return this.client.isMfiService();
    }

    boolean isNoRegister() {
        return this.client.getRegisterState() == MyServiceInfo.RegisterState.NO_REGISTER;
    }

    boolean hasActiveForegroundCard() {
        if (this.cards.isEmpty()) {
            return false;
        }
        Iterator<MyCardInfoAgent> it = this.cards.iterator();
        while (it.hasNext()) {
            if (it.next().isActiveForeground()) {
                return true;
            }
        }
        return false;
    }

    private String getTextFromAdditionalInfo(String cardId) {
        if (cardId == null) {
            return null;
        }
        for (MyCardInfoAgent myCardInfoAgent : this.cards) {
            if (TextUtils.equals(cardId, myCardInfoAgent.getCid())) {
                return myCardInfoAgent.getCardName();
            }
        }
        return null;
    }

    public MyCardInfoAgent getMainMyCardInfoAgent() {
        if (this.cards.isEmpty()) {
            return null;
        }
        for (MyCardInfoAgent myCardInfoAgent : this.cards) {
            if (myCardInfoAgent.isActiveForeground()) {
                return myCardInfoAgent;
            }
        }
        return this.cards.get(0);
    }

    public MyServiceInfo getClient() {
        return this.client;
    }

    public LeadType getLeadType() {
        if (isMfiService()) {
            if (!this.isMfiLoggedIn && !this.isChaced) {
                return LeadType.MFI_LOGIN;
            }
            if (isNoRegister()) {
                return LeadType.REGISTER_SERVICE;
            }
        } else if (isNoRegister()) {
            return LeadType.REGISTER_SERVICE;
        }
        return LeadType.NONE;
    }

    public boolean hasAdditionalInfoIllegalCard() {
        Iterator<MyCardInfoAgent> it = this.cards.iterator();
        while (it.hasNext()) {
            if (it.next().isAdditionalInfoIllegal()) {
                return true;
            }
        }
        return false;
    }

    public boolean isShowCardShadowImage() {
        return getMyCardInfoAgentList().size() > 1;
    }

    public static class Factory {
        public static MyServiceInfoAgent create(MyServiceInfo service, List<MyCardInfo> cards, boolean isMfiLoggedIn, boolean isChaced) {
            if (service instanceof MySuicaInfo) {
                return new MySuicaServiceInfoAgent((MySuicaInfo) service, cards, isMfiLoggedIn, isChaced);
            }
            if (service instanceof MyDcardInfo) {
                return new MyDcardServiceInfoAgent((MyDcardInfo) service, cards, isMfiLoggedIn, isChaced);
            }
            if (service instanceof MyQUICPayInfo) {
                return new MyQUICPayServiceInfoAgent((MyQUICPayInfo) service, cards, isMfiLoggedIn, isChaced);
            }
            return new MyFavoriteServiceInfoAgent(service, cards, isMfiLoggedIn, isChaced);
        }
    }
}
