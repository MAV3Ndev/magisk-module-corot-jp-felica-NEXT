package com.felicanetworks.mfm.main.presenter.agent;

import android.graphics.Bitmap;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.felicanetworks.mfm.main.model.DeleteCardListFunc;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DeleteCardListFuncAgent {
    private final MyCardInfoAgent cardInfo;
    private final DeleteCardListFunc client;
    private final boolean isMfiLoggedIn;
    private final MyServiceInfoAgent serviceInfo;

    public interface CreateDeleteCardListInfoListener {
        void onComplete(MyServiceGroupInfoAgent myServiceGroupInfoAgent, CompleteState completeState);
    }

    public interface IssueCardListener {
        void onComplete(String str, String str2, CompleteState completeState);
    }

    public interface LackCardFaceImageListener {
        void onGetImage(String str, String str2, Bitmap bitmap);
    }

    public DeleteCardListFuncAgent(DeleteCardListFunc deleteCardListFunc, MyServiceInfoAgent myServiceInfoAgent, MyCardInfoAgent myCardInfoAgent, boolean z) {
        if (deleteCardListFunc == null) {
            throw new IllegalArgumentException("Required param is null.");
        }
        this.client = deleteCardListFunc;
        this.serviceInfo = myServiceInfoAgent;
        this.cardInfo = myCardInfoAgent;
        this.isMfiLoggedIn = z;
        ArrayList arrayList = new ArrayList();
        arrayList.add(myServiceInfoAgent.getClient());
        deleteCardListFunc.setup(arrayList);
    }

    public MyServiceInfoAgent getServiceInfo() {
        return this.serviceInfo;
    }

    public MyCardInfoAgent getCardInfo() {
        MyCardInfoAgent myCardInfoAgent = this.cardInfo;
        return myCardInfoAgent != null ? myCardInfoAgent : getServiceInfo().getMainMyCardInfoAgent();
    }

    public boolean isVanishedInputService(MyServiceGroupInfoAgent myServiceGroupInfoAgent) {
        MyServiceInfoAgent myServiceInfoAgentFindService = myServiceGroupInfoAgent.findService(this.serviceInfo.getId());
        return myServiceInfoAgentFindService == null || myServiceInfoAgentFindService.getCards().isEmpty();
    }

    public boolean isVanishedInputCard(MyServiceGroupInfoAgent myServiceGroupInfoAgent) {
        MyCardInfoAgent myCardInfoAgent = this.cardInfo;
        return myCardInfoAgent != null && myServiceGroupInfoAgent.findCard(myCardInfoAgent.getCid()) == null;
    }

    public void createDeleteCardListInfo(final CreateDeleteCardListInfoListener createDeleteCardListInfoListener) {
        this.client.createDeleteCardListInfo(new DeleteCardListFunc.CreateDeleteCardListInfoListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.DeleteCardListFuncAgent.1
            @Override // com.felicanetworks.mfm.main.model.DeleteCardListFunc.CreateDeleteCardListInfoListener
            public void onCompleted(final List<MyServiceInfo> list, final List<MyCardInfo> list2) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.DeleteCardListFuncAgent.1.1
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
                                    arrayList.add(MyServiceInfoAgent.Factory.create(myServiceInfo, arrayList2, DeleteCardListFuncAgent.this.isMfiLoggedIn));
                                }
                                createDeleteCardListInfoListener.onComplete(new MyServiceGroupInfoAgent(arrayList), completeState);
                            } catch (Exception e) {
                                MfmException mfmException = new MfmException(CardDetailFuncAgent.class, 375, e);
                                LogUtil.error(mfmException);
                                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                            }
                        } finally {
                            DeleteCardListFuncAgent.this.client.mfcFinish();
                        }
                    }
                });
            }

            @Override // com.felicanetworks.mfm.main.model.DeleteCardListFunc.CreateDeleteCardListInfoListener
            public void onError(final ModelErrorInfo modelErrorInfo) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.DeleteCardListFuncAgent.1.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                createDeleteCardListInfoListener.onComplete(null, DeleteCardListFuncAgent.this.onErrorState(modelErrorInfo));
                            } catch (Exception e) {
                                MfmException mfmException = new MfmException(CardDetailFuncAgent.class, 385, e);
                                LogUtil.error(mfmException);
                                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                            }
                        } finally {
                            DeleteCardListFuncAgent.this.client.mfcFinish();
                        }
                    }
                });
            }
        });
    }

    public void getLackCardFaceImage(String str, String str2, final LackCardFaceImageListener lackCardFaceImageListener) {
        this.client.getLackCardFaceImage(str, str2, new DeleteCardListFunc.LackCardFaceImageListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.DeleteCardListFuncAgent.2
            @Override // com.felicanetworks.mfm.main.model.DeleteCardListFunc.LackCardFaceImageListener
            public void onGetImage(final String str3, final String str4, final Bitmap bitmap) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.DeleteCardListFuncAgent.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            lackCardFaceImageListener.onGetImage(str3, str4, bitmap);
                        } catch (Exception e) {
                            LogUtil.error(new MfmException(DeleteCardListFuncAgent.class, 377, e));
                            lackCardFaceImageListener.onGetImage(str3, str4, null);
                        }
                    }
                });
            }
        });
    }

    public void issueCard(String str, final IssueCardListener issueCardListener) {
        this.client.reissueCard(str, new DeleteCardListFunc.ReissueCardListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.DeleteCardListFuncAgent.3
            @Override // com.felicanetworks.mfm.main.model.DeleteCardListFunc.ReissueCardListener
            public void onIssued(final MyCardInfo myCardInfo) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.DeleteCardListFuncAgent.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                issueCardListener.onComplete(myCardInfo.getServiceId(), myCardInfo.getCardId(), new CompleteState(CompleteState.FelicaState.NO_PROBLEM));
                            } catch (Exception e) {
                                MfmException mfmException = new MfmException(CardDetailFuncAgent.class, TypedValues.Cycle.TYPE_CURVE_FIT, e);
                                LogUtil.error(mfmException);
                                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                            }
                        } finally {
                            DeleteCardListFuncAgent.this.client.mfcFinish();
                        }
                    }
                });
            }

            @Override // com.felicanetworks.mfm.main.model.DeleteCardListFunc.ReissueCardListener
            public void onError(final ModelErrorInfo modelErrorInfo) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.DeleteCardListFuncAgent.3.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                issueCardListener.onComplete(null, null, DeleteCardListFuncAgent.this.onErrorState(modelErrorInfo));
                            } catch (Exception e) {
                                MfmException mfmException = new MfmException(CardDetailFuncAgent.class, TypedValues.Cycle.TYPE_ALPHA, e);
                                LogUtil.error(mfmException);
                                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                            }
                        } finally {
                            DeleteCardListFuncAgent.this.client.mfcFinish();
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
            SP_MAINTENANCE_WARNING,
            MFI_MAINTENANCE_WARNING,
            UNSUPPORTED_DEVICE_WARNING,
            MFI_ISSUE_LIMIT_EXCEEDED,
            MFI_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED,
            MFI_ISSUE_LIMIT_PER_DEVICE_EXCEEDED,
            MFI_INSUFFICIENT_CHIP_SPACE,
            MFI_OTHER_SP_CARD_EXIST,
            MFI_INSTANCE_NOT_VACANT,
            MFI_TYPE_EXIST_UNKNOWN_CARD,
            MFI_INSUFFICIENT_ALLOCATED_FREE_SPACE,
            NETWORK_WARNING,
            PROCESS_FAILURE_WARNING
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

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.presenter.agent.DeleteCardListFuncAgent$4, reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type;

        static {
            int[] iArr = new int[ModelErrorInfo.Type.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type = iArr;
            try {
                iArr[ModelErrorInfo.Type.MFIC_SERVER_MAINTENANCE_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_UNSUPPORTED_DEVICE_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_ISSUE_LIMIT_EXCEEDED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_ISSUE_LIMIT_PER_DEVICE_EXCEEDED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_INSUFFICIENT_CHIP_SPACE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_OTHER_SP_CARD_EXIST.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_INSTANCE_NOT_VACANT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_TYPE_EXIST_UNKNOWN_CARD.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_INSUFFICIENT_ALLOCATED_FREE_SPACE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.FELICA_INVALID_RESPONSE_ERROR.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_NETWORK_ERROR.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.EXT_CARD_OPERATION_FATAL_ERROR.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_WARNING.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_OTHER_ERROR.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CompleteState onErrorState(ModelErrorInfo modelErrorInfo) throws MfmException {
        switch (AnonymousClass4.$SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[modelErrorInfo.getType().ordinal()]) {
            case 1:
                return new CompleteState(CompleteState.FelicaState.MFI_MAINTENANCE_WARNING, modelErrorInfo.getErrorCode(), modelErrorInfo.getMfcErrorCode());
            case 2:
                return new CompleteState(CompleteState.FelicaState.UNSUPPORTED_DEVICE_WARNING, modelErrorInfo.getErrorCode(), modelErrorInfo.getMfcErrorCode());
            case 3:
                return new CompleteState(CompleteState.FelicaState.MFI_ISSUE_LIMIT_EXCEEDED, modelErrorInfo.getErrorCode(), modelErrorInfo.getMfcErrorCode());
            case 4:
                return new CompleteState(CompleteState.FelicaState.MFI_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED, modelErrorInfo.getErrorCode(), modelErrorInfo.getMfcErrorCode());
            case 5:
                return new CompleteState(CompleteState.FelicaState.MFI_ISSUE_LIMIT_PER_DEVICE_EXCEEDED, modelErrorInfo.getErrorCode(), modelErrorInfo.getMfcErrorCode());
            case 6:
                return new CompleteState(CompleteState.FelicaState.MFI_INSUFFICIENT_CHIP_SPACE, modelErrorInfo.getErrorCode(), modelErrorInfo.getMfcErrorCode());
            case 7:
                return new CompleteState(CompleteState.FelicaState.MFI_OTHER_SP_CARD_EXIST, modelErrorInfo.getErrorCode(), modelErrorInfo.getMfcErrorCode());
            case 8:
                return new CompleteState(CompleteState.FelicaState.MFI_INSTANCE_NOT_VACANT, modelErrorInfo.getErrorCode(), modelErrorInfo.getMfcErrorCode());
            case 9:
                return new CompleteState(CompleteState.FelicaState.MFI_TYPE_EXIST_UNKNOWN_CARD, modelErrorInfo.getErrorCode(), modelErrorInfo.getMfcErrorCode());
            case 10:
                return new CompleteState(CompleteState.FelicaState.MFI_INSUFFICIENT_ALLOCATED_FREE_SPACE, modelErrorInfo.getErrorCode(), modelErrorInfo.getMfcErrorCode());
            case 11:
                return new CompleteState(CompleteState.FelicaState.SP_MAINTENANCE_WARNING, modelErrorInfo.getErrorCode(), modelErrorInfo.getMfcErrorCode());
            case 12:
                return new CompleteState(CompleteState.FelicaState.NETWORK_WARNING, modelErrorInfo.getErrorCode(), modelErrorInfo.getMfcErrorCode());
            case 13:
                throw modelErrorInfo.getException();
            default:
                return new CompleteState(CompleteState.FelicaState.PROCESS_FAILURE_WARNING, modelErrorInfo.getErrorCode(), modelErrorInfo.getMfcErrorCode());
        }
    }
}
