package com.felicanetworks.mfm.main.view.views.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.NoticeFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.NoticeInfoAgent;
import com.felicanetworks.mfm.main.presenter.structure.CentralDrawStructure;

/* JADX INFO: loaded from: classes.dex */
public class MyServiceHeaderViewHolder extends MyServiceViewHolder<Void> implements NoticeFuncAgent.ChangeDataListener, CentralFuncAgent.ChangeDataListener {
    private final CentralFuncAgent centralFuncAgent;
    private final View inductionMessage;
    private final OnClickHeaderListener listener;
    private final View lockedMessage;
    private final View mfiLoginBar;
    private final View mfiLoginBarClose;
    private final View mfiLoginButton;
    private final View nfcOffBar;
    private final View nfcOffButton;
    private final NoticeFuncAgent notice;
    private final View noticeBar;
    private final View noticeBarButton;

    interface OnClickHeaderListener {
        void onClickMfiLoginBar();

        void onClickMfiLoginClose();

        void onClickNfcOffBar();

        void onClickNoticeBar();
    }

    @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceViewHolder
    public void bind(Void r1) {
    }

    MyServiceHeaderViewHolder(ViewGroup viewGroup, CentralDrawStructure centralDrawStructure, final OnClickHeaderListener onClickHeaderListener) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_holder_myservice_header, viewGroup, false), centralDrawStructure);
        this.listener = onClickHeaderListener;
        this.nfcOffBar = this.itemView.findViewById(R.id.nfcOffBar);
        this.nfcOffButton = this.itemView.findViewById(R.id.nfcOffButton);
        this.mfiLoginBar = this.itemView.findViewById(R.id.mfiLoginBar);
        this.mfiLoginButton = this.itemView.findViewById(R.id.mfiLoginButton);
        this.mfiLoginBarClose = this.itemView.findViewById(R.id.mfiLoginBarClose);
        this.noticeBar = this.itemView.findViewById(R.id.noticeBar);
        this.noticeBarButton = this.itemView.findViewById(R.id.noticeBarButton);
        this.lockedMessage = this.itemView.findViewById(R.id.lockedMessage);
        this.inductionMessage = this.itemView.findViewById(R.id.inductionMessage);
        this.notice = centralDrawStructure.getNoticeFunc();
        this.centralFuncAgent = centralDrawStructure.getCentralFunc();
        this.nfcOffBar.setVisibility(centralDrawStructure.canUseWirelessFeliCa() ? 8 : 0);
        this.mfiLoginBar.setVisibility(centralDrawStructure.isShowLinkGoogleAccount() ? 0 : 8);
        this.lockedMessage.setVisibility(centralDrawStructure.isFelicaLock() ? 0 : 8);
        this.inductionMessage.setVisibility((centralDrawStructure.isFelicaLock() || centralDrawStructure.hasRegisteredServiceOrDeleteCardService()) ? 8 : 0);
        invalidateNoticeBar();
        this.nfcOffBar.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceHeaderViewHolder.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                onClickHeaderListener.onClickNfcOffBar();
            }
        });
        this.mfiLoginButton.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceHeaderViewHolder.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                onClickHeaderListener.onClickMfiLoginBar();
            }
        });
        this.mfiLoginBarClose.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceHeaderViewHolder.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MyServiceHeaderViewHolder.this.mfiLoginBar.setVisibility(8);
                onClickHeaderListener.onClickMfiLoginClose();
            }
        });
        this.noticeBarButton.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceHeaderViewHolder.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                onClickHeaderListener.onClickNoticeBar();
            }
        });
        this.notice.registerChangeDataListener(this);
        this.centralFuncAgent.registerChangeDataListener(viewGroup.getContext(), this);
    }

    @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceViewHolder
    public void unbind() {
        this.notice.unregisterChangeDataListener();
        this.centralFuncAgent.unregisterChangeDataListener(this.itemView.getContext());
    }

    @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceViewHolder
    public void resume() {
        this.notice.registerChangeDataListener(this);
        invalidateNoticeBar();
        invalidateNfcOffWarning(this.structure.canUseWirelessFeliCa());
    }

    private void invalidateNoticeBar() {
        if (this.notice.unreadCount() > 0) {
            showNoticeBar();
        } else {
            dismissNoticeBar();
        }
    }

    private void showNoticeBar() {
        if (this.noticeBar.getVisibility() == 0) {
            return;
        }
        AnalysisManager.stamp(MfmStamp.Event.AUTO_DUMP_NEW_ARRIVALS, new Object[0]);
        this.noticeBar.setVisibility(0);
        this.noticeBar.startAnimation(AnimationUtils.loadAnimation(this.itemView.getContext(), R.anim.in_notice_bar));
    }

    private void dismissNoticeBar() {
        this.noticeBar.setVisibility(8);
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.NoticeFuncAgent.ChangeDataListener
    public void onNewArrival(NoticeInfoAgent noticeInfoAgent, boolean z) {
        invalidateNoticeBar();
    }

    private void invalidateNfcOffWarning(boolean z) {
        if (z) {
            dismissNfcOffWarning();
        } else {
            showNfcOffWarning();
        }
    }

    private void showNfcOffWarning() {
        if (this.nfcOffBar.getVisibility() == 0) {
            return;
        }
        this.nfcOffBar.setVisibility(0);
    }

    private void dismissNfcOffWarning() {
        this.nfcOffBar.setVisibility(8);
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.ChangeDataListener
    public void onNfcSettingChange(boolean z) {
        invalidateNfcOffWarning(z);
    }
}
