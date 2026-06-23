package com.felicanetworks.mfc.mfi;

import android.content.Intent;
import android.os.RemoteException;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.FelicaResultInfo;
import com.felicanetworks.mfc.mfi.MfiClientExternalLogConst;
import com.felicanetworks.mfc.mfi.fws.FwsClientFacade;
import com.felicanetworks.mfc.mfi.fws.FwsConst;
import com.felicanetworks.mfc.mfi.fws.json.GetMfiControlInfoResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.JwsObject;
import com.felicanetworks.mfc.mfi.fws.json.LinkageDataJson;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.ServiceTypeInfoUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
class MfiOnline {
    static final int NOT_STARTED = 0;
    static final int STARTED_BY_CARD_ADMIN = 2;
    static final int STARTED_BY_USER = 1;
    private static MfiOnline sInstance;
    private ExecuteServerOperationEventCallback mExecuteServerOperationEventCallback;
    private FelicaWrapper mFelicaWrapper;
    private ICachedCardEnableEventCallback mICachedCardEnableEventCallback;
    private IDataListEventCallback mICachedCardListEventCallback;
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
    private ICardListEventCallback mICardListEventCallback;
    private ICardReissuableDeleteEventCallback mICardReissuableDeleteEventCallback;
    private IDataListEventCallback mIDataListEventCallback;
    private IInitializedEventCallback mIInitializedEventCallback;
    private ILinkageDataListEventCallback mILinkageDataListEventCallback;
    private ILoginEventCallback mILoginEventCallback;
    private ILogoutEventCallback mILogoutEventCallback;
    private IPipeEventCallback mIPipeEventCallback;
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

    /* JADX WARN: Removed duplicated region for block: B:48:0x00db A[Catch: all -> 0x0106, TRY_ENTER, TRY_LEAVE, TryCatch #5 {, blocks: (B:21:0x0082, B:48:0x00db, B:49:0x00de, B:53:0x0105, B:52:0x00ed), top: B:70:0x0034, outer: #7, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void login(java.lang.String r15, java.lang.String r16, com.felicanetworks.mfc.mfi.ILoginEventCallback r17) throws com.felicanetworks.mfc.FelicaException, java.lang.IllegalArgumentException {
        /*
            Method dump skipped, instruction units count: 313
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.MfiOnline.login(java.lang.String, java.lang.String, com.felicanetworks.mfc.mfi.ILoginEventCallback):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x00fb A[Catch: all -> 0x0126, TRY_ENTER, TRY_LEAVE, TryCatch #1 {, blocks: (B:21:0x0082, B:48:0x00fb, B:49:0x00fe, B:53:0x0125, B:52:0x010d), top: B:68:0x0031, outer: #7, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void silentStart(java.lang.String r16, java.lang.String r17, int r18, com.felicanetworks.mfc.mfi.ISilentStartEventCallback r19) throws com.felicanetworks.mfc.FelicaException, java.lang.IllegalArgumentException {
        /*
            Method dump skipped, instruction units count: 354
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.MfiOnline.silentStart(java.lang.String, java.lang.String, int, com.felicanetworks.mfc.mfi.ISilentStartEventCallback):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:91:0x017f A[Catch: all -> 0x01aa, TRY_ENTER, TRY_LEAVE, TryCatch #5 {, blocks: (B:42:0x00e6, B:91:0x017f, B:92:0x0182, B:96:0x01a9, B:95:0x0191), top: B:107:0x0075, outer: #6, inners: #0 }] */
    /* JADX WARN: Type inference failed for: r0v17, types: [com.felicanetworks.mfc.mfi.FelicaWrapper] */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v10 */
    /* JADX WARN: Type inference failed for: r13v11 */
    /* JADX WARN: Type inference failed for: r13v12 */
    /* JADX WARN: Type inference failed for: r13v2 */
    /* JADX WARN: Type inference failed for: r13v21 */
    /* JADX WARN: Type inference failed for: r13v22 */
    /* JADX WARN: Type inference failed for: r13v23 */
    /* JADX WARN: Type inference failed for: r13v24 */
    /* JADX WARN: Type inference failed for: r13v25 */
    /* JADX WARN: Type inference failed for: r13v26 */
    /* JADX WARN: Type inference failed for: r13v27 */
    /* JADX WARN: Type inference failed for: r13v28 */
    /* JADX WARN: Type inference failed for: r13v29 */
    /* JADX WARN: Type inference failed for: r13v3 */
    /* JADX WARN: Type inference failed for: r13v30 */
    /* JADX WARN: Type inference failed for: r13v4 */
    /* JADX WARN: Type inference failed for: r13v5 */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Type inference failed for: r13v7 */
    /* JADX WARN: Type inference failed for: r13v8, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r13v9, types: [com.felicanetworks.mfc.mfi.MfiOnline] */
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
        To view partially-correct add '--show-bad-code' argument
    */
    void silentStartForMfiAdmin(java.lang.String r20, java.lang.String r21, boolean r22, int r23, int r24, com.felicanetworks.mfc.mfi.ISilentStartForMfiAdminEventCallback r25) throws com.felicanetworks.mfc.FelicaException, java.lang.IllegalArgumentException {
        /*
            Method dump skipped, instruction units count: 445
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.MfiOnline.silentStartForMfiAdmin(java.lang.String, java.lang.String, boolean, int, int, com.felicanetworks.mfc.mfi.ISilentStartForMfiAdminEventCallback):void");
    }

    void logout(boolean z, ILogoutEventCallback iLogoutEventCallback, boolean z2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s isForce ? %s", "000", Boolean.valueOf(z2));
        if (z2) {
            finish(z2);
            return;
        }
        if (iLogoutEventCallback == null) {
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
                    this.mILogoutEventCallback = iLogoutEventCallback;
                    this.mFwsClientFacade.logout(z, this.mFelicaWrapper.getWalletAppCallerInfo(), this.mFelicaWrapper.getWalletAppIdentifiableInfo(), !isStartedBy(1));
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
        To view partially-correct add '--show-bad-code' argument
    */
    void clearMfiAccount() throws com.felicanetworks.mfc.FelicaException {
        /*
            r11 = this;
            java.lang.String r0 = "%s"
            java.lang.String r1 = "000"
            r2 = 4
            com.felicanetworks.mfc.util.LogMgr.log(r2, r0, r1)
            com.felicanetworks.mfc.mfi.FelicaWrapper r0 = r11.mFelicaWrapper
            monitor-enter(r0)
            monitor-enter(r11)     // Catch: java.lang.Throwable -> L99
            r1 = 0
            r3 = 1
            r4 = 2
            r5 = 0
            com.felicanetworks.mfc.mfi.FelicaWrapper r6 = r11.mFelicaWrapper     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5d com.felicanetworks.mfc.FelicaException -> L81
            r6.checkMfiActivated()     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5d com.felicanetworks.mfc.FelicaException -> L81
            com.felicanetworks.mfc.mfi.FelicaWrapper r6 = r11.mFelicaWrapper     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5d com.felicanetworks.mfc.FelicaException -> L81
            r6.checkNotLoggedIn()     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5d com.felicanetworks.mfc.FelicaException -> L81
            boolean r6 = com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity.setMfiStarted(r3)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5d com.felicanetworks.mfc.FelicaException -> L81
            if (r6 == 0) goto L52
            com.felicanetworks.mfc.mfi.FelicaWrapper r6 = r11.mFelicaWrapper     // Catch: java.lang.Exception -> L4d com.felicanetworks.mfc.FelicaException -> L50 java.lang.Throwable -> L8f
            r7 = 9
            r6.checkAccessRight(r7)     // Catch: java.lang.Exception -> L4d com.felicanetworks.mfc.FelicaException -> L50 java.lang.Throwable -> L8f
            com.felicanetworks.mfc.mfi.AccountCache r6 = com.felicanetworks.mfc.mfi.AccountCache.getInstance()     // Catch: java.lang.Exception -> L4d com.felicanetworks.mfc.FelicaException -> L50 java.lang.Throwable -> L8f
            r6.clearLoginData()     // Catch: java.lang.Exception -> L4d com.felicanetworks.mfc.FelicaException -> L50 java.lang.Throwable -> L8f
            com.felicanetworks.mfc.mfi.PreAccountCache r6 = com.felicanetworks.mfc.mfi.PreAccountCache.getInstance()     // Catch: java.lang.Exception -> L4d com.felicanetworks.mfc.FelicaException -> L50 java.lang.Throwable -> L8f
            r6.clearLoginData()     // Catch: java.lang.Exception -> L4d com.felicanetworks.mfc.FelicaException -> L50 java.lang.Throwable -> L8f
            com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity.setMfiStarted(r5)     // Catch: java.lang.Throwable -> L96
            monitor-exit(r11)     // Catch: java.lang.Throwable -> L96
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L99
            com.felicanetworks.mfc.mfi.FelicaAdapter r0 = com.felicanetworks.mfc.mfi.FelicaAdapter.getInstance()
            java.lang.String r1 = "account_clear"
            java.lang.String r3 = "dump"
            com.felicanetworks.mfc.mfi.LogSender.send(r0, r1, r3)
            java.lang.String r0 = "%s"
            java.lang.String r1 = "999"
            com.felicanetworks.mfc.util.LogMgr.log(r2, r0, r1)
            return
        L4d:
            r2 = move-exception
            r6 = 1
            goto L5f
        L50:
            r1 = move-exception
            goto L83
        L52:
            com.felicanetworks.mfc.mfi.MfiClientException r2 = new com.felicanetworks.mfc.mfi.MfiClientException     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5d com.felicanetworks.mfc.FelicaException -> L81
            r6 = 39
            r2.<init>(r4, r6, r1)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5d com.felicanetworks.mfc.FelicaException -> L81
            throw r2     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5d com.felicanetworks.mfc.FelicaException -> L81
        L5a:
            r1 = move-exception
            r3 = 0
            goto L90
        L5d:
            r2 = move-exception
            r6 = 0
        L5f:
            java.lang.String r7 = "%s : %s:%s"
            java.lang.String r8 = "701"
            java.lang.Class r9 = r2.getClass()     // Catch: java.lang.Throwable -> L7e
            java.lang.String r9 = r9.getSimpleName()     // Catch: java.lang.Throwable -> L7e
            java.lang.String r10 = r2.getMessage()     // Catch: java.lang.Throwable -> L7e
            com.felicanetworks.mfc.util.LogMgr.log(r4, r7, r8, r9, r10)     // Catch: java.lang.Throwable -> L7e
            r4 = 7
            com.felicanetworks.mfc.util.LogMgr.printStackTrace(r4, r2)     // Catch: java.lang.Throwable -> L7e
            com.felicanetworks.mfc.mfi.MfiClientException r2 = new com.felicanetworks.mfc.mfi.MfiClientException     // Catch: java.lang.Throwable -> L7e
            r4 = 150(0x96, float:2.1E-43)
            r2.<init>(r3, r4, r1)     // Catch: java.lang.Throwable -> L7e
            throw r2     // Catch: java.lang.Throwable -> L7e
        L7e:
            r1 = move-exception
            r3 = r6
            goto L90
        L81:
            r1 = move-exception
            r3 = 0
        L83:
            java.lang.String r2 = "%s : FelicaException:%s"
            java.lang.String r6 = "700"
            java.lang.String r7 = r1.getMessage()     // Catch: java.lang.Throwable -> L8f
            com.felicanetworks.mfc.util.LogMgr.log(r4, r2, r6, r7)     // Catch: java.lang.Throwable -> L8f
            throw r1     // Catch: java.lang.Throwable -> L8f
        L8f:
            r1 = move-exception
        L90:
            if (r3 == 0) goto L95
            com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity.setMfiStarted(r5)     // Catch: java.lang.Throwable -> L96
        L95:
            throw r1     // Catch: java.lang.Throwable -> L96
        L96:
            r1 = move-exception
            monitor-exit(r11)     // Catch: java.lang.Throwable -> L96
            throw r1     // Catch: java.lang.Throwable -> L99
        L99:
            r1 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L99
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.MfiOnline.clearMfiAccount():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x011f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    int identifyService() throws com.felicanetworks.mfc.FelicaException {
        /*
            Method dump skipped, instruction units count: 373
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.MfiOnline.identifyService():int");
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
                int i = MfiClientException.TYPE_GET_SE_INFORMATION_FAILED;
                int i2 = 3;
                String str = null;
                try {
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
                                if (type != 6) {
                                    if (type == 8) {
                                        str = FelicaException.NFC_RW_USED_MESSAGE;
                                        i = 8;
                                    } else if (type == 55) {
                                        i = 55;
                                        i2 = 8;
                                    }
                                    i2 = 1;
                                } else {
                                    i = 6;
                                }
                                throw new MfiClientException(i2, i, str);
                            }
                        } catch (Exception e2) {
                            LogMgr.log(2, "703 : " + e2.getClass().getSimpleName() + ":" + e2.getMessage());
                            LogMgr.printStackTrace(7, e2);
                            throw new MfiClientException(1, MfiClientException.TYPE_GET_SE_INFORMATION_FAILED, null);
                        }
                    } catch (GpException e3) {
                        LogMgr.log(2, "702 : " + e3.getClass().getSimpleName() + ":" + e3.getMessage());
                        LogMgr.printStackTrace(7, e3);
                        if (e3.getType() != 225) {
                            i2 = 1;
                        } else {
                            i = MfiClientException.TYPE_SE_ACCESS_ERROR;
                        }
                        throw new MfiClientException(i2, i, null);
                    } catch (InterruptedException e4) {
                        LogMgr.log(2, "701 : " + e4.getClass().getSimpleName() + ":" + e4.getMessage());
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

    void getCardList(ICardListEventCallback iCardListEventCallback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        if (iCardListEventCallback == null) {
            LogMgr.log(2, "%s callback is null.", "701");
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
                        JSONArray walletAppCertHashList = felicaWrapper.canAccessRight(11) ^ true ? felicaWrapper.getWalletAppCertHashList() : null;
                        String walletAppId = this.mFelicaWrapper.getWalletAppId();
                        this.mFwsClientFacade.checkNotRunningTask();
                        this.mICardListEventCallback = iCardListEventCallback;
                        this.mFwsClientFacade.getCardList(walletAppCertHashList, walletAppId);
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

    /* JADX WARN: Removed duplicated region for block: B:38:0x00cb A[Catch: all -> 0x00dd, TRY_ENTER, TryCatch #8 {, blocks: (B:14:0x005c, B:38:0x00cb, B:40:0x00d2, B:41:0x00dc), top: B:54:0x0010, outer: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void getCachedCardList(com.felicanetworks.mfc.mfi.IDataListEventCallback r11) throws com.felicanetworks.mfc.FelicaException, java.lang.IllegalArgumentException {
        /*
            Method dump skipped, instruction units count: 240
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.MfiOnline.getCachedCardList(com.felicanetworks.mfc.mfi.IDataListEventCallback):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00d7 A[Catch: all -> 0x00e9, TRY_ENTER, TryCatch #5 {, blocks: (B:16:0x0068, B:40:0x00d7, B:42:0x00de, B:43:0x00e8), top: B:60:0x001b, outer: #8 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void enableCachedCard(java.lang.String r11, com.felicanetworks.mfc.mfi.ICachedCardEnableEventCallback r12) throws com.felicanetworks.mfc.FelicaException, java.lang.IllegalArgumentException {
        /*
            Method dump skipped, instruction units count: 282
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.MfiOnline.enableCachedCard(java.lang.String, com.felicanetworks.mfc.mfi.ICachedCardEnableEventCallback):void");
    }

    void getCardListV2(IPipeEventCallback iPipeEventCallback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        if (iPipeEventCallback == null) {
            LogMgr.log(2, "%s callback is null.", "701");
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
                        JSONArray walletAppCertHashList = felicaWrapper.canAccessRight(11) ^ true ? felicaWrapper.getWalletAppCertHashList() : null;
                        String walletAppId = this.mFelicaWrapper.getWalletAppId();
                        this.mFwsClientFacade.checkNotRunningTask();
                        this.mIPipeEventCallback = iPipeEventCallback;
                        this.mFwsClientFacade.getCardListWithPipe(walletAppCertHashList, walletAppId, iPipeEventCallback);
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

    void getCardAdditionalInfoList(ICardAdditionalInfoListEventCallback iCardAdditionalInfoListEventCallback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        if (iCardAdditionalInfoListEventCallback == null) {
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
                    JSONArray walletAppCertHashList = felicaWrapper.canAccessRight(11) ^ true ? felicaWrapper.getWalletAppCertHashList() : null;
                    this.mFwsClientFacade.checkNotRunningTask();
                    this.mICardAdditionalInfoListEventCallback = iCardAdditionalInfoListEventCallback;
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

    void getCardAdditionalInfoListWithCidList(String[] strArr, IPipeEventCallback iPipeEventCallback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (strArr == null) {
            LogMgr.log(2, "700 cidList is null");
            throw new IllegalArgumentException("The specified cidList is null or invalid.");
        }
        if (strArr.length == 0) {
            LogMgr.log(2, "701 cidList length = 0");
            throw new IllegalArgumentException("The specified cidList is null or invalid.");
        }
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i] == null) {
                LogMgr.log(2, "702 cid is null");
                throw new IllegalArgumentException("The specified cidList is null or invalid.");
            }
            if (!strArr[i].matches(MfiClientConst.REGEX_ALPHANUMERIC)) {
                LogMgr.log(2, "703 cid involves invalid character.");
                throw new IllegalArgumentException("The specified cidList is null or invalid.");
            }
            if (strArr[i].length() != 63) {
                LogMgr.log(2, "704 cid length is invalid.");
                throw new IllegalArgumentException("The specified cidList is null or invalid.");
            }
        }
        if (iPipeEventCallback == null) {
            LogMgr.log(2, "705 callback is null.");
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
                    JSONArray walletAppCertHashList = felicaWrapper.canAccessRight(11) ^ true ? felicaWrapper.getWalletAppCertHashList() : null;
                    this.mFwsClientFacade.checkNotRunningTask();
                    this.mIPipeEventCallback = iPipeEventCallback;
                    this.mFwsClientFacade.getCardAdditionalInfoListWithPipe(strArr, walletAppCertHashList, iPipeEventCallback);
                } catch (FelicaException e) {
                    LogMgr.log(2, "706 : FelicaException:" + e.getMessage());
                    throw e;
                } catch (Exception e2) {
                    LogMgr.log(2, "707 : " + e2.getClass().getSimpleName() + ":" + e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            }
        }
        LogMgr.log(4, "999");
    }

    void getCardInfoListWithSpStatus(String str, IPipeEventCallback iPipeEventCallback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (str == null) {
            LogMgr.log(2, "700 serviceId is null.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (!str.matches(MfiClientConst.REGEX_ALPHANUMERIC)) {
            LogMgr.log(2, "701 serviceId involves invalid character.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (str.length() != 8) {
            LogMgr.log(2, "702 serviceId length is invalid.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (iPipeEventCallback == null) {
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
                        JSONArray walletAppCertHashList = this.mFelicaWrapper.canAccessRight(11) ^ true ? this.mFelicaWrapper.getWalletAppCertHashList() : null;
                        String walletAppId = this.mFelicaWrapper.getWalletAppId();
                        this.mFwsClientFacade.checkNotRunningTask();
                        this.mIPipeEventCallback = iPipeEventCallback;
                        this.mFwsClientFacade.getCardInfoListWithSpStatus(str, walletAppCertHashList, walletAppId, iPipeEventCallback);
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
    }

    void getCardListV3(IDataListEventCallback iDataListEventCallback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (iDataListEventCallback == null) {
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
                    JSONArray walletAppCertHashList = felicaWrapper.canAccessRight(11) ^ true ? felicaWrapper.getWalletAppCertHashList() : null;
                    String walletAppId = this.mFelicaWrapper.getWalletAppId();
                    this.mFwsClientFacade.checkNotRunningTask();
                    this.mIDataListEventCallback = iDataListEventCallback;
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

    void getCardAdditionalInfoListV3(String[] strArr, IDataListEventCallback iDataListEventCallback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (strArr == null) {
            LogMgr.log(2, "700 cidList is null");
            throw new IllegalArgumentException("The specified cidList is null or invalid.");
        }
        if (strArr.length == 0) {
            LogMgr.log(2, "701 cidList length = 0");
            throw new IllegalArgumentException("The specified cidList is null or invalid.");
        }
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i] == null) {
                LogMgr.log(2, "702 cid is null");
                throw new IllegalArgumentException("The specified cidList is null or invalid.");
            }
            if (!strArr[i].matches(MfiClientConst.REGEX_ALPHANUMERIC)) {
                LogMgr.log(2, "703 cid involves invalid character.");
                throw new IllegalArgumentException("The specified cidList is null or invalid.");
            }
            if (strArr[i].length() != 63) {
                LogMgr.log(2, "704 cid length is invalid.");
                throw new IllegalArgumentException("The specified cidList is null or invalid.");
            }
        }
        if (iDataListEventCallback == null) {
            LogMgr.log(2, "705 callback is null.");
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
                    JSONArray walletAppCertHashList = felicaWrapper.canAccessRight(11) ^ true ? felicaWrapper.getWalletAppCertHashList() : null;
                    this.mFwsClientFacade.checkNotRunningTask();
                    this.mIDataListEventCallback = iDataListEventCallback;
                    this.mFwsClientFacade.getCardAdditionalInfoListV3(strArr, walletAppCertHashList);
                } catch (FelicaException e) {
                    LogMgr.log(2, "706 : FelicaException:" + e.getMessage());
                    throw e;
                } catch (Exception e2) {
                    LogMgr.log(2, "707 : " + e2.getClass().getSimpleName() + ":" + e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            }
        }
        LogMgr.log(4, "999");
    }

    void getCardInfoListWithSpStatusV3(String str, IDataListEventCallback iDataListEventCallback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (str == null) {
            LogMgr.log(2, "700 serviceId is null.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (!str.matches(MfiClientConst.REGEX_ALPHANUMERIC)) {
            LogMgr.log(2, "701 serviceId involves invalid character.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (str.length() != 8) {
            LogMgr.log(2, "702 serviceId length is invalid.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (iDataListEventCallback == null) {
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
                        JSONArray walletAppCertHashList = this.mFelicaWrapper.canAccessRight(11) ^ true ? this.mFelicaWrapper.getWalletAppCertHashList() : null;
                        String walletAppId = this.mFelicaWrapper.getWalletAppId();
                        this.mFwsClientFacade.checkNotRunningTask();
                        this.mIDataListEventCallback = iDataListEventCallback;
                        this.mFwsClientFacade.getCardInfoListWithSpStatusV3(str, walletAppCertHashList, walletAppId);
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
    }

    void issueCard(String str, ICardIssueEventCallback iCardIssueEventCallback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        if (str == null) {
            LogMgr.log(2, "%s linkageData is null.", "700");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (str.isEmpty()) {
            LogMgr.log(2, "%s linkageData is empty.", "701");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (iCardIssueEventCallback == null) {
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
                        this.mICardIssueEventCallback = iCardIssueEventCallback;
                        this.mICardIssueV2EventCallback = null;
                        try {
                            LinkageDataJson linkageDataJson = new LinkageDataJson(new JwsObject(str).getPayload());
                            String serviceType = linkageDataJson.getServiceType();
                            boolean zEquals = FwsConst.ActionType.MIGRATE_CARD.equals(linkageDataJson.getActionType());
                            String mFCVersion = FelicaWrapper.getMFCVersion(FelicaAdapter.getInstance());
                            if (zEquals) {
                                LogMgr.log(1, "800 : Otp not set on migrate card .");
                                throw new MfiClientException(100, MfiClientException.TYPE_NOT_SUPPORTED, null);
                            }
                            ServiceTypeInfoUtil.issueCardSimpleServiceTypeCheck(serviceType, mFCVersion);
                            this.mFwsClientFacade.issueCard(str, null);
                        } catch (FelicaException e) {
                            LogMgr.log(2, "703 : FelicaException:" + e.getMessage());
                            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                        } catch (JSONException e2) {
                            LogMgr.log(2, "704 : linkageData is illegal.");
                            LogMgr.printStackTrace(2, e2);
                            throw new MfiClientException(100, MfiClientException.TYPE_ILLEGAL_LINKAGE_DATA, null);
                        }
                    } catch (FelicaException e3) {
                        LogMgr.log(2, "%s : FelicaException:%s", "705", e3.getMessage());
                        throw e3;
                    }
                } catch (Exception e4) {
                    LogMgr.log(2, "%s : %s:%s", "706", e4.getClass().getSimpleName(), e4.getMessage());
                    LogMgr.printStackTrace(7, e4);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            }
        }
        LogMgr.log(4, "%s", "999");
    }

    void issueCardV2(String str, ICardIssueV2EventCallback iCardIssueV2EventCallback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (str == null) {
            LogMgr.log(2, "700 linkageData is null.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (str.isEmpty()) {
            LogMgr.log(2, "701 linkageData is empty.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (iCardIssueV2EventCallback == null) {
            LogMgr.log(2, "702 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        issueCardV2Inner(str, null, iCardIssueV2EventCallback);
        LogMgr.log(4, "999");
    }

    void issueCardWithOtp(String str, String str2, ICardIssueV2EventCallback iCardIssueV2EventCallback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (str == null) {
            LogMgr.log(2, "700 linkageData is null.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (str.isEmpty()) {
            LogMgr.log(2, "701 linkageData is empty.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (str2 == null) {
            LogMgr.log(2, "702 otp is null.");
            throw new IllegalArgumentException("The specified Otp is null or invalid.");
        }
        if (str2.isEmpty()) {
            LogMgr.log(2, "703 otp is empty.");
            throw new IllegalArgumentException("The specified Otp is null or invalid.");
        }
        if (iCardIssueV2EventCallback == null) {
            LogMgr.log(2, "704 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        issueCardV2Inner(str, str2, iCardIssueV2EventCallback);
        LogMgr.log(4, "999");
    }

    private void issueCardV2Inner(String str, String str2, ICardIssueV2EventCallback iCardIssueV2EventCallback) throws FelicaException {
        LogMgr.log(4, "000");
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    try {
                        this.mFelicaWrapper.checkMfiActivated();
                        checkStartedBy(1);
                        FelicaAdapter.getInstance().getFelicaWrapper().checkAccessRight(7);
                        this.mFwsClientFacade.checkNotRunningTask();
                        this.mICardIssueV2EventCallback = iCardIssueV2EventCallback;
                        this.mICardIssueEventCallback = null;
                        try {
                            LinkageDataJson linkageDataJson = new LinkageDataJson(new JwsObject(str).getPayload());
                            String serviceType = linkageDataJson.getServiceType();
                            boolean zEquals = FwsConst.ActionType.MIGRATE_CARD.equals(linkageDataJson.getActionType());
                            String mFCVersion = FelicaWrapper.getMFCVersion(FelicaAdapter.getInstance());
                            if (str2 != null) {
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
                            this.mFwsClientFacade.issueCard(str, str2);
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

    void enable(CardInfo cardInfo, ICardEnableEventCallback iCardEnableEventCallback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        if (cardInfo == null) {
            LogMgr.log(2, "%s cardInfo is null.", "700");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
        if (iCardEnableEventCallback == null) {
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
                        this.mICardEnableEventCallback = iCardEnableEventCallback;
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

    void enableV2(String str, ICardEnableV2EventCallback iCardEnableV2EventCallback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (str == null) {
            LogMgr.log(2, "700 cardInfoJson is null.");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
        try {
            CardInfo cardInfo = new CardInfoJson(str).getCardInfo();
            if (iCardEnableV2EventCallback == null) {
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
                            this.mICardEnableV2EventCallback = iCardEnableV2EventCallback;
                            this.mICardEnableEventCallback = null;
                            this.mFwsClientFacade.enableCard(cardInfo);
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
        } catch (JSONException e3) {
            LogMgr.log(2, " 701 cannot create CardInfo.");
            LogMgr.printStackTrace(2, e3);
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
    }

    FelicaResultInfo disable(CardInfo cardInfo, ICardDisableEventCallback iCardDisableEventCallback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        if (cardInfo == null) {
            LogMgr.log(2, "%s cardInfo is null.", "700");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
        if (iCardDisableEventCallback == null) {
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
                    this.mICardDisableEventCallback = iCardDisableEventCallback;
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

    FelicaResultInfo disableV2(String str, ICardDisableV2EventCallback iCardDisableV2EventCallback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (str == null) {
            LogMgr.log(2, "700 cardInfoJson is null.");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
        try {
            CardInfo cardInfo = new CardInfoJson(str).getCardInfo();
            if (iCardDisableV2EventCallback == null) {
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
                        this.mICardDisableV2EventCallback = iCardDisableV2EventCallback;
                        this.mICardDisableEventCallback = null;
                        this.mFwsClientFacade.disableCard(cardInfo.mCid);
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
            return new FelicaResultInfo();
        } catch (JSONException e3) {
            LogMgr.log(2, " 701 cannot create CardInfo.");
            LogMgr.printStackTrace(2, e3);
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
    }

    void saveDelete(String str, String str2, ICardReissuableDeleteEventCallback iCardReissuableDeleteEventCallback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (str == null) {
            LogMgr.log(2, "700 cardInfoJson is null.");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
        try {
            CardInfo cardInfo = new CardInfoJson(str).getCardInfo();
            if (str2 == null) {
                LogMgr.log(2, "702 linkageData is null.");
                throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
            }
            if (str2.isEmpty()) {
                LogMgr.log(2, "703 linkageData is empty.");
                throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
            }
            if (iCardReissuableDeleteEventCallback == null) {
                LogMgr.log(2, "704 callback is null.");
                throw new IllegalArgumentException("The specified Callback is null.");
            }
            try {
                if (!cardInfo.mCid.equals(new LinkageDataJson(new JwsObject(str2).getPayload()).getCid())) {
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
                            this.mICardReissuableDeleteEventCallback = iCardReissuableDeleteEventCallback;
                            this.mFwsClientFacade.saveDeleteCard(cardInfo.mCid, str2);
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

    void delete(CardInfo cardInfo, String str, ICardDeleteEventCallback iCardDeleteEventCallback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        if (cardInfo == null) {
            LogMgr.log(2, "%s cardInfo is null.", "700");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
        if (str == null) {
            LogMgr.log(2, "%s linkageData is null.", "701");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (str.isEmpty()) {
            LogMgr.log(2, "%s linkageData is empty.", "702");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (iCardDeleteEventCallback == null) {
            LogMgr.log(2, "%s callback is null.", "703");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        try {
            if (!cardInfo.mCid.equals(new LinkageDataJson(new JwsObject(str).getPayload()).getCid())) {
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
                        this.mICardDeleteEventCallback = iCardDeleteEventCallback;
                        this.mICardDeleteV2EventCallback = null;
                        this.mFwsClientFacade.deleteCard(cardInfo.mCid, str);
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

    void deleteV2(String str, String str2, ICardDeleteV2EventCallback iCardDeleteV2EventCallback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (str == null) {
            LogMgr.log(2, "700 cardInfoJson is null.");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
        try {
            CardInfo cardInfo = new CardInfoJson(str).getCardInfo();
            if (str2 == null) {
                LogMgr.log(2, "702 linkageData is null.");
                throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
            }
            if (str2.isEmpty()) {
                LogMgr.log(2, "703 linkageData is empty.");
                throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
            }
            if (iCardDeleteV2EventCallback == null) {
                LogMgr.log(2, "704 callback is null.");
                throw new IllegalArgumentException("The specified Callback is null.");
            }
            try {
                if (!cardInfo.mCid.equals(new LinkageDataJson(new JwsObject(str2).getPayload()).getCid())) {
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
                            this.mICardDeleteV2EventCallback = iCardDeleteV2EventCallback;
                            this.mICardDeleteEventCallback = null;
                            this.mFwsClientFacade.deleteCard(cardInfo.mCid, str2);
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

    void access(String str, String str2, ICardAccessEventCallback iCardAccessEventCallback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        if (str == null) {
            LogMgr.log(2, "%s cardInfoJson is null.", "700");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_CARD);
        }
        try {
            CardInfo cardInfo = new CardInfoJson(str).getCardInfo();
            if (str2 == null) {
                LogMgr.log(2, "%s linkageData is null.", "702");
                throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
            }
            if (str2.isEmpty()) {
                LogMgr.log(2, "%s linkageData is empty.", "703");
                throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
            }
            if (iCardAccessEventCallback == null) {
                LogMgr.log(2, "%s callback is null.", "704");
                throw new IllegalArgumentException("The specified Callback is null.");
            }
            try {
                if (!cardInfo.mCid.equals(new LinkageDataJson(new JwsObject(str2).getPayload()).getCid())) {
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
                            this.mICardAccessEventCallback = iCardAccessEventCallback;
                            this.mFwsClientFacade.accessCard(cardInfo.mCid, str2);
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

    void initialize(String str, IInitializedEventCallback iInitializedEventCallback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (str == null) {
            LogMgr.log(2, "701 : linkageData is null.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (str.isEmpty()) {
            LogMgr.log(2, "702 : linkageData is empty.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (iInitializedEventCallback == null) {
            LogMgr.log(2, "703 : callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    this.mFelicaWrapper.checkMfiActivated();
                    checkStartedBy(2);
                    FelicaAdapter.getInstance().getFelicaWrapper().checkAccessRight(13);
                    this.mFwsClientFacade.checkNotRunningTask();
                    this.mIInitializedEventCallback = iInitializedEventCallback;
                    this.mFwsClientFacade.initialize(str);
                } catch (FelicaException e) {
                    LogMgr.log(2, "704 : FelicaException:" + e.getMessage());
                    throw e;
                } catch (Exception e2) {
                    LogMgr.log(2, "705 : Exception" + e2.getClass().getSimpleName(), e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            }
        }
        LogMgr.log(4, "999");
    }

    void getLinkageDataList(int i, String[] strArr, ILinkageDataListEventCallback iLinkageDataListEventCallback) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (i != 1 && i != 2 && i != 3) {
            LogMgr.log(2, "700 : actionType is invalid:");
            throw new IllegalArgumentException("The specified actionType is null or invalid.");
        }
        if (i == 1 || i == 2) {
            if (strArr == null || strArr.length == 0) {
                LogMgr.log(2, "701 : cidList is null or invalid.");
                throw new IllegalArgumentException("The specified cidList is null or invalid.");
            }
            for (String str : strArr) {
                if (str == null) {
                    LogMgr.log(2, "702 cid is null");
                    throw new IllegalArgumentException("The specified cidList is null or invalid.");
                }
                if (!str.matches(MfiClientConst.REGEX_ALPHANUMERIC)) {
                    LogMgr.log(2, "703 cid involves invalid character.");
                    throw new IllegalArgumentException("The specified cidList is null or invalid.");
                }
                if (str.length() != 63) {
                    LogMgr.log(2, "704 cid length is invalid.");
                    throw new IllegalArgumentException("The specified cidList is null or invalid.");
                }
            }
        }
        if (iLinkageDataListEventCallback == null) {
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
                        if (i == 1 || i == 2) {
                            felicaWrapper.checkAccessRight(7);
                        }
                        if (i == 3) {
                            felicaWrapper.checkAccessRight(13);
                        }
                        this.mFwsClientFacade.checkNotRunningTask();
                        this.mILinkageDataListEventCallback = iLinkageDataListEventCallback;
                        this.mFwsClientFacade.getLinkageDataList(i, strArr);
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

    /* JADX WARN: Removed duplicated region for block: B:44:0x00c3 A[Catch: all -> 0x00d5, TRY_ENTER, TryCatch #5 {, blocks: (B:20:0x0054, B:44:0x00c3, B:46:0x00ca, B:47:0x00d4), top: B:63:0x0020, outer: #6 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void executeServerOperation(java.lang.String r10, java.lang.String r11, com.felicanetworks.mfc.mfi.ExecuteServerOperationEventCallback r12) throws com.felicanetworks.mfc.FelicaException, java.lang.IllegalArgumentException {
        /*
            Method dump skipped, instruction units count: 258
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.MfiOnline.executeServerOperation(java.lang.String, java.lang.String, com.felicanetworks.mfc.mfi.ExecuteServerOperationEventCallback):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00b3 A[Catch: all -> 0x00c5, TRY_ENTER, TryCatch #5 {, blocks: (B:14:0x0044, B:38:0x00b3, B:40:0x00ba, B:41:0x00c4), top: B:53:0x0010, outer: #6 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void provisionServerOperation(com.felicanetworks.mfc.mfi.IServerOperationEventCallback r10) throws com.felicanetworks.mfc.FelicaException, java.lang.IllegalArgumentException {
        /*
            Method dump skipped, instruction units count: 216
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.MfiOnline.provisionServerOperation(com.felicanetworks.mfc.mfi.IServerOperationEventCallback):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x00e5 A[Catch: all -> 0x00f7, TRY_ENTER, TryCatch #5 {, blocks: (B:23:0x0076, B:47:0x00e5, B:49:0x00ec, B:50:0x00f6), top: B:68:0x0022, outer: #6 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void existUnsupportMfiService1Card(java.lang.String r11, com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardExistEventCallback r12) throws com.felicanetworks.mfc.FelicaException, java.lang.IllegalArgumentException {
        /*
            Method dump skipped, instruction units count: 305
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.MfiOnline.existUnsupportMfiService1Card(java.lang.String, com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardExistEventCallback):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00d9 A[Catch: all -> 0x00eb, TRY_ENTER, TryCatch #5 {, blocks: (B:22:0x006c, B:46:0x00d9, B:48:0x00e0, B:49:0x00ea), top: B:72:0x002b, outer: #9 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void deleteUnsupportMfiService1Card(java.lang.String r11, com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardDeleteEventCallback r12) throws com.felicanetworks.mfc.FelicaException, java.lang.IllegalArgumentException {
        /*
            Method dump skipped, instruction units count: 357
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.MfiOnline.deleteUnsupportMfiService1Card(java.lang.String, com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardDeleteEventCallback):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finish() {
        finish(false);
    }

    private void finish(boolean z) {
        finish(z, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishWhenNotStarted() {
        finish(false, true);
    }

    private void finish(boolean z, boolean z2) {
        LogMgr.log(4, "%s", "000");
        try {
        } catch (Exception e) {
            LogMgr.log(1, "%s", "800");
            LogMgr.printStackTrace(7, e);
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                if (z2) {
                    if (this.mStarted != 0) {
                        LogMgr.log(4, "998 mStarted=" + this.mStarted);
                        return;
                    }
                }
                this.mStarted = 0;
                this.mLoggingOut = false;
                this.mFelicaWrapper.setMfiOnline(null);
                OpenIdConnectActivity.setMfiStarted(false);
                if (z) {
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

    private synchronized void checkStartedBy(int i) throws MfiClientException {
        if (!isStartedBy(i)) {
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_NOT_STARTED, null);
        }
    }

    private synchronized boolean isStartedBy(int i) throws MfiClientException {
        return (this.mStarted & i) == i;
    }

    private void checkMfiAccess() throws MfiClientException {
        if (!this.mFelicaWrapper.canAccessRight(6) && !this.mFelicaWrapper.canAccessRight(7) && !this.mFelicaWrapper.canAccessRight(8) && !this.mFelicaWrapper.canAccessRight(16)) {
            throw new MfiClientException(12, 38, null);
        }
    }

    private class OnFinishListener implements FwsClientFacade.OnFinishListener {
        private OnFinishListener() {
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onLoginFinished(boolean z, int i, String str) {
            ILoginEventCallback iLoginEventCallback;
            int i2;
            LogMgr.log(4, "%s", "000");
            synchronized (MfiOnline.this) {
                iLoginEventCallback = MfiOnline.this.mILoginEventCallback;
                MfiOnline.this.mILoginEventCallback = null;
                i2 = MfiOnline.this.mStarted;
            }
            OpenIdConnectActivity.setMfiStarted(false);
            try {
                if (!z) {
                    synchronized (MfiOnline.this.mFelicaWrapper) {
                        synchronized (MfiOnline.this) {
                            MfiOnline.this.mFelicaWrapper.setMfiOnline(null);
                            MfiOnline.this.mFwsClientFacade.finish();
                        }
                    }
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_START, i);
                    iLoginEventCallback.onError(i, str);
                } else {
                    synchronized (MfiOnline.this) {
                        MfiOnline.this.mStarted = 1;
                    }
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "ILoginEventCallback", "onSuccess");
                    iLoginEventCallback.onSuccess();
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "ILoginEventCallback", "onSuccess");
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
                        MfiOnline.this.mStarted = i2;
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
        public void onLogoutFinished(boolean z, boolean z2, int i, String str) {
            ILogoutEventCallback iLogoutEventCallback;
            LogMgr.log(4, "%s", "000");
            synchronized (MfiOnline.this) {
                iLogoutEventCallback = MfiOnline.this.mILogoutEventCallback;
                MfiOnline.this.mILogoutEventCallback = null;
            }
            if (iLogoutEventCallback != null) {
                try {
                    if (z) {
                        MfiOnline.this.finish();
                        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "ILogoutEventCallback", "onSuccess");
                        iLogoutEventCallback.onSuccess();
                        LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "ILogoutEventCallback", "onSuccess");
                    } else {
                        if (z2) {
                            MfiOnline.this.finish();
                        }
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_STOP, i);
                        iLogoutEventCallback.onError(i, str);
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
        public void onInitializeFinished(boolean z, int i, String str) {
            IInitializedEventCallback iInitializedEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iInitializedEventCallback = MfiOnline.this.mIInitializedEventCallback;
                MfiOnline.this.mIInitializedEventCallback = null;
            }
            try {
                if (z) {
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "IInitializedEventCallback", "onSuccess");
                    iInitializedEventCallback.onSuccess();
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "IInitializedEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_ADMIN_INITIALIZE, i);
                    iInitializedEventCallback.onError(i, str);
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
        public void onGetCardListFinished(boolean z, CardInfo[] cardInfoArr, int i, String str) {
            ICardListEventCallback iCardListEventCallback;
            LogMgr.log(4, "%s", "000");
            synchronized (MfiOnline.this) {
                iCardListEventCallback = MfiOnline.this.mICardListEventCallback;
                MfiOnline.this.mICardListEventCallback = null;
            }
            try {
                if (z) {
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "ICardListEventCallback", "onSuccess");
                    iCardListEventCallback.onSuccess(cardInfoArr);
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "ICardListEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_LIST, i);
                    iCardListEventCallback.onError(i, str);
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
        public void onGetCardListV2Finished(boolean z, int i, String str) {
            IPipeEventCallback iPipeEventCallback;
            LogMgr.log(4, "000");
            if (!z) {
                LogMgr.log(4, "001");
                synchronized (MfiOnline.this) {
                    iPipeEventCallback = MfiOnline.this.mIPipeEventCallback;
                    MfiOnline.this.mIPipeEventCallback = null;
                }
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_LIST, i);
                    iPipeEventCallback.onError(i, str);
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
        public void onWritePipeFinished(boolean z, MfiClientExternalLogConst.MficApi mficApi, int i, String str) {
            IPipeEventCallback iPipeEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iPipeEventCallback = MfiOnline.this.mIPipeEventCallback;
                MfiOnline.this.mIPipeEventCallback = null;
            }
            if (!z) {
                try {
                    LogMgr.exLogOnError(mficApi, i);
                    iPipeEventCallback.onError(i, str);
                } catch (Exception e) {
                    LogMgr.log(1, "%s", "801");
                    LogMgr.printStackTrace(7, e);
                    try {
                        LogMgr.exLogOnError(mficApi, 200);
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
        public void onGetCardAdditionalInfoListFinished(boolean z, CardAdditionalInfo[] cardAdditionalInfoArr, int i, String str) {
            ICardAdditionalInfoListEventCallback iCardAdditionalInfoListEventCallback;
            LogMgr.log(4, "%s", "000");
            synchronized (MfiOnline.this) {
                iCardAdditionalInfoListEventCallback = MfiOnline.this.mICardAdditionalInfoListEventCallback;
                MfiOnline.this.mICardAdditionalInfoListEventCallback = null;
            }
            try {
                if (z) {
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "ICardAdditionalInfoListEventCallback", "onSuccess");
                    iCardAdditionalInfoListEventCallback.onSuccess(cardAdditionalInfoArr);
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "ICardAdditionalInfoListEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_ADDITIONAL_INFO_LIST, i);
                    iCardAdditionalInfoListEventCallback.onError(i, str);
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
        public void onGetCardAdditionalInfoListV2Finished(boolean z, int i, String str) {
            IPipeEventCallback iPipeEventCallback;
            LogMgr.log(4, "000");
            if (!z) {
                LogMgr.log(4, "001");
                synchronized (MfiOnline.this) {
                    iPipeEventCallback = MfiOnline.this.mIPipeEventCallback;
                    MfiOnline.this.mIPipeEventCallback = null;
                }
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_ADDITIONAL_INFO_LIST, i);
                    iPipeEventCallback.onError(i, str);
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
        public void onIssueCardFinished(boolean z, CardInfo cardInfo, int i, String str) {
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
                onIssueCardV1Finished(z, cardInfo, i, str, iCardIssueEventCallback);
            } else if (iCardIssueV2EventCallback != null) {
                LogMgr.log(4, "004");
                onIssueCardV2Finished(z, cardInfo, i, str, iCardIssueV2EventCallback);
            } else {
                LogMgr.log(2, "700 callback is null.");
            }
            LogMgr.log(4, "999");
        }

        private void onIssueCardV1Finished(boolean z, CardInfo cardInfo, int i, String str, ICardIssueEventCallback iCardIssueEventCallback) {
            LogMgr.log(4, "000");
            try {
                if (z) {
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "ICardIssueEventCallback", "onSuccess");
                    iCardIssueEventCallback.onSuccess(cardInfo);
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "ICardIssueEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_ISSUE_CARD, i);
                    iCardIssueEventCallback.onError(i, str);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_ISSUE_CARD, 200);
                    iCardIssueEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        private void onIssueCardV2Finished(boolean z, CardInfo cardInfo, int i, String str, ICardIssueV2EventCallback iCardIssueV2EventCallback) {
            LogMgr.log(4, "000");
            try {
                if (z) {
                    String string = cardInfo != null ? new CardInfoJson(cardInfo).toString() : null;
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "ICardIssueV2EventCallback", "onSuccess");
                    iCardIssueV2EventCallback.onSuccess(string);
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "ICardIssueV2EventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_ISSUE_CARD, i);
                    iCardIssueV2EventCallback.onError(i, str);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_ISSUE_CARD, 200);
                    iCardIssueV2EventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onDeleteCardFinished(boolean z, int i, String str) {
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
                onDeleteCardV1Finished(z, i, str, iCardDeleteEventCallback);
            } else if (iCardDeleteV2EventCallback != null) {
                LogMgr.log(4, "004");
                onDeleteCardV2Finished(z, i, str, iCardDeleteV2EventCallback);
            } else {
                LogMgr.log(2, "700 callback is null.");
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onSaveDeleteCardV2Finished(boolean z, CardInfo cardInfo, int i, String str) {
            LogMgr.log(4, "000");
            if (MfiOnline.this.mICardReissuableDeleteEventCallback != null) {
                ICardReissuableDeleteEventCallback iCardReissuableDeleteEventCallback = MfiOnline.this.mICardReissuableDeleteEventCallback;
                MfiOnline.this.mICardReissuableDeleteEventCallback = null;
                try {
                    if (z) {
                        String string = cardInfo != null ? new CardInfoJson(cardInfo).toString() : null;
                        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "ICardReissuableDeleteEventCallback", "onSuccess");
                        iCardReissuableDeleteEventCallback.onSuccess(string);
                        LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "ICardReissuableDeleteEventCallback", "onSuccess");
                    } else {
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_DELETE, i);
                        iCardReissuableDeleteEventCallback.onError(i, str);
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

        private void onDeleteCardV1Finished(boolean z, int i, String str, ICardDeleteEventCallback iCardDeleteEventCallback) {
            LogMgr.log(4, "000");
            try {
                if (z) {
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "ICardDeleteEventCallback", "onSuccess");
                    iCardDeleteEventCallback.onSuccess();
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "ICardDeleteEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_DELETE, i);
                    iCardDeleteEventCallback.onError(i, str);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_DELETE, 200);
                    iCardDeleteEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        private void onDeleteCardV2Finished(boolean z, int i, String str, ICardDeleteV2EventCallback iCardDeleteV2EventCallback) {
            LogMgr.log(4, "000");
            try {
                if (z) {
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "ICardDeleteV2EventCallback", "onSuccess");
                    iCardDeleteV2EventCallback.onSuccess();
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "ICardDeleteV2EventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_DELETE, i);
                    iCardDeleteV2EventCallback.onError(i, str);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_DELETE, 200);
                    iCardDeleteV2EventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onEnableCardFinished(boolean z, CardInfo cardInfo, CardInfo cardInfo2, int i, String str) {
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
                onEnableCardV1Finished(z, cardInfo, cardInfo2, i, str, iCardEnableEventCallback);
            } else if (iCardEnableV2EventCallback != null) {
                LogMgr.log(4, "004");
                onEnableCardV2Finished(z, cardInfo, cardInfo2, i, str, iCardEnableV2EventCallback);
            } else {
                LogMgr.log(2, "700 callback is null.");
            }
            LogMgr.log(4, "999");
        }

        private void onEnableCardV1Finished(boolean z, CardInfo cardInfo, CardInfo cardInfo2, int i, String str, ICardEnableEventCallback iCardEnableEventCallback) {
            LogMgr.log(4, "000");
            try {
                if (z) {
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "ICardEnableEventCallback", "onSuccess");
                    iCardEnableEventCallback.onSuccess(cardInfo, cardInfo2);
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "ICardEnableEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_ENABLE, i);
                    iCardEnableEventCallback.onError(i, str);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_ENABLE, 200);
                    iCardEnableEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        private void onEnableCardV2Finished(boolean z, CardInfo cardInfo, CardInfo cardInfo2, int i, String str, ICardEnableV2EventCallback iCardEnableV2EventCallback) {
            LogMgr.log(4, "000");
            try {
                if (z) {
                    String string = cardInfo != null ? new CardInfoJson(cardInfo).toString() : null;
                    String string2 = cardInfo2 != null ? new CardInfoJson(cardInfo2).toString() : null;
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "ICardEnableV2EventCallback", "onSuccess");
                    iCardEnableV2EventCallback.onSuccess(string, string2);
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "ICardEnableV2EventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_ENABLE, i);
                    iCardEnableV2EventCallback.onError(i, str);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_ENABLE, 200);
                    iCardEnableV2EventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onDisableCardFinished(boolean z, CardInfo cardInfo, int i, String str) {
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
                onDisableCardV1Finished(z, cardInfo, i, str, iCardDisableEventCallback);
            } else if (iCardDisableV2EventCallback != null) {
                LogMgr.log(4, "004");
                onDisableCardV2Finished(z, cardInfo, i, str, iCardDisableV2EventCallback);
            } else {
                LogMgr.log(2, "700 callback is null.");
            }
            LogMgr.log(4, "999");
        }

        private void onDisableCardV1Finished(boolean z, CardInfo cardInfo, int i, String str, ICardDisableEventCallback iCardDisableEventCallback) {
            LogMgr.log(4, "000");
            try {
                if (z) {
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "ICardDisableEventCallback", "onSuccess");
                    iCardDisableEventCallback.onSuccess(cardInfo);
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "ICardDisableEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_DISABLE, i);
                    iCardDisableEventCallback.onError(i, str);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_DISABLE, 200);
                    iCardDisableEventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        private void onDisableCardV2Finished(boolean z, CardInfo cardInfo, int i, String str, ICardDisableV2EventCallback iCardDisableV2EventCallback) {
            LogMgr.log(4, "000");
            try {
                if (z) {
                    String string = cardInfo != null ? new CardInfoJson(cardInfo).toString() : null;
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "ICardDisableV2EventCallback", "onSuccess");
                    iCardDisableV2EventCallback.onSuccess(string);
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "ICardDisableV2EventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_DISABLE, i);
                    iCardDisableV2EventCallback.onError(i, str);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801");
                LogMgr.printStackTrace(7, e);
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_DISABLE, 200);
                    iCardDisableV2EventCallback.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e));
                } catch (RemoteException e2) {
                    LogMgr.log(1, "802");
                    LogMgr.printStackTrace(7, e2);
                }
            }
            LogMgr.log(4, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishListener
        public void onGetAccessCardFinished(boolean z, int i, String str) {
            ICardAccessEventCallback iCardAccessEventCallback;
            LogMgr.log(4, "%s", "000");
            synchronized (MfiOnline.this) {
                iCardAccessEventCallback = MfiOnline.this.mICardAccessEventCallback;
                MfiOnline.this.mICardAccessEventCallback = null;
            }
            try {
                if (z) {
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "ICardAccessEventCallback", "onSuccess");
                    iCardAccessEventCallback.onSuccess();
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "ICardAccessEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CARD_ACCESS, i);
                    iCardAccessEventCallback.onError(i, str);
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
        public void onSilentStartForMfiAdminFinished(boolean z, boolean z2, Intent intent, int i, String str) {
            ISilentStartForMfiAdminEventCallback iSilentStartForMfiAdminEventCallback;
            int i2;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iSilentStartForMfiAdminEventCallback = MfiOnline.this.mISilentStartForMfiAdminEventCallback;
                MfiOnline.this.mISilentStartForMfiAdminEventCallback = null;
                i2 = MfiOnline.this.mStarted;
            }
            OpenIdConnectActivity.setMfiStarted(false);
            try {
                if (z) {
                    LogMgr.log(6, "001");
                    if (z2) {
                        LogMgr.log(6, "002");
                        synchronized (MfiOnline.this) {
                            MfiOnline.this.mStarted = 2;
                        }
                        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "ISilentStartForMfiAdminEventCallback", "onSuccessNoLogin");
                        iSilentStartForMfiAdminEventCallback.onSuccessNoLogin();
                        LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "ISilentStartForMfiAdminEventCallback", "onSuccessNoLogin");
                    } else {
                        LogMgr.log(6, "003");
                        synchronized (MfiOnline.this) {
                            MfiOnline.this.mStarted = 3;
                        }
                        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "ISilentStartForMfiAdminEventCallback", "onSuccess");
                        iSilentStartForMfiAdminEventCallback.onSuccess();
                        LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "ISilentStartForMfiAdminEventCallback", "onSuccess");
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
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_SILENT_START, i);
                        iSilentStartForMfiAdminEventCallback.onError(i, str);
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
                        MfiOnline.this.mStarted = i2;
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
        public void onSilentStartFinished(boolean z, Intent intent, int i, String str) {
            ISilentStartEventCallback iSilentStartEventCallback;
            int i2;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iSilentStartEventCallback = MfiOnline.this.mISilentStartEventCallback;
                MfiOnline.this.mISilentStartEventCallback = null;
                i2 = MfiOnline.this.mStarted;
            }
            OpenIdConnectActivity.setMfiStarted(false);
            try {
                if (!z) {
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
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_SILENT_START, i);
                        iSilentStartEventCallback.onError(i, str);
                    }
                } else {
                    LogMgr.log(6, "001");
                    synchronized (MfiOnline.this) {
                        MfiOnline.this.mStarted = 1;
                    }
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "ISilentStartEventCallback", "onSuccess");
                    iSilentStartEventCallback.onSuccess();
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "ISilentStartEventCallback", "onSuccess");
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
                        MfiOnline.this.mStarted = i2;
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
        public void onGetLinkageDataListFinished(boolean z, String[] strArr, int i, String str) {
            ILinkageDataListEventCallback iLinkageDataListEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iLinkageDataListEventCallback = MfiOnline.this.mILinkageDataListEventCallback;
                MfiOnline.this.mILinkageDataListEventCallback = null;
            }
            try {
                if (z) {
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "ILinkageDataListEventCallback", "onSuccess");
                    iLinkageDataListEventCallback.onSuccess(strArr);
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "ILinkageDataListEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_ADMIN_GET_LINKAGE_DATA_LIST, i);
                    iLinkageDataListEventCallback.onError(i, str);
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
        public void onGetCardInfoListWithSpStatusFinished(boolean z, int i, String str) {
            IPipeEventCallback iPipeEventCallback;
            LogMgr.log(4, "000");
            if (!z) {
                LogMgr.log(4, "001");
                synchronized (MfiOnline.this) {
                    iPipeEventCallback = MfiOnline.this.mIPipeEventCallback;
                    MfiOnline.this.mIPipeEventCallback = null;
                }
                try {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_INFO_LIST_WITH_SP_STATUS, i);
                    iPipeEventCallback.onError(i, str);
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
        public void onGetCardListV3Finished(boolean z, CardInfo[] cardInfoArr, int i, String str) {
            IDataListEventCallback iDataListEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iDataListEventCallback = MfiOnline.this.mIDataListEventCallback;
                MfiOnline.this.mIDataListEventCallback = null;
            }
            try {
                if (z) {
                    List listDivide = MfiOnline.divide(Arrays.asList(cardInfoArr), new GetMfiControlInfoResponseJson(MfiControlInfoCache.getInstance().getInfoCache()).getTransmittableGetCardListSize());
                    int i2 = 0;
                    while (i2 < listDivide.size()) {
                        ArrayList arrayList = new ArrayList();
                        for (int i3 = 0; i3 < ((List) listDivide.get(i2)).size(); i3++) {
                            arrayList.add(new CardInfoJson((CardInfo) ((List) listDivide.get(i2)).get(i3)).toString());
                        }
                        iDataListEventCallback.onPart(arrayList, i2 != listDivide.size() - 1);
                        i2++;
                    }
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "IDataListEventCallback", "onSuccess", "length = " + cardInfoArr.length);
                    iDataListEventCallback.onFinished(cardInfoArr.length);
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "IDataListEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_LIST, i);
                    iDataListEventCallback.onError(i, str);
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
        public void onGetCardAdditionalInfoListV3Finished(boolean z, CardAdditionalInfo[] cardAdditionalInfoArr, int i, String str) {
            IDataListEventCallback iDataListEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iDataListEventCallback = MfiOnline.this.mIDataListEventCallback;
                MfiOnline.this.mIDataListEventCallback = null;
            }
            try {
                if (z) {
                    List listDivide = MfiOnline.divide(Arrays.asList(cardAdditionalInfoArr), new GetMfiControlInfoResponseJson(MfiControlInfoCache.getInstance().getInfoCache()).getTransmittableGetCardAdditionalInfoListSize());
                    int i2 = 0;
                    while (i2 < listDivide.size()) {
                        ArrayList arrayList = new ArrayList();
                        for (int i3 = 0; i3 < ((List) listDivide.get(i2)).size(); i3++) {
                            arrayList.add(new CardAdditionalInfoJson((CardAdditionalInfo) ((List) listDivide.get(i2)).get(i3)).toString());
                        }
                        iDataListEventCallback.onPart(arrayList, i2 != listDivide.size() - 1);
                        i2++;
                    }
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "IDataListEventCallback", "onSuccess", "length = " + cardAdditionalInfoArr.length);
                    iDataListEventCallback.onFinished(cardAdditionalInfoArr.length);
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "IDataListEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_ADDITIONAL_INFO_LIST, i);
                    iDataListEventCallback.onError(i, str);
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
        public void onGetCardInfoListWithSpStatusV3Finished(boolean z, CardInfoWithSpStatus[] cardInfoWithSpStatusArr, int i, String str) {
            IDataListEventCallback iDataListEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iDataListEventCallback = MfiOnline.this.mIDataListEventCallback;
                MfiOnline.this.mIDataListEventCallback = null;
            }
            try {
                if (z) {
                    List listDivide = MfiOnline.divide(Arrays.asList(cardInfoWithSpStatusArr), new GetMfiControlInfoResponseJson(MfiControlInfoCache.getInstance().getInfoCache()).getTransmittableGetCardInfoListWithSpStatusSize());
                    int i2 = 0;
                    while (i2 < listDivide.size()) {
                        ArrayList arrayList = new ArrayList();
                        for (int i3 = 0; i3 < ((List) listDivide.get(i2)).size(); i3++) {
                            arrayList.add(new CardInfoWithSpStatusJson((CardInfoWithSpStatus) ((List) listDivide.get(i2)).get(i3)).toString());
                        }
                        iDataListEventCallback.onPart(arrayList, i2 != listDivide.size() - 1);
                        i2++;
                    }
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "IDataListEventCallback", "onSuccess", "length = " + cardInfoWithSpStatusArr.length);
                    iDataListEventCallback.onFinished(cardInfoWithSpStatusArr.length);
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "IDataListEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.USER_GET_CARD_INFO_LIST_WITH_SP_STATUS, i);
                    iDataListEventCallback.onError(i, str);
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
        public void onExecuteServerOperationFinished(boolean z, boolean z2, int i, String str) {
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
                    if (z) {
                        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "ExecuteServerOperationEventCallback", "onSuccess");
                        executeServerOperationEventCallback.onSuccess();
                        LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "ExecuteServerOperationEventCallback", "onSuccess");
                    } else if (z2) {
                        executeServerOperationEventCallback.onRetryRequired(i, str);
                    } else {
                        executeServerOperationEventCallback.onError(i, str);
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
        public void onProvisionServerOperationFinished(boolean z, int i, String str) {
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
                    if (z) {
                        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "IServerOperationEventCallback", "onSuccess");
                        iServerOperationEventCallback.onSuccess();
                        LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "IServerOperationEventCallback", "onSuccess");
                    } else {
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_PROVISION_SERVER_OPERATION, i);
                        iServerOperationEventCallback.onError(i, str);
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
        public void onCheckLegacyCardExistenceFinished(boolean z, boolean z2, String str, int i, String str2) {
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
                    if (z) {
                        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "UnsupportMfiService1CardExistEventCallback", "onSuccess");
                        iUnsupportMfiService1CardExistEventCallback.onSuccess(z2, str);
                        LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "UnsupportMfiService1CardExistEventCallback", "onSuccess");
                    } else {
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_EXIST_UNSUPPORT_MFI_SERVICE1_CARD, i);
                        iUnsupportMfiService1CardExistEventCallback.onError(i, str2);
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
        public void onDeleteLegacyCardFinished(boolean z, int i, String str) {
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
                    if (z) {
                        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "mIUnsupportMfiService1CardDeleteEventCallback", "onSuccess");
                        iUnsupportMfiService1CardDeleteEventCallback.onSuccess();
                        LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "mIUnsupportMfiService1CardDeleteEventCallback", "onSuccess");
                    } else {
                        LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_DELETE_UNSUPPORT_MFI_SERVICE1_CARD, i);
                        iUnsupportMfiService1CardDeleteEventCallback.onError(i, str);
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
        public void onGetCachedCardListFinished(boolean z, String[] strArr, int i, String str) {
            IDataListEventCallback iDataListEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iDataListEventCallback = MfiOnline.this.mICachedCardListEventCallback;
                MfiOnline.this.mICachedCardListEventCallback = null;
            }
            OpenIdConnectActivity.setMfiStarted(false);
            try {
                MfiOnline.this.finishWhenNotStarted();
                if (z) {
                    List listDivide = MfiOnline.divide(Arrays.asList(strArr), new GetMfiControlInfoResponseJson(MfiControlInfoCache.getInstance().getInfoCache()).getTransmittableGetCachedCardListSize());
                    int i2 = 0;
                    while (i2 < listDivide.size()) {
                        iDataListEventCallback.onPart((List) listDivide.get(i2), i2 != listDivide.size() - 1);
                        i2++;
                    }
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "IDataListEventCallback", "onSuccess", "length = " + strArr.length);
                    iDataListEventCallback.onFinished(strArr.length);
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "IDataListEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_CACHED_CARD_LIST, i);
                    iDataListEventCallback.onError(i, str);
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
        public void onEnableCachedCardFinished(boolean z, CachedCardInfo cachedCardInfo, CachedCardInfo cachedCardInfo2, int i, String str) {
            ICachedCardEnableEventCallback iCachedCardEnableEventCallback;
            LogMgr.log(4, "000");
            synchronized (MfiOnline.this) {
                iCachedCardEnableEventCallback = MfiOnline.this.mICachedCardEnableEventCallback;
                MfiOnline.this.mICachedCardEnableEventCallback = null;
            }
            OpenIdConnectActivity.setMfiStarted(false);
            try {
                MfiOnline.this.finishWhenNotStarted();
                if (z) {
                    String string = cachedCardInfo != null ? new CachedCardInfoJson(cachedCardInfo).toString() : null;
                    String string2 = cachedCardInfo2 != null ? new CachedCardInfoJson(cachedCardInfo2).toString() : null;
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "ICachedCardEnableEventCallback", "onSuccess");
                    iCachedCardEnableEventCallback.onSuccess(string, string2);
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "ICachedCardEnableEventCallback", "onSuccess");
                } else {
                    LogMgr.exLogOnError(MfiClientExternalLogConst.MficApi.CACHED_CARD_ENABLE, i);
                    iCachedCardEnableEventCallback.onError(i, str);
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
    }

    boolean isLoginedAccount(String str) throws FelicaException, IllegalArgumentException {
        boolean zEqualsIgnoreCase;
        LogMgr.log(4, "000");
        if (str == null) {
            LogMgr.log(2, "700 accountName is null.");
            throw new IllegalArgumentException("The specified AccountName is null.");
        }
        if (str.isEmpty()) {
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
                        zEqualsIgnoreCase = accountNameCache.equalsIgnoreCase(str);
                    } catch (FelicaException e) {
                        LogMgr.log(2, "702 : FelicaException: " + e.getMessage());
                        throw e;
                    }
                } catch (Exception e2) {
                    LogMgr.log(2, "703 : " + e2.getClass().getSimpleName() + ":" + e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                    throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
                }
            }
        }
        LogMgr.log(4, "999");
        return zEqualsIgnoreCase;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> List<List<T>> divide(List<T> list, int i) {
        if (list == null || list.isEmpty() || i <= 0) {
            return Collections.emptyList();
        }
        int size = (list.size() / i) + (list.size() % i > 0 ? 1 : 0);
        ArrayList arrayList = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            int i3 = i2 * i;
            arrayList.add(new ArrayList(list.subList(i3, Math.min(i3 + i, list.size()))));
        }
        return arrayList;
    }
}
