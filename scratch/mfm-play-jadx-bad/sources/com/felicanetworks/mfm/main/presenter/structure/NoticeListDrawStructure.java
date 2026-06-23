package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.presenter.agent.IFuncNotice;
import com.felicanetworks.mfm.main.presenter.agent.NoticeFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.NoticeInfoAgent;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;
import com.felicanetworks.mfm.main.view.views.ContentTabsFragment;

/* JADX INFO: loaded from: classes3.dex */
public class NoticeListDrawStructure extends PrimaryDrawStructure implements IFuncNotice {
    private NoticeFuncAgent _agent;
    private ContentTabsFragment.TabType _centralTabType;

    public NoticeListDrawStructure(NoticeFuncAgent agent, boolean isLcok, boolean isEnoughExtCardServiceInfo, boolean hasNeverLoggedIn, String mfiAccountName) {
        super(StructureType.NOTICE_LIST, isLcok, isEnoughExtCardServiceInfo, hasNeverLoggedIn, mfiAccountName);
        this._centralTabType = null;
        this._agent = agent;
    }

    public void actSelect(NoticeInfoAgent info) {
        StateController.postStateEvent(StateMachine.Event.EV_NOTICE_DETAIL, this, info);
    }

    public void setCentralTabType(ContentTabsFragment.TabType tab) {
        this._centralTabType = tab;
    }

    public ContentTabsFragment.TabType getCentralTabType() {
        return this._centralTabType;
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.IFuncNotice
    public NoticeFuncAgent getNoticeFunc() {
        return this._agent;
    }
}
