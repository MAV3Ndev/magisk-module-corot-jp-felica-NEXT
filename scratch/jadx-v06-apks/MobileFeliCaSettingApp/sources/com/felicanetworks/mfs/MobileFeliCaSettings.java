package com.felicanetworks.mfs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.felicanetworks.analysis.AnalysisManager;
import com.felicanetworks.analysis.MfsStamp;
import com.felicanetworks.common.cmnctrl.data.FelicaErrorInfo;
import com.felicanetworks.common.cmnctrl.net.NetworkUtil;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;
import com.felicanetworks.common.cmnview.AbstractTransferStateCommon;
import com.felicanetworks.common.cmnview.BaseView;
import com.felicanetworks.common.cmnview.TransferSenderInfo;
import com.felicanetworks.common.cmnview.TransferState;
import com.felicanetworks.common.cmnview.TransferStateData;
import com.felicanetworks.common.cmnview.ViewLayer;
import com.felicanetworks.mfs.AppStatus;
import com.felicanetworks.mfs.view.AlreadyInitView;
import com.felicanetworks.mfs.view.BackScreenView;
import com.felicanetworks.mfs.view.CancelInitView;
import com.felicanetworks.mfs.view.CommunicationErrorView;
import com.felicanetworks.mfs.view.CompleteInitView;
import com.felicanetworks.mfs.view.ConfirmInitView;
import com.felicanetworks.mfs.view.ConfirmVersionView;
import com.felicanetworks.mfs.view.FailedInitView;
import com.felicanetworks.mfs.view.FatalErrorBeforeAppInitView;
import com.felicanetworks.mfs.view.FatalErrorView;
import com.felicanetworks.mfs.view.FeliCaInUseProcessingView;
import com.felicanetworks.mfs.view.FeliCaInUseView;
import com.felicanetworks.mfs.view.FeliCaLockProcessingView;
import com.felicanetworks.mfs.view.FeliCaLockView;
import com.felicanetworks.mfs.view.FeliCaOtherErrorView;
import com.felicanetworks.mfs.view.FeliCaSignatureFailedView;
import com.felicanetworks.mfs.view.FeliCaTimeoutView;
import com.felicanetworks.mfs.view.HttpErrorView;
import com.felicanetworks.mfs.view.LogSendingView;
import com.felicanetworks.mfs.view.MfcDisableErrorView;
import com.felicanetworks.mfs.view.MfcNotFoundView;
import com.felicanetworks.mfs.view.MfsVersionView;
import com.felicanetworks.mfs.view.MultiplexStartView;
import com.felicanetworks.mfs.view.NotApplicableDevice;
import com.felicanetworks.mfs.view.OvercrowdingErrorView;
import com.felicanetworks.mfs.view.RunningInitView;
import com.felicanetworks.mfs.view.ServerMaintenanceErrorView;
import com.felicanetworks.mfs.view.StartingView;
import com.felicanetworks.mfsctrl.ControlFunctionLibrary;
import com.felicanetworks.mfsctrl.InitializeApplicationListener;
import com.felicanetworks.mfslib.MfsAppContext;
import com.felicanetworks.mfslib.log.MfsLogMgr;
import com.felicanetworks.mfslib.sg.MfsSgMgr;

/* JADX INFO: loaded from: classes.dex */
public class MobileFeliCaSettings extends BaseActivity implements FunctionCodeInterface {
    public static final String LAUNCH_APP_CHECK_PACKAGE_NAME = "com.felicanetworks.mfm.main";
    public static final boolean PARAM_DEFAULT_VALUE_CHECKBOX = false;
    public static final String PARAM_KEY_CHECKBOX = "com.felicanetworks.mfs.mfmchkbxflg";
    public static final String PARAM_KEY_LAUNCH_AGREED_TOS = "com.felicanetworks.mfs.tos";
    public static final String PARAM_KEY_LAUNCH_APP_ID = "com.felicanetworks.mfs.ai";
    public static final String PARAM_KEY_LAUNCH_CMD = "com.felicanetworks.mfs.log";
    public static final String RESULT_KEY_CHKBOX = "com.felicanetworks.mfs.result.chkbx";
    public static final String RESULT_KEY_FELICA = "com.felicanetworks.mfs.result.felica";
    private static TransferStateData _lastAppData;
    private static int _lastAppStatus;
    private static int _myInstanceCnt;
    private boolean _needcheckbox;
    private Intent defaultResultIntent;
    public boolean isSkipTos = false;
    private MfsAppContext _context = null;
    private ViewLayer _viewLayer = new ViewLayer();
    private ControlFunctionLibrary _cflInst = null;
    private boolean _isAlive = false;

    public static class Result {
        public static final int ALREADYINIT = 2;
        public static final int CANCEL = 0;
        public static final int FAILED_BADSTART = 102;
        public static final int FAILED_COMMUNICATIONERROR = 107;
        public static final int FAILED_FATALERROR = 110;
        public static final int FAILED_FELICAINUSE = 104;
        public static final int FAILED_FELICALOCK = 105;
        public static final int FAILED_INIT = 106;
        public static final int FAILED_MFCDISABLE = 132;
        public static final int FAILED_MFCNOTFOUND = 131;
        public static final int FAILED_MFICVERSION = 130;
        public static final int FAILED_MFSVERSION = 133;
        public static final int FAILED_NOTAPPLICABLEDEVICE = 134;
        public static final int FAILED_OVERCROWDING = 108;
        public static final int FAILED_SERVERMAINTENANCE = 109;
        public static final int SUCCESS = 1;
    }

    public static class ResultCheckBox {
        public static final int NONE = -1;
        public static final int OFF = 0;
        public static final int ON = 1;
    }

    public static class ResultFelica {
        public static final int ALREADYINIT = 2;
        public static final int CANCEL = 0;
        public static final int FAILED = 100;
        public static final int SUCCESS = 1;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 1;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 32;
    }

    private void showMultiplexStartView() {
        setResult(0, this.defaultResultIntent);
        this.dialog = new MultiplexStartView();
        this.dialog.setTargetTag(BaseActivity.TAG_FATAL_ERROR_DIALOG);
        showErrorDialog(this.dialog);
    }

    private void showFatalErrorView(String str) {
        setResult(110);
        this.dialog = new FatalErrorBeforeAppInitView().applyArgument(str);
        this.dialog.setTargetTag(BaseActivity.TAG_FATAL_ERROR_DIALOG);
        showErrorDialog(this.dialog);
    }

    private void setBackScreenView() {
        this._viewLayer.windowView = new BackScreenView(this);
        ViewLayer viewLayer = this._viewLayer;
        viewLayer.activeView = viewLayer.windowView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void destroyProc() {
        if (this._isAlive) {
            _myInstanceCnt--;
            this._isAlive = false;
            ViewLayer viewLayer = this._viewLayer;
            if (viewLayer != null && viewLayer.activeView != null) {
                try {
                    this._viewLayer.activeView.onActivityDestroy();
                } catch (Exception unused) {
                }
            }
            if (this.dialog != null) {
                try {
                    this.dialog.onActivityDestroy();
                } catch (Exception unused2) {
                }
            }
            ControlFunctionLibrary controlFunctionLibrary = this._cflInst;
            if (controlFunctionLibrary != null) {
                try {
                    controlFunctionLibrary.deinitializeApplication();
                } catch (Exception unused3) {
                }
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        AnalysisManager.stampCreate(this);
        try {
            super.onCreate(bundle);
            this._isAlive = true;
            _myInstanceCnt++;
            setTitle(R.string.act_title_001);
            setBackScreenView();
            Intent intent = getIntent();
            this._needcheckbox = false;
            String stringExtra = intent.getStringExtra(PARAM_KEY_LAUNCH_APP_ID);
            String stringExtra2 = intent.getStringExtra(PARAM_KEY_LAUNCH_CMD);
            String callingPackage = getCallingPackage();
            if (intent.hasExtra(PARAM_KEY_LAUNCH_AGREED_TOS) && TextUtils.equals(callingPackage, LAUNCH_APP_CHECK_PACKAGE_NAME) && intent.getBooleanExtra(PARAM_KEY_LAUNCH_AGREED_TOS, false)) {
                this.isSkipTos = true;
            }
            AnalysisManager.stamp(MfsStamp.Event.AUTO_DUMP_MFS_LAUNCH, stringExtra, stringExtra2, callingPackage);
            Intent intent2 = new Intent();
            this.defaultResultIntent = intent2;
            intent2.putExtra(RESULT_KEY_FELICA, 0);
            if (this._needcheckbox) {
                this.defaultResultIntent.putExtra(RESULT_KEY_CHKBOX, -1);
            }
            setResult(0, this.defaultResultIntent);
            if (isTaskRoot()) {
                setResult(102, this.defaultResultIntent);
                finish();
                return;
            }
            if (1 < _myInstanceCnt) {
                showMultiplexStartView();
                return;
            }
            try {
                MfsAppContext mfsAppContext = new MfsAppContext();
                this._context = mfsAppContext;
                mfsAppContext.androidContext = getApplicationContext();
                this._context.activeActivity = this;
                this._context.sgMgr = new MfsSgMgr(this._context);
                this._context.sgMgr.loadSg();
                this._context.logMgr = new MfsLogMgr(this._context);
                MfsAppData.getInstance().logMgr = this._context.logMgr;
                NetworkUtil.setUserAgent(this._context, this._context.sgMgr.getAppName());
                MfsAppData.getInstance().appContext = this._context;
                try {
                    TransferState.refreshHandler();
                    TransferState.setTransferStateListener(new MfsTransferStateControler());
                    if (checkLastAppStatus()) {
                        return;
                    }
                    AppStatus appStatus = new AppStatus();
                    appStatus.isNeedcheckbox = this._needcheckbox;
                    MfsAppData.setAppStatus(appStatus);
                    ControlFunctionLibrary controlFunctionLibrary = ControlFunctionLibrary.getInstance();
                    this._cflInst = controlFunctionLibrary;
                    controlFunctionLibrary.initializeApplication(this._context, new InitAppImpl());
                } catch (Exception e) {
                    TransferState.transferFatalError(this._context.logMgr.out(LogMgr.CatExp.ERR, this, e));
                }
            } catch (Exception e2) {
                showFatalErrorView(LogMgr.getErrIdentifierCode(getApplicationContext(), this, e2));
            }
        } catch (Exception e3) {
            showFatalErrorView(LogMgr.getErrIdentifierCode(getApplicationContext(), this, e3));
        }
    }

    private boolean checkLastAppStatus() {
        int i = _lastAppStatus;
        if (i != 4) {
            return false;
        }
        TransferState.transferState(i, _lastAppData);
        return true;
    }

    @Override // android.app.Activity
    protected void onRestart() {
        AnalysisManager.stampRestart(this);
        try {
            super.onRestart();
        } catch (Exception unused) {
        }
    }

    @Override // com.felicanetworks.mfs.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        AnalysisManager.stampPause(this);
        try {
            super.onPause();
            if (isFinishing()) {
                destroyProc();
            }
        } catch (Exception unused) {
        }
    }

    @Override // com.felicanetworks.mfs.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        AnalysisManager.stampDestroy(this);
        try {
            super.onDestroy();
            destroyProc();
        } catch (Exception unused) {
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        try {
            super.onActivityResult(i, i2, intent);
            throw new IllegalArgumentException("Unknown requestCode, " + i);
        } catch (Exception e) {
            TransferState.transferFatalError(this._context.logMgr.out(LogMgr.CatExp.ERR, this, e));
        }
    }

    protected class MfsTransferStateControler extends AbstractTransferStateCommon {
        protected MfsTransferStateControler() {
        }

        @Override // com.felicanetworks.common.cmnview.AbstractTransferStateCommon
        public void onTransferState(int i, TransferStateData transferStateData, TransferSenderInfo transferSenderInfo) {
            AppStatus appStatus;
            FelicaErrorInfo felicaErrorInfo;
            try {
                int unused = MobileFeliCaSettings._lastAppStatus = i;
                TransferStateData unused2 = MobileFeliCaSettings._lastAppData = transferStateData;
                appStatus = MfsAppData.getAppStatus();
            } catch (Exception e) {
                TransferState.transferFatalError(MobileFeliCaSettings.this._context.logMgr.out(LogMgr.CatExp.ERR, MobileFeliCaSettings.this, e));
                return;
            }
            if (i == 15) {
                MobileFeliCaSettings.this.dialog = new LogSendingView();
                MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_NORMAL_DIALOG);
                MobileFeliCaSettings.this.showNormalDialog(MobileFeliCaSettings.this.dialog);
                return;
            }
            if (i == 26) {
                MobileFeliCaSettings.this.setResult(((MfsTransferStateData_Result) transferStateData).result, MobileFeliCaSettings.this.getResultIntent());
                MobileFeliCaSettings.this.finish();
                return;
            }
            if (i == 27) {
                MobileFeliCaSettings.this.showResultView();
                return;
            }
            if (i != 1000 && i != 1001) {
                switch (i) {
                    case 1:
                        MobileFeliCaSettings.this.dialog = new StartingView();
                        MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_NORMAL_DIALOG);
                        MobileFeliCaSettings.this.showNormalDialog(MobileFeliCaSettings.this.dialog);
                        return;
                    case 2:
                        if (MobileFeliCaSettings.this.isSkipTos) {
                            AnalysisManager.stamp(MfsStamp.Event.AUTO_DUMP_SKIP_WID_1_2, Boolean.valueOf(MobileFeliCaSettings.this.isSkipTos));
                            TransferState.transferState(3);
                            return;
                        } else {
                            MobileFeliCaSettings.this.dialog = new ConfirmInitView();
                            MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_NORMAL_DIALOG);
                            MobileFeliCaSettings.this.showNormalDialog(MobileFeliCaSettings.this.dialog);
                            return;
                        }
                    case 3:
                        if (appStatus.felicaInitState == AppStatus.State.CHECK_NEEDINIT) {
                            MobileFeliCaSettings.this.dialog = new RunningInitView();
                            MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_NORMAL_DIALOG);
                            MobileFeliCaSettings.this.showNormalDialog(MobileFeliCaSettings.this.dialog);
                            return;
                        }
                        TransferState.transferState(27);
                        return;
                    case 4:
                        MobileFeliCaSettings.this.setResult(1, MobileFeliCaSettings.this.getResultIntent());
                        MobileFeliCaSettings.this.dialog = new CompleteInitView().applyArgument(MobileFeliCaSettings.this.isSkipTos);
                        MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_NORMAL_DIALOG);
                        MobileFeliCaSettings.this.showNormalDialog(MobileFeliCaSettings.this.dialog);
                        return;
                    case 5:
                        MobileFeliCaSettings.this.setResult(2, MobileFeliCaSettings.this.getResultIntent());
                        MobileFeliCaSettings.this.dialog = new AlreadyInitView();
                        MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_NORMAL_DIALOG);
                        MobileFeliCaSettings.this.showNormalDialog(MobileFeliCaSettings.this.dialog);
                        return;
                    case 6:
                        MobileFeliCaSettings.this.setResult(104, MobileFeliCaSettings.this.getResultIntent());
                        MobileFeliCaSettings.this.dialog = new FeliCaInUseView();
                        MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_FATAL_ERROR_DIALOG);
                        MobileFeliCaSettings.this.showErrorDialog(MobileFeliCaSettings.this.dialog);
                        return;
                    case 7:
                        MobileFeliCaSettings.this.setResult(105, MobileFeliCaSettings.this.getResultIntent());
                        MobileFeliCaSettings.this.dialog = new FeliCaLockView();
                        MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_FATAL_ERROR_DIALOG);
                        MobileFeliCaSettings.this.showErrorDialog(MobileFeliCaSettings.this.dialog);
                        return;
                    default:
                        switch (i) {
                            case 9:
                                MobileFeliCaSettings.this.setResult(0, MobileFeliCaSettings.this.getResultIntent());
                                MobileFeliCaSettings.this.dialog = new CancelInitView();
                                MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_NORMAL_DIALOG);
                                MobileFeliCaSettings.this.showNormalDialog(MobileFeliCaSettings.this.dialog);
                                return;
                            case 10:
                                MobileFeliCaSettings.this.setResult(106, MobileFeliCaSettings.this.getResultIntent());
                                MobileFeliCaSettings.this.dialog = new FailedInitView();
                                MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_FATAL_ERROR_DIALOG);
                                MobileFeliCaSettings.this.showErrorDialog(MobileFeliCaSettings.this.dialog);
                                return;
                            case 11:
                                MobileFeliCaSettings.this.setResult(107, MobileFeliCaSettings.this.getResultIntent());
                                MobileFeliCaSettings.this.dialog = new CommunicationErrorView();
                                MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_FATAL_ERROR_DIALOG);
                                MobileFeliCaSettings.this.showErrorDialog(MobileFeliCaSettings.this.dialog);
                                return;
                            case 12:
                                MobileFeliCaSettings.this.setResult(108, MobileFeliCaSettings.this.getResultIntent());
                                MobileFeliCaSettings.this.dialog = new OvercrowdingErrorView();
                                MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_FATAL_ERROR_DIALOG);
                                MobileFeliCaSettings.this.showErrorDialog(MobileFeliCaSettings.this.dialog);
                                return;
                            case 13:
                                MobileFeliCaSettings.this.setResult(109, MobileFeliCaSettings.this.getResultIntent());
                                MobileFeliCaSettings.this.dialog = new ServerMaintenanceErrorView();
                                MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_FATAL_ERROR_DIALOG);
                                MobileFeliCaSettings.this.showErrorDialog(MobileFeliCaSettings.this.dialog);
                                return;
                            default:
                                switch (i) {
                                    case 17:
                                        MobileFeliCaSettings.this.setResult(104, MobileFeliCaSettings.this.getResultIntent());
                                        MobileFeliCaSettings.this.dialog = new FeliCaInUseProcessingView();
                                        MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_FATAL_ERROR_DIALOG);
                                        MobileFeliCaSettings.this.showErrorDialog(MobileFeliCaSettings.this.dialog);
                                        return;
                                    case 18:
                                        MobileFeliCaSettings.this.setResult(105, MobileFeliCaSettings.this.getResultIntent());
                                        MobileFeliCaSettings.this.dialog = new FeliCaLockProcessingView();
                                        MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_FATAL_ERROR_DIALOG);
                                        MobileFeliCaSettings.this.showErrorDialog(MobileFeliCaSettings.this.dialog);
                                        return;
                                    case 19:
                                        MobileFeliCaSettings.this.setResult(Result.FAILED_MFICVERSION, MobileFeliCaSettings.this.getFatalErrorResultIntent());
                                        MobileFeliCaSettings.this.dialog = new ConfirmVersionView();
                                        MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_FATAL_ERROR_DIALOG);
                                        MobileFeliCaSettings.this.showErrorDialog(MobileFeliCaSettings.this.dialog);
                                        return;
                                    case 20:
                                        if (transferStateData != null) {
                                            String str = transferStateData.errorId;
                                        }
                                        MobileFeliCaSettings.this.setResult(107, MobileFeliCaSettings.this.getFatalErrorResultIntent());
                                        MobileFeliCaSettings.this.dialog = new HttpErrorView();
                                        MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_FATAL_ERROR_DIALOG);
                                        MobileFeliCaSettings.this.showErrorDialog(MobileFeliCaSettings.this.dialog);
                                        return;
                                    case 21:
                                        str = transferStateData != null ? transferStateData.errorId : null;
                                        MobileFeliCaSettings.this.setResult(110, MobileFeliCaSettings.this.getFatalErrorResultIntent());
                                        MobileFeliCaSettings.this.dialog = new FeliCaSignatureFailedView().applyArgument(str);
                                        MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_FATAL_ERROR_DIALOG);
                                        MobileFeliCaSettings.this.showErrorDialog(MobileFeliCaSettings.this.dialog);
                                        return;
                                    case 22:
                                        str = transferStateData != null ? transferStateData.errorId : null;
                                        MobileFeliCaSettings.this.setResult(110, MobileFeliCaSettings.this.getFatalErrorResultIntent());
                                        MobileFeliCaSettings.this.dialog = new FeliCaTimeoutView().applyArgument(str);
                                        MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_FATAL_ERROR_DIALOG);
                                        MobileFeliCaSettings.this.showErrorDialog(MobileFeliCaSettings.this.dialog);
                                        return;
                                    case 23:
                                        if (transferStateData != null) {
                                            str = transferStateData.errorId;
                                            felicaErrorInfo = transferStateData.felicaErrInfo;
                                        } else {
                                            felicaErrorInfo = null;
                                        }
                                        MobileFeliCaSettings.this.setResult(110, MobileFeliCaSettings.this.getFatalErrorResultIntent());
                                        MobileFeliCaSettings.this.dialog = new FeliCaOtherErrorView().applyArgument(str, felicaErrorInfo);
                                        MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_FATAL_ERROR_DIALOG);
                                        MobileFeliCaSettings.this.showErrorDialog(MobileFeliCaSettings.this.dialog);
                                        return;
                                    default:
                                        switch (i) {
                                            case 30:
                                                MobileFeliCaSettings.this.setResult(Result.FAILED_MFCDISABLE, MobileFeliCaSettings.this.getFatalErrorResultIntent());
                                                MobileFeliCaSettings.this.dialog = new MfcDisableErrorView();
                                                MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_FATAL_ERROR_DIALOG);
                                                MobileFeliCaSettings.this.showErrorDialog(MobileFeliCaSettings.this.dialog);
                                                return;
                                            case 31:
                                                MobileFeliCaSettings.this.setResult(Result.FAILED_MFCNOTFOUND, MobileFeliCaSettings.this.getFatalErrorResultIntent());
                                                MobileFeliCaSettings.this.dialog = new MfcNotFoundView();
                                                MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_FATAL_ERROR_DIALOG);
                                                MobileFeliCaSettings.this.showErrorDialog(MobileFeliCaSettings.this.dialog);
                                                return;
                                            case 32:
                                                MobileFeliCaSettings.this.setResult(Result.FAILED_MFSVERSION, MobileFeliCaSettings.this.getFatalErrorResultIntent());
                                                MobileFeliCaSettings.this.dialog = new MfsVersionView();
                                                MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_FATAL_ERROR_DIALOG);
                                                MobileFeliCaSettings.this.showErrorDialog(MobileFeliCaSettings.this.dialog);
                                                return;
                                            case 33:
                                                MobileFeliCaSettings.this.setResult(Result.FAILED_NOTAPPLICABLEDEVICE, MobileFeliCaSettings.this.getFatalErrorResultIntent());
                                                MobileFeliCaSettings.this.dialog = new NotApplicableDevice();
                                                MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_FATAL_ERROR_DIALOG);
                                                MobileFeliCaSettings.this.showErrorDialog(MobileFeliCaSettings.this.dialog);
                                                return;
                                            default:
                                                MobileFeliCaSettings.this.setResult(110, MobileFeliCaSettings.this.getFatalErrorResultIntent());
                                                if (transferStateData != null) {
                                                    try {
                                                        str = transferStateData.errorId;
                                                    } catch (Exception unused3) {
                                                        MobileFeliCaSettings.this.finish();
                                                        return;
                                                    }
                                                }
                                                MobileFeliCaSettings.this.dialog = new FatalErrorView().applyArgument(str);
                                                MobileFeliCaSettings.this.dialog.setTargetTag(BaseActivity.TAG_FATAL_ERROR_DIALOG);
                                                MobileFeliCaSettings.this.showErrorDialog(MobileFeliCaSettings.this.dialog);
                                                return;
                                        }
                                }
                        }
                }
                TransferState.transferFatalError(MobileFeliCaSettings.this._context.logMgr.out(LogMgr.CatExp.ERR, MobileFeliCaSettings.this, e));
                return;
            }
            MobileFeliCaSettings.this.finish();
        }

        @Override // com.felicanetworks.common.cmnview.AbstractTransferStateCommon
        protected boolean isFatalErrorScreen(BaseView baseView) {
            return MobileFeliCaSettings.this.dialog.getTargetTag().equals(BaseActivity.TAG_FATAL_ERROR_DIALOG);
        }

        @Override // com.felicanetworks.common.cmnview.AbstractTransferStateCommon
        protected ViewLayer getViewLayer() {
            return MobileFeliCaSettings.this._viewLayer;
        }

        @Override // com.felicanetworks.common.cmnview.AbstractTransferStateCommon
        protected boolean isActivityAlive() {
            return MobileFeliCaSettings.this._isAlive;
        }
    }

    protected class InitAppImpl implements InitializeApplicationListener {
        protected InitAppImpl() {
        }

        @Override // com.felicanetworks.mfsctrl.InitializeApplicationListener
        public void onComplete() {
            try {
                if (MobileFeliCaSettings.this._context.logMgr.isExistErrReport()) {
                    TransferState.transferState(15);
                } else {
                    TransferState.transferState(1);
                }
            } catch (Exception e) {
                TransferState.transferFatalError(MobileFeliCaSettings.this._context.logMgr.out(LogMgr.CatExp.ERR, MobileFeliCaSettings.this, e));
            }
        }

        @Override // com.felicanetworks.mfsctrl.InitializeApplicationListener
        public void onError(String str) {
            TransferState.transferFatalError(str);
        }
    }

    private void nextViewOnFelica(AppStatus appStatus) {
        if (appStatus.felicaInitState == AppStatus.State.INIT_SUCCESS) {
            TransferState.transferState(4);
            return;
        }
        if (appStatus.felicaInitState == AppStatus.State.INIT_CANCEL) {
            MfsTransferStateData_Result mfsTransferStateData_Result = new MfsTransferStateData_Result();
            mfsTransferStateData_Result.result = 0;
            TransferState.transferState(26, mfsTransferStateData_Result);
        } else {
            throw new IllegalStateException("appStatus:" + appStatus);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showResultView() {
        nextViewOnFelica(MfsAppData.getAppStatus());
    }

    protected Intent getResultIntent() {
        AppStatus appStatus = MfsAppData.getAppStatus();
        Intent intent = new Intent();
        int i = AnonymousClass2.$SwitchMap$com$felicanetworks$mfs$AppStatus$State[appStatus.felicaInitState.ordinal()];
        if (i == 1) {
            intent.putExtra(RESULT_KEY_FELICA, 1);
        } else if (i == 2) {
            intent.putExtra(RESULT_KEY_FELICA, 2);
        } else if (i == 3 || i == 4 || i == 5) {
            intent.putExtra(RESULT_KEY_FELICA, 0);
        } else {
            intent.putExtra(RESULT_KEY_FELICA, 100);
        }
        if (appStatus.isNeedcheckbox) {
            intent.putExtra(RESULT_KEY_CHKBOX, appStatus.chk);
        }
        return intent;
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfs.MobileFeliCaSettings$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfs$AppStatus$State;

        static {
            int[] iArr = new int[AppStatus.State.values().length];
            $SwitchMap$com$felicanetworks$mfs$AppStatus$State = iArr;
            try {
                iArr[AppStatus.State.INIT_SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfs$AppStatus$State[AppStatus.State.CHECK_DONEINIT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfs$AppStatus$State[AppStatus.State.READY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfs$AppStatus$State[AppStatus.State.INIT_CANCEL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfs$AppStatus$State[AppStatus.State.CHECK_NEEDINIT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    protected Intent getFatalErrorResultIntent() {
        Intent intent = new Intent();
        intent.putExtra(RESULT_KEY_FELICA, 100);
        if (this._needcheckbox) {
            intent.putExtra(RESULT_KEY_CHKBOX, -1);
        }
        return intent;
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
        try {
            Handler handler = new Handler(Looper.getMainLooper()) { // from class: com.felicanetworks.mfs.MobileFeliCaSettings.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    super.handleMessage(message);
                    try {
                        MobileFeliCaSettings.this.destroyProc();
                    } catch (Exception unused) {
                    }
                }
            };
            handler.sendMessageAtFrontOfQueue(handler.obtainMessage());
        } catch (Exception unused) {
        }
    }
}
