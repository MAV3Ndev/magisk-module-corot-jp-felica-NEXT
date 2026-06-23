package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.json.GetInitializeScriptRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.GetInitializeScriptResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.RequestJson;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
class InitializeTask extends AsyncParentTaskBase<String> {
    private static final List<String> VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT;
    private final MfiChipHolder mChipHolder;
    private final FwsClient mFwsClient;
    private final String mLinkageData;

    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public Void getResult2() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public void setResult(String str) {
    }

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT = arrayList;
        arrayList.add("0000");
        VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT.add(FwsConst.RESULT_CODE_CONTINUE);
        VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT.add(FwsConst.RESULT_CODE_RETRYREQUEST);
        VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT.add(FwsConst.RESULT_ILLEGAL_ARGUMENTS);
        VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT.add(FwsConst.RESULT_ILLEGAL_URL);
        VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT.add(FwsConst.RESULT_ILLEGAL_ACCESS_TOKEN);
        VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT.add(FwsConst.RESULT_ILLEGAL_LINKAGE_DATA);
        VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT.add(FwsConst.RESULT_INVALID_ACCESS_TOKEN);
        VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT.add(FwsConst.RESULT_EXPIRED_LINKAGE_DATA);
        VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT.add("4000");
        VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT.add(FwsConst.RESULT_UNKNOWN);
        VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT.add(FwsConst.RESULT_NOT_WHITELISTED);
        VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT.add(FwsConst.RESULT_INSUFFICIENT_CHIP_SPACE);
        VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT.add(FwsConst.RESULT_MEMORY_CLEARING);
        VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT.add(FwsConst.RESULT_INITIALIZED);
        VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT.add(FwsConst.RESULT_INITIALIZE_UNAVAILABLE);
        VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT.add("9000");
        VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT.add("9001");
        VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT.add(FwsConst.RESULT_CONGESTED);
    }

    InitializeTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, String str, FwsClient fwsClient, MfiChipHolder mfiChipHolder) {
        super(i, executorService, listener);
        this.mLinkageData = str;
        this.mFwsClient = fwsClient;
        this.mChipHolder = mfiChipHolder;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0098 A[Catch: Exception -> 0x00ae, GpException -> 0x00b0, NumberFormatException -> 0x00b3, MfiFelicaException -> 0x00b5, InterruptedException -> 0x010c, all -> 0x011f, TRY_LEAVE, TryCatch #11 {all -> 0x011f, blocks: (B:24:0x006f, B:26:0x0080, B:28:0x0088, B:30:0x008c, B:36:0x0098, B:33:0x0092, B:66:0x010c), top: B:92:0x0018 }] */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void run() throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 342
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.fws.InitializeTask.run():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void crsControl(com.felicanetworks.mfc.mfi.omapi.GpController r8) throws java.lang.InterruptedException, com.felicanetworks.mfc.mfi.omapi.GpException {
        /*
            r7 = this;
            r0 = 6
            java.lang.String r1 = "000"
            com.felicanetworks.mfc.util.LogMgr.log(r0, r1)
            boolean r1 = com.felicanetworks.mfc.mfi.mfw.i.fbl.Property.isChipGP()
            if (r1 == 0) goto L66
            com.felicanetworks.mfc.mfi.omapi.AppletManager r1 = new com.felicanetworks.mfc.mfi.omapi.AppletManager
            r1.<init>(r8)
            com.felicanetworks.mfc.mfi.omapi.CrsManager r2 = new com.felicanetworks.mfc.mfi.omapi.CrsManager
            r2.<init>(r8)
            int[] r8 = r1.getProtocolData()
            r1 = 1
            r3 = 0
            if (r8 == 0) goto L32
            int r4 = r8.length
            r5 = 2
            if (r4 < r5) goto L32
            r4 = r8[r3]
            r5 = 65024(0xfe00, float:9.1118E-41)
            if (r4 != r5) goto L32
            r8 = r8[r1]
            r4 = 65039(0xfe0f, float:9.1139E-41)
            if (r8 != r4) goto L32
            r8 = 1
            goto L33
        L32:
            r8 = 0
        L33:
            if (r8 != 0) goto L66
            java.util.List r8 = r2.getActivatedAidList()
            if (r8 == 0) goto L54
            java.util.Iterator r4 = r8.iterator()
        L3f:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L54
            java.lang.Object r5 = r4.next()
            byte[] r5 = (byte[]) r5
            byte[] r6 = com.felicanetworks.mfc.mfi.FlavorConst.SYSTEM0_AID
            boolean r5 = java.util.Arrays.equals(r5, r6)
            if (r5 == 0) goto L3f
            goto L55
        L54:
            r1 = 0
        L55:
            if (r1 != 0) goto L5c
            byte[] r1 = com.felicanetworks.mfc.mfi.FlavorConst.SYSTEM0_AID
            r2.activateApplet(r1)
        L5c:
            if (r8 == 0) goto L61
            r2.deactivateApplet(r8)
        L61:
            byte[] r8 = com.felicanetworks.mfc.mfi.FlavorConst.MANAGEMENT_SYSTEM_AID
            r2.activateApplet(r8)
        L66:
            java.lang.String r8 = "999"
            com.felicanetworks.mfc.util.LogMgr.log(r0, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.fws.InitializeTask.crsControl(com.felicanetworks.mfc.mfi.omapi.GpController):void");
    }

    private class FwsInitializeSubTask extends DoScriptTask<GetInitializeScriptResponseJson> {
        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected boolean isTimedRetrievable() {
            return false;
        }

        FwsInitializeSubTask(int i, FwsClient fwsClient, MfiChipHolder mfiChipHolder) {
            super(i, fwsClient, mfiChipHolder, InitializeTask.this.mExecutor);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected RequestJson createRequestJson() throws JSONException {
            GetInitializeScriptRequestJson getInitializeScriptRequestJson = new GetInitializeScriptRequestJson();
            getInitializeScriptRequestJson.setRequestId(createRequestId());
            getInitializeScriptRequestJson.setOperationId(this.mOperationId);
            if (this.mAccessToken != null) {
                getInitializeScriptRequestJson.setAccessToken(this.mAccessToken);
                this.mAccessToken = null;
            }
            if (this.mSeqNum == 1) {
                getInitializeScriptRequestJson.setLinkageData(InitializeTask.this.mLinkageData);
            }
            if (this.mTcapResult != null) {
                getInitializeScriptRequestJson.setTcapResult(this.mTcapResult);
                this.mTcapResult = null;
            }
            if (this.mApduResult != null) {
                getInitializeScriptRequestJson.setApduResult(this.mApduResult);
                this.mApduResult = null;
            }
            return getInitializeScriptRequestJson;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String str) throws ProtocolException, HttpException {
            return this.mFwsClient.getInitializeScript(str, this.mSeqNum);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public GetInitializeScriptResponseJson convertResponse(String str) throws JSONException {
            return new GetInitializeScriptResponseJson(str);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return InitializeTask.VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v0 */
        /* JADX WARN: Type inference failed for: r2v1, types: [com.felicanetworks.mfc.mfi.omapi.GpController] */
        /* JADX WARN: Type inference failed for: r2v10 */
        /* JADX WARN: Type inference failed for: r2v18 */
        /* JADX WARN: Type inference failed for: r2v19 */
        /* JADX WARN: Type inference failed for: r2v20 */
        /* JADX WARN: Type inference failed for: r2v21 */
        /* JADX WARN: Type inference failed for: r6v1 */
        /* JADX WARN: Type inference failed for: r6v10 */
        /* JADX WARN: Type inference failed for: r6v11 */
        /* JADX WARN: Type inference failed for: r6v13 */
        /* JADX WARN: Type inference failed for: r6v15 */
        /* JADX WARN: Type inference failed for: r6v17 */
        /* JADX WARN: Type inference failed for: r6v19 */
        /* JADX WARN: Type inference failed for: r6v2 */
        /* JADX WARN: Type inference failed for: r6v21 */
        /* JADX WARN: Type inference failed for: r6v22 */
        /* JADX WARN: Type inference failed for: r6v7, types: [com.felicanetworks.mfc.mfi.omapi.GpController] */
        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        public void onSuccessScript(GetInitializeScriptResponseJson getInitializeScriptResponseJson) throws Throwable {
            ?? r2;
            Throwable th;
            GpController gpController;
            GpException e;
            GpController gpController2;
            ?? r6 = 0;
            r6 = 0;
            str = null;
            r6 = 0;
            String str = null;
            r6 = 0;
            try {
                try {
                    try {
                        gpController = InitializeTask.this.mChipHolder.getGpController();
                        try {
                            InitializeTask.this.crsControl(gpController);
                            InitializeTask.this.onFinished(true, 0, null);
                            gpController2 = gpController;
                            r2 = gpController;
                        } catch (GpException e2) {
                            e = e2;
                            LogMgr.log(1, "801 : GpException");
                            InitializeTask initializeTask = InitializeTask.this;
                            int type = e.getType();
                            String message = e.getMessage();
                            initializeTask.onFinished(false, type, message);
                            r2 = gpController;
                            r6 = message;
                            if (gpController != null) {
                                gpController2 = gpController;
                                str = message;
                            }
                        } catch (InterruptedException unused) {
                            LogMgr.log(1, "800 : InterruptedException");
                            InitializeTask.this.onFinished(false, 215, null);
                            gpController2 = gpController;
                            r2 = gpController;
                            if (gpController != null) {
                            }
                        } catch (Exception unused2) {
                            r6 = gpController;
                            LogMgr.log(1, "802 : Exception");
                            InitializeTask.this.onFinished(false, 200, "Unknown error.");
                            if (r6 != 0) {
                                r6.closeChannel();
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (r2 != 0) {
                            r2.closeChannel();
                        }
                        throw th;
                    }
                } catch (GpException e3) {
                    gpController = null;
                    e = e3;
                } catch (InterruptedException unused3) {
                    gpController = null;
                } catch (Exception unused4) {
                }
                if (gpController != null) {
                    gpController2.closeChannel();
                    r2 = gpController2;
                    r6 = str;
                }
            } catch (Throwable th3) {
                r2 = r6;
                th = th3;
            }
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        void onErrorScript(int i, String str) {
            InitializeTask.this.onFinished(false, i, str);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_INITIALIZE_SCRIPT.msg;
        }
    }
}
