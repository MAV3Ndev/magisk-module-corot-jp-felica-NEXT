package com.felicanetworks.mfm.main.presenter.agent;

import android.content.Intent;
import android.graphics.Bitmap;
import com.felicanetworks.mfm.main.model.info.NoticeInfo;
import com.felicanetworks.mfm.main.presenter.PresenterData;
import java.util.Date;

/* JADX INFO: loaded from: classes3.dex */
public class NoticeInfoAgent extends InfoAgent {
    private NoticeInfo _client;

    public interface OrderImgListener {
        void onComplete(Bitmap data);
    }

    public NoticeInfoAgent(NoticeInfo client) {
        if (client == null) {
            throw new IllegalArgumentException("client is null");
        }
        this._client = client;
    }

    public String getId() {
        return this._client.getId();
    }

    public boolean isRead() {
        return this._client.isRead();
    }

    public String getTitle() {
        return this._client.getTitle();
    }

    public String getMessage() {
        return this._client.getMessage();
    }

    public Date getSendDate() {
        return this._client.getSendDate();
    }

    public Date getExpirationDate() {
        return this._client.getExpirationDate();
    }

    public boolean isExpiration() {
        return this._client.isExpiration();
    }

    public boolean orderIconImg(final OrderImgListener listener) {
        return this._client.orderIconImg(PresenterData.getInstance().getContext(), new NoticeInfo.OrderImgListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.NoticeInfoAgent.1
            @Override // com.felicanetworks.mfm.main.model.info.NoticeInfo.OrderImgListener
            public void onComplete(final Bitmap data) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.NoticeInfoAgent.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        listener.onComplete(data);
                    }
                });
            }
        });
    }

    public boolean orderMsgImg(final OrderImgListener listener) {
        return this._client.orderMsgImg(PresenterData.getInstance().getContext(), new NoticeInfo.OrderImgListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.NoticeInfoAgent.2
            @Override // com.felicanetworks.mfm.main.model.info.NoticeInfo.OrderImgListener
            public void onComplete(final Bitmap data) {
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.NoticeInfoAgent.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        listener.onComplete(data);
                    }
                });
            }
        });
    }

    public Intent getLinkIntent() {
        return this._client.getLinkIntent();
    }

    public String getBtnMsg() {
        return this._client.getBtnMsg();
    }
}
