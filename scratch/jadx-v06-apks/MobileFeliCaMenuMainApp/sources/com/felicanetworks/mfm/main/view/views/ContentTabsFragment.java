package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewpager.widget.ViewPager;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.presenter.structure.CentralDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.view.activity.BaseActivity;
import com.google.android.material.tabs.TabLayout;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ContentTabsFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    private TabType currentTab;
    private ViewPager pager;
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

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
    }

    public enum TabType {
        MYSERVICE(R.string.toolbar_title_myservice),
        RECOMMEND(R.string.toolbar_title_recommend);

        private int titleRes;

        TabType(int i) {
            this.titleRes = i;
        }

        public String getTitle(Resources resources) {
            return resources.getString(this.titleRes);
        }

        static TabType byPosition(int i) {
            return values()[i];
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_service_main, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        try {
            ViewPager viewPager = (ViewPager) view.findViewById(R.id.vp_viewpager);
            this.pager = viewPager;
            viewPager.setAdapter(new PagerAdapter(getContext(), getChildFragmentManager(), this.tabFragments));
            this.pager.addOnPageChangeListener(this);
            ((TabLayout) view.findViewById(R.id.tl_tabs)).setupWithViewPager(this.pager);
        } catch (Exception e) {
            notifyException(e);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int i) {
        try {
            changeTabMenu(TabType.byPosition(i));
            invalidateFragmentMenus(i);
        } catch (Exception e) {
            notifyException(e);
        }
    }

    private void invalidateFragmentMenus(int i) {
        PagerAdapter pagerAdapter;
        if (getActivity() == null || (pagerAdapter = (PagerAdapter) this.pager.getAdapter()) == null) {
            return;
        }
        int i2 = 0;
        while (i2 < pagerAdapter.getCount()) {
            pagerAdapter.getItem(i2).setHasOptionsMenu(i2 == i);
            i2++;
        }
        getActivity().invalidateOptionsMenu();
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
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.felicanetworks.mfm.main.view.views.ContentTabsFragment.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Structure structure = ContentTabsFragment.this.getStructure();
                    if (structure instanceof CentralDrawStructure) {
                        if (AnonymousClass3.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$CentralDrawStructure$Page[((CentralDrawStructure) structure).getFirstPage().ordinal()] == 1) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public void changeTabMenu(TabType tabType) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        TabType tabType2 = this.currentTab;
        if (tabType != tabType2) {
            LifecycleOwner lifecycleOwner = (Fragment) this.tabFragments.get(tabType2);
            if (lifecycleOwner instanceof ViewPageable) {
                ((ViewPageable) lifecycleOwner).onPagePause();
            }
            LifecycleOwner lifecycleOwner2 = (Fragment) this.tabFragments.get(tabType);
            if (lifecycleOwner2 instanceof ViewPageable) {
                ((ViewPageable) lifecycleOwner2).onPageResume();
            }
            this.currentTab = tabType;
        }
        ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle(tabType.getTitle(getResources()));
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.views.ContentTabsFragment$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$CentralDrawStructure$Page;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$view$views$ContentTabsFragment$TabType;

        static {
            int[] iArr = new int[TabType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$view$views$ContentTabsFragment$TabType = iArr;
            try {
                iArr[TabType.MYSERVICE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$ContentTabsFragment$TabType[TabType.RECOMMEND.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[CentralDrawStructure.Page.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$CentralDrawStructure$Page = iArr2;
            try {
                iArr2[CentralDrawStructure.Page.RECOMMEND.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$CentralDrawStructure$Page[CentralDrawStructure.Page.MY_SERVICE.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public void setCurrentTab(TabType tabType) {
        int i = AnonymousClass3.$SwitchMap$com$felicanetworks$mfm$main$view$views$ContentTabsFragment$TabType[tabType.ordinal()];
        if (i == 1) {
            this.pager.setCurrentItem(0);
        } else {
            if (i != 2) {
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

    private static class PagerAdapter extends FragmentPagerAdapter {
        private final Context context;
        private final Map<TabType, Fragment> fragments;

        PagerAdapter(Context context, FragmentManager fragmentManager, Map<TabType, Fragment> map) {
            super(fragmentManager);
            this.context = context;
            this.fragments = map;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return this.fragments.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public CharSequence getPageTitle(int i) {
            return TabType.byPosition(i).getTitle(this.context.getResources());
        }

        @Override // androidx.fragment.app.FragmentPagerAdapter
        public Fragment getItem(int i) {
            return this.fragments.get(TabType.byPosition(i));
        }
    }
}
