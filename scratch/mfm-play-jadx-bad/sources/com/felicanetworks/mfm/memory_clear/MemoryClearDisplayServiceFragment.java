package com.felicanetworks.mfm.memory_clear;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.model.info.AssetInfo;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import com.felicanetworks.mfm.main.model.info.specific.MyNanacoInfo;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.service.SupportServiceType;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.memory_clear.MemoryClearConstants;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes3.dex */
public class MemoryClearDisplayServiceFragment extends MemoryClearBaseFragment implements View.OnClickListener {
    private static final String ARGS_DISPLAY_SERVICE = "displayService";
    private static boolean haveBalance = false;
    private static TYPE1_CAUTION_PATTERN mType1CautionPattern = TYPE1_CAUTION_PATTERN.TYPE1_CAUTION_PATTERN_NO_DISPLAY;
    private int mOrientation = 1;
    private boolean mIsBtnStateChanged = true;

    enum TYPE1_CAUTION_PATTERN {
        TYPE1_CAUTION_PATTERN_NO_DISPLAY,
        TYPE1_CAUTION_PATTERN_TYPE_1,
        TYPE1_CAUTION_PATTERN_TYPE_2
    }

    private int getLayout(int orientation) {
        return orientation == 1 ? R.layout.fragment_memory_clear_display_service : R.layout.fragment_memory_clear_display_service_land;
    }

    public static MemoryClearDisplayServiceFragment newInstance(List<DisplayServiceInfo> displayServiceInfoList, Context context) {
        char c;
        MemoryClearDisplayServiceFragment memoryClearDisplayServiceFragment = new MemoryClearDisplayServiceFragment();
        char c2 = 0;
        haveBalance = false;
        String string = context.getString(R.string.text_memory_clear_display_service_currency);
        String string2 = context.getString(R.string.text_memory_clear_display_service_point);
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        boolean z2 = false;
        for (DisplayServiceInfo displayServiceInfo : displayServiceInfoList) {
            SupportServiceType supportServiceTypeResolve = SupportServiceType.resolve(displayServiceInfo.serviceId);
            if (supportServiceTypeResolve == SupportServiceType.M1 || supportServiceTypeResolve == SupportServiceType.M2) {
                if (displayServiceInfo.display) {
                    z = true;
                } else {
                    z2 = true;
                }
            }
            if (!displayServiceInfo.display) {
                c = c2;
            } else if (displayServiceInfo.asset == null) {
                Locale locale = Locale.US;
                Object[] objArr = new Object[1];
                objArr[c2] = displayServiceInfo.serviceName;
                sb.append(String.format(locale, "・%s%n", objArr));
                c = c2;
            } else {
                StringBuilder sb2 = new StringBuilder();
                int balanceValue = displayServiceInfo.asset.getBalanceValue();
                if (balanceValue >= 0) {
                    Locale locale2 = Locale.US;
                    String str = displayServiceInfo.serviceName;
                    Integer numValueOf = Integer.valueOf(balanceValue);
                    c = c2;
                    Object[] objArr2 = new Object[3];
                    objArr2[c] = str;
                    objArr2[1] = numValueOf;
                    objArr2[2] = string;
                    sb2.append(String.format(locale2, "・%s %,d%s", objArr2));
                    if (balanceValue >= 1) {
                        haveBalance = true;
                    }
                } else {
                    c = c2;
                    Locale locale3 = Locale.US;
                    Object[] objArr3 = new Object[1];
                    objArr3[c] = displayServiceInfo.serviceName;
                    sb2.append(String.format(locale3, "・%s", objArr3));
                }
                if (displayServiceInfo.serviceId.equals("SV000075")) {
                    ServiceInfo.Point.PointData[] pointDataArr = new ServiceInfo.Point.PointData[2];
                    pointDataArr[c] = new ServiceInfo.Point.PointData(displayServiceInfo.asset.getPoint1(), displayServiceInfo.asset.getDate1());
                    pointDataArr[1] = new ServiceInfo.Point.PointData(displayServiceInfo.asset.getPoint2(), displayServiceInfo.asset.getDate2());
                    int validPoint = MyNanacoInfo.getValidPoint(Arrays.asList(pointDataArr));
                    if (validPoint >= 0) {
                        Locale locale4 = Locale.US;
                        Object[] objArr4 = new Object[2];
                        objArr4[c] = Integer.valueOf(validPoint);
                        objArr4[1] = string2;
                        sb2.append(String.format(locale4, " %,d%s", objArr4));
                        if (validPoint >= 1) {
                            haveBalance = true;
                        }
                    }
                }
                Locale locale5 = Locale.US;
                Object[] objArr5 = new Object[1];
                objArr5[c] = sb2;
                sb.append(String.format(locale5, "%s\n", objArr5));
            }
            c2 = c;
        }
        if (!z && z2) {
            if (sb.length() == 0) {
                mType1CautionPattern = TYPE1_CAUTION_PATTERN.TYPE1_CAUTION_PATTERN_TYPE_2;
            } else {
                mType1CautionPattern = TYPE1_CAUTION_PATTERN.TYPE1_CAUTION_PATTERN_TYPE_1;
            }
        } else {
            mType1CautionPattern = TYPE1_CAUTION_PATTERN.TYPE1_CAUTION_PATTERN_NO_DISPLAY;
        }
        Bundle bundle = new Bundle();
        bundle.putString(ARGS_DISPLAY_SERVICE, sb.toString());
        memoryClearDisplayServiceFragment.setArguments(bundle);
        return memoryClearDisplayServiceFragment;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            this.mOrientation = activity.getResources().getConfiguration().orientation;
        }
        return inflater.inflate(getLayout(this.mOrientation), container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setViewItem(view);
    }

    private void setViewItem(View view) {
        final Button button = (Button) view.findViewById(R.id.memory_clear_display_service_next);
        button.setOnClickListener(this);
        ((Button) view.findViewById(R.id.memory_clear_display_service_cancel)).setOnClickListener(this);
        TextView textView = (TextView) view.findViewById(R.id.memory_clear_display_service_list);
        TextView textView2 = (TextView) view.findViewById(R.id.memory_clear_display_service_body_asset);
        TextView textView3 = (TextView) view.findViewById(R.id.memory_clear_display_service_type1_caution);
        if (mType1CautionPattern == TYPE1_CAUTION_PATTERN.TYPE1_CAUTION_PATTERN_NO_DISPLAY) {
            textView2.setVisibility(0);
            textView3.setVisibility(8);
        } else if (mType1CautionPattern == TYPE1_CAUTION_PATTERN.TYPE1_CAUTION_PATTERN_TYPE_1) {
            textView2.setVisibility(0);
            textView3.setText(R.string.text_memory_clear_display_service_type1_caution_1);
            textView3.setVisibility(0);
        } else if (mType1CautionPattern == TYPE1_CAUTION_PATTERN.TYPE1_CAUTION_PATTERN_TYPE_2) {
            textView2.setVisibility(8);
            textView3.setText(R.string.text_memory_clear_display_service_type1_caution_2);
            textView3.setVisibility(0);
        }
        ((CheckBox) view.findViewById(R.id.memory_clear_display_service_check_box)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearDisplayServiceFragment.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (MemoryClearDisplayServiceFragment.this.mIsBtnStateChanged) {
                    button.setEnabled(b);
                }
                MemoryClearDisplayServiceFragment.this.mIsBtnStateChanged = true;
            }
        });
        Bundle arguments = getArguments();
        if (arguments != null) {
            textView.setText(arguments.getString(ARGS_DISPLAY_SERVICE));
        }
        TextView textView4 = (TextView) view.findViewById(R.id.memory_clear_display_detail);
        textView4.setText(createSpannableString(getString(R.string.text_memory_clear_display_service_detail), getString(R.string.text_memory_clear_display_service_detail_link), (String) Sg.getValue(Sg.Key.MEMORY_CLEAR_DISPLAY_SERVICE_DETAIL_URL)));
        textView4.setMovementMethod(LinkMovementMethod.getInstance());
        TextView textView5 = (TextView) view.findViewById(R.id.text_memory_clear_display_service_check);
        textView5.setText(createSpannableString(getString(R.string.text_memory_clear_display_service_check), getString(R.string.text_memory_clear_display_service_check_link), (String) Sg.getValue(Sg.Key.MEMORY_CLEAR_DISPLAY_SERVICE_NOTES_URL)));
        textView5.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int i = newConfig.orientation;
        if (this.mOrientation != i) {
            this.mOrientation = i;
            View view = getView();
            if (view != null) {
                ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.root);
                boolean zIsChecked = ((CheckBox) viewGroup.findViewById(R.id.memory_clear_display_service_check_box)).isChecked();
                boolean zIsEnabled = ((Button) view.findViewById(R.id.memory_clear_display_service_next)).isEnabled();
                viewGroup.removeAllViews();
                View viewInflate = getLayoutInflater().inflate(getLayout(this.mOrientation), viewGroup);
                setViewItem(viewInflate);
                if (zIsChecked) {
                    CheckBox checkBox = (CheckBox) viewInflate.findViewById(R.id.memory_clear_display_service_check_box);
                    this.mIsBtnStateChanged = zIsEnabled;
                    checkBox.setChecked(true);
                }
            }
        }
    }

    private SpannableString createSpannableString(String message, String link, final String url) {
        int iStart;
        int iEnd;
        SpannableString spannableString = new SpannableString(message);
        Matcher matcher = Pattern.compile(link).matcher(message);
        if (matcher.find()) {
            iStart = matcher.start();
            iEnd = matcher.end();
        } else {
            iStart = 0;
            iEnd = 0;
        }
        spannableString.setSpan(new ClickableSpan() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearDisplayServiceFragment.2
            @Override // android.text.style.ClickableSpan
            public void onClick(View textView) {
                try {
                    MemoryClearDisplayServiceFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                } catch (Exception e) {
                    LogUtil.error(e);
                    MemoryClearDisplayServiceFragment.this.showBrowserStartupErrorDialog();
                }
            }
        }, iStart, iEnd, 18);
        return spannableString;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showBrowserStartupErrorDialog() {
        FragmentActivity activity = getActivity();
        if (activity instanceof MemoryClearActivity) {
            ((MemoryClearActivity) activity).sendMessageFailure(MemoryClearConstants.RESULT_CODE.FAILED_APP_START_FAILED);
        }
    }

    @Override // com.felicanetworks.mfm.memory_clear.MemoryClearBaseFragment, android.view.View.OnClickListener
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.memory_clear_display_service_next) {
            showMemoryClearConfirmationDialog();
        } else if (id == R.id.memory_clear_display_service_cancel) {
            cancelMemoryClear();
        }
        super.onClick(v);
    }

    private void showMemoryClearConfirmationDialog() {
        FragmentActivity activity = getActivity();
        if (activity instanceof MemoryClearActivity) {
            if (isDisplayAssets()) {
                ((MemoryClearActivity) activity).sendMessage(MemoryClearConstants.EVENT_ID.EXECUTE_CONFIRMATION);
            } else {
                ((MemoryClearActivity) activity).sendMessage(MemoryClearConstants.EVENT_ID.EXECUTE);
            }
        }
    }

    private void cancelMemoryClear() {
        FragmentActivity activity = getActivity();
        if (activity instanceof MemoryClearActivity) {
            ((MemoryClearActivity) activity).cancelMemoryClear(MemoryClearConstants.EVENT_ID.SHOW_DISPLAY_SERVICE);
        }
    }

    private boolean isDisplayAssets() {
        return haveBalance;
    }

    public static class DisplayServiceInfo {
        private final AssetInfo asset;
        private final boolean display;
        private final String serviceId;
        private final String serviceName;

        public DisplayServiceInfo(String serviceId, String serviceName, boolean display, AssetInfo asset) {
            this.serviceId = serviceId;
            this.serviceName = serviceName;
            this.display = display;
            this.asset = asset;
        }
    }
}
