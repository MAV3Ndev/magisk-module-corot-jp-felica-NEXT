package com.felicanetworks.mfs.view;

import com.felicanetworks.analysis.AnalysisManager;
import com.felicanetworks.analysis.MfsStamp;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;
import com.felicanetworks.common.cmnview.TransferState;
import com.felicanetworks.mfs.MfsAppData;
import com.felicanetworks.mfs.R;
import com.felicanetworks.mfs.view.CustomDialogFragment;

/* JADX INFO: loaded from: classes.dex */
public class NotApplicableDevice extends CustomDialogFragment implements FunctionCodeInterface {
    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 2;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 32;
    }

    public void onDialogButtonClick() {
        try {
            TransferState.transferState(1000);
        } catch (Exception e) {
            TransferState.transferFatalError(MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, this, e));
        }
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    protected void onSetupDialog() {
        AnalysisManager.stamp(MfsStamp.Event.SCREEN_WID_1_30, new Object[0]);
        setTitle(getString(R.string.dlg_title_034));
        setPositiveText(getString(R.string.btn_msg_040));
        setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfs.view.NotApplicableDevice.1
            @Override // com.felicanetworks.mfs.view.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                NotApplicableDevice.this.onDialogButtonClick();
                return false;
            }
        });
        setText(getString(R.string.dlg_msg_042));
    }
}
