package com.felicanetworks.mfc.mfi;

import android.content.Intent;
import android.os.RemoteException;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.FelicaResultInfo;
import com.felicanetworks.mfc.mfi.MfiClientExternalLogConst;
import com.felicanetworks.mfc.mfi.felica.access_control.AccessConfig;
import com.felicanetworks.mfc.mfi.fws.FwsClientFacade;
import com.felicanetworks.mfc.mfi.fws.FwsConst;
import com.felicanetworks.mfc.mfi.fws.FwsException;
import com.felicanetworks.mfc.mfi.fws.IndividualSpChecker;
import com.felicanetworks.mfc.mfi.fws.json.GetMfiControlInfoResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.JwsObject;
import com.felicanetworks.mfc.mfi.fws.json.LinkageDataJson;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectSharedPreferences;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.ServiceTypeInfoUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
class MfiOnline {
    static final int NOT_STARTED = 0;
    static final int STARTED_BY_CARD_ADMIN = 2;
    static final int STARTED_BY_USER = 1;
    private static MfiOnline sInstance;
    private ExecuteServerOperationEventCallback mExecuteServerOperationEventCallback;
    private FelicaWrapper mFelicaWrapper;
    private ICachedCardEnableEventCallback mICachedCardEnableEventCallback;
    private IDataListEventCallback mICachedCardListEventCallback;
    private IDataListEventCallback mICachedCardListWithIntegrityCheckEventCallback;
    private ICardAccessEventCallback mICardAccessEventCallback;
    private ICardAdditionalInfoListEventCallback mICardAdditionalInfoListEventCallback;
    private ICardDeleteEventCallback mICardDeleteEventCallback;
    private ICardDeleteV2EventCallback mICardDeleteV2EventCallback;
    private ICardDisableEventCallback mICardDisableEventCallback;
    private ICardDisableV2EventCallback mICardDisableV2EventCallback;
    private ICardEnableEventCallback mICardEnableEventCallback;
    private ICardEnableV2EventCallback mICardEnableV2EventCallback;
    private ICardIssueEventCallback mICardIssueEventCallback;
    private ICardIssueV2EventCallback mICardIssueV2EventCallback;
    private ICardKeyMigrateEventCallback mICardKeyMigrateEventCallback;
    private ICardListEventCallback mICardListEventCallback;
    private ICardReissuableDeleteEventCallback mICardReissuableDeleteEventCallback;
    private ICardsUploadEventCallback mICardsUploadToDeleteEventCallback;
    private ICardsUploadEventCallback mICardsUploadToPermanentDeleteEventCallback;
    private IDataListEventCallback mIDataListEventCallback;
    private IGoogleTosGetEventCallback mIGoogleTosGetEventCallback;
    private IInitializedEventCallback mIInitializedEventCallback;
    private ILinkageDataListEventCallback mILinkageDataListEventCallback;
    private ILoginEventCallback mILoginEventCallback;
    private ILogoutEventCallback mILogoutEventCallback;
    private IMemoryClearEventCallback mIMemoryClearEventCallback;
    private IMfiTosDataGetEventCallback mIMfiTosDataGetEventCallback;
    private IPipeEventCallback mIPipeEventCallback;
    private IRemainedCardsEventCallback mIRemainedCardsEventCallback;
    private IServerOperationEventCallback mIServerOperationEventCallback;
    private ISilentStartEventCallback mISilentStartEventCallback;
    private ISilentStartForMfiAdminEventCallback mISilentStartForMfiAdminEventCallback;
    private IUnsupportMfiService1CardDeleteEventCallback mIUnsupportMfiService1CardDeleteEventCallback;
    private IUnsupportMfiService1CardExistEventCallback mIUnsupportMfiService1CardExistEventCallback;
    private boolean mLoggingOut;
    private int mStarted;
    private final OnFinishListener mOnFinishListener = new OnFinishListener();
    private final DataManager mDataManager = new DataManager();
    private FwsClientFacade mFwsClientFacade = new FwsClientFacade();

    private MfiOnline() {
        LogMgr.log(4, "%s", "000");
        LogMgr.log(4, "%s", "999");
    }

    static synchronized MfiOnline getInstance() {
        LogMgr.log(4, "%s", "000");
        if (sInstance == null) {
            LogMgr.log(4, "%s", "001");
            sInstance = new MfiOnline();
        }
        LogMgr.log(4, "%s", "999");
        return sInstance;
    }

    void setFelicaWrapper(FelicaWrapper felicaWrapper) {
        this.mFelicaWrapper = felicaWrapper;
    }

    synchronized void checkNotRunningTask() throws MfiClientException {
        LogMgr.log(4, "000");
        FwsClientFacade fwsClientFacade = this.mFwsClientFacade;
        if (fwsClientFacade != null) {
            fwsClientFacade.checkNotRunningTask();
        }
        LogMgr.log(4, "999");
    }

    void login(String accountIssuer, String accountName, final ILoginEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (accountIssuer == null) {
            if (accountName != null) {
                LogMgr.log(2, "700 Account issuer is null.");
                throw new IllegalArgumentException("The specified AccountIssuer is invalid.");
            }
        } else {
            if (!MfiClientConst.ACCOUNT_ISSUER_LIST.contains(accountIssuer)) {
                LogMgr.log(2, "701 Account issuer is invalid. " + accountIssuer);
                throw new IllegalArgumentException("The specified AccountIssuer is invalid.");
            }
            if (accountName == null) {
                LogMgr.log(2, "702 Account name is null.");
                throw new IllegalArgumentException("The specified AccountName is null.");
            }
        }
        if (callback == null) {
            LogMgr.log(2, "703 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        LogMgr.log(4, "999");
        throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:27:0x0093 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:29:0x0095 */
    /* JADX DEBUG: Multi-variable search result rejected for r15v1, resolved type: java.lang.String */
    /* JADX DEBUG: Multi-variable search result rejected for r15v14, resolved type: java.lang.String */
    /* JADX DEBUG: Multi-variable search result rejected for r15v6, resolved type: java.lang.String */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0129 A[Catch: all -> 0x014f, TRY_ENTER, TRY_LEAVE, TryCatch #7 {, blocks: (B:23:0x008b, B:69:0x0129, B:70:0x012c, B:74:0x014e, B:73:0x013b), top: B:88:0x0037, outer: #9, inners: #11 }] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void silentStart(String str, String str2, int i, ISilentStartEventCallback iSilentStartEventCallback) throws FelicaException, IllegalArgumentException {
        MfiOnline mfiOnline;
        boolean z;
        String str3;
        Throwable th;
        boolean z2;
        MfiOnline mfiOnline2;
        LogMgr.log(4, "000");
        if (str == null) {
            if (str2 != null) {
                LogMgr.log(2, "701 Account issuer is null.");
                throw new IllegalArgumentException("The specified AccountIssuer is invalid.");
            }
        } else {
            if (!MfiClientConst.ACCOUNT_ISSUER_LIST.contains(str)) {
                LogMgr.log(2, "702 Account issuer is invalid. " + str);
                throw new IllegalArgumentException("The specified AccountIssuer is invalid.");
            }
            if (str2 == null) {
                LogMgr.log(2, "703 Account name is null.");
                throw new IllegalArgumentException("The specified AccountName is null.");
            }
        }
        if (iSilentStartEventCallback == null) {
            LogMgr.log(2, "705 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    try {
                        this.mFelicaWrapper.checkMfiActivated();
                        this.mFelicaWrapper.checkNotOpened();
                        checkMfiAccess();
                        this.mFelicaWrapper.setMfiOnline(this);
                    } catch (Throwable th2) {
                        th = th2;
                        z2 = true;
                    }
                } catch (FelicaException e) {
                    e = e;
                } catch (Exception e2) {
                    e = e2;
                    str3 = 0;
                } catch (Throwable th3) {
                    th = th3;
                    mfiOnline = null;
                    z = false;
                }
                try {
                    if (this.mStarted > 0) {
                        throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_ALREADY_STARTED, null);
                    }
                    this.mFwsClientFacade.checkNotRunningTask();
                    if (!OpenIdConnectActivity.setMfiStarted(true)) {
                        throw new MfiClientException(2, 39, null);
                    }
                    try {
                        boolean zCanAccessRight = this.mFelicaWrapper.canAccessRight(10);
                        this.mISilentStartEventCallback = iSilentStartEventCallback;
                        this.mFwsClientFacade.start(this.mOnFinishListener, this.mDataManager, this.mFelicaWrapper.getWalletAppId());
                        mfiOnline2 = null;
                        z = false;
                        try {
                            this.mFwsClientFacade.silentStart(str, str2, zCanAccessRight, this.mFelicaWrapper.getWalletAppCallerInfo(), this.mFelicaWrapper.getWalletAppIdentifiableInfo(), i);
                        } catch (FelicaException e3) {
                            e = e3;
                            LogMgr.log(2, "706 : FelicaException:" + e.getMessage());
                            throw e;
                        } catch (Exception e4) {
                            e = e4;
                            str3 = mfiOnline2;
                            LogMgr.log(2, "707 : " + e.getClass().getSimpleName() + ":" + e.getMessage());
                            LogMgr.printStackTrace(7, e);
                            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, str3);
                        } catch (Throwable th4) {
                            th = th4;
                            th = th;
                            z2 = true;
                            mfiOnline = mfiOnline2;
                            if (z2) {
                            }
                            try {
                                checkNotRunningTask();
                                this.mFelicaWrapper.setMfiOnline(mfiOnline);
                                this.mFwsClientFacade.finish();
                                throw th;
                            } catch (Exception e5) {
                                LogMgr.log(2, "708 : MfiClientException:" + e5.getMessage());
                                throw th;
                            }
                        }
                    } catch (FelicaException e6) {
                        e = e6;
                    } catch (Exception e7) {
                        e = e7;
                        mfiOnline2 = null;
                    } catch (Throwable th5) {
                        th = th5;
                        mfiOnline2 = null;
                        z = false;
                    }
                } catch (FelicaException e8) {
                    e = e8;
                } catch (Exception e9) {
                    e = e9;
                    LogMgr.log(2, "707 : " + e.getClass().getSimpleName() + ":" + e.getMessage());
                    LogMgr.printStackTrace(7, e);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, str3);
                } catch (Throwable th6) {
                    th = th6;
                    th = th;
                    z2 = false;
                    if (z2) {
                        OpenIdConnectActivity.setMfiStarted(z);
                    }
                    checkNotRunningTask();
                    this.mFelicaWrapper.setMfiOnline(mfiOnline);
                    this.mFwsClientFacade.finish();
                    throw th;
                }
                LogMgr.log(2, "706 : FelicaException:" + e.getMessage());
                throw e;
            }
        }
        LogMgr.log(4, "999");
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:50:0x0117 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:52:0x0119 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:54:0x011b */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:70:0x015d */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:72:0x015f */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:74:0x0161 */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00c0 A[Catch: all -> 0x0163, Exception -> 0x016e, FelicaException -> 0x01a7, TRY_LEAVE, TryCatch #17 {FelicaException -> 0x01a7, Exception -> 0x016e, all -> 0x0163, blocks: (B:28:0x0078, B:39:0x00b7, B:41:0x00c0), top: B:110:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x014d A[Catch: all -> 0x015d, Exception -> 0x015f, FelicaException -> 0x0161, TryCatch #12 {FelicaException -> 0x0161, Exception -> 0x015f, all -> 0x015d, blocks: (B:66:0x0145, B:67:0x014c, B:68:0x014d, B:69:0x015c), top: B:120:0x00be }] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x01d0 A[Catch: all -> 0x01f8, TRY_ENTER, TRY_LEAVE, TryCatch #13 {, blocks: (B:46:0x010f, B:92:0x01d0, B:93:0x01d3, B:97:0x01f7, B:96:0x01e2), top: B:111:0x0078, outer: #0, inners: #19 }] */
    /* JADX WARN: Type inference failed for: r0v20, types: [com.felicanetworks.mfc.mfi.FelicaWrapper] */
    /* JADX WARN: Type inference failed for: r13v20 */
    /* JADX WARN: Type inference failed for: r13v21 */
    /* JADX WARN: Type inference failed for: r13v22 */
    /* JADX WARN: Type inference failed for: r13v6, types: [com.felicanetworks.mfc.mfi.MfiOnline] */
    /* JADX WARN: Type inference failed for: r13v8 */
    /* JADX WARN: Type inference failed for: r15v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r15v10, types: [int] */
    /* JADX WARN: Type inference failed for: r15v11 */
    /* JADX WARN: Type inference failed for: r15v12 */
    /* JADX WARN: Type inference failed for: r15v13 */
    /* JADX WARN: Type inference failed for: r15v2 */
    /* JADX WARN: Type inference failed for: r15v22 */
    /* JADX WARN: Type inference failed for: r15v23 */
    /* JADX WARN: Type inference failed for: r15v24 */
    /* JADX WARN: Type inference failed for: r15v25 */
    /* JADX WARN: Type inference failed for: r15v26 */
    /* JADX WARN: Type inference failed for: r15v27 */
    /* JADX WARN: Type inference failed for: r15v28 */
    /* JADX WARN: Type inference failed for: r15v3 */
    /* JADX WARN: Type inference failed for: r15v4 */
    /* JADX WARN: Type inference failed for: r15v5 */
    /* JADX WARN: Type inference failed for: r15v6 */
    /* JADX WARN: Type inference failed for: r15v7 */
    /* JADX WARN: Type inference failed for: r15v8, types: [int] */
    /* JADX WARN: Type inference failed for: r15v9, types: [int] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void silentStartForMfiAdmin(String str, String str2, boolean z, boolean z2, int i, int i2, ISilentStartForMfiAdminEventCallback iSilentStartForMfiAdminEventCallback) throws FelicaException, IllegalArgumentException {
        FelicaWrapper felicaWrapper;
        String str3;
        String str4;
        Throwable th;
        boolean z3;
        ?? r15;
        ?? r13;
        ?? r152;
        String str5;
        ?? r153;
        char c;
        String str6;
        String str7 = "707 : MfiClientException:";
        ?? r154 = "706 : FelicaException:";
        LogMgr.log(4, "000");
        if (z) {
            if (str == null) {
                if (str2 != null) {
                    LogMgr.log(2, "701 Account issuer is null.");
                    throw new IllegalArgumentException("The specified AccountIssuer is invalid.");
                }
            } else {
                if (!MfiClientConst.ACCOUNT_ISSUER_LIST.contains(str)) {
                    LogMgr.log(2, "702 Account issuer is invalid. " + str);
                    throw new IllegalArgumentException("The specified AccountIssuer is invalid.");
                }
                if (str2 == null) {
                    LogMgr.log(2, "703 Account name is null.");
                    throw new IllegalArgumentException("The specified AccountName is null.");
                }
            }
            if (i2 != 1 && i2 != 2 && i2 != 3) {
                LogMgr.log(2, "704 layoutType is unexpected value or null.");
                throw new IllegalArgumentException("The specified LayoutType is unexpected value.");
            }
        }
        if (iSilentStartForMfiAdminEventCallback == null) {
            LogMgr.log(2, "705 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        FelicaWrapper felicaWrapper2 = this.mFelicaWrapper;
        synchronized (felicaWrapper2) {
            try {
                try {
                    synchronized (this) {
                        try {
                            try {
                                this.mFelicaWrapper.checkMfiActivated();
                                this.mFelicaWrapper.checkNotOpened();
                                this.mFelicaWrapper.checkAccessRight(14);
                            } catch (Throwable th2) {
                                th = th2;
                                z3 = true;
                                r13 = str7;
                                r15 = r154;
                            }
                        } catch (FelicaException e) {
                            e = e;
                            str4 = "706 : FelicaException:";
                            r154 = 2;
                        } catch (Exception e2) {
                            e = e2;
                            r154 = 2;
                            str7 = null;
                        } catch (Throwable th3) {
                            th = th3;
                            r154 = 2;
                            felicaWrapper = felicaWrapper2;
                            str3 = "707 : MfiClientException:";
                            str7 = null;
                        }
                        if (i2 != 1) {
                            try {
                                this.mFelicaWrapper.checkAccessRight(50);
                                this.mFelicaWrapper.setMfiOnline(this);
                            } catch (FelicaException e3) {
                                e = e3;
                                str4 = "706 : FelicaException:";
                                r153 = 2;
                            } catch (Exception e4) {
                                e = e4;
                                r152 = 2;
                                str5 = null;
                                LogMgr.log(r152, "707 : " + e.getClass().getSimpleName() + ":" + e.getMessage());
                                LogMgr.printStackTrace(7, e);
                                throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, str5);
                            } catch (Throwable th4) {
                                th = th4;
                                r15 = 2;
                                felicaWrapper = felicaWrapper2;
                                str3 = "707 : MfiClientException:";
                                z3 = false;
                                r13 = 0;
                                if (z3) {
                                    OpenIdConnectActivity.setMfiStarted(false);
                                }
                                try {
                                    checkNotRunningTask();
                                    this.mFelicaWrapper.setMfiOnline(r13);
                                    this.mFwsClientFacade.finish();
                                    throw th;
                                } catch (Exception e5) {
                                    LogMgr.log(r15, str3 + e5.getMessage());
                                    throw th;
                                }
                            }
                            try {
                                if (this.mStarted <= 0) {
                                    throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_ALREADY_STARTED, null);
                                }
                                this.mFwsClientFacade.checkNotRunningTask();
                                if (!OpenIdConnectActivity.setMfiStarted(true)) {
                                    throw new MfiClientException(2, 39, null);
                                }
                                try {
                                    boolean zCanAccessRight = this.mFelicaWrapper.canAccessRight(10);
                                    boolean zCanAccessRight2 = this.mFelicaWrapper.canAccessRight(50);
                                    this.mISilentStartForMfiAdminEventCallback = iSilentStartForMfiAdminEventCallback;
                                    this.mFwsClientFacade.start(this.mOnFinishListener, this.mDataManager, this.mFelicaWrapper.getWalletAppId());
                                    felicaWrapper = felicaWrapper2;
                                    str3 = "707 : MfiClientException:";
                                    str4 = "706 : FelicaException:";
                                    c = 2;
                                    str6 = null;
                                    try {
                                        this.mFwsClientFacade.silentStartForMfiAdmin(str, str2, zCanAccessRight, this.mFelicaWrapper.getWalletAppCallerInfo(), this.mFelicaWrapper.getWalletAppIdentifiableInfo(), z, i, i2, zCanAccessRight2, z2);
                                    } catch (FelicaException e6) {
                                        e = e6;
                                        r153 = c;
                                        LogMgr.log(r153, str4 + e.getMessage());
                                        throw e;
                                    } catch (Exception e7) {
                                        e = e7;
                                        str5 = str6;
                                        r152 = c;
                                        LogMgr.log(r152, "707 : " + e.getClass().getSimpleName() + ":" + e.getMessage());
                                        LogMgr.printStackTrace(7, e);
                                        throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, str5);
                                    } catch (Throwable th5) {
                                        th = th5;
                                        th = th;
                                        z3 = true;
                                        r13 = str6;
                                        r15 = c;
                                        if (z3) {
                                        }
                                        checkNotRunningTask();
                                        this.mFelicaWrapper.setMfiOnline(r13);
                                        this.mFwsClientFacade.finish();
                                        throw th;
                                    }
                                } catch (FelicaException e8) {
                                    e = e8;
                                    str4 = "706 : FelicaException:";
                                    c = 2;
                                } catch (Exception e9) {
                                    e = e9;
                                    c = 2;
                                    str6 = null;
                                } catch (Throwable th6) {
                                    th = th6;
                                    c = 2;
                                    felicaWrapper = felicaWrapper2;
                                    str3 = "707 : MfiClientException:";
                                    str6 = null;
                                }
                            } catch (FelicaException e10) {
                                e = e10;
                                r153 = r154;
                            } catch (Exception e11) {
                                e = e11;
                                str5 = str7;
                                r152 = r154;
                                LogMgr.log(r152, "707 : " + e.getClass().getSimpleName() + ":" + e.getMessage());
                                LogMgr.printStackTrace(7, e);
                                throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, str5);
                            } catch (Throwable th7) {
                                th = th7;
                                th = th;
                                z3 = false;
                                r13 = str7;
                                r15 = r154;
                                if (z3) {
                                }
                                checkNotRunningTask();
                                this.mFelicaWrapper.setMfiOnline(r13);
                                this.mFwsClientFacade.finish();
                                throw th;
                            }
                            r153 = r154;
                            LogMgr.log(r153, str4 + e.getMessage());
                            throw e;
                        }
                        this.mFelicaWrapper.setMfiOnline(this);
                        if (this.mStarted <= 0) {
                        }
                        r153 = r154;
                        LogMgr.log(r153, str4 + e.getMessage());
                        throw e;
                    }
                    LogMgr.log(4, "999");
                } catch (Throwable th8) {
                    th = th8;
                    throw th;
                }
            } catch (Throwable th9) {
                th = th9;
                felicaWrapper = felicaWrapper2;
                throw th;
            }
        }
    }

    void logout(boolean autoMfiServerLogout, ILogoutEventCallback callback, boolean isForce) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s isForce ? %s", "000", Boolean.valueOf(isForce));
        if (isForce) {
            finish(isForce);
            return;
        }
        if (callback == null) {
            LogMgr.log(2, "%s callback is null.", "701");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    this.mFelicaWrapper.checkMfiActivated();
                    if (this.mStarted == 0 && this.mFwsClientFacade.isNotRunningSilentStartTask()) {
                        throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_NOT_STARTED, null);
                    }
                    this.mFwsClientFacade.checkNotRunningNonStoppableTask();
                    this.mILogoutEventCallback = callback;
                    this.mFwsClientFacade.logout(autoMfiServerLogout, this.mFelicaWrapper.getWalletAppCallerInfo(), this.mFelicaWrapper.getWalletAppIdentifiableInfo(), !isStartedBy(1));
                    this.mLoggingOut = true;
                } catch (FelicaException e) {
                    LogMgr.log(2, "%s : FelicaException:%s", "702", e.getMessage());
                    throw e;
                } catch (Exception e2) {
                    LogMgr.log(2, "%s : %s:%s", "702", e2.getClass().getSimpleName(), e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            }
        }
        LogMgr.log(4, "%s", "999");
    }

    String getCurrentAccountHash() throws FelicaException {
        String accountHashCache;
        LogMgr.log(4, "%s", "000");
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    this.mFelicaWrapper.checkMfiActivated();
                    checkMfiAccess();
                    accountHashCache = AccountCache.getInstance().getAccountHashCache(this.mFelicaWrapper.getWalletAppIdentifiableInfo());
                    if (accountHashCache == null) {
                        throw new MfiClientException(101, MfiClientException.TYPE_NO_ACCOUNT_INFO, null);
                    }
                } catch (FelicaException e) {
                    LogMgr.log(2, "%s : FelicaException:%s", "700", e.getMessage());
                    throw e;
                } catch (Exception e2) {
                    LogMgr.log(2, "%s : %s:%s", "701", e2.getClass().getSimpleName(), e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            }
        }
        LogMgr.log(4, "%s", "999");
        return accountHashCache;
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0092 A[Catch: all -> 0x0096, TRY_ENTER, TryCatch #4 {, blocks: (B:9:0x0035, B:10:0x0038, B:34:0x0092, B:35:0x0095), top: B:42:0x0010, outer: #5 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void clearMfiAccount() throws FelicaException {
        boolean z;
        LogMgr.log(4, "%s", "000");
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                boolean z2 = true;
                try {
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    this.mFelicaWrapper.checkMfiActivated();
                    this.mFelicaWrapper.checkNotLoggedIn();
                    if (!OpenIdConnectActivity.setMfiStarted(true)) {
                        throw new MfiClientException(2, 39, null);
                    }
                    try {
                        this.mFelicaWrapper.checkAccessRight(9);
                        AccountCache.getInstance().clearLoginData();
                        PreAccountCache.getInstance().clearLoginData();
                        OpenIdConnectActivity.setMfiStarted(false);
                    } catch (FelicaException e) {
                        e = e;
                    } catch (Exception e2) {
                        e = e2;
                        z = true;
                        try {
                            LogMgr.log(2, "%s : %s:%s", "701", e.getClass().getSimpleName(), e.getMessage());
                            LogMgr.printStackTrace(7, e);
                            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                        } catch (Throwable th2) {
                            th = th2;
                            z2 = z;
                            if (z2) {
                                OpenIdConnectActivity.setMfiStarted(false);
                            }
                            throw th;
                        }
                    }
                } catch (FelicaException e3) {
                    e = e3;
                } catch (Exception e4) {
                    e = e4;
                    z = false;
                } catch (Throwable th3) {
                    th = th3;
                    z2 = false;
                    if (z2) {
                    }
                    throw th;
                }
                LogMgr.log(2, "%s : FelicaException:%s", "700", e.getMessage());
                throw e;
            }
        }
        LogSender.send(FelicaAdapter.getInstance(), LogSender.EXTRA_VALUE_EVENT_NAME_ACCOUNT_CLEAR, LogSender.EXTRA_VALUE_LOG_TYPE_DUMP);
        LogMgr.log(4, "%s", "999");
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00fb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    int identifyService() throws FelicaException {
        int i;
        int i2;
        int i3;
        int i4;
        int type;
        int i5;
        int i6;
        int iIdentifyService;
        LogMgr.log(4, "%s", "000");
        FelicaAdapter felicaAdapter = FelicaAdapter.getInstance();
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                this.mFelicaWrapper.checkMfiActivated();
                this.mFelicaWrapper.checkNotOpened();
                FelicaAdapter.getInstance().getFelicaWrapper();
                this.mFelicaWrapper.checkAccessRight(32);
                this.mFwsClientFacade.checkNotRunningTask();
                String str = null;
                try {
                    String mFCVersion = FelicaWrapper.getMFCVersion(felicaAdapter);
                    if (StringUtil.versionCompare(mFCVersion, MfiClientConst.MFC_VERSION_FAVER3) == -1) {
                        LogMgr.log(2, "%s : MFC Version : %s ", "701", mFCVersion);
                        throw new MfiClientException(103, MfiClientException.TYPE_NOT_SUPPORTED_CHIP_ERROR, null);
                    }
                    MfiChipHolder mfiChipHolder = new MfiChipHolder();
                    MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(mfiChipHolder);
                    int i7 = 55;
                    try {
                        mfiFelicaWrapper.open();
                        mfiFelicaWrapper.select(65039);
                        if (!AccessConfig.isValidContainerIssueInfo(mfiFelicaWrapper.getContainerIssueInformation())) {
                            i = 6;
                            i2 = 31;
                            i3 = 8;
                            try {
                                LogMgr.log(2, "%s : Not Initialized ", "701");
                                try {
                                    throw new MfiFelicaException(31, null);
                                } catch (MfiFelicaException e) {
                                    e = e;
                                    i4 = 31;
                                    type = e.getType();
                                    if (type == i) {
                                        if (type != i3) {
                                            if (type == i2) {
                                                i7 = i2;
                                            } else if (type != 55) {
                                                i7 = i4;
                                            }
                                            i5 = i3;
                                        } else {
                                            str = FelicaException.NFC_RW_USED_MESSAGE;
                                            i7 = i3;
                                        }
                                        i5 = 1;
                                    } else {
                                        i7 = i;
                                        i5 = 3;
                                    }
                                    mfiChipHolder.discard();
                                    LogMgr.log(2, "%s : %s:%s", "702", e.getClass().getSimpleName(), e.getMessage());
                                    throw new MfiClientException(i5, i7, str);
                                }
                            } catch (MfiFelicaException e2) {
                                e = e2;
                                i4 = MfiClientException.TYPE_IDENTIFY_SERVICE_FAILED;
                                type = e.getType();
                                if (type == i) {
                                }
                                mfiChipHolder.discard();
                                LogMgr.log(2, "%s : %s:%s", "702", e.getClass().getSimpleName(), e.getMessage());
                                throw new MfiClientException(i5, i7, str);
                            }
                        }
                        int i8 = 8;
                        try {
                            try {
                                iIdentifyService = IndividualSpChecker.identifyService(mfiChipHolder, 3, 72, 74, FlavorConst.INDIVIDUAL_SP_NODE_CODE_SERVICE1, FlavorConst.INDIVIDUAL_SP_NODE_CODE_SERVICE2);
                            } finally {
                                mfiChipHolder.discard();
                            }
                        } catch (FwsException e3) {
                            int type2 = e3.getType();
                            if (type2 == 6) {
                                i8 = 6;
                                i6 = 3;
                            } else if (type2 == 8) {
                                str = FelicaException.NFC_RW_USED_MESSAGE;
                                i6 = 1;
                            } else if (type2 == 31) {
                                i8 = 31;
                                i6 = 8;
                            } else if (type2 != 55) {
                                i6 = 1;
                                i8 = MfiClientException.TYPE_IDENTIFY_SERVICE_FAILED;
                            } else {
                                i6 = 8;
                                i8 = 55;
                            }
                            LogMgr.log(2, "%s : %s:%s", "703", e3.getClass().getSimpleName(), e3.getMessage());
                            throw new MfiClientException(i6, i8, str);
                        }
                    } catch (MfiFelicaException e4) {
                        e = e4;
                        i = 6;
                        i2 = 31;
                        i3 = 8;
                    }
                } catch (FelicaException e5) {
                    LogMgr.log(2, "%s : %s:%s", "700", e5.getClass().getSimpleName(), e5.getMessage());
                    throw new MfiClientException(1, MfiClientException.TYPE_IDENTIFY_SERVICE_FAILED, null);
                }
            }
        }
        LogMgr.log(4, "%s", "999");
        return iIdentifyService;
    }

    int checkChipFormatting() throws FelicaException {
        int initStatus;
        LogMgr.log(4, "000");
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                this.mFelicaWrapper.checkMfiActivated();
                checkStartedBy(2);
                this.mFwsClientFacade.checkNotRunningTask();
                MfiChipHolder mfiChipHolder = new MfiChipHolder();
                MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(mfiChipHolder);
                try {
                    try {
                        mfiFelicaWrapper.open();
                        initStatus = this.mDataManager.getInitStatus(mfiFelicaWrapper);
                        mfiFelicaWrapper.close();
                        LogMgr.log(4, "999");
                    } catch (Exception e) {
                        LogMgr.log(2, "701 : " + e.getClass().getSimpleName() + ":" + e.getMessage());
                        LogMgr.printStackTrace(7, e);
                        throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                    }
                } finally {
                    mfiFelicaWrapper.closeSilently();
                    mfiChipHolder.discard();
                }
            }
        }
        return initStatus;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0149 A[PHI: r1 r8
  0x0149: PHI (r1v13 int) = (r1v12 int), (r1v14 int) binds: [B:38:0x0139, B:42:0x0141] A[DONT_GENERATE, DONT_INLINE]
  0x0149: PHI (r8v3 int) = (r8v0 int), (r8v5 int) binds: [B:38:0x0139, B:42:0x0141] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    SeInfo getSeInfomation() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                LogMgr.log(4, "%s", "001");
                this.mFelicaWrapper.checkMfiActivated();
                checkMfiAccess();
                this.mFelicaWrapper.checkNotOpened();
                if (this.mStarted == 0) {
                    this.mFwsClientFacade.checkNotRunningTask();
                }
                SeInfo seInfo = this.mDataManager.getSeInfo();
                if (seInfo != null) {
                    LogMgr.log(4, "998");
                    return seInfo;
                }
                MfiChipHolder mfiChipHolder = new MfiChipHolder();
                MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(mfiChipHolder);
                int i = 3;
                String str = null;
                int i2 = MfiClientException.TYPE_GET_SE_INFORMATION_FAILED;
                try {
                    try {
                        try {
                            SeInfo seInfoCreateSeInfo = this.mDataManager.createSeInfo(mfiFelicaWrapper);
                            mfiFelicaWrapper.close();
                            mfiFelicaWrapper.closeSilently();
                            mfiChipHolder.discard();
                            LogMgr.log(4, "%s", "999");
                            return seInfoCreateSeInfo;
                        } catch (MfiFelicaException e) {
                            LogMgr.log(2, "700 : " + e.getClass().getSimpleName() + ": type=" + e.getType() + " msg=" + e.getMessage());
                            LogMgr.printStackTrace(7, e);
                            int type = e.getType();
                            int i3 = 6;
                            if (type != 6) {
                                i = 8;
                                if (type != 8) {
                                    i3 = 55;
                                    if (type == 55) {
                                        i2 = i3;
                                    }
                                } else {
                                    str = FelicaException.NFC_RW_USED_MESSAGE;
                                    i2 = 8;
                                }
                                i = 1;
                            }
                            throw new MfiClientException(i, i2, str);
                        } catch (InterruptedException e2) {
                            LogMgr.log(2, "701 : " + e2.getClass().getSimpleName() + ":" + e2.getMessage());
                            LogMgr.printStackTrace(7, e2);
                            throw new MfiClientException(1, MfiClientException.TYPE_GET_SE_INFORMATION_FAILED, null);
                        }
                    } catch (GpException e3) {
                        LogMgr.log(2, "702 : " + e3.getClass().getSimpleName() + ":" + e3.getMessage());
                        LogMgr.printStackTrace(7, e3);
                        if (e3.getType() != 225) {
                            i = 1;
                        } else {
                            i2 = MfiClientException.TYPE_SE_ACCESS_ERROR;
                        }
                        throw new MfiClientException(i, i2, null);
                    } catch (Exception e4) {
                        LogMgr.log(2, "703 : " + e4.getClass().getSimpleName() + ":" + e4.getMessage());
                        LogMgr.printStackTrace(7, e4);
                        throw new MfiClientException(1, MfiClientException.TYPE_GET_SE_INFORMATION_FAILED, null);
                    }
                } catch (Throwable th) {
                    mfiFelicaWrapper.closeSilently();
                    mfiChipHolder.discard();
                    throw th;
                }
            }
        }
    }

    void clearSeInfo() {
        LogMgr.log(4, "000");
        this.mDataManager.clearSeInfo();
        LogMgr.log(4, "999");
    }

    void getCardList(ICardListEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        if (callback == null) {
            LogMgr.log(2, "%s callback is null.", "701");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    this.mFelicaWrapper.checkMfiActivated();
                    checkStartedBy(1);
                    FelicaWrapper felicaWrapper = FelicaAdapter.getInstance().getFelicaWrapper();
                    felicaWrapper.checkAccessRight(6);
                    JSONArray walletAppCertHashList = !felicaWrapper.canAccessRight(11) ? felicaWrapper.getWalletAppCertHashList() : null;
                    String walletAppId = this.mFelicaWrapper.getWalletAppId();
                    this.mFwsClientFacade.checkNotRunningTask();
                    this.mICardListEventCallback = callback;
                    this.mFwsClientFacade.getCardList(walletAppCertHashList, walletAppId);
                } catch (FelicaException e) {
                    LogMgr.log(2, "%s : FelicaException:%s", "701", e.getMessage());
                    throw e;
                } catch (Exception e2) {
                    LogMgr.log(2, "%s : %s:%s", "702", e2.getClass().getSimpleName(), e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            }
        }
        LogMgr.log(4, "%s", "999");
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x00d9 A[Catch: all -> 0x00eb, TRY_ENTER, TryCatch #6 {, blocks: (B:17:0x0075, B:41:0x00d9, B:43:0x00e0, B:44:0x00ea), top: B:54:0x0015, outer: #8 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void getCachedCardList(IDataListEventCallback callback, boolean isSkipIntegrityCheck) throws FelicaException, IllegalArgumentException {
        boolean z;
        LogMgr.log(4, "000");
        if (callback == null) {
            LogMgr.log(2, "700 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                boolean z2 = true;
                try {
                    try {
                        this.mFelicaWrapper.checkMfiActivated();
                        this.mFelicaWrapper.checkNotOpenedNoSync();
                        this.mFelicaWrapper.checkNotLoggedIn();
                        if (!OpenIdConnectActivity.setMfiStarted(true)) {
                            throw new MfiClientException(2, 39, null);
                        }
                        try {
                            FelicaAdapter.getInstance().getFelicaWrapper().checkAccessRight(6);
                            if (this.mStarted == 0) {
                                this.mFelicaWrapper.setMfiOnline(this);
                                this.mFwsClientFacade.start(this.mOnFinishListener, this.mDataManager, this.mFelicaWrapper.getWalletAppId());
                            }
                            String walletAppCallerInfo = this.mFelicaWrapper.getWalletAppCallerInfo();
                            String walletAppIdentifiableInfo = this.mFelicaWrapper.getWalletAppIdentifiableInfo();
                            if (isSkipIntegrityCheck) {
                                LogMgr.log(7, "001");
                                this.mICachedCardListEventCallback = callback;
                                this.mFwsClientFacade.getCachedCardList(walletAppCallerInfo, walletAppIdentifiableInfo);
                            } else {
                                LogMgr.log(7, "002");
                                this.mICachedCardListWithIntegrityCheckEventCallback = callback;
                                this.mFwsClientFacade.getCachedCardListWithIntegrityCheck(walletAppCallerInfo, walletAppIdentifiableInfo);
                            }
                        } catch (FelicaException e) {
                            e = e;
                        } catch (Exception e2) {
                            e = e2;
                            z = true;
                            try {
                                LogMgr.log(2, "702 : " + e.getClass().getSimpleName() + ":" + e.getMessage());
                                LogMgr.printStackTrace(7, e);
                                throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                            } catch (Throwable th) {
                                th = th;
                                z2 = z;
                                if (z2) {
                                }
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (z2) {
                            OpenIdConnectActivity.setMfiStarted(false);
                            if (this.mStarted == 0) {
                                this.mFelicaWrapper.setMfiOnline(null);
                                this.mFwsClientFacade.finish();
                            }
                        }
                        throw th;
                    }
                } catch (FelicaException e3) {
                    e = e3;
                } catch (Exception e4) {
                    e = e4;
                    z = false;
                } catch (Throwable th3) {
                    th = th3;
                    z2 = false;
                    if (z2) {
                    }
                    throw th;
                }
                LogMgr.log(2, "701 : FelicaException:" + e.getMessage());
                throw e;
            }
        }
        LogMgr.log(4, "999");
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00d1 A[Catch: all -> 0x00e3, TRY_ENTER, TryCatch #1 {, blocks: (B:16:0x006c, B:40:0x00d1, B:42:0x00d8, B:43:0x00e2), top: B:57:0x001f, outer: #4 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void enableCachedCard(String cachedCardInfoJson, ICachedCardEnableEventCallback callback) throws FelicaException, IllegalArgumentException {
        boolean z;
        LogMgr.log(4, "000");
        if (cachedCardInfoJson == null) {
            LogMgr.log(2, "700 cachedCardInfoJson is null.");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
        try {
            CachedCardInfo cachedCardInfo = new CachedCardInfoJson(cachedCardInfoJson).getCachedCardInfo();
            if (callback == null) {
                LogMgr.log(2, "702 callback is null.");
                throw new IllegalArgumentException("The specified Callback is null.");
            }
            synchronized (this.mFelicaWrapper) {
                synchronized (this) {
                    boolean z2 = true;
                    try {
                    } catch (Throwable th) {
                        th = th;
                    }
                    try {
                        this.mFelicaWrapper.checkMfiActivated();
                        this.mFelicaWrapper.checkNotOpenedNoSync();
                        this.mFelicaWrapper.checkNotLoggedIn();
                        if (!OpenIdConnectActivity.setMfiStarted(true)) {
                            throw new MfiClientException(2, 39, null);
                        }
                        try {
                            FelicaAdapter.getInstance().getFelicaWrapper().checkAccessRight(8);
                            if (this.mStarted == 0) {
                                this.mFelicaWrapper.setMfiOnline(this);
                                this.mFwsClientFacade.start(this.mOnFinishListener, this.mDataManager, this.mFelicaWrapper.getWalletAppId());
                            }
                            String walletAppCallerInfo = this.mFelicaWrapper.getWalletAppCallerInfo();
                            String walletAppIdentifiableInfo = this.mFelicaWrapper.getWalletAppIdentifiableInfo();
                            this.mICachedCardEnableEventCallback = callback;
                            this.mFwsClientFacade.enableCachedCard(cachedCardInfo, walletAppCallerInfo, walletAppIdentifiableInfo);
                        } catch (FelicaException e) {
                            e = e;
                        } catch (Exception e2) {
                            e = e2;
                            z = true;
                            try {
                                LogMgr.log(2, "704 : " + e.getClass().getSimpleName() + ":" + e.getMessage());
                                LogMgr.printStackTrace(7, e);
                                throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                            } catch (Throwable th2) {
                                th = th2;
                                z2 = z;
                                if (z2) {
                                    OpenIdConnectActivity.setMfiStarted(false);
                                    if (this.mStarted == 0) {
                                        this.mFelicaWrapper.setMfiOnline(null);
                                        this.mFwsClientFacade.finish();
                                    }
                                }
                                throw th;
                            }
                        }
                    } catch (FelicaException e3) {
                        e = e3;
                    } catch (Exception e4) {
                        e = e4;
                        z = false;
                    } catch (Throwable th3) {
                        th = th3;
                        z2 = false;
                        if (z2) {
                        }
                        throw th;
                    }
                    LogMgr.log(2, "703 : FelicaException:" + e.getMessage());
                    throw e;
                }
            }
            LogMgr.log(4, "999");
        } catch (JSONException e5) {
            LogMgr.log(2, " 701 cannot create CachedCardInfo.");
            LogMgr.printStackTrace(2, e5);
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
    }

    void getCardListV2(IPipeEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        if (callback == null) {
            LogMgr.log(2, "%s callback is null.", "701");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    this.mFelicaWrapper.checkMfiActivated();
                    checkStartedBy(1);
                    FelicaWrapper felicaWrapper = FelicaAdapter.getInstance().getFelicaWrapper();
                    felicaWrapper.checkAccessRight(6);
                    JSONArray walletAppCertHashList = !felicaWrapper.canAccessRight(11) ? felicaWrapper.getWalletAppCertHashList() : null;
                    String walletAppId = this.mFelicaWrapper.getWalletAppId();
                    this.mFwsClientFacade.checkNotRunningTask();
                    this.mIPipeEventCallback = callback;
                    this.mFwsClientFacade.getCardListWithPipe(walletAppCertHashList, walletAppId, callback);
                } catch (FelicaException e) {
                    LogMgr.log(2, "%s : FelicaException:%s", "701", e.getMessage());
                    throw e;
                } catch (Exception e2) {
                    LogMgr.log(2, "%s : %s:%s", "702", e2.getClass().getSimpleName(), e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            }
        }
        LogMgr.log(4, "%s", "999");
    }

    void getCardAdditionalInfoList(ICardAdditionalInfoListEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        if (callback == null) {
            LogMgr.log(2, "%s callback is null.", "701");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    this.mFelicaWrapper.checkMfiActivated();
                    checkStartedBy(1);
                    FelicaWrapper felicaWrapper = FelicaAdapter.getInstance().getFelicaWrapper();
                    felicaWrapper.checkAccessRight(6);
                    felicaWrapper.checkAccessRight(12);
                    JSONArray walletAppCertHashList = !felicaWrapper.canAccessRight(11) ? felicaWrapper.getWalletAppCertHashList() : null;
                    this.mFwsClientFacade.checkNotRunningTask();
                    this.mICardAdditionalInfoListEventCallback = callback;
                    this.mFwsClientFacade.getCardAdditionalInfoList(walletAppCertHashList);
                } catch (FelicaException e) {
                    LogMgr.log(2, "%s : FelicaException:%s", "701", e.getMessage());
                    throw e;
                } catch (Exception e2) {
                    LogMgr.log(2, "%s : %s:%s", "702", e2.getClass().getSimpleName(), e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            }
        }
        LogMgr.log(4, "%s", "999");
    }

    void getCardAdditionalInfoListWithCidList(String[] cidList, IPipeEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (cidList == null) {
            LogMgr.log(2, "700 cidList is null");
            throw new IllegalArgumentException("The specified cidList is null or invalid.");
        }
        if (cidList.length == 0) {
            LogMgr.log(2, "701 cidList length = 0");
            throw new IllegalArgumentException("The specified cidList is null or invalid.");
        }
        for (int i = 0; i < cidList.length; i++) {
            String str = cidList[i];
            if (str == null) {
                LogMgr.log(2, "702 cid is null");
                throw new IllegalArgumentException("The specified cidList is null or invalid.");
            }
            if (!str.matches("[0-9a-zA-Z]*")) {
                LogMgr.log(2, "703 cid involves invalid character.");
                throw new IllegalArgumentException("The specified cidList is null or invalid.");
            }
            if (cidList[i].length() != 63) {
                LogMgr.log(2, "704 cid length is invalid.");
                throw new IllegalArgumentException("The specified cidList is null or invalid.");
            }
        }
        if (callback == null) {
            LogMgr.log(2, "705 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    try {
                        this.mFelicaWrapper.checkMfiActivated();
                        checkStartedBy(1);
                        FelicaWrapper felicaWrapper = FelicaAdapter.getInstance().getFelicaWrapper();
                        felicaWrapper.checkAccessRight(6);
                        felicaWrapper.checkAccessRight(12);
                        JSONArray walletAppCertHashList = !felicaWrapper.canAccessRight(11) ? felicaWrapper.getWalletAppCertHashList() : null;
                        this.mFwsClientFacade.checkNotRunningTask();
                        this.mIPipeEventCallback = callback;
                        this.mFwsClientFacade.getCardAdditionalInfoListWithPipe(cidList, walletAppCertHashList, callback);
                    } catch (FelicaException e) {
                        LogMgr.log(2, "706 : FelicaException:" + e.getMessage());
                        throw e;
                    }
                } catch (Exception e2) {
                    LogMgr.log(2, "707 : " + e2.getClass().getSimpleName() + ":" + e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            }
        }
        LogMgr.log(4, "999");
    }

    void getCardInfoListWithSpStatus(String serviceId, IPipeEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (serviceId == null) {
            LogMgr.log(2, "700 serviceId is null.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (!serviceId.matches("[0-9a-zA-Z]*")) {
            LogMgr.log(2, "701 serviceId involves invalid character.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (serviceId.length() != 8) {
            LogMgr.log(2, "702 serviceId length is invalid.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (callback == null) {
            LogMgr.log(2, "703 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    try {
                        this.mFelicaWrapper.checkMfiActivated();
                        checkStartedBy(1);
                        this.mFelicaWrapper.checkAccessRight(6);
                        this.mFelicaWrapper.checkAccessRight(48);
                        JSONArray walletAppCertHashList = !this.mFelicaWrapper.canAccessRight(11) ? this.mFelicaWrapper.getWalletAppCertHashList() : null;
                        String walletAppId = this.mFelicaWrapper.getWalletAppId();
                        this.mFwsClientFacade.checkNotRunningTask();
                        this.mIPipeEventCallback = callback;
                        this.mFwsClientFacade.getCardInfoListWithSpStatus(serviceId, walletAppCertHashList, walletAppId, callback);
                    } catch (Exception e) {
                        LogMgr.log(2, "706 : " + e.getClass().getSimpleName() + ":" + e.getMessage());
                        LogMgr.printStackTrace(7, e);
                        throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                    }
                } catch (FelicaException e2) {
                    LogMgr.log(2, "705 : FelicaException:" + e2.getMessage());
                    throw e2;
                }
            }
        }
        LogMgr.log(4, "999");
    }

    void getCardListV3(IDataListEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (callback == null) {
            LogMgr.log(2, "700 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    this.mFelicaWrapper.checkMfiActivated();
                    checkStartedBy(1);
                    FelicaWrapper felicaWrapper = FelicaAdapter.getInstance().getFelicaWrapper();
                    felicaWrapper.checkAccessRight(6);
                    JSONArray walletAppCertHashList = !felicaWrapper.canAccessRight(11) ? felicaWrapper.getWalletAppCertHashList() : null;
                    String walletAppId = this.mFelicaWrapper.getWalletAppId();
                    this.mFwsClientFacade.checkNotRunningTask();
                    this.mIDataListEventCallback = callback;
                    this.mFwsClientFacade.getCardListV3(walletAppCertHashList, walletAppId);
                } catch (FelicaException e) {
                    LogMgr.log(2, "701 : FelicaException:" + e.getMessage());
                    throw e;
                } catch (Exception e2) {
                    LogMgr.log(2, "702 : " + e2.getClass().getSimpleName() + ":" + e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            }
        }
        LogMgr.log(4, "999");
    }

    void getCardAdditionalInfoListV3(String[] cidList, IDataListEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (cidList == null) {
            LogMgr.log(2, "700 cidList is null");
            throw new IllegalArgumentException("The specified cidList is null or invalid.");
        }
        if (cidList.length == 0) {
            LogMgr.log(2, "701 cidList length = 0");
            throw new IllegalArgumentException("The specified cidList is null or invalid.");
        }
        for (int i = 0; i < cidList.length; i++) {
            String str = cidList[i];
            if (str == null) {
                LogMgr.log(2, "702 cid is null");
                throw new IllegalArgumentException("The specified cidList is null or invalid.");
            }
            if (!str.matches("[0-9a-zA-Z]*")) {
                LogMgr.log(2, "703 cid involves invalid character.");
                throw new IllegalArgumentException("The specified cidList is null or invalid.");
            }
            if (cidList[i].length() != 63) {
                LogMgr.log(2, "704 cid length is invalid.");
                throw new IllegalArgumentException("The specified cidList is null or invalid.");
            }
        }
        if (callback == null) {
            LogMgr.log(2, "705 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    try {
                        this.mFelicaWrapper.checkMfiActivated();
                        checkStartedBy(1);
                        FelicaWrapper felicaWrapper = FelicaAdapter.getInstance().getFelicaWrapper();
                        felicaWrapper.checkAccessRight(6);
                        felicaWrapper.checkAccessRight(12);
                        JSONArray walletAppCertHashList = !felicaWrapper.canAccessRight(11) ? felicaWrapper.getWalletAppCertHashList() : null;
                        this.mFwsClientFacade.checkNotRunningTask();
                        this.mIDataListEventCallback = callback;
                        this.mFwsClientFacade.getCardAdditionalInfoListV3(cidList, walletAppCertHashList);
                    } catch (FelicaException e) {
                        LogMgr.log(2, "706 : FelicaException:" + e.getMessage());
                        throw e;
                    }
                } catch (Exception e2) {
                    LogMgr.log(2, "707 : " + e2.getClass().getSimpleName() + ":" + e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            }
        }
        LogMgr.log(4, "999");
    }

    void getCardInfoListWithSpStatusV3(String serviceId, IDataListEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (serviceId == null) {
            LogMgr.log(2, "700 serviceId is null.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (!serviceId.matches("[0-9a-zA-Z]*")) {
            LogMgr.log(2, "701 serviceId involves invalid character.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (serviceId.length() != 8) {
            LogMgr.log(2, "702 serviceId length is invalid.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (callback == null) {
            LogMgr.log(2, "703 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    try {
                        this.mFelicaWrapper.checkMfiActivated();
                        checkStartedBy(1);
                        this.mFelicaWrapper.checkAccessRight(6);
                        this.mFelicaWrapper.checkAccessRight(48);
                        JSONArray walletAppCertHashList = !this.mFelicaWrapper.canAccessRight(11) ? this.mFelicaWrapper.getWalletAppCertHashList() : null;
                        String walletAppId = this.mFelicaWrapper.getWalletAppId();
                        this.mFwsClientFacade.checkNotRunningTask();
                        this.mIDataListEventCallback = callback;
                        this.mFwsClientFacade.getCardInfoListWithSpStatusV3(serviceId, walletAppCertHashList, walletAppId);
                    } catch (Exception e) {
                        LogMgr.log(2, "706 : " + e.getClass().getSimpleName() + ":" + e.getMessage());
                        LogMgr.printStackTrace(7, e);
                        throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                    }
                } catch (FelicaException e2) {
                    LogMgr.log(2, "705 : FelicaException:" + e2.getMessage());
                    throw e2;
                }
            }
        }
        LogMgr.log(4, "999");
    }

    void issueCard(String linkageData, ICardIssueEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
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
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    try {
                        this.mFelicaWrapper.checkMfiActivated();
                        checkStartedBy(1);
                        FelicaAdapter.getInstance().getFelicaWrapper().checkAccessRight(7);
                        this.mFwsClientFacade.checkNotRunningTask();
                        this.mICardIssueEventCallback = callback;
                        this.mICardIssueV2EventCallback = null;
                        try {
                            LinkageDataJson linkageDataJson = new LinkageDataJson(new JwsObject(linkageData).getPayload());
                            String serviceType = linkageDataJson.getServiceType();
                            boolean zEquals = FwsConst.ActionType.MIGRATE_CARD.equals(linkageDataJson.getActionType());
                            String mFCVersion = FelicaWrapper.getMFCVersion(FelicaAdapter.getInstance());
                            if (zEquals) {
                                LogMgr.log(1, "800 : Otp not set on migrate card .");
                                throw new MfiClientException(100, MfiClientException.TYPE_NOT_SUPPORTED, null);
                            }
                            ServiceTypeInfoUtil.issueCardSimpleServiceTypeCheck(serviceType, mFCVersion);
                            this.mFwsClientFacade.issueCard(linkageData, null);
                        } catch (FelicaException e) {
                            LogMgr.log(2, "703 : FelicaException:" + e.getMessage());
                            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                        } catch (JSONException e2) {
                            LogMgr.log(2, "704 : linkageData is illegal.");
                            LogMgr.printStackTrace(2, e2);
                            throw new MfiClientException(100, MfiClientException.TYPE_ILLEGAL_LINKAGE_DATA, null);
                        }
                    } catch (Exception e3) {
                        LogMgr.log(2, "%s : %s:%s", "706", e3.getClass().getSimpleName(), e3.getMessage());
                        LogMgr.printStackTrace(7, e3);
                        throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                    }
                } catch (FelicaException e4) {
                    LogMgr.log(2, "%s : FelicaException:%s", "705", e4.getMessage());
                    throw e4;
                }
            }
        }
        LogMgr.log(4, "%s", "999");
    }

    void issueCardV2(String linkageData, ICardIssueV2EventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
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
        issueCardV2Inner(linkageData, null, callback);
        LogMgr.log(4, "999");
    }

    void issueCardWithOtp(String linkageData, String otp, ICardIssueV2EventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (linkageData == null) {
            LogMgr.log(2, "700 linkageData is null.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (linkageData.isEmpty()) {
            LogMgr.log(2, "701 linkageData is empty.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (otp == null) {
            LogMgr.log(2, "702 otp is null.");
            throw new IllegalArgumentException("The specified Otp is null or invalid.");
        }
        if (otp.isEmpty()) {
            LogMgr.log(2, "703 otp is empty.");
            throw new IllegalArgumentException("The specified Otp is null or invalid.");
        }
        if (callback == null) {
            LogMgr.log(2, "704 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        issueCardV2Inner(linkageData, otp, callback);
        LogMgr.log(4, "999");
    }

    private void issueCardV2Inner(String linkageData, String otp, ICardIssueV2EventCallback callback) throws FelicaException {
        LogMgr.log(4, "000");
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    try {
                        this.mFelicaWrapper.checkMfiActivated();
                        checkStartedBy(1);
                        FelicaAdapter.getInstance().getFelicaWrapper().checkAccessRight(7);
                        this.mFwsClientFacade.checkNotRunningTask();
                        this.mICardIssueV2EventCallback = callback;
                        this.mICardIssueEventCallback = null;
                        try {
                            LinkageDataJson linkageDataJson = new LinkageDataJson(new JwsObject(linkageData).getPayload());
                            String serviceType = linkageDataJson.getServiceType();
                            boolean zEquals = FwsConst.ActionType.MIGRATE_CARD.equals(linkageDataJson.getActionType());
                            String mFCVersion = FelicaWrapper.getMFCVersion(FelicaAdapter.getInstance());
                            if (otp != null) {
                                if (zEquals) {
                                    ServiceTypeInfoUtil.issueCardsMigratedServiceTypeCheck(serviceType, mFCVersion);
                                } else {
                                    ServiceTypeInfoUtil.issueCardsFullServiceTypeCheck(serviceType, mFCVersion);
                                }
                            } else {
                                if (zEquals) {
                                    LogMgr.log(1, "800 : Otp not set on migrate card .");
                                    throw new MfiClientException(100, MfiClientException.TYPE_NOT_SUPPORTED, null);
                                }
                                ServiceTypeInfoUtil.issueCardsServiceTypeCheck(serviceType, mFCVersion);
                            }
                            this.mFwsClientFacade.issueCard(linkageData, otp);
                        } catch (FelicaException e) {
                            LogMgr.log(2, "705 : FelicaException:" + e.getMessage());
                            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                        } catch (JSONException e2) {
                            LogMgr.log(2, "706 : linkageData is illegal.");
                            LogMgr.printStackTrace(2, e2);
                            throw new MfiClientException(100, MfiClientException.TYPE_ILLEGAL_LINKAGE_DATA, null);
                        }
                    } catch (Exception e3) {
                        LogMgr.log(2, "708 : " + e3.getClass().getSimpleName() + ":" + e3.getMessage());
                        LogMgr.printStackTrace(7, e3);
                        throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                    }
                } catch (FelicaException e4) {
                    LogMgr.log(2, "707 : FelicaException:" + e4.getMessage());
                    throw e4;
                }
            }
        }
        LogMgr.log(4, "999");
    }

    void enable(CardInfo cardInfo, ICardEnableEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        if (cardInfo == null) {
            LogMgr.log(2, "%s cardInfo is null.", "700");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
        if (callback == null) {
            LogMgr.log(2, "%s callback is null.", "701");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    try {
                        this.mFelicaWrapper.checkMfiActivated();
                        checkStartedBy(1);
                        FelicaAdapter.getInstance().getFelicaWrapper().checkAccessRight(8);
                        this.mFwsClientFacade.checkNotRunningTask();
                        this.mICardEnableEventCallback = callback;
                        this.mICardEnableV2EventCallback = null;
                        this.mFwsClientFacade.enableCard(cardInfo);
                    } catch (FelicaException e) {
                        LogMgr.log(2, "%s : FelicaException:%s", "701", e.getMessage());
                        throw e;
                    }
                } catch (Exception e2) {
                    LogMgr.log(2, "%s : %s:%s", "702", e2.getClass().getSimpleName(), e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            }
        }
        LogMgr.log(4, "%s", "999");
    }

    void enableV2(String cardInfoJson, ICardEnableV2EventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (cardInfoJson == null) {
            LogMgr.log(2, "700 cardInfoJson is null.");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
        try {
            CardInfo cardInfo = new CardInfoJson(cardInfoJson).getCardInfo();
            if (callback == null) {
                LogMgr.log(2, "702 callback is null.");
                throw new IllegalArgumentException("The specified Callback is null.");
            }
            synchronized (this.mFelicaWrapper) {
                synchronized (this) {
                    try {
                        this.mFelicaWrapper.checkMfiActivated();
                        checkStartedBy(1);
                        FelicaAdapter.getInstance().getFelicaWrapper().checkAccessRight(8);
                        this.mFwsClientFacade.checkNotRunningTask();
                        this.mICardEnableV2EventCallback = callback;
                        this.mICardEnableEventCallback = null;
                        this.mFwsClientFacade.enableCard(cardInfo);
                    } catch (FelicaException e) {
                        LogMgr.log(2, "703 : FelicaException:" + e.getMessage());
                        throw e;
                    } catch (Exception e2) {
                        LogMgr.log(2, "704 : " + e2.getClass().getSimpleName() + ":" + e2.getMessage());
                        LogMgr.printStackTrace(7, e2);
                        throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                    }
                }
            }
            LogMgr.log(4, "999");
        } catch (JSONException e3) {
            LogMgr.log(2, " 701 cannot create CardInfo.");
            LogMgr.printStackTrace(2, e3);
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
    }

    FelicaResultInfo disable(CardInfo cardInfo, ICardDisableEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        if (cardInfo == null) {
            LogMgr.log(2, "%s cardInfo is null.", "700");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
        if (callback == null) {
            LogMgr.log(2, "%s callback is null.", "701");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    this.mFelicaWrapper.checkMfiActivated();
                    checkStartedBy(1);
                    FelicaAdapter.getInstance().getFelicaWrapper().checkAccessRight(8);
                    this.mFwsClientFacade.checkNotRunningTask();
                    this.mICardDisableEventCallback = callback;
                    this.mICardDisableV2EventCallback = null;
                    this.mFwsClientFacade.disableCard(cardInfo.mCid);
                } catch (FelicaException e) {
                    LogMgr.log(2, "%s : FelicaException:%s", "701", e.getMessage());
                    throw e;
                } catch (Exception e2) {
                    LogMgr.log(2, "%s : %s:%s", "702", e2.getClass().getSimpleName(), e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            }
        }
        LogMgr.log(4, "%s", "999");
        return new FelicaResultInfo();
    }

    FelicaResultInfo disableV2(String cardInfoJson, ICardDisableV2EventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (cardInfoJson == null) {
            LogMgr.log(2, "700 cardInfoJson is null.");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
        try {
            CardInfo cardInfo = new CardInfoJson(cardInfoJson).getCardInfo();
            if (callback == null) {
                LogMgr.log(2, "702 callback is null.");
                throw new IllegalArgumentException("The specified Callback is null.");
            }
            synchronized (this.mFelicaWrapper) {
                synchronized (this) {
                    try {
                        try {
                            this.mFelicaWrapper.checkMfiActivated();
                            checkStartedBy(1);
                            FelicaAdapter.getInstance().getFelicaWrapper().checkAccessRight(8);
                            this.mFwsClientFacade.checkNotRunningTask();
                            this.mICardDisableV2EventCallback = callback;
                            this.mICardDisableEventCallback = null;
                            this.mFwsClientFacade.disableCard(cardInfo.mCid);
                        } catch (FelicaException e) {
                            LogMgr.log(2, "703 : FelicaException:" + e.getMessage());
                            throw e;
                        }
                    } catch (Exception e2) {
                        LogMgr.log(2, "704 : " + e2.getClass().getSimpleName() + ":" + e2.getMessage());
                        LogMgr.printStackTrace(7, e2);
                        throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                    }
                }
            }
            LogMgr.log(4, "999");
            return new FelicaResultInfo();
        } catch (JSONException e3) {
            LogMgr.log(2, " 701 cannot create CardInfo.");
            LogMgr.printStackTrace(2, e3);
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
    }

    void saveDelete(String cardInfoJson, String linkageData, ICardReissuableDeleteEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (cardInfoJson == null) {
            LogMgr.log(2, "700 cardInfoJson is null.");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
        try {
            CardInfo cardInfo = new CardInfoJson(cardInfoJson).getCardInfo();
            if (linkageData == null) {
                LogMgr.log(2, "702 linkageData is null.");
                throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
            }
            if (linkageData.isEmpty()) {
                LogMgr.log(2, "703 linkageData is empty.");
                throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
            }
            if (callback == null) {
                LogMgr.log(2, "704 callback is null.");
                throw new IllegalArgumentException("The specified Callback is null.");
            }
            try {
                if (!cardInfo.mCid.equals(new LinkageDataJson(new JwsObject(linkageData).getPayload()).getCid())) {
                    LogMgr.log(2, "704 cid differs in cardInfo and linkageData.");
                    throw new MfiClientException(102, MfiClientException.TYPE_ILLEGAL_LINKAGE_DATA, null);
                }
                synchronized (this.mFelicaWrapper) {
                    synchronized (this) {
                        try {
                            this.mFelicaWrapper.checkMfiActivated();
                            checkStartedBy(1);
                            FelicaAdapter.getInstance().getFelicaWrapper().checkAccessRight(7);
                            this.mFwsClientFacade.checkNotRunningTask();
                            this.mICardReissuableDeleteEventCallback = callback;
                            this.mFwsClientFacade.saveDeleteCard(cardInfo.mCid, linkageData);
                        } catch (FelicaException e) {
                            LogMgr.log(2, "705 : FelicaException:" + e.getMessage());
                            throw e;
                        } catch (Exception e2) {
                            LogMgr.log(2, "706 : " + e2.getClass().getSimpleName() + ":" + e2.getMessage());
                            LogMgr.printStackTrace(7, e2);
                            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                        }
                    }
                }
                LogMgr.log(4, "999");
            } catch (JSONException e3) {
                LogMgr.log(2, "705 : FelicaException:" + e3.getMessage());
                throw new MfiClientException(102, MfiClientException.TYPE_ILLEGAL_LINKAGE_DATA, null);
            }
        } catch (JSONException e4) {
            LogMgr.log(2, " 701 cannot create CardInfo.");
            LogMgr.printStackTrace(2, e4);
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
    }

    void delete(CardInfo cardInfo, String linkageData, ICardDeleteEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        if (cardInfo == null) {
            LogMgr.log(2, "%s cardInfo is null.", "700");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
        if (linkageData == null) {
            LogMgr.log(2, "%s linkageData is null.", "701");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (linkageData.isEmpty()) {
            LogMgr.log(2, "%s linkageData is empty.", "702");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (callback == null) {
            LogMgr.log(2, "%s callback is null.", "703");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        try {
            if (!cardInfo.mCid.equals(new LinkageDataJson(new JwsObject(linkageData).getPayload()).getCid())) {
                LogMgr.log(2, "704 cid differs in cardInfo and linkageData.");
                throw new MfiClientException(102, MfiClientException.TYPE_ILLEGAL_LINKAGE_DATA, null);
            }
            synchronized (this.mFelicaWrapper) {
                synchronized (this) {
                    try {
                        this.mFelicaWrapper.checkMfiActivated();
                        checkStartedBy(1);
                        FelicaAdapter.getInstance().getFelicaWrapper().checkAccessRight(7);
                        this.mFwsClientFacade.checkNotRunningTask();
                        this.mICardDeleteEventCallback = callback;
                        this.mICardDeleteV2EventCallback = null;
                        this.mFwsClientFacade.deleteCard(cardInfo.mCid, linkageData);
                    } catch (FelicaException e) {
                        LogMgr.log(2, "%s : FelicaException:%s", "706", e.getMessage());
                        throw e;
                    } catch (Exception e2) {
                        LogMgr.log(2, "%s : %s:%s", "707", e2.getClass().getSimpleName(), e2.getMessage());
                        LogMgr.printStackTrace(7, e2);
                        throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                    }
                }
            }
            LogMgr.log(4, "%s", "999");
        } catch (JSONException e3) {
            LogMgr.log(2, "705 : FelicaException:" + e3.getMessage());
            throw new MfiClientException(102, MfiClientException.TYPE_ILLEGAL_LINKAGE_DATA, null);
        }
    }

    void deleteV2(String cardInfoJson, String linkageData, ICardDeleteV2EventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (cardInfoJson == null) {
            LogMgr.log(2, "700 cardInfoJson is null.");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
        try {
            CardInfo cardInfo = new CardInfoJson(cardInfoJson).getCardInfo();
            if (linkageData == null) {
                LogMgr.log(2, "702 linkageData is null.");
                throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
            }
            if (linkageData.isEmpty()) {
                LogMgr.log(2, "703 linkageData is empty.");
                throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
            }
            if (callback == null) {
                LogMgr.log(2, "704 callback is null.");
                throw new IllegalArgumentException("The specified Callback is null.");
            }
            try {
                if (!cardInfo.mCid.equals(new LinkageDataJson(new JwsObject(linkageData).getPayload()).getCid())) {
                    LogMgr.log(2, "704 cid differs in cardInfo and linkageData.");
                    throw new MfiClientException(102, MfiClientException.TYPE_ILLEGAL_LINKAGE_DATA, null);
                }
                synchronized (this.mFelicaWrapper) {
                    synchronized (this) {
                        try {
                            try {
                                this.mFelicaWrapper.checkMfiActivated();
                                checkStartedBy(1);
                                FelicaAdapter.getInstance().getFelicaWrapper().checkAccessRight(7);
                                this.mFwsClientFacade.checkNotRunningTask();
                                this.mICardDeleteV2EventCallback = callback;
                                this.mICardDeleteEventCallback = null;
                                this.mFwsClientFacade.deleteCard(cardInfo.mCid, linkageData);
                            } catch (FelicaException e) {
                                LogMgr.log(2, "705 : FelicaException:" + e.getMessage());
                                throw e;
                            }
                        } catch (Exception e2) {
                            LogMgr.log(2, "706 : " + e2.getClass().getSimpleName() + ":" + e2.getMessage());
                            LogMgr.printStackTrace(7, e2);
                            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                        }
                    }
                }
                LogMgr.log(4, "999");
            } catch (JSONException e3) {
                LogMgr.log(2, "705 : FelicaException:" + e3.getMessage());
                throw new MfiClientException(102, MfiClientException.TYPE_ILLEGAL_LINKAGE_DATA, null);
            }
        } catch (JSONException e4) {
            LogMgr.log(2, " 701 cannot create CardInfo.");
            LogMgr.printStackTrace(2, e4);
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
    }

    void access(String cardInfoJson, String linkageData, ICardAccessEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        if (cardInfoJson == null) {
            LogMgr.log(2, "%s cardInfoJson is null.", "700");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
        try {
            CardInfo cardInfo = new CardInfoJson(cardInfoJson).getCardInfo();
            if (linkageData == null) {
                LogMgr.log(2, "%s linkageData is null.", "702");
                throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
            }
            if (linkageData.isEmpty()) {
                LogMgr.log(2, "%s linkageData is empty.", "703");
                throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
            }
            if (callback == null) {
                LogMgr.log(2, "%s callback is null.", "704");
                throw new IllegalArgumentException("The specified Callback is null.");
            }
            try {
                if (!cardInfo.mCid.equals(new LinkageDataJson(new JwsObject(linkageData).getPayload()).getCid())) {
                    LogMgr.log(2, "704 cid differs in cardInfo and linkageData.");
                    throw new MfiClientException(102, MfiClientException.TYPE_ILLEGAL_LINKAGE_DATA, null);
                }
                synchronized (this.mFelicaWrapper) {
                    synchronized (this) {
                        try {
                            this.mFelicaWrapper.checkMfiActivated();
                            checkStartedBy(1);
                            this.mFelicaWrapper.checkAccessRight(16);
                            this.mFwsClientFacade.checkNotRunningTask();
                            this.mICardAccessEventCallback = callback;
                            this.mFwsClientFacade.accessCard(cardInfo.mCid, linkageData);
                        } catch (FelicaException e) {
                            LogMgr.log(2, "%s : FelicaException:%s", "705", e.getMessage());
                            throw e;
                        } catch (Exception e2) {
                            LogMgr.log(2, "%s : %s:%s", "706", e2.getClass().getSimpleName(), e2.getMessage());
                            LogMgr.printStackTrace(7, e2);
                            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                        }
                    }
                }
                LogMgr.log(4, "%s", "999");
            } catch (JSONException e3) {
                LogMgr.log(2, "705 : FelicaException:" + e3.getMessage());
                throw new MfiClientException(102, MfiClientException.TYPE_ILLEGAL_LINKAGE_DATA, null);
            }
        } catch (JSONException e4) {
            LogMgr.log(2, " 701 cannot create CardInfo.");
            LogMgr.printStackTrace(2, e4);
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
    }

    void cancelCardOperation() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    this.mFelicaWrapper.checkMfiActivated();
                    checkStarted();
                    if (this.mLoggingOut) {
                        return;
                    }
                    this.mFwsClientFacade.cancelCardOperation();
                    LogMgr.log(4, "%s", "999");
                } catch (FelicaException e) {
                    LogMgr.log(2, "%s : FelicaException:%s", "701", e.getMessage());
                    throw e;
                } catch (Exception e2) {
                    LogMgr.log(2, "%s : %s:%s", "702", e2.getClass().getSimpleName(), e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            }
        }
    }

    void initialize(String linkageData, IInitializedEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (linkageData == null) {
            LogMgr.log(2, "701 : linkageData is null.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (linkageData.isEmpty()) {
            LogMgr.log(2, "702 : linkageData is empty.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (callback == null) {
            LogMgr.log(2, "703 : callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    try {
                        this.mFelicaWrapper.checkMfiActivated();
                        checkStartedBy(2);
                        FelicaAdapter.getInstance().getFelicaWrapper().checkAccessRight(13);
                        this.mFwsClientFacade.checkNotRunningTask();
                        this.mIInitializedEventCallback = callback;
                        this.mFwsClientFacade.initialize(linkageData);
                    } catch (FelicaException e) {
                        LogMgr.log(2, "704 : FelicaException:" + e.getMessage());
                        throw e;
                    }
                } catch (Exception e2) {
                    LogMgr.log(2, "705 : Exception" + e2.getClass().getSimpleName(), e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            }
        }
        LogMgr.log(4, "999");
    }

    void getLinkageDataList(int actionType, String[] cidList, ILinkageDataListEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (actionType != 1 && actionType != 2 && actionType != 3) {
            LogMgr.log(2, "700 : actionType is invalid:");
            throw new IllegalArgumentException("The specified actionType is null or invalid.");
        }
        if (actionType == 1 || actionType == 2) {
            if (cidList == null || cidList.length == 0) {
                LogMgr.log(2, "701 : cidList is null or invalid.");
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
            LogMgr.log(2, "705 : callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    try {
                        this.mFelicaWrapper.checkMfiActivated();
                        checkStartedBy(2);
                        FelicaWrapper felicaWrapper = FelicaAdapter.getInstance().getFelicaWrapper();
                        if (actionType == 1 || actionType == 2) {
                            felicaWrapper.checkAccessRight(7);
                        }
                        if (actionType == 3) {
                            felicaWrapper.checkAccessRight(13);
                        }
                        this.mFwsClientFacade.checkNotRunningTask();
                        this.mILinkageDataListEventCallback = callback;
                        this.mFwsClientFacade.getLinkageDataList(actionType, cidList);
                    } catch (FelicaException e) {
                        LogMgr.log(2, "703 : FelicaException :" + e.getMessage());
                        throw e;
                    }
                } catch (Exception e2) {
                    LogMgr.log(2, "704 : Exception :" + e2.getClass().getSimpleName() + ":" + e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            }
        }
        LogMgr.log(4, "999");
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00bd A[Catch: all -> 0x00cf, TRY_ENTER, TryCatch #8 {, blocks: (B:20:0x0058, B:44:0x00bd, B:46:0x00c4, B:47:0x00ce), top: B:64:0x0024, outer: #5 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void executeServerOperation(String operationId, String messageId, ExecuteServerOperationEventCallback callback) throws FelicaException, IllegalArgumentException {
        boolean z;
        LogMgr.log(4, "000");
        if (operationId == null || operationId.isEmpty()) {
            LogMgr.log(2, "700 : operationId is invalid.");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_OPERATION_ID);
        }
        if (messageId == null || messageId.isEmpty()) {
            LogMgr.log(2, "701 : messageId is invalid.");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_MESSAGE_ID);
        }
        if (callback == null) {
            LogMgr.log(2, "702 : callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                boolean z2 = true;
                try {
                    try {
                        this.mFelicaWrapper.checkMfiActivated();
                        this.mFelicaWrapper.checkNotOpened();
                        this.mFwsClientFacade.checkNotRunningTask();
                        if (!OpenIdConnectActivity.setMfiStarted(true)) {
                            throw new MfiClientException(2, 39, null);
                        }
                        try {
                            this.mExecuteServerOperationEventCallback = callback;
                            if (this.mStarted == 0) {
                                this.mFelicaWrapper.setMfiOnline(this);
                                this.mFwsClientFacade.start(this.mOnFinishListener, this.mDataManager, this.mFelicaWrapper.getWalletAppId());
                            }
                            this.mFwsClientFacade.executeServerOperation(operationId, messageId);
                        } catch (FelicaException e) {
                            e = e;
                        } catch (Exception e2) {
                            e = e2;
                            z = true;
                            try {
                                LogMgr.log(2, "703 : Exception :" + e.getClass().getSimpleName() + ":" + e.getMessage());
                                LogMgr.printStackTrace(7, e);
                                throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                            } catch (Throwable th) {
                                th = th;
                                z2 = z;
                                if (z2) {
                                    OpenIdConnectActivity.setMfiStarted(false);
                                    if (this.mStarted == 0) {
                                        this.mFelicaWrapper.setMfiOnline(null);
                                        this.mFwsClientFacade.finish();
                                    }
                                }
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (z2) {
                        }
                        throw th;
                    }
                } catch (FelicaException e3) {
                    e = e3;
                } catch (Exception e4) {
                    e = e4;
                    z = false;
                } catch (Throwable th3) {
                    th = th3;
                    z2 = false;
                    if (z2) {
                    }
                    throw th;
                }
                LogMgr.log(2, "702 : FelicaException :" + e.getMessage());
                throw e;
            }
        }
        LogMgr.log(4, "999");
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00ad A[Catch: all -> 0x00bf, TRY_ENTER, TryCatch #8 {, blocks: (B:14:0x0048, B:38:0x00ad, B:40:0x00b4, B:41:0x00be), top: B:54:0x0014, outer: #5 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void provisionServerOperation(IServerOperationEventCallback callback) throws FelicaException, IllegalArgumentException {
        boolean z;
        LogMgr.log(4, "000");
        if (callback == null) {
            LogMgr.log(2, "701 : callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                boolean z2 = true;
                try {
                    try {
                        this.mFelicaWrapper.checkMfiActivated();
                        this.mFelicaWrapper.checkNotOpened();
                        this.mFwsClientFacade.checkNotRunningTask();
                        if (!OpenIdConnectActivity.setMfiStarted(true)) {
                            throw new MfiClientException(2, 39, null);
                        }
                        try {
                            this.mIServerOperationEventCallback = callback;
                            if (this.mStarted == 0) {
                                this.mFelicaWrapper.setMfiOnline(this);
                                this.mFwsClientFacade.start(this.mOnFinishListener, this.mDataManager, this.mFelicaWrapper.getWalletAppId());
                            }
                            this.mFwsClientFacade.provisionServerOperation();
                        } catch (FelicaException e) {
                            e = e;
                        } catch (Exception e2) {
                            e = e2;
                            z = true;
                            try {
                                LogMgr.log(2, "703 : Exception :" + e.getClass().getSimpleName() + ":" + e.getMessage());
                                LogMgr.printStackTrace(7, e);
                                throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                            } catch (Throwable th) {
                                th = th;
                                z2 = z;
                                if (z2) {
                                    OpenIdConnectActivity.setMfiStarted(false);
                                    if (this.mStarted == 0) {
                                        this.mFelicaWrapper.setMfiOnline(null);
                                        this.mFwsClientFacade.finish();
                                    }
                                }
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (z2) {
                        }
                        throw th;
                    }
                } catch (FelicaException e3) {
                    e = e3;
                } catch (Exception e4) {
                    e = e4;
                    z = false;
                } catch (Throwable th3) {
                    th = th3;
                    z2 = false;
                    if (z2) {
                    }
                    throw th;
                }
                LogMgr.log(2, "702 : FelicaException :" + e.getMessage());
                throw e;
            }
        }
        LogMgr.log(4, "999");
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00dc A[Catch: all -> 0x00ee, TRY_ENTER, TryCatch #8 {, blocks: (B:20:0x0077, B:44:0x00dc, B:46:0x00e3, B:47:0x00ed), top: B:66:0x0026, outer: #4 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void existUnsupportMfiService1Card(String serviceId, IUnsupportMfiService1CardExistEventCallback callback) throws FelicaException, IllegalArgumentException {
        boolean z;
        LogMgr.log(4, "000");
        if (callback == null) {
            LogMgr.log(2, "701 : callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        if (serviceId == null) {
            LogMgr.log(2, "700 serviceId is null.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (!serviceId.matches("[0-9a-zA-Z]*")) {
            LogMgr.log(2, "701 serviceId involves invalid character.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (serviceId.length() != 8) {
            LogMgr.log(2, "702 serviceId length is invalid.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                boolean z2 = true;
                try {
                    try {
                        this.mFelicaWrapper.checkMfiActivated();
                        this.mFelicaWrapper.checkNotOpened();
                        this.mFwsClientFacade.checkNotRunningTask();
                        if (!OpenIdConnectActivity.setMfiStarted(true)) {
                            throw new MfiClientException(2, 39, null);
                        }
                        this.mFelicaWrapper.checkAccessRight(14);
                        this.mFelicaWrapper.checkAccessRight(49);
                        this.mFelicaWrapper.checkMfiAccessServiceId(serviceId);
                        boolean zCanAccessRight = this.mFelicaWrapper.canAccessRight(50);
                        try {
                            this.mIUnsupportMfiService1CardExistEventCallback = callback;
                            if (this.mStarted == 0) {
                                this.mFelicaWrapper.setMfiOnline(this);
                                this.mFwsClientFacade.start(this.mOnFinishListener, this.mDataManager, this.mFelicaWrapper.getWalletAppId());
                            }
                            this.mFwsClientFacade.checkExistsManagementCard(serviceId, !zCanAccessRight);
                        } catch (FelicaException e) {
                            e = e;
                            LogMgr.log(2, "702 : FelicaException :" + e.getMessage());
                            throw e;
                        } catch (Exception e2) {
                            e = e2;
                            z = true;
                            try {
                                LogMgr.log(2, "703 : Exception :" + e.getClass().getSimpleName() + ":" + e.getMessage());
                                LogMgr.printStackTrace(7, e);
                                throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                            } catch (Throwable th) {
                                th = th;
                                z2 = z;
                                if (z2) {
                                    OpenIdConnectActivity.setMfiStarted(false);
                                    if (this.mStarted == 0) {
                                        this.mFelicaWrapper.setMfiOnline(null);
                                        this.mFwsClientFacade.finish();
                                    }
                                }
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (z2) {
                        }
                        throw th;
                    }
                } catch (FelicaException e3) {
                    e = e3;
                } catch (Exception e4) {
                    e = e4;
                    z = false;
                } catch (Throwable th3) {
                    th = th3;
                    z2 = false;
                    if (z2) {
                    }
                    throw th;
                }
            }
        }
        LogMgr.log(4, "999");
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00d3 A[Catch: all -> 0x00e5, TRY_ENTER, TryCatch #1 {, blocks: (B:22:0x0070, B:46:0x00d3, B:48:0x00da, B:49:0x00e4), top: B:68:0x002f, outer: #7 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void deleteUnsupportMfiService1Card(String linkageData, IUnsupportMfiService1CardDeleteEventCallback callback) throws FelicaException, IllegalArgumentException {
        boolean z;
        LogMgr.log(4, "000");
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
        boolean z2 = true;
        try {
            String mFCVersion = FelicaWrapper.getMFCVersion(FelicaAdapter.getInstance());
            if (StringUtil.versionCompare(mFCVersion, MfiClientConst.MFC_VERSION_FAVER3) == -1) {
                LogMgr.log(2, "704 : MFC Version : " + mFCVersion);
                throw new MfiClientException(103, MfiClientException.TYPE_NOT_SUPPORTED_CHIP_ERROR, null);
            }
            synchronized (this.mFelicaWrapper) {
                synchronized (this) {
                    try {
                        try {
                            this.mFelicaWrapper.checkMfiActivated();
                            this.mFelicaWrapper.checkNotOpened();
                            this.mFwsClientFacade.checkNotRunningTask();
                            if (!OpenIdConnectActivity.setMfiStarted(true)) {
                                throw new MfiClientException(2, 39, null);
                            }
                            FelicaAdapter.getInstance().getFelicaWrapper().checkAccessRight(49);
                            try {
                                this.mIUnsupportMfiService1CardDeleteEventCallback = callback;
                                if (this.mStarted == 0) {
                                    this.mFelicaWrapper.setMfiOnline(this);
                                    this.mFwsClientFacade.start(this.mOnFinishListener, this.mDataManager, this.mFelicaWrapper.getWalletAppId());
                                }
                                this.mFwsClientFacade.deleteLegacyCard(linkageData);
                            } catch (FelicaException e) {
                                e = e;
                                LogMgr.log(2, "706 : FelicaException: " + e.getMessage());
                                throw e;
                            } catch (Exception e2) {
                                e = e2;
                                z = true;
                                try {
                                    LogMgr.log(2, "707 : " + e.getClass().getSimpleName() + ":" + e.getMessage());
                                    LogMgr.printStackTrace(7, e);
                                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                                } catch (Throwable th) {
                                    th = th;
                                    z2 = z;
                                    if (z2) {
                                    }
                                    throw th;
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (z2) {
                                OpenIdConnectActivity.setMfiStarted(false);
                                if (this.mStarted == 0) {
                                    this.mFelicaWrapper.setMfiOnline(null);
                                    this.mFwsClientFacade.finish();
                                }
                            }
                            throw th;
                        }
                    } catch (FelicaException e3) {
                        e = e3;
                    } catch (Exception e4) {
                        e = e4;
                        z = false;
                    } catch (Throwable th3) {
                        th = th3;
                        z2 = false;
                        if (z2) {
                        }
                        throw th;
                    }
                }
            }
            LogMgr.log(4, "999");
        } catch (FelicaException e5) {
            LogMgr.log(2, "703 : " + e5.getClass().getSimpleName() + " : " + e5.getMessage());
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00bc A[Catch: all -> 0x00ca, TRY_ENTER, TryCatch #5 {, blocks: (B:13:0x004f, B:39:0x00bc, B:40:0x00c9), top: B:52:0x0014, outer: #8 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void getMfiTosData(int code, IMfiTosDataGetEventCallback callback) throws FelicaException, IllegalArgumentException {
        boolean z;
        LogMgr.log(4, "000");
        if (callback == null) {
            LogMgr.log(2, "700 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                boolean z2 = true;
                try {
                    try {
                        this.mFelicaWrapper.checkMfiActivated();
                        this.mFelicaWrapper.checkNotOpened();
                        if (this.mStarted > 0) {
                            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_ALREADY_STARTED, null);
                        }
                        this.mFwsClientFacade.checkNotRunningTask();
                        if (!OpenIdConnectActivity.setMfiStarted(true)) {
                            throw new MfiClientException(2, 39, null);
                        }
                        try {
                            this.mFelicaWrapper.checkAccessRight(14);
                            this.mIMfiTosDataGetEventCallback = callback;
                            this.mFelicaWrapper.setMfiOnline(this);
                            this.mFwsClientFacade.start(this.mOnFinishListener, this.mDataManager, this.mFelicaWrapper.getWalletAppId());
                            this.mFwsClientFacade.getMfiTosData(code);
                        } catch (FelicaException e) {
                            e = e;
                            LogMgr.log(2, "701 : FelicaException: " + e.getMessage());
                            throw e;
                        } catch (Exception e2) {
                            e = e2;
                            z = true;
                            try {
                                LogMgr.log(2, "702 : " + e.getClass().getSimpleName() + ":" + e.getMessage());
                                LogMgr.printStackTrace(7, e);
                                throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                            } catch (Throwable th) {
                                th = th;
                                z2 = z;
                                if (z2) {
                                    OpenIdConnectActivity.setMfiStarted(false);
                                    this.mFelicaWrapper.setMfiOnline(null);
                                    this.mFwsClientFacade.finish();
                                }
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (z2) {
                        }
                        throw th;
                    }
                } catch (FelicaException e3) {
                    e = e3;
                } catch (Exception e4) {
                    e = e4;
                    z = false;
                } catch (Throwable th3) {
                    th = th3;
                    z2 = false;
                    if (z2) {
                    }
                    throw th;
                }
            }
        }
        LogMgr.log(4, "999");
    }

    void setMfiTosAgreement(int code) throws FelicaException {
        boolean z;
        LogMgr.log(4, "000");
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    this.mFelicaWrapper.checkMfiActivated();
                    this.mFelicaWrapper.checkNotOpened();
                    if (this.mStarted > 0) {
                        throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_ALREADY_STARTED, null);
                    }
                    this.mFwsClientFacade.checkNotRunningTask();
                    z = true;
                    if (!OpenIdConnectActivity.setMfiStarted(true)) {
                        throw new MfiClientException(2, 39, null);
                    }
                    try {
                        this.mFelicaWrapper.checkAccessRight(14);
                        this.mFelicaWrapper.setMfiOnline(this);
                        if (code == 0) {
                            LogMgr.log(2, "700 Code is zero.");
                            throw new MfiClientException(2, MfiClientException.TYPE_INVALID_CODE_ERROR, null);
                        }
                        PreAccountCache preAccountCache = PreAccountCache.getInstance();
                        if (code != preAccountCache.getAccountCodeCache()) {
                            preAccountCache.clearLoginData();
                            LogMgr.log(2, "701 Code is invalid.");
                            throw new MfiClientException(2, MfiClientException.TYPE_INVALID_CODE_ERROR, null);
                        }
                        OpenIdConnectSharedPreferences.agreeCooperateAccountAll(FelicaAdapter.getInstance());
                        OpenIdConnectActivity.setMfiStarted(false);
                        this.mFelicaWrapper.setMfiOnline(null);
                    } catch (Throwable th) {
                        th = th;
                        if (z) {
                            OpenIdConnectActivity.setMfiStarted(false);
                            this.mFelicaWrapper.setMfiOnline(null);
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    z = false;
                }
            }
        }
        LogMgr.log(4, "999");
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00c2 A[Catch: all -> 0x00d0, TRY_ENTER, TryCatch #0 {, blocks: (B:14:0x0057, B:40:0x00c2, B:41:0x00cf), top: B:52:0x001c, outer: #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void getGoogleTos(int code, IGoogleTosGetEventCallback callback) throws FelicaException, IllegalArgumentException {
        boolean z;
        LogMgr.log(4, "000");
        boolean z2 = true;
        if (!isSmartWatch()) {
            LogMgr.log(2, "703 Can only be called from smart watch.");
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
        if (callback == null) {
            LogMgr.log(2, "700 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    this.mFelicaWrapper.checkMfiActivated();
                    this.mFelicaWrapper.checkNotOpened();
                    if (this.mStarted > 0) {
                        throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_ALREADY_STARTED, null);
                    }
                    this.mFwsClientFacade.checkNotRunningTask();
                    if (!OpenIdConnectActivity.setMfiStarted(true)) {
                        throw new MfiClientException(2, 39, null);
                    }
                    try {
                        this.mFelicaWrapper.checkAccessRight(14);
                        this.mIGoogleTosGetEventCallback = callback;
                        this.mFelicaWrapper.setMfiOnline(this);
                        this.mFwsClientFacade.start(this.mOnFinishListener, this.mDataManager, this.mFelicaWrapper.getWalletAppId());
                        this.mFwsClientFacade.getGoogleTos(code);
                    } catch (FelicaException e) {
                        e = e;
                        LogMgr.log(2, "701 : FelicaException: " + e.getMessage());
                        throw e;
                    } catch (Exception e2) {
                        e = e2;
                        z = true;
                        try {
                            LogMgr.log(2, "702 : " + e.getClass().getSimpleName() + ":" + e.getMessage());
                            LogMgr.printStackTrace(7, e);
                            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                        } catch (Throwable th2) {
                            th = th2;
                            z2 = z;
                            if (z2) {
                                OpenIdConnectActivity.setMfiStarted(false);
                                this.mFelicaWrapper.setMfiOnline(null);
                                this.mFwsClientFacade.finish();
                            }
                            throw th;
                        }
                    }
                } catch (FelicaException e3) {
                    e = e3;
                } catch (Exception e4) {
                    e = e4;
                    z = false;
                } catch (Throwable th3) {
                    th = th3;
                    z2 = false;
                    if (z2) {
                    }
                    throw th;
                }
            }
        }
        LogMgr.log(4, "999");
    }

    void getRemainedCards(IRemainedCardsEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (isSmartWatch()) {
            LogMgr.log(2, "700 Can not be called from smart watch.");
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
        if (callback == null) {
            LogMgr.log(2, "701 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    this.mFelicaWrapper.checkMfiActivated();
                    checkStartedBy(2);
                    this.mFelicaWrapper.checkAccessRight(50);
                    this.mFwsClientFacade.checkNotRunningTask();
                    this.mIRemainedCardsEventCallback = callback;
                    this.mFwsClientFacade.getRemainedCards();
                } catch (FelicaException e) {
                    LogMgr.log(2, "702 : FelicaException: " + e.getMessage());
                    throw e;
                } catch (Exception e2) {
                    LogMgr.log(2, "703 : " + e2.getClass().getSimpleName() + ":" + e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            }
        }
        LogMgr.log(4, "999");
    }

    void uploadCardsToDelete(ICardsUploadEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        boolean z = false;
        Boolean bool = false;
        if (callback == null) {
            LogMgr.log(2, "700 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    try {
                        this.mFelicaWrapper.checkMfiActivated();
                        this.mFelicaWrapper.checkNotOpened();
                        this.mFwsClientFacade.checkNotRunningTask();
                        checkProcessingStartApi();
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (FelicaException e) {
                    e = e;
                } catch (Exception e2) {
                    e = e2;
                }
                try {
                    this.mICardsUploadToDeleteEventCallback = callback;
                    changeStateToStarted();
                    this.mFwsClientFacade.uploadCardsToDelete();
                    Boolean bool2 = true;
                    bool2.getClass();
                } catch (FelicaException e3) {
                    e = e3;
                    LogMgr.log(2, "701 : FelicaException:" + e.getMessage());
                    throw e;
                } catch (Exception e4) {
                    e = e4;
                    LogMgr.log(2, "702 : " + e.getClass().getSimpleName() + ":" + e.getMessage());
                    LogMgr.printStackTrace(7, e);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                } catch (Throwable th2) {
                    th = th2;
                    z = true;
                    bool.getClass();
                    if (z) {
                        restoreStateFromStarted();
                    }
                    throw th;
                }
            }
        }
        LogMgr.log(4, "999");
    }

    void uploadCardsToDisable(ICardsUploadEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (callback == null) {
            LogMgr.log(2, "700 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        LogMgr.log(4, "999");
        throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
    }

    void uploadCardsToPermanentDelete(ICardsUploadEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        boolean z = false;
        Boolean bool = false;
        if (callback == null) {
            LogMgr.log(2, "700 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    try {
                        this.mFelicaWrapper.checkMfiActivated();
                        this.mFelicaWrapper.checkNotOpened();
                        this.mFwsClientFacade.checkNotRunningTask();
                        checkProcessingStartApi();
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (FelicaException e) {
                    e = e;
                } catch (Exception e2) {
                    e = e2;
                }
                try {
                    this.mICardsUploadToPermanentDeleteEventCallback = callback;
                    changeStateToStarted();
                    this.mFwsClientFacade.uploadCardsToPermanentDelete();
                    Boolean bool2 = true;
                    bool2.getClass();
                } catch (FelicaException e3) {
                    e = e3;
                    LogMgr.log(2, "701 : FelicaException:" + e.getMessage());
                    throw e;
                } catch (Exception e4) {
                    e = e4;
                    LogMgr.log(2, "702 : " + e.getClass().getSimpleName() + ":" + e.getMessage());
                    LogMgr.printStackTrace(7, e);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                } catch (Throwable th2) {
                    th = th2;
                    z = true;
                    bool.getClass();
                    if (z) {
                        restoreStateFromStarted();
                    }
                    throw th;
                }
            }
        }
        LogMgr.log(4, "999");
    }

    void clearMemory(IMemoryClearEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (isSmartWatch()) {
            LogMgr.log(2, "700 Can not be called from smart watch.");
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
        if (callback == null) {
            LogMgr.log(2, "701 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    this.mFelicaWrapper.checkMfiActivated();
                    checkStartedBy(2);
                    this.mFelicaWrapper.checkAccessRight(50);
                    this.mFwsClientFacade.checkNotRunningTask();
                    this.mIMemoryClearEventCallback = callback;
                    this.mFwsClientFacade.clearMemory();
                } catch (FelicaException e) {
                    LogMgr.log(2, "702 : FelicaException: " + e.getMessage());
                    throw e;
                } catch (Exception e2) {
                    LogMgr.log(2, "703 : " + e2.getClass().getSimpleName() + ":" + e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            }
        }
        LogMgr.log(4, "999");
    }

    void migrateCardKey(String serviceId, ICardKeyMigrateEventCallback callback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (callback == null) {
            LogMgr.log(2, "700 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        if (serviceId == null) {
            LogMgr.log(2, "701 serviceId is null.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (!serviceId.matches("[0-9a-zA-Z]*")) {
            LogMgr.log(2, "702 serviceId involves invalid character.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (serviceId.length() != 8) {
            LogMgr.log(2, "703 serviceId length is invalid.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (!FlavorConst.MIGRATING_DES_CARD_SERVICE_ID_PREFERENCE_MAP.containsKey(serviceId)) {
            LogMgr.log(2, "704 serviceIdList is invalid.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    this.mFelicaWrapper.checkMfiActivated();
                    checkStartedBy(1);
                    this.mFelicaWrapper.checkAccessRight(14);
                    this.mFelicaWrapper.checkAccessRight(6);
                    this.mFelicaWrapper.checkAccessRight(8);
                    this.mFwsClientFacade.checkNotRunningTask();
                    JSONArray walletAppCertHashList = !this.mFelicaWrapper.canAccessRight(11) ? this.mFelicaWrapper.getWalletAppCertHashList() : null;
                    this.mICardKeyMigrateEventCallback = callback;
                    this.mFwsClientFacade.migrateCardKey(walletAppCertHashList, serviceId);
                } catch (FelicaException e) {
                    LogMgr.log(2, "705 : FelicaException: " + e.getMessage());
                    throw e;
                } catch (Exception e2) {
                    LogMgr.log(2, "706 : " + e2.getClass().getSimpleName() + ":" + e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            }
        }
        LogMgr.log(4, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finish() {
        finish(false);
    }

    private void finish(boolean isForce) {
        finish(isForce, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishWhenNotStarted() {
        finish(false, true);
    }

    private boolean isSmartWatch() {
        LogMgr.log(3, "000");
        boolean zHasSystemFeature = FelicaAdapter.getInstance().getPackageManager().hasSystemFeature("android.hardware.type.watch");
        LogMgr.log(3, "999");
        return zHasSystemFeature;
    }

    private void finish(boolean isForce, boolean whenNotStarted) {
        LogMgr.log(4, "%s", "000");
        try {
        } catch (Exception e) {
            LogMgr.log(1, "%s", "800");
            LogMgr.printStackTrace(7, e);
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                if (whenNotStarted) {
                    if (this.mStarted != 0) {
                        LogMgr.log(4, "998 mStarted=" + this.mStarted);
                        return;
                    }
                }
                this.mStarted = 0;
                this.mLoggingOut = false;
                this.mFelicaWrapper.setMfiOnline(null);
                OpenIdConnectActivity.setMfiStarted(false);
                if (isForce) {
                    clearSeInfo();
                }
                this.mFwsClientFacade.finish();
                LogMgr.log(4, "%s", "999");
            }
        }
    }

    private synchronized void checkStarted() throws MfiClientException {
        if (this.mStarted == 0) {
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_NOT_STARTED, null);
        }
    }

    synchronized void checkStartedBy(int condition) throws MfiClientException {
        if (!isStartedBy(condition)) {
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_NOT_STARTED, null);
        }
    }

    private synchronized boolean isStartedBy(int condition) throws MfiClientException {
        return (this.mStarted & condition) == condition;
    }

    private void checkMfiAccess() throws MfiClientException {
        if (!this.mFelicaWrapper.canAccessRight(6) && !this.mFelicaWrapper.canAccessRight(7) && !this.mFelicaWrapper.canAccessRight(8) && !this.mFelicaWrapper.canAccessRight(16)) {
            throw new MfiClientException(12, 38, null);
        }
    }

    void checkProcessingStartApi() throws MfiClientException {
        if (!OpenIdConnectActivity.setMfiStarted(true)) {
            throw new MfiClientException(2, 39, null);
        }
    }

    void changeStateToStarted() throws MfiClientException {
        if (this.mStarted == 0) {
            this.mFelicaWrapper.setMfiOnline(this);
            this.mFwsClientFacade.start(this.mOnFinishListener, this.mDataManager, this.mFelicaWrapper.getWalletAppId());
        }
    }

    void restoreStateFromStarted() {
        OpenIdConnectActivity.setMfiStarted(false);
        if (this.mStarted == 0) {
            this.mFelicaWrapper.setMfiOnline(null);
            this.mFwsClientFacade.finish();
        }
    }

    private class OnFinishListener implements FwsClientFacade.OnFinishListener {
        private OnFinishListener() {
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onLoginFinished(boolean isSuccess, int errType, String errMsg) {
            ILoginEventCallback iLoginEventCallback;
            int i;
            LogMgr.log(4, "%s", "000");
            synchronized (MfiOnline.this) {
                iLoginEventCallback = MfiOnline.this.mILoginEventCallback;
                MfiOnline.this.mILoginEventCallback = null;
                i = MfiOnline.this.mStarted;
            }
            OpenIdConnectActivity.setMfiStarted(false);
            try {
                if (!isSuccess) {
                    synchronized (MfiOnline.this.mFelicaWrapper) {
                        synchronized (MfiOnline.this) {
                            MfiOnline.this.mFelicaWrapper.setMfiOnline(null);
                            MfiOnline.this.mFwsClientFacade.finish();
                        }
                    }
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_START, errType);
                    iLoginEventCallback.onError(errType, errMsg);
                } else {
                    synchronized (MfiOnline.this) {
                        MfiOnline.this.mStarted = 1;
                    }
                    LogMgr.performanceIn("API", "ILoginEventCallback", "onSuccess");
                    iLoginEventCallback.onSuccess();
                    LogMgr.performanceOut("API", "ILoginEventCallback", "onSuccess");
                }
            } catch (Exception e) {
                LogMgr.log(1, "%s", "801");
                LogMgr.printStackTrace(7, e);
                try {
                } catch (Exception e2) {
                    LogMgr.log(1, "%s", "802");
                    LogMgr.printStackTrace(7, e2);
                }
                synchronized (MfiOnline.this.mFelicaWrapper) {
                    synchronized (MfiOnline.this) {
                        MfiOnline.this.mStarted = i;
                        MfiOnline.this.mFelicaWrapper.setMfiOnline(null);
                        MfiOnline.this.mFwsClientFacade.finish();
                        try {
                            LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_START, 200);
                            iLoginEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                        } catch (RemoteException e3) {
                            LogMgr.log(1, "%s", "803");
                            LogMgr.printStackTrace(7, e3);
                        }
                    }
                }
            }
            LogMgr.log(4, "%s", "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onLogoutFinished(boolean isSuccess, boolean isSilentStartStopped, int errType, String errMsg) {
            ILogoutEventCallback iLogoutEventCallback;
            LogMgr.log(4, "%s", "000");
            synchronized (MfiOnline.this) {
                iLogoutEventCallback = MfiOnline.this.mILogoutEventCallback;
                MfiOnline.this.mILogoutEventCallback = null;
            }
            if (iLogoutEventCallback != null) {
                try {
                    if (isSuccess) {
                        MfiOnline.this.finish();
                        LogMgr.performanceIn("API", "ILogoutEventCallback", "onSuccess");
                        iLogoutEventCallback.onSuccess();
                        LogMgr.performanceOut("API", "ILogoutEventCallback", "onSuccess");
                    } else {
                        if (isSilentStartStopped) {
                            MfiOnline.this.finish();
                        }
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_STOP, errType);
                        iLogoutEventCallback.onError(errType, errMsg);
                    }
                } catch (Exception e) {
                    LogMgr.log(1, "%s", "801");
                    LogMgr.printStackTrace(7, e);
                    try {
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_STOP, 200);
                        iLogoutEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                    } catch (Exception e2) {
                        LogMgr.log(1, "%s", "802");
                        LogMgr.printStackTrace(7, e2);
                    }
                }
            }
            LogMgr.log(4, "%s", "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onInitializeFinished(boolean isSuccess, int errType, String errMsg) {
            IInitializedEventCallback iInitializedEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iInitializedEventCallback = MfiOnline.this.mIInitializedEventCallback;
                MfiOnline.this.mIInitializedEventCallback = null;
            }
            try {
                if (isSuccess) {
                    LogMgr.performanceIn("API", "IInitializedEventCallback", "onSuccess");
                    iInitializedEventCallback.onSuccess();
                    LogMgr.performanceOut("API", "IInitializedEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_ADMIN_INITIALIZE, errType);
                    iInitializedEventCallback.onError(errType, errMsg);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_ADMIN_INITIALIZE, 200);
                    iInitializedEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onGetCardListFinished(boolean isSuccess, CardInfo[] cardInfos, int errType, String errMsg) {
            ICardListEventCallback iCardListEventCallback;
            LogMgr.log(4, "%s", "000");
            synchronized (MfiOnline.this) {
                iCardListEventCallback = MfiOnline.this.mICardListEventCallback;
                MfiOnline.this.mICardListEventCallback = null;
            }
            try {
                if (isSuccess) {
                    LogMgr.performanceIn("API", "ICardListEventCallback", "onSuccess");
                    iCardListEventCallback.onSuccess(cardInfos);
                    LogMgr.performanceOut("API", "ICardListEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_LIST, errType);
                    iCardListEventCallback.onError(errType, errMsg);
                }
            } catch (Exception e) {
                LogMgr.log(1, "%s", "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_LIST, 200);
                    iCardListEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "%s", "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "%s", "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onGetCardListV2Finished(boolean isSuccess, int errType, String errMsg) {
            IPipeEventCallback iPipeEventCallback;
            LogMgr.log(4, "000");
            if (!isSuccess) {
                LogMgr.log(4, "001");
                synchronized (MfiOnline.this) {
                    iPipeEventCallback = MfiOnline.this.mIPipeEventCallback;
                    MfiOnline.this.mIPipeEventCallback = null;
                }
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_LIST, errType);
                    iPipeEventCallback.onError(errType, errMsg);
                } catch (Exception e) {
                    LogMgr.log(1, "801");
                    LogMgr.printStackTrace(7, e);
                    try {
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_LIST, 200);
                        iPipeEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                    } catch (RemoteException e2) {
                        LogMgr.log(1, "802");
                        LogMgr.printStackTrace(7, e2);
                    }
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onWritePipeFinished(boolean isSuccess, MfiClientExternalLogConst.MficApi api, int errType, String errMsg) {
            IPipeEventCallback iPipeEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iPipeEventCallback = MfiOnline.this.mIPipeEventCallback;
                MfiOnline.this.mIPipeEventCallback = null;
            }
            if (!isSuccess) {
                try {
                    LogMgr.exLogOnError(api, errType);
                    iPipeEventCallback.onError(errType, errMsg);
                } catch (Exception e) {
                    LogMgr.log(1, "%s", "801");
                    LogMgr.printStackTrace(7, e);
                    try {
                        LogMgr.exLogOnError(api, 200);
                        iPipeEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                    } catch (RemoteException e2) {
                        LogMgr.log(1, "%s", "802");
                        LogMgr.printStackTrace(7, e2);
                    }
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onGetCardAdditionalInfoListFinished(boolean isSuccess, CardAdditionalInfo[] cardAdditionalInfos, int errType, String errMsg) {
            ICardAdditionalInfoListEventCallback iCardAdditionalInfoListEventCallback;
            LogMgr.log(4, "%s", "000");
            synchronized (MfiOnline.this) {
                iCardAdditionalInfoListEventCallback = MfiOnline.this.mICardAdditionalInfoListEventCallback;
                MfiOnline.this.mICardAdditionalInfoListEventCallback = null;
            }
            try {
                if (isSuccess) {
                    LogMgr.performanceIn("API", "ICardAdditionalInfoListEventCallback", "onSuccess");
                    iCardAdditionalInfoListEventCallback.onSuccess(cardAdditionalInfos);
                    LogMgr.performanceOut("API", "ICardAdditionalInfoListEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_ADDITIONAL_INFO_LIST, errType);
                    iCardAdditionalInfoListEventCallback.onError(errType, errMsg);
                }
            } catch (Exception e) {
                LogMgr.log(1, "%s", "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_ADDITIONAL_INFO_LIST, 200);
                    iCardAdditionalInfoListEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "%s", "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "%s", "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onGetCardAdditionalInfoListV2Finished(boolean isSuccess, int errType, String errMsg) {
            IPipeEventCallback iPipeEventCallback;
            LogMgr.log(4, "000");
            if (!isSuccess) {
                LogMgr.log(4, "001");
                synchronized (MfiOnline.this) {
                    iPipeEventCallback = MfiOnline.this.mIPipeEventCallback;
                    MfiOnline.this.mIPipeEventCallback = null;
                }
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_ADDITIONAL_INFO_LIST, errType);
                    iPipeEventCallback.onError(errType, errMsg);
                } catch (Exception e) {
                    LogMgr.log(1, "%s", "801");
                    LogMgr.printStackTrace(7, e);
                    try {
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_ADDITIONAL_INFO_LIST, 200);
                        iPipeEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                    } catch (RemoteException e2) {
                        LogMgr.log(1, "%s", "802");
                        LogMgr.printStackTrace(7, e2);
                    }
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onIssueCardFinished(boolean isSuccess, CardInfo cardInfo, int errType, String errMsg) {
            ICardIssueV2EventCallback iCardIssueV2EventCallback;
            ICardIssueEventCallback iCardIssueEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                if (MfiOnline.this.mICardIssueEventCallback == null) {
                    if (MfiOnline.this.mICardIssueV2EventCallback != null) {
                        LogMgr.log(4, "002");
                        iCardIssueV2EventCallback = MfiOnline.this.mICardIssueV2EventCallback;
                        iCardIssueEventCallback = null;
                    } else {
                        iCardIssueV2EventCallback = null;
                        iCardIssueEventCallback = null;
                    }
                } else {
                    LogMgr.log(4, "001");
                    iCardIssueEventCallback = MfiOnline.this.mICardIssueEventCallback;
                    iCardIssueV2EventCallback = null;
                }
                MfiOnline.this.mICardIssueEventCallback = null;
                MfiOnline.this.mICardIssueV2EventCallback = null;
            }
            if (iCardIssueEventCallback != null) {
                LogMgr.log(4, "003");
                onIssueCardV1Finished(isSuccess, cardInfo, errType, errMsg, iCardIssueEventCallback);
            } else if (iCardIssueV2EventCallback != null) {
                LogMgr.log(4, "004");
                onIssueCardV2Finished(isSuccess, cardInfo, errType, errMsg, iCardIssueV2EventCallback);
            } else {
                LogMgr.log(2, "700 callback is null.");
            }
            LogMgr.log(4, "999");
        }

        private void onIssueCardV1Finished(boolean isSuccess, CardInfo cardInfo, int errType, String errMsg, ICardIssueEventCallback callback) {
            LogMgr.log(4, "000");
            try {
                if (isSuccess) {
                    LogMgr.performanceIn("API", "ICardIssueEventCallback", "onSuccess");
                    callback.onSuccess(cardInfo);
                    LogMgr.performanceOut("API", "ICardIssueEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_ISSUE_CARD, errType);
                    callback.onError(errType, errMsg);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_ISSUE_CARD, 200);
                    callback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        private void onIssueCardV2Finished(boolean isSuccess, CardInfo cardInfo, int errType, String errMsg, ICardIssueV2EventCallback callback) {
            LogMgr.log(4, "000");
            try {
                if (isSuccess) {
                    String string = cardInfo != null ? new CardInfoJson(cardInfo).toString() : null;
                    LogMgr.performanceIn("API", "ICardIssueV2EventCallback", "onSuccess");
                    callback.onSuccess(string);
                    LogMgr.performanceOut("API", "ICardIssueV2EventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_ISSUE_CARD, errType);
                    callback.onError(errType, errMsg);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_ISSUE_CARD, 200);
                    callback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onDeleteCardFinished(boolean isSuccess, int errType, String errMsg) {
            ICardDeleteEventCallback iCardDeleteEventCallback;
            ICardDeleteV2EventCallback iCardDeleteV2EventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                if (MfiOnline.this.mICardDeleteEventCallback == null) {
                    if (MfiOnline.this.mICardDeleteV2EventCallback != null) {
                        LogMgr.log(4, "002");
                        iCardDeleteV2EventCallback = MfiOnline.this.mICardDeleteV2EventCallback;
                        iCardDeleteEventCallback = null;
                    } else {
                        iCardDeleteEventCallback = null;
                        iCardDeleteV2EventCallback = null;
                    }
                } else {
                    LogMgr.log(4, "001");
                    iCardDeleteEventCallback = MfiOnline.this.mICardDeleteEventCallback;
                    iCardDeleteV2EventCallback = null;
                }
                MfiOnline.this.mICardDeleteEventCallback = null;
                MfiOnline.this.mICardDeleteV2EventCallback = null;
            }
            if (iCardDeleteEventCallback != null) {
                LogMgr.log(4, "003");
                onDeleteCardV1Finished(isSuccess, errType, errMsg, iCardDeleteEventCallback);
            } else if (iCardDeleteV2EventCallback != null) {
                LogMgr.log(4, "004");
                onDeleteCardV2Finished(isSuccess, errType, errMsg, iCardDeleteV2EventCallback);
            } else {
                LogMgr.log(2, "700 callback is null.");
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onSaveDeleteCardV2Finished(boolean isSuccess, CardInfo cardInfo, int errType, String errMsg) {
            LogMgr.log(4, "000");
            if (MfiOnline.this.mICardReissuableDeleteEventCallback != null) {
                ICardReissuableDeleteEventCallback iCardReissuableDeleteEventCallback = MfiOnline.this.mICardReissuableDeleteEventCallback;
                MfiOnline.this.mICardReissuableDeleteEventCallback = null;
                try {
                    if (isSuccess) {
                        String string = cardInfo != null ? new CardInfoJson(cardInfo).toString() : null;
                        LogMgr.performanceIn("API", "ICardReissuableDeleteEventCallback", "onSuccess");
                        iCardReissuableDeleteEventCallback.onSuccess(string);
                        LogMgr.performanceOut("API", "ICardReissuableDeleteEventCallback", "onSuccess");
                    } else {
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_DELETE, errType);
                        iCardReissuableDeleteEventCallback.onError(errType, errMsg);
                    }
                } catch (Exception e) {
                    LogMgr.log(1, "801");
                    LogMgr.printStackTrace(7, e);
                    try {
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_DELETE, 200);
                        iCardReissuableDeleteEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                    } catch (RemoteException e2) {
                        LogMgr.log(1, "802");
                        LogMgr.printStackTrace(7, e2);
                    }
                }
                LogMgr.log(4, "999");
                return;
            }
            LogMgr.log(2, "700 callback is null.");
        }

        private void onDeleteCardV1Finished(boolean isSuccess, int errType, String errMsg, ICardDeleteEventCallback callback) {
            LogMgr.log(4, "000");
            try {
                if (isSuccess) {
                    LogMgr.performanceIn("API", "ICardDeleteEventCallback", "onSuccess");
                    callback.onSuccess();
                    LogMgr.performanceOut("API", "ICardDeleteEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_DELETE, errType);
                    callback.onError(errType, errMsg);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_DELETE, 200);
                    callback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        private void onDeleteCardV2Finished(boolean isSuccess, int errType, String errMsg, ICardDeleteV2EventCallback callback) {
            LogMgr.log(4, "000");
            try {
                if (isSuccess) {
                    LogMgr.performanceIn("API", "ICardDeleteV2EventCallback", "onSuccess");
                    callback.onSuccess();
                    LogMgr.performanceOut("API", "ICardDeleteV2EventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_DELETE, errType);
                    callback.onError(errType, errMsg);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_DELETE, 200);
                    callback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onEnableCardFinished(boolean isSuccess, CardInfo enableCard, CardInfo disableCard, int errType, String errMsg) {
            ICardEnableV2EventCallback iCardEnableV2EventCallback;
            ICardEnableEventCallback iCardEnableEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                if (MfiOnline.this.mICardEnableEventCallback == null) {
                    if (MfiOnline.this.mICardEnableV2EventCallback != null) {
                        LogMgr.log(4, "002");
                        iCardEnableV2EventCallback = MfiOnline.this.mICardEnableV2EventCallback;
                        iCardEnableEventCallback = null;
                    } else {
                        iCardEnableV2EventCallback = null;
                        iCardEnableEventCallback = null;
                    }
                } else {
                    LogMgr.log(4, "001");
                    iCardEnableEventCallback = MfiOnline.this.mICardEnableEventCallback;
                    iCardEnableV2EventCallback = null;
                }
                MfiOnline.this.mICardEnableEventCallback = null;
                MfiOnline.this.mICardEnableV2EventCallback = null;
            }
            if (iCardEnableEventCallback != null) {
                LogMgr.log(4, "003");
                onEnableCardV1Finished(isSuccess, enableCard, disableCard, errType, errMsg, iCardEnableEventCallback);
            } else if (iCardEnableV2EventCallback != null) {
                LogMgr.log(4, "004");
                onEnableCardV2Finished(isSuccess, enableCard, disableCard, errType, errMsg, iCardEnableV2EventCallback);
            } else {
                LogMgr.log(2, "700 callback is null.");
            }
            LogMgr.log(4, "999");
        }

        private void onEnableCardV1Finished(boolean isSuccess, CardInfo enableCard, CardInfo disableCard, int errType, String errMsg, ICardEnableEventCallback callback) {
            LogMgr.log(4, "000");
            try {
                if (isSuccess) {
                    LogMgr.performanceIn("API", "ICardEnableEventCallback", "onSuccess");
                    callback.onSuccess(enableCard, disableCard);
                    LogMgr.performanceOut("API", "ICardEnableEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_ENABLE, errType);
                    callback.onError(errType, errMsg);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_ENABLE, 200);
                    callback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        private void onEnableCardV2Finished(boolean isSuccess, CardInfo enableCard, CardInfo disableCard, int errType, String errMsg, ICardEnableV2EventCallback callback) {
            LogMgr.log(4, "000");
            try {
                if (isSuccess) {
                    String string = enableCard != null ? new CardInfoJson(enableCard).toString() : null;
                    String string2 = disableCard != null ? new CardInfoJson(disableCard).toString() : null;
                    LogMgr.performanceIn("API", "ICardEnableV2EventCallback", "onSuccess");
                    callback.onSuccess(string, string2);
                    LogMgr.performanceOut("API", "ICardEnableV2EventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_ENABLE, errType);
                    callback.onError(errType, errMsg);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_ENABLE, 200);
                    callback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onDisableCardFinished(boolean isSuccess, CardInfo cardInfo, int errType, String errMsg) {
            ICardDisableV2EventCallback iCardDisableV2EventCallback;
            ICardDisableEventCallback iCardDisableEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                if (MfiOnline.this.mICardDisableEventCallback == null) {
                    if (MfiOnline.this.mICardDisableV2EventCallback != null) {
                        LogMgr.log(4, "002");
                        iCardDisableV2EventCallback = MfiOnline.this.mICardDisableV2EventCallback;
                        iCardDisableEventCallback = null;
                    } else {
                        iCardDisableV2EventCallback = null;
                        iCardDisableEventCallback = null;
                    }
                } else {
                    LogMgr.log(4, "001");
                    iCardDisableEventCallback = MfiOnline.this.mICardDisableEventCallback;
                    iCardDisableV2EventCallback = null;
                }
                MfiOnline.this.mICardDisableEventCallback = null;
                MfiOnline.this.mICardDisableV2EventCallback = null;
            }
            if (iCardDisableEventCallback != null) {
                LogMgr.log(4, "003");
                onDisableCardV1Finished(isSuccess, cardInfo, errType, errMsg, iCardDisableEventCallback);
            } else if (iCardDisableV2EventCallback != null) {
                LogMgr.log(4, "004");
                onDisableCardV2Finished(isSuccess, cardInfo, errType, errMsg, iCardDisableV2EventCallback);
            } else {
                LogMgr.log(2, "700 callback is null.");
            }
            LogMgr.log(4, "999");
        }

        private void onDisableCardV1Finished(boolean isSuccess, CardInfo cardInfo, int errType, String errMsg, ICardDisableEventCallback callback) {
            LogMgr.log(4, "000");
            try {
                if (isSuccess) {
                    LogMgr.performanceIn("API", "ICardDisableEventCallback", "onSuccess");
                    callback.onSuccess(cardInfo);
                    LogMgr.performanceOut("API", "ICardDisableEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_DISABLE, errType);
                    callback.onError(errType, errMsg);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_DISABLE, 200);
                    callback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        private void onDisableCardV2Finished(boolean isSuccess, CardInfo cardInfo, int errType, String errMsg, ICardDisableV2EventCallback callback) {
            LogMgr.log(4, "000");
            try {
                if (isSuccess) {
                    String string = cardInfo != null ? new CardInfoJson(cardInfo).toString() : null;
                    LogMgr.performanceIn("API", "ICardDisableV2EventCallback", "onSuccess");
                    callback.onSuccess(string);
                    LogMgr.performanceOut("API", "ICardDisableV2EventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_DISABLE, errType);
                    callback.onError(errType, errMsg);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_DISABLE, 200);
                    callback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onGetAccessCardFinished(boolean isSuccess, int errType, String errMsg) {
            ICardAccessEventCallback iCardAccessEventCallback;
            LogMgr.log(4, "%s", "000");
            synchronized (MfiOnline.this) {
                iCardAccessEventCallback = MfiOnline.this.mICardAccessEventCallback;
                MfiOnline.this.mICardAccessEventCallback = null;
            }
            try {
                if (isSuccess) {
                    LogMgr.performanceIn("API", "ICardAccessEventCallback", "onSuccess");
                    iCardAccessEventCallback.onSuccess();
                    LogMgr.performanceOut("API", "ICardAccessEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_ACCESS, errType);
                    iCardAccessEventCallback.onError(errType, errMsg);
                }
            } catch (Exception e) {
                LogMgr.log(1, "%s", "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_ACCESS, 200);
                    iCardAccessEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "%s", "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "%s", "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onSilentStartForMfiAdminFinished(boolean isSuccess, boolean noLogin, Intent intent, int errType, String errMsg) {
            ISilentStartForMfiAdminEventCallback iSilentStartForMfiAdminEventCallback;
            int i;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iSilentStartForMfiAdminEventCallback = MfiOnline.this.mISilentStartForMfiAdminEventCallback;
                MfiOnline.this.mISilentStartForMfiAdminEventCallback = null;
                i = MfiOnline.this.mStarted;
            }
            OpenIdConnectActivity.setMfiStarted(false);
            try {
                if (isSuccess) {
                    LogMgr.log(6, "001");
                    if (noLogin) {
                        LogMgr.log(6, "002");
                        synchronized (MfiOnline.this) {
                            MfiOnline.this.mStarted = 2;
                        }
                        LogMgr.performanceIn("API", "ISilentStartForMfiAdminEventCallback", "onSuccessNoLogin");
                        iSilentStartForMfiAdminEventCallback.onSuccessNoLogin();
                        LogMgr.performanceOut("API", "ISilentStartForMfiAdminEventCallback", "onSuccessNoLogin");
                    } else {
                        LogMgr.log(6, "003");
                        synchronized (MfiOnline.this) {
                            MfiOnline.this.mStarted = 3;
                        }
                        LogMgr.performanceIn("API", "ISilentStartForMfiAdminEventCallback", "onSuccess");
                        iSilentStartForMfiAdminEventCallback.onSuccess();
                        LogMgr.performanceOut("API", "ISilentStartForMfiAdminEventCallback", "onSuccess");
                    }
                } else {
                    LogMgr.log(6, "004");
                    synchronized (MfiOnline.this.mFelicaWrapper) {
                        synchronized (MfiOnline.this) {
                            MfiOnline.this.mFelicaWrapper.setMfiOnline(null);
                            MfiOnline.this.mFwsClientFacade.finish();
                        }
                    }
                    if (intent != null) {
                        LogMgr.log(6, "005");
                        LogMgr.exLogOnRequestActivity(MfiClientExternalLogConst.MficApi.MFI_CLIENT_SILENT_START);
                        iSilentStartForMfiAdminEventCallback.onRequestActivity(intent);
                    } else {
                        LogMgr.log(6, "006");
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_SILENT_START, errType);
                        iSilentStartForMfiAdminEventCallback.onError(errType, errMsg);
                    }
                }
            } catch (Exception e) {
                LogMgr.log(1, "800");
                LogMgr.printStackTrace(7, e);
                try {
                } catch (Exception e2) {
                    LogMgr.log(1, "%s", "801");
                    LogMgr.printStackTrace(7, e2);
                }
                synchronized (MfiOnline.this.mFelicaWrapper) {
                    synchronized (MfiOnline.this) {
                        MfiOnline.this.mStarted = i;
                        MfiOnline.this.mFelicaWrapper.setMfiOnline(null);
                        MfiOnline.this.mFwsClientFacade.finish();
                        try {
                            LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_SILENT_START, 200);
                            iSilentStartForMfiAdminEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                        } catch (RemoteException e3) {
                            LogMgr.log(1, "802");
                            LogMgr.printStackTrace(7, e3);
                        }
                    }
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onSilentStartFinished(boolean isSuccess, Intent intent, int errType, String errMsg) {
            ISilentStartEventCallback iSilentStartEventCallback;
            int i;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iSilentStartEventCallback = MfiOnline.this.mISilentStartEventCallback;
                MfiOnline.this.mISilentStartEventCallback = null;
                i = MfiOnline.this.mStarted;
            }
            OpenIdConnectActivity.setMfiStarted(false);
            try {
                if (!isSuccess) {
                    synchronized (MfiOnline.this.mFelicaWrapper) {
                        synchronized (MfiOnline.this) {
                            MfiOnline.this.mFelicaWrapper.setMfiOnline(null);
                            MfiOnline.this.mFwsClientFacade.finish();
                        }
                    }
                    if (intent != null) {
                        LogMgr.log(6, "002");
                        LogMgr.exLogOnRequestActivity(MfiClientExternalLogConst.MficApi.MFI_CLIENT_SILENT_START);
                        iSilentStartEventCallback.onRequestActivity(intent);
                    } else {
                        LogMgr.log(6, "003");
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_SILENT_START, errType);
                        iSilentStartEventCallback.onError(errType, errMsg);
                    }
                } else {
                    LogMgr.log(6, "001");
                    synchronized (MfiOnline.this) {
                        MfiOnline.this.mStarted = 1;
                    }
                    LogMgr.performanceIn("API", "ISilentStartEventCallback", "onSuccess");
                    iSilentStartEventCallback.onSuccess();
                    LogMgr.performanceOut("API", "ISilentStartEventCallback", "onSuccess");
                }
            } catch (Exception e) {
                LogMgr.log(1, "800");
                LogMgr.printStackTrace(7, e);
                try {
                } catch (Exception e2) {
                    LogMgr.log(1, "%s", "801");
                    LogMgr.printStackTrace(7, e2);
                }
                synchronized (MfiOnline.this.mFelicaWrapper) {
                    synchronized (MfiOnline.this) {
                        MfiOnline.this.mStarted = i;
                        MfiOnline.this.mFelicaWrapper.setMfiOnline(null);
                        MfiOnline.this.mFwsClientFacade.finish();
                        try {
                            LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_SILENT_START, 200);
                            iSilentStartEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                        } catch (RemoteException e3) {
                            LogMgr.log(1, "802");
                            LogMgr.printStackTrace(7, e3);
                        }
                    }
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onGetLinkageDataListFinished(boolean isSuccess, String[] linkageData, int errType, String errMsg) {
            ILinkageDataListEventCallback iLinkageDataListEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iLinkageDataListEventCallback = MfiOnline.this.mILinkageDataListEventCallback;
                MfiOnline.this.mILinkageDataListEventCallback = null;
            }
            try {
                if (isSuccess) {
                    LogMgr.performanceIn("API", "ILinkageDataListEventCallback", "onSuccess");
                    iLinkageDataListEventCallback.onSuccess(linkageData);
                    LogMgr.performanceOut("API", "ILinkageDataListEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_ADMIN_GET_LINKAGE_DATA_LIST, errType);
                    iLinkageDataListEventCallback.onError(errType, errMsg);
                }
            } catch (Exception e) {
                LogMgr.log(1, "800");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_ADMIN_GET_LINKAGE_DATA_LIST, 200);
                    iLinkageDataListEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "801");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onGetCardInfoListWithSpStatusFinished(boolean isSuccess, int errType, String errMsg) {
            IPipeEventCallback iPipeEventCallback;
            LogMgr.log(4, "000");
            if (!isSuccess) {
                LogMgr.log(4, "001");
                synchronized (MfiOnline.this) {
                    iPipeEventCallback = MfiOnline.this.mIPipeEventCallback;
                    MfiOnline.this.mIPipeEventCallback = null;
                }
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_INFO_LIST_WITH_SP_STATUS, errType);
                    iPipeEventCallback.onError(errType, errMsg);
                } catch (Exception e) {
                    LogMgr.log(1, "801");
                    LogMgr.printStackTrace(7, e);
                    try {
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_INFO_LIST_WITH_SP_STATUS, 200);
                        iPipeEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                    } catch (RemoteException e2) {
                        LogMgr.log(1, "802");
                        LogMgr.printStackTrace(7, e2);
                    }
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onGetCardListV3Finished(boolean isSuccess, CardInfo[] cardInfos, int errType, String errMsg) {
            IDataListEventCallback iDataListEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iDataListEventCallback = MfiOnline.this.mIDataListEventCallback;
                MfiOnline.this.mIDataListEventCallback = null;
            }
            try {
                if (isSuccess) {
                    List listDivide = MfiOnline.divide(Arrays.asList(cardInfos), new GetMfiControlInfoResponseJson(MfiControlInfoCache.getInstance().getInfoCache()).getTransmittableGetCardListSize());
                    int i = 0;
                    while (i < listDivide.size()) {
                        ArrayList arrayList = new ArrayList();
                        for (int i2 = 0; i2 < ((List) listDivide.get(i)).size(); i2++) {
                            arrayList.add(new CardInfoJson((CardInfo) ((List) listDivide.get(i)).get(i2)).toString());
                        }
                        iDataListEventCallback.onPart(arrayList, i != listDivide.size() - 1);
                        i++;
                    }
                    LogMgr.performanceIn("API", "IDataListEventCallback", "onSuccess", "length = " + cardInfos.length);
                    iDataListEventCallback.onFinished(cardInfos.length);
                    LogMgr.performanceOut("API", "IDataListEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_LIST, errType);
                    iDataListEventCallback.onError(errType, errMsg);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_LIST, 200);
                    iDataListEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onGetCardAdditionalInfoListV3Finished(boolean isSuccess, CardAdditionalInfo[] cardInfos, int errType, String errMsg) {
            IDataListEventCallback iDataListEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iDataListEventCallback = MfiOnline.this.mIDataListEventCallback;
                MfiOnline.this.mIDataListEventCallback = null;
            }
            try {
                if (isSuccess) {
                    List listDivide = MfiOnline.divide(Arrays.asList(cardInfos), new GetMfiControlInfoResponseJson(MfiControlInfoCache.getInstance().getInfoCache()).getTransmittableGetCardAdditionalInfoListSize());
                    int i = 0;
                    while (i < listDivide.size()) {
                        ArrayList arrayList = new ArrayList();
                        for (int i2 = 0; i2 < ((List) listDivide.get(i)).size(); i2++) {
                            arrayList.add(new CardAdditionalInfoJson((CardAdditionalInfo) ((List) listDivide.get(i)).get(i2)).toString());
                        }
                        iDataListEventCallback.onPart(arrayList, i != listDivide.size() - 1);
                        i++;
                    }
                    LogMgr.performanceIn("API", "IDataListEventCallback", "onSuccess", "length = " + cardInfos.length);
                    iDataListEventCallback.onFinished(cardInfos.length);
                    LogMgr.performanceOut("API", "IDataListEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_ADDITIONAL_INFO_LIST, errType);
                    iDataListEventCallback.onError(errType, errMsg);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_ADDITIONAL_INFO_LIST, 200);
                    iDataListEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onGetCardInfoListWithSpStatusV3Finished(boolean isSuccess, CardInfoWithSpStatus[] cardInfos, int errType, String errMsg) {
            IDataListEventCallback iDataListEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iDataListEventCallback = MfiOnline.this.mIDataListEventCallback;
                MfiOnline.this.mIDataListEventCallback = null;
            }
            try {
                if (isSuccess) {
                    List listDivide = MfiOnline.divide(Arrays.asList(cardInfos), new GetMfiControlInfoResponseJson(MfiControlInfoCache.getInstance().getInfoCache()).getTransmittableGetCardInfoListWithSpStatusSize());
                    int i = 0;
                    while (i < listDivide.size()) {
                        ArrayList arrayList = new ArrayList();
                        for (int i2 = 0; i2 < ((List) listDivide.get(i)).size(); i2++) {
                            arrayList.add(new CardInfoWithSpStatusJson((CardInfoWithSpStatus) ((List) listDivide.get(i)).get(i2)).toString());
                        }
                        iDataListEventCallback.onPart(arrayList, i != listDivide.size() - 1);
                        i++;
                    }
                    LogMgr.performanceIn("API", "IDataListEventCallback", "onSuccess", "length = " + cardInfos.length);
                    iDataListEventCallback.onFinished(cardInfos.length);
                    LogMgr.performanceOut("API", "IDataListEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_INFO_LIST_WITH_SP_STATUS, errType);
                    iDataListEventCallback.onError(errType, errMsg);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_INFO_LIST_WITH_SP_STATUS, 200);
                    iDataListEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onExecuteServerOperationFinished(boolean isSuccess, boolean requiredRetry, int errType, String errMsg) {
            ExecuteServerOperationEventCallback executeServerOperationEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                executeServerOperationEventCallback = MfiOnline.this.mExecuteServerOperationEventCallback;
                MfiOnline.this.mExecuteServerOperationEventCallback = null;
            }
            OpenIdConnectActivity.setMfiStarted(false);
            try {
                MfiOnline.this.finishWhenNotStarted();
                if (executeServerOperationEventCallback != null) {
                    if (isSuccess) {
                        LogMgr.performanceIn("API", "ExecuteServerOperationEventCallback", "onSuccess");
                        executeServerOperationEventCallback.onSuccess();
                        LogMgr.performanceOut("API", "ExecuteServerOperationEventCallback", "onSuccess");
                    } else if (requiredRetry) {
                        executeServerOperationEventCallback.onRetryRequired(errType, errMsg);
                    } else {
                        executeServerOperationEventCallback.onError(errType, errMsg);
                    }
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    executeServerOperationEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (Exception e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onProvisionServerOperationFinished(boolean isSuccess, int errType, String errMsg) {
            IServerOperationEventCallback iServerOperationEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iServerOperationEventCallback = MfiOnline.this.mIServerOperationEventCallback;
                MfiOnline.this.mIServerOperationEventCallback = null;
            }
            OpenIdConnectActivity.setMfiStarted(false);
            try {
                MfiOnline.this.finishWhenNotStarted();
                if (iServerOperationEventCallback != null) {
                    if (isSuccess) {
                        LogMgr.performanceIn("API", "IServerOperationEventCallback", "onSuccess");
                        iServerOperationEventCallback.onSuccess();
                        LogMgr.performanceOut("API", "IServerOperationEventCallback", "onSuccess");
                    } else {
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_PROVISION_SERVER_OPERATION, errType);
                        iServerOperationEventCallback.onError(errType, errMsg);
                    }
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_PROVISION_SERVER_OPERATION, 200);
                    iServerOperationEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (Exception e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onCheckLegacyCardExistenceFinished(boolean isSuccess, boolean exists, String cardInfoJson, int errType, String errMsg) {
            IUnsupportMfiService1CardExistEventCallback iUnsupportMfiService1CardExistEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iUnsupportMfiService1CardExistEventCallback = MfiOnline.this.mIUnsupportMfiService1CardExistEventCallback;
                MfiOnline.this.mIUnsupportMfiService1CardExistEventCallback = null;
            }
            OpenIdConnectActivity.setMfiStarted(false);
            try {
                MfiOnline.this.finishWhenNotStarted();
                if (iUnsupportMfiService1CardExistEventCallback != null) {
                    if (isSuccess) {
                        LogMgr.performanceIn("API", "UnsupportMfiService1CardExistEventCallback", "onSuccess");
                        iUnsupportMfiService1CardExistEventCallback.onSuccess(exists, cardInfoJson);
                        LogMgr.performanceOut("API", "UnsupportMfiService1CardExistEventCallback", "onSuccess");
                    } else {
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_EXIST_UNSUPPORT_MFI_SERVICE1_CARD, errType);
                        iUnsupportMfiService1CardExistEventCallback.onError(errType, errMsg);
                    }
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_EXIST_UNSUPPORT_MFI_SERVICE1_CARD, 200);
                    iUnsupportMfiService1CardExistEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (Exception e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onDeleteLegacyCardFinished(boolean isSuccess, int errType, String errMsg) {
            IUnsupportMfiService1CardDeleteEventCallback iUnsupportMfiService1CardDeleteEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iUnsupportMfiService1CardDeleteEventCallback = MfiOnline.this.mIUnsupportMfiService1CardDeleteEventCallback;
                MfiOnline.this.mIUnsupportMfiService1CardDeleteEventCallback = null;
            }
            OpenIdConnectActivity.setMfiStarted(false);
            try {
                MfiOnline.this.finishWhenNotStarted();
                if (iUnsupportMfiService1CardDeleteEventCallback != null) {
                    if (isSuccess) {
                        LogMgr.performanceIn("API", "mIUnsupportMfiService1CardDeleteEventCallback", "onSuccess");
                        iUnsupportMfiService1CardDeleteEventCallback.onSuccess();
                        LogMgr.performanceOut("API", "mIUnsupportMfiService1CardDeleteEventCallback", "onSuccess");
                    } else {
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_DELETE_UNSUPPORT_MFI_SERVICE1_CARD, errType);
                        iUnsupportMfiService1CardDeleteEventCallback.onError(errType, errMsg);
                    }
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_DELETE_UNSUPPORT_MFI_SERVICE1_CARD, 200);
                    iUnsupportMfiService1CardDeleteEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (Exception e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onGetCachedCardListFinished(boolean isSuccess, String[] cardInfos, int errType, String errMsg) {
            IDataListEventCallback iDataListEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iDataListEventCallback = MfiOnline.this.mICachedCardListEventCallback;
                MfiOnline.this.mICachedCardListEventCallback = null;
            }
            OpenIdConnectActivity.setMfiStarted(false);
            try {
                MfiOnline.this.finishWhenNotStarted();
                if (isSuccess) {
                    List listDivide = MfiOnline.divide(Arrays.asList(cardInfos), new GetMfiControlInfoResponseJson(MfiControlInfoCache.getInstance().getInfoCache()).getTransmittableGetCachedCardListSize());
                    int i = 0;
                    while (i < listDivide.size()) {
                        iDataListEventCallback.onPart((List) listDivide.get(i), i != listDivide.size() - 1);
                        i++;
                    }
                    LogMgr.performanceIn("API", "IDataListEventCallback", "onSuccess", "length = " + cardInfos.length);
                    iDataListEventCallback.onFinished(cardInfos.length);
                    LogMgr.performanceOut("API", "IDataListEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_CACHED_CARD_LIST, errType);
                    iDataListEventCallback.onError(errType, errMsg);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_CACHED_CARD_LIST, 200);
                    iDataListEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onEnableCachedCardFinished(boolean isSuccess, CachedCardInfo enableCard, CachedCardInfo disableCard, int errType, String errMsg) {
            ICachedCardEnableEventCallback iCachedCardEnableEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iCachedCardEnableEventCallback = MfiOnline.this.mICachedCardEnableEventCallback;
                MfiOnline.this.mICachedCardEnableEventCallback = null;
            }
            OpenIdConnectActivity.setMfiStarted(false);
            try {
                MfiOnline.this.finishWhenNotStarted();
                if (isSuccess) {
                    String string = enableCard != null ? new CachedCardInfoJson(enableCard).toString() : null;
                    String string2 = disableCard != null ? new CachedCardInfoJson(disableCard).toString() : null;
                    LogMgr.performanceIn("API", "ICachedCardEnableEventCallback", "onSuccess");
                    iCachedCardEnableEventCallback.onSuccess(string, string2);
                    LogMgr.performanceOut("API", "ICachedCardEnableEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CACHED_CARD_ENABLE, errType);
                    iCachedCardEnableEventCallback.onError(errType, errMsg);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CACHED_CARD_ENABLE, 200);
                    iCachedCardEnableEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onGetMfiTosDataFinished(boolean isSuccess, Map<String, String> mfiTosDataJsonMap, boolean isMfiTosAgreed, int errType, String errMsg) {
            IMfiTosDataGetEventCallback iMfiTosDataGetEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iMfiTosDataGetEventCallback = MfiOnline.this.mIMfiTosDataGetEventCallback;
                MfiOnline.this.mIMfiTosDataGetEventCallback = null;
            }
            OpenIdConnectActivity.setMfiStarted(false);
            try {
                MfiOnline.this.finishWhenNotStarted();
                if (iMfiTosDataGetEventCallback != null) {
                    if (isSuccess) {
                        LogMgr.performanceIn("API", "IMfiTosDataGetEventCallback", "onSuccess");
                        iMfiTosDataGetEventCallback.onSuccess(mfiTosDataJsonMap, isMfiTosAgreed);
                        LogMgr.performanceOut("API", "IMfiTosDataGetEventCallback", "onSuccess");
                    } else {
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_MFI_TOS_DATA, errType);
                        iMfiTosDataGetEventCallback.onError(errType, errMsg);
                    }
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_MFI_TOS_DATA, 200);
                    iMfiTosDataGetEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onGetGoogleTosFinished(boolean isSuccess, Intent intent, int errType, String errMsg) {
            IGoogleTosGetEventCallback iGoogleTosGetEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iGoogleTosGetEventCallback = MfiOnline.this.mIGoogleTosGetEventCallback;
                MfiOnline.this.mIGoogleTosGetEventCallback = null;
            }
            OpenIdConnectActivity.setMfiStarted(false);
            try {
                MfiOnline.this.finishWhenNotStarted();
                if (iGoogleTosGetEventCallback != null) {
                    if (isSuccess) {
                        LogMgr.performanceIn("API", "IGoogleTosGetEventCallback", "onSuccess");
                        iGoogleTosGetEventCallback.onSuccess(intent);
                        LogMgr.performanceOut("API", "IGoogleTosGetEventCallback", "onSuccess");
                    } else {
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_GOOGLE_TOS, errType);
                        iGoogleTosGetEventCallback.onError(errType, errMsg);
                    }
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_GOOGLE_TOS, 200);
                    iGoogleTosGetEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onGetRemainedCardsFinished(boolean isSuccess, CardInfo[] cardInfoListToDelete, CardInfo[] cardInfoListToPermanentDelete, int errType, String errMsg) {
            IRemainedCardsEventCallback iRemainedCardsEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iRemainedCardsEventCallback = MfiOnline.this.mIRemainedCardsEventCallback;
                MfiOnline.this.mIRemainedCardsEventCallback = null;
            }
            if (iRemainedCardsEventCallback != null) {
                try {
                    if (isSuccess) {
                        LogMgr.performanceIn("API", "IRemainedCardsEventCallback", "onSuccess");
                        iRemainedCardsEventCallback.onSuccess(convertCardInfoListToJsonStringList(cardInfoListToDelete), convertCardInfoListToJsonStringList(cardInfoListToPermanentDelete));
                        LogMgr.performanceOut("API", "IRemainedCardsEventCallback", "onSuccess");
                    } else {
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_ADMIN_GET_REMAINED_CARDS, errType);
                        iRemainedCardsEventCallback.onError(errType, errMsg);
                    }
                } catch (Exception e) {
                    LogMgr.log(1, "800");
                    LogMgr.printStackTrace(7, e);
                    try {
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_ADMIN_GET_REMAINED_CARDS, 200);
                        iRemainedCardsEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                    } catch (RemoteException e2) {
                        LogMgr.log(1, "801");
                        LogMgr.printStackTrace(7, e2);
                    }
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onUploadCardsToDeleteFinished(boolean isSuccess, int errType, String errMsg, String[] cidList) {
            ICardsUploadEventCallback iCardsUploadEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iCardsUploadEventCallback = MfiOnline.this.mICardsUploadToDeleteEventCallback;
                MfiOnline.this.mICardsUploadToDeleteEventCallback = null;
            }
            OpenIdConnectActivity.setMfiStarted(false);
            try {
                MfiOnline.this.finishWhenNotStarted();
                if (iCardsUploadEventCallback != null) {
                    if (isSuccess) {
                        LogMgr.log(7, "001");
                        LogMgr.performanceIn("API", "ICardsUploadEventCallback", "onSuccess");
                        iCardsUploadEventCallback.onSuccess();
                        LogMgr.performanceOut("API", "ICardsUploadEventCallback", "onSuccess");
                    } else {
                        LogMgr.log(7, "002");
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.REMAINED_CARDS_UPLOAD_CARDS_TO_DELETE, errType);
                        iCardsUploadEventCallback.onError(errType, errMsg, cidList);
                    }
                }
            } catch (Exception e) {
                LogMgr.log(1, "800");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.REMAINED_CARDS_UPLOAD_CARDS_TO_DELETE, 200);
                    iCardsUploadEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e), cidList);
                } catch (RemoteException e2) {
                    LogMgr.log(1, "801");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onUploadCardsToPermanentDeleteFinished(boolean isSuccess, int errType, String errMsg, String[] cidList) {
            ICardsUploadEventCallback iCardsUploadEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iCardsUploadEventCallback = MfiOnline.this.mICardsUploadToPermanentDeleteEventCallback;
                MfiOnline.this.mICardsUploadToPermanentDeleteEventCallback = null;
            }
            OpenIdConnectActivity.setMfiStarted(false);
            try {
                MfiOnline.this.finishWhenNotStarted();
                if (iCardsUploadEventCallback != null) {
                    if (isSuccess) {
                        LogMgr.log(7, "001");
                        LogMgr.performanceIn("API", "ICardsUploadEventCallback", "onSuccess");
                        iCardsUploadEventCallback.onSuccess();
                        LogMgr.performanceOut("API", "ICardsUploadEventCallback", "onSuccess");
                    } else {
                        LogMgr.log(7, "002");
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.REMAINED_CARDS_UPLOAD_CARDS_TO_PERMANENT_DELETE, errType);
                        iCardsUploadEventCallback.onError(errType, errMsg, cidList);
                    }
                }
            } catch (Exception e) {
                LogMgr.log(1, "800");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.REMAINED_CARDS_UPLOAD_CARDS_TO_PERMANENT_DELETE, 200);
                    iCardsUploadEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e), cidList);
                } catch (RemoteException e2) {
                    LogMgr.log(1, "801");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onClearMemoryFinished(boolean isSuccess, int errType, String errMsg) {
            IMemoryClearEventCallback iMemoryClearEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iMemoryClearEventCallback = MfiOnline.this.mIMemoryClearEventCallback;
                MfiOnline.this.mIMemoryClearEventCallback = null;
            }
            if (iMemoryClearEventCallback != null) {
                try {
                    if (isSuccess) {
                        LogMgr.performanceIn("API", "IMemoryClearEventCallback", "onSuccess");
                        iMemoryClearEventCallback.onSuccess();
                        LogMgr.performanceOut("API", "IMemoryClearEventCallback", "onSuccess");
                    } else {
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_ADMIN_CLEAR_MEMORY, errType);
                        iMemoryClearEventCallback.onError(errType, errMsg);
                    }
                } catch (Exception e) {
                    LogMgr.log(1, "800");
                    LogMgr.printStackTrace(7, e);
                    try {
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_ADMIN_CLEAR_MEMORY, 200);
                        iMemoryClearEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                    } catch (RemoteException e2) {
                        LogMgr.log(1, "801");
                        LogMgr.printStackTrace(7, e2);
                    }
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onGetCachedCardListWithIntegrityCheckFinished(boolean isSuccess, String[] cardInfos, int errType, String errMsg) {
            IDataListEventCallback iDataListEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iDataListEventCallback = MfiOnline.this.mICachedCardListWithIntegrityCheckEventCallback;
                MfiOnline.this.mICachedCardListWithIntegrityCheckEventCallback = null;
            }
            OpenIdConnectActivity.setMfiStarted(false);
            try {
                MfiOnline.this.finishWhenNotStarted();
                if (isSuccess) {
                    List listDivide = MfiOnline.divide(Arrays.asList(cardInfos), new GetMfiControlInfoResponseJson(MfiControlInfoCache.getInstance().getInfoCache()).getTransmittableGetCachedCardListSize());
                    int i = 0;
                    while (i < listDivide.size()) {
                        iDataListEventCallback.onPart((List) listDivide.get(i), i != listDivide.size() - 1);
                        i++;
                    }
                    LogMgr.performanceIn("API", "IDataListEventCallback", "onSuccess", "length = " + cardInfos.length);
                    iDataListEventCallback.onFinished(cardInfos.length);
                    LogMgr.performanceOut("API", "IDataListEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_CACHED_CARD_LIST_WITH_INTEGRITY_CHECK, errType);
                    iDataListEventCallback.onError(errType, errMsg);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_CACHED_CARD_LIST_WITH_INTEGRITY_CHECK, 200);
                    iDataListEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x0026 A[Catch: Exception -> 0x0022, TryCatch #2 {Exception -> 0x0022, blocks: (B:7:0x0018, B:11:0x0026, B:12:0x003c), top: B:28:0x0018 }] */
        /* JADX WARN: Removed duplicated region for block: B:12:0x003c A[Catch: Exception -> 0x0022, TRY_LEAVE, TryCatch #2 {Exception -> 0x0022, blocks: (B:7:0x0018, B:11:0x0026, B:12:0x003c), top: B:28:0x0018 }] */
        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onMigrateCardKeyFinished(boolean isSuccess, CardInfo cardInfo, int errType, String errMsg) {
            ICardKeyMigrateEventCallback iCardKeyMigrateEventCallback;
            String string;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iCardKeyMigrateEventCallback = MfiOnline.this.mICardKeyMigrateEventCallback;
                string = null;
                MfiOnline.this.mICardKeyMigrateEventCallback = null;
            }
            if (cardInfo != null) {
                try {
                    string = new CardInfoJson(cardInfo).toString();
                    if (!isSuccess) {
                        LogMgr.performanceIn("API", "ICardKeyMigrateEventCallback", "onSuccess");
                        iCardKeyMigrateEventCallback.onSuccess(string);
                        LogMgr.performanceOut("API", "ICardKeyMigrateEventCallback", "onSuccess");
                    } else {
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_MIGRATE_CARD_KEY, errType);
                        iCardKeyMigrateEventCallback.onError(errType, errMsg, string);
                    }
                } catch (Exception e) {
                    LogMgr.log(1, "800");
                    LogMgr.printStackTrace(7, e);
                    try {
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_MIGRATE_CARD_KEY, 200);
                        iCardKeyMigrateEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e), null);
                    } catch (RemoteException e2) {
                        LogMgr.log(1, "800");
                        LogMgr.printStackTrace(7, e2);
                    }
                }
            } else if (!isSuccess) {
            }
            LogMgr.log(4, "999");
        }

        private List<String> convertCardInfoListToJsonStringList(CardInfo[] cardInfoList) throws JSONException {
            ArrayList arrayList;
            LogMgr.log(6, "000");
            if (cardInfoList != null) {
                arrayList = new ArrayList();
                for (CardInfo cardInfo : cardInfoList) {
                    LogMgr.log(7, "001 cid = " + cardInfo.mCid);
                    arrayList.add(new CardInfoJson(cardInfo).toString());
                }
            } else {
                arrayList = null;
            }
            LogMgr.log(6, "999");
            return arrayList;
        }
    }

    boolean isLoginedAccount(String accountName) throws FelicaException, IllegalArgumentException {
        boolean zEqualsIgnoreCase;
        LogMgr.log(4, "000");
        if (accountName == null) {
            LogMgr.log(2, "700 accountName is null.");
            throw new IllegalArgumentException("The specified AccountName is null.");
        }
        if (accountName.isEmpty()) {
            LogMgr.log(2, "701 accountName is empty.");
            throw new IllegalArgumentException("The specified AccountName is empty.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    try {
                        this.mFelicaWrapper.checkMfiActivated();
                        this.mFelicaWrapper.checkAccessRight(14);
                        String accountNameCache = AccountCache.getInstance().getAccountNameCache();
                        if (accountNameCache == null) {
                            throw new MfiClientException(101, MfiClientException.TYPE_NO_ACCOUNT_INFO, null);
                        }
                        zEqualsIgnoreCase = accountNameCache.equalsIgnoreCase(accountName);
                    } catch (Exception e) {
                        LogMgr.log(2, "703 : " + e.getClass().getSimpleName() + ":" + e.getMessage());
                        LogMgr.printStackTrace(7, e);
                        throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                    }
                } catch (FelicaException e2) {
                    LogMgr.log(2, "702 : FelicaException: " + e2.getMessage());
                    throw e2;
                }
            }
        }
        LogMgr.log(4, "999");
        return zEqualsIgnoreCase;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> List<List<T>> divide(List<T> origin, int size) {
        if (origin == null || origin.isEmpty() || size <= 0) {
            return Collections.EMPTY_LIST;
        }
        int size2 = (origin.size() / size) + (origin.size() % size > 0 ? 1 : 0);
        ArrayList arrayList = new ArrayList(size2);
        for (int i = 0; i < size2; i++) {
            int i2 = i * size;
            arrayList.add(new ArrayList(origin.subList(i2, Math.min(i2 + size, origin.size()))));
        }
        return arrayList;
    }
}
