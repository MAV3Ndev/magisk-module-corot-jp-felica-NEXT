package com.felicanetworks.mfm.main.model.internal.main;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.felicanetworks.mfm.main.model.info.specific.MySuicaInfo;
import com.felicanetworks.mfm.main.model.info.specific.MyTrafficInfo;
import com.felicanetworks.mfm.main.model.info.specific.MyWaonInfo;
import com.felicanetworks.mfm.main.model.internal.main.CompileWorker;
import com.felicanetworks.mfm.main.model.internal.main.FuncUtil;
import com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker;
import com.felicanetworks.mfm.main.model.internal.main.OrderImageWorker;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaParams;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.ImageProtocol;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.model.internal.main.net.OsaifulifePlusImageProtocol;
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

/* JADX INFO: loaded from: classes.dex */
public class CentralFuncEntity implements CentralFunc {
    private ModelContext _context;
    private PackageExpert _pe;
    private Map<String, FuncUtil.AsyncRunner> _runners = new HashMap();
    private FpAreaInfo _fpAreaInfo = null;
    private FelicaPocketFunc.CompiledFpState _compiledFpState = new FelicaPocketFunc.CompiledFpState(FelicaPocketFunc.CompiledFpState.FelicaFpState.UNSUPPORTED);
    private NetworkExpert _neForOrderBannerOsaifuLifePlus = null;
    private CompileProperty _compileProperty = new CompileProperty();

    private FuncUtil.AsyncRunner getRunner(String str) {
        if (this._runners.containsKey(str)) {
            this._runners.remove(str).shutdown();
        }
        FuncUtil.AsyncRunner asyncRunner = new FuncUtil.AsyncRunner();
        this._runners.put(str, asyncRunner);
        return asyncRunner;
    }

    ModelContext getModelContext() {
        return this._context;
    }

    private static class CompileProperty {
        private List<AssetInfo> assetInfos;
        private List<MyServiceInfo> deleteServiceInfoList;
        private List<MyServiceInfo> myServiceInfoList;
        private CentralFunc.ProgressListener progress;
        private CentralFunc.CompiledResult result;
        private CentralFunc.CompiledState state;

        private CompileProperty() {
            this.myServiceInfoList = new ArrayList();
            this.deleteServiceInfoList = new ArrayList();
            this.progress = null;
            this.assetInfos = new ArrayList();
            this.result = null;
            this.state = null;
        }

        public synchronized void reset() {
            this.progress = null;
        }

        public synchronized void set(CentralFunc.CompiledResult compiledResult, CentralFunc.CompiledState compiledState) {
            this.result = compiledResult;
            this.state = compiledState;
        }

        public synchronized void setAsset(List<AssetInfo> list) {
            this.assetInfos = list;
        }

        public synchronized AssetInfo getAssetInfo(String str) {
            for (AssetInfo assetInfo : this.assetInfos) {
                if (TextUtils.equals(str, assetInfo.getServiceId())) {
                    return assetInfo;
                }
            }
            return null;
        }

        synchronized void setMyServiceInfoList(List<MyServiceInfo> list) {
            this.myServiceInfoList = list;
        }

        synchronized void setDeleteServiceInfoList(List<MyServiceInfo> list) {
            this.deleteServiceInfoList = list;
        }

        synchronized void markLackDisplayInfo() {
            if (this.state == null) {
                return;
            }
            this.state = new CentralFunc.CompiledState(this.state.getFelicaState(), this.state.getNetworkState(), true, this.state.isWarningContainOldDispInfo(), this.state.isWaringLackEmoneyInfo());
        }

        /* JADX WARN: Removed duplicated region for block: B:8:0x000b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public synchronized boolean isCompiled() {
            /*
                r1 = this;
                monitor-enter(r1)
                com.felicanetworks.mfm.main.model.CentralFunc$CompiledResult r0 = r1.result     // Catch: java.lang.Throwable -> Le
                if (r0 == 0) goto Lb
                com.felicanetworks.mfm.main.model.CentralFunc$CompiledState r0 = r1.state     // Catch: java.lang.Throwable -> Le
                if (r0 == 0) goto Lb
                r0 = 1
                goto Lc
            Lb:
                r0 = 0
            Lc:
                monitor-exit(r1)
                return r0
            Le:
                r0 = move-exception
                monitor-exit(r1)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.CompileProperty.isCompiled():boolean");
        }

        public synchronized void notifyProgress(int i, int i2) {
            if (this.progress != null) {
                this.progress.onProgress(i, i2);
            }
        }

        public synchronized void setProgressListener(CentralFunc.ProgressListener progressListener) {
            this.progress = progressListener;
        }

        public synchronized CentralFunc.CompiledState getCompiledState() {
            return this.state;
        }
    }

    CentralFuncEntity(ModelContext modelContext) {
        this._pe = null;
        this._context = modelContext;
        this._pe = new PackageExpert(modelContext);
        FpServiceInfo.clearCache();
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public MfcExpert.MfcStatus confirmExistMfc(PackageManager packageManager) {
        return ModelContext.getInitializedMfcExpert().confirmExistMfc(packageManager);
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void precompile(final CentralFunc.PrecompileListener precompileListener) {
        final FuncUtil.AsyncRunner runner = getRunner("precompile");
        if (!runner.startup(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.1
            @Override // java.lang.Runnable
            public void run() {
                FuncUtil.AsyncRunner asyncRunner;
                FuncUtil.Notify notify;
                ModelErrorInfo.Type type;
                FuncUtil.AsyncRunner asyncRunner2;
                FuncUtil.Notify notify2;
                MfcExpert.FelicaSettings felicaSettings;
                CentralFunc.PrecompileListener.PrecompiledState.FelicaDeviceState felicaDeviceState;
                try {
                    try {
                        ModelContext unused = CentralFuncEntity.this._context;
                        MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
                        try {
                            runner.checkInterrupted();
                            initializedMfcExpert.felicaStart();
                            initializedMfcExpert.provisionServerOperation();
                            initializedMfcExpert.mfcStart();
                            felicaSettings = initializedMfcExpert.getFelicaSettings();
                            Settings.initIdm(felicaSettings.idm);
                            Settings.initIcCode(felicaSettings.icCode);
                        } catch (MfcException e) {
                            switch (AnonymousClass6.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[e.getErrorType().ordinal()]) {
                                case 1:
                                    type = ModelErrorInfo.Type.FELICA_TIMEOUT_ERROR;
                                    break;
                                case 2:
                                    type = ModelErrorInfo.Type.MFC_OTHER_ERROR;
                                    break;
                                case 3:
                                    type = ModelErrorInfo.Type.LOCKED_FELICA;
                                    break;
                                case 4:
                                    type = ModelErrorInfo.Type.FELICA_OPEN_ERROR;
                                    break;
                                case 5:
                                    type = ModelErrorInfo.Type.FELICA_INVALID_RESPONSE_ERROR;
                                    break;
                                case 6:
                                    type = ModelErrorInfo.Type.USED_BY_OTHER_APP;
                                    break;
                                case 7:
                                    type = ModelErrorInfo.Type.MFIC_RUNNING_BY_ITSELF;
                                    break;
                                case 8:
                                    type = ModelErrorInfo.Type.PERM_INSPECT;
                                    break;
                                case 9:
                                    type = ModelErrorInfo.Type.FELICA_HTTP_ERROR;
                                    break;
                                case 10:
                                    type = ModelErrorInfo.Type.MFIC_VERSION_ERROR;
                                    break;
                                default:
                                    type = ModelErrorInfo.Type.OTHER_ERROR;
                                    break;
                            }
                            runner.putFailure(new ModelErrorInfo(type, e));
                            asyncRunner2 = runner;
                            notify2 = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.1.1
                                @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                public void go() {
                                    if (runner.hasFailure()) {
                                        precompileListener.onError((ModelErrorInfo) runner.getFailure());
                                    } else {
                                        precompileListener.onCompleted((CentralFunc.PrecompileListener.PrecompiledState) runner.getSuccess());
                                    }
                                }
                            };
                        }
                        if (felicaSettings.isFormated) {
                            felicaDeviceState = CentralFunc.PrecompileListener.PrecompiledState.FelicaDeviceState.INITIALIZED;
                        } else {
                            if (((Integer) Sg.getValue(Sg.Key.SETTING_ONLINE_FORMAT)).intValue() != 1) {
                                runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.NONSUPPORT_ERROR, new MfmException(CentralFuncEntity.class, 257)));
                                asyncRunner2 = runner;
                                notify2 = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.1.1
                                    @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                    public void go() {
                                        if (runner.hasFailure()) {
                                            precompileListener.onError((ModelErrorInfo) runner.getFailure());
                                        } else {
                                            precompileListener.onCompleted((CentralFunc.PrecompileListener.PrecompiledState) runner.getSuccess());
                                        }
                                    }
                                };
                                FuncUtil.notifySafety(asyncRunner2, notify2);
                                return;
                            }
                            felicaDeviceState = CentralFunc.PrecompileListener.PrecompiledState.FelicaDeviceState.UNINITIALIZED;
                        }
                        runner.putSuccess(new CentralFunc.PrecompileListener.PrecompiledState(felicaDeviceState));
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.1.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (runner.hasFailure()) {
                                    precompileListener.onError((ModelErrorInfo) runner.getFailure());
                                } else {
                                    precompileListener.onCompleted((CentralFunc.PrecompileListener.PrecompiledState) runner.getSuccess());
                                }
                            }
                        };
                    } catch (InterruptedException unused2) {
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.1.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (runner.hasFailure()) {
                                    precompileListener.onError((ModelErrorInfo) runner.getFailure());
                                } else {
                                    precompileListener.onCompleted((CentralFunc.PrecompileListener.PrecompiledState) runner.getSuccess());
                                }
                            }
                        };
                    } catch (NullPointerException unused3) {
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.1.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (runner.hasFailure()) {
                                    precompileListener.onError((ModelErrorInfo) runner.getFailure());
                                } else {
                                    precompileListener.onCompleted((CentralFunc.PrecompileListener.PrecompiledState) runner.getSuccess());
                                }
                            }
                        };
                    } catch (Exception e2) {
                        runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_RUNTIME_ERROR, new MfmException(CentralFuncEntity.class, 258, e2)));
                        asyncRunner = runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.1.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (runner.hasFailure()) {
                                    precompileListener.onError((ModelErrorInfo) runner.getFailure());
                                } else {
                                    precompileListener.onCompleted((CentralFunc.PrecompileListener.PrecompiledState) runner.getSuccess());
                                }
                            }
                        };
                    }
                    FuncUtil.notifySafety(asyncRunner, notify);
                } catch (Throwable th) {
                    FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.1.1
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (runner.hasFailure()) {
                                precompileListener.onError((ModelErrorInfo) runner.getFailure());
                            } else {
                                precompileListener.onCompleted((CentralFunc.PrecompileListener.PrecompiledState) runner.getSuccess());
                            }
                        }
                    });
                    throw th;
                }
            }
        })) {
            throw new IllegalStateException("CentralFunc#precompile() is still executing.");
        }
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void compile(boolean z, final CentralFunc.CompileListener compileListener) {
        final FuncUtil.AsyncRunner runner = getRunner("compile");
        this._compileProperty.reset();
        if (!runner.startup((Thread) new CompileWorker(this._context, z, new CompileWorker.Listener() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.2
            private static final int PROGRESS_DENOMINATOR = 100;
            private int _numerator = 0;

            @Override // com.felicanetworks.mfm.main.model.internal.main.CompileWorker.Listener
            public void progress(CompileWorker.Listener.Pos pos) {
                switch (AnonymousClass6.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$CompileWorker$Listener$Pos[pos.ordinal()]) {
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
            public void completed(CentralFunc.CompiledResult compiledResult, CentralFunc.CompiledState compiledState) {
                CentralFuncEntity.this._compileProperty.set(new CentralFunc.CompiledResult(compiledResult), new CentralFunc.CompiledState(compiledState));
                CentralFuncEntity.this._compileProperty.reset();
                FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.2.1
                    @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                    public void go() {
                        compileListener.onCompleted();
                    }
                });
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.CompileWorker.Listener
            public void error(final ModelErrorInfo modelErrorInfo) {
                CentralFuncEntity.this._compileProperty.reset();
                FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.2.2
                    @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                    public void go() {
                        compileListener.onError(modelErrorInfo);
                    }
                });
            }
        }))) {
            throw new IllegalStateException("CentralFunc#compile() is still executing.");
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity$6, reason: invalid class name */
    static /* synthetic */ class AnonymousClass6 {
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
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.LOCKED_FELICA.ordinal()] = 3;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.FELICA_OPEN_ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.FELICA_INVALID_RESPONSE_ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFC_USED_BY_OTHER_APP.ordinal()] = 6;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFIC_RUNNING_BY_ITSELF.ordinal()] = 7;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFC_INVALID_PERMISSION.ordinal()] = 8;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.FELICA_HTTP_ERROR.ordinal()] = 9;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFIC_VERSION_ERROR.ordinal()] = 10;
            } catch (NoSuchFieldError unused30) {
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void setCompileProgressListener(CentralFunc.ProgressListener progressListener) {
        this._compileProperty.setProgressListener(progressListener);
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
        if (mfiCardFunc.isLoggedIn()) {
            hashSet.addAll(mfiCardFunc.getMyCardDetectSet());
        } else if (MySuicaInfo.Position.ENABLE == mfiCardFunc.getSuicaPosition() || MySuicaInfo.Position.DISABLE == mfiCardFunc.getSuicaPosition()) {
            hashSet.add(FeliCaParams.SERVICE_ID_SUICA);
        }
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

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public CentralFunc.CompiledState getCompiledState() {
        return this._compileProperty.getCompiledState();
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public List<RecommendInfo> getRecommendInfoList() {
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
                Iterator<DatabaseExpert.Recommend> it2 = it;
                List<MyServiceInfo> list = myServiceInfoList;
                arrayList.add(new RecommendInfo(next.id, next.version, next.name, next.provider, "2".equals(next.linkType) ? new LinkageDownload(next.downloadType, next.downloadUrl) : new LinkageWeb(next.webUrl), next.status, next.summary, next.detail, arrayList2, next.categoryId, next.categoryName, (MyServiceInfo) IdPicker.pickupWithSid(myServiceInfoList, next.id), this._context.getOpenedDatabaseExpert()));
                it = it2;
                myServiceInfoList = list;
            }
        } catch (DatabaseException e) {
            LogUtil.warning(e);
        }
        return arrayList;
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public Boolean isCompiled() {
        return Boolean.valueOf(this._compileProperty.isCompiled());
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void orderBanner(final CentralFunc.OrderBannerListener orderBannerListener) {
        final FuncUtil.AsyncRunner runner = getRunner("orderBanner");
        if (!runner.startup((Thread) new OrderBannerWorker(this._context, OrderBannerWorker.Type.MY_SERVICE, new OrderBannerWorker.Listener() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.3
            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Listener
            public void onComplete(final List<BannerInfo> list) {
                FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.3.1
                    @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                    public void go() {
                        orderBannerListener.onSuccess(list);
                    }
                });
            }
        }))) {
            throw new IllegalStateException("CentralFunc#orderBanner() is still executing.");
        }
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void reportRecommend(final String str, final String str2, final String str3, final String str4) {
        new Thread(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    NetworkExpert networkExpert = CentralFuncEntity.this._context.getNetworkExpert();
                    networkExpert.connect(networkExpert.getSiteAccessProtocol().create(new SiteAccessProtocol.Parameter(str, str2, str3, str4, CentralFuncEntity.this._compileProperty.isCompiled())));
                } catch (Exception unused) {
                }
            }
        }).start();
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc, com.felicanetworks.mfm.main.model.FelicaPocketFunc
    public void cancel() {
        Iterator<FuncUtil.AsyncRunner> it = this._runners.values().iterator();
        while (it.hasNext()) {
            it.next().shutdown();
        }
        this._context.cancellation();
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void orderAsset(MfiCardFunc mfiCardFunc) {
        MfcExpert.FpArea fpAreaInfo;
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
        Iterator<String> it2 = this._compileProperty.result.areaHitIds.iterator();
        boolean z = false;
        while (it2.hasNext()) {
            if (TextUtils.equals(FeliCaParams.SERVICE_ID_FP, it2.next())) {
                z = true;
            }
        }
        if (!mapCreateAssetTargetMap.isEmpty() || z) {
            try {
                initializedMfcExpert.mfcStart();
                List<MfcExpert.Asset> assetList = initializedMfcExpert.getAssetList(mapCreateAssetTargetMap, false);
                ArrayList arrayList3 = new ArrayList();
                for (MfcExpert.Asset asset : assetList) {
                    arrayList3.add(new AssetInfo(asset.serviceId, asset.cardId, asset.balanceValue, asset.balanceLimit, asset.point1, asset.point2, asset.date1, asset.date2, asset.isDcardMini));
                }
                this._compileProperty.setAsset(arrayList3);
                if (!z || (fpAreaInfo = initializedMfcExpert.getFpAreaInfo()) == null) {
                    return;
                }
                FpAreaInfo fpAreaInfoMarshalFpAreaInfo = new FelicaPocketFunc.FpParser(fpAreaInfo, this._context).marshalFpAreaInfo();
                this._fpAreaInfo = fpAreaInfoMarshalFpAreaInfo;
                if (fpAreaInfoMarshalFpAreaInfo != null) {
                    this._compiledFpState = new FelicaPocketFunc.CompiledFpState(FelicaPocketFunc.CompiledFpState.FelicaFpState.NO_PROBLEM);
                    AnalysisManager.stamp(MfmStamp.Event.AUTO_DUMP_RESULT_CREATE_FP_SERVICES, this._fpAreaInfo);
                }
            } catch (DatabaseException e) {
                LogUtil.warning(e);
                this._compiledFpState = new FelicaPocketFunc.CompiledFpState(new MfmException(getClass(), 17, e));
            } catch (MfcException e2) {
                LogUtil.warning(e2);
                this._compiledFpState = new FelicaPocketFunc.CompiledFpState(new MfmException(getClass(), 16, e2));
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void getCardFaceImage(String str, String str2, final CentralFunc.CardFaceImageListener cardFaceImageListener) {
        final FuncUtil.AsyncRunner runner = getRunner("getCardFaceImage_" + str);
        if (!runner.startup((Thread) new OrderImageWorker(this._context, new OrderImageWorker.Request(str, str2), new OrderImageWorker.Listener() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.5
            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderImageWorker.Listener
            public void onCompleted(String str3) {
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderImageWorker.Listener
            public void onGetImage(String str3, String str4, final Bitmap bitmap) {
                FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity.5.1
                    @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                    public void go() {
                        cardFaceImageListener.onGetImage(bitmap);
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
    public void orderBannerOsaifuLifePlus(final CentralFunc.OrderBannerOsaifuLifePlusListener orderBannerOsaifuLifePlusListener) {
        if (!getRunner("orderBannerOsaifuLifePlus").startup(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.-$$Lambda$CentralFuncEntity$ZpuK9I86CS_--d1Z7_jy6--G0kk
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$orderBannerOsaifuLifePlus$0$CentralFuncEntity(orderBannerOsaifuLifePlusListener);
            }
        })) {
            throw new IllegalStateException("CentralFunc#orderBannerOsaifuLifePlus() is still executing.");
        }
    }

    public /* synthetic */ void lambda$orderBannerOsaifuLifePlus$0$CentralFuncEntity(CentralFunc.OrderBannerOsaifuLifePlusListener orderBannerOsaifuLifePlusListener) {
        NetworkExpert networkExpert;
        String str = (String) Sg.getValue(Sg.Key.NET_OSAIFULIFE_PLUS_BANNER_URL);
        int iIntValue = ((Integer) Sg.getValue(Sg.Key.NET_OSAIFULIFE_PLUS_BANNER_SESSION_TIMEOUT)).intValue();
        int iIntValue2 = ((Integer) Sg.getValue(Sg.Key.NET_OSAIFULIFE_PLUS_BANNER_RECEIVING_TIMEOUT)).intValue();
        Bitmap bitmap = null;
        try {
            synchronized (this) {
                networkExpert = new NetworkExpert(this._context);
                this._neForOrderBannerOsaifuLifePlus = networkExpert;
            }
            OsaifulifePlusImageProtocol osaifulifePlusImageProtocol = networkExpert.getOsaifulifePlusImageProtocol();
            ImageProtocol.Result result = osaifulifePlusImageProtocol.parse(this._neForOrderBannerOsaifuLifePlus.connect(osaifulifePlusImageProtocol.create(new OsaifulifePlusImageProtocol.Parameter(str, iIntValue, iIntValue2))));
            Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(result.bytes, 0, result.bytes.length);
            synchronized (this) {
                this._neForOrderBannerOsaifuLifePlus = null;
            }
            bitmap = bitmapDecodeByteArray;
        } catch (NetworkExpertException unused) {
            synchronized (this) {
                this._neForOrderBannerOsaifuLifePlus = null;
            }
        } catch (Throwable th) {
            synchronized (this) {
                this._neForOrderBannerOsaifuLifePlus = null;
                throw th;
            }
        }
        orderBannerOsaifuLifePlusListener.onSuccess(bitmap);
    }

    @Override // com.felicanetworks.mfm.main.model.CentralFunc
    public void cancelOrderBannerOsaifuLifePlus() {
        getRunner("cancelOrderBannerOsaifuLifePlus").startup(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.-$$Lambda$CentralFuncEntity$iyOQxqJk2TAF1vqpv7LRnzKFxTc
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$cancelOrderBannerOsaifuLifePlus$1$CentralFuncEntity();
            }
        });
    }

    public /* synthetic */ void lambda$cancelOrderBannerOsaifuLifePlus$1$CentralFuncEntity() {
        synchronized (this) {
            if (this._neForOrderBannerOsaifuLifePlus != null) {
                this._neForOrderBannerOsaifuLifePlus.disconnect();
            }
        }
    }

    private MyServiceInfo createMyServiceInfo(Service service, Set<String> set, MySuicaInfo.Position position) {
        Linkage linkageWeb;
        MyServiceInfo.DetectionResult detectionResult;
        MyServiceInfo.DetectionResult detectionResult2;
        MyServiceInfo.DetectionResult detectionResult3;
        MyServiceInfo.DetectionResult detectionResult4;
        MyServiceInfo myTrafficInfo;
        if (this._compileProperty.getCompiledState().getFelicaState() != CentralFunc.CompiledState.FelicaState.NO_PROBLEM) {
            return null;
        }
        if ("2".equals(service.getLinkType())) {
            linkageWeb = new LinkageApp(service.getPkg(), service.getSigHash(), service.getDownloadType(), service.getDownloadUrl(), this._pe);
        } else {
            linkageWeb = new LinkageWeb(service.getWebUrl());
        }
        if (this._compileProperty.result.appFamily.contains(service.getServiceId())) {
            detectionResult = this._compileProperty.result.appHitIds.contains(service.getServiceId()) ? MyServiceInfo.DetectionResult.DETECTION : MyServiceInfo.DetectionResult.NO_DETECTION;
        } else {
            detectionResult = MyServiceInfo.DetectionResult.INVALID;
        }
        if (this._compileProperty.result.areaFamily.contains(service.getServiceId())) {
            detectionResult2 = this._compileProperty.result.areaHitIds.contains(service.getServiceId()) ? MyServiceInfo.DetectionResult.DETECTION : MyServiceInfo.DetectionResult.NO_DETECTION;
        } else {
            detectionResult2 = MyServiceInfo.DetectionResult.INVALID;
        }
        if (this._compileProperty.result.sasFamily.contains(service.getServiceId())) {
            detectionResult3 = this._compileProperty.result.sasHitIds.contains(service.getServiceId()) ? MyServiceInfo.DetectionResult.DETECTION : MyServiceInfo.DetectionResult.NO_DETECTION;
        } else {
            detectionResult3 = MyServiceInfo.DetectionResult.INVALID;
        }
        if (service.isMfi() || TextUtils.equals(service.getServiceId(), FeliCaParams.SERVICE_ID_SUICA)) {
            detectionResult4 = set.contains(service.getServiceId()) ? MyServiceInfo.DetectionResult.DETECTION : MyServiceInfo.DetectionResult.NO_DETECTION;
        } else {
            detectionResult4 = MyServiceInfo.DetectionResult.INVALID;
        }
        MyServiceInfo myServiceInfo = new MyServiceInfo(service, linkageWeb, MyServiceInfo.makeDetectionResult(detectionResult, detectionResult2, detectionResult3, detectionResult4), this._context.getOpenedDatabaseExpert());
        AssetInfo assetInfo = this._compileProperty.getAssetInfo(myServiceInfo.getId());
        String serviceId = service.getServiceId();
        if ("SV000013".equals(serviceId)) {
            myTrafficInfo = new MyEdyInfo(myServiceInfo, assetInfo);
        } else if ("SV000075".equals(serviceId)) {
            myTrafficInfo = new MyNanacoInfo(myServiceInfo, assetInfo);
        } else if (FeliCaParams.SERVICE_ID_SUICA.equals(serviceId)) {
            myTrafficInfo = new MySuicaInfo(myServiceInfo, assetInfo, position);
        } else if ("SV000011".equals(serviceId)) {
            myTrafficInfo = new MyWaonInfo(myServiceInfo, assetInfo);
        } else if (FeliCaParams.SERVICE_ID_DCARD.equals(serviceId)) {
            myTrafficInfo = new MyDcardInfo(myServiceInfo, assetInfo);
        } else {
            if (ServiceGroupType.resolve(service.getServiceId()) != ServiceGroupType.TRANSPORTATION) {
                return myServiceInfo;
            }
            myTrafficInfo = new MyTrafficInfo(myServiceInfo, assetInfo);
        }
        return myTrafficInfo;
    }
}
