package com.felicanetworks.mfs.view;

import android.content.DialogInterface;
import android.os.Bundle;
import com.felicanetworks.analysis.AnalysisManager;
import com.felicanetworks.analysis.MfsStamp;
import com.felicanetworks.common.cmnctrl.data.FelicaErrorInfo;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;
import com.felicanetworks.common.cmnview.TransferState;
import com.felicanetworks.mfs.AppStatus;
import com.felicanetworks.mfs.MfsAppData;
import com.felicanetworks.mfs.MfsTransferStateData;
import com.felicanetworks.mfs.R;
import com.felicanetworks.mfs.view.CustomDialogFragment;
import com.felicanetworks.mfsctrl.ControlFunctionException;
import com.felicanetworks.mfsctrl.ControlFunctionLibrary;
import com.felicanetworks.mfsctrl.RunInitializationListener;

/* JADX INFO: loaded from: classes.dex */
public class RunningInitView extends CustomDialogFragment implements FunctionCodeInterface {
    private final int PROGRESS_MAXVAL = 100;
    private AppStatus appStatus = MfsAppData.getAppStatus();

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 23;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 32;
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    public void setProgress(int i, int i2) {
        super.setProgress(i, i2);
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    protected void onStartProcess(Bundle bundle) {
        try {
            ControlFunctionLibrary.getInstance().startRunInitialization(new FeliCaChipInitImpl());
        } catch (ControlFunctionException e) {
            TransferState.transferFatalError(MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, this, e));
        }
    }

    public void onDialogButtonClick() {
        AnalysisManager.stamp(MfsStamp.Event.ACTION_WID_1_3_CANCEL_INITIALIZE, new Object[0]);
        try {
            ControlFunctionLibrary.getInstance().stopRunInitialization();
            this.appStatus.felicaInitState = AppStatus.State.INIT_CANCEL;
            TransferState.transferState(9);
        } catch (Exception e) {
            TransferState.transferFatalError(MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, this, e));
        }
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    protected void onSetupDialog() {
        AnalysisManager.stamp(MfsStamp.Event.SCREEN_WID_1_3, new Object[0]);
        setHorizontalProgressEnable(true);
        setCircleProgressEnable(false);
        setTitle(getString(R.string.dlg_title_002));
        setNegativeText(getString(R.string.btn_msg_003));
        setNegativeButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfs.view.RunningInitView.1
            @Override // com.felicanetworks.mfs.view.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                RunningInitView.this.onDialogButtonClick();
                return false;
            }
        });
        setText(getString(R.string.dlg_msg_002));
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    public void onActivityDestroy() {
        try {
            ControlFunctionLibrary.getInstance().stopRunInitialization();
        } catch (ControlFunctionException e) {
            MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, this, e);
        }
    }

    protected class FeliCaChipInitImpl implements RunInitializationListener {
        protected FeliCaChipInitImpl() {
        }

        @Override // com.felicanetworks.mfsctrl.RunInitializationListener
        public void onComplete() {
            try {
                RunningInitView.this.setProgress(100, 100);
                RunningInitView.this.appStatus.felicaInitState = AppStatus.State.INIT_SUCCESS;
                TransferState.transferState(27);
            } catch (Exception e) {
                TransferState.transferFatalError(MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, RunningInitView.this, e));
            }
        }

        @Override // com.felicanetworks.mfsctrl.RunInitializationListener
        public void onProgress(int i) {
            try {
                RunningInitView.this.setProgress(i, 100);
            } catch (Exception e) {
                try {
                    ControlFunctionLibrary.getInstance().stopRunInitialization();
                } catch (ControlFunctionException e2) {
                    MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, RunningInitView.this, e2);
                }
                TransferState.transferFatalError(MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, RunningInitView.this, e));
            }
        }

        @Override // com.felicanetworks.mfsctrl.RunInitializationListener
        public void onWarning(int i, int i2) {
            try {
                RunningInitView.this.appStatus.felicaInitState = AppStatus.State.INIT_FAILED;
                if (i != 16) {
                    switch (i) {
                        case 3:
                            TransferState.transferState(12);
                            break;
                        case 4:
                            TransferState.transferState(13);
                            break;
                        case 5:
                            TransferState.transferState(10);
                            break;
                        case 6:
                            MfsTransferStateData mfsTransferStateData = new MfsTransferStateData();
                            mfsTransferStateData.pid = i2;
                            TransferState.transferState(17, mfsTransferStateData);
                            break;
                        case 7:
                            TransferState.transferState(18);
                            break;
                        case 8:
                            TransferState.transferState(11);
                            break;
                        default:
                            TransferState.transferFatalError(MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, RunningInitView.this, new Exception("Other Error at Running Initialization")));
                            break;
                    }
                } else {
                    TransferState.transferState(33);
                }
            } catch (Exception e) {
                TransferState.transferFatalError(MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, RunningInitView.this, e));
            }
        }

        @Override // com.felicanetworks.mfsctrl.RunInitializationListener
        public void onError(int i, String str, FelicaErrorInfo felicaErrorInfo) {
            try {
                RunningInitView.this.appStatus.felicaInitState = AppStatus.State.INIT_FAILED;
                MfsTransferStateData mfsTransferStateData = new MfsTransferStateData();
                switch (i) {
                    case 11:
                        mfsTransferStateData.errorId = str;
                        TransferState.transferState(21, mfsTransferStateData);
                        break;
                    case 12:
                        mfsTransferStateData.errorId = str;
                        TransferState.transferState(22, mfsTransferStateData);
                        break;
                    case 13:
                        mfsTransferStateData.errorId = str;
                        TransferState.transferState(19, mfsTransferStateData);
                        break;
                    case 14:
                    default:
                        TransferState.transferFatalError(str);
                        break;
                    case 15:
                        mfsTransferStateData.errorId = str;
                        mfsTransferStateData.felicaErrInfo = felicaErrorInfo;
                        TransferState.transferState(23, mfsTransferStateData);
                        break;
                }
            } catch (Exception e) {
                TransferState.transferFatalError(MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, RunningInitView.this, e));
            }
        }
    }
}
