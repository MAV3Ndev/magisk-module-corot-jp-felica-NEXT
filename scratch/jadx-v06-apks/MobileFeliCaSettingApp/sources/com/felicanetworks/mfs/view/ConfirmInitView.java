package com.felicanetworks.mfs.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.felicanetworks.analysis.AnalysisManager;
import com.felicanetworks.analysis.MfsStamp;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;
import com.felicanetworks.common.cmnlib.sg.SgMgrException;
import com.felicanetworks.common.cmnview.AppData;
import com.felicanetworks.common.cmnview.TransferState;
import com.felicanetworks.mfs.AppStatus;
import com.felicanetworks.mfs.MfsAppData;
import com.felicanetworks.mfs.R;
import com.felicanetworks.mfs.view.CustomDialogFragment;
import com.felicanetworks.mfs.view.ViewUtil;
import com.felicanetworks.mfslib.MfsAppContext;
import com.felicanetworks.mfslib.sg.MfsSgMgr;

/* JADX INFO: loaded from: classes.dex */
public class ConfirmInitView extends CustomDialogFragment implements FunctionCodeInterface, CompoundButton.OnCheckedChangeListener {
    private Button _button1 = null;
    protected MfsAppContext context;

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 11;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 32;
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    protected void onStartProcess(Bundle bundle) {
        this.context = (MfsAppContext) MfsAppData.getInstance().appContext;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (z) {
            this._button1.setEnabled(true);
        } else {
            this._button1.setEnabled(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void on1stDialogButtonClick() {
        AnalysisManager.stamp(MfsStamp.Event.ACTION_WID_1_2_YES, new Object[0]);
        try {
            TransferState.transferState(3);
        } catch (Exception e) {
            TransferState.transferFatalError(MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, this, e));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void on2ndDialogButtonClick() {
        AnalysisManager.stamp(MfsStamp.Event.ACTION_WID_1_2_NO, new Object[0]);
        try {
            AppStatus appStatus = MfsAppData.getAppStatus();
            if (appStatus.felicaInitState == AppStatus.State.CHECK_NEEDINIT) {
                appStatus.felicaInitState = AppStatus.State.INIT_CANCEL;
            }
            TransferState.transferState(27);
        } catch (Exception e) {
            TransferState.transferFatalError(MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, this, e));
        }
    }

    @Override // com.felicanetworks.mfs.view.CustomDialogFragment
    protected void onSetupDialog() {
        AnalysisManager.stamp(MfsStamp.Event.SCREEN_WID_1_2, new Object[0]);
        try {
            setTitle(getString(R.string.dlg_title_001));
            setPositiveText(getString(R.string.btn_msg_001));
            setNegativeText(getString(R.string.btn_msg_002));
            setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfs.view.ConfirmInitView.1
                @Override // com.felicanetworks.mfs.view.CustomDialogFragment.OnClickListener
                public boolean onClick() {
                    ConfirmInitView.this.on1stDialogButtonClick();
                    return false;
                }
            });
            setNegativeButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfs.view.ConfirmInitView.2
                @Override // com.felicanetworks.mfs.view.CustomDialogFragment.OnClickListener
                public boolean onClick() {
                    ConfirmInitView.this.on2ndDialogButtonClick();
                    return false;
                }
            });
            String strReplace = ((String) this.context.sgMgr.getSgValue(67)).replace("\\n", "\n");
            setMsg1Text(strReplace);
            setText(strReplace);
        } catch (Exception e) {
            TransferState.transferFatalError(MfsAppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, this, e));
        }
    }

    private void setMsg1Text(String str) throws SgMgrException {
        CharSequence charSequenceReplaceDisclamerLink = ViewUtil.replaceDisclamerLink(this.context, str, new LinkClickListener());
        if (charSequenceReplaceDisclamerLink != null) {
            setSpan(charSequenceReplaceDisclamerLink);
            setConfirmFlag(true);
            setcheckBox(true);
            setCheckBoxText((String) this.context.sgMgr.getSgValue(MfsSgMgr.KEY_UI_DISCLAMER_CHKBOX));
            checkButtonListener(new CheckBoxListener());
            return;
        }
        setText(str);
        setPositiveButtonEnable(true);
    }

    protected class LinkClickListener implements ViewUtil.ClickListener {
        protected LinkClickListener() {
        }

        @Override // com.felicanetworks.mfs.view.ViewUtil.ClickListener
        public void onClick(String str) {
            AnalysisManager.stamp(MfsStamp.Event.ACTION_WID_1_2_LINK, new Object[0]);
            try {
                ViewUtil.showBrowser(ConfirmInitView.this.context, str);
            } catch (Exception e) {
                TransferState.transferFatalError(AppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, ConfirmInitView.this, e));
            }
        }
    }

    class CheckBoxListener implements View.OnClickListener {
        TextView textView = null;
        CheckBox checkBox = null;

        CheckBoxListener() {
        }

        void setTextView(TextView textView) {
            this.textView = textView;
        }

        void setCheckBox(CheckBox checkBox) {
            this.checkBox = checkBox;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (this.checkBox.isChecked()) {
                this.textView.setEnabled(true);
            } else {
                this.textView.setEnabled(false);
            }
        }
    }
}
