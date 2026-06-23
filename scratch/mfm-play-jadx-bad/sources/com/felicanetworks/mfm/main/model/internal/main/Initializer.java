package com.felicanetworks.mfm.main.model.internal.main;

import android.content.Context;
import com.felicanetworks.mfm.main.model.CardDetailFunc;
import com.felicanetworks.mfm.main.model.CentralFunc;
import com.felicanetworks.mfm.main.model.DeleteCardListFunc;
import com.felicanetworks.mfm.main.model.ExtIcCardFunc;
import com.felicanetworks.mfm.main.model.MemoryUsageFunc;
import com.felicanetworks.mfm.main.model.MfiCardFunc;
import com.felicanetworks.mfm.main.model.ModelGateway;
import com.felicanetworks.mfm.main.model.NoticeFunc;
import com.felicanetworks.mfm.main.model.PushGateway;
import com.felicanetworks.mfm.main.model.TermsOfServiceFunc;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DateFormatter;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDataManager;
import com.felicanetworks.mfm.main.model.internal.main.FuncUtil;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.model.internal.main.net.TosVersionProtocol;
import com.felicanetworks.mfm.main.model.internal.main.net.VersionUpProtocol;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.err.ErrorManager;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.Calendar;

/* JADX INFO: loaded from: classes3.dex */
public class Initializer {
    private Context _context;
    private ModelContext _modelContext;
    private FuncUtil.AsyncRunner _runner = new FuncUtil.AsyncRunner();
    private FunctionProviderCage _cage = new FunctionProviderCage();
    private TosVersionProtocol.Result _tosVersionProtocolResult = null;

    public Initializer(Context context) {
        this._context = context;
    }

    public void initialize(final ModelGateway.InitializeListener listener) {
        if (!this._runner.startup(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.Initializer.1
            /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [139=4] */
            @Override // java.lang.Runnable
            public void run() {
                FuncUtil.AsyncRunner asyncRunner;
                FuncUtil.Notify notify;
                try {
                    try {
                        Initializer.this._runner.checkInterrupted();
                        Initializer.this._modelContext = new ModelContext(Initializer.this._context);
                        Initializer.this._modelContext.setMainMoelContext(Initializer.this._modelContext);
                        NetworkExpert networkExpert = new NetworkExpert(Initializer.this._modelContext);
                        Initializer.this._modelContext.setNetworkExpert(networkExpert);
                        ErrorManager.reportFatalErrorLogs(networkExpert);
                        Initializer.this._runner.checkInterrupted();
                        MfcExpert mfcExpert = new MfcExpert(Initializer.this._modelContext);
                        mfcExpert.initialize();
                        ModelContext.setInitializedMfcExpert(mfcExpert);
                        Initializer.this._runner.checkInterrupted();
                        DatabaseExpert databaseExpert = new DatabaseExpert(Initializer.this._modelContext);
                        Initializer.this._modelContext.setOpenedDatabaseExpert(databaseExpert.open());
                        Initializer.this.checkVersionUp(networkExpert, databaseExpert);
                        if (!Initializer.this._runner.hasFailure()) {
                            Initializer.this._runner.checkInterrupted();
                            Initializer.this.checkTosVersionUp(networkExpert, databaseExpert);
                        }
                    } catch (DatabaseException e) {
                        Initializer.this._runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.DB_ACCESS_ERROR, e));
                        asyncRunner = Initializer.this._runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.Initializer.1.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (Initializer.this._runner.hasFailure()) {
                                    listener.onFailure((ModelErrorInfo) Initializer.this._runner.getFailure());
                                    return;
                                }
                                try {
                                    Initializer.this._cage.create(Initializer.this._modelContext);
                                    if (Initializer.this._tosVersionProtocolResult != null) {
                                        Initializer.this._cage.termsOfServiceFunc.setTosVersionProtocolResult(Initializer.this._tosVersionProtocolResult);
                                        Initializer.this._tosVersionProtocolResult = null;
                                    }
                                    listener.onComplete(Initializer.this._cage, (ModelGateway.InitializedState) Initializer.this._runner.getSuccess());
                                } catch (Exception unused) {
                                }
                            }
                        };
                        FuncUtil.notifySafety(asyncRunner, notify);
                    } catch (InterruptedException unused) {
                        asyncRunner = Initializer.this._runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.Initializer.1.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (Initializer.this._runner.hasFailure()) {
                                    listener.onFailure((ModelErrorInfo) Initializer.this._runner.getFailure());
                                    return;
                                }
                                try {
                                    Initializer.this._cage.create(Initializer.this._modelContext);
                                    if (Initializer.this._tosVersionProtocolResult != null) {
                                        Initializer.this._cage.termsOfServiceFunc.setTosVersionProtocolResult(Initializer.this._tosVersionProtocolResult);
                                        Initializer.this._tosVersionProtocolResult = null;
                                    }
                                    listener.onComplete(Initializer.this._cage, (ModelGateway.InitializedState) Initializer.this._runner.getSuccess());
                                } catch (Exception unused2) {
                                }
                            }
                        };
                        FuncUtil.notifySafety(asyncRunner, notify);
                    } catch (Exception e2) {
                        Initializer.this._runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(Initializer.class, 1, e2)));
                        asyncRunner = Initializer.this._runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.Initializer.1.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (Initializer.this._runner.hasFailure()) {
                                    listener.onFailure((ModelErrorInfo) Initializer.this._runner.getFailure());
                                    return;
                                }
                                try {
                                    Initializer.this._cage.create(Initializer.this._modelContext);
                                    if (Initializer.this._tosVersionProtocolResult != null) {
                                        Initializer.this._cage.termsOfServiceFunc.setTosVersionProtocolResult(Initializer.this._tosVersionProtocolResult);
                                        Initializer.this._tosVersionProtocolResult = null;
                                    }
                                    listener.onComplete(Initializer.this._cage, (ModelGateway.InitializedState) Initializer.this._runner.getSuccess());
                                } catch (Exception unused2) {
                                }
                            }
                        };
                        FuncUtil.notifySafety(asyncRunner, notify);
                    }
                } finally {
                    FuncUtil.notifySafety(Initializer.this._runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.Initializer.1.1
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (Initializer.this._runner.hasFailure()) {
                                listener.onFailure((ModelErrorInfo) Initializer.this._runner.getFailure());
                                return;
                            }
                            try {
                                Initializer.this._cage.create(Initializer.this._modelContext);
                                if (Initializer.this._tosVersionProtocolResult != null) {
                                    Initializer.this._cage.termsOfServiceFunc.setTosVersionProtocolResult(Initializer.this._tosVersionProtocolResult);
                                    Initializer.this._tosVersionProtocolResult = null;
                                }
                                listener.onComplete(Initializer.this._cage, (ModelGateway.InitializedState) Initializer.this._runner.getSuccess());
                            } catch (Exception unused2) {
                            }
                        }
                    });
                }
            }
        })) {
            throw new IllegalStateException("ModelGateway#initialize() is still executing.");
        }
    }

    public void deinitialize() {
        this._runner.shutdown();
        this._cage.allCancel();
        this._cage.delete();
        synchronized (this) {
            if (this._modelContext != null) {
                MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
                if (initializedMfcExpert != null) {
                    initializedMfcExpert.finishFeliCaAccess();
                    initializedMfcExpert.deinitialize();
                    ModelContext.setInitializedMfcExpert(null);
                }
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
                this._modelContext = null;
                NoticeDataManager.getInstance(this._context).closeDatabase();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkVersionUp(NetworkExpert network, DatabaseExpert database) {
        ModelGateway.InitializedState.UpgradeType upgradeType;
        VuParams vuParams = new VuParams();
        try {
            DatabaseExpert.UpgradeSettings upgradeSettings = database.getUpgradeSettings();
            int i = upgradeSettings.chkCnt;
            upgradeSettings.chkCnt = i + 1;
            vuParams.factCount = i;
            vuParams.maxCount = upgradeSettings.maxCnt;
            database.setUpgradeSettings(upgradeSettings);
            if (!vuParams.setup(upgradeSettings)) {
                this._runner.putSuccess(new ModelGateway.InitializedState(ModelGateway.InitializedState.UpgradeType.NONE));
                return;
            }
            VersionUpProtocol versionUpProtocol = network.getVersionUpProtocol();
            NetworkExpert.Request requestCreate = versionUpProtocol.create(new VersionUpProtocol.Parameter(vuParams.factCount, vuParams.maxCount, vuParams.interval, vuParams.factInterval));
            this._runner.checkInterrupted();
            try {
                VersionUpProtocol.Result result = versionUpProtocol.parse(network.connect(requestCreate));
                this._runner.checkInterrupted();
                PushGateway.enableSendingMode(this._modelContext.getAndroidContext(), result.isPreventPush());
                AnalysisManager.setEnabled(!result.isPreventLogs());
                int i2 = result.versionupInfo;
                if (i2 == 0) {
                    database.setUpgradeSettings(new DatabaseExpert.UpgradeSettings(result.offlineCheckInterval, FuncUtil.convertFromCalendarToString(DateFormatter.DATE_MINUTE, vuParams.current), 0, result.offlineCheckMaxCnt));
                    upgradeType = ModelGateway.InitializedState.UpgradeType.NONE;
                } else if (i2 == 1) {
                    upgradeType = ModelGateway.InitializedState.UpgradeType.REQUIRED_UPGRADE;
                } else if (i2 != 2) {
                    return;
                } else {
                    upgradeType = ModelGateway.InitializedState.UpgradeType.OPTIONAL_UPGRADE;
                }
                this._runner.putSuccess(new ModelGateway.InitializedState(upgradeType));
            } catch (NetworkExpertException e) {
                if (vuParams.isCountLimit) {
                    this._runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.NETWORK_FAILED, e));
                } else {
                    this._runner.putSuccess(new ModelGateway.InitializedState(ModelGateway.InitializedState.UpgradeType.NONE));
                }
            }
        } catch (DatabaseException e2) {
            this._runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.DB_ACCESS_ERROR, e2));
        } catch (InterruptedException unused) {
        } catch (Exception e3) {
            this._runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(Initializer.class, 2, e3)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkTosVersionUp(NetworkExpert network, DatabaseExpert database) {
        VuParams vuParams = new VuParams();
        try {
            DatabaseExpert.TosVersionSettings tosVersionSettings = database.getTosVersionSettings();
            int i = tosVersionSettings.chkCnt;
            tosVersionSettings.chkCnt = i + 1;
            vuParams.factCount = i;
            vuParams.maxCount = tosVersionSettings.maxCnt;
            database.setTosVersionSettings(tosVersionSettings);
            if (vuParams.setup(tosVersionSettings)) {
                TosVersionProtocol tosVersionProtocol = network.getTosVersionProtocol();
                NetworkExpert.Request requestCreateTos = tosVersionProtocol.createTos(new TosVersionProtocol.Parameter(vuParams.factCount, vuParams.maxCount, vuParams.interval, vuParams.factInterval, tosVersionSettings.version));
                this._runner.checkInterrupted();
                try {
                    this._tosVersionProtocolResult = tosVersionProtocol.parseTos(network.connect(requestCreateTos));
                } catch (NetworkExpertException e) {
                    if (vuParams.isCountLimit) {
                        this._runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.NETWORK_FAILED, e));
                    }
                }
            }
        } catch (DatabaseException e2) {
            this._runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.DB_ACCESS_ERROR, e2));
        } catch (Exception e3) {
            this._runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(Initializer.class, 3, e3)));
        }
    }

    private static class FunctionProviderCage implements ModelGateway.FunctionProvider {
        private CardDetailFunc cardDetailFunc;
        private CentralFunc centralFunc;
        private DeleteCardListFunc deleteCardListFunc;
        private ExtIcCardFunc extIcCardFunc;
        private MemoryUsageFunc memoryUsageFunc;
        private MfiCardFunc mfiCardFunc;
        private NoticeFunc noticeFunc;
        private TermsOfServiceFunc termsOfServiceFunc;

        private FunctionProviderCage() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void create(ModelContext context) {
            this.centralFunc = new CentralFuncEntity(context);
            this.extIcCardFunc = new ExtIcCardFuncEntity(context);
            this.memoryUsageFunc = new MemoryUsageFuncEntity(context);
            this.noticeFunc = new NoticeFuncEntity(context);
            this.mfiCardFunc = new MfiCardFuncEntity(context);
            this.cardDetailFunc = new CardDetailFuncEntity(context);
            this.termsOfServiceFunc = new TermsOfServiceFuncEntity(context);
            this.deleteCardListFunc = new DeleteCardListFuncEntity(context);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void delete() {
            this.centralFunc = null;
            this.extIcCardFunc = null;
            this.memoryUsageFunc = null;
            this.noticeFunc = null;
            this.mfiCardFunc = null;
            this.cardDetailFunc = null;
            this.termsOfServiceFunc = null;
            this.deleteCardListFunc = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void allCancel() {
            CentralFunc centralFunc = this.centralFunc;
            if (centralFunc != null) {
                centralFunc.cancel();
            }
            ExtIcCardFunc extIcCardFunc = this.extIcCardFunc;
            if (extIcCardFunc != null) {
                extIcCardFunc.cancel();
            }
            MemoryUsageFunc memoryUsageFunc = this.memoryUsageFunc;
            if (memoryUsageFunc != null) {
                memoryUsageFunc.cancel();
            }
            NoticeFunc noticeFunc = this.noticeFunc;
            if (noticeFunc != null) {
                noticeFunc.cancel();
            }
            MfiCardFunc mfiCardFunc = this.mfiCardFunc;
            if (mfiCardFunc != null) {
                mfiCardFunc.cancel();
            }
            CardDetailFunc cardDetailFunc = this.cardDetailFunc;
            if (cardDetailFunc != null) {
                cardDetailFunc.cancel();
            }
            TermsOfServiceFunc termsOfServiceFunc = this.termsOfServiceFunc;
            if (termsOfServiceFunc != null) {
                termsOfServiceFunc.cancel();
            }
            DeleteCardListFunc deleteCardListFunc = this.deleteCardListFunc;
            if (deleteCardListFunc != null) {
                deleteCardListFunc.cancel();
            }
        }

        @Override // com.felicanetworks.mfm.main.model.ModelGateway.FunctionProvider
        public CentralFunc getCentralFunc() {
            return this.centralFunc;
        }

        @Override // com.felicanetworks.mfm.main.model.ModelGateway.FunctionProvider
        public ExtIcCardFunc getExtIcCardFunc() {
            return this.extIcCardFunc;
        }

        @Override // com.felicanetworks.mfm.main.model.ModelGateway.FunctionProvider
        public MemoryUsageFunc getMemoryUsageFunc() {
            return this.memoryUsageFunc;
        }

        @Override // com.felicanetworks.mfm.main.model.ModelGateway.FunctionProvider
        public NoticeFunc getNoticeFunc() {
            return this.noticeFunc;
        }

        @Override // com.felicanetworks.mfm.main.model.ModelGateway.FunctionProvider
        public MfiCardFunc getMfiCardFunc() {
            return this.mfiCardFunc;
        }

        @Override // com.felicanetworks.mfm.main.model.ModelGateway.FunctionProvider
        public CardDetailFunc getCardDetailFunc() {
            return this.cardDetailFunc;
        }

        @Override // com.felicanetworks.mfm.main.model.ModelGateway.FunctionProvider
        public TermsOfServiceFunc getTermsOfServiceFunc() {
            return this.termsOfServiceFunc;
        }

        @Override // com.felicanetworks.mfm.main.model.ModelGateway.FunctionProvider
        public DeleteCardListFunc getDeleteCardListFunc() {
            return this.deleteCardListFunc;
        }
    }

    private static class VuParams {
        private static final String DATE_FORMAT_YYYYMMDDHHMM = "yyyyMMddHHmm";
        private static final String LAST_CHECK_DATE = "000000000000";
        private static final long MAX_DIFFERENCE_MINUTES = 99999999;
        private static final long MILLISECOND_TO_MINUTE = 60000;
        private Calendar current;
        private int factCount;
        private int factInterval;
        private int interval;
        private boolean isCountLimit;
        private int maxCount;

        private VuParams() {
            this.isCountLimit = false;
            this.interval = Integer.valueOf((String) Sg.getValue(Sg.Key.NET_AIM_VERSION_DEFAULT_PARAM_INTERVAL)).intValue();
            this.factInterval = Integer.valueOf((String) Sg.getValue(Sg.Key.NET_AIM_VERSION_DEFAULT_PARAM_FACT_INTERVAL)).intValue();
            this.maxCount = Integer.valueOf((String) Sg.getValue(Sg.Key.NET_AIM_VERSION_DEFAULT_PARAM_MAX_COUNT)).intValue();
            this.factCount = Integer.valueOf((String) Sg.getValue(Sg.Key.NET_AIM_VERSION_DEFAULT_PARAM_FACT_COUNT)).intValue();
            this.current = FuncUtil.getCurrentCalendar();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean setup(DatabaseExpert.UpgradeSettings setting) {
            if (LAST_CHECK_DATE.equals(setting.lastChkDate)) {
                this.isCountLimit = true;
                return true;
            }
            if (setting.chkCnt > setting.maxCnt) {
                this.isCountLimit = true;
            }
            Calendar calendarConvertFromStringToCalendar = FuncUtil.convertFromStringToCalendar("yyyyMMddHHmm", setting.lastChkDate);
            if (calendarConvertFromStringToCalendar == null) {
                return true;
            }
            long timeInMillis = this.current.getTimeInMillis() / MILLISECOND_TO_MINUTE;
            long timeInMillis2 = calendarConvertFromStringToCalendar.getTimeInMillis() / MILLISECOND_TO_MINUTE;
            if (timeInMillis2 > timeInMillis) {
                return true;
            }
            if (((long) setting.interval) + timeInMillis2 >= timeInMillis) {
                if (!this.isCountLimit) {
                    return false;
                }
                this.interval = setting.interval;
                this.factInterval = (int) (timeInMillis - timeInMillis2);
                return true;
            }
            long j = timeInMillis - timeInMillis2;
            if (MAX_DIFFERENCE_MINUTES < j) {
                return true;
            }
            this.interval = setting.interval;
            this.factInterval = (int) j;
            return true;
        }
    }
}
