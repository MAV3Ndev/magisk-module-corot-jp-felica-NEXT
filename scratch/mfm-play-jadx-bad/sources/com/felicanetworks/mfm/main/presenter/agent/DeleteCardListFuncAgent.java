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
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class DeleteCardListFuncAgent {
    private final MyCardInfoAgent cardInfo;
    private final DeleteCardListFunc client;
    private final boolean isChaced;
    private final boolean isMfiLoggedIn;
    private final MyServiceInfoAgent serviceInfo;

    public interface CreateDeleteCardListInfoListener {
        void onComplete(MyServiceGroupInfoAgent result, CompleteState state);
    }

    public interface IssueCardListener {
        void onComplete(String issuedSid, String issuedCid, CompleteState state);
    }

    public interface LackCardFaceImageListener {
        void onGetImage(String id, String url, Bitmap image);
    }

    public DeleteCardListFuncAgent(DeleteCardListFunc client, MyServiceInfoAgent serviceInfo, MyCardInfoAgent cardInfo, boolean isMfiLoggedIn, boolean isChaced) {
        if (client == null) {
            throw new IllegalArgumentException("Required param is null.");
        }
        this.client = client;
        this.serviceInfo = serviceInfo;
        this.cardInfo = cardInfo;
        this.isMfiLoggedIn = isMfiLoggedIn;
        this.isChaced = isChaced;
        ArrayList arrayList = new ArrayList();
        arrayList.add(serviceInfo.getClient());
        client.setup(arrayList);
    }

    public MyServiceInfoAgent getServiceInfo() {
        return this.serviceInfo;
    }

    public MyCardInfoAgent getCardInfo() {
        MyCardInfoAgent myCardInfoAgent = this.cardInfo;
        return myCardInfoAgent != null ? myCardInfoAgent : getServiceInfo().getMainMyCardInfoAgent();
    }

    public boolean isVanishedInputService(MyServiceGroupInfoAgent group) {
        MyServiceInfoAgent myServiceInfoAgentFindService = group.findService(this.serviceInfo.getId());
        return myServiceInfoAgentFindService == null || myServiceInfoAgentFindService.getCards().isEmpty();
    }

    public boolean isVanishedInputCard(MyServiceGroupInfoAgent group) {
        MyCardInfoAgent myCardInfoAgent = this.cardInfo;
        return myCardInfoAgent != null && group.findCard(myCardInfoAgent.getCid()) == null;
    }

    public void createDeleteCardListInfo(final CreateDeleteCardListInfoListener listener) {
        this.client.createDeleteCardListInfo(new DeleteCardListFunc.CreateDeleteCardListInfoListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.DeleteCardListFuncAgent.1
            @Override // com.felicanetworks.mfm.main.model.DeleteCardListFunc.CreateDeleteCardListInfoListener
            public void onCompleted(final List<MyServiceInfo> myServiceInfoList, final List<MyCardInfo> myCardInfoList) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.DeleteCardListFuncAgent.1.1
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
                                    arrayList.add(MyServiceInfoAgent.Factory.create(myServiceInfo, arrayList2, DeleteCardListFuncAgent.this.isMfiLoggedIn, DeleteCardListFuncAgent.this.isChaced));
                                }
                                listener.onComplete(new MyServiceGroupInfoAgent(arrayList), completeState);
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
                                listener.onComplete(null, DeleteCardListFuncAgent.this.onErrorState(modelErrorInfo));
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

    public void getLackCardFaceImage(String unionId, Map<Integer, String> urls, final LackCardFaceImageListener listener) {
        this.client.getLackCardFaceImage(unionId, urls, new DeleteCardListFunc.LackCardFaceImageListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.DeleteCardListFuncAgent.2
            @Override // com.felicanetworks.mfm.main.model.DeleteCardListFunc.LackCardFaceImageListener
            public void onGetImage(final String id, final String url, final Bitmap image) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.DeleteCardListFuncAgent.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            listener.onGetImage(id, url, image);
                        } catch (Exception e) {
                            LogUtil.error(new MfmException(DeleteCardListFuncAgent.class, 377, e));
                            listener.onGetImage(id, url, null);
                        }
                    }
                });
            }
        });
    }

    public void issueCard(String cid, final IssueCardListener listener) {
        this.client.reissueCard(cid, new DeleteCardListFunc.ReissueCardListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.DeleteCardListFuncAgent.3
            @Override // com.felicanetworks.mfm.main.model.DeleteCardListFunc.ReissueCardListener
            public void onIssued(final MyCardInfo card) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.DeleteCardListFuncAgent.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                listener.onComplete(card.getServiceId(), card.getCardId(), new CompleteState(CompleteState.FelicaState.NO_PROBLEM));
                            } catch (Exception e) {
                                MfmException mfmException = new MfmException(CardDetailFuncAgent.class, 401, e);
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
            public void onError(final ModelErrorInfo error) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.DeleteCardListFuncAgent.3.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                listener.onComplete(null, null, DeleteCardListFuncAgent.this.onErrorState(error));
                            } catch (Exception e) {
                                MfmException mfmException = new MfmException(CardDetailFuncAgent.class, TypedValues.CycleType.TYPE_ALPHA, e);
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
    public CompleteState onErrorState(ModelErrorInfo error) throws MfmException {
        switch (AnonymousClass4.$SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[error.getType().ordinal()]) {
            case 1:
                return new CompleteState(CompleteState.FelicaState.MFI_MAINTENANCE_WARNING, error.getErrorCode(), error.getMfcErrorCode());
            case 2:
                return new CompleteState(CompleteState.FelicaState.UNSUPPORTED_DEVICE_WARNING, error.getErrorCode(), error.getMfcErrorCode());
            case 3:
                return new CompleteState(CompleteState.FelicaState.MFI_ISSUE_LIMIT_EXCEEDED, error.getErrorCode(), error.getMfcErrorCode());
            case 4:
                return new CompleteState(CompleteState.FelicaState.MFI_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED, error.getErrorCode(), error.getMfcErrorCode());
            case 5:
                return new CompleteState(CompleteState.FelicaState.MFI_ISSUE_LIMIT_PER_DEVICE_EXCEEDED, error.getErrorCode(), error.getMfcErrorCode());
            case 6:
                return new CompleteState(CompleteState.FelicaState.MFI_INSUFFICIENT_CHIP_SPACE, error.getErrorCode(), error.getMfcErrorCode());
            case 7:
                return new CompleteState(CompleteState.FelicaState.MFI_OTHER_SP_CARD_EXIST, error.getErrorCode(), error.getMfcErrorCode());
            case 8:
                return new CompleteState(CompleteState.FelicaState.MFI_INSTANCE_NOT_VACANT, error.getErrorCode(), error.getMfcErrorCode());
            case 9:
                return new CompleteState(CompleteState.FelicaState.MFI_TYPE_EXIST_UNKNOWN_CARD, error.getErrorCode(), error.getMfcErrorCode());
            case 10:
                return new CompleteState(CompleteState.FelicaState.MFI_INSUFFICIENT_ALLOCATED_FREE_SPACE, error.getErrorCode(), error.getMfcErrorCode());
            case 11:
                return new CompleteState(CompleteState.FelicaState.SP_MAINTENANCE_WARNING, error.getErrorCode(), error.getMfcErrorCode());
            case 12:
                return new CompleteState(CompleteState.FelicaState.NETWORK_WARNING, error.getErrorCode(), error.getMfcErrorCode());
            case 13:
                throw error.getException();
            default:
                return new CompleteState(CompleteState.FelicaState.PROCESS_FAILURE_WARNING, error.getErrorCode(), error.getMfcErrorCode());
        }
    }
}
