package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.AccountCache;
import com.felicanetworks.mfc.mfi.CachedCardInfo;
import com.felicanetworks.mfc.mfi.CachedCardInfoJson;
import com.felicanetworks.mfc.mfi.CardCache;
import com.felicanetworks.mfc.mfi.CompleteCardInfo;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiControlInfoCache;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.MfiFelicaWrapper;
import com.felicanetworks.mfc.mfi.WalletAppIdentifiableInfo;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
class GetCachedCardListTask extends AsyncParentTaskBase<String[]> {
    private final String mAppIdInfo;
    private final String mCallingAppInfo;
    private final MfiChipHolder mChipHolder;
    private final DataManager mDataManager;
    private String[] mResult;
    private boolean mSkipCheckIntegrity;

    GetCachedCardListTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, MfiChipHolder chipHolder, DataManager dataManager, String callingAppInfo, String appIdInfo) {
        super(taskId, executor, listener);
        this.mSkipCheckIntegrity = true;
        this.mChipHolder = chipHolder;
        this.mDataManager = dataManager;
        this.mCallingAppInfo = callingAppInfo;
        this.mAppIdInfo = appIdInfo;
    }

    GetCachedCardListTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, MfiChipHolder chipHolder, DataManager dataManager, String callingAppInfo, String appIdInfo, boolean skipCheckIntegrity) {
        this(taskId, executor, listener, chipHolder, dataManager, callingAppInfo, appIdInfo);
        this.mSkipCheckIntegrity = skipCheckIntegrity;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() throws Throwable {
        boolean z;
        int i;
        boolean z2;
        try {
            try {
                if (isStopped()) {
                    LogMgr.log(2, "700 Already has stopped.");
                    onFinished(false, 215, null);
                    return;
                }
                try {
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
                                onFinished(false, 157, ObfuscatedMsgUtil.executionPoint());
                                return;
                            }
                            CardCache.CardList cardListLoadOnly = CardCache.loadOnly(this.mCallingAppInfo, this.mAppIdInfo, accountId);
                            if (cardListLoadOnly != null && cardListLoadOnly.cardInfoMap != null && cardListLoadOnly.cardIdInfoMap != null) {
                                Map map = new HashMap();
                                if (this.mSkipCheckIntegrity && WalletAppIdentifiableInfo.getInstance().getPackageName().equals("com.felicanetworks.mfm.main")) {
                                    LogMgr.log(2, "001 getCachedCardList() is called by Menu.");
                                    for (CompleteCardInfo.Cache cache : cardListLoadOnly.cardInfoMap.values()) {
                                        CompleteCardInfo completeCardInfo = new CompleteCardInfo(cache.cid, cache.serviceId, cache.walletAppId, cache.state, cache.position, cache.task, cache.reissuePossibility, cache.serviceType, cache.additionalInfoHash, cache.cardCategory, cache.appletInstanceAid, cache.finish, cache.reissueStatus, cache.seId, cache.idm, cache.nodeCodeList, cache.cardType);
                                        completeCardInfo.setSpType(cache.spType);
                                        CompleteCardInfo completeCardInfo2 = new CompleteCardInfo(completeCardInfo);
                                        map.put(completeCardInfo2.getCid(), completeCardInfo2);
                                    }
                                } else {
                                    LogMgr.log(2, "002");
                                    if (MfiControlInfoCache.getInstance().getInfoCache() == null) {
                                        onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                                        return;
                                    }
                                    map = new CardIdentifiableInfoChecker().updateCachedCardByCidMap(this.mChipHolder, this.mDataManager, cardListLoadOnly.cardInfoMap, cardListLoadOnly.cardIdInfoMap);
                                }
                                String[] strArr = new String[map.size()];
                                Iterator it = map.values().iterator();
                                int i2 = 0;
                                while (it.hasNext()) {
                                    strArr[i2] = new CachedCardInfoJson(new CachedCardInfo(((CompleteCardInfo) it.next()).getForeignDataAsCache(), cardListLoadOnly.cachedTime)).toString();
                                    i2++;
                                }
                                setResult(strArr);
                                onFinished(true, 0, null);
                                return;
                            }
                            onFinished(false, 157, ObfuscatedMsgUtil.executionPoint());
                        }
                    } catch (MfiFelicaException e) {
                        LogMgr.log(2, "701 ERR: checkPrimaryIssue");
                        onFinished(false, e.getType(), e.getMessage());
                    }
                } catch (FwsException e2) {
                    e = e2;
                    z2 = false;
                    onFinished(z2, e.getType(), e.getMessage());
                } catch (GpException e3) {
                    e = e3;
                    i = 7;
                    z = false;
                    LogMgr.printStackTrace(i, e);
                    onFinished(z, e.getType(), e.getMessage());
                }
            } catch (FwsException e4) {
                e = e4;
                z2 = false;
            } catch (GpException e5) {
                e = e5;
                z = false;
                i = 7;
            }
        } catch (JSONException e6) {
            LogMgr.log(2, e6.getClass().getSimpleName() + ":" + e6.getMessage());
            LogMgr.printStackTrace(7, e6);
            onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e6));
        } catch (Exception e7) {
            LogMgr.log(2, e7.getClass().getSimpleName() + ":" + e7.getMessage());
            LogMgr.printStackTrace(7, e7);
            onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e7));
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
                mfiFelicaWrapper.closeSilently();
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

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public void setResult(String[] result) {
        this.mResult = result;
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public synchronized String[] getResult2() {
        return this.mResult;
    }
}
