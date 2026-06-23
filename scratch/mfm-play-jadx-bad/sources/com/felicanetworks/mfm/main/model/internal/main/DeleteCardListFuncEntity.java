package com.felicanetworks.mfm.main.model.internal.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import com.felicanetworks.mfm.main.model.DeleteCardListFunc;
import com.felicanetworks.mfm.main.model.MfiCardFunc;
import com.felicanetworks.mfm.main.model.info.MfiCardAdditionalInfo;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.MyCardAdditionalInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.internal.main.FuncUtil;
import com.felicanetworks.mfm.main.model.internal.main.OrderImageWorker;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.service.ServiceGroupType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes3.dex */
public class DeleteCardListFuncEntity implements DeleteCardListFunc {
    private final ModelContext _context;
    private final DatabaseExpert _db;
    private final Map<String, FuncUtil.AsyncRunner> _runners = new HashMap();
    private final List<MyServiceInfo> detailServices = new ArrayList();

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

    DeleteCardListFuncEntity(ModelContext context) {
        this._context = context;
        this._db = context.getOpenedDatabaseExpert();
    }

    @Override // com.felicanetworks.mfm.main.model.DeleteCardListFunc
    public void setup(List<MyServiceInfo> myServices) {
        this.detailServices.clear();
        this.detailServices.addAll(myServices);
    }

    @Override // com.felicanetworks.mfm.main.model.DeleteCardListFunc
    public void createDeleteCardListInfo(final DeleteCardListFunc.CreateDeleteCardListInfoListener listener) {
        final ArrayList arrayList = new ArrayList();
        final FuncUtil.AsyncRunner runner = getRunner("createDeleteCardListInfo");
        runner.startupOrThrow(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.DeleteCardListFuncEntity.1
            /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [181=4] */
            @Override // java.lang.Runnable
            public void run() {
                FuncUtil.AsyncRunner asyncRunner;
                FuncUtil.Notify notify;
                try {
                    try {
                        try {
                            if (DeleteCardListFuncEntity.this.detailServices.isEmpty()) {
                                throw new MfcException(DeleteCardListFuncEntity.class, 1043, MfcException.CognitiveType.FATAL_ERROR);
                            }
                            ArrayList arrayList2 = new ArrayList();
                            Iterator it = DeleteCardListFuncEntity.this.detailServices.iterator();
                            while (it.hasNext()) {
                                arrayList2.add(((MyServiceInfo) it.next()).getId());
                            }
                            runner.checkInterrupted();
                            if (ServiceGroupType.resolve(arrayList2) == ServiceGroupType.TRANSPORTATION) {
                                DeleteCardListFuncEntity deleteCardListFuncEntity = DeleteCardListFuncEntity.this;
                                deleteCardListFuncEntity.updateMfiInformation(deleteCardListFuncEntity.detailServices, arrayList, runner);
                                if (!runner.hasFailure()) {
                                    DeleteCardListFuncEntity deleteCardListFuncEntity2 = DeleteCardListFuncEntity.this;
                                    if (!deleteCardListFuncEntity2.checkEnableCard(deleteCardListFuncEntity2.detailServices, arrayList)) {
                                        runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(DeleteCardListFuncEntity.class, 257)));
                                    }
                                }
                            } else {
                                runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(DeleteCardListFuncEntity.class, 258)));
                            }
                        } catch (MfcException e) {
                            runner.putFailure(DeleteCardListFuncEntity.this.convertErrorToCompiledState(e).getMficWarningState());
                            asyncRunner = runner;
                            notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.DeleteCardListFuncEntity.1.1
                                @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                public void go() {
                                    if (!runner.hasFailure()) {
                                        listener.onCompleted(DeleteCardListFuncEntity.this.detailServices, arrayList);
                                    } else {
                                        listener.onError((ModelErrorInfo) runner.getFailure());
                                    }
                                }
                            };
                            FuncUtil.notifySafety(asyncRunner, notify);
                        }
                    } catch (DatabaseException e2) {
                        runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.DB_ACCESS_ERROR, e2));
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.DeleteCardListFuncEntity.1.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (!runner.hasFailure()) {
                                    listener.onCompleted(DeleteCardListFuncEntity.this.detailServices, arrayList);
                                } else {
                                    listener.onError((ModelErrorInfo) runner.getFailure());
                                }
                            }
                        };
                        FuncUtil.notifySafety(asyncRunner, notify);
                    } catch (InterruptedException unused) {
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.DeleteCardListFuncEntity.1.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (!runner.hasFailure()) {
                                    listener.onCompleted(DeleteCardListFuncEntity.this.detailServices, arrayList);
                                } else {
                                    listener.onError((ModelErrorInfo) runner.getFailure());
                                }
                            }
                        };
                        FuncUtil.notifySafety(asyncRunner, notify);
                    }
                } finally {
                    FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.DeleteCardListFuncEntity.1.1
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (!runner.hasFailure()) {
                                listener.onCompleted(DeleteCardListFuncEntity.this.detailServices, arrayList);
                            } else {
                                listener.onError((ModelErrorInfo) runner.getFailure());
                            }
                        }
                    });
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMfiInformation(List<MyServiceInfo> services, List<MyCardInfo> cards, FuncUtil.AsyncRunner runner) throws MfcException, InterruptedException, DatabaseException {
        MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final FuncUtil.AsyncPacket asyncPacket = new FuncUtil.AsyncPacket();
        runner.checkInterrupted();
        initializedMfcExpert.mfiStart(new MfcExpert.MfiStartListener() { // from class: com.felicanetworks.mfm.main.model.internal.main.DeleteCardListFuncEntity.2
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
            public void onSuccess(boolean executedSilentStart) {
                countDownLatch.countDown();
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
            public void onRequestActivity(Intent intent) {
                asyncPacket.set(new MfcException(DeleteCardListFuncEntity.class, 769, MfcException.CognitiveType.NOT_LOGIN));
                countDownLatch.countDown();
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
            public void errorResult(MfcException e) {
                asyncPacket.set(e);
                countDownLatch.countDown();
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
            public void onSuccessNoLogin() {
                asyncPacket.set(new MfcException(DeleteCardListFuncEntity.class, 770, MfcException.CognitiveType.FATAL_ERROR));
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        if (asyncPacket.exist()) {
            throw ((MfcException) asyncPacket.get());
        }
        runner.checkInterrupted();
        getCardInfoWithSpStatus(services, cards, runner);
        updateCardAdditionalInfoIfNeeded(cards, runner);
    }

    private void updateCardAdditionalInfoIfNeeded(List<MyCardInfo> detailCards, FuncUtil.AsyncRunner runner) throws MfcException, InterruptedException, DatabaseException {
        List<MyCardAdditionalInfo> listFetchCardAdditionalInfoList;
        MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (MyCardInfo myCardInfo : detailCards) {
            String cardId = myCardInfo.getCardId();
            String mfiHashValue = initializedMfcExpert.getMfiHashValue(cardId);
            if (mfiHashValue == null) {
                mfiHashValue = myCardInfo.getMfiAdditionalInfoHashValue();
            }
            arrayList.add(cardId);
            arrayList2.add(new DatabaseExpert.MfiHashValueInfo(cardId, mfiHashValue));
        }
        runner.checkInterrupted();
        String[] strArr = (String[]) arrayList.toArray(new String[0]);
        if (strArr.length > 0 && (listFetchCardAdditionalInfoList = initializedMfcExpert.fetchCardAdditionalInfoList(strArr)) != null) {
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

    @Override // com.felicanetworks.mfm.main.model.DeleteCardListFunc
    public void getLackCardFaceImage(String unionId, Map<Integer, String> urls, final DeleteCardListFunc.LackCardFaceImageListener listener) {
        final FuncUtil.AsyncRunner runner = getRunner("getLackCardFaceImage_" + unionId);
        if (!runner.startup((Thread) new OrderImageWorker(this._context, CardFuncUtility.putCardImageRequestsArrayList(unionId, urls), new OrderImageWorker.Listener() { // from class: com.felicanetworks.mfm.main.model.internal.main.DeleteCardListFuncEntity.3
            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderImageWorker.Listener
            public void onGetImage(final String id, final String url, final Bitmap image) {
                FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.DeleteCardListFuncEntity.3.1
                    @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                    public void go() {
                        listener.onGetImage(id, url, image);
                    }
                });
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderImageWorker.Listener
            public void onCompleted(final String id) {
                DeleteCardListFuncEntity.this.removeRunner("getLackCardFaceImage_" + id);
            }
        }))) {
            throw new IllegalStateException("DeleteCardListFuncEntity#getLackCardFaceImage() is still executing.");
        }
    }

    @Override // com.felicanetworks.mfm.main.model.DeleteCardListFunc
    public void reissueCard(final String cid, final DeleteCardListFunc.ReissueCardListener listener) {
        final FuncUtil.AsyncRunner runner = getRunner("reissueCard");
        if (!runner.startup(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.DeleteCardListFuncEntity.4
            /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [532=4] */
            @Override // java.lang.Runnable
            public void run() {
                FuncUtil.AsyncRunner asyncRunner;
                FuncUtil.Notify notify;
                ModelContext unused = DeleteCardListFuncEntity.this._context;
                MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
                try {
                    if (initializedMfcExpert == null) {
                        runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.EXT_CARD_OPERATION_FATAL_ERROR, new MfmException(DeleteCardListFuncEntity.class, 544)));
                        return;
                    }
                    try {
                        try {
                            runner.checkInterrupted();
                            initializedMfcExpert.felicaStart();
                            runner.checkInterrupted();
                            final CountDownLatch countDownLatch = new CountDownLatch(1);
                            initializedMfcExpert.mfiStart(new MfcExpert.MfiStartListener() { // from class: com.felicanetworks.mfm.main.model.internal.main.DeleteCardListFuncEntity.4.1
                                @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                                public void onSuccess(boolean executedSilentStart) {
                                    countDownLatch.countDown();
                                }

                                @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                                public void onRequestActivity(Intent intent) {
                                    DeleteCardListFuncEntity.this.setErrorToRunner(runner, new MfcException(DeleteCardListFuncEntity.class, 545, MfcException.CognitiveType.NOT_LOGIN));
                                    countDownLatch.countDown();
                                }

                                @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                                public void errorResult(MfcException exception) {
                                    DeleteCardListFuncEntity.this.setErrorToRunner(runner, exception);
                                    countDownLatch.countDown();
                                }

                                @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                                public void onSuccessNoLogin() {
                                    DeleteCardListFuncEntity.this.setErrorToRunner(runner, new MfcException(DeleteCardListFuncEntity.class, 546, MfcException.CognitiveType.FATAL_ERROR));
                                    countDownLatch.countDown();
                                }
                            });
                            DeleteCardListFuncEntity.this.await60(countDownLatch);
                            runner.checkInterrupted();
                        } catch (IllegalStateException e) {
                            runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.EXT_CARD_OPERATION_FATAL_ERROR, new MfmException(DeleteCardListFuncEntity.class, 548, e)));
                            asyncRunner = runner;
                            notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.DeleteCardListFuncEntity.4.2
                                @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                public void go() {
                                    if (runner.hasFailure() && ((ModelErrorInfo) runner.getFailure()).getType() != ModelErrorInfo.Type.MFIC_NO_WARNING) {
                                        listener.onError((ModelErrorInfo) runner.getFailure());
                                    } else {
                                        listener.onIssued((MyCardInfo) runner.getSuccess());
                                    }
                                }
                            };
                        }
                    } catch (MfcException e2) {
                        DeleteCardListFuncEntity.this.setErrorToRunner(runner, e2);
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.DeleteCardListFuncEntity.4.2
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (runner.hasFailure() && ((ModelErrorInfo) runner.getFailure()).getType() != ModelErrorInfo.Type.MFIC_NO_WARNING) {
                                    listener.onError((ModelErrorInfo) runner.getFailure());
                                } else {
                                    listener.onIssued((MyCardInfo) runner.getSuccess());
                                }
                            }
                        };
                    } catch (InterruptedException unused2) {
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.DeleteCardListFuncEntity.4.2
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (runner.hasFailure() && ((ModelErrorInfo) runner.getFailure()).getType() != ModelErrorInfo.Type.MFIC_NO_WARNING) {
                                    listener.onError((ModelErrorInfo) runner.getFailure());
                                } else {
                                    listener.onIssued((MyCardInfo) runner.getSuccess());
                                }
                            }
                        };
                    }
                    if (runner.hasFailure()) {
                        return;
                    }
                    MyCardInfo myCardInfoMfiCardReIssue = initializedMfcExpert.mfiCardReIssue(cid);
                    if (myCardInfoMfiCardReIssue != null) {
                        runner.putSuccess(myCardInfoMfiCardReIssue);
                        try {
                            DeleteCardListFuncEntity.this._db.deleteMfiCardAdditionalInfo(cid);
                        } catch (DatabaseException e3) {
                            LogUtil.error(e3);
                        }
                    } else {
                        runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(DeleteCardListFuncEntity.class, 547)));
                    }
                    asyncRunner = runner;
                    notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.DeleteCardListFuncEntity.4.2
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (runner.hasFailure() && ((ModelErrorInfo) runner.getFailure()).getType() != ModelErrorInfo.Type.MFIC_NO_WARNING) {
                                listener.onError((ModelErrorInfo) runner.getFailure());
                            } else {
                                listener.onIssued((MyCardInfo) runner.getSuccess());
                            }
                        }
                    };
                    FuncUtil.notifySafety(asyncRunner, notify);
                } finally {
                    FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.DeleteCardListFuncEntity.4.2
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (runner.hasFailure() && ((ModelErrorInfo) runner.getFailure()).getType() != ModelErrorInfo.Type.MFIC_NO_WARNING) {
                                listener.onError((ModelErrorInfo) runner.getFailure());
                            } else {
                                listener.onIssued((MyCardInfo) runner.getSuccess());
                            }
                        }
                    });
                }
            }
        })) {
            throw new IllegalStateException("DeleteCardListFunc#reissueCard() is still executing.");
        }
    }

    @Override // com.felicanetworks.mfm.main.model.DeleteCardListFunc
    public void cancel() {
        Iterator<FuncUtil.AsyncRunner> it = this._runners.values().iterator();
        while (it.hasNext()) {
            it.next().shutdown();
        }
        this._context.cancellation();
    }

    @Override // com.felicanetworks.mfm.main.model.DeleteCardListFunc
    public void mfcFinish() {
        MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
        if (initializedMfcExpert != null) {
            initializedMfcExpert.finishFeliCaAccess();
        }
    }

    private void getCardInfoWithSpStatus(List<MyServiceInfo> targetServiceInfoList, List<MyCardInfo> targetDetailCards, FuncUtil.AsyncRunner runner) throws InterruptedException {
        targetDetailCards.clear();
        for (MyServiceInfo myServiceInfo : targetServiceInfoList) {
            runner.checkInterrupted();
            try {
                targetDetailCards.addAll(ModelContext.getInitializedMfcExpert().getCardInfoListWithSpStatus(myServiceInfo.getId()));
            } catch (MfcException e) {
                runner.putFailure(convertErrorToCompiledState(e).getMficWarningState());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void await60(CountDownLatch countDownLatch) throws MfcException {
        try {
            if (countDownLatch.await(60L, TimeUnit.SECONDS)) {
                return;
            }
            MfcException mfcException = new MfcException(DeleteCardListFuncEntity.class, 2055, MfcException.CognitiveType.TIMEOUT);
            LogUtil.warning(mfcException);
            throw mfcException;
        } catch (InterruptedException unused) {
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.main.DeleteCardListFuncEntity$5, reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type;

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
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.UNSUPPORTED_DEVICE_ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFI_ISSUE_LIMIT_EXCEEDED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFI_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFI_ISSUE_LIMIT_PER_DEVICE_EXCEEDED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFI_INSUFFICIENT_CHIP_SPACE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFI_OTHER_SP_CARD_EXIST.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFI_INSTANCE_NOT_VACANT.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.TYPE_EXIST_UNKNOWN_CARD.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.FELICA_NETWORK_FAILED.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.OTHER_ERROR.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.FELICA_HTTP_ERROR.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFI_OPSRV_REQUIRED_LIB_UNAVAILABLE.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFIC_VERSION_ERROR.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFIC_JSON_ERROR.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFI_OPSRV_ACCOUNT_ERROR.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFC_USED_BY_OTHER_APP.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFIC_RUNNING_BY_ITSELF.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFI_INSUFFICIENT_ALLOCATED_FREE_SPACE.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MfiCardFunc.CompiledState convertErrorToCompiledState(MfcException e) {
        boolean zIsEmpty;
        switch (AnonymousClass5.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[e.getErrorType().ordinal()]) {
            case 1:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_OPEN_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_OPEN_ERROR, e));
            case 2:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_LOCK_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_LOCK_ERROR, e));
            case 3:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_SERVER_MAINTENANCE_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_SERVER_MAINTENANCE_ERROR, e));
            case 4:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_UNSUPPORTED_DEVICE_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_UNSUPPORTED_DEVICE_ERROR, e));
            case 5:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_ISSUE_LIMIT_EXCEEDED, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_ISSUE_LIMIT_EXCEEDED, e));
            case 6:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED, e));
            case 7:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_ISSUE_LIMIT_PER_DEVICE_EXCEEDED, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_ISSUE_LIMIT_PER_DEVICE_EXCEEDED, e));
            case 8:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_INSUFFICIENT_CHIP_SPACE, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_INSUFFICIENT_CHIP_SPACE, e));
            case 9:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_OTHER_SP_CARD_EXIST, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_OTHER_SP_CARD_EXIST, e));
            case 10:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_INSTANCE_NOT_VACANT, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_INSTANCE_NOT_VACANT, e));
            case 11:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_TYPE_EXIST_UNKNOWN_CARD, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_TYPE_EXIST_UNKNOWN_CARD, e));
            case 12:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_NETWORK_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_NETWORK_ERROR, e));
            case 13:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, new ModelErrorInfo(ModelErrorInfo.Type.EXT_CARD_OPERATION_FATAL_ERROR, e));
            case 14:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_FELICA_HTTP_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.FELICA_HTTP_ERROR, e));
            case 15:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_LIB_UNAVAILABLE, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_LIB_UNAVAILABLE, e));
            case 16:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_VERSION_ERROR, e));
            case 17:
                try {
                    zIsEmpty = this._db.getMfiCardAdditionalInfo().isEmpty();
                    break;
                } catch (Exception unused) {
                    zIsEmpty = false;
                }
                if (zIsEmpty) {
                    return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_JSON_ERROR_NO_CASHE, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_JSON_ERROR_NO_CASHE, e));
                }
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_JSON_ERROR_USE_CACHE, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_JSON_ERROR_USE_CACHE, e));
            case 18:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_LOGIN_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_LOGIN_ERROR, e));
            case 19:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_USED_BY_OTHER_APP, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_USED_BY_OTHER_APP, e));
            case 20:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_RUNNING_BY_ITSELF, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_RUNNING_BY_ITSELF, e));
            default:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_OTHER_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_OTHER_ERROR, e));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setErrorToRunner(FuncUtil.AsyncRunner runner, MfcException mfcException) {
        int i = AnonymousClass5.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[mfcException.getErrorType().ordinal()];
        if (i != 21) {
            switch (i) {
                case 3:
                    runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_SERVER_MAINTENANCE_ERROR, mfcException));
                    break;
                case 4:
                    runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_UNSUPPORTED_DEVICE_ERROR, mfcException));
                    break;
                case 5:
                    runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_ISSUE_LIMIT_EXCEEDED, mfcException));
                    break;
                case 6:
                    runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED, mfcException));
                    break;
                case 7:
                    runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_ISSUE_LIMIT_PER_DEVICE_EXCEEDED, mfcException));
                    break;
                case 8:
                    runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_INSUFFICIENT_CHIP_SPACE, mfcException));
                    break;
                case 9:
                    runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_OTHER_SP_CARD_EXIST, mfcException));
                    break;
                case 10:
                    runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_INSTANCE_NOT_VACANT, mfcException));
                    break;
                case 11:
                    runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_TYPE_EXIST_UNKNOWN_CARD, mfcException));
                    break;
                case 12:
                    runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_NETWORK_ERROR, mfcException));
                    break;
                case 13:
                    runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.EXT_CARD_OPERATION_FATAL_ERROR, mfcException));
                    break;
                default:
                    runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_OTHER_ERROR, mfcException));
                    break;
            }
            return;
        }
        runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_INSUFFICIENT_ALLOCATED_FREE_SPACE, mfcException));
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
}
