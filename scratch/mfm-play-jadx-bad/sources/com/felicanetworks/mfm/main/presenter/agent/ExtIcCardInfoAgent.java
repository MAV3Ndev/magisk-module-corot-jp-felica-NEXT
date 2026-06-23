package com.felicanetworks.mfm.main.presenter.agent;

import android.graphics.Bitmap;
import com.felicanetworks.mfm.main.model.info.ExtIcCardInfo;
import com.felicanetworks.mfm.main.presenter.PresenterData;

/* JADX INFO: loaded from: classes3.dex */
public class ExtIcCardInfoAgent extends ServiceInfoAgent {
    private ExtIcCardInfo _client;

    public ExtIcCardInfoAgent(ExtIcCardInfo client) {
        super(client);
        this._client = client;
    }

    public String getGuidanceId() {
        return this._client.getGuidance().getId();
    }

    public String getGuidanceDescription() {
        return this._client.getGuidance().getDescription();
    }

    public Bitmap getGuidanceImage() {
        return this._client.getGuidance().getImage(PresenterData.getInstance().getContext());
    }

    public boolean hasFelicaPocket() {
        return this._client.hasFelicaPocket();
    }
}
