package com.felicanetworks.mfs;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.felicanetworks.mfs.view.CustomDialogFragment;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseActivity extends AppCompatActivity {
    public static final String TAG_FATAL_ERROR_DIALOG = "FatalError";
    public static final String TAG_NORMAL_DIALOG = "Normal";
    public static final String TAG_WARNING_DIALOG = "Warning";
    public CustomDialogFragment dialog = null;
    private ActivityStatus mActivityStatus = ActivityStatus.IDLE;
    private CustomDialogFragment mDialogFragment = null;

    public enum ActivityStatus {
        IDLE,
        BACKGROUND,
        FOREGROUND
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        setActivityStatus(ActivityStatus.FOREGROUND);
        CustomDialogFragment customDialogFragment = this.mDialogFragment;
        if (customDialogFragment != null) {
            showDialog(customDialogFragment);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        setActivityStatus(ActivityStatus.BACKGROUND);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        setActivityStatus(ActivityStatus.BACKGROUND);
    }

    public CustomDialogFragment getDialog() {
        return this.dialog;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        setActivityStatus(ActivityStatus.BACKGROUND);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void startActivityForResult(Intent intent, int i, Bundle bundle) {
        if (i >= 0) {
            try {
                super.startActivityForResult(intent, i, bundle);
            } catch (Exception unused) {
            }
        } else {
            super.startActivityForResult(intent, i, bundle);
        }
    }

    public void setActivityStatus(ActivityStatus activityStatus) {
        this.mActivityStatus = activityStatus;
    }

    public ActivityStatus getActivityStatus() {
        return this.mActivityStatus;
    }

    public void showErrorDialog(CustomDialogFragment customDialogFragment) {
        CustomDialogFragment customDialogFragment2 = (CustomDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_NORMAL_DIALOG);
        if (customDialogFragment2 != null && customDialogFragment.getTargetTag().equals(TAG_FATAL_ERROR_DIALOG)) {
            customDialogFragment2.dismiss();
        }
        CustomDialogFragment customDialogFragment3 = (CustomDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_WARNING_DIALOG);
        if (customDialogFragment3 != null) {
            if (customDialogFragment.getTargetTag().equals(TAG_WARNING_DIALOG)) {
                return;
            } else {
                customDialogFragment3.dismiss();
            }
        }
        CustomDialogFragment customDialogFragment4 = (CustomDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_FATAL_ERROR_DIALOG);
        if (customDialogFragment4 != null) {
            if (!customDialogFragment.getTargetTag().equals(TAG_FATAL_ERROR_DIALOG)) {
                return;
            } else {
                customDialogFragment4.dismiss();
            }
        }
        showDialog(customDialogFragment);
    }

    public void showNormalDialog(CustomDialogFragment customDialogFragment) {
        CustomDialogFragment customDialogFragment2 = (CustomDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_NORMAL_DIALOG);
        if (customDialogFragment2 != null) {
            customDialogFragment2.dismiss();
        }
        showDialog(customDialogFragment);
    }

    public void showDialog(CustomDialogFragment customDialogFragment) {
        if (getActivityStatus() == ActivityStatus.FOREGROUND) {
            FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransactionBeginTransaction.add(customDialogFragment, customDialogFragment.getTargetTag());
            fragmentTransactionBeginTransaction.commitAllowingStateLoss();
            this.mDialogFragment = null;
            return;
        }
        this.mDialogFragment = customDialogFragment;
    }
}
