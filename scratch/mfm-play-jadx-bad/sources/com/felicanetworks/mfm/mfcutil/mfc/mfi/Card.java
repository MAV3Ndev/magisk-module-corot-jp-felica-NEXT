package com.felicanetworks.mfm.mfcutil.mfc.mfi;

import android.os.RemoteException;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.mfi.CardInfo;
import com.felicanetworks.mfc.mfi.CardInfoJson;
import com.felicanetworks.mfc.mfi.ICardAccessEventCallback;
import com.felicanetworks.mfc.mfi.ICardDeleteV2EventCallback;
import com.felicanetworks.mfc.mfi.ICardDisableV2EventCallback;
import com.felicanetworks.mfc.mfi.ICardEnableV2EventCallback;
import com.felicanetworks.mfc.mfi.ICardReissuableDeleteEventCallback;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfm.mfcutil.mfc.MfiClientAccess;
import com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class Card {
    public static final String EXC_INVALID_CALLBACK = "The specified Callback is null.";
    public static final String EXC_INVALID_LINKAGE_DATA = "The specified LinkageData is null or invalid.";
    protected CardInfo mCardInfo;
    protected final MfiClientAccess mMfiClientAccess;

    Card(CardInfo cardInfo, MfiClientAccess mfiClientAccess) {
        LogMgr.log(7, "%s", "000");
        this.mCardInfo = cardInfo;
        this.mMfiClientAccess = mfiClientAccess;
        LogMgr.log(7, "%s", "999");
    }

    public synchronized void access(String linkageData, CardAccessEventCallback callback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (linkageData == null) {
            LogMgr.log(2, "700 linkageData is null.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (linkageData.isEmpty()) {
            LogMgr.log(2, "701 linkageData is empty.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (callback == null) {
            LogMgr.log(2, "702 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.startOnline(callback);
        try {
            try {
                try {
                    MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().access(new CardInfoJson(this.mCardInfo).toString(), linkageData, new LocalICardAccessEventCallback()));
                    LogMgr.log(3, "999");
                } catch (IllegalArgumentException e) {
                    LogMgr.log(2, "704 " + e.toString());
                    this.mMfiClientAccess.stopOnline();
                    throw e;
                }
            } catch (FelicaException e2) {
                LogMgr.log(2, "703 " + e2.toString() + " id:" + e2.getID() + " type:" + e2.getType());
                this.mMfiClientAccess.stopOnline();
                throw new MfiClientException(e2);
            }
        } catch (Exception e3) {
            LogMgr.log(2, "705 Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized void enable(CardEnableEventCallback callback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "%s", "000");
        if (callback == null) {
            LogMgr.log(2, "%s callback is null.", "700");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.startOnline(callback);
        try {
            try {
                MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().enableV2(new CardInfoJson(this.mCardInfo).toString(), new LocalICardEnableV2EventCallback()));
                LogMgr.log(3, "%s", "999");
            } catch (IllegalArgumentException e) {
                LogMgr.log(2, "%s %s", "702", e.toString());
                this.mMfiClientAccess.stopOnline();
                throw e;
            }
        } catch (FelicaException e2) {
            LogMgr.log(2, "%s %s id:%d type:%d", "701", e2.toString(), Integer.valueOf(e2.getID()), Integer.valueOf(e2.getType()));
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(e2);
        } catch (Exception e3) {
            LogMgr.log(2, "%s %s", "703", "Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized void disable(CardDisableEventCallback callback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "%s", "000");
        if (callback == null) {
            LogMgr.log(2, "%s callback is null.", "700");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.startOnline(callback);
        try {
            try {
                MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().disableV2(new CardInfoJson(this.mCardInfo).toString(), new LocalICardDisableV2EventCallback()));
                LogMgr.log(3, "%s", "999");
            } catch (IllegalArgumentException e) {
                LogMgr.log(2, "%s %s", "702", e.toString());
                this.mMfiClientAccess.stopOnline();
                throw e;
            }
        } catch (FelicaException e2) {
            LogMgr.log(2, "%s %s id:%d type:%d", "701", e2.toString(), Integer.valueOf(e2.getID()), Integer.valueOf(e2.getType()));
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(e2);
        } catch (Exception e3) {
            LogMgr.log(2, "%s %s", "703", "Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized void delete(String linkageData, CardDeleteEventCallback callback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "%s", "000");
        if (linkageData == null) {
            LogMgr.log(2, "%s linkageData is null.", "700");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (linkageData.isEmpty()) {
            LogMgr.log(2, "%s linkageData is empty.", "701");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (callback == null) {
            LogMgr.log(2, "%s callback is null.", "702");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.startOnline(callback);
        try {
            try {
                MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().deleteV2(new CardInfoJson(this.mCardInfo).toString(), linkageData, new LocalICardDeleteV2EventCallback()));
                LogMgr.log(3, "%s", "999");
            } catch (FelicaException e) {
                LogMgr.log(2, "%s %s id:%d type:%d", "703", e.toString(), Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                this.mMfiClientAccess.stopOnline();
                throw new MfiClientException(e);
            }
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s %s", "704", e2.toString());
            this.mMfiClientAccess.stopOnline();
            throw e2;
        } catch (Exception e3) {
            LogMgr.log(2, "%s %s", "705", "Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized void delete(String linkageData, CardReissuableDeleteEventCallback callback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "%s", "000");
        if (linkageData == null) {
            LogMgr.log(2, "%s linkageData is null.", "700");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (linkageData.isEmpty()) {
            LogMgr.log(2, "%s linkageData is empty.", "701");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (callback == null) {
            LogMgr.log(2, "%s callback is null.", "702");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.startOnline(callback);
        try {
            try {
                MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().saveDelete(new CardInfoJson(this.mCardInfo).toString(), linkageData, new LocalICardReissuableDeleteEventCallback()));
                LogMgr.log(3, "%s", "999");
            } catch (FelicaException e) {
                LogMgr.log(2, "%s %s id:%d type:%d", "703", e.toString(), Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                this.mMfiClientAccess.stopOnline();
                throw new MfiClientException(e);
            }
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s %s", "704", e2.toString());
            this.mMfiClientAccess.stopOnline();
            throw e2;
        } catch (Exception e3) {
            LogMgr.log(2, "%s %s", "705", "Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public CardInfo getCardInfo() {
        return this.mCardInfo;
    }

    class LocalICardDeleteV2EventCallback extends ICardDeleteV2EventCallback.Stub {
        LocalICardDeleteV2EventCallback() {
        }

        @Override // com.felicanetworks.mfc.mfi.ICardDeleteV2EventCallback
        public void onSuccess() throws RemoteException {
            LogMgr.log(3, "000");
            BaseMfiEventCallback baseMfiEventCallbackStopOnline = null;
            try {
                baseMfiEventCallbackStopOnline = Card.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null && (baseMfiEventCallbackStopOnline instanceof CardDeleteEventCallback)) {
                    LogMgr.log(6, "001");
                    try {
                        ((CardDeleteEventCallback) baseMfiEventCallbackStopOnline).onSuccess();
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

        @Override // com.felicanetworks.mfc.mfi.ICardDeleteV2EventCallback
        public void onError(int id, String msg) throws RemoteException {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = Card.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null) {
                    LogMgr.log(6, "001");
                    try {
                        baseMfiEventCallbackStopOnline.onError(id, msg);
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

    class LocalICardReissuableDeleteEventCallback extends ICardReissuableDeleteEventCallback.Stub {
        LocalICardReissuableDeleteEventCallback() {
        }

        @Override // com.felicanetworks.mfc.mfi.ICardReissuableDeleteEventCallback
        public void onSuccess(String str) throws RemoteException {
            LogMgr.log(3, "000");
            BaseMfiEventCallback baseMfiEventCallback = null;
            Card card = null;
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = Card.this.mMfiClientAccess.stopOnline();
                if (str != null) {
                    try {
                        LogMgr.log(6, "001");
                        card = new Card(new CardInfoJson(str).getCardInfo(), Card.this.mMfiClientAccess);
                    } catch (Exception e) {
                        e = e;
                        baseMfiEventCallback = baseMfiEventCallbackStopOnline;
                        LogMgr.log(1, "800 " + e.getMessage());
                        if (baseMfiEventCallback != null) {
                            try {
                                baseMfiEventCallback.onError(200, "Unknown error.");
                            } catch (Exception unused) {
                                LogMgr.log(2, "701 " + e.getMessage());
                            }
                        }
                    }
                }
                if (baseMfiEventCallbackStopOnline != null && (baseMfiEventCallbackStopOnline instanceof CardReissuableDeleteEventCallback)) {
                    LogMgr.log(6, "001");
                    try {
                        ((CardReissuableDeleteEventCallback) baseMfiEventCallbackStopOnline).onSuccess(card);
                    } catch (Exception e2) {
                        LogMgr.log(2, "700 " + e2.getMessage());
                    }
                } else {
                    LogMgr.log(6, "002");
                }
            } catch (Exception e3) {
                e = e3;
            }
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.ICardReissuableDeleteEventCallback
        public void onError(int id, String msg) throws RemoteException {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = Card.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null) {
                    LogMgr.log(6, "001");
                    try {
                        baseMfiEventCallbackStopOnline.onError(id, msg);
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

    class LocalICardDisableV2EventCallback extends ICardDisableV2EventCallback.Stub {
        LocalICardDisableV2EventCallback() {
        }

        @Override // com.felicanetworks.mfc.mfi.ICardDisableV2EventCallback
        public void onSuccess(String str) throws RemoteException {
            LogMgr.log(3, "000");
            BaseMfiEventCallback baseMfiEventCallback = null;
            Card card = null;
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = Card.this.mMfiClientAccess.stopOnline();
                if (str != null) {
                    try {
                        LogMgr.log(6, "001");
                        card = new Card(new CardInfoJson(str).getCardInfo(), Card.this.mMfiClientAccess);
                    } catch (Exception e) {
                        e = e;
                        baseMfiEventCallback = baseMfiEventCallbackStopOnline;
                        LogMgr.log(1, "800 " + e.getMessage());
                        if (baseMfiEventCallback != null) {
                            try {
                                baseMfiEventCallback.onError(200, "Unknown error.");
                            } catch (Exception unused) {
                                LogMgr.log(2, "701 " + e.getMessage());
                            }
                        }
                    }
                }
                if (baseMfiEventCallbackStopOnline != null && (baseMfiEventCallbackStopOnline instanceof CardDisableEventCallback)) {
                    LogMgr.log(6, "002");
                    try {
                        ((CardDisableEventCallback) baseMfiEventCallbackStopOnline).onSuccess(card);
                    } catch (Exception e2) {
                        LogMgr.log(2, "700 " + e2.getMessage());
                    }
                } else {
                    LogMgr.log(6, "003");
                }
            } catch (Exception e3) {
                e = e3;
            }
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.ICardDisableV2EventCallback
        public void onError(int id, String msg) throws RemoteException {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = Card.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null) {
                    LogMgr.log(6, "001");
                    try {
                        baseMfiEventCallbackStopOnline.onError(id, msg);
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

    class LocalICardEnableV2EventCallback extends ICardEnableV2EventCallback.Stub {
        LocalICardEnableV2EventCallback() {
        }

        @Override // com.felicanetworks.mfc.mfi.ICardEnableV2EventCallback
        public void onSuccess(String str, String str2) throws RemoteException {
            Card card;
            LogMgr.log(3, "000");
            BaseMfiEventCallback baseMfiEventCallback = null;
            Card card2 = null;
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = Card.this.mMfiClientAccess.stopOnline();
                if (str != null) {
                    try {
                        LogMgr.log(6, "001");
                        card = new Card(new CardInfoJson(str).getCardInfo(), Card.this.mMfiClientAccess);
                    } catch (Exception e) {
                        e = e;
                        baseMfiEventCallback = baseMfiEventCallbackStopOnline;
                        LogMgr.log(1, "800 " + e.getMessage());
                        if (baseMfiEventCallback != null) {
                            try {
                                baseMfiEventCallback.onError(200, "Unknown error.");
                            } catch (Exception unused) {
                                LogMgr.log(2, "701 " + e.getMessage());
                            }
                        }
                    }
                } else {
                    card = null;
                }
                if (str2 != null) {
                    LogMgr.log(6, "002");
                    card2 = new Card(new CardInfoJson(str2).getCardInfo(), Card.this.mMfiClientAccess);
                }
                if (baseMfiEventCallbackStopOnline != null && (baseMfiEventCallbackStopOnline instanceof CardEnableEventCallback)) {
                    LogMgr.log(6, "003");
                    try {
                        ((CardEnableEventCallback) baseMfiEventCallbackStopOnline).onSuccess(card, card2);
                    } catch (Exception e2) {
                        LogMgr.log(2, "700 " + e2.getMessage());
                    }
                } else {
                    LogMgr.log(6, "004");
                }
            } catch (Exception e3) {
                e = e3;
            }
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.ICardEnableV2EventCallback
        public void onError(int id, String msg) throws RemoteException {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = Card.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null) {
                    LogMgr.log(6, "001");
                    try {
                        baseMfiEventCallbackStopOnline.onError(id, msg);
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

    class LocalICardAccessEventCallback extends ICardAccessEventCallback.Stub {
        LocalICardAccessEventCallback() {
        }

        @Override // com.felicanetworks.mfc.mfi.ICardAccessEventCallback
        public void onSuccess() throws RemoteException {
            LogMgr.log(3, "000");
            BaseMfiEventCallback baseMfiEventCallbackStopOnline = null;
            try {
                baseMfiEventCallbackStopOnline = Card.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null && (baseMfiEventCallbackStopOnline instanceof CardAccessEventCallback)) {
                    LogMgr.log(6, "002");
                    try {
                        ((CardAccessEventCallback) baseMfiEventCallbackStopOnline).onSuccess();
                    } catch (Exception e) {
                        LogMgr.log(2, "700 " + e.getMessage());
                    }
                } else {
                    LogMgr.log(6, "003");
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

        @Override // com.felicanetworks.mfc.mfi.ICardAccessEventCallback
        public void onError(int id, String msg) throws RemoteException {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = Card.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null) {
                    LogMgr.log(6, "001");
                    try {
                        baseMfiEventCallbackStopOnline.onError(id, msg);
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
