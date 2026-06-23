package com.felicanetworks.mfm.main.presenter.agent;

import android.graphics.Bitmap;
import com.felicanetworks.mfm.main.model.info.Linkage;
import com.felicanetworks.mfm.main.model.info.MyCardAdditionalInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import com.felicanetworks.mfm.main.model.info.TransitPassInfo;
import com.felicanetworks.mfm.main.model.info.specific.MyDcardInfo;
import com.felicanetworks.mfm.main.model.info.specific.MySuicaInfo;
import com.felicanetworks.mfm.main.policy.service.SupportServiceType;
import com.felicanetworks.mfm.main.presenter.PresenterData;
import com.felicanetworks.mfm.main.presenter.agent.HistoryAgent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public abstract class MyCardInfoAgent extends InfoAgent {
    private MyCardInfo _client;

    public enum CardStatusForCardDetailUI {
        ACTIVE,
        INACTIVE,
        NOT_EXIST,
        RECOVERY,
        UNPROCESSED,
        HIDDEN
    }

    public interface OrderImgListener {
        void onComplete(Bitmap data);
    }

    Bitmap getCardFaceImage() {
        return null;
    }

    public MyCardInfoAgent(MyCardInfo client) {
        this._client = client;
    }

    MyCardInfo getClient() {
        return this._client;
    }

    public String getCid() {
        return this._client.getCardId();
    }

    public String getServiceId() {
        return this._client.getServiceId();
    }

    public String getContactName() {
        return this._client.getContactName();
    }

    public List<LinkageAgent> getLinkageAppList() {
        ArrayList arrayList = new ArrayList();
        MyCardAdditionalInfo cardAdditionalInfo = this._client.getCardAdditionalInfo();
        if (cardAdditionalInfo != null) {
            Iterator<Linkage> it = cardAdditionalInfo.getLinkageInfoAppList().iterator();
            while (it.hasNext()) {
                arrayList.add(new LinkageAgent(it.next()));
            }
        }
        return arrayList;
    }

    public LinkageAgent getLinkage(int position) {
        if (this._client.getCardAdditionalInfo() == null) {
            return null;
        }
        return new LinkageAgent(this._client.getCardAdditionalInfo().getLinkageInfo(position));
    }

    public List<LinkageAgent> getLinkageOtherList() {
        ArrayList arrayList = new ArrayList();
        MyCardAdditionalInfo cardAdditionalInfo = this._client.getCardAdditionalInfo();
        if (cardAdditionalInfo != null) {
            Iterator<Linkage> it = cardAdditionalInfo.getLinkageInfoOtherList().iterator();
            while (it.hasNext()) {
                arrayList.add(new LinkageAgent(it.next()));
            }
        }
        return arrayList;
    }

    public List<HistoryAgent> getHistory() {
        HistoryAgent.UseType useType;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this._client.getHistory().size(); i++) {
            ServiceInfo.History.HistoryData historyData = this._client.getHistory().get(i);
            int i2 = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$model$info$ServiceInfo$History$HistoryData$UseType[historyData.getUseType().ordinal()];
            if (i2 == 1) {
                useType = HistoryAgent.UseType.CHARGE;
            } else if (i2 == 2) {
                useType = HistoryAgent.UseType.PAYMENT;
            } else if (i2 == 3) {
                useType = HistoryAgent.UseType.TRAFFIC;
            } else {
                useType = HistoryAgent.UseType.OTHER;
            }
            arrayList.add(new HistoryAgent(historyData.getDate(), useType, historyData.getMoney(), historyData.getIsPlus()));
        }
        return arrayList;
    }

    public boolean orderIconImg(final String url, final OrderImgListener listener) {
        return this._client.orderIconImg(PresenterData.getInstance().getContext(), url, new MyCardInfo.OrderImgListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.MyCardInfoAgent.1
            @Override // com.felicanetworks.mfm.main.model.info.MyCardInfo.OrderImgListener
            public void onComplete(final Bitmap data) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.MyCardInfoAgent.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        listener.onComplete(data);
                    }
                });
            }
        });
    }

    public String getBalance() {
        return this._client.getBalance();
    }

    public String getAvailableCredit() {
        return this._client.getAvailableCredit();
    }

    public String getValidPoint() {
        return this._client.getValidPoint();
    }

    public int getPointNum() {
        return this._client.getPointNum();
    }

    public String getCreditLimit() {
        return this._client.getCreditLimit();
    }

    String getCardFaceImageUrl() {
        return this._client.getCardFaceImgUrl();
    }

    String getSpecialCardFaceImageUrl() {
        return this._client.getSpecialCardFaceImgUrl();
    }

    String getCardName() {
        return this._client.getCardName();
    }

    boolean isActiveForeground() {
        return this._client.isActiveForeground();
    }

    public boolean isActiveBackground() {
        return this._client.isActiveBackground();
    }

    public String getIDiDisplayName() {
        return this._client.getIDiDisplayName();
    }

    public String getIDiValue() {
        return this._client.getIDiValue();
    }

    private CardStatusForCardDetailUI getCardStatusForFullIF() {
        CardStatusForCardDetailUI cardStatusForCardDetailUI = CardStatusForCardDetailUI.HIDDEN;
        MyCardInfo.CardStatus cardStatus = this._client.getCardStatus();
        MyCardInfo.CardPosition cardPosition = this._client.getCardPosition();
        MyCardInfo.CardSpStatus cardSpStatus = this._client.getCardSpStatus();
        if (cardStatus == MyCardInfo.CardStatus.STATUS_ACTIVE) {
            if (cardPosition == MyCardInfo.CardPosition.POSITION_FOREGROUND) {
                return CardStatusForCardDetailUI.ACTIVE;
            }
            if (cardPosition == MyCardInfo.CardPosition.POSITION_BACKGROUND) {
                return CardStatusForCardDetailUI.INACTIVE;
            }
            return cardPosition == MyCardInfo.CardPosition.POSITION_PENDING ? CardStatusForCardDetailUI.RECOVERY : cardStatusForCardDetailUI;
        }
        if (cardStatus == MyCardInfo.CardStatus.STATUS_DELETED) {
            if (cardPosition != MyCardInfo.CardPosition.POSITION_NOT_EXIST) {
                return cardPosition == MyCardInfo.CardPosition.POSITION_PENDING ? CardStatusForCardDetailUI.RECOVERY : cardStatusForCardDetailUI;
            }
            if (cardSpStatus == MyCardInfo.CardSpStatus.SP_STATUS_REISSUABLE) {
                return CardStatusForCardDetailUI.NOT_EXIST;
            }
            if (cardSpStatus == MyCardInfo.CardSpStatus.SP_STATUS_PENDING || cardSpStatus == MyCardInfo.CardSpStatus.SP_STATUS_UNKNOWN) {
                return CardStatusForCardDetailUI.UNPROCESSED;
            }
            return cardSpStatus == MyCardInfo.CardSpStatus.SP_STATUS_UNREISSUABLE ? CardStatusForCardDetailUI.HIDDEN : cardStatusForCardDetailUI;
        }
        if (cardStatus == MyCardInfo.CardStatus.STATUS_IN_PROCESS) {
            return CardStatusForCardDetailUI.RECOVERY;
        }
        return cardStatus == MyCardInfo.CardStatus.STATUS_LOST ? CardStatusForCardDetailUI.HIDDEN : cardStatusForCardDetailUI;
    }

    private CardStatusForCardDetailUI getCardStatusForSimpleIF() {
        CardStatusForCardDetailUI cardStatusForCardDetailUI = CardStatusForCardDetailUI.HIDDEN;
        MyCardInfo.CardStatus cardStatus = this._client.getCardStatus();
        MyCardInfo.CardPosition cardPosition = this._client.getCardPosition();
        if (cardStatus != MyCardInfo.CardStatus.STATUS_ACTIVE) {
            return cardStatusForCardDetailUI;
        }
        if (cardPosition == MyCardInfo.CardPosition.POSITION_FOREGROUND) {
            return CardStatusForCardDetailUI.ACTIVE;
        }
        if (cardPosition == MyCardInfo.CardPosition.POSITION_BACKGROUND) {
            return CardStatusForCardDetailUI.INACTIVE;
        }
        return cardPosition == MyCardInfo.CardPosition.POSITION_PENDING ? CardStatusForCardDetailUI.RECOVERY : cardStatusForCardDetailUI;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public CardStatusForCardDetailUI getCardUIStatus(String serviceId) {
        CardStatusForCardDetailUI cardStatusForCardDetailUI = CardStatusForCardDetailUI.HIDDEN;
        switch (AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.resolve(serviceId).ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return CardStatusForCardDetailUI.ACTIVE;
            case 7:
                if (MyCardInfo.SupportMfiServiceType.UNSUPPORTED_MFI_SERVICE_1 == this._client.getUnsupportedMfiService1()) {
                    if (this._client.isActiveForeground()) {
                        return CardStatusForCardDetailUI.ACTIVE;
                    }
                    if (this._client.isActiveBackground()) {
                        return CardStatusForCardDetailUI.INACTIVE;
                    }
                } else if (MyCardInfo.SupportMfiServiceType.SUPPORTED_MFI_SERVICE_1 == this._client.getUnsupportedMfiService1()) {
                    return getCardStatusForFullIF();
                }
                return cardStatusForCardDetailUI;
            case 8:
                return getCardStatusForFullIF();
            case 9:
            case 10:
                return getCardStatusForSimpleIF();
            default:
                return cardStatusForCardDetailUI;
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.presenter.agent.MyCardInfoAgent$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$info$ServiceInfo$History$HistoryData$UseType;

        static {
            int[] iArr = new int[SupportServiceType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType = iArr;
            try {
                iArr[SupportServiceType.A1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.A2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.B1.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.B2.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.C1.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.F1.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.S1.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.T1.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.M1.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.M2.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            int[] iArr2 = new int[ServiceInfo.History.HistoryData.UseType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$info$ServiceInfo$History$HistoryData$UseType = iArr2;
            try {
                iArr2[ServiceInfo.History.HistoryData.UseType.CHARGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ServiceInfo$History$HistoryData$UseType[ServiceInfo.History.HistoryData.UseType.PAYMENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ServiceInfo$History$HistoryData$UseType[ServiceInfo.History.HistoryData.UseType.TRAFFIC.ordinal()] = 3;
            } catch (NoSuchFieldError unused13) {
            }
        }
    }

    public boolean isShowCardDeposit(String serviceId) {
        int i = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.resolve(serviceId).ordinal()];
        if (i != 7) {
            if (i != 8) {
                return false;
            }
            return this._client.isActiveForeground() || this._client.isActiveBackground();
        }
        if (MyCardInfo.SupportMfiServiceType.SUPPORTED_MFI_SERVICE_1 == this._client.getUnsupportedMfiService1()) {
            return this._client.isActiveForeground() || this._client.isActiveBackground();
        }
        return false;
    }

    public List<TransitPassInfoAgent> getTransitPassInfoList() {
        ArrayList arrayList = new ArrayList();
        try {
            Iterator<TransitPassInfo> it = this._client.getCardAdditionalInfo().getAdditionalInfo().transitInfo.transitPassInfoList.iterator();
            while (it.hasNext()) {
                arrayList.add(new TransitPassInfoAgent(it.next()));
            }
        } catch (Exception unused) {
        }
        return arrayList;
    }

    public boolean isAdditionalInfoIllegal() {
        if (this._client.getCardAdditionalInfo() != null) {
            return false;
        }
        switch (SupportServiceType.resolve(this._client.getServiceId())) {
            case S1:
                if (MyCardInfo.SupportMfiServiceType.SUPPORTED_MFI_SERVICE_1 == this._client.getUnsupportedMfiService1()) {
                }
                break;
        }
        return true;
    }

    public static class Factory {
        public static MyCardInfoAgent create(MyServiceInfo service, MyCardInfo client) {
            if (service instanceof MySuicaInfo) {
                return new MySuicaCardInfoAgent((MySuicaInfo) service, client);
            }
            if (service instanceof MyDcardInfo) {
                return new MyDcardCardInfoAgent((MyDcardInfo) service, client);
            }
            return new MyFavoriteCardInfoAgent(client);
        }
    }
}
