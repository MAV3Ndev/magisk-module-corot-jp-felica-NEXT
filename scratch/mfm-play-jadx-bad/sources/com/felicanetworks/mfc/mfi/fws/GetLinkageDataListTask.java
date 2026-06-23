package com.felicanetworks.mfc.mfi.fws;

import android.os.Build;
import com.felicanetworks.mfc.mfi.BadPropertyException;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.MfiFelicaWrapper;
import com.felicanetworks.mfc.mfi.SeInfoEx;
import com.felicanetworks.mfc.mfi.fws.AccessFwsTask;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.json.LinkageDataListRequestTokenPayloadJson;
import com.felicanetworks.mfc.mfi.fws.json.LinkageDataRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.LinkageDataResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.RequestJson;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.DeviceIdentificationData;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.AmsdAppletInfo;
import com.felicanetworks.mfc.mfi.omapi.AppletManager;
import com.felicanetworks.mfc.mfi.omapi.ClsdAppletInfo;
import com.felicanetworks.mfc.mfi.omapi.FelicaInstanceInfo;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.omapi.IsdAppletInfo;
import com.felicanetworks.mfc.mfi.omapi.ManagementSystemAppletInfo;
import com.felicanetworks.mfc.mfi.omapi.SdAppletInfo;
import com.felicanetworks.mfc.mfi.omapi.SeAppletInfo;
import com.felicanetworks.mfc.mfi.omapi.SsdAppletInfo;
import com.felicanetworks.mfc.mfi.omapi.System0AppletInfo;
import com.felicanetworks.mfc.mfi.omapi.TopLevelSdAppletInfo;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
class GetLinkageDataListTask extends AsyncParentTaskBase<String[]> {
    private static final int AMSD = 2;
    private static final int CLSD = 3;
    private static final int ISD = 1;
    private static final int MANAGEMENT_SYSTEM = 1;
    private static final int SD_KEY_CHANGE_MODEL_01 = 1;
    private static final int SD_KEY_CHANGE_MODEL_02 = 2;
    private static final int SD_KEY_CHANGE_MODEL_03 = 3;
    private static final int SSD = 4;
    private static final int SYSYTEM_0 = 2;
    private static final int TOP_LEVEL_SD = 5;
    private static final List<String> VALID_RESULT_CODE_LIST_GET_LINKAGE_DATA_LIST;
    private final int mActionType;
    private AmsdAppletInfo mAmsdAppletInfo;
    private final FelicaInstanceInfo mAppletInfo;
    private JSONArray mAppletInfoListJson;
    private AppletManager mAppletManager;
    private final MfiChipHolder mChipHolder;
    private final String[] mCid;
    private ClsdAppletInfo mClsdAppletInfo;
    private JSONObject mContainerInfoJson;
    private final DataManager mDataManager;
    private final FwsClient mFwsClient;
    private String mIdm;
    private IsdAppletInfo mIsdAppletInfo;
    private final a mJwsCreator;
    private String[] mLinkageDataList;
    private ManagementSystemAppletInfo mManagementAppletInfo;
    private LinkageDataListRequestTokenPayloadJson mPayloadJson;
    private JSONArray mSdInfoListJson;
    private String mSignedJws;
    private SsdAppletInfo mSsdAppletInfo;
    private System0AppletInfo mSystem0AppletInfo;
    private TopLevelSdAppletInfo mTopLevelSdAppletInfo;
    private String mTsmConfiguration;

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_LINKAGE_DATA_LIST = arrayList;
        arrayList.add("0000");
        arrayList.add("1000");
        arrayList.add("2000");
        arrayList.add(FwsConst.RESULT_ILLEGAL_URL);
        arrayList.add(FwsConst.RESULT_ILLEGAL_REQUEST_TOKEN);
        arrayList.add(FwsConst.RESULT_INVALID_REQUEST_TOKEN);
        arrayList.add("4000");
        arrayList.add(FwsConst.RESULT_NOT_ACTIVE_CARD);
        arrayList.add(FwsConst.RESULT_NOT_EXIST_CARD);
        arrayList.add("9000");
        arrayList.add("9001");
        arrayList.add("9005");
    }

    GetLinkageDataListTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, int actionType, String[] cid, DataManager dataManager, a jwsCreator, FwsClient fwsClient, MfiChipHolder chipHolder) {
        this(taskId, executor, listener, actionType, cid, null, dataManager, jwsCreator, fwsClient, chipHolder);
    }

    GetLinkageDataListTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, int actionType, String[] cid, FelicaInstanceInfo appletInfo, DataManager dataManager, a jwsCreator, FwsClient fwsClient, MfiChipHolder chipHolder) {
        super(taskId, executor, listener);
        this.mPayloadJson = new LinkageDataListRequestTokenPayloadJson();
        this.mSignedJws = null;
        this.mTsmConfiguration = null;
        this.mIdm = null;
        this.mActionType = actionType;
        this.mCid = cid;
        this.mAppletInfo = appletInfo;
        this.mDataManager = dataManager;
        this.mJwsCreator = jwsCreator;
        this.mFwsClient = fwsClient;
        this.mChipHolder = chipHolder;
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [328=4] */
    /* JADX WARN: Not initialized variable reg: 8, insn: 0x011c: MOVE (r7 I:??[OBJECT, ARRAY]) = (r8 I:??[OBJECT, ARRAY]), block:B:63:0x011c */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() throws Throwable {
        GpController gpController;
        GpController gpController2;
        LogMgr.log(6, "000");
        boolean z = false;
        GpController gpController3 = null;
        try {
            try {
                try {
                    this.mPayloadJson.setCnonce(FwsParamCreator.createRandomNumber());
                    int i = this.mActionType;
                    if (1 == i || 2 == i || 5 == i) {
                        this.mPayloadJson.setActionTypeCidList(i, this.mCid);
                    } else if (4 == i) {
                        this.mPayloadJson.setActionTypeCidList(i, this.mCid, this.mAppletInfo);
                    } else {
                        this.mPayloadJson.setActionTypeCidList(i, null);
                    }
                    getSecureElementInfo();
                    if (3 == this.mActionType) {
                        LogMgr.log(6, "001");
                        getDeviceInfo();
                        if (Property.isChipGP()) {
                            LogMgr.log(6, "002");
                            gpController2 = this.mChipHolder.getGpController();
                            try {
                                this.mAppletManager = new AppletManager(gpController2);
                                getGpChipInfo();
                            } catch (BadPropertyException e) {
                                e = e;
                                gpController3 = gpController2;
                                LogMgr.log(1, "804 : BadPropertyException");
                                onFinished(false, e.getType(), e.getMessage());
                                if (gpController3 == null) {
                                    return;
                                }
                                gpController3.closeChannel();
                                return;
                            } catch (MfiFelicaException e2) {
                                e = e2;
                                gpController3 = gpController2;
                                LogMgr.log(1, "801 : MfiFelicaException");
                                onFinished(false, e.getType(), e.getMessage());
                                if (gpController3 != null) {
                                    gpController3.closeChannel();
                                    return;
                                }
                                return;
                            } catch (GpException e3) {
                                e = e3;
                                gpController3 = gpController2;
                                LogMgr.log(1, "802 : GpException");
                                onFinished(false, e.getType(), e.getMessage());
                                if (gpController3 != null) {
                                    gpController3.closeChannel();
                                    return;
                                }
                                return;
                            } catch (InterruptedException unused) {
                                LogMgr.log(1, "803 : InterruptedException");
                                onFinished(false, 215, null);
                                if (gpController2 != null) {
                                    gpController2.closeChannel();
                                    return;
                                }
                                return;
                            } catch (JSONException e4) {
                                e = e4;
                                gpController3 = gpController2;
                                LogMgr.log(1, "800 : JSONException");
                                LogMgr.printStackTrace(2, e);
                                onFinished(false, 200, "Unknown error.");
                                if (gpController3 == null) {
                                    return;
                                }
                                gpController3.closeChannel();
                                return;
                            } catch (Exception unused2) {
                                gpController3 = gpController2;
                                LogMgr.log(1, "805 : Exception");
                                onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                                if (gpController3 == null) {
                                    return;
                                }
                                gpController3.closeChannel();
                                return;
                            }
                        } else {
                            LogMgr.log(6, "003");
                            getFaverChipInfo();
                            gpController2 = null;
                        }
                        this.mPayloadJson.setSeDetailInfo(this.mTsmConfiguration, this.mIdm, this.mContainerInfoJson, this.mAppletInfoListJson, this.mSdInfoListJson);
                        gpController3 = gpController2;
                    }
                    if (gpController3 != null) {
                        gpController3.closeChannel();
                    }
                    LogMgr.log(6, "004");
                    String strA = this.mJwsCreator.a(this.mPayloadJson);
                    this.mSignedJws = strA;
                    if (strA == null) {
                        onFinished(false, 200, "Unknown error.");
                        return;
                    }
                    FwsGetLinkageDataSubTask fwsGetLinkageDataSubTask = new FwsGetLinkageDataSubTask(0, this.mFwsClient);
                    setStoppableSubTask(fwsGetLinkageDataSubTask);
                    fwsGetLinkageDataSubTask.start();
                    AccessFwsTask.Result result = fwsGetLinkageDataSubTask.getResult2();
                    boolean z2 = result.isSuccess;
                    int i2 = result.errType;
                    String str = result.errMsg;
                    if (z2) {
                        try {
                            setResult(((LinkageDataResponseJson) result.response).optFirstLinkageDataList());
                            z = z2;
                        } catch (JSONException unused3) {
                            i2 = 202;
                            str = MfiClientCallbackConst.MSG_FORMAT_ERROR;
                        }
                    } else {
                        z = z2;
                    }
                    onFinished(z, i2, str);
                    LogMgr.log(6, "999");
                } catch (Throwable th) {
                    th = th;
                    if (gpController3 != null) {
                        gpController3.closeChannel();
                    }
                    throw th;
                }
            } catch (BadPropertyException e5) {
                e = e5;
            } catch (MfiFelicaException e6) {
                e = e6;
            } catch (GpException e7) {
                e = e7;
            } catch (InterruptedException unused4) {
                gpController2 = null;
            } catch (JSONException e8) {
                e = e8;
            } catch (Exception unused5) {
            }
        } catch (Throwable th2) {
            th = th2;
            gpController3 = gpController;
        }
    }

    private void getSecureElementInfo() throws JSONException {
        LogMgr.log(6, "000");
        this.mPayloadJson.setSeInfo(this.mDataManager.getSeInfo());
    }

    private void getDeviceInfo() throws BadPropertyException, JSONException {
        LogMgr.log(6, "000");
        this.mPayloadJson.setDeviceInfo(Property.getDeviceType(), Build.MODEL, Build.MANUFACTURER, new DeviceIdentificationData().get(), "02", Property.getCareerIdentifyCode(), Property.getMobileDeviceInformation());
    }

    private void getGpChipInfo() throws Throwable {
        LogMgr.log(6, "000");
        getTsmConfiguration();
        if (isGpChipPersonalized()) {
            SeInfoEx seInfoExCreateSeInfoEx = createSeInfoEx();
            getIdm(seInfoExCreateSeInfoEx);
            getContainerInfo(seInfoExCreateSeInfoEx, true);
        } else {
            getAppletInfoList();
            getSdInfoList(getSdKeyChangedModel());
        }
    }

    private boolean isGpChipPersonalized() throws InterruptedException, GpException {
        LogMgr.log(6, "000");
        return getSeAppletInfo(2).isPersonalized();
    }

    private void getContainerInfo(SeInfoEx seInfoEx, boolean isGpChip) throws JSONException, InterruptedException, GpException {
        String containerIssueInformation;
        LogMgr.log(6, "000");
        if (isGpChip) {
            containerIssueInformation = StringUtil.bytesToHexString(this.mAppletManager.getContainerIssueInfoDirectly(FlavorConst.MANAGEMENT_SYSTEM_AID));
        } else {
            containerIssueInformation = seInfoEx.getContainerIssueInformation();
        }
        this.mContainerInfoJson = this.mPayloadJson.createContainerInfoJson(seInfoEx.getIcCode(), seInfoEx.getContainerId(), containerIssueInformation);
    }

    private void getTsmConfiguration() throws InterruptedException, GpException {
        LogMgr.log(6, "000");
        ClsdAppletInfo clsdAppletInfo = (ClsdAppletInfo) getSdAppletInfo(3);
        this.mTsmConfiguration = StringUtil.bytesToHexString(clsdAppletInfo != null ? clsdAppletInfo.getSdManagementDataTsmSetting() : null);
        LogMgr.log(6, "999");
    }

    private int getSdKeyChangedModel() throws InterruptedException, GpException {
        LogMgr.log(6, "000");
        ClsdAppletInfo clsdAppletInfo = (ClsdAppletInfo) getSdAppletInfo(3);
        int sdKeyChangedModel = clsdAppletInfo != null ? clsdAppletInfo.getSdKeyChangedModel() & 255 : 0;
        LogMgr.log(6, "999");
        return sdKeyChangedModel;
    }

    private void getAppletInfoList() throws JSONException, InterruptedException, GpException {
        LogMgr.log(6, "000");
        this.mAppletInfoListJson = new JSONArray();
        this.mAppletInfoListJson.put(getAppletInfoJson(1));
        this.mAppletInfoListJson.put(getAppletInfoJson(2));
    }

    private void getSdInfoList(int sdKeyChangedModel) throws BadPropertyException, JSONException, InterruptedException, GpException {
        LogMgr.log(6, "000");
        if (sdKeyChangedModel == 1 || sdKeyChangedModel == 2) {
            this.mSdInfoListJson = new JSONArray();
            if (sdKeyChangedModel == 1) {
                this.mSdInfoListJson.put(getSdInfo(1));
            }
            this.mSdInfoListJson.put(getSdInfo(2));
            this.mSdInfoListJson.put(getSdInfo(3));
            this.mSdInfoListJson.put(getSdInfo(4));
            return;
        }
        if (sdKeyChangedModel >= 3) {
            if (!Property.isValidTopLevelSdInfo()) {
                LogMgr.log(2, "700 : topLevelSdType is -1 or topLevelSdAid is null");
                throw new BadPropertyException(200, ObfuscatedMsgUtil.executionPoint());
            }
            this.mSdInfoListJson = new JSONArray();
            this.mSdInfoListJson.put(getSdInfo(2));
            this.mSdInfoListJson.put(getSdInfo(3));
            this.mSdInfoListJson.put(getSdInfo(4));
            this.mSdInfoListJson.put(getSdInfo(5));
        }
    }

    private JSONObject getAppletInfoJson(int appletType) throws JSONException, InterruptedException, GpException {
        LogMgr.log(6, "000");
        SeAppletInfo seAppletInfo = getSeAppletInfo(appletType);
        return this.mPayloadJson.createAppletInfoJson(appletType, StringUtil.bytesToHexString(seAppletInfo.getBinaryVersion()), StringUtil.bytesToHexString(seAppletInfo.getAppletVersion()), StringUtil.bytesToHexString(seAppletInfo.getPackageKeyVersion()), StringUtil.bytesToHexString(seAppletInfo.getApplicationLifeCycleState()));
    }

    private JSONObject getSdInfo(int sdType) throws JSONException, InterruptedException, GpException {
        String strBytesToHexString;
        String strBytesToHexString2;
        String str;
        String str2;
        LogMgr.log(6, "000 sdType = " + sdType);
        SdAppletInfo sdAppletInfo = getSdAppletInfo(sdType);
        if (sdType == 1 || sdType == 2 || sdType == 3 || sdType == 4) {
            strBytesToHexString = StringUtil.bytesToHexString(sdAppletInfo.getKeyVersion());
            strBytesToHexString2 = StringUtil.bytesToHexString(sdAppletInfo.getSequenceCount());
        } else if (sdType == 5) {
            sdType = Property.getTopLevelSdType();
            strBytesToHexString = StringUtil.bytesToHexString(sdAppletInfo.getKeyVersion());
            strBytesToHexString2 = StringUtil.bytesToHexString(sdAppletInfo.getSequenceCount());
        } else {
            str2 = null;
            str = null;
            LogMgr.log(6, "999");
            return this.mPayloadJson.createSdInfoJson(sdType, str2, str);
        }
        String str3 = strBytesToHexString;
        str = strBytesToHexString2;
        str2 = str3;
        LogMgr.log(6, "999");
        return this.mPayloadJson.createSdInfoJson(sdType, str2, str);
    }

    private SeAppletInfo getSeAppletInfo(int appletType) throws InterruptedException, GpException {
        SeAppletInfo seAppletInfo;
        LogMgr.log(6, "000 + SE TYPE = " + appletType);
        if (appletType == 1) {
            if (this.mManagementAppletInfo == null) {
                this.mManagementAppletInfo = (ManagementSystemAppletInfo) this.mAppletManager.getAppletInfo(0);
            }
            seAppletInfo = this.mManagementAppletInfo;
        } else if (appletType == 2) {
            if (this.mSystem0AppletInfo == null) {
                this.mSystem0AppletInfo = (System0AppletInfo) this.mAppletManager.getAppletInfo(1);
            }
            seAppletInfo = this.mSystem0AppletInfo;
        } else {
            LogMgr.log(6, "700 Invalid SE TYPE = " + appletType);
            seAppletInfo = null;
        }
        LogMgr.log(6, "999");
        return seAppletInfo;
    }

    private SdAppletInfo getSdAppletInfo(int sdType) throws InterruptedException, GpException {
        SdAppletInfo sdAppletInfo;
        LogMgr.log(6, "000 + SD TYPE = " + sdType);
        if (sdType == 1) {
            if (this.mIsdAppletInfo == null) {
                this.mIsdAppletInfo = (IsdAppletInfo) this.mAppletManager.getAppletInfo(2);
            }
            sdAppletInfo = this.mIsdAppletInfo;
        } else if (sdType == 2) {
            if (this.mAmsdAppletInfo == null) {
                this.mAmsdAppletInfo = (AmsdAppletInfo) this.mAppletManager.getAppletInfo(3);
            }
            sdAppletInfo = this.mAmsdAppletInfo;
        } else if (sdType == 3) {
            if (this.mClsdAppletInfo == null) {
                this.mClsdAppletInfo = (ClsdAppletInfo) this.mAppletManager.getAppletInfo(4);
            }
            sdAppletInfo = this.mClsdAppletInfo;
        } else if (sdType == 4) {
            if (this.mSsdAppletInfo == null) {
                this.mSsdAppletInfo = (SsdAppletInfo) this.mAppletManager.getAppletInfo(5);
            }
            sdAppletInfo = this.mSsdAppletInfo;
        } else if (sdType == 5) {
            if (this.mTopLevelSdAppletInfo == null) {
                this.mTopLevelSdAppletInfo = (TopLevelSdAppletInfo) this.mAppletManager.getAppletInfo(6);
            }
            sdAppletInfo = this.mTopLevelSdAppletInfo;
        } else {
            LogMgr.log(6, "700 Invalid SD TYPE = " + sdType);
            sdAppletInfo = null;
        }
        LogMgr.log(6, "999");
        return sdAppletInfo;
    }

    private void getFaverChipInfo() throws Throwable {
        LogMgr.log(6, "000");
        SeInfoEx seInfoExCreateSeInfoEx = createSeInfoEx();
        getIdm(seInfoExCreateSeInfoEx);
        getContainerInfo(seInfoExCreateSeInfoEx, false);
    }

    private SeInfoEx createSeInfoEx() throws Throwable {
        MfiFelicaWrapper mfiFelicaWrapper;
        Throwable th;
        try {
            mfiFelicaWrapper = new MfiFelicaWrapper(this.mChipHolder);
            try {
                mfiFelicaWrapper.open();
                SeInfoEx seInfoExCreateSeInfoEx = this.mDataManager.createSeInfoEx(mfiFelicaWrapper);
                mfiFelicaWrapper.closeSilently();
                return seInfoExCreateSeInfoEx;
            } catch (Throwable th2) {
                th = th2;
                if (mfiFelicaWrapper != null) {
                    mfiFelicaWrapper.closeSilently();
                }
                throw th;
            }
        } catch (Throwable th3) {
            mfiFelicaWrapper = null;
            th = th3;
        }
    }

    private void getIdm(SeInfoEx seInfoEx) {
        this.mIdm = seInfoEx.getManagementAreaIDm();
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public void setResult(String[] result) {
        this.mLinkageDataList = result;
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public String[] getResult2() {
        return this.mLinkageDataList;
    }

    private class FwsGetLinkageDataSubTask extends AccessFwsTask<LinkageDataResponseJson> {
        private String mOperationId;
        int mSeqNum;

        FwsGetLinkageDataSubTask(int taskId, FwsClient fwsClient) {
            super(taskId, fwsClient);
            this.mSeqNum = 1;
            this.mOperationId = null;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected RequestJson createRequestJson() throws JSONException {
            this.mOperationId = FwsParamCreator.createOperationId();
            LinkageDataRequestJson linkageDataRequestJson = new LinkageDataRequestJson();
            linkageDataRequestJson.setRequestId(createRequestId());
            linkageDataRequestJson.setOperationId(this.mOperationId);
            if (GetLinkageDataListTask.this.mSignedJws != null) {
                linkageDataRequestJson.setLinkageDataListRequestToken(GetLinkageDataListTask.this.mSignedJws);
            }
            return linkageDataRequestJson;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String request) throws ProtocolException, HttpException {
            return this.mFwsClient.getLinkageDataScript(request, this.mSeqNum);
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/mfc/mfi/fws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public LinkageDataResponseJson convertResponse(String json) throws JSONException {
            return new LinkageDataResponseJson(json);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return GetLinkageDataListTask.VALID_RESULT_CODE_LIST_GET_LINKAGE_DATA_LIST;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_GET_LINKAGEDATA_SCRIPT.msg;
        }
    }
}
