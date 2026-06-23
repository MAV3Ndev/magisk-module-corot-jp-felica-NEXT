package com.felicanetworks.mfm.memory_clear;

import android.content.Intent;
import android.net.Uri;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.memory_clear.MemoryClearConstants;
import com.felicanetworks.mfm.memory_clear.MemoryClearCustomDialogFragment;

/* JADX INFO: loaded from: classes3.dex */
public class MemoryClearDialogFactory {
    static final String TAG_MEMORY_CLEAR_DIALOG = "memoryClearDialog";
    static final String TAG_MEMORY_CLEAR_EXECUTING_DIALOG = "memoryClearExecutingDialog";
    static final String TAG_MEMORY_CLEAR_PROCESSING_DIALOG = "memoryClearProcessingDialog";

    public static MemoryClearCustomDialogFragment createMemoryClearConfirmation(final MemoryClearActivity activity) {
        MemoryClearCustomDialogFragment memoryClearCustomDialogFragment = new MemoryClearCustomDialogFragment();
        memoryClearCustomDialogFragment.setPositiveButtonEnable(true);
        memoryClearCustomDialogFragment.setPositiveText(activity.getApplicationContext().getString(R.string.dlg_button_text_memory_clear_reset));
        memoryClearCustomDialogFragment.setNegativeButtonEnable(true);
        memoryClearCustomDialogFragment.setNegativeText(activity.getApplicationContext().getText(R.string.dlg_button_text_memory_clear_back));
        memoryClearCustomDialogFragment.setCircleProgressEnable(false);
        memoryClearCustomDialogFragment.setText(activity.getApplicationContext().getString(R.string.dlg_text_memory_clear_reset_start));
        memoryClearCustomDialogFragment.setTargetTag(TAG_MEMORY_CLEAR_DIALOG);
        memoryClearCustomDialogFragment.setPositiveButtonListener(new MemoryClearCustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearDialogFactory.1
            @Override // com.felicanetworks.mfm.memory_clear.MemoryClearCustomDialogFragment.OnClickListener
            public boolean onClick() {
                activity.sendMessage(MemoryClearConstants.EVENT_ID.EXECUTE);
                return true;
            }
        });
        return memoryClearCustomDialogFragment;
    }

    public static MemoryClearCustomDialogFragment createMemoryClearSuccess(final MemoryClearActivity activity, int resId) {
        MemoryClearCustomDialogFragment memoryClearCustomDialogFragmentCreateDialog = createDialog(activity);
        memoryClearCustomDialogFragmentCreateDialog.setText(activity.getApplicationContext().getString(resId));
        return memoryClearCustomDialogFragmentCreateDialog;
    }

    public static MemoryClearCustomDialogFragment createFailureVersionupDialog(final MemoryClearActivity activity, int resId, String packageName) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(String.format("market://details?id=%s", packageName)));
        intent.setPackage((String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_APP_PKG_PLAY_STORE));
        MemoryClearCustomDialogFragment memoryClearCustomDialogFragmentCreateFailureDialogWithStartApp = createFailureDialogWithStartApp(activity, intent);
        memoryClearCustomDialogFragmentCreateFailureDialogWithStartApp.setText(activity.getApplicationContext().getString(resId));
        return memoryClearCustomDialogFragmentCreateFailureDialogWithStartApp;
    }

    public static MemoryClearCustomDialogFragment createMemoryClearProcessingDialog(final MemoryClearActivity activity) {
        MemoryClearCustomDialogFragment memoryClearCustomDialogFragment = new MemoryClearCustomDialogFragment();
        memoryClearCustomDialogFragment.setPositiveButtonEnable(false);
        memoryClearCustomDialogFragment.setCircleProgressEnable(true);
        memoryClearCustomDialogFragment.setText(activity.getApplicationContext().getString(R.string.text_memory_clear_process));
        memoryClearCustomDialogFragment.setTargetTag(TAG_MEMORY_CLEAR_PROCESSING_DIALOG);
        return memoryClearCustomDialogFragment;
    }

    public static MemoryClearCustomDialogFragment createMemoryClearExecutingDialog(final MemoryClearActivity activity) {
        MemoryClearCustomDialogFragment memoryClearCustomDialogFragment = new MemoryClearCustomDialogFragment();
        memoryClearCustomDialogFragment.setPositiveButtonEnable(false);
        memoryClearCustomDialogFragment.setCircleProgressEnable(true);
        memoryClearCustomDialogFragment.setText(activity.getApplicationContext().getString(R.string.dlg_text_memory_clear_execute));
        memoryClearCustomDialogFragment.setTargetTag(TAG_MEMORY_CLEAR_EXECUTING_DIALOG);
        return memoryClearCustomDialogFragment;
    }

    private static MemoryClearCustomDialogFragment createDialog(final MemoryClearActivity activity) {
        MemoryClearCustomDialogFragment memoryClearCustomDialogFragment = new MemoryClearCustomDialogFragment();
        memoryClearCustomDialogFragment.setNegativeButtonEnable(false);
        memoryClearCustomDialogFragment.setPositiveButtonEnable(true);
        memoryClearCustomDialogFragment.setPositiveText(activity.getApplicationContext().getString(R.string.dlg_button_text_memory_clear_end));
        memoryClearCustomDialogFragment.setPositiveButtonListener(new MemoryClearCustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearDialogFactory.2
            @Override // com.felicanetworks.mfm.memory_clear.MemoryClearCustomDialogFragment.OnClickListener
            public boolean onClick() {
                activity.finish();
                return true;
            }
        });
        memoryClearCustomDialogFragment.setCircleProgressEnable(false);
        memoryClearCustomDialogFragment.setTargetTag(TAG_MEMORY_CLEAR_DIALOG);
        LogUtil.inquiryLog(activity.getApplicationContext(), "");
        return memoryClearCustomDialogFragment;
    }

    public static MemoryClearCustomDialogFragment createFailureDialog(final MemoryClearActivity activity, int resId) {
        MemoryClearCustomDialogFragment memoryClearCustomDialogFragmentCreateDialog = createDialog(activity);
        memoryClearCustomDialogFragmentCreateDialog.setText(activity.getApplicationContext().getString(resId));
        memoryClearCustomDialogFragmentCreateDialog.setPositiveText(activity.getApplicationContext().getString(R.string.dlg_error_button_text_memory_clear_end));
        LogUtil.inquiryLog(activity.getApplicationContext(), "");
        return memoryClearCustomDialogFragmentCreateDialog;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0042, code lost:
    
        r6 = r3.replaceAll("\\[|\\]", "").substring(1);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static MemoryClearCustomDialogFragment createFailureDialog(final MemoryClearActivity activity, int resId, final String errorCode) {
        MemoryClearCustomDialogFragment memoryClearCustomDialogFragmentCreateDialog = createDialog(activity);
        memoryClearCustomDialogFragmentCreateDialog.setText(activity.getApplicationContext().getString(resId) + errorCode);
        memoryClearCustomDialogFragmentCreateDialog.setPositiveText(activity.getApplicationContext().getString(R.string.dlg_error_button_text_memory_clear_end));
        String strSubstring = "";
        try {
            String[] strArrSplit = errorCode.split("\r?\n|\r");
            int length = strArrSplit.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                String str = strArrSplit[i];
                if (str.startsWith("[M")) {
                    break;
                }
                i++;
            }
        } catch (Exception unused) {
        }
        LogUtil.inquiryLog(activity.getApplicationContext(), strSubstring);
        return memoryClearCustomDialogFragmentCreateDialog;
    }

    private static MemoryClearCustomDialogFragment createFailureDialogWithStartApp(final MemoryClearActivity activity, final Intent intent) {
        MemoryClearCustomDialogFragment memoryClearCustomDialogFragment = new MemoryClearCustomDialogFragment();
        memoryClearCustomDialogFragment.setNegativeButtonEnable(false);
        memoryClearCustomDialogFragment.setPositiveButtonEnable(true);
        memoryClearCustomDialogFragment.setPositiveText(activity.getApplicationContext().getString(R.string.dlg_button_text_memory_launch_play_store));
        memoryClearCustomDialogFragment.setPositiveButtonListener(new MemoryClearCustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearDialogFactory.3
            @Override // com.felicanetworks.mfm.memory_clear.MemoryClearCustomDialogFragment.OnClickListener
            public boolean onClick() {
                try {
                    activity.startActivity(intent);
                    activity.finish();
                    return true;
                } catch (Exception e) {
                    LogUtil.error(e);
                    activity.sendMessageFailure(MemoryClearConstants.RESULT_CODE.FAILED_APP_START_FAILED);
                    return true;
                }
            }
        });
        memoryClearCustomDialogFragment.setCircleProgressEnable(false);
        memoryClearCustomDialogFragment.setTargetTag(TAG_MEMORY_CLEAR_DIALOG);
        return memoryClearCustomDialogFragment;
    }
}
