package com.felicanetworks.mfm.main.view.views;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.result.ActivityResultCaller;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.presenter.structure.CentralDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.view.activity.BaseActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
public class ContentTabsFragment extends BaseFragment {
    private TabType currentTab;
    private ViewPager2 pager;
    private boolean isFastBoot = true;
    private final Map<TabType, Fragment> tabFragments = new HashMap<TabType, Fragment>() { // from class: com.felicanetworks.mfm.main.view.views.ContentTabsFragment.1
        {
            put(TabType.MYSERVICE, new MyServiceFragment());
            put(TabType.RECOMMEND, new RecommendListFragment());
        }
    };

    public interface ViewPageable {
        void onPagePause();

        void onPageResume();
    }

    public enum TabType {
        MYSERVICE(R.string.toolbar_title_myservice),
        RECOMMEND(R.string.toolbar_title_recommend);

        private final int titleRes;

        TabType(int titleRes) {
            this.titleRes = titleRes;
        }

        public String getTitle(Resources res) {
            return res.getString(this.titleRes);
        }

        static TabType byPosition(int position) {
            return values()[position];
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_service_main, container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        try {
            ViewPager2 viewPager2 = (ViewPager2) view.findViewById(R.id.vp_viewpager);
            this.pager = viewPager2;
            viewPager2.setAdapter(new PagerAdapter(this, this.tabFragments));
            this.pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.felicanetworks.mfm.main.view.views.ContentTabsFragment.2
                @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                public void onPageSelected(int position) {
                    try {
                        ContentTabsFragment.this.changeTabMenu(TabType.byPosition(position));
                    } catch (Exception e) {
                        ContentTabsFragment.this.notifyException(e);
                    }
                }
            });
            new TabLayoutMediator((TabLayout) view.findViewById(R.id.tl_tabs), this.pager, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.felicanetworks.mfm.main.view.views.ContentTabsFragment$$ExternalSyntheticLambda0
                /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
                @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
                public final void onConfigureTab(TabLayout.Tab tab, int i) {
                    this.f$0.m417xd89eb86a(tab, i);
                }
            }).attach();
        } catch (Exception e) {
            notifyException(e);
        }
    }

    /* JADX INFO: renamed from: lambda$onViewCreated$0$com-felicanetworks-mfm-main-view-views-ContentTabsFragment, reason: not valid java name */
    /* synthetic */ void m417xd89eb86a(TabLayout.Tab tab, int i) {
        tab.setText(TabType.byPosition(i).getTitle(getResources()));
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        if (getActivity() instanceof BaseActivity) {
            if (((BaseActivity) getActivity()).isReturnFromOtherScreen() || this.isFastBoot) {
                this.isFastBoot = false;
                initialPosition();
                return;
            }
            Structure structure = getStructure();
            if (structure instanceof CentralDrawStructure) {
                CentralDrawStructure centralDrawStructure = (CentralDrawStructure) structure;
                CentralDrawStructure.Page requestPage = centralDrawStructure.getRequestPage();
                centralDrawStructure.setRequestPage(null);
                if (requestPage != null) {
                    if (requestPage == CentralDrawStructure.Page.MY_SERVICE) {
                        if (getCurrentTab() != TabType.MYSERVICE) {
                            initialPosition();
                        }
                    } else {
                        if (requestPage != CentralDrawStructure.Page.RECOMMEND || getCurrentTab() == TabType.RECOMMEND) {
                            return;
                        }
                        initialPosition();
                    }
                }
            }
        }
    }

    private void initialPosition() {
        this.currentTab = null;
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.felicanetworks.mfm.main.view.views.ContentTabsFragment.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Structure structure = ContentTabsFragment.this.getStructure();
                    if (structure instanceof CentralDrawStructure) {
                        if (AnonymousClass4.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$CentralDrawStructure$Page[((CentralDrawStructure) structure).getFirstPage().ordinal()] == 1) {
                            ContentTabsFragment.this.setCurrentTab(TabType.RECOMMEND);
                            ContentTabsFragment.this.changeTabMenu(TabType.RECOMMEND);
                        } else {
                            ContentTabsFragment.this.setCurrentTab(TabType.MYSERVICE);
                            ContentTabsFragment.this.changeTabMenu(TabType.MYSERVICE);
                        }
                    }
                } catch (Exception e) {
                    ContentTabsFragment.this.notifyException(e);
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.views.ContentTabsFragment$4, reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$CentralDrawStructure$Page;

        static {
            int[] iArr = new int[CentralDrawStructure.Page.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$CentralDrawStructure$Page = iArr;
            try {
                iArr[CentralDrawStructure.Page.RECOMMEND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$CentralDrawStructure$Page[CentralDrawStructure.Page.MY_SERVICE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeTabMenu(TabType nextType) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        TabType tabType = this.currentTab;
        if (nextType != tabType) {
            ActivityResultCaller activityResultCaller = (Fragment) this.tabFragments.get(tabType);
            if (activityResultCaller instanceof ViewPageable) {
                ((ViewPageable) activityResultCaller).onPagePause();
            }
            ActivityResultCaller activityResultCaller2 = (Fragment) this.tabFragments.get(nextType);
            if (activityResultCaller2 instanceof ViewPageable) {
                ((ViewPageable) activityResultCaller2).onPageResume();
            }
            this.currentTab = nextType;
        }
        ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle(nextType.getTitle(getResources()));
    }

    public void setCurrentTab(TabType tab) {
        int iOrdinal = tab.ordinal();
        if (iOrdinal == 0) {
            this.pager.setCurrentItem(0);
        } else {
            if (iOrdinal != 1) {
                return;
            }
            this.pager.setCurrentItem(1);
        }
    }

    public TabType getCurrentTab() {
        if (this.pager.getCurrentItem() == 0) {
            return TabType.MYSERVICE;
        }
        return TabType.RECOMMEND;
    }

    private static class PagerAdapter extends FragmentStateAdapter {
        private final Map<TabType, Fragment> fragments;

        PagerAdapter(Fragment fragment, Map<TabType, Fragment> fragments) {
            super(fragment);
            this.fragments = fragments;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.fragments.size();
        }

        @Override // androidx.viewpager2.adapter.FragmentStateAdapter
        public Fragment createFragment(int position) {
            return (Fragment) Objects.requireNonNull(this.fragments.get(TabType.byPosition(position)));
        }
    }
}
