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

/* JADX INFO: loaded from: classes3.dex */
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

    /* JADX DEBUG: Method merged with bridge method: bind(Ljava/lang/Object;)V */
    @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceViewHolder
    public void bind(Void data) {
    }

    MyServiceHeaderViewHolder(ViewGroup parent, CentralDrawStructure structure, final OnClickHeaderListener listener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_myservice_header, parent, false), structure);
        this.listener = listener;
        View viewFindViewById = this.itemView.findViewById(R.id.nfcOffBar);
        this.nfcOffBar = viewFindViewById;
        this.nfcOffButton = this.itemView.findViewById(R.id.nfcOffButton);
        View viewFindViewById2 = this.itemView.findViewById(R.id.mfiLoginBar);
        this.mfiLoginBar = viewFindViewById2;
        View viewFindViewById3 = this.itemView.findViewById(R.id.mfiLoginButton);
        this.mfiLoginButton = viewFindViewById3;
        View viewFindViewById4 = this.itemView.findViewById(R.id.mfiLoginBarClose);
        this.mfiLoginBarClose = viewFindViewById4;
        this.noticeBar = this.itemView.findViewById(R.id.noticeBar);
        View viewFindViewById5 = this.itemView.findViewById(R.id.noticeBarButton);
        this.noticeBarButton = viewFindViewById5;
        View viewFindViewById6 = this.itemView.findViewById(R.id.lockedMessage);
        this.lockedMessage = viewFindViewById6;
        View viewFindViewById7 = this.itemView.findViewById(R.id.inductionMessage);
        this.inductionMessage = viewFindViewById7;
        NoticeFuncAgent noticeFunc = structure.getNoticeFunc();
        this.notice = noticeFunc;
        CentralFuncAgent centralFunc = structure.getCentralFunc();
        this.centralFuncAgent = centralFunc;
        viewFindViewById.setVisibility(structure.canUseWirelessFeliCa() ? 8 : 0);
        viewFindViewById2.setVisibility(structure.isShowLinkGoogleAccount() ? 0 : 8);
        viewFindViewById6.setVisibility(structure.isFelicaLock() ? 0 : 8);
        viewFindViewById7.setVisibility((structure.isFelicaLock() || structure.hasRegisteredServiceOrDeleteCardService()) ? 8 : 0);
        invalidateNoticeBar();
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceHeaderViewHolder.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                listener.onClickNfcOffBar();
            }
        });
        viewFindViewById3.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceHeaderViewHolder.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                listener.onClickMfiLoginBar();
            }
        });
        viewFindViewById4.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceHeaderViewHolder.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MyServiceHeaderViewHolder.this.mfiLoginBar.setVisibility(8);
                listener.onClickMfiLoginClose();
            }
        });
        viewFindViewById5.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceHeaderViewHolder.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                listener.onClickNoticeBar();
            }
        });
        noticeFunc.registerChangeDataListener(this);
        centralFunc.registerChangeDataListener(parent.getContext(), this);
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

    public void update() {
        int i = 8;
        this.nfcOffBar.setVisibility(this.structure.canUseWirelessFeliCa() ? 8 : 0);
        this.mfiLoginBar.setVisibility(this.structure.isShowLinkGoogleAccount() ? 0 : 8);
        this.lockedMessage.setVisibility(this.structure.isFelicaLock() ? 0 : 8);
        View view = this.inductionMessage;
        if (!this.structure.isFelicaLock() && !this.structure.hasRegisteredServiceOrDeleteCardService()) {
            i = 0;
        }
        view.setVisibility(i);
        invalidateNoticeBar();
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
    public void onNewArrival(NoticeInfoAgent data, boolean isNew) {
        invalidateNoticeBar();
    }

    private void invalidateNfcOffWarning(boolean isNfcEnable) {
        if (isNfcEnable) {
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
    public void onNfcSettingChange(boolean isNfcEnable) {
        invalidateNfcOffWarning(isNfcEnable);
    }
}
