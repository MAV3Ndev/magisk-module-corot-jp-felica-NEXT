package com.felicanetworks.mfm.main.view.views;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaParams;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.presenter.agent.ExtIcCardFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.ExtIcCardInfoAgent;
import com.felicanetworks.mfm.main.presenter.structure.ExtIcCardDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.PrimaryDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.view.views.CommonProps;
import java.text.NumberFormat;

/* JADX INFO: loaded from: classes.dex */
public class ExtIcCardReadResultFragment extends BaseFragment implements ExtIcCardFuncAgent.OrderPromotionImageListener {
    private static final int BALANCE_ICON_HEIGHT = 46;
    private static final int BALANCE_ICON_WIDTH = 40;
    private ExtIcCardInfoAgent mCardInfoAgent;
    private ImageView promotionImageView = null;
    private FrameLayout promotionButtonBackground = null;
    private Button promotionButton = null;
    private boolean isForeground = false;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_06_02, this.mCardInfoAgent);
        View viewInflate = layoutInflater.inflate(R.layout.fragment_card_read_result, viewGroup, false);
        try {
            ((TextView) viewInflate.findViewById(R.id.tv_service_title)).setText(this.mCardInfoAgent.getName());
            ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_service_icon);
            if (this.mCardInfoAgent.getId().equals(FeliCaParams.SERVICE_ID_SUICA)) {
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                layoutParams.width = (int) (displayMetrics.density * 40.0f);
                layoutParams.height = (int) (displayMetrics.density * 46.0f);
                imageView.setLayoutParams(layoutParams);
                imageView.setImageResource(R.drawable.ic_cardreader_balance);
            } else {
                imageView.setImageBitmap(this.mCardInfoAgent.getIcon());
            }
            Typeface typeface = CommonProps.getTypeface(getContext(), CommonProps.FontType.BOLD);
            Typeface typeface2 = CommonProps.getTypeface(getContext(), CommonProps.FontType.REGULAR);
            if (this.mCardInfoAgent.hasPrepaidEmoney()) {
                int balance = this.mCardInfoAgent.getPrepaidEmoney().getBalance();
                TextView textView = (TextView) viewInflate.findViewById(R.id.tv_balance_value);
                textView.setTypeface(typeface);
                ((TextView) viewInflate.findViewById(R.id.tv_balance_currency)).setTypeface(typeface2);
                if (balance >= 0 && balance <= 99999) {
                    textView.setText(NumberFormat.getNumberInstance().format(balance));
                } else {
                    textView.setText(R.string.warning_value_nothing);
                }
            } else {
                viewInflate.findViewById(R.id.ll_balance_item).setVisibility(8);
            }
            if (this.mCardInfoAgent.hasPoint()) {
                int point = this.mCardInfoAgent.getPoint().getPointDataList().get(0).getPoint();
                TextView textView2 = (TextView) viewInflate.findViewById(R.id.tv_point_value);
                textView2.setTypeface(typeface2);
                ((TextView) viewInflate.findViewById(R.id.tv_point_currency)).setTypeface(typeface2);
                if (point >= 0 && point <= 99999999) {
                    textView2.setText(NumberFormat.getNumberInstance().format(point));
                } else {
                    textView2.setText(R.string.warning_value_nothing);
                }
            } else {
                viewInflate.findViewById(R.id.ll_point_item).setVisibility(8);
            }
            ImageView imageView2 = (ImageView) viewInflate.findViewById(R.id.iv_guide_image);
            this.promotionImageView = imageView2;
            imageView2.setVisibility(8);
            this.promotionImageView.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.ExtIcCardReadResultFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_JUMP_OSAIFU_VIA_IMAGE_AREA, ExtIcCardReadResultFragment.this.mCardInfoAgent);
                    try {
                        ExtIcCardReadResultFragment.this.showOsaifulifePlusDownloadSite();
                    } catch (Exception e) {
                        ExtIcCardReadResultFragment.this.notifyException(e);
                    }
                }
            });
            if (this.mCardInfoAgent.hasFelicaPocket()) {
                viewInflate.findViewById(R.id.b_select_fp_button).setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.ExtIcCardReadResultFragment.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        AnalysisManager.stamp(MfmStamp.Event.ACTION_SHOW_FP, new Object[0]);
                        try {
                            Structure structure = ExtIcCardReadResultFragment.this.getStructure();
                            if (structure instanceof ExtIcCardDrawStructure) {
                                ((ExtIcCardDrawStructure) structure).actFpServiceList();
                            }
                        } catch (Exception e) {
                            ExtIcCardReadResultFragment.this.notifyException(e);
                        }
                    }
                });
            } else {
                viewInflate.findViewById(R.id.ll_fp_item).setVisibility(8);
            }
            FrameLayout frameLayout = (FrameLayout) viewInflate.findViewById(R.id.fl_button_background);
            this.promotionButtonBackground = frameLayout;
            frameLayout.setVisibility(8);
            Button button = (Button) viewInflate.findViewById(R.id.b_select_recommend_button);
            this.promotionButton = button;
            button.setVisibility(8);
            this.promotionButton.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.ExtIcCardReadResultFragment.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_JUMP_OSAIFU_VIA_IMAGE_BUTTON, ExtIcCardReadResultFragment.this.mCardInfoAgent);
                    try {
                        ExtIcCardReadResultFragment.this.showOsaifulifePlusDownloadSite();
                    } catch (Exception e) {
                        ExtIcCardReadResultFragment.this.notifyException(e);
                    }
                }
            });
            this.isForeground = true;
            String str = null;
            String id = this.mCardInfoAgent.getId();
            if (FeliCaParams.SERVICE_ID_SUICA.equals(id)) {
                str = (String) Sg.getValue(Sg.Key.NET_OSAIFULIFE_PLUS_IMAGE_TRAFFIC_URL);
            } else if ("SV000013".equals(id)) {
                str = (String) Sg.getValue(Sg.Key.NET_OSAIFULIFE_PLUS_IMAGE_EDY_URL);
            } else if ("SV000075".equals(id)) {
                str = (String) Sg.getValue(Sg.Key.NET_OSAIFULIFE_PLUS_IMAGE_NANACO_URL);
            } else if ("SV000011".equals(id)) {
                str = (String) Sg.getValue(Sg.Key.NET_OSAIFULIFE_PLUS_IMAGE_WAON_URL);
            }
            if (str != null) {
                Structure structure = getStructure();
                if (structure instanceof ExtIcCardDrawStructure) {
                    ((ExtIcCardDrawStructure) structure).getExIcCardFunc().orderPromotionImage(str, this);
                }
            }
        } catch (Exception e) {
            notifyException(e);
        }
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.isForeground = false;
        Structure structure = getStructure();
        if (structure == null || !(structure instanceof ExtIcCardDrawStructure)) {
            return;
        }
        ((ExtIcCardDrawStructure) structure).getExIcCardFunc().cancelPromotionImage();
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.ExtIcCardFuncAgent.OrderPromotionImageListener
    public void onCompletePromotionImage(Bitmap bitmap) {
        ImageView imageView;
        if (bitmap == null || !this.isForeground || (imageView = this.promotionImageView) == null || this.promotionButtonBackground == null || this.promotionButton == null) {
            return;
        }
        imageView.setImageBitmap(bitmap);
        this.promotionImageView.setVisibility(0);
        this.promotionButtonBackground.setVisibility(0);
        this.promotionButton.setVisibility(0);
    }

    public void setCardInfoAgent(ExtIcCardInfoAgent extIcCardInfoAgent) {
        this.mCardInfoAgent = extIcCardInfoAgent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showOsaifulifePlusDownloadSite() {
        PrimaryDrawStructure.LinkType linkType = PrimaryDrawStructure.LinkType.LINK_TYPE_MAX;
        String id = this.mCardInfoAgent.getId();
        if (FeliCaParams.SERVICE_ID_SUICA.equals(id)) {
            linkType = PrimaryDrawStructure.LinkType.OSAIFULIFE_PLUS_DL_TRAFFIC_URL;
        } else if ("SV000013".equals(id)) {
            linkType = PrimaryDrawStructure.LinkType.OSAIFULIFE_PLUS_DL_EDY_URL;
        } else if ("SV000075".equals(id)) {
            linkType = PrimaryDrawStructure.LinkType.OSAIFULIFE_PLUS_DL_NANACO_URL;
        } else if ("SV000011".equals(id)) {
            linkType = PrimaryDrawStructure.LinkType.OSAIFULIFE_PLUS_DL_WAON_URL;
        }
        Structure structure = getStructure();
        if (structure instanceof ExtIcCardDrawStructure) {
            try {
                startActivity(((ExtIcCardDrawStructure) structure).getDefaultIntent(linkType));
            } catch (Exception unused) {
                showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
            }
        }
    }
}
