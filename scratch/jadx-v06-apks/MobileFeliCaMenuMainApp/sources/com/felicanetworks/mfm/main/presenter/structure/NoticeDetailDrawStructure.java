package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.presenter.agent.NoticeInfoAgent;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes.dex */
public class NoticeDetailDrawStructure extends CloseDrawStructure {
    private NoticeInfoAgent _info;

    public NoticeDetailDrawStructure(NoticeInfoAgent noticeInfoAgent) {
        super(StructureType.NOTICE_DETAIL);
        this._info = noticeInfoAgent;
    }

    public void actLaunchExternalApp() {
        StateController.postStateEvent(StateMachine.Event.EV_NOTICE_DETAIL_SITE_ACCESS, this, this._info);
    }

    public NoticeInfoAgent getNoticeInfo() {
        return this._info;
    }
}
