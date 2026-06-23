package com.felicanetworks.mfm.main.presenter.agent;

import android.graphics.Bitmap;
import android.nfc.Tag;
import androidx.core.view.InputDeviceCompat;
import com.felicanetworks.mfm.main.model.ExtIcCardFunc;
import com.felicanetworks.mfm.main.model.info.ExtIcCardInfo;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes3.dex */
public class ExtIcCardFuncAgent {
    private ExtIcCardFunc _client;
    private DiscoverListener _listener = null;
    private boolean _resolving = false;

    public interface OrderPromotionImageListener {
        void onCompletePromotionImage(Bitmap image);
    }

    public interface DiscoverListener {
        void onDiscover();

        void onResolved(ExtIcCardInfoAgent info, CompleteState state);

        public static class CompleteState {
            private FelicaState _felicaState;
            private ModelErrorInfo _modelErrorInfo;

            public enum FelicaState {
                NO_PROBLEM,
                ONLY_FELICA_POCKET_SUPPORTED,
                UNSUPPORTED_CARD,
                TIMEOUT_ERROR,
                FELICA_OTHER_ERROR,
                OPEN_ERROR,
                NFC_IO_ERROR,
                NFC_ILLEGALSTATE_ERROR
            }

            CompleteState(FelicaState felicaState) {
                this._felicaState = felicaState;
            }

            CompleteState(FelicaState felicaState, ModelErrorInfo modelErrorInfo) {
                this._felicaState = felicaState;
                this._modelErrorInfo = modelErrorInfo;
            }

            public FelicaState getFelicaState() {
                return FelicaState.valueOf(this._felicaState.name());
            }

            public boolean withErrorCode() {
                return this._felicaState == FelicaState.FELICA_OTHER_ERROR;
            }

            public String getErrorCode() {
                if (this._felicaState == FelicaState.FELICA_OTHER_ERROR) {
                    return this._modelErrorInfo.getErrorCode();
                }
                return null;
            }

            public boolean hasMfcErrorCode() {
                return this._felicaState == FelicaState.FELICA_OTHER_ERROR;
            }

            public String getMfcErrorCode() {
                if (this._felicaState == FelicaState.FELICA_OTHER_ERROR) {
                    return this._modelErrorInfo.getMfcErrorCode();
                }
                return null;
            }
        }
    }

    public ExtIcCardFuncAgent(ExtIcCardFunc client) {
        this._client = client;
    }

    public synchronized void registerDiscoverListener(DiscoverListener listener) {
        this._listener = listener;
    }

    public synchronized void unregisterDiscoverListener() {
        this._listener = null;
    }

    public synchronized void declareReady() {
        StateController.sendLocalTagEvent();
    }

    public synchronized boolean resolveTag(Tag tag) {
        if (!runResolve()) {
            return false;
        }
        notifyDiscover();
        this._client.orderInfo(tag, new ExtIcCardFunc.ExtIcCardListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.ExtIcCardFuncAgent.1
            @Override // com.felicanetworks.mfm.main.model.ExtIcCardFunc.ExtIcCardListener
            public void onSuccess(ExtIcCardInfo info) {
                ExtIcCardFuncAgent.this.notifyResolved(new ExtIcCardInfoAgent(info));
            }

            @Override // com.felicanetworks.mfm.main.model.ExtIcCardFunc.ExtIcCardListener
            public void onFailure(ModelErrorInfo error) {
                ExtIcCardFuncAgent.this.notifyUnresolved(error);
            }
        });
        return true;
    }

    public void orderPromotionImage(final String url, final OrderPromotionImageListener listener) {
        try {
            this._client.orderPromotionImage(url, new ExtIcCardFunc.OrderPromotionImageListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.ExtIcCardFuncAgent.2
                @Override // com.felicanetworks.mfm.main.model.ExtIcCardFunc.OrderPromotionImageListener
                public void onSuccess(final Bitmap image) {
                    try {
                        AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.ExtIcCardFuncAgent.2.1
                            @Override // java.lang.Runnable
                            public void run() {
                                try {
                                    listener.onCompletePromotionImage(image);
                                } catch (Exception e) {
                                    MfmException mfmException = new MfmException(ExtIcCardFuncAgent.class, 512, e);
                                    LogUtil.error(mfmException);
                                    StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                                }
                            }
                        });
                    } catch (Exception e) {
                        MfmException mfmException = new MfmException(ExtIcCardFuncAgent.class, InputDeviceCompat.SOURCE_DPAD, e);
                        LogUtil.error(mfmException);
                        StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                    }
                }
            });
        } catch (Exception e) {
            MfmException mfmException = new MfmException(ExtIcCardFuncAgent.class, 514, e);
            LogUtil.error(mfmException);
            StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
        }
    }

    public void cancelPromotionImage() {
        this._client.cancelPromotionImage();
    }

    private void notifyDiscover() {
        AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.ExtIcCardFuncAgent.3
            @Override // java.lang.Runnable
            public void run() {
                synchronized (this) {
                    if (ExtIcCardFuncAgent.this._listener != null) {
                        ExtIcCardFuncAgent.this._listener.onDiscover();
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyResolved(final ExtIcCardInfoAgent info) {
        AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.ExtIcCardFuncAgent.4
            @Override // java.lang.Runnable
            public void run() {
                DiscoverListener.CompleteState completeState;
                synchronized (this) {
                    if (ExtIcCardFuncAgent.this._listener != null) {
                        if (info.hasAsset()) {
                            completeState = new DiscoverListener.CompleteState(DiscoverListener.CompleteState.FelicaState.NO_PROBLEM);
                        } else {
                            completeState = new DiscoverListener.CompleteState(DiscoverListener.CompleteState.FelicaState.ONLY_FELICA_POCKET_SUPPORTED);
                        }
                        ExtIcCardFuncAgent.this._listener.onResolved(info, completeState);
                    }
                    ExtIcCardFuncAgent.this.finishResolve();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyUnresolved(final ModelErrorInfo error) {
        AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.ExtIcCardFuncAgent.5
            @Override // java.lang.Runnable
            public void run() {
                DiscoverListener.CompleteState completeState;
                synchronized (this) {
                    if (ExtIcCardFuncAgent.this._listener != null) {
                        switch (AnonymousClass6.$SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[error.getType().ordinal()]) {
                            case 1:
                                completeState = new DiscoverListener.CompleteState(DiscoverListener.CompleteState.FelicaState.UNSUPPORTED_CARD);
                                break;
                            case 2:
                                completeState = new DiscoverListener.CompleteState(DiscoverListener.CompleteState.FelicaState.TIMEOUT_ERROR);
                                break;
                            case 3:
                                completeState = new DiscoverListener.CompleteState(DiscoverListener.CompleteState.FelicaState.OPEN_ERROR);
                                break;
                            case 4:
                                completeState = new DiscoverListener.CompleteState(DiscoverListener.CompleteState.FelicaState.FELICA_OTHER_ERROR, error);
                                break;
                            case 5:
                                completeState = new DiscoverListener.CompleteState(DiscoverListener.CompleteState.FelicaState.NFC_IO_ERROR);
                                break;
                            case 6:
                                completeState = new DiscoverListener.CompleteState(DiscoverListener.CompleteState.FelicaState.NFC_ILLEGALSTATE_ERROR);
                                break;
                            default:
                                LogUtil.error(error.getException());
                                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, error);
                                completeState = null;
                                break;
                        }
                        ExtIcCardFuncAgent.this._listener.onResolved(null, completeState);
                    }
                    ExtIcCardFuncAgent.this.finishResolve();
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.presenter.agent.ExtIcCardFuncAgent$6, reason: invalid class name */
    static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type;

        static {
            int[] iArr = new int[ModelErrorInfo.Type.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type = iArr;
            try {
                iArr[ModelErrorInfo.Type.NONSUPPORT_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.FELICA_TIMEOUT_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.FELICA_OPEN_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFC_OTHER_ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.NFC_IO_ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.NFC_ILLEGALSTATE_ERROR.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private synchronized boolean runResolve() {
        if (this._resolving) {
            return false;
        }
        this._resolving = true;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void finishResolve() {
        this._resolving = false;
    }

    public FelicaPocketFuncAgent getFelicaPocketFuncAgent() {
        return new FelicaPocketFuncAgent(this._client);
    }
}
