package com.felicanetworks.mfm.main.presenter.agent;

import android.graphics.Bitmap;
import com.felicanetworks.mfm.main.model.CardDetailFunc;
import com.felicanetworks.mfm.main.model.CentralFunc;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class CardDetailFuncAgent {
    private final MyCardInfoAgent cardInfo;
    private final CentralFuncAgent centralFunc;
    private final CardDetailFunc client;
    private MyServiceGroupInfoAgent groupInfo;
    private final MfiCardFuncAgent mfiCardFunc;
    CentralFunc.BackgroundUpdateMyServiceListener ml;
    private final MyServiceInfoAgent serviceInfo;

    public interface CreateDetailCardInfoListener {
        void onComplete(MyServiceGroupInfoAgent result, CompleteState state);
    }

    public interface DeleteCardListener {
        void onComplete(CompleteState state);
    }

    public interface EnableCardListener {
        void onComplete(CompleteState state);
    }

    public interface IssueCardListener {
        void onComplete(String issuedSid, String issuedCid, CompleteState state);
    }

    public interface NotFoundImageListener {
        void onComplete();

        void onGetImage(String id, String url, Bitmap image);
    }

    public interface OrderUpdateCacheListener {
        void onCompleted(MyServiceGroupInfoAgent groupInfo, CompleteState state);

        void onError(ModelErrorInfo error);
    }

    public interface RecoveryCardListener {
        void onComplete(CompleteState state);
    }

    public CardDetailFuncAgent(CardDetailFunc client, CentralFuncAgent centralFunc, MfiCardFuncAgent mfiCardFunc, MyServiceGroupInfoAgent groupInfo, MyServiceInfoAgent serviceInfo, MyCardInfoAgent cardInfo) {
        if (client == null) {
            throw new IllegalArgumentException("Required param is null.");
        }
        this.client = client;
        this.centralFunc = centralFunc;
        this.mfiCardFunc = mfiCardFunc;
        this.groupInfo = groupInfo;
        this.serviceInfo = serviceInfo;
        this.cardInfo = cardInfo;
        initialize();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initialize() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        MyServiceGroupInfoAgent myServiceGroupInfoAgent = this.groupInfo;
        if (myServiceGroupInfoAgent != null) {
            for (MyServiceInfoAgent myServiceInfoAgent : myServiceGroupInfoAgent.getServices()) {
                arrayList.add(myServiceInfoAgent.getClient());
                Iterator<MyCardInfoAgent> it = myServiceInfoAgent.getCards().iterator();
                while (it.hasNext()) {
                    arrayList2.add(it.next().getClient());
                }
            }
        }
        this.client.setup(arrayList, arrayList2);
    }

    public MyServiceInfoAgent getServiceInfo() {
        MyServiceInfoAgent myServiceInfoAgent = this.serviceInfo;
        return myServiceInfoAgent != null ? myServiceInfoAgent : this.groupInfo.getHighPriorityService();
    }

    public MyCardInfoAgent getCardInfo() {
        MyCardInfoAgent myCardInfoAgent = this.cardInfo;
        return myCardInfoAgent != null ? myCardInfoAgent : getServiceInfo().getMainMyCardInfoAgent();
    }

    public boolean isVanishedInputService(MyServiceGroupInfoAgent group) {
        MyServiceInfoAgent myServiceInfoAgent = this.serviceInfo;
        if (myServiceInfoAgent == null) {
            return false;
        }
        MyServiceInfoAgent myServiceInfoAgentFindService = group.findService(myServiceInfoAgent.getId());
        return myServiceInfoAgentFindService == null || myServiceInfoAgentFindService.getCards().isEmpty();
    }

    public boolean isVanishedInputCard(MyServiceGroupInfoAgent group) {
        MyCardInfoAgent myCardInfoAgent = this.cardInfo;
        return myCardInfoAgent != null && group.findCard(myCardInfoAgent.getCid()) == null;
    }

    public void createDetailCardInfo(boolean forceUpdate, final CreateDetailCardInfoListener listener) {
        this.client.createDetailCardInfo(this.mfiCardFunc.isLoggedIn(), forceUpdate, new CardDetailFunc.CreateDetailCardInfoListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.1
            @Override // com.felicanetworks.mfm.main.model.CardDetailFunc.CreateDetailCardInfoListener
            public void onCompleted(final List<MyServiceInfo> myServiceInfoList, final List<MyCardInfo> myCardInfoList) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                CompleteState completeState = new CompleteState(CompleteState.FelicaState.NO_PROBLEM);
                                ArrayList arrayList = new ArrayList();
                                for (MyServiceInfo myServiceInfo : myServiceInfoList) {
                                    ArrayList arrayList2 = new ArrayList();
                                    for (MyCardInfo myCardInfo : myCardInfoList) {
                                        if (myCardInfo.getServiceId().equals(myServiceInfo.getId())) {
                                            arrayList2.add(myCardInfo);
                                        }
                                    }
                                    arrayList.add(MyServiceInfoAgent.Factory.create(myServiceInfo, arrayList2, CardDetailFuncAgent.this.mfiCardFunc.isLoggedIn(), CardDetailFuncAgent.this.mfiCardFunc.isChaced()));
                                }
                                listener.onComplete(new MyServiceGroupInfoAgent(arrayList), completeState);
                            } catch (Exception e) {
                                MfmException mfmException = new MfmException(CardDetailFuncAgent.class, 375, e);
                                LogUtil.error(mfmException);
                                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                            }
                        } finally {
                            CardDetailFuncAgent.this.client.mfcFinish();
                        }
                    }
                });
            }

            @Override // com.felicanetworks.mfm.main.model.CardDetailFunc.CreateDetailCardInfoListener
            public void onError(final ModelErrorInfo modelErrorInfo) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.1.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                listener.onComplete(null, CardDetailFuncAgent.this.onErrorState(modelErrorInfo));
                            } catch (Exception e) {
                                MfmException mfmException = new MfmException(CardDetailFuncAgent.class, 385, e);
                                LogUtil.error(mfmException);
                                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                            }
                        } finally {
                            CardDetailFuncAgent.this.client.mfcFinish();
                        }
                    }
                });
            }
        });
    }

    public void getNotFoundImage(String unionId, Map<Integer, String> urls, final NotFoundImageListener listener) {
        this.client.getNotFoundImage(unionId, urls, new CardDetailFunc.NotFoundImageListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.2
            @Override // com.felicanetworks.mfm.main.model.CardDetailFunc.NotFoundImageListener
            public void onGetImage(final String id, final String url, final Bitmap image) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            listener.onGetImage(id, url, image);
                        } catch (Exception e) {
                            LogUtil.error(new MfmException(CardDetailFuncAgent.class, 377, e));
                            listener.onGetImage(id, url, null);
                        }
                    }
                });
            }

            @Override // com.felicanetworks.mfm.main.model.CardDetailFunc.NotFoundImageListener
            public void onCompleted() {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.2.2
                    @Override // java.lang.Runnable
                    public void run() {
                        listener.onComplete();
                    }
                });
            }
        });
    }

    public void enableCard(final String cid, final String sid, final EnableCardListener listener) {
        this.client.enableCard(cid, sid, new CardDetailFunc.CardOperationListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.3
            @Override // com.felicanetworks.mfm.main.model.CardDetailFunc.CardOperationListener
            public void onCompleted() {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            listener.onComplete(new CompleteState(CompleteState.FelicaState.NO_PROBLEM));
                        } catch (Exception e) {
                            MfmException mfmException = new MfmException(CardDetailFuncAgent.class, 384, e);
                            LogUtil.error(mfmException);
                            StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                        }
                    }
                });
            }

            @Override // com.felicanetworks.mfm.main.model.CardDetailFunc.CardOperationListener
            public void onError(final ModelErrorInfo error) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.3.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                listener.onComplete(CardDetailFuncAgent.this.onErrorState(error));
                            } catch (Exception e) {
                                MfmException mfmException = new MfmException(CardDetailFuncAgent.class, 388, e);
                                LogUtil.error(mfmException);
                                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                            }
                        } finally {
                            CardDetailFuncAgent.this.client.mfcFinish();
                        }
                    }
                });
            }
        });
    }

    public boolean needWarningDelete(String cid) {
        return this.client.needWarningDelete(cid);
    }

    public void deleteCard(final String cid, final DeleteCardListener listener) {
        this.client.deleteCard(cid, new CardDetailFunc.CardOperationListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.4
            @Override // com.felicanetworks.mfm.main.model.CardDetailFunc.CardOperationListener
            public void onCompleted() {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                listener.onComplete(new CompleteState(CompleteState.FelicaState.NO_PROBLEM));
                            } catch (Exception e) {
                                MfmException mfmException = new MfmException(CardDetailFuncAgent.class, 392, e);
                                LogUtil.error(mfmException);
                                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                            }
                        } finally {
                            CardDetailFuncAgent.this.client.mfcFinish();
                        }
                    }
                });
            }

            @Override // com.felicanetworks.mfm.main.model.CardDetailFunc.CardOperationListener
            public void onError(final ModelErrorInfo error) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.4.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                listener.onComplete(CardDetailFuncAgent.this.onErrorState(error));
                            } catch (Exception e) {
                                MfmException mfmException = new MfmException(CardDetailFuncAgent.class, 400, e);
                                LogUtil.error(mfmException);
                                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                            }
                        } finally {
                            CardDetailFuncAgent.this.client.mfcFinish();
                        }
                    }
                });
            }
        });
    }

    public void recoveryCard(final String cid, final String sid, final RecoveryCardListener listener) {
        this.client.recoveryCard(cid, sid, new CardDetailFunc.CardOperationListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.5
            @Override // com.felicanetworks.mfm.main.model.CardDetailFunc.CardOperationListener
            public void onCompleted() {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            listener.onComplete(new CompleteState(CompleteState.FelicaState.NO_PROBLEM));
                        } catch (Exception e) {
                            MfmException mfmException = new MfmException(CardDetailFuncAgent.class, 386, e);
                            LogUtil.error(mfmException);
                            StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                        }
                    }
                });
            }

            @Override // com.felicanetworks.mfm.main.model.CardDetailFunc.CardOperationListener
            public void onError(final ModelErrorInfo error) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.5.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                listener.onComplete(CardDetailFuncAgent.this.onErrorState(error));
                            } catch (Exception e) {
                                MfmException mfmException = new MfmException(CardDetailFuncAgent.class, 387, e);
                                LogUtil.error(mfmException);
                                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                            }
                        } finally {
                            CardDetailFuncAgent.this.client.mfcFinish();
                        }
                    }
                });
            }
        });
    }

    public static class CompleteState {
        private String _errorCode;
        private FelicaState _felicaState;
        private String _warCode;

        public enum FelicaState {
            NO_PROBLEM,
            MFI_MAINTENANCE_WARNING,
            MFI_INSUFFICIENT_CHIP_SPACE,
            MFI_TYPE_EXIST_UNKNOWN_CARD,
            NETWORK_WARNING,
            PROCESS_FAILURE_WARNING,
            INSIDE_TRANSIT_GATE_ERROR
        }

        CompleteState(FelicaState felicaState) {
            this._felicaState = felicaState;
        }

        CompleteState(FelicaState felicaState, String errorCode, String warCode) {
            this._felicaState = felicaState;
            this._errorCode = errorCode;
            this._warCode = warCode;
        }

        public FelicaState getFelicaState() {
            return this._felicaState;
        }

        public String getErrorCode() {
            return this._errorCode;
        }

        public String getWarCode() {
            return this._warCode;
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent$7, reason: invalid class name */
    static /* synthetic */ class AnonymousClass7 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type;

        static {
            int[] iArr = new int[ModelErrorInfo.Type.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type = iArr;
            try {
                iArr[ModelErrorInfo.Type.MFIC_SERVER_MAINTENANCE_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_INSUFFICIENT_CHIP_SPACE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_TYPE_EXIST_UNKNOWN_CARD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_NETWORK_ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_WARNING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_OTHER_ERROR.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.EXT_CARD_OPERATION_FATAL_ERROR.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_INSIDE_TRANSIT_GATE_ERROR.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CompleteState onErrorState(ModelErrorInfo error) throws MfmException {
        switch (AnonymousClass7.$SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[error.getType().ordinal()]) {
            case 1:
                return new CompleteState(CompleteState.FelicaState.MFI_MAINTENANCE_WARNING, error.getErrorCode(), error.getMfcErrorCode());
            case 2:
                return new CompleteState(CompleteState.FelicaState.MFI_INSUFFICIENT_CHIP_SPACE, error.getErrorCode(), error.getMfcErrorCode());
            case 3:
                return new CompleteState(CompleteState.FelicaState.MFI_TYPE_EXIST_UNKNOWN_CARD, error.getErrorCode(), error.getMfcErrorCode());
            case 4:
                return new CompleteState(CompleteState.FelicaState.NETWORK_WARNING, error.getErrorCode(), error.getMfcErrorCode());
            case 5:
            case 6:
                return new CompleteState(CompleteState.FelicaState.PROCESS_FAILURE_WARNING, error.getErrorCode(), error.getMfcErrorCode());
            case 7:
                throw error.getException();
            case 8:
                return new CompleteState(CompleteState.FelicaState.INSIDE_TRANSIT_GATE_ERROR, error.getErrorCode(), error.getMfcErrorCode());
            default:
                return new CompleteState(CompleteState.FelicaState.PROCESS_FAILURE_WARNING, error.getErrorCode(), error.getMfcErrorCode());
        }
    }

    public boolean isRunning() {
        return this.centralFunc.isUpdateCacheRunning();
    }

    public boolean isUpdateCacheError() {
        return this.centralFunc.isUpdateCacheError();
    }

    public void updateErrorInitialization() {
        this.centralFunc.updateErrorInitialization();
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent$6, reason: invalid class name */
    class AnonymousClass6 implements CentralFunc.BackgroundUpdateMyServiceListener {
        final /* synthetic */ OrderUpdateCacheListener val$listener;

        /* JADX DEBUG: Incorrect args count in method signature: ()V */
        AnonymousClass6(final OrderUpdateCacheListener val$listener) {
            this.val$listener = val$listener;
        }

        @Override // com.felicanetworks.mfm.main.model.CentralFunc.BackgroundUpdateMyServiceListener
        public void onCompleted() {
            if (CardDetailFuncAgent.this.client.isUserOperation()) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.6.1
                    @Override // java.lang.Runnable
                    public void run() {
                        AnonymousClass6.this.val$listener.onError(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(CardDetailFuncEntity.class, 258)));
                    }
                });
                return;
            }
            if (this.val$listener != null) {
                for (MyServiceGroupInfoAgent myServiceGroupInfoAgent : CardDetailFuncAgent.this.centralFunc.getMyServiceGroupInfoList()) {
                    if (myServiceGroupInfoAgent.getGroupId().equals(CardDetailFuncAgent.this.groupInfo.getGroupId())) {
                        CardDetailFuncAgent.this.groupInfo = myServiceGroupInfoAgent;
                    }
                }
                CardDetailFuncAgent.this.initialize();
                CardDetailFuncAgent.this.client.createDetailCardInfo(CardDetailFuncAgent.this.mfiCardFunc.isLoggedIn(), false, new CardDetailFunc.CreateDetailCardInfoListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.6.2
                    @Override // com.felicanetworks.mfm.main.model.CardDetailFunc.CreateDetailCardInfoListener
                    public void onCompleted(final List<MyServiceInfo> myServiceInfoList, final List<MyCardInfo> myCardInfoList) {
                        if (CardDetailFuncAgent.this.client.isUserOperation()) {
                            AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.6.2.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    AnonymousClass6.this.val$listener.onError(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(CardDetailFuncEntity.class, 257)));
                                }
                            });
                        } else {
                            AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.6.2.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    try {
                                        try {
                                            CompleteState completeState = new CompleteState(CompleteState.FelicaState.NO_PROBLEM);
                                            ArrayList arrayList = new ArrayList();
                                            for (MyServiceInfo myServiceInfo : myServiceInfoList) {
                                                ArrayList arrayList2 = new ArrayList();
                                                for (MyCardInfo myCardInfo : myCardInfoList) {
                                                    if (myCardInfo.getServiceId().equals(myServiceInfo.getId())) {
                                                        arrayList2.add(myCardInfo);
                                                    }
                                                }
                                                arrayList.add(MyServiceInfoAgent.Factory.create(myServiceInfo, arrayList2, CardDetailFuncAgent.this.mfiCardFunc.isLoggedIn(), CardDetailFuncAgent.this.mfiCardFunc.isChaced()));
                                            }
                                            AnonymousClass6.this.val$listener.onCompleted(new MyServiceGroupInfoAgent(arrayList), completeState);
                                        } catch (Exception e) {
                                            AnonymousClass6.this.val$listener.onError(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(CardDetailFuncEntity.class, 256, e)));
                                        }
                                    } finally {
                                        CardDetailFuncAgent.this.client.mfcFinish();
                                    }
                                }
                            });
                        }
                    }

                    @Override // com.felicanetworks.mfm.main.model.CardDetailFunc.CreateDetailCardInfoListener
                    public void onError(final ModelErrorInfo modelErrorInfo) {
                        AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.6.2.3
                            @Override // java.lang.Runnable
                            public void run() {
                                if (!CardDetailFuncAgent.this.client.isUserOperation()) {
                                    CardDetailFuncAgent.this.client.mfcFinish();
                                }
                                AnonymousClass6.this.val$listener.onError(modelErrorInfo);
                            }
                        });
                    }
                });
            }
        }

        @Override // com.felicanetworks.mfm.main.model.CentralFunc.BackgroundUpdateMyServiceListener
        public void onError(final ModelErrorInfo error) {
            AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.6.3
                @Override // java.lang.Runnable
                public void run() {
                    if (AnonymousClass6.this.val$listener != null) {
                        AnonymousClass6.this.val$listener.onError(error);
                    }
                }
            });
        }
    }

    public void registOrderAssetListener(OrderUpdateCacheListener listener) {
        AnonymousClass6 anonymousClass6 = new AnonymousClass6(listener);
        this.ml = anonymousClass6;
        this.centralFunc.registBackgroundUpdateMyServiceListener(anonymousClass6);
    }

    public void unRegistOrderAssetListener() {
        this.centralFunc.unRegistBackgroundUpdateMyServiceListener(this.ml);
    }
}
