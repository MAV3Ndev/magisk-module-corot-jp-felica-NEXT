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

/* JADX INFO: loaded from: classes.dex */
class FeliCaStatusManager {
    private MfmFelicaAccess _mfmFelicaAccess;
    private FelicaAccess.Layout layout;
    private MfcExpert.MfiStartListener mfiStartListener;
    private boolean isLoggedIn = false;
    private boolean login = false;
    private boolean clearAccount = false;
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

        StartSuccessPacket(MfcExpert.MfiStartListener mfiStartListener, boolean z) {
            this.mfiStartListener = mfiStartListener;
            this.executedSilentStart = z;
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

        StartOnError(MfcExpert.MfiStartListener mfiStartListener, MfcException mfcException) {
            this.mfiStartListener = mfiStartListener;
            this.exception = mfcException;
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
                            public void errorResult(MfcException mfcException) {
                                map.put(AnonymousClass1.KEY_EXCEPTION, mfcException);
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
                            public void onSuccess(boolean z, User user, MfiAdmin mfiAdmin) {
                                MfmFelicaAccess.setMfiUser(user);
                                MfmFelicaAccess.setMfiAdmin(mfiAdmin);
                                feliCaStatusManager.isLoggedIn = true;
                                FeliCaStatusManager feliCaStatusManager2 = feliCaStatusManager;
                                feliCaStatusManager2.packet = new StartSuccessPacket(feliCaStatusManager2.mfiStartListener, z);
                                feliCaStatusManager.latcher.go();
                            }

                            @Override // com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.MfiAccessListener
                            public void onRequestActivity(Intent intent) {
                                FeliCaStatusManager feliCaStatusManager2 = feliCaStatusManager;
                                feliCaStatusManager2.packet = new StartRequestActivityPacket(feliCaStatusManager2.mfiStartListener, intent);
                                feliCaStatusManager.latcher.go();
                            }

                            @Override // com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.MfiAccessListener
                            public void onSuccessNoLogin(MfiAdmin mfiAdmin) {
                                MfmFelicaAccess.setMfiAdmin(mfiAdmin);
                                FeliCaStatusManager feliCaStatusManager2 = feliCaStatusManager;
                                feliCaStatusManager2.packet = new StartSuccessNoLoginPacket(feliCaStatusManager2.mfiStartListener);
                                feliCaStatusManager.latcher.go();
                            }

                            @Override // com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.MfiAccessListener
                            public void errorResult(MfcException mfcException) {
                                FeliCaStatusManager feliCaStatusManager2 = feliCaStatusManager;
                                feliCaStatusManager2.packet = new StartOnError(feliCaStatusManager2.mfiStartListener, mfcException);
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

        /* synthetic */ Action(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public enum FeliCaState {
        NONE { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState.1
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState
            List<Action> toStates(FeliCaState feliCaState) {
                int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$FeliCaStatusManager$FeliCaState[feliCaState.ordinal()];
                if (i != 1) {
                    return i != 2 ? i != 3 ? Collections.emptyList() : Arrays.asList(Action.ACTIVATE, Action.START) : Arrays.asList(Action.ACTIVATE, Action.OPEN);
                }
                return Collections.singletonList(Action.ACTIVATE);
            }
        },
        ACTIVATEING { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState.2
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState
            List<Action> toStates(FeliCaState feliCaState) {
                return Collections.emptyList();
            }
        },
        ACTIVATED { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState.3
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState
            List<Action> toStates(FeliCaState feliCaState) {
                int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$FeliCaStatusManager$FeliCaState[feliCaState.ordinal()];
                if (i == 2) {
                    return Collections.singletonList(Action.OPEN);
                }
                if (i == 3) {
                    return Collections.singletonList(Action.START);
                }
                return Collections.emptyList();
            }
        },
        OPENED { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState.4
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState
            List<Action> toStates(FeliCaState feliCaState) {
                int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$FeliCaStatusManager$FeliCaState[feliCaState.ordinal()];
                if (i != 1) {
                    return i != 3 ? Collections.emptyList() : Arrays.asList(Action.CLOSE, Action.START);
                }
                return Collections.singletonList(Action.CLOSE);
            }
        },
        STARTING { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState.5
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState
            List<Action> toStates(FeliCaState feliCaState) {
                return Collections.emptyList();
            }
        },
        STARTED { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState.6
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager.FeliCaState
            List<Action> toStates(FeliCaState feliCaState) {
                int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$FeliCaStatusManager$FeliCaState[feliCaState.ordinal()];
                if (i == 1) {
                    return Collections.singletonList(Action.STOP);
                }
                if (i == 2) {
                    return Arrays.asList(Action.STOP, Action.OPEN);
                }
                if (i == 3) {
                    return Collections.singletonList(Action.NOTIFY_START);
                }
                return Collections.emptyList();
            }
        };

        abstract List<Action> toStates(FeliCaState feliCaState);

        /* synthetic */ FeliCaState(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    FeliCaStatusManager(MfmFelicaAccess mfmFelicaAccess) {
        this._mfmFelicaAccess = mfmFelicaAccess;
    }

    synchronized void changeState(FeliCaState feliCaState) throws MfcException {
        MfcException e;
        try {
            for (Action action : this.state.toStates(feliCaState)) {
                try {
                    int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$FeliCaStatusManager$Action[action.ordinal()];
                    if (i == 1) {
                        this.state = FeliCaState.ACTIVATEING;
                    } else if (i == 2) {
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
        if (this.packet != null && this.packet.isError()) {
            forceFinish();
        }
        if (this.packet != null) {
            this.packet.post();
            this.packet = null;
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$FeliCaStatusManager$Action;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$FeliCaStatusManager$FeliCaState;

        static {
            int[] iArr = new int[Action.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$FeliCaStatusManager$Action = iArr;
            try {
                iArr[Action.ACTIVATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$FeliCaStatusManager$Action[Action.START.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[FeliCaState.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$FeliCaStatusManager$FeliCaState = iArr2;
            try {
                iArr2[FeliCaState.ACTIVATED.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$FeliCaStatusManager$FeliCaState[FeliCaState.OPENED.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$FeliCaStatusManager$FeliCaState[FeliCaState.STARTED.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$FeliCaStatusManager$FeliCaState[FeliCaState.STARTING.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    void setStartSettings(boolean z, boolean z2, FelicaAccess.Layout layout, MfcExpert.MfiStartListener mfiStartListener) {
        this.login = z;
        this.clearAccount = z2;
        this.layout = layout;
        this.mfiStartListener = mfiStartListener;
    }

    void forceFinish() {
        this.latcher.interrupt();
        synchronized (this) {
            int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$FeliCaStatusManager$FeliCaState[this.state.ordinal()];
            if (i == 2) {
                this._mfmFelicaAccess.close();
            } else if (i == 3 || i == 4) {
                this._mfmFelicaAccess.stop();
            }
            if (this.state != FeliCaState.NONE) {
                this._mfmFelicaAccess.inactivateFelica();
            }
            this.mfiStartListener = null;
            this.state = FeliCaState.NONE;
        }
    }

    boolean isLoggedIn() {
        return this.isLoggedIn;
    }

    public String toString() {
        return "FeliCaStatusManager$AreaId{login:" + this.login + ",clearAccount:" + this.clearAccount + ",clearAccount:" + this.clearAccount + ",layout:" + this.layout + "}";
    }
}
