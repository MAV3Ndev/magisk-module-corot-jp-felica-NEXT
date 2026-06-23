package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.felicanetworks.mfm.main.R;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes3.dex */
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
        MfiDialogFragment mfiDialogFragment;
        if (Thread.currentThread().equals(context.getMainLooper().getThread())) {
            mfiDialogFragment = new MfiDialogFragment();
        } else {
            final AsyncPacket asyncPacket = new AsyncPacket();
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.felicanetworks.mfm.main.view.views.DialogFactory.1
                @Override // java.lang.Runnable
                public void run() {
                    asyncPacket.set(new MfiDialogFragment());
                    countDownLatch.countDown();
                }
            });
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mfiDialogFragment = (MfiDialogFragment) asyncPacket.get();
            if (mfiDialogFragment == null) {
                throw new IllegalStateException();
            }
        }
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

    public static CustomDialogFragment createCircleProgressDialog(Context context, String text) {
        CustomDialogFragment customDialogFragmentCreateCircleProgressDialog = createCircleProgressDialog(context);
        customDialogFragmentCreateCircleProgressDialog.setText(text);
        return customDialogFragmentCreateCircleProgressDialog;
    }

    private static class AsyncPacket<T> {
        private T packet;

        private AsyncPacket() {
            this.packet = null;
        }

        void set(T packet) {
            this.packet = packet;
        }

        T get() {
            return this.packet;
        }
    }
}
