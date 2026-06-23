package com.felicanetworks.mfm.main.presenter.agent;

import com.felicanetworks.mfm.main.model.MemoryUsageFunc;
import com.felicanetworks.mfm.main.model.info.MemoryUsageInfo;
import com.felicanetworks.mfm.main.model.info.MemoryUsageInfos;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class MemoryUsageFuncAgent {
    private MemoryUsageFunc _client;

    public MemoryUsageFuncAgent(MemoryUsageFunc memoryUsageFunc) {
        if (memoryUsageFunc == null) {
            throw new IllegalArgumentException("client is null");
        }
        this._client = memoryUsageFunc;
    }

    public void orderInfoList(final MemoryUsageListener memoryUsageListener) {
        this._client.orderInfoList(new MemoryUsageFunc.MemoryUsageListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.MemoryUsageFuncAgent.1
            @Override // com.felicanetworks.mfm.main.model.MemoryUsageFunc.MemoryUsageListener
            public void onSuccess(final MemoryUsageInfos memoryUsageInfos) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.MemoryUsageFuncAgent.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            MemoryUsageListener.CompleteState completeState = new MemoryUsageListener.CompleteState(MemoryUsageListener.CompleteState.FelicaState.NO_PROBLEM);
                            MemoryUsageInfosAgent memoryUsageInfosAgent = new MemoryUsageInfosAgent();
                            memoryUsageInfosAgent.setSeid(memoryUsageInfos.getSeid());
                            if (memoryUsageInfos.isDisplayInfoList()) {
                                ArrayList arrayList = new ArrayList();
                                Iterator<MemoryUsageInfo> it = memoryUsageInfos.getMemoryUsageInfoList().iterator();
                                while (it.hasNext()) {
                                    arrayList.add(new MemoryUsageInfoAgent(it.next()));
                                }
                                memoryUsageInfosAgent.setMemoryUsageInfoAgentList(arrayList);
                            } else {
                                memoryUsageInfosAgent.setMemoryUsage(memoryUsageInfos.getMemoryUsage());
                            }
                            memoryUsageListener.onComplete(memoryUsageInfosAgent, completeState);
                        } catch (Exception e) {
                            MfmException mfmException = new MfmException(MemoryUsageFuncAgent.class, 104, e);
                            LogUtil.error(mfmException);
                            StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                        }
                    }
                });
            }

            @Override // com.felicanetworks.mfm.main.model.MemoryUsageFunc.MemoryUsageListener
            public void onFailure(final ModelErrorInfo modelErrorInfo) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.MemoryUsageFuncAgent.1.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            MemoryUsageListener.CompleteState completeState = new MemoryUsageListener.CompleteState(MemoryUsageListener.CompleteState.FelicaState.PROCESS_FAILURE_WARNING, modelErrorInfo.getErrorCode(), modelErrorInfo.getMfcErrorCode());
                            int i = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[modelErrorInfo.getType().ordinal()];
                            if (i == 1) {
                                completeState = new MemoryUsageListener.CompleteState(MemoryUsageListener.CompleteState.FelicaState.SERVER_MAINTENANCE_WARNING);
                            } else if (i == 2) {
                                completeState = new MemoryUsageListener.CompleteState(MemoryUsageListener.CompleteState.FelicaState.NETWORK_WARNING);
                            } else if (i == 3) {
                                completeState = new MemoryUsageListener.CompleteState(MemoryUsageListener.CompleteState.FelicaState.OTHER_ERROR);
                                MfmException mfmException = new MfmException(MemoryUsageFuncAgent.class, 390);
                                LogUtil.error(mfmException);
                                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                            }
                            memoryUsageListener.onComplete(new MemoryUsageInfosAgent(), completeState);
                        } catch (Exception e) {
                            MfmException mfmException2 = new MfmException(MemoryUsageFuncAgent.class, 273, e);
                            LogUtil.error(mfmException2);
                            StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException2);
                        }
                    }
                });
            }
        });
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.presenter.agent.MemoryUsageFuncAgent$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type;

        static {
            int[] iArr = new int[ModelErrorInfo.Type.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type = iArr;
            try {
                iArr[ModelErrorInfo.Type.MFIC_SERVER_MAINTENANCE_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_NETWORK_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.OTHER_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public interface MemoryUsageListener {
        void onComplete(MemoryUsageInfosAgent memoryUsageInfosAgent, CompleteState completeState);

        public static class CompleteState {
            private String _errorCode;
            private FelicaState _felicaState;
            private String _warnCode;

            public enum FelicaState {
                NO_PROBLEM,
                NETWORK_WARNING,
                SERVER_MAINTENANCE_WARNING,
                MFC_OTHER_WARNING,
                PROCESS_FAILURE_WARNING,
                OTHER_ERROR
            }

            CompleteState(FelicaState felicaState) {
                this._felicaState = felicaState;
            }

            CompleteState(FelicaState felicaState, String str, String str2) {
                this._felicaState = felicaState;
                this._errorCode = str;
                this._warnCode = str2;
            }

            public FelicaState getFelicaState() {
                return FelicaState.valueOf(this._felicaState.name());
            }

            public String getErrorCode() {
                return this._errorCode;
            }

            public String getWarnCode() {
                return this._warnCode;
            }
        }
    }
}
