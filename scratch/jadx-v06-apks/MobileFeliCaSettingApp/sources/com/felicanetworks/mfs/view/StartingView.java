package com.felicanetworks.mfs.view;

import android.os.Bundle;
import com.felicanetworks.analysis.AnalysisManager;
import com.felicanetworks.analysis.MfsStamp;
import com.felicanetworks.common.cmnctrl.data.FelicaErrorInfo;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;
import com.felicanetworks.common.cmnview.AppData;
import com.felicanetworks.common.cmnview.TransferState;
import com.felicanetworks.mfs.AppStatus;
import com.felicanetworks.mfs.MfsAppData;
import com.felicanetworks.mfs.MfsTransferStateData;
import com.felicanetworks.mfs.view.CustomDialogFragment;
import com.felicanetworks.mfsctrl.CheckInitializationListener;
import com.felicanetworks.mfsctrl.ControlFunctionException;
import com.felicanetworks.mfsctrl.ControlFunctionLibrary;

/* JADX INFO: loaded from: classes.dex */
public class StartingView extends CustomDialogFragment implements FunctionCodeInterface {
    private ControlFunctionLibrary _cfl = ControlFunctionLibrary.getInstance();
    private AppStatus appStatus = MfsAppData.getAppStatus();

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 25;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 32;
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    protected void onStartProcess(Bundle bundle) {
        try {
            this._cfl.startCheckInitialization(new CheckFelicaInitImpl());
        } catch (ControlFunctionException e) {
            TransferState.transferFatalError(AppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, this, e));
        }
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    protected void onSetupDialog() {
        AnalysisManager.stamp(MfsStamp.Event.SCREEN_WID_1_1, new Object[0]);
        setCircleProgressEnable(true);
        setCancelEnable(true);
        setCancelListener(new CustomDialogFragment.OnCancelListener() { // from class: com.felicanetworks.mfs.view.StartingView.1
            @Override // com.felicanetworks.mfs.view.CustomDialogFragment.OnCancelListener
            public boolean onCancel() {
                try {
                    StartingView.this._cfl.stopCheckInitialization();
                } catch (ControlFunctionException unused) {
                }
                TransferState.transferState(1001);
                return true;
            }
        });
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    public void onActivityDestroy() {
        super.onActivityDestroy();
        try {
            this._cfl.stopCheckInitialization();
        } catch (ControlFunctionException e) {
            AppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, this, e);
        }
    }

    protected class CheckFelicaInitImpl implements CheckInitializationListener {
        protected CheckFelicaInitImpl() {
        }

        @Override // com.felicanetworks.mfsctrl.CheckInitializationListener
        public void onComplete(int i) {
            StartingView.this.appStatus.felicaStatus = i;
            try {
                if (i == 0) {
                    StartingView.this.appStatus.felicaInitState = AppStatus.State.CHECK_NEEDINIT;
                    StartingView.this.judgeStatus();
                } else if (i == 1) {
                    StartingView.this.appStatus.felicaInitState = AppStatus.State.CHECK_DONEINIT;
                    StartingView.this.judgeStatus();
                } else {
                    TransferState.transferFatalError(MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, StartingView.this, new Exception("Other Error at Complete Parame " + i)));
                }
            } catch (Exception e) {
                TransferState.transferFatalError(MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, StartingView.this, e));
            }
        }

        @Override // com.felicanetworks.mfsctrl.CheckInitializationListener
        public void onWarning(int i, int i2) {
            try {
                StartingView.this.appStatus.felicaInitState = AppStatus.State.CHECK_FAILED;
                if (i == 2) {
                    MfsTransferStateData mfsTransferStateData = new MfsTransferStateData();
                    mfsTransferStateData.pid = i2;
                    TransferState.transferState(6, mfsTransferStateData);
                } else if (i == 3) {
                    TransferState.transferState(7);
                } else if (i == 14) {
                    TransferState.transferState(11);
                } else {
                    TransferState.transferFatalError(MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, StartingView.this, new Exception()));
                }
            } catch (Exception e) {
                TransferState.transferFatalError(MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, StartingView.this, e));
            }
        }

        @Override // com.felicanetworks.mfsctrl.CheckInitializationListener
        public void onError(int i, String str, FelicaErrorInfo felicaErrorInfo) {
            try {
                StartingView.this.appStatus.felicaInitState = AppStatus.State.CHECK_FAILED;
                MfsTransferStateData mfsTransferStateData = new MfsTransferStateData();
                switch (i) {
                    case 6:
                        mfsTransferStateData.errorId = str;
                        TransferState.transferState(21, mfsTransferStateData);
                        break;
                    case 7:
                        mfsTransferStateData.errorId = str;
                        TransferState.transferState(22, mfsTransferStateData);
                        break;
                    case 8:
                        mfsTransferStateData.errorId = str;
                        mfsTransferStateData.felicaErrInfo = felicaErrorInfo;
                        TransferState.transferState(23, mfsTransferStateData);
                        break;
                    case 9:
                        mfsTransferStateData.errorId = str;
                        TransferState.transferState(19, mfsTransferStateData);
                        break;
                    case 10:
                        mfsTransferStateData.errorId = str;
                        TransferState.transferState(20, mfsTransferStateData);
                        break;
                    case 11:
                        TransferState.transferState(31);
                        break;
                    case 12:
                        TransferState.transferState(30);
                        break;
                    case 13:
                        TransferState.transferState(32);
                        break;
                    default:
                        TransferState.transferFatalError(str);
                        break;
                }
            } catch (Exception e) {
                TransferState.transferFatalError(MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, StartingView.this, e));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void judgeStatus() {
        if (this.appStatus.felicaInitState == AppStatus.State.CHECK_DONEINIT) {
            TransferState.transferState(5);
        } else {
            TransferState.transferState(2);
        }
    }
}
