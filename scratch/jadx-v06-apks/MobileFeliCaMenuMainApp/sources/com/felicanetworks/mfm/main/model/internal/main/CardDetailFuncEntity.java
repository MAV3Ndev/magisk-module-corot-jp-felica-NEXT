package com.felicanetworks.mfm.main.model.internal.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
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

/* JADX INFO: loaded from: classes.dex */
public class CardDetailFuncEntity implements CardDetailFunc {
    private final ModelContext _context;
    private final DatabaseExpert _db;
    private final Map<String, FuncUtil.AsyncRunner> _runners = new HashMap();
    private List<MyServiceInfo> detailServices = new ArrayList();
    private List<MyCardInfo> detailCards = new ArrayList();
    private CountDownLatch _cardOperationLatch = new CountDownLatch(1);

    private FuncUtil.AsyncRunner getRunner(String str) {
        FuncUtil.AsyncRunner asyncRunnerRemove;
        if (this._runners.containsKey(str) && (asyncRunnerRemove = this._runners.remove(str)) != null) {
            asyncRunnerRemove.shutdown();
        }
        FuncUtil.AsyncRunner asyncRunner = new FuncUtil.AsyncRunner();
        this._runners.put(str, asyncRunner);
        return asyncRunner;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeRunner(String str) {
        FuncUtil.AsyncRunner asyncRunnerRemove;
        if (!this._runners.containsKey(str) || (asyncRunnerRemove = this._runners.remove(str)) == null) {
            return;
        }
        asyncRunnerRemove.shutdown();
    }

    CardDetailFuncEntity(ModelContext modelContext) {
        this._context = modelContext;
        this._db = modelContext.getOpenedDatabaseExpert();
    }

    @Override // com.felicanetworks.mfm.main.model.CardDetailFunc
    public void setup(List<MyServiceInfo> list, List<MyCardInfo> list2) {
        this.detailServices.clear();
        this.detailCards.clear();
        for (MyServiceInfo myServiceInfo : list) {
            if (myServiceInfo.getRegisterState() != MyServiceInfo.RegisterState.NO_REGISTER) {
                ArrayList arrayList = new ArrayList();
                for (MyCardInfo myCardInfo : list2) {
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
    public void createDetailCardInfo(final boolean z, final boolean z2, final CardDetailFunc.CreateDetailCardInfoListener createDetailCardInfoListener) {
        final FuncUtil.AsyncRunner runner = getRunner("createDetailCardInfo");
        runner.startupOrThrow(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.1
            @Override // java.lang.Runnable
            public void run() {
                FuncUtil.AsyncRunner asyncRunner;
                FuncUtil.Notify notify;
                try {
                    try {
                    } catch (DatabaseException e) {
                        runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.DB_ACCESS_ERROR, e));
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.1.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (!runner.hasFailure()) {
                                    createDetailCardInfoListener.onCompleted(CardDetailFuncEntity.this.detailServices, CardDetailFuncEntity.this.detailCards);
                                } else {
                                    createDetailCardInfoListener.onError((ModelErrorInfo) runner.getFailure());
                                }
                            }
                        };
                    } catch (MfcException e2) {
                        runner.putFailure(CardDetailFuncEntity.this.convertErrorToCompiledState(e2).getMficWarningState());
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.1.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (!runner.hasFailure()) {
                                    createDetailCardInfoListener.onCompleted(CardDetailFuncEntity.this.detailServices, CardDetailFuncEntity.this.detailCards);
                                } else {
                                    createDetailCardInfoListener.onError((ModelErrorInfo) runner.getFailure());
                                }
                            }
                        };
                    } catch (InterruptedException unused) {
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.1.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (!runner.hasFailure()) {
                                    createDetailCardInfoListener.onCompleted(CardDetailFuncEntity.this.detailServices, CardDetailFuncEntity.this.detailCards);
                                } else {
                                    createDetailCardInfoListener.onError((ModelErrorInfo) runner.getFailure());
                                }
                            }
                        };
                    }
                    if (CardDetailFuncEntity.this.detailServices.isEmpty()) {
                        throw new MfcException(CardDetailFuncEntity.class, 1043, MfcException.CognitiveType.FATAL_ERROR);
                    }
                    ArrayList arrayList = new ArrayList();
                    Iterator it = CardDetailFuncEntity.this.detailServices.iterator();
                    while (it.hasNext()) {
                        arrayList.add(((MyServiceInfo) it.next()).getId());
                    }
                    runner.checkInterrupted();
                    int i = AnonymousClass6.$SwitchMap$com$felicanetworks$mfm$main$policy$service$ServiceGroupType[ServiceGroupType.resolve(arrayList).ordinal()];
                    if (i != 1) {
                        if (i != 2) {
                            CardDetailFuncEntity.this.updateBalance(CardDetailFuncEntity.this.detailServices, CardDetailFuncEntity.this.detailCards, runner);
                        } else if (z2) {
                            CardDetailFuncEntity.this.updateMfiInformation(z2, CardDetailFuncEntity.this.detailServices, CardDetailFuncEntity.this.detailCards, runner);
                            if (!CardDetailFuncEntity.this.checkEnableCard(CardDetailFuncEntity.this.detailServices, CardDetailFuncEntity.this.detailCards)) {
                                runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_OTHER_ERROR, new MfmException(CardDetailFuncEntity.class, 259)));
                            }
                        } else {
                            CardDetailFuncEntity.this.updateDcardBalanceDetail(CardDetailFuncEntity.this.detailServices, CardDetailFuncEntity.this.detailCards);
                        }
                    } else if (z) {
                        CardDetailFuncEntity.this.updateMfiInformation(z2, CardDetailFuncEntity.this.detailServices, CardDetailFuncEntity.this.detailCards, runner);
                        if (!CardDetailFuncEntity.this.checkEnableCard(CardDetailFuncEntity.this.detailServices, CardDetailFuncEntity.this.detailCards)) {
                            runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_OTHER_ERROR, new MfmException(CardDetailFuncEntity.class, 258)));
                        }
                    } else {
                        CardDetailFuncEntity.this.updateBalance(CardDetailFuncEntity.this.detailServices, CardDetailFuncEntity.this.detailCards, runner);
                    }
                    asyncRunner = runner;
                    notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.1.1
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (!runner.hasFailure()) {
                                createDetailCardInfoListener.onCompleted(CardDetailFuncEntity.this.detailServices, CardDetailFuncEntity.this.detailCards);
                            } else {
                                createDetailCardInfoListener.onError((ModelErrorInfo) runner.getFailure());
                            }
                        }
                    };
                    FuncUtil.notifySafety(asyncRunner, notify);
                } catch (Throwable th) {
                    FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.1.1
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (!runner.hasFailure()) {
                                createDetailCardInfoListener.onCompleted(CardDetailFuncEntity.this.detailServices, CardDetailFuncEntity.this.detailCards);
                            } else {
                                createDetailCardInfoListener.onError((ModelErrorInfo) runner.getFailure());
                            }
                        }
                    });
                    throw th;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMfiInformation(boolean z, List<MyServiceInfo> list, List<MyCardInfo> list2, FuncUtil.AsyncRunner asyncRunner) throws MfcException, InterruptedException, DatabaseException {
        MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        if (z) {
            final FuncUtil.AsyncPacket asyncPacket = new FuncUtil.AsyncPacket();
            asyncRunner.checkInterrupted();
            initializedMfcExpert.mfiStart(new MfcExpert.MfiStartListener() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.2
                @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                public void onSuccessNoLogin() {
                }

                @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                public void onSuccess(boolean z2) {
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                public void onRequestActivity(Intent intent) {
                    asyncPacket.set(new MfcException(CardDetailFuncEntity.class, 769, MfcException.CognitiveType.NOT_LOGIN));
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                public void errorResult(MfcException mfcException) {
                    asyncPacket.set(mfcException);
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await();
            if (asyncPacket.exist()) {
                throw ((MfcException) asyncPacket.get());
            }
            asyncRunner.checkInterrupted();
            List<MyCardInfo> cardInfoList = initializedMfcExpert.fetchCardList().getCardInfoList();
            list2.clear();
            for (MyCardInfo myCardInfo : cardInfoList) {
                MyServiceInfo myServiceInfo = (MyServiceInfo) IdPicker.pickupWithSid(list, myCardInfo.getServiceId());
                if (myServiceInfo != null) {
                    if (TextUtils.equals(FeliCaParams.SERVICE_ID_SUICA, myCardInfo.getServiceId()) && MyCardInfo.SupportMfiServiceType.UNSUPPORTED_MFI_SERVICE_1 == myCardInfo.getUnsupportedMfiService1()) {
                        list2.add(applyAdditionalInfoForSuica(myServiceInfo, myCardInfo));
                    } else {
                        list2.add(myCardInfo);
                    }
                }
            }
            asyncRunner.checkInterrupted();
            updateCardAdditionalInfoIfNeeded(list2, asyncRunner);
        }
        updateBalance(list, list2, asyncRunner);
    }

    private void updateCardAdditionalInfoIfNeeded(List<MyCardInfo> list, FuncUtil.AsyncRunner asyncRunner) throws MfcException, InterruptedException, DatabaseException {
        MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (MyCardInfo myCardInfo : list) {
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
        asyncRunner.checkInterrupted();
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
        asyncRunner.checkInterrupted();
        ArrayList arrayList3 = new ArrayList();
        for (MfiCardAdditionalInfo mfiCardAdditionalInfo : this._db.getMfiCardAdditionalInfo()) {
            arrayList3.add(CardFuncUtility.convertCardAdditionalDbToInfo(this._context, mfiCardAdditionalInfo, mfiCardAdditionalInfo.transitInfo));
        }
        for (int i = 0; i < list.size(); i++) {
            MyCardInfo myCardInfo2 = list.get(i);
            for (int i2 = 0; i2 < arrayList3.size(); i2++) {
                if (myCardInfo2.getCardId().equals(((MyCardAdditionalInfo) arrayList3.get(i2)).getCid())) {
                    myCardInfo2.setCardAdditionalInfo((MyCardAdditionalInfo) arrayList3.get(i2));
                }
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.model.CardDetailFunc
    public void getNotFoundImage(String str, String str2, final CardDetailFunc.NotFoundImageListener notFoundImageListener) {
        if (str == null || str2 == null || "".equals(str2)) {
            notFoundImageListener.onGetImage(str, str2, null);
            return;
        }
        final FuncUtil.AsyncRunner runner = getRunner("getNotFoundImage_" + str);
        if (!runner.startup((Thread) new OrderImageWorker(this._context, new OrderImageWorker.Request(str, str2), new OrderImageWorker.Listener() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.3
            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderImageWorker.Listener
            public void onGetImage(final String str3, final String str4, final Bitmap bitmap) {
                FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.3.1
                    @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                    public void go() {
                        notFoundImageListener.onGetImage(str3, str4, bitmap);
                    }
                });
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderImageWorker.Listener
            public void onCompleted(String str3) {
                notFoundImageListener.onCompleted();
                CardDetailFuncEntity.this.removeRunner("getNotFoundImage_" + str3);
            }
        }))) {
            throw new IllegalStateException("CardDetailFuncEntity#getNotFoundImage() is still executing.");
        }
    }

    @Override // com.felicanetworks.mfm.main.model.CardDetailFunc
    public void enableCard(final String str, String str2, final CardDetailFunc.CardOperationListener cardOperationListener) {
        final FuncUtil.AsyncRunner runner = getRunner("enableCard");
        if (!runner.startup(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.4
            @Override // java.lang.Runnable
            public void run() {
                FuncUtil.AsyncRunner asyncRunner;
                FuncUtil.Notify notify;
                ModelContext unused = CardDetailFuncEntity.this._context;
                MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
                try {
                    if (initializedMfcExpert == null) {
                        runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.EXT_CARD_OPERATION_FATAL_ERROR, new MfmException(CardDetailFuncEntity.class, 512)));
                        return;
                    }
                    try {
                        CardDetailFuncEntity.this._cardOperationLatch = new CountDownLatch(1);
                        initializedMfcExpert.mfiStart(CardDetailFuncEntity.this.new MfiStartListenerWithCardOpe(runner));
                        CardDetailFuncEntity.this.await60(CardDetailFuncEntity.this._cardOperationLatch);
                        runner.checkInterrupted();
                        if (runner.hasFailure()) {
                            return;
                        }
                        initializedMfcExpert.mfiCardEnable(str);
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.4.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (runner.hasFailure() && ((ModelErrorInfo) runner.getFailure()).getType() != ModelErrorInfo.Type.MFIC_NO_WARNING) {
                                    cardOperationListener.onError((ModelErrorInfo) runner.getFailure());
                                } else {
                                    cardOperationListener.onCompleted();
                                }
                            }
                        };
                    } catch (MfcException e) {
                        CardDetailFuncEntity.this.setErrorToRunner(runner, e);
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.4.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (runner.hasFailure() && ((ModelErrorInfo) runner.getFailure()).getType() != ModelErrorInfo.Type.MFIC_NO_WARNING) {
                                    cardOperationListener.onError((ModelErrorInfo) runner.getFailure());
                                } else {
                                    cardOperationListener.onCompleted();
                                }
                            }
                        };
                    } catch (InterruptedException unused2) {
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.4.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (runner.hasFailure() && ((ModelErrorInfo) runner.getFailure()).getType() != ModelErrorInfo.Type.MFIC_NO_WARNING) {
                                    cardOperationListener.onError((ModelErrorInfo) runner.getFailure());
                                } else {
                                    cardOperationListener.onCompleted();
                                }
                            }
                        };
                    }
                    FuncUtil.notifySafety(asyncRunner, notify);
                } finally {
                    FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.4.1
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (runner.hasFailure() && ((ModelErrorInfo) runner.getFailure()).getType() != ModelErrorInfo.Type.MFIC_NO_WARNING) {
                                cardOperationListener.onError((ModelErrorInfo) runner.getFailure());
                            } else {
                                cardOperationListener.onCompleted();
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
    public boolean needWarningDelete(String str) {
        MyCardInfo next;
        Iterator<MyCardInfo> it = this.detailCards.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (next.getCardId().equals(str)) {
                break;
            }
        }
        return next != null && MyCardInfo.CardStatus.STATUS_ACTIVE == next.getCardStatus() && MyCardInfo.CardPosition.POSITION_FOREGROUND == next.getCardPosition();
    }

    @Override // com.felicanetworks.mfm.main.model.CardDetailFunc
    public void deleteCard(final String str, final CardDetailFunc.CardOperationListener cardOperationListener) {
        final FuncUtil.AsyncRunner runner = getRunner("deleteCard");
        if (!runner.startup(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.5
            @Override // java.lang.Runnable
            public void run() {
                FuncUtil.AsyncRunner asyncRunner;
                FuncUtil.Notify notify;
                ModelContext unused = CardDetailFuncEntity.this._context;
                MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
                try {
                    if (initializedMfcExpert == null) {
                        runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.EXT_CARD_OPERATION_FATAL_ERROR, new MfmException(CardDetailFuncEntity.class, 528)));
                        return;
                    }
                    try {
                        runner.checkInterrupted();
                        CardDetailFuncEntity.this._cardOperationLatch = new CountDownLatch(1);
                        initializedMfcExpert.mfiStart(CardDetailFuncEntity.this.new MfiStartListenerWithCardOpe(runner));
                        CardDetailFuncEntity.this.await60(CardDetailFuncEntity.this._cardOperationLatch);
                        runner.checkInterrupted();
                        if (runner.hasFailure()) {
                            return;
                        }
                        initializedMfcExpert.mfiCardDelete(str);
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.5.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (runner.hasFailure() && ((ModelErrorInfo) runner.getFailure()).getType() != ModelErrorInfo.Type.MFIC_NO_WARNING) {
                                    cardOperationListener.onError((ModelErrorInfo) runner.getFailure());
                                } else {
                                    cardOperationListener.onCompleted();
                                }
                            }
                        };
                    } catch (MfcException e) {
                        CardDetailFuncEntity.this.setErrorToRunner(runner, e);
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.5.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (runner.hasFailure() && ((ModelErrorInfo) runner.getFailure()).getType() != ModelErrorInfo.Type.MFIC_NO_WARNING) {
                                    cardOperationListener.onError((ModelErrorInfo) runner.getFailure());
                                } else {
                                    cardOperationListener.onCompleted();
                                }
                            }
                        };
                    } catch (InterruptedException unused2) {
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.5.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (runner.hasFailure() && ((ModelErrorInfo) runner.getFailure()).getType() != ModelErrorInfo.Type.MFIC_NO_WARNING) {
                                    cardOperationListener.onError((ModelErrorInfo) runner.getFailure());
                                } else {
                                    cardOperationListener.onCompleted();
                                }
                            }
                        };
                    }
                    FuncUtil.notifySafety(asyncRunner, notify);
                } finally {
                    FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity.5.1
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (runner.hasFailure() && ((ModelErrorInfo) runner.getFailure()).getType() != ModelErrorInfo.Type.MFIC_NO_WARNING) {
                                cardOperationListener.onError((ModelErrorInfo) runner.getFailure());
                            } else {
                                cardOperationListener.onCompleted();
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
    public void recoveryCard(String str, String str2, CardDetailFunc.CardOperationListener cardOperationListener) {
        enableCard(str, str2, cardOperationListener);
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
        if (initializedMfcExpert != null) {
            initializedMfcExpert.finishFeliCaAccess();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBalance(List<MyServiceInfo> list, List<MyCardInfo> list2, FuncUtil.AsyncRunner asyncRunner) throws MfcException, InterruptedException {
        MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
        if (initializedMfcExpert == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<MyServiceInfo> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getService());
        }
        Map<Service, List<MyCardInfo>> mapCreateAssetTargetMap = initializedMfcExpert.createAssetTargetMap(arrayList, list2, true);
        if (mapCreateAssetTargetMap.isEmpty()) {
            return;
        }
        asyncRunner.checkInterrupted();
        initializedMfcExpert.mfcStart();
        asyncRunner.checkInterrupted();
        List<MfcExpert.Asset> assetList = initializedMfcExpert.getAssetList(mapCreateAssetTargetMap, false);
        if (assetList == null) {
            return;
        }
        asyncRunner.checkInterrupted();
        for (MyServiceInfo myServiceInfo : list) {
            if (mapCreateAssetTargetMap.containsKey(myServiceInfo.getService())) {
                for (MfcExpert.Asset asset : assetList) {
                    if (TextUtils.equals(myServiceInfo.getId(), asset.serviceId)) {
                        Iterator<MyCardInfo> it2 = list2.iterator();
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
        return new MyCardInfo(myServiceInfo.getId(), "", "", null, null, null, new MyCardAdditionalInfo("", this._context, new AdditionalInfo("", arrayList, new CommonInfo(new GeneralInfo(myServiceInfo.getService().getServiceName(), null, myServiceInfo.getService().getProvider()), new ContactInfo(myServiceInfo.getService().getContactName(), myServiceInfo.getService().getContactPhone(), myServiceInfo.getService().getContactUrl(), myServiceInfo.getService().getContactEmail())), null)), this._context.getNetworkExpert());
    }

    public MyCardInfo applyAdditionalInfoForSuica(MyServiceInfo myServiceInfo, MyCardInfo myCardInfo) {
        myCardInfo.setCardAdditionalInfo(createMyCardInfoFromMyServiceInfo(myServiceInfo).getCardAdditionalInfo());
        return myCardInfo;
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
    public void updateDcardBalanceDetail(List<MyServiceInfo> list, List<MyCardInfo> list2) {
        if (list.isEmpty() || list2.isEmpty()) {
            return;
        }
        MyDcardInfo myDcardInfo = null;
        Iterator<MyServiceInfo> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
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
        for (MyCardInfo myCardInfo : list2) {
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
    public MfiCardFunc.CompiledState convertErrorToCompiledState(MfcException mfcException) {
        switch (AnonymousClass6.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[mfcException.getErrorType().ordinal()]) {
            case 1:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_OPEN_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_OPEN_ERROR, mfcException));
            case 2:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_LOCK_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_LOCK_ERROR, mfcException));
            case 3:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_SERVER_MAINTENANCE_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_SERVER_MAINTENANCE_ERROR, mfcException));
            case 4:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_INSUFFICIENT_CHIP_SPACE, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_INSUFFICIENT_CHIP_SPACE, mfcException));
            case 5:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_TYPE_EXIST_UNKNOWN_CARD, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_TYPE_EXIST_UNKNOWN_CARD, mfcException));
            case 6:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_NETWORK_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_NETWORK_ERROR, mfcException));
            case 7:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, new ModelErrorInfo(ModelErrorInfo.Type.EXT_CARD_OPERATION_FATAL_ERROR, mfcException));
            case 8:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_FELICA_HTTP_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.FELICA_HTTP_ERROR, mfcException));
            case 9:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_LIB_UNAVAILABLE, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_LIB_UNAVAILABLE, mfcException));
            case 10:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_VERSION_ERROR, mfcException));
            case 11:
                boolean zIsEmpty = false;
                try {
                    zIsEmpty = this._context.getOpenedDatabaseExpert().getMfiCardAdditionalInfo().isEmpty();
                    break;
                } catch (Exception unused) {
                }
                if (zIsEmpty) {
                    return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_JSON_ERROR_NO_CASHE, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_JSON_ERROR_NO_CASHE, mfcException));
                }
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_JSON_ERROR_USE_CACHE, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_JSON_ERROR_USE_CACHE, mfcException));
            case 12:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_LOGIN_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_LOGIN_ERROR, mfcException));
            case 13:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_USED_BY_OTHER_APP, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_USED_BY_OTHER_APP, mfcException));
            case 14:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_RUNNING_BY_ITSELF, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_RUNNING_BY_ITSELF, mfcException));
            default:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_OTHER_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_OTHER_ERROR, mfcException));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setErrorToRunner(FuncUtil.AsyncRunner asyncRunner, MfcException mfcException) {
        int i = AnonymousClass6.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[mfcException.getErrorType().ordinal()];
        if (i == 3) {
            asyncRunner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_SERVER_MAINTENANCE_ERROR, mfcException));
            return;
        }
        if (i == 4) {
            asyncRunner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_INSUFFICIENT_CHIP_SPACE, mfcException));
            return;
        }
        if (i == 5) {
            asyncRunner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_TYPE_EXIST_UNKNOWN_CARD, mfcException));
            return;
        }
        if (i == 6) {
            asyncRunner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_NETWORK_ERROR, mfcException));
            return;
        }
        if (i == 7) {
            asyncRunner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.EXT_CARD_OPERATION_FATAL_ERROR, mfcException));
        } else if (i == 15) {
            asyncRunner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_INSIDE_TRANSIT_GATE_ERROR, mfcException));
        } else {
            asyncRunner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_OTHER_ERROR, mfcException));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkEnableCard(List<MyServiceInfo> list, List<MyCardInfo> list2) {
        for (MyServiceInfo myServiceInfo : list) {
            Iterator<MyCardInfo> it = list2.iterator();
            while (it.hasNext()) {
                if (TextUtils.equals(myServiceInfo.getId(), it.next().getServiceId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private MyServiceInfo findServiceBySid(List<MyServiceInfo> list, MyCardInfo myCardInfo) {
        for (MyServiceInfo myServiceInfo : list) {
            if (TextUtils.equals(myServiceInfo.getId(), myCardInfo.getServiceId())) {
                return myServiceInfo;
            }
        }
        return null;
    }

    private class MfiStartListenerWithCardOpe implements MfcExpert.MfiStartListener {
        final FuncUtil.AsyncRunner runner;

        @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
        public void onSuccessNoLogin() {
        }

        MfiStartListenerWithCardOpe(FuncUtil.AsyncRunner asyncRunner) {
            this.runner = asyncRunner;
        }

        @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
        public void onSuccess(boolean z) {
            CardDetailFuncEntity.this._cardOperationLatch.countDown();
        }

        @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
        public void onRequestActivity(Intent intent) {
            CardDetailFuncEntity.this.setErrorToRunner(this.runner, new MfcException(CardDetailFuncEntity.class, 256, MfcException.CognitiveType.NOT_LOGIN));
            CardDetailFuncEntity.this._cardOperationLatch.countDown();
        }

        @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
        public void errorResult(MfcException mfcException) {
            CardDetailFuncEntity.this.setErrorToRunner(this.runner, mfcException);
            CardDetailFuncEntity.this._cardOperationLatch.countDown();
        }
    }
}
