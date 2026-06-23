package com.felicanetworks.mfm.mfcutil.mfc.mfi;

import android.content.Intent;
import android.os.RemoteException;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.FelicaResultInfoBoolean;
import com.felicanetworks.mfc.FelicaResultInfoInt;
import com.felicanetworks.mfc.mfi.FelicaResultInfoSeInfo;
import com.felicanetworks.mfc.mfi.FelicaResultInfoString;
import com.felicanetworks.mfc.mfi.FelicaResultInfoStringArray;
import com.felicanetworks.mfc.mfi.IDataListEventCallback;
import com.felicanetworks.mfc.mfi.ILoginEventCallback;
import com.felicanetworks.mfc.mfi.ILogoutEventCallback;
import com.felicanetworks.mfc.mfi.IServerOperationEventCallback;
import com.felicanetworks.mfc.mfi.ISilentStartEventCallback;
import com.felicanetworks.mfc.mfi.ISilentStartForMfiAdminEventCallback;
import com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardDeleteEventCallback;
import com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardExistEventCallback;
import com.felicanetworks.mfc.mfi.LocalPartialCardInfo;
import com.felicanetworks.mfc.mfi.LocalPartialCardInfoJson;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfc.mfi.SeInfo;
import com.felicanetworks.mfm.mfcutil.mfc.MfiClientAccess;
import com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class MfiClient {
    public static final String ACCOUNT_ISSUER_GOOGLE = "Google";
    private static final List<String> ACCOUNT_ISSUER_LIST;
    public static final String EXC_INVALID_ACCOUNT_ISSUER = "The specified AccountIssuer is invalid.";
    public static final String EXC_INVALID_ACCOUNT_NAME = "The specified AccountName is null.";
    public static final String EXC_INVALID_ACCOUNT_NAME_EMPTY = "The specified AccountName is empty.";
    public static final String EXC_INVALID_CALLBACK = "The specified Callback is null.";
    public static final String EXC_INVALID_LAYOUT_TYPE = "The specified LayoutType is unexpected value.";
    public static final String EXC_INVALID_LINKAGE_DATA = "The specified LinkageData is null or invalid.";
    public static final String EXC_INVALID_SERVICE_ID = "The specified ServiceId is null or invalid.";
    public static final int IDENTIFIED_SERVICE_1 = 1;
    public static final int IDENTIFIED_SERVICE_2 = 2;
    public static final int IDENTIFIED_SERVICE_NONE = 0;
    public static final int IDENTIFIED_SERVICE_UNKNOWN = -1;
    public static final int LAYOUT_TYPE_SIGN_IN_ONLY = 3;
    public static final int LAYOUT_TYPE_SIGN_IN_WITH_TERMS = 1;
    public static final int LAYOUT_TYPE_SKIPPABLE_SIGN_IN = 2;
    private static final int LEN_SERVICE_ID = 8;
    private static final String REGEX_ALPHANUMERIC = "[0-9a-zA-Z]*";
    public static final int SLOT_STATUS_EMPTY = 0;
    public static final int SLOT_STATUS_INSUFFICIENT_ALLOCATED_FREE_SPACE = 2;
    public static final int SLOT_STATUS_NOT_EMPTY = 1;
    private final MfiClientAccess mMfiClientAccess;
    private ILoginEventCallback mILoginEventCallback = new ILoginEventCallback.Stub() { // from class: com.felicanetworks.mfm.mfcutil.mfc.mfi.MfiClient.1
        @Override // com.felicanetworks.mfc.mfi.ILoginEventCallback
        public void onSuccess() throws RemoteException {
            BaseMfiEventCallback baseMfiEventCallbackStopLoggingInOut;
            Exception e;
            LogMgr.log(3, "%s", "000");
            try {
                baseMfiEventCallbackStopLoggingInOut = MfiClient.this.mMfiClientAccess.stopLoggingInOut();
            } catch (Exception e2) {
                baseMfiEventCallbackStopLoggingInOut = null;
                e = e2;
            }
            try {
                User user = new User(MfiClient.this.mMfiClientAccess);
                MfiClient.this.mMfiClientAccess.setLoginUser(user, null);
                if (baseMfiEventCallbackStopLoggingInOut != null && (baseMfiEventCallbackStopLoggingInOut instanceof StartEventCallback)) {
                    LogMgr.log(6, "%s", "001");
                    try {
                        ((StartEventCallback) baseMfiEventCallbackStopLoggingInOut).onSuccess(user);
                    } catch (Exception e3) {
                        LogMgr.log(2, "%s %s", "700", e3.getMessage());
                    }
                } else {
                    LogMgr.log(6, "%s", "002");
                }
            } catch (Exception e4) {
                e = e4;
                LogMgr.log(1, "%s %s", "800", e.getMessage());
                if (baseMfiEventCallbackStopLoggingInOut != null) {
                    try {
                        baseMfiEventCallbackStopLoggingInOut.onError(200, "Unknown error.");
                    } catch (Exception unused) {
                        LogMgr.log(2, "%s %s", "701", e.getMessage());
                    }
                }
            }
            LogMgr.log(3, "%s", "999");
        }

        @Override // com.felicanetworks.mfc.mfi.ILoginEventCallback
        public void onError(int i, String str) throws RemoteException {
            LogMgr.log(3, "%s", "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopLoggingInOut = MfiClient.this.mMfiClientAccess.stopLoggingInOut();
                if (baseMfiEventCallbackStopLoggingInOut != null) {
                    LogMgr.log(6, "%s", "001");
                    try {
                        baseMfiEventCallbackStopLoggingInOut.onError(i, str);
                    } catch (Exception e) {
                        LogMgr.log(2, "%s %s", "700", e.getMessage());
                    }
                }
            } catch (Exception e2) {
                LogMgr.log(1, "%s %s", "800", e2.getMessage());
            }
            LogMgr.log(3, "%s", "999");
        }
    };
    private ISilentStartEventCallback mISilentStartEventCallback = new ISilentStartEventCallback.Stub() { // from class: com.felicanetworks.mfm.mfcutil.mfc.mfi.MfiClient.2
        @Override // com.felicanetworks.mfc.mfi.ISilentStartEventCallback
        public void onSuccess() throws RemoteException {
            BaseMfiEventCallback baseMfiEventCallbackStopLoggingInOut;
            Exception e;
            LogMgr.log(3, "000");
            try {
                baseMfiEventCallbackStopLoggingInOut = MfiClient.this.mMfiClientAccess.stopLoggingInOut();
            } catch (Exception e2) {
                baseMfiEventCallbackStopLoggingInOut = null;
                e = e2;
            }
            try {
                User user = new User(MfiClient.this.mMfiClientAccess);
                MfiClient.this.mMfiClientAccess.setLoginUser(user, null);
                if (baseMfiEventCallbackStopLoggingInOut != null && (baseMfiEventCallbackStopLoggingInOut instanceof SilentStartEventCallback)) {
                    LogMgr.log(6, "001");
                    try {
                        ((SilentStartEventCallback) baseMfiEventCallbackStopLoggingInOut).onSuccess(user);
                    } catch (Exception e3) {
                        LogMgr.log(2, "700 " + e3.getMessage());
                    }
                } else {
                    LogMgr.log(6, "002");
                }
            } catch (Exception e4) {
                e = e4;
                LogMgr.log(1, "800 " + e.getMessage());
                if (baseMfiEventCallbackStopLoggingInOut != null) {
                    try {
                        baseMfiEventCallbackStopLoggingInOut.onError(200, "Unknown error.");
                    } catch (Exception unused) {
                        LogMgr.log(2, "701 " + e.getMessage());
                    }
                }
            }
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.ISilentStartEventCallback
        public void onRequestActivity(Intent intent) throws RemoteException {
            BaseMfiEventCallback baseMfiEventCallbackStopLoggingInOut = null;
            try {
                baseMfiEventCallbackStopLoggingInOut = MfiClient.this.mMfiClientAccess.stopLoggingInOut();
                if (baseMfiEventCallbackStopLoggingInOut != null && (baseMfiEventCallbackStopLoggingInOut instanceof SilentStartEventCallback)) {
                    LogMgr.log(6, "001");
                    try {
                        ((SilentStartEventCallback) baseMfiEventCallbackStopLoggingInOut).onRequestActivity(intent);
                    } catch (Exception e) {
                        LogMgr.log(2, "700 " + e.getMessage());
                    }
                } else {
                    LogMgr.log(6, "002");
                }
            } catch (Exception e2) {
                LogMgr.log(1, "800 " + e2.getMessage());
                if (baseMfiEventCallbackStopLoggingInOut != null) {
                    try {
                        baseMfiEventCallbackStopLoggingInOut.onError(200, "Unknown error.");
                    } catch (Exception unused) {
                        LogMgr.log(2, "701 " + e2.getMessage());
                    }
                }
            }
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.ISilentStartEventCallback
        public void onError(int i, String str) throws RemoteException {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopLoggingInOut = MfiClient.this.mMfiClientAccess.stopLoggingInOut();
                if (baseMfiEventCallbackStopLoggingInOut != null) {
                    LogMgr.log(6, "001");
                    try {
                        baseMfiEventCallbackStopLoggingInOut.onError(i, str);
                    } catch (Exception e) {
                        LogMgr.log(2, "700 " + e.getMessage());
                    }
                }
            } catch (Exception e2) {
                LogMgr.log(1, "800 " + e2.getMessage());
            }
            LogMgr.log(3, "999");
        }
    };
    private ILogoutEventCallback mILogoutEventCallback = new ILogoutEventCallback.Stub() { // from class: com.felicanetworks.mfm.mfcutil.mfc.mfi.MfiClient.3
        @Override // com.felicanetworks.mfc.mfi.ILogoutEventCallback
        public void onSuccess() throws RemoteException {
            LogMgr.log(3, "%s", "000");
            BaseMfiEventCallback baseMfiEventCallbackStopLoggingInOut = null;
            try {
                baseMfiEventCallbackStopLoggingInOut = MfiClient.this.mMfiClientAccess.stopLoggingInOut();
                MfiClient.this.mMfiClientAccess.clearLoginUser();
                if (baseMfiEventCallbackStopLoggingInOut != null && (baseMfiEventCallbackStopLoggingInOut instanceof StopEventCallback)) {
                    LogMgr.log(6, "%s", "001");
                    try {
                        ((StopEventCallback) baseMfiEventCallbackStopLoggingInOut).onSuccess();
                    } catch (Exception e) {
                        LogMgr.log(2, "%s %s", "700", e.getMessage());
                    }
                } else {
                    LogMgr.log(6, "%s", "002");
                }
            } catch (Exception e2) {
                LogMgr.log(1, "%s %s", "800", e2.getMessage());
                if (baseMfiEventCallbackStopLoggingInOut != null) {
                    try {
                        baseMfiEventCallbackStopLoggingInOut.onError(200, "Unknown error.");
                    } catch (Exception unused) {
                        LogMgr.log(2, "%s %s", "701", e2.getMessage());
                    }
                }
            }
            LogMgr.log(3, "%s", "999");
        }

        @Override // com.felicanetworks.mfc.mfi.ILogoutEventCallback
        public void onError(int i, String str) throws RemoteException {
            LogMgr.log(3, "%s", "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopLoggingInOut = MfiClient.this.mMfiClientAccess.stopLoggingInOut();
                if (baseMfiEventCallbackStopLoggingInOut != null) {
                    LogMgr.log(6, "%s", "001");
                    try {
                        baseMfiEventCallbackStopLoggingInOut.onError(i, str);
                    } catch (Exception e) {
                        LogMgr.log(2, "%s %s", "700", e.getMessage());
                    }
                }
            } catch (Exception e2) {
                LogMgr.log(1, "%s %s", "800", e2.getMessage());
            }
            LogMgr.log(3, "%s", "999");
        }
    };
    private ISilentStartForMfiAdminEventCallback mISilentStartForMfiAdminEventCallback = new ISilentStartForMfiAdminEventCallback.Stub() { // from class: com.felicanetworks.mfm.mfcutil.mfc.mfi.MfiClient.4
        @Override // com.felicanetworks.mfc.mfi.ISilentStartForMfiAdminEventCallback
        public void onSuccess() throws RemoteException {
            LogMgr.log(3, "000");
            BaseMfiEventCallback baseMfiEventCallbackStopLoggingInOut = null;
            try {
                baseMfiEventCallbackStopLoggingInOut = MfiClient.this.mMfiClientAccess.stopLoggingInOut();
                User user = new User(MfiClient.this.mMfiClientAccess);
                MfiAdmin mfiAdmin = new MfiAdmin(MfiClient.this.mMfiClientAccess);
                MfiClient.this.mMfiClientAccess.setLoginUser(user, mfiAdmin);
                if (baseMfiEventCallbackStopLoggingInOut != null && (baseMfiEventCallbackStopLoggingInOut instanceof SilentStartForMfiAdminEventCallback)) {
                    LogMgr.log(6, "001");
                    try {
                        ((SilentStartForMfiAdminEventCallback) baseMfiEventCallbackStopLoggingInOut).onSuccess(user, mfiAdmin);
                    } catch (Exception e) {
                        LogMgr.log(2, "700 " + e.getMessage());
                    }
                } else {
                    LogMgr.log(6, "002");
                }
            } catch (Exception e2) {
                LogMgr.log(1, "800 " + e2.getMessage());
                if (baseMfiEventCallbackStopLoggingInOut != null) {
                    try {
                        baseMfiEventCallbackStopLoggingInOut.onError(200, "Unknown error.");
                    } catch (Exception unused) {
                        LogMgr.log(2, "701 " + e2.getMessage());
                    }
                }
            }
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.ISilentStartForMfiAdminEventCallback
        public void onSuccessNoLogin() throws RemoteException {
            BaseMfiEventCallback baseMfiEventCallbackStopLoggingInOut;
            Exception e;
            LogMgr.log(3, "000");
            try {
                baseMfiEventCallbackStopLoggingInOut = MfiClient.this.mMfiClientAccess.stopLoggingInOut();
            } catch (Exception e2) {
                baseMfiEventCallbackStopLoggingInOut = null;
                e = e2;
            }
            try {
                MfiAdmin mfiAdmin = new MfiAdmin(MfiClient.this.mMfiClientAccess);
                MfiClient.this.mMfiClientAccess.setLoginUser(null, mfiAdmin);
                if (baseMfiEventCallbackStopLoggingInOut != null && (baseMfiEventCallbackStopLoggingInOut instanceof SilentStartForMfiAdminEventCallback)) {
                    LogMgr.log(6, "001");
                    try {
                        ((SilentStartForMfiAdminEventCallback) baseMfiEventCallbackStopLoggingInOut).onSuccessNoLogin(mfiAdmin);
                    } catch (Exception e3) {
                        LogMgr.log(2, "700 " + e3.getMessage());
                    }
                } else {
                    LogMgr.log(6, "002");
                }
            } catch (Exception e4) {
                e = e4;
                LogMgr.log(1, "800 " + e.getMessage());
                if (baseMfiEventCallbackStopLoggingInOut != null) {
                    try {
                        baseMfiEventCallbackStopLoggingInOut.onError(200, "Unknown error.");
                    } catch (Exception unused) {
                        LogMgr.log(2, "701 " + e.getMessage());
                    }
                }
            }
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.ISilentStartForMfiAdminEventCallback
        public void onRequestActivity(Intent intent) throws RemoteException {
            BaseMfiEventCallback baseMfiEventCallbackStopLoggingInOut = null;
            try {
                baseMfiEventCallbackStopLoggingInOut = MfiClient.this.mMfiClientAccess.stopLoggingInOut();
                if (baseMfiEventCallbackStopLoggingInOut != null && (baseMfiEventCallbackStopLoggingInOut instanceof SilentStartForMfiAdminEventCallback)) {
                    LogMgr.log(6, "001");
                    try {
                        ((SilentStartForMfiAdminEventCallback) baseMfiEventCallbackStopLoggingInOut).onRequestActivity(intent);
                    } catch (Exception e) {
                        LogMgr.log(2, "700 " + e.getMessage());
                    }
                } else {
                    LogMgr.log(6, "002");
                }
            } catch (Exception e2) {
                LogMgr.log(1, "800 " + e2.getMessage());
                if (baseMfiEventCallbackStopLoggingInOut != null) {
                    try {
                        baseMfiEventCallbackStopLoggingInOut.onError(200, "Unknown error.");
                    } catch (Exception unused) {
                        LogMgr.log(2, "701 " + e2.getMessage());
                    }
                }
            }
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.ISilentStartForMfiAdminEventCallback
        public void onError(int i, String str) throws RemoteException {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopLoggingInOut = MfiClient.this.mMfiClientAccess.stopLoggingInOut();
                if (baseMfiEventCallbackStopLoggingInOut != null) {
                    LogMgr.log(6, "001");
                    try {
                        baseMfiEventCallbackStopLoggingInOut.onError(i, str);
                    } catch (Exception e) {
                        LogMgr.log(2, "700 " + e.getMessage());
                    }
                }
            } catch (Exception e2) {
                LogMgr.log(1, "800 " + e2.getMessage());
            }
            LogMgr.log(3, "999");
        }
    };
    private IServerOperationEventCallback mIServerOperationEventCallback = new IServerOperationEventCallback.Stub() { // from class: com.felicanetworks.mfm.mfcutil.mfc.mfi.MfiClient.5
        @Override // com.felicanetworks.mfc.mfi.IServerOperationEventCallback
        public void onSuccess() {
            LogMgr.log(3, "%s", "000");
            BaseMfiEventCallback baseMfiEventCallbackStopLoggingInOut = null;
            try {
                baseMfiEventCallbackStopLoggingInOut = MfiClient.this.mMfiClientAccess.stopLoggingInOut();
                if (baseMfiEventCallbackStopLoggingInOut != null && (baseMfiEventCallbackStopLoggingInOut instanceof ServerOperationEventCallback)) {
                    LogMgr.log(6, "%s", "001");
                    try {
                        ((ServerOperationEventCallback) baseMfiEventCallbackStopLoggingInOut).onSuccess();
                    } catch (Exception e) {
                        LogMgr.log(2, "%s %s", "700", e.getMessage());
                    }
                } else {
                    LogMgr.log(6, "%s", "002");
                }
            } catch (Exception e2) {
                LogMgr.log(1, "%s %s", "800", e2.getMessage());
                if (baseMfiEventCallbackStopLoggingInOut != null) {
                    try {
                        baseMfiEventCallbackStopLoggingInOut.onError(200, "Unknown error.");
                    } catch (Exception unused) {
                        LogMgr.log(2, "%s %s", "701", e2.getMessage());
                    }
                }
            }
            LogMgr.log(3, "%s", "999");
        }

        @Override // com.felicanetworks.mfc.mfi.IServerOperationEventCallback
        public void onError(int i, String str) {
            LogMgr.log(3, "%s", "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopLoggingInOut = MfiClient.this.mMfiClientAccess.stopLoggingInOut();
                if (baseMfiEventCallbackStopLoggingInOut != null) {
                    LogMgr.log(6, "%s", "001");
                    try {
                        baseMfiEventCallbackStopLoggingInOut.onError(i, str);
                    } catch (Exception e) {
                        LogMgr.log(2, "%s %s", "700", e.getMessage());
                    }
                }
            } catch (Exception e2) {
                LogMgr.log(1, "%s %s", "800", e2.getMessage());
            }
            LogMgr.log(3, "%s", "999");
        }
    };
    private IUnsupportMfiService1CardExistEventCallback mIUnsupportMfiService1CardExistEventCallback = new IUnsupportMfiService1CardExistEventCallback.Stub() { // from class: com.felicanetworks.mfm.mfcutil.mfc.mfi.MfiClient.6
        @Override // com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardExistEventCallback
        public void onSuccess(boolean z, String str) throws RemoteException {
            LogMgr.log(3, "000");
            BaseMfiEventCallback baseMfiEventCallback = null;
            BaseMfiEventCallback baseMfiEventCallback2 = null;
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopLoggingInOut = MfiClient.this.mMfiClientAccess.stopLoggingInOut();
                try {
                    try {
                        if (baseMfiEventCallbackStopLoggingInOut instanceof UnsupportMfiService1CardExistEventCallback) {
                            LogMgr.log(6, "001");
                            try {
                                ((UnsupportMfiService1CardExistEventCallback) baseMfiEventCallbackStopLoggingInOut).onSuccess(z, str != null ? new LocalPartialCardInfoJson(str).getLocalPartialCardInfo() : null);
                            } catch (Exception e) {
                                LogMgr.log(2, "700", e.getMessage());
                            }
                        } else {
                            LogMgr.log(6, "002");
                        }
                    } catch (JSONException e2) {
                        e = e2;
                        baseMfiEventCallback2 = baseMfiEventCallbackStopLoggingInOut;
                        LogMgr.log(1, "800", e.getMessage());
                        if (baseMfiEventCallback2 != null) {
                            try {
                                baseMfiEventCallback2.onError(200, "Json parse error.");
                            } catch (Exception unused) {
                                LogMgr.log(2, "701", e.getMessage());
                            }
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    baseMfiEventCallback = baseMfiEventCallbackStopLoggingInOut;
                    LogMgr.log(1, "801", e.getMessage());
                    if (baseMfiEventCallback != null) {
                        try {
                            baseMfiEventCallback.onError(200, "Unknown error.");
                        } catch (Exception unused2) {
                            LogMgr.log(2, "701", e.getMessage());
                        }
                    }
                }
            } catch (JSONException e4) {
                e = e4;
            } catch (Exception e5) {
                e = e5;
            }
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardExistEventCallback
        public void onError(int i, String str) throws RemoteException {
            BaseMfiEventCallback baseMfiEventCallbackStopLoggingInOut = null;
            try {
                baseMfiEventCallbackStopLoggingInOut = MfiClient.this.mMfiClientAccess.stopLoggingInOut();
                if (baseMfiEventCallbackStopLoggingInOut instanceof UnsupportMfiService1CardExistEventCallback) {
                    LogMgr.log(6, "001");
                    try {
                        ((UnsupportMfiService1CardExistEventCallback) baseMfiEventCallbackStopLoggingInOut).onError(i, str);
                    } catch (Exception e) {
                        LogMgr.log(2, "700", e.getMessage());
                    }
                } else {
                    LogMgr.log(6, "002");
                }
            } catch (Exception e2) {
                LogMgr.log(1, "800", e2.getMessage());
                if (baseMfiEventCallbackStopLoggingInOut != null) {
                    try {
                        baseMfiEventCallbackStopLoggingInOut.onError(200, "Unknown error.");
                    } catch (Exception unused) {
                        LogMgr.log(2, "701", e2.getMessage());
                    }
                }
            }
        }
    };
    private IUnsupportMfiService1CardDeleteEventCallback mIUnsupportMfiService1CardDeleteEventCallback = new IUnsupportMfiService1CardDeleteEventCallback.Stub() { // from class: com.felicanetworks.mfm.mfcutil.mfc.mfi.MfiClient.7
        @Override // com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardDeleteEventCallback
        public void onSuccess() throws RemoteException {
            LogMgr.log(3, "000");
            BaseMfiEventCallback baseMfiEventCallbackStopLoggingInOut = null;
            try {
                baseMfiEventCallbackStopLoggingInOut = MfiClient.this.mMfiClientAccess.stopLoggingInOut();
                if (baseMfiEventCallbackStopLoggingInOut != null && (baseMfiEventCallbackStopLoggingInOut instanceof UnsupportMfiService1CardDeleteEventCallback)) {
                    LogMgr.log(6, "001");
                    try {
                        ((UnsupportMfiService1CardDeleteEventCallback) baseMfiEventCallbackStopLoggingInOut).onSuccess();
                    } catch (Exception e) {
                        LogMgr.log(2, "700" + e.getMessage());
                    }
                } else {
                    LogMgr.log(6, "002");
                }
            } catch (Exception e2) {
                LogMgr.log(1, "800", e2.getMessage());
                if (baseMfiEventCallbackStopLoggingInOut != null) {
                    try {
                        baseMfiEventCallbackStopLoggingInOut.onError(200, "Unknown error.");
                    } catch (Exception unused) {
                        LogMgr.log(2, "701", e2.getMessage());
                    }
                }
            }
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardDeleteEventCallback
        public void onError(int i, String str) throws RemoteException {
            BaseMfiEventCallback baseMfiEventCallbackStopLoggingInOut = null;
            try {
                baseMfiEventCallbackStopLoggingInOut = MfiClient.this.mMfiClientAccess.stopLoggingInOut();
                if (baseMfiEventCallbackStopLoggingInOut instanceof UnsupportMfiService1CardDeleteEventCallback) {
                    LogMgr.log(6, "001");
                    try {
                        ((UnsupportMfiService1CardDeleteEventCallback) baseMfiEventCallbackStopLoggingInOut).onError(i, str);
                    } catch (Exception e) {
                        LogMgr.log(2, "700", e.getMessage());
                    }
                } else {
                    LogMgr.log(6, "002");
                }
            } catch (Exception e2) {
                LogMgr.log(1, "800", e2.getMessage());
                if (baseMfiEventCallbackStopLoggingInOut != null) {
                    try {
                        baseMfiEventCallbackStopLoggingInOut.onError(200, "Unknown error.");
                    } catch (Exception unused) {
                        LogMgr.log(2, "701", e2.getMessage());
                    }
                }
            }
        }
    };

    static {
        ArrayList arrayList = new ArrayList();
        arrayList.add("Google");
        ACCOUNT_ISSUER_LIST = Collections.unmodifiableList(arrayList);
    }

    private class ICachedCardListEventCallback extends IDataListEventCallback.Stub {
        final List<String> mPartialDataList;

        private ICachedCardListEventCallback() {
            this.mPartialDataList = Collections.synchronizedList(new ArrayList());
        }

        @Override // com.felicanetworks.mfc.mfi.IDataListEventCallback
        public void onPart(List<String> list, boolean z) throws RemoteException {
            LogMgr.log(3, "000");
            this.mPartialDataList.addAll(list);
            LogMgr.log(6, "001 partialDataList=" + this.mPartialDataList.size() + " more=" + z);
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.IDataListEventCallback
        public void onFinished(final int i) throws RemoteException {
            LogMgr.log(3, "000 size=" + i);
            new Thread(new Runnable() { // from class: com.felicanetworks.mfm.mfcutil.mfc.mfi.MfiClient.ICachedCardListEventCallback.1
                /* JADX WARN: Removed duplicated region for block: B:49:0x00fd A[EXC_TOP_SPLITTER, SYNTHETIC] */
                /* JADX WARN: Removed duplicated region for block: B:52:0x00d9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public void run() {
                    /*
                        Method dump skipped, instruction units count: 299
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.mfcutil.mfc.mfi.MfiClient.ICachedCardListEventCallback.AnonymousClass1.run():void");
                }
            }).start();
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.IDataListEventCallback
        public void onError(int i, String str) throws RemoteException {
            try {
                try {
                    BaseMfiEventCallback baseMfiEventCallbackStopOnline = MfiClient.this.mMfiClientAccess.stopOnline();
                    if (baseMfiEventCallbackStopOnline instanceof CachedCardListEventCallback) {
                        ((CachedCardListEventCallback) baseMfiEventCallbackStopOnline).onError(i, str);
                    }
                } catch (Exception e) {
                    LogMgr.log(2, "700", e.getMessage());
                }
            } finally {
                this.mPartialDataList.clear();
            }
        }
    }

    public MfiClient(MfiClientAccess mfiClientAccess) {
        LogMgr.log(3, "%s", "000");
        this.mMfiClientAccess = mfiClientAccess;
        LogMgr.log(3, "%s", "999");
    }

    public synchronized void start(String str, String str2, StartEventCallback startEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "%s", "000");
        if (str == null) {
            if (str2 != null) {
                LogMgr.log(2, "%s Account issuer is null.", "700");
                throw new IllegalArgumentException("The specified AccountIssuer is invalid.");
            }
        } else {
            if (!ACCOUNT_ISSUER_LIST.contains(str)) {
                LogMgr.log(2, "%s Account issuer is invalid. %s", "701", str);
                throw new IllegalArgumentException("The specified AccountIssuer is invalid.");
            }
            if (str2 == null) {
                LogMgr.log(2, "%s Account name is null.", "702");
                throw new IllegalArgumentException("The specified AccountName is null.");
            }
        }
        if (startEventCallback == null) {
            LogMgr.log(2, "%s callback is null.", "703");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        if (this.mMfiClientAccess.isMfiClientStarted()) {
            LogMgr.log(2, "%s Already logged in.", "704");
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_ALREADY_STARTED, null);
        }
        this.mMfiClientAccess.startLoggingInOut(startEventCallback, true);
        try {
            try {
                MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().login(str, str2, this.mILoginEventCallback));
                LogMgr.log(3, "%s", "999");
            } catch (Exception e) {
                LogMgr.log(2, "%s %s", "707", "Other Exception");
                LogMgr.printStackTrace(7, e);
                this.mMfiClientAccess.stopLoggingInOut();
                throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
            }
        } catch (FelicaException e2) {
            LogMgr.log(2, "%s %s id:%d type:%d", "705", e2.toString(), Integer.valueOf(e2.getID()), Integer.valueOf(e2.getType()));
            this.mMfiClientAccess.stopLoggingInOut();
            throw new MfiClientException(e2);
        } catch (IllegalArgumentException e3) {
            LogMgr.log(2, "%s %s", "706", e3.toString());
            this.mMfiClientAccess.stopLoggingInOut();
            throw e3;
        }
    }

    public synchronized void silentStart(String str, String str2, boolean z, int i, SilentStartForMfiAdminEventCallback silentStartForMfiAdminEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (z) {
            if (str == null) {
                if (str2 != null) {
                    LogMgr.log(2, "700 Account issuer is null.");
                    throw new IllegalArgumentException("The specified AccountIssuer is invalid.");
                }
            } else {
                if (!ACCOUNT_ISSUER_LIST.contains(str)) {
                    LogMgr.log(2, "701 Account issuer is invalid. " + str);
                    throw new IllegalArgumentException("The specified AccountIssuer is invalid.");
                }
                if (str2 == null) {
                    LogMgr.log(2, "702 Account name is null.");
                    throw new IllegalArgumentException("The specified AccountName is null.");
                }
            }
        }
        if (silentStartForMfiAdminEventCallback == null) {
            LogMgr.log(2, "703 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        if (this.mMfiClientAccess.isMfiClientStarted()) {
            LogMgr.log(2, "704 started by User.");
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_ALREADY_STARTED, null);
        }
        this.mMfiClientAccess.startLoggingInOut(silentStartForMfiAdminEventCallback, true);
        try {
            try {
                try {
                    MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().silentStartForMfiAdmin(str, str2, z, i, this.mISilentStartForMfiAdminEventCallback));
                    LogMgr.log(3, "999");
                } catch (FelicaException e) {
                    LogMgr.log(2, "705 " + e.toString() + " id:" + e.getID() + " type:" + e.getType());
                    this.mMfiClientAccess.stopLoggingInOut();
                    throw new MfiClientException(e);
                }
            } catch (IllegalArgumentException e2) {
                LogMgr.log(2, "706 " + e2.toString());
                this.mMfiClientAccess.stopLoggingInOut();
                throw e2;
            }
        } catch (Exception e3) {
            LogMgr.log(2, "707 Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopLoggingInOut();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized void silentStart(String str, String str2, int i, SilentStartEventCallback silentStartEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (str == null) {
            if (str2 != null) {
                LogMgr.log(2, "700 Account issuer is null.");
                throw new IllegalArgumentException("The specified AccountIssuer is invalid.");
            }
        } else {
            if (!ACCOUNT_ISSUER_LIST.contains(str)) {
                LogMgr.log(2, "701 Account issuer is invalid. " + str);
                throw new IllegalArgumentException("The specified AccountIssuer is invalid.");
            }
            if (str2 == null) {
                LogMgr.log(2, "702 Account name is null.");
                throw new IllegalArgumentException("The specified AccountName is null.");
            }
        }
        if (silentStartEventCallback == null) {
            LogMgr.log(2, "703 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        if (this.mMfiClientAccess.isMfiClientStarted()) {
            LogMgr.log(2, "704 silentStarted by User.");
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_ALREADY_STARTED, null);
        }
        this.mMfiClientAccess.startLoggingInOut(silentStartEventCallback, true);
        try {
            try {
                MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().silentStart(str, str2, i, this.mISilentStartEventCallback));
                LogMgr.log(3, "999");
            } catch (FelicaException e) {
                LogMgr.log(2, "705 " + e.toString() + " id:" + e.getID() + " type:" + e.getType());
                this.mMfiClientAccess.stopLoggingInOut();
                throw new MfiClientException(e);
            }
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "706 " + e2.toString());
            this.mMfiClientAccess.stopLoggingInOut();
            throw e2;
        } catch (Exception e3) {
            LogMgr.log(2, "707 Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopLoggingInOut();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized void silentStart(String str, String str2, boolean z, int i, int i2, SilentStartForMfiAdminEventCallback silentStartForMfiAdminEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (z) {
            if (str == null) {
                if (str2 != null) {
                    LogMgr.log(2, "700 Account issuer is null.");
                    throw new IllegalArgumentException("The specified AccountIssuer is invalid.");
                }
            } else {
                if (!ACCOUNT_ISSUER_LIST.contains(str)) {
                    LogMgr.log(2, "701 Account issuer is invalid. " + str);
                    throw new IllegalArgumentException("The specified AccountIssuer is invalid.");
                }
                if (str2 == null) {
                    LogMgr.log(2, "702 Account name is null.");
                    throw new IllegalArgumentException("The specified AccountName is null.");
                }
            }
            if (i2 != 1 && i2 != 2 && i2 != 3) {
                LogMgr.log(2, "703 layoutType is unexpected value.");
                throw new IllegalArgumentException("The specified LayoutType is unexpected value.");
            }
        }
        if (silentStartForMfiAdminEventCallback == null) {
            LogMgr.log(2, "704 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        if (this.mMfiClientAccess.isMfiClientStarted()) {
            LogMgr.log(2, "705 started by User.");
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_ALREADY_STARTED, null);
        }
        this.mMfiClientAccess.startLoggingInOut(silentStartForMfiAdminEventCallback, true);
        try {
            MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().silentStartForMfiAdminV2(str, str2, z, i, i2, this.mISilentStartForMfiAdminEventCallback));
            LogMgr.log(3, "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "706 " + e.toString() + " id:" + e.getID() + " type:" + e.getType());
            this.mMfiClientAccess.stopLoggingInOut();
            throw new MfiClientException(e);
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "707 " + e2.toString());
            this.mMfiClientAccess.stopLoggingInOut();
            throw e2;
        } catch (Exception e3) {
            LogMgr.log(2, "708 Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopLoggingInOut();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized void silentStart(String str, String str2, int i, SilentStartForMfiAdminEventCallback silentStartForMfiAdminEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (str == null) {
            if (str2 != null) {
                LogMgr.log(2, "700 Account issuer is null.");
                throw new IllegalArgumentException("The specified AccountIssuer is invalid.");
            }
        } else {
            if (!ACCOUNT_ISSUER_LIST.contains(str)) {
                LogMgr.log(2, "701 Account issuer is invalid. " + str);
                throw new IllegalArgumentException("The specified AccountIssuer is invalid.");
            }
            if (str2 == null) {
                LogMgr.log(2, "702 Account name is null.");
                throw new IllegalArgumentException("The specified AccountName is null.");
            }
        }
        if (silentStartForMfiAdminEventCallback == null) {
            LogMgr.log(2, "703 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        if (this.mMfiClientAccess.isMfiClientStarted()) {
            LogMgr.log(2, "704 started by User.");
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_ALREADY_STARTED, null);
        }
        this.mMfiClientAccess.startLoggingInOut(silentStartForMfiAdminEventCallback, true);
        try {
            try {
                MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().silentStartForMfiAdmin(str, str2, true, i, this.mISilentStartForMfiAdminEventCallback));
                LogMgr.log(3, "999");
            } catch (Exception e) {
                LogMgr.log(2, "707 Other Exception");
                LogMgr.printStackTrace(7, e);
                this.mMfiClientAccess.stopLoggingInOut();
                throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
            }
        } catch (FelicaException e2) {
            LogMgr.log(2, "705 " + e2.toString() + " id:" + e2.getID() + " type:" + e2.getType());
            this.mMfiClientAccess.stopLoggingInOut();
            throw new MfiClientException(e2);
        } catch (IllegalArgumentException e3) {
            LogMgr.log(2, "706 " + e3.toString());
            this.mMfiClientAccess.stopLoggingInOut();
            throw e3;
        }
    }

    public synchronized String getCurrentAccountHash() throws MfiClientException {
        FelicaResultInfoString currentAccountHash;
        LogMgr.log(3, "%s", "000");
        this.mMfiClientAccess.checkActivated();
        try {
            currentAccountHash = this.mMfiClientAccess.getIMfiFelica().getCurrentAccountHash();
            MfiUtil.checkMfcResult(currentAccountHash);
            LogMgr.log(3, "%s", "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", e.toString(), Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw new MfiClientException(e);
        } catch (Exception e2) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            LogMgr.printStackTrace(7, e2);
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
        return currentAccountHash.getValue();
    }

    public synchronized void clearMfiAccount() throws MfiClientException {
        LogMgr.log(3, "%s", "000");
        this.mMfiClientAccess.checkActivated();
        try {
            try {
                MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().clearMfiAccount());
                LogMgr.log(3, "%s", "999");
            } catch (FelicaException e) {
                LogMgr.log(2, "%s %s id:%d type:%d", "700", e.toString(), Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                throw new MfiClientException(e);
            }
        } catch (Exception e2) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            LogMgr.printStackTrace(7, e2);
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized SeInfo getSeInformation() throws MfiClientException {
        FelicaResultInfoSeInfo seInfomation;
        LogMgr.log(3, "%s", "000");
        this.mMfiClientAccess.checkActivated();
        if (!this.mMfiClientAccess.isMfiClientStarted()) {
            this.mMfiClientAccess.onlineCheck();
        }
        try {
            try {
                seInfomation = this.mMfiClientAccess.getIMfiFelica().getSeInfomation();
                MfiUtil.checkMfcResult(seInfomation);
                LogMgr.log(3, "%s", "999");
            } catch (FelicaException e) {
                LogMgr.log(2, "%s %s id:%d type:%d", "700", e.toString(), Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                throw new MfiClientException(e);
            }
        } catch (Exception e2) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            LogMgr.printStackTrace(7, e2);
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
        return seInfomation.getValue();
    }

    public synchronized void stop(boolean z, StopEventCallback stopEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "%s", "000");
        if (stopEventCallback == null) {
            LogMgr.log(2, "%s callback is null.", "700");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        if (this.mMfiClientAccess.isStartRunning() && !this.mMfiClientAccess.isMfiClientStarted()) {
            LogMgr.log(2, "%s Not logged in.", "701");
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_NOT_STARTED, null);
        }
        this.mMfiClientAccess.startLoggingInOut(stopEventCallback, false);
        try {
            try {
                MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().logout(z, this.mILogoutEventCallback));
                LogMgr.log(3, "%s", "999");
            } catch (IllegalArgumentException e) {
                LogMgr.log(2, "%s %s", "703", e.toString());
                this.mMfiClientAccess.stopLoggingInOut();
                throw e;
            }
        } catch (FelicaException e2) {
            LogMgr.log(2, "%s %s id:%d type:%d", "702", e2.toString(), Integer.valueOf(e2.getID()), Integer.valueOf(e2.getType()));
            this.mMfiClientAccess.stopLoggingInOut();
            throw new MfiClientException(e2);
        } catch (Exception e3) {
            LogMgr.log(2, "%s %s", "704", "Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopLoggingInOut();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized void cancelCardOperation() throws MfiClientException {
        LogMgr.log(3, "%s", "000");
        this.mMfiClientAccess.checkActivated();
        if (!this.mMfiClientAccess.isMfiClientStarted()) {
            LogMgr.log(2, "%s Not logged in.", "700");
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_NOT_STARTED, null);
        }
        try {
            MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().cancelCardOperation());
            LogMgr.log(3, "%s", "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "701", e.toString(), Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw new MfiClientException(e);
        } catch (Exception e2) {
            LogMgr.log(2, "%s %s", "702", "Other Exception");
            LogMgr.printStackTrace(7, e2);
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized int identifyService() throws MfiClientException {
        FelicaResultInfoInt felicaResultInfoIntIdentifyService;
        LogMgr.log(3, "%s", "000");
        this.mMfiClientAccess.checkActivated();
        try {
            felicaResultInfoIntIdentifyService = this.mMfiClientAccess.getIMfiFelica().identifyService();
            MfiUtil.checkMfcResult(felicaResultInfoIntIdentifyService);
            LogMgr.log(3, "%s", "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", e.toString(), Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw new MfiClientException(e);
        } catch (Exception e2) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            LogMgr.printStackTrace(7, e2);
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
        return felicaResultInfoIntIdentifyService.getValue().intValue();
    }

    public synchronized String[] getLocalCidList() throws MfiClientException {
        FelicaResultInfoStringArray localCidListV2;
        LogMgr.log(3, "000");
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.onlineCheck();
        try {
            localCidListV2 = this.mMfiClientAccess.getIMfiFelica().getLocalCidListV2();
            MfiUtil.checkMfcResult(localCidListV2);
            LogMgr.log(3, "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "700 " + e.toString() + " id:" + e.getID() + " type:" + e.getType());
            throw new MfiClientException(e);
        } catch (Exception e2) {
            LogMgr.log(2, "702 Other Exception");
            LogMgr.printStackTrace(7, e2);
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
        return localCidListV2.getValue();
    }

    public synchronized int getUnsupportMfiService1CardPosition() throws MfiClientException {
        FelicaResultInfoInt unsupportMfiService1CardPosition;
        LogMgr.log(3, "000");
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.onlineCheck();
        try {
            unsupportMfiService1CardPosition = this.mMfiClientAccess.getIMfiFelica().getUnsupportMfiService1CardPosition();
            MfiUtil.checkMfcResult(unsupportMfiService1CardPosition);
            LogMgr.log(3, "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "700 " + e.toString() + " id:" + e.getID() + " type:" + e.getType());
            throw new MfiClientException(e);
        } catch (Exception e2) {
            LogMgr.log(2, "701 Other Exception");
            LogMgr.printStackTrace(7, e2);
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
        return unsupportMfiService1CardPosition.getValue().intValue();
    }

    public synchronized LocalPartialCardInfo[] getLocalPartialCardInfoList(String str) throws IllegalArgumentException, MfiClientException {
        LocalPartialCardInfo[] localPartialCardInfoArr;
        LogMgr.log(3, "000");
        if (str == null) {
            LogMgr.log(2, "700 serviceId is null.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (!str.matches("[0-9a-zA-Z]*")) {
            LogMgr.log(2, "701 serviceId involves invalid character.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (str.length() != 8) {
            LogMgr.log(2, "702 serviceId length is invalid.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.onlineCheck();
        try {
            try {
                FelicaResultInfoStringArray localPartialCardInfoList = this.mMfiClientAccess.getIMfiFelica().getLocalPartialCardInfoList(str);
                MfiUtil.checkMfcResult(localPartialCardInfoList);
                String[] value = localPartialCardInfoList.getValue();
                if (value == null) {
                    LogMgr.log(3, "997 jsonArray is null.");
                    throw new MfiClientException(1, MfiClientException.TYPE_GET_LOCAL_PARTIAL_CARD_INFO_LIST_FAILED, "Json parse error.");
                }
                int length = value.length;
                localPartialCardInfoArr = new LocalPartialCardInfo[length];
                for (int i = 0; i < length; i++) {
                    try {
                        localPartialCardInfoArr[i] = new LocalPartialCardInfoJson(value[i]).getLocalPartialCardInfo();
                    } catch (JSONException e) {
                        LogMgr.log(3, "998 " + e.toString());
                        throw new MfiClientException(1, MfiClientException.TYPE_GET_LOCAL_PARTIAL_CARD_INFO_LIST_FAILED, "Json parse error.");
                    }
                }
                LogMgr.log(3, "999");
            } catch (Exception e2) {
                LogMgr.log(2, "702 Other Exception");
                LogMgr.printStackTrace(7, e2);
                throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
            }
        } catch (FelicaException e3) {
            LogMgr.log(2, "700 " + e3.toString() + " id:" + e3.getID() + " type:" + e3.getType());
            throw new MfiClientException(e3);
        } catch (IllegalArgumentException e4) {
            LogMgr.log(2, "701 " + e4.toString());
            throw e4;
        }
        return localPartialCardInfoArr;
    }

    public void cancelMfiOffline() throws MfiClientException {
        LogMgr.log(3, "000");
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.onlineCheck();
        try {
            MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().cancelMfiOffline());
            LogMgr.log(3, "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "700 " + e.toString() + " id:" + e.getID() + " type:" + e.getType());
            throw new MfiClientException(e);
        } catch (Exception e2) {
            LogMgr.log(2, "701 Other Exception");
            LogMgr.printStackTrace(7, e2);
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized void provisionServerOperation(ServerOperationEventCallback serverOperationEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (serverOperationEventCallback == null) {
            LogMgr.log(2, "%s callback is null.", "700");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.startLoggingInOut(serverOperationEventCallback, true);
        try {
            MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().provisionServerOperation(this.mIServerOperationEventCallback));
            LogMgr.log(3, "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "700 " + e.toString() + " id:" + e.getID() + " type:" + e.getType());
            this.mMfiClientAccess.stopLoggingInOut();
            throw new MfiClientException(e);
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 " + e2.toString());
            this.mMfiClientAccess.stopLoggingInOut();
            throw e2;
        } catch (Exception e3) {
            LogMgr.log(2, "701 Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopLoggingInOut();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized void existUnsupportMfiService1Card(String str, UnsupportMfiService1CardExistEventCallback unsupportMfiService1CardExistEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (unsupportMfiService1CardExistEventCallback == null) {
            LogMgr.log(2, "700 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        if (str == null) {
            LogMgr.log(2, "701 serviceId is null.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (!str.matches("[0-9a-zA-Z]*")) {
            LogMgr.log(2, "702 serviceId involves invalid character.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (str.length() != 8) {
            LogMgr.log(2, "703 serviceId length is invalid.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.startLoggingInOut(unsupportMfiService1CardExistEventCallback, true);
        try {
            MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().existUnsupportMfiService1Card(str, this.mIUnsupportMfiService1CardExistEventCallback));
            LogMgr.log(3, "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "704 " + e.toString() + " id:" + e.getID() + " type:" + e.getType());
            this.mMfiClientAccess.stopLoggingInOut();
            throw new MfiClientException(e);
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "705 " + e2.toString());
            this.mMfiClientAccess.stopLoggingInOut();
            throw e2;
        } catch (Exception e3) {
            LogMgr.log(2, "706 Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopLoggingInOut();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized void getCachedCardList(CachedCardListEventCallback cachedCardListEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (cachedCardListEventCallback == null) {
            LogMgr.log(2, "700 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        if (this.mMfiClientAccess.isMfiClientStarted()) {
            LogMgr.log(2, "701 started by User.");
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_STARTED, null);
        }
        this.mMfiClientAccess.startOnlineWithoutLogin(cachedCardListEventCallback);
        try {
            try {
                try {
                    MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().getCachedCardList(new ICachedCardListEventCallback()));
                    LogMgr.log(3, "999");
                } catch (IllegalArgumentException e) {
                    LogMgr.log(2, "703 " + e.toString());
                    this.mMfiClientAccess.stopOnline();
                    throw e;
                }
            } catch (FelicaException e2) {
                LogMgr.log(2, "702 " + e2.toString() + " id:" + e2.getID() + " type:" + e2.getType());
                this.mMfiClientAccess.stopOnline();
                throw new MfiClientException(e2);
            }
        } catch (Exception e3) {
            LogMgr.log(2, "704 Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized int existEmptySlot() throws MfiClientException {
        FelicaResultInfoInt felicaResultInfoIntExistEmptySlot;
        LogMgr.log(3, "000");
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.onlineCheck();
        try {
            try {
                felicaResultInfoIntExistEmptySlot = this.mMfiClientAccess.getIMfiFelica().existEmptySlot();
                MfiUtil.checkMfcResult(felicaResultInfoIntExistEmptySlot);
                LogMgr.log(3, "999");
            } catch (FelicaException e) {
                LogMgr.log(2, "700 " + e.toString() + " id:" + e.getID() + " type:" + e.getType());
                throw new MfiClientException(e);
            }
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "701 " + e2.toString());
            throw e2;
        } catch (Exception e3) {
            LogMgr.log(2, "702 Other Exception");
            LogMgr.printStackTrace(7, e3);
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
        return felicaResultInfoIntExistEmptySlot.getValue().intValue();
    }

    public synchronized void deleteUnsupportMfiService1Card(String str, UnsupportMfiService1CardDeleteEventCallback unsupportMfiService1CardDeleteEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (str == null) {
            LogMgr.log(2, "700 linkageData is null.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (str.isEmpty()) {
            LogMgr.log(2, "701 linkageData is empty.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (unsupportMfiService1CardDeleteEventCallback == null) {
            LogMgr.log(2, "702 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.startLoggingInOut(unsupportMfiService1CardDeleteEventCallback, true);
        try {
            MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().deleteUnsupportMfiService1Card(str, this.mIUnsupportMfiService1CardDeleteEventCallback));
            LogMgr.log(3, "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "701 " + e.toString() + " id:" + e.getID() + " type:" + e.getType());
            this.mMfiClientAccess.stopLoggingInOut();
            throw new MfiClientException(e);
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "702 " + e2.toString());
            this.mMfiClientAccess.stopLoggingInOut();
            throw e2;
        } catch (Exception e3) {
            LogMgr.log(2, "703 Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopLoggingInOut();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized boolean isLoginedAccount(String str) throws IllegalArgumentException, MfiClientException {
        FelicaResultInfoBoolean felicaResultInfoBooleanIsLoginedAccount;
        LogMgr.log(3, "000");
        if (str == null) {
            LogMgr.log(2, "700 accountName is null.");
            throw new IllegalArgumentException("The specified AccountName is null.");
        }
        if (str.isEmpty()) {
            LogMgr.log(2, "701 accountName is empty.");
            throw new IllegalArgumentException("The specified AccountName is empty.");
        }
        this.mMfiClientAccess.checkActivated();
        try {
            try {
                felicaResultInfoBooleanIsLoginedAccount = this.mMfiClientAccess.getIMfiFelica().isLoginedAccount(str);
                MfiUtil.checkMfcResult(felicaResultInfoBooleanIsLoginedAccount);
                LogMgr.log(3, "999");
            } catch (Exception e) {
                LogMgr.log(2, "704 : Other Exception");
                LogMgr.printStackTrace(7, e);
                throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
            }
        } catch (FelicaException e2) {
            LogMgr.log(2, "702 " + e2.toString() + " id:" + e2.getID() + " type:" + e2.getType());
            throw new MfiClientException(e2);
        } catch (IllegalArgumentException e3) {
            LogMgr.log(2, "703 " + e3.toString());
            throw e3;
        }
        return felicaResultInfoBooleanIsLoginedAccount.getValue().booleanValue();
    }

    public void finish() {
        LogMgr.log(3, "%s", "000");
        LogMgr.log(3, "%s", "999");
    }
}
