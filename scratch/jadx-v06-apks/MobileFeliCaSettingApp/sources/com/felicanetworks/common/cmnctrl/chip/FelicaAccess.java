package com.felicanetworks.common.cmnctrl.chip;

import com.felicanetworks.common.cmnctrl.data.FelicaErrorInfo;
import com.felicanetworks.common.cmnctrl.data.IssueStateData;
import com.felicanetworks.common.cmnlib.AppContext;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;
import com.felicanetworks.common.cmnlib.sg.SgMgr;
import com.felicanetworks.common.cmnlib.util.CommonUtil;
import com.felicanetworks.mfc.AppInfo;
import com.felicanetworks.mfc.Felica;
import com.felicanetworks.mfc.FelicaEventListener;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.mfi.BaseMfiEventCallback;
import com.felicanetworks.mfc.mfi.MfiClientException;

/* JADX INFO: loaded from: classes.dex */
public class FelicaAccess implements FunctionCodeInterface {
    protected static final int CODESTR_BASEMENT = 16;
    protected static final int NODE_CODE_SIZE = 4;
    protected static final int PID_NONE = 0;
    protected static final int TYPE_DF = 0;
    private AppContext context;
    private FelicaAccessListener faListener = null;
    private ActivateFelicaClient client = new ActivateFelicaClient();

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 2;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 3;
    }

    public FelicaAccess(AppContext appContext) {
        this.context = appContext;
    }

    public String getMFICVersion() throws FelicaAccessException {
        try {
            Felica felica = this.context.felica;
            return Felica.getMfiClientVersion(this.context.androidContext);
        } catch (MfiClientException e) {
            this.assortmfiException(e);
            return null;
        }
    }

    public synchronized void felicaUseStart(FelicaAccessListener felicaAccessListener) throws FelicaAccessException {
        try {
            try {
            } catch (Exception e) {
                throw new FelicaAccessException(e, this.context.logMgr.out(LogMgr.CatExp.ERR, this, e), 1);
            }
        } catch (FelicaException e2) {
            assortException(e2);
        }
        if (this.faListener != null) {
            throw new IllegalStateException("Activation is already running.");
        }
        Felica felica = this.context.felica;
        String[] strArr = {Felica.MFI_PERMIT};
        this.faListener = felicaAccessListener;
        this.context.felica.activateFelica(strArr, this.client);
    }

    protected class MfcNotificationConverter {
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
            if (exc instanceof FelicaException) {
                feToListenerParameter((FelicaException) exc);
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
                case BaseMfiEventCallback.TYPE_HTTP_COMMUNICATION_ERROR /* 205 */:
                case BaseMfiEventCallback.TYPE_SERVER_GENERAL_ERROR /* 206 */:
                    this.notificationResult = 7;
                    this.type = i;
                    break;
                case BaseMfiEventCallback.TYPE_SERVER_CANNOT_RESPOND_ERROR /* 204 */:
                    this.notificationResult = 8;
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
            }
            switch (i) {
                case BaseMfiEventCallback.TYPE_PROTOCOL_ERROR /* 202 */:
                case BaseMfiEventCallback.TYPE_HTTP_ERROR /* 203 */:
                case BaseMfiEventCallback.TYPE_HTTP_COMMUNICATION_ERROR /* 205 */:
                case BaseMfiEventCallback.TYPE_SERVER_GENERAL_ERROR /* 206 */:
                    this.notificationResult = 0;
                    this.type = i;
                    break;
                case BaseMfiEventCallback.TYPE_SERVER_CANNOT_RESPOND_ERROR /* 204 */:
                    this.notificationResult = 5;
                    this.type = i;
                    break;
                default:
                    this.notificationResult = 1;
                    this.type = i;
                    break;
            }
        }

        protected void errNotificationToListenerParameter(int i, AppInfo appInfo) {
            if (i == 3) {
                this.notificationResult = 9;
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

        protected void feToListenerParameter(FelicaException felicaException) {
            int type = felicaException.getType();
            if (type == 7) {
                this.notificationResult = 4;
            } else if (type == 55) {
                this.notificationResult = 1;
                this.category = LogMgr.CatExp.WAR;
            } else {
                this.notificationResult = 5;
            }
        }
    }

    protected class ActivateFelicaClient implements FelicaEventListener {
        public ActivateFelicaClient() {
        }

        @Override // com.felicanetworks.mfc.FelicaEventListener
        public void errorOccurred(int i, String str, AppInfo appInfo) {
            FelicaAccessListener felicaAccessListener;
            MfcNotificationConverter mfcNotificationConverter = FelicaAccess.this.new MfcNotificationConverter(i, str, appInfo);
            FelicaErrorInfo felicaErrorInfoMakeFelicaErrorInfo = FelicaAccess.this.makeFelicaErrorInfo(mfcNotificationConverter);
            FelicaAccessException felicaAccessException = new FelicaAccessException(felicaErrorInfoMakeFelicaErrorInfo.toString());
            synchronized (FelicaAccess.this) {
                felicaAccessListener = null;
                if (FelicaAccess.this.faListener != null) {
                    FelicaAccessListener felicaAccessListener2 = FelicaAccess.this.faListener;
                    FelicaAccess.this.faListener = null;
                    felicaAccessListener = felicaAccessListener2;
                }
            }
            if (felicaAccessListener != null) {
                felicaAccessListener.errorResult(mfcNotificationConverter.notificationResult, FelicaAccess.this.context.logMgr.out(mfcNotificationConverter.category, FelicaAccess.this, felicaAccessException), mfcNotificationConverter.pid, felicaErrorInfoMakeFelicaErrorInfo);
            }
        }

        @Override // com.felicanetworks.mfc.FelicaEventListener
        public void finished() throws Throwable {
            FelicaAccessListener felicaAccessListener;
            FelicaAccessListener felicaAccessListener2 = null;
            try {
                try {
                    try {
                        synchronized (FelicaAccess.this) {
                            try {
                                if (FelicaAccess.this.faListener != null) {
                                    felicaAccessListener = FelicaAccess.this.faListener;
                                    FelicaAccess.this.faListener = null;
                                    FelicaAccess.this.context.felica.open();
                                    try {
                                        FelicaAccess.this.context.felica.setTimeout(((Integer) FelicaAccess.this.context.sgMgr.getSgValue(SgMgr.KEY_MFC_TIMEOUT)).intValue());
                                        FelicaAccess.this.context.felica.setRetryCount(((Integer) FelicaAccess.this.context.sgMgr.getSgValue(SgMgr.KEY_MFC_RETRY_CNT)).intValue());
                                    } catch (Exception e) {
                                        try {
                                            FelicaAccess.this.context.felica.close();
                                            FelicaAccess.this.context.felica.inactivateFelica();
                                        } catch (Exception e2) {
                                            FelicaAccess.this.context.logMgr.out(LogMgr.CatExp.WAR, FelicaAccess.this, e2);
                                        }
                                        throw e;
                                    }
                                } else {
                                    felicaAccessListener = null;
                                }
                                if (felicaAccessListener != null) {
                                    felicaAccessListener.finishResult();
                                }
                            } catch (Throwable th) {
                                th = th;
                                throw th;
                            }
                        }
                    } catch (Exception e3) {
                        e = e3;
                        MfcNotificationConverter mfcNotificationConverter = FelicaAccess.this.new MfcNotificationConverter(e);
                        if (!(e instanceof FelicaException)) {
                            felicaAccessListener2.errorResult(mfcNotificationConverter.notificationResult, FelicaAccess.this.context.logMgr.out(mfcNotificationConverter.category, FelicaAccess.this, e), mfcNotificationConverter.pid, null);
                            return;
                        }
                        FelicaErrorInfo felicaErrorInfoMakeFelicaErrorInfo = FelicaAccess.this.makeFelicaErrorInfo((FelicaException) e);
                        felicaAccessListener2.errorResult(mfcNotificationConverter.notificationResult, FelicaAccess.this.context.logMgr.out(mfcNotificationConverter.category, FelicaAccess.this, new FelicaAccessException(felicaErrorInfoMakeFelicaErrorInfo.toString(), e)), mfcNotificationConverter.pid, felicaErrorInfoMakeFelicaErrorInfo);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
            }
        }
    }

    protected FelicaErrorInfo makeFelicaErrorInfo(FelicaException felicaException) {
        return new FelicaErrorInfo(FelicaErrorInfo.EX_TYPE_VALUE, felicaException.getID(), felicaException.getType(), felicaException.getStatusFlag1(), felicaException.getStatusFlag2(), felicaException.getMessage());
    }

    protected FelicaErrorInfo makeFelicaErrorInfo(MfcNotificationConverter mfcNotificationConverter) {
        return new FelicaErrorInfo(FelicaErrorInfo.LS_TYPE_VALUE, 0, mfcNotificationConverter.type, 0, 0, mfcNotificationConverter.msgResult);
    }

    public synchronized void felicaUseEnd() {
        this.faListener = null;
        try {
            this.context.felica.close();
        } catch (FelicaException e) {
            this.context.logMgr.out(LogMgr.CatExp.WAR, this, e);
        }
        try {
            this.context.felica.inactivateFelica();
        } catch (FelicaException e2) {
            this.context.logMgr.out(LogMgr.CatExp.WAR, this, e2);
        }
    }

    public IssueStateData getIssueStateResult() throws FelicaAccessException {
        try {
            this.context.felica.select(Integer.parseInt(this.context.sgMgr.getManageSystemCode(), 16));
            byte[] iDm = this.context.felica.getIDm();
            iDm[0] = (byte) (iDm[0] & 15);
            this.context.idm = CommonUtil.binToHexString(iDm);
            return new IssueStateData(isSetupContainerIssue(this.context.felica.getContainerIssueInformation()), CommonUtil.binToHexString(iDm), CommonUtil.binToHexString(this.context.felica.getICCode()), null, null);
        } catch (FelicaException e) {
            assortException(e);
            return null;
        } catch (Exception e2) {
            throw new FelicaAccessException(e2, this.context.logMgr.out(LogMgr.CatExp.ERR, this, e2), 1);
        }
    }

    protected boolean isSetupContainerIssue(byte[] bArr) throws FelicaAccessException {
        for (byte b : bArr) {
            if (b != 0) {
                return true;
            }
        }
        return false;
    }

    protected void assortException(FelicaException felicaException) throws FelicaAccessException {
        FelicaErrorInfo felicaErrorInfoMakeFelicaErrorInfo = makeFelicaErrorInfo(felicaException);
        FelicaAccessException felicaAccessException = new FelicaAccessException(felicaErrorInfoMakeFelicaErrorInfo.toString(), felicaException);
        if (felicaException.getType() == 7) {
            throw new FelicaAccessException(felicaException, this.context.logMgr.out(LogMgr.CatExp.ERR, this, felicaAccessException), 2, felicaErrorInfoMakeFelicaErrorInfo);
        }
        throw new FelicaAccessException(felicaException, this.context.logMgr.out(LogMgr.CatExp.ERR, this, felicaAccessException), 3, felicaErrorInfoMakeFelicaErrorInfo);
    }

    protected void assortmfiException(MfiClientException mfiClientException) throws FelicaAccessException {
        FelicaErrorInfo felicaErrorInfoMakeFelicaErrorInfo = makeFelicaErrorInfo(mfiClientException);
        FelicaAccessException felicaAccessException = new FelicaAccessException(felicaErrorInfoMakeFelicaErrorInfo.toString(), mfiClientException);
        if (mfiClientException.getType() == 151) {
            throw new FelicaAccessException(mfiClientException, this.context.logMgr.out(LogMgr.CatExp.ERR, this, felicaAccessException), 4, felicaErrorInfoMakeFelicaErrorInfo);
        }
        throw new FelicaAccessException(mfiClientException, this.context.logMgr.out(LogMgr.CatExp.ERR, this, felicaAccessException), 3, felicaErrorInfoMakeFelicaErrorInfo);
    }
}
