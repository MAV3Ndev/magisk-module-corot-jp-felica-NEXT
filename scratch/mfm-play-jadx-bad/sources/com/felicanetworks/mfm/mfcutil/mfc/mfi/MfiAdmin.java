package com.felicanetworks.mfm.mfcutil.mfc.mfi;

import android.os.RemoteException;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.FelicaResultInfoBoolean;
import com.felicanetworks.mfc.mfi.CardInfo;
import com.felicanetworks.mfc.mfi.CardInfoJson;
import com.felicanetworks.mfc.mfi.IInitializedEventCallback;
import com.felicanetworks.mfc.mfi.ILinkageDataListEventCallback;
import com.felicanetworks.mfc.mfi.IMemoryClearEventCallback;
import com.felicanetworks.mfc.mfi.IRemainedCardsEventCallback;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfm.mfcutil.mfc.MfiClientAccess;
import com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr;
import java.util.List;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class MfiAdmin {
    public static final int ACTION_TYPE_DELETE_CARDS = 2;
    public static final int ACTION_TYPE_INITIALIZE_CHIP = 3;
    public static final int ACTION_TYPE_REISSUE_CARDS = 1;
    public static final String EXC_INVALID_ACTION_TYPE = "The specified actionType is null or invalid.";
    public static final String EXC_INVALID_CALLBACK = "The specified Callback is null.";
    public static final String EXC_INVALID_CID_LIST = "The specified cidList is null or invalid.";
    public static final String EXC_INVALID_LINKAGE_DATA = "The specified LinkageData is null or invalid.";
    private static final int LEN_CID = 63;
    private static final String REGEX_ALPHANUMERIC = "[0-9a-zA-Z]*";
    private final MfiClientAccess mMfiClientAccess;

    MfiAdmin(MfiClientAccess mfiClientAccess) {
        LogMgr.log(7, "000");
        this.mMfiClientAccess = mfiClientAccess;
        LogMgr.log(7, "999");
    }

    public synchronized void getLinkageDataList(int actionType, String[] cidList, LinkageDataListEventCallback callback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (actionType != 1 && actionType != 2 && actionType != 3) {
            LogMgr.log(2, "700 actionType is invalid:" + actionType);
            throw new IllegalArgumentException("The specified actionType is null or invalid.");
        }
        if (actionType == 1 || actionType == 2) {
            if (cidList == null || cidList.length == 0) {
                LogMgr.log(2, "701 cidList is null or invalid.");
                throw new IllegalArgumentException("The specified cidList is null or invalid.");
            }
            for (String str : cidList) {
                if (str == null) {
                    LogMgr.log(2, "702 cid is null");
                    throw new IllegalArgumentException("The specified cidList is null or invalid.");
                }
                if (!str.matches("[0-9a-zA-Z]*")) {
                    LogMgr.log(2, "703 cid involves invalid character.");
                    throw new IllegalArgumentException("The specified cidList is null or invalid.");
                }
                if (str.length() != 63) {
                    LogMgr.log(2, "704 cid length is invalid.");
                    throw new IllegalArgumentException("The specified cidList is null or invalid.");
                }
            }
        }
        if (callback == null) {
            LogMgr.log(2, "705 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.checkMfiAdminLoggedIn(this);
        this.mMfiClientAccess.startOnline(callback);
        try {
            try {
                MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().getLinkageDataList(actionType, cidList, new LocalILinkageDataListEventCallback()));
                LogMgr.log(3, "999");
            } catch (FelicaException e) {
                LogMgr.log(2, "703 " + e.toString() + " id:" + e.getID() + " type:" + e.getType());
                this.mMfiClientAccess.stopOnline();
                throw new MfiClientException(e);
            }
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "704 " + e2.toString());
            this.mMfiClientAccess.stopOnline();
            throw e2;
        } catch (Exception e3) {
            LogMgr.log(2, "705 Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized void initialize(String linkageData, InitializedEventCallback callback) throws IllegalArgumentException, MfiClientException {
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
        this.mMfiClientAccess.checkMfiAdminLoggedIn(this);
        this.mMfiClientAccess.startOnline(callback);
        try {
            MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().initialize(linkageData, new LocalIInitializedEventCallback()));
            LogMgr.log(3, "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "703 " + e.toString() + " id:" + e.getID() + " type:" + e.getType());
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(e);
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "704 " + e2.toString());
            this.mMfiClientAccess.stopOnline();
            throw e2;
        } catch (Exception e3) {
            LogMgr.log(2, "705 Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized boolean isChipInitialized() throws MfiClientException {
        FelicaResultInfoBoolean felicaResultInfoBooleanIsChipInitialized;
        LogMgr.log(3, "000");
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.checkMfiAdminLoggedIn(this);
        this.mMfiClientAccess.onlineCheck();
        try {
            felicaResultInfoBooleanIsChipInitialized = this.mMfiClientAccess.getIMfiFelica().isChipInitialized();
            MfiUtil.checkMfcResult(felicaResultInfoBooleanIsChipInitialized);
            LogMgr.log(3, "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "700 " + e.toString() + " id:" + e.getID() + " type:" + e.getType());
            throw new MfiClientException(e);
        } catch (Exception e2) {
            LogMgr.log(2, "701 Other Exception");
            LogMgr.printStackTrace(7, e2);
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
        return felicaResultInfoBooleanIsChipInitialized.getValue().booleanValue();
    }

    public synchronized void getRemainedCards(RemainedCardsEventCallback callback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (callback == null) {
            LogMgr.log(2, "700 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.checkMfiAdminLoggedIn(this);
        this.mMfiClientAccess.startOnline(callback);
        try {
            try {
                MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().getRemainedCardsV2(new LocalIRemainedCardsEventCallback()));
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
        } catch (Exception e3) {
            LogMgr.log(2, "703 Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized void clearMemory(MemoryClearEventCallback callback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (callback == null) {
            LogMgr.log(2, "700 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.checkMfiAdminLoggedIn(this);
        this.mMfiClientAccess.startOnline(callback);
        try {
            try {
                MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().clearMemoryV2(new LocalIMemoryClearEventCallback()));
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
        } catch (Exception e3) {
            LogMgr.log(2, "703 Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized boolean checkAndRecoverCrsState() throws MfiClientException {
        FelicaResultInfoBoolean felicaResultInfoBooleanCheckAndRecoverCrsState;
        LogMgr.log(3, "000");
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.checkMfiAdminLoggedIn(this);
        this.mMfiClientAccess.onlineCheck();
        try {
            felicaResultInfoBooleanCheckAndRecoverCrsState = this.mMfiClientAccess.getIMfiFelica().checkAndRecoverCrsState();
            MfiUtil.checkMfcResult(felicaResultInfoBooleanCheckAndRecoverCrsState);
            LogMgr.log(3, "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "700 " + e.toString() + " id:" + e.getID() + " type:" + e.getType());
            throw new MfiClientException(e);
        } catch (Exception e2) {
            LogMgr.log(2, "701 Other Exception");
            LogMgr.printStackTrace(7, e2);
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
        return felicaResultInfoBooleanCheckAndRecoverCrsState.getValue().booleanValue();
    }

    class LocalILinkageDataListEventCallback extends ILinkageDataListEventCallback.Stub {
        LocalILinkageDataListEventCallback() {
        }

        @Override // com.felicanetworks.mfc.mfi.ILinkageDataListEventCallback
        public void onSuccess(String[] linkageData) throws RemoteException {
            LogMgr.log(3, "000");
            BaseMfiEventCallback baseMfiEventCallbackStopOnline = null;
            try {
                baseMfiEventCallbackStopOnline = MfiAdmin.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null && (baseMfiEventCallbackStopOnline instanceof LinkageDataListEventCallback)) {
                    LogMgr.log(6, "001");
                    try {
                        ((LinkageDataListEventCallback) baseMfiEventCallbackStopOnline).onSuccess(linkageData);
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

        @Override // com.felicanetworks.mfc.mfi.ILinkageDataListEventCallback
        public void onError(int id, String msg) throws RemoteException {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = MfiAdmin.this.mMfiClientAccess.stopOnline();
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

    class LocalIInitializedEventCallback extends IInitializedEventCallback.Stub {
        LocalIInitializedEventCallback() {
        }

        @Override // com.felicanetworks.mfc.mfi.IInitializedEventCallback
        public void onSuccess() throws RemoteException {
            LogMgr.log(3, "000");
            BaseMfiEventCallback baseMfiEventCallbackStopOnline = null;
            try {
                baseMfiEventCallbackStopOnline = MfiAdmin.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null && (baseMfiEventCallbackStopOnline instanceof InitializedEventCallback)) {
                    LogMgr.log(6, "001");
                    try {
                        ((InitializedEventCallback) baseMfiEventCallbackStopOnline).onSuccess();
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

        @Override // com.felicanetworks.mfc.mfi.IInitializedEventCallback
        public void onError(int id, String msg) throws RemoteException {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = MfiAdmin.this.mMfiClientAccess.stopOnline();
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

    class LocalIRemainedCardsEventCallback extends IRemainedCardsEventCallback.Stub {
        LocalIRemainedCardsEventCallback() {
        }

        @Override // com.felicanetworks.mfc.mfi.IRemainedCardsEventCallback
        public void onSuccess(final List<String> cardInfoJsonStringListToDelete, final List<String> cardInfoJsonStringListToPermanentDelete) throws RemoteException {
            LogMgr.log(3, "000");
            new Thread(new Runnable() { // from class: com.felicanetworks.mfm.mfcutil.mfc.mfi.MfiAdmin.LocalIRemainedCardsEventCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    LogMgr.log(6, "000");
                    BaseMfiEventCallback baseMfiEventCallbackStopOnline = null;
                    try {
                        try {
                            baseMfiEventCallbackStopOnline = MfiAdmin.this.mMfiClientAccess.stopOnline();
                            if (baseMfiEventCallbackStopOnline instanceof RemainedCardsEventCallback) {
                                LogMgr.log(6, "001");
                                try {
                                    ((RemainedCardsEventCallback) baseMfiEventCallbackStopOnline).onSuccess(new RemainedCards(LocalIRemainedCardsEventCallback.this.convertJsonStringListToCardInfoList(cardInfoJsonStringListToDelete), LocalIRemainedCardsEventCallback.this.convertJsonStringListToCardInfoList(cardInfoJsonStringListToPermanentDelete), MfiAdmin.this.mMfiClientAccess));
                                } catch (Exception e) {
                                    LogMgr.log(2, "700 " + e.getMessage());
                                }
                            } else {
                                LogMgr.log(6, "002");
                            }
                        } catch (JSONException e2) {
                            LogMgr.log(2, "701 " + e2.getMessage());
                            try {
                                baseMfiEventCallbackStopOnline.onError(200, "Json parse error.");
                            } catch (Exception e3) {
                                LogMgr.log(2, "702 " + e3.getMessage());
                            }
                        }
                    } catch (Exception e4) {
                        LogMgr.log(1, "800 " + e4.getMessage());
                        if (baseMfiEventCallbackStopOnline != null) {
                            try {
                                baseMfiEventCallbackStopOnline.onError(200, "Unknown error.");
                            } catch (Exception e5) {
                                LogMgr.log(2, "703 " + e5.getMessage());
                            }
                        }
                    }
                    LogMgr.log(6, "999");
                }
            }).start();
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.IRemainedCardsEventCallback
        public void onError(int type, String msg) throws RemoteException {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = MfiAdmin.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null) {
                    LogMgr.log(6, "001");
                    try {
                        baseMfiEventCallbackStopOnline.onError(type, msg);
                    } catch (Exception e) {
                        LogMgr.log(2, "700 " + e.getMessage());
                    }
                }
            } catch (Exception e2) {
                LogMgr.log(1, "800 " + e2.getMessage());
            }
            LogMgr.log(3, "999");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public CardInfo[] convertJsonStringListToCardInfoList(List<String> cardInfoJsonStringList) throws JSONException {
            CardInfo[] cardInfoArr;
            LogMgr.log(6, "000");
            if (cardInfoJsonStringList != null) {
                cardInfoArr = new CardInfo[cardInfoJsonStringList.size()];
                for (int i = 0; i < cardInfoJsonStringList.size(); i++) {
                    cardInfoArr[i] = new CardInfoJson(cardInfoJsonStringList.get(i)).getCardInfo();
                    LogMgr.log(7, "001 cid = " + cardInfoArr[i].getCid());
                }
            } else {
                cardInfoArr = null;
            }
            LogMgr.log(6, "999");
            return cardInfoArr;
        }
    }

    class LocalIMemoryClearEventCallback extends IMemoryClearEventCallback.Stub {
        LocalIMemoryClearEventCallback() {
        }

        @Override // com.felicanetworks.mfc.mfi.IMemoryClearEventCallback
        public void onSuccess() throws RemoteException {
            LogMgr.log(3, "000");
            BaseMfiEventCallback baseMfiEventCallbackStopOnline = null;
            try {
                baseMfiEventCallbackStopOnline = MfiAdmin.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline instanceof MemoryClearEventCallback) {
                    LogMgr.log(6, "001");
                    try {
                        ((MemoryClearEventCallback) baseMfiEventCallbackStopOnline).onSuccess();
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

        @Override // com.felicanetworks.mfc.mfi.IMemoryClearEventCallback
        public void onError(int type, String msg) throws RemoteException {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = MfiAdmin.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null) {
                    LogMgr.log(6, "001");
                    try {
                        baseMfiEventCallbackStopOnline.onError(type, msg);
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
