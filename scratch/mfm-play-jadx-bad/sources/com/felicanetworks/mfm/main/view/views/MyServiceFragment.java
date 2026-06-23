package com.felicanetworks.mfm.main.view.views;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.core.graphics.Insets;
import androidx.core.view.MenuProvider;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.presenter.agent.BannerInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.LinkageAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceGroupInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent;
import com.felicanetworks.mfm.main.presenter.structure.CentralDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.PrimaryDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.view.activity.CentralActivity;
import com.felicanetworks.mfm.main.view.views.ContentTabsFragment;
import com.felicanetworks.mfm.main.view.views.list.MyServiceAdapter;
import com.felicanetworks.mfm.main.view.views.parts.UpdateButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
public class MyServiceFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, ContentTabsFragment.ViewPageable {
    private MyServiceAdapter adapter;
    SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView recycler;
    private CentralDrawStructure structure;
    private Insets mInsets = Insets.NONE;
    private boolean isForeground = false;
    private boolean isFastBoot = true;
    MyServiceMenuProvider mMenuProvider = null;
    private boolean isCacheUpdateComplete = false;

    public interface MyServiceMenuProvider extends MenuProvider {
        void updateEnd();

        void updateError();

        void updateStart();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewInflate = inflater.inflate(R.layout.fragment_my_service, container, false);
        Structure structure = getStructure();
        if (structure instanceof CentralDrawStructure) {
            this.structure = (CentralDrawStructure) structure;
            AnalysisManager.stamp(MfmStamp.Event.AUTO_DUMP_RESULT_MFI, this.structure);
            addMenuProvider();
            this.adapter = new MyServiceAdapter(this.structure, new MyServiceAdapter.AdapterClient() { // from class: com.felicanetworks.mfm.main.view.views.MyServiceFragment.1
                @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceAdapter.AdapterClient
                protected void onChangedItems(List<MyServiceGroupInfoAgent> groups) {
                    MyServiceFragment.this.structure.actChangedSort(groups);
                }

                @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.OnClickContentListener
                public void onClickCard(MyServiceGroupInfoAgent group, MyServiceInfoAgent service) {
                    if (service.isActiveService() || !service.hasMoreInformation()) {
                        MyServiceFragment.this.launchLinkage(service);
                    } else {
                        AnalysisManager.stamp(MfmStamp.Event.ACTION_SHOW_CARD_DETAIL_BACKGROUND, service.getId());
                        MyServiceFragment.this.structure.actCardDetail(group, service);
                    }
                }

                @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.OnClickContentListener
                public void onClickChangeCard(MyServiceGroupInfoAgent group) {
                    if (group.hasMoreInformation()) {
                        AnalysisManager.stamp(MfmStamp.Event.ACTION_SHOW_CARD_DETAIL_GROUP, group.getGroupId());
                        MyServiceFragment.this.structure.actCardDetail(group, null);
                    }
                }

                @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.OnClickContentListener
                public void onClickMoreInformation(MyServiceGroupInfoAgent group, MyServiceInfoAgent service) {
                    if (service.hasMoreInformation()) {
                        AnalysisManager.stamp(MfmStamp.Event.ACTION_SHOW_CARD_DETAIL_SERVICE, service.getId());
                        MyServiceFragment.this.structure.actCardDetail(group, service);
                    }
                }

                @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.OnClickContentListener
                public void onClickMfiLogin(MyServiceInfoAgent info) {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_RECOMMEND_LOGIN_ON_CARD, info);
                    MyServiceFragment.this.structure.actMfiLogin();
                }

                @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.OnClickContentListener
                public void onClickRegisterService(MyServiceInfoAgent info) {
                    MyServiceFragment.this.launchLinkage(info);
                }

                @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.OnClickContentListener
                public void onClickRegisterServiceClose(MyServiceInfoAgent info) {
                    MyServiceFragment.this.structure.actCloseService(info);
                    if (MyServiceFragment.this.adapter != null) {
                        MyServiceFragment.this.adapter.invalidateAdapter();
                    }
                }

                @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.OnClickContentListener
                public void onClickDeleteCardListConductor(MyServiceGroupInfoAgent group) {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_SHOW_DELETED_CARD_LIST, group.getGroupId());
                    MyServiceFragment.this.showSelectListDialog(group);
                }

                @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceHeaderViewHolder.OnClickHeaderListener
                public void onClickNfcOffBar() {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_NFC_OFF_WARNING, new Object[0]);
                    try {
                        MyServiceFragment myServiceFragment = MyServiceFragment.this;
                        myServiceFragment.startActivity(myServiceFragment.structure.getDefaultIntent(PrimaryDrawStructure.LinkType.RW_P2P_SETTING));
                    } catch (Exception unused) {
                        MyServiceFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                        AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                    }
                }

                @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceHeaderViewHolder.OnClickHeaderListener
                public void onClickMfiLoginBar() {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_RECOMMEND_LOGIN, new Object[0]);
                    MyServiceFragment.this.structure.actMfiLogin();
                }

                @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceHeaderViewHolder.OnClickHeaderListener
                public void onClickMfiLoginClose() {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_HIDE_RECOMMEND_LOGIN, new Object[0]);
                    MyServiceFragment.this.structure.actCloseMfiLoginConductor();
                }

                @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceHeaderViewHolder.OnClickHeaderListener
                public void onClickNoticeBar() {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_NEW_ARRIVALS, new Object[0]);
                    MyServiceFragment.this.structure.actNotice();
                }

                @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceFooterViewHolder.OnClickFooterListener
                public void onClickGoToRecommend() {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_SHOW_RECOMMEND, new Object[0]);
                    try {
                        ViewPager2 viewPager2 = (ViewPager2) ((FragmentActivity) Objects.requireNonNull(MyServiceFragment.this.getActivity())).findViewById(R.id.vp_viewpager);
                        if (viewPager2 == null || viewPager2.getCurrentItem() != 0) {
                            return;
                        }
                        viewPager2.setCurrentItem(1);
                    } catch (Exception e) {
                        MyServiceFragment.this.notifyException(e);
                    }
                }

                @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceFooterViewHolder.OnClickFooterListener
                public void onClickBanner(BannerInfoAgent banner) {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_BANNER_MYSERVICE, banner);
                }

                @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceFooterViewHolder.OnClickFooterListener
                public void onClickBannerError(Exception e) {
                    MyServiceFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                    AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                }
            });
            adjustViewToNavigationBar(viewInflate);
            if (this.structure.isCached()) {
                this.structure.actBackgroundUpdateMyService(new CentralDrawStructure.BackgroundUpdateMyServiceListener() { // from class: com.felicanetworks.mfm.main.view.views.MyServiceFragment.2
                    @Override // com.felicanetworks.mfm.main.presenter.structure.CentralDrawStructure.BackgroundUpdateMyServiceListener
                    public void onCompleted() {
                        if (!MyServiceFragment.this.isForeground) {
                            MyServiceFragment.this.isCacheUpdateComplete = true;
                        } else {
                            MyServiceFragment.this.mMenuProvider.updateEnd();
                            MyServiceFragment.this.adapter.update();
                        }
                    }

                    @Override // com.felicanetworks.mfm.main.presenter.structure.CentralDrawStructure.BackgroundUpdateMyServiceListener
                    public void onError() {
                        MyServiceFragment.this.mMenuProvider.updateError();
                        MyServiceFragment.this.isCacheUpdateComplete = false;
                    }
                });
            }
        }
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipelayout);
        this.mSwipeRefresh = swipeRefreshLayout;
        swipeRefreshLayout.setColorSchemeResources(R.color.color_category_red, R.color.color_green, R.color.color_blue, R.color.color_category_orange);
        this.mSwipeRefresh.setRefreshing(false);
        this.mSwipeRefresh.setOnRefreshListener(this);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.msl_myservice_list);
        this.recycler = recyclerView;
        recyclerView.setHasFixedSize(true);
        this.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recycler.setAdapter(this.adapter);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        MyServiceAdapter myServiceAdapter = this.adapter;
        if (myServiceAdapter != null) {
            myServiceAdapter.destroy();
        }
        this.recycler.setAdapter(null);
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        adjustViewToNavigationBar(getView());
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        MyServiceAdapter myServiceAdapter = this.adapter;
        if (myServiceAdapter != null) {
            myServiceAdapter.resume();
            if (this.isCacheUpdateComplete) {
                this.isCacheUpdateComplete = false;
                this.mMenuProvider.updateEnd();
                this.adapter.update();
            }
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
        MyServiceAdapter myServiceAdapter = this.adapter;
        if (myServiceAdapter != null) {
            myServiceAdapter.pause();
        }
        this.isForeground = false;
    }

    private void addMenuProvider() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        removeMenuProvider();
        if (this.mMenuProvider == null) {
            this.mMenuProvider = new MyServiceMenuProvider() { // from class: com.felicanetworks.mfm.main.view.views.MyServiceFragment.3
                public MenuItem _lock;
                public MenuItem _refresh;
                public MenuItem _refresh_anim;
                public UpdateButton _v;

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
                    menuInflater.inflate(R.menu.menu_myservice, menu);
                    MenuItem menuItemFindItem = menu.findItem(R.id.tool_lock);
                    this._lock = menuItemFindItem;
                    MyServiceFragment.this.updateLockMenu(menuItemFindItem);
                    this._refresh = menu.findItem(R.id.tool_refresh);
                    MenuItem menuItemFindItem2 = menu.findItem(R.id.tool_refresh_animation);
                    this._refresh_anim = menuItemFindItem2;
                    if (menuItemFindItem2 != null) {
                        UpdateButton updateButton = (UpdateButton) menuItemFindItem2.getActionView();
                        this._v = updateButton;
                        if (updateButton != null) {
                            updateButton.setUpdateButtonClickListener(new UpdateButton.UpdateButtonClickListener() { // from class: com.felicanetworks.mfm.main.view.views.MyServiceFragment.3.1
                                @Override // com.felicanetworks.mfm.main.view.views.parts.UpdateButton.UpdateButtonClickListener
                                public void onClick() {
                                    if (MyServiceFragment.this.structure.isUpdateCacheRunning()) {
                                        return;
                                    }
                                    AnalysisManager.stamp(MfmStamp.Event.ACTION_RECREATE_SERVICES, new Object[0]);
                                    MyServiceFragment.this.structure.actUpdate(false, true, true, true, true);
                                    MyServiceFragment.this.structure.updateErrorInitialization();
                                }
                            });
                        }
                        this._refresh_anim.setVisible(false);
                    }
                    if (!MyServiceFragment.this.structure.isUpdateCacheRunning()) {
                        if (MyServiceFragment.this.structure.isUpdateCacheError()) {
                            MenuItem menuItem = this._refresh;
                            if (menuItem != null && this._refresh_anim != null) {
                                menuItem.setVisible(false);
                                this._refresh_anim.setVisible(true);
                            }
                            UpdateButton updateButton2 = this._v;
                            if (updateButton2 != null) {
                                updateButton2.setError(true);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    updateStart();
                }

                @Override // androidx.core.view.MenuProvider
                public boolean onMenuItemSelected(MenuItem item) {
                    Exception exc;
                    boolean z;
                    boolean z2 = false;
                    try {
                        int itemId = item.getItemId();
                        if (itemId == R.id.tool_refresh) {
                            try {
                                AnalysisManager.stamp(MfmStamp.Event.ACTION_RECREATE_SERVICES, new Object[0]);
                                MyServiceFragment.this.structure.actUpdate(false, true, true, true, true);
                                z = true;
                            } catch (Exception e) {
                                exc = e;
                                z2 = true;
                                MyServiceFragment.this.notifyException(exc);
                                return z2;
                            }
                        } else {
                            z = false;
                        }
                        if (itemId != R.id.tool_lock) {
                            return z;
                        }
                        AnalysisManager.stamp(MfmStamp.Event.ACTION_LOCK_SETTING, new Object[0]);
                        if (Settings.DeviceType.GP == Settings.getDeviceType()) {
                            try {
                                MyServiceFragment.this.startActivity(MyServiceFragment.this.structure.getDefaultIntent(PrimaryDrawStructure.LinkType.SCREEN_LOCK_SETTING));
                                return true;
                            } catch (Exception unused) {
                                MyServiceFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                                return true;
                            }
                        }
                        if (Settings.DeviceType.PIXEL != Settings.getDeviceType() || Build.VERSION.SDK_INT < 29) {
                            MyServiceFragment.this.structure.actLock();
                            return true;
                        }
                        try {
                            MyServiceFragment.this.startActivity(MyServiceFragment.this.structure.getDefaultIntent(PrimaryDrawStructure.LinkType.SCREEN_LOCK_SETTING));
                            return true;
                        } catch (Exception unused2) {
                            MyServiceFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                            return true;
                        }
                    } catch (Exception e2) {
                        exc = e2;
                    }
                }

                @Override // com.felicanetworks.mfm.main.view.views.MyServiceFragment.MyServiceMenuProvider
                public void updateStart() {
                    UpdateButton updateButton = this._v;
                    if (updateButton != null) {
                        updateButton.startAnimation();
                    }
                    MenuItem menuItem = this._refresh;
                    if (menuItem != null && menuItem.isVisible()) {
                        this._refresh.setVisible(false);
                    }
                    MenuItem menuItem2 = this._refresh_anim;
                    if (menuItem2 == null || menuItem2.isVisible()) {
                        return;
                    }
                    this._refresh_anim.setVisible(true);
                }

                @Override // com.felicanetworks.mfm.main.view.views.MyServiceFragment.MyServiceMenuProvider
                public void updateEnd() {
                    UpdateButton updateButton = this._v;
                    if (updateButton != null) {
                        updateButton.stopAnimation();
                    }
                    MenuItem menuItem = this._refresh;
                    if (menuItem != null && !menuItem.isVisible()) {
                        this._refresh.setVisible(true);
                    }
                    MenuItem menuItem2 = this._refresh_anim;
                    if (menuItem2 != null && menuItem2.isVisible()) {
                        this._refresh_anim.setVisible(false);
                    }
                    MyServiceFragment.this.updateLockMenu(this._lock);
                }

                @Override // com.felicanetworks.mfm.main.view.views.MyServiceFragment.MyServiceMenuProvider
                public void updateError() {
                    UpdateButton updateButton = this._v;
                    if (updateButton != null) {
                        updateButton.stopAnimation();
                        this._v.setError(true);
                    }
                }
            };
        }
        activity.addMenuProvider(this.mMenuProvider, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void removeMenuProvider() {
        MyServiceMenuProvider myServiceMenuProvider;
        FragmentActivity activity = getActivity();
        if (activity == null || (myServiceMenuProvider = this.mMenuProvider) == null) {
            return;
        }
        activity.removeMenuProvider(myServiceMenuProvider);
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
            if (this.structure.isFelicaLock()) {
                lock.setIcon(R.drawable.ic_lock_lock_active);
                return;
            } else {
                lock.setIcon(R.drawable.ic_lock_unlock_active);
                return;
            }
        }
        if (this.structure.isScreenLock(getContext())) {
            lock.setIcon(R.drawable.header_lock);
        } else {
            lock.setIcon(R.drawable.header_unlock);
        }
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        try {
            AnalysisManager.stamp(MfmStamp.Event.ACTION_SWIPE_REFRESH_SERVICES, new Object[0]);
            if (this.structure.isUpdateCacheRunning()) {
                this.mSwipeRefresh.setRefreshing(false);
            } else {
                this.structure.actUpdate(false, true, true, true, true);
                this.structure.updateErrorInitialization();
            }
        } catch (Exception e) {
            notifyException(e);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.views.ContentTabsFragment.ViewPageable
    public void onPageResume() {
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_02_01, getStructure());
        Structure structure = getStructure();
        if ((structure instanceof CentralDrawStructure) && ((CentralDrawStructure) structure).isUpdateCacheRunning()) {
            this.mMenuProvider.updateStart();
        }
        MyServiceAdapter myServiceAdapter = this.adapter;
        if (myServiceAdapter == null) {
            return;
        }
        myServiceAdapter.resume();
    }

    @Override // com.felicanetworks.mfm.main.view.views.ContentTabsFragment.ViewPageable
    public void onPagePause() {
        this.mMenuProvider.updateEnd();
        MyServiceAdapter myServiceAdapter = this.adapter;
        if (myServiceAdapter == null) {
            return;
        }
        myServiceAdapter.pause();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchLinkage(MyServiceInfoAgent service) {
        LinkageAgent.LinkageInfo linkageInfo = service.getLinkageInfo();
        if (linkageInfo == null) {
            return;
        }
        if (linkageInfo.getError() == LinkageAgent.ErrorType.NOT_FOUND_ACTIVITY) {
            showWarningDialog(R.string.dlg_warning_not_found_activity);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_not_found_activity");
            return;
        }
        if (linkageInfo.getError() == LinkageAgent.ErrorType.NOT_FOUND_PACKAGE) {
            showWarningDialog(R.string.dlg_warning_signature_unmatched);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_signature_unmatched");
            return;
        }
        Intent intent = linkageInfo.getIntent();
        if (intent == null) {
            showWarningDialog(R.string.dlg_warning_signature_unmatched);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_signature_unmatched");
            return;
        }
        try {
            startActivity(intent);
            AnalysisManager.stamp(MfmStamp.Event.ACTION_START_MYSERVICE, service);
        } catch (Exception unused) {
            showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSelectListDialog(MyServiceGroupInfoAgent group) {
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_18_01, new Object[0]);
        SpSelectListDialogFragment spSelectListDialogFragmentCreateSpSelectListDialog = DialogFactory.createSpSelectListDialog();
        ArrayList arrayList = new ArrayList();
        for (MyServiceGroupInfoAgent.GroupState groupState : group.getGroupStates()) {
            if (groupState instanceof MyServiceGroupInfoAgent.DeleteCardState) {
                arrayList.add(((MyServiceGroupInfoAgent.DeleteCardState) groupState).getMyServiceInfoAgent());
            }
        }
        spSelectListDialogFragmentCreateSpSelectListDialog.setMyServiceInfoAgentList(arrayList);
        spSelectListDialogFragmentCreateSpSelectListDialog.setListItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.felicanetworks.mfm.main.view.views.MyServiceFragment.4
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyServiceInfoAgent myServiceInfoAgent = (MyServiceInfoAgent) ((ListView) parent).getItemAtPosition(position);
                if (myServiceInfoAgent != null) {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_SELECT_SERVICE, myServiceInfoAgent.getId());
                    MyServiceFragment.this.structure.actDeleteCardList(myServiceInfoAgent);
                }
            }
        });
        showNormalDialog(spSelectListDialogFragmentCreateSpSelectListDialog);
    }

    private void adjustViewToNavigationBar(View view) {
        if (view == null) {
            return;
        }
        ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() { // from class: com.felicanetworks.mfm.main.view.views.MyServiceFragment$$ExternalSyntheticLambda0
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                return this.f$0.m419xd89b07f0(view2, windowInsetsCompat);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$adjustViewToNavigationBar$0$com-felicanetworks-mfm-main-view-views-MyServiceFragment, reason: not valid java name */
    /* synthetic */ WindowInsetsCompat m419xd89b07f0(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
        if (this.mInsets.bottom != insets.bottom) {
            View viewFindViewById = view.findViewById(R.id.msl_myservice_list);
            viewFindViewById.setPadding(viewFindViewById.getPaddingLeft(), viewFindViewById.getPaddingTop(), viewFindViewById.getPaddingRight(), (viewFindViewById.getPaddingBottom() + insets.bottom) - this.mInsets.bottom);
        }
        this.mInsets = insets;
        return windowInsetsCompat;
    }
}
