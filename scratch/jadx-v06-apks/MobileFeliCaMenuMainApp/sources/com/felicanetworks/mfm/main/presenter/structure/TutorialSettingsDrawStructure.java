package com.felicanetworks.mfm.main.presenter.structure;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import com.felicanetworks.mfm.main.ReceiveNfcTagActivity;
import com.felicanetworks.mfm.main.presenter.PresenterData;
import com.felicanetworks.mfm.main.presenter.agent.NoticeFuncAgent;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes.dex */
public class TutorialSettingsDrawStructure extends CloseDrawStructure {
    private final NoticeFuncAgent _notice_func_agent;
    private final boolean hasNfc;

    public TutorialSettingsDrawStructure(NoticeFuncAgent noticeFuncAgent, boolean z) {
        super(StructureType.TUTORIAL_SETTINGS);
        this._notice_func_agent = noticeFuncAgent;
        this.hasNfc = z;
    }

    public boolean isEnableNoticeSetting() {
        return this._notice_func_agent.isEnableNoticeSetting();
    }

    public boolean hasNfc() {
        return this.hasNfc;
    }

    public void setEnableCardReading(boolean z) {
        PackageManager packageManager = PresenterData.getInstance().getContext().getPackageManager();
        packageManager.getComponentEnabledSetting(new ComponentName(PresenterData.getInstance().getContext(), (Class<?>) ReceiveNfcTagActivity.class));
        if (z) {
            packageManager.setComponentEnabledSetting(new ComponentName(PresenterData.getInstance().getContext(), (Class<?>) ReceiveNfcTagActivity.class), 1, 1);
        } else {
            packageManager.setComponentEnabledSetting(new ComponentName(PresenterData.getInstance().getContext(), (Class<?>) ReceiveNfcTagActivity.class), 2, 1);
        }
    }

    public void setEnablePushReceiving(boolean z) {
        this._notice_func_agent.setNoticeInitialSetting(z);
    }

    public void actCompleted() {
        StateController.postStateEvent(StateMachine.Event.EV_COMPLETE_TUTORIAL_SETTINGS, this, new Object[0]);
    }
}
