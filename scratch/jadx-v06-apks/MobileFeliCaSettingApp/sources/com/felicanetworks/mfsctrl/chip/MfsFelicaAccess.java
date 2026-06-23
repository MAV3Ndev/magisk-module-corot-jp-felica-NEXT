package com.felicanetworks.mfsctrl.chip;

import android.content.Intent;
import com.felicanetworks.common.cmnctrl.chip.FelicaAccess;
import com.felicanetworks.common.cmnctrl.chip.FelicaAccessException;
import com.felicanetworks.common.cmnctrl.chip.FelicaAccessListener;
import com.felicanetworks.common.cmnctrl.data.FelicaErrorInfo;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;
import com.felicanetworks.mfc.AppInfo;
import com.felicanetworks.mfc.Felica;
import com.felicanetworks.mfc.FelicaEventListener;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.mfi.BaseMfiEventCallback;
import com.felicanetworks.mfc.mfi.InitializedEventCallback;
import com.felicanetworks.mfc.mfi.LinkageDataListEventCallback;
import com.felicanetworks.mfc.mfi.MfiAdmin;
import com.felicanetworks.mfc.mfi.MfiClient;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfc.mfi.SilentStartForMfiAdminEventCallback;
import com.felicanetworks.mfc.mfi.StopEventCallback;
import com.felicanetworks.mfc.mfi.User;
import com.felicanetworks.mfslib.MfsAppContext;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes.dex */
public class MfsFelicaAccess extends FelicaAccess implements FunctionCodeInterface {
    private MfsAppContext _context;
    private ActivateMFIClient client;
    CountDownLatch count;
    private FelicaAccessListener faListener;
    private FelicaInitializeListener fiListener;
    private String[] linkageData;
    private MfiAdmin mfiAdmin;
    private MfiClient mfiClient;
    private SilentStartListener silentStartListener;

    @Override // com.felicanetworks.common.cmnctrl.chip.FelicaAccess, com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 2;
    }

    @Override // com.felicanetworks.common.cmnctrl.chip.FelicaAccess, com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 34;
    }

    public MfsFelicaAccess(MfsAppContext mfsAppContext) {
        super(mfsAppContext);
        this.fiListener = null;
        this.faListener = null;
        this.mfiClient = null;
        this.mfiAdmin = null;
        this.client = new ActivateMFIClient();
        this.silentStartListener = new SilentStartListener();
        this.linkageData = null;
        this._context = mfsAppContext;
    }

    public synchronized void mfiUseStart(FelicaAccessListener felicaAccessListener) throws FelicaAccessException {
        try {
        } catch (FelicaException e) {
            assortException(e);
        } catch (Exception e2) {
            throw new FelicaAccessException(e2, this._context.logMgr.out(LogMgr.CatExp.ERR, this, e2), 1);
        }
        if (this.faListener != null || this.fiListener != null) {
            throw new IllegalStateException("Activation is already running.");
        }
        Felica felica = this._context.felica;
        String[] strArr = {Felica.MFI_PERMIT};
        this.faListener = felicaAccessListener;
        this._context.felica.activateFelica(strArr, this.client);
    }

    public synchronized void initializeFelica(FelicaInitializeListener felicaInitializeListener) throws InterruptedException, FelicaAccessException {
        this.count = new CountDownLatch(1);
        this.fiListener = felicaInitializeListener;
        try {
            try {
                this.mfiAdmin.getLinkageDataList(3, null, new LinkageCallback());
            } catch (MfiClientException e) {
                assortException(e);
            }
            try {
                try {
                    this.count.await();
                    if (this.linkageData != null) {
                        this.count = new CountDownLatch(1);
                        this.mfiAdmin.initialize(this.linkageData[0], new InitializeCallback());
                        this.count.await();
                    }
                } catch (InterruptedException unused) {
                    throw new InterruptedException("InterruptedException");
                }
            } catch (MfiClientException e2) {
                assortException(e2);
            }
        } catch (Exception e3) {
            throw new FelicaAccessException(e3, this._context.logMgr.out(LogMgr.CatExp.ERR, this, e3), 1);
        }
    }

    protected class MfcNotificationConverter {
        public static final int INITIALIZE = 1;
        public static final int START = 0;
        public LogMgr.CatExp category;
        public String msgResult;
        public int notificationResult;
        public int pid;
        public int type;

        MfcNotificationConverter(int i, String str, AppInfo appInfo) {
            this.category = LogMgr.CatExp.ERR;
            this.notificationResult = 2;
            this.pid = 0;
            this.msgResult = "";
            this.type = 0;
            this.msgResult = str;
            errNotificationToListenerParameter(i, appInfo);
        }

        MfcNotificationConverter(Exception exc) {
            this.category = LogMgr.CatExp.ERR;
            this.notificationResult = 2;
            this.pid = 0;
            this.msgResult = "";
            this.type = 0;
            if (exc instanceof MfiClientException) {
                feToListenerParameter((MfiClientException) exc);
            }
        }

        MfcNotificationConverter(int i, String str, int i2) {
            this.category = LogMgr.CatExp.ERR;
            this.notificationResult = 2;
            this.pid = 0;
            this.msgResult = "";
            this.type = 0;
            this.msgResult = str;
            if (i2 == 0) {
                errNotificationToSilentStartListenerParameter(i);
            } else if (i2 == 1) {
                errNotificationToInitializeListenerParameter(i);
            }
        }

        protected void errNotificationToSilentStartListenerParameter(int i) {
            if (i == 7) {
                this.notificationResult = 0;
                this.category = LogMgr.CatExp.WAR;
            }
            if (i == 55) {
                this.notificationResult = 1;
                this.type = i;
                return;
            }
            switch (i) {
                case BaseMfiEventCallback.TYPE_PROTOCOL_ERROR /* 202 */:
                case BaseMfiEventCallback.TYPE_HTTP_ERROR /* 203 */:
                case BaseMfiEventCallback.TYPE_SERVER_GENERAL_ERROR /* 206 */:
                    this.notificationResult = 10;
                    this.type = i;
                    break;
                case BaseMfiEventCallback.TYPE_SERVER_CANNOT_RESPOND_ERROR /* 204 */:
                    this.notificationResult = 8;
                    this.type = i;
                    break;
                case BaseMfiEventCallback.TYPE_HTTP_COMMUNICATION_ERROR /* 205 */:
                    this.notificationResult = 7;
                    this.type = i;
                    break;
                default:
                    this.notificationResult = 5;
                    this.type = i;
                    break;
            }
        }

        protected void errNotificationToInitializeListenerParameter(int i) {
            if (i == 55) {
                this.notificationResult = 4;
                this.type = i;
                return;
            }
            if (i == 239) {
                this.notificationResult = 6;
                this.type = i;
            } else if (i == 204) {
                this.notificationResult = 5;
                this.type = i;
            } else if (i == 205) {
                this.notificationResult = 0;
                this.type = i;
            } else {
                this.notificationResult = 1;
                this.type = i;
            }
        }

        protected void errNotificationToListenerParameter(int i, AppInfo appInfo) {
            if (i == 3) {
                this.notificationResult = 7;
                return;
            }
            if (i == 4) {
                this.notificationResult = 3;
                this.type = i;
                return;
            }
            if (i == 7) {
                this.notificationResult = 0;
                this.category = LogMgr.CatExp.WAR;
                this.pid = appInfo.getPid();
            } else if (i == 100) {
                this.notificationResult = 6;
            } else if (i == 101) {
                this.notificationResult = 3;
            } else {
                this.notificationResult = 5;
                this.type = i;
            }
        }

        protected void feToListenerParameter(MfiClientException mfiClientException) {
            int type = mfiClientException.getType();
            if (type == 7) {
                this.notificationResult = 4;
                return;
            }
            if (type == 39) {
                this.notificationResult = 0;
            } else if (type == 55) {
                this.notificationResult = 1;
                this.category = LogMgr.CatExp.WAR;
            } else {
                this.notificationResult = 5;
            }
        }
    }

    @Override // com.felicanetworks.common.cmnctrl.chip.FelicaAccess
    protected FelicaErrorInfo makeFelicaErrorInfo(FelicaException felicaException) {
        return new FelicaErrorInfo(FelicaErrorInfo.EX_TYPE_VALUE, felicaException.getID(), felicaException.getType(), felicaException.getStatusFlag1(), felicaException.getStatusFlag2(), felicaException.getMessage());
    }

    protected FelicaErrorInfo makeFelicaErrorInfo(MfcNotificationConverter mfcNotificationConverter) {
        return new FelicaErrorInfo(FelicaErrorInfo.LS_TYPE_VALUE, 0, mfcNotificationConverter.type, 0, 0, mfcNotificationConverter.msgResult);
    }

    protected class ActivateMFIClient implements FelicaEventListener {
        public ActivateMFIClient() {
        }

        @Override // com.felicanetworks.mfc.FelicaEventListener
        public void errorOccurred(int i, String str, AppInfo appInfo) {
            FelicaAccessListener felicaAccessListener;
            MfcNotificationConverter mfcNotificationConverter = MfsFelicaAccess.this.new MfcNotificationConverter(i, str, appInfo);
            FelicaErrorInfo felicaErrorInfoMakeFelicaErrorInfo = MfsFelicaAccess.this.makeFelicaErrorInfo(mfcNotificationConverter);
            FelicaAccessException felicaAccessException = new FelicaAccessException(felicaErrorInfoMakeFelicaErrorInfo.toString());
            synchronized (MfsFelicaAccess.this) {
                felicaAccessListener = null;
                if (MfsFelicaAccess.this.faListener != null) {
                    FelicaAccessListener felicaAccessListener2 = MfsFelicaAccess.this.faListener;
                    MfsFelicaAccess.this.faListener = null;
                    felicaAccessListener = felicaAccessListener2;
                }
            }
            if (felicaAccessListener != null) {
                felicaAccessListener.errorResult(mfcNotificationConverter.notificationResult, MfsFelicaAccess.this._context.logMgr.out(mfcNotificationConverter.category, MfsFelicaAccess.this, felicaAccessException), mfcNotificationConverter.pid, felicaErrorInfoMakeFelicaErrorInfo);
            }
        }

        @Override // com.felicanetworks.mfc.FelicaEventListener
        public void finished() throws Throwable {
            FelicaAccessListener felicaAccessListener;
            FelicaAccessListener felicaAccessListener2 = null;
            try {
                try {
                    try {
                        synchronized (MfsFelicaAccess.this) {
                            try {
                                if (MfsFelicaAccess.this.faListener != null) {
                                    felicaAccessListener = MfsFelicaAccess.this.faListener;
                                    MfsFelicaAccess.this.fiListener = null;
                                    MfsFelicaAccess.this.count = new CountDownLatch(1);
                                    felicaAccessListener.completeActivate();
                                    MfsFelicaAccess.this.mfiClient = MfsFelicaAccess.this._context.felica.getMfiClient();
                                    MfsFelicaAccess.this.mfiClient.silentStart(null, null, false, 0, MfsFelicaAccess.this.silentStartListener);
                                } else {
                                    felicaAccessListener = null;
                                }
                                MfsFelicaAccess.this.count.await();
                                if (felicaAccessListener != null) {
                                    felicaAccessListener.finishResult();
                                }
                            } catch (Throwable th) {
                                th = th;
                                throw th;
                            }
                        }
                    } catch (Exception e) {
                        e = e;
                        MfcNotificationConverter mfcNotificationConverter = MfsFelicaAccess.this.new MfcNotificationConverter(e);
                        if (!(e instanceof FelicaException)) {
                            felicaAccessListener2.errorResult(mfcNotificationConverter.notificationResult, MfsFelicaAccess.this._context.logMgr.out(mfcNotificationConverter.category, MfsFelicaAccess.this, e), mfcNotificationConverter.pid, null);
                            return;
                        }
                        FelicaErrorInfo felicaErrorInfoMakeFelicaErrorInfo = MfsFelicaAccess.this.makeFelicaErrorInfo((FelicaException) e);
                        felicaAccessListener2.errorResult(mfcNotificationConverter.notificationResult, MfsFelicaAccess.this._context.logMgr.out(mfcNotificationConverter.category, MfsFelicaAccess.this, new FelicaAccessException(felicaErrorInfoMakeFelicaErrorInfo.toString(), e)), mfcNotificationConverter.pid, felicaErrorInfoMakeFelicaErrorInfo);
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e2) {
                e = e2;
            }
        }
    }

    protected class SilentStartListener implements SilentStartForMfiAdminEventCallback {
        @Override // com.felicanetworks.mfc.mfi.SilentStartForMfiAdminEventCallback
        public void onRequestActivity(Intent intent) {
        }

        @Override // com.felicanetworks.mfc.mfi.SilentStartForMfiAdminEventCallback
        public void onSuccess(User user, MfiAdmin mfiAdmin) {
        }

        protected SilentStartListener() {
        }

        @Override // com.felicanetworks.mfc.mfi.SilentStartForMfiAdminEventCallback
        public void onSuccessNoLogin(MfiAdmin mfiAdmin) {
            synchronized (MfsFelicaAccess.this) {
                MfsFelicaAccess.this.mfiAdmin = mfiAdmin;
            }
            MfsFelicaAccess.this.count.countDown();
        }

        @Override // com.felicanetworks.mfc.mfi.SilentStartForMfiAdminEventCallback, com.felicanetworks.mfc.mfi.BaseMfiEventCallback
        public void onError(int i, String str) {
            FelicaAccessListener felicaAccessListener;
            MfcNotificationConverter mfcNotificationConverter = MfsFelicaAccess.this.new MfcNotificationConverter(i, str, 0);
            FelicaErrorInfo felicaErrorInfoMakeFelicaErrorInfo = MfsFelicaAccess.this.makeFelicaErrorInfo(mfcNotificationConverter);
            FelicaAccessException felicaAccessException = new FelicaAccessException(felicaErrorInfoMakeFelicaErrorInfo.toString());
            synchronized (MfsFelicaAccess.this) {
                felicaAccessListener = null;
                if (MfsFelicaAccess.this.faListener != null) {
                    FelicaAccessListener felicaAccessListener2 = MfsFelicaAccess.this.faListener;
                    MfsFelicaAccess.this.faListener = null;
                    felicaAccessListener = felicaAccessListener2;
                }
            }
            if (felicaAccessListener != null) {
                felicaAccessListener.errorResult(mfcNotificationConverter.notificationResult, MfsFelicaAccess.this._context.logMgr.out(mfcNotificationConverter.category, MfsFelicaAccess.this, felicaAccessException), mfcNotificationConverter.pid, felicaErrorInfoMakeFelicaErrorInfo);
            }
            MfsFelicaAccess.this.count.countDown();
        }
    }

    protected class LinkageCallback implements LinkageDataListEventCallback {
        protected LinkageCallback() {
        }

        @Override // com.felicanetworks.mfc.mfi.LinkageDataListEventCallback
        public void onSuccess(String[] strArr) {
            FelicaInitializeListener felicaInitializeListener = MfsFelicaAccess.this.fiListener;
            synchronized (this) {
                MfsFelicaAccess.this.linkageData = strArr;
            }
            if (felicaInitializeListener != null) {
                felicaInitializeListener.completeLinkageData();
            }
            MfsFelicaAccess.this.count.countDown();
        }

        @Override // com.felicanetworks.mfc.mfi.LinkageDataListEventCallback, com.felicanetworks.mfc.mfi.BaseMfiEventCallback
        public void onError(int i, String str) {
            MfcNotificationConverter mfcNotificationConverter = MfsFelicaAccess.this.new MfcNotificationConverter(i, str, 1);
            FelicaInitializeListener felicaInitializeListener = null;
            if (MfsFelicaAccess.this.fiListener != null) {
                FelicaInitializeListener felicaInitializeListener2 = MfsFelicaAccess.this.fiListener;
                MfsFelicaAccess.this.fiListener = null;
                felicaInitializeListener = felicaInitializeListener2;
            }
            if (felicaInitializeListener != null) {
                felicaInitializeListener.onWarning(mfcNotificationConverter.notificationResult);
            }
            MfsFelicaAccess.this.count.countDown();
        }
    }

    protected class InitializeCallback implements InitializedEventCallback {
        protected InitializeCallback() {
        }

        @Override // com.felicanetworks.mfc.mfi.InitializedEventCallback
        public void onSuccess() {
            FelicaInitializeListener felicaInitializeListener = null;
            if (MfsFelicaAccess.this.fiListener != null) {
                FelicaInitializeListener felicaInitializeListener2 = MfsFelicaAccess.this.fiListener;
                MfsFelicaAccess.this.fiListener = null;
                felicaInitializeListener = felicaInitializeListener2;
            }
            if (felicaInitializeListener != null) {
                felicaInitializeListener.onComplete();
            }
            MfsFelicaAccess.this.count.countDown();
        }

        @Override // com.felicanetworks.mfc.mfi.InitializedEventCallback, com.felicanetworks.mfc.mfi.BaseMfiEventCallback
        public void onError(int i, String str) {
            MfcNotificationConverter mfcNotificationConverter = MfsFelicaAccess.this.new MfcNotificationConverter(i, str, 1);
            FelicaInitializeListener felicaInitializeListener = null;
            if (MfsFelicaAccess.this.fiListener != null) {
                FelicaInitializeListener felicaInitializeListener2 = MfsFelicaAccess.this.fiListener;
                MfsFelicaAccess.this.fiListener = null;
                felicaInitializeListener = felicaInitializeListener2;
            }
            if (felicaInitializeListener != null) {
                felicaInitializeListener.onWarning(mfcNotificationConverter.notificationResult);
            }
            MfsFelicaAccess.this.count.countDown();
        }
    }

    public synchronized void mfiUseEnd() {
        this.count = new CountDownLatch(1);
        this.faListener = null;
        try {
            if (this.mfiClient != null) {
                this.mfiClient.stop(false, new StopCallback());
                this.count.await();
            }
        } catch (MfiClientException | InterruptedException | Exception unused) {
        }
        try {
            this._context.felica.inactivateFelica();
        } catch (FelicaException unused2) {
        }
    }

    protected class StopCallback implements StopEventCallback {
        protected StopCallback() {
        }

        @Override // com.felicanetworks.mfc.mfi.StopEventCallback
        public void onSuccess() {
            MfsFelicaAccess.this.count.countDown();
        }

        @Override // com.felicanetworks.mfc.mfi.StopEventCallback, com.felicanetworks.mfc.mfi.BaseMfiEventCallback
        public void onError(int i, String str) {
            MfsFelicaAccess.this.count.countDown();
        }
    }
}
