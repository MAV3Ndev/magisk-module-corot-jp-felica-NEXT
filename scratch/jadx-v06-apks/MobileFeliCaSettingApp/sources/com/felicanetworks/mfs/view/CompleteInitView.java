package com.felicanetworks.mfs.view;

import android.os.Bundle;
import com.felicanetworks.analysis.AnalysisManager;
import com.felicanetworks.analysis.MfsStamp;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;
import com.felicanetworks.common.cmnview.TransferState;
import com.felicanetworks.mfs.MfsAppData;
import com.felicanetworks.mfs.R;
import com.felicanetworks.mfs.view.CustomDialogFragment;

/* JADX INFO: loaded from: classes.dex */
public class CompleteInitView extends CustomDialogFragment implements FunctionCodeInterface {
    private static final String ARGUMENT_KEY_IS_AGREED_TOS = "is_agreed_tos";
    private boolean isAgreedTos;

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 10;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 32;
    }

    public CompleteInitView applyArgument(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ARGUMENT_KEY_IS_AGREED_TOS, z);
        setArguments(bundle);
        return this;
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    protected void onStartProcess(Bundle bundle) {
        this.isAgreedTos = bundle.getBoolean(ARGUMENT_KEY_IS_AGREED_TOS);
    }

    public void onDialogButtonClick() {
        AnalysisManager.stamp(MfsStamp.Event.ACTION_WID_1_4_CLOSE, new Object[0]);
        try {
            TransferState.transferState(1000);
        } catch (Exception e) {
            TransferState.transferFatalError(MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, this, e));
        }
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    protected void onSetupDialog() {
        AnalysisManager.stamp(MfsStamp.Event.SCREEN_WID_1_4, new Object[0]);
        setTitle(getString(R.string.dlg_title_003));
        if (this.isAgreedTos) {
            setPositiveText(getString(R.string.btn_msg_next));
        } else {
            setPositiveText(getString(R.string.btn_msg_004));
        }
        setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfs.view.CompleteInitView.1
            @Override // com.felicanetworks.mfs.view.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                CompleteInitView.this.onDialogButtonClick();
                return true;
            }
        });
        setText(getString(R.string.dlg_msg_004));
    }
}
