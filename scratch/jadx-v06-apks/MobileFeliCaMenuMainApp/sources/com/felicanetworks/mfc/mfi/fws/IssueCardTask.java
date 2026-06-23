package com.felicanetworks.mfc.mfi.fws;

import android.os.Build;
import android.os.Looper;
import com.felicanetworks.mfc.mfi.CardIdentifiableBlockData;
import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import com.felicanetworks.mfc.mfi.CompleteCardInfo;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.EventBroadcastSender;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.MfiClientConst;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfc.mfi.MfiControlInfoCache;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.MfiFelicaWrapper;
import com.felicanetworks.mfc.mfi.MigratedServiceCache;
import com.felicanetworks.mfc.mfi.SeInfoEx;
import com.felicanetworks.mfc.mfi.fws.AccessFwsTask;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.CheckLegacyCardExistenceTask;
import com.felicanetworks.mfc.mfi.fws.FwsConst;
import com.felicanetworks.mfc.mfi.fws.ReadCiaBlockListTask;
import com.felicanetworks.mfc.mfi.fws.json.CardJson;
import com.felicanetworks.mfc.mfi.fws.json.CheckCardInformationRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.CheckCardInformationResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.CreateCardRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.CreateCardResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.GetIssueScriptRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.GetIssueScriptResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.GetMfiControlInfoResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.JwsObject;
import com.felicanetworks.mfc.mfi.fws.json.LinkageDataJson;
import com.felicanetworks.mfc.mfi.fws.json.RequestJson;
import com.felicanetworks.mfc.mfi.fws.json.TcapResultJson;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.AppletManager;
import com.felicanetworks.mfc.mfi.omapi.CrsManager;
import com.felicanetworks.mfc.mfi.omapi.FelicaInstanceInfo;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.omapi.InstanceStatus;
import com.felicanetworks.mfc.mfi.omapi.MigrateCardInstanceInfo;
import com.felicanetworks.mfc.mfi.omapi.SeAppletInfo;
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
class IssueCardTask extends AsyncParentTaskBase<IssueCard> {
    private static final String NOTIFICATIONTYPE_PUSH = "Push";
    private static final List<String> VALID_RESULT_CODE_LIST_CHECK_CARD_INFORMATION = new ArrayList();
    private static final List<String> VALID_RESULT_CODE_LIST_CREATE_CARD = new ArrayList();
    private static final List<String> VALID_RESULT_CODE_LIST_GET_ISSUE_SCRIPT = new ArrayList();
    private String mActionType;
    private EventBroadcastSender.CardIssueEvent mCardIssueEvent;
    private final MfiChipHolder mChipHolder;
    private final DataManager mDataManager;
    private final FwsClient mFwsClient;
    private CardIdentifiableInfo mIdentifiableInfo;
    private IssueCard mIssueCard;
    private final a mJwsCreator;
    private String mLinkageData;
    private final String mLoginTokenId;
    private String mMigrateCardIdm;
    private String mOtp;
    private String mServiceId;
    private String mServiceType;

    static class IssueCard {
        CardIdentifiableInfo cardIdInfo;
        CompleteCardInfo cardInfo;
        boolean isMigratedCard;

        IssueCard() {
        }
    }

    static {
        VALID_RESULT_CODE_LIST_CHECK_CARD_INFORMATION.add(FwsConst.RESULT_CODE_CONTINUE);
        VALID_RESULT_CODE_LIST_CHECK_CARD_INFORMATION.add(FwsConst.RESULT_CODE_RETRYREQUEST);
        VALID_RESULT_CODE_LIST_CHECK_CARD_INFORMATION.add(FwsConst.RESULT_ILLEGAL_ARGUMENTS);
        VALID_RESULT_CODE_LIST_CHECK_CARD_INFORMATION.add(FwsConst.RESULT_ILLEGAL_URL);
        VALID_RESULT_CODE_LIST_CHECK_CARD_INFORMATION.add(FwsConst.RESULT_ILLEGAL_LINKAGE_DATA);
        VALID_RESULT_CODE_LIST_CHECK_CARD_INFORMATION.add(FwsConst.RESULT_EXPIRED_LOGIN_TOKEN_ID);
        VALID_RESULT_CODE_LIST_CHECK_CARD_INFORMATION.add(FwsConst.RESULT_EXPIRED_LINKAGE_DATA);
        VALID_RESULT_CODE_LIST_CHECK_CARD_INFORMATION.add("4000");
        VALID_RESULT_CODE_LIST_CHECK_CARD_INFORMATION.add(FwsConst.RESULT_USED_LINKAGE_DATA);
        VALID_RESULT_CODE_LIST_CHECK_CARD_INFORMATION.add(FwsConst.RESULT_INVALID_ISSUE_INFORMATION);
        VALID_RESULT_CODE_LIST_CHECK_CARD_INFORMATION.add(FwsConst.RESULT_NOT_REISSUABLE);
        VALID_RESULT_CODE_LIST_CHECK_CARD_INFORMATION.add(FwsConst.RESULT_NOT_WHITELISTED);
        VALID_RESULT_CODE_LIST_CHECK_CARD_INFORMATION.add("9000");
        VALID_RESULT_CODE_LIST_CHECK_CARD_INFORMATION.add("9001");
        VALID_RESULT_CODE_LIST_CHECK_CARD_INFORMATION.add(FwsConst.RESULT_CONGESTED);
        VALID_RESULT_CODE_LIST_CREATE_CARD.add(FwsConst.RESULT_CODE_CONTINUE);
        VALID_RESULT_CODE_LIST_CREATE_CARD.add(FwsConst.RESULT_CODE_RETRYREQUEST);
        VALID_RESULT_CODE_LIST_CREATE_CARD.add(FwsConst.RESULT_ILLEGAL_ARGUMENTS);
        VALID_RESULT_CODE_LIST_CREATE_CARD.add(FwsConst.RESULT_ILLEGAL_URL);
        VALID_RESULT_CODE_LIST_CREATE_CARD.add(FwsConst.RESULT_ILLEGAL_LINKAGE_DATA);
        VALID_RESULT_CODE_LIST_CREATE_CARD.add(FwsConst.RESULT_EXPIRED_LOGIN_TOKEN_ID);
        VALID_RESULT_CODE_LIST_CREATE_CARD.add(FwsConst.RESULT_EXPIRED_LINKAGE_DATA);
        VALID_RESULT_CODE_LIST_CREATE_CARD.add("4000");
        VALID_RESULT_CODE_LIST_CREATE_CARD.add(FwsConst.RESULT_USED_LINKAGE_DATA);
        VALID_RESULT_CODE_LIST_CREATE_CARD.add(FwsConst.RESULT_INVALID_OTP);
        VALID_RESULT_CODE_LIST_CREATE_CARD.add(FwsConst.RESULT_INVALID_ADDITIONAL_INFORMATION);
        VALID_RESULT_CODE_LIST_CREATE_CARD.add(FwsConst.RESULT_INTERRUPTED);
        VALID_RESULT_CODE_LIST_CREATE_CARD.add(FwsConst.RESULT_USER_INFORMATION_VERIFICATION_FAILED);
        VALID_RESULT_CODE_LIST_CREATE_CARD.add(FwsConst.RESULT_NOT_WHITELISTED);
        VALID_RESULT_CODE_LIST_CREATE_CARD.add(FwsConst.RESULT_ISSUE_LIMIT_EXCEEDED);
        VALID_RESULT_CODE_LIST_CREATE_CARD.add(FwsConst.RESULT_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED);
        VALID_RESULT_CODE_LIST_CREATE_CARD.add(FwsConst.RESULT_ISSUE_LIMIT_PER_DEVICE_EXCEEDED);
        VALID_RESULT_CODE_LIST_CREATE_CARD.add(FwsConst.RESULT_INSUFFICIENT_CHIP_SPACE);
        VALID_RESULT_CODE_LIST_CREATE_CARD.add(FwsConst.RESULT_NOT_ISSUABLE_CHIP);
        VALID_RESULT_CODE_LIST_CREATE_CARD.add(FwsConst.RESULT_NOT_SUPPORTED_DEVICE);
        VALID_RESULT_CODE_LIST_CREATE_CARD.add("9000");
        VALID_RESULT_CODE_LIST_CREATE_CARD.add("9001");
        VALID_RESULT_CODE_LIST_CREATE_CARD.add(FwsConst.RESULT_CONGESTED);
        VALID_RESULT_CODE_LIST_CREATE_CARD.add(FwsConst.RESULT_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED_BY_MFI);
        VALID_RESULT_CODE_LIST_GET_ISSUE_SCRIPT.add("0000");
        VALID_RESULT_CODE_LIST_GET_ISSUE_SCRIPT.add(FwsConst.RESULT_CODE_CONTINUE);
        VALID_RESULT_CODE_LIST_GET_ISSUE_SCRIPT.add(FwsConst.RESULT_CODE_RETRYREQUEST);
        VALID_RESULT_CODE_LIST_GET_ISSUE_SCRIPT.add(FwsConst.RESULT_CODE_TIMEDRETRYREQUEST);
        VALID_RESULT_CODE_LIST_GET_ISSUE_SCRIPT.add(FwsConst.RESULT_ILLEGAL_ARGUMENTS);
        VALID_RESULT_CODE_LIST_GET_ISSUE_SCRIPT.add(FwsConst.RESULT_ILLEGAL_URL);
        VALID_RESULT_CODE_LIST_GET_ISSUE_SCRIPT.add(FwsConst.RESULT_ILLEGAL_ACCESS_TOKEN);
        VALID_RESULT_CODE_LIST_GET_ISSUE_SCRIPT.add(FwsConst.RESULT_INVALID_ACCESS_TOKEN);
        VALID_RESULT_CODE_LIST_GET_ISSUE_SCRIPT.add("4000");
        VALID_RESULT_CODE_LIST_GET_ISSUE_SCRIPT.add(FwsConst.RESULT_UNKNOWN);
        VALID_RESULT_CODE_LIST_GET_ISSUE_SCRIPT.add(FwsConst.RESULT_INSUFFICIENT_CHIP_SPACE);
        VALID_RESULT_CODE_LIST_GET_ISSUE_SCRIPT.add(FwsConst.RESULT_OTHER_SP_CARD_EXIST);
        VALID_RESULT_CODE_LIST_GET_ISSUE_SCRIPT.add("9000");
        VALID_RESULT_CODE_LIST_GET_ISSUE_SCRIPT.add("9001");
        VALID_RESULT_CODE_LIST_GET_ISSUE_SCRIPT.add(FwsConst.RESULT_CONGESTED);
    }

    private static class Parameters {
        private byte[] mAppletVersion;
        private boolean mAvailableArea;
        private byte[] mBinaryVersion;
        private List<CardIdentifiableBlockData> mBlockDataList;
        private FelicaInstanceInfo mDeletedLegacyCardInstanceInfo;
        private boolean mHasCid;
        private String mIssueIdm;
        private byte[] mIssueTargetAid;
        private String mNotificationType;
        private byte[] mPackageKeyVersion;
        private SeInfoEx mSeInfoEx;
        private boolean mSeparateCondition;
        private byte[] mSequenceCounter;

        private Parameters() {
        }
    }

    IssueCardTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, String str, String str2, String str3, FwsClient fwsClient, MfiChipHolder mfiChipHolder, DataManager dataManager, a aVar) throws MfiClientException {
        super(i, executorService, listener);
        this.mLoginTokenId = str;
        this.mLinkageData = str2;
        this.mFwsClient = fwsClient;
        this.mChipHolder = mfiChipHolder;
        this.mOtp = str3;
        try {
            LinkageDataJson linkageDataJson = new LinkageDataJson(new JwsObject(str2).getPayload());
            this.mIdentifiableInfo = linkageDataJson.getCardIdentifiableInfo();
            this.mServiceType = linkageDataJson.getServiceType();
            this.mServiceId = linkageDataJson.getServiceId();
            this.mActionType = linkageDataJson.getActionType();
            this.mMigrateCardIdm = linkageDataJson.getMigrateCardIdm();
        } catch (JSONException e) {
            LogMgr.log(2, "700 : JSONException " + e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        if (this.mIdentifiableInfo == null || ((ServiceTypeInfoUtil.SysType.isCommon(this.mServiceType) && this.mIdentifiableInfo.areaCode == -1) || ((FwsConst.ActionType.MIGRATE_CARD.equals(this.mActionType) && this.mMigrateCardIdm == null) || (FwsConst.ActionType.MIGRATE_CARD.equals(this.mActionType) && this.mServiceType == null)))) {
            LogMgr.log(1, "800 : linkageData is illegal.");
            throw new MfiClientException(100, MfiClientException.TYPE_ILLEGAL_LINKAGE_DATA, null);
        }
        this.mDataManager = dataManager;
        this.mJwsCreator = aVar;
    }

    private class LegacyCardExistenceChecker implements AsyncTaskBase.Listener {
        CheckLegacyCardExistenceTask mTask;

        private LegacyCardExistenceChecker() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void check(String str) {
            CheckLegacyCardExistenceTask checkLegacyCardExistenceTask = new CheckLegacyCardExistenceTask(0, IssueCardTask.this.mExecutor, this, IssueCardTask.this.mFwsClient, IssueCardTask.this.mChipHolder, IssueCardTask.this.mDataManager, IssueCardTask.this.mJwsCreator, str, false);
            this.mTask = checkLegacyCardExistenceTask;
            IssueCardTask.this.setStoppableSubTask(checkLegacyCardExistenceTask);
            this.mTask.start();
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase.Listener
        public void onFinishTask(TaskBase taskBase, boolean z, int i, String str) {
            CheckLegacyCardExistenceTask.Result result2 = this.mTask.getResult2();
            LogMgr.log(6, "000 isSuccess=" + z + ",errType=" + i + ",errMsg=" + str);
            if (z) {
                if (result2.exist) {
                    LogMgr.log(6, "002");
                    IssueCardTask.this.onFinished(false, 212, ObfuscatedMsgUtil.executionPoint());
                } else {
                    LogMgr.log(6, "003");
                    IssueCardTask.this.checkIssue();
                }
            } else {
                if (i != 6 && i != 8) {
                    if (i == 31) {
                        str = ObfuscatedMsgUtil.executionPoint();
                    } else if (i != 55 && i != 200 && i != 215 && i != 225 && i != 226) {
                        switch (i) {
                            case 202:
                            case 203:
                            case 204:
                            case 205:
                            case 206:
                                break;
                            default:
                                LogMgr.log(2, "700 Unexpected errType.");
                                str = ObfuscatedMsgUtil.executionPoint();
                                break;
                        }
                    }
                    i = 200;
                }
                LogMgr.log(6, "003 errType=" + i + ",errMsg=" + str);
                IssueCardTask.this.onFinished(false, i, str);
            }
            LogMgr.log(6, "999");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0064 A[Catch: all -> 0x00a3, MfiFelicaException -> 0x00a5, Merged into TryCatch #0 {all -> 0x00a3, MfiFelicaException -> 0x00a5, blocks: (B:13:0x0044, B:15:0x0054, B:17:0x0058, B:23:0x0064, B:25:0x0072, B:27:0x007c, B:29:0x0085, B:33:0x0095, B:36:0x009c, B:20:0x005e, B:41:0x00a6), top: B:62:0x0042 }] */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void run() {
        /*
            Method dump skipped, instruction units count: 253
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.fws.IssueCardTask.run():void");
    }

    private boolean isIndividualSp1(String str) throws JSONException {
        List<String> service1ServiceIdList = new GetMfiControlInfoResponseJson(MfiControlInfoCache.getInstance().getInfoCache()).getService1ServiceIdList();
        return service1ServiceIdList != null && service1ServiceIdList.contains(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkIssue() {
        try {
            if (ServiceTypeInfoUtil.IfType.isFullIf(this.mServiceType)) {
                FwsCheckCardInformationSubTask fwsCheckCardInformationSubTask = new FwsCheckCardInformationSubTask(0, this.mFwsClient);
                setStoppableSubTask(fwsCheckCardInformationSubTask);
                fwsCheckCardInformationSubTask.start();
            } else {
                Parameters createCardParamater = getCreateCardParamater();
                if (createCardParamater.mDeletedLegacyCardInstanceInfo == null) {
                    FwsCreateCardSubTask fwsCreateCardSubTask = new FwsCreateCardSubTask(0, this.mFwsClient, this.mChipHolder, createCardParamater);
                    setStoppableSubTask(fwsCreateCardSubTask);
                    fwsCreateCardSubTask.start();
                } else {
                    LogMgr.log(1, "800 Find DeletedLegacyCardInstanceInfo in simple I/F");
                    onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                    return;
                }
            }
            LogMgr.log(6, "999");
        } catch (MfiFelicaException e) {
            LogMgr.log(1, "801 MfiFelicaException:" + e.getMessage());
            onFinished(false, e.getType(), e.getMessage());
        } catch (FwsException e2) {
            LogMgr.log(1, "804 FwsEception:" + e2.getMessage());
            onFinished(false, e2.getType(), e2.getMessage());
        } catch (GpException e3) {
            LogMgr.log(1, "803 GpException:" + e3.getMessage());
            onFinished(false, e3.getType(), e3.getMessage());
        } catch (InterruptedException e4) {
            LogMgr.log(1, "802 InterruptedException:" + e4.getMessage());
            onFinished(false, 215, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Parameters getCreateCardParamater() throws InterruptedException, FwsException, MfiFelicaException, GpException {
        String message;
        int i = 6;
        LogMgr.log(6, "000");
        Parameters parameters = new Parameters();
        parameters.mAvailableArea = false;
        parameters.mBlockDataList = null;
        parameters.mSeparateCondition = false;
        MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(this.mChipHolder);
        try {
            try {
                mfiFelicaWrapper.open();
                parameters.mSeInfoEx = this.mDataManager.createSeInfoEx(mfiFelicaWrapper);
                mfiFelicaWrapper.close();
                mfiFelicaWrapper.closeSilently();
                if (ServiceTypeInfoUtil.SysType.isCommon(this.mServiceType)) {
                    ReadCiaBlockListTask readCiaBlockListTask = new ReadCiaBlockListTask(0, this.mIdentifiableInfo.getCacheableData(), this.mChipHolder);
                    setStoppableSubTask(readCiaBlockListTask);
                    readCiaBlockListTask.start();
                    ReadCiaBlockListTask.Result result2 = readCiaBlockListTask.getResult2();
                    if (result2.isSuccess) {
                        parameters.mAvailableArea = result2.readSeResult.isAvailableArea();
                        parameters.mBlockDataList = result2.readSeResult.getReadCiaBlockList();
                        if (Property.isChipGP()) {
                            parameters.mIssueTargetAid = FlavorConst.SYSTEM0_AID;
                            getIssueTargetInfo(parameters);
                        }
                    } else {
                        LogMgr.log(1, "800 : Failed to read identifiableInfo.");
                        throw new MfiFelicaException(result2.errType, result2.errMsg);
                    }
                } else if (!Property.isChipGP() && ServiceTypeInfoUtil.SysType.isPrivate(this.mServiceType)) {
                    if (FwsConst.ActionType.MIGRATE_CARD.equals(this.mActionType)) {
                        parameters.mIssueIdm = this.mMigrateCardIdm;
                    } else {
                        setTargetSystemIdm(parameters);
                    }
                } else if (Property.isChipGP() && ServiceTypeInfoUtil.SysType.isPrivate(this.mServiceType)) {
                    if (FwsConst.ActionType.MIGRATE_CARD.equals(this.mActionType)) {
                        MigrateCardInstanceInfo migrateCardInstanceAid = new AppletManager(this.mChipHolder.getGpController()).getMigrateCardInstanceAid(this.mIdentifiableInfo.systemCode, this.mMigrateCardIdm);
                        if (migrateCardInstanceAid != null) {
                            parameters.mIssueTargetAid = migrateCardInstanceAid.getAid();
                            parameters.mHasCid = migrateCardInstanceAid.hasCid();
                            getIssueTargetInfo(parameters);
                            parameters.mIssueIdm = this.mMigrateCardIdm;
                        } else {
                            LogMgr.log(1, "801 : migrated card Instance not found.");
                            throw new GpException(241, null, null);
                        }
                    } else {
                        byte[] emptyInstanceAid = new AppletManager(this.mChipHolder.getGpController()).getEmptyInstanceAid();
                        if (emptyInstanceAid != null) {
                            parameters.mIssueTargetAid = emptyInstanceAid;
                            getIssueTargetInfo(parameters);
                        } else {
                            try {
                                FelicaInstanceInfo deletedLegacyCardInstanceInfo = getDeletedLegacyCardInstanceInfo();
                                if (deletedLegacyCardInstanceInfo != null) {
                                    parameters.mDeletedLegacyCardInstanceInfo = deletedLegacyCardInstanceInfo;
                                } else {
                                    LogMgr.log(1, "802 : Not exist vacant applet instance.");
                                    throw new GpException(237, null, null);
                                }
                            } catch (FwsException e) {
                                int type = e.getType();
                                if (type == 6) {
                                    message = e.getMessage();
                                } else if (type == 8) {
                                    message = e.getMessage();
                                    i = 8;
                                } else if (type != 31 && type == 55) {
                                    message = e.getMessage();
                                    i = 55;
                                } else {
                                    String strExecutionPoint = ObfuscatedMsgUtil.executionPoint();
                                    message = strExecutionPoint;
                                    i = 200;
                                }
                                throw new FwsException(i, message);
                            }
                        }
                    }
                } else {
                    LogMgr.log(2, "700 : SeviceType Error.");
                }
                LogMgr.log(6, "999");
                return parameters;
            } catch (Throwable th) {
                mfiFelicaWrapper.closeSilently();
                throw th;
            }
        } catch (MfiFelicaException e2) {
            throw e2;
        }
    }

    private void getIssueTargetInfo(Parameters parameters) throws InterruptedException, GpException {
        LogMgr.log(6, "000");
        GpController gpController = null;
        try {
            try {
                gpController = this.mChipHolder.getGpController();
                AppletManager appletManager = new AppletManager(gpController);
                parameters.mSequenceCounter = ((SsdAppletInfo) appletManager.getAppletInfo(5)).getSequenceCount();
                SeAppletInfo seAppletInfo = appletManager.getSeAppletInfo(parameters.mIssueTargetAid);
                parameters.mAppletVersion = seAppletInfo.getAppletVersion();
                parameters.mBinaryVersion = seAppletInfo.getBinaryVersion();
                parameters.mPackageKeyVersion = appletManager.getAppletKeyVersion(parameters.mIssueTargetAid);
                LogMgr.log(6, "999");
            } catch (GpException e) {
                LogMgr.log(1, "801 : GpException");
                throw e;
            } catch (InterruptedException e2) {
                LogMgr.log(1, "800 : InterruptedException");
                throw e2;
            }
        } finally {
            if (gpController != null) {
                gpController.closeChannel();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTargetSystemIdm(Parameters parameters) throws MfiFelicaException {
        LogMgr.log(6, "000");
        MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(this.mChipHolder);
        try {
            mfiFelicaWrapper.open();
            mfiFelicaWrapper.select(65024);
            int[] systemCodeList = mfiFelicaWrapper.getSystemCodeList();
            boolean z = false;
            if (systemCodeList != null) {
                int length = systemCodeList.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    if (systemCodeList[i] == this.mIdentifiableInfo.getCacheableData().systemCode) {
                        z = true;
                        break;
                    }
                    i++;
                }
            }
            if (z) {
                LogMgr.log(6, "001 exist system");
                mfiFelicaWrapper.select(this.mIdentifiableInfo.getCacheableData().systemCode);
                parameters.mIssueIdm = StringUtil.bytesToHexString(mfiFelicaWrapper.getIDm());
            } else {
                LogMgr.log(6, "002 Not exist system");
                parameters.mSeparateCondition = true;
                parameters.mIssueIdm = null;
            }
            mfiFelicaWrapper.close();
            LogMgr.log(6, "999");
        } catch (MfiFelicaException e) {
            LogMgr.log(1, "800 MfiFelicaException:" + e.getMessage());
            mfiFelicaWrapper.closeSilently();
            throw e;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSuccessIssue(CardJson cardJson, DoScriptTask doScriptTask) throws JSONException {
        CompleteCardInfo cardInfo;
        boolean z;
        LogMgr.log(6, "000");
        if (cardJson == null) {
            LogMgr.log(2, "%s : %s", "700", "Card is null.");
            onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            return;
        }
        try {
            cardInfo = cardJson.getCardInfo(CardJson.CheckType.FWS_GET_ISSUE_SCRIPT, this.mDataManager.getSeInfo());
        } catch (JSONException e) {
            LogMgr.log(2, "%s JSONException:%s", "700", e.getMessage());
            LogMgr.printStackTrace(7, e);
            cardInfo = null;
        }
        if (cardInfo != null) {
            if (doScriptTask instanceof FwsGetIssueScriptSubTask) {
                try {
                    if (isIndividualSp1(cardInfo.getServiceId())) {
                        MigratedServiceCache.cacheMigratedService(FelicaAdapter.getInstance(), cardInfo.getServiceId());
                    }
                } catch (JSONException e2) {
                    LogMgr.log(2, "700 JSONException:", "700", e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                }
            }
            IssueCard issueCard = new IssueCard();
            if (cardInfo.statusIs(1, 0, CompleteCardInfo.Finish.NOT_YET)) {
                cardInfo.setPosition(2);
            }
            issueCard.isMigratedCard = FwsConst.ActionType.MIGRATE_CARD.equals(this.mActionType);
            if (Property.isChipGP() && ServiceTypeInfoUtil.SysType.isPrivate(cardInfo.getServiceType())) {
                try {
                    Map<Integer, String> cardCategoryMap = new GetMfiControlInfoResponseJson(MfiControlInfoCache.getInstance().getInfoCache()).getCardCategoryMap();
                    try {
                        CrsManager crsManager = new CrsManager(this.mChipHolder.getGpController());
                        Map<Integer, Map<String, InstanceStatus>> mapCreateCrsStatusTable = crsManager.createCrsStatusTable();
                        if (FwsConst.ActionType.MIGRATE_CARD.equals(this.mActionType)) {
                            Iterator<Map.Entry<Integer, Map<String, InstanceStatus>>> it = mapCreateCrsStatusTable.entrySet().iterator();
                            InstanceStatus instanceStatus = null;
                            while (it.hasNext() && (instanceStatus = it.next().getValue().get(cardInfo.getCid())) == null) {
                            }
                            if (instanceStatus != null) {
                                if (instanceStatus.isActivated()) {
                                    cardInfo.setPosition(0);
                                } else {
                                    cardInfo.setPosition(1);
                                }
                            } else {
                                onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                                return;
                            }
                        } else {
                            ArrayList arrayList = new ArrayList();
                            for (Map.Entry<Integer, String> entry : cardCategoryMap.entrySet()) {
                                if (entry.getValue().equals(cardInfo.getCardCategory())) {
                                    arrayList.add(entry.getKey());
                                }
                            }
                            ArrayList arrayList2 = new ArrayList();
                            ArrayList arrayList3 = new ArrayList();
                            Iterator it2 = arrayList.iterator();
                            while (it2.hasNext()) {
                                Map<String, InstanceStatus> map = mapCreateCrsStatusTable.get((Integer) it2.next());
                                if (map != null) {
                                    for (Map.Entry<String, InstanceStatus> entry2 : map.entrySet()) {
                                        if (entry2.getValue().isActivated()) {
                                            arrayList2.add(entry2.getValue().getAid());
                                            if (entry2.getValue().getSystemCode() == 3 && MfiClientConst.NO_CID_INSTANCE_KEY.equals(entry2.getValue().getCidString()) && new AppletManager(this.mChipHolder.getGpController()).isOnlyArea0(entry2.getValue().getAid()) && new AppletManager(this.mChipHolder.getGpController()).hasNoOwner(entry2.getValue().getAid())) {
                                                arrayList3.add(entry2.getValue().getAid());
                                            }
                                        }
                                    }
                                }
                            }
                            if (arrayList2.size() == 0) {
                                LogMgr.log(6, "001 Not exist activated Applet");
                                crsManager.activateApplet(StringUtil.hexToByteArray(cardInfo.getAppletInstanceAid()));
                                cardInfo.setPosition(0);
                            } else {
                                LogMgr.log(6, "002 Already exist activated Applet");
                                cardInfo.setPosition(1);
                                if (arrayList3.size() != 0) {
                                    LogMgr.log(6, "003 Deactivate already exist activated Applet");
                                    try {
                                        crsManager.deactivateApplet(arrayList3);
                                        z = true;
                                    } catch (GpException | InterruptedException unused) {
                                        cardInfo.setPosition(1);
                                        LogMgr.log(2, "700 Secure Element access error on deactivating previous AID.");
                                        z = false;
                                    }
                                    if (z && arrayList2.size() == arrayList3.size()) {
                                        crsManager.activateApplet(StringUtil.hexToByteArray(cardInfo.getAppletInstanceAid()));
                                        cardInfo.setPosition(0);
                                    }
                                }
                            }
                        }
                    } catch (GpException e3) {
                        LogMgr.log(1, "802 Secure Element access error.");
                        onFinished(false, e3.getType(), e3.getMessage());
                        return;
                    } catch (InterruptedException unused2) {
                        LogMgr.log(1, "801 cancel occured.");
                        onFinished(false, 215, null);
                        return;
                    }
                } catch (JSONException e4) {
                    LogMgr.printStackTrace(1, e4);
                    LogMgr.log(1, "800 failed to parse MfiControlInfoCache data.");
                    onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                    return;
                }
            }
            issueCard.cardInfo = cardInfo;
            this.mIdentifiableInfo.serviceId = cardInfo.getServiceId();
            issueCard.cardIdInfo = this.mIdentifiableInfo;
            setResult(issueCard);
            onFinished(true, 0, null);
        } else {
            onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
        }
        LogMgr.log(6, "999");
    }

    private FelicaInstanceInfo getDeletedLegacyCardInstanceInfo() throws InterruptedException, FwsException, GpException {
        LogMgr.log(6, "000");
        List<FelicaInstanceInfo> personalizedNoCidInstanceInfoList = new AppletManager(this.mChipHolder.getGpController()).getPersonalizedNoCidInstanceInfoList(3);
        LogMgr.log(6, "001 getPersonalizedNoCidInstanceInfoList size=" + personalizedNoCidInstanceInfoList.size());
        FelicaInstanceInfo felicaInstanceInfo = null;
        if (personalizedNoCidInstanceInfoList.size() == 1) {
            int iIdentifyService = IndividualSpChecker.identifyService(this.mChipHolder, 3, 72, 74, FlavorConst.INDIVIDUAL_SP_NODE_CODE_SERVICE1, FlavorConst.INDIVIDUAL_SP_NODE_CODE_SERVICE2);
            LogMgr.log(6, "002 identifyService=" + iIdentifyService);
            if (iIdentifyService == 0) {
                felicaInstanceInfo = personalizedNoCidInstanceInfoList.get(0);
            }
        } else if (personalizedNoCidInstanceInfoList.size() >= 2) {
            onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
        }
        LogMgr.log(6, "999");
        return felicaInstanceInfo;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void onFinished(boolean z, int i, String str) {
        LogMgr.log(6, "000");
        EventBroadcastSender.sendFinishEventBroadcast(FelicaAdapter.getInstance(), this.mCardIssueEvent, z);
        super.onFinished(z, i, str);
        LogMgr.log(6, "999");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public synchronized void setResult(IssueCard issueCard) {
        this.mIssueCard = issueCard;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public synchronized IssueCard getResult2() {
        return this.mIssueCard;
    }

    private class FwsCheckCardInformationSubTask extends AccessFwsTask<CheckCardInformationResponseJson> {
        private static final int FWS_SEQUENCE_NUMBER = 1;

        FwsCheckCardInformationSubTask(int i, FwsClient fwsClient) {
            super(i, fwsClient);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask, com.felicanetworks.mfc.mfi.fws.StoppableTaskBase, com.felicanetworks.mfc.mfi.fws.TaskBase
        public void start() {
            LogMgr.log(5, "000");
            super.start();
            if (isStopped()) {
                LogMgr.log(2, "701 Already has stopped.");
                IssueCardTask.this.onFinished(false, 215, null);
                return;
            }
            AccessFwsTask.Result result = getResult2();
            if (!result.isSuccess) {
                IssueCardTask.this.onFinished(false, result.errType, result.errMsg);
                return;
            }
            if (((CheckCardInformationResponseJson) result.response).isContinue()) {
                try {
                    String linakgeData = ((CheckCardInformationResponseJson) result.response).getLinakgeData();
                    IssueCardTask.this.mLinkageData = linakgeData;
                    LinkageDataJson linkageDataJson = new LinkageDataJson(new JwsObject(linakgeData).getPayload());
                    IssueCardTask.this.mIdentifiableInfo = linkageDataJson.getCardIdentifiableInfo();
                    IssueCardTask.this.mServiceType = linkageDataJson.getServiceType();
                    String notificationType = linkageDataJson.getNotificationType();
                    try {
                        Parameters createCardParamater = IssueCardTask.this.getCreateCardParamater();
                        createCardParamater.mNotificationType = notificationType;
                        if (createCardParamater.mDeletedLegacyCardInstanceInfo == null) {
                            FwsCreateCardSubTask fwsCreateCardSubTask = IssueCardTask.this.new FwsCreateCardSubTask(0, this.mFwsClient, IssueCardTask.this.mChipHolder, createCardParamater);
                            IssueCardTask.this.setStoppableSubTask(fwsCreateCardSubTask);
                            fwsCreateCardSubTask.start();
                        } else {
                            DeleteSystemTask deleteSystemTask = IssueCardTask.this.new DeleteSystemTask(0, createCardParamater.mDeletedLegacyCardInstanceInfo, createCardParamater);
                            IssueCardTask.this.setStoppableSubTask(deleteSystemTask);
                            deleteSystemTask.start();
                        }
                        LogMgr.log(5, "999");
                        return;
                    } catch (MfiFelicaException e) {
                        LogMgr.log(1, "800 MfiFelicaException:" + e.getMessage());
                        IssueCardTask.this.onFinished(false, e.getType(), e.getMessage());
                        return;
                    } catch (FwsException e2) {
                        LogMgr.log(1, "803 FwsEception:" + e2.getMessage());
                        IssueCardTask.this.onFinished(false, e2.getType(), e2.getMessage());
                        return;
                    } catch (GpException e3) {
                        LogMgr.log(1, "802 GpException:" + e3.getMessage());
                        IssueCardTask.this.onFinished(false, e3.getType(), e3.getMessage());
                        return;
                    } catch (InterruptedException e4) {
                        LogMgr.log(1, "801 InterruptedException:" + e4.getMessage());
                        IssueCardTask.this.onFinished(false, 215, null);
                        return;
                    }
                } catch (JSONException e5) {
                    LogMgr.log(2, "%s JSONException:%s", "700", e5.getMessage());
                    LogMgr.printStackTrace(7, e5);
                    IssueCardTask.this.onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
                    return;
                }
            }
            LogMgr.log(2, "700 Unexpected result code " + ((CheckCardInformationResponseJson) result.response).optResultCode());
            IssueCardTask.this.onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected RequestJson createRequestJson() throws JSONException {
            CheckCardInformationRequestJson checkCardInformationRequestJson = new CheckCardInformationRequestJson();
            checkCardInformationRequestJson.setRequestId(createRequestId());
            checkCardInformationRequestJson.setOperationId(FwsParamCreator.createOperationId());
            checkCardInformationRequestJson.setLoginTokenId(IssueCardTask.this.mLoginTokenId);
            checkCardInformationRequestJson.setSeInfo(IssueCardTask.this.mDataManager.getSeInfo(), Property.getSeType());
            checkCardInformationRequestJson.setLinkageData(IssueCardTask.this.mLinkageData);
            checkCardInformationRequestJson.setLanguageCode(FwsParamCreator.getLanguageCode());
            checkCardInformationRequestJson.setDeiveInfo(Build.MODEL, MfiClientConst.DEVICE_PLATFFORM_NAME, Build.VERSION.RELEASE, Property.getDeviceType());
            return checkCardInformationRequestJson;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String str) throws ProtocolException, HttpException {
            return this.mFwsClient.checkCardInformation(str, 1);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public CheckCardInformationResponseJson convertResponse(String str) throws JSONException {
            return new CheckCardInformationResponseJson(str);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return IssueCardTask.VALID_RESULT_CODE_LIST_CHECK_CARD_INFORMATION;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_CHECK_CARD_INFORMATION.msg;
        }
    }

    private class FwsCreateCardSubTask extends DoScriptTask<CreateCardResponseJson> {
        private final Parameters mParam;

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected boolean isTimedRetrievable() {
            return false;
        }

        FwsCreateCardSubTask(int i, FwsClient fwsClient, MfiChipHolder mfiChipHolder, Parameters parameters) {
            super(i, fwsClient, mfiChipHolder, IssueCardTask.this.mExecutor);
            this.mParam = parameters;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected void nextStart() {
            LogMgr.log(6, "000");
            onFinishTcap(null);
            LogMgr.log(6, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected RequestJson createRequestJson() throws JSONException {
            CreateCardRequestJson createCardRequestJson = new CreateCardRequestJson();
            createCardRequestJson.setRequestId(createRequestId());
            createCardRequestJson.setOperationId(this.mOperationId);
            if (this.mParam.mSeparateCondition && this.mAccessToken != null) {
                createCardRequestJson.setAccessToken(this.mAccessToken);
                this.mAccessToken = null;
            }
            createCardRequestJson.setLoginTokenId(IssueCardTask.this.mLoginTokenId);
            if (this.mSeqNum == 1) {
                createCardRequestJson.setLinkageData(IssueCardTask.this.mLinkageData);
                if (ServiceTypeInfoUtil.SysType.isCommon(IssueCardTask.this.mServiceType)) {
                    if (ServiceTypeInfoUtil.MultiCardType.isServersMultiple(IssueCardTask.this.mServiceType) || ServiceTypeInfoUtil.IfType.isSimpleIf(IssueCardTask.this.mServiceType)) {
                        createCardRequestJson.setReadSeResult(this.mParam.mAvailableArea, this.mParam.mBlockDataList);
                    } else {
                        createCardRequestJson.setReadSeResult(this.mParam.mAvailableArea, null);
                    }
                }
            }
            createCardRequestJson.setLanguageCode(FwsParamCreator.getLanguageCode());
            createCardRequestJson.setDeiveInfo(Build.MODEL, MfiClientConst.DEVICE_PLATFFORM_NAME, Build.VERSION.RELEASE, Property.getDeviceType());
            if (this.mSeqNum == 1) {
                if (ServiceTypeInfoUtil.SysType.isCommon(IssueCardTask.this.mServiceType)) {
                    Parameters parameters = this.mParam;
                    parameters.mIssueIdm = parameters.mSeInfoEx.getCommonAreaIDm();
                } else if (!Property.isChipGP() && ServiceTypeInfoUtil.SysType.isPrivate(IssueCardTask.this.mServiceType) && this.mParam.mSeparateCondition) {
                    Parameters parameters2 = this.mParam;
                    parameters2.mIssueIdm = parameters2.mSeInfoEx.getManagementAreaIDm();
                }
            }
            createCardRequestJson.setSeInfo(IssueCardTask.this.mDataManager.getSeInfo(), this.mParam.mSeInfoEx, Property.getSeType(), this.mParam.mIssueIdm);
            if (Property.isChipGP()) {
                if (ServiceTypeInfoUtil.IfType.isFullIf(IssueCardTask.this.mServiceType) && FwsConst.ActionType.MIGRATE_CARD.equals(IssueCardTask.this.mActionType)) {
                    createCardRequestJson.setAppletInstanceInfo(StringUtil.bytesToHexString(this.mParam.mIssueTargetAid), StringUtil.bytesToHexString(this.mParam.mBinaryVersion), StringUtil.bytesToHexString(this.mParam.mAppletVersion), StringUtil.bytesToHexString(this.mParam.mPackageKeyVersion), Boolean.valueOf(this.mParam.mHasCid));
                } else {
                    createCardRequestJson.setAppletInstanceInfo(StringUtil.bytesToHexString(this.mParam.mIssueTargetAid), StringUtil.bytesToHexString(this.mParam.mBinaryVersion), StringUtil.bytesToHexString(this.mParam.mAppletVersion), StringUtil.bytesToHexString(this.mParam.mPackageKeyVersion), null);
                }
                createCardRequestJson.setSequenceCounter(StringUtil.bytesToHexString(this.mParam.mSequenceCounter));
            }
            if (IssueCardTask.this.mOtp != null && ServiceTypeInfoUtil.IfType.isFullIf(IssueCardTask.this.mServiceType) && IssueCardTask.NOTIFICATIONTYPE_PUSH.equals(this.mParam.mNotificationType)) {
                createCardRequestJson.setOtp(IssueCardTask.this.mOtp);
            }
            if (this.mSeqNum == 1 && !Property.isChipGP()) {
                createCardRequestJson.setSeparateCondition(this.mParam.mSeparateCondition);
                if (this.mParam.mSeparateCondition) {
                    createCardRequestJson.setDivideParameter(Property.getCareerIdentifyCode(), Property.getMobileDeviceInformation());
                }
            }
            if (this.mTcapResult != null) {
                createCardRequestJson.setTcapResult(this.mTcapResult);
            }
            return createCardRequestJson;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String str) throws ProtocolException, HttpException {
            return this.mFwsClient.createCard(str, this.mSeqNum);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public CreateCardResponseJson convertResponse(String str) throws JSONException {
            return new CreateCardResponseJson(str);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return IssueCardTask.VALID_RESULT_CODE_LIST_CREATE_CARD;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        public void onSuccessScript(CreateCardResponseJson createCardResponseJson) throws JSONException {
            try {
                IssueCardTask.this.onSuccessIssue(createCardResponseJson.getCreatedCard(), this);
            } catch (JSONException e) {
                LogMgr.log(1, "800 JSONException:", e.getMessage());
                LogMgr.printStackTrace(7, e);
                IssueCardTask.this.onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            }
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        void onErrorScript(int i, String str) {
            IssueCardTask.this.onFinished(false, i, str);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected boolean isEnableRetryHttpAccess() {
            return ServiceTypeInfoUtil.IfType.isFullIf(IssueCardTask.this.mServiceType);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected void onFinishTcap(TcapResultJson tcapResultJson) {
            LogMgr.log(6, "000");
            this.mTcapResult = tcapResultJson;
            this.mSeqNum++;
            if (this.mSeqNum == 2 && this.mParam.mSeparateCondition) {
                try {
                    IssueCardTask.this.setTargetSystemIdm(this.mParam);
                    super.start();
                    return;
                } catch (MfiFelicaException e) {
                    LogMgr.log(1, "800 : MfiFelicaException");
                    IssueCardTask.this.onFinished(false, e.getType(), e.getMessage());
                    return;
                }
            }
            CreateCardResponseJson createCardResponseJson = (CreateCardResponseJson) getResult2().response;
            try {
                FwsGetIssueScriptSubTask fwsGetIssueScriptSubTask = IssueCardTask.this.new FwsGetIssueScriptSubTask(0, this.mFwsClient, IssueCardTask.this.mChipHolder, this.mOperationId, createCardResponseJson.getCreatedCard().getCardInfo(CardJson.CheckType.FWS_CREATE_CARD, IssueCardTask.this.mDataManager.getSeInfo()).getServiceId(), createCardResponseJson.optNextAccessToken(), this.mTcapResult);
                IssueCardTask.this.setStoppableSubTask(fwsGetIssueScriptSubTask);
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    fwsGetIssueScriptSubTask.start();
                } else {
                    LogMgr.log(6, "001 In main thread.");
                    fwsGetIssueScriptSubTask.executeStart();
                }
                LogMgr.log(6, "999");
            } catch (JSONException e2) {
                LogMgr.log(2, "%s JSONException:%s", "700", e2.getMessage());
                LogMgr.printStackTrace(7, e2);
                IssueCardTask.this.onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            }
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_CREATE_CARD.msg;
        }
    }

    private class FwsGetIssueScriptSubTask extends DoScriptTask<GetIssueScriptResponseJson> {
        private final String mServiceId;

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected boolean isTimedRetrievable() {
            return true;
        }

        FwsGetIssueScriptSubTask(int i, FwsClient fwsClient, MfiChipHolder mfiChipHolder, String str, String str2, String str3, TcapResultJson tcapResultJson) {
            super(i, fwsClient, mfiChipHolder, IssueCardTask.this.mExecutor, str);
            this.mServiceId = str2;
            this.mAccessToken = str3;
            this.mTcapResult = tcapResultJson;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected RequestJson createRequestJson() throws JSONException {
            GetIssueScriptRequestJson getIssueScriptRequestJson = new GetIssueScriptRequestJson();
            getIssueScriptRequestJson.setRequestId(createRequestId());
            getIssueScriptRequestJson.setOperationId(this.mOperationId);
            if (this.mAccessToken != null) {
                getIssueScriptRequestJson.setAccessToken(this.mAccessToken);
                this.mAccessToken = null;
            }
            if (this.mTcapResult != null) {
                getIssueScriptRequestJson.setTcapResult(this.mTcapResult);
                this.mTcapResult = null;
            }
            if (this.mApduResult != null) {
                getIssueScriptRequestJson.setApduResult(this.mApduResult);
                this.mApduResult = null;
            }
            return getIssueScriptRequestJson;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String str) throws ProtocolException, HttpException {
            return this.mFwsClient.getIssueScript(str, this.mServiceId, this.mSeqNum);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public GetIssueScriptResponseJson convertResponse(String str) throws JSONException {
            return new GetIssueScriptResponseJson(str);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return IssueCardTask.VALID_RESULT_CODE_LIST_GET_ISSUE_SCRIPT;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        public void onSuccessScript(GetIssueScriptResponseJson getIssueScriptResponseJson) throws JSONException {
            LogMgr.log(6, "000");
            try {
                CardJson issueCard = getIssueScriptResponseJson.getIssueCard();
                LogMgr.log(6, "999");
                IssueCardTask.this.onSuccessIssue(issueCard, this);
            } catch (JSONException e) {
                LogMgr.log(2, "%s JSONException:%s", "700", e.getMessage());
                LogMgr.printStackTrace(7, e);
                IssueCardTask.this.onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            }
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        void onErrorScript(int i, String str) {
            IssueCardTask.this.onFinished(false, i, str);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected boolean isEnableRetryHttpAccess() {
            return ServiceTypeInfoUtil.IfType.isFullIf(IssueCardTask.this.mServiceType);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_GET_ISSUE_SCRIPT.msg;
        }
    }

    private class DeleteSystemTask extends StoppableTaskBase<Void> implements AsyncTaskBase.Listener {
        private static final int TASK_ID_DELETE_CARD = 1;
        private static final int TASK_ID_GET_LINKAGE_DATA_LIST = 0;
        private final FelicaInstanceInfo mAppletInfo;
        private final Parameters mParameters;

        @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
        /* JADX INFO: renamed from: getResult, reason: avoid collision after fix types in other method */
        public Void getResult2() {
            return null;
        }

        DeleteSystemTask(int i, FelicaInstanceInfo felicaInstanceInfo, Parameters parameters) {
            super(i);
            this.mAppletInfo = felicaInstanceInfo;
            this.mParameters = parameters;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase, com.felicanetworks.mfc.mfi.fws.TaskBase
        public void start() {
            LogMgr.log(5, "000");
            GetLinkageDataListTask getLinkageDataListTask = new GetLinkageDataListTask(0, IssueCardTask.this.mExecutor, this, 4, null, this.mAppletInfo, IssueCardTask.this.mDataManager, IssueCardTask.this.mJwsCreator, IssueCardTask.this.mFwsClient, IssueCardTask.this.mChipHolder);
            IssueCardTask.this.setStoppableSubTask(getLinkageDataListTask);
            getLinkageDataListTask.start();
            LogMgr.log(5, "999");
        }

        private void deleteSystemOfDeletedLegacyCard(String str) {
            LogMgr.log(6, "000");
            try {
                DeleteCardTask deleteCardTask = new DeleteCardTask(1, IssueCardTask.this.mExecutor, this, IssueCardTask.this.mLoginTokenId, str, IssueCardTask.this.mFwsClient, IssueCardTask.this.mChipHolder, IssueCardTask.this.mDataManager, this.mAppletInfo.getAid());
                IssueCardTask.this.setStoppableSubTask(deleteCardTask);
                deleteCardTask.start();
            } catch (Exception e) {
                LogMgr.log(2, "700", e.getClass().getSimpleName(), e.getMessage());
                LogMgr.printStackTrace(7, e);
                IssueCardTask.this.onFinishDeleteSystem(false, this.mParameters, this.mAppletInfo.getAid(), 200, ObfuscatedMsgUtil.exExecutionPoint(e));
            }
            LogMgr.log(6, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase.Listener
        public void onFinishTask(TaskBase taskBase, boolean z, int i, String str) {
            LogMgr.log(5, "000 isSuccess=" + z);
            int taskId = taskBase.getTaskId();
            if (taskId != 0) {
                if (taskId == 1) {
                    this.mParameters.mDeletedLegacyCardInstanceInfo = null;
                    IssueCardTask.this.onFinishDeleteSystem(z, this.mParameters, this.mAppletInfo.getAid(), i, str);
                }
            } else if (z) {
                if (!(taskBase instanceof GetLinkageDataListTask)) {
                    IssueCardTask.this.onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                    return;
                }
                String[] result2 = ((GetLinkageDataListTask) taskBase).getResult2();
                if (result2 == null || result2.length != 1) {
                    IssueCardTask.this.onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                    return;
                }
                deleteSystemOfDeletedLegacyCard(result2[0]);
            } else {
                if (211 == i) {
                    LogMgr.log(2, "700 TYPE=", Integer.valueOf(i));
                    str = ObfuscatedMsgUtil.executionPoint();
                } else if (208 == i) {
                    LogMgr.log(2, "701 TYPE=", Integer.valueOf(i));
                    str = ObfuscatedMsgUtil.executionPoint();
                } else {
                    if (238 == i) {
                        LogMgr.log(2, "703 TYPE=", Integer.valueOf(i));
                    }
                    IssueCardTask.this.onFinished(false, i, str);
                }
                i = 200;
                IssueCardTask.this.onFinished(false, i, str);
            }
            LogMgr.log(5, "999");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFinishDeleteSystem(boolean z, Parameters parameters, byte[] bArr, int i, String str) {
        String str2;
        String strExecutionPoint;
        int i2 = i;
        LogMgr.log(6, "000");
        try {
            SeAppletInfo seAppletInfo = new AppletManager(this.mChipHolder.getGpController()).getSeAppletInfo(bArr);
            if (seAppletInfo == null) {
                onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                return;
            }
            if (!seAppletInfo.isPersonalized()) {
                parameters.mIssueTargetAid = bArr;
                getIssueTargetInfo(parameters);
                FwsCreateCardSubTask fwsCreateCardSubTask = new FwsCreateCardSubTask(0, this.mFwsClient, this.mChipHolder, parameters);
                setStoppableSubTask(fwsCreateCardSubTask);
                fwsCreateCardSubTask.start();
                LogMgr.log(6, "999");
                return;
            }
            if (z) {
                onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                return;
            }
            if (209 == i2) {
                LogMgr.log(2, "700 TYPE=", Integer.valueOf(i));
                strExecutionPoint = ObfuscatedMsgUtil.executionPoint();
            } else if (210 == i2) {
                LogMgr.log(2, "701 TYPE=", Integer.valueOf(i));
                strExecutionPoint = ObfuscatedMsgUtil.executionPoint();
            } else if (211 == i2) {
                LogMgr.log(2, "702 TYPE=", Integer.valueOf(i));
                strExecutionPoint = ObfuscatedMsgUtil.executionPoint();
            } else if (208 == i2) {
                LogMgr.log(2, "703 TYPE=", Integer.valueOf(i));
                strExecutionPoint = ObfuscatedMsgUtil.executionPoint();
            } else {
                str2 = str;
                onFinished(false, i2, str2);
            }
            str2 = strExecutionPoint;
            i2 = 200;
            onFinished(false, i2, str2);
        } catch (GpException e) {
            LogMgr.log(1, "802 GpException:" + e.getType() + "," + e.getMessage());
            onFinished(false, e.getType(), e.getMessage());
        } catch (InterruptedException e2) {
            LogMgr.log(1, "801 InterruptedException:" + e2.getMessage());
            onFinished(false, 215, null);
        } catch (Exception e3) {
            LogMgr.log(2, "803 " + e3.getClass().getSimpleName());
            LogMgr.printStackTrace(7, e3);
            onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e3));
        }
    }
}
