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
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
class GetCachedCardListTask extends AsyncParentTaskBase<String[]> {
    private final String mAppIdInfo;
    private final String mCallingAppInfo;
    private final MfiChipHolder mChipHolder;
    private final DataManager mDataManager;
    private String[] mResult;

    GetCachedCardListTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, MfiChipHolder mfiChipHolder, DataManager dataManager, String str, String str2) {
        super(i, executorService, listener);
        this.mChipHolder = mfiChipHolder;
        this.mDataManager = dataManager;
        this.mCallingAppInfo = str;
        this.mAppIdInfo = str2;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() throws Throwable {
        try {
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
                        onFinished(false, 157, ObfuscatedMsgUtil.executionPoint());
                        return;
                    }
                    CardCache.CardList cardListLoadOnly = CardCache.loadOnly(this.mCallingAppInfo, this.mAppIdInfo, accountId);
                    if (cardListLoadOnly != null && cardListLoadOnly.cardInfoMap != null && cardListLoadOnly.cardIdInfoMap != null) {
                        if (MfiControlInfoCache.getInstance().getInfoCache() == null) {
                            onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                            return;
                        }
                        LinkedHashMap<String, CompleteCardInfo> linkedHashMapUpdateCachedCardByCidMap = new CardIdentifiableInfoChecker().updateCachedCardByCidMap(this.mChipHolder, this.mDataManager, cardListLoadOnly.cardInfoMap, cardListLoadOnly.cardIdInfoMap);
                        String[] strArr = new String[linkedHashMapUpdateCachedCardByCidMap.size()];
                        Iterator<CompleteCardInfo> it = linkedHashMapUpdateCachedCardByCidMap.values().iterator();
                        int i = 0;
                        while (it.hasNext()) {
                            strArr[i] = new CachedCardInfoJson(new CachedCardInfo(it.next().getForeignDataAsCache(), cardListLoadOnly.cachedTime)).toString();
                            i++;
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
            onFinished(false, e2.getType(), e2.getMessage());
        } catch (GpException e3) {
            LogMgr.printStackTrace(7, e3);
            onFinished(false, e3.getType(), e3.getMessage());
        } catch (JSONException e4) {
            LogMgr.log(2, e4.getClass().getSimpleName() + ":" + e4.getMessage());
            LogMgr.printStackTrace(7, e4);
            onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e4));
        } catch (Exception e5) {
            LogMgr.log(2, e5.getClass().getSimpleName() + ":" + e5.getMessage());
            LogMgr.printStackTrace(7, e5);
            onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e5));
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public void setResult(String[] strArr) {
        this.mResult = strArr;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public synchronized String[] getResult2() {
        return this.mResult;
    }
}
