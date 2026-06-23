package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.policy.sg.Sg;

/* JADX INFO: loaded from: classes.dex */
public class FaqDrawStructure extends PrimaryDrawStructure {
    public FaqDrawStructure(boolean z, boolean z2, boolean z3, String str) {
        super(StructureType.FAQ, z, z2, z3, str);
    }

    public String getContentUrl() {
        return (String) Sg.getValue(Sg.Key.SETTING_URL_FAQ);
    }
}
