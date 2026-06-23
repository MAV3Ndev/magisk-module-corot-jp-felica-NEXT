package com.felicanetworks.mfc.mfi;

import android.os.RemoteException;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.MfiClientAccess;
import com.felicanetworks.mfc.mfi.ICachedCardEnableEventCallback;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class CachedCard {
    public static final String EXC_INVALID_CALLBACK = "The specified Callback is null.";
    protected CachedCardInfo mCachedCardInfo;
    protected final MfiClientAccess mMfiClientAccess;

    CachedCard(CachedCardInfo cachedCardInfo, MfiClientAccess mfiClientAccess) {
        this.mCachedCardInfo = cachedCardInfo;
        this.mMfiClientAccess = mfiClientAccess;
    }

    public synchronized void enable(CachedCardEnableEventCallback cachedCardEnableEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (cachedCardEnableEventCallback == null) {
            LogMgr.log(2, "700 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        if (this.mMfiClientAccess.isMfiClientStarted()) {
            LogMgr.log(2, "701 started by User.");
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_STARTED, null);
        }
        this.mMfiClientAccess.startOnlineWithoutLogin(cachedCardEnableEventCallback);
        try {
            try {
                MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().enableCachedCard(new CachedCardInfoJson(this.mCachedCardInfo).toString(), new LocalICachedCardEnableEventCallback()));
                LogMgr.log(3, "999");
            } catch (Exception e) {
                LogMgr.log(2, "704 Other Exception");
                LogMgr.printStackTrace(7, e);
                this.mMfiClientAccess.stopOnline();
                throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
            }
        } catch (FelicaException e2) {
            LogMgr.log(2, "702 " + e2.toString() + " id:" + e2.getID() + " type:" + e2.getType());
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(e2);
        } catch (IllegalArgumentException e3) {
            LogMgr.log(2, "703 " + e3.toString());
            this.mMfiClientAccess.stopOnline();
            throw e3;
        }
    }

    public synchronized CachedCardInfo getCachedCardInfo() {
        return this.mCachedCardInfo;
    }

    class LocalICachedCardEnableEventCallback extends ICachedCardEnableEventCallback.Stub {
        LocalICachedCardEnableEventCallback() {
        }

        @Override // com.felicanetworks.mfc.mfi.ICachedCardEnableEventCallback
        public void onSuccess(String str, String str2) throws RemoteException {
            CachedCard cachedCard;
            LogMgr.log(3, "000");
            BaseMfiEventCallback baseMfiEventCallback = null;
            CachedCard cachedCard2 = null;
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = CachedCard.this.mMfiClientAccess.stopOnline();
                if (str != null) {
                    try {
                        LogMgr.log(6, "001");
                        cachedCard = new CachedCard(new CachedCardInfoJson(str).getCachedCardInfo(), CachedCard.this.mMfiClientAccess);
                    } catch (Exception e) {
                        e = e;
                        baseMfiEventCallback = baseMfiEventCallbackStopOnline;
                        LogMgr.log(1, "800 " + e.getMessage());
                        try {
                            if (baseMfiEventCallback instanceof CachedCardEnableEventCallback) {
                                ((CachedCardEnableEventCallback) baseMfiEventCallback).onError(BaseMfiEventCallback.TYPE_UNKNOWN_ERROR, "Unknown error.");
                            }
                        } catch (Exception unused) {
                            LogMgr.log(2, "701 " + e.getMessage());
                        }
                    }
                } else {
                    cachedCard = null;
                }
                if (str2 != null) {
                    LogMgr.log(6, "002");
                    cachedCard2 = new CachedCard(new CachedCardInfoJson(str2).getCachedCardInfo(), CachedCard.this.mMfiClientAccess);
                }
                LogMgr.log(6, "003");
                try {
                    if (baseMfiEventCallbackStopOnline instanceof CachedCardEnableEventCallback) {
                        ((CachedCardEnableEventCallback) baseMfiEventCallbackStopOnline).onSuccess(cachedCard, cachedCard2);
                    }
                } catch (Exception e2) {
                    LogMgr.log(2, "700 " + e2.getMessage());
                }
            } catch (Exception e3) {
                e = e3;
            }
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.ICachedCardEnableEventCallback
        public void onError(int i, String str) throws RemoteException {
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = CachedCard.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline instanceof CachedCardEnableEventCallback) {
                    ((CachedCardEnableEventCallback) baseMfiEventCallbackStopOnline).onError(i, str);
                }
            } catch (Exception e) {
                LogMgr.log(2, "700", e.getMessage());
            }
        }
    }
}
