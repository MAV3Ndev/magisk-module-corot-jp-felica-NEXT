package com.felicanetworks.mfm.main.model.internal.main;

import com.felicanetworks.mfm.main.model.MemoryUsageFunc;
import com.felicanetworks.mfm.main.model.info.MemoryUsageInfo;
import com.felicanetworks.mfm.main.model.info.MemoryUsageInfos;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.internal.main.FuncUtil;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class MemoryUsageFuncEntity implements MemoryUsageFunc {
    private ModelContext _modelContext;
    private FuncUtil.AsyncRunner _runner;

    private FuncUtil.AsyncRunner getRunner() {
        FuncUtil.AsyncRunner asyncRunner = new FuncUtil.AsyncRunner();
        FuncUtil.AsyncRunner asyncRunner2 = this._runner;
        if (asyncRunner2 != null) {
            asyncRunner2.shutdown();
        }
        this._runner = asyncRunner;
        return asyncRunner;
    }

    MemoryUsageFuncEntity(ModelContext modelContext) {
        this._modelContext = modelContext;
    }

    @Override // com.felicanetworks.mfm.main.model.MemoryUsageFunc
    public void orderInfoList(final MemoryUsageFunc.MemoryUsageListener memoryUsageListener) {
        final FuncUtil.AsyncRunner runner = getRunner();
        if (!runner.startup(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.MemoryUsageFuncEntity.1
            @Override // java.lang.Runnable
            public void run() {
                FuncUtil.AsyncRunner asyncRunner;
                FuncUtil.Notify notify;
                MfcExpert initializedMfcExpert = null;
                try {
                    try {
                        try {
                            ModelContext unused = MemoryUsageFuncEntity.this._modelContext;
                            initializedMfcExpert = ModelContext.getInitializedMfcExpert();
                            runner.checkInterrupted();
                            initializedMfcExpert.felicaStart();
                            MemoryUsageInfos memoryUsageInfos = new MemoryUsageInfos();
                            memoryUsageInfos.setSeid(initializedMfcExpert.getSeInformation().getSeId());
                            initializedMfcExpert.mfcStart();
                            if (Settings.FelicaChipVersion.FAVER_GP_4_1 == Settings.getFelicaChipVersion()) {
                                if (initializedMfcExpert.isSysAreaUseCondInfo()) {
                                    memoryUsageInfos.setMemoryUsage(true);
                                } else {
                                    memoryUsageInfos.setMemoryUsage(false);
                                }
                            } else if (Settings.FelicaChipVersion.FAVER_GP_4_0 != Settings.getFelicaChipVersion()) {
                                ArrayList<MfcExpert.MemoryUsage> arrayList = new ArrayList(initializedMfcExpert.getSystemMemoryUsageList());
                                ArrayList arrayList2 = new ArrayList();
                                for (MfcExpert.MemoryUsage memoryUsage : arrayList) {
                                    arrayList2.add(new MemoryUsageInfo(String.format("%04X", Integer.valueOf(memoryUsage.systemCode)), memoryUsage.usedBlocks, memoryUsage.freeBlocks));
                                }
                                memoryUsageInfos.setMemoryUsageInfoList(arrayList2);
                            } else if (initializedMfcExpert.isSysAreaUseCondInfoNoCidList()) {
                                memoryUsageInfos.setMemoryUsage(true);
                            } else {
                                memoryUsageInfos.setMemoryUsage(false);
                            }
                            runner.putSuccess(memoryUsageInfos);
                            if (initializedMfcExpert != null) {
                                initializedMfcExpert.finishFeliCaAccess();
                            }
                            asyncRunner = runner;
                            notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MemoryUsageFuncEntity.1.1
                                @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                public void go() {
                                    if (runner.hasFailure()) {
                                        memoryUsageListener.onFailure((ModelErrorInfo) runner.getFailure());
                                    } else {
                                        memoryUsageListener.onSuccess((MemoryUsageInfos) runner.getSuccess());
                                    }
                                }
                            };
                        } catch (InterruptedException unused2) {
                            if (initializedMfcExpert != null) {
                                initializedMfcExpert.finishFeliCaAccess();
                            }
                            asyncRunner = runner;
                            notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MemoryUsageFuncEntity.1.1
                                @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                public void go() {
                                    if (runner.hasFailure()) {
                                        memoryUsageListener.onFailure((ModelErrorInfo) runner.getFailure());
                                    } else {
                                        memoryUsageListener.onSuccess((MemoryUsageInfos) runner.getSuccess());
                                    }
                                }
                            };
                        } catch (Exception e) {
                            runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.EXT_CARD_OPERATION_FATAL_ERROR, new MfmException(MemoryUsageFuncEntity.class, 1, e)));
                            if (initializedMfcExpert != null) {
                                initializedMfcExpert.finishFeliCaAccess();
                            }
                            asyncRunner = runner;
                            notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MemoryUsageFuncEntity.1.1
                                @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                public void go() {
                                    if (runner.hasFailure()) {
                                        memoryUsageListener.onFailure((ModelErrorInfo) runner.getFailure());
                                    } else {
                                        memoryUsageListener.onSuccess((MemoryUsageInfos) runner.getSuccess());
                                    }
                                }
                            };
                        }
                    } catch (MfcException e2) {
                        ModelErrorInfo.Type type = ModelErrorInfo.Type.OTHER_ERROR;
                        int i = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[e2.getErrorType().ordinal()];
                        runner.putFailure(new ModelErrorInfo(i != 1 ? i != 2 ? (i == 3 || i == 4) ? ModelErrorInfo.Type.OTHER_ERROR : ModelErrorInfo.Type.MFIC_OTHER_ERROR : ModelErrorInfo.Type.MFIC_NETWORK_ERROR : ModelErrorInfo.Type.MFIC_SERVER_MAINTENANCE_ERROR, e2));
                        if (initializedMfcExpert != null) {
                            initializedMfcExpert.finishFeliCaAccess();
                        }
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MemoryUsageFuncEntity.1.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (runner.hasFailure()) {
                                    memoryUsageListener.onFailure((ModelErrorInfo) runner.getFailure());
                                } else {
                                    memoryUsageListener.onSuccess((MemoryUsageInfos) runner.getSuccess());
                                }
                            }
                        };
                    } catch (NullPointerException unused3) {
                        if (initializedMfcExpert != null) {
                            initializedMfcExpert.finishFeliCaAccess();
                        }
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MemoryUsageFuncEntity.1.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (runner.hasFailure()) {
                                    memoryUsageListener.onFailure((ModelErrorInfo) runner.getFailure());
                                } else {
                                    memoryUsageListener.onSuccess((MemoryUsageInfos) runner.getSuccess());
                                }
                            }
                        };
                    }
                    FuncUtil.notifySafety(asyncRunner, notify);
                } catch (Throwable th) {
                    if (initializedMfcExpert != null) {
                        initializedMfcExpert.finishFeliCaAccess();
                    }
                    FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MemoryUsageFuncEntity.1.1
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (runner.hasFailure()) {
                                memoryUsageListener.onFailure((ModelErrorInfo) runner.getFailure());
                            } else {
                                memoryUsageListener.onSuccess((MemoryUsageInfos) runner.getSuccess());
                            }
                        }
                    });
                    throw th;
                }
            }
        })) {
            throw new IllegalStateException("MemoryUsageFunc#orderInfoList() is still executing.");
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.main.MemoryUsageFuncEntity$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type;

        static {
            int[] iArr = new int[MfcException.Type.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type = iArr;
            try {
                iArr[MfcException.Type.MFI_SERVER_MAINTENANCE_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.FELICA_NETWORK_FAILED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.UNSUPPORTED_CHIP_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.OTHER_ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.model.MemoryUsageFunc
    public void cancel() {
        FuncUtil.AsyncRunner asyncRunner = this._runner;
        if (asyncRunner != null) {
            asyncRunner.shutdown();
        }
        this._modelContext.cancellation();
    }
}
