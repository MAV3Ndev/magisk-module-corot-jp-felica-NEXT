package com.felicanetworks.mfm.main.model;

import com.felicanetworks.mfm.main.model.CentralFunc;
import com.felicanetworks.mfm.main.model.MfiCardFunc;
import com.felicanetworks.mfm.main.model.info.BannerInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.info.NoticeInfo;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
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

    NoticeInfo getNotice(String str);

    void invalidateServiceRegisterState();

    boolean isEnableNoticeSetting();

    void orderBanner(OrderBannerListener orderBannerListener);

    void setDetailAccess(String str);

    void setNoticeSetting(boolean z);

    void setReadStatus(String str);

    void setServiceRegisterState(Map<String, MyServiceInfo.RegisterState> map, CentralFunc.CompiledState compiledState, MfiCardFunc.CompiledState compiledState2) throws MfmException;

    void syncServer(SyncType syncType);

    int unreadCount();
}
