package com.felicanetworks.mfm.main.presenter.structure;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import com.felicanetworks.mfm.main.ReceiveNfcTagActivity;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.presenter.PresenterData;
import com.felicanetworks.mfm.main.presenter.agent.NoticeFuncAgent;
import com.felicanetworks.mfm.main.presenter.internal.MfiTapPreference;
import com.felicanetworks.mfm.main.presenter.internal.ServicePreference;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes3.dex */
public class TutorialSettingsDrawStructure extends CloseDrawStructure {
    private final NoticeFuncAgent _notice_func_agent;
    private final boolean hasNfc;

    public TutorialSettingsDrawStructure(NoticeFuncAgent agent, boolean hasNfc) {
        super(StructureType.TUTORIAL_SETTINGS);
        this._notice_func_agent = agent;
        this.hasNfc = hasNfc;
    }

    public boolean isEnableNoticeSetting() {
        return this._notice_func_agent.isEnableNoticeSetting();
    }

    public boolean hasNfc() {
        return this.hasNfc;
    }

    public void setEnableCardReading(boolean enabled) {
        PackageManager packageManager = PresenterData.getInstance().getContext().getPackageManager();
        packageManager.getComponentEnabledSetting(new ComponentName(PresenterData.getInstance().getContext(), (Class<?>) ReceiveNfcTagActivity.class));
        if (enabled) {
            packageManager.setComponentEnabledSetting(new ComponentName(PresenterData.getInstance().getContext(), (Class<?>) ReceiveNfcTagActivity.class), 1, 1);
        } else {
            packageManager.setComponentEnabledSetting(new ComponentName(PresenterData.getInstance().getContext(), (Class<?>) ReceiveNfcTagActivity.class), 2, 1);
        }
    }

    public void setEnablePushReceiving(boolean enabled) {
        this._notice_func_agent.setNoticeInitialSetting(enabled);
        if (!Settings.getTIIncompatibleFlag() && Settings.DeviceType.FAVER != Settings.getDeviceType()) {
            setTapInteractionNotificationSetting(enabled);
        }
        setAccountChangeNotificationSetting(enabled);
    }

    public void actCompleted() {
        StateController.postStateEvent(StateMachine.Event.EV_COMPLETE_TUTORIAL_SETTINGS, this, new Object[0]);
    }

    private void setTapInteractionNotificationSetting(boolean b) {
        MfiTapPreference.getInstance().saveTapInteractionFlag1(PresenterData.getInstance().getContext(), b);
    }

    private void setAccountChangeNotificationSetting(boolean b) {
        ServicePreference.getInstance().saveAccountChangeHistoryNotificationSetting(PresenterData.getInstance().getContext(), b);
    }
}
