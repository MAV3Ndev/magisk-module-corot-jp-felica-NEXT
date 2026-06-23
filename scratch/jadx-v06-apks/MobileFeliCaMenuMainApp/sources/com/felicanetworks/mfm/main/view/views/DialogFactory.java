package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import com.felicanetworks.mfm.main.R;

/* JADX INFO: loaded from: classes.dex */
public class DialogFactory {
    public static CustomDialogFragment createSingleChoiceDialog(Context context) {
        CustomDialogFragment customDialogFragment = new CustomDialogFragment();
        customDialogFragment.setNegativeButtonEnable(false);
        customDialogFragment.setPositiveButtonEnable(true);
        customDialogFragment.setPositiveText(context.getString(R.string.button_text_close));
        customDialogFragment.setCircleProgressEnable(false);
        return customDialogFragment;
    }

    public static MfiDialogFragment createCancelMfiDialog(Context context) {
        MfiDialogFragment mfiDialogFragment = new MfiDialogFragment();
        mfiDialogFragment.setNegativeButtonEnable(false);
        mfiDialogFragment.setPositiveButtonEnable(false);
        mfiDialogFragment.setCircleProgressEnable(false);
        return mfiDialogFragment;
    }

    public static CustomDialogFragment createSelectDialog(Context context) {
        CustomDialogFragment customDialogFragment = new CustomDialogFragment();
        customDialogFragment.setNegativeButtonEnable(true);
        customDialogFragment.setNegativeText(context.getString(R.string.button_text_negative));
        customDialogFragment.setPositiveButtonEnable(true);
        customDialogFragment.setPositiveText(context.getString(R.string.button_text_positive));
        customDialogFragment.setCircleProgressEnable(false);
        return customDialogFragment;
    }

    public static MfiDialogFragment createSelectMfiDialog(Context context) {
        MfiDialogFragment mfiDialogFragment = new MfiDialogFragment();
        mfiDialogFragment.setNegativeButtonEnable(true);
        mfiDialogFragment.setNegativeText(context.getString(R.string.button_text_negative));
        mfiDialogFragment.setPositiveButtonEnable(true);
        mfiDialogFragment.setPositiveText(context.getString(R.string.button_text_positive));
        return mfiDialogFragment;
    }

    public static DepositDialogFragment createSelectDepositDialog(Context context) {
        return new DepositDialogFragment();
    }

    public static SpSelectListDialogFragment createSpSelectListDialog() {
        return new SpSelectListDialogFragment();
    }

    public static CustomDialogFragment createCircleProgressDialog(Context context) {
        CustomDialogFragment customDialogFragment = new CustomDialogFragment();
        customDialogFragment.setPositiveButtonEnable(false);
        customDialogFragment.setCircleProgressEnable(true);
        return customDialogFragment;
    }

    public static CustomDialogFragment createCircleProgressDialog(Context context, String str) {
        CustomDialogFragment customDialogFragmentCreateCircleProgressDialog = createCircleProgressDialog(context);
        customDialogFragmentCreateCircleProgressDialog.setText(str);
        return customDialogFragmentCreateCircleProgressDialog;
    }
}
