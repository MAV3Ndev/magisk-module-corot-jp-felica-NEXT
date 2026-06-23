package com.felicanetworks.mfm.main.presenter.agent;

import android.content.Intent;
import android.graphics.Bitmap;
import com.felicanetworks.mfm.main.model.info.BannerInfo;

/* JADX INFO: loaded from: classes.dex */
public class BannerInfoAgent extends InfoAgent {
    private BannerInfo _client;

    public BannerInfoAgent(BannerInfo bannerInfo) {
        if (bannerInfo == null) {
            throw new IllegalArgumentException("client is null");
        }
        this._client = bannerInfo;
    }

    public String getId() {
        return this._client.getId();
    }

    public Bitmap getImage() {
        return this._client.getImage();
    }

    public Intent getDefaultIntent() {
        return this._client.getDefaultIntent();
    }
}
