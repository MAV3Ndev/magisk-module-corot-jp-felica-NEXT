package com.felicanetworks.mfs.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.felicanetworks.analysis.AnalysisManager;
import com.felicanetworks.analysis.MfsStamp;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.mfs.R;
import com.felicanetworks.mfs.view.CustomDialogFragment;

/* JADX INFO: loaded from: classes.dex */
public class FatalErrorBeforeAppInitView extends CustomDialogFragment implements FunctionCodeInterface {
    private static final String ARGUMENT_KEY_ERROR_ID = "error_id";
    private Activity _activity;
    private String _errorId;

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 14;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 32;
    }

    public FatalErrorBeforeAppInitView applyArgument(String str) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_KEY_ERROR_ID, str);
        setArguments(bundle);
        return this;
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    protected void onStartProcess(Bundle bundle) {
        super.onStartProcess(bundle);
        this._errorId = bundle.getString(ARGUMENT_KEY_ERROR_ID, "");
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this._activity = (Activity) context;
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    public void onActivityDestroy() {
        super.onActivityDestroy();
        dismiss();
    }

    public void onDialogButtonClick() {
        AnalysisManager.stamp(MfsStamp.Event.ACTION_WID_2_2_CLOSE, new Object[0]);
        try {
            this._activity.finish();
        } catch (Exception unused) {
        }
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    protected void onSetupDialog() {
        AnalysisManager.stamp(MfsStamp.Event.SCREEN_WID_2_2, this._errorId);
        setTitle(getString(R.string.dlg_title_019));
        setNegativeText(getString(R.string.btn_msg_022));
        setNegativeButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfs.view.FatalErrorBeforeAppInitView.1
            @Override // com.felicanetworks.mfs.view.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                FatalErrorBeforeAppInitView.this.onDialogButtonClick();
                return false;
            }
        });
        setText(getString(R.string.dlg_msg_023));
        setText2(getString(R.string.dlg_msg_024, this._errorId));
    }
}
