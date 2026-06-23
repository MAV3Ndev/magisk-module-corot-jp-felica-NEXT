package com.felicanetworks.mfm.main.presenter.agent;

import com.felicanetworks.mfm.main.model.info.OptionalInfo;

/* JADX INFO: loaded from: classes.dex */
public class OptionalInfoAgent {
    private OptionalInfo _client;

    public enum OptionalInfoAgentType {
        WEB,
        CONTACT
    }

    public OptionalInfoAgent(OptionalInfo optionalInfo) {
        this._client = optionalInfo;
    }

    public String getOptionalInfoKey() {
        return this._client.getOptionalInfoKey();
    }

    public String getOptionalInfoValue() {
        return this._client.getOptionalInfoValue();
    }
}
