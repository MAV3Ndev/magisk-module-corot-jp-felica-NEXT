package com.felicanetworks.mfm.main.model;

import com.felicanetworks.mfm.main.model.CentralFunc;
import com.felicanetworks.mfm.main.model.MfiCardFunc;
import com.felicanetworks.mfm.main.model.info.BannerInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.info.NoticeInfo;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public interface NoticeFunc {

    public interface OrderBannerListener {
        void onSuccess(List<BannerInfo> list);
    }

    public enum SyncType {
        ACCEPT,
        ALL
    }

    void cancel();

    List<NoticeInfo> getDataList();

    NoticeInfo getNotice(String nid);

    void invalidateServiceRegisterState();

    boolean isEnableNoticeSetting();

    void orderBanner(OrderBannerListener listener);

    void setDetailAccess(String nid);

    void setNoticeSetting(boolean noticeSetting);

    void setReadStatus(String nid);

    void setServiceRegisterState(Map<String, MyServiceInfo.RegisterState> registerStateMap, CentralFunc.CompiledState simCompiledState, MfiCardFunc.CompiledState mficCompiledState) throws MfmException;

    void syncServer(SyncType syncType);

    int unreadCount();
}
