package com.felicanetworks.mfm.main.presenter.structure;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.presenter.PresenterData;
import com.felicanetworks.mfm.main.presenter.action.IActionAppResult;
import com.felicanetworks.mfm.main.presenter.agent.NoticeFuncAgent;
import com.felicanetworks.mfm.main.presenter.internal.MfiTapPreference;
import com.felicanetworks.mfm.main.presenter.internal.ServicePreference;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes3.dex */
public class SettingDrawStructure extends PrimaryDrawStructure implements IActionAppResult {
    private static final int RESULT_MFIC_VERSION_ERROR = 3;
    private static final int RESULT_SWITCHING_CANCEL = 0;
    private static final int RESULT_SWITCHING_FAILED = 1;
    private static final int RESULT_SWITCHING_MULTIPLE = 2;
    private static final int RESULT_SWITCHING_SUCCESS = -1;
    private static boolean mInputEnable = true;
    private NoticeFuncAgent _agent;

    public SettingDrawStructure(NoticeFuncAgent agent, boolean isLock, boolean isEnoughExtCardServiceInfo, boolean hasNeverLoggedIn, String mfiAccountName) {
        super(StructureType.SETTING, isLock, isEnoughExtCardServiceInfo, hasNeverLoggedIn, mfiAccountName);
        this._agent = agent;
    }

    public boolean isEnableNoticeSetting() {
        return this._agent.isEnableNoticeSetting();
    }

    public boolean isEnableAccountChangeHistorySetting() {
        return ServicePreference.getInstance().loadAccountChangeHistoryNotificationSetting(PresenterData.getInstance().getContext());
    }

    public void setAccountChangeNotificationSetting(boolean b) {
        ServicePreference.getInstance().saveAccountChangeHistoryNotificationSetting(PresenterData.getInstance().getContext(), b);
    }

    public boolean isRestrictCardReadSetting() {
        return ServicePreference.getInstance().loadRestrictCardReadSetting(PresenterData.getInstance().getContext());
    }

    public void setRestrictCardReadSetting(boolean b) {
        ServicePreference.getInstance().saveRestrictCardReadSetting(PresenterData.getInstance().getContext(), b);
    }

    public boolean isTapInteractionNotificationSetting() {
        return MfiTapPreference.getInstance().loadTapInteractionFlag1(PresenterData.getInstance().getContext());
    }

    public void setTapInteractionNotificationSetting(boolean b) {
        MfiTapPreference.getInstance().saveTapInteractionFlag1(PresenterData.getInstance().getContext(), b);
    }

    public boolean isStopChipAccessSetting() {
        return MfiTapPreference.getInstance().loadStopChipAccessFlag(PresenterData.getInstance().getContext());
    }

    public void setStopChipAccessSetting(boolean b) {
        MfiTapPreference.getInstance().saveStopChipAccessFlag(PresenterData.getInstance().getContext(), b);
    }

    public void actTISetting() {
        StateController.postStateEvent(StateMachine.Event.EV_TAP_INTERACTION_SETTING, this, new Object[0]);
    }

    public void actSetPushStatus(boolean pushStatus) {
        this._agent.setNoticeSetting(pushStatus);
    }

    @Override // com.felicanetworks.mfm.main.presenter.structure.PrimaryDrawStructure, com.felicanetworks.mfm.main.presenter.action.IActionAppResult
    public void actResult(int resultCode, Intent data) {
        if (resultCode != -1) {
            if (resultCode != 0) {
                if (resultCode != 1) {
                    if (resultCode != 2) {
                        if (resultCode != 3) {
                            return;
                        }
                    }
                }
            }
            StateController.postStateEvent(StateMachine.Event.EV_RESULT_MFI_ACCOUNT_CANCEL, this, new Object[0]);
            return;
        }
        StateController.postStateEvent(StateMachine.Event.EV_RESULT_MFI_ACCOUNT_COMPLETE, this, new Object[0]);
    }

    public Intent getTapInteractionIntent(boolean tapInteractionSetting) {
        String str = (String) Sg.getValue(Sg.Key.SETTING_TAP_INTERACTION_PKG);
        String str2 = (String) Sg.getValue(Sg.Key.SETTING_TAP_INTERACTION_CLS);
        String str3 = (String) Sg.getValue(Sg.Key.SETTING_TAP_INTERACTION_ACT);
        String str4 = (String) Sg.getValue(Sg.Key.SETTING_TAP_INTERACTION_EXT);
        Intent intent = new Intent(str3);
        intent.setComponent(new ComponentName(str, str2));
        intent.addFlags(32);
        intent.putExtra(str4, tapInteractionSetting);
        return intent;
    }

    public Intent getStopChipAccessIntent(boolean stopChipAccessSetting) {
        String str = (String) Sg.getValue(Sg.Key.SETTING_TAP_INTERACTION_PKG);
        String str2 = (String) Sg.getValue(Sg.Key.SETTING_TAP_INTERACTION_CLS);
        String str3 = (String) Sg.getValue(Sg.Key.SETTING_STOP_CHIP_ACCESS_ACT);
        String str4 = (String) Sg.getValue(Sg.Key.SETTING_STOP_CHIP_ACCESS_EXT);
        Intent intent = new Intent(str3);
        intent.setComponent(new ComponentName(str, str2));
        intent.addFlags(32);
        intent.putExtra(str4, stopChipAccessSetting);
        return intent;
    }

    public static boolean enableInput() {
        if (!mInputEnable) {
            return false;
        }
        mInputEnable = false;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.structure.SettingDrawStructure.1
            @Override // java.lang.Runnable
            public void run() {
                boolean unused = SettingDrawStructure.mInputEnable = true;
            }
        }, 1000L);
        return true;
    }

    public boolean isNotificationPermissionRequirement() {
        return isEnableNoticeSetting() || isTapInteractionNotificationSetting() || isEnableAccountChangeHistorySetting();
    }
}
