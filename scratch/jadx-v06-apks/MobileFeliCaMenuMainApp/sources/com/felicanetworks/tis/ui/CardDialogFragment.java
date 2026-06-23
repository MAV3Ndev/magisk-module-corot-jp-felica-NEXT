package com.felicanetworks.tis.ui;

import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import com.felicanetworks.tis.LogSender;
import com.felicanetworks.tis.R;
import com.felicanetworks.tis.util.LogMgr;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class CardDialogFragment extends DialogFragment {
    private static final int NOTIFICATION_ID = 100;
    private static final String TIS_CARD_PREFIX = "tis_";
    private static final String TIS_CARD_SUFFIX = "_card";
    private static final String TIS_TITLE_HEAD = "tis_title_";
    private Dialog mDialog;

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

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        Window window;
        LogMgr.log(4, "000");
        super.onActivityCreated(bundle);
        Dialog dialog = this.mDialog;
        if (dialog == null || (window = dialog.getWindow()) == null) {
            return;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (getActivity() != null) {
            if (Build.VERSION.SDK_INT >= 30) {
                getActivity().getDisplay().getRealMetrics(displayMetrics);
            } else {
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            }
        }
        attributes.width = (int) (displayMetrics.scaledDensity * 316.0f);
        window.setAttributes(attributes);
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

    void setupDialog(Context context, int i, String str, int i2, int i3, String str2) {
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
        setupDialogView(context, i, str, i2, i3, str2);
        LogMgr.log(5, "999");
    }

    void setupDialogView(Context context, int i, String str, int i2, int i3, String str2) {
        LogMgr.log(5, "000");
        if (this.mDialog != null) {
            LogMgr.log(6, "001");
            this.mDialog.setContentView(R.layout.tis_card_fragment);
            View viewInflate = this.mDialog.getLayoutInflater().inflate(R.layout.tis_card_fragment, (ViewGroup) null);
            if (viewInflate != null) {
                LogMgr.log(6, "002");
                TextView textView = (TextView) viewInflate.findViewById(R.id.tis_title);
                int identifier = context.getResources().getIdentifier(TIS_TITLE_HEAD + str, TypedValues.Custom.S_STRING, context.getPackageName());
                if (identifier > 0) {
                    textView.setText(context.getResources().getString(identifier));
                }
                ImageView imageView = (ImageView) viewInflate.findViewById(R.id.tis_card);
                int identifier2 = context.getResources().getIdentifier(TIS_CARD_PREFIX + str + TIS_CARD_SUFFIX, "drawable", context.getPackageName());
                if (identifier2 > 0) {
                    imageView.setImageDrawable(context.getResources().getDrawable(identifier2, null));
                }
                setupPatternView(viewInflate, i, i2, i3);
                LaunchButtonOnClickListener launchButtonOnClickListener = new LaunchButtonOnClickListener();
                launchButtonOnClickListener.setLaunchLogParameters(context, str, str2);
                viewInflate.findViewById(R.id.tis_launch_link).setOnClickListener(launchButtonOnClickListener);
                viewInflate.findViewById(R.id.tis_close_link).setOnClickListener(new CloseButtonOnClickListener());
                this.mDialog.setContentView(viewInflate);
            }
        }
        LogMgr.log(5, "999");
    }

    private void setupPatternView(View view, int i, int i2, int i3) {
        switch (i) {
            case 1:
                setPayInfoView(view, i2);
                setBalanceInfoCommonView(view, i3);
                break;
            case 2:
                setChargeInfoView(view, i2);
                setBalanceInfoCommonView(view, i3);
                break;
            case 3:
                setTapInfoView(view);
                break;
            case 4:
                setPayInfoView(view, i2);
                break;
            case 5:
                setGateInfoInView(view);
                setSeparatorView(view);
                setBalanceInfoOnlyView(view, i3);
                break;
            case 6:
                setGateInfoInView(view);
                setSeparatorView(view);
                setNoPayInfoView(view);
                setBalanceInfoNoPayView(view, i3);
                break;
            case 7:
                setGateInfoInView(view);
                setSeparatorView(view);
                setPayInfoView(view, i2);
                setBalanceInfoCommonView(view, i3);
                break;
            case 8:
                setGateInfoInView(view);
                setSeparatorView(view);
                setChargeInfoView(view, i2);
                setBalanceInfoCommonView(view, i3);
                break;
            case 9:
                setGateInfoOutView(view);
                setSeparatorView(view);
                setNoPayInfoView(view);
                setBalanceInfoNoPayView(view, i3);
                break;
            case 10:
                setGateInfoOutView(view);
                setSeparatorView(view);
                setPayInfoView(view, i2);
                setBalanceInfoCommonView(view, i3);
                break;
            case 11:
                setGateInfoOutView(view);
                setSeparatorView(view);
                setChargeInfoView(view, i2);
                setBalanceInfoCommonView(view, i3);
                break;
            case 12:
                setGateInfoEmptyView(view);
                setSeparatorView(view);
                setPayInfoView(view, i2);
                setBalanceInfoCommonView(view, i3);
                break;
        }
    }

    private void setGateInfoInView(View view) {
        ((TextView) view.findViewById(R.id.tis_gate_info_in)).setVisibility(0);
    }

    private void setGateInfoOutView(View view) {
        ((TextView) view.findViewById(R.id.tis_gate_info_out)).setVisibility(0);
    }

    private void setGateInfoEmptyView(View view) {
        TextView textView = (TextView) view.findViewById(R.id.tis_gate_info_in);
        textView.setText("");
        textView.setVisibility(0);
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

    private void setChargeInfoView(View view, int i) {
        View viewFindViewById = view.findViewById(R.id.tis_charge_info);
        viewFindViewById.setVisibility(0);
        ((TextView) viewFindViewById.findViewById(R.id.amount_charge)).setText(getCommaSeparatedString(i));
    }

    private void setNoPayInfoView(View view) {
        view.findViewById(R.id.tis_no_pay_info).setVisibility(0);
    }

    private void setBalanceInfoCommonView(View view, int i) {
        View viewFindViewById = view.findViewById(R.id.tis_balance_info_common);
        viewFindViewById.setVisibility(0);
        ((TextView) viewFindViewById.findViewById(R.id.amount_balance_common)).setText(getCommaSeparatedString(i));
    }

    private void setBalanceInfoOnlyView(View view, int i) {
        View viewFindViewById = view.findViewById(R.id.tis_balance_info_only);
        viewFindViewById.setVisibility(0);
        ((TextView) viewFindViewById.findViewById(R.id.amount_balance_only)).setText(getCommaSeparatedString(i));
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
