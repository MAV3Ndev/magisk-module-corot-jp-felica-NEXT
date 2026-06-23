package com.felicanetworks.mfm.mfcutil.mfc.mfi;

import android.os.RemoteException;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.mfi.CardInfo;
import com.felicanetworks.mfc.mfi.ICardsUploadEventCallback;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfm.mfcutil.mfc.MfiClientAccess;
import com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class RemainedCards {
    public static final String EXC_INVALID_CALLBACK = "The specified Callback is null.";
    private final CardInfo[] mCardInfoListToDelete;
    private final CardInfo[] mCardInfoListToPermanentDelete;
    private final MfiClientAccess mMfiClientAccess;

    protected RemainedCards(CardInfo[] cardInfoListToDelete, CardInfo[] cardInfoListToPermanentDelete, MfiClientAccess mfiClientAccess) {
        LogMgr.log(6, "000");
        this.mCardInfoListToDelete = cardInfoListToDelete == null ? new CardInfo[0] : cardInfoListToDelete;
        this.mCardInfoListToPermanentDelete = cardInfoListToPermanentDelete == null ? new CardInfo[0] : cardInfoListToPermanentDelete;
        this.mMfiClientAccess = mfiClientAccess;
        LogMgr.log(6, "999");
    }

    public CardInfo[] getCardInfoListToDelete() {
        LogMgr.log(3, "000");
        LogMgr.log(3, "999");
        return this.mCardInfoListToDelete;
    }

    public CardInfo[] getCardInfoListToPermanentDelete() {
        LogMgr.log(3, "000");
        LogMgr.log(3, "999");
        return this.mCardInfoListToPermanentDelete;
    }

    public synchronized void uploadCardsToDelete(CardsUploadEventCallback callback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (callback == null) {
            LogMgr.log(2, "700 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.startOnline(callback);
        try {
            try {
                try {
                    MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().uploadCardsToDelete(new LocalICardsUploadEventCallback()));
                    LogMgr.log(3, "999");
                } catch (FelicaException e) {
                    LogMgr.log(2, "701 " + e + " id:" + e.getID() + " type:" + e.getType());
                    this.mMfiClientAccess.stopOnline();
                    throw new MfiClientException(e);
                }
            } catch (IllegalArgumentException e2) {
                LogMgr.log(2, "702 " + e2);
                this.mMfiClientAccess.stopOnline();
                throw e2;
            }
        } catch (Exception e3) {
            LogMgr.log(2, "703 Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized void uploadCardsToPermanentDelete(CardsUploadEventCallback callback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (callback == null) {
            LogMgr.log(2, "700 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.startOnline(callback);
        try {
            try {
                try {
                    MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().uploadCardsToPermanentDelete(new LocalICardsUploadEventCallback()));
                    LogMgr.log(3, "999");
                } catch (FelicaException e) {
                    LogMgr.log(2, "701 " + e + " id:" + e.getID() + " type:" + e.getType());
                    this.mMfiClientAccess.stopOnline();
                    throw new MfiClientException(e);
                }
            } catch (IllegalArgumentException e2) {
                LogMgr.log(2, "702 " + e2);
                this.mMfiClientAccess.stopOnline();
                throw e2;
            }
        } catch (Exception e3) {
            LogMgr.log(2, "703 Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    class LocalICardsUploadEventCallback extends ICardsUploadEventCallback.Stub {
        LocalICardsUploadEventCallback() {
        }

        @Override // com.felicanetworks.mfc.mfi.ICardsUploadEventCallback
        public void onSuccess() throws RemoteException {
            LogMgr.log(3, "000");
            BaseMfiEventCallback baseMfiEventCallbackStopOnline = null;
            try {
                baseMfiEventCallbackStopOnline = RemainedCards.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline instanceof CardsUploadEventCallback) {
                    LogMgr.log(6, "001");
                    try {
                        ((CardsUploadEventCallback) baseMfiEventCallbackStopOnline).onSuccess();
                    } catch (Exception e) {
                        LogMgr.log(2, "700 " + e.getMessage());
                    }
                } else {
                    LogMgr.log(6, "002");
                }
            } catch (Exception e2) {
                LogMgr.log(1, "800 " + e2.getMessage());
                if (baseMfiEventCallbackStopOnline != null) {
                    try {
                        baseMfiEventCallbackStopOnline.onError(200, "Unknown error.");
                    } catch (Exception unused) {
                        LogMgr.log(2, "701 " + e2.getMessage());
                    }
                }
            }
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.ICardsUploadEventCallback
        public void onError(int type, String msg, String[] cidList) throws RemoteException {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = RemainedCards.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null) {
                    LogMgr.log(6, "001");
                    try {
                        ((CardsUploadEventCallback) baseMfiEventCallbackStopOnline).onError(type, msg, cidList);
                    } catch (Exception e) {
                        LogMgr.log(2, "700 " + e.getMessage());
                    }
                }
            } catch (Exception e2) {
                LogMgr.log(1, "800 " + e2.getMessage());
            }
            LogMgr.log(3, "999");
        }
    }
}
