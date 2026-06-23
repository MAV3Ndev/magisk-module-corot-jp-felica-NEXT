package com.felicanetworks.mfm.main.model.internal.main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import com.felicanetworks.mfm.main.model.CentralFunc;
import com.felicanetworks.mfm.main.model.FelicaPocketFunc;
import com.felicanetworks.mfm.main.model.MfiCardFunc;
import com.felicanetworks.mfm.main.model.info.AssetInfo;
import com.felicanetworks.mfm.main.model.info.BannerInfo;
import com.felicanetworks.mfm.main.model.info.FpAreaInfo;
import com.felicanetworks.mfm.main.model.info.FpServiceInfo;
import com.felicanetworks.mfm.main.model.info.Linkage;
import com.felicanetworks.mfm.main.model.info.LinkageApp;
import com.felicanetworks.mfm.main.model.info.LinkageDownload;
import com.felicanetworks.mfm.main.model.info.LinkageWeb;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.info.RecommendInfo;
import com.felicanetworks.mfm.main.model.info.Service;
import com.felicanetworks.mfm.main.model.info.specific.MyDcardInfo;
import com.felicanetworks.mfm.main.model.info.specific.MyEdyInfo;
import com.felicanetworks.mfm.main.model.info.specific.MyNanacoInfo;
import com.felicanetworks.mfm.main.model.info.specific.MyQUICPayInfo;
import com.felicanetworks.mfm.main.model.info.specific.MySuicaInfo;
import com.felicanetworks.mfm.main.model.info.specific.MyTrafficInfo;
import com.felicanetworks.mfm.main.model.info.specific.MyWaonInfo;
import com.felicanetworks.mfm.main.model.internal.main.CompileWorker;
import com.felicanetworks.mfm.main.model.internal.main.FuncUtil;
import com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker;
import com.felicanetworks.mfm.main.model.internal.main.OrderImageWorker;
import com.felicanetworks.mfm.main.model.internal.main.OrderUpdateCacheWorker;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaParams;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.SiteAccessProtocol;
import com.felicanetworks.mfm.main.model.internal.main.pkg.PackageExpert;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.service.IdPicker;
import com.felicanetworks.mfm.main.policy.service.ServiceGroupType;
import com.felicanetworks.mfm.main.policy.service.SupportServiceType;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes3.dex */
public class CentralFuncEntity implements CentralFunc {
    private final ModelContext _context;
    private PackageExpert _pe;
    private final Map<String, FuncUtil.AsyncRunner> _runners = new HashMap();
    private FpAreaInfo _fpAreaInfo = null;
    private FelicaPocketFunc.CompiledFpState _compiledFpState = new FelicaPocketFunc.CompiledFpState(FelicaPocketFunc.CompiledFpState.FelicaFpState.UNSUPPORTED);
    private OrderUpdateCacheWorker _orderUpdateCacheWorker = null;
    private FuncUtil.AsyncRunner _orderUpdateCacheRunner = null;
    private List<CentralFunc.BackgroundUpdateMyServiceListener> _BackgroundMyServiceUpdateListenerList = new ArrayList();
    private CompileProperty _compileProperty = new CompileProperty();

    private FuncUtil.AsyncRunner getRunner(String methodName) {
        if (this._runners.containsKey(methodName)) {
            this._runners.remove(methodName).shutdown();
        }
        FuncUtil.AsyncRunner asyncRunner = new FuncUtil.AsyncRunner(methodName);
        this._runners.put(methodName, asyncRunner);
        return asyncRunner;
    }

    ModelContext getModelContext() {
        return this._context;
    }

    static class CompileProperty {
        private List<MyServiceInfo> myServiceInfoList = new ArrayList();
        private List<MyServiceInfo> deleteServiceInfoList = new ArrayList();
        private CentralFunc.ProgressListener progress = null;
        private List<AssetInfo> assetInfos = new ArrayList();
        private MyQUICPayInfo.QPType qpType = MyQUICPayInfo.QPType.UNKNOWN;
        private CentralFunc.CompiledResult result = null;
        private CentralFunc.CompiledState state = null;

        CompileProperty() {
        }

        public synchronized void reset() {
            this.progress = null;
        }

        public synchronized void set(CentralFunc.CompiledResult result, CentralFunc.CompiledState state) {
            this.result = result;
            this.state = state;
        }

        public synchronized void setAsset(List<AssetInfo> assetInfos) {
            this.assetInfos = assetInfos;
        }

        public synchronized AssetInfo getAssetInfo(String serviceId) {
            for (AssetInfo assetInfo : this.assetInfos) {
                if (TextUtils.equals(serviceId, assetInfo.getServiceId())) {
                    return assetInfo;
                }
            }
            return null;
        }

        public synchronized void setQpType(MyQUICPayInfo.QPType qpType) {
            this.qpType = qpType;
        }

        public synchronized MyQUICPayInfo.QPType getQpType() {
            return this.qpType;
        }

        synchronized void setMyServiceInfoList(List<MyServiceInfo> myServiceInfoList) {
            this.myServiceInfoList = myServiceInfoList;
        }

        synchronized void setDeleteServiceInfoList(List<MyServiceInfo> deleteServiceInfoList) {
            this.deleteServiceInfoList = deleteServiceInfoList;
        }

        synchronized void markLackDisplayInfo() {
            if (this.state == null) {
                return;
            }
            this.state = new CentralFunc.CompiledState(this.state.getFelicaState(), this.state.getNetworkState(), true, this.state.isWarningContainOldDispInfo(), this.state.isWaringLackEmoneyInfo(), this.state.isCached());
        }

        /* JADX WARN: Removed duplicated region for block: B:8:0x000b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public synchronized boolean isCompiled() {
            boolean z;
            if (this.result != null) {
                z = this.state != null;
            }
            return z;
        }

        public synchronized void notifyProgress(int numerator, int denominator) {
            CentralFunc.ProgressListener progressListener = this.progress;
            if (progressListener != null) {
                progressListener.onProgress(numerator, denominator);
            }
        }

        public synchronized void setProgressListener(CentralFunc.ProgressListener progress) {
            this.progress = progress;
        }

        public synchronized CentralFunc.CompiledState getCompiledState() {
            return this.state;
        }

        public CentralFunc.CompiledResult getResult() {
            return this.result;
        }
    }

    CentralFuncEntity(ModelContext context) {
        this._pe = null;
        this._context = context;
        ModelContext.setCentralFunc(this);
        this._pe = new PackageExpert(context);
        FpServiceInfo.clearCache();
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public MfcExpert.MfcStatus confirmExistMfc(PackageManager pm) {
        return ModelContext.getInitializedMfcExpert().confirmExistMfc(pm);
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void precompile(final CentralFunc.PrecompileListener listener) {
        final FuncUtil.AsyncRunner runner = getRunner("precompile");
        if (!runner.startup(runner.getName(), new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.1
            @Override // java.lang.Runnable
            public void run() {
                FuncUtil.AsyncRunner asyncRunner;
                FuncUtil.Notify notify;
                ModelErrorInfo.Type type;
                CentralFunc.PrecompileListener.PrecompiledState.FelicaDeviceState felicaDeviceState;
                ModelContext unused = CentralFuncEntity.this._context;
                MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
                try {
                    try {
                        try {
                            runner.checkInterrupted();
                            initializedMfcExpert.felicaStart();
                            runner.checkInterrupted();
                            initializedMfcExpert.provisionServerOperation();
                            runner.checkInterrupted();
                            initializedMfcExpert.mfcStart();
                            MfcExpert.FelicaSettings felicaSettings = initializedMfcExpert.getFelicaSettings();
                            Settings.initIdm(felicaSettings.idm);
                            Settings.initIcCode(felicaSettings.icCode);
                            if (felicaSettings.issueState.equals(MfcExpert.IssueState.ISSUE_STATE_PERFORMED)) {
                                runner.putSuccess(new CentralFunc.PrecompileListener.PrecompiledState(CentralFunc.PrecompileListener.PrecompiledState.FelicaDeviceState.INITIALIZED));
                            } else if (felicaSettings.issueState.equals(MfcExpert.IssueState.ISSUE_STATE_NO_PERFORMED)) {
                                if (((Integer) Sg.getValue(Sg.Key.SETTING_ONLINE_FORMAT)).intValue() == 1) {
                                    CentralFuncEntity.this.mfiStartNoLogin();
                                    try {
                                        if (initializedMfcExpert.checkAndRecoverCrsState()) {
                                            felicaDeviceState = CentralFunc.PrecompileListener.PrecompiledState.FelicaDeviceState.CRS_CHECK_AND_RECOVERY;
                                        } else {
                                            felicaDeviceState = CentralFunc.PrecompileListener.PrecompiledState.FelicaDeviceState.UNINITIALIZED;
                                        }
                                    } catch (MfcException e) {
                                        if (e.getErrorType() == MfcException.Type.NOT_IC_CHIP_FORMATTING) {
                                            felicaDeviceState = CentralFunc.PrecompileListener.PrecompiledState.FelicaDeviceState.UNINITIALIZED;
                                        } else {
                                            throw e;
                                        }
                                    }
                                    runner.putSuccess(new CentralFunc.PrecompileListener.PrecompiledState(felicaDeviceState));
                                } else {
                                    runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.NONSUPPORT_ERROR, new MfmException(CentralFuncEntity.class, 257)));
                                }
                            } else if (felicaSettings.issueState.equals(MfcExpert.IssueState.ISSUE_STATE_MEMORY_CLEAR_RUNNING)) {
                                if (Build.VERSION.SDK_INT >= 33) {
                                    CentralFuncEntity.this.mfiStartNoLogin();
                                    AnalysisManager.stamp(MfmStamp.Event.AUTO_DUMP_EXECUTE_CLEAR_MEMORY, new Object[0]);
                                    CentralFuncEntity.this.memoryClear();
                                    runner.putSuccess(new CentralFunc.PrecompileListener.PrecompiledState(CentralFunc.PrecompileListener.PrecompiledState.FelicaDeviceState.UNINITIALIZED));
                                } else {
                                    runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.EXT_MEMORY_CLEAR_NONSUPPORT_ERROR, new MfmException(CentralFuncEntity.class, 259)));
                                }
                            } else {
                                runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.NONSUPPORT_ERROR, new MfmException(CentralFuncEntity.class, 260)));
                            }
                            asyncRunner = runner;
                            notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.1.1
                                @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                public void go() {
                                    if (runner.hasFailure()) {
                                        listener.onError((ModelErrorInfo) runner.getFailure());
                                    } else {
                                        listener.onCompleted((CentralFunc.PrecompileListener.PrecompiledState) runner.getSuccess());
                                    }
                                }
                            };
                        } catch (Throwable th) {
                            FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.1.1
                                @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                public void go() {
                                    if (runner.hasFailure()) {
                                        listener.onError((ModelErrorInfo) runner.getFailure());
                                    } else {
                                        listener.onCompleted((CentralFunc.PrecompileListener.PrecompiledState) runner.getSuccess());
                                    }
                                }
                            });
                            throw th;
                        }
                    } catch (MfcException e2) {
                        switch (AnonymousClass8.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[e2.getErrorType().ordinal()]) {
                            case 1:
                                type = ModelErrorInfo.Type.FELICA_TIMEOUT_ERROR;
                                break;
                            case 2:
                            case 3:
                                type = ModelErrorInfo.Type.MFC_OTHER_ERROR;
                                break;
                            case 4:
                                type = ModelErrorInfo.Type.LOCKED_FELICA;
                                break;
                            case 5:
                                type = ModelErrorInfo.Type.FELICA_OPEN_ERROR;
                                break;
                            case 6:
                                type = ModelErrorInfo.Type.FELICA_INVALID_RESPONSE_ERROR;
                                break;
                            case 7:
                                type = ModelErrorInfo.Type.USED_BY_OTHER_APP;
                                break;
                            case 8:
                                type = ModelErrorInfo.Type.MFIC_RUNNING_BY_ITSELF;
                                break;
                            case 9:
                                type = ModelErrorInfo.Type.PERM_INSPECT;
                                break;
                            case 10:
                                type = ModelErrorInfo.Type.FELICA_HTTP_ERROR;
                                break;
                            case 11:
                                type = ModelErrorInfo.Type.MFIC_VERSION_ERROR;
                                break;
                            case 12:
                                type = ModelErrorInfo.Type.MFIC_INVALID_REQUEST_TOKEN_ERROR;
                                break;
                            case 13:
                                type = ModelErrorInfo.Type.MFIC_SERVER_MAINTENANCE_ERROR;
                                break;
                            case 14:
                                type = ModelErrorInfo.Type.NETWORK_FAILED;
                                break;
                            case 15:
                                type = ModelErrorInfo.Type.PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_SERVICE;
                                break;
                            case 16:
                                type = ModelErrorInfo.Type.PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_STORE;
                                break;
                            case 17:
                                type = ModelErrorInfo.Type.PIA_PLAY_INTEGRITY_RECOVERABLE_FAILED;
                                break;
                            case 18:
                                type = ModelErrorInfo.Type.PIA_PLAY_INTEGRITY_NONRECOVERABLE_FAILED;
                                break;
                            case 19:
                                type = ModelErrorInfo.Type.MFIC_UNSUPPORTED_DEVICE_ERROR;
                                break;
                            default:
                                type = ModelErrorInfo.Type.OTHER_ERROR;
                                break;
                        }
                        runner.putFailure(new ModelErrorInfo(type, e2));
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.1.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (runner.hasFailure()) {
                                    listener.onError((ModelErrorInfo) runner.getFailure());
                                } else {
                                    listener.onCompleted((CentralFunc.PrecompileListener.PrecompiledState) runner.getSuccess());
                                }
                            }
                        };
                    }
                } catch (InterruptedException unused2) {
                    asyncRunner = runner;
                    notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.1.1
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (runner.hasFailure()) {
                                listener.onError((ModelErrorInfo) runner.getFailure());
                            } else {
                                listener.onCompleted((CentralFunc.PrecompileListener.PrecompiledState) runner.getSuccess());
                            }
                        }
                    };
                } catch (NullPointerException unused3) {
                    asyncRunner = runner;
                    notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.1.1
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (runner.hasFailure()) {
                                listener.onError((ModelErrorInfo) runner.getFailure());
                            } else {
                                listener.onCompleted((CentralFunc.PrecompileListener.PrecompiledState) runner.getSuccess());
                            }
                        }
                    };
                } catch (Exception e3) {
                    runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_RUNTIME_ERROR, new MfmException(CentralFuncEntity.class, 258, e3)));
                    asyncRunner = runner;
                    notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.1.1
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (runner.hasFailure()) {
                                listener.onError((ModelErrorInfo) runner.getFailure());
                            } else {
                                listener.onCompleted((CentralFunc.PrecompileListener.PrecompiledState) runner.getSuccess());
                            }
                        }
                    };
                }
                FuncUtil.notifySafety(asyncRunner, notify);
            }
        })) {
            throw new IllegalStateException("CentralFunc#precompile() is still executing.");
        }
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void compile(boolean isCheckNow, boolean isSimCacheUpdate, final CentralFunc.CompileListener listener) {
        compile("compile", isCheckNow, isSimCacheUpdate, listener);
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void compile(String name, boolean isCheckNow, boolean isSimCacheUpdate, final CentralFunc.CompileListener listener) {
        final FuncUtil.AsyncRunner runner = getRunner(name);
        this._compileProperty.reset();
        if (!runner.startup(runner.getName(), (Thread) new CompileWorker(this._context, isCheckNow, isSimCacheUpdate, new CompileWorker.Listener() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.2
            private static final int PROGRESS_DENOMINATOR = 100;
            private int _numerator = 0;

            @Override // com.felicanetworks.mfm.main.model.internal.main.CompileWorker.Listener
            public void progress(CompileWorker.Listener.Pos pos) {
                switch (AnonymousClass8.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos[pos.ordinal()]) {
                    case 1:
                        this._numerator += 5;
                        break;
                    case 2:
                        this._numerator += 10;
                        break;
                    case 3:
                        this._numerator += 5;
                        break;
                    case 4:
                        this._numerator += 5;
                        break;
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                        this._numerator += 5;
                        break;
                    case 13:
                    case 14:
                        this._numerator += 10;
                        break;
                    case 15:
                        this._numerator = 85;
                        break;
                    case 16:
                        this._numerator = 90;
                        break;
                    case 17:
                        this._numerator = 92;
                        break;
                    case 18:
                        this._numerator = 95;
                        break;
                    case 19:
                        this._numerator = 97;
                        break;
                    case 20:
                        this._numerator = 100;
                        break;
                }
                if (runner.isInterrupt()) {
                    return;
                }
                CentralFuncEntity.this._compileProperty.notifyProgress(this._numerator, 100);
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.CompileWorker.Listener
            public void completed(final CentralFunc.CompiledResult result, final CentralFunc.CompiledState state) {
                CentralFuncEntity.this._compileProperty.set(new CentralFunc.CompiledResult(result), new CentralFunc.CompiledState(state));
                CentralFuncEntity.this._compileProperty.reset();
                FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.2.1
                    @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                    public void go() {
                        listener.onCompleted();
                    }
                });
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.CompileWorker.Listener
            public void error(final ModelErrorInfo error) {
                CentralFuncEntity.this._compileProperty.reset();
                FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.2.2
                    @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                    public void go() {
                        listener.onError(error);
                    }
                });
            }
        }))) {
            throw new IllegalStateException("CentralFunc#compile() is still executing.");
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity$8, reason: invalid class name */
    static /* synthetic */ class AnonymousClass8 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type;

        static {
            int[] iArr = new int[CompileWorker.Listener.Pos.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos = iArr;
            try {
                iArr[CompileWorker.Listener.Pos.MYSERVICE_SIM_UC_END.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos[CompileWorker.Listener.Pos.MYSERVICE_SIM_UC_SKIP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos[CompileWorker.Listener.Pos.MYSERVICE_SIM_GID_END.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos[CompileWorker.Listener.Pos.MYSERVICE_SIM_GID_SKIP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos[CompileWorker.Listener.Pos.MYSERVICE_SIM_SID_CONNECTED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos[CompileWorker.Listener.Pos.MYSERVICE_SIM_SID_RECEIVING_1.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos[CompileWorker.Listener.Pos.MYSERVICE_SIM_SID_RECEIVING_2.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos[CompileWorker.Listener.Pos.MYSERVICE_SIM_SID_RECEIVING_3.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos[CompileWorker.Listener.Pos.MYSERVICE_SIM_SID_RECEIVING_4.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos[CompileWorker.Listener.Pos.MYSERVICE_SIM_SID_RECEIVING_5.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos[CompileWorker.Listener.Pos.MYSERVICE_SIM_SID_RECEIVING_6.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos[CompileWorker.Listener.Pos.MYSERVICE_SIM_SID_RECEIVING_7.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos[CompileWorker.Listener.Pos.MYSERVICE_PARALLEL_MFC_END.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos[CompileWorker.Listener.Pos.MYSERVICE_PARALLEL_APP_END.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos[CompileWorker.Listener.Pos.MYSERVICE_PARALLEL_END.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos[CompileWorker.Listener.Pos.MYSERVICE_END.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos[CompileWorker.Listener.Pos.RECOMMEND_SIM_BOOKMARK_END.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos[CompileWorker.Listener.Pos.RECOMMEND_END.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos[CompileWorker.Listener.Pos.FPSERVICE_SIM_END.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos[CompileWorker.Listener.Pos.FPSERVICE_END.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            int[] iArr2 = new int[MfcException.Type.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type = iArr2;
            try {
                iArr2[MfcException.Type.FELICA_TIMEOUT_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFC_OTHER_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFC_WARNING.ordinal()] = 3;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.LOCKED_FELICA.ordinal()] = 4;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.FELICA_OPEN_ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.FELICA_INVALID_RESPONSE_ERROR.ordinal()] = 6;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFC_USED_BY_OTHER_APP.ordinal()] = 7;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFIC_RUNNING_BY_ITSELF.ordinal()] = 8;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFC_INVALID_PERMISSION.ordinal()] = 9;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.FELICA_HTTP_ERROR.ordinal()] = 10;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFIC_VERSION_ERROR.ordinal()] = 11;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.INVALID_REQUEST_TOKEN.ordinal()] = 12;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFI_SERVER_MAINTENANCE_ERROR.ordinal()] = 13;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.FELICA_NETWORK_FAILED.ordinal()] = 14;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_SERVICE.ordinal()] = 15;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_STORE.ordinal()] = 16;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.PIA_PLAY_INTEGRITY_RECOVERABLE_FAILED.ordinal()] = 17;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.PIA_PLAY_INTEGRITY_NONRECOVERABLE_FAILED.ordinal()] = 18;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.UNSUPPORTED_DEVICE_ERROR.ordinal()] = 19;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFI_OPSRV_ACCOUNT_ERROR.ordinal()] = 20;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFI_OPSRV_REQUIRED_LIB_UNAVAILABLE.ordinal()] = 21;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.UNKNOWN_CHIP_STATE.ordinal()] = 22;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFC_CANNOT_MEMORY_CLEAR_BY_NOT_INITIALIZE.ordinal()] = 23;
            } catch (NoSuchFieldError unused43) {
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void setCompileProgressListener(CentralFunc.ProgressListener listener) {
        this._compileProperty.setProgressListener(listener);
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void marshalModelData(MfiCardFunc mfiCardFunc) {
        AnalysisManager.stamp(MfmStamp.Event.AUTO_DUMP_RESULT_SIM, this._compileProperty.result, this._compileProperty.assetInfos, this._compileProperty.state, getModelContext());
        MySuicaInfo.Position suicaPosition = mfiCardFunc.getSuicaPosition();
        HashSet hashSet = new HashSet();
        hashSet.addAll(getDetectedServiceIdList(mfiCardFunc));
        hashSet.addAll(getAppDetectedServiceIdList());
        hashSet.addAll(getSasDetectedServiceIdList());
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Service service : this._compileProperty.result.master) {
            String serviceId = service.getServiceId();
            if (hashSet.contains(serviceId)) {
                arrayList2.add(serviceId);
                MyServiceInfo myServiceInfoCreateMyServiceInfo = createMyServiceInfo(service, mfiCardFunc.getMyCardDetectSet(), suicaPosition);
                if (myServiceInfoCreateMyServiceInfo != null) {
                    arrayList.add(myServiceInfoCreateMyServiceInfo);
                }
            }
        }
        if (hashSet.size() != arrayList2.size()) {
            this._compileProperty.markLackDisplayInfo();
        }
        this._compileProperty.setMyServiceInfoList(arrayList);
        marshalModelDeleteData(mfiCardFunc);
    }

    private void marshalModelDeleteData(MfiCardFunc mfiCardFunc) {
        MyServiceInfo myServiceInfoCreateMyServiceInfo;
        List<MyCardInfo> deleteCardInfoList = mfiCardFunc.getDeleteCardInfoList();
        MySuicaInfo.Position suicaPosition = mfiCardFunc.getSuicaPosition();
        HashSet hashSet = new HashSet();
        Iterator<MyCardInfo> it = deleteCardInfoList.iterator();
        while (it.hasNext()) {
            hashSet.add(it.next().getServiceId());
        }
        ArrayList arrayList = new ArrayList();
        for (Service service : this._compileProperty.result.master) {
            if (hashSet.contains(service.getServiceId()) && (myServiceInfoCreateMyServiceInfo = createMyServiceInfo(service, hashSet, suicaPosition)) != null) {
                arrayList.add(myServiceInfoCreateMyServiceInfo);
            }
        }
        this._compileProperty.setDeleteServiceInfoList(arrayList);
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public List<String> getAreaDetectedServiceIdList() {
        return this._compileProperty.result.areaHitIds;
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public List<String> getAppDetectedServiceIdList() {
        return this._compileProperty.result.appHitIds;
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public List<String> getSasDetectedServiceIdList() {
        return this._compileProperty.result.sasHitIds;
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public Set<String> getDetectedServiceIdList(MfiCardFunc mfiCardFunc) {
        HashSet hashSet = new HashSet();
        for (String str : this._compileProperty.result.areaHitIds) {
            if (!SupportServiceType.T1.targetIds.contains(str) && !SupportServiceType.S1.targetIds.contains(str)) {
                hashSet.add(str);
            }
        }
        if (mfiCardFunc.isLoggedIn() || mfiCardFunc.isChaced()) {
            hashSet.addAll(mfiCardFunc.getMyCardDetectSet());
            return hashSet;
        }
        if (MySuicaInfo.Position.ENABLE != mfiCardFunc.getSuicaPosition() && MySuicaInfo.Position.DISABLE != mfiCardFunc.getSuicaPosition()) {
            return hashSet;
        }
        hashSet.add(FeliCaParams.SERVICE_ID_SUICA);
        return hashSet;
    }

    @Override // com.felicanetworks.mfm.main.model.FelicaPocketFunc
    public FpAreaInfo getFpAreaInfo() {
        return this._fpAreaInfo;
    }

    @Override // com.felicanetworks.mfm.main.model.FelicaPocketFunc
    public FelicaPocketFunc.CompiledFpState getCompiledFpState() {
        return this._compiledFpState;
    }

    public void clearFelicaFpState() {
        this._compiledFpState.clearState();
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public CentralFunc.CompiledState getCompiledState() {
        return this._compileProperty.getCompiledState();
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public List<RecommendInfo> getRecommendInfoList() {
        Linkage linkageWeb;
        ArrayList arrayList = new ArrayList();
        List<MyServiceInfo> myServiceInfoList = getMyServiceInfoList();
        try {
            Iterator<DatabaseExpert.Recommend> it = this._context.getOpenedDatabaseExpert().getRecommends().iterator();
            while (it.hasNext()) {
                DatabaseExpert.Recommend next = it.next();
                ArrayList arrayList2 = new ArrayList();
                if (next.procedure1 != null) {
                    arrayList2.add(next.procedure1);
                }
                if (next.procedure2 != null) {
                    arrayList2.add(next.procedure2);
                }
                if (next.procedure3 != null) {
                    arrayList2.add(next.procedure3);
                }
                if (next.procedure4 != null) {
                    arrayList2.add(next.procedure4);
                }
                if (next.procedure5 != null) {
                    arrayList2.add(next.procedure5);
                }
                String str = next.id;
                String str2 = next.version;
                String str3 = next.name;
                String str4 = next.provider;
                if ("2".equals(next.linkType)) {
                    linkageWeb = new LinkageDownload(next.downloadType, next.downloadUrl);
                } else {
                    linkageWeb = new LinkageWeb(next.webUrl);
                }
                Iterator<DatabaseExpert.Recommend> it2 = it;
                arrayList.add(new RecommendInfo(str, str2, str3, str4, linkageWeb, next.status, next.summary, next.detail, arrayList2, next.categoryId, next.categoryName, (MyServiceInfo) IdPicker.pickupWithSid(myServiceInfoList, next.id), this._context.getOpenedDatabaseExpert()));
                it = it2;
            }
            return arrayList;
        } catch (DatabaseException e) {
            LogUtil.warning(e);
            return arrayList;
        }
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public Boolean isCompiled() {
        return Boolean.valueOf(this._compileProperty.isCompiled());
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void orderBanner(final CentralFunc.OrderBannerListener listener) {
        final FuncUtil.AsyncRunner runner = getRunner("orderBanner");
        if (!runner.startup(runner.getName(), (Thread) new OrderBannerWorker(this._context, OrderBannerWorker.Type.MY_SERVICE, new OrderBannerWorker.Listener() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.3
            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Listener
            public void onComplete(final List<BannerInfo> bannerInfoList) {
                FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.3.1
                    @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                    public void go() {
                        listener.onSuccess(bannerInfoList);
                    }
                });
            }
        }))) {
            throw new IllegalStateException("CentralFunc#orderBanner() is still executing.");
        }
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void reportRecommend(final String serviceId, final String serviceVersion, final String categoryId, final String status) {
        new Thread(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    NetworkExpert networkExpert = CentralFuncEntity.this._context.getNetworkExpert();
                    networkExpert.connect(networkExpert.getSiteAccessProtocol().create(new SiteAccessProtocol.Parameter(serviceId, serviceVersion, categoryId, status, CentralFuncEntity.this._compileProperty.isCompiled())));
                } catch (Exception unused) {
                }
            }
        }).start();
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc, com.felicanetworks.mfm.main.model.FelicaPocketFunc
    public void cancel() {
        for (FuncUtil.AsyncRunner asyncRunner : this._runners.values()) {
            if (asyncRunner.getName().contains(OrderUpdateCacheWorker.NAME)) {
                if (!isUpdateCacheRunning()) {
                    asyncRunner.shutdown();
                }
            } else {
                asyncRunner.shutdown();
            }
        }
        this._context.cancellation();
    }

    public void cancelOrderUpdateCacheWorker() {
        for (FuncUtil.AsyncRunner asyncRunner : this._runners.values()) {
            if (asyncRunner.getName().contains(OrderUpdateCacheWorker.NAME)) {
                asyncRunner.shutdown();
            }
        }
        this._context.forceFinishFeliCaAccess();
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void orderAsset(MfiCardFunc mfiCardFunc) {
        HashSet hashSet = new HashSet((Collection) Objects.requireNonNull(getAreaDetectedServiceIdList()));
        ArrayList arrayList = new ArrayList();
        Iterator<MyCardInfo> it = mfiCardFunc.getMyCardInfoList().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getServiceId());
        }
        HashSet hashSet2 = new HashSet(arrayList);
        this._fpAreaInfo = null;
        this._compiledFpState = new FelicaPocketFunc.CompiledFpState(FelicaPocketFunc.CompiledFpState.FelicaFpState.UNSUPPORTED);
        ArrayList arrayList2 = new ArrayList();
        for (Service service : this._compileProperty.result.master) {
            if (hashSet.contains(service.getServiceId()) || hashSet2.contains(service.getServiceId())) {
                arrayList2.add(service);
            }
        }
        MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
        Map<Service, List<MyCardInfo>> mapCreateAssetTargetMap = initializedMfcExpert.createAssetTargetMap(arrayList2, mfiCardFunc.getMyCardInfoList(), false);
        try {
            if (!mapCreateAssetTargetMap.isEmpty()) {
                initializedMfcExpert.mfcStart();
                List<MfcExpert.Asset> assetList = initializedMfcExpert.getAssetList(mapCreateAssetTargetMap, false);
                ArrayList arrayList3 = new ArrayList();
                for (MfcExpert.Asset asset : assetList) {
                    arrayList3.add(new AssetInfo(asset.serviceId, asset.cardId, asset.balanceValue, asset.balanceLimit, asset.point1, asset.point2, asset.date1, asset.date2, asset.isDcardMini));
                }
                this._compileProperty.setAsset(arrayList3);
                return;
            }
            this._context.getOpenedDatabaseExpert().setCacheAssetList(new ArrayList());
            this._compileProperty.setAsset(new ArrayList());
        } catch (DatabaseException e) {
            LogUtil.warning(e);
        } catch (MfcException e2) {
            LogUtil.warning(e2);
            this._compiledFpState = new FelicaPocketFunc.CompiledFpState(new MfmException(getClass(), 16, e2));
        }
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void setAsset(List<AssetInfo> assetInfoList) {
        this._compileProperty.setAsset(assetInfoList);
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public synchronized void registBackgroundUpdateMyServiceListener(CentralFunc.BackgroundUpdateMyServiceListener listener) {
        if (!this._BackgroundMyServiceUpdateListenerList.contains(listener)) {
            this._BackgroundMyServiceUpdateListenerList.add(listener);
        }
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public synchronized void unRegistBackgroundUpdateMyServiceListener(CentralFunc.BackgroundUpdateMyServiceListener listener) {
        this._BackgroundMyServiceUpdateListenerList.remove(listener);
    }

    public synchronized void onCompletedBackgroundUpdateMyServiceListener() {
        Iterator<CentralFunc.BackgroundUpdateMyServiceListener> it = this._BackgroundMyServiceUpdateListenerList.iterator();
        while (it.hasNext()) {
            it.next().onCompleted();
        }
    }

    public synchronized void onErrorBackgroundUpdateMyServiceListener(ModelErrorInfo e) {
        Iterator<CentralFunc.BackgroundUpdateMyServiceListener> it = this._BackgroundMyServiceUpdateListenerList.iterator();
        while (it.hasNext()) {
            it.next().onError(e);
        }
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public boolean isUpdateCacheRunning() {
        FuncUtil.AsyncRunner asyncRunner = this._orderUpdateCacheRunner;
        if (asyncRunner == null) {
            return false;
        }
        return asyncRunner.isRunning();
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void updateErrorInitialization() {
        OrderUpdateCacheWorker orderUpdateCacheWorker = this._orderUpdateCacheWorker;
        if (orderUpdateCacheWorker != null) {
            orderUpdateCacheWorker.updateErrorInitialization();
        }
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public boolean isUpdateCacheError() {
        OrderUpdateCacheWorker orderUpdateCacheWorker = this._orderUpdateCacheWorker;
        if (orderUpdateCacheWorker == null) {
            return false;
        }
        return orderUpdateCacheWorker.isError();
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void interrupt() {
        OrderUpdateCacheWorker orderUpdateCacheWorker = this._orderUpdateCacheWorker;
        if (orderUpdateCacheWorker != null) {
            orderUpdateCacheWorker.interrupt();
        }
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void updateCache(MfiCardFunc mfiCardFunc) {
        FuncUtil.AsyncRunner runner = getRunner(OrderUpdateCacheWorker.NAME);
        this._orderUpdateCacheRunner = runner;
        this._context.setOrderUpdateCacheRunner(runner);
        this._orderUpdateCacheWorker = new OrderUpdateCacheWorker(this._context, this, mfiCardFunc, new OrderUpdateCacheWorker.OrderUpdateCacheListener() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.5
            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderUpdateCacheWorker.OrderUpdateCacheListener
            public void onCompleted() {
                FuncUtil.notify(CentralFuncEntity.this._orderUpdateCacheRunner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.5.1
                    @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                    public void go() {
                        CentralFuncEntity.this.onCompletedBackgroundUpdateMyServiceListener();
                    }
                });
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderUpdateCacheWorker.OrderUpdateCacheListener
            public void onError(ModelErrorInfo e) {
                CentralFuncEntity.this.onErrorBackgroundUpdateMyServiceListener(e);
            }
        });
        FuncUtil.AsyncRunner asyncRunner = this._orderUpdateCacheRunner;
        if (!asyncRunner.startup(asyncRunner.getName(), (Thread) this._orderUpdateCacheWorker)) {
            throw new IllegalStateException("CentralFunc#updateCache() is still executing.");
        }
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void readQPType(Set<String> cardDetects) {
        MyQUICPayInfo.QPType qUICPayID;
        MyQUICPayInfo.QPType qPType = MyQUICPayInfo.QPType.UNKNOWN;
        try {
        } catch (MfcException e) {
            LogUtil.warning(e);
            this._compiledFpState = new FelicaPocketFunc.CompiledFpState(new MfmException(getClass(), 17, e));
        }
        if (new HashSet((Collection) Objects.requireNonNull(getAreaDetectedServiceIdList())).contains(FeliCaParams.SERVICE_ID_QP)) {
            MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
            initializedMfcExpert.mfcStart();
            qUICPayID = initializedMfcExpert.getQUICPayID();
        } else {
            if (cardDetects.contains(FeliCaParams.SERVICE_ID_QP)) {
                qUICPayID = MyQUICPayInfo.QPType.QP_MFI;
            }
            this._compileProperty.setQpType(qPType);
        }
        qPType = qUICPayID;
        this._compileProperty.setQpType(qPType);
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void getCardFaceImage(String cardId, Map<Integer, String> urls, final CentralFunc.CardFaceImageListener listener) {
        final FuncUtil.AsyncRunner runner = getRunner("getCardFaceImage_" + cardId);
        if (!runner.startup(runner.getName(), (Thread) new OrderImageWorker(this._context, CardFuncUtility.putCardImageRequestsArrayList(cardId, urls), new OrderImageWorker.Listener() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.6
            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderImageWorker.Listener
            public void onCompleted(final String id) {
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderImageWorker.Listener
            public void onGetImage(final String id, final String url, final Bitmap image) {
                FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.6.1
                    @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                    public void go() {
                        listener.onGetImage(image);
                    }
                });
            }
        }))) {
            throw new IllegalStateException("CardDetailFuncEntity#getCardFaceImage() is still executing.");
        }
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public List<MyServiceInfo> getMyServiceInfoList() {
        return this._compileProperty.myServiceInfoList;
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public List<MyServiceInfo> getDeleteServiceInfoList() {
        return this._compileProperty.deleteServiceInfoList;
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void mfcFinish() {
        MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
        if (initializedMfcExpert != null) {
            initializedMfcExpert.finishFeliCaAccess();
        }
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public List<MfcExpert.Asset> getCacheAssetList() throws DatabaseException {
        return this._context.getOpenedDatabaseExpert().getCacheAssetList();
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public boolean getCacheAssetListSetting() throws DatabaseException {
        return this._context.getOpenedDatabaseExpert().getCacheAssetListSetting();
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public boolean isCached() {
        return getCompiledState().isCached();
    }

    /* JADX WARN: Removed duplicated region for block: B:72:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01c4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private MyServiceInfo createMyServiceInfo(Service service, Set<String> cardDetects, MySuicaInfo.Position suicaPosition) {
        Linkage linkageWeb;
        MyServiceInfo.DetectionResult detectionResult;
        MyServiceInfo.DetectionResult detectionResult2;
        MyServiceInfo.DetectionResult detectionResult3;
        MyServiceInfo.DetectionResult detectionResult4;
        LinkageApp linkageApp;
        LinkageApp linkageApp2 = null;
        if (this._compileProperty.getCompiledState().getFelicaState() != CentralFunc.CompiledState.FelicaState.NO_PROBLEM) {
            return null;
        }
        if ("2".equals(service.getLinkType())) {
            linkageWeb = new LinkageApp(service.getPkg(), service.getSigHash(), service.getDownloadType(), service.getDownloadUrl(), this._pe);
        } else {
            linkageWeb = new LinkageWeb(service.getWebUrl());
        }
        if (this._compileProperty.result.appFamily.contains(service.getServiceId())) {
            if (this._compileProperty.result.appHitIds.contains(service.getServiceId())) {
                detectionResult = MyServiceInfo.DetectionResult.DETECTION;
            } else {
                detectionResult = MyServiceInfo.DetectionResult.NO_DETECTION;
            }
        } else {
            detectionResult = MyServiceInfo.DetectionResult.INVALID;
        }
        if (this._compileProperty.result.areaFamily.contains(service.getServiceId())) {
            if (this._compileProperty.result.areaHitIds.contains(service.getServiceId())) {
                detectionResult2 = MyServiceInfo.DetectionResult.DETECTION;
            } else {
                detectionResult2 = MyServiceInfo.DetectionResult.NO_DETECTION;
            }
        } else {
            detectionResult2 = MyServiceInfo.DetectionResult.INVALID;
        }
        if (this._compileProperty.result.sasFamily.contains(service.getServiceId())) {
            if (this._compileProperty.result.sasHitIds.contains(service.getServiceId())) {
                detectionResult3 = MyServiceInfo.DetectionResult.DETECTION;
            } else {
                detectionResult3 = MyServiceInfo.DetectionResult.NO_DETECTION;
            }
        } else {
            detectionResult3 = MyServiceInfo.DetectionResult.INVALID;
        }
        if (service.isMfi() || TextUtils.equals(service.getServiceId(), FeliCaParams.SERVICE_ID_SUICA)) {
            if (cardDetects.contains(service.getServiceId())) {
                detectionResult4 = MyServiceInfo.DetectionResult.DETECTION;
            } else {
                detectionResult4 = MyServiceInfo.DetectionResult.NO_DETECTION;
            }
        } else {
            detectionResult4 = MyServiceInfo.DetectionResult.INVALID;
        }
        MyServiceInfo myServiceInfo = new MyServiceInfo(service, linkageWeb, MyServiceInfo.makeDetectionResult(detectionResult, detectionResult2, detectionResult3, detectionResult4), this._context.getOpenedDatabaseExpert());
        AssetInfo assetInfo = this._compileProperty.getAssetInfo(myServiceInfo.getId());
        String serviceId = service.getServiceId();
        if ("SV000013".equals(serviceId)) {
            return new MyEdyInfo(myServiceInfo, assetInfo);
        }
        if ("SV000075".equals(serviceId)) {
            return new MyNanacoInfo(myServiceInfo, assetInfo);
        }
        if (FeliCaParams.SERVICE_ID_SUICA.equals(serviceId)) {
            return new MySuicaInfo(myServiceInfo, assetInfo, suicaPosition);
        }
        if ("SV000011".equals(serviceId)) {
            return new MyWaonInfo(myServiceInfo, assetInfo);
        }
        if (FeliCaParams.SERVICE_ID_DCARD.equals(serviceId)) {
            return new MyDcardInfo(myServiceInfo, assetInfo);
        }
        if (!FeliCaParams.SERVICE_ID_QP.equals(serviceId)) {
            return ServiceGroupType.resolve(service.getServiceId()) == ServiceGroupType.TRANSPORTATION ? new MyTrafficInfo(myServiceInfo, assetInfo) : myServiceInfo;
        }
        MyQUICPayInfo.QPType qpType = this._compileProperty.getQpType();
        if (qpType == MyQUICPayInfo.QPType.QP_LOCAL) {
            linkageApp = new LinkageApp((String) Sg.getValue(Sg.Key.SETTING_QP_MIZUHO_PKG), (String) Sg.getValue(Sg.Key.SETTING_QP_MIZUHO_SIG_HASH), (String) Sg.getValue(Sg.Key.SETTING_QP_MIZUHO_DOWNLOAD_TYPE), (String) Sg.getValue(Sg.Key.SETTING_QP_MIZUHO_DOWNLOAD_URL), this._pe);
        } else {
            if (qpType == MyQUICPayInfo.QPType.QP_MFI || qpType == MyQUICPayInfo.QPType.UNKNOWN) {
                linkageApp = new LinkageApp((String) Sg.getValue(Sg.Key.SETTING_QP_PKG), (String) Sg.getValue(Sg.Key.SETTING_QP_SIG_HASH), (String) Sg.getValue(Sg.Key.SETTING_QP_DOWNLOAD_TYPE), (String) Sg.getValue(Sg.Key.SETTING_QP_DOWNLOAD_URL), this._pe);
            }
            if (linkageApp2 != null) {
                return new MyQUICPayInfo(myServiceInfo, qpType);
            }
            return new MyQUICPayInfo(myServiceInfo, qpType, linkageApp2);
        }
        linkageApp2 = linkageApp;
        if (linkageApp2 != null) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mfiStartNoLogin() throws MfcException {
        MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
        initializedMfcExpert.setSilentStartCode(0);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final FuncUtil.AsyncPacket asyncPacket = new FuncUtil.AsyncPacket();
        initializedMfcExpert.mfiStartNoLogin(new MfcExpert.MfiStartListener() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.7
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
            public void onSuccess(boolean executedSilentStart) {
                asyncPacket.set(new MfcException(MemoryClearFuncEntity.class, 1, MfcException.CognitiveType.ILLEGAL_STATE));
                countDownLatch.countDown();
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
            public void onRequestActivity(Intent intent) {
                asyncPacket.set(new MfcException(MemoryClearFuncEntity.class, 2, MfcException.CognitiveType.ILLEGAL_STATE));
                countDownLatch.countDown();
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
            public void errorResult(MfcException e) {
                asyncPacket.set(e);
                countDownLatch.countDown();
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
            public void onSuccessNoLogin() {
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException unused) {
        }
        if (asyncPacket.exist()) {
            throw ((MfcException) asyncPacket.get());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void memoryClear() throws MfcException {
        MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
        try {
            initializedMfcExpert.clearMemory();
        } catch (MfcException e) {
            if (e.getErrorType().equals(MfcException.Type.FELICA_NETWORK_FAILED)) {
                SystemClock.sleep(1000L);
                initializedMfcExpert.clearMemory();
                return;
            }
            throw e;
        }
    }
}
