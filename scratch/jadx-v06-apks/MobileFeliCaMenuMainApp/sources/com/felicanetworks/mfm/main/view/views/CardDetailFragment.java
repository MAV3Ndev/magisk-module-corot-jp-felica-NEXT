package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
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
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import com.felicanetworks.mfm.main.R;
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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
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
    private ViewPager mCardImageView;
    private TextView mCardNameTv;
    private BannerLayoutCardDetail mCardSwitchView;
    private ImageView mCloseBtn;
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
    private ImageView mReloadBtn;
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

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_card_detail, viewGroup, false);
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
            initProcess();
            initData(false);
        } catch (Exception e) {
            notifyException(e);
        }
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mCardSwitchView.updateMetrics();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.bt_enable /* 2131361915 */:
                    setCardOperationBtnEnable(false);
                    doEnableAction();
                    break;
                case R.id.bt_recovery /* 2131361918 */:
                    setCardOperationBtnEnable(false);
                    doRecoveryAction();
                    break;
                case R.id.iv_card_detail_close /* 2131362071 */:
                    setCardOperationBtnEnable(false);
                    this.mStructure.actClose();
                    break;
                case R.id.iv_card_detail_reload /* 2131362072 */:
                    setCardOperationBtnEnable(false);
                    reload();
                    break;
                case R.id.ll_card_deposit /* 2131362109 */:
                    doDeleteConfirmAction();
                    break;
                case R.id.ll_more_history /* 2131362139 */:
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

    private void initView(View view) {
        this.scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_card_detail_close);
        this.mCloseBtn = imageView;
        imageView.setOnClickListener(this);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.iv_card_detail_reload);
        this.mReloadBtn = imageView2;
        imageView2.setOnClickListener(this);
        this.mGroupView = (LinearLayout) view.findViewById(R.id.ll_card_header);
        this.mGroupNameTv = (TextView) view.findViewById(R.id.tv_card_grouping);
        this.mGroupIconIv = (ImageView) view.findViewById(R.id.iv_card_grouping);
        this.mCardImageView = (ViewPager) view.findViewById(R.id.vp_card_detail);
        this.mIndicatorView = (LimitableIndicatorLayout) view.findViewById(R.id.il_card_detail);
        this.mCardSwitchView = (BannerLayoutCardDetail) view.findViewById(R.id.bl_card_detail);
        this.mDetailView = (LinearLayout) view.findViewById(R.id.ll_card_detail_main);
        this.mCardNameTv = (TextView) view.findViewById(R.id.tv_server_name);
        this.mProviderNameTv = (TextView) view.findViewById(R.id.tv_provider_name);
        this.midLayout = (LinearLayout) view.findViewById(R.id.ll_id_layout);
        this.mIdKeyTv = (TextView) view.findViewById(R.id.tv_id_key);
        this.mIdValueTv = (TextView) view.findViewById(R.id.tv_id_value);
        this.mBalanceLayout = (LinearLayout) view.findViewById(R.id.ll_balance_layout);
        this.mBalanceTv = (TextView) view.findViewById(R.id.tv_balance);
        this.mSpendLayout = (LinearLayout) view.findViewById(R.id.ll_spend_layout);
        this.mSpendTv = (TextView) view.findViewById(R.id.tv_spend);
        this.mPointLayout = (LinearLayout) view.findViewById(R.id.ll_point_layout);
        this.mPointTv = (TextView) view.findViewById(R.id.tv_point);
        this.mSpendLimitLayout = (LinearLayout) view.findViewById(R.id.ll_spend_limit_layout);
        this.mSpendLimitTv = (TextView) view.findViewById(R.id.tv_spend_limit);
        this.mActiveView = (LinearLayout) view.findViewById(R.id.ll_active_layout);
        Button button = (Button) view.findViewById(R.id.bt_enable);
        this.mEnableBt = button;
        button.setOnClickListener(this);
        Button button2 = (Button) view.findViewById(R.id.bt_recovery);
        this.mRecoveryBt = button2;
        button2.setOnClickListener(this);
        this.mLinkAppView = (LinearLayout) view.findViewById(R.id.ll_link_app_layout);
        this.mLinkAppListView = (ListView) view.findViewById(R.id.lv_link_app_list);
        this.mAddInfoIllegalView = view.findViewById(R.id.fl_additional_info_illegal);
        this.mSpServerListView = (ListView) view.findViewById(R.id.lv_sp_server_list);
        this.mOtherInfoView = (LinearLayout) view.findViewById(R.id.ll_other_layout);
        this.mOtherGridView = (GridView) view.findViewById(R.id.gv_other_list);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_card_deposit);
        this.mCardDepositLayout = linearLayout;
        linearLayout.setOnClickListener(this);
        this.mHistoryListView = (ListView) view.findViewById(R.id.lv_history_list);
        this.mMoreHistroyButtonLayout = (LinearLayout) view.findViewById(R.id.ll_more_history);
        this.mMoreCloseHistroyTextView = (TextView) view.findViewById(R.id.tv_more_close_history);
        this.mMoreHistroyButtonLayout.setOnClickListener(this);
        this.mhistoryLayout = (LinearLayout) view.findViewById(R.id.ll_history_list);
        ((Button) view.findViewById(R.id.bt_issue)).setVisibility(8);
        ((Button) view.findViewById(R.id.bt_unknown)).setVisibility(8);
        this.mCardSwitchView.setIndicator(this.mIndicatorView);
    }

    private void initProcess() {
        Animation animationLoadAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.translate_in);
        animationLoadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.1
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
                } catch (Exception e) {
                    CardDetailFragment.this.notifyException(e);
                }
            }
        });
        this.mCardSwitchView.startAnimation(animationLoadAnimation);
        this.mCardImageView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.2
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
                try {
                    if (i == 0) {
                        CardDetailFragment.this.mOnceSlideFlg = true;
                        CardDetailFragment.this.setCardOperationBtnEnable(true);
                        CardDetailFragment.this.mDetailView.setVisibility(0);
                        CardDetailFragment.this.mReloadBtn.setVisibility(0);
                        CardDetailFragment.this.mCloseBtn.setVisibility(0);
                        CardDetailFragment.this.mCurCardPosition = CardDetailFragment.this.mCardImageView.getCurrentItem();
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
                        CardDetailFragment.this.mReloadBtn.setVisibility(4);
                        CardDetailFragment.this.mCloseBtn.setVisibility(4);
                    }
                } catch (Exception e) {
                    CardDetailFragment.this.notifyException(e);
                }
            }
        });
    }

    private void initData(final boolean z) {
        if (getActivity() == null) {
            return;
        }
        setCardOperationBtnEnable(false);
        final CustomDialogFragment customDialogFragmentShowProgressDialog = ((CardDetailActivity) getActivity()).showProgressDialog();
        customDialogFragmentShowProgressDialog.setCircleProgressEnable(true);
        final CardDetailFuncAgent cardDetailFunc = this.mStructure.getCardDetailFunc();
        cardDetailFunc.createDetailCardInfo(z, new CardDetailFuncAgent.CreateDetailCardInfoListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.3
            @Override // com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.CreateDetailCardInfoListener
            public void onComplete(MyServiceGroupInfoAgent myServiceGroupInfoAgent, CardDetailFuncAgent.CompleteState completeState) {
                try {
                    try {
                        StringBuilder sb = new StringBuilder();
                        String str = null;
                        int i = AnonymousClass14.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CardDetailFuncAgent$CompleteState$FelicaState[completeState.getFelicaState().ordinal()];
                        if (i == 1) {
                            if (!z) {
                                if (cardDetailFunc.isVanishedInputService(myServiceGroupInfoAgent)) {
                                    sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_direct_card_detail_vanished_service));
                                    str = "dlg_text_warning_direct_card_detail_vanished_service";
                                } else if (cardDetailFunc.isVanishedInputCard(myServiceGroupInfoAgent)) {
                                    sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_direct_card_detail_vanished_card));
                                    str = "dlg_text_warning_direct_card_detail_vanished_card";
                                }
                            }
                            if (myServiceGroupInfoAgent != null) {
                                CardDetailFragment.this.mGroupInfo = myServiceGroupInfoAgent;
                                CardDetailFragment.this.updateView();
                            } else {
                                CardDetailFragment.this.mStructure.actClose();
                            }
                            CardDetailFragment.this.setCardOperationBtnEnable(true);
                            return;
                        }
                        if (i == 2) {
                            sb.append(CardDetailFragment.this.getString(R.string.dlg_warning_mfiservices_network_failed));
                            str = "dlg_warning_mfiservices_network_failed";
                        } else if (i == 3) {
                            sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_mfi_server_maintenance));
                            str = "dlg_text_warning_mfi_server_maintenance";
                        } else if (i == 4) {
                            sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_get_card_detail_info));
                            str = "dlg_text_warning_get_card_detail_info";
                            if (!TextUtils.isEmpty(completeState.getErrorCode())) {
                                sb.append(CardDetailFragment.this.getString(R.string.dlg_error_code, completeState.getErrorCode()));
                            }
                            if (!TextUtils.isEmpty(completeState.getWarCode())) {
                                sb.append(CardDetailFragment.this.getString(R.string.dlg_error_code_mfc, completeState.getWarCode()));
                            }
                        }
                        if (!TextUtils.isEmpty(sb)) {
                            CardDetailFragment.this.stampWithJudgeActivityState(MfmStamp.Event.SCREEN_WARNING, str);
                            CardDetailFragment.this.showWarningDialogWithButtonListener(sb.toString());
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
    public void updateView() {
        Bitmap cardFaceImage;
        stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_17_01, this.mGroupInfo);
        this.scrollView.scrollTo(0, 0);
        this.mReloadBtn.setVisibility(0);
        this.mCloseBtn.setVisibility(0);
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
                String cardImageUrl = myServiceInfoAgent.getCardImageUrl(cid);
                if (cardImageUrl == null) {
                    cardFaceImage = myServiceInfoAgent.getCardFaceImage(cid);
                } else {
                    this.mStructure.getCardDetailFunc().getNotFoundImage(str, cardImageUrl, new CardDetailFuncAgent.NotFoundImageListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.4
                        @Override // com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.NotFoundImageListener
                        public void onComplete() {
                        }

                        @Override // com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.NotFoundImageListener
                        public void onGetImage(String str2, String str3, Bitmap bitmap) {
                            if (bitmap != null) {
                                CardDetailFragment.this.mCardSwitchView.update(str2, null, bitmap);
                            } else {
                                CardDetailFragment.this.mCardSwitchView.update(str2, myServiceInfoAgent.getCardName(cid), null);
                            }
                        }
                    });
                    cardFaceImage = null;
                }
                this.mCardSwitchView.addItem(id, myServiceInfoAgent.getCardName(cid), str, cardFaceImage, myCardInfoAgent.getCardUIStatus(myServiceInfoAgent.getId()));
            }
        }
        this.mCardSwitchView.setBanner(this.mCardImageView);
        int iFindInitIndex = findInitIndex(this.mMyCardInfoList, this.focusedServiceId, this.focusedCardId);
        this.mCurCardPosition = iFindInitIndex;
        this.mCardSwitchView.setCurrentItem(iFindInitIndex);
        this.mCardSwitchView.notifyDataSetChanged();
        updateCardDetailView();
    }

    private void doEnableAction() {
        CardDetailFuncAgent cardDetailFunc = this.mStructure.getCardDetailFunc();
        final MyCardInfoAgent focusedCard = getFocusedCard();
        AnalysisManager.stamp(MfmStamp.Event.ACTION_ENABLE_CARD, focusedCard.getServiceId());
        final CustomDialogFragment customDialogFragmentShowProgressDialog = ((CardDetailActivity) Objects.requireNonNull(getActivity())).showProgressDialog();
        customDialogFragmentShowProgressDialog.setCircleProgressEnable(true);
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_17_02, new Object[0]);
        cardDetailFunc.enableCard(focusedCard.getCid(), focusedCard.getServiceId(), new CardDetailFuncAgent.EnableCardListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.5
            @Override // com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.EnableCardListener
            public void onComplete(CardDetailFuncAgent.CompleteState completeState) {
                try {
                    try {
                        StringBuilder sb = new StringBuilder();
                        String str = null;
                        switch (AnonymousClass14.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CardDetailFuncAgent$CompleteState$FelicaState[completeState.getFelicaState().ordinal()]) {
                            case 1:
                                CardDetailFragment.this.reload(focusedCard.getServiceId(), focusedCard.getCid());
                                return;
                            case 2:
                                sb.append(CardDetailFragment.this.getString(R.string.dlg_warning_mfiservices_network_failed));
                                str = "dlg_warning_mfiservices_network_failed";
                                break;
                            case 3:
                                sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_mfi_server_maintenance));
                                str = "dlg_text_warning_mfi_server_maintenance";
                                break;
                            case 4:
                                sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_enable_card));
                                str = "dlg_text_warning_enable_card";
                                if (!TextUtils.isEmpty(completeState.getErrorCode())) {
                                    sb.append(CardDetailFragment.this.getString(R.string.dlg_error_code, completeState.getErrorCode()));
                                }
                                if (!TextUtils.isEmpty(completeState.getWarCode())) {
                                    sb.append(CardDetailFragment.this.getString(R.string.dlg_error_code_mfc, completeState.getWarCode()));
                                }
                                break;
                            case 5:
                                sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_chip_capacity_over));
                                str = "dlg_text_warning_chip_capacity_over";
                                break;
                            case 6:
                                sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_unmanaged_card_detection));
                                str = "dlg_text_warning_unmanaged_card_detection";
                                break;
                        }
                        if (!TextUtils.isEmpty(sb)) {
                            CardDetailFragment.this.stampWithJudgeActivityState(MfmStamp.Event.SCREEN_WARNING, str);
                            CardDetailFragment.this.showWarningDialogWithButtonListener(sb.toString());
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
        cardDetailFunc.recoveryCard(focusedCard.getCid(), focusedCard.getServiceId(), new CardDetailFuncAgent.RecoveryCardListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.6
            @Override // com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.RecoveryCardListener
            public void onComplete(CardDetailFuncAgent.CompleteState completeState) {
                StringBuilder sb;
                String str;
                int i;
                try {
                    try {
                        sb = new StringBuilder();
                        str = null;
                        i = AnonymousClass14.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CardDetailFuncAgent$CompleteState$FelicaState[completeState.getFelicaState().ordinal()];
                    } catch (Exception e) {
                        CardDetailFragment.this.notifyException(e);
                    }
                    if (i == 1) {
                        CardDetailFragment.this.reload(focusedCard.getServiceId(), focusedCard.getCid());
                        return;
                    }
                    if (i == 2) {
                        sb.append(CardDetailFragment.this.getString(R.string.dlg_warning_mfiservices_network_failed));
                        str = "dlg_warning_mfiservices_network_failed";
                    } else if (i == 3) {
                        sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_mfi_server_maintenance));
                        str = "dlg_text_warning_mfi_server_maintenance";
                    } else if (i == 4) {
                        sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_recovery_card));
                        str = "dlg_text_warning_recovery_card";
                        if (!TextUtils.isEmpty(completeState.getErrorCode())) {
                            sb.append(CardDetailFragment.this.getString(R.string.dlg_error_code, completeState.getErrorCode()));
                        }
                        if (!TextUtils.isEmpty(completeState.getWarCode())) {
                            sb.append(CardDetailFragment.this.getString(R.string.dlg_error_code_mfc, completeState.getWarCode()));
                        }
                    }
                    if (!TextUtils.isEmpty(sb)) {
                        CardDetailFragment.this.stampWithJudgeActivityState(MfmStamp.Event.SCREEN_WARNING, str);
                        CardDetailFragment.this.showWarningDialogWithButtonListener(sb.toString());
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
            customDialogFragmentCreateSelectDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.7
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
            customDialogFragmentCreateSelectDialog.setNegativeButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.8
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
        depositDialogFragmentCreateSelectDepositDialog.setPositiveButtonListener(new DepositDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.9
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
        depositDialogFragmentCreateSelectDepositDialog.setNegativeButtonListener(new DepositDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.10
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
    public void doDeleteAction(MyCardInfoAgent myCardInfoAgent) {
        CardDetailFuncAgent cardDetailFunc = this.mStructure.getCardDetailFunc();
        final CustomDialogFragment customDialogFragmentShowProgressDialog = ((CardDetailActivity) Objects.requireNonNull(getActivity())).showProgressDialog();
        customDialogFragmentShowProgressDialog.setCircleProgressEnable(true);
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_17_02, new Object[0]);
        cardDetailFunc.deleteCard(myCardInfoAgent.getCid(), new CardDetailFuncAgent.DeleteCardListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.11
            @Override // com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent.DeleteCardListener
            public void onComplete(CardDetailFuncAgent.CompleteState completeState) {
                StringBuilder sb;
                String str;
                int i;
                try {
                    try {
                        sb = new StringBuilder();
                        str = null;
                        i = AnonymousClass14.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CardDetailFuncAgent$CompleteState$FelicaState[completeState.getFelicaState().ordinal()];
                    } catch (Exception e) {
                        CardDetailFragment.this.notifyException(e);
                    }
                    if (i == 1) {
                        CardDetailFragment.this.showDeleteCompleteDialog();
                        return;
                    }
                    if (i == 2) {
                        sb.append(CardDetailFragment.this.getString(R.string.dlg_warning_mfiservices_network_failed));
                        str = "dlg_warning_mfiservices_network_failed";
                    } else if (i == 3) {
                        sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_mfi_server_maintenance));
                        str = "dlg_text_warning_mfi_server_maintenance";
                    } else if (i == 4) {
                        sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_delete_card));
                        str = "dlg_text_warning_delete_card";
                        if (!TextUtils.isEmpty(completeState.getErrorCode())) {
                            sb.append(CardDetailFragment.this.getString(R.string.dlg_error_code, completeState.getErrorCode()));
                        }
                        if (!TextUtils.isEmpty(completeState.getWarCode())) {
                            sb.append(CardDetailFragment.this.getString(R.string.dlg_error_code_mfc, completeState.getWarCode()));
                        }
                    } else if (i == 7) {
                        sb.append(CardDetailFragment.this.getString(R.string.dlg_text_warning_inside_transit_gate_error));
                        str = "dlg_text_warning_inside_transit_gate_error";
                    }
                    if (!TextUtils.isEmpty(sb)) {
                        CardDetailFragment.this.stampWithJudgeActivityState(MfmStamp.Event.SCREEN_WARNING, str);
                        CardDetailFragment.this.showWarningDialogWithButtonListener(sb.toString());
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

    private void setCardStatus(MyCardInfoAgent.CardStatusForCardDetailUI cardStatusForCardDetailUI) {
        this.mActiveView.setVisibility(8);
        this.mEnableBt.setVisibility(8);
        this.mRecoveryBt.setVisibility(8);
        if (MyCardInfoAgent.CardStatusForCardDetailUI.ACTIVE == cardStatusForCardDetailUI) {
            this.mActiveView.setVisibility(0);
        } else if (MyCardInfoAgent.CardStatusForCardDetailUI.INACTIVE == cardStatusForCardDetailUI) {
            this.mEnableBt.setVisibility(0);
        } else if (MyCardInfoAgent.CardStatusForCardDetailUI.RECOVERY == cardStatusForCardDetailUI) {
            this.mRecoveryBt.setVisibility(0);
        }
    }

    private void setCardDeposit(boolean z) {
        this.mCardDepositLayout.setVisibility(8);
        if (z) {
            this.mCardDepositLayout.setVisibility(0);
        }
    }

    private class TransitPassInfoAdapter extends BaseAdapter {
        List<TransitPassInfoAgent> transitPassInfoAgentList;

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
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

        TransitPassInfoAdapter(List<TransitPassInfoAgent> list) {
            this.transitPassInfoAgentList = list;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.transitPassInfoAgentList.size();
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            try {
                if (view == null) {
                    view = LayoutInflater.from(CardDetailFragment.this.getContext()).inflate(R.layout.list_item_sp_server, viewGroup, false);
                    viewHolder = new ViewHolder();
                    viewHolder.mTransitTitle = (TextView) view.findViewById(R.id.tv_transit_title);
                    viewHolder.mCommuterPassNameTv = (TextView) view.findViewById(R.id.tv_commuter_pass_name);
                    viewHolder.mSection1FromTv = (TextView) view.findViewById(R.id.tv_section1_from);
                    viewHolder.mSection1ToTv = (TextView) view.findViewById(R.id.tv_section1_to);
                    viewHolder.mSection1Layout = view.findViewById(R.id.ll_section1_layout);
                    viewHolder.mSection2Layout = view.findViewById(R.id.ll_section2_layout);
                    viewHolder.mSection2FromTv = (TextView) view.findViewById(R.id.tv_section2_from);
                    viewHolder.mSection2ToTv = (TextView) view.findViewById(R.id.tv_section2_to);
                    viewHolder.mAdditionalInfoTv = (TextView) view.findViewById(R.id.tv_additional_info);
                    viewHolder.mViaTv = (TextView) view.findViewById(R.id.tv_transit_via);
                    viewHolder.mIssuerNameTv = (TextView) view.findViewById(R.id.tv_issuer_name);
                    viewHolder.mTermOfValidityLayout = view.findViewById(R.id.ll_term_of_validity_layout);
                    viewHolder.mTermOfValidityKeyTv = (TextView) view.findViewById(R.id.tv_term_of_validity_key);
                    viewHolder.mTermOfValidityStartTv = (TextView) view.findViewById(R.id.tv_term_of_validity_start);
                    viewHolder.mTermOfValiditySeparate = (TextView) view.findViewById(R.id.tv_term_of_validity_separate);
                    viewHolder.mTermOfValidityEndTv = (TextView) view.findViewById(R.id.tv_term_of_validity_end);
                    viewHolder.mTermOfValidityExtensionTv = (TextView) view.findViewById(R.id.tv_term_of_validity_extension);
                    viewHolder.mOptionalInfoListView = (ScrollListView) view.findViewById(R.id.lv_optional_info_view);
                    view.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) view.getTag();
                }
                updateListItem(viewHolder, i);
            } catch (Exception e) {
                CardDetailFragment.this.notifyException(e);
            }
            return view;
        }

        private void updateListItem(ViewHolder viewHolder, int i) {
            String transitPassName = this.transitPassInfoAgentList.get(i).getTransitPassName();
            String category = this.transitPassInfoAgentList.get(i).getCategory();
            CardDetailFragment.this.TextViewShow(viewHolder.mTransitTitle, transitPassName + " " + category);
            CardDetailFragment.this.TextViewShow(viewHolder.mCommuterPassNameTv, this.transitPassInfoAgentList.get(i).getCommuterPassName());
            String section1From = this.transitPassInfoAgentList.get(i).getSection1From();
            String section1To = this.transitPassInfoAgentList.get(i).getSection1To();
            if (section1From == null && section1To == null) {
                viewHolder.mSection1Layout.setVisibility(8);
            } else {
                viewHolder.mSection1Layout.setVisibility(0);
                CardDetailFragment.this.TextViewShow(viewHolder.mSection1FromTv, section1From);
                CardDetailFragment.this.TextViewShow(viewHolder.mSection1ToTv, section1To);
            }
            String section2From = this.transitPassInfoAgentList.get(i).getSection2From();
            String section2To = this.transitPassInfoAgentList.get(i).getSection2To();
            if (section2From == null && section2To == null) {
                viewHolder.mSection2Layout.setVisibility(8);
            } else {
                viewHolder.mSection2Layout.setVisibility(0);
                CardDetailFragment.this.TextViewShow(viewHolder.mSection2FromTv, section2From);
                CardDetailFragment.this.TextViewShow(viewHolder.mSection2ToTv, section2To);
            }
            CardDetailFragment.this.TextViewShow(viewHolder.mAdditionalInfoTv, this.transitPassInfoAgentList.get(i).getAdditionalInformation());
            CardDetailFragment.this.TextViewShow(viewHolder.mViaTv, this.transitPassInfoAgentList.get(i).getVia());
            CardDetailFragment.this.TextViewShow(viewHolder.mIssuerNameTv, this.transitPassInfoAgentList.get(i).getIssuerName());
            String termOfValidityKey = this.transitPassInfoAgentList.get(i).getTermOfValidityKey();
            String termOfValidityStart = this.transitPassInfoAgentList.get(i).getTermOfValidityStart();
            String termOfValidityEnd = this.transitPassInfoAgentList.get(i).getTermOfValidityEnd();
            boolean termOfValidityExtension = this.transitPassInfoAgentList.get(i).getTermOfValidityExtension();
            if (termOfValidityKey == null && termOfValidityStart == null && termOfValidityEnd == null && !termOfValidityExtension) {
                viewHolder.mTermOfValidityLayout.setVisibility(8);
            } else {
                viewHolder.mTermOfValidityLayout.setVisibility(0);
                CardDetailFragment.this.TextViewShow(viewHolder.mTermOfValidityKeyTv, termOfValidityKey);
                CardDetailFragment.this.TextViewShow(viewHolder.mTermOfValidityStartTv, termOfValidityStart);
                CardDetailFragment.this.TextViewShow(viewHolder.mTermOfValidityEndTv, termOfValidityEnd);
                if (termOfValidityStart != null || termOfValidityEnd != null) {
                    CardDetailFragment.this.TextViewShow(viewHolder.mTermOfValiditySeparate, CardDetailFragment.this.getString(R.string.card_item_label_train_detail_screen_period_mark));
                } else {
                    CardDetailFragment.this.TextViewShow(viewHolder.mTermOfValiditySeparate, "");
                }
                CardDetailFragment.this.TextViewShow(viewHolder.mTermOfValidityExtensionTv, termOfValidityExtension ? CardDetailFragment.this.getString(R.string.card_item_label_detail_screen_continuity) : "");
            }
            List<Map<String, String>> optionalInfoList = this.transitPassInfoAgentList.get(i).getOptionalInfoList();
            ArrayList arrayList = new ArrayList();
            for (Map<String, String> map : optionalInfoList) {
                if (map.get(TransitPassInfoAgent.OPTIONAL_INFO_KEY) != null || map.get(TransitPassInfoAgent.OPTIONAL_INFO_VALUE) != null) {
                    arrayList.add(map);
                }
            }
            if (arrayList.size() > 0) {
                viewHolder.mOptionalInfoListView.setAdapter((ListAdapter) new SimpleAdapter(CardDetailFragment.this.getContext(), arrayList, R.layout.list_item_optional_info, new String[]{TransitPassInfoAgent.OPTIONAL_INFO_KEY, TransitPassInfoAgent.OPTIONAL_INFO_VALUE}, new int[]{R.id.tv_optional_info_key, R.id.tv_optional_info_value}));
                viewHolder.mOptionalInfoListView.setVisibility(0);
            } else {
                viewHolder.mOptionalInfoListView.setVisibility(8);
            }
        }
    }

    private class LinkAppItemAdapter extends BaseAdapter {
        List<LinkageAgent> mLinkageAgentList;

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        private class ViewHolder {
            ImageView mLinkAppIconIv;
            TextView mLinkAppNameTv;

            private ViewHolder() {
            }
        }

        LinkAppItemAdapter(List<LinkageAgent> list) {
            this.mLinkageAgentList = list;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.mLinkageAgentList.size();
        }

        @Override // android.widget.Adapter
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder;
            try {
                if (view == null) {
                    view = LayoutInflater.from(CardDetailFragment.this.getContext()).inflate(R.layout.list_item_link_app, viewGroup, false);
                    viewHolder = new ViewHolder();
                    viewHolder.mLinkAppIconIv = (ImageView) view.findViewById(R.id.iv_link_app_icon);
                    viewHolder.mLinkAppNameTv = (TextView) view.findViewById(R.id.tv_link_app_name);
                    view.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) view.getTag();
                }
                final LinkageAgent linkageAgent = this.mLinkageAgentList.get(i);
                CardDetailFragment.this.TextViewShow(viewHolder.mLinkAppNameTv, linkageAgent.getLinkageName());
                if (linkageAgent.getAppIcon() != null) {
                    CardDetailFragment.this.ImageViewShow(viewHolder.mLinkAppIconIv, linkageAgent.getAppIcon());
                } else {
                    CardDetailFragment.this.getFocusedCard().orderIconImg(linkageAgent.getAppIconUrl(), new MyCardInfoAgent.OrderImgListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.LinkAppItemAdapter.1
                        @Override // com.felicanetworks.mfm.main.presenter.agent.MyCardInfoAgent.OrderImgListener
                        public void onComplete(Bitmap bitmap) {
                            try {
                                if (CardDetailFragment.this.getActivity() == null || CardDetailFragment.this.getActivity().isFinishing() || bitmap == null) {
                                    return;
                                }
                                CardDetailFragment.this.ImageViewShow(viewHolder.mLinkAppIconIv, bitmap);
                            } catch (Exception e) {
                                CardDetailFragment.this.notifyException(e);
                            }
                        }
                    });
                }
                view.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.LinkAppItemAdapter.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        Intent intent;
                        try {
                            AnalysisManager.stamp(MfmStamp.Event.ACTION_LINKAGE_APP, Integer.valueOf(i + 1));
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
            } catch (Exception e) {
                CardDetailFragment.this.notifyException(e);
            }
            return view;
        }
    }

    private class CardDetailHistoryAdapter extends BaseAdapter {
        static final int SHOW_MIN_COUNT = 3;
        private List<HistoryAgent> mHisoryList;
        private LayoutInflater mInflater;
        private Boolean mIsMore;
        private int mLayoutId;

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        class ViewItem {
            TextView mDate;
            TextView mMoney;
            TextView mPlus;
            TextView mType;

            ViewItem() {
            }
        }

        CardDetailHistoryAdapter(Context context, int i, List<HistoryAgent> list, Boolean bool) {
            this.mInflater = LayoutInflater.from(context);
            this.mLayoutId = i;
            this.mHisoryList = list;
            this.mIsMore = bool;
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewItem viewItem;
            String string;
            if (view == null) {
                view = this.mInflater.inflate(this.mLayoutId, (ViewGroup) null);
                viewItem = new ViewItem();
                viewItem.mDate = (TextView) view.findViewById(R.id.tv_history_date);
                viewItem.mType = (TextView) view.findViewById(R.id.tv_history_type);
                viewItem.mMoney = (TextView) view.findViewById(R.id.tv_use_money);
                viewItem.mPlus = (TextView) view.findViewById(R.id.tv_use_money_plus);
                view.setTag(viewItem);
            } else {
                viewItem = (ViewItem) view.getTag();
            }
            viewItem.mDate.setText(this.mHisoryList.get(i).getDate());
            CardDetailFragment.this.getString(R.string.card_item_text_usage_history_detail_screen_use_amt);
            int i2 = AnonymousClass14.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$HistoryAgent$UseType[this.mHisoryList.get(i).getUseType().ordinal()];
            if (i2 == 1) {
                string = CardDetailFragment.this.getString(R.string.card_item_text_usage_history_detail_screen_charge);
                CardDetailFragment.this.getString(R.string.card_item_text_usage_history_detail_screen_charge_amt);
            } else if (i2 == 2) {
                string = CardDetailFragment.this.getString(R.string.card_item_text_usage_history_detail_screen_payment);
            } else if (i2 == 3) {
                string = CardDetailFragment.this.getString(R.string.card_item_text_usage_history_detail_screen_traffic);
            } else {
                string = i2 != 4 ? "" : CardDetailFragment.this.getString(R.string.card_item_text_usage_history_detail_screen_other);
            }
            viewItem.mType.setText(string);
            viewItem.mMoney.setText(NumberFormat.getNumberInstance().format(this.mHisoryList.get(i).getMoney()));
            if (this.mHisoryList.get(i).getIsPlus()) {
                viewItem.mPlus.setVisibility(0);
            } else {
                viewItem.mPlus.setVisibility(8);
            }
            view.setLayoutParams(new LinearLayout.LayoutParams(-1, 120));
            return view;
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
        public Object getItem(int i) {
            return Integer.valueOf(i);
        }
    }

    private class OtherItemAdapter extends BaseAdapter {
        private final List<LinkageAgent> linkageList;
        private final LayoutInflater mInflater;

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        private class ViewHolder {
            TextView mText;

            private ViewHolder() {
            }
        }

        OtherItemAdapter(Context context, List<LinkageAgent> list) {
            this.mInflater = LayoutInflater.from(context);
            this.linkageList = list;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.linkageList.size();
        }

        @Override // android.widget.Adapter
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            try {
                if (view == null) {
                    view = this.mInflater.inflate(R.layout.list_item_other, viewGroup, false);
                    viewHolder = new ViewHolder();
                    viewHolder.mText = (TextView) view.findViewById(R.id.tv_other);
                    view.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) view.getTag();
                }
                view.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.OtherItemAdapter.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        try {
                            OtherItemAdapter.this.doOtherAction(i);
                        } catch (Exception unused) {
                            CardDetailFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                        }
                    }
                });
                viewHolder.mText.setText(this.linkageList.get(i).getLinkageName());
            } catch (Exception e) {
                CardDetailFragment.this.notifyException(e);
            }
            return view;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void doOtherAction(int i) {
            try {
                LinkageAgent linkageAgent = this.linkageList.get(i);
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
                int i2 = AnonymousClass14.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$LinkageAgent$LinkageType[linkageAgent.getLinkageType().ordinal()];
                if (i2 == 1) {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_LINKAGE_SITE, Integer.valueOf(i + 1));
                } else if (i2 == 2) {
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

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.views.CardDetailFragment$14, reason: invalid class name */
    static /* synthetic */ class AnonymousClass14 {
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
    public void TextViewShow(TextView textView, String str) {
        if (TextUtils.isEmpty(str)) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
            textView.setText(str);
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
    public void showWarningDialogWithButtonListener(String str) {
        showWarningDialog(str).setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.12
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

    private int findInitIndex(List<MyCardInfoAgent> list, String str, String str2) {
        for (int i = 0; i < list.size(); i++) {
            MyCardInfoAgent myCardInfoAgent = list.get(i);
            if (TextUtils.equals(str, myCardInfoAgent.getServiceId()) && TextUtils.equals(str2, myCardInfoAgent.getCid())) {
                return i;
            }
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (TextUtils.equals(str, list.get(i2).getServiceId())) {
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
    public void reload(String str, String str2) {
        this.focusedServiceId = str;
        this.focusedCardId = str2;
        initData(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCardOperationBtnEnable(boolean z) {
        this.mReloadBtn.setEnabled(z);
        this.mEnableBt.setEnabled(z);
        this.mRecoveryBt.setEnabled(z);
        this.mCloseBtn.setEnabled(z);
        this.mCardDepositLayout.setEnabled(z);
        int childCount = this.mLinkAppListView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            this.mLinkAppListView.getChildAt(i).setEnabled(z);
        }
        int childCount2 = this.mOtherGridView.getChildCount();
        for (int i2 = 0; i2 < childCount2; i2++) {
            this.mOtherGridView.getChildAt(i2).setEnabled(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDeleteCompleteDialog() {
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_17_08, new Object[0]);
        CustomDialogFragment customDialogFragmentCreateSingleChoiceDialog = DialogFactory.createSingleChoiceDialog(((FragmentActivity) Objects.requireNonNull(getActivity())).getApplicationContext());
        customDialogFragmentCreateSingleChoiceDialog.setText(getString(R.string.dlg_text_delete_card_complete));
        customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.CardDetailFragment.13
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                CardDetailFragment.this.reload();
                return true;
            }
        });
        showNormalDialog(customDialogFragmentCreateSingleChoiceDialog);
    }
}
