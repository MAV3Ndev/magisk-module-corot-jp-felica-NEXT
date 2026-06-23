package com.felicanetworks.mfm.main.view.views;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.presenter.agent.LinkageAgent;
import com.felicanetworks.mfm.main.presenter.agent.RecommendInfoAgent;
import com.felicanetworks.mfm.main.presenter.structure.RecommendDetailDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class RecommendDetailFragment extends BaseFragment {
    private static final String RECOMMEND_CATEGORY_ID_1 = "01";
    private static final String RECOMMEND_CATEGORY_ID_2 = "02";
    private static final String RECOMMEND_CATEGORY_ID_3 = "03";
    private static final String RECOMMEND_CATEGORY_ID_4 = "04";
    private static final String RECOMMEND_CATEGORY_ID_5 = "05";
    private static final String RECOMMEND_CATEGORY_ID_6 = "06";

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        boolean z = false;
        View viewInflate = layoutInflater.inflate(R.layout.fragment_recommend_detail, viewGroup, false);
        try {
            Structure structure = getStructure();
            if (structure != null && (structure instanceof RecommendDetailDrawStructure)) {
                final RecommendDetailDrawStructure recommendDetailDrawStructure = (RecommendDetailDrawStructure) structure;
                int i = 1;
                stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_03_01, recommendDetailDrawStructure);
                RecommendInfoAgent recommendInfo = recommendDetailDrawStructure.getRecommendInfo();
                ScrollView scrollView = (ScrollView) viewInflate.findViewById(R.id.sv_recommend_detail);
                scrollView.setBackgroundColor(getCategoryColor(recommendInfo));
                LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.ll_service_background);
                float f = getContext().getResources().getDisplayMetrics().density * 10.0f;
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setColor(getCategorySubColor(recommendInfo));
                int i2 = 2;
                int i3 = 3;
                char c = 4;
                gradientDrawable.setCornerRadii(new float[]{f, f, f, f, 0.0f, 0.0f, 0.0f, 0.0f});
                linearLayout.setBackground(gradientDrawable);
                ((TextView) viewInflate.findViewById(R.id.tv_use_procedure)).setTextColor(getCategoryColor(recommendInfo));
                ((TextView) viewInflate.findViewById(R.id.tv_service_title)).setText(recommendInfo.getName());
                ((ImageView) viewInflate.findViewById(R.id.iv_service_icon)).setImageBitmap(recommendInfo.getIcon());
                ((TextView) viewInflate.findViewById(R.id.tv_service_detail)).setText(recommendInfo.getDetails());
                ((TextView) viewInflate.findViewById(R.id.tv_service_provider)).setText(getString(R.string.label_recommend_detail_provider, recommendInfo.getProvider()));
                List<String> procedures = recommendInfo.getProcedures();
                LinearLayout linearLayout2 = (LinearLayout) viewInflate.findViewById(R.id.ll_use_procedure);
                int i4 = 1;
                for (String str : procedures) {
                    char c2 = c;
                    LinearLayout linearLayout3 = (LinearLayout) layoutInflater.inflate(R.layout.list_item_recommend_detail, linearLayout2, z);
                    boolean z2 = z;
                    TextView textView = (TextView) linearLayout3.findViewById(R.id.tv_procedure_number);
                    int i5 = i3;
                    textView.setText(String.valueOf(i4));
                    float f2 = getContext().getResources().getDisplayMetrics().density * 24.0f;
                    int i6 = i2;
                    GradientDrawable gradientDrawable2 = new GradientDrawable();
                    int i7 = i;
                    gradientDrawable2.setColor(getCategoryColor(recommendInfo));
                    float[] fArr = new float[8];
                    fArr[z2 ? 1 : 0] = f2;
                    fArr[i7] = f2;
                    fArr[i6] = f2;
                    fArr[i5] = f2;
                    fArr[c2] = f2;
                    fArr[5] = f2;
                    fArr[6] = f2;
                    fArr[7] = f2;
                    gradientDrawable2.setCornerRadii(fArr);
                    textView.setBackground(gradientDrawable2);
                    ((TextView) linearLayout3.findViewById(R.id.tv_procedure_detail)).setText(str);
                    linearLayout2.addView(linearLayout3);
                    i4++;
                    c = c2;
                    z = z2 ? 1 : 0;
                    i3 = i5;
                    i2 = i6;
                    i = i7;
                }
                int i8 = i;
                int i9 = i2;
                int i10 = i3;
                final LinkageAgent linkage = recommendInfo.getLinkage();
                Button button = (Button) viewInflate.findViewById(R.id.b_select_recommend_button);
                int i11 = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$LinkageAgent$LaunchTarget[linkage.getLaunchTarget().ordinal()];
                if (i11 == i8) {
                    button.setText(getText(R.string.button_text_recommend_detail_app_start));
                } else if (i11 == i9) {
                    button.setText(getText(R.string.button_text_recommend_detail_site_access));
                } else if (i11 == i10) {
                    button.setText(getText(R.string.button_text_recommend_detail_app_download));
                }
                button.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.RecommendDetailFragment.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        AnalysisManager.stamp(MfmStamp.Event.ACTION_START_RECOMMEND_SERVICE, recommendDetailDrawStructure);
                        try {
                            LinkageAgent.LinkageInfo linkageInfo = linkage.getLinkageInfo();
                            if (linkageInfo.getError() == LinkageAgent.ErrorType.NOT_FOUND_ACTIVITY) {
                                RecommendDetailFragment.this.showWarningDialog(R.string.dlg_warning_not_found_activity);
                                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_not_found_activity");
                                return;
                            }
                            if (linkageInfo.getError() == LinkageAgent.ErrorType.NOT_FOUND_PACKAGE) {
                                RecommendDetailFragment.this.showWarningDialog(R.string.dlg_warning_signature_unmatched);
                                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_signature_unmatched");
                                return;
                            }
                            Intent intent = linkageInfo.getIntent();
                            if (intent == null) {
                                RecommendDetailFragment.this.showWarningDialog(R.string.dlg_warning_signature_unmatched);
                                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_signature_unmatched");
                                return;
                            }
                            Structure structure2 = RecommendDetailFragment.this.getStructure();
                            if (structure2 == null || !(structure2 instanceof RecommendDetailDrawStructure)) {
                                return;
                            }
                            RecommendDetailFragment.this.startActivity(intent);
                            ((RecommendDetailDrawStructure) structure2).actServiceStartSuccess();
                        } catch (Exception unused) {
                            RecommendDetailFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                        }
                    }
                });
                adjustViewToNavigationBar(scrollView, true);
                return viewInflate;
            }
        } catch (Exception e) {
            notifyException(e);
        }
        return viewInflate;
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.views.RecommendDetailFragment$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$LinkageAgent$LaunchTarget;

        static {
            int[] iArr = new int[LinkageAgent.LaunchTarget.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$LinkageAgent$LaunchTarget = iArr;
            try {
                iArr[LinkageAgent.LaunchTarget.APPLICATION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$LinkageAgent$LaunchTarget[LinkageAgent.LaunchTarget.BROWSER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$LinkageAgent$LaunchTarget[LinkageAgent.LaunchTarget.PLAY_STORE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        View view = getView();
        if (view != null) {
            adjustViewToNavigationBar(view.findViewById(R.id.sv_recommend_detail), true);
        }
    }

    private int getCategoryColor(RecommendInfoAgent recommendInfoAgent) {
        int i;
        String categoryId = recommendInfoAgent.getCategoryId();
        categoryId.hashCode();
        switch (categoryId) {
            case "01":
                i = R.color.color_category_red;
                break;
            case "02":
                i = R.color.color_category_purple;
                break;
            case "03":
                i = R.color.color_category_blue;
                break;
            case "04":
                i = R.color.color_category_green;
                break;
            case "05":
                i = R.color.color_category_yellow_green;
                break;
            case "06":
                i = R.color.color_category_orange;
                break;
            default:
                i = R.color.color_category_light_gray;
                break;
        }
        return ContextCompat.getColor(getContext(), i);
    }

    private int getCategorySubColor(RecommendInfoAgent recommendInfoAgent) {
        int i;
        String categoryId = recommendInfoAgent.getCategoryId();
        categoryId.hashCode();
        switch (categoryId) {
            case "01":
                i = R.color.color_category_red_sub;
                break;
            case "02":
                i = R.color.color_category_purple_sub;
                break;
            case "03":
                i = R.color.color_category_blue_sub;
                break;
            case "04":
                i = R.color.color_category_green_sub;
                break;
            case "05":
                i = R.color.color_category_yellow_green_sub;
                break;
            case "06":
                i = R.color.color_category_orange_sub;
                break;
            default:
                i = R.color.color_category_light_gray_sub;
                break;
        }
        return ContextCompat.getColor(getContext(), i);
    }
}
