package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.Block;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.CyclicData;
import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.mfi.CardIdentifiableBlockData;
import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import com.felicanetworks.mfc.mfi.CompleteCardInfo;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.EventBroadcastSender;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.MfiFelicaWrapper;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.FwsConst;
import com.felicanetworks.mfc.mfi.fws.ReadCiaBlockListTask;
import com.felicanetworks.mfc.mfi.fws.json.CardJson;
import com.felicanetworks.mfc.mfi.fws.json.GetDeleteScriptRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.GetDeleteScriptResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.JwsObject;
import com.felicanetworks.mfc.mfi.fws.json.LinkageDataJson;
import com.felicanetworks.mfc.mfi.fws.json.RequestJson;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.AppletManager;
import com.felicanetworks.mfc.mfi.omapi.CrsManager;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.omapi.InstanceStatus;
import com.felicanetworks.mfc.mfi.omapi.SelectResponse;
import com.felicanetworks.mfc.mfi.omapi.SsdAppletInfo;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.ServiceTypeInfoUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
class DeleteCardTask extends AsyncParentTaskBase<String> {
    private static final String SERVICE_TYPE_FULL_PRIVATE_LOCAL = "MFPL0000";
    private static final List<String> VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT;
    private byte[] mAppletInstanceAid;
    private EventBroadcastSender.CardDeleteEvent mCardDeleteEvent;
    private CompleteCardInfo mCardInfo;
    private final MfiChipHolder mChipHolder;
    private final String mCid;
    private final DataManager mDataManager;
    private byte[] mDeactivateAid;
    private boolean mExecuteCheckOutsideTransitGate;
    private final FwsClient mFwsClient;
    private final CardIdentifiableInfo.Cache mIdentifiableInfo;
    private final String mLinkageData;
    private final String mLoginTokenId;
    private final boolean mNeedNotifyEvent;
    private String mOperationId;
    private String mSequenceCounter;
    private final String mServiceType;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public void setResult(String str) {
    }

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT = arrayList;
        arrayList.add("0000");
        VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT.add(FwsConst.RESULT_CODE_CONTINUE);
        VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT.add(FwsConst.RESULT_CODE_RETRYREQUEST);
        VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT.add(FwsConst.RESULT_ILLEGAL_ARGUMENTS);
        VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT.add(FwsConst.RESULT_ILLEGAL_URL);
        VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT.add(FwsConst.RESULT_ILLEGAL_ACCESS_TOKEN);
        VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT.add(FwsConst.RESULT_ILLEGAL_LINKAGE_DATA);
        VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT.add(FwsConst.RESULT_INVALID_ACCESS_TOKEN);
        VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT.add(FwsConst.RESULT_EXPIRED_LOGIN_TOKEN_ID);
        VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT.add(FwsConst.RESULT_EXPIRED_LINKAGE_DATA);
        VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT.add("4000");
        VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT.add(FwsConst.RESULT_UNKNOWN);
        VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT.add(FwsConst.RESULT_NOT_ACTIVE_CARD);
        VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT.add(FwsConst.RESULT_NOT_EXIST_CARD);
        VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT.add(FwsConst.RESULT_INVALID_KEY_VERSION);
        VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT.add("9000");
        VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT.add("9001");
        VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT.add(FwsConst.RESULT_CONGESTED);
    }

    DeleteCardTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, String str, String str2, CardIdentifiableInfo.Cache cache, String str3, FwsClient fwsClient, MfiChipHolder mfiChipHolder, DataManager dataManager, String str4, boolean z) {
        super(i, executorService, listener);
        this.mExecuteCheckOutsideTransitGate = false;
        this.mLoginTokenId = str;
        this.mCid = str2;
        this.mIdentifiableInfo = cache;
        this.mLinkageData = str3;
        this.mFwsClient = fwsClient;
        this.mChipHolder = mfiChipHolder;
        this.mDataManager = dataManager;
        this.mServiceType = str4;
        this.mNeedNotifyEvent = z;
    }

    DeleteCardTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, String str, String str2, CardIdentifiableInfo.Cache cache, String str3, FwsClient fwsClient, MfiChipHolder mfiChipHolder, DataManager dataManager, String str4, String str5) {
        this(i, executorService, listener, str, str2, cache, str3, fwsClient, mfiChipHolder, dataManager, str4, true);
        this.mOperationId = str5;
    }

    DeleteCardTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, String str, String str2, FwsClient fwsClient, MfiChipHolder mfiChipHolder, DataManager dataManager, byte[] bArr) {
        this(i, executorService, listener, str, (String) null, (CardIdentifiableInfo.Cache) null, str2, fwsClient, mfiChipHolder, dataManager, SERVICE_TYPE_FULL_PRIVATE_LOCAL, false);
        this.mDeactivateAid = bArr;
    }

    DeleteCardTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, String str, String str2, CardIdentifiableInfo.Cache cache, String str3, FwsClient fwsClient, MfiChipHolder mfiChipHolder, DataManager dataManager, String str4, boolean z, byte[] bArr) {
        this(i, executorService, listener, str, str2, cache, str3, fwsClient, mfiChipHolder, dataManager, str4, true);
        this.mExecuteCheckOutsideTransitGate = z;
        this.mAppletInstanceAid = bArr;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() throws Throwable {
        List<CardIdentifiableBlockData> readCiaBlockList;
        boolean z;
        FwsDeleteSubTask fwsDeleteSubTask;
        int i;
        LogMgr.log(6, "000");
        if (this.mNeedNotifyEvent) {
            this.mCardDeleteEvent = new EventBroadcastSender.CardDeleteEvent(this.mIdentifiableInfo.serviceId);
            EventBroadcastSender.sendStartEventBroadcast(FelicaAdapter.getInstance(), this.mCardDeleteEvent);
        }
        try {
            String actionType = new LinkageDataJson(new JwsObject(this.mLinkageData).getPayload()).getActionType();
            try {
                if (this.mExecuteCheckOutsideTransitGate && FwsConst.ActionType.DELETE_CARDS.equals(actionType) && (i = this.mIdentifiableInfo.systemCode) == 3) {
                    MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(this.mChipHolder);
                    if (checkExistTargetSystem(i, mfiFelicaWrapper)) {
                        if (isStopped()) {
                            LogMgr.log(2, "700 Already has stopped.");
                            onFinished(false, 215, null);
                            return;
                        } else if (!checkOutsideTransitGate(i, mfiFelicaWrapper)) {
                            return;
                        }
                    }
                }
                if (isStopped()) {
                    LogMgr.log(2, "701 Already has stopped.");
                    onFinished(false, 215, null);
                    return;
                }
                ReadCiaBlockListTask readCiaBlockListTask = new ReadCiaBlockListTask(0, this.mIdentifiableInfo, this.mChipHolder);
                try {
                    if (Property.isChipGP() && ServiceTypeInfoUtil.SupportMfi.isMfiSupported(this.mServiceType) && ServiceTypeInfoUtil.IfType.isFullIf(this.mServiceType) && ServiceTypeInfoUtil.SysType.isPrivate(this.mServiceType)) {
                        LogMgr.log(6, "001");
                        if (this.mDeactivateAid != null) {
                            deactivate(this.mDeactivateAid);
                        } else {
                            deactivate(this.mCid);
                        }
                    }
                    if (ServiceTypeInfoUtil.SysType.isCommon(this.mServiceType)) {
                        LogMgr.log(6, "002");
                        setStoppableSubTask(readCiaBlockListTask);
                        readCiaBlockListTask.start();
                        ReadCiaBlockListTask.Result result2 = readCiaBlockListTask.getResult2();
                        if (!result2.isSuccess) {
                            onFinished(false, result2.errType, result2.errMsg);
                            return;
                        } else {
                            boolean zIsAvailableArea = result2.readSeResult.isAvailableArea();
                            readCiaBlockList = result2.readSeResult.getReadCiaBlockList();
                            z = zIsAvailableArea;
                        }
                    } else {
                        readCiaBlockList = null;
                        z = false;
                    }
                    if (Property.isChipGP()) {
                        this.mSequenceCounter = getSequenceCounter();
                    }
                    if (this.mOperationId == null) {
                        fwsDeleteSubTask = new FwsDeleteSubTask(0, this.mFwsClient, this.mChipHolder, z, readCiaBlockList);
                    } else {
                        fwsDeleteSubTask = new FwsDeleteSubTask(0, this.mFwsClient, this.mChipHolder, z, readCiaBlockList, this.mOperationId);
                    }
                    setStoppableSubTask(fwsDeleteSubTask);
                    fwsDeleteSubTask.start();
                    LogMgr.log(6, "999");
                } catch (GpException e) {
                    LogMgr.log(1, "805 : GpException");
                    onFinished(false, e.getType(), e.getMessage());
                } catch (InterruptedException unused) {
                    LogMgr.log(1, "804 : InterruptedException");
                    onFinished(false, 215, null);
                } catch (Exception unused2) {
                    LogMgr.log(1, "806 : Exception");
                    onFinished(false, 200, "Unknown error.");
                }
            } catch (MfiFelicaException e2) {
                LogMgr.log(1, "801 : MfiFelicaException");
                onFinished(false, e2.getType(), e2.getMessage());
            } catch (GpException e3) {
                LogMgr.log(1, "803 : GpException");
                onFinished(false, e3.getType(), e3.getMessage());
            } catch (InterruptedException unused3) {
                LogMgr.log(1, "802 : InterruptedException");
                onFinished(false, 215, null);
            }
        } catch (Exception e4) {
            LogMgr.log(1, "800 : Exception");
            LogMgr.printStackTrace(7, e4);
            onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e4));
        }
    }

    private void deactivate(String str) throws Throwable {
        GpController gpController;
        LogMgr.log(6, "000");
        GpController gpController2 = null;
        InstanceStatus value = null;
        try {
            try {
                gpController = this.mChipHolder.getGpController();
            } catch (Throwable th) {
                th = th;
            }
        } catch (GpException e) {
            e = e;
        } catch (InterruptedException e2) {
            e = e2;
        }
        try {
            CrsManager crsManager = new CrsManager(gpController);
            Iterator<Map.Entry<Integer, Map<String, InstanceStatus>>> it = crsManager.createCrsStatusTable().entrySet().iterator();
            loop0: while (true) {
                if (!it.hasNext()) {
                    break;
                }
                for (Map.Entry<String, InstanceStatus> entry : it.next().getValue().entrySet()) {
                    if (entry.getKey().equals(str)) {
                        value = entry.getValue();
                        break loop0;
                    }
                }
            }
            if (value != null) {
                if (value.isActivated()) {
                    crsManager.deactivateApplet(value.getAid());
                }
            } else {
                LogMgr.log(2, "700 : Not exist card.");
            }
            if (gpController != null) {
                gpController.closeChannel();
            }
            LogMgr.log(6, "999");
        } catch (GpException e3) {
            e = e3;
            LogMgr.log(1, "801 : Secure Element access error.");
            throw e;
        } catch (InterruptedException e4) {
            e = e4;
            LogMgr.log(1, "800 : cancel occured.");
            throw e;
        } catch (Throwable th2) {
            th = th2;
            gpController2 = gpController;
            if (gpController2 != null) {
                gpController2.closeChannel();
            }
            throw th;
        }
    }

    private void deactivate(byte[] bArr) throws InterruptedException, GpException {
        LogMgr.log(6, "000");
        GpController gpController = null;
        try {
            try {
                try {
                    gpController = this.mChipHolder.getGpController();
                    CrsManager crsManager = new CrsManager(gpController);
                    if (new SelectResponse(gpController.select(bArr)).isActivated()) {
                        crsManager.deactivateApplet(bArr);
                    }
                    LogMgr.log(6, "999");
                } catch (GpException e) {
                    LogMgr.log(1, "801 : Secure Element access error.");
                    throw e;
                }
            } catch (InterruptedException e2) {
                LogMgr.log(1, "800 : cancel occured.");
                throw e2;
            }
        } finally {
            if (gpController != null) {
                gpController.closeChannel();
            }
        }
    }

    private String getSequenceCounter() throws InterruptedException, GpException {
        LogMgr.log(6, "000");
        GpController gpController = null;
        try {
            try {
                gpController = this.mChipHolder.getGpController();
                String strBytesToHexString = StringUtil.bytesToHexString(((SsdAppletInfo) new AppletManager(gpController).getAppletInfo(5)).getSequenceCount());
                LogMgr.log(6, "999");
                return strBytesToHexString;
            } catch (GpException e) {
                LogMgr.log(1, "801 : Secure Element access error.");
                throw e;
            } catch (InterruptedException e2) {
                LogMgr.log(1, "800 : cancel occured.");
                throw e2;
            }
        } finally {
            if (gpController != null) {
                gpController.closeChannel();
            }
        }
    }

    private boolean checkOutsideTransitGate(int i, MfiFelicaWrapper mfiFelicaWrapper) {
        Data[] dataArr;
        int type;
        String str;
        Data[] withCheckServiceAndBlock;
        boolean z;
        LogMgr.log(6, "000");
        try {
            try {
                try {
                    if (Property.isChipGP()) {
                        mfiFelicaWrapper.open();
                        mfiFelicaWrapper.selectWritableSystemWithCid(i, this.mCid);
                    } else {
                        mfiFelicaWrapper.select(i);
                    }
                    BlockList blockList = new BlockList();
                    blockList.add(new Block(FlavorConst.INDIVIDUAL_SP_SERVICE_CODE_TICKET_PUNCHING_STATUS, 0));
                    withCheckServiceAndBlock = mfiFelicaWrapper.readWithCheckServiceAndBlock(blockList);
                } catch (MfiFelicaException e) {
                    e = e;
                    dataArr = null;
                }
                try {
                    mfiFelicaWrapper.close();
                    mfiFelicaWrapper.closeSilently();
                    str = null;
                    z = false;
                    type = 200;
                } catch (MfiFelicaException e2) {
                    dataArr = withCheckServiceAndBlock;
                    e = e2;
                    LogMgr.log(1, "800 : MfiFelicaException");
                    type = e.getType();
                    String message = e.getMessage();
                    mfiFelicaWrapper.closeSilently();
                    str = message;
                    withCheckServiceAndBlock = dataArr;
                    z = true;
                }
                if (z) {
                    onFinished(false, type, str);
                    return false;
                }
                if (withCheckServiceAndBlock == null || withCheckServiceAndBlock.length == 0) {
                    LogMgr.log(2, "701 : dataList is null or empty.");
                    onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                    return false;
                }
                LogMgr.log(6, "001 : dataList.length : " + withCheckServiceAndBlock.length);
                if (!(withCheckServiceAndBlock[0] instanceof CyclicData)) {
                    LogMgr.log(2, "702 : Data type is invalid. dataType= " + withCheckServiceAndBlock[0].getType());
                    onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                    return false;
                }
                byte[] bytes = ((CyclicData) withCheckServiceAndBlock[0]).getBytes();
                LogMgr.log(6, "002 : dataList[0].getBytes() : ");
                LogMgr.logArray(6, bytes);
                if (bytes == null) {
                    LogMgr.log(2, "703 : Fail to get bytes.");
                    onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                    return false;
                }
                if (bytes.length < 2) {
                    LogMgr.log(2, "704 : bytes length is " + bytes.length);
                    onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                    return false;
                }
                boolean z2 = ((bytes[0] >> 7) & 1) == 1;
                LogMgr.log(6, "003 : inside transit gate flag :  " + z2);
                boolean z3 = ((bytes[1] >> 6) & 1) == 1;
                LogMgr.log(6, "004 : inside super express transit gate flag : " + z3);
                boolean z4 = z2 || z3;
                if (z4) {
                    LogMgr.log(2, "705 : Card is inside of the ticket gate.");
                    onFinished(false, 245, null);
                }
                LogMgr.log(6, "999");
                return !z4;
            } catch (FelicaException e3) {
                LogMgr.log(2, "700 : FelicaException(id : " + e3.getID() + " type : " + e3.getType() + ")");
                mfiFelicaWrapper.closeSilently();
                return true;
            }
        } catch (Throwable th) {
            mfiFelicaWrapper.closeSilently();
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00aa A[Catch: MfiFelicaException -> 0x00b3, TRY_LEAVE, TryCatch #1 {MfiFelicaException -> 0x00b3, blocks: (B:14:0x0061, B:16:0x0071, B:18:0x008a, B:24:0x00aa, B:21:0x00a4), top: B:30:0x0061 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean checkExistTargetSystem(int r10, com.felicanetworks.mfc.mfi.MfiFelicaWrapper r11) throws java.lang.InterruptedException, com.felicanetworks.mfc.mfi.MfiFelicaException, com.felicanetworks.mfc.mfi.omapi.GpException {
        /*
            r9 = this;
            r0 = 6
            java.lang.String r1 = "000"
            com.felicanetworks.mfc.util.LogMgr.log(r0, r1)
            boolean r1 = com.felicanetworks.mfc.mfi.mfw.i.fbl.Property.isChipGP()
            r2 = 1
            if (r1 == 0) goto L61
            com.felicanetworks.mfc.mfi.omapi.AppletManager r11 = new com.felicanetworks.mfc.mfi.omapi.AppletManager     // Catch: com.felicanetworks.mfc.mfi.omapi.GpException -> L41 java.lang.InterruptedException -> L48 java.lang.IllegalArgumentException -> L4f
            com.felicanetworks.mfc.mfi.MfiChipHolder r1 = r9.mChipHolder     // Catch: com.felicanetworks.mfc.mfi.omapi.GpException -> L41 java.lang.InterruptedException -> L48 java.lang.IllegalArgumentException -> L4f
            com.felicanetworks.mfc.mfi.omapi.GpController r1 = r1.getGpController()     // Catch: com.felicanetworks.mfc.mfi.omapi.GpException -> L41 java.lang.InterruptedException -> L48 java.lang.IllegalArgumentException -> L4f
            r11.<init>(r1)     // Catch: com.felicanetworks.mfc.mfi.omapi.GpException -> L41 java.lang.InterruptedException -> L48 java.lang.IllegalArgumentException -> L4f
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: com.felicanetworks.mfc.mfi.omapi.GpException -> L41 java.lang.InterruptedException -> L48 java.lang.IllegalArgumentException -> L4f
            r1.<init>()     // Catch: com.felicanetworks.mfc.mfi.omapi.GpException -> L41 java.lang.InterruptedException -> L48 java.lang.IllegalArgumentException -> L4f
            java.lang.String r3 = "001 : mCid:"
            r1.append(r3)     // Catch: com.felicanetworks.mfc.mfi.omapi.GpException -> L41 java.lang.InterruptedException -> L48 java.lang.IllegalArgumentException -> L4f
            java.lang.String r3 = r9.mCid     // Catch: com.felicanetworks.mfc.mfi.omapi.GpException -> L41 java.lang.InterruptedException -> L48 java.lang.IllegalArgumentException -> L4f
            r1.append(r3)     // Catch: com.felicanetworks.mfc.mfi.omapi.GpException -> L41 java.lang.InterruptedException -> L48 java.lang.IllegalArgumentException -> L4f
            java.lang.String r1 = r1.toString()     // Catch: com.felicanetworks.mfc.mfi.omapi.GpException -> L41 java.lang.InterruptedException -> L48 java.lang.IllegalArgumentException -> L4f
            com.felicanetworks.mfc.util.LogMgr.log(r0, r1)     // Catch: com.felicanetworks.mfc.mfi.omapi.GpException -> L41 java.lang.InterruptedException -> L48 java.lang.IllegalArgumentException -> L4f
            java.lang.String r1 = "002 : mSystemExistCheckAid:"
            com.felicanetworks.mfc.util.LogMgr.log(r0, r1)     // Catch: com.felicanetworks.mfc.mfi.omapi.GpException -> L41 java.lang.InterruptedException -> L48 java.lang.IllegalArgumentException -> L4f
            byte[] r1 = r9.mAppletInstanceAid     // Catch: com.felicanetworks.mfc.mfi.omapi.GpException -> L41 java.lang.InterruptedException -> L48 java.lang.IllegalArgumentException -> L4f
            com.felicanetworks.mfc.util.LogMgr.logArray(r0, r1)     // Catch: com.felicanetworks.mfc.mfi.omapi.GpException -> L41 java.lang.InterruptedException -> L48 java.lang.IllegalArgumentException -> L4f
            java.lang.String r1 = r9.mCid     // Catch: com.felicanetworks.mfc.mfi.omapi.GpException -> L41 java.lang.InterruptedException -> L48 java.lang.IllegalArgumentException -> L4f
            byte[] r3 = r9.mAppletInstanceAid     // Catch: com.felicanetworks.mfc.mfi.omapi.GpException -> L41 java.lang.InterruptedException -> L48 java.lang.IllegalArgumentException -> L4f
            boolean r10 = r11.checkExistTargetSystem(r10, r1, r3)     // Catch: com.felicanetworks.mfc.mfi.omapi.GpException -> L41 java.lang.InterruptedException -> L48 java.lang.IllegalArgumentException -> L4f
            goto Lad
        L41:
            r10 = move-exception
            java.lang.String r11 = "802 : GpException"
            com.felicanetworks.mfc.util.LogMgr.log(r2, r11)
            throw r10
        L48:
            r10 = move-exception
            java.lang.String r11 = "801 : InterruptedException"
            com.felicanetworks.mfc.util.LogMgr.log(r2, r11)
            throw r10
        L4f:
            java.lang.String r10 = "800 : IllegalArgumentException"
            com.felicanetworks.mfc.util.LogMgr.log(r2, r10)
            com.felicanetworks.mfc.mfi.omapi.GpException r10 = new com.felicanetworks.mfc.mfi.omapi.GpException
            r11 = 200(0xc8, float:2.8E-43)
            java.lang.String r0 = com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil.executionPoint()
            r1 = 0
            r10.<init>(r11, r0, r1)
            throw r10
        L61:
            r11.open()     // Catch: com.felicanetworks.mfc.mfi.MfiFelicaException -> Lb3
            r1 = 65039(0xfe0f, float:9.1139E-41)
            r11.select(r1)     // Catch: com.felicanetworks.mfc.mfi.MfiFelicaException -> Lb3
            int[] r1 = r11.getSystemCodeList()     // Catch: com.felicanetworks.mfc.mfi.MfiFelicaException -> Lb3
            r3 = 0
            if (r1 == 0) goto La7
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: com.felicanetworks.mfc.mfi.MfiFelicaException -> Lb3
            r4.<init>()     // Catch: com.felicanetworks.mfc.mfi.MfiFelicaException -> Lb3
            java.lang.String r5 = "003 : systemCodeList.length:"
            r4.append(r5)     // Catch: com.felicanetworks.mfc.mfi.MfiFelicaException -> Lb3
            int r5 = r1.length     // Catch: com.felicanetworks.mfc.mfi.MfiFelicaException -> Lb3
            r4.append(r5)     // Catch: com.felicanetworks.mfc.mfi.MfiFelicaException -> Lb3
            java.lang.String r4 = r4.toString()     // Catch: com.felicanetworks.mfc.mfi.MfiFelicaException -> Lb3
            com.felicanetworks.mfc.util.LogMgr.log(r0, r4)     // Catch: com.felicanetworks.mfc.mfi.MfiFelicaException -> Lb3
            int r4 = r1.length     // Catch: com.felicanetworks.mfc.mfi.MfiFelicaException -> Lb3
            r5 = 0
        L88:
            if (r5 >= r4) goto La7
            r6 = r1[r5]     // Catch: com.felicanetworks.mfc.mfi.MfiFelicaException -> Lb3
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: com.felicanetworks.mfc.mfi.MfiFelicaException -> Lb3
            r7.<init>()     // Catch: com.felicanetworks.mfc.mfi.MfiFelicaException -> Lb3
            java.lang.String r8 = "004 : systemCode:"
            r7.append(r8)     // Catch: com.felicanetworks.mfc.mfi.MfiFelicaException -> Lb3
            r7.append(r6)     // Catch: com.felicanetworks.mfc.mfi.MfiFelicaException -> Lb3
            java.lang.String r7 = r7.toString()     // Catch: com.felicanetworks.mfc.mfi.MfiFelicaException -> Lb3
            com.felicanetworks.mfc.util.LogMgr.log(r0, r7)     // Catch: com.felicanetworks.mfc.mfi.MfiFelicaException -> Lb3
            if (r6 != r10) goto La4
            r10 = 1
            goto La8
        La4:
            int r5 = r5 + 1
            goto L88
        La7:
            r10 = 0
        La8:
            if (r10 != 0) goto Lad
            r11.close()     // Catch: com.felicanetworks.mfc.mfi.MfiFelicaException -> Lb3
        Lad:
            java.lang.String r11 = "999"
            com.felicanetworks.mfc.util.LogMgr.log(r0, r11)
            return r10
        Lb3:
            r10 = move-exception
            java.lang.String r0 = "803 : MfiFelicaException"
            com.felicanetworks.mfc.util.LogMgr.log(r2, r0)
            r11.closeSilently()
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.fws.DeleteCardTask.checkExistTargetSystem(int, com.felicanetworks.mfc.mfi.MfiFelicaWrapper):boolean");
    }

    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public synchronized String getResult2() {
        return this.mCid;
    }

    protected void setCardInfo(CompleteCardInfo completeCardInfo) {
        this.mCardInfo = completeCardInfo;
    }

    public synchronized CompleteCardInfo getCardInfo() {
        return this.mCardInfo;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void onFinished(boolean z, int i, String str) {
        LogMgr.log(6, "000");
        if (this.mNeedNotifyEvent) {
            EventBroadcastSender.sendFinishEventBroadcast(FelicaAdapter.getInstance(), this.mCardDeleteEvent, z);
        }
        super.onFinished(z, i, str);
        LogMgr.log(6, "999");
    }

    private class FwsDeleteSubTask extends DoScriptTask<GetDeleteScriptResponseJson> {
        private final boolean mAvailableArea;
        private final List<CardIdentifiableBlockData> mBlockDataList;

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected boolean isTimedRetrievable() {
            return false;
        }

        FwsDeleteSubTask(int i, FwsClient fwsClient, MfiChipHolder mfiChipHolder, boolean z, List<CardIdentifiableBlockData> list) {
            super(i, fwsClient, mfiChipHolder, DeleteCardTask.this.mExecutor);
            this.mAvailableArea = z;
            this.mBlockDataList = list;
        }

        FwsDeleteSubTask(int i, FwsClient fwsClient, MfiChipHolder mfiChipHolder, boolean z, List<CardIdentifiableBlockData> list, String str) {
            super(i, fwsClient, mfiChipHolder, DeleteCardTask.this.mExecutor, str);
            this.mAvailableArea = z;
            this.mBlockDataList = list;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected RequestJson createRequestJson() throws JSONException {
            GetDeleteScriptRequestJson getDeleteScriptRequestJson = new GetDeleteScriptRequestJson();
            getDeleteScriptRequestJson.setRequestId(createRequestId());
            getDeleteScriptRequestJson.setOperationId(this.mOperationId);
            if (this.mAccessToken != null) {
                getDeleteScriptRequestJson.setAccessToken(this.mAccessToken);
                this.mAccessToken = null;
            }
            getDeleteScriptRequestJson.setLoginTokenId(DeleteCardTask.this.mLoginTokenId);
            if (this.mSeqNum == 1) {
                getDeleteScriptRequestJson.setCid(DeleteCardTask.this.mCid);
                getDeleteScriptRequestJson.setLinkageData(DeleteCardTask.this.mLinkageData);
                if (DeleteCardTask.this.mSequenceCounter != null && Property.isChipGP()) {
                    getDeleteScriptRequestJson.setSequenceCounter(DeleteCardTask.this.mSequenceCounter);
                }
                if (ServiceTypeInfoUtil.SysType.isCommon(DeleteCardTask.this.mServiceType)) {
                    if (ServiceTypeInfoUtil.MultiCardType.isServersMultiple(DeleteCardTask.this.mServiceType)) {
                        getDeleteScriptRequestJson.setReadSeResult(this.mAvailableArea, this.mBlockDataList);
                    } else {
                        getDeleteScriptRequestJson.setReadSeResult(this.mAvailableArea, null);
                    }
                }
            }
            if (this.mTcapResult != null) {
                getDeleteScriptRequestJson.setTcapResult(this.mTcapResult);
                this.mTcapResult = null;
            }
            if (this.mApduResult != null) {
                getDeleteScriptRequestJson.setApduResult(this.mApduResult);
                this.mApduResult = null;
            }
            return getDeleteScriptRequestJson;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String str) throws ProtocolException, HttpException {
            if (DeleteCardTask.this.mIdentifiableInfo != null) {
                return this.mFwsClient.getDeleteScript(str, DeleteCardTask.this.mIdentifiableInfo.serviceId, this.mSeqNum);
            }
            return this.mFwsClient.getDeleteScript(str, null, this.mSeqNum);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public GetDeleteScriptResponseJson convertResponse(String str) throws JSONException {
            return new GetDeleteScriptResponseJson(str);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return DeleteCardTask.VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        public void onSuccessScript(GetDeleteScriptResponseJson getDeleteScriptResponseJson) {
            CompleteCardInfo cardInfo;
            try {
                CardJson deletedCard = getDeleteScriptResponseJson.getDeletedCard();
                if (deletedCard != null) {
                    cardInfo = deletedCard.getCardInfo(CardJson.CheckType.FWS_GET_DELETE_SCRIPT, DeleteCardTask.this.mDataManager.getSeInfo());
                    cardInfo.setPosition(4);
                } else {
                    cardInfo = null;
                }
                DeleteCardTask.this.setCardInfo(cardInfo);
                DeleteCardTask.this.onFinished(true, 0, null);
            } catch (JSONException e) {
                LogMgr.log(1, "800 JSONException:" + e.getMessage());
                LogMgr.printStackTrace(7, e);
                DeleteCardTask.this.onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            }
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        void onErrorScript(int i, String str) {
            DeleteCardTask.this.onFinished(false, i, str);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected boolean isEnableRetryHttpAccess() {
            return ServiceTypeInfoUtil.IfType.isFullIf(DeleteCardTask.this.mServiceType);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_GET_DELETE_SCRIPT.msg;
        }
    }
}
