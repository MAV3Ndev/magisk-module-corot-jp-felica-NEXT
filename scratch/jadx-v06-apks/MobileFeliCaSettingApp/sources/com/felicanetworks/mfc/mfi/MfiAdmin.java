package com.felicanetworks.mfc.mfi;

import android.os.RemoteException;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.MfiClientAccess;
import com.felicanetworks.mfc.mfi.IInitializedEventCallback;
import com.felicanetworks.mfc.mfi.ILinkageDataListEventCallback;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
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

    public synchronized void getLinkageDataList(int i, String[] strArr, LinkageDataListEventCallback linkageDataListEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (i != 1 && i != 2 && i != 3) {
            LogMgr.log(2, "700 actionType is invalid:" + i);
            throw new IllegalArgumentException(EXC_INVALID_ACTION_TYPE);
        }
        if (i == 1 || i == 2) {
            if (strArr == null || strArr.length == 0) {
                LogMgr.log(2, "701 cidList is null or invalid.");
                throw new IllegalArgumentException("The specified cidList is null or invalid.");
            }
            for (String str : strArr) {
                if (str == null) {
                    LogMgr.log(2, "702 cid is null");
                    throw new IllegalArgumentException("The specified cidList is null or invalid.");
                }
                if (!str.matches(REGEX_ALPHANUMERIC)) {
                    LogMgr.log(2, "703 cid involves invalid character.");
                    throw new IllegalArgumentException("The specified cidList is null or invalid.");
                }
                if (str.length() != 63) {
                    LogMgr.log(2, "704 cid length is invalid.");
                    throw new IllegalArgumentException("The specified cidList is null or invalid.");
                }
            }
        }
        if (linkageDataListEventCallback == null) {
            LogMgr.log(2, "705 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.checkMfiAdminLoggedIn(this);
        this.mMfiClientAccess.startOnline(linkageDataListEventCallback);
        try {
            try {
                MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().getLinkageDataList(i, strArr, new LocalILinkageDataListEventCallback()));
                LogMgr.log(3, "999");
            } catch (Exception e) {
                LogMgr.log(2, "705 Other Exception");
                LogMgr.printStackTrace(7, e);
                this.mMfiClientAccess.stopOnline();
                throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
            }
        } catch (FelicaException e2) {
            LogMgr.log(2, "703 " + e2.toString() + " id:" + e2.getID() + " type:" + e2.getType());
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(e2);
        } catch (IllegalArgumentException e3) {
            LogMgr.log(2, "704 " + e3.toString());
            this.mMfiClientAccess.stopOnline();
            throw e3;
        }
    }

    public synchronized void initialize(String str, InitializedEventCallback initializedEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (str == null) {
            LogMgr.log(2, "700 linkageData is null.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (str.isEmpty()) {
            LogMgr.log(2, "701 linkageData is empty.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (initializedEventCallback == null) {
            LogMgr.log(2, "702 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.checkMfiAdminLoggedIn(this);
        this.mMfiClientAccess.startOnline(initializedEventCallback);
        try {
            try {
                MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().initialize(str, new LocalIInitializedEventCallback()));
                LogMgr.log(3, "999");
            } catch (Exception e) {
                LogMgr.log(2, "705 Other Exception");
                LogMgr.printStackTrace(7, e);
                this.mMfiClientAccess.stopOnline();
                throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
            }
        } catch (FelicaException e2) {
            LogMgr.log(2, "703 " + e2.toString() + " id:" + e2.getID() + " type:" + e2.getType());
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(e2);
        } catch (IllegalArgumentException e3) {
            LogMgr.log(2, "704 " + e3.toString());
            this.mMfiClientAccess.stopOnline();
            throw e3;
        }
    }

    class LocalILinkageDataListEventCallback extends ILinkageDataListEventCallback.Stub {
        LocalILinkageDataListEventCallback() {
        }

        @Override // com.felicanetworks.mfc.mfi.ILinkageDataListEventCallback
        public void onSuccess(String[] strArr) throws RemoteException {
            LogMgr.log(3, "000");
            BaseMfiEventCallback baseMfiEventCallbackStopOnline = null;
            try {
                baseMfiEventCallbackStopOnline = MfiAdmin.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null && (baseMfiEventCallbackStopOnline instanceof LinkageDataListEventCallback)) {
                    LogMgr.log(6, "001");
                    try {
                        ((LinkageDataListEventCallback) baseMfiEventCallbackStopOnline).onSuccess(strArr);
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

        @Override // com.felicanetworks.mfc.mfi.ILinkageDataListEventCallback
        public void onError(int i, String str) throws RemoteException {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = MfiAdmin.this.mMfiClientAccess.stopOnline();
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
                        baseMfiEventCallbackStopOnline.onError(BaseMfiEventCallback.TYPE_UNKNOWN_ERROR, "Unknown error.");
                    } catch (Exception unused) {
                        LogMgr.log(2, "701 " + e2.getMessage());
                    }
                }
            }
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.IInitializedEventCallback
        public void onError(int i, String str) throws RemoteException {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = MfiAdmin.this.mMfiClientAccess.stopOnline();
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
