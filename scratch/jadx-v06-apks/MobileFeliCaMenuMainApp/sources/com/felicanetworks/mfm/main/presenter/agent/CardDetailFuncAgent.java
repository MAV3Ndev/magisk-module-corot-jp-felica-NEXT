package com.felicanetworks.mfm.main.presenter.agent;

import android.graphics.Bitmap;
import com.felicanetworks.mfm.main.model.CardDetailFunc;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class CardDetailFuncAgent {
    private final MyCardInfoAgent cardInfo;
    private final CardDetailFunc client;
    private final MyServiceGroupInfoAgent groupInfo;
    private final boolean isMfiLoggedIn;
    private final MyServiceInfoAgent serviceInfo;

    public interface CreateDetailCardInfoListener {
        void onComplete(MyServiceGroupInfoAgent myServiceGroupInfoAgent, CompleteState completeState);
    }

    public interface DeleteCardListener {
        void onComplete(CompleteState completeState);
    }

    public interface EnableCardListener {
        void onComplete(CompleteState completeState);
    }

    public interface IssueCardListener {
        void onComplete(String str, String str2, CompleteState completeState);
    }

    public interface NotFoundImageListener {
        void onComplete();

        void onGetImage(String str, String str2, Bitmap bitmap);
    }

    public interface RecoveryCardListener {
        void onComplete(CompleteState completeState);
    }

    public CardDetailFuncAgent(CardDetailFunc cardDetailFunc, MyServiceGroupInfoAgent myServiceGroupInfoAgent, MyServiceInfoAgent myServiceInfoAgent, MyCardInfoAgent myCardInfoAgent, boolean z) {
        if (cardDetailFunc == null || myServiceGroupInfoAgent == null) {
            throw new IllegalArgumentException("Required param is null.");
        }
        this.client = cardDetailFunc;
        this.groupInfo = myServiceGroupInfoAgent;
        this.serviceInfo = myServiceInfoAgent;
        this.cardInfo = myCardInfoAgent;
        this.isMfiLoggedIn = z;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (MyServiceInfoAgent myServiceInfoAgent2 : this.groupInfo.getServices()) {
            arrayList.add(myServiceInfoAgent2.getClient());
            Iterator<MyCardInfoAgent> it = myServiceInfoAgent2.getCards().iterator();
            while (it.hasNext()) {
                arrayList2.add(it.next().getClient());
            }
        }
        cardDetailFunc.setup(arrayList, arrayList2);
    }

    public MyServiceGroupInfoAgent getGroupInfo() {
        return this.groupInfo;
    }

    public MyServiceInfoAgent getServiceInfo() {
        MyServiceInfoAgent myServiceInfoAgent = this.serviceInfo;
        return myServiceInfoAgent != null ? myServiceInfoAgent : this.groupInfo.getHighPriorityService();
    }

    public MyCardInfoAgent getCardInfo() {
        MyCardInfoAgent myCardInfoAgent = this.cardInfo;
        return myCardInfoAgent != null ? myCardInfoAgent : getServiceInfo().getMainMyCardInfoAgent();
    }

    public boolean isVanishedInputService(MyServiceGroupInfoAgent myServiceGroupInfoAgent) {
        MyServiceInfoAgent myServiceInfoAgent = this.serviceInfo;
        if (myServiceInfoAgent == null) {
            return false;
        }
        MyServiceInfoAgent myServiceInfoAgentFindService = myServiceGroupInfoAgent.findService(myServiceInfoAgent.getId());
        return myServiceInfoAgentFindService == null || myServiceInfoAgentFindService.getCards().isEmpty();
    }

    public boolean isVanishedInputCard(MyServiceGroupInfoAgent myServiceGroupInfoAgent) {
        MyCardInfoAgent myCardInfoAgent = this.cardInfo;
        return myCardInfoAgent != null && myServiceGroupInfoAgent.findCard(myCardInfoAgent.getCid()) == null;
    }

    public void createDetailCardInfo(boolean z, final CreateDetailCardInfoListener createDetailCardInfoListener) {
        this.client.createDetailCardInfo(this.isMfiLoggedIn, z, new CardDetailFunc.CreateDetailCardInfoListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.1
            @Override // com.felicanetworks.mfm.main.model.CardDetailFunc.CreateDetailCardInfoListener
            public void onCompleted(final List<MyServiceInfo> list, final List<MyCardInfo> list2) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                CompleteState completeState = new CompleteState(CompleteState.FelicaState.NO_PROBLEM);
                                ArrayList arrayList = new ArrayList();
                                for (MyServiceInfo myServiceInfo : list) {
                                    ArrayList arrayList2 = new ArrayList();
                                    for (MyCardInfo myCardInfo : list2) {
                                        if (myCardInfo.getServiceId().equals(myServiceInfo.getId())) {
                                            arrayList2.add(myCardInfo);
                                        }
                                    }
                                    arrayList.add(MyServiceInfoAgent.Factory.create(myServiceInfo, arrayList2, CardDetailFuncAgent.this.isMfiLoggedIn));
                                }
                                createDetailCardInfoListener.onComplete(new MyServiceGroupInfoAgent(arrayList), completeState);
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
                                createDetailCardInfoListener.onComplete(null, CardDetailFuncAgent.this.onErrorState(modelErrorInfo));
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

    public void getNotFoundImage(String str, String str2, final NotFoundImageListener notFoundImageListener) {
        this.client.getNotFoundImage(str, str2, new CardDetailFunc.NotFoundImageListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.2
            @Override // com.felicanetworks.mfm.main.model.CardDetailFunc.NotFoundImageListener
            public void onGetImage(final String str3, final String str4, final Bitmap bitmap) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            notFoundImageListener.onGetImage(str3, str4, bitmap);
                        } catch (Exception e) {
                            LogUtil.error(new MfmException(CardDetailFuncAgent.class, 377, e));
                            notFoundImageListener.onGetImage(str3, str4, null);
                        }
                    }
                });
            }

            @Override // com.felicanetworks.mfm.main.model.CardDetailFunc.NotFoundImageListener
            public void onCompleted() {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.2.2
                    @Override // java.lang.Runnable
                    public void run() {
                        notFoundImageListener.onComplete();
                    }
                });
            }
        });
    }

    public void enableCard(String str, String str2, final EnableCardListener enableCardListener) {
        this.client.enableCard(str, str2, new CardDetailFunc.CardOperationListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.3
            @Override // com.felicanetworks.mfm.main.model.CardDetailFunc.CardOperationListener
            public void onCompleted() {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            enableCardListener.onComplete(new CompleteState(CompleteState.FelicaState.NO_PROBLEM));
                        } catch (Exception e) {
                            MfmException mfmException = new MfmException(CardDetailFuncAgent.class, 384, e);
                            LogUtil.error(mfmException);
                            StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                        }
                    }
                });
            }

            @Override // com.felicanetworks.mfm.main.model.CardDetailFunc.CardOperationListener
            public void onError(final ModelErrorInfo modelErrorInfo) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.3.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                enableCardListener.onComplete(CardDetailFuncAgent.this.onErrorState(modelErrorInfo));
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

    public boolean needWarningDelete(String str) {
        return this.client.needWarningDelete(str);
    }

    public void deleteCard(String str, final DeleteCardListener deleteCardListener) {
        this.client.deleteCard(str, new CardDetailFunc.CardOperationListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.4
            @Override // com.felicanetworks.mfm.main.model.CardDetailFunc.CardOperationListener
            public void onCompleted() {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                deleteCardListener.onComplete(new CompleteState(CompleteState.FelicaState.NO_PROBLEM));
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
            public void onError(final ModelErrorInfo modelErrorInfo) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.4.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                deleteCardListener.onComplete(CardDetailFuncAgent.this.onErrorState(modelErrorInfo));
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

    public void recoveryCard(String str, String str2, final RecoveryCardListener recoveryCardListener) {
        this.client.recoveryCard(str, str2, new CardDetailFunc.CardOperationListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.5
            @Override // com.felicanetworks.mfm.main.model.CardDetailFunc.CardOperationListener
            public void onCompleted() {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            recoveryCardListener.onComplete(new CompleteState(CompleteState.FelicaState.NO_PROBLEM));
                        } catch (Exception e) {
                            MfmException mfmException = new MfmException(CardDetailFuncAgent.class, 386, e);
                            LogUtil.error(mfmException);
                            StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                        }
                    }
                });
            }

            @Override // com.felicanetworks.mfm.main.model.CardDetailFunc.CardOperationListener
            public void onError(final ModelErrorInfo modelErrorInfo) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.5.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                recoveryCardListener.onComplete(CardDetailFuncAgent.this.onErrorState(modelErrorInfo));
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

        CompleteState(FelicaState felicaState, String str, String str2) {
            this._felicaState = felicaState;
            this._errorCode = str;
            this._warCode = str2;
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

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent$6, reason: invalid class name */
    static /* synthetic */ class AnonymousClass6 {
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
    public CompleteState onErrorState(ModelErrorInfo modelErrorInfo) throws MfmException {
        switch (AnonymousClass6.$SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[modelErrorInfo.getType().ordinal()]) {
            case 1:
                return new CompleteState(CompleteState.FelicaState.MFI_MAINTENANCE_WARNING, modelErrorInfo.getErrorCode(), modelErrorInfo.getMfcErrorCode());
            case 2:
                return new CompleteState(CompleteState.FelicaState.MFI_INSUFFICIENT_CHIP_SPACE, modelErrorInfo.getErrorCode(), modelErrorInfo.getMfcErrorCode());
            case 3:
                return new CompleteState(CompleteState.FelicaState.MFI_TYPE_EXIST_UNKNOWN_CARD, modelErrorInfo.getErrorCode(), modelErrorInfo.getMfcErrorCode());
            case 4:
                return new CompleteState(CompleteState.FelicaState.NETWORK_WARNING, modelErrorInfo.getErrorCode(), modelErrorInfo.getMfcErrorCode());
            case 5:
            case 6:
                return new CompleteState(CompleteState.FelicaState.PROCESS_FAILURE_WARNING, modelErrorInfo.getErrorCode(), modelErrorInfo.getMfcErrorCode());
            case 7:
                throw modelErrorInfo.getException();
            case 8:
                return new CompleteState(CompleteState.FelicaState.INSIDE_TRANSIT_GATE_ERROR, modelErrorInfo.getErrorCode(), modelErrorInfo.getMfcErrorCode());
            default:
                return new CompleteState(CompleteState.FelicaState.PROCESS_FAILURE_WARNING, modelErrorInfo.getErrorCode(), modelErrorInfo.getMfcErrorCode());
        }
    }
}
