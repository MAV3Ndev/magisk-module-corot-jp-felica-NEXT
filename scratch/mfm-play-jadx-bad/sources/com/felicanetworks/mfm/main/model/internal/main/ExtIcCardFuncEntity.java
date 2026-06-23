package com.felicanetworks.mfm.main.model.internal.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import com.felicanetworks.mfm.main.model.ExtIcCardFunc;
import com.felicanetworks.mfm.main.model.FelicaPocketFunc;
import com.felicanetworks.mfm.main.model.info.ExtIcCardInfo;
import com.felicanetworks.mfm.main.model.info.FpAreaInfo;
import com.felicanetworks.mfm.main.model.info.FpServiceInfo;
import com.felicanetworks.mfm.main.model.info.Linkage;
import com.felicanetworks.mfm.main.model.info.LinkageApp;
import com.felicanetworks.mfm.main.model.info.LinkageWeb;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.Service;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import com.felicanetworks.mfm.main.model.info.specific.ExtEdyInfo;
import com.felicanetworks.mfm.main.model.info.specific.ExtNanacoInfo;
import com.felicanetworks.mfm.main.model.info.specific.ExtSuicaInfo;
import com.felicanetworks.mfm.main.model.info.specific.ExtWaonInfo;
import com.felicanetworks.mfm.main.model.internal.main.FuncUtil;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.model.internal.main.mfc.ExtEdyBalanceReader;
import com.felicanetworks.mfm.main.model.internal.main.mfc.ExtFelicaReader;
import com.felicanetworks.mfm.main.model.internal.main.mfc.ExtMobileSuicaBalanceReader;
import com.felicanetworks.mfm.main.model.internal.main.mfc.ExtNanacoBalanceReader;
import com.felicanetworks.mfm.main.model.internal.main.mfc.ExtWaonBalanceReader;
import com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaParams;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcAdapterExpert;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.ImageProtocol;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.model.internal.main.net.OsaifulifePlusImageProtocol;
import com.felicanetworks.mfm.main.model.internal.main.pkg.PackageExpert;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class ExtIcCardFuncEntity implements ExtIcCardFunc {
    private static final String SERVICE_KIND_APP = "2";
    private FelicaPocketFunc.CompiledFpState _compiledFpState;
    private ModelContext _context;
    private MfcAdapterExpert _expert;
    private NetworkExpert _neForOrderPromotionImage;
    private FuncUtil.AsyncRunner _runner = new FuncUtil.AsyncRunner();
    private FpAreaInfo _fpAreaInfo = null;

    ExtIcCardFuncEntity(ModelContext modelContext) {
        this._context = modelContext;
        FpServiceInfo.clearCache();
    }

    @Override // com.felicanetworks.mfm.main.model.ExtIcCardFunc
    public void orderInfo(final Tag tag, final ExtIcCardFunc.ExtIcCardListener listener) {
        this._fpAreaInfo = null;
        FelicaPocketFunc.CompiledFpState compiledFpState = this._compiledFpState;
        if (compiledFpState != null) {
            compiledFpState.clearState();
        }
        if (!this._runner.startup(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.ExtIcCardFuncEntity.1
            /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [361=5] */
            /* JADX DEBUG: Failed to insert an additional move for type inference into block B:111:0x0232 */
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v35, types: [com.felicanetworks.mfm.main.model.internal.main.FuncUtil$AsyncRunner] */
            /* JADX WARN: Type inference failed for: r5v10 */
            /* JADX WARN: Type inference failed for: r5v14 */
            /* JADX WARN: Type inference failed for: r5v5, types: [com.felicanetworks.mfm.main.model.internal.main.mfc.MfcAdapterExpert] */
            /* JADX WARN: Type inference failed for: r5v6, types: [java.lang.Object] */
            @Override // java.lang.Runnable
            public void run() {
                FuncUtil.AsyncRunner asyncRunner;
                FuncUtil.Notify notify;
                MfcAdapterExpert.Asset balance;
                Object extWaonInfo;
                FuncUtil.AsyncRunner asyncRunner2;
                FuncUtil.Notify notify2;
                try {
                    try {
                        try {
                            ExtIcCardFuncEntity.this._expert = new MfcAdapterExpert(tag);
                            ExtIcCardFuncEntity.this._runner.checkInterrupted();
                        } catch (MfcException e) {
                            LogUtil.warning(e);
                            int i = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[e.getErrorType().ordinal()];
                            ExtIcCardFuncEntity.this._runner.putFailure(new ModelErrorInfo(i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? ModelErrorInfo.Type.OTHER_ERROR : ModelErrorInfo.Type.NFC_ILLEGALSTATE_ERROR : ModelErrorInfo.Type.NFC_IO_ERROR : ModelErrorInfo.Type.FELICA_OPEN_ERROR : ModelErrorInfo.Type.MFC_OTHER_ERROR : ModelErrorInfo.Type.FELICA_TIMEOUT_ERROR, e));
                            asyncRunner = ExtIcCardFuncEntity.this._runner;
                            notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.ExtIcCardFuncEntity.1.1
                                @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                public void go() {
                                    if (!ExtIcCardFuncEntity.this._runner.hasFailure()) {
                                        if (ExtIcCardFuncEntity.this._fpAreaInfo == null) {
                                            ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(FelicaPocketFunc.CompiledFpState.FelicaFpState.UNSUPPORTED);
                                        } else {
                                            ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(FelicaPocketFunc.CompiledFpState.FelicaFpState.NO_PROBLEM);
                                        }
                                        listener.onSuccess((ExtIcCardInfo) ExtIcCardFuncEntity.this._runner.getSuccess());
                                        return;
                                    }
                                    ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(((ModelErrorInfo) ExtIcCardFuncEntity.this._runner.getFailure()).getException());
                                    listener.onFailure((ModelErrorInfo) ExtIcCardFuncEntity.this._runner.getFailure());
                                }
                            };
                        } catch (NullPointerException unused) {
                            asyncRunner = ExtIcCardFuncEntity.this._runner;
                            notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.ExtIcCardFuncEntity.1.1
                                @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                public void go() {
                                    if (!ExtIcCardFuncEntity.this._runner.hasFailure()) {
                                        if (ExtIcCardFuncEntity.this._fpAreaInfo == null) {
                                            ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(FelicaPocketFunc.CompiledFpState.FelicaFpState.UNSUPPORTED);
                                        } else {
                                            ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(FelicaPocketFunc.CompiledFpState.FelicaFpState.NO_PROBLEM);
                                        }
                                        listener.onSuccess((ExtIcCardInfo) ExtIcCardFuncEntity.this._runner.getSuccess());
                                        return;
                                    }
                                    ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(((ModelErrorInfo) ExtIcCardFuncEntity.this._runner.getFailure()).getException());
                                    listener.onFailure((ModelErrorInfo) ExtIcCardFuncEntity.this._runner.getFailure());
                                }
                            };
                        }
                    } catch (InterruptedException unused2) {
                        asyncRunner = ExtIcCardFuncEntity.this._runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.ExtIcCardFuncEntity.1.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (!ExtIcCardFuncEntity.this._runner.hasFailure()) {
                                    if (ExtIcCardFuncEntity.this._fpAreaInfo == null) {
                                        ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(FelicaPocketFunc.CompiledFpState.FelicaFpState.UNSUPPORTED);
                                    } else {
                                        ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(FelicaPocketFunc.CompiledFpState.FelicaFpState.NO_PROBLEM);
                                    }
                                    listener.onSuccess((ExtIcCardInfo) ExtIcCardFuncEntity.this._runner.getSuccess());
                                    return;
                                }
                                ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(((ModelErrorInfo) ExtIcCardFuncEntity.this._runner.getFailure()).getException());
                                listener.onFailure((ModelErrorInfo) ExtIcCardFuncEntity.this._runner.getFailure());
                            }
                        };
                    } catch (Exception e2) {
                        LogUtil.warning(e2);
                        ExtIcCardFuncEntity.this._runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_RUNTIME_ERROR, new MfmException(ExtIcCardFuncEntity.class, 259)));
                        asyncRunner = ExtIcCardFuncEntity.this._runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.ExtIcCardFuncEntity.1.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (!ExtIcCardFuncEntity.this._runner.hasFailure()) {
                                    if (ExtIcCardFuncEntity.this._fpAreaInfo == null) {
                                        ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(FelicaPocketFunc.CompiledFpState.FelicaFpState.UNSUPPORTED);
                                    } else {
                                        ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(FelicaPocketFunc.CompiledFpState.FelicaFpState.NO_PROBLEM);
                                    }
                                    listener.onSuccess((ExtIcCardInfo) ExtIcCardFuncEntity.this._runner.getSuccess());
                                    return;
                                }
                                ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(((ModelErrorInfo) ExtIcCardFuncEntity.this._runner.getFailure()).getException());
                                listener.onFailure((ModelErrorInfo) ExtIcCardFuncEntity.this._runner.getFailure());
                            }
                        };
                    }
                    if (Arrays.asList(tag.getTechList()).contains("android.nfc.tech.NfcF")) {
                        ?? r5 = 0;
                        r5 = 0;
                        try {
                            MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
                            if (initializedMfcExpert != null) {
                                initializedMfcExpert.cancelBackgroundUpdate();
                            }
                            ExtIcCardFuncEntity.this._expert.open();
                            if (ExtIcCardFuncEntity.this._expert.canGetSystemCodeList()) {
                                int[] systemCodeList = ExtIcCardFuncEntity.this._expert.getSystemCodeList();
                                ArrayList<ExtFelicaReader> arrayList = new ArrayList();
                                for (int i2 : systemCodeList) {
                                    if (i2 == 3) {
                                        arrayList.add(new ExtMobileSuicaBalanceReader());
                                    } else if (i2 == 65024) {
                                        arrayList.add(new ExtEdyBalanceReader());
                                        arrayList.add(new ExtWaonBalanceReader());
                                        arrayList.add(new ExtNanacoBalanceReader());
                                    }
                                }
                                for (ExtFelicaReader extFelicaReader : arrayList) {
                                    ExtIcCardFuncEntity.this._runner.checkInterrupted();
                                    try {
                                        balance = extFelicaReader.getBalance(ExtIcCardFuncEntity.this._expert.read(extFelicaReader.systemCode, extFelicaReader.blocklist));
                                        break;
                                    } catch (MfcException e3) {
                                        if (MfcException.Type.MFC_READ_FAILED != e3.getErrorType()) {
                                            throw e3;
                                        }
                                    }
                                }
                            }
                            balance = null;
                            if (balance != null) {
                                DatabaseExpert openedDatabaseExpert = ExtIcCardFuncEntity.this._context.getOpenedDatabaseExpert();
                                List<Service> service = openedDatabaseExpert.getService();
                                boolean z = ExtIcCardFuncEntity.this._fpAreaInfo != null;
                                Iterator<Service> it = service.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        break;
                                    }
                                    Service next = it.next();
                                    if (balance.serviceId.equals(next.getServiceId())) {
                                        if (FeliCaParams.SERVICE_ID_SUICA.equals(balance.serviceId)) {
                                            extWaonInfo = new ExtSuicaInfo(next.getServiceId(), next.getVersion(), next.getServiceName(), next.getProvider(), ExtIcCardFuncEntity.this.createLinkage(next), balance.balanceValue, openedDatabaseExpert, z);
                                        } else if ("SV000013".equals(balance.serviceId)) {
                                            extWaonInfo = new ExtEdyInfo(next.getServiceId(), next.getVersion(), next.getServiceName(), next.getProvider(), ExtIcCardFuncEntity.this.createLinkage(next), balance.balanceValue, openedDatabaseExpert, z);
                                        } else if ("SV000075".equals(balance.serviceId)) {
                                            extWaonInfo = new ExtNanacoInfo(next.getServiceId(), next.getVersion(), next.getServiceName(), next.getProvider(), ExtIcCardFuncEntity.this.createLinkage(next), balance.balanceValue, Arrays.asList(new ServiceInfo.Point.PointData(balance.point1, balance.date1), new ServiceInfo.Point.PointData(balance.point2, balance.date2)), openedDatabaseExpert, z);
                                        } else if ("SV000011".equals(balance.serviceId)) {
                                            extWaonInfo = new ExtWaonInfo(next.getServiceId(), next.getVersion(), next.getServiceName(), next.getProvider(), ExtIcCardFuncEntity.this.createLinkage(next), balance.balanceValue, Collections.singletonList(new ServiceInfo.Point.PointData(balance.point1, null)), openedDatabaseExpert, z);
                                        }
                                        r5 = extWaonInfo;
                                    }
                                }
                                if (r5 != 0) {
                                    ExtIcCardFuncEntity.this._runner.putSuccess(r5);
                                } else {
                                    ExtIcCardFuncEntity.this._runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.NO_CONTENT_DATA, new MfmException(ExtIcCardFuncEntity.class, 257)));
                                }
                                asyncRunner = ExtIcCardFuncEntity.this._runner;
                                notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.ExtIcCardFuncEntity.1.1
                                    @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                    public void go() {
                                        if (!ExtIcCardFuncEntity.this._runner.hasFailure()) {
                                            if (ExtIcCardFuncEntity.this._fpAreaInfo == null) {
                                                ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(FelicaPocketFunc.CompiledFpState.FelicaFpState.UNSUPPORTED);
                                            } else {
                                                ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(FelicaPocketFunc.CompiledFpState.FelicaFpState.NO_PROBLEM);
                                            }
                                            listener.onSuccess((ExtIcCardInfo) ExtIcCardFuncEntity.this._runner.getSuccess());
                                            return;
                                        }
                                        ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(((ModelErrorInfo) ExtIcCardFuncEntity.this._runner.getFailure()).getException());
                                        listener.onFailure((ModelErrorInfo) ExtIcCardFuncEntity.this._runner.getFailure());
                                    }
                                };
                                FuncUtil.notifySafety(asyncRunner, notify);
                                return;
                            }
                            ExtIcCardFuncEntity.this._runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.NONSUPPORT_ERROR, new MfmException(ExtIcCardFuncEntity.class, 258)));
                            asyncRunner2 = ExtIcCardFuncEntity.this._runner;
                            notify2 = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.ExtIcCardFuncEntity.1.1
                                @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                public void go() {
                                    if (!ExtIcCardFuncEntity.this._runner.hasFailure()) {
                                        if (ExtIcCardFuncEntity.this._fpAreaInfo == null) {
                                            ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(FelicaPocketFunc.CompiledFpState.FelicaFpState.UNSUPPORTED);
                                        } else {
                                            ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(FelicaPocketFunc.CompiledFpState.FelicaFpState.NO_PROBLEM);
                                        }
                                        listener.onSuccess((ExtIcCardInfo) ExtIcCardFuncEntity.this._runner.getSuccess());
                                        return;
                                    }
                                    ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(((ModelErrorInfo) ExtIcCardFuncEntity.this._runner.getFailure()).getException());
                                    listener.onFailure((ModelErrorInfo) ExtIcCardFuncEntity.this._runner.getFailure());
                                }
                            };
                        } finally {
                            ExtIcCardFuncEntity.this._expert.close();
                            ExtIcCardFuncEntity.this._expert = null;
                        }
                    } else {
                        ExtIcCardFuncEntity.this._runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.NONSUPPORT_ERROR, new MfmException(ExtIcCardFuncEntity.class, 274)));
                        asyncRunner2 = ExtIcCardFuncEntity.this._runner;
                        notify2 = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.ExtIcCardFuncEntity.1.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (!ExtIcCardFuncEntity.this._runner.hasFailure()) {
                                    if (ExtIcCardFuncEntity.this._fpAreaInfo == null) {
                                        ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(FelicaPocketFunc.CompiledFpState.FelicaFpState.UNSUPPORTED);
                                    } else {
                                        ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(FelicaPocketFunc.CompiledFpState.FelicaFpState.NO_PROBLEM);
                                    }
                                    listener.onSuccess((ExtIcCardInfo) ExtIcCardFuncEntity.this._runner.getSuccess());
                                    return;
                                }
                                ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(((ModelErrorInfo) ExtIcCardFuncEntity.this._runner.getFailure()).getException());
                                listener.onFailure((ModelErrorInfo) ExtIcCardFuncEntity.this._runner.getFailure());
                            }
                        };
                    }
                    FuncUtil.notifySafety(asyncRunner2, notify2);
                } catch (Throwable th) {
                    FuncUtil.notifySafety(ExtIcCardFuncEntity.this._runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.ExtIcCardFuncEntity.1.1
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (!ExtIcCardFuncEntity.this._runner.hasFailure()) {
                                if (ExtIcCardFuncEntity.this._fpAreaInfo == null) {
                                    ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(FelicaPocketFunc.CompiledFpState.FelicaFpState.UNSUPPORTED);
                                } else {
                                    ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(FelicaPocketFunc.CompiledFpState.FelicaFpState.NO_PROBLEM);
                                }
                                listener.onSuccess((ExtIcCardInfo) ExtIcCardFuncEntity.this._runner.getSuccess());
                                return;
                            }
                            ExtIcCardFuncEntity.this._compiledFpState = new FelicaPocketFunc.CompiledFpState(((ModelErrorInfo) ExtIcCardFuncEntity.this._runner.getFailure()).getException());
                            listener.onFailure((ModelErrorInfo) ExtIcCardFuncEntity.this._runner.getFailure());
                        }
                    });
                    throw th;
                }
            }
        })) {
            throw new IllegalStateException("ExtIcCardFunc#orderInfo() is still executing.");
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.main.ExtIcCardFuncEntity$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type;

        static {
            int[] iArr = new int[MfcException.Type.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type = iArr;
            try {
                iArr[MfcException.Type.FELICA_TIMEOUT_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFC_OTHER_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.FELICA_OPEN_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.NFC_TRANSCEIVE_IO_ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.NFC_ILLEGALSTATE_ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.model.ExtIcCardFunc
    public boolean isExistTargetCards() {
        return !this._context.getOpenedDatabaseExpert().isExists(Arrays.asList("SV000013", "SV000075", FeliCaParams.SERVICE_ID_SUICA, "SV000011")).contains(false);
    }

    @Override // com.felicanetworks.mfm.main.model.ExtIcCardFunc, com.felicanetworks.mfm.main.model.FelicaPocketFunc
    public void cancel() {
        this._runner.shutdown();
        MfcAdapterExpert mfcAdapterExpert = this._expert;
        if (mfcAdapterExpert != null) {
            mfcAdapterExpert.close();
            this._expert = null;
        }
    }

    @Override // com.felicanetworks.mfm.main.model.FelicaPocketFunc
    public FpAreaInfo getFpAreaInfo() {
        return this._fpAreaInfo;
    }

    @Override // com.felicanetworks.mfm.main.model.FelicaPocketFunc
    public FelicaPocketFunc.CompiledFpState getCompiledFpState() {
        return this._compiledFpState;
    }

    @Override // com.felicanetworks.mfm.main.model.ExtIcCardFunc
    public void orderPromotionImage(final String url, final ExtIcCardFunc.OrderPromotionImageListener listener) {
        new FuncUtil.AsyncRunner().startupOrThrow(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.ExtIcCardFuncEntity$$ExternalSyntheticLambda0
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m409x6e1ffb62(url, listener);
            }
        });
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [476=5] */
    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    /* JADX INFO: renamed from: lambda$orderPromotionImage$0$com-felicanetworks-mfm-main-model-internal-main-ExtIcCardFuncEntity, reason: not valid java name */
    /* synthetic */ void m409x6e1ffb62(String str, ExtIcCardFunc.OrderPromotionImageListener orderPromotionImageListener) {
        NetworkExpert networkExpert;
        int iIntValue = ((Integer) Sg.getValue(Sg.Key.NET_OSAIFULIFE_PLUS_IMAGE_SESSION_TIMEOUT)).intValue();
        int iIntValue2 = ((Integer) Sg.getValue(Sg.Key.NET_OSAIFULIFE_PLUS_IMAGE_RECEIVING_TIMEOUT)).intValue();
        Bitmap bitmap = null;
        try {
            synchronized (this) {
                networkExpert = new NetworkExpert(this._context);
                this._neForOrderPromotionImage = networkExpert;
            }
            OsaifulifePlusImageProtocol osaifulifePlusImageProtocol = networkExpert.getOsaifulifePlusImageProtocol();
            ImageProtocol.Result result = osaifulifePlusImageProtocol.parse(this._neForOrderPromotionImage.connect(osaifulifePlusImageProtocol.create(new OsaifulifePlusImageProtocol.Parameter(str, iIntValue, iIntValue2))));
            Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(result.bytes, 0, result.bytes.length);
            synchronized (this) {
                this._neForOrderPromotionImage = null;
            }
            bitmap = bitmapDecodeByteArray;
        } catch (NetworkExpertException unused) {
            synchronized (this) {
                this._neForOrderPromotionImage = null;
            }
        } catch (Throwable th) {
            synchronized (this) {
                this._neForOrderPromotionImage = null;
                throw th;
            }
        }
        orderPromotionImageListener.onSuccess(bitmap);
    }

    @Override // com.felicanetworks.mfm.main.model.ExtIcCardFunc
    public void cancelPromotionImage() {
        new FuncUtil.AsyncRunner().startup(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.ExtIcCardFuncEntity$$ExternalSyntheticLambda1
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m408x254c4da9();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$cancelPromotionImage$1$com-felicanetworks-mfm-main-model-internal-main-ExtIcCardFuncEntity, reason: not valid java name */
    /* synthetic */ void m408x254c4da9() {
        synchronized (this) {
            NetworkExpert networkExpert = this._neForOrderPromotionImage;
            if (networkExpert != null) {
                networkExpert.disconnect();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Linkage createLinkage(Service service) {
        if (service.getLinkType().equals("2")) {
            return new LinkageApp(service.getPkg(), service.getSigHash(), service.getDownloadType(), service.getDownloadUrl(), new PackageExpert(this._context));
        }
        return new LinkageWeb(service.getWebUrl());
    }
}
