package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.MfiClientConst;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.MfiFelicaWrapper;
import com.felicanetworks.mfc.mfi.felica.access_control.AccessConfig;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.json.GetInitializeScriptRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.GetInitializeScriptResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.RequestJson;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.AppletManager;
import com.felicanetworks.mfc.mfi.omapi.CrsManager;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.omapi.SeAppletInfo;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
class InitializeTask extends AsyncParentTaskBase<String> {
    private static final List<String> VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT;
    private final MfiChipHolder mChipHolder;
    private final FwsClient mFwsClient;
    private final String mLinkageData;

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public Void getResult2() {
        return null;
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public void setResult(String result) {
    }

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT = arrayList;
        arrayList.add("0000");
        arrayList.add(FwsConst.RESULT_CODE_CONTINUE);
        arrayList.add("1000");
        arrayList.add("2000");
        arrayList.add(FwsConst.RESULT_ILLEGAL_URL);
        arrayList.add(FwsConst.RESULT_ILLEGAL_ACCESS_TOKEN);
        arrayList.add(FwsConst.RESULT_ILLEGAL_LINKAGE_DATA);
        arrayList.add("3000");
        arrayList.add(FwsConst.RESULT_EXPIRED_LINKAGE_DATA);
        arrayList.add("4000");
        arrayList.add("4001");
        arrayList.add(FwsConst.RESULT_NOT_WHITELISTED);
        arrayList.add(FwsConst.RESULT_INSUFFICIENT_CHIP_SPACE);
        arrayList.add(FwsConst.RESULT_MEMORY_CLEARING);
        arrayList.add(FwsConst.RESULT_INITIALIZED);
        arrayList.add("5081");
        arrayList.add("9000");
        arrayList.add("9001");
        arrayList.add("9005");
    }

    InitializeTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, String linkageData, FwsClient fwsClient, MfiChipHolder chipHolder) {
        super(taskId, executor, listener);
        this.mLinkageData = linkageData;
        this.mFwsClient = fwsClient;
        this.mChipHolder = chipHolder;
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [212=8, 214=10] */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:41:0x00ca */
    /* JADX DEBUG: Multi-variable search result rejected for r7v0, resolved type: com.felicanetworks.mfc.mfi.omapi.GpController */
    /* JADX DEBUG: Multi-variable search result rejected for r7v1, resolved type: com.felicanetworks.mfc.mfi.omapi.GpController */
    /* JADX DEBUG: Multi-variable search result rejected for r7v2, resolved type: com.felicanetworks.mfc.mfi.omapi.GpController */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() throws Throwable {
        GpController gpController;
        LogMgr.log(6, "000");
        FelicaAdapter felicaAdapter = FelicaAdapter.getInstance();
        MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(this.mChipHolder);
        GpController gpController2 = 0;
        GpController gpController3 = null;
        GpController gpController4 = null;
        GpController gpController5 = null;
        GpController gpController6 = null;
        GpController gpController7 = null;
        try {
            try {
                try {
                    if (StringUtil.versionCompare(mfiFelicaWrapper.getMFCVersion(felicaAdapter), MfiClientConst.MFC_VERSION_FAVER3) == -1) {
                        LogMgr.log(1, "800 : Not supported chip.");
                        onFinished(false, 223, MfiClientCallbackConst.MSG_NOT_SUPPORTED_CHIP_ERROR);
                        mfiFelicaWrapper.closeSilently();
                        return;
                    }
                    mfiFelicaWrapper.open();
                    mfiFelicaWrapper.select(65039);
                    byte[] containerIssueInformation = mfiFelicaWrapper.getContainerIssueInformation();
                    mfiFelicaWrapper.close();
                    if (containerIssueInformation != null) {
                        for (byte b : containerIssueInformation) {
                            if (b != 0) {
                                LogMgr.log(1, "801 : Already initialized.");
                                onFinished(false, 224, MfiClientCallbackConst.MSG_INITIALIZED_ERROR);
                                mfiFelicaWrapper.closeSilently();
                                return;
                            }
                        }
                    }
                    if (Property.isChipGP()) {
                        gpController = this.mChipHolder.getGpController();
                        try {
                            AppletManager appletManager = new AppletManager(gpController);
                            if (((SeAppletInfo) appletManager.getAppletInfo(1)).isPersonalized() && AccessConfig.isValidContainerIssueInfo(appletManager.getContainerIssueInfoDirectly(FlavorConst.MANAGEMENT_SYSTEM_AID))) {
                                new CrsManager(gpController).checkAndRecoverCrsState();
                                onFinished(true, 0, null);
                                LogMgr.log(6, "998");
                                if (gpController != null) {
                                    gpController.closeChannel();
                                }
                                mfiFelicaWrapper.closeSilently();
                                return;
                            }
                            gpController3 = gpController;
                        } catch (MfiFelicaException e) {
                            e = e;
                            gpController4 = gpController;
                            LogMgr.log(1, "800 : MfiFelicaException");
                            onFinished(false, e.getType(), e.getMessage());
                            if (gpController4 != null) {
                                gpController4.closeChannel();
                            }
                            mfiFelicaWrapper.closeSilently();
                            return;
                        } catch (GpException e2) {
                            e = e2;
                            gpController5 = gpController;
                            LogMgr.log(1, "803 : GpException");
                            onFinished(false, e.getType(), e.getMessage());
                            if (gpController5 != null) {
                                gpController5.closeChannel();
                            }
                            mfiFelicaWrapper.closeSilently();
                            return;
                        } catch (InterruptedException unused) {
                            LogMgr.log(1, "802 : InterruptedException");
                            onFinished(false, 215, null);
                            if (gpController != null) {
                                gpController.closeChannel();
                            }
                            mfiFelicaWrapper.closeSilently();
                            return;
                        } catch (NumberFormatException unused2) {
                            gpController6 = gpController;
                            LogMgr.log(1, "801 : NumberFormatException");
                            onFinished(false, 223, MfiClientCallbackConst.MSG_NOT_SUPPORTED_CHIP_ERROR);
                            if (gpController6 != null) {
                                gpController6.closeChannel();
                            }
                            mfiFelicaWrapper.closeSilently();
                            return;
                        } catch (Exception unused3) {
                            gpController7 = gpController;
                            LogMgr.log(1, "804 : Exception");
                            mfiFelicaWrapper.closeSilently();
                            onFinished(false, 200, "Unknown error.");
                            if (gpController7 != null) {
                                gpController7.closeChannel();
                            }
                            mfiFelicaWrapper.closeSilently();
                            return;
                        }
                    }
                    if (gpController3 != null) {
                        gpController3.closeChannel();
                    }
                    mfiFelicaWrapper.closeSilently();
                    FwsInitializeSubTask fwsInitializeSubTask = new FwsInitializeSubTask(0, this.mFwsClient, this.mChipHolder);
                    setStoppableSubTask(fwsInitializeSubTask);
                    fwsInitializeSubTask.start();
                    LogMgr.log(6, "999");
                } catch (Throwable th) {
                    th = th;
                    gpController2 = felicaAdapter;
                    if (gpController2 != 0) {
                        gpController2.closeChannel();
                    }
                    mfiFelicaWrapper.closeSilently();
                    throw th;
                }
            } catch (MfiFelicaException e3) {
                e = e3;
            } catch (GpException e4) {
                e = e4;
            } catch (InterruptedException unused4) {
                gpController = null;
            } catch (NumberFormatException unused5) {
            } catch (Exception unused6) {
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private class FwsInitializeSubTask extends DoScriptTask<GetInitializeScriptResponseJson> {
        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected boolean isTimedRetrievable() {
            return false;
        }

        FwsInitializeSubTask(int taskId, FwsClient fwsClient, MfiChipHolder chipHolder) {
            super(taskId, fwsClient, chipHolder, InitializeTask.this.mExecutor);
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
        protected String callFws(String request) throws ProtocolException, HttpException {
            return this.mFwsClient.getInitializeScript(request, this.mSeqNum);
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/mfc/mfi/fws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public GetInitializeScriptResponseJson convertResponse(String json) throws JSONException {
            return new GetInitializeScriptResponseJson(json);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return InitializeTask.VALID_RESULT_CODE_LIST_GET_INITIALIZE_SCRIPT;
        }

        /* JADX DEBUG: Method merged with bridge method: onSuccessScript(Lcom/felicanetworks/mfc/mfi/fws/json/GetScriptResponseJson;)V */
        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Not initialized variable reg: 2, insn: 0x0069: MOVE (r1 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:30:0x0069 */
        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        public void onSuccessScript(GetInitializeScriptResponseJson response) throws Throwable {
            GpController gpController;
            GpController gpController2;
            GpException e;
            GpController gpController3 = null;
            try {
                try {
                    try {
                        gpController2 = this.mChipHolder.getGpController();
                    } catch (Throwable th) {
                        th = th;
                        if (gpController3 != null) {
                            gpController3.closeChannel();
                        }
                        throw th;
                    }
                } catch (GpException e2) {
                    gpController2 = null;
                    e = e2;
                } catch (InterruptedException unused) {
                    gpController2 = null;
                } catch (Exception unused2) {
                }
                try {
                    if (Property.isChipGP()) {
                        new CrsManager(gpController2).checkAndRecoverCrsState();
                    }
                    InitializeTask.this.onFinished(true, 0, null);
                    if (gpController2 != null) {
                        gpController2.closeChannel();
                    }
                } catch (GpException e3) {
                    e = e3;
                    LogMgr.log(1, "801 : GpException");
                    InitializeTask.this.onFinished(false, e.getType(), e.getMessage());
                    if (gpController2 != null) {
                        gpController2.closeChannel();
                    }
                } catch (InterruptedException unused3) {
                    LogMgr.log(1, "800 : InterruptedException");
                    InitializeTask.this.onFinished(false, 215, null);
                    if (gpController2 == null) {
                        return;
                    }
                    gpController2.closeChannel();
                } catch (Exception unused4) {
                    gpController3 = gpController2;
                    LogMgr.log(1, "802 : Exception");
                    InitializeTask.this.onFinished(false, 200, "Unknown error.");
                    if (gpController3 != null) {
                        gpController3.closeChannel();
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                gpController3 = gpController;
            }
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        void onErrorScript(int type, String msg) {
            InitializeTask.this.onFinished(false, type, msg);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_INITIALIZE_SCRIPT.msg;
        }
    }
}
