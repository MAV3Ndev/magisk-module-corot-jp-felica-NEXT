package com.felicanetworks.mfm.main.presenter.agent;

import com.felicanetworks.mfm.main.model.TermsOfServiceFunc;

/* JADX INFO: loaded from: classes.dex */
public class TermsOfServiceFuncAgent {
    private TermsOfServiceFunc _client;

    public TermsOfServiceFuncAgent(TermsOfServiceFunc termsOfServiceFunc) {
        if (termsOfServiceFunc == null) {
            throw new IllegalArgumentException("client is null");
        }
        this._client = termsOfServiceFunc;
    }

    public boolean getDisplayTos() {
        return this._client.getDisplayedTos();
    }

    public int getTosVersion() {
        return this._client.getTosVersion();
    }
}
