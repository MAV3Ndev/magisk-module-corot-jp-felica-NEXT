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
import com.felicanetworks.mfm.MficStatusChangeReceiver;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.model.info.GeneralInfo;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.presenter.agent.DeleteCardListFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.LinkageAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyCardInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceGroupInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.TransitPassInfoAgent;
import com.felicanetworks.mfm.main.presenter.structure.DeleteCardListDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.view.activity.DeleteCardListActivity;
import com.felicanetworks.mfm.main.view.views.CustomDialogFragment;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
public class DeleteCardListFragment extends BaseFragment implements View.OnClickListener {
    private String focusedCardId;
    private String focusedServiceId;
    private View mAddInfoIllegalView;
    private LinearLayout mBalanceLayout;
    private TextView mBalanceTv;
    private ViewPager2 mCardImageView;
    private TextView mCardNameTv;
    private BannerLayoutCardDetail mCardSwitchView;
    private LinearLayout mDetailView;
    private ImageView mGroupIconIv;
    private MyServiceGroupInfoAgent mGroupInfo;
    private TextView mGroupNameTv;
    private LinearLayout mGroupView;
    private TextView mIdKeyTv;
    private TextView mIdValueTv;
    private LimitableIndicatorLayout mIndicatorView;
    private Button mIssueBt;
    private ListView mLinkAppListView;
    private LinearLayout mLinkAppView;
    private GridView mOtherGridView;
    private LinearLayout mOtherInfoView;
    private LinearLayout mPointLayout;
    private TextView mPointTv;
    private TextView mProviderNameTv;
    private ListView mSpServerListView;
    private LinearLayout mSpendLayout;
    private LinearLayout mSpendLimitLayout;
    private TextView mSpendLimitTv;
    private TextView mSpendTv;
    private DeleteCardListDrawStructure mStructure;
    private Button mUnKnownBt;
    private LinearLayout midLayout;
    private ScrollView scrollView;
    private boolean mOnceSlideFlg = true;
    private int mCurCardPosition = 0;
    private List<MyCardInfoAgent> mMyCardInfoList = new ArrayList();
    MenuProvider mMenuProvider = null;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewInflate = inflater.inflate(R.layout.fragment_card_detail, container, false);
        try {
            Structure structure = getStructure();
            if (structure instanceof DeleteCardListDrawStructure) {
                DeleteCardListDrawStructure deleteCardListDrawStructure = (DeleteCardListDrawStructure) structure;
                this.mStructure = deleteCardListDrawStructure;
                this.focusedServiceId = deleteCardListDrawStructure.getFocusSid();
                this.focusedCardId = this.mStructure.getFocusCid();
                initView(viewInflate);
                addMenuProvider();
                initProcess();
                initData(false);
                adjustViewToNavigationBar(this.scrollView, true);
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
        this.mCardSwitchView.updateMetrics();
        adjustViewToNavigationBar(this.scrollView, true);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        try {
            if (view.getId() == R.id.bt_issue) {
                setCardOperationBtnEnable(false);
                doIssueAction();
            }
        } catch (Exception e) {
            notifyException(e);
        }
    }

    private void addMenuProvider() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        removeMenuProvider();
        if (this.mMenuProvider == null) {
            this.mMenuProvider = new MenuProvider() { // from class: com.felicanetworks.mfm.main.view.views.DeleteCardListFragment.1
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
                    menuInflater.inflate(R.menu.menu_deletecard, menu);
                }

                @Override // androidx.core.view.MenuProvider
                public boolean onMenuItemSelected(MenuItem item) {
                    try {
                        if (item.getItemId() == R.id.tool_delete_card_update) {
                            DeleteCardListFragment.this.setCardOperationBtnEnable(false);
                            DeleteCardListFragment.this.reload();
                            return false;
                        }
                    } catch (Exception e) {
                        DeleteCardListFragment.this.notifyException(e);
                    }
                    return false;
                }
            };
        }
        activity.addMenuProvider(this.mMenuProvider, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void removeMenuProvider() {
        MenuProvider menuProvider;
        FragmentActivity activity = getActivity();
        if (activity == null || (menuProvider = this.mMenuProvider) == null) {
            return;
        }
        activity.removeMenuProvider(menuProvider);
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
        Button button = (Button) layout.findViewById(R.id.bt_issue);
        this.mIssueBt = button;
        button.setOnClickListener(this);
        this.mUnKnownBt = (Button) layout.findViewById(R.id.bt_unknown);
        this.mLinkAppView = (LinearLayout) layout.findViewById(R.id.ll_link_app_layout);
        this.mLinkAppListView = (ListView) layout.findViewById(R.id.lv_link_app_list);
        this.mAddInfoIllegalView = layout.findViewById(R.id.fl_additional_info_illegal);
        this.mSpServerListView = (ListView) layout.findViewById(R.id.lv_sp_server_list);
        this.mOtherInfoView = (LinearLayout) layout.findViewById(R.id.ll_other_layout);
        this.mOtherGridView = (GridView) layout.findViewById(R.id.gv_other_list);
        ((LinearLayout) layout.findViewById(R.id.ll_active_layout)).setVisibility(8);
        ((Button) layout.findViewById(R.id.bt_enable)).setVisibility(8);
        ((Button) layout.findViewById(R.id.bt_recovery)).setVisibility(8);
        ((LinearLayout) layout.findViewById(R.id.ll_card_deposit)).setVisibility(8);
        ((LinearLayout) layout.findViewById(R.id.ll_history_list)).setVisibility(8);
        this.mCardSwitchView.setIndicator(this.mIndicatorView);
    }

    private void initProcess() {
        Animation animationLoadAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.translate_in);
        animationLoadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.felicanetworks.mfm.main.view.views.DeleteCardListFragment.2
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
                try {
                    DeleteCardListFragment.this.mGroupView.setVisibility(4);
                    DeleteCardListFragment.this.mIndicatorView.setVisibility(4);
                    DeleteCardListFragment.this.mDetailView.setVisibility(4);
                } catch (Exception e) {
                    DeleteCardListFragment.this.notifyException(e);
                }
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                try {
                    Animation animationLoadAnimation2 = AnimationUtils.loadAnimation(DeleteCardListFragment.this.getContext(), R.anim.fade_in);
                    DeleteCardListFragment.this.mGroupView.startAnimation(animationLoadAnimation2);
                    if (DeleteCardListFragment.this.mIndicatorView.getNoOfPages() > 1) {
                        DeleteCardListFragment.this.mIndicatorView.startAnimation(animationLoadAnimation2);
                    }
                    DeleteCardListFragment.this.mDetailView.startAnimation(animationLoadAnimation2);
                    DeleteCardListFragment.this.mDetailView.setVisibility(0);
                } catch (Exception e) {
                    DeleteCardListFragment.this.notifyException(e);
                }
            }
        });
        this.mCardSwitchView.startAnimation(animationLoadAnimation);
        this.mCardImageView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.felicanetworks.mfm.main.view.views.DeleteCardListFragment.3
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageScrollStateChanged(int state) {
                try {
                    if (state == 0) {
                        DeleteCardListFragment.this.mOnceSlideFlg = true;
                        DeleteCardListFragment.this.setCardOperationBtnEnable(true);
                        DeleteCardListFragment.this.mDetailView.setVisibility(0);
                        DeleteCardListFragment deleteCardListFragment = DeleteCardListFragment.this;
                        deleteCardListFragment.mCurCardPosition = deleteCardListFragment.mCardImageView.getCurrentItem();
                        DeleteCardListFragment.this.updateCardDetailView();
                        return;
                    }
                    if (DeleteCardListFragment.this.mOnceSlideFlg) {
                        DeleteCardListFragment.this.mOnceSlideFlg = false;
                        DeleteCardListFragment.this.setCardOperationBtnEnable(false);
                        DeleteCardListFragment.this.mDetailView.setVisibility(8);
                    }
                } catch (Exception e) {
                    DeleteCardListFragment.this.notifyException(e);
                }
            }
        });
    }

    private void initData(final boolean update) {
        if (getActivity() == null) {
            return;
        }
        setCardOperationBtnEnable(false);
        final CustomDialogFragment customDialogFragmentShowProgressDialog = ((DeleteCardListActivity) getActivity()).showProgressDialog();
        customDialogFragmentShowProgressDialog.setCircleProgressEnable(true);
        final DeleteCardListFuncAgent deleteCardListFunc = this.mStructure.getDeleteCardListFunc();
        deleteCardListFunc.createDeleteCardListInfo(new DeleteCardListFuncAgent.CreateDeleteCardListInfoListener() { // from class: com.felicanetworks.mfm.main.view.views.DeleteCardListFragment.4
            @Override // com.felicanetworks.mfm.main.presenter.agent.DeleteCardListFuncAgent.CreateDeleteCardListInfoListener
            public void onComplete(MyServiceGroupInfoAgent groupInfo, DeleteCardListFuncAgent.CompleteState state) {
                StringBuilder sb;
                String errorCode;
                int i;
                String str;
                try {
                    try {
                        sb = new StringBuilder();
                        errorCode = "";
                        i = AnonymousClass9.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$DeleteCardListFuncAgent$CompleteState$FelicaState[state.getFelicaState().ordinal()];
                    } catch (Exception e) {
                        DeleteCardListFragment.this.notifyException(e);
                    }
                    if (i == 1) {
                        if (groupInfo == null) {
                            DeleteCardListFragment.this.mStructure.actClose();
                        } else {
                            if (!update) {
                                if (deleteCardListFunc.isVanishedInputService(groupInfo)) {
                                    sb.append(DeleteCardListFragment.this.getString(R.string.dlg_text_warning_direct_card_detail_vanished_service));
                                    str = "dlg_text_warning_direct_card_detail_vanished_service";
                                } else if (deleteCardListFunc.isVanishedInputCard(groupInfo)) {
                                    sb.append(DeleteCardListFragment.this.getString(R.string.dlg_text_warning_direct_card_detail_vanished_card));
                                    str = "dlg_text_warning_direct_card_detail_vanished_card";
                                }
                            }
                            DeleteCardListFragment.this.mGroupInfo = groupInfo;
                            DeleteCardListFragment.this.updateView();
                        }
                        DeleteCardListFragment.this.setCardOperationBtnEnable(true);
                    }
                    if (i == 2) {
                        sb.append(DeleteCardListFragment.this.getString(R.string.dlg_warning_mfiservices_network_failed));
                        str = "dlg_warning_mfiservices_network_failed";
                    } else if (i == 3) {
                        sb.append(DeleteCardListFragment.this.getString(R.string.dlg_text_warning_mfi_server_maintenance));
                        str = "dlg_text_warning_mfi_server_maintenance";
                    } else if (i != 4) {
                        str = null;
                    } else {
                        sb.append(DeleteCardListFragment.this.getString(R.string.dlg_text_warning_get_delete_card_list_info));
                        str = "dlg_text_warning_get_delete_card_list_info";
                        if (!TextUtils.isEmpty(state.getErrorCode())) {
                            sb.append(DeleteCardListFragment.this.getString(R.string.dlg_error_code, state.getErrorCode()));
                            errorCode = state.getErrorCode();
                        }
                        if (!TextUtils.isEmpty(state.getWarCode())) {
                            sb.append(DeleteCardListFragment.this.getString(R.string.dlg_error_code_mfc, state.getWarCode()));
                        }
                        MficStatusChangeReceiver.sendMficStatusChangeByMySelf(DeleteCardListFragment.this.getContext());
                    }
                    if (!TextUtils.isEmpty(sb)) {
                        DeleteCardListFragment.this.stampWithJudgeActivityState(MfmStamp.Event.SCREEN_WARNING, str);
                        DeleteCardListFragment.this.showWarningDialogWithButtonListener(sb.toString(), errorCode);
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
        stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_19_01, this.mGroupInfo);
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
                    this.mStructure.getDeleteCardListFunc().getLackCardFaceImage(str, cardImageUrls, new DeleteCardListFuncAgent.LackCardFaceImageListener() { // from class: com.felicanetworks.mfm.main.view.views.DeleteCardListFragment.5
                        @Override // com.felicanetworks.mfm.main.presenter.agent.DeleteCardListFuncAgent.LackCardFaceImageListener
                        public void onGetImage(String id2, String url, Bitmap image) {
                            if (image != null) {
                                DeleteCardListFragment.this.mCardSwitchView.update(id2, null, image);
                            } else {
                                DeleteCardListFragment.this.mCardSwitchView.update(id2, myServiceInfoAgent.getCardName(cid), null);
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

    private void doIssueAction() {
        DeleteCardListFuncAgent deleteCardListFunc = this.mStructure.getDeleteCardListFunc();
        MyCardInfoAgent focusedCard = getFocusedCard();
        AnalysisManager.stamp(MfmStamp.Event.ACTION_ISSUE_CARD, focusedCard.getServiceId());
        final CustomDialogFragment customDialogFragmentShowProgressDialog = ((DeleteCardListActivity) Objects.requireNonNull(getActivity())).showProgressDialog();
        customDialogFragmentShowProgressDialog.setCircleProgressEnable(true);
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_19_02, new Object[0]);
        deleteCardListFunc.issueCard(focusedCard.getCid(), new DeleteCardListFuncAgent.IssueCardListener() { // from class: com.felicanetworks.mfm.main.view.views.DeleteCardListFragment.6
            /* JADX WARN: Removed duplicated region for block: B:28:0x012a A[Catch: all -> 0x013f, Exception -> 0x0141, TRY_LEAVE, TryCatch #0 {Exception -> 0x0141, blocks: (B:2:0x0000, B:3:0x0015, B:26:0x0124, B:28:0x012a, B:5:0x001b, B:6:0x002b, B:7:0x003b, B:8:0x004b, B:9:0x005b, B:10:0x006b, B:11:0x007b, B:12:0x008b, B:13:0x009b, B:14:0x00ab, B:16:0x00c3, B:17:0x00db, B:19:0x00e5, B:21:0x00fb, B:22:0x010a, B:23:0x0119), top: B:37:0x0000, outer: #1 }] */
            @Override // com.felicanetworks.mfm.main.presenter.agent.DeleteCardListFuncAgent.IssueCardListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onComplete(String issuedSid, String issuedCid, DeleteCardListFuncAgent.CompleteState state) {
                String str;
                try {
                    try {
                        StringBuilder sb = new StringBuilder();
                        String errorCode = "";
                        switch (AnonymousClass9.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$DeleteCardListFuncAgent$CompleteState$FelicaState[state.getFelicaState().ordinal()]) {
                            case 1:
                                DeleteCardListFragment.this.showIssueCompleteDialog();
                                break;
                            case 2:
                                sb.append(DeleteCardListFragment.this.getString(R.string.dlg_warning_mfiservices_network_failed));
                                str = "dlg_warning_mfiservices_network_failed";
                                if (!TextUtils.isEmpty(sb)) {
                                    DeleteCardListFragment.this.stampWithJudgeActivityState(MfmStamp.Event.SCREEN_WARNING, str);
                                    DeleteCardListFragment.this.showWarningDialogWithButtonListener(sb.toString(), errorCode);
                                }
                                break;
                            case 3:
                                sb.append(DeleteCardListFragment.this.getString(R.string.dlg_text_warning_mfi_server_maintenance));
                                str = "dlg_text_warning_mfi_server_maintenance";
                                if (!TextUtils.isEmpty(sb)) {
                                }
                                break;
                            case 4:
                                sb.append(DeleteCardListFragment.this.getString(R.string.dlg_text_warning_issue_card));
                                if (!TextUtils.isEmpty(state.getErrorCode())) {
                                    sb.append(DeleteCardListFragment.this.getString(R.string.dlg_error_code, state.getErrorCode()));
                                    errorCode = state.getErrorCode();
                                }
                                if (!TextUtils.isEmpty(state.getWarCode())) {
                                    sb.append(DeleteCardListFragment.this.getString(R.string.dlg_error_code_mfc, state.getWarCode()));
                                }
                                str = "dlg_text_warning_issue_card";
                                if (!TextUtils.isEmpty(sb)) {
                                }
                                break;
                            case 5:
                                sb.append(DeleteCardListFragment.this.getString(R.string.dlg_text_warning_unsupported_device));
                                str = "dlg_text_warning_unsupported_device";
                                if (!TextUtils.isEmpty(sb)) {
                                }
                                break;
                            case 6:
                                sb.append(DeleteCardListFragment.this.getString(R.string.dlg_text_warning_card_issue_limit_over));
                                str = "dlg_text_warning_card_issue_limit_over";
                                if (!TextUtils.isEmpty(sb)) {
                                }
                                break;
                            case 7:
                                sb.append(DeleteCardListFragment.this.getString(R.string.dlg_text_warning_card_issue_limit_over_in_account));
                                str = "dlg_text_warning_card_issue_limit_over_in_account";
                                if (!TextUtils.isEmpty(sb)) {
                                }
                                break;
                            case 8:
                                sb.append(DeleteCardListFragment.this.getString(R.string.dlg_text_warning_card_issue_limit_over_in_device));
                                str = "dlg_text_warning_card_issue_limit_over_in_device";
                                if (!TextUtils.isEmpty(sb)) {
                                }
                                break;
                            case 9:
                                sb.append(DeleteCardListFragment.this.getString(R.string.dlg_text_warning_chip_capacity_over));
                                str = "dlg_text_warning_chip_capacity_over";
                                if (!TextUtils.isEmpty(sb)) {
                                }
                                break;
                            case 10:
                                sb.append(DeleteCardListFragment.this.getString(R.string.dlg_text_warning_same_system_card_issued));
                                str = "dlg_text_warning_same_system_card_issued";
                                if (!TextUtils.isEmpty(sb)) {
                                }
                                break;
                            case 11:
                                sb.append(DeleteCardListFragment.this.getString(R.string.dlg_text_warning_issued_instance_missing));
                                str = "dlg_text_warning_issued_instance_missing";
                                if (!TextUtils.isEmpty(sb)) {
                                }
                                break;
                            case 12:
                                sb.append(DeleteCardListFragment.this.getString(R.string.dlg_text_warning_unmanaged_card_detection));
                                str = "dlg_text_warning_unmanaged_card_detection";
                                if (!TextUtils.isEmpty(sb)) {
                                }
                                break;
                            case 13:
                                sb.append(DeleteCardListFragment.this.getString(R.string.dlg_text_warning_insfficient_allocated_free_space));
                                str = "dlg_text_warning_insfficient_allocated_free_space";
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
                        DeleteCardListFragment.this.notifyException(e);
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
        List<LinkageAgent> linkageOtherList = myServiceInfoAgentFindService.getLinkageOtherList(focusedCard.getCid());
        if (linkageOtherList.size() >= 1) {
            this.mOtherInfoView.setVisibility(0);
        } else {
            this.mOtherInfoView.setVisibility(8);
        }
        this.mOtherGridView.setAdapter((ListAdapter) new OtherItemAdapter(getContext(), linkageOtherList));
    }

    private void setCardStatus(MyCardInfoAgent.CardStatusForCardDetailUI status) {
        this.mIssueBt.setVisibility(8);
        this.mUnKnownBt.setVisibility(8);
        if (MyCardInfoAgent.CardStatusForCardDetailUI.NOT_EXIST == status) {
            this.mIssueBt.setVisibility(0);
        } else if (MyCardInfoAgent.CardStatusForCardDetailUI.UNPROCESSED == status) {
            this.mUnKnownBt.setVisibility(0);
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
                    convertView = LayoutInflater.from(DeleteCardListFragment.this.getContext()).inflate(R.layout.list_item_sp_server, parent, false);
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
                DeleteCardListFragment.this.notifyException(e);
                return convertView;
            }
        }

        private void updateListItem(ViewHolder viewHolder, int position) {
            String transitPassName = this.transitPassInfoAgentList.get(position).getTransitPassName();
            String category = this.transitPassInfoAgentList.get(position).getCategory();
            DeleteCardListFragment.this.TextViewShow(viewHolder.mTransitTitle, transitPassName + " " + category);
            DeleteCardListFragment.this.TextViewShow(viewHolder.mCommuterPassNameTv, this.transitPassInfoAgentList.get(position).getCommuterPassName());
            String section1From = this.transitPassInfoAgentList.get(position).getSection1From();
            String section1To = this.transitPassInfoAgentList.get(position).getSection1To();
            if (section1From == null && section1To == null) {
                viewHolder.mSection1Layout.setVisibility(8);
            } else {
                viewHolder.mSection1Layout.setVisibility(0);
                DeleteCardListFragment.this.TextViewShow(viewHolder.mSection1FromTv, section1From);
                DeleteCardListFragment.this.TextViewShow(viewHolder.mSection1ToTv, section1To);
            }
            String section2From = this.transitPassInfoAgentList.get(position).getSection2From();
            String section2To = this.transitPassInfoAgentList.get(position).getSection2To();
            if (section2From == null && section2To == null) {
                viewHolder.mSection2Layout.setVisibility(8);
            } else {
                viewHolder.mSection2Layout.setVisibility(0);
                DeleteCardListFragment.this.TextViewShow(viewHolder.mSection2FromTv, section2From);
                DeleteCardListFragment.this.TextViewShow(viewHolder.mSection2ToTv, section2To);
            }
            DeleteCardListFragment.this.TextViewShow(viewHolder.mAdditionalInfoTv, this.transitPassInfoAgentList.get(position).getAdditionalInformation());
            DeleteCardListFragment.this.TextViewShow(viewHolder.mViaTv, this.transitPassInfoAgentList.get(position).getVia());
            DeleteCardListFragment.this.TextViewShow(viewHolder.mIssuerNameTv, this.transitPassInfoAgentList.get(position).getIssuerName());
            String termOfValidityKey = this.transitPassInfoAgentList.get(position).getTermOfValidityKey();
            String termOfValidityStart = this.transitPassInfoAgentList.get(position).getTermOfValidityStart();
            String termOfValidityEnd = this.transitPassInfoAgentList.get(position).getTermOfValidityEnd();
            boolean termOfValidityExtension = this.transitPassInfoAgentList.get(position).getTermOfValidityExtension();
            if (termOfValidityKey == null && termOfValidityStart == null && termOfValidityEnd == null && !termOfValidityExtension) {
                viewHolder.mTermOfValidityLayout.setVisibility(8);
            } else {
                viewHolder.mTermOfValidityLayout.setVisibility(0);
                DeleteCardListFragment.this.TextViewShow(viewHolder.mTermOfValidityKeyTv, termOfValidityKey);
                DeleteCardListFragment.this.TextViewShow(viewHolder.mTermOfValidityStartTv, termOfValidityStart);
                DeleteCardListFragment.this.TextViewShow(viewHolder.mTermOfValidityEndTv, termOfValidityEnd);
                String string = "";
                if (termOfValidityStart != null || termOfValidityEnd != null) {
                    DeleteCardListFragment.this.TextViewShow(viewHolder.mTermOfValiditySeparate, DeleteCardListFragment.this.getString(R.string.card_item_label_train_detail_screen_period_mark));
                } else {
                    DeleteCardListFragment.this.TextViewShow(viewHolder.mTermOfValiditySeparate, "");
                }
                DeleteCardListFragment deleteCardListFragment = DeleteCardListFragment.this;
                TextView textView = viewHolder.mTermOfValidityExtensionTv;
                if (termOfValidityExtension) {
                    string = DeleteCardListFragment.this.getString(R.string.card_item_label_detail_screen_continuity);
                }
                deleteCardListFragment.TextViewShow(textView, string);
            }
            List<Map<String, String>> optionalInfoList = this.transitPassInfoAgentList.get(position).getOptionalInfoList();
            ArrayList arrayList = new ArrayList();
            for (Map<String, String> map : optionalInfoList) {
                if (map.get(TransitPassInfoAgent.OPTIONAL_INFO_KEY) != null || map.get("value") != null) {
                    arrayList.add(map);
                }
            }
            if (arrayList.size() > 0) {
                viewHolder.mOptionalInfoListView.setAdapter((ListAdapter) new SimpleAdapter(DeleteCardListFragment.this.getContext(), arrayList, R.layout.list_item_optional_info, new String[]{TransitPassInfoAgent.OPTIONAL_INFO_KEY, "value"}, new int[]{R.id.tv_optional_info_key, R.id.tv_optional_info_value}));
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
                    convertView = LayoutInflater.from(DeleteCardListFragment.this.getContext()).inflate(R.layout.list_item_link_app, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.mLinkAppIconIv = (ImageView) convertView.findViewById(R.id.iv_link_app_icon);
                    viewHolder.mLinkAppNameTv = (TextView) convertView.findViewById(R.id.tv_link_app_name);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                final LinkageAgent linkageAgent = this.mLinkageAgentList.get(position);
                DeleteCardListFragment.this.TextViewShow(viewHolder.mLinkAppNameTv, linkageAgent.getLinkageName());
                if (linkageAgent.getAppIcon() != null) {
                    DeleteCardListFragment.this.ImageViewShow(viewHolder.mLinkAppIconIv, linkageAgent.getAppIcon());
                } else {
                    DeleteCardListFragment.this.getFocusedCard().orderIconImg(linkageAgent.getAppIconUrl(), new MyCardInfoAgent.OrderImgListener() { // from class: com.felicanetworks.mfm.main.view.views.DeleteCardListFragment.LinkAppItemAdapter.1
                        @Override // com.felicanetworks.mfm.main.presenter.agent.MyCardInfoAgent.OrderImgListener
                        public void onComplete(Bitmap data) {
                            try {
                                if (DeleteCardListFragment.this.getActivity() == null || DeleteCardListFragment.this.getActivity().isFinishing() || data == null) {
                                    return;
                                }
                                DeleteCardListFragment.this.ImageViewShow(viewHolder.mLinkAppIconIv, data);
                            } catch (Exception e) {
                                DeleteCardListFragment.this.notifyException(e);
                            }
                        }
                    });
                }
                convertView.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.DeleteCardListFragment.LinkAppItemAdapter.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        Intent intent;
                        try {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_LINKAGE_APP, Integer.valueOf(position + 1));
                            LinkageAgent.LinkageInfo linkageInfo = linkageAgent.getLinkageInfo();
                            if (linkageInfo.getError() == LinkageAgent.ErrorType.NOT_FOUND_ACTIVITY) {
                                DeleteCardListFragment.this.showWarningDialog(R.string.dlg_warning_not_found_activity);
                                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_not_found_activity");
                            } else if (linkageInfo.getError() == LinkageAgent.ErrorType.NOT_FOUND_PACKAGE || (intent = linkageInfo.getIntent()) == null) {
                                DeleteCardListFragment.this.showWarningDialog(R.string.dlg_warning_signature_unmatched);
                                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_signature_unmatched");
                            } else {
                                try {
                                    DeleteCardListFragment.this.startActivity(intent);
                                } catch (Exception unused) {
                                    DeleteCardListFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                                    AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                                }
                            }
                        } catch (Exception e) {
                            DeleteCardListFragment.this.notifyException(e);
                        }
                    }
                });
                return convertView;
            } catch (Exception e) {
                DeleteCardListFragment.this.notifyException(e);
                return convertView;
            }
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
                convertView.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.DeleteCardListFragment.OtherItemAdapter.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        try {
                            OtherItemAdapter.this.doOtherAction(position);
                        } catch (Exception unused) {
                            DeleteCardListFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                        }
                    }
                });
                viewHolder.mText.setText(this.linkageList.get(position).getLinkageName());
                return convertView;
            } catch (Exception e) {
                DeleteCardListFragment.this.notifyException(e);
                return convertView;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void doOtherAction(int index) {
            try {
                LinkageAgent linkageAgent = this.linkageList.get(index);
                LinkageAgent.LinkageInfo linkageInfo = linkageAgent.getLinkageInfo();
                if (linkageInfo.getError() == LinkageAgent.ErrorType.NOT_FOUND_ACTIVITY) {
                    DeleteCardListFragment.this.showWarningDialog(R.string.dlg_warning_not_found_activity);
                    AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_not_found_activity");
                    return;
                }
                if (linkageInfo.getError() == LinkageAgent.ErrorType.NOT_FOUND_PACKAGE) {
                    DeleteCardListFragment.this.showWarningDialog(R.string.dlg_warning_signature_unmatched);
                    AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_signature_unmatched");
                    return;
                }
                Intent intent = linkageInfo.getIntent();
                if (intent == null) {
                    DeleteCardListFragment.this.showWarningDialog(R.string.dlg_warning_signature_unmatched);
                    AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_signature_unmatched");
                    return;
                }
                int i = AnonymousClass9.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$LinkageAgent$LinkageType[linkageAgent.getLinkageType().ordinal()];
                if (i == 1) {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_LINKAGE_SITE, Integer.valueOf(index + 1));
                } else if (i == 2) {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_LINKAGE_CONTACT, intent);
                }
                try {
                    DeleteCardListFragment.this.startActivity(intent);
                } catch (Exception unused) {
                    DeleteCardListFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                    AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                }
            } catch (Exception e) {
                DeleteCardListFragment.this.notifyException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.views.DeleteCardListFragment$9, reason: invalid class name */
    static /* synthetic */ class AnonymousClass9 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$DeleteCardListFuncAgent$CompleteState$FelicaState;
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
            int[] iArr2 = new int[DeleteCardListFuncAgent.CompleteState.FelicaState.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$DeleteCardListFuncAgent$CompleteState$FelicaState = iArr2;
            try {
                iArr2[DeleteCardListFuncAgent.CompleteState.FelicaState.NO_PROBLEM.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$DeleteCardListFuncAgent$CompleteState$FelicaState[DeleteCardListFuncAgent.CompleteState.FelicaState.NETWORK_WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$DeleteCardListFuncAgent$CompleteState$FelicaState[DeleteCardListFuncAgent.CompleteState.FelicaState.MFI_MAINTENANCE_WARNING.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$DeleteCardListFuncAgent$CompleteState$FelicaState[DeleteCardListFuncAgent.CompleteState.FelicaState.PROCESS_FAILURE_WARNING.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$DeleteCardListFuncAgent$CompleteState$FelicaState[DeleteCardListFuncAgent.CompleteState.FelicaState.UNSUPPORTED_DEVICE_WARNING.ordinal()] = 5;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$DeleteCardListFuncAgent$CompleteState$FelicaState[DeleteCardListFuncAgent.CompleteState.FelicaState.MFI_ISSUE_LIMIT_EXCEEDED.ordinal()] = 6;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$DeleteCardListFuncAgent$CompleteState$FelicaState[DeleteCardListFuncAgent.CompleteState.FelicaState.MFI_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED.ordinal()] = 7;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$DeleteCardListFuncAgent$CompleteState$FelicaState[DeleteCardListFuncAgent.CompleteState.FelicaState.MFI_ISSUE_LIMIT_PER_DEVICE_EXCEEDED.ordinal()] = 8;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$DeleteCardListFuncAgent$CompleteState$FelicaState[DeleteCardListFuncAgent.CompleteState.FelicaState.MFI_INSUFFICIENT_CHIP_SPACE.ordinal()] = 9;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$DeleteCardListFuncAgent$CompleteState$FelicaState[DeleteCardListFuncAgent.CompleteState.FelicaState.MFI_OTHER_SP_CARD_EXIST.ordinal()] = 10;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$DeleteCardListFuncAgent$CompleteState$FelicaState[DeleteCardListFuncAgent.CompleteState.FelicaState.MFI_INSTANCE_NOT_VACANT.ordinal()] = 11;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$DeleteCardListFuncAgent$CompleteState$FelicaState[DeleteCardListFuncAgent.CompleteState.FelicaState.MFI_TYPE_EXIST_UNKNOWN_CARD.ordinal()] = 12;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$DeleteCardListFuncAgent$CompleteState$FelicaState[DeleteCardListFuncAgent.CompleteState.FelicaState.MFI_INSUFFICIENT_ALLOCATED_FREE_SPACE.ordinal()] = 13;
            } catch (NoSuchFieldError unused15) {
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
        customDialogFragmentShowWarningDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.DeleteCardListFragment.7
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                try {
                    DeleteCardListFragment.this.mStructure.actClose();
                    return true;
                } catch (Exception e) {
                    DeleteCardListFragment.this.notifyException(e);
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
    public void setCardOperationBtnEnable(boolean enable) {
        this.mIssueBt.setEnabled(enable);
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
    public void showIssueCompleteDialog() {
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_19_05, new Object[0]);
        CustomDialogFragment customDialogFragmentCreateSingleChoiceDialog = DialogFactory.createSingleChoiceDialog(((FragmentActivity) Objects.requireNonNull(getActivity())).getApplicationContext());
        customDialogFragmentCreateSingleChoiceDialog.setText(getString(R.string.dlg_text_issue_card_complete));
        customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.DeleteCardListFragment.8
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                try {
                    DeleteCardListFragment.this.mStructure.actClose();
                    return true;
                } catch (Exception e) {
                    DeleteCardListFragment.this.notifyException(e);
                    return true;
                }
            }
        });
        showNormalDialog(customDialogFragmentCreateSingleChoiceDialog);
    }
}
