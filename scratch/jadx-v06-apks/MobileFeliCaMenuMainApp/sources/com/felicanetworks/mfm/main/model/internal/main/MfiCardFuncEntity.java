package com.felicanetworks.mfm.main.model.internal.main;

import android.content.Intent;
import com.felicanetworks.mfm.main.model.CentralFunc;
import com.felicanetworks.mfm.main.model.MfiCardFunc;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.specific.MySuicaInfo;
import com.felicanetworks.mfm.main.model.internal.main.FuncUtil;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaParams;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.fix.MfiLoginResultCode;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes.dex */
public class MfiCardFuncEntity implements MfiCardFunc {
    private ModelContext _context;
    private final DatabaseExpert _db;
    private boolean _hasUnrecoveredCard;
    private boolean hasNeverLoggedIn;
    private AsyncRunnerForCompile _runner = new AsyncRunnerForCompile();
    private FuncUtil.AsyncRunner recoveryRunner = new FuncUtil.AsyncRunner();
    private ModelErrorInfo modelErrorInfo = null;
    private MfiCardFunc.CompiledState _compiledState = new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, null);
    private boolean _isCompiled = false;
    private boolean _isLogined = false;
    private ModelErrorInfo _recoveryErrorInfo = null;
    private boolean _isConfirmCardRecovery = true;

    MfiCardFuncEntity(ModelContext modelContext) {
        this._context = modelContext;
        this._db = modelContext.getOpenedDatabaseExpert();
    }

    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc
    public void reset() {
        this._compiledState = new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, null);
        this._isCompiled = false;
        this.hasNeverLoggedIn = false;
        this._hasUnrecoveredCard = false;
        this.modelErrorInfo = null;
        ModelContext.getInitializedMfcExpert().clearVariable();
        this._runner = new AsyncRunnerForCompile();
        this._isLogined = false;
        this.recoveryRunner = new FuncUtil.AsyncRunner();
    }

    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc
    public void compile(final CentralFunc centralFunc, final String str, final int i, final int i2, final MfiCardFunc.CompileListener compileListener) {
        if (!this._runner.startup(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.1
            /* JADX WARN: Removed duplicated region for block: B:110:0x0392 A[Catch: all -> 0x08b4, Exception -> 0x08b6, NullPointerException -> 0x08dc, DatabaseException -> 0x08e8, InterruptedException -> 0x0914, TRY_ENTER, TryCatch #22 {NullPointerException -> 0x08dc, Exception -> 0x08b6, blocks: (B:3:0x0013, B:11:0x003d, B:13:0x0045, B:14:0x005c, B:16:0x0068, B:18:0x0070, B:20:0x0078, B:23:0x008b, B:24:0x00a5, B:35:0x00db, B:37:0x00e3, B:38:0x00fa, B:40:0x0106, B:42:0x010e, B:44:0x0116, B:47:0x0129, B:48:0x0143, B:52:0x017c, B:54:0x0184, B:55:0x019b, B:57:0x01a7, B:59:0x01af, B:61:0x01b7, B:64:0x01ca, B:65:0x01e4, B:69:0x020c, B:71:0x0214, B:72:0x022b, B:74:0x0237, B:76:0x023f, B:78:0x0247, B:81:0x025a, B:82:0x0274, B:92:0x02f4, B:94:0x02fc, B:95:0x0313, B:97:0x031f, B:99:0x0327, B:101:0x032f, B:104:0x0342, B:105:0x035c, B:110:0x0392, B:112:0x039a, B:113:0x03b1, B:115:0x03bd, B:117:0x03c5, B:119:0x03cd, B:122:0x03e0, B:123:0x03fa, B:205:0x0627, B:208:0x0631, B:210:0x0669, B:209:0x0657, B:211:0x066e, B:213:0x067a, B:215:0x0682, B:217:0x068a, B:220:0x069d, B:221:0x06b7, B:222:0x06c3, B:268:0x0814, B:271:0x081e, B:273:0x0856, B:272:0x0844, B:274:0x085b, B:276:0x0867, B:278:0x086f, B:280:0x0877, B:283:0x088a, B:284:0x08a4, B:285:0x08b3, B:249:0x0769, B:252:0x0773, B:254:0x07ab, B:253:0x0799, B:255:0x07b0, B:257:0x07bc, B:259:0x07c4, B:261:0x07cc, B:264:0x07df, B:265:0x07f9), top: B:315:0x0013, outer: #15 }] */
            /* JADX WARN: Removed duplicated region for block: B:158:0x04e0  */
            /* JADX WARN: Removed duplicated region for block: B:228:0x06e2 A[Catch: all -> 0x0744, MfcException -> 0x0748, LOOP:10: B:226:0x06dc->B:228:0x06e2, LOOP_END, TryCatch #21 {MfcException -> 0x0748, all -> 0x0744, blocks: (B:170:0x0513, B:171:0x0526, B:173:0x052c, B:175:0x0543, B:177:0x0553, B:178:0x0564, B:180:0x056a, B:182:0x057e, B:190:0x05ad, B:191:0x05c0, B:193:0x05c6, B:195:0x05dd, B:197:0x05ed, B:198:0x05fe, B:200:0x0604, B:202:0x0618, B:225:0x06c9, B:226:0x06dc, B:228:0x06e2, B:230:0x06f9, B:232:0x0709, B:233:0x071a, B:235:0x0720, B:237:0x0734, B:238:0x073d, B:239:0x0740, B:240:0x0743), top: B:324:0x0416 }] */
            /* JADX WARN: Removed duplicated region for block: B:232:0x0709 A[Catch: all -> 0x0744, MfcException -> 0x0748, TryCatch #21 {MfcException -> 0x0748, all -> 0x0744, blocks: (B:170:0x0513, B:171:0x0526, B:173:0x052c, B:175:0x0543, B:177:0x0553, B:178:0x0564, B:180:0x056a, B:182:0x057e, B:190:0x05ad, B:191:0x05c0, B:193:0x05c6, B:195:0x05dd, B:197:0x05ed, B:198:0x05fe, B:200:0x0604, B:202:0x0618, B:225:0x06c9, B:226:0x06dc, B:228:0x06e2, B:230:0x06f9, B:232:0x0709, B:233:0x071a, B:235:0x0720, B:237:0x0734, B:238:0x073d, B:239:0x0740, B:240:0x0743), top: B:324:0x0416 }] */
            /* JADX WARN: Removed duplicated region for block: B:324:0x0416 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public void run() {
                /*
                    Method dump skipped, instruction units count: 2339
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.AnonymousClass1.run():void");
            }
        })) {
            throw new IllegalStateException("MfiCardFunc#Compile() is still executing.");
        }
    }

    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc
    public void executeCardRecovery(final MfiCardFunc.RecoveryListener recoveryListener) {
        this.recoveryRunner.startupOrThrow(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.2
            @Override // java.lang.Runnable
            public void run() {
                FuncUtil.AsyncRunner asyncRunner;
                FuncUtil.Notify notify;
                try {
                    try {
                        ModelContext unused = MfiCardFuncEntity.this._context;
                        ModelContext.getInitializedMfcExpert().executeCardRecovery();
                        asyncRunner = MfiCardFuncEntity.this.recoveryRunner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.2.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                recoveryListener.onCompleted();
                            }
                        };
                    } catch (MfcException e) {
                        MfiCardFuncEntity.this._recoveryErrorInfo = MfiCardFuncEntity.this.convertErrorToCompiledState(e).getMficWarningState();
                        asyncRunner = MfiCardFuncEntity.this.recoveryRunner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.2.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                recoveryListener.onCompleted();
                            }
                        };
                    }
                    FuncUtil.notifySafety(asyncRunner, notify);
                } catch (Throwable th) {
                    FuncUtil.notifySafety(MfiCardFuncEntity.this.recoveryRunner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.2.1
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            recoveryListener.onCompleted();
                        }
                    });
                    throw th;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getMfiAccountInfo() throws MfcException {
        MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
        try {
            this.hasNeverLoggedIn = false;
            return initializedMfcExpert.getAccountHash();
        } catch (MfcException e) {
            if (MfcException.Type.MFIC_NO_ACCOUNT_INFO.equals(e.getErrorType())) {
                this.hasNeverLoggedIn = true;
                return null;
            }
            throw e;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAccountRelatedInfo() {
        try {
            this._db.deleteAccountRelatedInfo();
        } catch (DatabaseException e) {
            LogUtil.error(e);
        }
    }

    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc
    public MfiCardFunc.CompiledState getCompiledState() {
        return this._compiledState;
    }

    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc
    public Boolean isCompiled() {
        return Boolean.valueOf(this._isCompiled);
    }

    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc
    public void cancel() {
        this._runner.shutdown();
        this.recoveryRunner.shutdown();
    }

    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc
    public boolean isLoggedIn() {
        return this._isLogined && this._isCompiled;
    }

    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc
    public boolean hasNeverLoggedIn() {
        return this.hasNeverLoggedIn;
    }

    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc
    public boolean hasUnrecoveredCard() {
        return this._hasUnrecoveredCard;
    }

    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc
    public ModelErrorInfo getRecoveryErrorInfo() {
        return this._recoveryErrorInfo;
    }

    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc
    public boolean isInsideTransitGateRecoveryFailed() {
        ModelErrorInfo modelErrorInfo = this._recoveryErrorInfo;
        return (modelErrorInfo == null || modelErrorInfo.getException() == null || this._recoveryErrorInfo.getException().getExtra() != 245) ? false : true;
    }

    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc
    public void clearRecoveryErrorInfo() {
        this._recoveryErrorInfo = null;
    }

    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc
    public List<MyCardInfo> getMyCardInfoList() {
        return this._runner.getMyCardInfoList();
    }

    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc
    public List<MyCardInfo> getDeleteCardInfoList() {
        return this._runner.getDeleteCardInfoList();
    }

    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc
    public Set<String> getMyCardDetectSet() {
        return this._runner.getMyCardDetectSet();
    }

    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc
    public MySuicaInfo.Position getSuicaPosition() {
        return this._runner.getSuicaPosition();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void orderSuicaPosition(List<String> list) throws MfcException {
        if (!list.contains(FeliCaParams.SERVICE_ID_SUICA)) {
            this._runner.setSuicaPosition(MySuicaInfo.Position.NONE);
            return;
        }
        try {
            MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
            initializedMfcExpert.felicaStart();
            int iExistUnsupportMfiService1Card = initializedMfcExpert.existUnsupportMfiService1Card();
            if (iExistUnsupportMfiService1Card == 1) {
                this._runner.setSuicaPosition(MySuicaInfo.Position.ENABLE);
            } else if (iExistUnsupportMfiService1Card == 2) {
                this._runner.setSuicaPosition(MySuicaInfo.Position.DISABLE);
            } else {
                this._runner.setSuicaPosition(MySuicaInfo.Position.NONE);
            }
        } catch (MfcException e) {
            this._runner.setSuicaPosition(MySuicaInfo.Position.NONE);
            throw e;
        }
    }

    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc
    public void mfiLogin(final int i, final boolean z, final MfiCardFunc.MfiLoginListener mfiLoginListener) {
        if (!this._runner.startup(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.3
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v12, types: [com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert] */
            /* JADX WARN: Type inference failed for: r0v18, types: [com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity$AsyncRunnerForCompile] */
            /* JADX WARN: Type inference failed for: r0v23 */
            /* JADX WARN: Type inference failed for: r0v24 */
            @Override // java.lang.Runnable
            public void run() {
                AsyncRunnerForCompile asyncRunnerForCompile;
                FuncUtil.Notify notify;
                MfiCardFunc.CompiledState compiledState;
                MfiCardFuncEntity.this._runner.intentForRequestActivity = null;
                try {
                    try {
                        ModelContext unused = MfiCardFuncEntity.this._context;
                        final MfcExpert initializedMfcExpert = ModelContext.getInitializedMfcExpert();
                        try {
                            try {
                                final CountDownLatch countDownLatch = new CountDownLatch(1);
                                initializedMfcExpert.setSilentStartCode(i);
                                initializedMfcExpert.mfiLogin(z, new MfcExpert.MfiStartListener() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.3.1
                                    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                                    public void onSuccess(boolean z2) {
                                        MfiCardFuncEntity.this._isLogined = z2;
                                        countDownLatch.countDown();
                                    }

                                    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                                    public void onRequestActivity(Intent intent) {
                                        MfiCardFuncEntity.this._isLogined = false;
                                        MfiCardFuncEntity.this._runner.setIntentForRequestActivity(intent);
                                        initializedMfcExpert.finishFeliCaAccess();
                                        countDownLatch.countDown();
                                    }

                                    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                                    public void errorResult(MfcException mfcException) {
                                        MfiCardFuncEntity.this._runner.putFailure(MfiCardFuncEntity.this.convertErrorToCompiledState(mfcException).getMficWarningState());
                                        countDownLatch.countDown();
                                    }

                                    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                                    public void onSuccessNoLogin() {
                                        MfiCardFuncEntity.this._runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(MfiCardFuncEntity.class, 259)));
                                        countDownLatch.countDown();
                                    }
                                });
                                countDownLatch.await();
                                if (initializedMfcExpert != 0) {
                                    initializedMfcExpert.finishFeliCaAccess();
                                }
                                AsyncRunnerForCompile asyncRunnerForCompile2 = MfiCardFuncEntity.this._runner;
                                compiledState = MfiCardFuncEntity.this._compiledState;
                                initializedMfcExpert = asyncRunnerForCompile2;
                            } finally {
                            }
                        } catch (MfcException e) {
                            MfiCardFuncEntity.this._runner.putFailure(MfiCardFuncEntity.this.convertErrorToCompiledState(e).getMficWarningState());
                            if (initializedMfcExpert != 0) {
                                initializedMfcExpert.finishFeliCaAccess();
                            }
                            AsyncRunnerForCompile asyncRunnerForCompile3 = MfiCardFuncEntity.this._runner;
                            compiledState = MfiCardFuncEntity.this._compiledState;
                            initializedMfcExpert = asyncRunnerForCompile3;
                        }
                        initializedMfcExpert.putSuccess(compiledState);
                        asyncRunnerForCompile = MfiCardFuncEntity.this._runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.3.2
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                    mfiLoginListener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                                } else if (MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                    mfiLoginListener.onRequestActivity(MfiCardFuncEntity.this._runner.getIntentForRequestActivity());
                                } else {
                                    mfiLoginListener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                                }
                            }
                        };
                    } catch (Throwable th) {
                        FuncUtil.notifySafety(MfiCardFuncEntity.this._runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.3.2
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                    mfiLoginListener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                                } else if (MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                    mfiLoginListener.onRequestActivity(MfiCardFuncEntity.this._runner.getIntentForRequestActivity());
                                } else {
                                    mfiLoginListener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                                }
                            }
                        });
                        throw th;
                    }
                } catch (InterruptedException unused2) {
                    asyncRunnerForCompile = MfiCardFuncEntity.this._runner;
                    notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.3.2
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                mfiLoginListener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                            } else if (MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                mfiLoginListener.onRequestActivity(MfiCardFuncEntity.this._runner.getIntentForRequestActivity());
                            } else {
                                mfiLoginListener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                            }
                        }
                    };
                } catch (NullPointerException unused3) {
                    asyncRunnerForCompile = MfiCardFuncEntity.this._runner;
                    notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.3.2
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                mfiLoginListener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                            } else if (MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                mfiLoginListener.onRequestActivity(MfiCardFuncEntity.this._runner.getIntentForRequestActivity());
                            } else {
                                mfiLoginListener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                            }
                        }
                    };
                } catch (Exception e2) {
                    MfiCardFuncEntity.this._runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_RUNTIME_ERROR, new MfmException(MfiCardFuncEntity.class, 258, e2)));
                    asyncRunnerForCompile = MfiCardFuncEntity.this._runner;
                    notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.3.2
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                mfiLoginListener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                            } else if (MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                mfiLoginListener.onRequestActivity(MfiCardFuncEntity.this._runner.getIntentForRequestActivity());
                            } else {
                                mfiLoginListener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                            }
                        }
                    };
                }
                FuncUtil.notifySafety(asyncRunnerForCompile, notify);
            }
        })) {
            throw new IllegalStateException("MfiCardFunc#Compile() is still executing.");
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity$4, reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode;

        static {
            int[] iArr = new int[MfcException.Type.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type = iArr;
            try {
                iArr[MfcException.Type.FELICA_OPEN_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.LOCKED_FELICA.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFI_SERVER_MAINTENANCE_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.FELICA_NETWORK_FAILED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.INVALID_REQUEST_TOKEN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.UNSUPPORTED_CHIP_ERROR.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.OTHER_ERROR.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.FELICA_HTTP_ERROR.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFI_OPSRV_REQUIRED_LIB_UNAVAILABLE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFIC_VERSION_ERROR.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFIC_JSON_ERROR.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFI_OPSRV_ACCOUNT_ERROR.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFC_USED_BY_OTHER_APP.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFIC_RUNNING_BY_ITSELF.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            int[] iArr2 = new int[MfiLoginResultCode.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode = iArr2;
            try {
                iArr2[MfiLoginResultCode.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode[MfiLoginResultCode.SUCCESS.ordinal()] = 2;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode[MfiLoginResultCode.CANCELLED_BY_ANDROID.ordinal()] = 3;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode[MfiLoginResultCode.CANCELLED_BY_USER_DISAGREE.ordinal()] = 4;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode[MfiLoginResultCode.CANCELLED_BY_USER_SKIPPED.ordinal()] = 5;
            } catch (NoSuchFieldError unused19) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MfiCardFunc.CompiledState convertErrorToCompiledState(MfcException mfcException) {
        switch (AnonymousClass4.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[mfcException.getErrorType().ordinal()]) {
            case 1:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_OPEN_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_OPEN_ERROR, mfcException));
            case 2:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_LOCK_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_LOCK_ERROR, mfcException));
            case 3:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_SERVER_MAINTENANCE_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_SERVER_MAINTENANCE_ERROR, mfcException));
            case 4:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_NETWORK_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_NETWORK_ERROR, mfcException));
            case 5:
                ModelErrorInfo modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.MFIC_INVALID_REQUEST_TOKEN_ERROR, mfcException);
                this._runner.putFailure(modelErrorInfo);
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_INVALID_REQUEST_TOKEN, modelErrorInfo);
            case 6:
            case 7:
                ModelErrorInfo modelErrorInfo2 = new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, mfcException);
                this._runner.putFailure(modelErrorInfo2);
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.OTHER_ERROR, modelErrorInfo2);
            case 8:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_FELICA_HTTP_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.FELICA_HTTP_ERROR, mfcException));
            case 9:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_LIB_UNAVAILABLE, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_LIB_UNAVAILABLE, mfcException));
            case 10:
                ModelErrorInfo modelErrorInfo3 = new ModelErrorInfo(ModelErrorInfo.Type.MFIC_VERSION_ERROR, mfcException);
                this._runner.putFailure(modelErrorInfo3);
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, modelErrorInfo3);
            case 11:
                boolean zIsEmpty = false;
                try {
                    zIsEmpty = this._db.getMfiCardAdditionalInfo().isEmpty();
                    break;
                } catch (Exception unused) {
                }
                if (zIsEmpty) {
                    return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_JSON_ERROR_NO_CASHE, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_JSON_ERROR_NO_CASHE, mfcException));
                }
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_JSON_ERROR_USE_CACHE, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_JSON_ERROR_USE_CACHE, mfcException));
            case 12:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_LOGIN_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_LOGIN_ERROR, mfcException));
            case 13:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_USED_BY_OTHER_APP, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_USED_BY_OTHER_APP, mfcException));
            case 14:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_RUNNING_BY_ITSELF, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_RUNNING_BY_ITSELF, mfcException));
            default:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_OTHER_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_OTHER_ERROR, mfcException));
        }
    }

    private class AsyncRunnerForCompile extends FuncUtil.AsyncRunner {
        private List<MyCardInfo> deleteCardInfoList;
        private Intent intentForRequestActivity;
        private Set<String> myCardDetectSet;
        private List<MyCardInfo> myCardInfoList;
        private MySuicaInfo.Position suicaPosition;

        private AsyncRunnerForCompile() {
            this.myCardInfoList = new ArrayList();
            this.deleteCardInfoList = new ArrayList();
            this.myCardDetectSet = new HashSet();
            this.intentForRequestActivity = null;
            this.suicaPosition = MySuicaInfo.Position.UNKNOWN;
        }

        List<MyCardInfo> getMyCardInfoList() {
            return this.myCardInfoList;
        }

        List<MyCardInfo> getDeleteCardInfoList() {
            return this.deleteCardInfoList;
        }

        Set<String> getMyCardDetectSet() {
            return this.myCardDetectSet;
        }

        MySuicaInfo.Position getSuicaPosition() {
            return this.suicaPosition;
        }

        void setMyCardInfoList(List<MyCardInfo> list) {
            if (list != null) {
                Iterator<MyCardInfo> it = list.iterator();
                while (it.hasNext()) {
                    this.myCardDetectSet.add(it.next().getServiceId());
                }
                this.myCardInfoList = list;
            }
        }

        void setDeleteCardInfoList(List<MyCardInfo> list) {
            if (list != null) {
                this.deleteCardInfoList = list;
            }
        }

        void setSuicaPosition(MySuicaInfo.Position position) {
            this.suicaPosition = position;
            ModelContext unused = MfiCardFuncEntity.this._context;
            ModelContext.getInitializedMfcExpert().setSuicaPosition(position);
        }

        boolean hasIntentForRequestActivity() {
            return this.intentForRequestActivity != null;
        }

        Intent getIntentForRequestActivity() {
            return this.intentForRequestActivity;
        }

        void setIntentForRequestActivity(Intent intent) {
            this.intentForRequestActivity = intent;
        }
    }
}
