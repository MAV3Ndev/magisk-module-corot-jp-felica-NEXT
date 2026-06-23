package com.felicanetworks.mfm.main.presenter.agent;

import com.felicanetworks.mfm.main.model.info.RecommendInfo;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RecommendInfoAgent extends ServiceInfoAgent {
    private RecommendInfo _client;

    public RecommendInfoAgent(RecommendInfo recommendInfo) {
        super(recommendInfo);
        this._client = recommendInfo;
    }

    public String getStatus() {
        return this._client.getStatus();
    }

    public String getOverview() {
        return this._client.getOverview();
    }

    public String getDetails() {
        return this._client.getDetails();
    }

    public List<String> getProcedures() {
        return this._client.getProcedures();
    }

    public String getCategoryId() {
        return this._client.getCategoryId();
    }

    public String getCategoryTitle() {
        return this._client.getCategoryTitle();
    }
}
