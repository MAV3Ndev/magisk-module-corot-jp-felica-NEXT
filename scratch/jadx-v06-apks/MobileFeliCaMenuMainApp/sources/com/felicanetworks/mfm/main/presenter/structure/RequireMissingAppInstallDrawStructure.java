package com.felicanetworks.mfm.main.presenter.structure;

import android.content.Intent;
import android.net.Uri;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes.dex */
public class RequireMissingAppInstallDrawStructure extends AskDrawStructure {
    public static final int MFC_APP = 1;
    public static final int MFS_APP = 2;
    private int missingApp;

    public RequireMissingAppInstallDrawStructure(int i) {
        super(StructureType.REQUIRE_MISSING_APP_INSTALL);
        this.missingApp = i;
    }

    public Intent getMissingAppIntent() {
        String str;
        Intent intent;
        Intent intent2 = null;
        try {
            int i = this.missingApp;
            if (i == 1) {
                str = (String) Sg.getValue(Sg.Key.SETTING_MFC_PACKAGE_NAME);
            } else if (i == 2) {
                str = (String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_APP_PKG_MFS);
            } else {
                throw new Exception();
            }
            intent = new Intent("android.intent.action.VIEW");
        } catch (Exception e) {
            e = e;
        }
        try {
            intent.setData(Uri.parse(String.format("market://details?id=%s", str)));
            intent.setPackage((String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_APP_PKG_PLAY_STORE));
            return intent;
        } catch (Exception e2) {
            e = e2;
            intent2 = intent;
            StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, this, new MfmException(getClass(), 1, e));
            return intent2;
        }
    }

    public int getMissingApp() {
        return this.missingApp;
    }
}
