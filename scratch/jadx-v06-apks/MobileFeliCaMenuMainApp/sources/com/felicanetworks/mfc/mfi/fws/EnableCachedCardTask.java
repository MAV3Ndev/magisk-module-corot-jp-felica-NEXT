package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.AccountCache;
import com.felicanetworks.mfc.mfi.CachedCardInfo;
import com.felicanetworks.mfc.mfi.CardCache;
import com.felicanetworks.mfc.mfi.CompleteCardInfo;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.EventBroadcastSender;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfc.mfi.MfiControlInfoCache;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.MfiFelicaWrapper;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.json.GetMfiControlInfoResponseJson;
import com.felicanetworks.mfc.mfi.omapi.CrsManager;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.omapi.InstanceStatus;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.ServiceTypeInfoUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
class EnableCachedCardTask extends AsyncParentTaskBase<Result> {
    private final String mAppIdInfo;
    private final CachedCardInfo mCachedCardInfo;
    private final String mCallingAppInfo;
    private EventBroadcastSender.CardChangeEvent mCardChangeEvent;
    private final MfiChipHolder mChipHolder;
    private final DataManager mDataManager;
    private Result mResult;

    class Result {
        long cachedTime;
        List<CompleteCardInfo.Cache> cardInfoList;

        Result() {
        }
    }

    EnableCachedCardTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, CachedCardInfo cachedCardInfo, String str, String str2, MfiChipHolder mfiChipHolder, DataManager dataManager) {
        super(i, executorService, listener);
        this.mCachedCardInfo = cachedCardInfo;
        this.mChipHolder = mfiChipHolder;
        this.mDataManager = dataManager;
        this.mCallingAppInfo = str;
        this.mAppIdInfo = str2;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() {
        String cid;
        LogMgr.log(6, "000");
        this.mCardChangeEvent = new EventBroadcastSender.CardChangeEvent(this.mCachedCardInfo.getServiceId());
        EventBroadcastSender.sendStartEventBroadcast(FelicaAdapter.getInstance(), this.mCardChangeEvent);
        int i = 215;
        try {
            try {
                try {
                    cid = this.mCachedCardInfo.getCid();
                } catch (GpException e) {
                    e = e;
                    i = 2;
                }
            } catch (InterruptedException unused) {
                i = 2;
            }
            try {
            } catch (GpException e2) {
                e = e2;
                LogMgr.log(i, "717 GpException.");
                onFinished(false, e.getType(), e.getMessage());
            } catch (InterruptedException unused2) {
                LogMgr.log(i, "716 cancel occured.");
                onFinished(false, 215, null);
            }
        } catch (Exception e3) {
            LogMgr.log(2, e3.getClass().getSimpleName() + ":" + e3.getMessage());
            LogMgr.printStackTrace(7, e3);
            onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e3));
        }
        if (isStopped()) {
            LogMgr.log(2, "700 Already has stopped.");
            onFinished(false, 215, null);
            return;
        }
        try {
            checkPrimaryIssue();
            if (isStopped()) {
                LogMgr.log(2, "702 Already has stopped.");
                onFinished(false, 215, null);
                return;
            }
            if (createSeInfo()) {
                if (isStopped()) {
                    LogMgr.log(2, "703 Already has stopped.");
                    onFinished(false, 215, null);
                    return;
                }
                String accountId = AccountCache.getInstance().getAccountId();
                if (accountId == null) {
                    LogMgr.log(2, "704");
                    onFinished(false, 157, ObfuscatedMsgUtil.executionPoint());
                    return;
                }
                CardCache.CardList cardListLoadOnly = CardCache.loadOnly(this.mCallingAppInfo, this.mAppIdInfo, accountId);
                if (cardListLoadOnly != null && cardListLoadOnly.cardInfoMap != null && cardListLoadOnly.cardIdInfoMap != null) {
                    LogMgr.log(6, "001 Cached cards has loaded.");
                    CompleteCardInfo.Cache cache = cardListLoadOnly.cardInfoMap.get(cid);
                    if (cache == null) {
                        LogMgr.log(2, "706");
                        onFinished(false, 158, ObfuscatedMsgUtil.executionPoint());
                        return;
                    }
                    try {
                        ServiceTypeInfoUtil.enablesCachedCardServiceTypeCheck(cache.serviceType);
                        if (cache.seId != null && cache.seId.equalsIgnoreCase(this.mDataManager.getSeInfo().getSeId())) {
                            if (cache.state != 1) {
                                LogMgr.log(2, "710");
                                onFinished(false, 158, ObfuscatedMsgUtil.executionPoint());
                                return;
                            }
                            String infoCache = MfiControlInfoCache.getInstance().getInfoCache();
                            if (infoCache == null) {
                                LogMgr.log(2, "711");
                                onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                                return;
                            }
                            try {
                                if (!new CardIdentifiableInfoChecker().checkIntegrityForGpPrivate(this.mChipHolder, cache)) {
                                    LogMgr.log(2, "712 : Not exist card.");
                                    onFinished(false, 208, null);
                                    return;
                                }
                                try {
                                    Map<Integer, String> cardCategoryMap = new GetMfiControlInfoResponseJson(infoCache).getCardCategoryMap();
                                    CrsManager crsManager = new CrsManager(this.mChipHolder.getGpController());
                                    Map<Integer, Map<String, InstanceStatus>> mapCreateCrsStatusTable = crsManager.createCrsStatusTable();
                                    ArrayList arrayList = new ArrayList();
                                    for (Map.Entry<Integer, String> entry : cardCategoryMap.entrySet()) {
                                        if (entry.getValue().equals(cache.cardCategory)) {
                                            arrayList.add(entry.getKey());
                                        }
                                    }
                                    ArrayList arrayList2 = new ArrayList();
                                    String str = cache.appletInstanceAid;
                                    Iterator it = arrayList.iterator();
                                    boolean z = false;
                                    while (it.hasNext()) {
                                        Map<String, InstanceStatus> map = mapCreateCrsStatusTable.get((Integer) it.next());
                                        if (map != null) {
                                            for (Map.Entry<String, InstanceStatus> entry2 : map.entrySet()) {
                                                if (entry2.getValue().isActivated()) {
                                                    byte[] aid = entry2.getValue().getAid();
                                                    if (StringUtil.bytesToHexString(aid).equals(str)) {
                                                        z = true;
                                                    } else {
                                                        arrayList2.add(aid);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (arrayList2.size() != 0) {
                                        LogMgr.log(6, "002 Exist activated Applet.");
                                        crsManager.deactivateApplet(arrayList2);
                                    }
                                    if (!z) {
                                        crsManager.activateApplet(StringUtil.hexToByteArray(str));
                                    }
                                    ArrayList arrayList3 = new ArrayList();
                                    cache.state = 1;
                                    cache.position = 0;
                                    CompleteCardInfo completeCardInfo = new CompleteCardInfo(this.mCachedCardInfo, cache);
                                    completeCardInfo.setFixStatus(1);
                                    completeCardInfo.setPosition(0);
                                    completeCardInfo.setAdditionalInfoHash(null);
                                    completeCardInfo.setSpType(cache.spType);
                                    arrayList3.add(completeCardInfo.getForeignDataAsCache());
                                    arrayList3.add(null);
                                    Result result = new Result();
                                    result.cardInfoList = arrayList3;
                                    result.cachedTime = cardListLoadOnly.cachedTime;
                                    setResult(result);
                                    onFinished(true, 0, null);
                                    LogMgr.log(6, "999");
                                    return;
                                } catch (JSONException unused3) {
                                    LogMgr.log(2, "715");
                                    onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                                    return;
                                }
                            } catch (FwsException e4) {
                                LogMgr.log(2, "713 : FwsException");
                                onFinished(false, e4.getType(), e4.getMessage());
                                return;
                            } catch (GpException e5) {
                                LogMgr.log(2, "714 : GpException");
                                onFinished(false, e5.getType(), e5.getMessage());
                                return;
                            }
                        }
                        LogMgr.log(2, "709");
                        onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                        return;
                    } catch (MfiClientException unused4) {
                        LogMgr.log(2, "708");
                        onFinished(false, 244, ObfuscatedMsgUtil.executionPoint());
                        return;
                    }
                }
                LogMgr.log(2, "705");
                onFinished(false, 157, ObfuscatedMsgUtil.executionPoint());
            }
        } catch (MfiFelicaException e6) {
            LogMgr.log(2, "701 ERR: checkPrimaryIssue");
            onFinished(false, e6.getType(), e6.getMessage());
        }
    }

    private void checkPrimaryIssue() throws MfiFelicaException {
        LogMgr.log(6, "000");
        MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(this.mChipHolder);
        try {
            mfiFelicaWrapper.open();
            mfiFelicaWrapper.select(65039);
            mfiFelicaWrapper.getContainerIssueInformationWithCheckIssued();
            mfiFelicaWrapper.close();
            mfiFelicaWrapper.closeSilently();
            LogMgr.log(6, "999");
        } catch (Throwable th) {
            mfiFelicaWrapper.closeSilently();
            throw th;
        }
    }

    private boolean createSeInfo() {
        LogMgr.log(6, "000");
        MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(this.mChipHolder);
        boolean z = false;
        try {
            try {
                this.mDataManager.createSeInfo(mfiFelicaWrapper);
                mfiFelicaWrapper.close();
                z = true;
            } catch (MfiFelicaException e) {
                LogMgr.log(2, "700 MfiFelicaException");
                LogMgr.printStackTrace(7, e);
                onFinished(false, e.getType(), e.getMessage());
            } catch (GpException e2) {
                LogMgr.log(2, "701 GpException");
                onFinished(false, e2.getType(), e2.getMessage());
            } catch (Exception e3) {
                LogMgr.log(2, "702 " + e3.getClass().getSimpleName() + ":" + e3.getMessage());
                LogMgr.printStackTrace(7, e3);
                onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e3));
            }
            LogMgr.log(6, "999");
            return z;
        } finally {
            mfiFelicaWrapper.closeSilently();
        }
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void onFinished(boolean z, int i, String str) {
        LogMgr.log(6, "000");
        EventBroadcastSender.sendFinishEventBroadcast(FelicaAdapter.getInstance(), this.mCardChangeEvent, z);
        super.onFinished(z, i, str);
        LogMgr.log(6, "999");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public synchronized void setResult(Result result) {
        this.mResult = result;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public synchronized Result getResult2() {
        return this.mResult;
    }
}
