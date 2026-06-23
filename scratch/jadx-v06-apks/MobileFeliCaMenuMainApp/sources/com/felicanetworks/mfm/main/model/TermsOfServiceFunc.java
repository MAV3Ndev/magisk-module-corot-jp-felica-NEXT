package com.felicanetworks.mfm.main.model;

import com.felicanetworks.mfm.main.model.internal.main.net.TosVersionProtocol;

/* JADX INFO: loaded from: classes.dex */
public interface TermsOfServiceFunc {
    void cancel();

    boolean getDisplayedTos();

    int getTosVersion();

    boolean isUpgrade();

    void savePolicyConsent();

    void setDisplayedTos(boolean z);

    void setTosVersionProtocolResult(TosVersionProtocol.Result result);
}
