package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.BlendMode;
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
import androidx.core.view.MenuProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.felicanetworks.mfc.FSC$$ExternalSyntheticApiModelOutline0;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.presenter.agent.RecommendCategoryInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.RecommendInfoAgent;
import com.felicanetworks.mfm.main.presenter.structure.CentralDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.PrimaryDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.view.activity.CentralActivity;
import com.felicanetworks.mfm.main.view.views.ContentTabsFragment;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class RecommendListFragment extends BaseFragment implements ContentTabsFragment.ViewPageable {
    private RecommendListAdapter adapter;
    private CentralDrawStructure mStructure;
    LinearLayoutManager mLayoutManager = null;
    RecyclerView mRecommendList = null;
    private boolean isFastBoot = true;
    RecommendListMenuProvider mMenuProvider = null;
    private boolean isForeground = false;

    public interface RecommendListMenuProvider extends MenuProvider {
        void update();
    }

    @Override // com.felicanetworks.mfm.main.view.views.ContentTabsFragment.ViewPageable
    public void onPagePause() {
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewInflate = inflater.inflate(R.layout.fragment_recommend, container, false);
        try {
            Structure structure = getStructure();
            if (structure != null && (structure instanceof CentralDrawStructure)) {
                CentralDrawStructure centralDrawStructure = (CentralDrawStructure) structure;
                this.mStructure = centralDrawStructure;
                centralDrawStructure.registerBackgroundUpdateRecommend(new CentralDrawStructure.BackgroundUpdateRecommendListener() { // from class: com.felicanetworks.mfm.main.view.views.RecommendListFragment.1
                    @Override // com.felicanetworks.mfm.main.presenter.structure.CentralDrawStructure.BackgroundUpdateRecommendListener
                    public void onCompleted() {
                        if (RecommendListFragment.this.isForeground) {
                            if (RecommendListFragment.this.mMenuProvider != null) {
                                RecommendListFragment.this.mMenuProvider.update();
                            }
                            if (RecommendListFragment.this.adapter != null) {
                                RecommendListFragment.this.adapter.update();
                            }
                        }
                    }
                });
                addMenuProvider();
                this.mLayoutManager = new LinearLayoutManager(getContext());
                this.adapter = new RecommendListAdapter(getActivity(), this.mStructure.getCentralFunc().getRecommendInfoList());
                RecyclerView recyclerView = (RecyclerView) viewInflate.findViewById(R.id.rv_bookmark_list);
                this.mRecommendList = recyclerView;
                recyclerView.setFocusableInTouchMode(false);
                this.mRecommendList.setFocusable(false);
                this.mRecommendList.setAdapter(this.adapter);
                this.mRecommendList.setLayoutManager(this.mLayoutManager);
                adjustViewToNavigationBar(this.mRecommendList, true);
                return viewInflate;
            }
        } catch (Exception e) {
            notifyException(e);
        }
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        adjustViewToNavigationBar(this.mRecommendList, true);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        RecommendListMenuProvider recommendListMenuProvider = this.mMenuProvider;
        if (recommendListMenuProvider != null) {
            recommendListMenuProvider.update();
        }
        RecommendListAdapter recommendListAdapter = this.adapter;
        if (recommendListAdapter != null) {
            recommendListAdapter.update();
        }
        if (getActivity() instanceof CentralActivity) {
            CentralActivity centralActivity = (CentralActivity) getActivity();
            if (centralActivity.isReturnFromOtherScreen() || this.isFastBoot) {
                centralActivity.setReturnFromOtherScreen(false);
                this.isFastBoot = false;
            }
            this.isForeground = true;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.isForeground = false;
    }

    private void addMenuProvider() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        removeMenuProvider();
        if (this.mMenuProvider == null) {
            this.mMenuProvider = new RecommendListMenuProvider() { // from class: com.felicanetworks.mfm.main.view.views.RecommendListFragment.2
                public MenuItem _lock;

                @Override // androidx.core.view.MenuProvider
                public /* synthetic */ void onMenuClosed(Menu menu) {
                    MenuProvider.CC.$default$onMenuClosed(this, menu);
                }

                @Override // androidx.core.view.MenuProvider
                public /* synthetic */ void onPrepareMenu(Menu menu) {
                    MenuProvider.CC.$default$onPrepareMenu(this, menu);
                }

                @Override // androidx.core.view.MenuProvider
                public void onCreateMenu(Menu menu, MenuInflater menuInflater) {
                    try {
                        menuInflater.inflate(R.menu.menu_lock, menu);
                        MenuItem menuItemFindItem = menu.findItem(R.id.tool_lock);
                        this._lock = menuItemFindItem;
                        RecommendListFragment.this.updateLockMenu(menuItemFindItem);
                    } catch (Exception e) {
                        RecommendListFragment.this.notifyException(e);
                    }
                }

                @Override // androidx.core.view.MenuProvider
                public boolean onMenuItemSelected(MenuItem item) {
                    Exception e;
                    boolean z;
                    try {
                    } catch (Exception e2) {
                        e = e2;
                        z = false;
                    }
                    if (item.getItemId() != R.id.tool_lock) {
                        return false;
                    }
                    z = true;
                    try {
                        AnalysisManager.stamp(MfmStamp.Event.ACTION_LOCK_SETTING, new Object[0]);
                        if (Settings.DeviceType.GP == Settings.getDeviceType()) {
                            try {
                                RecommendListFragment.this.startActivity(RecommendListFragment.this.mStructure.getDefaultIntent(PrimaryDrawStructure.LinkType.SCREEN_LOCK_SETTING));
                            } catch (Exception unused) {
                                RecommendListFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                            }
                        } else if (Settings.DeviceType.PIXEL != Settings.getDeviceType() || Build.VERSION.SDK_INT < 29) {
                            RecommendListFragment.this.mStructure.actLock();
                        } else {
                            try {
                                RecommendListFragment.this.startActivity(RecommendListFragment.this.mStructure.getDefaultIntent(PrimaryDrawStructure.LinkType.SCREEN_LOCK_SETTING));
                            } catch (Exception unused2) {
                                RecommendListFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                            }
                        }
                    } catch (Exception e3) {
                        e = e3;
                        RecommendListFragment.this.notifyException(e);
                    }
                    return z;
                }

                @Override // com.felicanetworks.mfm.main.view.views.RecommendListFragment.RecommendListMenuProvider
                public void update() {
                    RecommendListFragment.this.updateLockMenu(this._lock);
                }
            };
        }
        activity.addMenuProvider(this.mMenuProvider, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void removeMenuProvider() {
        RecommendListMenuProvider recommendListMenuProvider;
        FragmentActivity activity = getActivity();
        if (activity == null || (recommendListMenuProvider = this.mMenuProvider) == null) {
            return;
        }
        activity.removeMenuProvider(recommendListMenuProvider);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLockMenu(MenuItem lock) {
        if (lock == null) {
            return;
        }
        if (Settings.DeviceType.PIXEL == Settings.getDeviceType() && Build.VERSION.SDK_INT <= 28) {
            lock.setVisible(false);
            return;
        }
        if (Settings.DeviceType.FAVER == Settings.getDeviceType()) {
            if (this.mStructure.isFelicaLock()) {
                lock.setIcon(R.drawable.ic_lock_lock_active);
                return;
            } else {
                lock.setIcon(R.drawable.ic_lock_unlock_active);
                return;
            }
        }
        if (this.mStructure.isScreenLock(getContext())) {
            lock.setIcon(R.drawable.header_lock);
        } else {
            lock.setIcon(R.drawable.header_unlock);
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
        public long getItemId(int position) {
            return position;
        }

        private class RecommendList {
            private List<RecommendData> mServiceList = new ArrayList();

            RecommendList(List<RecommendCategoryInfoAgent> categoryList) {
                int color = ContextCompat.getColor(RecommendListFragment.this.getContext(), R.color.color_category_gray);
                int i = color;
                for (RecommendCategoryInfoAgent recommendCategoryInfoAgent : categoryList) {
                    List<RecommendInfoAgent> recommendList = recommendCategoryInfoAgent.getRecommendList();
                    if (recommendList.size() > 0) {
                        int categoryColor = getCategoryColor(recommendCategoryInfoAgent);
                        int categoryIcon = getCategoryIcon(recommendCategoryInfoAgent);
                        Iterator<RecommendInfoAgent> it = recommendList.iterator();
                        boolean z = true;
                        while (it.hasNext()) {
                            this.mServiceList.add(new RecommendData(recommendCategoryInfoAgent, it.next(), z, categoryColor, i, categoryIcon));
                            z = false;
                        }
                        i = categoryColor;
                    }
                }
            }

            private int getCategoryColor(RecommendCategoryInfoAgent categoryInfoAgent) {
                int i;
                String id = categoryInfoAgent.getId();
                id.hashCode();
                switch (id) {
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
                return ContextCompat.getColor(RecommendListFragment.this.getContext(), i);
            }

            private int getCategoryIcon(RecommendCategoryInfoAgent categoryInfoAgent) {
                String id = categoryInfoAgent.getId();
                id.hashCode();
                switch (id) {
                    case "01":
                        return R.drawable.ic_recommend_eyecatch_docomo;
                    case "02":
                        return R.drawable.ic_recommend_eyecatch_felica;
                    case "03":
                        return R.drawable.ic_recommend_eyecatch_electronic_money;
                    case "04":
                        return R.drawable.ic_recommend_eyecatch_transportation;
                    case "05":
                        return R.drawable.ic_recommend_eyecatch_point;
                    case "06":
                        return R.drawable.ic_recommend_eyecatch_ticket;
                    default:
                        return R.drawable.ic_recommend_eyecatch_others;
                }
            }

            int getCount() {
                return this.mServiceList.size() + 1;
            }

            RecommendData getRecommendAgent(int position) {
                if (position == 0) {
                    return null;
                }
                return this.mServiceList.get(position - 1);
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

                RecommendData(RecommendCategoryInfoAgent categoryInfoAgent, RecommendInfoAgent recommendInfoAgent, boolean categoryTop, int colorId, int previousColorId, int iconId) {
                    this.mCategoryInfoAgent = categoryInfoAgent;
                    this.mRecommendInfoAgents = recommendInfoAgent;
                    this.mCategoryTop = categoryTop;
                    this.mCategoryColor = colorId;
                    this.mPreviousCategoryColor = previousColorId;
                    this.mCategoryIcon = iconId;
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

            public void setPosition(int position) {
                this.mPosition = position;
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

        public RecommendListAdapter(Context context, List<RecommendCategoryInfoAgent> categoryList) {
            this.mInflater = null;
            this.mRecommendAgentList = null;
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
            this.mRecommendAgentList = new RecommendList(categoryList);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mRecommendAgentList.getCount();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int position) {
            try {
                RecommendList.RecommendData recommendAgent = this.mRecommendAgentList.getRecommendAgent(position);
                if (recommendAgent == null) {
                    return 0;
                }
                return recommendAgent.isCategoryTop() ? 1 : 2;
            } catch (Exception e) {
                RecommendListFragment.this.notifyException(e);
                return 2;
            }
        }

        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:19:0x005d */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v0 */
        /* JADX WARN: Type inference failed for: r2v1, types: [androidx.recyclerview.widget.RecyclerView$ViewHolder] */
        /* JADX WARN: Type inference failed for: r2v2 */
        /* JADX WARN: Type inference failed for: r6v0, types: [int] */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ?? r2 = 0;
            try {
                if (viewType == 0) {
                    View viewInflate = this.mInflater.inflate(R.layout.list_item_recommend_header, parent, false);
                    viewInflate.setFocusable(true);
                    return new HeaderViewHolder(viewInflate);
                }
                try {
                    if (viewType == 1) {
                        View viewInflate2 = this.mInflater.inflate(R.layout.list_item_recommend_category_top, parent, false);
                        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(viewInflate2);
                        View viewFindViewById = viewInflate2.findViewById(R.id.ll_recommend_card);
                        viewFindViewById.setFocusable(true);
                        viewFindViewById.setClickable(true);
                        viewFindViewById.setOnClickListener(this);
                        viewFindViewById.setTag(categoryViewHolder);
                        return categoryViewHolder;
                    }
                    if (viewType != 2) {
                        return null;
                    }
                    View viewInflate3 = this.mInflater.inflate(R.layout.list_item_recommend, parent, false);
                    viewInflate3.setFocusable(true);
                    viewInflate3.setClickable(true);
                    viewInflate3.setOnClickListener(this);
                    ListItemViewHolder listItemViewHolder = new ListItemViewHolder(viewInflate3);
                    viewInflate3.setTag(listItemViewHolder);
                    return listItemViewHolder;
                } catch (Exception e) {
                    e = e;
                    r2 = viewType;
                    RecommendListFragment.this.notifyException(e);
                    return r2;
                }
            } catch (Exception e2) {
                e = e2;
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            try {
                if (holder instanceof HeaderViewHolder) {
                    if (this.mRecommendAgentList.checkExistRecommendList()) {
                        return;
                    }
                    ((HeaderViewHolder) holder).mHeaderView.setText(R.string.warning_recommend_empty);
                    return;
                }
                RecommendList.RecommendData recommendAgent = this.mRecommendAgentList.getRecommendAgent(position);
                if (holder instanceof ListItemViewHolder) {
                    RecommendInfoAgent recommendInfoAgent = recommendAgent.getRecommendInfoAgent();
                    ListItemViewHolder listItemViewHolder = (ListItemViewHolder) holder;
                    listItemViewHolder.mServiceImage.setImageBitmap(recommendInfoAgent.getIcon());
                    listItemViewHolder.mServiceName.setText(recommendInfoAgent.getName());
                    listItemViewHolder.mRecommendText.setText(recommendInfoAgent.getOverview());
                    listItemViewHolder.mProviderName.setText(recommendInfoAgent.getProvider());
                    listItemViewHolder.mView.setBackgroundColor(recommendAgent.getCategoryColor());
                    listItemViewHolder.setPosition(position);
                }
                if (holder instanceof CategoryViewHolder) {
                    CategoryViewHolder categoryViewHolder = (CategoryViewHolder) holder;
                    categoryViewHolder.mCategoryView.setText(recommendAgent.getCategoryInfoAgent().getTitle());
                    if (Build.VERSION.SDK_INT >= 29) {
                        ImageView imageView = categoryViewHolder.mCategoryBar;
                        FSC$$ExternalSyntheticApiModelOutline0.m$2();
                        imageView.setColorFilter(FSC$$ExternalSyntheticApiModelOutline0.m(recommendAgent.getCategoryColor(), BlendMode.SRC_IN));
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
        public void onClick(View v) {
            try {
                RecommendInfoAgent recommendInfoAgent = this.mRecommendAgentList.getRecommendAgent(((BaseViewHolder) v.getTag()).mPosition).getRecommendInfoAgent();
                AnalysisManager.stamp(MfmStamp.Event.ACTION_SHOW_DETAIL, recommendInfoAgent);
                Structure structure = RecommendListFragment.this.getStructure();
                if (structure instanceof CentralDrawStructure) {
                    ((CentralDrawStructure) structure).actSelectRecommend(recommendInfoAgent);
                }
            } catch (Exception e) {
                RecommendListFragment.this.notifyException(e);
            }
        }

        public void update() {
            this.mRecommendAgentList = new RecommendList(RecommendListFragment.this.mStructure.getCentralFunc().getRecommendInfoList());
            notifyDataSetChanged();
        }
    }
}
