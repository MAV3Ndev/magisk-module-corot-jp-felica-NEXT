package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes.dex */
public class SupportMenuDrawStructure extends PrimaryDrawStructure {
    public SupportMenuDrawStructure(boolean z, boolean z2, boolean z3, String str) {
        super(StructureType.SUPPORT_MENU, z, z2, z3, str);
    }

    public boolean isProvideOsaifuTerms() {
        try {
            String str = (String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_URL_DISCLAIMER_SECURE_ELEMENT_F);
            if (str != null) {
                return !str.isEmpty();
            }
            return false;
        } catch (Exception e) {
            LogUtil.warning(e);
            return false;
        }
    }

    public void actSelectMemory() {
        StateController.postStateEvent(StateMachine.Event.EV_MRMORY_USAGE, this, new Object[0]);
    }

    public void actTutorial() {
        StateController.postStateEvent(StateMachine.Event.EV_TUTORIAL, this, new Object[0]);
    }

    public void actAccountChange() {
        StateController.postStateEvent(StateMachine.Event.EV_ACCOUNT_CHANGE_HISTORY, this, new Object[0]);
    }
}
