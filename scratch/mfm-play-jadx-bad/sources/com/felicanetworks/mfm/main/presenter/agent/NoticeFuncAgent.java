package com.felicanetworks.mfm.main.presenter.agent;

import com.felicanetworks.mfm.main.model.NoticeFunc;
import com.felicanetworks.mfm.main.model.info.BannerInfo;
import com.felicanetworks.mfm.main.model.info.NoticeInfo;
import com.felicanetworks.mfm.main.presenter.internal.notification.NoticeChangeDataListenerLazyKeeper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class NoticeFuncAgent {
    private NoticeFunc _client;

    public interface ChangeDataListener {
        void onNewArrival(NoticeInfoAgent data, boolean isNew);
    }

    public interface OrderBannerListener {
        void onComplete(List<BannerInfoAgent> list);
    }

    public NoticeFuncAgent(NoticeFunc client) {
        if (client == null) {
            throw new IllegalArgumentException("client is null");
        }
        this._client = client;
    }

    public int unreadCount() {
        return this._client.unreadCount();
    }

    public List<NoticeInfoAgent> getInfoList() {
        ArrayList arrayList = new ArrayList();
        List<NoticeInfo> dataList = this._client.getDataList();
        if (dataList != null) {
            Iterator<NoticeInfo> it = dataList.iterator();
            while (it.hasNext()) {
                arrayList.add(new NoticeInfoAgent(it.next()));
            }
        }
        return arrayList;
    }

    public boolean isEnableNoticeSetting() {
        return this._client.isEnableNoticeSetting();
    }

    public void setNoticeSetting(boolean enable) {
        this._client.setNoticeSetting(enable);
    }

    public void setNoticeInitialSetting(boolean enabled) {
        this._client.setNoticeSetting(enabled);
        this._client.syncServer(NoticeFunc.SyncType.ALL);
    }

    public void registerChangeDataListener(final ChangeDataListener listener) {
        NoticeChangeDataListenerLazyKeeper.setListener(listener);
    }

    public void unregisterChangeDataListener() {
        NoticeChangeDataListenerLazyKeeper.setListener(null);
    }

    public void orderBanner(final OrderBannerListener listener) {
        this._client.orderBanner(new NoticeFunc.OrderBannerListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.NoticeFuncAgent.1
            @Override // com.felicanetworks.mfm.main.model.NoticeFunc.OrderBannerListener
            public void onSuccess(List<BannerInfo> list) {
                final ArrayList arrayList = new ArrayList();
                Iterator<BannerInfo> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(new BannerInfoAgent(it.next()));
                }
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.NoticeFuncAgent.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        listener.onComplete(arrayList);
                    }
                });
            }
        });
    }
}
