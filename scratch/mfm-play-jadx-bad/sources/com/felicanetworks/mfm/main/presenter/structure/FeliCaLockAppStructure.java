package com.felicanetworks.mfm.main.presenter.structure;

import android.content.Intent;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes3.dex */
public class FeliCaLockAppStructure extends ExternalAppStructure {
    private static final int CANCEL = 0;

    public FeliCaLockAppStructure() {
        super(StructureType.FELICA_LOCK_APP);
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionAppResult
    public void actResult(int resultCode, Intent data) {
        if (resultCode != 0) {
            StateController.postStateEvent(StateMachine.Event.EV_RESULT_LOCK_STATE_CHANGE, this, data);
        } else {
            StateController.postStateEvent(StateMachine.Event.EV_RESULT_LOCK_CANCEL, this, data);
        }
    }

    @Override // com.felicanetworks.mfm.main.presenter.structure.ExternalAppStructure
    public Intent getDefaultIntent() {
        try {
            return new Intent().setClassName((String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_APP_PKG_FELICA_LOCK), (String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_APP_CLS_FELICA_LOCK));
        } catch (Exception e) {
            LogUtil.warning(e);
            return null;
        }
    }
}
