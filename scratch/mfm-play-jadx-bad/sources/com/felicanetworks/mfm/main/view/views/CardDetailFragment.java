package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.model.info.GeneralInfo;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.HistoryAgent;
import com.felicanetworks.mfm.main.presenter.agent.LinkageAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyCardInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceGroupInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.TransitPassInfoAgent;
import com.felicanetworks.mfm.main.presenter.internal.MfiPreference;
import com.felicanetworks.mfm.main.presenter.structure.CardDetailDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.view.activity.BaseActivity;
import com.felicanetworks.mfm.main.view.activity.CardDetailActivity;
import com.felicanetworks.mfm.main.view.views.CustomDialogFragment;
import com.felicanetworks.mfm.main.view.views.DepositDialogFragment;
import com.felicanetworks.mfm.main.view.views.parts.UpdateButtonCardDetail;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
public class CardDetailFragment extends BaseFragment implements View.OnClickListener {
    private static final int HISTORY_DEFAULT_COUNT = 3;
    CustomDialogFragment deleteConfirmDialog;
    DepositDialogFragment depositDialogFragment;
    private String focusedCardId;
    private String focusedServiceId;
    private LinearLayout mActiveView;
    private View mAddInfoIllegalView;
    private LinearLayout mBalanceLayout;
    private TextView mBalanceTv;
    private LinearLayout mCardDepositLayout;
    private ViewPager2 mCardImageView;
    private TextView mCardNameTv;
    private BannerLayoutCardDetail mCardSwitchView;
    private LinearLayout mDetailView;
    private Button mEnableBt;
    private ImageView mGroupIconIv;
    private MyServiceGroupInfoAgent mGroupInfo;
    private TextView mGroupNameTv;
    private LinearLayout mGroupView;
    private ListView mHistoryListView;
    private TextView mIdKeyTv;
    private TextView mIdValueTv;
    private LimitableIndicatorLayout mIndicatorView;
    private ListView mLinkAppListView;
    private LinearLayout mLinkAppView;
    private TextView mMoreCloseHistroyTextView;
    private LinearLayout mMoreHistroyButtonLayout;
    private GridView mOtherGridView;
    private LinearLayout mOtherInfoView;
    private LinearLayout mPointLayout;
    private TextView mPointTv;
    private TextView mProviderNameTv;
    private Button mRecoveryBt;
    private ListView mSpServerListView;
    private LinearLayout mSpendLayout;
    private LinearLayout mSpendLimitLayout;
    private TextView mSpendLimitTv;
    private TextView mSpendTv;
    private CardDetailDrawStructure mStructure;
    private LinearLayout mhistoryLayout;
    private LinearLayout midLayout;
    private ScrollView scrollView;
    private boolean mOnceSlideFlg = true;
    private int mCurCardPosition = 0;
    private List<MyCardInfoAgent> mMyCardInfoList = new ArrayList();
    private Boolean isMore = false;
    CardDetailMenuProvider mMenuProvider = null;
    private boolean isForeground = false;
    private BgUpdateType mBgUpdateType = BgUpdateType.NONE;

    public enum BgUpdateType {
        NONE,
        UPDATE,
        ERROR
    }

    public interface CardDetailMenuProvider extends MenuProvider {
        void updateEnd();

        void updateError();

        void updateStart();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewInflate = inflater.inflate(R.layout.fragment_card_detail, container, false);
        try {
            Structure structure = getStructure();
            if (!(structure instanceof CardDetailDrawStructure)) {
                return viewInflate;
            }
            CardDetailDrawStructure cardDetailDrawStructure = (CardDetailDrawStructure) structure;
            this.mStructure = cardDetailDrawStructure;
            this.focusedServiceId = cardDetailDrawStructure.getFocusSid();
            this.focusedCardId = this.mStructure.getFocusCid();
            initView(viewInflate);
            addMenuProvider();
            initProcess();
            initData(false);
            adjustViewToNavigationBar(this.scrollView, true);
        } catch (Exception e) {
            notifyException(e);
        }
        this.mStructure.getCardDetailFunc().registOrderAssetListener(new CardDetailFuncAgent.OrderUpdateCacheListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.1
            @Override // com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.OrderUpdateCacheListener
            public void onCompleted(MyServiceGroupInfoAgent groupInfo, CardDetailFuncAgent.CompleteState state) {
                if (groupInfo != null) {
                    CardDetailFragment.this.mBgUpdateType = BgUpdateType.UPDATE;
                    CardDetailFragment.this.mGroupInfo = groupInfo;
                } else {
                    CardDetailFragment.this.mBgUpdateType = BgUpdateType.ERROR;
                }
                if (CardDetailFragment.this.isForeground) {
                    CardDetailFragment.this.updateCacheInfo();
                }
            }

            @Override // com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.OrderUpdateCacheListener
            public void onError(ModelErrorInfo error) {
                CardDetailFragment.this.mBgUpdateType = BgUpdateType.ERROR;
                if (CardDetailFragment.this.isForeground) {
                    CardDetailFragment.this.updateCacheInfo();
                }
            }
        });
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mCardSwitchView.updateMetrics();
        adjustViewToNavigationBar(this.scrollView, true);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.isForeground = true;
        updateCacheInfo();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.isForeground = false;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.mStructure.getCardDetailFunc().unRegistOrderAssetListener();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.bt_enable /* 2131361919 */:
                    setCardOperationBtnEnable(false);
                    doEnableAction();
                    break;
                case R.id.bt_recovery /* 2131361922 */:
                    setCardOperationBtnEnable(false);
                    doRecoveryAction();
                    break;
                case R.id.ll_card_deposit /* 2131362140 */:
                    doDeleteConfirmAction();
                    break;
                case R.id.ll_more_history /* 2131362170 */:
                    if (this.isMore.booleanValue()) {
                        this.isMore = false;
                        this.mMoreCloseHistroyTextView.setText(getString(R.string.button_text_usage_history_detail_screen_see_more));
                    } else {
                        this.isMore = true;
                        this.mMoreCloseHistroyTextView.setText(getString(R.string.button_text_usage_history_detail_screen_see_close));
                    }
                    updateCardDetailView();
                    break;
            }
        } catch (Exception e) {
            notifyException(e);
        }
    }

    private void initView(View layout) {
        this.scrollView = (ScrollView) layout.findViewById(R.id.scrollView);
        this.mGroupView = (LinearLayout) layout.findViewById(R.id.ll_card_header);
        this.mGroupNameTv = (TextView) layout.findViewById(R.id.tv_card_grouping);
        this.mGroupIconIv = (ImageView) layout.findViewById(R.id.iv_card_grouping);
        this.mCardImageView = (ViewPager2) layout.findViewById(R.id.vp_card_detail);
        this.mIndicatorView = (LimitableIndicatorLayout) layout.findViewById(R.id.il_card_detail);
        this.mCardSwitchView = (BannerLayoutCardDetail) layout.findViewById(R.id.bl_card_detail);
        this.mDetailView = (LinearLayout) layout.findViewById(R.id.ll_card_detail_main);
        this.mCardNameTv = (TextView) layout.findViewById(R.id.tv_server_name);
        this.mProviderNameTv = (TextView) layout.findViewById(R.id.tv_provider_name);
        this.midLayout = (LinearLayout) layout.findViewById(R.id.ll_id_layout);
        this.mIdKeyTv = (TextView) layout.findViewById(R.id.tv_id_key);
        this.mIdValueTv = (TextView) layout.findViewById(R.id.tv_id_value);
        this.mBalanceLayout = (LinearLayout) layout.findViewById(R.id.ll_balance_layout);
        this.mBalanceTv = (TextView) layout.findViewById(R.id.tv_balance);
        this.mSpendLayout = (LinearLayout) layout.findViewById(R.id.ll_spend_layout);
        this.mSpendTv = (TextView) layout.findViewById(R.id.tv_spend);
        this.mPointLayout = (LinearLayout) layout.findViewById(R.id.ll_point_layout);
        this.mPointTv = (TextView) layout.findViewById(R.id.tv_point);
        this.mSpendLimitLayout = (LinearLayout) layout.findViewById(R.id.ll_spend_limit_layout);
        this.mSpendLimitTv = (TextView) layout.findViewById(R.id.tv_spend_limit);
        this.mActiveView = (LinearLayout) layout.findViewById(R.id.ll_active_layout);
        Button button = (Button) layout.findViewById(R.id.bt_enable);
        this.mEnableBt = button;
        button.setOnClickListener(this);
        Button button2 = (Button) layout.findViewById(R.id.bt_recovery);
        this.mRecoveryBt = button2;
        button2.setOnClickListener(this);
        this.mLinkAppView = (LinearLayout) layout.findViewById(R.id.ll_link_app_layout);
        this.mLinkAppListView = (ListView) layout.findViewById(R.id.lv_link_app_list);
        this.mAddInfoIllegalView = layout.findViewById(R.id.fl_additional_info_illegal);
        this.mSpServerListView = (ListView) layout.findViewById(R.id.lv_sp_server_list);
        this.mOtherInfoView = (LinearLayout) layout.findViewById(R.id.ll_other_layout);
        this.mOtherGridView = (GridView) layout.findViewById(R.id.gv_other_list);
        LinearLayout linearLayout = (LinearLayout) layout.findViewById(R.id.ll_card_deposit);
        this.mCardDepositLayout = linearLayout;
        linearLayout.setOnClickListener(this);
        this.mHistoryListView = (ListView) layout.findViewById(R.id.lv_history_list);
        this.mMoreHistroyButtonLayout = (LinearLayout) layout.findViewById(R.id.ll_more_history);
        this.mMoreCloseHistroyTextView = (TextView) layout.findViewById(R.id.tv_more_close_history);
        this.mMoreHistroyButtonLayout.setOnClickListener(this);
        this.mhistoryLayout = (LinearLayout) layout.findViewById(R.id.ll_history_list);
        ((Button) layout.findViewById(R.id.bt_issue)).setVisibility(8);
        ((Button) layout.findViewById(R.id.bt_unknown)).setVisibility(8);
        this.mCardSwitchView.setIndicator(this.mIndicatorView);
    }

    private void addMenuProvider() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        removeMenuProvider();
        if (this.mMenuProvider == null) {
            this.mMenuProvider = new AnonymousClass2();
        }
        activity.addMenuProvider(this.mMenuProvider, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.views.CardDetailFragment$2, reason: invalid class name */
    class AnonymousClass2 implements CardDetailMenuProvider {
        public MenuItem _refresh;
        public MenuItem _refresh_anim;
        public UpdateButtonCardDetail _v;

        @Override // androidx.core.view.MenuProvider
        public /* synthetic */ void onMenuClosed(Menu menu) {
            MenuProvider.CC.$default$onMenuClosed(this, menu);
        }

        @Override // androidx.core.view.MenuProvider
        public /* synthetic */ void onPrepareMenu(Menu menu) {
            MenuProvider.CC.$default$onPrepareMenu(this, menu);
        }

        AnonymousClass2() {
        }

        @Override // androidx.core.view.MenuProvider
        public void onCreateMenu(Menu menu, MenuInflater menuInflater) {
            menuInflater.inflate(R.menu.menu_carddetail, menu);
            this._refresh = menu.findItem(R.id.tool_card_detail_update);
            MenuItem menuItemFindItem = menu.findItem(R.id.tool_card_detail_update_animation);
            this._refresh_anim = menuItemFindItem;
            if (menuItemFindItem != null) {
                UpdateButtonCardDetail updateButtonCardDetail = (UpdateButtonCardDetail) menuItemFindItem.getActionView();
                this._v = updateButtonCardDetail;
                if (updateButtonCardDetail != null) {
                    updateButtonCardDetail.setUpdateButtonClickListener(new UpdateButtonCardDetail.UpdateButtonCardDetailClickListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.2.1
                        @Override // com.felicanetworks.mfm.main.view.views.parts.UpdateButtonCardDetail.UpdateButtonCardDetailClickListener
                        public void onClick() {
                            if (CardDetailFragment.this.mStructure.isRunning()) {
                                return;
                            }
                            CardDetailFragment.this.setCardOperationBtnEnable(false);
                            CardDetailFragment.this.reload();
                            CardDetailFragment.this.mStructure.updateErrorInitialization();
                            if (AnonymousClass2.this._refresh != null && !AnonymousClass2.this._refresh.isVisible()) {
                                AnonymousClass2.this._refresh.setVisible(true);
                            }
                            if (AnonymousClass2.this._refresh_anim == null || !AnonymousClass2.this._refresh_anim.isVisible()) {
                                return;
                            }
                            AnonymousClass2.this._refresh_anim.setVisible(false);
                        }
                    });
                }
                this._refresh_anim.setVisible(false);
            }
            if (!CardDetailFragment.this.mStructure.isRunning()) {
                if (CardDetailFragment.this.mStructure.isUpdateCacheError()) {
                    MenuItem menuItem = this._refresh;
                    if (menuItem != null && this._refresh_anim != null) {
                        menuItem.setVisible(false);
                        this._refresh_anim.setVisible(true);
                    }
                    UpdateButtonCardDetail updateButtonCardDetail2 = this._v;
                    if (updateButtonCardDetail2 != null) {
                        updateButtonCardDetail2.setError(true);
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
            Exception e;
            boolean z;
            try {
                if (item.getItemId() != R.id.tool_card_detail_update) {
                    return false;
                }
                z = true;
                try {
                    CardDetailFragment.this.setCardOperationBtnEnable(false);
                    CardDetailFragment.this.reload();
                    return true;
                } catch (Exception e2) {
                    e = e2;
                    CardDetailFragment.this.notifyException(e);
                    return z;
                }
            } catch (Exception e3) {
                e = e3;
                z = false;
            }
        }

        @Override // com.felicanetworks.mfm.main.view.views.CardDetailFragment.CardDetailMenuProvider
        public void updateStart() {
            UpdateButtonCardDetail updateButtonCardDetail = this._v;
            if (updateButtonCardDetail != null) {
                updateButtonCardDetail.startAnimation();
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

        @Override // com.felicanetworks.mfm.main.view.views.CardDetailFragment.CardDetailMenuProvider
        public void updateEnd() {
            UpdateButtonCardDetail updateButtonCardDetail = this._v;
            if (updateButtonCardDetail != null) {
                updateButtonCardDetail.stopAnimation();
            }
            MenuItem menuItem = this._refresh;
            if (menuItem != null && !menuItem.isVisible()) {
                this._refresh.setVisible(true);
            }
            MenuItem menuItem2 = this._refresh_anim;
            if (menuItem2 == null || !menuItem2.isVisible()) {
                return;
            }
            this._refresh_anim.setVisible(false);
        }

        @Override // com.felicanetworks.mfm.main.view.views.CardDetailFragment.CardDetailMenuProvider
        public void updateError() {
            UpdateButtonCardDetail updateButtonCardDetail = this._v;
            if (updateButtonCardDetail != null) {
                updateButtonCardDetail.stopAnimation();
                this._v.setError(true);
            }
        }
    }

    private void removeMenuProvider() {
        CardDetailMenuProvider cardDetailMenuProvider;
        FragmentActivity activity = getActivity();
        if (activity == null || (cardDetailMenuProvider = this.mMenuProvider) == null) {
            return;
        }
        activity.removeMenuProvider(cardDetailMenuProvider);
    }

    private void initProcess() {
        Animation animationLoadAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.translate_in);
        animationLoadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.3
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
                try {
                    CardDetailFragment.this.mGroupView.setVisibility(4);
                    CardDetailFragment.this.mIndicatorView.setVisibility(4);
                    CardDetailFragment.this.mDetailView.setVisibility(4);
                } catch (Exception e) {
                    CardDetailFragment.this.notifyException(e);
                }
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                try {
                    Animation animationLoadAnimation2 = AnimationUtils.loadAnimation(CardDetailFragment.this.getContext(), R.anim.fade_in);
                    CardDetailFragment.this.mGroupView.startAnimation(animationLoadAnimation2);
                    if (CardDetailFragment.this.mIndicatorView.getNoOfPages() > 1) {
                        CardDetailFragment.this.mIndicatorView.startAnimation(animationLoadAnimation2);
                    }
                    CardDetailFragment.this.mDetailView.startAnimation(animationLoadAnimation2);
                    CardDetailFragment.this.mDetailView.setVisibility(0);
                } catch (Exception e) {
                    CardDetailFragment.this.notifyException(e);
                }
            }
        });
        this.mCardSwitchView.startAnimation(animationLoadAnimation);
        this.mCardImageView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.4
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageScrollStateChanged(int state) {
                try {
                    if (state == 0) {
                        CardDetailFragment.this.mOnceSlideFlg = true;
                        CardDetailFragment.this.setCardOperationBtnEnable(true);
                        CardDetailFragment.this.mDetailView.setVisibility(0);
                        CardDetailFragment cardDetailFragment = CardDetailFragment.this;
                        cardDetailFragment.mCurCardPosition = cardDetailFragment.mCardImageView.getCurrentItem();
                        CardDetailFragment.this.updateCardDetailView();
                        return;
                    }
                    if (CardDetailFragment.this.mOnceSlideFlg) {
                        CardDetailFragment.this.mOnceSlideFlg = false;
                        if (CardDetailFragment.this.deleteConfirmDialog != null && CardDetailFragment.this.deleteConfirmDialog.getShowsDialog()) {
                            CardDetailFragment.this.deleteConfirmDialog.dismiss();
                            CardDetailFragment.this.deleteConfirmDialog = null;
                        }
                        if (CardDetailFragment.this.depositDialogFragment != null && CardDetailFragment.this.depositDialogFragment.getShowsDialog()) {
                            CardDetailFragment.this.depositDialogFragment.dismiss();
                            CardDetailFragment.this.depositDialogFragment = null;
                        }
                        CardDetailFragment.this.setCardOperationBtnEnable(false);
                        CardDetailFragment.this.mDetailView.setVisibility(8);
                    }
                } catch (Exception e) {
                    CardDetailFragment.this.notifyException(e);
                }
            }
        });
    }

    private void initData(final boolean update) {
        if (getActivity() == null) {
            return;
        }
        setCardOperationBtnEnable(false);
        final CustomDialogFragment customDialogFragmentShowProgressDialog = ((CardDetailActivity) getActivity()).showProgressDialog();
        customDialogFragmentShowProgressDialog.setCircleProgressEnable(true);
        final CardDetailFuncAgent cardDetailFunc = this.mStructure.getCardDetailFunc();
        cardDetailFunc.createDetailCardInfo(update, new CardDetailFuncAgent.CreateDetailCardInfoListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.5
            @Override // com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.CreateDetailCardInfoListener
            public void onComplete(MyServiceGroupInfoAgent groupInfo, CardDetailFuncAgent.CompleteState state) {
                StringBuilder sb;
                String errorCode;
                int i;
                String str;
                try {
                    try {
                        sb = new StringBuilder();
                        errorCode = "";
                        i = AnonymousClass16.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CardDetailFuncAgent$CompleteState$FelicaState[state.getFelicaState().ordinal()];
                    } catch (Exception e) {
                        CardDetailFragment.this.notifyException(e);
                    }
                    if (i == 1) {
                        if (!update) {
                            if (cardDetailFunc.isVanishedInputService(groupInfo)) {
                                sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_direct_card_detail_vanished_service));
                                str = "dlg_text_warning_direct_card_detail_vanished_service";
                            } else if (cardDetailFunc.isVanishedInputCard(groupInfo)) {
                                sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_direct_card_detail_vanished_card));
                                str = "dlg_text_warning_direct_card_detail_vanished_card";
                            }
                        }
                        if (groupInfo != null) {
                            CardDetailFragment.this.mGroupInfo = groupInfo;
                            CardDetailFragment.this.updateView();
                        } else {
                            CardDetailFragment.this.mStructure.actClose();
                        }
                        CardDetailFragment.this.setCardOperationBtnEnable(true);
                    }
                    if (i == 2) {
                        sb.append(CardDetailFragment.this.getString(R.string.dlg_warning_mfiservices_network_failed));
                        str = "dlg_warning_mfiservices_network_failed";
                    } else if (i == 3) {
                        sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_mfi_server_maintenance));
                        str = "dlg_text_warning_mfi_server_maintenance";
                    } else if (i != 4) {
                        str = null;
                    } else {
                        sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_get_card_detail_info));
                        str = "dlg_text_warning_get_card_detail_info";
                        if (!TextUtils.isEmpty(state.getErrorCode())) {
                            sb.append(CardDetailFragment.this.getString(R.string.dlg_error_code, state.getErrorCode()));
                            errorCode = state.getErrorCode();
                        }
                        if (!TextUtils.isEmpty(state.getWarCode())) {
                            sb.append(CardDetailFragment.this.getString(R.string.dlg_error_code_mfc, state.getWarCode()));
                        }
                    }
                    if (!TextUtils.isEmpty(sb)) {
                        CardDetailFragment.this.stampWithJudgeActivityState(MfmStamp.Event.SCREEN_WARNING, str);
                        CardDetailFragment.this.showWarningDialogWithButtonListener(sb.toString(), errorCode);
                    }
                } finally {
                    customDialogFragmentShowProgressDialog.dismiss();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateView() {
        Bitmap cardFaceImage;
        stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_17_01, this.mGroupInfo);
        this.scrollView.scrollTo(0, 0);
        this.mGroupNameTv.setText(this.mGroupInfo.getGroupTitle((Context) Objects.requireNonNull(getContext())));
        this.mGroupIconIv.setBackground(new BitmapDrawable(getContext().getResources(), this.mGroupInfo.getGroupIcon(getContext())));
        this.mMyCardInfoList.clear();
        this.mCardSwitchView.clearItem();
        for (final MyServiceInfoAgent myServiceInfoAgent : this.mGroupInfo.getServices()) {
            for (MyCardInfoAgent myCardInfoAgent : myServiceInfoAgent.getMyCardInfoAgentList()) {
                this.mMyCardInfoList.add(myCardInfoAgent);
                String id = myServiceInfoAgent.getId();
                final String cid = myCardInfoAgent.getCid();
                String str = id + cid;
                Map<Integer, String> cardImageUrls = myServiceInfoAgent.getCardImageUrls(cid);
                if (cardImageUrls.isEmpty() || cardImageUrls.get(Integer.valueOf(GeneralInfo.CARD_ART_PRIORITIZATION.DEFAULT.ordinal())) == null) {
                    cardFaceImage = myServiceInfoAgent.getCardFaceImage(cid);
                } else {
                    this.mStructure.getCardDetailFunc().getNotFoundImage(str, cardImageUrls, new CardDetailFuncAgent.NotFoundImageListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.6
                        @Override // com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.NotFoundImageListener
                        public void onComplete() {
                        }

                        @Override // com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.NotFoundImageListener
                        public void onGetImage(String id2, String url, Bitmap image) {
                            if (image != null) {
                                CardDetailFragment.this.mCardSwitchView.update(id2, null, image);
                            } else {
                                CardDetailFragment.this.mCardSwitchView.update(id2, myServiceInfoAgent.getCardName(cid), null);
                            }
                        }
                    });
                    cardFaceImage = null;
                }
                this.mCardSwitchView.addItem(myServiceInfoAgent.getCardName(cid), str, cardFaceImage, myCardInfoAgent.getCardUIStatus(myServiceInfoAgent.getId()));
            }
        }
        this.mCardSwitchView.setBanner(this.mCardImageView);
        int iFindInitIndex = findInitIndex(this.mMyCardInfoList, this.focusedServiceId, this.focusedCardId);
        this.mCurCardPosition = iFindInitIndex;
        this.mCardSwitchView.setCurrentItem(iFindInitIndex);
        this.mCardSwitchView.notifyDataSetChanged();
        updateCardDetailView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCacheInfo() {
        if (this.mBgUpdateType == BgUpdateType.UPDATE) {
            updateView();
            this.mMenuProvider.updateEnd();
        } else if (this.mBgUpdateType == BgUpdateType.ERROR) {
            this.mMenuProvider.updateError();
        }
        this.mBgUpdateType = BgUpdateType.NONE;
    }

    private void doEnableAction() {
        CardDetailFuncAgent cardDetailFunc = this.mStructure.getCardDetailFunc();
        final MyCardInfoAgent focusedCard = getFocusedCard();
        AnalysisManager.stamp(MfmStamp.Event.ACTION_ENABLE_CARD, focusedCard.getServiceId());
        final CustomDialogFragment customDialogFragmentShowProgressDialog = ((CardDetailActivity) Objects.requireNonNull(getActivity())).showProgressDialog();
        customDialogFragmentShowProgressDialog.setCircleProgressEnable(true);
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_17_02, new Object[0]);
        cardDetailFunc.enableCard(focusedCard.getCid(), focusedCard.getServiceId(), new CardDetailFuncAgent.EnableCardListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.7
            /* JADX WARN: Removed duplicated region for block: B:21:0x00c6 A[Catch: all -> 0x00db, Exception -> 0x00dd, TRY_LEAVE, TryCatch #0 {Exception -> 0x00dd, blocks: (B:2:0x0000, B:3:0x0015, B:19:0x00c0, B:21:0x00c6, B:5:0x001b, B:6:0x002b, B:7:0x003b, B:9:0x0053, B:10:0x006b, B:12:0x0075, B:14:0x008b, B:15:0x009a, B:16:0x00a9), top: B:30:0x0000, outer: #1 }] */
            @Override // com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.EnableCardListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onComplete(CardDetailFuncAgent.CompleteState state) {
                String str;
                try {
                    try {
                        StringBuilder sb = new StringBuilder();
                        String errorCode = "";
                        switch (AnonymousClass16.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CardDetailFuncAgent$CompleteState$FelicaState[state.getFelicaState().ordinal()]) {
                            case 1:
                                CardDetailFragment.this.reload(focusedCard.getServiceId(), focusedCard.getCid());
                                break;
                            case 2:
                                sb.append(CardDetailFragment.this.getString(R.string.dlg_warning_mfiservices_network_failed));
                                str = "dlg_warning_mfiservices_network_failed";
                                if (!TextUtils.isEmpty(sb)) {
                                    CardDetailFragment.this.stampWithJudgeActivityState(MfmStamp.Event.SCREEN_WARNING, str);
                                    CardDetailFragment.this.showWarningDialogWithButtonListener(sb.toString(), errorCode);
                                }
                                break;
                            case 3:
                                sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_mfi_server_maintenance));
                                str = "dlg_text_warning_mfi_server_maintenance";
                                if (!TextUtils.isEmpty(sb)) {
                                }
                                break;
                            case 4:
                                sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_enable_card));
                                if (!TextUtils.isEmpty(state.getErrorCode())) {
                                    sb.append(CardDetailFragment.this.getString(R.string.dlg_error_code, state.getErrorCode()));
                                    errorCode = state.getErrorCode();
                                }
                                if (!TextUtils.isEmpty(state.getWarCode())) {
                                    sb.append(CardDetailFragment.this.getString(R.string.dlg_error_code_mfc, state.getWarCode()));
                                }
                                str = "dlg_text_warning_enable_card";
                                if (!TextUtils.isEmpty(sb)) {
                                }
                                break;
                            case 5:
                                sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_chip_capacity_over));
                                str = "dlg_text_warning_chip_capacity_over";
                                if (!TextUtils.isEmpty(sb)) {
                                }
                                break;
                            case 6:
                                sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_unmanaged_card_detection));
                                str = "dlg_text_warning_unmanaged_card_detection";
                                if (!TextUtils.isEmpty(sb)) {
                                }
                                break;
                            default:
                                str = null;
                                if (!TextUtils.isEmpty(sb)) {
                                }
                                break;
                        }
                    } catch (Exception e) {
                        CardDetailFragment.this.notifyException(e);
                    }
                } finally {
                    customDialogFragmentShowProgressDialog.dismiss();
                }
            }
        });
    }

    private void doRecoveryAction() {
        CardDetailFuncAgent cardDetailFunc = this.mStructure.getCardDetailFunc();
        final MyCardInfoAgent focusedCard = getFocusedCard();
        AnalysisManager.stamp(MfmStamp.Event.ACTION_RECOVERY_CARD, focusedCard.getServiceId());
        final CustomDialogFragment customDialogFragmentShowProgressDialog = ((CardDetailActivity) Objects.requireNonNull(getActivity())).showProgressDialog();
        customDialogFragmentShowProgressDialog.setCircleProgressEnable(true);
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_17_02, new Object[0]);
        cardDetailFunc.recoveryCard(focusedCard.getCid(), focusedCard.getServiceId(), new CardDetailFuncAgent.RecoveryCardListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.8
            @Override // com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.RecoveryCardListener
            public void onComplete(CardDetailFuncAgent.CompleteState state) {
                String str;
                try {
                    try {
                        StringBuilder sb = new StringBuilder();
                        String errorCode = "";
                        int i = AnonymousClass16.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CardDetailFuncAgent$CompleteState$FelicaState[state.getFelicaState().ordinal()];
                        if (i == 1) {
                            CardDetailFragment.this.reload(focusedCard.getServiceId(), focusedCard.getCid());
                        } else {
                            if (i == 2) {
                                sb.append(CardDetailFragment.this.getString(R.string.dlg_warning_mfiservices_network_failed));
                                str = "dlg_warning_mfiservices_network_failed";
                            } else if (i == 3) {
                                sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_mfi_server_maintenance));
                                str = "dlg_text_warning_mfi_server_maintenance";
                            } else if (i != 4) {
                                str = null;
                            } else {
                                sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_recovery_card));
                                if (!TextUtils.isEmpty(state.getErrorCode())) {
                                    sb.append(CardDetailFragment.this.getString(R.string.dlg_error_code, state.getErrorCode()));
                                    errorCode = state.getErrorCode();
                                }
                                if (!TextUtils.isEmpty(state.getWarCode())) {
                                    sb.append(CardDetailFragment.this.getString(R.string.dlg_error_code_mfc, state.getWarCode()));
                                }
                                str = "dlg_text_warning_recovery_card";
                            }
                            if (!TextUtils.isEmpty(sb)) {
                                CardDetailFragment.this.stampWithJudgeActivityState(MfmStamp.Event.SCREEN_WARNING, str);
                                CardDetailFragment.this.showWarningDialogWithButtonListener(sb.toString(), errorCode);
                            }
                        }
                    } catch (Exception e) {
                        CardDetailFragment.this.notifyException(e);
                    }
                } finally {
                    customDialogFragmentShowProgressDialog.dismiss();
                }
            }
        });
    }

    private void doDeleteConfirmAction() {
        final MyCardInfoAgent focusedCard = getFocusedCard();
        AnalysisManager.stamp(MfmStamp.Event.ACTION_DELETE_CARD, focusedCard.getServiceId());
        if (this.mStructure.getCardDetailFunc().needWarningDelete(focusedCard.getCid())) {
            final CustomDialogFragment customDialogFragmentCreateSelectDialog = DialogFactory.createSelectDialog(((FragmentActivity) Objects.requireNonNull(getActivity())).getApplicationContext());
            this.deleteConfirmDialog = customDialogFragmentCreateSelectDialog;
            customDialogFragmentCreateSelectDialog.setTargetTag(BaseActivity.TAG_WARNING_DIALOG);
            customDialogFragmentCreateSelectDialog.setTitle(getString(R.string.dlg_title_warning_delete_main_card));
            customDialogFragmentCreateSelectDialog.setText(getString(R.string.dlg_text_warning_delete_main_card));
            customDialogFragmentCreateSelectDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.9
                @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                public boolean onClick() {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_EXECUTE_UPLOAD_MAIN_CARD, true, focusedCard.getServiceId());
                    try {
                        try {
                            CardDetailFragment.this.doConfirmModelChangeAction();
                        } catch (Exception e) {
                            CardDetailFragment.this.notifyException(e);
                        }
                        return true;
                    } finally {
                        CardDetailFragment.this.deleteConfirmDialog = null;
                    }
                }
            });
            customDialogFragmentCreateSelectDialog.setNegativeButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.10
                @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                public boolean onClick() {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_EXECUTE_UPLOAD_MAIN_CARD, false, focusedCard.getServiceId());
                    try {
                        try {
                            customDialogFragmentCreateSelectDialog.dismiss();
                        } catch (Exception e) {
                            CardDetailFragment.this.notifyException(e);
                        }
                        return true;
                    } finally {
                        CardDetailFragment.this.deleteConfirmDialog = null;
                    }
                }
            });
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_17_07, new Object[0]);
            showNormalDialog(customDialogFragmentCreateSelectDialog);
            return;
        }
        doConfirmModelChangeAction();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doConfirmModelChangeAction() {
        final MyCardInfoAgent focusedCard = getFocusedCard();
        final DepositDialogFragment depositDialogFragmentCreateSelectDepositDialog = DialogFactory.createSelectDepositDialog(((FragmentActivity) Objects.requireNonNull(getActivity())).getApplicationContext());
        this.depositDialogFragment = depositDialogFragmentCreateSelectDepositDialog;
        try {
            depositDialogFragmentCreateSelectDepositDialog.setAccount(MfiPreference.getInstance(getContext()).loadMfiAccountName());
        } catch (Exception e) {
            notifyException(e);
        }
        depositDialogFragmentCreateSelectDepositDialog.setPositiveButtonListener(new DepositDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.11
            @Override // com.felicanetworks.mfm.main.view.views.DepositDialogFragment.OnClickListener
            public boolean onClick() {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_EXECUTE_UPLOAD_MF_CARD, true, focusedCard.getServiceId());
                try {
                    try {
                        CardDetailFragment.this.doDeleteAction(focusedCard);
                    } catch (Exception e2) {
                        CardDetailFragment.this.notifyException(e2);
                    }
                    return true;
                } finally {
                    CardDetailFragment.this.depositDialogFragment = null;
                }
            }
        });
        depositDialogFragmentCreateSelectDepositDialog.setNegativeButtonListener(new DepositDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.12
            @Override // com.felicanetworks.mfm.main.view.views.DepositDialogFragment.OnClickListener
            public boolean onClick() {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_EXECUTE_UPLOAD_MF_CARD, false, focusedCard.getServiceId());
                try {
                    try {
                        depositDialogFragmentCreateSelectDepositDialog.dismiss();
                    } catch (Exception e2) {
                        CardDetailFragment.this.notifyException(e2);
                    }
                    return true;
                } finally {
                    CardDetailFragment.this.depositDialogFragment = null;
                }
            }
        });
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_17_06, new Object[0]);
        showNormalDialog(depositDialogFragmentCreateSelectDepositDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doDeleteAction(final MyCardInfoAgent myCardInfo) {
        CardDetailFuncAgent cardDetailFunc = this.mStructure.getCardDetailFunc();
        final CustomDialogFragment customDialogFragmentShowProgressDialog = ((CardDetailActivity) Objects.requireNonNull(getActivity())).showProgressDialog();
        customDialogFragmentShowProgressDialog.setCircleProgressEnable(true);
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_17_02, new Object[0]);
        cardDetailFunc.deleteCard(myCardInfo.getCid(), new CardDetailFuncAgent.DeleteCardListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.13
            @Override // com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.DeleteCardListener
            public void onComplete(CardDetailFuncAgent.CompleteState state) {
                String str;
                try {
                    try {
                        StringBuilder sb = new StringBuilder();
                        String errorCode = "";
                        int i = AnonymousClass16.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CardDetailFuncAgent$CompleteState$FelicaState[state.getFelicaState().ordinal()];
                        if (i == 1) {
                            CardDetailFragment.this.showDeleteCompleteDialog();
                        } else {
                            if (i == 2) {
                                sb.append(CardDetailFragment.this.getString(R.string.dlg_warning_mfiservices_network_failed));
                                str = "dlg_warning_mfiservices_network_failed";
                            } else if (i == 3) {
                                sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_mfi_server_maintenance));
                                str = "dlg_text_warning_mfi_server_maintenance";
                            } else if (i == 4) {
                                sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_delete_card));
                                if (!TextUtils.isEmpty(state.getErrorCode())) {
                                    sb.append(CardDetailFragment.this.getString(R.string.dlg_error_code, state.getErrorCode()));
                                    errorCode = state.getErrorCode();
                                }
                                if (!TextUtils.isEmpty(state.getWarCode())) {
                                    sb.append(CardDetailFragment.this.getString(R.string.dlg_error_code_mfc, state.getWarCode()));
                                }
                                str = "dlg_text_warning_delete_card";
                            } else if (i != 7) {
                                str = null;
                            } else {
                                sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_inside_transit_gate_error));
                                str = "dlg_text_warning_inside_transit_gate_error";
                            }
                            if (!TextUtils.isEmpty(sb)) {
                                CardDetailFragment.this.stampWithJudgeActivityState(MfmStamp.Event.SCREEN_WARNING, str);
                                CardDetailFragment.this.showWarningDialogWithButtonListener(sb.toString(), errorCode);
                            }
                        }
                    } catch (Exception e) {
                        CardDetailFragment.this.notifyException(e);
                    }
                } finally {
                    customDialogFragmentShowProgressDialog.dismiss();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCardDetailView() {
        MyCardInfoAgent focusedCard = getFocusedCard();
        MyServiceGroupInfoAgent myServiceGroupInfoAgent = this.mGroupInfo;
        if (myServiceGroupInfoAgent == null) {
            this.mStructure.actClose();
            return;
        }
        MyServiceInfoAgent myServiceInfoAgentFindService = myServiceGroupInfoAgent.findService(focusedCard.getServiceId());
        if (myServiceInfoAgentFindService == null) {
            this.mStructure.actClose();
            return;
        }
        this.mCardNameTv.setText(myServiceInfoAgentFindService.getCardName(focusedCard.getCid()));
        this.mProviderNameTv.setText(myServiceInfoAgentFindService.getContactName(focusedCard.getCid()));
        String iDiDisplayName = focusedCard.getIDiDisplayName();
        String iDiValue = focusedCard.getIDiValue();
        if (TextUtils.isEmpty(iDiDisplayName) && TextUtils.isEmpty(iDiValue)) {
            this.midLayout.setVisibility(8);
        } else {
            this.mIdKeyTv.setText(iDiDisplayName);
            this.mIdValueTv.setText(iDiValue);
            this.midLayout.setVisibility(0);
        }
        MyServiceInfoAgent myServiceInfoAgentFindService2 = this.mGroupInfo.findService(focusedCard.getServiceId());
        if (myServiceInfoAgentFindService2 != null && myServiceInfoAgentFindService2.hasPrepaidEmoney()) {
            long jLongValue = Double.valueOf(focusedCard.getBalance()).longValue();
            if (0 <= jLongValue && jLongValue <= 99999) {
                this.mBalanceTv.setText(NumberFormat.getNumberInstance().format(jLongValue));
            } else {
                this.mBalanceTv.setText(R.string.warning_value_nothing);
            }
            this.mBalanceLayout.setVisibility(0);
            this.mSpendLayout.setVisibility(8);
        } else {
            String availableCredit = focusedCard.getAvailableCredit();
            if (TextUtils.isEmpty(availableCredit)) {
                this.mSpendLayout.setVisibility(8);
            } else {
                this.mSpendTv.setText(availableCredit);
                this.mSpendLayout.setVisibility(0);
            }
        }
        String validPoint = focusedCard.getValidPoint();
        if (TextUtils.isEmpty(validPoint)) {
            this.mPointLayout.setVisibility(8);
            String creditLimit = focusedCard.getCreditLimit();
            if (TextUtils.isEmpty(creditLimit)) {
                this.mSpendLimitLayout.setVisibility(8);
            } else {
                this.mSpendLimitTv.setText(creditLimit);
                this.mSpendLimitLayout.setVisibility(0);
            }
        } else {
            this.mPointTv.setText(validPoint);
            this.mPointLayout.setVisibility(0);
            this.mSpendLimitLayout.setVisibility(8);
        }
        setCardStatus(focusedCard.getCardUIStatus(myServiceInfoAgentFindService.getId()));
        setCardDeposit(focusedCard.isShowCardDeposit(myServiceInfoAgentFindService.getId()));
        if (focusedCard.isAdditionalInfoIllegal()) {
            this.mAddInfoIllegalView.setVisibility(0);
        } else {
            this.mAddInfoIllegalView.setVisibility(8);
        }
        List<TransitPassInfoAgent> transitPassInfoList = focusedCard.getTransitPassInfoList();
        if (transitPassInfoList.isEmpty()) {
            this.mSpServerListView.setVisibility(8);
        } else {
            TransitPassInfoAdapter transitPassInfoAdapter = new TransitPassInfoAdapter(transitPassInfoList);
            this.mSpServerListView.setVisibility(0);
            this.mSpServerListView.setAdapter((ListAdapter) transitPassInfoAdapter);
        }
        List<LinkageAgent> linkageAppList = myServiceInfoAgentFindService.getLinkageAppList(focusedCard.getCid());
        if (linkageAppList.isEmpty()) {
            this.mLinkAppView.setVisibility(8);
        } else {
            this.mLinkAppView.setVisibility(0);
            this.mLinkAppListView.setAdapter((ListAdapter) new LinkAppItemAdapter(linkageAppList));
        }
        this.mHistoryListView.setAdapter((ListAdapter) new CardDetailHistoryAdapter(getContext(), R.layout.list_item_history, focusedCard.getHistory(), this.isMore));
        if (focusedCard.getHistory().size() == 0) {
            this.mhistoryLayout.setVisibility(8);
        } else {
            this.mhistoryLayout.setVisibility(0);
            if (focusedCard.getHistory().size() <= 3) {
                this.mMoreHistroyButtonLayout.setVisibility(8);
            } else {
                this.mMoreHistroyButtonLayout.setVisibility(0);
            }
        }
        List<LinkageAgent> linkageOtherList = myServiceInfoAgentFindService.getLinkageOtherList(focusedCard.getCid());
        if (linkageOtherList.size() >= 1) {
            this.mOtherInfoView.setVisibility(0);
        } else {
            this.mOtherInfoView.setVisibility(8);
        }
        this.mOtherGridView.setAdapter((ListAdapter) new OtherItemAdapter(getContext(), linkageOtherList));
    }

    private void setCardStatus(MyCardInfoAgent.CardStatusForCardDetailUI status) {
        this.mActiveView.setVisibility(8);
        this.mEnableBt.setVisibility(8);
        this.mRecoveryBt.setVisibility(8);
        if (MyCardInfoAgent.CardStatusForCardDetailUI.ACTIVE == status) {
            this.mActiveView.setVisibility(0);
        } else if (MyCardInfoAgent.CardStatusForCardDetailUI.INACTIVE == status) {
            this.mEnableBt.setVisibility(0);
        } else if (MyCardInfoAgent.CardStatusForCardDetailUI.RECOVERY == status) {
            this.mRecoveryBt.setVisibility(0);
        }
    }

    private void setCardDeposit(boolean show) {
        this.mCardDepositLayout.setVisibility(8);
        if (show) {
            this.mCardDepositLayout.setVisibility(0);
        }
    }

    private class TransitPassInfoAdapter extends BaseAdapter {
        List<TransitPassInfoAgent> transitPassInfoAgentList;

        @Override // android.widget.Adapter
        public Object getItem(int position) {
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            TextView mAdditionalInfoTv;
            TextView mCommuterPassNameTv;
            TextView mIssuerNameTv;
            ScrollListView mOptionalInfoListView;
            TextView mSection1FromTv;
            View mSection1Layout;
            TextView mSection1ToTv;
            TextView mSection2FromTv;
            View mSection2Layout;
            TextView mSection2ToTv;
            TextView mTermOfValidityEndTv;
            TextView mTermOfValidityExtensionTv;
            TextView mTermOfValidityKeyTv;
            View mTermOfValidityLayout;
            TextView mTermOfValiditySeparate;
            TextView mTermOfValidityStartTv;
            TextView mTransitTitle;
            TextView mViaTv;

            private ViewHolder() {
            }
        }

        TransitPassInfoAdapter(List<TransitPassInfoAgent> transitPassInfoAgents) {
            this.transitPassInfoAgentList = transitPassInfoAgents;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.transitPassInfoAgentList.size();
        }

        @Override // android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            try {
                if (convertView == null) {
                    convertView = LayoutInflater.from(CardDetailFragment.this.getContext()).inflate(R.layout.list_item_sp_server, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.mTransitTitle = (TextView) convertView.findViewById(R.id.tv_transit_title);
                    viewHolder.mCommuterPassNameTv = (TextView) convertView.findViewById(R.id.tv_commuter_pass_name);
                    viewHolder.mSection1FromTv = (TextView) convertView.findViewById(R.id.tv_section1_from);
                    viewHolder.mSection1ToTv = (TextView) convertView.findViewById(R.id.tv_section1_to);
                    viewHolder.mSection1Layout = convertView.findViewById(R.id.ll_section1_layout);
                    viewHolder.mSection2Layout = convertView.findViewById(R.id.ll_section2_layout);
                    viewHolder.mSection2FromTv = (TextView) convertView.findViewById(R.id.tv_section2_from);
                    viewHolder.mSection2ToTv = (TextView) convertView.findViewById(R.id.tv_section2_to);
                    viewHolder.mAdditionalInfoTv = (TextView) convertView.findViewById(R.id.tv_additional_info);
                    viewHolder.mViaTv = (TextView) convertView.findViewById(R.id.tv_transit_via);
                    viewHolder.mIssuerNameTv = (TextView) convertView.findViewById(R.id.tv_issuer_name);
                    viewHolder.mTermOfValidityLayout = convertView.findViewById(R.id.ll_term_of_validity_layout);
                    viewHolder.mTermOfValidityKeyTv = (TextView) convertView.findViewById(R.id.tv_term_of_validity_key);
                    viewHolder.mTermOfValidityStartTv = (TextView) convertView.findViewById(R.id.tv_term_of_validity_start);
                    viewHolder.mTermOfValiditySeparate = (TextView) convertView.findViewById(R.id.tv_term_of_validity_separate);
                    viewHolder.mTermOfValidityEndTv = (TextView) convertView.findViewById(R.id.tv_term_of_validity_end);
                    viewHolder.mTermOfValidityExtensionTv = (TextView) convertView.findViewById(R.id.tv_term_of_validity_extension);
                    viewHolder.mOptionalInfoListView = (ScrollListView) convertView.findViewById(R.id.lv_optional_info_view);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                updateListItem(viewHolder, position);
                return convertView;
            } catch (Exception e) {
                CardDetailFragment.this.notifyException(e);
                return convertView;
            }
        }

        private void updateListItem(ViewHolder viewHolder, int position) {
            String transitPassName = this.transitPassInfoAgentList.get(position).getTransitPassName();
            String category = this.transitPassInfoAgentList.get(position).getCategory();
            CardDetailFragment.this.TextViewShow(viewHolder.mTransitTitle, transitPassName + " " + category);
            CardDetailFragment.this.TextViewShow(viewHolder.mCommuterPassNameTv, this.transitPassInfoAgentList.get(position).getCommuterPassName());
            String section1From = this.transitPassInfoAgentList.get(position).getSection1From();
            String section1To = this.transitPassInfoAgentList.get(position).getSection1To();
            if (section1From == null && section1To == null) {
                viewHolder.mSection1Layout.setVisibility(8);
            } else {
                viewHolder.mSection1Layout.setVisibility(0);
                CardDetailFragment.this.TextViewShow(viewHolder.mSection1FromTv, section1From);
                CardDetailFragment.this.TextViewShow(viewHolder.mSection1ToTv, section1To);
            }
            String section2From = this.transitPassInfoAgentList.get(position).getSection2From();
            String section2To = this.transitPassInfoAgentList.get(position).getSection2To();
            if (section2From == null && section2To == null) {
                viewHolder.mSection2Layout.setVisibility(8);
            } else {
                viewHolder.mSection2Layout.setVisibility(0);
                CardDetailFragment.this.TextViewShow(viewHolder.mSection2FromTv, section2From);
                CardDetailFragment.this.TextViewShow(viewHolder.mSection2ToTv, section2To);
            }
            CardDetailFragment.this.TextViewShow(viewHolder.mAdditionalInfoTv, this.transitPassInfoAgentList.get(position).getAdditionalInformation());
            CardDetailFragment.this.TextViewShow(viewHolder.mViaTv, this.transitPassInfoAgentList.get(position).getVia());
            CardDetailFragment.this.TextViewShow(viewHolder.mIssuerNameTv, this.transitPassInfoAgentList.get(position).getIssuerName());
            String termOfValidityKey = this.transitPassInfoAgentList.get(position).getTermOfValidityKey();
            String termOfValidityStart = this.transitPassInfoAgentList.get(position).getTermOfValidityStart();
            String termOfValidityEnd = this.transitPassInfoAgentList.get(position).getTermOfValidityEnd();
            boolean termOfValidityExtension = this.transitPassInfoAgentList.get(position).getTermOfValidityExtension();
            if (termOfValidityKey == null && termOfValidityStart == null && termOfValidityEnd == null && !termOfValidityExtension) {
                viewHolder.mTermOfValidityLayout.setVisibility(8);
            } else {
                viewHolder.mTermOfValidityLayout.setVisibility(0);
                CardDetailFragment.this.TextViewShow(viewHolder.mTermOfValidityKeyTv, termOfValidityKey);
                CardDetailFragment.this.TextViewShow(viewHolder.mTermOfValidityStartTv, termOfValidityStart);
                CardDetailFragment.this.TextViewShow(viewHolder.mTermOfValidityEndTv, termOfValidityEnd);
                String string = "";
                if (termOfValidityStart != null || termOfValidityEnd != null) {
                    CardDetailFragment.this.TextViewShow(viewHolder.mTermOfValiditySeparate, CardDetailFragment.this.getString(R.string.card_item_label_train_detail_screen_period_mark));
                } else {
                    CardDetailFragment.this.TextViewShow(viewHolder.mTermOfValiditySeparate, "");
                }
                CardDetailFragment cardDetailFragment = CardDetailFragment.this;
                TextView textView = viewHolder.mTermOfValidityExtensionTv;
                if (termOfValidityExtension) {
                    string = CardDetailFragment.this.getString(R.string.card_item_label_detail_screen_continuity);
                }
                cardDetailFragment.TextViewShow(textView, string);
            }
            List<Map<String, String>> optionalInfoList = this.transitPassInfoAgentList.get(position).getOptionalInfoList();
            ArrayList arrayList = new ArrayList();
            for (Map<String, String> map : optionalInfoList) {
                if (map.get(TransitPassInfoAgent.OPTIONAL_INFO_KEY) != null || map.get("value") != null) {
                    arrayList.add(map);
                }
            }
            if (arrayList.size() > 0) {
                viewHolder.mOptionalInfoListView.setAdapter((ListAdapter) new SimpleAdapter(CardDetailFragment.this.getContext(), arrayList, R.layout.list_item_optional_info, new String[]{TransitPassInfoAgent.OPTIONAL_INFO_KEY, "value"}, new int[]{R.id.tv_optional_info_key, R.id.tv_optional_info_value}));
                viewHolder.mOptionalInfoListView.setVisibility(0);
                return;
            }
            viewHolder.mOptionalInfoListView.setVisibility(8);
        }
    }

    private class LinkAppItemAdapter extends BaseAdapter {
        List<LinkageAgent> mLinkageAgentList;

        @Override // android.widget.Adapter
        public Object getItem(int position) {
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            ImageView mLinkAppIconIv;
            TextView mLinkAppNameTv;

            private ViewHolder() {
            }
        }

        LinkAppItemAdapter(List<LinkageAgent> linkageAgentList) {
            this.mLinkageAgentList = linkageAgentList;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.mLinkageAgentList.size();
        }

        @Override // android.widget.Adapter
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            try {
                if (convertView == null) {
                    convertView = LayoutInflater.from(CardDetailFragment.this.getContext()).inflate(R.layout.list_item_link_app, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.mLinkAppIconIv = (ImageView) convertView.findViewById(R.id.iv_link_app_icon);
                    viewHolder.mLinkAppNameTv = (TextView) convertView.findViewById(R.id.tv_link_app_name);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                final LinkageAgent linkageAgent = this.mLinkageAgentList.get(position);
                CardDetailFragment.this.TextViewShow(viewHolder.mLinkAppNameTv, linkageAgent.getLinkageName());
                if (linkageAgent.getAppIcon() != null) {
                    CardDetailFragment.this.ImageViewShow(viewHolder.mLinkAppIconIv, linkageAgent.getAppIcon());
                } else {
                    CardDetailFragment.this.getFocusedCard().orderIconImg(linkageAgent.getAppIconUrl(), new MyCardInfoAgent.OrderImgListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.LinkAppItemAdapter.1
                        @Override // com.felicanetworks.mfm.main.presenter.agent.MyCardInfoAgent.OrderImgListener
                        public void onComplete(Bitmap data) {
                            try {
                                if (CardDetailFragment.this.getActivity() == null || CardDetailFragment.this.getActivity().isFinishing() || data == null) {
                                    return;
                                }
                                CardDetailFragment.this.ImageViewShow(viewHolder.mLinkAppIconIv, data);
                            } catch (Exception e) {
                                CardDetailFragment.this.notifyException(e);
                            }
                        }
                    });
                }
                convertView.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.LinkAppItemAdapter.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        Intent intent;
                        try {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_LINKAGE_APP, Integer.valueOf(position + 1));
                            LinkageAgent.LinkageInfo linkageInfo = linkageAgent.getLinkageInfo();
                            if (linkageInfo.getError() == LinkageAgent.ErrorType.NOT_FOUND_ACTIVITY) {
                                CardDetailFragment.this.showWarningDialog(R.string.dlg_warning_not_found_activity);
                                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_not_found_activity");
                            } else if (linkageInfo.getError() == LinkageAgent.ErrorType.NOT_FOUND_PACKAGE || (intent = linkageInfo.getIntent()) == null) {
                                CardDetailFragment.this.showWarningDialog(R.string.dlg_warning_signature_unmatched);
                                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_signature_unmatched");
                            } else {
                                try {
                                    CardDetailFragment.this.startActivity(intent);
                                } catch (Exception unused) {
                                    CardDetailFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                                    AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                                }
                            }
                        } catch (Exception e) {
                            CardDetailFragment.this.notifyException(e);
                        }
                    }
                });
                return convertView;
            } catch (Exception e) {
                CardDetailFragment.this.notifyException(e);
                return convertView;
            }
        }
    }

    private class CardDetailHistoryAdapter extends BaseAdapter {
        static final int SHOW_MIN_COUNT = 3;
        private List<HistoryAgent> mHisoryList;
        private LayoutInflater mInflater;
        private Boolean mIsMore;
        private int mLayoutId;

        @Override // android.widget.Adapter
        public long getItemId(int position) {
            return position;
        }

        class ViewItem {
            TextView mDate;
            TextView mMoney;
            TextView mPlus;
            TextView mType;

            ViewItem() {
            }
        }

        CardDetailHistoryAdapter(Context context, int itemLayoutId, List<HistoryAgent> list, Boolean isMore) {
            this.mInflater = LayoutInflater.from(context);
            this.mLayoutId = itemLayoutId;
            this.mHisoryList = list;
            this.mIsMore = isMore;
        }

        @Override // android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewItem viewItem;
            String string;
            if (convertView == null) {
                convertView = this.mInflater.inflate(this.mLayoutId, (ViewGroup) null);
                viewItem = new ViewItem();
                viewItem.mDate = (TextView) convertView.findViewById(R.id.tv_history_date);
                viewItem.mType = (TextView) convertView.findViewById(R.id.tv_history_type);
                viewItem.mMoney = (TextView) convertView.findViewById(R.id.tv_use_money);
                viewItem.mPlus = (TextView) convertView.findViewById(R.id.tv_use_money_plus);
                convertView.setTag(viewItem);
            } else {
                viewItem = (ViewItem) convertView.getTag();
            }
            viewItem.mDate.setText(this.mHisoryList.get(position).getDate());
            CardDetailFragment.this.getString(R.string.card_item_text_usage_history_detail_screen_use_amt);
            int i = AnonymousClass16.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$HistoryAgent$UseType[this.mHisoryList.get(position).getUseType().ordinal()];
            if (i == 1) {
                string = CardDetailFragment.this.getString(R.string.card_item_text_usage_history_detail_screen_charge);
                CardDetailFragment.this.getString(R.string.card_item_text_usage_history_detail_screen_charge_amt);
            } else if (i == 2) {
                string = CardDetailFragment.this.getString(R.string.card_item_text_usage_history_detail_screen_payment);
            } else if (i == 3) {
                string = CardDetailFragment.this.getString(R.string.card_item_text_usage_history_detail_screen_traffic);
            } else {
                string = i != 4 ? "" : CardDetailFragment.this.getString(R.string.card_item_text_usage_history_detail_screen_other);
            }
            viewItem.mType.setText(string);
            viewItem.mMoney.setText(NumberFormat.getNumberInstance().format(this.mHisoryList.get(position).getMoney()));
            if (this.mHisoryList.get(position).getIsPlus()) {
                viewItem.mPlus.setVisibility(0);
            } else {
                viewItem.mPlus.setVisibility(8);
            }
            convertView.setLayoutParams(new LinearLayout.LayoutParams(-1, 120));
            return convertView;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            if (this.mIsMore.booleanValue()) {
                return this.mHisoryList.size();
            }
            if (3 > this.mHisoryList.size()) {
                return this.mHisoryList.size();
            }
            return 3;
        }

        @Override // android.widget.Adapter
        public Object getItem(int position) {
            return Integer.valueOf(position);
        }
    }

    private class OtherItemAdapter extends BaseAdapter {
        private final List<LinkageAgent> linkageList;
        private final LayoutInflater mInflater;

        @Override // android.widget.Adapter
        public Object getItem(int position) {
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            TextView mText;

            private ViewHolder() {
            }
        }

        OtherItemAdapter(Context context, List<LinkageAgent> linkageList) {
            this.mInflater = LayoutInflater.from(context);
            this.linkageList = linkageList;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.linkageList.size();
        }

        @Override // android.widget.Adapter
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            try {
                if (convertView == null) {
                    convertView = this.mInflater.inflate(R.layout.list_item_other, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.mText = (TextView) convertView.findViewById(R.id.tv_other);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                convertView.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.OtherItemAdapter.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        try {
                            OtherItemAdapter.this.doOtherAction(position);
                        } catch (Exception unused) {
                            CardDetailFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                        }
                    }
                });
                viewHolder.mText.setText(this.linkageList.get(position).getLinkageName());
                return convertView;
            } catch (Exception e) {
                CardDetailFragment.this.notifyException(e);
                return convertView;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void doOtherAction(int index) {
            try {
                LinkageAgent linkageAgent = this.linkageList.get(index);
                LinkageAgent.LinkageInfo linkageInfo = linkageAgent.getLinkageInfo();
                if (linkageInfo.getError() == LinkageAgent.ErrorType.NOT_FOUND_ACTIVITY) {
                    CardDetailFragment.this.showWarningDialog(R.string.dlg_warning_not_found_activity);
                    AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_not_found_activity");
                    return;
                }
                if (linkageInfo.getError() == LinkageAgent.ErrorType.NOT_FOUND_PACKAGE) {
                    CardDetailFragment.this.showWarningDialog(R.string.dlg_warning_signature_unmatched);
                    AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_signature_unmatched");
                    return;
                }
                Intent intent = linkageInfo.getIntent();
                if (intent == null) {
                    CardDetailFragment.this.showWarningDialog(R.string.dlg_warning_signature_unmatched);
                    AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_signature_unmatched");
                    return;
                }
                int i = AnonymousClass16.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$LinkageAgent$LinkageType[linkageAgent.getLinkageType().ordinal()];
                if (i == 1) {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_LINKAGE_SITE, Integer.valueOf(index + 1));
                } else if (i == 2) {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_LINKAGE_CONTACT, intent);
                }
                try {
                    CardDetailFragment.this.startActivity(intent);
                } catch (Exception unused) {
                    CardDetailFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                    AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                }
            } catch (Exception e) {
                CardDetailFragment.this.notifyException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.views.CardDetailFragment$16, reason: invalid class name */
    static /* synthetic */ class AnonymousClass16 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CardDetailFuncAgent$CompleteState$FelicaState;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$HistoryAgent$UseType;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$LinkageAgent$LinkageType;

        static {
            int[] iArr = new int[LinkageAgent.LinkageType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$LinkageAgent$LinkageType = iArr;
            try {
                iArr[LinkageAgent.LinkageType.WEB.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$LinkageAgent$LinkageType[LinkageAgent.LinkageType.CONTACT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[HistoryAgent.UseType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$HistoryAgent$UseType = iArr2;
            try {
                iArr2[HistoryAgent.UseType.CHARGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$HistoryAgent$UseType[HistoryAgent.UseType.PAYMENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$HistoryAgent$UseType[HistoryAgent.UseType.TRAFFIC.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$HistoryAgent$UseType[HistoryAgent.UseType.OTHER.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr3 = new int[CardDetailFuncAgent.CompleteState.FelicaState.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CardDetailFuncAgent$CompleteState$FelicaState = iArr3;
            try {
                iArr3[CardDetailFuncAgent.CompleteState.FelicaState.NO_PROBLEM.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CardDetailFuncAgent$CompleteState$FelicaState[CardDetailFuncAgent.CompleteState.FelicaState.NETWORK_WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CardDetailFuncAgent$CompleteState$FelicaState[CardDetailFuncAgent.CompleteState.FelicaState.MFI_MAINTENANCE_WARNING.ordinal()] = 3;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CardDetailFuncAgent$CompleteState$FelicaState[CardDetailFuncAgent.CompleteState.FelicaState.PROCESS_FAILURE_WARNING.ordinal()] = 4;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CardDetailFuncAgent$CompleteState$FelicaState[CardDetailFuncAgent.CompleteState.FelicaState.MFI_INSUFFICIENT_CHIP_SPACE.ordinal()] = 5;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CardDetailFuncAgent$CompleteState$FelicaState[CardDetailFuncAgent.CompleteState.FelicaState.MFI_TYPE_EXIST_UNKNOWN_CARD.ordinal()] = 6;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CardDetailFuncAgent$CompleteState$FelicaState[CardDetailFuncAgent.CompleteState.FelicaState.INSIDE_TRANSIT_GATE_ERROR.ordinal()] = 7;
            } catch (NoSuchFieldError unused13) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void TextViewShow(TextView textView, String value) {
        if (TextUtils.isEmpty(value)) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
            textView.setText(value);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ImageViewShow(ImageView imageView, Bitmap bitmap) {
        if (bitmap == null) {
            imageView.setVisibility(8);
        } else {
            imageView.setVisibility(0);
            imageView.setImageBitmap(bitmap);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showWarningDialogWithButtonListener(String errMsg, String errCode) {
        CustomDialogFragment customDialogFragmentShowWarningDialog = showWarningDialog(errMsg, errCode);
        if (customDialogFragmentShowWarningDialog == null) {
            return;
        }
        customDialogFragmentShowWarningDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.14
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                try {
                    CardDetailFragment.this.mStructure.actClose();
                    return true;
                } catch (Exception e) {
                    CardDetailFragment.this.notifyException(e);
                    return true;
                }
            }
        });
    }

    private int findInitIndex(List<MyCardInfoAgent> list, String sid, String cid) {
        for (int i = 0; i < list.size(); i++) {
            MyCardInfoAgent myCardInfoAgent = list.get(i);
            if (TextUtils.equals(sid, myCardInfoAgent.getServiceId()) && TextUtils.equals(cid, myCardInfoAgent.getCid())) {
                return i;
            }
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (TextUtils.equals(sid, list.get(i2).getServiceId())) {
                return i2;
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MyCardInfoAgent getFocusedCard() {
        if (this.mCurCardPosition >= 0) {
            int size = this.mMyCardInfoList.size();
            int i = this.mCurCardPosition;
            if (size > i) {
                return this.mMyCardInfoList.get(i);
            }
        }
        throw new IllegalStateException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reload() {
        AnalysisManager.stamp(MfmStamp.Event.ACTION_REFRESH_CARD_DETAIL, new Object[0]);
        MyCardInfoAgent focusedCard = getFocusedCard();
        this.focusedServiceId = focusedCard.getServiceId();
        this.focusedCardId = focusedCard.getCid();
        initData(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reload(String sid, String cid) {
        this.focusedServiceId = sid;
        this.focusedCardId = cid;
        initData(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCardOperationBtnEnable(boolean enable) {
        this.mEnableBt.setEnabled(enable);
        this.mRecoveryBt.setEnabled(enable);
        this.mCardDepositLayout.setEnabled(enable);
        int childCount = this.mLinkAppListView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            this.mLinkAppListView.getChildAt(i).setEnabled(enable);
        }
        int childCount2 = this.mOtherGridView.getChildCount();
        for (int i2 = 0; i2 < childCount2; i2++) {
            this.mOtherGridView.getChildAt(i2).setEnabled(enable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDeleteCompleteDialog() {
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_17_08, new Object[0]);
        CustomDialogFragment customDialogFragmentCreateSingleChoiceDialog = DialogFactory.createSingleChoiceDialog(((FragmentActivity) Objects.requireNonNull(getActivity())).getApplicationContext());
        customDialogFragmentCreateSingleChoiceDialog.setText(getString(R.string.dlg_text_delete_card_complete));
        customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.15
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                CardDetailFragment.this.reload();
                return true;
            }
        });
        showNormalDialog(customDialogFragmentCreateSingleChoiceDialog);
    }
}
