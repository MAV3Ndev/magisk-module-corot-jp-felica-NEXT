package com.felicanetworks.mfm.main.model.internal.main.mfc;

import android.content.Intent;
import android.os.SystemClock;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccess;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccessException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccessListener;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.MfiAccessListener;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.MfiAdmin;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.User;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes3.dex */
class FeliCaStatusManager {
    private MfmFelicaAccess _mfmFelicaAccess;
    private FelicaAccess.Layout layout;
    private MfcExpert.MfiStartListener mfiStartListener;
    private boolean isLoggedIn = false;
    private boolean login = false;
    private boolean clearAccount = false;
    private boolean isBackground = false;
    private CountDownLatch backgroundLatch = null;
    private CallbackPacket packet = null;
    private Latcher latcher = new Latcher(null);
    private FeliCaState state = FeliCaState.NONE;

    private interface CallbackPacket {
        boolean isError();

        void post();
    }

    private static class StartSuccessPacket implements CallbackPacket {
        private final boolean executedSilentStart;
        private final MfcExpert.MfiStartListener mfiStartListener;

        @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.CallbackPacket
        public boolean isError() {
            return false;
        }

        StartSuccessPacket(MfcExpert.MfiStartListener mfiStartListener, boolean executedSilentStart) {
            this.mfiStartListener = mfiStartListener;
            this.executedSilentStart = executedSilentStart;
        }

        @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.CallbackPacket
        public void post() {
            this.mfiStartListener.onSuccess(this.executedSilentStart);
        }
    }

    private static class StartRequestActivityPacket implements CallbackPacket {
        private final Intent intent;
        private final MfcExpert.MfiStartListener mfiStartListener;

        @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.CallbackPacket
        public boolean isError() {
            return false;
        }

        StartRequestActivityPacket(MfcExpert.MfiStartListener mfiStartListener, Intent intent) {
            this.mfiStartListener = mfiStartListener;
            this.intent = intent;
        }

        @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.CallbackPacket
        public void post() {
            this.mfiStartListener.onRequestActivity(this.intent);
        }
    }

    private static class StartSuccessNoLoginPacket implements CallbackPacket {
        private final MfcExpert.MfiStartListener mfiStartListener;

        @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.CallbackPacket
        public boolean isError() {
            return false;
        }

        StartSuccessNoLoginPacket(MfcExpert.MfiStartListener mfiStartListener) {
            this.mfiStartListener = mfiStartListener;
        }

        @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.CallbackPacket
        public void post() {
            this.mfiStartListener.onSuccessNoLogin();
        }
    }

    private static class StartOnError implements CallbackPacket {
        private final MfcException exception;
        private final MfcExpert.MfiStartListener mfiStartListener;

        @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.CallbackPacket
        public boolean isError() {
            return true;
        }

        StartOnError(MfcExpert.MfiStartListener mfiStartListener, MfcException exception) {
            this.mfiStartListener = mfiStartListener;
            this.exception = exception;
        }

        @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.CallbackPacket
        public void post() {
            this.mfiStartListener.errorResult(this.exception);
        }
    }

    private static class Latcher {
        private boolean interrupted;
        private CountDownLatch latch;

        private Latcher() {
        }

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:148) call: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.Latcher.<init>():void type: THIS */
        /* synthetic */ Latcher(AnonymousClass1 anonymousClass1) {
            this();
        }

        void begin() {
            this.latch = new CountDownLatch(1);
            this.interrupted = false;
        }

        void go() {
            CountDownLatch countDownLatch = this.latch;
            if (countDownLatch == null) {
                return;
            }
            countDownLatch.countDown();
        }

        void await() throws InterruptedException {
            CountDownLatch countDownLatch = this.latch;
            if (countDownLatch == null) {
                return;
            }
            countDownLatch.await();
            if (this.interrupted) {
                throw new InterruptedException();
            }
        }

        void interrupt() {
            this.interrupted = true;
            go();
        }
    }

    private enum Action {
        ACTIVATE { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.Action.1
            private static final String KEY_EXCEPTION = "exception";

            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.Action
            FeliCaState action(final FeliCaStatusManager feliCaStatusManager) throws MfcException, InterruptedException {
                boolean z;
                final HashMap map = new HashMap();
                int i = 0;
                do {
                    z = i < 5;
                    map.clear();
                    feliCaStatusManager.latcher.begin();
                    try {
                        feliCaStatusManager._mfmFelicaAccess.activateFelica(new FelicaAccessListener() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.Action.1.1
                            @Override // com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccessListener
                            public void finishResult() {
                                feliCaStatusManager.latcher.go();
                            }

                            @Override // com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccessListener
                            public void errorResult(MfcException exception) {
                                map.put(AnonymousClass1.KEY_EXCEPTION, exception);
                                feliCaStatusManager.latcher.go();
                            }
                        });
                        feliCaStatusManager.latcher.await();
                    } catch (FelicaAccessException e) {
                        map.put(KEY_EXCEPTION, new MfcException(FeliCaStatusManager.class, 16, e));
                    }
                    MfcException mfcException = (MfcException) map.get(KEY_EXCEPTION);
                    if (mfcException == null || !mfcException.isRunningMfic()) {
                        break;
                    }
                    if (z) {
                        SystemClock.sleep(1000L);
                        i++;
                    }
                } while (z);
                if (map.containsKey(KEY_EXCEPTION)) {
                    MfcException mfcException2 = (MfcException) map.get(KEY_EXCEPTION);
                    if (mfcException2.isRunningMfic()) {
                        throw new MfcException(FeliCaStatusManager.class, 17, MfcException.CognitiveType.RUNNING_BY_MFIC);
                    }
                    throw mfcException2;
                }
                return FeliCaState.ACTIVATED;
            }
        },
        INACTIVATE { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.Action.2
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.Action
            FeliCaState action(FeliCaStatusManager feliCaStatusManager) {
                feliCaStatusManager._mfmFelicaAccess.inactivateFelica();
                return FeliCaState.NONE;
            }
        },
        OPEN { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.Action.3
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.Action
            FeliCaState action(FeliCaStatusManager feliCaStatusManager) throws MfcException {
                try {
                    feliCaStatusManager._mfmFelicaAccess.open();
                    return FeliCaState.OPENED;
                } catch (FelicaAccessException e) {
                    throw new MfcException(FeliCaStatusManager.class, 32, e);
                }
            }
        },
        CLOSE { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.Action.4
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.Action
            FeliCaState action(FeliCaStatusManager feliCaStatusManager) {
                feliCaStatusManager._mfmFelicaAccess.close();
                return FeliCaState.ACTIVATED;
            }
        },
        START { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.Action.5
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.Action
            FeliCaState action(final FeliCaStatusManager feliCaStatusManager) throws MfcException, InterruptedException {
                if (feliCaStatusManager.mfiStartListener != null) {
                    feliCaStatusManager.latcher.begin();
                    try {
                        feliCaStatusManager.isLoggedIn = false;
                        feliCaStatusManager._mfmFelicaAccess.silentStart(feliCaStatusManager.clearAccount, feliCaStatusManager.login, feliCaStatusManager.layout, new MfiAccessListener() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.Action.5.1
                            @Override // com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.MfiAccessListener
                            public void onSuccess(boolean executedSilentStart, User user, MfiAdmin mfiAdmin) {
                                MfmFelicaAccess.setMfiUser(user);
                                MfmFelicaAccess.setMfiAdmin(mfiAdmin);
                                feliCaStatusManager.isLoggedIn = true;
                                feliCaStatusManager.packet = new StartSuccessPacket(feliCaStatusManager.mfiStartListener, executedSilentStart);
                                feliCaStatusManager.latcher.go();
                            }

                            @Override // com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.MfiAccessListener
                            public void onRequestActivity(Intent intent) {
                                feliCaStatusManager.packet = new StartRequestActivityPacket(feliCaStatusManager.mfiStartListener, intent);
                                feliCaStatusManager.latcher.go();
                            }

                            @Override // com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.MfiAccessListener
                            public void onSuccessNoLogin(MfiAdmin mfiAdmin) {
                                MfmFelicaAccess.setMfiAdmin(mfiAdmin);
                                feliCaStatusManager.packet = new StartSuccessNoLoginPacket(feliCaStatusManager.mfiStartListener);
                                feliCaStatusManager.latcher.go();
                            }

                            @Override // com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.MfiAccessListener
                            public void errorResult(MfcException exception) {
                                feliCaStatusManager.packet = new StartOnError(feliCaStatusManager.mfiStartListener, exception);
                                feliCaStatusManager.latcher.go();
                            }
                        });
                        feliCaStatusManager.latcher.await();
                        return FeliCaState.STARTED;
                    } catch (MfiClientException e) {
                        throw new MfcException(FeliCaStatusManager.class, 48, e);
                    } catch (IllegalArgumentException e2) {
                        throw new MfcException(FeliCaStatusManager.class, 49, e2);
                    }
                }
                throw new IllegalArgumentException("");
            }
        },
        STOP { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.Action.6
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.Action
            FeliCaState action(FeliCaStatusManager feliCaStatusManager) {
                feliCaStatusManager._mfmFelicaAccess.stop();
                return FeliCaState.ACTIVATED;
            }
        },
        NOTIFY_START { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.Action.7
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.Action
            FeliCaState action(FeliCaStatusManager feliCaStatusManager) {
                feliCaStatusManager.packet = new StartSuccessPacket(feliCaStatusManager.mfiStartListener, true);
                return FeliCaState.STARTED;
            }
        };

        abstract FeliCaState action(FeliCaStatusManager feliCaStatusManager) throws MfcException, InterruptedException;

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR (r1v0 java.lang.String), (r2v0 int) A[MD:(java.lang.String, int):void (m)] (LINE:181) call: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.Action.<init>(java.lang.String, int):void type: THIS */
        /* synthetic */ Action(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public enum FeliCaState {
        NONE { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState.1
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState
            List<Action> toStates(FeliCaState toState) {
                int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$FeliCaStatusManager$FeliCaState[toState.ordinal()];
                if (i != 1) {
                    return i != 2 ? i != 3 ? Collections.EMPTY_LIST : Arrays.asList(Action.ACTIVATE, Action.START) : Arrays.asList(Action.ACTIVATE, Action.OPEN);
                }
                return Collections.singletonList(Action.ACTIVATE);
            }
        },
        ACTIVATEING { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState.2
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState
            List<Action> toStates(FeliCaState toState) {
                return Collections.EMPTY_LIST;
            }
        },
        ACTIVATED { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState.3
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState
            List<Action> toStates(FeliCaState toState) {
                int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$FeliCaStatusManager$FeliCaState[toState.ordinal()];
                if (i == 2) {
                    return Collections.singletonList(Action.OPEN);
                }
                if (i == 3) {
                    return Collections.singletonList(Action.START);
                }
                return Collections.EMPTY_LIST;
            }
        },
        OPENED { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState.4
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState
            List<Action> toStates(FeliCaState toState) {
                int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$FeliCaStatusManager$FeliCaState[toState.ordinal()];
                if (i != 1) {
                    return i != 3 ? Collections.EMPTY_LIST : Arrays.asList(Action.CLOSE, Action.START);
                }
                return Collections.singletonList(Action.CLOSE);
            }
        },
        STARTING { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState.5
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState
            List<Action> toStates(FeliCaState toState) {
                return Collections.EMPTY_LIST;
            }
        },
        STARTED { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState.6
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState
            List<Action> toStates(FeliCaState toState) {
                int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$FeliCaStatusManager$FeliCaState[toState.ordinal()];
                if (i == 1) {
                    return Collections.singletonList(Action.STOP);
                }
                if (i == 2) {
                    return Arrays.asList(Action.STOP, Action.OPEN);
                }
                if (i == 3) {
                    return Collections.singletonList(Action.NOTIFY_START);
                }
                return Collections.EMPTY_LIST;
            }
        };

        abstract List<Action> toStates(FeliCaState toState);

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR (r1v0 java.lang.String), (r2v0 int) A[MD:(java.lang.String, int):void (m)] (LINE:381) call: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState.<init>(java.lang.String, int):void type: THIS */
        /* synthetic */ FeliCaState(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$FeliCaStatusManager$FeliCaState;

        static {
            int[] iArr = new int[FeliCaState.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$FeliCaStatusManager$FeliCaState = iArr;
            try {
                iArr[FeliCaState.ACTIVATED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$FeliCaStatusManager$FeliCaState[FeliCaState.OPENED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$FeliCaStatusManager$FeliCaState[FeliCaState.STARTED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    FeliCaStatusManager(MfmFelicaAccess mfmFelicaAccess) {
        this._mfmFelicaAccess = mfmFelicaAccess;
    }

    synchronized void changeState(FeliCaState toState) throws MfcException {
        MfcException e;
        try {
            for (Action action : this.state.toStates(toState)) {
                try {
                    int iOrdinal = action.ordinal();
                    if (iOrdinal == 0) {
                        this.state = FeliCaState.ACTIVATEING;
                    } else if (iOrdinal == 4) {
                        this.state = FeliCaState.STARTING;
                    }
                    this.state = action.action(this);
                } catch (MfcException e2) {
                    e = e2;
                    LogUtil.error(e);
                }
            }
            e = null;
        } catch (InterruptedException unused) {
            forceFinish();
        }
        if (e != null) {
            forceFinish();
            throw e;
        }
        CallbackPacket callbackPacket = this.packet;
        if (callbackPacket != null && callbackPacket.isError()) {
            forceFinish();
        }
        CallbackPacket callbackPacket2 = this.packet;
        if (callbackPacket2 != null) {
            callbackPacket2.post();
            this.packet = null;
        }
    }

    void setStartSettings(boolean login, boolean clearAccount, FelicaAccess.Layout layout, MfcExpert.MfiStartListener mfiStartListener) {
        this.login = login;
        this.clearAccount = clearAccount;
        this.layout = layout;
        this.mfiStartListener = mfiStartListener;
    }

    void setStartSettings(boolean login, boolean clearAccount, FelicaAccess.Layout layout, boolean background, MfcExpert.MfiStartListener mfiStartListener) {
        this.login = login;
        this.clearAccount = clearAccount;
        this.layout = layout;
        this.isBackground = background;
        this.mfiStartListener = mfiStartListener;
    }

    void forceFinish() {
        this.latcher.interrupt();
        synchronized (this) {
            int iOrdinal = this.state.ordinal();
            if (iOrdinal == 3) {
                this._mfmFelicaAccess.close();
            } else if (iOrdinal == 4 || iOrdinal == 5) {
                this._mfmFelicaAccess.stop();
            }
            if (this.state != FeliCaState.NONE) {
                this._mfmFelicaAccess.inactivateFelica();
            }
            this.mfiStartListener = null;
            this.state = FeliCaState.NONE;
        }
    }

    void forceStop() {
        this.latcher.interrupt();
        try {
            this._mfmFelicaAccess.stop();
        } catch (Exception e) {
            LogUtil.error(e);
        }
    }

    boolean isLoggedIn() {
        return this.isLoggedIn;
    }

    public String toString() {
        return "FeliCaStatusManager$AreaId{login:" + this.login + ",clearAccount:" + this.clearAccount + ",clearAccount:" + this.clearAccount + ",layout:" + this.layout + "}";
    }

    public void finishBackground() {
        this.isBackground = false;
        CountDownLatch countDownLatch = this.backgroundLatch;
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    public void cancelCardOperation() {
        if (this.isBackground) {
            synchronized (this) {
                if (this.state.equals(FeliCaState.STARTED)) {
                    this.backgroundLatch = new CountDownLatch(1);
                    try {
                        try {
                            this._mfmFelicaAccess.cancelCardOperation();
                            this.backgroundLatch.await(60L, TimeUnit.SECONDS);
                        } catch (MfiClientException e) {
                            LogUtil.error(e);
                        } catch (InterruptedException unused) {
                        }
                    } finally {
                        this.backgroundLatch = null;
                    }
                }
            }
        }
    }
}
