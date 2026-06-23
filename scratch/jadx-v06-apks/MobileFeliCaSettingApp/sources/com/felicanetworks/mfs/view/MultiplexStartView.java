package com.felicanetworks.mfs.view;

import android.app.Activity;
import android.content.Context;
import com.felicanetworks.analysis.AnalysisManager;
import com.felicanetworks.analysis.MfsStamp;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.mfs.R;
import com.felicanetworks.mfs.view.CustomDialogFragment;

/* JADX INFO: loaded from: classes.dex */
public class MultiplexStartView extends CustomDialogFragment implements FunctionCodeInterface {
    private Activity _activity;

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 21;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 32;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this._activity = (Activity) context;
    }

    public void onDialogButtonClick() {
        AnalysisManager.stamp(MfsStamp.Event.ACTION_WID_1_8_CLOSE, new Object[0]);
        try {
            this._activity.finish();
        } catch (Exception unused) {
        }
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    protected void onSetupDialog() {
        AnalysisManager.stamp(MfsStamp.Event.SCREEN_WID_1_8, new Object[0]);
        setTitle(getString(R.string.dlg_title_007));
        setNegativeText(getString(R.string.btn_msg_008));
        setNegativeButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfs.view.MultiplexStartView.1
            @Override // com.felicanetworks.mfs.view.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                MultiplexStartView.this.onDialogButtonClick();
                return false;
            }
        });
        setText(getString(R.string.dlg_msg_008));
    }
}
