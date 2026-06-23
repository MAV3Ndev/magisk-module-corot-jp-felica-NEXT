package com.felicanetworks.mfs.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import com.felicanetworks.mfs.R;
import com.felicanetworks.mfs.view.ConfirmInitView;

/* JADX INFO: loaded from: classes.dex */
public abstract class CustomDialogFragment extends DialogFragment {
    static final double DIALOG_WIDE_RATIO = 0.9d;
    static final int ICON_BOTTOM_MARGIN = 12;
    TextView body;
    private String mTargetTag = null;
    private Bitmap mIconImage = null;
    private CharSequence mTitle = null;
    private CharSequence mText = null;
    private CharSequence mText2 = null;
    private CharSequence mCheckText = null;
    private boolean mHorizontalProgressEnable = false;
    private int mHorizontalProgressValue = 0;
    private int mHorizontalProgressMax = 100;
    private boolean mCircleProgressEnable = false;
    private boolean mSkeletonLayoutEnable = false;
    private boolean mNegativeButtonEnable = false;
    private CharSequence mNegativeText = null;
    private OnClickListener mNegativeButtonListener = null;
    private boolean mPositiveButtonEnable = false;
    private CharSequence mPositiveText = null;
    private OnClickListener mPositiveButtonListener = null;
    private ConfirmInitView.CheckBoxListener mCheckButtonListener = null;
    private boolean mCancel = false;
    private boolean mCanceledOnTouchOutside = false;
    private OnCancelListener mCancelListener = null;
    private CharSequence span = null;
    Boolean confirmFlag = false;
    private boolean mCheck = false;
    private int currentOrientation = -1;
    private int currentScreenHeightDp = 0;
    private int currentScreenWidthDp = 0;

    public interface OnCancelListener {
        boolean onCancel();
    }

    public interface OnClickListener {
        boolean onClick();
    }

    public void onActivityDestroy() {
    }

    protected abstract void onSetupDialog();

    protected void onStartProcess(Bundle bundle) {
    }

    public CustomDialogFragment() {
        setShowsDialog(false);
    }

    @Override // androidx.fragment.app.DialogFragment
    public void dismiss() {
        if (getDialog() != null) {
            onDismiss(getDialog());
        }
    }

    public void setTargetTag(String str) {
        this.mTargetTag = str;
    }

    public void setSpan(CharSequence charSequence) {
        this.span = charSequence;
    }

    public void setConfirmFlag(Boolean bool) {
        this.confirmFlag = bool;
    }

    public String getTargetTag() {
        return this.mTargetTag;
    }

    public void setIcon(Bitmap bitmap) {
        this.mIconImage = bitmap;
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
    }

    public void setText(CharSequence charSequence) {
        this.mText = charSequence;
    }

    public void setText2(CharSequence charSequence) {
        this.mText2 = charSequence;
    }

    public void setCheckBoxText(CharSequence charSequence) {
        this.mCheckText = charSequence;
    }

    public void setHorizontalProgressEnable(boolean z) {
        this.mHorizontalProgressEnable = z;
    }

    public void setProgress(int i, int i2) {
        if (this.mHorizontalProgressEnable) {
            if (getShowsDialog() && getDialog() != null) {
                ProgressBar progressBar = (ProgressBar) getDialog().findViewById(R.id.pb_horizontal_progress);
                if (progressBar != null) {
                    progressBar.setMax(i2);
                    progressBar.setProgress(i);
                    return;
                }
                return;
            }
            this.mHorizontalProgressMax = i2;
            this.mHorizontalProgressValue = i;
        }
    }

    public void setCircleProgressEnable(boolean z) {
        this.mCircleProgressEnable = z;
    }

    public void setPositiveButtonEnable(boolean z) {
        this.mPositiveButtonEnable = z;
    }

    public void setNegativeButtonListener(OnClickListener onClickListener) {
        this.mNegativeButtonEnable = true;
        this.mNegativeButtonListener = onClickListener;
    }

    public void setPositiveButtonListener(OnClickListener onClickListener) {
        this.mPositiveButtonEnable = true;
        this.mPositiveButtonListener = onClickListener;
    }

    public void checkButtonListener(ConfirmInitView.CheckBoxListener checkBoxListener) {
        this.mCheck = true;
        this.mCheckButtonListener = checkBoxListener;
    }

    public void setNegativeText(CharSequence charSequence) {
        this.mNegativeButtonEnable = true;
        this.mNegativeText = charSequence;
    }

    public void setPositiveText(CharSequence charSequence) {
        this.mPositiveButtonEnable = true;
        this.mPositiveText = charSequence;
    }

    public void setCancelEnable(boolean z) {
        this.mCancel = z;
    }

    public void setCancelListener(OnCancelListener onCancelListener) {
        this.mCancel = true;
        this.mCanceledOnTouchOutside = false;
        this.mCancelListener = onCancelListener;
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
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

    public void setcheckBox(boolean z) {
        this.mCheck = z;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            onStartProcess(getArguments() != null ? getArguments() : new Bundle());
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        Dialog dialog;
        if (bundle != null) {
            return super.onCreateDialog(bundle);
        }
        onSetupDialog();
        Configuration configuration = getResources().getConfiguration();
        this.currentOrientation = configuration.orientation;
        this.currentScreenHeightDp = configuration.screenHeightDp;
        this.currentScreenWidthDp = configuration.screenWidthDp;
        if (this.mSkeletonLayoutEnable) {
            Dialog dialog2 = new Dialog(getActivity(), R.style.Theme_Main_DimEnableDialog);
            dialog2.getWindow().requestFeature(1);
            dialog2.setContentView(R.layout.skeleton_layout);
            return dialog2;
        }
        if (this.mCircleProgressEnable) {
            dialog = new Dialog(getActivity(), R.style.Theme_Main_DimEnableDialog);
        } else {
            dialog = new Dialog(getActivity(), R.style.Theme_Main_DimDisableDialog);
        }
        dialog.getWindow().requestFeature(1);
        dialog.setContentView(R.layout.custom_dialog);
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        attributes.width = (int) (((double) displayMetrics.widthPixels) * DIALOG_WIDE_RATIO);
        dialog.getWindow().setAttributes(attributes);
        CheckBox checkBox = (CheckBox) dialog.findViewById(R.id.cb_dlg_check);
        if (this.mCheck) {
            checkBox.setVisibility(0);
        } else {
            checkBox.setVisibility(8);
        }
        ImageView imageView = (ImageView) dialog.findViewById(R.id.iv_icon_image);
        if (this.mIconImage == null) {
            imageView.setVisibility(8);
        } else {
            imageView.setVisibility(0);
            imageView.setImageBitmap(this.mIconImage);
            LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.ll_title_layout);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin, (int) (displayMetrics.density * 12.0f));
            linearLayout.setLayoutParams(layoutParams);
        }
        TextView textView = (TextView) dialog.findViewById(R.id.tv_title_text);
        if (this.mTitle == null) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
            textView.setText(this.mTitle);
        }
        TextView textView2 = (TextView) dialog.findViewById(R.id.tv_body_text);
        this.body = textView2;
        if (this.mText == null) {
            textView2.setVisibility(8);
        } else {
            textView2.setVisibility(0);
            this.body.setText(this.mText);
            if (this.confirmFlag.booleanValue()) {
                this.body.setMovementMethod(LinkMovementMethod.getInstance());
                this.body.setFocusableInTouchMode(true);
                this.body.requestFocus();
            }
            CharSequence charSequence = this.span;
            if (charSequence != null) {
                this.body.setText(charSequence, TextView.BufferType.SPANNABLE);
            }
        }
        TextView textView3 = (TextView) dialog.findViewById(R.id.tv_body_text2);
        this.body = textView3;
        if (this.mText2 == null) {
            textView3.setVisibility(8);
        } else {
            textView3.setVisibility(0);
            this.body.setText(this.mText2);
        }
        TextView textView4 = (TextView) dialog.findViewById(R.id.tv_dlg_cb_msg);
        if (this.mCheckText == null) {
            textView4.setVisibility(8);
        } else {
            textView4.setVisibility(0);
            textView4.setText(this.mCheckText);
        }
        ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.pb_horizontal_progress);
        if (!this.mHorizontalProgressEnable) {
            progressBar.setVisibility(8);
        } else {
            progressBar.setVisibility(0);
            progressBar.setMax(this.mHorizontalProgressMax);
            progressBar.setProgress(this.mHorizontalProgressValue);
        }
        View viewFindViewById = dialog.findViewById(R.id.pg_circle_progress);
        if (!this.mCircleProgressEnable) {
            viewFindViewById.setVisibility(8);
        } else {
            viewFindViewById.setVisibility(0);
        }
        TextView textView5 = (TextView) dialog.findViewById(R.id.tv_negative_button);
        if (!this.mNegativeButtonEnable) {
            textView5.setVisibility(8);
        } else {
            textView5.setVisibility(0);
            textView5.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfs.view.CustomDialogFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (CustomDialogFragment.this.mNegativeButtonListener != null) {
                        if (CustomDialogFragment.this.mNegativeButtonListener.onClick() && CustomDialogFragment.this.getShowsDialog()) {
                            CustomDialogFragment.this.dismiss();
                            return;
                        }
                        return;
                    }
                    if (CustomDialogFragment.this.getShowsDialog()) {
                        CustomDialogFragment.this.dismiss();
                    }
                }
            });
            CharSequence charSequence2 = this.mNegativeText;
            if (charSequence2 != null) {
                textView5.setText(charSequence2);
            }
        }
        TextView textView6 = (TextView) dialog.findViewById(R.id.tv_positive_button);
        if (!this.mPositiveButtonEnable) {
            textView6.setVisibility(8);
        } else {
            textView6.setVisibility(0);
            textView6.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfs.view.CustomDialogFragment.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (CustomDialogFragment.this.mPositiveButtonListener != null) {
                        if (CustomDialogFragment.this.mPositiveButtonListener.onClick() && CustomDialogFragment.this.getShowsDialog()) {
                            CustomDialogFragment.this.dismiss();
                            return;
                        }
                        return;
                    }
                    if (CustomDialogFragment.this.getShowsDialog()) {
                        CustomDialogFragment.this.dismiss();
                    }
                }
            });
            if (this.mCheckText != null) {
                textView6.setEnabled(false);
                this.mCheckButtonListener.setCheckBox(checkBox);
                this.mCheckButtonListener.setTextView(textView6);
                checkBox.setOnClickListener(this.mCheckButtonListener);
            }
            CharSequence charSequence3 = this.mPositiveText;
            if (charSequence3 != null) {
                textView6.setText(charSequence3);
            }
        }
        setCancelable(this.mCancel);
        dialog.setCanceledOnTouchOutside(this.mCanceledOnTouchOutside);
        return dialog;
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.currentOrientation == configuration.orientation && this.currentScreenHeightDp == configuration.screenHeightDp && this.currentScreenWidthDp == configuration.screenWidthDp) {
            return;
        }
        this.currentOrientation = configuration.orientation;
        this.currentScreenHeightDp = configuration.screenHeightDp;
        this.currentScreenWidthDp = configuration.screenWidthDp;
        refreshLayout();
    }

    public void refreshLayout() {
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            dialog.getWindow().getAttributes();
            dialog.getWindow().setLayout((int) (((double) displayMetrics.widthPixels) * DIALOG_WIDE_RATIO), -2);
        }
    }
}
