package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.policy.sg.Sg;

/* JADX INFO: loaded from: classes3.dex */
public class FaqDrawStructure extends PrimaryDrawStructure {
    public FaqDrawStructure(boolean isLock, boolean isEnoughExtCardServiceInfo, boolean hasNeverLoggedIn, String mfiAccountName) {
        super(StructureType.FAQ, isLock, isEnoughExtCardServiceInfo, hasNeverLoggedIn, mfiAccountName);
    }

    public String getContentUrl() {
        return (String) Sg.getValue(Sg.Key.SETTING_URL_FAQ);
    }
}
