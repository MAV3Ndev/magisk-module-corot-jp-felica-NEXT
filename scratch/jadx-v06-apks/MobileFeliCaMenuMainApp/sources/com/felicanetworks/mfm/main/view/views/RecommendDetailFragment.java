package com.felicanetworks.mfm.main.view.views;

import android.content.Intent;
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
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.presenter.agent.LinkageAgent;
import com.felicanetworks.mfm.main.presenter.agent.RecommendInfoAgent;
import com.felicanetworks.mfm.main.presenter.structure.RecommendDetailDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RecommendDetailFragment extends BaseFragment {
    private static final String RECOMMEND_CATEGORY_ID_1 = "01";
    private static final String RECOMMEND_CATEGORY_ID_2 = "02";
    private static final String RECOMMEND_CATEGORY_ID_3 = "03";
    private static final String RECOMMEND_CATEGORY_ID_4 = "04";
    private static final String RECOMMEND_CATEGORY_ID_5 = "05";
    private static final String RECOMMEND_CATEGORY_ID_6 = "06";

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Structure structure;
        View viewInflate = layoutInflater.inflate(R.layout.fragment_recommend_detail, viewGroup, false);
        try {
            structure = getStructure();
        } catch (Exception e) {
            notifyException(e);
        }
        if (structure != null && (structure instanceof RecommendDetailDrawStructure)) {
            final RecommendDetailDrawStructure recommendDetailDrawStructure = (RecommendDetailDrawStructure) structure;
            stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_03_01, recommendDetailDrawStructure);
            RecommendInfoAgent recommendInfo = recommendDetailDrawStructure.getRecommendInfo();
            ((ScrollView) viewInflate.findViewById(R.id.sv_recommend_detail)).setBackgroundColor(getCategoryColor(recommendInfo));
            LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.ll_service_background);
            float f = getContext().getResources().getDisplayMetrics().density * 10.0f;
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(getCategorySubColor(recommendInfo));
            gradientDrawable.setCornerRadii(new float[]{f, f, f, f, 0.0f, 0.0f, 0.0f, 0.0f});
            linearLayout.setBackground(gradientDrawable);
            ((TextView) viewInflate.findViewById(R.id.tv_use_procedure)).setTextColor(getCategoryColor(recommendInfo));
            ((TextView) viewInflate.findViewById(R.id.tv_service_title)).setText(recommendInfo.getName());
            ((ImageView) viewInflate.findViewById(R.id.iv_service_icon)).setImageBitmap(recommendInfo.getIcon());
            ((TextView) viewInflate.findViewById(R.id.tv_service_detail)).setText(recommendInfo.getDetails());
            ((TextView) viewInflate.findViewById(R.id.tv_service_provider)).setText(getString(R.string.label_recommend_detail_provider, recommendInfo.getProvider()));
            List<String> procedures = recommendInfo.getProcedures();
            LinearLayout linearLayout2 = (LinearLayout) viewInflate.findViewById(R.id.ll_use_procedure);
            int i = 1;
            for (String str : procedures) {
                LinearLayout linearLayout3 = (LinearLayout) layoutInflater.inflate(R.layout.list_item_recommend_detail, (ViewGroup) linearLayout2, false);
                TextView textView = (TextView) linearLayout3.findViewById(R.id.tv_procedure_number);
                textView.setText(String.valueOf(i));
                float f2 = getContext().getResources().getDisplayMetrics().density * 24.0f;
                GradientDrawable gradientDrawable2 = new GradientDrawable();
                gradientDrawable2.setColor(getCategoryColor(recommendInfo));
                gradientDrawable2.setCornerRadii(new float[]{f2, f2, f2, f2, f2, f2, f2, f2});
                textView.setBackground(gradientDrawable2);
                ((TextView) linearLayout3.findViewById(R.id.tv_procedure_detail)).setText(str);
                linearLayout2.addView(linearLayout3);
                i++;
            }
            final LinkageAgent linkage = recommendInfo.getLinkage();
            Button button = (Button) viewInflate.findViewById(R.id.b_select_recommend_button);
            int i2 = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$LinkageAgent$LaunchTarget[linkage.getLaunchTarget().ordinal()];
            if (i2 == 1) {
                button.setText(getText(R.string.button_text_recommend_detail_app_start));
            } else if (i2 == 2) {
                button.setText(getText(R.string.button_text_recommend_detail_site_access));
            } else if (i2 == 3) {
                button.setText(getText(R.string.button_text_recommend_detail_app_download));
            }
            button.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.RecommendDetailFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    Intent intent;
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_START_RECOMMEND_SERVICE, recommendDetailDrawStructure);
                    try {
                        LinkageAgent.LinkageInfo linkageInfo = linkage.getLinkageInfo();
                        if (linkageInfo.getError() == LinkageAgent.ErrorType.NOT_FOUND_ACTIVITY) {
                            RecommendDetailFragment.this.showWarningDialog(R.string.dlg_warning_not_found_activity);
                            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_not_found_activity");
                        } else if (linkageInfo.getError() == LinkageAgent.ErrorType.NOT_FOUND_PACKAGE || (intent = linkageInfo.getIntent()) == null) {
                            RecommendDetailFragment.this.showWarningDialog(R.string.dlg_warning_signature_unmatched);
                            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_signature_unmatched");
                        } else {
                            Structure structure2 = RecommendDetailFragment.this.getStructure();
                            if (structure2 != null && (structure2 instanceof RecommendDetailDrawStructure)) {
                                RecommendDetailFragment.this.startActivity(intent);
                                ((RecommendDetailDrawStructure) structure2).actServiceStartSuccess();
                            }
                        }
                    } catch (Exception unused) {
                        RecommendDetailFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                        AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                    }
                }
            });
            return viewInflate;
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:23:0x004d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int getCategoryColor(com.felicanetworks.mfm.main.presenter.agent.RecommendInfoAgent r7) {
        /*
            r6 = this;
            java.lang.String r7 = r7.getCategoryId()
            int r0 = r7.hashCode()
            r1 = 5
            r2 = 4
            r3 = 3
            r4 = 2
            r5 = 1
            switch(r0) {
                case 1537: goto L43;
                case 1538: goto L39;
                case 1539: goto L2f;
                case 1540: goto L25;
                case 1541: goto L1b;
                case 1542: goto L11;
                default: goto L10;
            }
        L10:
            goto L4d
        L11:
            java.lang.String r0 = "06"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L4d
            r7 = 5
            goto L4e
        L1b:
            java.lang.String r0 = "05"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L4d
            r7 = 4
            goto L4e
        L25:
            java.lang.String r0 = "04"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L4d
            r7 = 3
            goto L4e
        L2f:
            java.lang.String r0 = "03"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L4d
            r7 = 2
            goto L4e
        L39:
            java.lang.String r0 = "02"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L4d
            r7 = 1
            goto L4e
        L43:
            java.lang.String r0 = "01"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L4d
            r7 = 0
            goto L4e
        L4d:
            r7 = -1
        L4e:
            if (r7 == 0) goto L72
            if (r7 == r5) goto L6e
            if (r7 == r4) goto L6a
            if (r7 == r3) goto L66
            if (r7 == r2) goto L62
            if (r7 == r1) goto L5e
            r7 = 2131099748(0x7f060064, float:1.7811858E38)
            goto L75
        L5e:
            r7 = 2131099750(0x7f060066, float:1.7811862E38)
            goto L75
        L62:
            r7 = 2131099756(0x7f06006c, float:1.7811874E38)
            goto L75
        L66:
            r7 = 2131099746(0x7f060062, float:1.7811854E38)
            goto L75
        L6a:
            r7 = 2131099742(0x7f06005e, float:1.7811846E38)
            goto L75
        L6e:
            r7 = 2131099752(0x7f060068, float:1.7811866E38)
            goto L75
        L72:
            r7 = 2131099754(0x7f06006a, float:1.781187E38)
        L75:
            android.content.Context r0 = r6.getContext()
            int r7 = androidx.core.content.ContextCompat.getColor(r0, r7)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.view.views.RecommendDetailFragment.getCategoryColor(com.felicanetworks.mfm.main.presenter.agent.RecommendInfoAgent):int");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:23:0x004d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int getCategorySubColor(com.felicanetworks.mfm.main.presenter.agent.RecommendInfoAgent r7) {
        /*
            r6 = this;
            java.lang.String r7 = r7.getCategoryId()
            int r0 = r7.hashCode()
            r1 = 5
            r2 = 4
            r3 = 3
            r4 = 2
            r5 = 1
            switch(r0) {
                case 1537: goto L43;
                case 1538: goto L39;
                case 1539: goto L2f;
                case 1540: goto L25;
                case 1541: goto L1b;
                case 1542: goto L11;
                default: goto L10;
            }
        L10:
            goto L4d
        L11:
            java.lang.String r0 = "06"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L4d
            r7 = 5
            goto L4e
        L1b:
            java.lang.String r0 = "05"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L4d
            r7 = 4
            goto L4e
        L25:
            java.lang.String r0 = "04"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L4d
            r7 = 3
            goto L4e
        L2f:
            java.lang.String r0 = "03"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L4d
            r7 = 2
            goto L4e
        L39:
            java.lang.String r0 = "02"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L4d
            r7 = 1
            goto L4e
        L43:
            java.lang.String r0 = "01"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L4d
            r7 = 0
            goto L4e
        L4d:
            r7 = -1
        L4e:
            if (r7 == 0) goto L72
            if (r7 == r5) goto L6e
            if (r7 == r4) goto L6a
            if (r7 == r3) goto L66
            if (r7 == r2) goto L62
            if (r7 == r1) goto L5e
            r7 = 2131099749(0x7f060065, float:1.781186E38)
            goto L75
        L5e:
            r7 = 2131099751(0x7f060067, float:1.7811864E38)
            goto L75
        L62:
            r7 = 2131099757(0x7f06006d, float:1.7811876E38)
            goto L75
        L66:
            r7 = 2131099747(0x7f060063, float:1.7811856E38)
            goto L75
        L6a:
            r7 = 2131099743(0x7f06005f, float:1.7811848E38)
            goto L75
        L6e:
            r7 = 2131099753(0x7f060069, float:1.7811868E38)
            goto L75
        L72:
            r7 = 2131099755(0x7f06006b, float:1.7811872E38)
        L75:
            android.content.Context r0 = r6.getContext()
            int r7 = androidx.core.content.ContextCompat.getColor(r0, r7)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.view.views.RecommendDetailFragment.getCategorySubColor(com.felicanetworks.mfm.main.presenter.agent.RecommendInfoAgent):int");
    }
}
