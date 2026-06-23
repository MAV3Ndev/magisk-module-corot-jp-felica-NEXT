package com.felicanetworks.mfm.main.model.internal.main;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.felicanetworks.mfm.main.model.CentralFunc;
import com.felicanetworks.mfm.main.model.info.AssetInfo;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.Service;
import com.felicanetworks.mfm.main.model.internal.main.CentralFuncEntity;
import com.felicanetworks.mfm.main.model.internal.main.FuncUtil;
import com.felicanetworks.mfm.main.model.internal.main.MemoryClearCompileWorker;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaParams;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.model.internal.main.net.VersionUpProtocol;
import com.felicanetworks.mfm.main.model.internal.main.pkg.PackageExpert;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.service.IdPicker;
import com.felicanetworks.mfm.main.policy.service.ServiceGroupType;
import com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.memory_clear.MemoryClearFunc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes3.dex */
public class MemoryClearFuncEntity implements MemoryClearFunc {
    private final MfcExpert _mfcExpert;
    private final ModelContext _modelContext;
    private final PackageExpert _pe;
    private MfcExpert.RemainedCardInfo remainedCardInfo;
    private final Map<String, FuncUtil.AsyncRunner> _runners = new HashMap();
    private final CentralFuncEntity.CompileProperty _compileProperty = new CentralFuncEntity.CompileProperty();

    public MemoryClearFuncEntity(Context context) {
        ModelContext modelContext = new ModelContext(context);
        this._modelContext = modelContext;
        this._mfcExpert = new MfcExpert(modelContext);
        this._pe = new PackageExpert(modelContext);
    }

    @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc
    public ModelContext getModelContext() {
        return this._modelContext;
    }

    @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc
    public void mfcExpertInitialize() throws MfcException {
        this._mfcExpert.initialize();
        ModelContext.setInitializedMfcExpert(this._mfcExpert);
        this._modelContext.setNetworkExpert(new NetworkExpert(this._modelContext));
        try {
            this._modelContext.setOpenedDatabaseExpert(new DatabaseExpert(this._modelContext).open());
        } catch (DatabaseException e) {
            LogUtil.warning(e);
        }
    }

    @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc
    public void mfcExpertDeInitialize() {
        this._mfcExpert.finishFeliCaAccess();
        this._mfcExpert.deinitialize();
        ModelContext.setInitializedMfcExpert(null);
        NetworkExpert networkExpert = this._modelContext.getNetworkExpert();
        if (networkExpert != null) {
            networkExpert.disconnect();
            this._modelContext.setNetworkExpert(null);
        }
        DatabaseExpert openedDatabaseExpert = this._modelContext.getOpenedDatabaseExpert();
        if (openedDatabaseExpert != null) {
            openedDatabaseExpert.close();
            this._modelContext.setOpenedDatabaseExpert(null);
        }
    }

    @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc
    public void startUserOperation() {
        this._mfcExpert.finishFeliCaAccess();
    }

    @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc
    public void checkVersionUp(MemoryClearFunc.CheckVersionUpListener listener) {
        try {
            int i = Integer.parseInt((String) Sg.getValue(Sg.Key.NET_AIM_VERSION_DEFAULT_PARAM_FACT_COUNT));
            int i2 = Integer.parseInt((String) Sg.getValue(Sg.Key.NET_AIM_VERSION_DEFAULT_PARAM_MAX_COUNT));
            int i3 = Integer.parseInt((String) Sg.getValue(Sg.Key.NET_AIM_VERSION_DEFAULT_PARAM_FACT_INTERVAL));
            int i4 = Integer.parseInt((String) Sg.getValue(Sg.Key.NET_AIM_VERSION_DEFAULT_PARAM_INTERVAL));
            NetworkExpert networkExpert = new NetworkExpert(getModelContext());
            VersionUpProtocol versionUpProtocol = networkExpert.getVersionUpProtocol();
            listener.onCompleted(Integer.parseInt("1") == versionUpProtocol.parse(networkExpert.connect(versionUpProtocol.create(new VersionUpProtocol.Parameter(i, i2, i4, i3)))).versionupInfo);
        } catch (NetworkExpertException e) {
            LogUtil.error(e);
            listener.onError(new ModelErrorInfo(ModelErrorInfo.Type.NETWORK_FAILED, e));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00c2  */
    @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void checkIssueState(MemoryClearFunc.IssueStateListener listener) {
        ModelErrorInfo modelErrorInfo;
        CentralFunc.PrecompileListener.PrecompiledState precompiledState;
        int i = AnonymousClass5.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcExpert$MfcStatus[this._mfcExpert.confirmExistMfc(this._modelContext.getAndroidContext().getPackageManager()).ordinal()];
        ModelErrorInfo modelErrorInfo2 = null;
        if (i != 1 && i != 2) {
            if (i != 3) {
                precompiledState = null;
            } else {
                try {
                    mfcExpertInitialize();
                    mfiStartNoLogin();
                    this._mfcExpert.checkAndRecoverCrsState();
                    if (this._mfcExpert.isChipInitialized()) {
                        this._mfcExpert.mfcStart();
                        MfcExpert.FelicaSettings iDmAndICCode = this._mfcExpert.getIDmAndICCode();
                        Settings.initIdm(iDmAndICCode.idm);
                        Settings.initIcCode(iDmAndICCode.icCode);
                        precompiledState = new CentralFunc.PrecompileListener.PrecompiledState(CentralFunc.PrecompileListener.PrecompiledState.FelicaDeviceState.INITIALIZED);
                    } else {
                        precompiledState = new CentralFunc.PrecompileListener.PrecompiledState(CentralFunc.PrecompileListener.PrecompiledState.FelicaDeviceState.UNINITIALIZED);
                    }
                } catch (MfcException e) {
                    LogUtil.error(e);
                    if (e.getErrorType() == MfcException.Type.MEMORY_CLEAR_IN_PROGRESS) {
                        modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.EXT_MEMORY_CLEAR_RUNNING, new MfmException(MemoryClearFuncEntity.class, 258));
                    } else if (e.getErrorType() == MfcException.Type.UNKNOWN_CHIP_STATE) {
                        modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(MemoryClearFuncEntity.class, 259));
                    } else if (e.getErrorType() == MfcException.Type.NOT_IC_CHIP_FORMATTING) {
                        precompiledState = new CentralFunc.PrecompileListener.PrecompiledState(CentralFunc.PrecompileListener.PrecompiledState.FelicaDeviceState.UNINITIALIZED);
                    } else {
                        modelErrorInfo = convertMemoryClearError(e);
                    }
                    modelErrorInfo2 = modelErrorInfo;
                    precompiledState = null;
                }
            }
            if (modelErrorInfo2 == null) {
                this._mfcExpert.finishFeliCaAccess();
                listener.onError(modelErrorInfo2);
                return;
            } else if (precompiledState != null) {
                if (precompiledState.getFelicaDeviceState() == CentralFunc.PrecompileListener.PrecompiledState.FelicaDeviceState.UNINITIALIZED) {
                    this._mfcExpert.finishFeliCaAccess();
                }
                listener.onCompleted(precompiledState);
                return;
            } else {
                this._mfcExpert.finishFeliCaAccess();
                listener.onError(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(MemoryClearFuncEntity.class, 260)));
                return;
            }
        }
        modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.EXT_MFC_DISABLED, new MfmException(MemoryClearFuncEntity.class, 257));
        modelErrorInfo2 = modelErrorInfo;
        precompiledState = null;
        if (modelErrorInfo2 == null) {
        }
    }

    @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc
    public void getRemainedDeleteCards(MemoryClearFunc.GetRemainedCardsListener listener) {
        try {
            mfiStartNoLogin();
            MfcExpert.RemainedCardInfo remainedCards = this._mfcExpert.getRemainedCards();
            this.remainedCardInfo = remainedCards;
            listener.onCompleted(remainedCards.getDisplayDeleteCardList(), this.remainedCardInfo.getNotDisplayDeleteCardList());
        } catch (MfcException e) {
            LogUtil.error(e);
            this._mfcExpert.finishFeliCaAccess();
            listener.onError(convertMemoryClearError(e));
        }
    }

    @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc
    public void compile(boolean isCheckNow, final MemoryClearFunc.CompileListener listener) {
        final FuncUtil.AsyncRunner runner = getRunner("compile_memory_clear");
        this._compileProperty.reset();
        if (!runner.startup((Thread) new MemoryClearCompileWorker(this._modelContext, isCheckNow, new MemoryClearCompileWorker.Listener() { // from class: com.felicanetworks.mfm.main.model.internal.main.MemoryClearFuncEntity.1
            private static final int PROGRESS_DENOMINATOR = 100;
            private int _numerator = 0;

            @Override // com.felicanetworks.mfm.main.model.internal.main.MemoryClearCompileWorker.Listener
            public void progress(MemoryClearCompileWorker.Listener.Pos pos) {
                switch (AnonymousClass5.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$MemoryClearCompileWorker$Listener$Pos[pos.ordinal()]) {
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
                MemoryClearFuncEntity.this._compileProperty.notifyProgress(this._numerator, 100);
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.MemoryClearCompileWorker.Listener
            public void completed(final CentralFunc.CompiledResult result, final CentralFunc.CompiledState state) {
                MemoryClearFuncEntity.this._compileProperty.set(new CentralFunc.CompiledResult(result), new CentralFunc.CompiledState(state));
                MemoryClearFuncEntity.this._compileProperty.reset();
                FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MemoryClearFuncEntity.1.1
                    @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                    public void go() {
                        ModelErrorInfo modelErrorInfo;
                        CentralFunc.CompiledState.NetworkState networkState = MemoryClearFuncEntity.this._compileProperty.getCompiledState().getNetworkState();
                        CentralFunc.CompiledState.FelicaState felicaState = MemoryClearFuncEntity.this._compileProperty.getCompiledState().getFelicaState();
                        if (networkState == CentralFunc.CompiledState.NetworkState.CONNECT_ERROR) {
                            modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.NETWORK_FAILED, new MfmException(MemoryClearFuncEntity.class, 272));
                        } else if (felicaState == CentralFunc.CompiledState.FelicaState.LOCK_ERROR) {
                            modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.LOCKED_FELICA, new MfmException(MemoryClearFuncEntity.class, 273));
                        } else if (felicaState == CentralFunc.CompiledState.FelicaState.OPEN_ERROR) {
                            modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.FELICA_OPEN_ERROR, new MfmException(MemoryClearFuncEntity.class, 274));
                        } else if (felicaState == CentralFunc.CompiledState.FelicaState.TIMEOUT_ERROR) {
                            modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.FELICA_TIMEOUT_ERROR, new MfmException(MemoryClearFuncEntity.class, 275));
                        } else if (felicaState == CentralFunc.CompiledState.FelicaState.USED_BY_OTHER_APP) {
                            modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.USED_BY_OTHER_APP, new MfmException(MemoryClearFuncEntity.class, 276));
                        } else {
                            modelErrorInfo = felicaState == CentralFunc.CompiledState.FelicaState.RUNNING_BY_MFIC ? new ModelErrorInfo(ModelErrorInfo.Type.MFIC_RUNNING_BY_ITSELF, new MfmException(MemoryClearFuncEntity.class, 277)) : null;
                        }
                        if (modelErrorInfo != null) {
                            MemoryClearFuncEntity.this._mfcExpert.finishFeliCaAccess();
                            listener.onError(modelErrorInfo);
                        } else {
                            listener.onCompleted();
                        }
                    }
                });
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.MemoryClearCompileWorker.Listener
            public void error(final ModelErrorInfo error) {
                MemoryClearFuncEntity.this._compileProperty.reset();
                FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MemoryClearFuncEntity.1.2
                    @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                    public void go() {
                        MemoryClearFuncEntity.this._mfcExpert.finishFeliCaAccess();
                        listener.onError(error);
                    }
                });
            }
        }))) {
            throw new IllegalStateException("MemoryClearFuncEntity#compile() is still executing.");
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.main.MemoryClearFuncEntity$5, reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$MemoryClearCompileWorker$Listener$Pos;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcExpert$MfcStatus;

        static {
            int[] iArr = new int[MemoryClearCompileWorker.Listener.Pos.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$MemoryClearCompileWorker$Listener$Pos = iArr;
            try {
                iArr[MemoryClearCompileWorker.Listener.Pos.MYSERVICE_SIM_UC_END.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$MemoryClearCompileWorker$Listener$Pos[MemoryClearCompileWorker.Listener.Pos.MYSERVICE_SIM_UC_SKIP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$MemoryClearCompileWorker$Listener$Pos[MemoryClearCompileWorker.Listener.Pos.MYSERVICE_SIM_GID_END.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$MemoryClearCompileWorker$Listener$Pos[MemoryClearCompileWorker.Listener.Pos.MYSERVICE_SIM_GID_SKIP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$MemoryClearCompileWorker$Listener$Pos[MemoryClearCompileWorker.Listener.Pos.MYSERVICE_SIM_SID_CONNECTED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$MemoryClearCompileWorker$Listener$Pos[MemoryClearCompileWorker.Listener.Pos.MYSERVICE_SIM_SID_RECEIVING_1.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$MemoryClearCompileWorker$Listener$Pos[MemoryClearCompileWorker.Listener.Pos.MYSERVICE_SIM_SID_RECEIVING_2.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$MemoryClearCompileWorker$Listener$Pos[MemoryClearCompileWorker.Listener.Pos.MYSERVICE_SIM_SID_RECEIVING_3.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$MemoryClearCompileWorker$Listener$Pos[MemoryClearCompileWorker.Listener.Pos.MYSERVICE_SIM_SID_RECEIVING_4.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$MemoryClearCompileWorker$Listener$Pos[MemoryClearCompileWorker.Listener.Pos.MYSERVICE_SIM_SID_RECEIVING_5.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$MemoryClearCompileWorker$Listener$Pos[MemoryClearCompileWorker.Listener.Pos.MYSERVICE_SIM_SID_RECEIVING_6.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$MemoryClearCompileWorker$Listener$Pos[MemoryClearCompileWorker.Listener.Pos.MYSERVICE_SIM_SID_RECEIVING_7.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$MemoryClearCompileWorker$Listener$Pos[MemoryClearCompileWorker.Listener.Pos.MYSERVICE_PARALLEL_MFC_END.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$MemoryClearCompileWorker$Listener$Pos[MemoryClearCompileWorker.Listener.Pos.MYSERVICE_PARALLEL_APP_END.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$MemoryClearCompileWorker$Listener$Pos[MemoryClearCompileWorker.Listener.Pos.MYSERVICE_PARALLEL_END.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$MemoryClearCompileWorker$Listener$Pos[MemoryClearCompileWorker.Listener.Pos.MYSERVICE_END.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$MemoryClearCompileWorker$Listener$Pos[MemoryClearCompileWorker.Listener.Pos.RECOMMEND_SIM_BOOKMARK_END.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$MemoryClearCompileWorker$Listener$Pos[MemoryClearCompileWorker.Listener.Pos.RECOMMEND_END.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$MemoryClearCompileWorker$Listener$Pos[MemoryClearCompileWorker.Listener.Pos.FPSERVICE_SIM_END.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$MemoryClearCompileWorker$Listener$Pos[MemoryClearCompileWorker.Listener.Pos.FPSERVICE_END.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            int[] iArr2 = new int[MfcExpert.MfcStatus.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcExpert$MfcStatus = iArr2;
            try {
                iArr2[MfcExpert.MfcStatus.MFC_DISABLED.ordinal()] = 1;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcExpert$MfcStatus[MfcExpert.MfcStatus.MFC_UNINSTALLED.ordinal()] = 2;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcExpert$MfcStatus[MfcExpert.MfcStatus.MFC_ENABLED.ordinal()] = 3;
            } catch (NoSuchFieldError unused23) {
            }
        }
    }

    @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc
    public List<Service> getAreaDetectedServiceList() {
        CentralFunc.CompiledResult result = this._compileProperty.getResult();
        ArrayList arrayList = new ArrayList();
        for (Service service : result.master) {
            String serviceId = service.getServiceId();
            if (result.areaHitIds.contains(serviceId)) {
                if (ServiceGroupType.resolve(serviceId) != ServiceGroupType.TRANSPORTATION || serviceId.equals(FeliCaParams.SERVICE_ID_SUICA)) {
                    arrayList.add(service);
                }
            } else if (result.sasHitIds.contains(serviceId)) {
                arrayList.add(service);
            }
        }
        return arrayList;
    }

    @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc
    public List<MemoryClearFunc.MemoryClearServiceInfo> getMemoryClearServiceList() {
        Service service;
        List<Service> areaDetectedServiceList = getAreaDetectedServiceList();
        MfcExpert.RemainedCardInfo remainedCardInfo = this.remainedCardInfo;
        if (remainedCardInfo == null) {
            ArrayList arrayList = new ArrayList();
            Iterator<Service> it = areaDetectedServiceList.iterator();
            while (it.hasNext()) {
                arrayList.add(new MemoryClearFunc.MemoryClearServiceInfo(it.next()));
            }
            return arrayList;
        }
        List<MyCardInfo> displayPermanentDeleteCardList = remainedCardInfo.getDisplayPermanentDeleteCardList();
        Iterator<MyCardInfo> it2 = displayPermanentDeleteCardList.iterator();
        boolean z = false;
        while (it2.hasNext()) {
            String strSid = it2.next().sid();
            if (!ServiceIdPolicy.contains(areaDetectedServiceList, strSid) && (service = (Service) IdPicker.pickupWithSid(this._compileProperty.getResult().master, strSid, new IdPicker.ServiceIdSelector<Service>() { // from class: com.felicanetworks.mfm.main.model.internal.main.MemoryClearFuncEntity.2
                /* JADX DEBUG: Method merged with bridge method: sid(Ljava/lang/Object;)Ljava/lang/String; */
                @Override // com.felicanetworks.mfm.main.policy.service.IdPicker.ServiceIdSelector
                public String sid(Service item) {
                    return item.getServiceId();
                }
            })) != null) {
                areaDetectedServiceList.add(service);
                z = true;
            }
        }
        if (z) {
            ServiceIdPolicy.sort(areaDetectedServiceList);
        }
        ArrayList arrayList2 = new ArrayList();
        for (Service service2 : areaDetectedServiceList) {
            MemoryClearFunc.MemoryClearServiceInfo memoryClearServiceInfo = new MemoryClearFunc.MemoryClearServiceInfo(service2);
            for (MyCardInfo myCardInfo : displayPermanentDeleteCardList) {
                if (service2.getServiceId().equals(myCardInfo.getServiceId())) {
                    memoryClearServiceInfo.addCard(myCardInfo);
                }
            }
            arrayList2.add(memoryClearServiceInfo);
        }
        return arrayList2;
    }

    @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc
    public String getServiceNameFromServiceId(String serviceId) {
        try {
            for (Service service : this._modelContext.getOpenedDatabaseExpert().getService()) {
                if (service.getServiceId().equals(serviceId)) {
                    return service.getServiceName();
                }
            }
            return null;
        } catch (DatabaseException e) {
            LogUtil.warning(e);
            return null;
        }
    }

    @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc
    public void deleteCards(MemoryClearFunc.DeleteListener listener) {
        try {
            this._mfcExpert.felicaStart();
            this._mfcExpert.uploadCardsToDelete();
            listener.onCompleted();
        } catch (MfcException e) {
            LogUtil.error(e);
            this._mfcExpert.forceStop();
            this._mfcExpert.finishFeliCaAccess();
            listener.onError(convertMemoryClearError(e));
        }
    }

    @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc
    public void getAssetList(MemoryClearFunc.GetAssetListener listener) {
        try {
            List<Service> areaDetectedServiceList = getAreaDetectedServiceList();
            ArrayList arrayList = new ArrayList();
            if (((Service) IdPicker.pickupWithSid(areaDetectedServiceList, FeliCaParams.SERVICE_ID_SUICA, new IdPicker.ServiceIdSelector<Service>() { // from class: com.felicanetworks.mfm.main.model.internal.main.MemoryClearFuncEntity.3
                /* JADX DEBUG: Method merged with bridge method: sid(Ljava/lang/Object;)Ljava/lang/String; */
                @Override // com.felicanetworks.mfm.main.policy.service.IdPicker.ServiceIdSelector
                public String sid(Service item) {
                    return item.getServiceId();
                }
            })) != null) {
                arrayList.add(new MyCardInfo(FeliCaParams.SERVICE_ID_SUICA, "D00000000000000000000000000000000000000000000000000000000000001", FeliCaParams.CARD_CATEGORY_TRAFFIC, MyCardInfo.CardStatus.STATUS_ACTIVE, MyCardInfo.CardPosition.POSITION_FOREGROUND, "", null, this._modelContext.getNetworkExpert(), false));
            }
            Map<Service, List<MyCardInfo>> mapCreateAssetTargetMap = this._mfcExpert.createAssetTargetMap(areaDetectedServiceList, arrayList, true);
            this._mfcExpert.mfcStart();
            List<MfcExpert.Asset> assetList = this._mfcExpert.getAssetList(mapCreateAssetTargetMap, false);
            ArrayList arrayList2 = new ArrayList();
            for (MfcExpert.Asset asset : assetList) {
                arrayList2.add(new AssetInfo(asset.serviceId, asset.cardId, asset.balanceValue, asset.balanceLimit, asset.point1, asset.point2, asset.date1, asset.date2, asset.isDcardMini));
            }
            listener.onCompleted(arrayList2);
        } catch (MfcException e) {
            LogUtil.error(e);
            this._mfcExpert.finishFeliCaAccess();
            listener.onError(convertMemoryClearError(e));
        }
    }

    @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc
    public void clearMemory(MemoryClearFunc.ClearMemoryListener listener) {
        ModelErrorInfo modelErrorInfoConvertMemoryClearError;
        try {
            try {
                this._mfcExpert.felicaStart();
                this._mfcExpert.uploadCardsToPermanentDelete();
                try {
                    this._mfcExpert.felicaStart();
                    mfiStartNoLogin();
                    try {
                        this._mfcExpert.clearMemory();
                    } catch (MfcException e) {
                        if (e.getErrorType().equals(MfcException.Type.FELICA_NETWORK_FAILED)) {
                            SystemClock.sleep(1000L);
                            this._mfcExpert.clearMemory();
                        } else {
                            throw e;
                        }
                    }
                    this._mfcExpert.finishFeliCaAccess();
                } catch (MfcException e2) {
                    LogUtil.error(e2);
                    if (e2.getErrorType().equals(MfcException.Type.FELICA_NETWORK_FAILED)) {
                        modelErrorInfoConvertMemoryClearError = new ModelErrorInfo(ModelErrorInfo.Type.MFIC_NETWORK_RETRY_ERROR, e2);
                    } else {
                        modelErrorInfoConvertMemoryClearError = convertMemoryClearError(e2);
                    }
                    this._mfcExpert.finishFeliCaAccess();
                    if (modelErrorInfoConvertMemoryClearError != null) {
                        listener.onError(modelErrorInfoConvertMemoryClearError);
                        return;
                    }
                }
                listener.onCompleted();
            } catch (MfcException e3) {
                LogUtil.error(e3);
                this._mfcExpert.forceStop();
                this._mfcExpert.finishFeliCaAccess();
                listener.onError(convertMemoryClearError(e3));
            }
        } catch (Throwable th) {
            this._mfcExpert.finishFeliCaAccess();
            listener.onCompleted();
            throw th;
        }
    }

    private void mfiStartNoLogin() throws MfcException {
        this._mfcExpert.setSilentStartCode(0);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final FuncUtil.AsyncPacket asyncPacket = new FuncUtil.AsyncPacket();
        this._mfcExpert.mfiStartNoLogin(new MfcExpert.MfiStartListener() { // from class: com.felicanetworks.mfm.main.model.internal.main.MemoryClearFuncEntity.4
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

    private ModelErrorInfo convertMemoryClearError(MfcException exception) {
        MfcException.Type errorType = exception.getErrorType();
        if (errorType.equals(MfcException.Type.OTHER_ERROR)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, exception);
        }
        if (errorType.equals(MfcException.Type.FELICA_TIMEOUT_ERROR)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.FELICA_TIMEOUT_ERROR, exception);
        }
        if (errorType.equals(MfcException.Type.MFC_OTHER_ERROR)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.MFC_OTHER_ERROR, exception);
        }
        if (errorType.equals(MfcException.Type.LOCKED_FELICA)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.LOCKED_FELICA, exception);
        }
        if (errorType.equals(MfcException.Type.FELICA_OPEN_ERROR)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.FELICA_OPEN_ERROR, exception);
        }
        if (errorType.equals(MfcException.Type.FELICA_INVALID_RESPONSE_ERROR)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.FELICA_INVALID_RESPONSE_ERROR, exception);
        }
        if (errorType.equals(MfcException.Type.MFC_USED_BY_OTHER_APP)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.USED_BY_OTHER_APP, exception);
        }
        if (errorType.equals(MfcException.Type.MFIC_RUNNING_BY_ITSELF)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.MFIC_RUNNING_BY_ITSELF, exception);
        }
        if (errorType.equals(MfcException.Type.MFC_INVALID_PERMISSION)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.PERM_INSPECT, exception);
        }
        if (errorType.equals(MfcException.Type.MFC_READ_FAILED)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.MFC_OTHER_ERROR, exception);
        }
        if (errorType.equals(MfcException.Type.NFC_TRANSCEIVE_IO_ERROR)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.NFC_IO_ERROR, exception);
        }
        if (errorType.equals(MfcException.Type.NFC_ILLEGALSTATE_ERROR)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.NFC_ILLEGALSTATE_ERROR, exception);
        }
        if (errorType.equals(MfcException.Type.MFI_OPSRV_ACCOUNT_ERROR)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.MFIC_LOGIN_ERROR, exception);
        }
        if (errorType.equals(MfcException.Type.FELICA_HTTP_ERROR)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.FELICA_HTTP_ERROR, exception);
        }
        if (errorType.equals(MfcException.Type.MFI_OPSRV_REQUIRED_LIB_UNAVAILABLE)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.MFIC_LIB_UNAVAILABLE, exception);
        }
        if (errorType.equals(MfcException.Type.MFIC_VERSION_ERROR)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.MFIC_VERSION_ERROR, exception);
        }
        if (errorType.equals(MfcException.Type.MFIC_JSON_ERROR)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.MFIC_JSON_ERROR_NO_CASHE, exception);
        }
        if (errorType.equals(MfcException.Type.MFIC_NO_ACCOUNT_INFO)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, exception);
        }
        if (errorType.equals(MfcException.Type.INVALID_REQUEST_TOKEN)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.MFIC_INVALID_REQUEST_TOKEN_ERROR, exception);
        }
        if (errorType.equals(MfcException.Type.UNSUPPORTED_CHIP_ERROR)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, exception);
        }
        if (errorType.equals(MfcException.Type.MFI_SERVER_MAINTENANCE_ERROR)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.MFIC_SERVER_MAINTENANCE_ERROR, exception);
        }
        if (errorType.equals(MfcException.Type.FELICA_NETWORK_FAILED)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.MFIC_NETWORK_ERROR, exception);
        }
        if (errorType.equals(MfcException.Type.UNSUPPORTED_DEVICE_ERROR)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.MFIC_UNSUPPORTED_DEVICE_ERROR, exception);
        }
        if (errorType.equals(MfcException.Type.MFI_ISSUE_LIMIT_EXCEEDED)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.MFIC_ISSUE_LIMIT_EXCEEDED, exception);
        }
        if (errorType.equals(MfcException.Type.MFI_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.MFIC_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED, exception);
        }
        if (errorType.equals(MfcException.Type.MFI_ISSUE_LIMIT_PER_DEVICE_EXCEEDED)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.MFIC_ISSUE_LIMIT_PER_DEVICE_EXCEEDED, exception);
        }
        if (errorType.equals(MfcException.Type.MFI_INSUFFICIENT_CHIP_SPACE)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.MFIC_INSUFFICIENT_CHIP_SPACE, exception);
        }
        if (errorType.equals(MfcException.Type.MFI_OTHER_SP_CARD_EXIST)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.MFIC_OTHER_SP_CARD_EXIST, exception);
        }
        if (errorType.equals(MfcException.Type.MFI_INSTANCE_NOT_VACANT)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.MFIC_INSTANCE_NOT_VACANT, exception);
        }
        if (errorType.equals(MfcException.Type.TYPE_EXIST_UNKNOWN_CARD)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.MFIC_TYPE_EXIST_UNKNOWN_CARD, exception);
        }
        if (errorType.equals(MfcException.Type.MFI_INSUFFICIENT_ALLOCATED_FREE_SPACE)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.MFIC_INSUFFICIENT_ALLOCATED_FREE_SPACE, exception);
        }
        if (errorType.equals(MfcException.Type.MFC_WARNING)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.MFC_OTHER_ERROR, exception);
        }
        if (errorType.equals(MfcException.Type.INSIDE_TRANSIT_GATE_ERROR)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.MFIC_INSIDE_TRANSIT_GATE_ERROR, exception);
        }
        if (errorType.equals(MfcException.Type.MFC_CANNOT_MEMORY_CLEAR_BY_NOT_INITIALIZE)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.MFC_CANNOT_MEMORY_CLEAR_BY_NOT_INITIALIZE, exception);
        }
        if (errorType.equals(MfcException.Type.PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_SERVICE)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_SERVICE, exception);
        }
        if (errorType.equals(MfcException.Type.PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_STORE)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_STORE, exception);
        }
        if (errorType.equals(MfcException.Type.PIA_PLAY_INTEGRITY_RECOVERABLE_FAILED)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.PIA_PLAY_INTEGRITY_RECOVERABLE_FAILED, exception);
        }
        if (errorType.equals(MfcException.Type.PIA_PLAY_INTEGRITY_NONRECOVERABLE_FAILED)) {
            return new ModelErrorInfo(ModelErrorInfo.Type.PIA_PLAY_INTEGRITY_NONRECOVERABLE_FAILED, exception);
        }
        return new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, exception);
    }

    private FuncUtil.AsyncRunner getRunner(String methodName) {
        FuncUtil.AsyncRunner asyncRunnerRemove;
        if (this._runners.containsKey(methodName) && (asyncRunnerRemove = this._runners.remove(methodName)) != null) {
            asyncRunnerRemove.shutdown();
        }
        FuncUtil.AsyncRunner asyncRunner = new FuncUtil.AsyncRunner();
        this._runners.put(methodName, asyncRunner);
        return asyncRunner;
    }
}
