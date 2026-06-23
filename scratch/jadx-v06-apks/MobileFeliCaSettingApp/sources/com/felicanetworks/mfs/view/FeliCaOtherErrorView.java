package com.felicanetworks.mfs.view;

import android.os.Bundle;
import com.felicanetworks.analysis.AnalysisManager;
import com.felicanetworks.analysis.MfsStamp;
import com.felicanetworks.common.cmnctrl.data.FelicaErrorInfo;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;
import com.felicanetworks.common.cmnview.TransferState;
import com.felicanetworks.mfs.MfsAppData;
import com.felicanetworks.mfs.R;
import com.felicanetworks.mfs.view.CustomDialogFragment;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class FeliCaOtherErrorView extends CustomDialogFragment implements FunctionCodeInterface {
    private static final String ARGUMENT_KEY_ERROR_ID = "error_id";
    private static final String ARGUMENT_KEY_FELICA_ERROR_INFO = "felica_error_info";
    private String _errorId;
    private FelicaErrorInfo _felicaErrInfo;
    private String _mfcErrorId;

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 29;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 32;
    }

    public FeliCaOtherErrorView applyArgument(String str, FelicaErrorInfo felicaErrorInfo) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_KEY_ERROR_ID, str);
        bundle.putSerializable(ARGUMENT_KEY_FELICA_ERROR_INFO, felicaErrorInfo);
        setArguments(bundle);
        return this;
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    protected void onStartProcess(Bundle bundle) {
        this._errorId = bundle.getString(ARGUMENT_KEY_ERROR_ID, "");
        FelicaErrorInfo felicaErrorInfo = (FelicaErrorInfo) bundle.getSerializable(ARGUMENT_KEY_FELICA_ERROR_INFO);
        this._felicaErrInfo = felicaErrorInfo;
        this._mfcErrorId = felicaErrorInfo != null ? makeMfcErrorId(felicaErrorInfo) : "";
    }

    public void onDialogButtonClick() {
        AnalysisManager.stamp(MfsStamp.Event.ACTION_WID_2_5_CLOSE, new Object[0]);
        try {
            TransferState.transferState(1001);
        } catch (Exception e) {
            MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, this, e);
        }
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    protected void onSetupDialog() {
        AnalysisManager.stamp(MfsStamp.Event.SCREEN_WID_2_5, this._errorId, this._felicaErrInfo);
        setTitle(getString(R.string.dlg_title_022));
        setNegativeText(getString(R.string.btn_msg_025));
        setNegativeButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfs.view.FeliCaOtherErrorView.1
            @Override // com.felicanetworks.mfs.view.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                FeliCaOtherErrorView.this.onDialogButtonClick();
                return false;
            }
        });
        setText(getString(R.string.dlg_msg_029));
        setText2(getString(R.string.dlg_msg_030).replaceFirst("%s", this._errorId).replaceFirst("%s", this._mfcErrorId));
    }

    private String makeMfcErrorId(FelicaErrorInfo felicaErrorInfo) {
        return felicaErrorInfo.genTypeValue + "." + String.format(Locale.US, "%02d", Integer.valueOf(felicaErrorInfo.errorID)) + "." + String.format(Locale.US, "%03d", Integer.valueOf(felicaErrorInfo.errorType));
    }
}
