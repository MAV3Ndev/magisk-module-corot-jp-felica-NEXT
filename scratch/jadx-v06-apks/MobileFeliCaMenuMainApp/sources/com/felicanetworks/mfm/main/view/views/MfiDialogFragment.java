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

/* JADX INFO: loaded from: classes.dex */
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
    public void setTargetTag(String str) {
        this.mTargetTag = str;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment
    public String getTargetTag() {
        return this.mTargetTag;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment
    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment
    public void setText(CharSequence charSequence) {
        this.mText = charSequence;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment
    public void setCircleProgressEnable(boolean z) {
        this.mCircleProgressEnable = z;
    }

    public void setMfiChar(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        this.mTextDescription = charSequence;
        this.mTextAccount = charSequence2;
        this.mTextNoAccount = charSequence3;
    }

    public void setMfiCharWithDesctiption2(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4) {
        this.mTextDescription = charSequence;
        this.mTextAccount = charSequence2;
        this.mTextNoAccount = charSequence3;
        this.mTextDescription2 = charSequence4;
    }

    public void setMfiCancelChar(CharSequence charSequence, CharSequence charSequence2) {
        this.mTextProcessing = charSequence;
        this.mTextCancelButton = charSequence2;
    }

    public void setMfiChar(CharSequence charSequence) {
        this.mTextProcessing = charSequence;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment
    public void setNegativeButtonEnable(boolean z) {
        this.mNegativeButtonEnable = z;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment
    public void setPositiveButtonEnable(boolean z) {
        this.mPositiveButtonEnable = z;
    }

    public void setPositiveButtonWrapSize(boolean z) {
        this.mPositiveButtonResizable = z;
    }

    public void setCancelMfiButtonListener(OnClickListener onClickListener) {
        this.mCircleProgressCancelableEnable = true;
        this.mCancelButtonListener = onClickListener;
    }

    public void setNegativeButtonListener(OnClickListener onClickListener) {
        this.mNegativeButtonEnable = true;
        this.mNegativeButtonListener = onClickListener;
    }

    public void setPositiveButtonListener(OnClickListener onClickListener) {
        this.mPositiveButtonEnable = true;
        this.mPositiveButtonListener = onClickListener;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment
    public void setNegativeText(CharSequence charSequence) {
        this.mNegativeButtonEnable = true;
        this.mNegativeText = charSequence;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment
    public void setPositiveText(CharSequence charSequence) {
        this.mPositiveButtonEnable = true;
        this.mPositiveText = charSequence;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment
    public void setCancelEnable(boolean z) {
        this.mCancel = z;
    }

    public void setCancelOnTouchOutside(boolean z) {
        this.mCanceledOnTouchOutside = z;
    }

    public void setMfiDialog() {
        this.mIsMfi = true;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment, androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
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
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putCharSequence("mTargetTag", this.mTargetTag);
        bundle.putCharSequence("mTitle", this.mTitle);
        bundle.putCharSequence("mTextDescription", this.mTextDescription);
        bundle.putCharSequence("mTextAccount", this.mTextAccount);
        bundle.putCharSequence("mTextNoAccount", this.mTextNoAccount);
        bundle.putCharSequence("mNegativeText", this.mNegativeText);
        bundle.putCharSequence("mPositiveText", this.mPositiveText);
        bundle.putCharSequence("mTextProcessing", this.mTextProcessing);
        bundle.putCharSequence("mTextCancelButton", this.mTextCancelButton);
        bundle.putBoolean("mNegativeButtonEnable", this.mNegativeButtonEnable);
        bundle.putBoolean("mPositiveButtonEnable", this.mPositiveButtonEnable);
        bundle.putBoolean("mPositiveButtonResizable", this.mPositiveButtonResizable);
        bundle.putBoolean("mIsMfi", this.mIsMfi);
        bundle.putBoolean("mCircleProgressEnable", this.mCircleProgressEnable);
        bundle.putBoolean("mCircleProgressCancelableEnable", this.mCircleProgressCancelableEnable);
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment, androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        Dialog dialog;
        TextView textView;
        if (bundle != null) {
            this.mTargetTag = (String) bundle.getCharSequence("mTargetTag");
            this.mTitle = bundle.getCharSequence("mTitle");
            this.mTextDescription = bundle.getCharSequence("mTextDescription");
            this.mTextAccount = bundle.getCharSequence("mTextAccount");
            this.mTextNoAccount = bundle.getCharSequence("mTextNoAccount");
            this.mNegativeText = bundle.getCharSequence("mNegativeText");
            this.mPositiveText = bundle.getCharSequence("mPositiveText");
            this.mTextProcessing = bundle.getCharSequence("mTextProcessing");
            this.mTextCancelButton = bundle.getCharSequence("mTextCancelButton");
            this.mNegativeButtonEnable = bundle.getBoolean("mNegativeButtonEnable");
            this.mPositiveButtonEnable = bundle.getBoolean("mPositiveButtonEnable");
            this.mPositiveButtonResizable = bundle.getBoolean("mPositiveButtonResizable");
            this.mIsMfi = bundle.getBoolean("mIsMfi");
            this.mCircleProgressEnable = bundle.getBoolean("mCircleProgressEnable");
            this.mCircleProgressCancelableEnable = bundle.getBoolean("mCircleProgressCancelableEnable");
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
                public void onClick(View view) {
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
                    public void onClick(View view) {
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
                    public void onClick(View view) {
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
