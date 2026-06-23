package com.felicanetworks.mfs.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.felicanetworks.analysis.AnalysisManager;
import com.felicanetworks.analysis.MfsStamp;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;
import com.felicanetworks.common.cmnview.TransferState;
import com.felicanetworks.mfs.MfsAppData;
import com.felicanetworks.mfs.R;
import com.felicanetworks.mfs.view.CustomDialogFragment;
import com.felicanetworks.mfslib.MfsAppContext;

/* JADX INFO: loaded from: classes.dex */
public class MfcNotFoundView extends CustomDialogFragment implements FunctionCodeInterface {
    private MfsAppContext _context;

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 18;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 32;
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    protected void onStartProcess(Bundle bundle) {
        this._context = (MfsAppContext) MfsAppData.getInstance().appContext;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDialogNegativeButtonClick() {
        AnalysisManager.stamp(MfsStamp.Event.ACTION_WID_1_28_REQUEST_APP_UPDATE_NEGATIVE, new Object[0]);
        try {
            TransferState.transferState(1000);
        } catch (Exception e) {
            TransferState.transferFatalError(MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, this, e));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDialogPositiveButtonClick() {
        AnalysisManager.stamp(MfsStamp.Event.ACTION_WID_1_28_REQUEST_APP_UPDATE_POSITIVE, new Object[0]);
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(String.format("market://details?id=%s", this._context.sgMgr.getMfcPackageName())));
            startActivity(intent);
            TransferState.transferState(1000);
        } catch (Exception e) {
            TransferState.transferFatalError(MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, this, e));
        }
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    protected void onSetupDialog() {
        AnalysisManager.stamp(MfsStamp.Event.SCREEN_WID_1_28, new Object[0]);
        setTitle(getString(R.string.dlg_title_032));
        setPositiveText(getString(R.string.btn_msg_036));
        setNegativeText(getString(R.string.btn_msg_037));
        setNegativeButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfs.view.MfcNotFoundView.1
            @Override // com.felicanetworks.mfs.view.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                MfcNotFoundView.this.onDialogNegativeButtonClick();
                return false;
            }
        });
        setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfs.view.MfcNotFoundView.2
            @Override // com.felicanetworks.mfs.view.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                MfcNotFoundView.this.onDialogPositiveButtonClick();
                return false;
            }
        });
        setText(getString(R.string.dlg_msg_040));
    }
}
