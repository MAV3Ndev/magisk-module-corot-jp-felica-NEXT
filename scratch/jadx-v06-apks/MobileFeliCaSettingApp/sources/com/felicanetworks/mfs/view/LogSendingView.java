package com.felicanetworks.mfs.view;

import android.os.Bundle;
import com.felicanetworks.analysis.AnalysisManager;
import com.felicanetworks.analysis.MfsStamp;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;
import com.felicanetworks.common.cmnlib.log.LogMgrEventListener;
import com.felicanetworks.common.cmnview.TransferState;
import com.felicanetworks.mfs.MfsAppData;
import com.felicanetworks.mfs.view.CustomDialogFragment;

/* JADX INFO: loaded from: classes.dex */
public class LogSendingView extends CustomDialogFragment implements FunctionCodeInterface {
    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 20;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 32;
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    protected void onStartProcess(Bundle bundle) {
        try {
            try {
                MfsAppData.getInstance().logMgr.sendErrReport(new LogMgrEventImpl());
            } catch (Exception unused) {
                TransferState.transferState(1);
            }
        } finally {
            MfsAppData.getInstance().logMgr.deleteErrReport();
        }
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    protected void onSetupDialog() {
        AnalysisManager.stamp(MfsStamp.Event.SCREEN_WID_1_15, new Object[0]);
        setCircleProgressEnable(true);
        setCancelEnable(true);
        setCancelListener(new CustomDialogFragment.OnCancelListener() { // from class: com.felicanetworks.mfs.view.LogSendingView.1
            @Override // com.felicanetworks.mfs.view.CustomDialogFragment.OnCancelListener
            public boolean onCancel() {
                MfsAppData.getInstance().logMgr.stopSendingErrReport();
                TransferState.transferState(1001);
                return true;
            }
        });
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    public void onActivityDestroy() {
        try {
            MfsAppData.getInstance().logMgr.stopSendingErrReport();
        } catch (Exception e) {
            MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.WAR, this, e);
        }
    }

    protected class LogMgrEventImpl implements LogMgrEventListener {
        protected LogMgrEventImpl() {
        }

        @Override // com.felicanetworks.common.cmnlib.log.LogMgrEventListener
        public void finished(int i, String str) {
            try {
                TransferState.transferState(1);
            } catch (Exception e) {
                TransferState.transferFatalError(MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, LogSendingView.this, e));
            }
        }
    }
}
