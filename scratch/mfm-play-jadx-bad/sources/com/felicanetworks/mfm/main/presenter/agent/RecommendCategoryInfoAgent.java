package com.felicanetworks.mfm.main.presenter.agent;

import android.text.TextUtils;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class RecommendCategoryInfoAgent {
    private List<RecommendInfoAgent> _recommendInfoAgentList;

    public RecommendCategoryInfoAgent(List<RecommendInfoAgent> recommendInfoAgentList) {
        this._recommendInfoAgentList = recommendInfoAgentList;
    }

    public List<RecommendInfoAgent> getRecommendList() {
        return this._recommendInfoAgentList;
    }

    public String getTitle() {
        return this._recommendInfoAgentList.get(0).getCategoryTitle();
    }

    public String getId() {
        return this._recommendInfoAgentList.get(0).getCategoryId();
    }

    void addRecommend(RecommendInfoAgent recommendInfoAgent) {
        if (this._recommendInfoAgentList.isEmpty() || TextUtils.equals(this._recommendInfoAgentList.get(0).getCategoryId(), recommendInfoAgent.getCategoryId())) {
            this._recommendInfoAgentList.add(recommendInfoAgent);
        }
    }
}
