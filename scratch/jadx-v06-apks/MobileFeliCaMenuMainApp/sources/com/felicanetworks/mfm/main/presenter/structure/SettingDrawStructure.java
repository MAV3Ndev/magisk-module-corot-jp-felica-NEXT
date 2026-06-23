package com.felicanetworks.mfm.main.presenter.structure;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.presenter.PresenterData;
import com.felicanetworks.mfm.main.presenter.action.IActionAppResult;
import com.felicanetworks.mfm.main.presenter.agent.NoticeFuncAgent;
import com.felicanetworks.mfm.main.presenter.internal.ServicePreference;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes.dex */
public class SettingDrawStructure extends PrimaryDrawStructure implements IActionAppResult {
    private static final int RESULT_MFIC_VERSION_ERROR = 3;
    private static final int RESULT_SWITCHING_CANCEL = 0;
    private static final int RESULT_SWITCHING_FAILED = 1;
    private static final int RESULT_SWITCHING_MULTIPLE = 2;
    private static final int RESULT_SWITCHING_SUCCESS = -1;
    private static boolean mInputEnable = true;
    private NoticeFuncAgent _agent;

    public SettingDrawStructure(NoticeFuncAgent noticeFuncAgent, boolean z, boolean z2, boolean z3, String str) {
        super(StructureType.SETTING, z, z2, z3, str);
        this._agent = noticeFuncAgent;
    }

    public boolean isEnableNoticeSetting() {
        return this._agent.isEnableNoticeSetting();
    }

    public boolean isEnableAccountChangeHistorySetting() {
        return ServicePreference.getInstance().loadAccountChangeHistoryNotificationSetting(PresenterData.getInstance().getContext());
    }

    public void setAccountChangeNotificationSetting(boolean z) {
        ServicePreference.getInstance().saveAccountChangeHistoryNotificationSetting(PresenterData.getInstance().getContext(), z);
    }

    public void actTISetting() {
        StateController.postStateEvent(StateMachine.Event.EV_TAP_INTERACTION_SETTING, this, new Object[0]);
    }

    public void actSetPushStatus(boolean z) {
        this._agent.setNoticeSetting(z);
    }

    @Override // com.felicanetworks.mfm.main.presenter.structure.PrimaryDrawStructure, com.felicanetworks.mfm.main.presenter.action.IActionAppResult
    public void actResult(int i, Intent intent) {
        if (i != -1) {
            if (i != 0) {
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
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

    public Intent getTapInteractionIntent(boolean z) {
        String str = (String) Sg.getValue(Sg.Key.SETTING_TAP_INTERACTION_PKG);
        String str2 = (String) Sg.getValue(Sg.Key.SETTING_TAP_INTERACTION_CLS);
        String str3 = (String) Sg.getValue(Sg.Key.SETTING_TAP_INTERACTION_ACT);
        String str4 = (String) Sg.getValue(Sg.Key.SETTING_TAP_INTERACTION_EXT);
        Intent intent = new Intent(str3);
        intent.setComponent(new ComponentName(str, str2));
        intent.addFlags(32);
        intent.putExtra(str4, z);
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
}
