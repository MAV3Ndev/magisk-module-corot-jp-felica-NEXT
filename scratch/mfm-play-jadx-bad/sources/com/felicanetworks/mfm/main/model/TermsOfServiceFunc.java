package com.felicanetworks.mfm.main.model;

import com.felicanetworks.mfm.main.model.internal.main.net.TosVersionProtocol;

/* JADX INFO: loaded from: classes3.dex */
public interface TermsOfServiceFunc {
    void cancel();

    boolean getDisplayedTos();

    int getTosVersion();

    boolean isUpgrade();

    void savePolicyConsent();

    void setDisplayedTos(boolean displayed);

    void setTosVersionProtocolResult(TosVersionProtocol.Result result);
}
