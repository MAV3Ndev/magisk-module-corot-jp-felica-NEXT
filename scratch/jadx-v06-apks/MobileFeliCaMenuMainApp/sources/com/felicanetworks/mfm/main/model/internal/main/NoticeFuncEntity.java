package com.felicanetworks.mfm.main.model.internal.main;

import com.felicanetworks.mfm.main.model.CentralFunc;
import com.felicanetworks.mfm.main.model.MfiCardFunc;
import com.felicanetworks.mfm.main.model.NoticeFunc;
import com.felicanetworks.mfm.main.model.info.BannerInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.info.NoticeInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeManager;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeData;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDataManager;
import com.felicanetworks.mfm.main.model.internal.main.FuncUtil;
import com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.presenter.internal.ServicePreference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class NoticeFuncEntity implements NoticeFunc {
    private ModelContext _context;
    private NoticeManager _legacyManager;
    private Map<String, FuncUtil.AsyncRunner> _runners = new HashMap();

    private FuncUtil.AsyncRunner getRunner(String str) {
        if (this._runners.containsKey(str)) {
            this._runners.remove(str).shutdown();
        }
        FuncUtil.AsyncRunner asyncRunner = new FuncUtil.AsyncRunner();
        this._runners.put(str, asyncRunner);
        return asyncRunner;
    }

    NoticeFuncEntity(ModelContext modelContext) {
        this._context = modelContext;
        this._legacyManager = NoticeManager.getInstance(modelContext.getAndroidContext());
        NoticeInfo.clearCache();
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.main.NoticeFuncEntity$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$NoticeFunc$SyncType;

        static {
            int[] iArr = new int[NoticeFunc.SyncType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$NoticeFunc$SyncType = iArr;
            try {
                iArr[NoticeFunc.SyncType.ALL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$NoticeFunc$SyncType[NoticeFunc.SyncType.ACCEPT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.model.NoticeFunc
    public void syncServer(NoticeFunc.SyncType syncType) {
        int i = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$model$NoticeFunc$SyncType[syncType.ordinal()];
        if (i == 1) {
            this._legacyManager.setClient();
        } else {
            if (i != 2) {
                return;
            }
            this._legacyManager.setAccept();
        }
    }

    @Override // com.felicanetworks.mfm.main.model.NoticeFunc
    public List<NoticeInfo> getDataList() {
        List<NoticeData> noticeDataList = this._legacyManager.getNoticeDataList();
        ArrayList arrayList = new ArrayList();
        Iterator<NoticeData> it = noticeDataList.iterator();
        while (it.hasNext()) {
            arrayList.add(new NoticeInfo(it.next(), this._context.getNetworkExpert()));
        }
        return arrayList;
    }

    @Override // com.felicanetworks.mfm.main.model.NoticeFunc
    public boolean isEnableNoticeSetting() {
        return "Y".equals(this._legacyManager.getPushReceiveStatus());
    }

    @Override // com.felicanetworks.mfm.main.model.NoticeFunc
    public void setNoticeSetting(boolean z) {
        this._legacyManager.setPushReceiveStatus(z ? "Y" : ServicePreference.OFF);
    }

    @Override // com.felicanetworks.mfm.main.model.NoticeFunc
    public NoticeInfo getNotice(String str) {
        NoticeData noticeData = this._legacyManager.getNoticeData(str);
        if (noticeData != null) {
            return new NoticeInfo(noticeData, this._context.getNetworkExpert());
        }
        return null;
    }

    @Override // com.felicanetworks.mfm.main.model.NoticeFunc
    public void setServiceRegisterState(Map<String, MyServiceInfo.RegisterState> map, CentralFunc.CompiledState compiledState, MfiCardFunc.CompiledState compiledState2) throws MfmException {
        if (compiledState.hasWarning() || compiledState.isWarningLackDispInfo() || compiledState.isWarningContainOldDispInfo() || compiledState2.getMfiCardState() != MfiCardFunc.CompiledState.MfiCardState.NO_PROBLEM) {
            return;
        }
        try {
            NoticeDataManager.getInstance(this._context.getAndroidContext()).setDisplayServiceList(map);
        } catch (DatabaseAccessException e) {
            throw new MfmException(getClass(), 512, e);
        }
    }

    @Override // com.felicanetworks.mfm.main.model.NoticeFunc
    public void invalidateServiceRegisterState() {
        NoticeDataManager.getInstance(this._context.getAndroidContext()).invalidateDispService();
    }

    @Override // com.felicanetworks.mfm.main.model.NoticeFunc
    public int unreadCount() {
        return this._legacyManager.unreadCount();
    }

    @Override // com.felicanetworks.mfm.main.model.NoticeFunc
    public void setReadStatus(String str) throws Throwable {
        this._legacyManager.setReadStatus(str);
    }

    @Override // com.felicanetworks.mfm.main.model.NoticeFunc
    public void setDetailAccess(String str) throws Throwable {
        this._legacyManager.setDetailAccess(str);
    }

    @Override // com.felicanetworks.mfm.main.model.NoticeFunc
    public void orderBanner(final NoticeFunc.OrderBannerListener orderBannerListener) {
        final FuncUtil.AsyncRunner runner = getRunner("orderBanner");
        if (!runner.startup((Thread) new OrderBannerWorker(this._context, OrderBannerWorker.Type.NOTIFICATION, new OrderBannerWorker.Listener() { // from class: com.felicanetworks.mfm.main.model.internal.main.NoticeFuncEntity.1
            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Listener
            public void onComplete(final List<BannerInfo> list) {
                FuncUtil.notifySafety(runner, new FuncUtil.Notify() { // from class: com.felicanetworks.mfm.main.model.internal.main.NoticeFuncEntity.1.1
                    @Override // com.felicanetworks.mfm.main.model.internal.main.FuncUtil.Notify
                    public void go() {
                        orderBannerListener.onSuccess(list);
                    }
                });
            }
        }))) {
            throw new IllegalStateException("NoticeFunc#orderBanner() is still executing.");
        }
    }

    @Override // com.felicanetworks.mfm.main.model.NoticeFunc
    public void cancel() {
        Iterator<FuncUtil.AsyncRunner> it = this._runners.values().iterator();
        while (it.hasNext()) {
            it.next().shutdown();
        }
        this._context.cancellation();
    }
}
