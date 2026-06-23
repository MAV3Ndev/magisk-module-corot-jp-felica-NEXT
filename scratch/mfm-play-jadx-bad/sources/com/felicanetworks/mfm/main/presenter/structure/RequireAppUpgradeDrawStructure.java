package com.felicanetworks.mfm.main.presenter.structure;

import android.content.Intent;
import android.net.Uri;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.presenter.PresenterData;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes3.dex */
public class RequireAppUpgradeDrawStructure extends AskDrawStructure {
    public RequireAppUpgradeDrawStructure() {
        super(StructureType.REQUIRE_APP_UPGRADE);
    }

    public Intent getDefaultIntent() {
        Intent intent;
        Exception e;
        try {
            intent = new Intent("android.intent.action.VIEW");
        } catch (Exception e2) {
            intent = null;
            e = e2;
        }
        try {
            intent.setData(Uri.parse(String.format("market://details?id=%s", PresenterData.getInstance().getContext().getPackageName())));
            intent.setPackage((String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_APP_PKG_PLAY_STORE));
            return intent;
        } catch (Exception e3) {
            e = e3;
            StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, this, new MfmException(getClass(), 1, e));
            return intent;
        }
    }
}
