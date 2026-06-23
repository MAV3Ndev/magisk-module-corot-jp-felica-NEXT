package com.felicanetworks.tis.ui;

import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import com.felicanetworks.tis.LogSender;
import com.felicanetworks.tis.R;
import com.felicanetworks.tis.ui.OrderImageWorker;
import com.felicanetworks.tis.util.LogMgr;
import com.google.android.material.imageview.ShapeableImageView;
import java.util.ArrayList;
import java.util.Locale;

/* JADX INFO: loaded from: classes3.dex */
public class CardDialogFragment extends DialogFragment {
    private static final int NOTIFICATION_ID = 100;
    private Dialog mDialog;

    public interface CardFaceImageListener {
        void onGetImage(Bitmap bitmap);
    }

    private class LaunchButtonOnClickListener implements View.OnClickListener {
        private Context mContext;
        private String mServiceId;
        private String mUuid;

        private LaunchButtonOnClickListener() {
        }

        void setLaunchLogParameters(Context context, String str, String str2) {
            this.mContext = context;
            this.mServiceId = str;
            this.mUuid = str2;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            LogMgr.log(6, "000 launch button clicked");
            new LogSender().sendAppLaunchLog(this.mContext, this.mServiceId, this.mUuid);
            CardDialogFragment.this.launchMenuApp();
            if (CardDialogFragment.this.mDialog != null) {
                CardDialogFragment.this.mDialog.dismiss();
            }
        }
    }

    private class CloseButtonOnClickListener implements View.OnClickListener {
        private CloseButtonOnClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            LogMgr.log(6, "000 close button clicked");
            if (CardDialogFragment.this.mDialog != null) {
                CardDialogFragment.this.mDialog.dismiss();
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Window window;
        LogMgr.log(4, "000");
        Dialog dialog = this.mDialog;
        if (dialog != null && (window = dialog.getWindow()) != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            if (getActivity() != null) {
                if (Build.VERSION.SDK_INT >= 30) {
                    Display display = getActivity().getDisplay();
                    if (display != null) {
                        display.getRealMetrics(displayMetrics);
                    }
                } else {
                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                }
            }
            attributes.width = (int) (displayMetrics.density * 316.0f);
            window.setAttributes(attributes);
        }
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        LogMgr.log(4, "000");
        if (this.mDialog == null) {
            setShowsDialog(false);
        }
        return this.mDialog;
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        NotificationManager notificationManager;
        LogMgr.log(4, "000");
        super.onDismiss(dialogInterface);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            KeyguardManager keyguardManager = (KeyguardManager) activity.getSystemService("keyguard");
            if (keyguardManager != null && keyguardManager.isKeyguardLocked() && (notificationManager = (NotificationManager) activity.getSystemService("notification")) != null) {
                LogMgr.log(6, "001 cancel notification");
                notificationManager.cancel(100);
            }
            if (activity.isFinishing()) {
                return;
            }
            activity.finish();
        }
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        LogMgr.log(4, "000");
        FragmentActivity activity = getActivity();
        if (activity == null || activity.isFinishing()) {
            return;
        }
        getActivity().finish();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStop() {
        LogMgr.log(4, "000");
        super.onStop();
    }

    void setupDialog(Context context, int i, String str, int i2, int i3, String str2, String str3, int i4, int i5, boolean z) {
        LogMgr.log(5, "000");
        if (this.mDialog == null) {
            Dialog dialog = new Dialog(context);
            this.mDialog = dialog;
            Window window = dialog.getWindow();
            if (window != null) {
                window.addFlags(256);
                window.setBackgroundDrawable(new ColorDrawable(0));
            }
            this.mDialog.setCanceledOnTouchOutside(true);
        } else if (getShowsDialog()) {
            this.mDialog = getDialog();
        }
        setupDialogView(context, i, str, i2, i3, str2, str3, i4, i5, z);
        LogMgr.log(5, "999");
    }

    void setupDialogView(Context context, int i, String str, int i2, int i3, String str2, String str3, int i4, final int i5, boolean z) {
        LogMgr.log(5, "000");
        if (this.mDialog != null) {
            LogMgr.log(6, "001");
            this.mDialog.setContentView(R.layout.tis_card_fragment);
            final View viewInflate = this.mDialog.getLayoutInflater().inflate(R.layout.tis_card_fragment, (ViewGroup) null);
            if (viewInflate != null) {
                LogMgr.log(6, "002");
                TextView textView = (TextView) viewInflate.findViewById(R.id.tis_title);
                if (i4 > 0) {
                    textView.setText(context.getResources().getString(i4));
                }
                getCardFaceImage(context, str3, new CardFaceImageListener() { // from class: com.felicanetworks.tis.ui.CardDialogFragment.1
                    @Override // com.felicanetworks.tis.ui.CardDialogFragment.CardFaceImageListener
                    public void onGetImage(final Bitmap bitmap) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.felicanetworks.tis.ui.CardDialogFragment.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                try {
                                    CardDialogFragment.this.updateCardImageView(viewInflate, i5, bitmap);
                                } catch (Exception unused) {
                                    CardDialogFragment.this.updateCardImageView(viewInflate, i5, null);
                                }
                            }
                        });
                    }
                });
                setupPatternView(context, viewInflate, i, i2, i3, z);
                LaunchButtonOnClickListener launchButtonOnClickListener = new LaunchButtonOnClickListener();
                launchButtonOnClickListener.setLaunchLogParameters(context, str, str2);
                viewInflate.findViewById(R.id.tis_launch_link).setOnClickListener(launchButtonOnClickListener);
                viewInflate.findViewById(R.id.tis_close_link).setOnClickListener(new CloseButtonOnClickListener());
                this.mDialog.setContentView(viewInflate);
            }
        }
        LogMgr.log(5, "999");
    }

    void updateCardImageView(View view, int i, Bitmap bitmap) {
        ImageView imageView = (ImageView) view.findViewById(R.id.tis_card);
        ShapeableImageView shapeableImageView = (ShapeableImageView) view.findViewById(R.id.tis_round_card);
        if (bitmap != null) {
            imageView.setVisibility(8);
            shapeableImageView.setVisibility(0);
            shapeableImageView.setImageBitmap(bitmap);
        } else {
            imageView.setVisibility(0);
            shapeableImageView.setVisibility(8);
            if (i > 0) {
                imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), i, null));
            }
        }
    }

    public void getCardFaceImage(Context context, String str, final CardFaceImageListener cardFaceImageListener) {
        ArrayList arrayList = new ArrayList();
        if (str != null && !str.isEmpty()) {
            arrayList.add("SPECIAL" + str);
        }
        new Thread(new OrderImageWorker(context, new OrderImageWorker.Request(arrayList), new OrderImageWorker.Listener() { // from class: com.felicanetworks.tis.ui.CardDialogFragment.2
            @Override // com.felicanetworks.tis.ui.OrderImageWorker.Listener
            public void onCompleted() {
            }

            @Override // com.felicanetworks.tis.ui.OrderImageWorker.Listener
            public void onGetImage(Bitmap bitmap) {
                cardFaceImageListener.onGetImage(bitmap);
            }
        })).start();
    }

    private void setupPatternView(Context context, View view, int i, int i2, int i3, boolean z) {
        switch (i) {
            case 1:
            case 7:
            case 10:
                if (z) {
                    setPayInfoMinusView(context, view, i2);
                } else {
                    setPayInfoView(view, i2);
                }
                setBalanceInfoCommonView(view, i3);
                break;
            case 2:
            case 8:
            case 11:
                if (z) {
                    setChargeInfoPlusView(context, view, i2);
                } else {
                    setChargeInfoView(view, i2);
                }
                setBalanceInfoCommonView(view, i3);
                break;
            case 3:
                setTapInfoView(view);
                break;
            case 4:
                setPayInfoView(view, i2);
                break;
            case 5:
            case 6:
            case 9:
                setNoPayInfoView(view);
                setBalanceInfoNoPayView(view, i3);
                break;
            case 12:
                setSeparatorView(view);
                setPayInfoView(view, i2);
                setBalanceInfoCommonView(view, i3);
                break;
        }
    }

    private void setSeparatorView(View view) {
        view.findViewById(R.id.tis_separator).setVisibility(0);
    }

    private void setTapInfoView(View view) {
        view.findViewById(R.id.tis_tap_info).setVisibility(0);
    }

    private void setPayInfoView(View view, int i) {
        View viewFindViewById = view.findViewById(R.id.tis_pay_info);
        viewFindViewById.setVisibility(0);
        ((TextView) viewFindViewById.findViewById(R.id.amount_pay)).setText(getCommaSeparatedString(i));
    }

    private void setPayInfoMinusView(Context context, View view, int i) {
        View viewFindViewById = view.findViewById(R.id.tis_pay_info_sign);
        viewFindViewById.setVisibility(0);
        ((TextView) viewFindViewById.findViewById(R.id.amount_pay_sign)).setText(String.format(context.getResources().getString(R.string.tis_common_word_minus), getCommaSeparatedString(i)));
    }

    private void setChargeInfoView(View view, int i) {
        View viewFindViewById = view.findViewById(R.id.tis_charge_info);
        viewFindViewById.setVisibility(0);
        ((TextView) viewFindViewById.findViewById(R.id.amount_charge)).setText(getCommaSeparatedString(i));
    }

    private void setChargeInfoPlusView(Context context, View view, int i) {
        View viewFindViewById = view.findViewById(R.id.tis_pay_info_sign);
        viewFindViewById.setVisibility(0);
        ((TextView) viewFindViewById.findViewById(R.id.amount_pay_sign)).setText(String.format(context.getResources().getString(R.string.tis_common_word_plus), getCommaSeparatedString(i)));
    }

    private void setNoPayInfoView(View view) {
        view.findViewById(R.id.tis_no_pay_info).setVisibility(0);
    }

    private void setBalanceInfoCommonView(View view, int i) {
        View viewFindViewById = view.findViewById(R.id.tis_balance_info_common);
        viewFindViewById.setVisibility(0);
        ((TextView) viewFindViewById.findViewById(R.id.amount_balance_common)).setText(getCommaSeparatedString(i));
    }

    private void setBalanceInfoNoPayView(View view, int i) {
        View viewFindViewById = view.findViewById(R.id.tis_balance_info_no_pay);
        viewFindViewById.setVisibility(0);
        ((TextView) viewFindViewById.findViewById(R.id.amount_balance_no_pay)).setText(getCommaSeparatedString(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchMenuApp() {
        try {
            startActivity(new Intent().setComponent(new ComponentName("com.felicanetworks.mfm.main", "com.felicanetworks.mfm.main.ServiceListActivity")).putExtra("com.felicanetworks.mfm.main.tapinteraction", true).addFlags(268435456).addFlags(32768));
        } catch (Exception unused) {
            LogMgr.log(2, "700 failed to launch");
        }
    }

    private String getCommaSeparatedString(int i) {
        return String.format(Locale.JAPAN, "%,d", Integer.valueOf(i));
    }
}
