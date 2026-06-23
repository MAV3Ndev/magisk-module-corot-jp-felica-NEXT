package com.felicanetworks.mfc.mfi;

import android.os.RemoteException;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.MfiClientAccess;
import com.felicanetworks.mfc.mfi.ICardAccessEventCallback;
import com.felicanetworks.mfc.mfi.ICardDeleteV2EventCallback;
import com.felicanetworks.mfc.mfi.ICardDisableV2EventCallback;
import com.felicanetworks.mfc.mfi.ICardEnableV2EventCallback;
import com.felicanetworks.mfc.mfi.ICardReissuableDeleteEventCallback;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
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

    public synchronized void access(String str, CardAccessEventCallback cardAccessEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (str == null) {
            LogMgr.log(2, "700 linkageData is null.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (str.isEmpty()) {
            LogMgr.log(2, "701 linkageData is empty.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (cardAccessEventCallback == null) {
            LogMgr.log(2, "702 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.startOnline(cardAccessEventCallback);
        try {
            try {
                MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().access(new CardInfoJson(this.mCardInfo).toString(), str, new LocalICardAccessEventCallback()));
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
        } catch (Exception e3) {
            LogMgr.log(2, "705 Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized void enable(CardEnableEventCallback cardEnableEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "%s", "000");
        if (cardEnableEventCallback == null) {
            LogMgr.log(2, "%s callback is null.", "700");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.startOnline(cardEnableEventCallback);
        try {
            try {
                try {
                    MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().enableV2(new CardInfoJson(this.mCardInfo).toString(), new LocalICardEnableV2EventCallback()));
                    LogMgr.log(3, "%s", "999");
                } catch (Exception e) {
                    LogMgr.log(2, "%s %s", "703", "Other Exception");
                    LogMgr.printStackTrace(7, e);
                    this.mMfiClientAccess.stopOnline();
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            } catch (FelicaException e2) {
                LogMgr.log(2, "%s %s id:%d type:%d", "701", e2.toString(), Integer.valueOf(e2.getID()), Integer.valueOf(e2.getType()));
                this.mMfiClientAccess.stopOnline();
                throw new MfiClientException(e2);
            }
        } catch (IllegalArgumentException e3) {
            LogMgr.log(2, "%s %s", "702", e3.toString());
            this.mMfiClientAccess.stopOnline();
            throw e3;
        }
    }

    public synchronized void disable(CardDisableEventCallback cardDisableEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "%s", "000");
        if (cardDisableEventCallback == null) {
            LogMgr.log(2, "%s callback is null.", "700");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.startOnline(cardDisableEventCallback);
        try {
            try {
                try {
                    MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().disableV2(new CardInfoJson(this.mCardInfo).toString(), new LocalICardDisableV2EventCallback()));
                    LogMgr.log(3, "%s", "999");
                } catch (Exception e) {
                    LogMgr.log(2, "%s %s", "703", "Other Exception");
                    LogMgr.printStackTrace(7, e);
                    this.mMfiClientAccess.stopOnline();
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            } catch (FelicaException e2) {
                LogMgr.log(2, "%s %s id:%d type:%d", "701", e2.toString(), Integer.valueOf(e2.getID()), Integer.valueOf(e2.getType()));
                this.mMfiClientAccess.stopOnline();
                throw new MfiClientException(e2);
            }
        } catch (IllegalArgumentException e3) {
            LogMgr.log(2, "%s %s", "702", e3.toString());
            this.mMfiClientAccess.stopOnline();
            throw e3;
        }
    }

    public synchronized void delete(String str, CardDeleteEventCallback cardDeleteEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "%s", "000");
        if (str == null) {
            LogMgr.log(2, "%s linkageData is null.", "700");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (str.isEmpty()) {
            LogMgr.log(2, "%s linkageData is empty.", "701");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (cardDeleteEventCallback == null) {
            LogMgr.log(2, "%s callback is null.", "702");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.startOnline(cardDeleteEventCallback);
        try {
            try {
                MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().deleteV2(new CardInfoJson(this.mCardInfo).toString(), str, new LocalICardDeleteV2EventCallback()));
                LogMgr.log(3, "%s", "999");
            } catch (Exception e) {
                LogMgr.log(2, "%s %s", "705", "Other Exception");
                LogMgr.printStackTrace(7, e);
                this.mMfiClientAccess.stopOnline();
                throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
            }
        } catch (FelicaException e2) {
            LogMgr.log(2, "%s %s id:%d type:%d", "703", e2.toString(), Integer.valueOf(e2.getID()), Integer.valueOf(e2.getType()));
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(e2);
        } catch (IllegalArgumentException e3) {
            LogMgr.log(2, "%s %s", "704", e3.toString());
            this.mMfiClientAccess.stopOnline();
            throw e3;
        }
    }

    public synchronized void delete(String str, CardReissuableDeleteEventCallback cardReissuableDeleteEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "%s", "000");
        if (str == null) {
            LogMgr.log(2, "%s linkageData is null.", "700");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (str.isEmpty()) {
            LogMgr.log(2, "%s linkageData is empty.", "701");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (cardReissuableDeleteEventCallback == null) {
            LogMgr.log(2, "%s callback is null.", "702");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.startOnline(cardReissuableDeleteEventCallback);
        try {
            try {
                MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().saveDelete(new CardInfoJson(this.mCardInfo).toString(), str, new LocalICardReissuableDeleteEventCallback()));
                LogMgr.log(3, "%s", "999");
            } catch (Exception e) {
                LogMgr.log(2, "%s %s", "705", "Other Exception");
                LogMgr.printStackTrace(7, e);
                this.mMfiClientAccess.stopOnline();
                throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
            }
        } catch (FelicaException e2) {
            LogMgr.log(2, "%s %s id:%d type:%d", "703", e2.toString(), Integer.valueOf(e2.getID()), Integer.valueOf(e2.getType()));
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(e2);
        } catch (IllegalArgumentException e3) {
            LogMgr.log(2, "%s %s", "704", e3.toString());
            this.mMfiClientAccess.stopOnline();
            throw e3;
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
                        baseMfiEventCallbackStopOnline.onError(BaseMfiEventCallback.TYPE_UNKNOWN_ERROR, "Unknown error.");
                    } catch (Exception unused) {
                        LogMgr.log(2, "701 " + e2.getMessage());
                    }
                }
            }
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.ICardDeleteV2EventCallback
        public void onError(int i, String str) throws RemoteException {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = Card.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null) {
                    LogMgr.log(6, "001");
                    try {
                        baseMfiEventCallbackStopOnline.onError(i, str);
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
                                baseMfiEventCallback.onError(BaseMfiEventCallback.TYPE_UNKNOWN_ERROR, "Unknown error.");
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
        public void onError(int i, String str) throws RemoteException {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = Card.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null) {
                    LogMgr.log(6, "001");
                    try {
                        baseMfiEventCallbackStopOnline.onError(i, str);
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
                                baseMfiEventCallback.onError(BaseMfiEventCallback.TYPE_UNKNOWN_ERROR, "Unknown error.");
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
        public void onError(int i, String str) throws RemoteException {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = Card.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null) {
                    LogMgr.log(6, "001");
                    try {
                        baseMfiEventCallbackStopOnline.onError(i, str);
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
                                baseMfiEventCallback.onError(BaseMfiEventCallback.TYPE_UNKNOWN_ERROR, "Unknown error.");
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
        public void onError(int i, String str) throws RemoteException {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = Card.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null) {
                    LogMgr.log(6, "001");
                    try {
                        baseMfiEventCallbackStopOnline.onError(i, str);
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
                        baseMfiEventCallbackStopOnline.onError(BaseMfiEventCallback.TYPE_UNKNOWN_ERROR, "Unknown error.");
                    } catch (Exception unused) {
                        LogMgr.log(2, "701 " + e2.getMessage());
                    }
                }
            }
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.ICardAccessEventCallback
        public void onError(int i, String str) throws RemoteException {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = Card.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null) {
                    LogMgr.log(6, "001");
                    try {
                        baseMfiEventCallbackStopOnline.onError(i, str);
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
