package com.felicanetworks.mfc.mfi.fws;

import android.os.Build;
import android.os.Looper;
import com.felicanetworks.mfc.BlockCountInformation;
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
import com.felicanetworks.mfc.mfi.WalletAppIdentifiableInfo;
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
import com.felicanetworks.mfc.mfi.fws.json.ErrorMessageContainableComServInfo;
import com.felicanetworks.mfc.mfi.fws.json.GetIssueScriptRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.GetIssueScriptResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.GetMfiControlInfoResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.JwsObject;
import com.felicanetworks.mfc.mfi.fws.json.LinkageDataJson;
import com.felicanetworks.mfc.mfi.fws.json.RequestJson;
import com.felicanetworks.mfc.mfi.fws.json.ResponseJson;
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

/* JADX INFO: loaded from: classes3.dex */
class IssueCardTask extends AsyncParentTaskBase<IssueCard> {
    private static final String NOTIFICATIONTYPE_PUSH = "Push";
    private static final List<String> VALID_RESULT_CODE_LIST_CHECK_CARD_INFORMATION;
    private static final List<String> VALID_RESULT_CODE_LIST_CREATE_CARD;
    private static final List<String> VALID_RESULT_CODE_LIST_GET_ISSUE_SCRIPT;
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
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_CHECK_CARD_INFORMATION = arrayList;
        ArrayList arrayList2 = new ArrayList();
        VALID_RESULT_CODE_LIST_CREATE_CARD = arrayList2;
        ArrayList arrayList3 = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_ISSUE_SCRIPT = arrayList3;
        arrayList.add(FwsConst.RESULT_CODE_CONTINUE);
        arrayList.add("1000");
        arrayList.add("2000");
        arrayList.add(FwsConst.RESULT_ILLEGAL_URL);
        arrayList.add(FwsConst.RESULT_ILLEGAL_LINKAGE_DATA);
        arrayList.add("3001");
        arrayList.add(FwsConst.RESULT_EXPIRED_LINKAGE_DATA);
        arrayList.add("4000");
        arrayList.add(FwsConst.RESULT_USED_LINKAGE_DATA);
        arrayList.add(FwsConst.RESULT_INVALID_ISSUE_INFORMATION);
        arrayList.add(FwsConst.RESULT_NOT_REISSUABLE);
        arrayList.add(FwsConst.RESULT_NOT_WHITELISTED);
        arrayList.add("9000");
        arrayList.add("9001");
        arrayList.add("9005");
        arrayList2.add(FwsConst.RESULT_CODE_CONTINUE);
        arrayList2.add("1000");
        arrayList2.add("2000");
        arrayList2.add(FwsConst.RESULT_ILLEGAL_URL);
        arrayList2.add(FwsConst.RESULT_ILLEGAL_LINKAGE_DATA);
        arrayList2.add("3001");
        arrayList2.add(FwsConst.RESULT_EXPIRED_LINKAGE_DATA);
        arrayList2.add("4000");
        arrayList2.add(FwsConst.RESULT_USED_LINKAGE_DATA);
        arrayList2.add(FwsConst.RESULT_INVALID_OTP);
        arrayList2.add(FwsConst.RESULT_INVALID_ADDITIONAL_INFORMATION);
        arrayList2.add(FwsConst.RESULT_INTERRUPTED);
        arrayList2.add(FwsConst.RESULT_USER_INFORMATION_VERIFICATION_FAILED);
        arrayList2.add(FwsConst.RESULT_NOT_WHITELISTED);
        arrayList2.add(FwsConst.RESULT_ISSUE_LIMIT_EXCEEDED);
        arrayList2.add(FwsConst.RESULT_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED);
        arrayList2.add(FwsConst.RESULT_ISSUE_LIMIT_PER_DEVICE_EXCEEDED);
        arrayList2.add(FwsConst.RESULT_INSUFFICIENT_CHIP_SPACE);
        arrayList2.add(FwsConst.RESULT_NOT_ISSUABLE_CHIP);
        arrayList2.add(FwsConst.RESULT_NOT_SUPPORTED_DEVICE);
        arrayList2.add("9000");
        arrayList2.add("9001");
        arrayList2.add("9005");
        arrayList2.add(FwsConst.RESULT_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED_BY_MFI);
        arrayList3.add("0000");
        arrayList3.add(FwsConst.RESULT_CODE_CONTINUE);
        arrayList3.add("1000");
        arrayList3.add(FwsConst.RESULT_CODE_TIMEDRETRYREQUEST);
        arrayList3.add("2000");
        arrayList3.add(FwsConst.RESULT_ILLEGAL_URL);
        arrayList3.add(FwsConst.RESULT_ILLEGAL_ACCESS_TOKEN);
        arrayList3.add("3000");
        arrayList3.add("4000");
        arrayList3.add("4001");
        arrayList3.add(FwsConst.RESULT_INSUFFICIENT_CHIP_SPACE);
        arrayList3.add(FwsConst.RESULT_OTHER_SP_CARD_EXIST);
        arrayList3.add("9000");
        arrayList3.add("9001");
        arrayList3.add("9005");
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

    IssueCardTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, String loginTokenId, String linkageData, String otp, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager, a jwsCreator) throws MfiClientException {
        super(taskId, executor, listener);
        this.mLoginTokenId = loginTokenId;
        this.mLinkageData = linkageData;
        this.mFwsClient = fwsClient;
        this.mChipHolder = chipHolder;
        this.mOtp = otp;
        try {
            LinkageDataJson linkageDataJson = new LinkageDataJson(new JwsObject(linkageData).getPayload());
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
        this.mJwsCreator = jwsCreator;
    }

    private class LegacyCardExistenceChecker implements AsyncTaskBase.Listener {
        CheckLegacyCardExistenceTask mTask;

        private LegacyCardExistenceChecker() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void check(String serviceId) {
            CheckLegacyCardExistenceTask checkLegacyCardExistenceTask = new CheckLegacyCardExistenceTask(0, IssueCardTask.this.mExecutor, this, IssueCardTask.this.mFwsClient, IssueCardTask.this.mChipHolder, IssueCardTask.this.mDataManager, IssueCardTask.this.mJwsCreator, serviceId, false);
            this.mTask = checkLegacyCardExistenceTask;
            IssueCardTask.this.setStoppableSubTask(checkLegacyCardExistenceTask);
            this.mTask.start();
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase.Listener
        public void onFinishTask(TaskBase task, boolean isSuccess, int errType, String errMsg) {
            CheckLegacyCardExistenceTask.Result result2 = this.mTask.getResult2();
            LogMgr.log(6, "000 isSuccess=" + isSuccess + ",errType=" + errType + ",errMsg=" + errMsg);
            if (isSuccess) {
                if (result2.exist) {
                    LogMgr.log(6, "002");
                    IssueCardTask.this.onFinished(false, 212, ObfuscatedMsgUtil.executionPoint());
                } else {
                    LogMgr.log(6, "003");
                    IssueCardTask.this.checkIssue();
                }
            } else {
                if (errType != 6 && errType != 8) {
                    if (errType == 31) {
                        errMsg = ObfuscatedMsgUtil.executionPoint();
                    } else if (errType != 55 && errType != 200 && errType != 215 && errType != 225 && errType != 226) {
                        switch (errType) {
                            case 202:
                            case 203:
                            case 204:
                            case 205:
                            case 206:
                                break;
                            default:
                                LogMgr.log(2, "700 Unexpected errType.");
                                errMsg = ObfuscatedMsgUtil.executionPoint();
                                break;
                        }
                    }
                    errType = 200;
                }
                LogMgr.log(6, "003 errType=" + errType + ",errMsg=" + errMsg);
                IssueCardTask.this.onFinished(false, errType, errMsg);
            }
            LogMgr.log(6, "999");
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [492=4] */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() {
        String str;
        LogMgr.log(6, "000");
        if (this.mServiceId != null) {
            this.mCardIssueEvent = new EventBroadcastSender.CardIssueEvent(this.mServiceId, WalletAppIdentifiableInfo.getInstance().getPackageName());
            EventBroadcastSender.sendStartEventBroadcast(FelicaAdapter.getInstance(), this.mCardIssueEvent);
        }
        if (!Property.isChipGP()) {
            MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(this.mChipHolder);
            try {
                if (StringUtil.versionCompare(mfiFelicaWrapper.getMFCVersion(FelicaAdapter.getInstance()), MfiClientConst.MFC_VERSION_FAVER3) != -1) {
                    try {
                        if (this.mIdentifiableInfo.systemCode == 3) {
                            mfiFelicaWrapper.open();
                            mfiFelicaWrapper.select(65039);
                            int[] systemCodeList = mfiFelicaWrapper.getSystemCodeList();
                            if (systemCodeList != null) {
                                int length = systemCodeList.length;
                                int i = 0;
                                while (true) {
                                    if (i >= length) {
                                        break;
                                    }
                                    if (systemCodeList[i] == 3) {
                                        mfiFelicaWrapper.select(3);
                                        BlockCountInformation[] blockCountInformation = mfiFelicaWrapper.getBlockCountInformation(new int[]{0});
                                        if (blockCountInformation.length > 0 && blockCountInformation[0].getAssignedBlocks() < 345) {
                                            mfiFelicaWrapper.close();
                                            if (!isStopped()) {
                                                onFinished(false, 242, null);
                                                return;
                                            } else {
                                                LogMgr.log(2, "701 Already has stopped.");
                                                onFinished(false, 215, null);
                                                return;
                                            }
                                        }
                                    } else {
                                        i++;
                                    }
                                }
                            }
                            mfiFelicaWrapper.close();
                        }
                    } catch (MfiFelicaException e) {
                        onFinished(false, e.getType(), e.getMessage());
                        return;
                    } finally {
                        mfiFelicaWrapper.closeSilently();
                    }
                }
            } catch (MfiFelicaException unused) {
                LogMgr.log(2, "700 failed to get mfc version.");
                onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                return;
            }
        }
        try {
            if (FwsConst.ActionType.MIGRATE_CARD.equals(this.mActionType) || !Property.isChipGP() || (str = this.mServiceId) == null) {
                checkIssue();
            } else if (isIndividualSp1(str)) {
                new LegacyCardExistenceChecker().check(this.mServiceId);
            } else {
                checkIssue();
            }
        } catch (JSONException unused2) {
            onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
        }
    }

    private boolean isIndividualSp1(String serviceId) throws JSONException {
        List<String> service1ServiceIdList = new GetMfiControlInfoResponseJson(MfiControlInfoCache.getInstance().getInfoCache()).getService1ServiceIdList();
        return service1ServiceIdList != null && service1ServiceIdList.contains(serviceId);
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
                try {
                    if (createCardParamater.mDeletedLegacyCardInstanceInfo == null) {
                        FwsCreateCardSubTask fwsCreateCardSubTask = new FwsCreateCardSubTask(0, this.mFwsClient, this.mChipHolder, createCardParamater);
                        setStoppableSubTask(fwsCreateCardSubTask);
                        fwsCreateCardSubTask.start();
                    } else {
                        LogMgr.log(1, "800 Find DeletedLegacyCardInstanceInfo in simple I/F");
                        onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                        return;
                    }
                } catch (MfiFelicaException e) {
                    e = e;
                    LogMgr.log(1, "801 MfiFelicaException:" + e.getMessage());
                    onFinished(false, e.getType(), e.getMessage());
                    return;
                } catch (FwsException e2) {
                    e = e2;
                    LogMgr.log(1, "804 FwsEception:" + e.getMessage());
                    onFinished(false, e.getType(), e.getMessage());
                    return;
                } catch (GpException e3) {
                    e = e3;
                    LogMgr.log(1, "803 GpException:" + e.getMessage());
                    onFinished(false, e.getType(), e.getMessage());
                    return;
                } catch (InterruptedException e4) {
                    e = e4;
                    LogMgr.log(1, "802 InterruptedException:" + e.getMessage());
                    onFinished(false, 215, null);
                    return;
                } catch (Exception e5) {
                    e = e5;
                    LogMgr.log(2, "805 Exception:" + e.getClass().getSimpleName(), e.getMessage());
                    onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e));
                    return;
                }
            }
            LogMgr.log(6, "999");
        } catch (MfiFelicaException e6) {
            e = e6;
        } catch (FwsException e7) {
            e = e7;
        } catch (GpException e8) {
            e = e8;
        } catch (InterruptedException e9) {
            e = e9;
        } catch (Exception e10) {
            e = e10;
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
                                if (type != 6) {
                                    i = 8;
                                    if (type != 8) {
                                        i = 200;
                                        if (type != 31 && type == 55) {
                                            message = e.getMessage();
                                            i = 55;
                                        } else {
                                            message = ObfuscatedMsgUtil.executionPoint();
                                        }
                                    } else {
                                        message = e.getMessage();
                                    }
                                } else {
                                    message = e.getMessage();
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
            LogMgr.log(2, "700 MfiFelicaException:" + e2.getMessage());
            throw e2;
        } catch (Exception e3) {
            LogMgr.log(2, "701 Exception:" + e3.getClass().getSimpleName(), e3.getMessage());
            throw e3;
        }
    }

    private void getIssueTargetInfo(Parameters param) throws InterruptedException, GpException {
        LogMgr.log(6, "000");
        GpController gpController = null;
        try {
            try {
                gpController = this.mChipHolder.getGpController();
                AppletManager appletManager = new AppletManager(gpController);
                param.mSequenceCounter = ((SsdAppletInfo) appletManager.getAppletInfo(5)).getSequenceCount();
                SeAppletInfo seAppletInfo = appletManager.getSeAppletInfo(param.mIssueTargetAid);
                param.mAppletVersion = seAppletInfo.getAppletVersion();
                param.mBinaryVersion = seAppletInfo.getBinaryVersion();
                param.mPackageKeyVersion = appletManager.getAppletKeyVersion(param.mIssueTargetAid);
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
    public void setTargetSystemIdm(Parameters param) throws MfiFelicaException {
        LogMgr.log(6, "000");
        MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(this.mChipHolder);
        try {
            mfiFelicaWrapper.open();
            mfiFelicaWrapper.select(65024);
            int[] systemCodeList = mfiFelicaWrapper.getSystemCodeList();
            if (systemCodeList != null) {
                for (int i : systemCodeList) {
                    if (i == this.mIdentifiableInfo.getCacheableData().systemCode) {
                        LogMgr.log(6, "001 exist system");
                        mfiFelicaWrapper.select(this.mIdentifiableInfo.getCacheableData().systemCode);
                        param.mIssueIdm = StringUtil.bytesToHexString(mfiFelicaWrapper.getIDm());
                        break;
                    }
                }
                LogMgr.log(6, "002 Not exist system");
                param.mSeparateCondition = true;
                param.mIssueIdm = null;
            } else {
                LogMgr.log(6, "002 Not exist system");
                param.mSeparateCondition = true;
                param.mIssueIdm = null;
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
    public void onSuccessIssue(CardJson card, DoScriptTask task) throws JSONException {
        CompleteCardInfo cardInfo;
        LogMgr.log(6, "000");
        if (card == null) {
            LogMgr.log(2, "%s : %s", "700", "Card is null.");
            onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            return;
        }
        try {
            cardInfo = card.getCardInfo(CardJson.CheckType.FWS_GET_ISSUE_SCRIPT, this.mDataManager.getSeInfo());
        } catch (JSONException e) {
            LogMgr.log(2, "700 JSONException:", e.getMessage());
            LogMgr.printStackTrace(7, e);
            cardInfo = null;
        } catch (Exception e2) {
            LogMgr.log(2, "701 Exception:" + e2.getClass().getSimpleName(), e2.getMessage());
            LogMgr.printStackTrace(7, e2);
            cardInfo = null;
        }
        if (cardInfo != null) {
            if (task instanceof FwsGetIssueScriptSubTask) {
                try {
                    if (isIndividualSp1(cardInfo.getServiceId())) {
                        MigratedServiceCache.cacheMigratedService(FelicaAdapter.getInstance(), cardInfo.getServiceId());
                    }
                } catch (JSONException e3) {
                    LogMgr.log(2, "700 JSONException:", "700", e3.getMessage());
                    LogMgr.printStackTrace(7, e3);
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
                                        if (arrayList2.size() == arrayList3.size()) {
                                            crsManager.activateApplet(StringUtil.hexToByteArray(cardInfo.getAppletInstanceAid()));
                                            cardInfo.setPosition(0);
                                        }
                                    } catch (GpException | InterruptedException unused) {
                                        cardInfo.setPosition(1);
                                        LogMgr.log(2, "700 Secure Element access error on deactivating previous AID.");
                                    }
                                }
                            }
                        }
                    } catch (GpException e4) {
                        LogMgr.log(1, "802 Secure Element access error.");
                        onFinished(false, e4.getType(), e4.getMessage());
                        return;
                    } catch (InterruptedException unused2) {
                        LogMgr.log(1, "801 cancel occured.");
                        onFinished(false, 215, null);
                        return;
                    }
                } catch (JSONException e5) {
                    LogMgr.printStackTrace(1, e5);
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
    protected void onFinished(boolean isSuccess, int errType, String errMsg) {
        LogMgr.log(6, "000");
        if (this.mCardIssueEvent != null && getResult2() != null && getResult2().cardInfo != null) {
            this.mCardIssueEvent.setCid(getResult2().cardInfo.getCid());
        }
        EventBroadcastSender.sendFinishEventBroadcast(FelicaAdapter.getInstance(), this.mCardIssueEvent, isSuccess);
        super.onFinished(isSuccess, errType, errMsg);
        LogMgr.log(6, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFinishedWithResponse(int errType, String errMsg, ResponseJson response) {
        LogMgr.log(6, "000");
        if (errType == 206) {
            LogMgr.log(2, "700 Server General Error.");
            ErrorMessageContainableComServInfo errorMessageContainableComServInfo = new ErrorMessageContainableComServInfo(errMsg, response);
            if (errorMessageContainableComServInfo.isValidFormat()) {
                errMsg = errorMessageContainableComServInfo.getString();
            } else {
                errType = 202;
                errMsg = MfiClientCallbackConst.MSG_FORMAT_ERROR;
            }
        }
        onFinished(false, errType, errMsg);
        LogMgr.log(6, "999");
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public synchronized void setResult(IssueCard issueCard) {
        this.mIssueCard = issueCard;
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public synchronized IssueCard getResult2() {
        return this.mIssueCard;
    }

    private class FwsCheckCardInformationSubTask extends AccessFwsTask<CheckCardInformationResponseJson> {
        private static final int FWS_SEQUENCE_NUMBER = 1;

        FwsCheckCardInformationSubTask(int taskId, FwsClient fwsClient) {
            super(taskId, fwsClient);
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
                IssueCardTask.this.onFinishedWithResponse(result.errType, result.errMsg, result.response);
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
                    } catch (Exception e5) {
                        LogMgr.log(2, "804 Exception:" + e5.getClass().getSimpleName(), e5.getMessage());
                        IssueCardTask.this.onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e5));
                        return;
                    }
                } catch (JSONException e6) {
                    LogMgr.log(2, "%s JSONException:%s", "700", e6.getMessage());
                    LogMgr.printStackTrace(7, e6);
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
        protected String callFws(String request) throws ProtocolException, HttpException {
            return this.mFwsClient.checkCardInformation(request, 1);
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/mfc/mfi/fws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public CheckCardInformationResponseJson convertResponse(String json) throws JSONException {
            return new CheckCardInformationResponseJson(json);
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

        FwsCreateCardSubTask(int taskId, FwsClient fwsClient, MfiChipHolder chipHolder, Parameters param) {
            super(taskId, fwsClient, chipHolder, IssueCardTask.this.mExecutor);
            this.mParam = param;
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
        protected String callFws(String request) throws ProtocolException, HttpException {
            return this.mFwsClient.createCard(request, this.mSeqNum);
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/mfc/mfi/fws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public CreateCardResponseJson convertResponse(String json) throws JSONException {
            return new CreateCardResponseJson(json);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return IssueCardTask.VALID_RESULT_CODE_LIST_CREATE_CARD;
        }

        /* JADX DEBUG: Method merged with bridge method: onSuccessScript(Lcom/felicanetworks/mfc/mfi/fws/json/GetScriptResponseJson;)V */
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        public void onSuccessScript(CreateCardResponseJson response) throws JSONException {
            try {
                IssueCardTask.this.onSuccessIssue(response.getCreatedCard(), this);
            } catch (JSONException e) {
                LogMgr.log(1, "800 JSONException:", e.getMessage());
                LogMgr.printStackTrace(7, e);
                IssueCardTask.this.onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            }
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        void onErrorScript(int type, String msg) {
            IssueCardTask.this.onFinishedWithResponse(type, msg, getResult2().response);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected boolean isEnableRetryHttpAccess() {
            return ServiceTypeInfoUtil.IfType.isFullIf(IssueCardTask.this.mServiceType);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected void onFinishTcap(TcapResultJson tcapResult) {
            LogMgr.log(6, "000");
            this.mTcapResult = tcapResult;
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
                FwsGetIssueScriptSubTask fwsGetIssueScriptSubTask = IssueCardTask.this.new FwsGetIssueScriptSubTask(0, this.mFwsClient, this.mChipHolder, this.mOperationId, createCardResponseJson.getCreatedCard().getCardInfo(CardJson.CheckType.FWS_CREATE_CARD, IssueCardTask.this.mDataManager.getSeInfo()).getServiceId(), createCardResponseJson.optNextAccessToken(), this.mTcapResult);
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
            } catch (Exception e3) {
                LogMgr.log(2, "701 Exception:" + e3.getClass().getSimpleName(), e3.getMessage());
                LogMgr.printStackTrace(7, e3);
                IssueCardTask.this.onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
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

        FwsGetIssueScriptSubTask(int taskId, FwsClient fwsClient, MfiChipHolder chipHolder, String operationId, String serviceId, String accessToken, TcapResultJson tcapResult) {
            super(taskId, fwsClient, chipHolder, IssueCardTask.this.mExecutor, operationId);
            this.mServiceId = serviceId;
            this.mAccessToken = accessToken;
            this.mTcapResult = tcapResult;
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
        protected String callFws(String request) throws ProtocolException, HttpException {
            return this.mFwsClient.getIssueScript(request, this.mServiceId, this.mSeqNum);
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/mfc/mfi/fws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public GetIssueScriptResponseJson convertResponse(String json) throws JSONException {
            return new GetIssueScriptResponseJson(json);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return IssueCardTask.VALID_RESULT_CODE_LIST_GET_ISSUE_SCRIPT;
        }

        /* JADX DEBUG: Method merged with bridge method: onSuccessScript(Lcom/felicanetworks/mfc/mfi/fws/json/GetScriptResponseJson;)V */
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        public void onSuccessScript(GetIssueScriptResponseJson response) throws JSONException {
            LogMgr.log(6, "000");
            try {
                CardJson issueCard = response.getIssueCard();
                LogMgr.log(6, "999");
                IssueCardTask.this.onSuccessIssue(issueCard, this);
            } catch (JSONException e) {
                LogMgr.log(2, "%s JSONException:%s", "700", e.getMessage());
                LogMgr.printStackTrace(7, e);
                IssueCardTask.this.onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            }
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        void onErrorScript(int type, String msg) {
            IssueCardTask.this.onFinished(false, type, msg);
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

        /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
        @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
        /* JADX INFO: renamed from: getResult, reason: avoid collision after fix types in other method */
        public Void getResult2() {
            return null;
        }

        DeleteSystemTask(int taskId, FelicaInstanceInfo appletInfo, Parameters parameters) {
            super(taskId);
            this.mAppletInfo = appletInfo;
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

        private void deleteSystemOfDeletedLegacyCard(String linkageData) {
            DeleteSystemTask deleteSystemTask;
            LogMgr.log(6, "000");
            try {
                deleteSystemTask = this;
            } catch (Exception e) {
                e = e;
                deleteSystemTask = this;
            }
            try {
                DeleteCardTask deleteCardTask = new DeleteCardTask(1, IssueCardTask.this.mExecutor, deleteSystemTask, IssueCardTask.this.mLoginTokenId, linkageData, IssueCardTask.this.mFwsClient, IssueCardTask.this.mChipHolder, IssueCardTask.this.mDataManager, this.mAppletInfo.getAid());
                IssueCardTask.this.setStoppableSubTask(deleteCardTask);
                deleteCardTask.start();
            } catch (Exception e2) {
                e = e2;
                Exception exc = e;
                LogMgr.log(2, "700", exc.getClass().getSimpleName(), exc.getMessage());
                LogMgr.printStackTrace(7, exc);
                IssueCardTask.this.onFinishDeleteSystem(false, deleteSystemTask.mParameters, deleteSystemTask.mAppletInfo.getAid(), 200, ObfuscatedMsgUtil.exExecutionPoint(exc));
            }
            LogMgr.log(6, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase.Listener
        public void onFinishTask(TaskBase task, boolean isSuccess, int errType, String errMsg) {
            String strExecutionPoint;
            LogMgr.log(5, "000 isSuccess=" + isSuccess);
            int taskId = task.getTaskId();
            if (taskId == 0) {
                int i = 200;
                if (isSuccess) {
                    if (!(task instanceof GetLinkageDataListTask)) {
                        IssueCardTask.this.onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                        return;
                    }
                    String[] result2 = ((GetLinkageDataListTask) task).getResult2();
                    if (result2 == null || result2.length != 1) {
                        IssueCardTask.this.onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                        return;
                    }
                    deleteSystemOfDeletedLegacyCard(result2[0]);
                } else {
                    if (211 == errType) {
                        LogMgr.log(2, "700 TYPE=", Integer.valueOf(errType));
                        strExecutionPoint = ObfuscatedMsgUtil.executionPoint();
                    } else if (208 == errType) {
                        LogMgr.log(2, "701 TYPE=", Integer.valueOf(errType));
                        strExecutionPoint = ObfuscatedMsgUtil.executionPoint();
                    } else {
                        if (238 == errType) {
                            LogMgr.log(2, "703 TYPE=", Integer.valueOf(errType));
                        } else {
                            i = errType;
                        }
                        strExecutionPoint = errMsg;
                    }
                    IssueCardTask.this.onFinished(false, i, strExecutionPoint);
                }
            } else if (taskId == 1) {
                this.mParameters.mDeletedLegacyCardInstanceInfo = null;
                IssueCardTask.this.onFinishDeleteSystem(isSuccess, this.mParameters, this.mAppletInfo.getAid(), errType, errMsg);
            }
            LogMgr.log(5, "999");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFinishDeleteSystem(boolean isSuccess, Parameters parameters, byte[] aid, int errType, String errMsg) {
        int i;
        String str;
        String strExecutionPoint;
        LogMgr.log(6, "000");
        try {
            SeAppletInfo seAppletInfo = new AppletManager(this.mChipHolder.getGpController()).getSeAppletInfo(aid);
            if (seAppletInfo == null) {
                onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                return;
            }
            if (!seAppletInfo.isPersonalized()) {
                parameters.mIssueTargetAid = aid;
                getIssueTargetInfo(parameters);
                FwsCreateCardSubTask fwsCreateCardSubTask = new FwsCreateCardSubTask(0, this.mFwsClient, this.mChipHolder, parameters);
                setStoppableSubTask(fwsCreateCardSubTask);
                fwsCreateCardSubTask.start();
                LogMgr.log(6, "999");
                return;
            }
            if (isSuccess) {
                onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                return;
            }
            if (209 == errType) {
                LogMgr.log(2, "700 TYPE=", Integer.valueOf(errType));
                strExecutionPoint = ObfuscatedMsgUtil.executionPoint();
            } else if (210 == errType) {
                LogMgr.log(2, "701 TYPE=", Integer.valueOf(errType));
                strExecutionPoint = ObfuscatedMsgUtil.executionPoint();
            } else if (211 == errType) {
                LogMgr.log(2, "702 TYPE=", Integer.valueOf(errType));
                strExecutionPoint = ObfuscatedMsgUtil.executionPoint();
            } else if (208 == errType) {
                LogMgr.log(2, "703 TYPE=", Integer.valueOf(errType));
                strExecutionPoint = ObfuscatedMsgUtil.executionPoint();
            } else {
                i = errType;
                str = errMsg;
                onFinished(false, i, str);
            }
            str = strExecutionPoint;
            i = 200;
            onFinished(false, i, str);
        } catch (GpException e) {
            LogMgr.log(1, "802 GpException:" + e.getType() + "," + e.getMessage());
            onFinished(false, e.getType(), e.getMessage());
        } catch (InterruptedException e2) {
            LogMgr.log(1, "801 InterruptedException:" + e2.getMessage());
            onFinished(false, 215, null);
        } catch (Exception e3) {
            LogMgr.log(2, "803 " + e3.getClass().getSimpleName(), e3.getMessage());
            LogMgr.printStackTrace(7, e3);
            onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e3));
        }
    }
}
