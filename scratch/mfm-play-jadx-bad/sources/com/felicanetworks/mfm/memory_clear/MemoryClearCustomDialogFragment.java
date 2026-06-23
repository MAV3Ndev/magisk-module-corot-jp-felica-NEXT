package com.felicanetworks.mfm.memory_clear;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import com.felicanetworks.mfm.main.R;

/* JADX INFO: loaded from: classes3.dex */
public class MemoryClearCustomDialogFragment extends DialogFragment {
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

    public MemoryClearCustomDialogFragment() {
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

    public void setTargetTag(String tag) {
        this.mTargetTag = tag;
    }

    public String getTargetTag() {
        return this.mTargetTag;
    }

    public void setTitle(CharSequence title) {
        this.mTitle = title;
    }

    public void setText(CharSequence text) {
        this.mText = text;
    }

    public void setCircleProgressEnable(boolean flag) {
        this.mCircleProgressEnable = flag;
    }

    public boolean isCircleProgressEnable() {
        return this.mCircleProgressEnable;
    }

    public void setSkeletonLayoutEnable(boolean flag) {
        this.mSkeletonLayoutEnable = flag;
    }

    public void setNegativeButtonEnable(boolean flag) {
        this.mNegativeButtonEnable = flag;
    }

    public void setPositiveButtonEnable(boolean flag) {
        this.mPositiveButtonEnable = flag;
    }

    public void setNegativeButtonListener(OnClickListener listener) {
        this.mNegativeButtonEnable = true;
        this.mNegativeButtonListener = listener;
    }

    public void setPositiveButtonListener(OnClickListener listener) {
        this.mPositiveButtonEnable = true;
        this.mPositiveButtonListener = listener;
    }

    public void setNegativeText(CharSequence negativeText) {
        this.mNegativeButtonEnable = true;
        this.mNegativeText = negativeText;
    }

    public void setPositiveText(CharSequence positiveText) {
        this.mPositiveButtonEnable = true;
        this.mPositiveText = positiveText;
    }

    public void setCancelEnable(boolean flag) {
        this.mCancel = flag;
    }

    public void setCancelListener(OnCancelListener listener) {
        this.mCancel = true;
        this.mCanceledOnTouchOutside = false;
        this.mCancelListener = listener;
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
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
            ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MemoryClearCircleProgressDialogTheme);
            progressDialog.setProgressStyle(0);
            CharSequence charSequence = this.mTitle;
            if (charSequence != null) {
                progressDialog.setTitle(charSequence);
            }
            CharSequence charSequence2 = this.mText;
            dialog = progressDialog;
            if (charSequence2 != null) {
                progressDialog.setMessage(charSequence2);
                dialog = progressDialog;
            }
        } else {
            dialog = new Dialog(getActivity(), R.style.MemoryClearDialogTheme);
        }
        dialog.getWindow().requestFeature(1);
        dialog.setContentView(R.layout.memory_clear_custom_dialog);
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
            viewFindViewById.setVisibility(8);
            textView2.setVisibility(8);
        }
        TextView textView3 = (TextView) dialog.findViewById(R.id.tv_negative_button);
        if (!this.mNegativeButtonEnable) {
            textView3.setVisibility(8);
        } else {
            textView3.setVisibility(0);
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearCustomDialogFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    if (MemoryClearCustomDialogFragment.this.mNegativeButtonListener != null) {
                        if (MemoryClearCustomDialogFragment.this.mNegativeButtonListener.onClick() && MemoryClearCustomDialogFragment.this.getShowsDialog()) {
                            MemoryClearCustomDialogFragment.this.dismiss();
                            return;
                        }
                        return;
                    }
                    if (MemoryClearCustomDialogFragment.this.getShowsDialog()) {
                        MemoryClearCustomDialogFragment.this.dismiss();
                    }
                }
            });
            CharSequence charSequence3 = this.mNegativeText;
            if (charSequence3 != null) {
                textView3.setText(charSequence3);
            }
        }
        TextView textView4 = (TextView) dialog.findViewById(R.id.tv_positive_button);
        if (!this.mPositiveButtonEnable) {
            textView4.setVisibility(8);
        } else {
            textView4.setVisibility(0);
            textView4.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearCustomDialogFragment.2
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    if (MemoryClearCustomDialogFragment.this.mPositiveButtonListener != null) {
                        if (MemoryClearCustomDialogFragment.this.mPositiveButtonListener.onClick() && MemoryClearCustomDialogFragment.this.getShowsDialog()) {
                            MemoryClearCustomDialogFragment.this.dismiss();
                            return;
                        }
                        return;
                    }
                    if (MemoryClearCustomDialogFragment.this.getShowsDialog()) {
                        MemoryClearCustomDialogFragment.this.dismiss();
                    }
                }
            });
            CharSequence charSequence4 = this.mPositiveText;
            if (charSequence4 != null) {
                textView4.setText(charSequence4);
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.mCurrentScreenWidthDp != newConfig.screenWidthDp) {
            this.mCurrentScreenWidthDp = newConfig.screenWidthDp;
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

    public void setOnActivityCreatedListener(OnActivityCreatedListener listener) {
        this.onActivityCreatedListener = listener;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        OnActivityCreatedListener onActivityCreatedListener = this.onActivityCreatedListener;
        if (onActivityCreatedListener != null) {
            onActivityCreatedListener.onActivityCreated();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
