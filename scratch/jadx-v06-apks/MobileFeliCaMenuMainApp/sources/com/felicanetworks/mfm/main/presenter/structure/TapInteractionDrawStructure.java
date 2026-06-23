package com.felicanetworks.mfm.main.presenter.structure;

import android.content.ComponentName;
import android.content.Intent;
import com.felicanetworks.mfm.main.policy.sg.Sg;

/* JADX INFO: loaded from: classes.dex */
public class TapInteractionDrawStructure extends CloseDrawStructure {
    public TapInteractionDrawStructure() {
        super(StructureType.TAP_INTERACTION);
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
}
