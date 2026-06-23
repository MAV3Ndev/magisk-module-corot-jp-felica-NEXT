package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.presenter.PresenterData;
import com.felicanetworks.mfm.main.presenter.agent.NoticeFuncAgent;
import com.felicanetworks.mfm.main.presenter.internal.MfiTapPreference;
import com.felicanetworks.mfm.main.presenter.internal.ServicePreference;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes3.dex */
public class CompleteTutorialDrawStructure extends AskDrawStructure {
    private final NoticeFuncAgent _notice_func_agent;

    public CompleteTutorialDrawStructure(NoticeFuncAgent agent) {
        super(StructureType.COMPLETE_TUTORIAL);
        this._notice_func_agent = agent;
    }

    public boolean isEnableNoticeSetting() {
        return this._notice_func_agent.isEnableNoticeSetting();
    }

    public boolean isTapInteractionNotificationSetting() {
        return MfiTapPreference.getInstance().loadTapInteractionFlag1(PresenterData.getInstance().getContext());
    }

    public boolean isEnableAccountChangeHistorySetting() {
        return ServicePreference.getInstance().loadAccountChangeHistoryNotificationSetting(PresenterData.getInstance().getContext());
    }

    public void actCompleted() {
        StateController.postStateEvent(StateMachine.Event.EP_JUDGE_TUTORIAL_COMPLETE, this, new Object[0]);
    }

    public boolean isNotificationPermissionRequirement() {
        return isEnableNoticeSetting() || isTapInteractionNotificationSetting() || isEnableAccountChangeHistorySetting();
    }
}
