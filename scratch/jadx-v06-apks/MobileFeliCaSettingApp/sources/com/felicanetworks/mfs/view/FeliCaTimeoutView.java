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
public class FeliCaTimeoutView extends CustomDialogFragment implements FunctionCodeInterface {
    private static final String ARGUMENT_KEY_ERROR_ID = "error_id";
    private String _errorId;

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 28;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 32;
    }

    public FeliCaTimeoutView applyArgument(String str) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_KEY_ERROR_ID, str);
        setArguments(bundle);
        return this;
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    protected void onStartProcess(Bundle bundle) {
        this._errorId = bundle.getString(ARGUMENT_KEY_ERROR_ID, "");
    }

    public void onDialogButtonClick() {
        AnalysisManager.stamp(MfsStamp.Event.ACTION_WID_2_4_CLOSE, new Object[0]);
        try {
            TransferState.transferState(1001);
        } catch (Exception e) {
            MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, this, e);
        }
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    protected void onSetupDialog() {
        AnalysisManager.stamp(MfsStamp.Event.SCREEN_WID_2_4, this._errorId);
        setTitle(getString(R.string.dlg_title_021));
        setNegativeText(getString(R.string.btn_msg_024));
        setNegativeButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfs.view.FeliCaTimeoutView.1
            @Override // com.felicanetworks.mfs.view.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                FeliCaTimeoutView.this.onDialogButtonClick();
                return false;
            }
        });
        setText(getString(R.string.dlg_msg_027));
        setText2(getString(R.string.dlg_msg_028, this._errorId));
    }
}
