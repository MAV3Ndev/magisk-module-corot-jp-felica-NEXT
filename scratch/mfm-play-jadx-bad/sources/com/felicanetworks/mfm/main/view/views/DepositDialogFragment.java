package com.felicanetworks.mfm.main.view.views;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.core.net.MailTo;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;

/* JADX INFO: loaded from: classes3.dex */
public class DepositDialogFragment extends CustomDialogFragment {
    private OnClickListener mPositiveButtonListener = null;
    private OnClickListener mNegativeButtonListener = null;
    private CharSequence mTextAccount = null;

    public interface OnClickListener {
        boolean onClick();
    }

    public void setPositiveButtonListener(OnClickListener listener) {
        this.mPositiveButtonListener = listener;
    }

    public void setNegativeButtonListener(OnClickListener listener) {
        this.mNegativeButtonListener = listener;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment, androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dialog_model_change);
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = (int) (((double) getResources().getDisplayMetrics().widthPixels) * 0.8d);
        dialog.getWindow().setAttributes(attributes);
        TextView textView = (TextView) dialog.findViewById(R.id.tv_account);
        if (this.mTextAccount == null) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
            textView.setText(this.mTextAccount);
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.DepositDialogFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    try {
                        AnalysisManager.stamp(MfmStamp.Event.ACTION_ACCOUNT_NAME, new Object[0]);
                        DepositDialogFragment.this.startActivity(DepositDialogFragment.this.createIntentForMail());
                    } catch (Exception unused) {
                    }
                }
            });
        }
        ((TextView) dialog.findViewById(R.id.tv_negative_button)).setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.DepositDialogFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (DepositDialogFragment.this.mNegativeButtonListener != null) {
                    if (DepositDialogFragment.this.mNegativeButtonListener.onClick() && DepositDialogFragment.this.getShowsDialog()) {
                        DepositDialogFragment.this.dismiss();
                        return;
                    }
                    return;
                }
                if (DepositDialogFragment.this.getShowsDialog()) {
                    DepositDialogFragment.this.dismiss();
                }
            }
        });
        ((TextView) dialog.findViewById(R.id.tv_positive_button)).setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.DepositDialogFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (DepositDialogFragment.this.mPositiveButtonListener != null) {
                    if (DepositDialogFragment.this.mPositiveButtonListener.onClick() && DepositDialogFragment.this.getShowsDialog()) {
                        DepositDialogFragment.this.dismiss();
                        return;
                    }
                    return;
                }
                if (DepositDialogFragment.this.getShowsDialog()) {
                    DepositDialogFragment.this.dismiss();
                }
            }
        });
        setCancelable(false);
        return dialog;
    }

    public void setAccount(CharSequence account) {
        this.mTextAccount = account;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Intent createIntentForMail() {
        if (this.mTextAccount == null) {
            return null;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SENDTO");
        intent.setData(Uri.parse(MailTo.MAILTO_SCHEME));
        String str = getString(R.string.card_item_label_detail_screen_remind_mail_app_text_1) + ((Object) this.mTextAccount) + getString(R.string.card_item_label_detail_screen_remind_mail_app_text_2);
        intent.putExtra("android.intent.extra.EMAIL", new String[]{this.mTextAccount.toString()});
        intent.putExtra("android.intent.extra.SUBJECT", getString(R.string.card_item_label_detail_screen_remind_mail_app_subject));
        intent.putExtra("android.intent.extra.TEXT", str);
        return intent;
    }
}
