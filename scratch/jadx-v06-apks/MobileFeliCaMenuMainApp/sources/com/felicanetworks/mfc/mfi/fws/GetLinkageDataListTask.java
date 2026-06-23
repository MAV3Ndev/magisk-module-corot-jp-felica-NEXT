package com.felicanetworks.mfc.mfi.fws;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import com.felicanetworks.mfc.mfi.BadPropertyException;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
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
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class GetLinkageDataListTask extends AsyncParentTaskBase<String[]> {
    private static final int AMSD = 2;
    private static final int CLSD = 3;
    private static final int ISD = 1;
    private static final int MANAGEMENT_SYSTEM = 1;
    private static final byte SD_KEY_CHANGE_MODEL_01 = 1;
    private static final byte SD_KEY_CHANGE_MODEL_02 = 2;
    private static final int SSD = 4;
    private static final int SYSYTEM_0 = 2;
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
    private String mTsmConfiguration;

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_LINKAGE_DATA_LIST = arrayList;
        arrayList.add("0000");
        VALID_RESULT_CODE_LIST_GET_LINKAGE_DATA_LIST.add(FwsConst.RESULT_CODE_RETRYREQUEST);
        VALID_RESULT_CODE_LIST_GET_LINKAGE_DATA_LIST.add(FwsConst.RESULT_ILLEGAL_ARGUMENTS);
        VALID_RESULT_CODE_LIST_GET_LINKAGE_DATA_LIST.add(FwsConst.RESULT_ILLEGAL_URL);
        VALID_RESULT_CODE_LIST_GET_LINKAGE_DATA_LIST.add(FwsConst.RESULT_ILLEGAL_REQUEST_TOKEN);
        VALID_RESULT_CODE_LIST_GET_LINKAGE_DATA_LIST.add(FwsConst.RESULT_INVALID_REQUEST_TOKEN);
        VALID_RESULT_CODE_LIST_GET_LINKAGE_DATA_LIST.add("4000");
        VALID_RESULT_CODE_LIST_GET_LINKAGE_DATA_LIST.add(FwsConst.RESULT_NOT_ACTIVE_CARD);
        VALID_RESULT_CODE_LIST_GET_LINKAGE_DATA_LIST.add(FwsConst.RESULT_NOT_EXIST_CARD);
        VALID_RESULT_CODE_LIST_GET_LINKAGE_DATA_LIST.add("9000");
        VALID_RESULT_CODE_LIST_GET_LINKAGE_DATA_LIST.add("9001");
        VALID_RESULT_CODE_LIST_GET_LINKAGE_DATA_LIST.add(FwsConst.RESULT_CONGESTED);
    }

    GetLinkageDataListTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, int i2, String[] strArr, DataManager dataManager, a aVar, FwsClient fwsClient, MfiChipHolder mfiChipHolder) {
        this(i, executorService, listener, i2, strArr, null, dataManager, aVar, fwsClient, mfiChipHolder);
    }

    GetLinkageDataListTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, int i2, String[] strArr, FelicaInstanceInfo felicaInstanceInfo, DataManager dataManager, a aVar, FwsClient fwsClient, MfiChipHolder mfiChipHolder) {
        super(i, executorService, listener);
        this.mPayloadJson = new LinkageDataListRequestTokenPayloadJson();
        this.mSignedJws = null;
        this.mTsmConfiguration = null;
        this.mIdm = null;
        this.mActionType = i2;
        this.mCid = strArr;
        this.mAppletInfo = felicaInstanceInfo;
        this.mDataManager = dataManager;
        this.mJwsCreator = aVar;
        this.mFwsClient = fwsClient;
        this.mChipHolder = mfiChipHolder;
    }

    /* JADX WARN: Not initialized variable reg: 7, insn: 0x0110: MOVE (r4 I:??[OBJECT, ARRAY]) = (r7 I:??[OBJECT, ARRAY]), block:B:60:0x0110 */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() throws Throwable {
        GpController gpController;
        GpController gpController2;
        LogMgr.log(6, "000");
        GpController gpController3 = null;
        boolean z = false;
        try {
            try {
                try {
                    this.mPayloadJson.setCnonce(FwsParamCreator.createRandomNumber());
                    if (1 == this.mActionType || 2 == this.mActionType) {
                        this.mPayloadJson.setActionTypeCidList(this.mActionType, this.mCid);
                    } else if (4 == this.mActionType) {
                        this.mPayloadJson.setActionTypeCidList(this.mActionType, this.mCid, this.mAppletInfo);
                    } else {
                        this.mPayloadJson.setActionTypeCidList(this.mActionType, null);
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
                                if (gpController3 != null) {
                                    gpController3.closeChannel();
                                    return;
                                }
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
                                if (gpController3 != null) {
                                    gpController3.closeChannel();
                                    return;
                                }
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
                    int i = result.errType;
                    String str = result.errMsg;
                    if (z2) {
                        try {
                            setResult(((LinkageDataResponseJson) result.response).optFirstLinkageDataList());
                            z = z2;
                        } catch (JSONException unused2) {
                            i = 202;
                            str = MfiClientCallbackConst.MSG_FORMAT_ERROR;
                        }
                    } else {
                        z = z2;
                    }
                    onFinished(z, i, str);
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
            } catch (InterruptedException unused3) {
                gpController2 = null;
            } catch (JSONException e8) {
                e = e8;
            }
        } catch (Throwable th2) {
            th = th2;
            gpController3 = gpController;
        }
    }

    private void getSecureElementInfo() {
        LogMgr.log(6, "000");
        try {
            this.mPayloadJson.setSeInfo(this.mDataManager.getSeInfo());
        } catch (JSONException e) {
            LogMgr.log(1, "800 : JSONException");
            LogMgr.printStackTrace(2, e);
            onFinished(false, 200, "Unknown error.");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v3, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v5 */
    private void getDeviceInfo() throws Throwable {
        String str;
        String string;
        LogMgr.log(6, "000");
        String deviceType = Property.getDeviceType();
        String str2 = Build.MODEL;
        String skuUrl = Property.getSkuUrl();
        String skuKeyValue = Property.getSkuKeyValue();
        LogMgr.log(7, "001 skuUrl=" + skuUrl);
        LogMgr.log(7, "002 skuKeyValue=" + skuKeyValue);
        String deviceIdentificationInfo = Property.getDeviceIdentificationInfo();
        LogMgr.log(7, "003 deviceIdentificationInfo=" + deviceIdentificationInfo);
        String str3 = Build.MANUFACTURER;
        ?? r9 = 0;
        if (skuUrl != null && skuKeyValue != null) {
            try {
                try {
                    try {
                        LogMgr.log(6, "003 Get SKU");
                        Cursor cursorQuery = FelicaAdapter.getInstance().getContentResolver().query(Uri.parse(skuUrl), null, null, null, null);
                        try {
                            if (cursorQuery == null) {
                                throw new BadPropertyException(238, MfiClientCallbackConst.MSG_DEVICE_IDENTIFICATION_DATA_NULL);
                            }
                            cursorQuery.moveToFirst();
                            int columnIndex = cursorQuery.getColumnIndex(skuKeyValue);
                            int type = cursorQuery.getType(columnIndex);
                            LogMgr.log(6, "004 SKU FieldType=" + type);
                            if (1 == type) {
                                string = Integer.toString(cursorQuery.getInt(columnIndex));
                            } else if (3 == type) {
                                string = cursorQuery.getString(columnIndex);
                            } else {
                                throw new BadPropertyException(238, null);
                            }
                            if (string == null) {
                                LogMgr.log(2, "700 deviceIdentificationData is null");
                                throw new BadPropertyException(238, null);
                            }
                            LogMgr.log(6, "005 deviceIdentificationData=" + string);
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            str = string;
                        } catch (BadPropertyException e) {
                            r9 = cursorQuery;
                            throw e;
                        } catch (Exception e2) {
                            e = e2;
                            LogMgr.log(2, "701 Fail to get SKU");
                            LogMgr.printStackTrace(7, e);
                            throw new BadPropertyException(238, null);
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (r9 != 0) {
                            r9.close();
                        }
                        throw th;
                    }
                } catch (BadPropertyException e3) {
                    throw e3;
                } catch (Exception e4) {
                    e = e4;
                }
            } catch (Throwable th2) {
                th = th2;
                r9 = skuUrl;
            }
        } else {
            if (skuUrl != null || skuKeyValue != null) {
                LogMgr.log(2, "702 Either skuUrl or skuKeyValue is null.");
                throw new BadPropertyException(200, ObfuscatedMsgUtil.executionPoint());
            }
            if (deviceIdentificationInfo != null) {
                LogMgr.log(6, "006 deviceIdentificationData=" + deviceIdentificationInfo);
                str = deviceIdentificationInfo;
            } else {
                str = null;
            }
        }
        this.mPayloadJson.setDeviceInfo(deviceType, str2, str3, str, "02", Property.getCareerIdentifyCode(), Property.getMobileDeviceInformation());
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

    private void getContainerInfo(SeInfoEx seInfoEx, boolean z) throws JSONException, InterruptedException, GpException {
        String containerIssueInformation;
        LogMgr.log(6, "000");
        if (z) {
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

    private byte getSdKeyChangedModel() throws InterruptedException, GpException {
        LogMgr.log(6, "000");
        ClsdAppletInfo clsdAppletInfo = (ClsdAppletInfo) getSdAppletInfo(3);
        byte sdKeyChangedModel = clsdAppletInfo != null ? clsdAppletInfo.getSdKeyChangedModel() : (byte) 0;
        LogMgr.log(6, "999");
        return sdKeyChangedModel;
    }

    private void getAppletInfoList() throws InterruptedException, GpException {
        LogMgr.log(6, "000");
        this.mAppletInfoListJson = new JSONArray();
        this.mAppletInfoListJson.put(getAppletInfoJson(1));
        this.mAppletInfoListJson.put(getAppletInfoJson(2));
    }

    private void getSdInfoList(byte b) throws JSONException, InterruptedException, GpException {
        LogMgr.log(6, "000");
        if (b == 1 || b == 2) {
            this.mSdInfoListJson = new JSONArray();
            if (b == 1) {
                this.mSdInfoListJson.put(getSdInfo(1));
            }
            this.mSdInfoListJson.put(getSdInfo(2));
            this.mSdInfoListJson.put(getSdInfo(3));
            this.mSdInfoListJson.put(getSdInfo(4));
        }
    }

    private JSONObject getAppletInfoJson(int i) throws InterruptedException, GpException {
        LogMgr.log(6, "000");
        SeAppletInfo seAppletInfo = getSeAppletInfo(i);
        try {
            return this.mPayloadJson.createAppletInfoJson(i, StringUtil.bytesToHexString(seAppletInfo.getBinaryVersion()), StringUtil.bytesToHexString(seAppletInfo.getAppletVersion()), StringUtil.bytesToHexString(seAppletInfo.getPackageKeyVersion()), StringUtil.bytesToHexString(seAppletInfo.getApplicationLifeCycleState()));
        } catch (JSONException e) {
            LogMgr.log(1, "800 : JSONException");
            LogMgr.printStackTrace(2, e);
            onFinished(false, 200, "Unknown error.");
            return null;
        }
    }

    private JSONObject getSdInfo(int i) throws JSONException, InterruptedException, GpException {
        String strBytesToHexString;
        LogMgr.log(6, "000 sdType = " + i);
        SdAppletInfo sdAppletInfo = getSdAppletInfo(i);
        String strBytesToHexString2 = null;
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            strBytesToHexString2 = StringUtil.bytesToHexString(sdAppletInfo.getKeyVersion());
            strBytesToHexString = StringUtil.bytesToHexString(sdAppletInfo.getSequenceCount());
        } else {
            strBytesToHexString = null;
        }
        LogMgr.log(6, "999");
        return this.mPayloadJson.createSdInfoJson(i, strBytesToHexString2, strBytesToHexString);
    }

    private SeAppletInfo getSeAppletInfo(int i) throws InterruptedException, GpException {
        SeAppletInfo seAppletInfo;
        LogMgr.log(6, "000 + SE TYPE = " + i);
        if (i == 1) {
            if (this.mManagementAppletInfo == null) {
                this.mManagementAppletInfo = (ManagementSystemAppletInfo) this.mAppletManager.getAppletInfo(0);
            }
            seAppletInfo = this.mManagementAppletInfo;
        } else if (i == 2) {
            if (this.mSystem0AppletInfo == null) {
                this.mSystem0AppletInfo = (System0AppletInfo) this.mAppletManager.getAppletInfo(1);
            }
            seAppletInfo = this.mSystem0AppletInfo;
        } else {
            LogMgr.log(6, "700 Invalid SE TYPE = " + i);
            seAppletInfo = null;
        }
        LogMgr.log(6, "999");
        return seAppletInfo;
    }

    private SdAppletInfo getSdAppletInfo(int i) throws InterruptedException, GpException {
        SdAppletInfo sdAppletInfo;
        LogMgr.log(6, "000 + SD TYPE = " + i);
        if (i == 1) {
            if (this.mIsdAppletInfo == null) {
                this.mIsdAppletInfo = (IsdAppletInfo) this.mAppletManager.getAppletInfo(2);
            }
            sdAppletInfo = this.mIsdAppletInfo;
        } else if (i == 2) {
            if (this.mAmsdAppletInfo == null) {
                this.mAmsdAppletInfo = (AmsdAppletInfo) this.mAppletManager.getAppletInfo(3);
            }
            sdAppletInfo = this.mAmsdAppletInfo;
        } else if (i == 3) {
            if (this.mClsdAppletInfo == null) {
                this.mClsdAppletInfo = (ClsdAppletInfo) this.mAppletManager.getAppletInfo(4);
            }
            sdAppletInfo = this.mClsdAppletInfo;
        } else if (i == 4) {
            if (this.mSsdAppletInfo == null) {
                this.mSsdAppletInfo = (SsdAppletInfo) this.mAppletManager.getAppletInfo(5);
            }
            sdAppletInfo = this.mSsdAppletInfo;
        } else {
            LogMgr.log(6, "700 Invalid SD TYPE = " + i);
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public void setResult(String[] strArr) {
        this.mLinkageDataList = strArr;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public String[] getResult2() {
        return this.mLinkageDataList;
    }

    private class FwsGetLinkageDataSubTask extends AccessFwsTask<LinkageDataResponseJson> {
        private String mOperationId;
        int mSeqNum;

        FwsGetLinkageDataSubTask(int i, FwsClient fwsClient) {
            super(i, fwsClient);
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
        protected String callFws(String str) throws ProtocolException, HttpException {
            return this.mFwsClient.getLinkageDataScript(str, this.mSeqNum);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public LinkageDataResponseJson convertResponse(String str) throws JSONException {
            return new LinkageDataResponseJson(str);
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
