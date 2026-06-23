package com.felicanetworks.mfm.main.presenter.agent;

import com.felicanetworks.mfm.main.model.info.OptionalInfo;

/* JADX INFO: loaded from: classes3.dex */
public class OptionalInfoAgent {
    private OptionalInfo _client;

    public enum OptionalInfoAgentType {
        WEB,
        CONTACT
    }

    public OptionalInfoAgent(OptionalInfo client) {
        this._client = client;
    }

    public String getOptionalInfoKey() {
        return this._client.getOptionalInfoKey();
    }

    public String getOptionalInfoValue() {
        return this._client.getOptionalInfoValue();
    }
}
