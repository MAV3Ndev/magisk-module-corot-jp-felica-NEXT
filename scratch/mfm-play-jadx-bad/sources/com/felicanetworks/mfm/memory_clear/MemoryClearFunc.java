package com.felicanetworks.mfm.memory_clear;

import com.felicanetworks.mfm.main.model.CentralFunc;
import com.felicanetworks.mfm.main.model.info.AssetInfo;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.Service;
import com.felicanetworks.mfm.main.model.internal.main.ModelContext;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public interface MemoryClearFunc {

    public interface CheckVersionUpListener {
        void onCompleted(boolean isVersionUp);

        void onError(ModelErrorInfo modelErrorInfo);
    }

    public interface ClearMemoryListener {
        void onCompleted();

        void onError(ModelErrorInfo modelErrorInfo);
    }

    public interface CompileListener {
        void onCompleted();

        void onError(ModelErrorInfo modelErrorInfo);
    }

    public interface DeleteListener {
        void onCompleted();

        void onError(ModelErrorInfo modelErrorInfo);
    }

    public interface GetAssetListener {
        void onCompleted(List<AssetInfo> assets);

        void onError(ModelErrorInfo modelErrorInfo);
    }

    public interface GetRemainedCardsListener {
        void onCompleted(List<MyCardInfo> displayList, List<MyCardInfo> notDisplayList);

        void onError(ModelErrorInfo modelErrorInfo);
    }

    public interface IssueStateListener extends CentralFunc.PrecompileListener {
    }

    void checkIssueState(IssueStateListener listener);

    void checkVersionUp(CheckVersionUpListener listener);

    void clearMemory(ClearMemoryListener listener);

    void compile(boolean isCheckNow, CompileListener listener);

    void deleteCards(DeleteListener listener);

    List<Service> getAreaDetectedServiceList();

    void getAssetList(GetAssetListener listener);

    List<MemoryClearServiceInfo> getMemoryClearServiceList();

    ModelContext getModelContext();

    void getRemainedDeleteCards(GetRemainedCardsListener listener);

    String getServiceNameFromServiceId(String serviceId);

    void mfcExpertDeInitialize();

    void mfcExpertInitialize() throws MfcException;

    void startUserOperation();

    public static class MemoryClearServiceInfo {
        private static final int CARD_ACTIVE_BACKGROUND = 10;
        private static final int CARD_ACTIVE_FOREGROUND = 1;
        private static final int CARD_ACTIVE_PENDING = 100;
        private static final int NO_CARD = 0;
        Service service;
        private int hasCardStatusPosition = 0;
        List<MyCardInfo> cards = new ArrayList();

        public MemoryClearServiceInfo(Service s) {
            this.service = s;
        }

        public void addCard(MyCardInfo card) {
            Iterator<MyCardInfo> it = this.cards.iterator();
            while (it.hasNext()) {
                if (it.next().cid().equals(card.cid())) {
                    return;
                }
            }
            this.cards.add(card);
            if (card.getCardStatus() == MyCardInfo.CardStatus.STATUS_ACTIVE) {
                if (card.getCardPosition() == MyCardInfo.CardPosition.POSITION_FOREGROUND) {
                    this.hasCardStatusPosition |= 1;
                } else if (card.getCardPosition() == MyCardInfo.CardPosition.POSITION_BACKGROUND) {
                    this.hasCardStatusPosition |= 10;
                } else if (card.getCardPosition() == MyCardInfo.CardPosition.POSITION_PENDING) {
                    this.hasCardStatusPosition |= 100;
                }
            }
        }

        public Service getService() {
            return this.service;
        }

        public String getServiceId() {
            return this.service.getServiceId();
        }

        public boolean hasActiveForegroundCard() {
            return (this.hasCardStatusPosition & 1) != 0;
        }

        public boolean hasActiveBackgroundCard() {
            return (this.hasCardStatusPosition & 10) != 0;
        }

        public boolean hasActivePendingCard() {
            return (this.hasCardStatusPosition & 100) != 0;
        }
    }
}
