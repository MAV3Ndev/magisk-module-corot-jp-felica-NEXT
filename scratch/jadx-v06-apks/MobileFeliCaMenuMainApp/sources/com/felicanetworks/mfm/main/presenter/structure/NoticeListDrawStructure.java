package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.presenter.agent.IFuncNotice;
import com.felicanetworks.mfm.main.presenter.agent.NoticeFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.NoticeInfoAgent;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;
import com.felicanetworks.mfm.main.view.views.ContentTabsFragment;

/* JADX INFO: loaded from: classes.dex */
public class NoticeListDrawStructure extends PrimaryDrawStructure implements IFuncNotice {
    private NoticeFuncAgent _agent;
    private ContentTabsFragment.TabType _centralTabType;

    public NoticeListDrawStructure(NoticeFuncAgent noticeFuncAgent, boolean z, boolean z2, boolean z3, String str) {
        super(StructureType.NOTICE_LIST, z, z2, z3, str);
        this._centralTabType = null;
        this._agent = noticeFuncAgent;
    }

    public void actSelect(NoticeInfoAgent noticeInfoAgent) {
        StateController.postStateEvent(StateMachine.Event.EV_NOTICE_DETAIL, this, noticeInfoAgent);
    }

    public void setCentralTabType(ContentTabsFragment.TabType tabType) {
        this._centralTabType = tabType;
    }

    public ContentTabsFragment.TabType getCentralTabType() {
        return this._centralTabType;
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.IFuncNotice
    public NoticeFuncAgent getNoticeFunc() {
        return this._agent;
    }
}
