package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.presenter.agent.RecommendCategoryInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.RecommendInfoAgent;
import com.felicanetworks.mfm.main.presenter.structure.CentralDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.PrimaryDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.view.views.ContentTabsFragment;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RecommendListFragment extends BaseFragment implements ContentTabsFragment.ViewPageable {
    LinearLayoutManager mLayoutManager = null;
    RecyclerView mRecommendList = null;
    private CentralDrawStructure mStructure;

    @Override // com.felicanetworks.mfm.main.view.views.ContentTabsFragment.ViewPageable
    public void onPagePause() {
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Structure structure;
        View viewInflate = layoutInflater.inflate(R.layout.fragment_recommend, viewGroup, false);
        try {
            structure = getStructure();
        } catch (Exception e) {
            notifyException(e);
        }
        if (structure != null && (structure instanceof CentralDrawStructure)) {
            this.mStructure = (CentralDrawStructure) structure;
            setHasOptionsMenu(true);
            this.mLayoutManager = new LinearLayoutManager(getContext());
            RecommendListAdapter recommendListAdapter = new RecommendListAdapter(getActivity(), this.mStructure.getCentralFunc().getRecommendInfoList());
            RecyclerView recyclerView = (RecyclerView) viewInflate.findViewById(R.id.rv_bookmark_list);
            this.mRecommendList = recyclerView;
            recyclerView.setFocusableInTouchMode(false);
            this.mRecommendList.setFocusable(false);
            this.mRecommendList.setAdapter(recommendListAdapter);
            this.mRecommendList.setLayoutManager(this.mLayoutManager);
            return viewInflate;
        }
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        try {
            menuInflater.inflate(R.menu.menu_lock, menu);
            updateLockMenu(menu);
        } catch (Exception e) {
            notifyException(e);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        try {
            if (menuItem.getItemId() == R.id.tool_lock) {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_LOCK_SETTING, new Object[0]);
                if (Settings.DeviceType.GP == Settings.getDeviceType()) {
                    try {
                        startActivity(this.mStructure.getDefaultIntent(PrimaryDrawStructure.LinkType.SCREEN_LOCK_SETTING));
                    } catch (Exception unused) {
                        showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                        AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                    }
                } else if (Settings.DeviceType.PIXEL == Settings.getDeviceType() && Build.VERSION.SDK_INT >= 29) {
                    try {
                        startActivity(this.mStructure.getDefaultIntent(PrimaryDrawStructure.LinkType.SCREEN_LOCK_SETTING));
                    } catch (Exception unused2) {
                        showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                        AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                    }
                } else {
                    this.mStructure.actLock();
                }
            }
        } catch (Exception e) {
            notifyException(e);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void updateLockMenu(Menu menu) {
        MenuItem menuItemFindItem = menu.findItem(R.id.tool_lock);
        if (Settings.DeviceType.PIXEL == Settings.getDeviceType() && Build.VERSION.SDK_INT <= 28) {
            menuItemFindItem.setVisible(false);
            return;
        }
        if (Settings.DeviceType.FAVER == Settings.getDeviceType()) {
            if (this.mStructure.isFelicaLock()) {
                menuItemFindItem.setIcon(R.drawable.ic_lock_lock_active);
                return;
            } else {
                menuItemFindItem.setIcon(R.drawable.ic_lock_unlock_active);
                return;
            }
        }
        if (this.mStructure.isScreenLock(getContext())) {
            menuItemFindItem.setIcon(R.drawable.header_lock);
        } else {
            menuItemFindItem.setIcon(R.drawable.header_unlock);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.views.ContentTabsFragment.ViewPageable
    public void onPageResume() {
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_02_02, new Object[0]);
    }

    public class RecommendListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
        private static final String RECOMMEND_CATEGORY_ID_1 = "01";
        private static final String RECOMMEND_CATEGORY_ID_2 = "02";
        private static final String RECOMMEND_CATEGORY_ID_3 = "03";
        private static final String RECOMMEND_CATEGORY_ID_4 = "04";
        private static final String RECOMMEND_CATEGORY_ID_5 = "05";
        private static final String RECOMMEND_CATEGORY_ID_6 = "06";
        private static final int VIEW_TYPE_CATEGORY = 1;
        private static final int VIEW_TYPE_HEADER = 0;
        private static final int VIEW_TYPE_LIST_ITEM = 2;
        private LayoutInflater mInflater;
        private RecommendList mRecommendAgentList;

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public long getItemId(int i) {
            return i;
        }

        private class RecommendList {
            private List<RecommendData> mServiceList;
            final /* synthetic */ RecommendListAdapter this$1;

            RecommendList(RecommendListAdapter recommendListAdapter, List<RecommendCategoryInfoAgent> list) {
                RecommendList recommendList = this;
                recommendList.this$1 = recommendListAdapter;
                recommendList.mServiceList = new ArrayList();
                int color = ContextCompat.getColor(RecommendListFragment.this.getContext(), R.color.color_category_gray);
                int i = color;
                for (RecommendCategoryInfoAgent recommendCategoryInfoAgent : list) {
                    List<RecommendInfoAgent> recommendList2 = recommendCategoryInfoAgent.getRecommendList();
                    if (recommendList2.size() > 0) {
                        int categoryColor = recommendList.getCategoryColor(recommendCategoryInfoAgent);
                        int categoryIcon = recommendList.getCategoryIcon(recommendCategoryInfoAgent);
                        Iterator<RecommendInfoAgent> it = recommendList2.iterator();
                        boolean z = true;
                        while (it.hasNext()) {
                            recommendList.mServiceList.add(new RecommendData(recommendCategoryInfoAgent, it.next(), z, categoryColor, i, categoryIcon));
                            z = false;
                            recommendList = this;
                        }
                        i = categoryColor;
                    }
                    recommendList = this;
                }
            }

            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
            /* JADX WARN: Removed duplicated region for block: B:23:0x004d  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            private int getCategoryColor(com.felicanetworks.mfm.main.presenter.agent.RecommendCategoryInfoAgent r7) {
                /*
                    r6 = this;
                    java.lang.String r7 = r7.getId()
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
                    com.felicanetworks.mfm.main.view.views.RecommendListFragment$RecommendListAdapter r0 = r6.this$1
                    com.felicanetworks.mfm.main.view.views.RecommendListFragment r0 = com.felicanetworks.mfm.main.view.views.RecommendListFragment.this
                    android.content.Context r0 = r0.getContext()
                    int r7 = androidx.core.content.ContextCompat.getColor(r0, r7)
                    return r7
                */
                throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.view.views.RecommendListFragment.RecommendListAdapter.RecommendList.getCategoryColor(com.felicanetworks.mfm.main.presenter.agent.RecommendCategoryInfoAgent):int");
            }

            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
            /* JADX WARN: Removed duplicated region for block: B:23:0x004d  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            private int getCategoryIcon(com.felicanetworks.mfm.main.presenter.agent.RecommendCategoryInfoAgent r7) {
                /*
                    r6 = this;
                    java.lang.String r7 = r7.getId()
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
                    r7 = 2131230974(0x7f0800fe, float:1.8078016E38)
                    goto L75
                L5e:
                    r7 = 2131230976(0x7f080100, float:1.807802E38)
                    goto L75
                L62:
                    r7 = 2131230975(0x7f0800ff, float:1.8078018E38)
                    goto L75
                L66:
                    r7 = 2131230977(0x7f080101, float:1.8078022E38)
                    goto L75
                L6a:
                    r7 = 2131230972(0x7f0800fc, float:1.8078012E38)
                    goto L75
                L6e:
                    r7 = 2131230973(0x7f0800fd, float:1.8078014E38)
                    goto L75
                L72:
                    r7 = 2131230971(0x7f0800fb, float:1.807801E38)
                L75:
                    return r7
                */
                throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.view.views.RecommendListFragment.RecommendListAdapter.RecommendList.getCategoryIcon(com.felicanetworks.mfm.main.presenter.agent.RecommendCategoryInfoAgent):int");
            }

            int getCount() {
                return this.mServiceList.size() + 1;
            }

            RecommendData getRecommendAgent(int i) {
                if (i == 0) {
                    return null;
                }
                return this.mServiceList.get(i - 1);
            }

            public boolean checkExistRecommendList() {
                return this.mServiceList.size() > 0;
            }

            public class RecommendData {
                private int mCategoryColor;
                private int mCategoryIcon;
                private RecommendCategoryInfoAgent mCategoryInfoAgent;
                private boolean mCategoryTop;
                private int mPreviousCategoryColor;
                private RecommendInfoAgent mRecommendInfoAgents;

                RecommendData(RecommendCategoryInfoAgent recommendCategoryInfoAgent, RecommendInfoAgent recommendInfoAgent, boolean z, int i, int i2, int i3) {
                    this.mCategoryTop = false;
                    this.mCategoryInfoAgent = recommendCategoryInfoAgent;
                    this.mRecommendInfoAgents = recommendInfoAgent;
                    this.mCategoryTop = z;
                    this.mCategoryColor = i;
                    this.mPreviousCategoryColor = i2;
                    this.mCategoryIcon = i3;
                }

                public RecommendCategoryInfoAgent getCategoryInfoAgent() {
                    return this.mCategoryInfoAgent;
                }

                public RecommendInfoAgent getRecommendInfoAgent() {
                    return this.mRecommendInfoAgents;
                }

                public boolean isCategoryTop() {
                    return this.mCategoryTop;
                }

                public int getCategoryColor() {
                    return this.mCategoryColor;
                }

                public int getPreviousCategoryColor() {
                    return this.mPreviousCategoryColor;
                }

                public Drawable getCategoryIcon() {
                    return ContextCompat.getDrawable(RecommendListFragment.this.getContext(), this.mCategoryIcon);
                }
            }
        }

        public class BaseViewHolder extends RecyclerView.ViewHolder {
            public int mPosition;
            public View mView;

            public BaseViewHolder(View view) {
                super(view);
                this.mView = view;
            }

            public void setPosition(int i) {
                this.mPosition = i;
            }
        }

        public class ListItemViewHolder extends BaseViewHolder {
            public TextView mProviderName;
            public TextView mRecommendText;
            public ImageView mServiceImage;
            public TextView mServiceName;

            public ListItemViewHolder(View view) {
                super(view);
                this.mServiceImage = (ImageView) view.findViewById(R.id.iv_service_icon);
                this.mServiceName = (TextView) view.findViewById(R.id.tv_label);
                this.mRecommendText = (TextView) view.findViewById(R.id.tv_recommend_text);
                this.mProviderName = (TextView) view.findViewById(R.id.tv_recommend_provider);
            }
        }

        public class CategoryViewHolder extends ListItemViewHolder {
            public View mCategoryBackground;
            public ImageView mCategoryBar;
            public ImageView mCategoryImage;
            public TextView mCategoryView;

            public CategoryViewHolder(View view) {
                super(view);
                this.mCategoryBar = (ImageView) view.findViewById(R.id.iv_recommend_bar_top);
                this.mCategoryImage = (ImageView) view.findViewById(R.id.iv_category_image);
                this.mCategoryView = (TextView) view.findViewById(R.id.tv_category_text);
                this.mCategoryBackground = view.findViewById(R.id.fl_category_background);
            }
        }

        public class HeaderViewHolder extends BaseViewHolder {
            public TextView mHeaderView;

            public HeaderViewHolder(View view) {
                super(view);
                this.mHeaderView = (TextView) view.findViewById(R.id.tv_recommend_header);
            }
        }

        public RecommendListAdapter(Context context, List<RecommendCategoryInfoAgent> list) {
            this.mInflater = null;
            this.mRecommendAgentList = null;
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
            this.mRecommendAgentList = new RecommendList(this, list);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mRecommendAgentList.getCount();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            try {
                RecommendList.RecommendData recommendAgent = this.mRecommendAgentList.getRecommendAgent(i);
                if (recommendAgent == null) {
                    return 0;
                }
                return recommendAgent.isCategoryTop() ? 1 : 2;
            } catch (Exception e) {
                RecommendListFragment.this.notifyException(e);
                return 2;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v0 */
        /* JADX WARN: Type inference failed for: r2v1 */
        /* JADX WARN: Type inference failed for: r2v2, types: [androidx.recyclerview.widget.RecyclerView$ViewHolder] */
        /* JADX WARN: Type inference failed for: r2v3 */
        /* JADX WARN: Type inference failed for: r2v4, types: [androidx.recyclerview.widget.RecyclerView$ViewHolder] */
        /* JADX WARN: Type inference failed for: r6v0, types: [int] */
        /* JADX WARN: Type inference failed for: r6v10, types: [com.felicanetworks.mfm.main.view.views.RecommendListFragment$RecommendListAdapter$HeaderViewHolder] */
        /* JADX WARN: Type inference failed for: r6v11 */
        /* JADX WARN: Type inference failed for: r6v12 */
        /* JADX WARN: Type inference failed for: r6v2 */
        /* JADX WARN: Type inference failed for: r6v5 */
        /* JADX WARN: Type inference failed for: r6v8 */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            ?? r2 = 0;
            try {
                if (i == 0) {
                    View viewInflate = this.mInflater.inflate(R.layout.list_item_recommend_header, viewGroup, false);
                    viewInflate.setFocusable(true);
                    i = new HeaderViewHolder(viewInflate);
                } else {
                    try {
                        if (i == 1) {
                            View viewInflate2 = this.mInflater.inflate(R.layout.list_item_recommend_category_top, viewGroup, false);
                            CategoryViewHolder categoryViewHolder = new CategoryViewHolder(viewInflate2);
                            View viewFindViewById = viewInflate2.findViewById(R.id.ll_recommend_card);
                            viewFindViewById.setFocusable(true);
                            viewFindViewById.setClickable(true);
                            viewFindViewById.setOnClickListener(this);
                            viewFindViewById.setTag(categoryViewHolder);
                            i = categoryViewHolder;
                        } else {
                            if (i != 2) {
                                return null;
                            }
                            View viewInflate3 = this.mInflater.inflate(R.layout.list_item_recommend, viewGroup, false);
                            viewInflate3.setFocusable(true);
                            viewInflate3.setClickable(true);
                            viewInflate3.setOnClickListener(this);
                            ListItemViewHolder listItemViewHolder = new ListItemViewHolder(viewInflate3);
                            viewInflate3.setTag(listItemViewHolder);
                            i = listItemViewHolder;
                        }
                    } catch (Exception e) {
                        e = e;
                        r2 = i;
                        RecommendListFragment.this.notifyException(e);
                        return r2;
                    }
                }
                r2 = i;
                return r2;
            } catch (Exception e2) {
                e = e2;
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            try {
                if (viewHolder instanceof HeaderViewHolder) {
                    if (this.mRecommendAgentList.checkExistRecommendList()) {
                        return;
                    }
                    ((HeaderViewHolder) viewHolder).mHeaderView.setText(R.string.warning_recommend_empty);
                    return;
                }
                RecommendList.RecommendData recommendAgent = this.mRecommendAgentList.getRecommendAgent(i);
                if (viewHolder instanceof ListItemViewHolder) {
                    RecommendInfoAgent recommendInfoAgent = recommendAgent.getRecommendInfoAgent();
                    ListItemViewHolder listItemViewHolder = (ListItemViewHolder) viewHolder;
                    listItemViewHolder.mServiceImage.setImageBitmap(recommendInfoAgent.getIcon());
                    listItemViewHolder.mServiceName.setText(recommendInfoAgent.getName());
                    listItemViewHolder.mRecommendText.setText(recommendInfoAgent.getOverview());
                    listItemViewHolder.mProviderName.setText(recommendInfoAgent.getProvider());
                    listItemViewHolder.mView.setBackgroundColor(recommendAgent.getCategoryColor());
                    listItemViewHolder.setPosition(i);
                }
                if (viewHolder instanceof CategoryViewHolder) {
                    CategoryViewHolder categoryViewHolder = (CategoryViewHolder) viewHolder;
                    categoryViewHolder.mCategoryView.setText(recommendAgent.getCategoryInfoAgent().getTitle());
                    if (Build.VERSION.SDK_INT >= 29) {
                        categoryViewHolder.mCategoryBar.setColorFilter(new BlendModeColorFilter(recommendAgent.getCategoryColor(), BlendMode.SRC_IN));
                    } else {
                        categoryViewHolder.mCategoryBar.setColorFilter(recommendAgent.getCategoryColor(), PorterDuff.Mode.SRC_IN);
                    }
                    categoryViewHolder.mCategoryBackground.setBackgroundColor(recommendAgent.getCategoryColor());
                    categoryViewHolder.mView.setBackgroundColor(recommendAgent.getPreviousCategoryColor());
                    categoryViewHolder.mCategoryImage.setImageDrawable(recommendAgent.getCategoryIcon());
                }
            } catch (Exception e) {
                RecommendListFragment.this.notifyException(e);
            }
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            try {
                RecommendInfoAgent recommendInfoAgent = this.mRecommendAgentList.getRecommendAgent(((BaseViewHolder) view.getTag()).mPosition).getRecommendInfoAgent();
                AnalysisManager.stamp(MfmStamp.Event.ACTION_SHOW_DETAIL, recommendInfoAgent);
                Structure structure = RecommendListFragment.this.getStructure();
                if (structure instanceof CentralDrawStructure) {
                    ((CentralDrawStructure) structure).actSelectRecommend(recommendInfoAgent);
                }
            } catch (Exception e) {
                RecommendListFragment.this.notifyException(e);
            }
        }
    }
}
