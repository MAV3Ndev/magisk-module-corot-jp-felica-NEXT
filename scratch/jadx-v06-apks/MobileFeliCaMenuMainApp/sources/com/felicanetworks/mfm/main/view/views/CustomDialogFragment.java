package com.felicanetworks.mfm.main.view.views;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.view.widget.CirculateProgressView;

/* JADX INFO: loaded from: classes.dex */
public class CustomDialogFragment extends DialogFragment {
    static final double DIALOG_WIDE_RATIO = 0.8d;
    private boolean isDismissed = false;
    private String mTargetTag = null;
    private CharSequence mTitle = null;
    private CharSequence mText = null;
    private boolean mCircleProgressEnable = false;
    private boolean mSkeletonLayoutEnable = false;
    private boolean mNegativeButtonEnable = false;
    private CharSequence mNegativeText = null;
    private OnClickListener mNegativeButtonListener = null;
    private boolean mPositiveButtonEnable = false;
    private CharSequence mPositiveText = null;
    private OnClickListener mPositiveButtonListener = null;
    private boolean mCancel = false;
    private boolean mCanceledOnTouchOutside = false;
    private OnCancelListener mCancelListener = null;
    private int mCurrentScreenWidthDp = 0;
    private OnActivityCreatedListener onActivityCreatedListener = null;
    private boolean mIsOnStop = false;
    private boolean mIsRefreshLayout = false;

    public interface OnActivityCreatedListener {
        void onActivityCreated();
    }

    public interface OnCancelListener {
        boolean onCancel();
    }

    public interface OnClickListener {
        boolean onClick();
    }

    public CustomDialogFragment() {
        setShowsDialog(false);
    }

    public boolean isDismissed() {
        return this.isDismissed;
    }

    @Override // androidx.fragment.app.DialogFragment
    public void dismiss() {
        this.isDismissed = true;
        if (getDialog() != null) {
            onDismiss(getDialog());
        }
    }

    public void setTargetTag(String str) {
        this.mTargetTag = str;
    }

    public String getTargetTag() {
        return this.mTargetTag;
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
    }

    public void setText(CharSequence charSequence) {
        this.mText = charSequence;
    }

    public void setCircleProgressEnable(boolean z) {
        this.mCircleProgressEnable = z;
    }

    public void setSkeletonLayoutEnable(boolean z) {
        this.mSkeletonLayoutEnable = z;
    }

    public void setNegativeButtonEnable(boolean z) {
        this.mNegativeButtonEnable = z;
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

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        Dialog dialog;
        this.mCurrentScreenWidthDp = getResources().getConfiguration().screenWidthDp;
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
        attributes.width = (int) (((double) getResources().getDisplayMetrics().widthPixels) * DIALOG_WIDE_RATIO);
        dialog.getWindow().setAttributes(attributes);
        TextView textView = (TextView) dialog.findViewById(R.id.tv_title_text);
        if (this.mTitle == null) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
            textView.setText(this.mTitle);
        }
        TextView textView2 = (TextView) dialog.findViewById(R.id.tv_body_text);
        if (this.mText == null) {
            textView2.setVisibility(8);
        } else {
            textView2.setVisibility(0);
            textView2.setText(this.mText);
        }
        View viewFindViewById = dialog.findViewById(R.id.pg_circle_progress);
        if (!this.mCircleProgressEnable) {
            viewFindViewById.setVisibility(8);
        } else {
            viewFindViewById.setVisibility(0);
            ((CirculateProgressView) viewFindViewById.findViewById(R.id.pb_cycle_progress_bar)).setText(this.mText);
            textView2.setVisibility(8);
        }
        TextView textView3 = (TextView) dialog.findViewById(R.id.tv_negative_button);
        if (!this.mNegativeButtonEnable) {
            textView3.setVisibility(8);
        } else {
            textView3.setVisibility(0);
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.CustomDialogFragment.1
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
            CharSequence charSequence = this.mNegativeText;
            if (charSequence != null) {
                textView3.setText(charSequence);
            }
        }
        TextView textView4 = (TextView) dialog.findViewById(R.id.tv_positive_button);
        if (!this.mPositiveButtonEnable) {
            textView4.setVisibility(8);
        } else {
            textView4.setVisibility(0);
            textView4.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.CustomDialogFragment.2
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
            CharSequence charSequence2 = this.mPositiveText;
            if (charSequence2 != null) {
                textView4.setText(charSequence2);
            }
        }
        setCancelable(this.mCancel);
        dialog.setCanceledOnTouchOutside(this.mCanceledOnTouchOutside);
        return dialog;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        if (this.mIsRefreshLayout) {
            refreshLayout();
        }
        this.mIsOnStop = false;
        this.mIsRefreshLayout = false;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.mIsOnStop = true;
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mCurrentScreenWidthDp != configuration.screenWidthDp) {
            this.mCurrentScreenWidthDp = configuration.screenWidthDp;
            if (!this.mIsOnStop) {
                refreshLayout();
            } else {
                this.mIsRefreshLayout = true;
            }
        }
    }

    public void refreshLayout() {
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout((int) (((double) getResources().getDisplayMetrics().widthPixels) * DIALOG_WIDE_RATIO), -2);
        }
    }

    public void setOnActivityCreatedListener(OnActivityCreatedListener onActivityCreatedListener) {
        this.onActivityCreatedListener = onActivityCreatedListener;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        OnActivityCreatedListener onActivityCreatedListener = this.onActivityCreatedListener;
        if (onActivityCreatedListener != null) {
            onActivityCreatedListener.onActivityCreated();
        }
    }
}
