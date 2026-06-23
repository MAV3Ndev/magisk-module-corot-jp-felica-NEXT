package com.felicanetworks.mfm.main.view.views;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.felicanetworks.mfm.main.R;

/* JADX INFO: loaded from: classes3.dex */
public class MfiDialogFragment extends CustomDialogFragment {
    private static final int MFI_TITLE_BOTTOM_MARGIN = 18;
    private String mTargetTag = null;
    private CharSequence mTitle = null;
    private CharSequence mText = null;
    private boolean mCircleProgressEnable = false;
    private boolean mCircleProgressCancelableEnable = false;
    private CharSequence mTextDescription = null;
    private CharSequence mTextDescription2 = null;
    private CharSequence mTextAccount = null;
    private CharSequence mTextNoAccount = null;
    private CharSequence mTextProcessing = null;
    private CharSequence mTextCancelButton = null;
    private OnClickListener mCancelButtonListener = null;
    private boolean mNegativeButtonEnable = false;
    private CharSequence mNegativeText = null;
    private OnClickListener mNegativeButtonListener = null;
    private boolean mPositiveButtonEnable = false;
    private CharSequence mPositiveText = null;
    private OnClickListener mPositiveButtonListener = null;
    private boolean mPositiveButtonResizable = false;
    private boolean mCancel = false;
    private boolean mCanceledOnTouchOutside = false;
    private OnCancelListener mCancelListener = null;
    private boolean mIsMfi = false;

    public interface OnCancelListener {
        boolean onCancel();
    }

    public interface OnClickListener {
        boolean onClick();
    }

    public MfiDialogFragment() {
        setShowsDialog(false);
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment, androidx.fragment.app.DialogFragment
    public void dismiss() {
        if (getDialog() != null) {
            onDismiss(getDialog());
        }
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment
    public void setTargetTag(String tag) {
        this.mTargetTag = tag;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment
    public String getTargetTag() {
        return this.mTargetTag;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment
    public void setTitle(CharSequence title) {
        this.mTitle = title;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment
    public void setText(CharSequence text) {
        this.mText = text;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment
    public void setCircleProgressEnable(boolean flag) {
        this.mCircleProgressEnable = flag;
    }

    public void setMfiChar(CharSequence description, CharSequence account, CharSequence noAccount) {
        this.mTextDescription = description;
        this.mTextAccount = account;
        this.mTextNoAccount = noAccount;
    }

    public void setMfiCharWithDesctiption2(CharSequence description, CharSequence account, CharSequence noAccount, CharSequence textDescription2) {
        this.mTextDescription = description;
        this.mTextAccount = account;
        this.mTextNoAccount = noAccount;
        this.mTextDescription2 = textDescription2;
    }

    public void setMfiCancelChar(CharSequence textProcessing, CharSequence textCancelButton) {
        this.mTextProcessing = textProcessing;
        this.mTextCancelButton = textCancelButton;
    }

    public void setMfiChar(CharSequence textProcessing) {
        this.mTextProcessing = textProcessing;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment
    public void setNegativeButtonEnable(boolean flag) {
        this.mNegativeButtonEnable = flag;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment
    public void setPositiveButtonEnable(boolean flag) {
        this.mPositiveButtonEnable = flag;
    }

    public void setPositiveButtonWrapSize(boolean flag) {
        this.mPositiveButtonResizable = flag;
    }

    public void setCancelMfiButtonListener(OnClickListener listener) {
        this.mCircleProgressCancelableEnable = true;
        this.mCancelButtonListener = listener;
    }

    public void setNegativeButtonListener(OnClickListener listener) {
        this.mNegativeButtonEnable = true;
        this.mNegativeButtonListener = listener;
    }

    public void setPositiveButtonListener(OnClickListener listener) {
        this.mPositiveButtonEnable = true;
        this.mPositiveButtonListener = listener;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment
    public void setNegativeText(CharSequence negativeText) {
        this.mNegativeButtonEnable = true;
        this.mNegativeText = negativeText;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment
    public void setPositiveText(CharSequence positiveText) {
        this.mPositiveButtonEnable = true;
        this.mPositiveText = positiveText;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment
    public void setCancelEnable(boolean flag) {
        this.mCancel = flag;
    }

    public void setCancelOnTouchOutside(boolean flag) {
        this.mCanceledOnTouchOutside = flag;
    }

    public void setMfiDialog() {
        this.mIsMfi = true;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment, androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialog) {
        if (this.mCancel) {
            OnCancelListener onCancelListener = this.mCancelListener;
            if (onCancelListener != null) {
                if (onCancelListener.onCancel() && getShowsDialog()) {
                    dismiss();
                    return;
                }
                return;
            }
            if (getShowsDialog()) {
                dismiss();
            }
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("mTargetTag", this.mTargetTag);
        outState.putCharSequence("mTitle", this.mTitle);
        outState.putCharSequence("mTextDescription", this.mTextDescription);
        outState.putCharSequence("mTextAccount", this.mTextAccount);
        outState.putCharSequence("mTextNoAccount", this.mTextNoAccount);
        outState.putCharSequence("mNegativeText", this.mNegativeText);
        outState.putCharSequence("mPositiveText", this.mPositiveText);
        outState.putCharSequence("mTextProcessing", this.mTextProcessing);
        outState.putCharSequence("mTextCancelButton", this.mTextCancelButton);
        outState.putBoolean("mNegativeButtonEnable", this.mNegativeButtonEnable);
        outState.putBoolean("mPositiveButtonEnable", this.mPositiveButtonEnable);
        outState.putBoolean("mPositiveButtonResizable", this.mPositiveButtonResizable);
        outState.putBoolean("mIsMfi", this.mIsMfi);
        outState.putBoolean("mCircleProgressEnable", this.mCircleProgressEnable);
        outState.putBoolean("mCircleProgressCancelableEnable", this.mCircleProgressCancelableEnable);
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment, androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog;
        TextView textView;
        if (savedInstanceState != null) {
            this.mTargetTag = (String) savedInstanceState.getCharSequence("mTargetTag");
            this.mTitle = savedInstanceState.getCharSequence("mTitle");
            this.mTextDescription = savedInstanceState.getCharSequence("mTextDescription");
            this.mTextAccount = savedInstanceState.getCharSequence("mTextAccount");
            this.mTextNoAccount = savedInstanceState.getCharSequence("mTextNoAccount");
            this.mNegativeText = savedInstanceState.getCharSequence("mNegativeText");
            this.mPositiveText = savedInstanceState.getCharSequence("mPositiveText");
            this.mTextProcessing = savedInstanceState.getCharSequence("mTextProcessing");
            this.mTextCancelButton = savedInstanceState.getCharSequence("mTextCancelButton");
            this.mNegativeButtonEnable = savedInstanceState.getBoolean("mNegativeButtonEnable");
            this.mPositiveButtonEnable = savedInstanceState.getBoolean("mPositiveButtonEnable");
            this.mPositiveButtonResizable = savedInstanceState.getBoolean("mPositiveButtonResizable");
            this.mIsMfi = savedInstanceState.getBoolean("mIsMfi");
            this.mCircleProgressEnable = savedInstanceState.getBoolean("mCircleProgressEnable");
            this.mCircleProgressCancelableEnable = savedInstanceState.getBoolean("mCircleProgressCancelableEnable");
        }
        if (this.mCircleProgressEnable || this.mCircleProgressCancelableEnable) {
            dialog = new Dialog(getActivity(), R.style.Theme_Main_DimEnableDialog);
        } else {
            dialog = new Dialog(getActivity(), R.style.Theme_Main_DimDisableDialog);
        }
        if (dialog.getWindow() != null) {
            dialog.getWindow().requestFeature(1);
        }
        dialog.setContentView(R.layout.custom_dialog_mfi);
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        attributes.width = (int) (((double) displayMetrics.widthPixels) * 0.8d);
        dialog.getWindow().setAttributes(attributes);
        TextView textView2 = (TextView) dialog.findViewById(R.id.tv_title_text);
        if (this.mTitle == null) {
            textView2.setVisibility(8);
        } else {
            textView2.setVisibility(0);
            textView2.setText(this.mTitle);
            if (this.mIsMfi) {
                LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.ll_title_layout);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin, (int) (displayMetrics.density * 18.0f));
                linearLayout.setLayoutParams(layoutParams);
            }
        }
        TextView textView3 = (TextView) dialog.findViewById(R.id.tv_body_text);
        if (this.mText == null) {
            textView3.setVisibility(8);
        } else {
            textView3.setVisibility(0);
            textView3.setText(this.mText);
        }
        View viewFindViewById = dialog.findViewById(R.id.pg_circle_progress);
        if (!this.mCircleProgressEnable && !this.mCircleProgressCancelableEnable) {
            viewFindViewById.setVisibility(8);
        } else {
            viewFindViewById.setVisibility(0);
        }
        TextView textView4 = (TextView) dialog.findViewById(R.id.tv_body_text_14dp_primary);
        TextView textView5 = (TextView) dialog.findViewById(R.id.tv_body_text_18dp_accent);
        TextView textView6 = (TextView) dialog.findViewById(R.id.tv_body_text_18dp_secondary);
        TextView textView7 = (TextView) dialog.findViewById(R.id.tv_body_text_14dp_secondary);
        if (this.mTextDescription == null) {
            textView4.setVisibility(8);
        } else {
            textView4.setVisibility(0);
            textView4.setText(this.mTextDescription);
        }
        if (this.mTextAccount == null) {
            textView5.setVisibility(8);
        } else {
            textView5.setVisibility(0);
            textView5.setText(this.mTextAccount);
        }
        if (this.mTextNoAccount == null) {
            textView6.setVisibility(8);
        } else {
            textView6.setVisibility(0);
            textView6.setText(this.mTextNoAccount);
        }
        if (this.mTextDescription2 == null) {
            textView7.setVisibility(8);
        } else {
            textView7.setVisibility(0);
            textView7.setText(this.mTextDescription2);
        }
        View viewFindViewById2 = dialog.findViewById(R.id.ll_bottom_right_button);
        View viewFindViewById3 = dialog.findViewById(R.id.ll_mfi_cancel);
        View viewFindViewById4 = dialog.findViewById(R.id.ll_mfi_cancel_pre);
        TextView textView8 = (TextView) dialog.findViewById(R.id.tv_mfi_cancel_description);
        Button button = (Button) dialog.findViewById(R.id.bt_mfi_cancel);
        if (this.mCircleProgressCancelableEnable) {
            viewFindViewById3.setVisibility(0);
            viewFindViewById4.setVisibility(0);
            viewFindViewById2.setVisibility(8);
            textView8.setVisibility(0);
            textView8.setText(this.mTextProcessing);
            button.setVisibility(0);
            button.setText(this.mTextCancelButton);
            button.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.MfiDialogFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    if (MfiDialogFragment.this.mCancelButtonListener != null) {
                        if (MfiDialogFragment.this.mCancelButtonListener.onClick() && MfiDialogFragment.this.getShowsDialog()) {
                            MfiDialogFragment.this.dismiss();
                            return;
                        }
                        return;
                    }
                    if (MfiDialogFragment.this.getShowsDialog()) {
                        MfiDialogFragment.this.dismiss();
                    }
                }
            });
        } else {
            viewFindViewById3.setVisibility(8);
            viewFindViewById2.setVisibility(0);
            TextView textView9 = (TextView) dialog.findViewById(R.id.tv_negative_button);
            if (!this.mNegativeButtonEnable) {
                textView9.setVisibility(8);
            } else {
                textView9.setVisibility(0);
                textView9.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.MfiDialogFragment.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        if (MfiDialogFragment.this.mNegativeButtonListener != null) {
                            if (MfiDialogFragment.this.mNegativeButtonListener.onClick() && MfiDialogFragment.this.getShowsDialog()) {
                                MfiDialogFragment.this.dismiss();
                                return;
                            }
                            return;
                        }
                        if (MfiDialogFragment.this.getShowsDialog()) {
                            MfiDialogFragment.this.dismiss();
                        }
                    }
                });
                CharSequence charSequence = this.mNegativeText;
                if (charSequence != null) {
                    textView9.setText(charSequence);
                }
            }
            if (this.mPositiveButtonResizable) {
                textView = (TextView) dialog.findViewById(R.id.tv_positive_button_wrap);
                textView.setVisibility(0);
                dialog.findViewById(R.id.tv_positive_button).setVisibility(8);
            } else {
                textView = (TextView) dialog.findViewById(R.id.tv_positive_button);
                textView.setVisibility(0);
                dialog.findViewById(R.id.tv_positive_button_wrap).setVisibility(8);
            }
            if (!this.mPositiveButtonEnable) {
                textView.setVisibility(8);
            } else {
                textView.setVisibility(0);
                textView.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.MfiDialogFragment.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        if (MfiDialogFragment.this.mPositiveButtonListener != null) {
                            if (MfiDialogFragment.this.mPositiveButtonListener.onClick() && MfiDialogFragment.this.getShowsDialog()) {
                                MfiDialogFragment.this.dismiss();
                                return;
                            }
                            return;
                        }
                        if (MfiDialogFragment.this.getShowsDialog()) {
                            MfiDialogFragment.this.dismiss();
                        }
                    }
                });
                CharSequence charSequence2 = this.mPositiveText;
                if (charSequence2 != null) {
                    textView.setText(charSequence2);
                }
            }
        }
        setCancelable(this.mCancel);
        dialog.setCanceledOnTouchOutside(this.mCanceledOnTouchOutside);
        return dialog;
    }
}
