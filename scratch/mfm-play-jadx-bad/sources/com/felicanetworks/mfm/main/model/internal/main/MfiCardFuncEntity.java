package com.felicanetworks.mfm.main.model.internal.main;

import android.content.Intent;
import android.text.TextUtils;
import androidx.core.view.InputDeviceCompat;
import com.felicanetworks.mfm.main.model.CentralFunc;
import com.felicanetworks.mfm.main.model.MfiCardFunc;
import com.felicanetworks.mfm.main.model.info.MfiCardAdditionalInfo;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.MyCardAdditionalInfo;
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
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes3.dex */
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
    private boolean _isChaced = false;
    private ModelErrorInfo _recoveryErrorInfo = null;
    private boolean _isConfirmCardRecovery = true;

    MfiCardFuncEntity(ModelContext context) {
        this._context = context;
        this._db = context.getOpenedDatabaseExpert();
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
        this._isChaced = false;
        this.recoveryRunner = new FuncUtil.AsyncRunner();
    }

    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc
    public void compile(final CentralFunc centralFunc, final String lastAccountHash, final int resultCode, final int extraAccountCode, final boolean isBackground, final MfiCardFunc.CompileListener listener) {
        compile("mfi_compile", centralFunc, lastAccountHash, resultCode, extraAccountCode, isBackground, listener);
    }

    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc
    public void compile(String name, final CentralFunc centralFunc, final String lastAccountHash, final int resultCode, final int extraAccountCode, final boolean isBackground, final MfiCardFunc.CompileListener listener) {
        this._context.setMfiCardFuncRunner(this._runner);
        if (!this._runner.startup(name, new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.1
            /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [443=9, 446=6, 454=9, 456=9, 459=9, 461=9, 464=18, 465=9, 467=9, 468=9, 473=9, 497=11] */
            /* JADX WARN: Removed duplicated region for block: B:113:0x03a9 A[Catch: all -> 0x08a7, Exception -> 0x08a9, NullPointerException -> 0x08cf, DatabaseException -> 0x08df, InterruptedException -> 0x090b, TRY_ENTER, TryCatch #20 {DatabaseException -> 0x08df, Exception -> 0x08a9, blocks: (B:3:0x0013, B:9:0x0046, B:11:0x004e, B:12:0x0065, B:14:0x0071, B:16:0x0079, B:18:0x0081, B:21:0x0094, B:22:0x00aa, B:35:0x00ee, B:37:0x00f6, B:38:0x010d, B:40:0x0119, B:42:0x0121, B:44:0x0129, B:47:0x013c, B:48:0x0152, B:52:0x018d, B:54:0x0195, B:55:0x01ac, B:57:0x01b8, B:59:0x01c0, B:61:0x01c8, B:64:0x01db, B:65:0x01f1, B:69:0x0219, B:71:0x0221, B:72:0x0238, B:74:0x0244, B:76:0x024c, B:78:0x0254, B:81:0x0267, B:82:0x027d, B:95:0x030f, B:97:0x0317, B:98:0x032e, B:100:0x033a, B:102:0x0342, B:104:0x034a, B:107:0x035d, B:108:0x0373, B:113:0x03a9, B:115:0x03b1, B:116:0x03c8, B:118:0x03d4, B:120:0x03dc, B:122:0x03e4, B:125:0x03f7, B:126:0x040d, B:208:0x0636, B:211:0x0640, B:213:0x0674, B:212:0x0662, B:214:0x0679, B:216:0x0685, B:218:0x068d, B:220:0x0695, B:223:0x06a8, B:224:0x06be, B:225:0x06ca, B:271:0x080f, B:274:0x0819, B:276:0x084d, B:275:0x083b, B:277:0x0852, B:279:0x085e, B:281:0x0866, B:283:0x086e, B:286:0x0881, B:287:0x0897, B:288:0x08a6, B:252:0x076c, B:255:0x0776, B:257:0x07aa, B:256:0x0798, B:258:0x07af, B:260:0x07bb, B:262:0x07c3, B:264:0x07cb, B:267:0x07de, B:268:0x07f4), top: B:305:0x0013, outer: #4 }] */
            /* JADX WARN: Removed duplicated region for block: B:161:0x04f3  */
            /* JADX WARN: Removed duplicated region for block: B:311:0x0429 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void run() {
                AsyncRunnerForCompile asyncRunnerForCompile;
                FuncUtil.Notify notify;
                final MfcExpert initializedMfcExpert;
                AsyncRunnerForCompile asyncRunnerForCompile2;
                MfiCardFunc.CompiledState compiledState;
                boolean z;
                boolean zIsEmpty;
                ArrayList arrayList;
                ArrayList arrayList2;
                boolean z2;
                boolean z3;
                AsyncRunnerForCompile asyncRunnerForCompile3;
                FuncUtil.Notify notify2;
                MfiCardFuncEntity.this._compiledState = null;
                final FuncUtil.AsyncPacket asyncPacket = new FuncUtil.AsyncPacket();
                boolean z4 = false;
                asyncPacket.set(false);
                try {
                    try {
                        try {
                            ModelContext unused = MfiCardFuncEntity.this._context;
                            initializedMfcExpert = ModelContext.getInitializedMfcExpert();
                            try {
                                try {
                                    initializedMfcExpert.felicaStart();
                                    MfiCardFuncEntity.this._runner.checkInterrupted();
                                    z = !TextUtils.isEmpty(MfiCardFuncEntity.this.getMfiAccountInfo());
                                } catch (MfcException e) {
                                    e = e;
                                }
                            } catch (Throwable th) {
                                th = th;
                            }
                        } catch (Throwable th2) {
                            FuncUtil.notifySafety(MfiCardFuncEntity.this._runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.1.2
                                @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                public void go() {
                                    if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                        MfiCardFuncEntity.this._isCompiled = false;
                                        listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                                    } else if (MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                        listener.onRequestActivity(MfiCardFuncEntity.this._runner.getIntentForRequestActivity());
                                    } else {
                                        listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                                    }
                                    MfiCardFuncEntity.this._context.setMfiCardFuncRunner(null);
                                }
                            });
                            throw th2;
                        }
                    } catch (DatabaseException e2) {
                        MfiCardFuncEntity.this._runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.DB_ACCESS_ERROR, e2));
                        asyncRunnerForCompile = MfiCardFuncEntity.this._runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.1.2
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                    MfiCardFuncEntity.this._isCompiled = false;
                                    listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                                } else if (MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                    listener.onRequestActivity(MfiCardFuncEntity.this._runner.getIntentForRequestActivity());
                                } else {
                                    listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                                }
                                MfiCardFuncEntity.this._context.setMfiCardFuncRunner(null);
                            }
                        };
                    } catch (Exception e3) {
                        MfiCardFuncEntity.this._runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_RUNTIME_ERROR, new MfmException(MfiCardFuncEntity.class, 257, e3)));
                        asyncRunnerForCompile = MfiCardFuncEntity.this._runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.1.2
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                    MfiCardFuncEntity.this._isCompiled = false;
                                    listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                                } else if (MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                    listener.onRequestActivity(MfiCardFuncEntity.this._runner.getIntentForRequestActivity());
                                } else {
                                    listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                                }
                                MfiCardFuncEntity.this._context.setMfiCardFuncRunner(null);
                            }
                        };
                    }
                } catch (InterruptedException unused2) {
                    asyncRunnerForCompile = MfiCardFuncEntity.this._runner;
                    notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.1.2
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                MfiCardFuncEntity.this._isCompiled = false;
                                listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                            } else if (MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                listener.onRequestActivity(MfiCardFuncEntity.this._runner.getIntentForRequestActivity());
                            } else {
                                listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                            }
                            MfiCardFuncEntity.this._context.setMfiCardFuncRunner(null);
                        }
                    };
                } catch (NullPointerException e4) {
                    LogUtil.warning(e4);
                    asyncRunnerForCompile = MfiCardFuncEntity.this._runner;
                    notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.1.2
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                MfiCardFuncEntity.this._isCompiled = false;
                                listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                            } else if (MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                listener.onRequestActivity(MfiCardFuncEntity.this._runner.getIntentForRequestActivity());
                            } else {
                                listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                            }
                            MfiCardFuncEntity.this._context.setMfiCardFuncRunner(null);
                        }
                    };
                }
                if (!centralFunc.getCompiledState().hasNetworkWarning()) {
                    int i = AnonymousClass5.$SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode[MfiLoginResultCode.resolve(resultCode).ordinal()];
                    if (i != 1) {
                        z = i == 2;
                    }
                    if (z) {
                        final CountDownLatch countDownLatch = new CountDownLatch(1);
                        initializedMfcExpert.setSilentStartCode(extraAccountCode);
                        initializedMfcExpert.mfiStart(isBackground, new MfcExpert.MfiStartListener() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.1.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                            public void onSuccess(boolean executedSilentStart) {
                                MfiCardFuncEntity.this._isLogined = executedSilentStart;
                                countDownLatch.countDown();
                            }

                            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                            public void onRequestActivity(Intent intent) {
                                MfiCardFuncEntity.this._isLogined = false;
                                asyncPacket.set(true);
                                MfiCardFuncEntity.this._runner.setIntentForRequestActivity(intent);
                                initializedMfcExpert.finishFeliCaAccess();
                                countDownLatch.countDown();
                            }

                            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                            public void errorResult(MfcException exception) {
                                MfiCardFuncEntity.this._compiledState = MfiCardFuncEntity.this.convertErrorToCompiledState(exception);
                                MfiCardFuncEntity.this.modelErrorInfo = MfiCardFuncEntity.this._compiledState.getMficWarningState();
                                countDownLatch.countDown();
                            }

                            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                            public void onSuccessNoLogin() {
                                MfiCardFuncEntity.this._compiledState = new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.OTHER_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(MfiCardFuncEntity.class, 291)));
                                countDownLatch.countDown();
                            }
                        });
                        countDownLatch.await();
                        if (MfiCardFuncEntity.this._compiledState != null) {
                            if (MfiCardFuncEntity.this._compiledState == null) {
                                MfiCardFuncEntity.this._compiledState = new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, MfiCardFuncEntity.this.modelErrorInfo);
                                MfiCardFuncEntity.this._isCompiled = true;
                            }
                            if (!((Boolean) asyncPacket.get()).booleanValue() && (!MfiCardFuncEntity.this._isLogined || !MfiCardFuncEntity.this._isCompiled)) {
                                try {
                                    MfiCardFuncEntity.this.orderSuicaPosition((List) Objects.requireNonNull(centralFunc.getAreaDetectedServiceIdList()));
                                } catch (MfcException e5) {
                                    MfiCardFuncEntity mfiCardFuncEntity = MfiCardFuncEntity.this;
                                    mfiCardFuncEntity._compiledState = mfiCardFuncEntity.convertErrorToCompiledState(e5);
                                    MfiCardFuncEntity mfiCardFuncEntity2 = MfiCardFuncEntity.this;
                                    mfiCardFuncEntity2.modelErrorInfo = mfiCardFuncEntity2._compiledState.getMficWarningState();
                                }
                            }
                            MfiCardFuncEntity.this._runner.putSuccess(MfiCardFuncEntity.this._compiledState);
                            asyncRunnerForCompile3 = MfiCardFuncEntity.this._runner;
                            notify2 = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.1.2
                                @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                public void go() {
                                    if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                        MfiCardFuncEntity.this._isCompiled = false;
                                        listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                                    } else if (MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                        listener.onRequestActivity(MfiCardFuncEntity.this._runner.getIntentForRequestActivity());
                                    } else {
                                        listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                                    }
                                    MfiCardFuncEntity.this._context.setMfiCardFuncRunner(null);
                                }
                            };
                        } else {
                            if (!MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                String mfiAccountInfo = MfiCardFuncEntity.this.getMfiAccountInfo();
                                if (!TextUtils.equals(lastAccountHash, mfiAccountInfo)) {
                                    MfiCardFuncEntity.this.clearAccountRelatedInfo();
                                    if (!TextUtils.isEmpty(lastAccountHash)) {
                                        MfiCardFuncEntity.this.clearCache();
                                    }
                                    listener.onChangedAccount(mfiAccountInfo, lastAccountHash);
                                }
                                MfcExpert.CardInfos cardInfosFetchCardList = initializedMfcExpert.fetchCardList();
                                MfiCardFuncEntity.this._runner.setMyCardInfoList(cardInfosFetchCardList.getCardInfoList());
                                MfiCardFuncEntity.this._runner.setDeleteCardInfoList(cardInfosFetchCardList.getDeleteCardInfoList());
                                MfiCardFuncEntity.this._isChaced = false;
                                if (!initializedMfcExpert.isNeedCardRecovery()) {
                                    MfiCardFuncEntity.this._isConfirmCardRecovery = true;
                                    zIsEmpty = MfiCardFuncEntity.this._runner.getMyCardInfoList().isEmpty();
                                    if (zIsEmpty) {
                                        try {
                                            try {
                                                MfiCardFuncEntity.this._runner.checkInterrupted();
                                                arrayList = new ArrayList();
                                                arrayList2 = new ArrayList();
                                            } catch (Throwable th3) {
                                                th = th3;
                                            }
                                            try {
                                                ArrayList arrayList3 = new ArrayList();
                                                Iterator<MyCardInfo> it = MfiCardFuncEntity.this._runner.getMyCardInfoList().iterator();
                                                while (it.hasNext()) {
                                                    arrayList3.add(it.next().getCardId());
                                                }
                                                List<DatabaseExpert.MfiHashValueInfo> mfiHashValueInfo = MfiCardFuncEntity.this._db.getMfiHashValueInfo(arrayList3);
                                                for (int i2 = 0; i2 < MfiCardFuncEntity.this._runner.getMyCardInfoList().size(); i2++) {
                                                    MyCardInfo myCardInfo = MfiCardFuncEntity.this._runner.getMyCardInfoList().get(i2);
                                                    String cardId = myCardInfo.getCardId();
                                                    String mfiHashValue = initializedMfcExpert.getMfiHashValue(cardId);
                                                    if (mfiHashValue != null || MyCardInfo.SupportMfiServiceType.UNSUPPORTED_MFI_SERVICE_1 != myCardInfo.getUnsupportedMfiService1()) {
                                                        Iterator<DatabaseExpert.MfiHashValueInfo> it2 = mfiHashValueInfo.iterator();
                                                        while (true) {
                                                            if (!it2.hasNext()) {
                                                                break;
                                                            }
                                                            DatabaseExpert.MfiHashValueInfo next = it2.next();
                                                            if (next.cardId.equals(cardId)) {
                                                                z3 = TextUtils.equals(mfiHashValue, next.mfiHashValue);
                                                            }
                                                        }
                                                        if (!z3) {
                                                            arrayList.add(cardId);
                                                            arrayList2.add(new DatabaseExpert.MfiHashValueInfo(cardId, mfiHashValue));
                                                        }
                                                    }
                                                }
                                                String[] strArr = (String[]) arrayList.toArray(new String[0]);
                                                if (strArr.length > 0) {
                                                    List<MyCardAdditionalInfo> listFetchCardAdditionalInfoList = initializedMfcExpert.fetchCardAdditionalInfoList(strArr);
                                                    if (!listFetchCardAdditionalInfoList.isEmpty()) {
                                                        z2 = listFetchCardAdditionalInfoList.size() != strArr.length;
                                                        try {
                                                            for (MyCardAdditionalInfo myCardAdditionalInfo : listFetchCardAdditionalInfoList) {
                                                                if (myCardAdditionalInfo.getAdditionalInfo() == null) {
                                                                    MfiCardFuncEntity.this._db.deleteMfiCardAdditionalInfo(myCardAdditionalInfo.getCid());
                                                                }
                                                            }
                                                            MfiCardFuncEntity.this._db.setMfiCardAdditionalInfo(listFetchCardAdditionalInfoList, arrayList2);
                                                        } catch (MfcException e6) {
                                                            e = e6;
                                                            MfiCardFuncEntity mfiCardFuncEntity3 = MfiCardFuncEntity.this;
                                                            mfiCardFuncEntity3._compiledState = mfiCardFuncEntity3.convertErrorToCompiledState(e);
                                                            MfiCardFuncEntity mfiCardFuncEntity4 = MfiCardFuncEntity.this;
                                                            mfiCardFuncEntity4.modelErrorInfo = mfiCardFuncEntity4._compiledState.getMficWarningState();
                                                            ArrayList arrayList4 = new ArrayList();
                                                            for (MfiCardAdditionalInfo mfiCardAdditionalInfo : MfiCardFuncEntity.this._db.getMfiCardAdditionalInfo()) {
                                                                arrayList4.add(CardFuncUtility.convertCardAdditionalDbToInfo(MfiCardFuncEntity.this._context, mfiCardAdditionalInfo, mfiCardAdditionalInfo.transitInfo));
                                                            }
                                                            for (int i3 = 0; i3 < MfiCardFuncEntity.this._runner.getMyCardInfoList().size(); i3++) {
                                                                MyCardInfo myCardInfo2 = MfiCardFuncEntity.this._runner.getMyCardInfoList().get(i3);
                                                                for (int i4 = 0; i4 < arrayList4.size(); i4++) {
                                                                    if (myCardInfo2.getCardId().equals(((MyCardAdditionalInfo) arrayList4.get(i4)).getCid())) {
                                                                        myCardInfo2.setCardAdditionalInfo((MyCardAdditionalInfo) arrayList4.get(i4));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    z2 = false;
                                                }
                                                ArrayList arrayList5 = new ArrayList();
                                                for (MfiCardAdditionalInfo mfiCardAdditionalInfo2 : MfiCardFuncEntity.this._db.getMfiCardAdditionalInfo()) {
                                                    arrayList5.add(CardFuncUtility.convertCardAdditionalDbToInfo(MfiCardFuncEntity.this._context, mfiCardAdditionalInfo2, mfiCardAdditionalInfo2.transitInfo));
                                                }
                                                for (int i5 = 0; i5 < MfiCardFuncEntity.this._runner.getMyCardInfoList().size(); i5++) {
                                                    MyCardInfo myCardInfo3 = MfiCardFuncEntity.this._runner.getMyCardInfoList().get(i5);
                                                    for (int i6 = 0; i6 < arrayList5.size(); i6++) {
                                                        if (myCardInfo3.getCardId().equals(((MyCardAdditionalInfo) arrayList5.get(i6)).getCid())) {
                                                            myCardInfo3.setCardAdditionalInfo((MyCardAdditionalInfo) arrayList5.get(i6));
                                                        }
                                                    }
                                                }
                                            } catch (MfcException e7) {
                                                e = e7;
                                                z2 = false;
                                            } catch (Throwable th4) {
                                                th = th4;
                                                ArrayList arrayList6 = new ArrayList();
                                                for (MfiCardAdditionalInfo mfiCardAdditionalInfo3 : MfiCardFuncEntity.this._db.getMfiCardAdditionalInfo()) {
                                                    arrayList6.add(CardFuncUtility.convertCardAdditionalDbToInfo(MfiCardFuncEntity.this._context, mfiCardAdditionalInfo3, mfiCardAdditionalInfo3.transitInfo));
                                                }
                                                for (int i7 = 0; i7 < MfiCardFuncEntity.this._runner.getMyCardInfoList().size(); i7++) {
                                                    MyCardInfo myCardInfo4 = MfiCardFuncEntity.this._runner.getMyCardInfoList().get(i7);
                                                    for (int i8 = 0; i8 < arrayList6.size(); i8++) {
                                                        if (myCardInfo4.getCardId().equals(((MyCardAdditionalInfo) arrayList6.get(i8)).getCid())) {
                                                            myCardInfo4.setCardAdditionalInfo((MyCardAdditionalInfo) arrayList6.get(i8));
                                                        }
                                                    }
                                                }
                                                throw th;
                                            }
                                            if (MfiCardFuncEntity.this._compiledState == null) {
                                                if (z2) {
                                                    MfiCardFuncEntity mfiCardFuncEntity5 = MfiCardFuncEntity.this;
                                                    mfiCardFuncEntity5._compiledState = mfiCardFuncEntity5.convertErrorToCompiledState(new MfcException(getClass(), 289, MfcException.CognitiveType.ILLEGAL_JSON_FORMAT));
                                                    MfiCardFuncEntity mfiCardFuncEntity6 = MfiCardFuncEntity.this;
                                                    mfiCardFuncEntity6.modelErrorInfo = mfiCardFuncEntity6._compiledState.getMficWarningState();
                                                } else {
                                                    MfiCardFuncEntity.this._compiledState = new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, MfiCardFuncEntity.this.modelErrorInfo);
                                                }
                                                MfiCardFuncEntity.this._isCompiled = true;
                                            }
                                            if (!((Boolean) asyncPacket.get()).booleanValue() && (!MfiCardFuncEntity.this._isLogined || !MfiCardFuncEntity.this._isCompiled)) {
                                                try {
                                                    MfiCardFuncEntity.this.orderSuicaPosition((List) Objects.requireNonNull(centralFunc.getAreaDetectedServiceIdList()));
                                                } catch (MfcException e8) {
                                                    MfiCardFuncEntity mfiCardFuncEntity7 = MfiCardFuncEntity.this;
                                                    mfiCardFuncEntity7._compiledState = mfiCardFuncEntity7.convertErrorToCompiledState(e8);
                                                    MfiCardFuncEntity mfiCardFuncEntity8 = MfiCardFuncEntity.this;
                                                    mfiCardFuncEntity8.modelErrorInfo = mfiCardFuncEntity8._compiledState.getMficWarningState();
                                                }
                                            }
                                            asyncRunnerForCompile2 = MfiCardFuncEntity.this._runner;
                                            compiledState = MfiCardFuncEntity.this._compiledState;
                                        } catch (MfcException e9) {
                                            e = e9;
                                            z4 = zIsEmpty;
                                            MfiCardFuncEntity mfiCardFuncEntity9 = MfiCardFuncEntity.this;
                                            mfiCardFuncEntity9._compiledState = mfiCardFuncEntity9.convertErrorToCompiledState(e);
                                            MfiCardFuncEntity mfiCardFuncEntity10 = MfiCardFuncEntity.this;
                                            mfiCardFuncEntity10.modelErrorInfo = mfiCardFuncEntity10._compiledState.getMficWarningState();
                                            if (MfiCardFuncEntity.this._compiledState == null) {
                                                if (z4) {
                                                    MfiCardFuncEntity mfiCardFuncEntity11 = MfiCardFuncEntity.this;
                                                    mfiCardFuncEntity11._compiledState = mfiCardFuncEntity11.convertErrorToCompiledState(new MfcException(getClass(), 289, MfcException.CognitiveType.ILLEGAL_JSON_FORMAT));
                                                    MfiCardFuncEntity mfiCardFuncEntity12 = MfiCardFuncEntity.this;
                                                    mfiCardFuncEntity12.modelErrorInfo = mfiCardFuncEntity12._compiledState.getMficWarningState();
                                                } else {
                                                    MfiCardFuncEntity.this._compiledState = new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, MfiCardFuncEntity.this.modelErrorInfo);
                                                }
                                                MfiCardFuncEntity.this._isCompiled = true;
                                            }
                                            if (!((Boolean) asyncPacket.get()).booleanValue() && (!MfiCardFuncEntity.this._isLogined || !MfiCardFuncEntity.this._isCompiled)) {
                                                try {
                                                    MfiCardFuncEntity.this.orderSuicaPosition((List) Objects.requireNonNull(centralFunc.getAreaDetectedServiceIdList()));
                                                } catch (MfcException e10) {
                                                    MfiCardFuncEntity mfiCardFuncEntity13 = MfiCardFuncEntity.this;
                                                    mfiCardFuncEntity13._compiledState = mfiCardFuncEntity13.convertErrorToCompiledState(e10);
                                                    MfiCardFuncEntity mfiCardFuncEntity14 = MfiCardFuncEntity.this;
                                                    mfiCardFuncEntity14.modelErrorInfo = mfiCardFuncEntity14._compiledState.getMficWarningState();
                                                }
                                            }
                                            asyncRunnerForCompile2 = MfiCardFuncEntity.this._runner;
                                            compiledState = MfiCardFuncEntity.this._compiledState;
                                        } catch (Throwable th5) {
                                            th = th5;
                                            z4 = zIsEmpty;
                                            if (MfiCardFuncEntity.this._compiledState == null) {
                                                if (z4) {
                                                    MfiCardFuncEntity mfiCardFuncEntity15 = MfiCardFuncEntity.this;
                                                    mfiCardFuncEntity15._compiledState = mfiCardFuncEntity15.convertErrorToCompiledState(new MfcException(getClass(), 289, MfcException.CognitiveType.ILLEGAL_JSON_FORMAT));
                                                    MfiCardFuncEntity mfiCardFuncEntity16 = MfiCardFuncEntity.this;
                                                    mfiCardFuncEntity16.modelErrorInfo = mfiCardFuncEntity16._compiledState.getMficWarningState();
                                                } else {
                                                    MfiCardFuncEntity.this._compiledState = new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, MfiCardFuncEntity.this.modelErrorInfo);
                                                }
                                                MfiCardFuncEntity.this._isCompiled = true;
                                            }
                                            if (!((Boolean) asyncPacket.get()).booleanValue() && (!MfiCardFuncEntity.this._isLogined || !MfiCardFuncEntity.this._isCompiled)) {
                                                try {
                                                    MfiCardFuncEntity.this.orderSuicaPosition((List) Objects.requireNonNull(centralFunc.getAreaDetectedServiceIdList()));
                                                } catch (MfcException e11) {
                                                    MfiCardFuncEntity mfiCardFuncEntity17 = MfiCardFuncEntity.this;
                                                    mfiCardFuncEntity17._compiledState = mfiCardFuncEntity17.convertErrorToCompiledState(e11);
                                                    MfiCardFuncEntity mfiCardFuncEntity18 = MfiCardFuncEntity.this;
                                                    mfiCardFuncEntity18.modelErrorInfo = mfiCardFuncEntity18._compiledState.getMficWarningState();
                                                }
                                            }
                                            MfiCardFuncEntity.this._runner.putSuccess(MfiCardFuncEntity.this._compiledState);
                                            throw th;
                                        }
                                        asyncRunnerForCompile2.putSuccess(compiledState);
                                        asyncRunnerForCompile = MfiCardFuncEntity.this._runner;
                                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.1.2
                                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                            public void go() {
                                                if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                                    MfiCardFuncEntity.this._isCompiled = false;
                                                    listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                                                } else if (MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                                    listener.onRequestActivity(MfiCardFuncEntity.this._runner.getIntentForRequestActivity());
                                                } else {
                                                    listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                                                }
                                                MfiCardFuncEntity.this._context.setMfiCardFuncRunner(null);
                                            }
                                        };
                                    } else {
                                        if (MfiCardFuncEntity.this._compiledState == null) {
                                            MfiCardFuncEntity.this._compiledState = new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, MfiCardFuncEntity.this.modelErrorInfo);
                                            MfiCardFuncEntity.this._isCompiled = true;
                                        }
                                        if (!((Boolean) asyncPacket.get()).booleanValue() && (!MfiCardFuncEntity.this._isLogined || !MfiCardFuncEntity.this._isCompiled)) {
                                            try {
                                                MfiCardFuncEntity.this.orderSuicaPosition((List) Objects.requireNonNull(centralFunc.getAreaDetectedServiceIdList()));
                                            } catch (MfcException e12) {
                                                MfiCardFuncEntity mfiCardFuncEntity19 = MfiCardFuncEntity.this;
                                                mfiCardFuncEntity19._compiledState = mfiCardFuncEntity19.convertErrorToCompiledState(e12);
                                                MfiCardFuncEntity mfiCardFuncEntity20 = MfiCardFuncEntity.this;
                                                mfiCardFuncEntity20.modelErrorInfo = mfiCardFuncEntity20._compiledState.getMficWarningState();
                                            }
                                        }
                                        MfiCardFuncEntity.this._runner.putSuccess(MfiCardFuncEntity.this._compiledState);
                                        asyncRunnerForCompile3 = MfiCardFuncEntity.this._runner;
                                        notify2 = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.1.2
                                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                            public void go() {
                                                if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                                    MfiCardFuncEntity.this._isCompiled = false;
                                                    listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                                                } else if (MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                                    listener.onRequestActivity(MfiCardFuncEntity.this._runner.getIntentForRequestActivity());
                                                } else {
                                                    listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                                                }
                                                MfiCardFuncEntity.this._context.setMfiCardFuncRunner(null);
                                            }
                                        };
                                    }
                                } else if (MfiCardFuncEntity.this._isConfirmCardRecovery) {
                                    MfiCardFuncEntity.this._isConfirmCardRecovery = false;
                                    asyncPacket.set(true);
                                    MfiCardFuncEntity.this._compiledState = new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NEED_CARD_RECOVERY, MfiCardFuncEntity.this.modelErrorInfo);
                                    if (MfiCardFuncEntity.this._compiledState == null) {
                                        MfiCardFuncEntity.this._compiledState = new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, MfiCardFuncEntity.this.modelErrorInfo);
                                        MfiCardFuncEntity.this._isCompiled = true;
                                    }
                                    if (!((Boolean) asyncPacket.get()).booleanValue() && (!MfiCardFuncEntity.this._isLogined || !MfiCardFuncEntity.this._isCompiled)) {
                                        try {
                                            MfiCardFuncEntity.this.orderSuicaPosition((List) Objects.requireNonNull(centralFunc.getAreaDetectedServiceIdList()));
                                        } catch (MfcException e13) {
                                            MfiCardFuncEntity mfiCardFuncEntity21 = MfiCardFuncEntity.this;
                                            mfiCardFuncEntity21._compiledState = mfiCardFuncEntity21.convertErrorToCompiledState(e13);
                                            MfiCardFuncEntity mfiCardFuncEntity22 = MfiCardFuncEntity.this;
                                            mfiCardFuncEntity22.modelErrorInfo = mfiCardFuncEntity22._compiledState.getMficWarningState();
                                        }
                                    }
                                    MfiCardFuncEntity.this._runner.putSuccess(MfiCardFuncEntity.this._compiledState);
                                    asyncRunnerForCompile3 = MfiCardFuncEntity.this._runner;
                                    notify2 = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.1.2
                                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                        public void go() {
                                            if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                                MfiCardFuncEntity.this._isCompiled = false;
                                                listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                                            } else if (MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                                listener.onRequestActivity(MfiCardFuncEntity.this._runner.getIntentForRequestActivity());
                                            } else {
                                                listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                                            }
                                            MfiCardFuncEntity.this._context.setMfiCardFuncRunner(null);
                                        }
                                    };
                                } else {
                                    MfiCardFuncEntity.this._hasUnrecoveredCard = true;
                                    MfiCardFuncEntity.this._isConfirmCardRecovery = true;
                                    zIsEmpty = MfiCardFuncEntity.this._runner.getMyCardInfoList().isEmpty();
                                    if (zIsEmpty) {
                                    }
                                }
                                FuncUtil.notifySafety(asyncRunnerForCompile, notify);
                                return;
                            }
                            if (MfiCardFuncEntity.this._compiledState == null) {
                                MfiCardFuncEntity.this._compiledState = new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, MfiCardFuncEntity.this.modelErrorInfo);
                                MfiCardFuncEntity.this._isCompiled = true;
                            }
                            if (!((Boolean) asyncPacket.get()).booleanValue() && (!MfiCardFuncEntity.this._isLogined || !MfiCardFuncEntity.this._isCompiled)) {
                                try {
                                    MfiCardFuncEntity.this.orderSuicaPosition((List) Objects.requireNonNull(centralFunc.getAreaDetectedServiceIdList()));
                                } catch (MfcException e14) {
                                    MfiCardFuncEntity mfiCardFuncEntity23 = MfiCardFuncEntity.this;
                                    mfiCardFuncEntity23._compiledState = mfiCardFuncEntity23.convertErrorToCompiledState(e14);
                                    MfiCardFuncEntity mfiCardFuncEntity24 = MfiCardFuncEntity.this;
                                    mfiCardFuncEntity24.modelErrorInfo = mfiCardFuncEntity24._compiledState.getMficWarningState();
                                }
                            }
                            MfiCardFuncEntity.this._runner.putSuccess(MfiCardFuncEntity.this._compiledState);
                            asyncRunnerForCompile3 = MfiCardFuncEntity.this._runner;
                            notify2 = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.1.2
                                @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                public void go() {
                                    if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                        MfiCardFuncEntity.this._isCompiled = false;
                                        listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                                    } else if (MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                        listener.onRequestActivity(MfiCardFuncEntity.this._runner.getIntentForRequestActivity());
                                    } else {
                                        listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                                    }
                                    MfiCardFuncEntity.this._context.setMfiCardFuncRunner(null);
                                }
                            };
                        }
                    } else {
                        if (MfiCardFuncEntity.this._compiledState == null) {
                            MfiCardFuncEntity.this._compiledState = new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, MfiCardFuncEntity.this.modelErrorInfo);
                            MfiCardFuncEntity.this._isCompiled = true;
                        }
                        if (!((Boolean) asyncPacket.get()).booleanValue() && (!MfiCardFuncEntity.this._isLogined || !MfiCardFuncEntity.this._isCompiled)) {
                            try {
                                MfiCardFuncEntity.this.orderSuicaPosition((List) Objects.requireNonNull(centralFunc.getAreaDetectedServiceIdList()));
                            } catch (MfcException e15) {
                                MfiCardFuncEntity mfiCardFuncEntity25 = MfiCardFuncEntity.this;
                                mfiCardFuncEntity25._compiledState = mfiCardFuncEntity25.convertErrorToCompiledState(e15);
                                MfiCardFuncEntity mfiCardFuncEntity26 = MfiCardFuncEntity.this;
                                mfiCardFuncEntity26.modelErrorInfo = mfiCardFuncEntity26._compiledState.getMficWarningState();
                            }
                        }
                        MfiCardFuncEntity.this._runner.putSuccess(MfiCardFuncEntity.this._compiledState);
                        asyncRunnerForCompile3 = MfiCardFuncEntity.this._runner;
                        notify2 = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.1.2
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                    MfiCardFuncEntity.this._isCompiled = false;
                                    listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                                } else if (MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                    listener.onRequestActivity(MfiCardFuncEntity.this._runner.getIntentForRequestActivity());
                                } else {
                                    listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                                }
                                MfiCardFuncEntity.this._context.setMfiCardFuncRunner(null);
                            }
                        };
                    }
                } else {
                    if (isBackground) {
                        throw new MfcException(getClass(), 290, MfcException.CognitiveType.NETWORK_ERROR);
                    }
                    if (MfiCardFuncEntity.this._compiledState == null) {
                        MfiCardFuncEntity.this._compiledState = new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, MfiCardFuncEntity.this.modelErrorInfo);
                        MfiCardFuncEntity.this._isCompiled = true;
                    }
                    if (!((Boolean) asyncPacket.get()).booleanValue() && (!MfiCardFuncEntity.this._isLogined || !MfiCardFuncEntity.this._isCompiled)) {
                        try {
                            MfiCardFuncEntity.this.orderSuicaPosition((List) Objects.requireNonNull(centralFunc.getAreaDetectedServiceIdList()));
                        } catch (MfcException e16) {
                            MfiCardFuncEntity mfiCardFuncEntity27 = MfiCardFuncEntity.this;
                            mfiCardFuncEntity27._compiledState = mfiCardFuncEntity27.convertErrorToCompiledState(e16);
                            MfiCardFuncEntity mfiCardFuncEntity28 = MfiCardFuncEntity.this;
                            mfiCardFuncEntity28.modelErrorInfo = mfiCardFuncEntity28._compiledState.getMficWarningState();
                        }
                    }
                    MfiCardFuncEntity.this._runner.putSuccess(MfiCardFuncEntity.this._compiledState);
                    asyncRunnerForCompile3 = MfiCardFuncEntity.this._runner;
                    notify2 = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.1.2
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                MfiCardFuncEntity.this._isCompiled = false;
                                listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                            } else if (MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                listener.onRequestActivity(MfiCardFuncEntity.this._runner.getIntentForRequestActivity());
                            } else {
                                listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                            }
                            MfiCardFuncEntity.this._context.setMfiCardFuncRunner(null);
                        }
                    };
                }
                FuncUtil.notifySafety(asyncRunnerForCompile3, notify2);
            }
        })) {
            throw new IllegalStateException("MfiCardFunc#Compile() is still executing.");
        }
    }

    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc
    public void compileCache(final CentralFunc centralFunc, final String lastAccountHash, final MfiCardFunc.CompileListener listener) {
        if (!this._runner.startup(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.2
            /* JADX DEBUG: Another duplicated slice has different insns count: {[IGET, INVOKE, INVOKE]}, finally: {[IGET, INVOKE] complete} */
            /* JADX DEBUG: Another duplicated slice has different insns count: {[IGET, INVOKE]}, finally: {[IGET, INVOKE, IGET, INVOKE, IGET, INVOKE, INVOKE, IGET, SGET, IGET, INVOKE, CONSTRUCTOR, INVOKE, IGET, INVOKE, IGET, INVOKE, IGET, INVOKE, INVOKE, IF] complete} */
            /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [650=6, 622=5, 624=5, 625=5, 627=5] */
            /* JADX DEBUG: Incorrect finally slice size: {[IGET, INVOKE, INVOKE] complete}, expected: {[IGET, INVOKE, IGET, INVOKE, IGET, INVOKE, INVOKE, IGET, SGET, IGET, INVOKE, CONSTRUCTOR, INVOKE, IGET, INVOKE, IGET, INVOKE, IGET, INVOKE, INVOKE, IF] complete} */
            /* JADX WARN: Finally extract failed */
            @Override // java.lang.Runnable
            public void run() {
                AsyncRunnerForCompile asyncRunnerForCompile;
                FuncUtil.Notify notify;
                MfcExpert initializedMfcExpert;
                AsyncRunnerForCompile asyncRunnerForCompile2;
                MfiCardFunc.CompiledState compiledState;
                String mfiAccountInfo;
                AsyncRunnerForCompile asyncRunnerForCompile3;
                FuncUtil.Notify notify2;
                MfiCardFuncEntity.this._compiledState = null;
                try {
                    try {
                        initializedMfcExpert = ModelContext.getInitializedMfcExpert();
                        try {
                            try {
                                initializedMfcExpert.felicaStart();
                                mfiAccountInfo = MfiCardFuncEntity.this.getMfiAccountInfo();
                            } catch (MfcException e) {
                                MfiCardFuncEntity mfiCardFuncEntity = MfiCardFuncEntity.this;
                                mfiCardFuncEntity._compiledState = mfiCardFuncEntity.convertErrorToCompiledState(e);
                                MfiCardFuncEntity mfiCardFuncEntity2 = MfiCardFuncEntity.this;
                                mfiCardFuncEntity2.modelErrorInfo = mfiCardFuncEntity2._compiledState.getMficWarningState();
                                if (MfiCardFuncEntity.this._compiledState == null) {
                                    MfiCardFuncEntity.this._compiledState = new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, MfiCardFuncEntity.this.modelErrorInfo);
                                    MfiCardFuncEntity.this._isCompiled = true;
                                }
                                asyncRunnerForCompile2 = MfiCardFuncEntity.this._runner;
                                compiledState = MfiCardFuncEntity.this._compiledState;
                            }
                        } catch (Throwable th) {
                            if (MfiCardFuncEntity.this._compiledState == null) {
                                MfiCardFuncEntity.this._compiledState = new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, MfiCardFuncEntity.this.modelErrorInfo);
                                MfiCardFuncEntity.this._isCompiled = true;
                            }
                            MfiCardFuncEntity.this._runner.putSuccess(MfiCardFuncEntity.this._compiledState);
                            throw th;
                        }
                    } catch (DatabaseException e2) {
                        MfiCardFuncEntity.this._runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.DB_ACCESS_ERROR, e2));
                        asyncRunnerForCompile = MfiCardFuncEntity.this._runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.2.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                    MfiCardFuncEntity.this._isCompiled = false;
                                    listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                                } else if (!MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                    listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                                } else {
                                    listener.onError(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(MfiCardFuncEntity.class, 514)));
                                }
                            }
                        };
                    } catch (InterruptedException unused) {
                        asyncRunnerForCompile = MfiCardFuncEntity.this._runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.2.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                    MfiCardFuncEntity.this._isCompiled = false;
                                    listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                                } else if (!MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                    listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                                } else {
                                    listener.onError(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(MfiCardFuncEntity.class, 514)));
                                }
                            }
                        };
                    } catch (NullPointerException unused2) {
                        asyncRunnerForCompile = MfiCardFuncEntity.this._runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.2.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                    MfiCardFuncEntity.this._isCompiled = false;
                                    listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                                } else if (!MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                    listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                                } else {
                                    listener.onError(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(MfiCardFuncEntity.class, 514)));
                                }
                            }
                        };
                    } catch (Exception e3) {
                        MfiCardFuncEntity.this._runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_RUNTIME_ERROR, new MfmException(MfiCardFuncEntity.class, InputDeviceCompat.SOURCE_DPAD, e3)));
                        asyncRunnerForCompile = MfiCardFuncEntity.this._runner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.2.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                    MfiCardFuncEntity.this._isCompiled = false;
                                    listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                                } else if (!MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                    listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                                } else {
                                    listener.onError(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(MfiCardFuncEntity.class, 514)));
                                }
                            }
                        };
                    }
                    if (TextUtils.equals(lastAccountHash, mfiAccountInfo)) {
                        MfiCardFuncEntity.this._runner.checkInterrupted();
                        MfcExpert.CardInfos cardInfosFetchCacheCardList = initializedMfcExpert.fetchCacheCardList(false);
                        MfiCardFuncEntity.this._runner.setMyCardInfoList(cardInfosFetchCacheCardList.getCardInfoList());
                        MfiCardFuncEntity.this._runner.setDeleteCardInfoList(cardInfosFetchCacheCardList.getDeleteCardInfoList());
                        MfiCardFuncEntity.this._isChaced = true;
                        if (!MfiCardFuncEntity.this._runner.getMyCardInfoList().isEmpty()) {
                            ArrayList arrayList = new ArrayList();
                            for (MfiCardAdditionalInfo mfiCardAdditionalInfo : MfiCardFuncEntity.this._db.getMfiCardAdditionalInfo()) {
                                arrayList.add(CardFuncUtility.convertCardAdditionalDbToInfo(MfiCardFuncEntity.this._context, mfiCardAdditionalInfo, mfiCardAdditionalInfo.transitInfo));
                            }
                            for (int i = 0; i < MfiCardFuncEntity.this._runner.getMyCardInfoList().size(); i++) {
                                MyCardInfo myCardInfo = MfiCardFuncEntity.this._runner.getMyCardInfoList().get(i);
                                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                                    if (myCardInfo.getCardId().equals(((MyCardAdditionalInfo) arrayList.get(i2)).getCid())) {
                                        myCardInfo.setCardAdditionalInfo((MyCardAdditionalInfo) arrayList.get(i2));
                                    }
                                }
                            }
                            if (MfiCardFuncEntity.this._compiledState == null) {
                                MfiCardFuncEntity.this._compiledState = new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, MfiCardFuncEntity.this.modelErrorInfo);
                                MfiCardFuncEntity.this._isCompiled = true;
                            }
                            asyncRunnerForCompile2 = MfiCardFuncEntity.this._runner;
                            compiledState = MfiCardFuncEntity.this._compiledState;
                            asyncRunnerForCompile2.putSuccess(compiledState);
                            asyncRunnerForCompile = MfiCardFuncEntity.this._runner;
                            notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.2.1
                                @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                                public void go() {
                                    if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                        MfiCardFuncEntity.this._isCompiled = false;
                                        listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                                    } else if (!MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                        listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                                    } else {
                                        listener.onError(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(MfiCardFuncEntity.class, 514)));
                                    }
                                }
                            };
                            FuncUtil.notifySafety(asyncRunnerForCompile, notify);
                            return;
                        }
                        if (MfiCardFuncEntity.this._compiledState == null) {
                            MfiCardFuncEntity.this._compiledState = new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, MfiCardFuncEntity.this.modelErrorInfo);
                            MfiCardFuncEntity.this._isCompiled = true;
                        }
                        MfiCardFuncEntity.this._runner.putSuccess(MfiCardFuncEntity.this._compiledState);
                        asyncRunnerForCompile3 = MfiCardFuncEntity.this._runner;
                        notify2 = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.2.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                    MfiCardFuncEntity.this._isCompiled = false;
                                    listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                                } else if (!MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                    listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                                } else {
                                    listener.onError(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(MfiCardFuncEntity.class, 514)));
                                }
                            }
                        };
                    } else {
                        MfiCardFuncEntity.this.clearAccountRelatedInfo();
                        MfiCardFuncEntity.this.clearCache();
                        listener.onChangedAccount(mfiAccountInfo, lastAccountHash);
                        if (MfiCardFuncEntity.this._compiledState == null) {
                            MfiCardFuncEntity.this._compiledState = new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, MfiCardFuncEntity.this.modelErrorInfo);
                            MfiCardFuncEntity.this._isCompiled = true;
                        }
                        MfiCardFuncEntity.this._runner.putSuccess(MfiCardFuncEntity.this._compiledState);
                        asyncRunnerForCompile3 = MfiCardFuncEntity.this._runner;
                        notify2 = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.2.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                    MfiCardFuncEntity.this._isCompiled = false;
                                    listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                                } else if (!MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                    listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                                } else {
                                    listener.onError(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(MfiCardFuncEntity.class, 514)));
                                }
                            }
                        };
                    }
                    FuncUtil.notifySafety(asyncRunnerForCompile3, notify2);
                } catch (Throwable th2) {
                    FuncUtil.notifySafety(MfiCardFuncEntity.this._runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.2.1
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                MfiCardFuncEntity.this._isCompiled = false;
                                listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                            } else if (!MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                            } else {
                                listener.onError(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(MfiCardFuncEntity.class, 514)));
                            }
                        }
                    });
                    throw th2;
                }
            }
        })) {
            throw new IllegalStateException("MfiCardFunc#Compile() is still executing.");
        }
    }

    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc
    public void executeCardRecovery(final MfiCardFunc.RecoveryListener listener) {
        this.recoveryRunner.startupOrThrow(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.3
            @Override // java.lang.Runnable
            public void run() {
                FuncUtil.AsyncRunner asyncRunner;
                FuncUtil.Notify notify;
                try {
                    try {
                        ModelContext unused = MfiCardFuncEntity.this._context;
                        ModelContext.getInitializedMfcExpert().executeCardRecovery();
                    } catch (MfcException e) {
                        MfiCardFuncEntity mfiCardFuncEntity = MfiCardFuncEntity.this;
                        mfiCardFuncEntity._recoveryErrorInfo = mfiCardFuncEntity.convertErrorToCompiledState(e).getMficWarningState();
                        asyncRunner = MfiCardFuncEntity.this.recoveryRunner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.3.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                listener.onCompleted();
                            }
                        };
                        FuncUtil.notifySafety(asyncRunner, notify);
                    } catch (InterruptedException unused2) {
                        asyncRunner = MfiCardFuncEntity.this.recoveryRunner;
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.3.1
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                listener.onCompleted();
                            }
                        };
                        FuncUtil.notifySafety(asyncRunner, notify);
                    }
                } finally {
                    FuncUtil.notifySafety(MfiCardFuncEntity.this.recoveryRunner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.3.1
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            listener.onCompleted();
                        }
                    });
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
    public boolean isChaced() {
        return this._isChaced && this._isCompiled;
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
    public void orderSuicaPosition(List<String> areaDetectServiceIdList) throws MfcException {
        if (!areaDetectServiceIdList.contains(FeliCaParams.SERVICE_ID_SUICA)) {
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
    public void mfiLogin(final int extraAccountCode, final boolean isTutrial, final MfiCardFunc.MfiLoginListener listener) {
        if (this._runner.startup(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.4
            /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, IGET, INVOKE, IGET, INVOKE, INVOKE, INVOKE, IGET, INVOKE, IGET, INVOKE, INVOKE] complete} */
            /* JADX DEBUG: Failed to insert an additional move for type inference into block B:34:0x0013 */
            /* JADX DEBUG: Multi-variable search result rejected for r0v15, resolved type: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert */
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
                                initializedMfcExpert.setSilentStartCode(extraAccountCode);
                                initializedMfcExpert.mfiLogin(isTutrial, new MfcExpert.MfiStartListener() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.4.1
                                    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                                    public void onSuccess(boolean executedSilentStart) {
                                        MfiCardFuncEntity.this._isLogined = executedSilentStart;
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
                                    public void errorResult(MfcException exception) {
                                        MfiCardFuncEntity.this._runner.putFailure(MfiCardFuncEntity.this.convertErrorToCompiledState(exception).getMficWarningState());
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
                        notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.4.2
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                    listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                                } else if (MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                    listener.onRequestActivity(MfiCardFuncEntity.this._runner.getIntentForRequestActivity());
                                } else {
                                    listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                                }
                            }
                        };
                    } catch (Throwable th) {
                        FuncUtil.notifySafety(MfiCardFuncEntity.this._runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.4.2
                            @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                            public void go() {
                                if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                    listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                                } else if (MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                    listener.onRequestActivity(MfiCardFuncEntity.this._runner.getIntentForRequestActivity());
                                } else {
                                    listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                                }
                            }
                        });
                        throw th;
                    }
                } catch (InterruptedException unused2) {
                    asyncRunnerForCompile = MfiCardFuncEntity.this._runner;
                    notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.4.2
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                            } else if (MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                listener.onRequestActivity(MfiCardFuncEntity.this._runner.getIntentForRequestActivity());
                            } else {
                                listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                            }
                        }
                    };
                } catch (NullPointerException unused3) {
                    asyncRunnerForCompile = MfiCardFuncEntity.this._runner;
                    notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.4.2
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                            } else if (MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                listener.onRequestActivity(MfiCardFuncEntity.this._runner.getIntentForRequestActivity());
                            } else {
                                listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                            }
                        }
                    };
                } catch (Exception e2) {
                    MfiCardFuncEntity.this._runner.putFailure(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_RUNTIME_ERROR, new MfmException(MfiCardFuncEntity.class, 258, e2)));
                    asyncRunnerForCompile = MfiCardFuncEntity.this._runner;
                    notify = new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity.4.2
                        @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                        public void go() {
                            if (MfiCardFuncEntity.this._runner.hasFailure()) {
                                listener.onError((ModelErrorInfo) MfiCardFuncEntity.this._runner.getFailure());
                            } else if (MfiCardFuncEntity.this._runner.hasIntentForRequestActivity()) {
                                listener.onRequestActivity(MfiCardFuncEntity.this._runner.getIntentForRequestActivity());
                            } else {
                                listener.onCompleted(MfiCardFuncEntity.this._runner.getMyCardInfoList(), (MfiCardFunc.CompiledState) MfiCardFuncEntity.this._runner.getSuccess());
                            }
                        }
                    };
                }
                FuncUtil.notifySafety(asyncRunnerForCompile, notify);
            }
        })) {
            return;
        }
        if (this._context.isUpdateCacheRunning()) {
            ModelContext.getInitializedMfcExpert().cancelBackgroundUpdate();
            mfiLogin(extraAccountCode, isTutrial, listener);
            return;
        }
        throw new IllegalStateException("MfiCardFunc#Compile() is still executing.");
    }

    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc
    public void finishBackground() {
        ModelContext.getInitializedMfcExpert().finishBackground();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearCache() {
        try {
            this._db.deleteCache();
        } catch (DatabaseException e) {
            LogUtil.error(e);
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.main.MfiCardFuncEntity$5, reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {
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
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFI_INTERRUPTED_ERROR.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFI_CARD_NOT_CACHED.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            int[] iArr2 = new int[MfiLoginResultCode.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode = iArr2;
            try {
                iArr2[MfiLoginResultCode.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode[MfiLoginResultCode.SUCCESS.ordinal()] = 2;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode[MfiLoginResultCode.CANCELLED_BY_ANDROID.ordinal()] = 3;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode[MfiLoginResultCode.CANCELLED_BY_USER_DISAGREE.ordinal()] = 4;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode[MfiLoginResultCode.CANCELLED_BY_USER_SKIPPED.ordinal()] = 5;
            } catch (NoSuchFieldError unused21) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MfiCardFunc.CompiledState convertErrorToCompiledState(MfcException e) {
        boolean zIsEmpty;
        switch (AnonymousClass5.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[e.getErrorType().ordinal()]) {
            case 1:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_OPEN_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_OPEN_ERROR, e));
            case 2:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_LOCK_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_LOCK_ERROR, e));
            case 3:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_SERVER_MAINTENANCE_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_SERVER_MAINTENANCE_ERROR, e));
            case 4:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_NETWORK_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_NETWORK_ERROR, e));
            case 5:
                ModelErrorInfo modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.MFIC_INVALID_REQUEST_TOKEN_ERROR, e);
                this._runner.putFailure(modelErrorInfo);
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_INVALID_REQUEST_TOKEN, modelErrorInfo);
            case 6:
            case 7:
                ModelErrorInfo modelErrorInfo2 = new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, e);
                this._runner.putFailure(modelErrorInfo2);
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.OTHER_ERROR, modelErrorInfo2);
            case 8:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_FELICA_HTTP_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.FELICA_HTTP_ERROR, e));
            case 9:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_LIB_UNAVAILABLE, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_LIB_UNAVAILABLE, e));
            case 10:
                ModelErrorInfo modelErrorInfo3 = new ModelErrorInfo(ModelErrorInfo.Type.MFIC_VERSION_ERROR, e);
                this._runner.putFailure(modelErrorInfo3);
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM, modelErrorInfo3);
            case 11:
                try {
                    zIsEmpty = this._db.getMfiCardAdditionalInfo().isEmpty();
                    break;
                } catch (Exception unused) {
                    zIsEmpty = false;
                }
                if (zIsEmpty) {
                    return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_JSON_ERROR_NO_CASHE, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_JSON_ERROR_NO_CASHE, e));
                }
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_JSON_ERROR_USE_CACHE, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_JSON_ERROR_USE_CACHE, e));
            case 12:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_LOGIN_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_LOGIN_ERROR, e));
            case 13:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_USED_BY_OTHER_APP, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_USED_BY_OTHER_APP, e));
            case 14:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_RUNNING_BY_ITSELF, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_RUNNING_BY_ITSELF, e));
            case 15:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_INTERRUPTED_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_INTERRUPTED_ERROR, e));
            case 16:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_CARD_NOT_CACHED, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_CARD_NOT_CACHED, e));
            default:
                return new MfiCardFunc.CompiledState(MfiCardFunc.CompiledState.MfiCardState.MFIC_OTHER_ERROR, new ModelErrorInfo(ModelErrorInfo.Type.MFIC_OTHER_ERROR, e));
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

        void setMyCardInfoList(List<MyCardInfo> myCardInfoList) {
            this.myCardDetectSet.clear();
            if (myCardInfoList != null) {
                Iterator<MyCardInfo> it = myCardInfoList.iterator();
                while (it.hasNext()) {
                    this.myCardDetectSet.add(it.next().getServiceId());
                }
                this.myCardInfoList = myCardInfoList;
            }
        }

        void setDeleteCardInfoList(List<MyCardInfo> deleteCardInfoList) {
            if (deleteCardInfoList != null) {
                this.deleteCardInfoList = deleteCardInfoList;
            }
        }

        void setSuicaPosition(MySuicaInfo.Position suicaPosition) {
            this.suicaPosition = suicaPosition;
            ModelContext unused = MfiCardFuncEntity.this._context;
            ModelContext.getInitializedMfcExpert().setSuicaPosition(suicaPosition);
        }

        boolean hasIntentForRequestActivity() {
            return this.intentForRequestActivity != null;
        }

        Intent getIntentForRequestActivity() {
            return this.intentForRequestActivity;
        }

        void setIntentForRequestActivity(Intent intentForRequestActivity) {
            this.intentForRequestActivity = intentForRequestActivity;
        }
    }
}
