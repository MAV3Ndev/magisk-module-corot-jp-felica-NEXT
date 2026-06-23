package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.presenter.agent.FelicaPocketFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.FpAreaInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.FpServiceInfoAgent;
import com.felicanetworks.mfm.main.presenter.structure.FpServiceListDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.view.views.CommonProps;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class FpServiceListFragment extends BaseFragment {
    FpServiceListView mFpServiceList = null;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Structure structure;
        stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_15_01, new Object[0]);
        View viewInflate = layoutInflater.inflate(R.layout.fragment_felica_pocket, viewGroup, false);
        try {
            structure = getStructure();
        } catch (Exception e) {
            notifyException(e);
        }
        if (structure != null && (structure instanceof FpServiceListDrawStructure)) {
            final FpServiceListDrawStructure fpServiceListDrawStructure = (FpServiceListDrawStructure) structure;
            FpAreaInfoAgent fPAreaInfo = fpServiceListDrawStructure.getFelicaPocketFunc().getFPAreaInfo();
            if (fPAreaInfo != null && FelicaPocketFuncAgent.CompiledFpState.FpState.NO_PROBLEM == fpServiceListDrawStructure.getFelicaPocketFunc().getCompiledFpState().getFpState()) {
                Typeface typeface = CommonProps.getTypeface(getContext(), CommonProps.FontType.REGULAR);
                TextView textView = (TextView) viewInflate.findViewById(R.id.tv_fp_num_value);
                textView.setText(String.format(Locale.US, "%010d", Long.valueOf(fPAreaInfo.getFpNum())));
                textView.setTypeface(typeface);
                TextView textView2 = (TextView) viewInflate.findViewById(R.id.tv_pocket_cnt_value);
                textView2.setText(getResources().getString(R.string.text_pocketcount_value_felicapocket, String.format(Locale.US, "%d", Integer.valueOf(fPAreaInfo.getUsedPocket())), String.format(Locale.US, "%d", Integer.valueOf(fPAreaInfo.getTotalPocket()))));
                textView2.setTypeface(typeface);
                this.mFpServiceList = (FpServiceListView) viewInflate.findViewById(R.id.lv_fp_service_list);
                this.mFpServiceList.setAdapter(new FpServiceListAdapter(getActivity(), fPAreaInfo.getFpServiceList()));
            }
            ((Button) viewInflate.findViewById(R.id.text_website_fe_icapocket_res_0x7f0a0261)).setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.FpServiceListFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_WEB_SITE_FP, new Object[0]);
                    try {
                        FpServiceListFragment.this.startActivity(fpServiceListDrawStructure.getDefaultIntent(FpServiceListDrawStructure.LinkType.FP_SITE));
                    } catch (Exception unused) {
                        FpServiceListFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                        AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                    }
                }
            });
            if (fpServiceListDrawStructure.needRecommend()) {
                ((Button) viewInflate.findViewById(R.id.b_select_fp_button)).setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.FpServiceListFragment.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        AnalysisManager.stamp(MfmStamp.Event.ACTION_SHOW_RECOMMEND_FP_BUTTON, new Object[0]);
                        fpServiceListDrawStructure.actRecommend();
                    }
                });
                ImageView imageView = (ImageView) viewInflate.findViewById(R.id.fp_bunner);
                imageView.setImageResource(R.drawable.gr_banner);
                imageView.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.FpServiceListFragment.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        AnalysisManager.stamp(MfmStamp.Event.ACTION_SHOW_RECOMMEND_FP_AREA, new Object[0]);
                        fpServiceListDrawStructure.actRecommend();
                    }
                });
            } else {
                ((LinearLayout) viewInflate.findViewById(R.id.ll_select_recommend)).setVisibility(8);
            }
            return viewInflate;
        }
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        setHasOptionsMenu(true);
    }

    private class FpServiceListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<FpServiceListViewHolder> mViewHolders = new ArrayList();

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        public class FpServiceListViewHolder {
            public LinearLayout mEndServiceDiscription;
            public ImageView mFpIcon;
            public LinearLayout mFpProviderName;
            public FpServiceInfoAgent mFpServiceInfoAgent;
            public TextView mFpServiceName;
            public LinearLayout mLable;
            public FrameLayout mLineRootLayout;
            public TextView mPointUnit;
            public TextView mPointValue;
            public int mPosition;
            public LinearLayout mUnknownService;
            public View mView;

            public FpServiceListViewHolder(FpServiceInfoAgent fpServiceInfoAgent) {
                if (fpServiceInfoAgent == null) {
                    return;
                }
                this.mFpServiceInfoAgent = fpServiceInfoAgent;
            }

            public void setView(View view, int i) {
                this.mPosition = i;
                this.mView = view;
                this.mLable = (LinearLayout) view.findViewById(R.id.ll_card_label);
                this.mLineRootLayout = (FrameLayout) view.findViewById(R.id.fl_line_item_root);
                this.mFpIcon = (ImageView) view.findViewById(R.id.iv_service_icon);
                this.mFpServiceName = (TextView) view.findViewById(R.id.cardArticleTitle);
            }
        }

        public FpServiceListAdapter(Context context, List<FpServiceInfoAgent> list) {
            this.mInflater = null;
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
            Iterator<FpServiceInfoAgent> it = list.iterator();
            while (it.hasNext()) {
                this.mViewHolders.add(new FpServiceListViewHolder(it.next()));
            }
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.mViewHolders.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return this.mViewHolders.get(i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v2, types: [com.felicanetworks.mfm.main.view.views.FpServiceListFragment$FpServiceListAdapter$FpServiceListViewHolder, java.lang.Object] */
        /* JADX WARN: Type inference failed for: r5v0, types: [com.felicanetworks.mfm.main.view.views.FpServiceListFragment$FpServiceListAdapter] */
        /* JADX WARN: Type inference failed for: r7v0, types: [android.view.View] */
        /* JADX WARN: Type inference failed for: r7v1 */
        /* JADX WARN: Type inference failed for: r7v2, types: [android.view.View] */
        /* JADX WARN: Type inference failed for: r7v3 */
        /* JADX WARN: Type inference failed for: r7v4, types: [android.view.View] */
        /* JADX WARN: Type inference failed for: r7v5 */
        /* JADX WARN: Type inference failed for: r7v6 */
        /* JADX WARN: Type inference failed for: r7v7 */
        /* JADX WARN: Type inference failed for: r7v8 */
        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            ?? r7;
            try {
                FpServiceListViewHolder fpServiceListViewHolder = this.mViewHolders.get(i);
                final FpServiceInfoAgent fpServiceInfoAgent = fpServiceListViewHolder.mFpServiceInfoAgent;
                view = view;
                if (view == 0) {
                    ?? Inflate = this.mInflater.inflate(R.layout.list_item_fpservice, viewGroup, false);
                    fpServiceListViewHolder.setView(Inflate, i);
                    Inflate.setTag(fpServiceListViewHolder);
                    view = Inflate;
                }
                fpServiceListViewHolder.mView.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.FpServiceListFragment.FpServiceListAdapter.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        AnalysisManager.stamp(MfmStamp.Event.ACTION_SERVICE_FP, fpServiceInfoAgent);
                        try {
                            try {
                                FpServiceListFragment.this.startActivity(fpServiceInfoAgent.getLinkIntent());
                            } catch (Exception unused) {
                                FpServiceListFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                            }
                        } catch (Exception e) {
                            FpServiceListFragment.this.notifyException(e);
                        }
                    }
                });
                updateListItem(fpServiceListViewHolder, fpServiceInfoAgent);
                r7 = view;
            } catch (Exception e) {
                FpServiceListFragment.this.notifyException(e);
                r7 = view;
            }
            return r7;
        }

        private void updateListItem(final FpServiceListViewHolder fpServiceListViewHolder, final FpServiceInfoAgent fpServiceInfoAgent) {
            float f = FpServiceListFragment.this.getContext().getResources().getDisplayMetrics().density * 8.0f;
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(fpServiceInfoAgent.getColorCode());
            gradientDrawable.setCornerRadii(new float[]{f, f, f, f, 0.0f, 0.0f, 0.0f, 0.0f});
            fpServiceListViewHolder.mLable.setBackground(gradientDrawable);
            fpServiceListViewHolder.mFpServiceName.setText(fpServiceInfoAgent.getServiceName());
            fpServiceListViewHolder.mFpIcon.setImageResource(0);
            if (fpServiceListViewHolder.mFpServiceInfoAgent.getType() != FpServiceInfoAgent.Type.UNKNOWN) {
                fpServiceInfoAgent.orderIconImg(new FpServiceInfoAgent.OrderImgListener() { // from class: com.felicanetworks.mfm.main.view.views.FpServiceListFragment.FpServiceListAdapter.2
                    @Override // com.felicanetworks.mfm.main.presenter.agent.FpServiceInfoAgent.OrderImgListener
                    public void onComplete(Bitmap bitmap) {
                        if (FpServiceListFragment.this.getActivity() == null || FpServiceListFragment.this.getActivity().isFinishing()) {
                            return;
                        }
                        if (fpServiceInfoAgent.getServiceNumber() != ((FpServiceListViewHolder) FpServiceListAdapter.this.getItem(fpServiceListViewHolder.mPosition)).mFpServiceInfoAgent.getServiceNumber() || bitmap == null) {
                            return;
                        }
                        fpServiceListViewHolder.mFpIcon.setImageBitmap(bitmap);
                    }
                });
            }
            Typeface typeface = CommonProps.getTypeface(FpServiceListFragment.this.getContext(), CommonProps.FontType.REGULAR);
            int i = AnonymousClass4.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$FpServiceInfoAgent$Type[fpServiceListViewHolder.mFpServiceInfoAgent.getType().ordinal()];
            if (i == 1) {
                View viewInflate = this.mInflater.inflate(R.layout.list_item_fpservice_twoline, (ViewGroup) fpServiceListViewHolder.mLineRootLayout, true);
                fpServiceListViewHolder.mPointValue = (TextView) viewInflate.findViewById(R.id.tv_point_value);
                fpServiceListViewHolder.mPointValue.setText(NumberFormat.getNumberInstance().format(fpServiceInfoAgent.getPoint()));
                fpServiceListViewHolder.mPointValue.setTypeface(typeface);
                fpServiceListViewHolder.mPointUnit = (TextView) viewInflate.findViewById(R.id.tv_point_unit);
                fpServiceListViewHolder.mPointUnit.setText(fpServiceInfoAgent.getPointUnit());
                fpServiceListViewHolder.mFpProviderName = (LinearLayout) viewInflate.findViewById(R.id.ll_provider_name_area);
                ((TextView) fpServiceListViewHolder.mFpProviderName.findViewById(R.id.tv_provider_name)).setText(fpServiceInfoAgent.getServiceProvider());
                return;
            }
            if (i == 2) {
                View viewInflate2 = this.mInflater.inflate(R.layout.list_item_fpservice_twoline, (ViewGroup) fpServiceListViewHolder.mLineRootLayout, true);
                fpServiceListViewHolder.mPointValue = (TextView) viewInflate2.findViewById(R.id.tv_point_value);
                fpServiceListViewHolder.mPointValue.setText("");
                fpServiceListViewHolder.mPointUnit = (TextView) viewInflate2.findViewById(R.id.tv_point_unit);
                fpServiceListViewHolder.mPointUnit.setText("");
                fpServiceListViewHolder.mFpProviderName = (LinearLayout) viewInflate2.findViewById(R.id.ll_provider_name_area);
                ((TextView) fpServiceListViewHolder.mFpProviderName.findViewById(R.id.tv_provider_name)).setText(fpServiceInfoAgent.getServiceProvider());
                return;
            }
            if (i == 3) {
                View viewInflate3 = this.mInflater.inflate(R.layout.list_item_fpservice_endservice, (ViewGroup) fpServiceListViewHolder.mLineRootLayout, true);
                fpServiceListViewHolder.mEndServiceDiscription = (LinearLayout) viewInflate3.findViewById(R.id.ll_endservice_item);
                ((TextView) fpServiceListViewHolder.mEndServiceDiscription.findViewById(R.id.tv_end_discription)).setText(R.string.text_description_end_service_felicapocket);
                fpServiceListViewHolder.mFpProviderName = (LinearLayout) viewInflate3.findViewById(R.id.ll_provider_name_area);
                ((TextView) fpServiceListViewHolder.mFpProviderName.findViewById(R.id.tv_provider_name)).setText(fpServiceInfoAgent.getServiceProvider());
                return;
            }
            if (i != 4) {
                return;
            }
            View viewInflate4 = this.mInflater.inflate(R.layout.list_item_fpservice_unknown, (ViewGroup) fpServiceListViewHolder.mLineRootLayout, true);
            fpServiceListViewHolder.mFpServiceName.setText(FpServiceListFragment.this.getResources().getString(R.string.text_description_unknown_service_felicapocket));
            fpServiceListViewHolder.mFpIcon.setImageResource(R.drawable.hatena);
            fpServiceListViewHolder.mUnknownService = (LinearLayout) viewInflate4.findViewById(R.id.ll_unknown_fp_service_item);
            ((TextView) fpServiceListViewHolder.mUnknownService.findViewById(R.id.tv_unknown_service_id)).setText(FpServiceListFragment.this.getResources().getString(R.string.text_unknown_service_felicapocket, String.format(Locale.US, "%08d", Integer.valueOf(fpServiceInfoAgent.getServiceNumber()))));
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.views.FpServiceListFragment$4, reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$FpServiceInfoAgent$Type;

        static {
            int[] iArr = new int[FpServiceInfoAgent.Type.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$FpServiceInfoAgent$Type = iArr;
            try {
                iArr[FpServiceInfoAgent.Type.POINT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$FpServiceInfoAgent$Type[FpServiceInfoAgent.Type.OTHER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$FpServiceInfoAgent$Type[FpServiceInfoAgent.Type.UNSUPPORTED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$FpServiceInfoAgent$Type[FpServiceInfoAgent.Type.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }
}
