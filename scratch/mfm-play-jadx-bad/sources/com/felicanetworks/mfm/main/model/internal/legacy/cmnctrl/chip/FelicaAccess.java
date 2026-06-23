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
import com.google.common.base.Ascii;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes3.dex */
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

    protected FelicaAccess(AppContext context) {
        this.context = context;
    }

    public synchronized void activateFelica(FelicaAccessListener listener) throws FelicaAccessException {
        try {
            try {
                if (this.faListener != null) {
                    throw new IllegalStateException("Activation is already running.");
                }
                String[] strArr = {Felica.MFI_PERMIT};
                this.faListener = listener;
                AppContext appContext = this.context;
                if (appContext == null || appContext.felica == null) {
                    throw new IllegalStateException("activateFelica cannot be executed");
                }
                this.context.felica.activateFelica(strArr, this.client);
            } catch (FelicaException e) {
                LogUtil.warning(e);
                throw new FelicaAccessException((Class) getClass(), 257, e);
            }
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
        public void errorOccurred(int id, String msg, AppInfo otherAppInfo) {
            FelicaAccessListener felicaAccessListener;
            MfcException mfcException = new MfcException(FelicaAccess.class, InputDeviceCompat.SOURCE_DPAD, new ActivateCallbackInfo(id, msg, otherAppInfo));
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
        /* JADX WARN: Code restructure failed: missing block: B:13:0x001e, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:?, code lost:
        
            return;
         */
        @Override // com.felicanetworks.mfm.mfcutil.mfc.FelicaEventListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void finished() throws Throwable {
            FelicaAccessListener felicaAccessListener;
            FelicaAccessListener felicaAccessListener2 = null;
            try {
                try {
                    synchronized (FelicaAccess.this) {
                        try {
                            if (FelicaAccess.this.faListener != null) {
                                FelicaAccessListener felicaAccessListener3 = FelicaAccess.this.faListener;
                                FelicaAccess.this.faListener = null;
                                felicaAccessListener2 = felicaAccessListener3;
                            }
                        } catch (Throwable th) {
                            felicaAccessListener = felicaAccessListener2;
                            th = th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e) {
                e = e;
            }
            try {
                throw th;
            } catch (Exception e2) {
                e = e2;
                felicaAccessListener2 = felicaAccessListener;
                LogUtil.warning(e);
                felicaAccessListener2.errorResult(new MfcException(FelicaAccess.class, 770, MfcException.CognitiveType.FATAL_ERROR));
            }
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
            iDm[0] = (byte) (iDm[0] & Ascii.SI);
            return new IssueStateData(this.context.felica.getContainerIssueInformation(), CommonUtil.binToHexString(iDm), CommonUtil.binToHexString(this.context.felica.getICCode()));
        } catch (FelicaException e) {
            throw new FelicaAccessException((Class) getClass(), InputDeviceCompat.SOURCE_GAMEPAD, e);
        } catch (Exception e2) {
            LogUtil.error(e2);
            throw new FelicaAccessException(getClass(), 1026, e2);
        }
    }

    public synchronized void silentStart(boolean needClearAccount, boolean login, Layout layout, MfiAccessListener listener) throws MfiClientException {
        int i;
        try {
            if (this.mfiListener != null) {
                throw new IllegalStateException("Activation is already running.");
            }
            this.mfiListener = listener;
            synchronized (this) {
                if (this.mfiListener != null) {
                    MfiClient mfiClient = this.context.felica.getMfiClient();
                    if (needClearAccount) {
                        mfiClient.clearMfiAccount();
                    }
                    int silentStartCode = getSilentStartCode();
                    setSilentStartCode(0);
                    int iOrdinal = layout.ordinal();
                    if (iOrdinal == 0) {
                        i = 1;
                    } else if (iOrdinal != 1) {
                        i = iOrdinal == 2 ? 3 : 0;
                    } else {
                        i = 2;
                    }
                    mfiClient.silentStart((String) null, (String) null, login, silentStartCode, i, new SilentStartClient());
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
        public void onError(int i, String s) {
            error(new MfcException(FelicaAccess.class, 2050, new MficApiCallbackInfo(i, s)));
        }

        private void error(MfcException e) {
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
                mfiAccessListener.errorResult(e);
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
        public void onError(int type, String msg) {
            LogUtil.warning(new MfcException(FelicaAccess.class, 2305, new MficApiCallbackInfo(type, msg)));
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

    protected void setSilentStartCode(int code) {
        this._silentStartAccountCode = code;
    }

    protected void felicaSelect(int systemCode) throws FelicaException {
        if (systemCode == this.lastSelectSystemCode && "".equals(this.lastSelectCid)) {
            return;
        }
        this.context.felica.select(systemCode);
        this.lastSelectSystemCode = systemCode;
        this.lastSelectCid = "";
    }

    protected void felicaSelectWithCid(int systemCode, String cid) throws FelicaException {
        if (systemCode == this.lastSelectSystemCode && cid.equals(this.lastSelectCid)) {
            return;
        }
        this.context.felica.select(systemCode, cid);
        this.lastSelectSystemCode = systemCode;
        this.lastSelectCid = cid;
    }

    public String getCurrentAccountHash() throws MfiClientException {
        return this.context.felica.getMfiClient().getCurrentAccountHash();
    }

    private int getSilentStartCode() {
        return this._silentStartAccountCode;
    }

    private boolean isSetupContainerIssue(byte[] containerIssueInfo) {
        if (containerIssueInfo != null) {
            for (byte b : containerIssueInfo) {
                if (b != 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
