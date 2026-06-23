package com.felicanetworks.mfm.main.model.internal.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import androidx.core.view.InputDeviceCompat;
import com.felicanetworks.mfm.main.model.CardDetailFunc;
import com.felicanetworks.mfm.main.model.MfiCardFunc;
import com.felicanetworks.mfm.main.model.info.AdditionalInfo;
import com.felicanetworks.mfm.main.model.info.CommonInfo;
import com.felicanetworks.mfm.main.model.info.ContactInfo;
import com.felicanetworks.mfm.main.model.info.DetailLinkageApp;
import com.felicanetworks.mfm.main.model.info.DetailLinkageWeb;
import com.felicanetworks.mfm.main.model.info.GeneralInfo;
import com.felicanetworks.mfm.main.model.info.LinkageApplicationInfo;
import com.felicanetworks.mfm.main.model.info.LinkageInfo;
import com.felicanetworks.mfm.main.model.info.MfiCardAdditionalInfo;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.MyCardAdditionalInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.info.Service;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import com.felicanetworks.mfm.main.model.info.specific.MyDcardInfo;
import com.felicanetworks.mfm.main.model.internal.main.FuncUtil;
import com.felicanetworks.mfm.main.model.internal.main.OrderImageWorker;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaParams;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.service.IdPicker;
import com.felicanetworks.mfm.main.policy.service.ServiceGroupType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes3.dex */
public class CardDetailFuncEntity implements CardDetailFunc {
    private static final String RUN_NAME_CREATE_DETAIL_CARD_INFO = "createDetailCardInfo";
    private static final String RUN_NAME_DELETE_CARD = "deleteCard";
    private static final String RUN_NAME_ENABLE_CARD = "enableCard";
    private static final String RUN_NAME_GET_NOT_FOUND_IMAGE = "getNotFoundImage";
    private final ModelContext _context;
    private final DatabaseExpert _db;
    private final Map<String, FuncUtil.AsyncRunner> _runners = new HashMap();
    private List<MyServiceInfo> detailServices = new ArrayList();
    private List<MyCardInfo> detailCards = new ArrayList();
    private CountDownLatch _cardOperationLatch = new CountDownLatch(1);

    private FuncUtil.AsyncRunner getRunner(String methodName) {
        FuncUtil.AsyncRunner asyncRunnerRemove;
        if (this._runners.containsKey(methodName) && (asyncRunnerRemove = this._runners.remove(methodName)) != null) {
            asyncRunnerRemove.shutdown();
        }
        FuncUtil.AsyncRunner asyncRunner = new FuncUtil.AsyncRunner();
        this._runners.put(methodName, asyncRunner);
        return asyncRunner;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeRunner(String methodName) {
        FuncUtil.AsyncRunner asyncRunnerRemove;
        if (!this._runners.containsKey(methodName) || (asyncRunnerRemove = this._runners.remove(methodName)) == null) {
            return;
        }
        asyncRunnerRemove.shutdown();
    }

    @Override // com.felicanetworks.mfm.main.model.CardDetailFunc
    public boolean isUserOperation() {
        boolean z = false;
        for (Map.Entry<String, FuncUtil.AsyncRunner> entry : this._runners.entrySet()) {
            if (entry.getKey().equals(RUN_NAME_ENABLE_CARD) || entry.getKey().equals(RUN_NAME_DELETE_CARD)) {
                if (entry.getValue().isRunning()) {
                    z = true;
                }
            }
        }
        return z;
    }

    public void checkUserOperation() throws InterruptedException {
        for (Map.Entry<String, FuncUtil.AsyncRunner> entry : this._runners.entrySet()) {
            if (entry.getKey().equals(RUN_NAME_ENABLE_CARD) || entry.getKey().equals(RUN_NAME_DELETE_CARD)) {
                if (entry.getValue().isRunning()) {
                    throw new InterruptedException();
                }
            }
        }
    }

    CardDetailFuncEntity(ModelContext context) {
        this._context = context;
        this._db = context.getOpenedDatabaseExpert();
    }

    @Override // com.felicanetworks.mfm.main.model.CardDetailFunc
    public void setup(List<MyServiceInfo> myServices, List<MyCardInfo> myCards) {
        this.detailServices.clear();
        this.detailCards.clear();
        for (MyServiceInfo myServiceInfo : myServices) {
            if (myServiceInfo.getRegisterState() != MyServiceInfo.RegisterState.NO_REGISTER) {
                ArrayList arrayList = new ArrayList();
                for (MyCardInfo myCardInfo : myCards) {
                    if (TextUtils.equals(myServiceInfo.getId(), myCardInfo.getServiceId())) {
                        if (TextUtils.equals(FeliCaParams.SERVICE_ID_SUICA, myCardInfo.getServiceId()) && MyCardInfo.SupportMfiServiceType.UNSUPPORTED_MFI_SERVICE_1 == myCardInfo.getUnsupportedMfiService1()) {
                            arrayList.add(applyAdditionalInfoForSuica(myServiceInfo, myCardInfo));
                        } else {
                            arrayList.add(myCardInfo);
                        }
                    }
                }
                if (arrayList.isEmpty()) {
                    arrayList.add(createMyCardInfoFromMyServiceInfo(myServiceInfo));
                }
                this.detailServices.add(myServiceInfo);
                this.detailCards.addAll(arrayList);
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.model.CardDetailFunc
    public void createDetailCardInfo(final boolean isMfiLoggedIn, final boolean forceUpdate, final CardDetailFunc.CreateDetailCardInfoListener listener) {
        final FuncUtil.AsyncRunner runner = getRunner(RUN_NAME_CREATE_DETAIL_CARD_INFO);
        this._context.setCardDetailFuncRunner(runner);
        runner.startupOrThrow(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.1
            @Override // java.lang.Runnable
            public void run() {
                FuncUtil.AsyncRunner asyncRunner;
                FuncUtil.Notify notify;
                try {
                    try {
                        try {
                            if (CardDetailFuncEntity.this.detailServices.isEmpty()) {
                                runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_OTHER_ERROR, new MfmException(CardDetailFuncEntity.class, 1043)));
                            } else {
                                ArrayList arrayList = new ArrayList();
                                Iterator it = CardDetailFuncEntity.this.detailServices.iterator();
                                while (it.hasNext()) {
                                    arrayList.add(((MyServiceInfo) it.next()).getId());
                                }
                                runner.checkInterrupted();
                                int i = AnonymousClass6.$SwitchMap$com$felicanetworks$mfm$main$policy$service$ServiceGroupType[ServiceGroupType.resolve(arrayList).ordinal()];
                                if (i != 1) {
                                    if (i == 2) {
                                        if (forceUpdate) {
                                            if (CardDetailFuncEntity.this.hasCacheCard()) {
                                                CardDetailFuncEntity cardDetailFuncEntity = CardDetailFuncEntity.this;
                                                cardDetailFuncEntity.updateCacheInformation(forceUpdate, cardDetailFuncEntity.detailServices, CardDetailFuncEntity.this.detailCards, runner);
                                            } else {
                                                CardDetailFuncEntity cardDetailFuncEntity2 = CardDetailFuncEntity.this;
                                                cardDetailFuncEntity2.updateMfiInformation(forceUpdate, cardDetailFuncEntity2.detailServices, CardDetailFuncEntity.this.detailCards, runner);
                                            }
                                            CardDetailFuncEntity cardDetailFuncEntity3 = CardDetailFuncEntity.this;
                                            if (!cardDetailFuncEntity3.checkEnableCard(cardDetailFuncEntity3.detailServices, CardDetailFuncEntity.this.detailCards)) {
                                                runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_OTHER_ERROR, new MfmException(CardDetailFuncEntity.class, 259)));
                                            }
                                        } else {
                                            CardDetailFuncEntity cardDetailFuncEntity4 = CardDetailFuncEntity.this;
                                            cardDetailFuncEntity4.updateDcardBalanceDetail(cardDetailFuncEntity4.detailServices, CardDetailFuncEntity.this.detailCards);
                                        }
                                    } else {
                                        CardDetailFuncEntity cardDetailFuncEntity5 = CardDetailFuncEntity.this;
                                        cardDetailFuncEntity5.updateBalance(cardDetailFuncEntity5.detailServices, CardDetailFuncEntity.this.detailCards, runner);
                                    }
                                } else if (!isMfiLoggedIn) {
                                    if (CardDetailFuncEntity.this.hasCacheCard()) {
                                        CardDetailFuncEntity cardDetailFuncEntity6 = CardDetailFuncEntity.this;
                                        cardDetailFuncEntity6.updateCacheInformation(forceUpdate, cardDetailFuncEntity6.detailServices, CardDetailFuncEntity.this.detailCards, runner);
                                    } else {
                                        CardDetailFuncEntity cardDetailFuncEntity7 = CardDetailFuncEntity.this;
                                        cardDetailFuncEntity7.updateBalance(cardDetailFuncEntity7.detailServices, CardDetailFuncEntity.this.detailCards, runner);
                                    }
                                } else {
                                    CardDetailFuncEntity cardDetailFuncEntity8 = CardDetailFuncEntity.this;
                                    cardDetailFuncEntity8.updateMfiInformation(forceUpdate, cardDetailFuncEntity8.detailServices, CardDetailFuncEntity.this.detailCards, runner);
                                    CardDetailFuncEntity cardDetailFuncEntity9 = CardDetailFuncEntity.this;
                                    if (!cardDetailFuncEntity9.checkEnableCard(cardDetailFuncEntity9.detailServices, CardDetailFuncEntity.this.detailCards)) {
                                        runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_OTHER_ERROR, new MfmException(CardDetailFuncEntity.class, 258)));
                                    }
                                }
                            }
                        } catch (MfcException e) {
                            runner.putFailure(CardDetailFuncEntity.this.convertErrorToCompiledState(e).getMficWarningState());
                            asyncRunner = runner;
                            notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.1.1
                                @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                public void go() {
                                    if (!runner.hasFailure()) {
                                        listener.onCompleted(CardDetailFuncEntity.this.detailServices, CardDetailFuncEntity.this.detailCards);
                                    } else {
                                        listener.onError((ModelErrorInfo) runner.getFailure());
                                    }
                                    CardDetailFuncEntity.this._context.setCardDetailFuncRunner(null);
                                }
                            };
                            FuncUtil.notifySafety(asyncRunner, notify);
                        }
                    } catch (DatabaseException e2) {
                        runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.DB_ACCESS_ERROR, e2));
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.1.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (!runner.hasFailure()) {
                                    listener.onCompleted(CardDetailFuncEntity.this.detailServices, CardDetailFuncEntity.this.detailCards);
                                } else {
                                    listener.onError((ModelErrorInfo) runner.getFailure());
                                }
                                CardDetailFuncEntity.this._context.setCardDetailFuncRunner(null);
                            }
                        };
                        FuncUtil.notifySafety(asyncRunner, notify);
                    } catch (InterruptedException unused) {
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.1.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (!runner.hasFailure()) {
                                    listener.onCompleted(CardDetailFuncEntity.this.detailServices, CardDetailFuncEntity.this.detailCards);
                                } else {
                                    listener.onError((ModelErrorInfo) runner.getFailure());
                                }
                                CardDetailFuncEntity.this._context.setCardDetailFuncRunner(null);
                            }
                        };
                        FuncUtil.notifySafety(asyncRunner, notify);
                    }
                } finally {
                    FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.1.1
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (!runner.hasFailure()) {
                                listener.onCompleted(CardDetailFuncEntity.this.detailServices, CardDetailFuncEntity.this.detailCards);
                            } else {
                                listener.onError((ModelErrorInfo) runner.getFailure());
                            }
                            CardDetailFuncEntity.this._context.setCardDetailFuncRunner(null);
                        }
                    });
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMfiInformation(boolean forceUpdate, List<MyServiceInfo> services, List<MyCardInfo> cards, FuncUtil.AsyncRunner runner) throws MfcException, InterruptedException, DatabaseException {
        MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        if (forceUpdate) {
            final FuncUtil.AsyncPacket asyncPacket = new FuncUtil.AsyncPacket();
            runner.checkInterrupted();
            initializedMfcExpert.mfiStart(new MfcExpert.MfiStartListener() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.2
                @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                public void onSuccessNoLogin() {
                }

                @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                public void onSuccess(boolean executedSilentStart) {
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                public void onRequestActivity(Intent intent) {
                    asyncPacket.set(new MfcException(CardDetailFuncEntity.class, 769, MfcException.CognitiveType.NOT_LOGIN));
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                public void errorResult(MfcException e) {
                    asyncPacket.set(e);
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await();
            if (asyncPacket.exist()) {
                throw ((MfcException) asyncPacket.get());
            }
            runner.checkInterrupted();
            List<MyCardInfo> cardInfoList = initializedMfcExpert.fetchCardList().getCardInfoList();
            cards.clear();
            for (MyCardInfo myCardInfo : cardInfoList) {
                MyServiceInfo myServiceInfo = (MyServiceInfo) IdPicker.pickupWithSid(services, myCardInfo.getServiceId());
                if (myServiceInfo != null) {
                    if (TextUtils.equals(FeliCaParams.SERVICE_ID_SUICA, myCardInfo.getServiceId()) && MyCardInfo.SupportMfiServiceType.UNSUPPORTED_MFI_SERVICE_1 == myCardInfo.getUnsupportedMfiService1()) {
                        cards.add(applyAdditionalInfoForSuica(myServiceInfo, myCardInfo));
                    } else {
                        cards.add(myCardInfo);
                    }
                }
            }
            runner.checkInterrupted();
            updateCardAdditionalInfoIfNeeded(cards, runner);
        }
        updateBalance(services, cards, runner);
    }

    private void updateCardAdditionalInfoIfNeeded(List<MyCardInfo> detailCards, FuncUtil.AsyncRunner runner) throws MfcException, InterruptedException, DatabaseException {
        MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (MyCardInfo myCardInfo : detailCards) {
            if (MyCardInfo.SupportMfiServiceType.UNSUPPORTED_MFI_SERVICE_1 != myCardInfo.getUnsupportedMfiService1()) {
                String cardId = myCardInfo.getCardId();
                String mfiHashValue = initializedMfcExpert.getMfiHashValue(cardId);
                if (mfiHashValue == null) {
                    mfiHashValue = myCardInfo.getMfiAdditionalInfoHashValue();
                }
                arrayList.add(cardId);
                arrayList2.add(new DatabaseExpert.MfiHashValueInfo(cardId, mfiHashValue));
            }
        }
        runner.checkInterrupted();
        String[] strArr = (String[]) arrayList.toArray(new String[0]);
        if (strArr.length > 0) {
            List<MyCardAdditionalInfo> listFetchCardAdditionalInfoList = initializedMfcExpert.fetchCardAdditionalInfoList(strArr);
            for (MyCardAdditionalInfo myCardAdditionalInfo : listFetchCardAdditionalInfoList) {
                if (myCardAdditionalInfo.getAdditionalInfo() == null) {
                    this._db.deleteMfiCardAdditionalInfo(myCardAdditionalInfo.getCid());
                }
            }
            this._db.setMfiCardAdditionalInfo(listFetchCardAdditionalInfoList, arrayList2);
        }
        runner.checkInterrupted();
        ArrayList arrayList3 = new ArrayList();
        for (MfiCardAdditionalInfo mfiCardAdditionalInfo : this._db.getMfiCardAdditionalInfo()) {
            arrayList3.add(CardFuncUtility.convertCardAdditionalDbToInfo(this._context, mfiCardAdditionalInfo, mfiCardAdditionalInfo.transitInfo));
        }
        for (int i = 0; i < detailCards.size(); i++) {
            MyCardInfo myCardInfo2 = detailCards.get(i);
            for (int i2 = 0; i2 < arrayList3.size(); i2++) {
                if (myCardInfo2.getCardId().equals(((MyCardAdditionalInfo) arrayList3.get(i2)).getCid())) {
                    myCardInfo2.setCardAdditionalInfo((MyCardAdditionalInfo) arrayList3.get(i2));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCacheInformation(boolean forceUpdate, List<MyServiceInfo> services, List<MyCardInfo> cards, FuncUtil.AsyncRunner runner) throws MfcException, InterruptedException, DatabaseException {
        MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
        if (forceUpdate) {
            initializedMfcExpert.felicaStart();
            runner.checkInterrupted();
            List<MyCardInfo> cardInfoList = initializedMfcExpert.fetchCacheCardList(true).getCardInfoList();
            cards.clear();
            for (MyCardInfo myCardInfo : cardInfoList) {
                MyServiceInfo myServiceInfo = (MyServiceInfo) IdPicker.pickupWithSid(services, myCardInfo.getServiceId());
                if (myServiceInfo != null) {
                    if (TextUtils.equals(FeliCaParams.SERVICE_ID_SUICA, myCardInfo.getServiceId()) && MyCardInfo.SupportMfiServiceType.UNSUPPORTED_MFI_SERVICE_1 == myCardInfo.getUnsupportedMfiService1()) {
                        cards.add(applyAdditionalInfoForSuica(myServiceInfo, myCardInfo));
                    } else {
                        cards.add(myCardInfo);
                    }
                }
            }
            runner.checkInterrupted();
            ArrayList<MyCardAdditionalInfo> arrayList = new ArrayList();
            for (MfiCardAdditionalInfo mfiCardAdditionalInfo : this._db.getMfiCardAdditionalInfo()) {
                arrayList.add(CardFuncUtility.convertCardAdditionalDbToInfo(this._context, mfiCardAdditionalInfo, mfiCardAdditionalInfo.transitInfo));
            }
            for (MyCardInfo myCardInfo2 : cards) {
                for (MyCardAdditionalInfo myCardAdditionalInfo : arrayList) {
                    if (myCardInfo2.getCardId().equals(myCardAdditionalInfo.getCid())) {
                        myCardInfo2.setCardAdditionalInfo(myCardAdditionalInfo);
                    }
                }
            }
        }
        updateBalance(services, cards, runner);
    }

    @Override // com.felicanetworks.mfm.main.model.CardDetailFunc
    public void getNotFoundImage(String unionId, Map<Integer, String> urls, final CardDetailFunc.NotFoundImageListener listener) {
        final FuncUtil.AsyncRunner runner = getRunner("getNotFoundImage_" + unionId);
        if (!runner.startup((Thread) new OrderImageWorker(this._context, CardFuncUtility.putCardImageRequestsArrayList(unionId, urls), new OrderImageWorker.Listener() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.3
            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderImageWorker.Listener
            public void onGetImage(final String id, final String url, final Bitmap image) {
                FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.3.1
                    @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                    public void go() {
                        listener.onGetImage(id, url, image);
                    }
                });
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderImageWorker.Listener
            public void onCompleted(final String id) {
                listener.onCompleted();
                CardDetailFuncEntity.this.removeRunner("getNotFoundImage_" + id);
            }
        }))) {
            throw new IllegalStateException("CardDetailFuncEntity#getNotFoundImage() is still executing.");
        }
    }

    @Override // com.felicanetworks.mfm.main.model.CardDetailFunc
    public void enableCard(final String cid, final String sid, final CardDetailFunc.CardOperationListener enableCardListener) {
        final FuncUtil.AsyncRunner runner = getRunner(RUN_NAME_ENABLE_CARD);
        if (!runner.startup(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.4
            @Override // java.lang.Runnable
            public void run() {
                FuncUtil.AsyncRunner asyncRunner;
                FuncUtil.Notify notify;
                MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
                try {
                    if (initializedMfcExpert != null) {
                        try {
                            MyCardInfo detaileCard = CardDetailFuncEntity.this.getDetaileCard(cid);
                            if (detaileCard == null) {
                                runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.EXT_CARD_OPERATION_FATAL_ERROR, new MfmException(CardDetailFuncEntity.class, InputDeviceCompat.SOURCE_DPAD)));
                                return;
                            }
                            runner.checkInterrupted();
                            CardDetailFuncEntity.this._cardOperationLatch = new CountDownLatch(1);
                            if (detaileCard.isCached()) {
                                initializedMfcExpert.cancelBackgroundUpdate();
                                initializedMfcExpert.felicaStart();
                                runner.checkInterrupted();
                                initializedMfcExpert.mfiCachedCardEnable(cid);
                            } else {
                                initializedMfcExpert.mfiStart(CardDetailFuncEntity.this.new MfiStartListenerWithCardOpe(runner));
                                CardDetailFuncEntity cardDetailFuncEntity = CardDetailFuncEntity.this;
                                cardDetailFuncEntity.await60(cardDetailFuncEntity._cardOperationLatch);
                                runner.checkInterrupted();
                                initializedMfcExpert.mfiCardEnable(cid);
                            }
                            return;
                        } catch (MfcException e) {
                            CardDetailFuncEntity.this.setErrorToRunner(runner, e);
                            asyncRunner = runner;
                            notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.4.1
                                @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                public void go() {
                                    if (runner.hasFailure() && ((ModelErrorInfo) runner.getFailure()).getType() != ModelErrorInfo.Type.MFIC_NO_WARNING) {
                                        enableCardListener.onError((ModelErrorInfo) runner.getFailure());
                                    } else {
                                        enableCardListener.onCompleted();
                                    }
                                }
                            };
                            FuncUtil.notifySafety(asyncRunner, notify);
                            return;
                        } catch (InterruptedException unused) {
                            asyncRunner = runner;
                            notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.4.1
                                @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                public void go() {
                                    if (runner.hasFailure() && ((ModelErrorInfo) runner.getFailure()).getType() != ModelErrorInfo.Type.MFIC_NO_WARNING) {
                                        enableCardListener.onError((ModelErrorInfo) runner.getFailure());
                                    } else {
                                        enableCardListener.onCompleted();
                                    }
                                }
                            };
                            FuncUtil.notifySafety(asyncRunner, notify);
                            return;
                        }
                    }
                    runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.EXT_CARD_OPERATION_FATAL_ERROR, new MfmException(CardDetailFuncEntity.class, 512)));
                } finally {
                    FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.4.1
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (runner.hasFailure() && ((ModelErrorInfo) runner.getFailure()).getType() != ModelErrorInfo.Type.MFIC_NO_WARNING) {
                                enableCardListener.onError((ModelErrorInfo) runner.getFailure());
                            } else {
                                enableCardListener.onCompleted();
                            }
                        }
                    });
                }
            }
        })) {
            throw new IllegalStateException("CardDetailFunc#enableCard() is still executing.");
        }
    }

    @Override // com.felicanetworks.mfm.main.model.CardDetailFunc
    public boolean needWarningDelete(String targetCid) {
        MyCardInfo next;
        Iterator<MyCardInfo> it = this.detailCards.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (next.getCardId().equals(targetCid)) {
                break;
            }
        }
        return next != null && MyCardInfo.CardStatus.STATUS_ACTIVE == next.getCardStatus() && MyCardInfo.CardPosition.POSITION_FOREGROUND == next.getCardPosition();
    }

    @Override // com.felicanetworks.mfm.main.model.CardDetailFunc
    public void deleteCard(final String cid, final CardDetailFunc.CardOperationListener deleteCardListener) {
        final FuncUtil.AsyncRunner runner = getRunner(RUN_NAME_DELETE_CARD);
        if (!runner.startup(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.5
            /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [920=5] */
            @Override // java.lang.Runnable
            public void run() {
                FuncUtil.AsyncRunner asyncRunner;
                FuncUtil.Notify notify;
                MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
                try {
                    if (initializedMfcExpert == null) {
                        runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.EXT_CARD_OPERATION_FATAL_ERROR, new MfmException(CardDetailFuncEntity.class, 528)));
                        return;
                    }
                    try {
                        MyCardInfo detaileCard = CardDetailFuncEntity.this.getDetaileCard(cid);
                        if (detaileCard == null) {
                            runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.EXT_CARD_OPERATION_FATAL_ERROR, new MfmException(CardDetailFuncEntity.class, 529)));
                            return;
                        }
                        runner.checkInterrupted();
                        if (detaileCard.isCached()) {
                            runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(CardDetailFuncEntity.class, 530)));
                            return;
                        }
                        CardDetailFuncEntity.this._cardOperationLatch = new CountDownLatch(1);
                        initializedMfcExpert.mfiStart(CardDetailFuncEntity.this.new MfiStartListenerWithCardOpe(runner));
                        CardDetailFuncEntity cardDetailFuncEntity = CardDetailFuncEntity.this;
                        cardDetailFuncEntity.await60(cardDetailFuncEntity._cardOperationLatch);
                        runner.checkInterrupted();
                        if (runner.hasFailure()) {
                            return;
                        }
                        initializedMfcExpert.mfiCardDelete(cid);
                    } catch (MfcException e) {
                        CardDetailFuncEntity.this.setErrorToRunner(runner, e);
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.5.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (runner.hasFailure() && ((ModelErrorInfo) runner.getFailure()).getType() != ModelErrorInfo.Type.MFIC_NO_WARNING) {
                                    deleteCardListener.onError((ModelErrorInfo) runner.getFailure());
                                } else {
                                    deleteCardListener.onCompleted();
                                }
                            }
                        };
                        FuncUtil.notifySafety(asyncRunner, notify);
                    } catch (InterruptedException unused) {
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.5.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (runner.hasFailure() && ((ModelErrorInfo) runner.getFailure()).getType() != ModelErrorInfo.Type.MFIC_NO_WARNING) {
                                    deleteCardListener.onError((ModelErrorInfo) runner.getFailure());
                                } else {
                                    deleteCardListener.onCompleted();
                                }
                            }
                        };
                        FuncUtil.notifySafety(asyncRunner, notify);
                    }
                } finally {
                    FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.5.1
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (runner.hasFailure() && ((ModelErrorInfo) runner.getFailure()).getType() != ModelErrorInfo.Type.MFIC_NO_WARNING) {
                                deleteCardListener.onError((ModelErrorInfo) runner.getFailure());
                            } else {
                                deleteCardListener.onCompleted();
                            }
                        }
                    });
                }
            }
        })) {
            throw new IllegalStateException("CardDetailFunc#deleteCard() is still executing.");
        }
    }

    @Override // com.felicanetworks.mfm.main.model.CardDetailFunc
    public void recoveryCard(final String cid, final String sid, final CardDetailFunc.CardOperationListener listener) {
        enableCard(cid, sid, listener);
    }

    @Override // com.felicanetworks.mfm.main.model.CardDetailFunc
    public void cancel() {
        Iterator<FuncUtil.AsyncRunner> it = this._runners.values().iterator();
        while (it.hasNext()) {
            it.next().shutdown();
        }
        this._context.cancellation();
    }

    @Override // com.felicanetworks.mfm.main.model.CardDetailFunc
    public void mfcFinish() {
        MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
        if (initializedMfcExpert == null || this._context.isUpdateCacheRunning()) {
            return;
        }
        initializedMfcExpert.finishFeliCaAccess();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBalance(List<MyServiceInfo> myServiceInfoList, List<MyCardInfo> myCardInfoList, FuncUtil.AsyncRunner runner) throws MfcException, InterruptedException {
        List<MfcExpert.Asset> cacheAssetList;
        MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
        if (initializedMfcExpert == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<MyServiceInfo> it = myServiceInfoList.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getService());
        }
        Map<Service, List<MyCardInfo>> mapCreateAssetTargetMap = initializedMfcExpert.createAssetTargetMap(arrayList, myCardInfoList, true);
        if (mapCreateAssetTargetMap.isEmpty()) {
            return;
        }
        runner.checkInterrupted();
        if (this._context.isUpdateCacheRunning()) {
            try {
                cacheAssetList = this._db.getCacheAssetList();
            } catch (DatabaseException unused) {
                cacheAssetList = null;
            }
        } else {
            runner.checkInterrupted();
            checkUserOperation();
            initializedMfcExpert.mfcStart();
            runner.checkInterrupted();
            checkUserOperation();
            cacheAssetList = initializedMfcExpert.getAssetList(mapCreateAssetTargetMap, false);
        }
        if (cacheAssetList == null) {
            return;
        }
        runner.checkInterrupted();
        for (MyServiceInfo myServiceInfo : myServiceInfoList) {
            if (mapCreateAssetTargetMap.containsKey(myServiceInfo.getService())) {
                for (MfcExpert.Asset asset : cacheAssetList) {
                    if (TextUtils.equals(myServiceInfo.getId(), asset.serviceId)) {
                        Iterator<MyCardInfo> it2 = myCardInfoList.iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                MyCardInfo next = it2.next();
                                if (TextUtils.equals(next.getServiceId(), asset.serviceId) && asset.cardId.isEmpty()) {
                                    updateBalanceDetail(asset, next);
                                    break;
                                } else if (TextUtils.equals(next.getCardId(), asset.cardId)) {
                                    updateBalanceDetail(asset, next);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private MyCardInfo createMyCardInfoFromMyServiceInfo(MyServiceInfo myServiceInfo) {
        ArrayList arrayList = new ArrayList();
        for (DetailLinkageApp detailLinkageApp : myServiceInfo.getService().getDetailLinkageAppList()) {
            arrayList.add(new LinkageInfo(LinkageInfo.LinkageKind.APP, detailLinkageApp.getLinkageAppName(), new LinkageApplicationInfo(BitmapFactory.decodeByteArray(detailLinkageApp.getLinkageAppIcon(), 0, detailLinkageApp.getLinkageAppIcon().length), detailLinkageApp.getLinkageAppPkg(), detailLinkageApp.getLinkageAppHash(), String.valueOf(detailLinkageApp.getLinkageAppGetType()), detailLinkageApp.getLinkageAppUrl()), detailLinkageApp.getLinkageAppUrl()));
        }
        for (DetailLinkageWeb detailLinkageWeb : myServiceInfo.getService().getDetailLinkageWebList()) {
            arrayList.add(new LinkageInfo(LinkageInfo.LinkageKind.WEB, detailLinkageWeb.getLinkageWebName(), null, detailLinkageWeb.getLinkageWebUrl()));
        }
        return new MyCardInfo(myServiceInfo.getId(), "", "", null, null, null, new MyCardAdditionalInfo("", this._context, new AdditionalInfo("", arrayList, new CommonInfo(new GeneralInfo(myServiceInfo.getService().getServiceName(), null, myServiceInfo.getService().getProvider(), null), new ContactInfo(myServiceInfo.getService().getContactName(), myServiceInfo.getService().getContactPhone(), myServiceInfo.getService().getContactUrl(), myServiceInfo.getService().getContactEmail())), null), (String) null), this._context.getNetworkExpert(), false);
    }

    public MyCardInfo applyAdditionalInfoForSuica(MyServiceInfo service, MyCardInfo card) {
        card.setCardAdditionalInfo(createMyCardInfoFromMyServiceInfo(service).getCardAdditionalInfo());
        return card;
    }

    private void updateBalanceDetail(MfcExpert.Asset asset, MyCardInfo myCardInfo) {
        MyServiceInfo myServiceInfoFindServiceBySid;
        List<MyServiceInfo> list = this.detailServices;
        if (list == null || (myServiceInfoFindServiceBySid = findServiceBySid(list, myCardInfo)) == null) {
            return;
        }
        if (myServiceInfoFindServiceBySid.getId().equals(FeliCaParams.SERVICE_ID_DCARD)) {
            ((MyDcardInfo) myServiceInfoFindServiceBySid).setDcardType(Boolean.valueOf(asset.isDcardMini));
        }
        myCardInfo.setAssetType(myServiceInfoFindServiceBySid.getAssetType());
        if (myServiceInfoFindServiceBySid.hasPrepaidEmoney()) {
            myCardInfo.getPrepaidEmoney().setBalance(asset.balanceValue);
        }
        if (myServiceInfoFindServiceBySid.hasPostpayEmoney()) {
            myCardInfo.getPostpaidEmoney().setCreditLimit(asset.balanceLimit);
            myCardInfo.getPostpaidEmoney().setAvailableCredit(asset.balanceValue);
        }
        if (myServiceInfoFindServiceBySid.hasPoint()) {
            myCardInfo.setPointData(Arrays.asList(new ServiceInfo.Point.PointData(asset.point1, asset.date1), new ServiceInfo.Point.PointData(asset.point2, asset.date2)));
        }
        myCardInfo.setHistory(asset.historyDataList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDcardBalanceDetail(List<MyServiceInfo> services, List<MyCardInfo> cards) {
        MyDcardInfo myDcardInfo;
        if (services.isEmpty() || cards.isEmpty()) {
            return;
        }
        Iterator<MyServiceInfo> it = services.iterator();
        while (true) {
            if (!it.hasNext()) {
                myDcardInfo = null;
                break;
            }
            MyServiceInfo next = it.next();
            if (next instanceof MyDcardInfo) {
                myDcardInfo = (MyDcardInfo) next;
                break;
            }
        }
        if (myDcardInfo == null) {
            return;
        }
        for (MyCardInfo myCardInfo : cards) {
            if (myCardInfo.isActiveForeground()) {
                updateBalanceDetail(new MfcExpert.Asset(myDcardInfo.getId(), myCardInfo.getCardId(), myDcardInfo.getAvailableCredit(), myDcardInfo.getCreditLimit(), -1, -1, null, null, myDcardInfo.getDcardType() == MyDcardInfo.DcardType.DCARD_MINI), myCardInfo);
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void await60(CountDownLatch countDownLatch) throws MfcException {
        try {
            if (countDownLatch.await(60L, TimeUnit.SECONDS)) {
                return;
            }
            MfcException mfcException = new MfcException(CardDetailFuncEntity.class, 2055, MfcException.CognitiveType.TIMEOUT);
            LogUtil.warning(mfcException);
            throw mfcException;
        } catch (InterruptedException unused) {
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity$6, reason: invalid class name */
    static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$policy$service$ServiceGroupType;

        static {
            int[] iArr = new int[MfcException.Type.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type = iArr;
            try {
                iArr[MfcException.Type.FELICA_OPEN_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.LOCKED_FELICA.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFI_SERVER_MAINTENANCE_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFI_INSUFFICIENT_CHIP_SPACE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.TYPE_EXIST_UNKNOWN_CARD.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.FELICA_NETWORK_FAILED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.OTHER_ERROR.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.FELICA_HTTP_ERROR.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFI_OPSRV_REQUIRED_LIB_UNAVAILABLE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFIC_VERSION_ERROR.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFIC_JSON_ERROR.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFI_OPSRV_ACCOUNT_ERROR.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFC_USED_BY_OTHER_APP.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFIC_RUNNING_BY_ITSELF.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.INSIDE_TRANSIT_GATE_ERROR.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            int[] iArr2 = new int[ServiceGroupType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$policy$service$ServiceGroupType = iArr2;
            try {
                iArr2[ServiceGroupType.TRANSPORTATION.ordinal()] = 1;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$ServiceGroupType[ServiceGroupType.ID.ordinal()] = 2;
            } catch (NoSuchFieldError unused17) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MfiCardFunc.CompiledState convertErrorToCompiledState(MfcException e) {
        boolean zIsEmpty;
        switch (AnonymousClass6.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[e.getErrorType().ordinal()]) {
            case 1:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_OPEN_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_OPEN_ERROR, e));
            case 2:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_LOCK_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_LOCK_ERROR, e));
            case 3:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_SERVER_MAINTENANCE_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_SERVER_MAINTENANCE_ERROR, e));
            case 4:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_INSUFFICIENT_CHIP_SPACE, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_INSUFFICIENT_CHIP_SPACE, e));
            case 5:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_TYPE_EXIST_UNKNOWN_CARD, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_TYPE_EXIST_UNKNOWN_CARD, e));
            case 6:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_NETWORK_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_NETWORK_ERROR, e));
            case 7:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, new ModelErrorInfo(ModelErrorInfo.Type.EXT_CARD_OPERATION_FATAL_ERROR, e));
            case 8:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_FELICA_HTTP_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.FELICA_HTTP_ERROR, e));
            case 9:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_LIB_UNAVAILABLE, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_LIB_UNAVAILABLE, e));
            case 10:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_VERSION_ERROR, e));
            case 11:
                try {
                    zIsEmpty = this._context.getOpenedDatabaseExpert().getMfiCardAdditionalInfo().isEmpty();
                    break;
                } catch (Exception unused) {
                    zIsEmpty = false;
                }
                if (zIsEmpty) {
                    return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_JSON_ERROR_NO_CASHE, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_JSON_ERROR_NO_CASHE, e));
                }
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_JSON_ERROR_USE_CACHE, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_JSON_ERROR_USE_CACHE, e));
            case 12:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_LOGIN_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_LOGIN_ERROR, e));
            case 13:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_USED_BY_OTHER_APP, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_USED_BY_OTHER_APP, e));
            case 14:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_RUNNING_BY_ITSELF, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_RUNNING_BY_ITSELF, e));
            default:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_OTHER_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_OTHER_ERROR, e));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setErrorToRunner(FuncUtil.AsyncRunner runner, MfcException mfcException) {
        int i = AnonymousClass6.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[mfcException.getErrorType().ordinal()];
        if (i == 3) {
            runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_SERVER_MAINTENANCE_ERROR, mfcException));
            return;
        }
        if (i == 4) {
            runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_INSUFFICIENT_CHIP_SPACE, mfcException));
            return;
        }
        if (i == 5) {
            runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_TYPE_EXIST_UNKNOWN_CARD, mfcException));
            return;
        }
        if (i == 6) {
            runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_NETWORK_ERROR, mfcException));
            return;
        }
        if (i == 7) {
            runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.EXT_CARD_OPERATION_FATAL_ERROR, mfcException));
        } else if (i == 15) {
            runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_INSIDE_TRANSIT_GATE_ERROR, mfcException));
        } else {
            runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_OTHER_ERROR, mfcException));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkEnableCard(List<MyServiceInfo> services, List<MyCardInfo> cards) {
        for (MyServiceInfo myServiceInfo : services) {
            Iterator<MyCardInfo> it = cards.iterator();
            while (it.hasNext()) {
                if (TextUtils.equals(myServiceInfo.getId(), it.next().getServiceId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private MyServiceInfo findServiceBySid(List<MyServiceInfo> services, MyCardInfo card) {
        for (MyServiceInfo myServiceInfo : services) {
            if (TextUtils.equals(myServiceInfo.getId(), card.getServiceId())) {
                return myServiceInfo;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MyCardInfo getDetaileCard(String cid) {
        MyCardInfo myCardInfo = null;
        for (MyCardInfo myCardInfo2 : this.detailCards) {
            if (cid.equals(myCardInfo2.getCardId())) {
                myCardInfo = myCardInfo2;
            }
        }
        return myCardInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasCacheCard() {
        Iterator<MyCardInfo> it = this.detailCards.iterator();
        while (it.hasNext()) {
            if (it.next().isCached()) {
                return true;
            }
        }
        return false;
    }

    private class MfiStartListenerWithCardOpe implements MfcExpert.MfiStartListener {
        final FuncUtil.AsyncRunner runner;

        @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
        public void onSuccessNoLogin() {
        }

        MfiStartListenerWithCardOpe(FuncUtil.AsyncRunner runner) {
            this.runner = runner;
        }

        @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
        public void onSuccess(boolean executedSilentStart) {
            CardDetailFuncEntity.this._cardOperationLatch.countDown();
        }

        @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
        public void onRequestActivity(Intent intent) {
            CardDetailFuncEntity.this.setErrorToRunner(this.runner, new MfcException(CardDetailFuncEntity.class, 256, MfcException.CognitiveType.NOT_LOGIN));
            CardDetailFuncEntity.this._cardOperationLatch.countDown();
        }

        @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
        public void errorResult(MfcException exception) {
            CardDetailFuncEntity.this.setErrorToRunner(this.runner, exception);
            CardDetailFuncEntity.this._cardOperationLatch.countDown();
        }
    }
}
