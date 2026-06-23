package com.felicanetworks.mfm.main.view.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.presenter.agent.BannerInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.LinkageAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceGroupInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent;
import com.felicanetworks.mfm.main.presenter.structure.CentralDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.PrimaryDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.view.activity.CentralActivity;
import com.felicanetworks.mfm.main.view.views.ContentTabsFragment;
import com.felicanetworks.mfm.main.view.views.list.MyServiceAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
public class MyServiceFragment extends BaseFragment implements CentralFuncAgent.OrderBannerImageListener, SwipeRefreshLayout.OnRefreshListener, ContentTabsFragment.ViewPageable {
    private MyServiceAdapter adapter;
    private RecyclerView recycler;
    private CentralDrawStructure structure;
    private ImageView bannerImageView = null;
    private boolean isForeground = false;
    private Timer dismissBannerTimer = null;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private boolean isFastBoot = true;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_my_service, viewGroup, false);
        Structure structure = getStructure();
        if (!(structure instanceof CentralDrawStructure)) {
            return viewInflate;
        }
        this.structure = (CentralDrawStructure) structure;
        AnalysisManager.stamp(MfmStamp.Event.AUTO_DUMP_RESULT_MFI, this.structure);
        setHasOptionsMenu(false);
        this.adapter = new MyServiceAdapter(this.structure, new MyServiceAdapter.AdapterClient() { // from class: com.felicanetworks.mfm.main.view.views.MyServiceFragment.1
            @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceAdapter.AdapterClient
            protected void onChangedItems(List<MyServiceGroupInfoAgent> list) {
                MyServiceFragment.this.structure.actChangedSort(list);
            }

            @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.OnClickContentListener
            public void onClickCard(MyServiceGroupInfoAgent myServiceGroupInfoAgent, MyServiceInfoAgent myServiceInfoAgent) {
                if (myServiceInfoAgent.isFelicaPocketService()) {
                    MyServiceFragment.this.structure.actFpServiceList();
                } else if (myServiceInfoAgent.isActiveService() || !myServiceInfoAgent.hasMoreInformation()) {
                    MyServiceFragment.this.launchLinkage(myServiceInfoAgent);
                } else {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_SHOW_CARD_DETAIL_BACKGROUND, myServiceInfoAgent.getId());
                    MyServiceFragment.this.structure.actCardDetail(myServiceGroupInfoAgent, myServiceInfoAgent);
                }
            }

            @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.OnClickContentListener
            public void onClickChangeCard(MyServiceGroupInfoAgent myServiceGroupInfoAgent) {
                if (myServiceGroupInfoAgent.hasMoreInformation()) {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_SHOW_CARD_DETAIL_GROUP, myServiceGroupInfoAgent.getGroupId());
                    MyServiceFragment.this.structure.actCardDetail(myServiceGroupInfoAgent, null);
                }
            }

            @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.OnClickContentListener
            public void onClickMoreInformation(MyServiceGroupInfoAgent myServiceGroupInfoAgent, MyServiceInfoAgent myServiceInfoAgent) {
                if (myServiceInfoAgent.hasMoreInformation()) {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_SHOW_CARD_DETAIL_SERVICE, myServiceInfoAgent.getId());
                    MyServiceFragment.this.structure.actCardDetail(myServiceGroupInfoAgent, myServiceInfoAgent);
                }
            }

            @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.OnClickContentListener
            public void onClickMfiLogin(MyServiceInfoAgent myServiceInfoAgent) {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_RECOMMEND_LOGIN_ON_CARD, myServiceInfoAgent);
                MyServiceFragment.this.structure.actMfiLogin();
            }

            @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.OnClickContentListener
            public void onClickRegisterService(MyServiceInfoAgent myServiceInfoAgent) {
                MyServiceFragment.this.launchLinkage(myServiceInfoAgent);
            }

            @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.OnClickContentListener
            public void onClickRegisterServiceClose(MyServiceInfoAgent myServiceInfoAgent) {
                MyServiceFragment.this.structure.actCloseService(myServiceInfoAgent);
                if (MyServiceFragment.this.adapter != null) {
                    MyServiceFragment.this.adapter.invalidateAdapter();
                }
            }

            @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.OnClickContentListener
            public void onClickDeleteCardListConductor(MyServiceGroupInfoAgent myServiceGroupInfoAgent) {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_SHOW_DELETED_CARD_LIST, myServiceGroupInfoAgent.getGroupId());
                MyServiceFragment.this.showSelectListDialog(myServiceGroupInfoAgent);
            }

            @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceHeaderViewHolder.OnClickHeaderListener
            public void onClickNfcOffBar() {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_NFC_OFF_WARNING, new Object[0]);
                try {
                    MyServiceFragment.this.startActivity(MyServiceFragment.this.structure.getDefaultIntent(PrimaryDrawStructure.LinkType.RW_P2P_SETTING));
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
                    ViewPager viewPager = (ViewPager) ((FragmentActivity) Objects.requireNonNull(MyServiceFragment.this.getActivity())).findViewById(R.id.vp_viewpager);
                    if (viewPager == null || viewPager.getCurrentItem() != 0) {
                        return;
                    }
                    viewPager.setCurrentItem(1);
                } catch (Exception e) {
                    MyServiceFragment.this.notifyException(e);
                }
            }

            @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceFooterViewHolder.OnClickFooterListener
            public void onClickBanner(BannerInfoAgent bannerInfoAgent) {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_BANNER_MYSERVICE, bannerInfoAgent);
            }

            @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceFooterViewHolder.OnClickFooterListener
            public void onClickBannerError(Exception exc) {
                MyServiceFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
            }
        });
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_banner_image);
        this.bannerImageView = imageView;
        imageView.setVisibility(8);
        this.bannerImageView.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.MyServiceFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                try {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_JUMP_OSAIFU_VIA_BANNER, new Object[0]);
                    MyServiceFragment.this.startActivity(MyServiceFragment.this.structure.getDefaultIntent(PrimaryDrawStructure.LinkType.OSAIFULIFE_PLUS_DL_URL));
                } catch (Exception unused) {
                    MyServiceFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                    AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                }
            }
        });
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipelayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.color_category_red, R.color.color_green, R.color.color_blue, R.color.color_category_orange);
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setOnRefreshListener(this);
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

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        setHasOptionsMenu(true);
        MyServiceAdapter myServiceAdapter = this.adapter;
        if (myServiceAdapter != null) {
            myServiceAdapter.resume();
        }
        if (getActivity() instanceof CentralActivity) {
            CentralActivity centralActivity = (CentralActivity) getActivity();
            if (centralActivity.isReturnFromOtherScreen() && !this.isFastBoot) {
                this.structure.actUpdate(CentralDrawStructure.Page.RECOMMEND == this.structure.getFirstPage(), false);
                return;
            }
            if (centralActivity.isReturnFromOtherScreen() || this.isFastBoot) {
                centralActivity.setReturnFromOtherScreen(false);
                this.isFastBoot = false;
            }
            this.isForeground = true;
            this.structure.getCentralFunc().orderBannerImage(this);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        setHasOptionsMenu(false);
        MyServiceAdapter myServiceAdapter = this.adapter;
        if (myServiceAdapter != null) {
            myServiceAdapter.pause();
        }
        this.isForeground = false;
        this.structure.getCentralFunc().cancelBannerImage();
        Timer timer = this.dismissBannerTimer;
        if (timer != null) {
            timer.cancel();
            this.dismissBannerTimer = null;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_myservice, menu);
        updateLockMenu(menu);
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        try {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.tool_refresh) {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_RECREATE_SERVICES, new Object[0]);
                this.structure.actUpdate(false, true);
            }
            if (itemId == R.id.tool_lock) {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_LOCK_SETTING, new Object[0]);
                if (Settings.DeviceType.GP == Settings.getDeviceType()) {
                    try {
                        startActivity(this.structure.getDefaultIntent(PrimaryDrawStructure.LinkType.SCREEN_LOCK_SETTING));
                    } catch (Exception unused) {
                        showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                        AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                    }
                } else if (Settings.DeviceType.PIXEL == Settings.getDeviceType() && Build.VERSION.SDK_INT >= 29) {
                    try {
                        startActivity(this.structure.getDefaultIntent(PrimaryDrawStructure.LinkType.SCREEN_LOCK_SETTING));
                    } catch (Exception unused2) {
                        showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                        AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                    }
                } else {
                    this.structure.actLock();
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
            if (this.structure.isFelicaLock()) {
                menuItemFindItem.setIcon(R.drawable.ic_lock_lock_active);
                return;
            } else {
                menuItemFindItem.setIcon(R.drawable.ic_lock_unlock_active);
                return;
            }
        }
        if (this.structure.isScreenLock(getContext())) {
            menuItemFindItem.setIcon(R.drawable.header_lock);
        } else {
            menuItemFindItem.setIcon(R.drawable.header_unlock);
        }
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        try {
            try {
                setHasOptionsMenu(false);
                AnalysisManager.stamp(MfmStamp.Event.ACTION_SWIPE_REFRESH_SERVICES, new Object[0]);
                this.structure.actUpdate(false, true);
            } catch (Exception e) {
                notifyException(e);
            }
        } finally {
            setHasOptionsMenu(true);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.views.ContentTabsFragment.ViewPageable
    public void onPageResume() {
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_02_01, getStructure());
        MyServiceAdapter myServiceAdapter = this.adapter;
        if (myServiceAdapter == null) {
            return;
        }
        myServiceAdapter.resume();
    }

    @Override // com.felicanetworks.mfm.main.view.views.ContentTabsFragment.ViewPageable
    public void onPagePause() {
        MyServiceAdapter myServiceAdapter = this.adapter;
        if (myServiceAdapter == null) {
            return;
        }
        myServiceAdapter.pause();
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.OrderBannerImageListener
    public void onCompleteBannerImage(Bitmap bitmap) {
        if (this.isForeground && this.bannerImageView != null) {
            MfmStamp.Event event = MfmStamp.Event.AUTO_DUMP_RESULT_OSAIFU_BN_IMAGE;
            Object[] objArr = new Object[1];
            objArr[0] = Boolean.valueOf(bitmap != null);
            AnalysisManager.stamp(event, objArr);
            if (bitmap == null) {
                return;
            }
            this.bannerImageView.setImageBitmap(bitmap);
            this.bannerImageView.setVisibility(0);
            int iIntValue = ((Integer) Sg.getValue(Sg.Key.SETTING_OSAIFULIFE_PLUS_BANNER_HIDE_TIME)).intValue();
            if (this.dismissBannerTimer == null) {
                this.dismissBannerTimer = new Timer(false);
            }
            this.dismissBannerTimer.schedule(new DismissBannerTimerTask(), iIntValue);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchLinkage(MyServiceInfoAgent myServiceInfoAgent) {
        LinkageAgent.LinkageInfo linkageInfo = myServiceInfoAgent.getLinkageInfo();
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
            AnalysisManager.stamp(MfmStamp.Event.ACTION_START_MYSERVICE, myServiceInfoAgent);
        } catch (Exception unused) {
            showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSelectListDialog(MyServiceGroupInfoAgent myServiceGroupInfoAgent) {
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_18_01, new Object[0]);
        SpSelectListDialogFragment spSelectListDialogFragmentCreateSpSelectListDialog = DialogFactory.createSpSelectListDialog();
        ArrayList arrayList = new ArrayList();
        for (MyServiceGroupInfoAgent.GroupState groupState : myServiceGroupInfoAgent.getGroupStates()) {
            if (groupState instanceof MyServiceGroupInfoAgent.DeleteCardState) {
                arrayList.add(((MyServiceGroupInfoAgent.DeleteCardState) groupState).getMyServiceInfoAgent());
            }
        }
        spSelectListDialogFragmentCreateSpSelectListDialog.setMyServiceInfoAgentList(arrayList);
        spSelectListDialogFragmentCreateSpSelectListDialog.setListItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.felicanetworks.mfm.main.view.views.MyServiceFragment.3
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                MyServiceInfoAgent myServiceInfoAgent = (MyServiceInfoAgent) ((ListView) adapterView).getItemAtPosition(i);
                if (myServiceInfoAgent != null) {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_SELECT_SERVICE, myServiceInfoAgent.getId());
                    MyServiceFragment.this.structure.actDeleteCardList(myServiceInfoAgent);
                }
            }
        });
        showNormalDialog(spSelectListDialogFragmentCreateSpSelectListDialog);
    }

    private class DismissBannerTimerTask extends TimerTask {
        private DismissBannerTimerTask() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            MyServiceFragment.this.mHandler.post(new Runnable() { // from class: com.felicanetworks.mfm.main.view.views.MyServiceFragment.DismissBannerTimerTask.1
                @Override // java.lang.Runnable
                public void run() {
                    if (MyServiceFragment.this.bannerImageView != null) {
                        MyServiceFragment.this.bannerImageView.setVisibility(8);
                    }
                }
            });
        }
    }
}
