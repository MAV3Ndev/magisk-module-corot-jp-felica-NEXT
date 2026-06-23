package com.felicanetworks.mfm.main.presenter.agent;

import android.content.Intent;
import android.graphics.Bitmap;
import com.felicanetworks.mfm.main.model.info.FpServiceInfo;
import com.felicanetworks.mfm.main.presenter.PresenterData;

/* JADX INFO: loaded from: classes.dex */
public class FpServiceInfoAgent extends InfoAgent {
    private FpServiceInfo _client;

    public interface OrderImgListener {
        void onComplete(Bitmap bitmap);
    }

    public enum Type {
        POINT,
        OTHER,
        UNSUPPORTED,
        UNKNOWN
    }

    public FpServiceInfoAgent(FpServiceInfo fpServiceInfo) {
        if (fpServiceInfo == null) {
            throw new IllegalArgumentException("client is null");
        }
        this._client = fpServiceInfo;
    }

    public int getServiceNumber() {
        return this._client.getServiceNumber();
    }

    public String getServiceName() {
        return this._client.getServiceName();
    }

    public long getPoint() {
        return this._client.getPoint();
    }

    public String getPointUnit() {
        return this._client.getPointUnit();
    }

    public String getServiceProvider() {
        return this._client.getServiceProvider();
    }

    public Type getType() {
        return Type.valueOf(this._client.getType().name());
    }

    public int getColorCode() {
        return this._client.getColorCode();
    }

    public Intent getLinkIntent() {
        return this._client.getLinkIntent();
    }

    public boolean orderIconImg(final OrderImgListener orderImgListener) {
        return this._client.orderIconImg(PresenterData.getInstance().getContext(), new FpServiceInfo.OrderImgListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.FpServiceInfoAgent.1
            @Override // com.felicanetworks.mfm.main.model.info.FpServiceInfo.OrderImgListener
            public void onComplete(final Bitmap bitmap) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.FpServiceInfoAgent.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        orderImgListener.onComplete(bitmap);
                    }
                });
            }
        });
    }
}
