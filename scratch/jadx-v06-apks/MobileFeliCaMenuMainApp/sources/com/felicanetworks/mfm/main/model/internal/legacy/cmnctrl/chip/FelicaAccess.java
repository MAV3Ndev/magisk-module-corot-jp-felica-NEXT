package com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip;

import android.content.Intent;
import androidx.core.view.InputDeviceCompat;
import com.felicanetworks.mfc.AppInfo;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.data.IssueStateData;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.AppContext;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.CommonUtil;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;
import com.felicanetworks.mfm.main.model.internal.main.mfc.em.ActivateCallbackInfo;
import com.felicanetworks.mfm.main.model.internal.main.mfc.em.MficApiCallbackInfo;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.mfcutil.mfc.Felica;
import com.felicanetworks.mfm.mfcutil.mfc.FelicaEventListener;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.MfiAdmin;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.MfiClient;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.SilentStartForMfiAdminEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.StopEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.User;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes.dex */
public class FelicaAccess {
    protected static final int CODESTR_BASEMENT = 16;
    protected static final int NODE_CODE_SIZE = 4;
    protected static MfiAdmin _mfiAdmin;
    protected static User mfiUser;
    protected AppContext context;
    private FelicaAccessListener faListener = null;
    private ActivateFelicaClient client = new ActivateFelicaClient();
    private MfiAccessListener mfiListener = null;
    private CountDownLatch _stopLatch = null;
    private int _silentStartAccountCode = 0;
    private int lastSelectSystemCode = 0;
    private String lastSelectCid = "";

    public enum Layout {
        LAYOUT_TYPE_SIGN_IN_WITH_TERMS,
        LAYOUT_TYPE_SKIPPABLE_SIGN_IN,
        LAYOUT_TYPE_SIGN_IN_ONLY
    }

    protected FelicaAccess(AppContext appContext) {
        this.context = appContext;
    }

    public synchronized void activateFelica(FelicaAccessListener felicaAccessListener) throws FelicaAccessException {
        try {
            if (this.faListener != null) {
                throw new IllegalStateException("Activation is already running.");
            }
            String[] strArr = {Felica.MFI_PERMIT};
            this.faListener = felicaAccessListener;
            if (this.context == null || this.context.felica == null) {
                throw new IllegalStateException("activateFelica cannot be executed");
            }
            this.context.felica.activateFelica(strArr, this.client);
        } catch (FelicaException e) {
            LogUtil.warning(e);
            throw new FelicaAccessException((Class) getClass(), 257, e);
        } catch (Exception e2) {
            LogUtil.warning(e2);
            throw new FelicaAccessException(getClass(), 258, e2);
        }
    }

    public void open() throws FelicaAccessException {
        this.lastSelectSystemCode = 0;
        this.lastSelectCid = "";
        try {
            this.context.felica.open();
            this.context.felica.setTimeout(((Integer) Sg.getValue(Sg.Key.FELICA_CHIP_TIMEOUT_VALUE)).intValue());
            this.context.felica.setRetryCount(((Integer) Sg.getValue(Sg.Key.FELICA_CHIP_RETRY_COUNT)).intValue());
        } catch (FelicaException e) {
            LogUtil.warning(e);
            throw new FelicaAccessException((Class) getClass(), 1, e);
        }
    }

    protected class ActivateFelicaClient implements FelicaEventListener {
        ActivateFelicaClient() {
        }

        @Override // com.felicanetworks.mfm.mfcutil.mfc.FelicaEventListener
        public void errorOccurred(int i, String str, AppInfo appInfo) {
            FelicaAccessListener felicaAccessListener;
            MfcException mfcException = new MfcException(FelicaAccess.class, InputDeviceCompat.SOURCE_DPAD, new ActivateCallbackInfo(i, str, appInfo));
            synchronized (FelicaAccess.this) {
                felicaAccessListener = null;
                if (FelicaAccess.this.faListener != null) {
                    FelicaAccessListener felicaAccessListener2 = FelicaAccess.this.faListener;
                    FelicaAccess.this.faListener = null;
                    felicaAccessListener = felicaAccessListener2;
                }
            }
            if (felicaAccessListener != null) {
                felicaAccessListener.errorResult(mfcException);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x0019, code lost:
        
            if (r0 == null) goto L32;
         */
        /* JADX WARN: Code restructure failed: missing block: B:12:0x001b, code lost:
        
            r0.finishResult();
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:?, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:?, code lost:
        
            return;
         */
        @Override // com.felicanetworks.mfm.mfcutil.mfc.FelicaEventListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void finished() throws java.lang.Throwable {
            /*
                r6 = this;
                r0 = 0
                com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccess r1 = com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccess.this     // Catch: java.lang.Exception -> L2a
                monitor-enter(r1)     // Catch: java.lang.Exception -> L2a
                com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccess r2 = com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccess.this     // Catch: java.lang.Throwable -> L1f
                com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccessListener r2 = com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccess.access$000(r2)     // Catch: java.lang.Throwable -> L1f
                if (r2 == 0) goto L18
                com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccess r2 = com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccess.this     // Catch: java.lang.Throwable -> L1f
                com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccessListener r2 = com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccess.access$000(r2)     // Catch: java.lang.Throwable -> L1f
                com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccess r3 = com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccess.this     // Catch: java.lang.Throwable -> L28
                com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccess.access$002(r3, r0)     // Catch: java.lang.Throwable -> L28
                r0 = r2
            L18:
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L1f
                if (r0 == 0) goto L3c
                r0.finishResult()     // Catch: java.lang.Exception -> L2a
                goto L3c
            L1f:
                r2 = move-exception
                r5 = r2
                r2 = r0
                r0 = r5
            L23:
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L28
                throw r0     // Catch: java.lang.Exception -> L25
            L25:
                r1 = move-exception
                r0 = r2
                goto L2b
            L28:
                r0 = move-exception
                goto L23
            L2a:
                r1 = move-exception
            L2b:
                com.felicanetworks.mfm.main.policy.log.LogUtil.warning(r1)
                com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException r1 = new com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException
                java.lang.Class<com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccess> r2 = com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccess.class
                r3 = 770(0x302, float:1.079E-42)
                com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException$CognitiveType r4 = com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException.CognitiveType.FATAL_ERROR
                r1.<init>(r2, r3, r4)
                r0.errorResult(r1)
            L3c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccess.ActivateFelicaClient.finished():void");
        }
    }

    public synchronized void close() {
        try {
            this.context.felica.close();
            this.lastSelectSystemCode = 0;
            this.lastSelectCid = "";
        } catch (FelicaException e) {
            LogUtil.warning(e);
        }
    }

    public synchronized void inactivateFelica() {
        this.faListener = null;
        try {
            this.context.felica.inactivateFelica();
        } catch (FelicaException e) {
            LogUtil.warning(e);
        }
    }

    protected IssueStateData getIssueStateResult() throws FelicaAccessException {
        try {
            felicaSelect(65039);
            byte[] iDm = this.context.felica.getIDm();
            iDm[0] = (byte) (iDm[0] & 15);
            return new IssueStateData(isSetupContainerIssue(this.context.felica.getContainerIssueInformation()), CommonUtil.binToHexString(iDm), CommonUtil.binToHexString(this.context.felica.getICCode()));
        } catch (FelicaException e) {
            throw new FelicaAccessException((Class) getClass(), InputDeviceCompat.SOURCE_GAMEPAD, e);
        } catch (Exception e2) {
            LogUtil.error(e2);
            throw new FelicaAccessException(getClass(), 1026, e2);
        }
    }

    public synchronized void silentStart(boolean z, boolean z2, Layout layout, MfiAccessListener mfiAccessListener) throws MfiClientException {
        try {
            if (this.mfiListener != null) {
                throw new IllegalStateException("Activation is already running.");
            }
            this.mfiListener = mfiAccessListener;
            synchronized (this) {
                if (this.mfiListener != null) {
                    MfiClient mfiClient = this.context.felica.getMfiClient();
                    if (z) {
                        mfiClient.clearMfiAccount();
                    }
                    int silentStartCode = getSilentStartCode();
                    setSilentStartCode(0);
                    int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$internal$legacy$cmnctrl$chip$FelicaAccess$Layout[layout.ordinal()];
                    mfiClient.silentStart(null, null, z2, silentStartCode, i != 1 ? i != 2 ? i != 3 ? 0 : 3 : 2 : 1, new SilentStartClient());
                }
            }
        } catch (MfiClientException e) {
            synchronized (this) {
                if (this.mfiListener != null) {
                    this.mfiListener = null;
                }
                LogUtil.warning(e);
                throw e;
            }
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccess$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$internal$legacy$cmnctrl$chip$FelicaAccess$Layout;

        static {
            int[] iArr = new int[Layout.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$internal$legacy$cmnctrl$chip$FelicaAccess$Layout = iArr;
            try {
                iArr[Layout.LAYOUT_TYPE_SIGN_IN_WITH_TERMS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$legacy$cmnctrl$chip$FelicaAccess$Layout[Layout.LAYOUT_TYPE_SKIPPABLE_SIGN_IN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$legacy$cmnctrl$chip$FelicaAccess$Layout[Layout.LAYOUT_TYPE_SIGN_IN_ONLY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    protected class SilentStartClient implements SilentStartForMfiAdminEventCallback {
        protected SilentStartClient() {
        }

        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.SilentStartForMfiAdminEventCallback
        public void onSuccess(User user, MfiAdmin mfiAdmin) {
            MfiAccessListener mfiAccessListener;
            synchronized (FelicaAccess.this) {
                mfiAccessListener = null;
                if (FelicaAccess.this.mfiListener != null) {
                    MfiAccessListener mfiAccessListener2 = FelicaAccess.this.mfiListener;
                    FelicaAccess.this.mfiListener = null;
                    mfiAccessListener = mfiAccessListener2;
                }
            }
            if (mfiAccessListener != null) {
                mfiAccessListener.onSuccess(true, user, mfiAdmin);
            }
        }

        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.SilentStartForMfiAdminEventCallback
        public void onRequestActivity(Intent intent) {
            MfiAccessListener mfiAccessListener;
            synchronized (FelicaAccess.this) {
                mfiAccessListener = null;
                if (FelicaAccess.this.mfiListener != null) {
                    MfiAccessListener mfiAccessListener2 = FelicaAccess.this.mfiListener;
                    FelicaAccess.this.mfiListener = null;
                    mfiAccessListener = mfiAccessListener2;
                }
            }
            if (mfiAccessListener != null) {
                mfiAccessListener.onRequestActivity(intent);
            }
        }

        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.SilentStartForMfiAdminEventCallback
        public void onSuccessNoLogin(MfiAdmin mfiAdmin) {
            MfiAccessListener mfiAccessListener;
            synchronized (FelicaAccess.this) {
                mfiAccessListener = null;
                if (FelicaAccess.this.mfiListener != null) {
                    MfiAccessListener mfiAccessListener2 = FelicaAccess.this.mfiListener;
                    FelicaAccess.this.mfiListener = null;
                    mfiAccessListener = mfiAccessListener2;
                }
            }
            if (mfiAccessListener != null) {
                mfiAccessListener.onSuccessNoLogin(mfiAdmin);
            }
        }

        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.SilentStartForMfiAdminEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
        public void onError(int i, String str) {
            error(new MfcException(FelicaAccess.class, 2050, new MficApiCallbackInfo(i, str)));
        }

        private void error(MfcException mfcException) {
            MfiAccessListener mfiAccessListener;
            synchronized (FelicaAccess.this) {
                mfiAccessListener = null;
                if (FelicaAccess.this.mfiListener != null) {
                    MfiAccessListener mfiAccessListener2 = FelicaAccess.this.mfiListener;
                    FelicaAccess.this.mfiListener = null;
                    mfiAccessListener = mfiAccessListener2;
                }
            }
            if (mfiAccessListener != null) {
                mfiAccessListener.errorResult(mfcException);
            }
        }
    }

    protected class StopMfiClient implements StopEventCallback {
        protected StopMfiClient() {
        }

        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.StopEventCallback
        public void onSuccess() {
            FelicaAccess.this._stopLatch.countDown();
        }

        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.StopEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
        public void onError(int i, String str) {
            LogUtil.warning(new MfcException(FelicaAccess.class, 2305, new MficApiCallbackInfo(i, str)));
            FelicaAccess.this._stopLatch.countDown();
        }
    }

    public synchronized void stop() {
        try {
            this._stopLatch = new CountDownLatch(1);
            this.context.felica.getMfiClient().stop(false, new StopMfiClient());
            this._stopLatch.await();
        } catch (Exception e) {
            LogUtil.warning(e);
        }
    }

    protected void setSilentStartCode(int i) {
        this._silentStartAccountCode = i;
    }

    protected void felicaSelect(int i) throws FelicaException {
        if (i == this.lastSelectSystemCode && "".equals(this.lastSelectCid)) {
            return;
        }
        this.context.felica.select(i);
        this.lastSelectSystemCode = i;
        this.lastSelectCid = "";
    }

    protected void felicaSelectWithCid(int i, String str) throws FelicaException {
        if (i == this.lastSelectSystemCode && str.equals(this.lastSelectCid)) {
            return;
        }
        this.context.felica.select(i, str);
        this.lastSelectSystemCode = i;
        this.lastSelectCid = str;
    }

    public String getCurrentAccountHash() throws MfiClientException {
        return this.context.felica.getMfiClient().getCurrentAccountHash();
    }

    private int getSilentStartCode() {
        return this._silentStartAccountCode;
    }

    private boolean isSetupContainerIssue(byte[] bArr) {
        if (bArr == null) {
            return false;
        }
        for (byte b : bArr) {
            if (b != 0) {
                return true;
            }
        }
        return false;
    }
}
