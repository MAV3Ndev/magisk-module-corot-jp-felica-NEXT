package com.felicanetworks.mfc.mfi.fws;

import android.content.Intent;
import android.os.ParcelFileDescriptor;
import com.felicanetworks.mfc.mfi.CachedCardInfo;
import com.felicanetworks.mfc.mfi.CardAdditionalInfo;
import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import com.felicanetworks.mfc.mfi.CardInfo;
import com.felicanetworks.mfc.mfi.CardInfoWithSpStatus;
import com.felicanetworks.mfc.mfi.CompleteCardInfo;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.IPipeEventCallback;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientConst;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfc.mfi.MfiClientExternalLogConst;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.CheckLegacyCardExistenceTask;
import com.felicanetworks.mfc.mfi.fws.DeprecatedStartTask;
import com.felicanetworks.mfc.mfi.fws.EnableCachedCardTask;
import com.felicanetworks.mfc.mfi.fws.GetCardListTask;
import com.felicanetworks.mfc.mfi.fws.GetMfiTosDataTask;
import com.felicanetworks.mfc.mfi.fws.IssueCardTask;
import com.felicanetworks.mfc.mfi.fws.MigrateCardKeyTask;
import com.felicanetworks.mfc.mfi.fws.StartTask;
import com.felicanetworks.mfc.mfi.fws.UploadCardsTask;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.ServiceTypeInfoUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes3.dex */
public class FwsClientFacade implements AsyncTaskBase.Listener {
    private static final Integer[] TASK_CAN_NOT_FINISH_WITH_CANCEL_CARD_OPERATION;
    private static final Integer[] TASK_CAN_NOT_FINISH_WITH_STOP;
    private static final int TASK_ID_CLEAR_MEMORY = 20971520;
    private static final int TASK_ID_DELETE_CARD = 32;
    private static final int TASK_ID_DELETE_UNSUPPORT_MFI_SERVICE_1_CARD = 2359296;
    private static final int TASK_ID_DISABLE_CARD = 128;
    private static final int TASK_ID_ENABLE_CACHED_CARD = 69;
    private static final int TASK_ID_ENABLE_CARD = 64;
    private static final int TASK_ID_EXECUTE_SERVER_OPERATION = 2097152;
    private static final int TASK_ID_GET_CACHED_CARD_LIST = 68;
    private static final int TASK_ID_GET_CACHED_CARD_LIST_WITH_INTEGRITY_CHECK = 70;
    private static final int TASK_ID_GET_CARD_EX_LIST = 8;
    private static final int TASK_ID_GET_CARD_EX_LIST_V3 = 524288;
    private static final int TASK_ID_GET_CARD_EX_LIST_WITH_PIPE = 512;
    private static final int TASK_ID_GET_CARD_LIST = 4;
    private static final int TASK_ID_GET_CARD_LIST_V3 = 262144;
    private static final int TASK_ID_GET_CARD_LIST_WITH_PIPE = 256;
    private static final int TASK_ID_GET_CARD_LIST_WITH_SP_STATUS = 65536;
    private static final int TASK_ID_GET_CARD_LIST_WITH_SP_STATUS_V3 = 1048576;
    private static final int TASK_ID_GET_GOOGLE_TOS = 8388608;
    private static final int TASK_ID_GET_LINKAGE_DATA_LIST = 4096;
    private static final int TASK_ID_GET_MFI_TOS_DATA = 4194304;
    private static final int TASK_ID_GET_REMAINED_CARDS = 16777216;
    private static final int TASK_ID_INITIALIZE = 2048;
    private static final int TASK_ID_ISSUE_CARD = 16;
    private static final int TASK_ID_LEGACY_CARD_EXISTANCE_TASK = 2236416;
    private static final int TASK_ID_LOGIN = 1;
    private static final int TASK_ID_LOGOUT = 2;
    private static final int TASK_ID_MIGRATE_CARD_KEY = 160;
    private static final int TASK_ID_PROVISION_SERVER_OPERATION = 2228224;
    private static final int TASK_ID_SAVE_DELETE_CARD = 131072;
    private static final int TASK_ID_SE_ACCESS = 32768;
    private static final int TASK_ID_SILENT_START = 16384;
    private static final int TASK_ID_SILENT_START_FOR_ADMIN = 8192;
    private static final int TASK_ID_UPLOAD_CARDS_TO_DELETE = 18874368;
    private static final int TASK_ID_UPLOAD_CARDS_TO_PERMANENT_DELETE = 18939904;
    private static final int TASK_ID_WRITE_PIPE = 1024;
    private static final Integer[] TASK_SILENT_START;
    private MfiChipHolder mChipHolder;
    private AsyncTaskBase mCurrentTask;
    private DataManager mDataManager;
    private ExecutorService mExecutor;
    private FwsClient mFwsClient;
    private IPipeEventCallback mIPipeEventCallback;
    private boolean mIsSilentStartStopped;
    private OnFinishListener mListener;
    private Parameters mParams;
    private RemainedCardsCache mRemainedCardsCache;
    private a mJwsCreator = null;
    private boolean mIsStarted = false;
    private LogoutArguments mWaitingLogoutArgs = null;

    public interface OnFinishListener {
        void onCheckLegacyCardExistenceFinished(boolean isSuccess, boolean exists, String cardInfoJson, int errType, String errMsg);

        void onClearMemoryFinished(boolean isSuccess, int errType, String errMsg);

        void onDeleteCardFinished(boolean isSuccess, int errType, String errMsg);

        void onDeleteLegacyCardFinished(boolean isSuccess, int errType, String errMsg);

        void onDisableCardFinished(boolean isSuccess, CardInfo cardInfo, int errType, String errMsg);

        void onEnableCachedCardFinished(boolean isSuccess, CachedCardInfo enableCard, CachedCardInfo disableCard, int errType, String errMsg);

        void onEnableCardFinished(boolean isSuccess, CardInfo enableCard, CardInfo disableCard, int errType, String errMsg);

        void onExecuteServerOperationFinished(boolean isSuccess, boolean requiredRetry, int errType, String errMsg);

        void onGetAccessCardFinished(boolean isSuccess, int errType, String errMsg);

        void onGetCachedCardListFinished(boolean isSuccess, String[] cardInfos, int errType, String errMsg);

        void onGetCachedCardListWithIntegrityCheckFinished(boolean isSuccess, String[] cardInfos, int errType, String errMsg);

        void onGetCardAdditionalInfoListFinished(boolean isSuccess, CardAdditionalInfo[] cardInfos, int errType, String errMsg);

        void onGetCardAdditionalInfoListV2Finished(boolean isSuccess, int errType, String errMsg);

        void onGetCardAdditionalInfoListV3Finished(boolean isSuccess, CardAdditionalInfo[] cardInfos, int errType, String errMsg);

        void onGetCardInfoListWithSpStatusFinished(boolean isSuccess, int errType, String errMsg);

        void onGetCardInfoListWithSpStatusV3Finished(boolean isSuccess, CardInfoWithSpStatus[] cardInfos, int errType, String errMsg);

        void onGetCardListFinished(boolean isSuccess, CardInfo[] cardInfos, int errType, String errMsg);

        void onGetCardListV2Finished(boolean isSuccess, int errType, String errMsg);

        void onGetCardListV3Finished(boolean isSuccess, CardInfo[] cardInfos, int errType, String errMsg);

        void onGetGoogleTosFinished(boolean isSuccess, Intent intent, int errType, String errMsg);

        void onGetLinkageDataListFinished(boolean isSuccess, String[] linkageData, int errType, String errMsg);

        void onGetMfiTosDataFinished(boolean isSuccess, Map<String, String> mfiTosDataJsonMap, boolean isMfiTosAgreed, int errType, String errMsg);

        void onGetRemainedCardsFinished(boolean isSuccess, CardInfo[] cardInfoListToDelete, CardInfo[] cardInfoListToPermanentDelete, int errType, String errMsg);

        void onInitializeFinished(boolean isSuccess, int errType, String errMsg);

        void onIssueCardFinished(boolean isSuccess, CardInfo cardInfo, int errType, String errMsg);

        void onLoginFinished(boolean isSuccess, int errType, String errMsg);

        void onLogoutFinished(boolean isSuccess, boolean isSilentStartStopped, int errType, String errMsg);

        void onMigrateCardKeyFinished(boolean isSuccess, CardInfo cardInfo, int errType, String errMsg);

        void onProvisionServerOperationFinished(boolean isSuccess, int errType, String errMsg);

        void onSaveDeleteCardV2Finished(boolean isSuccess, CardInfo cardInfo, int errType, String errMsg);

        void onSilentStartFinished(boolean isSuccess, Intent intent, int errType, String errMsg);

        void onSilentStartForMfiAdminFinished(boolean isSuccess, boolean noLogin, Intent intent, int errType, String errMsg);

        void onUploadCardsToDeleteFinished(boolean isSuccess, int errType, String errMsg, String[] cidList);

        void onUploadCardsToPermanentDeleteFinished(boolean isSuccess, int errType, String errMsg, String[] cidList);

        void onWritePipeFinished(boolean isSuccess, MfiClientExternalLogConst.MficApi api, int errType, String errMsg);
    }

    static {
        Integer numValueOf = Integer.valueOf(TASK_ID_PROVISION_SERVER_OPERATION);
        Integer numValueOf2 = Integer.valueOf(TASK_ID_LEGACY_CARD_EXISTANCE_TASK);
        Integer numValueOf3 = Integer.valueOf(TASK_ID_DELETE_UNSUPPORT_MFI_SERVICE_1_CARD);
        TASK_CAN_NOT_FINISH_WITH_CANCEL_CARD_OPERATION = new Integer[]{1, 2, 16384, 8192, 4096, 2048, numValueOf, 2097152, numValueOf2, numValueOf3, 68, 69, 4194304, 8388608, Integer.valueOf(TASK_ID_CLEAR_MEMORY), 70};
        TASK_CAN_NOT_FINISH_WITH_STOP = new Integer[]{1, 2};
        TASK_SILENT_START = new Integer[]{16384, 8192, numValueOf, 2097152, numValueOf2, numValueOf3, 68, 69, 4194304, 8388608, 70};
    }

    private static class Parameters {
        private String accountId;
        private Map<String, CardIdentifiableInfo.Cache> cardIdInfoMap;
        private Map<String, CompleteCardInfo.Cache> cardInfoMap;
        private String loginTokenId;

        private Parameters() {
        }
    }

    private class LogoutArguments {
        final String appCallerInfo;
        final String appIdInfo;
        final boolean autoMfiServerLogout;
        final boolean noLogin;

        LogoutArguments(boolean autoMfiServerLogout, String appCallerInfo, String appIdInfo, boolean noLogin) {
            this.autoMfiServerLogout = autoMfiServerLogout;
            this.appCallerInfo = appCallerInfo;
            this.appIdInfo = appIdInfo;
            this.noLogin = noLogin;
        }
    }

    private class OnFinishTaskParam {
        Object[] args;
        String errMsg;
        int errType;
        boolean isSuccess;
        int taskId;

        OnFinishTaskParam(int taskId, boolean isSuccess, Object[] args, int errType, String errMsg) {
            this.taskId = taskId;
            this.isSuccess = isSuccess;
            this.errType = errType;
            this.errMsg = errMsg;
            this.args = args;
        }
    }

    public synchronized void start(OnFinishListener listener, DataManager dataManager, String walletAppId) throws MfiClientException {
        LogMgr.log(4, "%s", "000");
        if (this.mIsStarted) {
            LogMgr.log(1, "%s : Already running online task.", "800");
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
        if (listener == null) {
            LogMgr.log(1, "%s : Listener is null.", "801");
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
        this.mListener = listener;
        this.mDataManager = dataManager;
        dataManager.onStartMfiClient(walletAppId);
        this.mFwsClient = new FwsClient(dataManager);
        this.mChipHolder = new MfiChipHolder();
        this.mParams = new Parameters();
        ExecutorService executorService = this.mExecutor;
        if (executorService != null) {
            executorService.shutdown();
        }
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mWaitingLogoutArgs = null;
        this.mIsStarted = true;
        LogMgr.log(4, "%s", "999");
    }

    public synchronized void finish() {
        LogMgr.log(4, "%s", "000");
        if (!this.mIsStarted) {
            LogMgr.log(6, "%s Already finished", "700");
            return;
        }
        this.mListener = null;
        this.mParams = new Parameters();
        this.mWaitingLogoutArgs = null;
        this.mIsStarted = false;
        AsyncTaskBase asyncTaskBase = this.mCurrentTask;
        if (asyncTaskBase != null) {
            asyncTaskBase.stop();
            this.mCurrentTask = null;
        }
        FwsClient fwsClient = this.mFwsClient;
        if (fwsClient != null) {
            fwsClient.stop();
            this.mFwsClient = null;
        }
        MfiChipHolder mfiChipHolder = this.mChipHolder;
        if (mfiChipHolder != null) {
            mfiChipHolder.discard();
            this.mChipHolder = null;
        }
        ExecutorService executorService = this.mExecutor;
        if (executorService != null) {
            executorService.shutdown();
            this.mExecutor = null;
        }
        a aVar = this.mJwsCreator;
        if (aVar != null) {
            aVar.a();
            this.mJwsCreator = null;
        }
        DataManager dataManager = this.mDataManager;
        if (dataManager != null) {
            dataManager.onStopMfiClient();
            this.mDataManager = null;
        }
        LogMgr.log(4, "%s", "999");
    }

    public synchronized void silentStart(String accountIssuer, String accountName, boolean skipAgreement, String appCallerInfo, String appIdInfo, int code) throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        StartTask startTask = new StartTask(16384, this.mExecutor, this, accountIssuer, accountName, code, skipAgreement, appCallerInfo, appIdInfo, this.mFwsClient, this.mChipHolder, this.mDataManager);
        startTask.start();
        this.mCurrentTask = startTask;
        LogMgr.log(4, "999");
    }

    public synchronized void silentStartForMfiAdmin(String accountIssuer, String accountName, boolean skipAgreement, String appCallerInfo, String appIdInfo, boolean login, int code, int layoutType, boolean isPrivileged, boolean skipCheckChipInitialized) throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        StartTask.AdditionalParams additionalParams = new StartTask.AdditionalParams();
        additionalParams.noLogin = !login;
        additionalParams.isAdmin = true;
        additionalParams.layoutType = layoutType;
        additionalParams.isPrivileged = isPrivileged;
        additionalParams.skipCheckChipInitialized = skipCheckChipInitialized;
        StartTask startTask = new StartTask(8192, this.mExecutor, this, accountIssuer, accountName, code, skipAgreement, appCallerInfo, appIdInfo, this.mFwsClient, this.mChipHolder, this.mDataManager, additionalParams);
        startTask.start();
        this.mCurrentTask = startTask;
        LogMgr.log(4, "999");
    }

    public synchronized void logout(boolean autoMfiServerLogout, String appCallerInfo, String appIdInfo, boolean noLogin) throws Throwable {
        try {
            try {
                LogMgr.log(4, "%s autoMfiServerLogout=%s", "000", Boolean.valueOf(autoMfiServerLogout));
                checkStarted();
                checkNotRunningNonStoppableTask();
                if (this.mCurrentTask != null) {
                    LogMgr.log(6, "001 call stopTask and wait");
                    this.mWaitingLogoutArgs = new LogoutArguments(autoMfiServerLogout, appCallerInfo, appIdInfo, noLogin);
                    stopTask();
                } else {
                    doLogout(autoMfiServerLogout, appCallerInfo, appIdInfo, noLogin);
                }
                LogMgr.log(4, "%s", "999");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    private void doLogout(boolean autoMfiServerLogout, String appCallerInfo, String appIdInfo, boolean noLogin) {
        LogMgr.log(4, "000 autoMfiServerLogout=" + autoMfiServerLogout + " noLogin=" + noLogin);
        StopTask stopTask = new StopTask(2, this.mExecutor, this, this.mParams.loginTokenId, this.mParams.accountId, autoMfiServerLogout, appCallerInfo, appIdInfo, this.mParams.cardInfoMap, this.mParams.cardIdInfoMap, this.mFwsClient);
        if (noLogin) {
            stopTask.requestNoLogin();
        }
        stopTask.start();
        this.mCurrentTask = stopTask;
        LogMgr.log(6, "%s", "999");
    }

    public synchronized void initialize(String linkageData) throws Throwable {
        try {
            try {
                LogMgr.log(4, "000");
                checkStarted();
                checkNotRunningTask();
                InitializeTask initializeTask = new InitializeTask(2048, this.mExecutor, this, linkageData, this.mFwsClient, this.mChipHolder);
                initializeTask.start();
                this.mCurrentTask = initializeTask;
                LogMgr.log(4, "999");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public synchronized void getCardList(JSONArray callingAppInfos, String applicationId) throws MfiClientException {
        LogMgr.log(4, "%s", "000");
        checkStarted();
        checkNotRunningTask();
        GetCardListTask getCardListTask = new GetCardListTask(4, this.mExecutor, this, this.mParams.loginTokenId, callingAppInfos, this.mFwsClient, this.mChipHolder, this.mDataManager, GetCardListTask.Options.createCardListOptions(applicationId), this.mJwsCreator);
        getCardListTask.start();
        this.mCurrentTask = getCardListTask;
        LogMgr.log(4, "%s", "999");
    }

    public synchronized void getCachedCardList(String callingAppInfo, String appIdInfo) throws Throwable {
        try {
            try {
                LogMgr.log(4, "000");
                checkStarted();
                checkNotRunningTask();
                GetCachedCardListTask getCachedCardListTask = new GetCachedCardListTask(68, this.mExecutor, this, this.mChipHolder, this.mDataManager, callingAppInfo, appIdInfo);
                getCachedCardListTask.start();
                this.mCurrentTask = getCachedCardListTask;
                LogMgr.log(4, "999");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public synchronized void enableCachedCard(CachedCardInfo cachedCardInfo, String callingAppInfo, String appIdInfo) throws Throwable {
        try {
            try {
                LogMgr.log(4, "000");
                checkStarted();
                checkNotRunningTask();
                EnableCachedCardTask enableCachedCardTask = new EnableCachedCardTask(69, this.mExecutor, this, cachedCardInfo, callingAppInfo, appIdInfo, this.mChipHolder, this.mDataManager);
                enableCachedCardTask.start();
                this.mCurrentTask = enableCachedCardTask;
                LogMgr.log(4, "999");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public synchronized void getCardListWithPipe(JSONArray callingAppInfos, String applicationId, IPipeEventCallback callback) throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        GetCardListTask getCardListTask = new GetCardListTask(256, this.mExecutor, this, this.mParams.loginTokenId, callingAppInfos, this.mFwsClient, this.mChipHolder, this.mDataManager, GetCardListTask.Options.createCardListOptions(applicationId), this.mJwsCreator);
        getCardListTask.start();
        this.mCurrentTask = getCardListTask;
        this.mIPipeEventCallback = callback;
        LogMgr.log(4, "999");
    }

    public synchronized void getCardListV3(JSONArray callingAppInfos, String applicationId) throws Throwable {
        try {
            try {
                LogMgr.log(4, "000");
                checkStarted();
                checkNotRunningTask();
                GetCardListTask getCardListTask = new GetCardListTask(262144, this.mExecutor, this, this.mParams.loginTokenId, callingAppInfos, this.mFwsClient, this.mChipHolder, this.mDataManager, GetCardListTask.Options.createCardListOptions(applicationId), this.mJwsCreator);
                getCardListTask.start();
                this.mCurrentTask = getCardListTask;
                LogMgr.log(4, "999");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public synchronized void getCardAdditionalInfoList(JSONArray callingAppInfos) throws Throwable {
        try {
            try {
                LogMgr.log(4, "%s", "000");
                checkStarted();
                checkNotRunningTask();
                GetCardListTask getCardListTask = new GetCardListTask(8, this.mExecutor, this, this.mParams.loginTokenId, callingAppInfos, this.mFwsClient, this.mChipHolder, this.mDataManager, GetCardListTask.Options.createCardAdditionalInfoListOptions(null), this.mJwsCreator);
                getCardListTask.start();
                this.mCurrentTask = getCardListTask;
                LogMgr.log(4, "%s", "999");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    public synchronized void getCardAdditionalInfoListWithPipe(String[] cidList, JSONArray callingAppInfos, IPipeEventCallback callback) throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        GetCardListTask getCardListTask = new GetCardListTask(512, this.mExecutor, this, this.mParams.loginTokenId, callingAppInfos, this.mFwsClient, this.mChipHolder, this.mDataManager, GetCardListTask.Options.createCardAdditionalInfoListOptions(cidList), this.mJwsCreator);
        getCardListTask.start();
        this.mCurrentTask = getCardListTask;
        this.mIPipeEventCallback = callback;
        LogMgr.log(4, "999");
    }

    public synchronized void getCardAdditionalInfoListV3(String[] cidList, JSONArray callingAppInfos) throws Throwable {
        try {
            try {
                LogMgr.log(4, "000");
                checkStarted();
                checkNotRunningTask();
                GetCardListTask getCardListTask = new GetCardListTask(524288, this.mExecutor, this, this.mParams.loginTokenId, callingAppInfos, this.mFwsClient, this.mChipHolder, this.mDataManager, GetCardListTask.Options.createCardAdditionalInfoListOptions(cidList), this.mJwsCreator);
                getCardListTask.start();
                this.mCurrentTask = getCardListTask;
                LogMgr.log(4, "999");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public synchronized void issueCard(String linkageData, String otp) throws MfiClientException {
        LogMgr.log(4, "%s", "000");
        checkStarted();
        checkNotRunningTask();
        IssueCardTask issueCardTask = new IssueCardTask(16, this.mExecutor, this, this.mParams.loginTokenId, linkageData, otp, this.mFwsClient, this.mChipHolder, this.mDataManager, this.mJwsCreator);
        issueCardTask.start();
        this.mCurrentTask = issueCardTask;
        LogMgr.log(4, "%s", "999");
    }

    public synchronized void deleteCard(String cid, String linkageData) throws MfiClientException {
        LogMgr.log(4, "%s", "000");
        checkStarted();
        checkNotRunningTask();
        CompleteCardInfo.Cache cardInfo = getCardInfo(cid);
        CardIdentifiableInfo.Cache cardIdInfo = getCardIdInfo(cardInfo.serviceId);
        if (cardInfo.spType == CompleteCardInfo.SpType.INDIVIDUAL_SP) {
            LogMgr.log(1, "800 : Individual card is unavailable .");
            throw new MfiClientException(102, MfiClientException.TYPE_NOT_SUPPORTED, null);
        }
        ServiceTypeInfoUtil.deleteCardsSimlpleServiceTypeCheck(cardInfo.serviceType);
        checkSeid(cardInfo);
        DeleteCardTask deleteCardTask = new DeleteCardTask(32, this.mExecutor, (AsyncTaskBase.Listener) this, this.mParams.loginTokenId, cid, cardIdInfo, linkageData, this.mFwsClient, this.mChipHolder, this.mDataManager, cardInfo.serviceType, true);
        deleteCardTask.start();
        this.mCurrentTask = deleteCardTask;
        LogMgr.log(4, "%s", "999");
    }

    public synchronized void saveDeleteCard(String cid, String linkageData) throws MfiClientException {
        LogMgr.log(4, "%s", "000");
        checkStarted();
        checkNotRunningTask();
        CompleteCardInfo.Cache cardInfo = getCardInfo(cid);
        CardIdentifiableInfo.Cache cardIdInfo = getCardIdInfo(cardInfo.serviceId);
        if (cardInfo.spType == CompleteCardInfo.SpType.INDIVIDUAL_SP) {
            LogMgr.log(1, "800 : Individual card is unavailable .");
            throw new MfiClientException(102, MfiClientException.TYPE_NOT_SUPPORTED, null);
        }
        ServiceTypeInfoUtil.deleteCardsServiceTypeCheck(cardInfo.serviceType);
        checkSeid(cardInfo);
        DeleteCardTask deleteCardTask = new DeleteCardTask(131072, this.mExecutor, this, this.mParams.loginTokenId, cid, cardIdInfo, linkageData, this.mFwsClient, this.mChipHolder, this.mDataManager, cardInfo.serviceType, true, StringUtil.hexToByteArray(cardInfo.appletInstanceAid), cardInfo.state, cardInfo.position);
        deleteCardTask.start();
        this.mCurrentTask = deleteCardTask;
        LogMgr.log(4, "%s", "999");
    }

    public synchronized void enableCard(CardInfo cardInfo) throws Throwable {
        Throwable th;
        try {
            try {
                LogMgr.log(4, "%s", "000");
                checkStarted();
                checkNotRunningTask();
                CompleteCardInfo.Cache cardInfo2 = getCardInfo(cardInfo.getCid());
                CardIdentifiableInfo.Cache cardIdInfo = getCardIdInfo(cardInfo2.serviceId);
                if (cardInfo2.spType == CompleteCardInfo.SpType.INDIVIDUAL_SP) {
                    try {
                        if (!Property.isChipGP()) {
                            LogMgr.log(1, "800 : Individual card is unavailable .");
                            throw new MfiClientException(102, MfiClientException.TYPE_NOT_SUPPORTED, null);
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                ServiceTypeInfoUtil.enablesServiceTypeCheck(cardInfo2.serviceType);
                checkSeid(cardInfo2);
                EnableCardTask enableCardTask = new EnableCardTask(64, this.mExecutor, this, this.mParams.loginTokenId, cardInfo, cardInfo2, cardIdInfo, this.mFwsClient, this.mChipHolder, this.mDataManager);
                enableCardTask.start();
                this.mCurrentTask = enableCardTask;
                LogMgr.log(4, "%s", "999");
            } catch (Throwable th3) {
                th = th3;
                th = th;
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            th = th;
            throw th;
        }
    }

    public synchronized void disableCard(String cid) throws Throwable {
        try {
            try {
                LogMgr.log(4, "%s", "000");
                checkStarted();
                checkNotRunningTask();
                CompleteCardInfo.Cache cardInfo = getCardInfo(cid);
                CardIdentifiableInfo.Cache cardIdInfo = getCardIdInfo(cardInfo.serviceId);
                if (cardInfo.spType == CompleteCardInfo.SpType.INDIVIDUAL_SP) {
                    LogMgr.log(1, "800 : Individual card is unavailable .");
                    throw new MfiClientException(102, MfiClientException.TYPE_NOT_SUPPORTED, null);
                }
                ServiceTypeInfoUtil.disablesServiceTypeCheck(cardInfo.serviceType);
                checkSeid(cardInfo);
                DisableCardTask disableCardTask = new DisableCardTask(128, this.mExecutor, this, this.mParams.loginTokenId, cid, cardIdInfo, this.mFwsClient, this.mChipHolder, this.mDataManager, true);
                disableCardTask.start();
                this.mCurrentTask = disableCardTask;
                LogMgr.log(4, "%s", "999");
                return;
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        throw th;
    }

    public synchronized void accessCard(String cid, String linkageData) throws MfiClientException {
        LogMgr.log(4, "%s", "000");
        checkStarted();
        checkNotRunningTask();
        CompleteCardInfo.Cache cardInfo = getCardInfo(cid);
        CardIdentifiableInfo.Cache cardIdInfo = getCardIdInfo(cardInfo.serviceId);
        if (cardInfo.spType == CompleteCardInfo.SpType.INDIVIDUAL_SP) {
            LogMgr.log(1, "800 : Individual card is unavailable .");
            throw new MfiClientException(102, MfiClientException.TYPE_NOT_SUPPORTED, null);
        }
        ServiceTypeInfoUtil.accessCardsServiceTypeCheck(cardInfo.serviceType);
        checkSeid(cardInfo);
        SEAccessTask sEAccessTask = new SEAccessTask(32768, this.mExecutor, this, this.mParams.loginTokenId, cardIdInfo, linkageData, this.mFwsClient, this.mChipHolder, this.mDataManager, cardInfo.serviceType);
        sEAccessTask.start();
        this.mCurrentTask = sEAccessTask;
        LogMgr.log(4, "%s", "999");
    }

    public synchronized void cancelCardOperation() {
        LogMgr.log(4, "%s", "000");
        AsyncTaskBase asyncTaskBase = this.mCurrentTask;
        if (asyncTaskBase != null) {
            int taskId = asyncTaskBase.getTaskId();
            if (Arrays.asList(TASK_CAN_NOT_FINISH_WITH_CANCEL_CARD_OPERATION).contains(Integer.valueOf(taskId))) {
                LogMgr.log(2, "%s Cannot finish task (%d) .", "700", Integer.valueOf(taskId));
                return;
            }
            stopTask();
        }
        LogMgr.log(4, "%s", "999");
    }

    private void stopTask() {
        this.mCurrentTask.stop();
        MfiChipHolder mfiChipHolder = this.mChipHolder;
        if (mfiChipHolder != null) {
            mfiChipHolder.cancel();
        }
        FwsClient fwsClient = this.mFwsClient;
        if (fwsClient != null) {
            fwsClient.stop();
            this.mFwsClient = new FwsClient(this.mFwsClient.getDataManager());
        }
    }

    public synchronized void getLinkageDataList(int actionType, String[] cid) throws Throwable {
        try {
            try {
                LogMgr.log(4, "000");
                checkStarted();
                checkNotRunningTask();
                GetLinkageDataListTask getLinkageDataListTask = new GetLinkageDataListTask(4096, this.mExecutor, this, actionType, cid, this.mDataManager, this.mJwsCreator, this.mFwsClient, this.mChipHolder);
                getLinkageDataListTask.start();
                this.mCurrentTask = getLinkageDataListTask;
                LogMgr.log(4, "999");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    public synchronized void getCardInfoListWithSpStatus(String serviceId, JSONArray callingAppInfos, String applicationId, IPipeEventCallback callback) throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        GetCardListTask getCardListTask = new GetCardListTask(65536, this.mExecutor, this, this.mParams.loginTokenId, callingAppInfos, this.mFwsClient, this.mChipHolder, this.mDataManager, GetCardListTask.Options.createCardInfoListWithSpStatusOptions(applicationId, serviceId), this.mJwsCreator);
        getCardListTask.start();
        this.mCurrentTask = getCardListTask;
        this.mIPipeEventCallback = callback;
        LogMgr.log(4, "999");
    }

    public synchronized void getCardInfoListWithSpStatusV3(String serviceId, JSONArray callingAppInfos, String applicationId) throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        GetCardListTask getCardListTask = new GetCardListTask(1048576, this.mExecutor, this, this.mParams.loginTokenId, callingAppInfos, this.mFwsClient, this.mChipHolder, this.mDataManager, GetCardListTask.Options.createCardInfoListWithSpStatusOptions(applicationId, serviceId), this.mJwsCreator);
        getCardListTask.start();
        this.mCurrentTask = getCardListTask;
        LogMgr.log(4, "999");
    }

    public synchronized void executeServerOperation(String operationId, String messageId) throws Throwable {
        try {
            try {
                LogMgr.log(4, "000");
                checkStarted();
                checkNotRunningTask();
                ExecuteServerOperationTask executeServerOperationTask = new ExecuteServerOperationTask(2097152, this.mExecutor, this, this.mFwsClient, this.mChipHolder, this.mDataManager, operationId, messageId);
                executeServerOperationTask.start();
                this.mCurrentTask = executeServerOperationTask;
                LogMgr.log(4, "999");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    public synchronized void provisionServerOperation() throws Throwable {
        try {
            try {
                LogMgr.log(4, "000");
                checkStarted();
                checkNotRunningTask();
                UpdateDeviceRegistrationTokenTask updateDeviceRegistrationTokenTask = new UpdateDeviceRegistrationTokenTask(TASK_ID_PROVISION_SERVER_OPERATION, this.mExecutor, this, this.mFwsClient, this.mChipHolder, this.mDataManager);
                updateDeviceRegistrationTokenTask.start();
                this.mCurrentTask = updateDeviceRegistrationTokenTask;
                LogMgr.log(4, "999");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public synchronized void checkExistsManagementCard(String serviceId, boolean isNeededCheckPrimaryIssue) throws Throwable {
        try {
            try {
                LogMgr.log(4, "000");
                checkStarted();
                checkNotRunningTask();
                CheckLegacyCardExistenceTask checkLegacyCardExistenceTask = new CheckLegacyCardExistenceTask(TASK_ID_LEGACY_CARD_EXISTANCE_TASK, this.mExecutor, this, this.mFwsClient, this.mChipHolder, this.mDataManager, serviceId, isNeededCheckPrimaryIssue);
                checkLegacyCardExistenceTask.start();
                this.mCurrentTask = checkLegacyCardExistenceTask;
                LogMgr.log(4, "999");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    public synchronized void deleteLegacyCard(String linkageData) throws Throwable {
        try {
            try {
                LogMgr.log(4, "000");
                checkStarted();
                checkNotRunningTask();
                DeleteLegacyCardTask deleteLegacyCardTask = new DeleteLegacyCardTask(TASK_ID_DELETE_UNSUPPORT_MFI_SERVICE_1_CARD, this.mExecutor, this, linkageData, this.mFwsClient, this.mChipHolder, this.mDataManager);
                deleteLegacyCardTask.start();
                this.mCurrentTask = deleteLegacyCardTask;
                LogMgr.log(4, "999");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public synchronized void getMfiTosData(int code) throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        GetMfiTosDataTask getMfiTosDataTask = new GetMfiTosDataTask(4194304, this.mExecutor, this, code);
        getMfiTosDataTask.start();
        this.mCurrentTask = getMfiTosDataTask;
        LogMgr.log(4, "999");
    }

    public synchronized void getGoogleTos(int code) throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        GetGoogleTosTask getGoogleTosTask = new GetGoogleTosTask(8388608, this.mExecutor, this, code);
        getGoogleTosTask.start();
        this.mCurrentTask = getGoogleTosTask;
        LogMgr.log(4, "999");
    }

    public synchronized void getRemainedCards() throws Throwable {
        try {
            try {
                LogMgr.log(4, "000");
                checkStarted();
                checkNotRunningTask();
                GetRemainedCardsTask getRemainedCardsTask = new GetRemainedCardsTask(16777216, this.mExecutor, this, this.mFwsClient, this.mChipHolder, this.mDataManager);
                getRemainedCardsTask.start();
                this.mCurrentTask = getRemainedCardsTask;
                LogMgr.log(4, "999");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public synchronized void uploadCardsToDelete() throws Throwable {
        try {
            try {
                LogMgr.log(4, "000");
                checkStarted();
                checkNotRunningTask();
                if (!isValidRemainedCardsCache(TASK_ID_UPLOAD_CARDS_TO_DELETE)) {
                    LogMgr.log(1, "800 : RemainedCards are not cached.");
                    throw new MfiClientException(1, 157, null);
                }
                UploadCardsToDeleteTask uploadCardsToDeleteTask = new UploadCardsToDeleteTask(TASK_ID_UPLOAD_CARDS_TO_DELETE, this.mExecutor, this, this.mFwsClient, this.mChipHolder, this.mDataManager, this.mRemainedCardsCache);
                uploadCardsToDeleteTask.start();
                this.mCurrentTask = uploadCardsToDeleteTask;
                LogMgr.log(4, "999");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public synchronized void uploadCardsToPermanentDelete() throws Throwable {
        try {
            try {
                LogMgr.log(4, "000");
                checkStarted();
                checkNotRunningTask();
                if (!isValidRemainedCardsCache(TASK_ID_UPLOAD_CARDS_TO_PERMANENT_DELETE)) {
                    LogMgr.log(1, "800 : RemainedCards are not cached.");
                    throw new MfiClientException(1, 157, null);
                }
                UploadCardsToPermanentDeleteTask uploadCardsToPermanentDeleteTask = new UploadCardsToPermanentDeleteTask(TASK_ID_UPLOAD_CARDS_TO_PERMANENT_DELETE, this.mExecutor, this, this.mFwsClient, this.mChipHolder, this.mDataManager, this.mRemainedCardsCache);
                uploadCardsToPermanentDeleteTask.start();
                this.mCurrentTask = uploadCardsToPermanentDeleteTask;
                LogMgr.log(4, "999");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public synchronized void clearMemory() throws Throwable {
        try {
            try {
                LogMgr.log(4, "000");
                checkStarted();
                checkNotRunningTask();
                ClearMemoryTask clearMemoryTask = new ClearMemoryTask(TASK_ID_CLEAR_MEMORY, this.mExecutor, this, this.mFwsClient, this.mChipHolder, this.mDataManager);
                clearMemoryTask.start();
                this.mCurrentTask = clearMemoryTask;
                LogMgr.log(4, "999");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public synchronized void getCachedCardListWithIntegrityCheck(String callingAppInfo, String appIdInfo) throws Throwable {
        try {
            try {
                LogMgr.log(4, "000");
                checkStarted();
                checkNotRunningTask();
                GetCachedCardListTask getCachedCardListTask = new GetCachedCardListTask(70, this.mExecutor, this, this.mChipHolder, this.mDataManager, callingAppInfo, appIdInfo, false);
                getCachedCardListTask.start();
                this.mCurrentTask = getCachedCardListTask;
                LogMgr.log(4, "999");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public synchronized void migrateCardKey(JSONArray callingAppInfoArray, String serviceId) throws Throwable {
        try {
            try {
                LogMgr.log(4, "000");
                checkStarted();
                checkNotRunningTask();
                MigrateCardKeyTask migrateCardKeyTask = new MigrateCardKeyTask(160, this.mExecutor, this, this.mParams.loginTokenId, callingAppInfoArray, this.mFwsClient, this.mChipHolder, this.mDataManager, serviceId);
                migrateCardKeyTask.start();
                this.mCurrentTask = migrateCardKeyTask;
                LogMgr.log(4, "999");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private synchronized void checkStarted() throws MfiClientException {
        if (!this.mIsStarted) {
            LogMgr.log(1, "%s : Not started.", "800");
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized void checkNotRunningTask() throws MfiClientException {
        if (this.mCurrentTask != null) {
            LogMgr.log(1, "%s : Aleady running task.", "800");
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_CURRENTLY_ONLINE, null);
        }
    }

    public synchronized void checkNotRunningNonStoppableTask() throws MfiClientException {
        if (this.mWaitingLogoutArgs != null) {
            LogMgr.log(1, "%s : already waiting for logout.", "800");
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_CURRENTLY_ONLINE, null);
        }
        AsyncTaskBase asyncTaskBase = this.mCurrentTask;
        if (asyncTaskBase != null) {
            if (Arrays.asList(TASK_CAN_NOT_FINISH_WITH_STOP).contains(Integer.valueOf(asyncTaskBase.getTaskId()))) {
                LogMgr.log(1, "%s : Aleady running task.", "801");
                throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_CURRENTLY_ONLINE, null);
            }
        }
    }

    private boolean isValidRemainedCardsCache(final int taskId) {
        RemainedCardsCache remainedCardsCache = this.mRemainedCardsCache;
        if (remainedCardsCache == null) {
            return false;
        }
        if (taskId == TASK_ID_UPLOAD_CARDS_TO_DELETE) {
            return remainedCardsCache.existInfoToDelete();
        }
        if (taskId != TASK_ID_UPLOAD_CARDS_TO_PERMANENT_DELETE) {
            return false;
        }
        return remainedCardsCache.existInfoToPermanentDelete();
    }

    public synchronized boolean isNotRunningSilentStartTask() {
        AsyncTaskBase asyncTaskBase = this.mCurrentTask;
        if (asyncTaskBase != null) {
            if (Arrays.asList(TASK_SILENT_START).contains(Integer.valueOf(asyncTaskBase.getTaskId()))) {
                return false;
            }
        }
        return true;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase.Listener
    public void onFinishTask(TaskBase task, boolean isSuccess, int errType, String errMsg) {
        LogMgr.log(6, "%s", "000");
        synchronized (this) {
            OnFinishTaskParam onFinishTaskParamOnFinishTaskInner = onFinishTaskInner(task, isSuccess, errType, errMsg);
            if (onFinishTaskParamOnFinishTaskInner == null) {
                LogMgr.log(2, "%s OnFinishTaskParam is null.", "700");
                return;
            }
            OnFinishListener onFinishListener = this.mListener;
            switch (onFinishTaskParamOnFinishTaskInner.taskId) {
                case 1:
                    onFinishListener.onLoginFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 2:
                    onFinishListener.onLogoutFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, ((Boolean) onFinishTaskParamOnFinishTaskInner.args[0]).booleanValue(), onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 4:
                    onFinishListener.onGetCardListFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, (CardInfo[]) onFinishTaskParamOnFinishTaskInner.args[0], onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 8:
                    onFinishListener.onGetCardAdditionalInfoListFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, (CardAdditionalInfo[]) onFinishTaskParamOnFinishTaskInner.args[0], onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 16:
                    onFinishListener.onIssueCardFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, (CardInfo) onFinishTaskParamOnFinishTaskInner.args[0], onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 32:
                    onFinishListener.onDeleteCardFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 64:
                    onFinishListener.onEnableCardFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, (CardInfo) onFinishTaskParamOnFinishTaskInner.args[0], (CardInfo) onFinishTaskParamOnFinishTaskInner.args[1], onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 68:
                    onFinishListener.onGetCachedCardListFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, (String[]) onFinishTaskParamOnFinishTaskInner.args[0], onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 69:
                    onFinishListener.onEnableCachedCardFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, (CachedCardInfo) onFinishTaskParamOnFinishTaskInner.args[0], (CachedCardInfo) onFinishTaskParamOnFinishTaskInner.args[1], onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 70:
                    onFinishListener.onGetCachedCardListWithIntegrityCheckFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, (String[]) onFinishTaskParamOnFinishTaskInner.args[0], onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 128:
                    onFinishListener.onDisableCardFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, (CardInfo) onFinishTaskParamOnFinishTaskInner.args[0], onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 160:
                    onFinishListener.onMigrateCardKeyFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, (CardInfo) onFinishTaskParamOnFinishTaskInner.args[0], onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 256:
                    onFinishListener.onGetCardListV2Finished(onFinishTaskParamOnFinishTaskInner.isSuccess, onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 512:
                    onFinishListener.onGetCardAdditionalInfoListV2Finished(onFinishTaskParamOnFinishTaskInner.isSuccess, onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 1024:
                    onFinishListener.onWritePipeFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, (MfiClientExternalLogConst.MficApi) onFinishTaskParamOnFinishTaskInner.args[0], onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 2048:
                    onFinishListener.onInitializeFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 4096:
                    onFinishListener.onGetLinkageDataListFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, (String[]) onFinishTaskParamOnFinishTaskInner.args[0], onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 8192:
                    onFinishListener.onSilentStartForMfiAdminFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, ((Boolean) onFinishTaskParamOnFinishTaskInner.args[0]).booleanValue(), (Intent) onFinishTaskParamOnFinishTaskInner.args[1], onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 16384:
                    onFinishListener.onSilentStartFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, (Intent) onFinishTaskParamOnFinishTaskInner.args[1], onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 32768:
                    onFinishListener.onGetAccessCardFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 65536:
                    onFinishListener.onGetCardInfoListWithSpStatusFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 131072:
                    onFinishListener.onSaveDeleteCardV2Finished(onFinishTaskParamOnFinishTaskInner.isSuccess, (CardInfo) onFinishTaskParamOnFinishTaskInner.args[0], onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 262144:
                    onFinishListener.onGetCardListV3Finished(onFinishTaskParamOnFinishTaskInner.isSuccess, (CardInfo[]) onFinishTaskParamOnFinishTaskInner.args[0], onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 524288:
                    onFinishListener.onGetCardAdditionalInfoListV3Finished(onFinishTaskParamOnFinishTaskInner.isSuccess, (CardAdditionalInfo[]) onFinishTaskParamOnFinishTaskInner.args[0], onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 1048576:
                    onFinishListener.onGetCardInfoListWithSpStatusV3Finished(onFinishTaskParamOnFinishTaskInner.isSuccess, (CardInfoWithSpStatus[]) onFinishTaskParamOnFinishTaskInner.args[0], onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 2097152:
                    onFinishListener.onExecuteServerOperationFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, ((Boolean) onFinishTaskParamOnFinishTaskInner.args[0]).booleanValue(), onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case TASK_ID_PROVISION_SERVER_OPERATION /* 2228224 */:
                    onFinishListener.onProvisionServerOperationFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case TASK_ID_LEGACY_CARD_EXISTANCE_TASK /* 2236416 */:
                    onFinishListener.onCheckLegacyCardExistenceFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, ((Boolean) onFinishTaskParamOnFinishTaskInner.args[0]).booleanValue(), onFinishTaskParamOnFinishTaskInner.args[1] instanceof String ? (String) onFinishTaskParamOnFinishTaskInner.args[1] : null, onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case TASK_ID_DELETE_UNSUPPORT_MFI_SERVICE_1_CARD /* 2359296 */:
                    onFinishListener.onDeleteLegacyCardFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 4194304:
                    onFinishListener.onGetMfiTosDataFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, (Map) onFinishTaskParamOnFinishTaskInner.args[0], ((Boolean) onFinishTaskParamOnFinishTaskInner.args[1]).booleanValue(), onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 8388608:
                    onFinishListener.onGetGoogleTosFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, (Intent) onFinishTaskParamOnFinishTaskInner.args[0], onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case 16777216:
                    onFinishListener.onGetRemainedCardsFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, (CardInfo[]) onFinishTaskParamOnFinishTaskInner.args[0], (CardInfo[]) onFinishTaskParamOnFinishTaskInner.args[1], onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                case TASK_ID_UPLOAD_CARDS_TO_DELETE /* 18874368 */:
                    onFinishListener.onUploadCardsToDeleteFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg, (String[]) onFinishTaskParamOnFinishTaskInner.args[0]);
                    break;
                case TASK_ID_UPLOAD_CARDS_TO_PERMANENT_DELETE /* 18939904 */:
                    onFinishListener.onUploadCardsToPermanentDeleteFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg, (String[]) onFinishTaskParamOnFinishTaskInner.args[0]);
                    break;
                case TASK_ID_CLEAR_MEMORY /* 20971520 */:
                    onFinishListener.onClearMemoryFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
                    break;
                default:
                    LogMgr.log(1, "%s : Unknown task.", "801");
                    break;
            }
            LogMgr.log(6, "%s", "999");
        }
    }

    private synchronized OnFinishTaskParam onFinishTaskInner(TaskBase taskBase, boolean z, int i, String str) {
        OnFinishTaskParam onFinishTaskParam;
        boolean z2;
        int i2;
        String strExecutionPoint;
        boolean z3;
        int i3;
        String strExecutionPoint2;
        boolean z4;
        int i4;
        String strExecutionPoint3;
        boolean z5;
        int i5;
        String strExecutionPoint4;
        boolean z6;
        int i6;
        String strExecutionPoint5;
        CompleteCardInfo completeCardInfo;
        boolean z7;
        int i7;
        String strExecutionPoint6;
        CachedCardInfo cachedCardInfo;
        CompleteCardInfo.Cache cache;
        CompleteCardInfo.Cache cache2;
        boolean z8;
        int i8;
        String strExecutionPoint7;
        boolean z9;
        int i9;
        int i10;
        String str2;
        String strExecutionPoint8;
        boolean z10;
        int i11;
        int i12;
        String str3;
        String strExecutionPoint9;
        MfiClientExternalLogConst.MficApi mficApi;
        ParcelFileDescriptor parcelFileDescriptor;
        boolean z11;
        boolean z12;
        int i13;
        String strExecutionPoint10;
        boolean z13;
        int i14;
        int i15;
        String str4;
        String strExecutionPoint11;
        boolean z14;
        int i16;
        String strExecutionPoint12;
        boolean z15;
        int i17;
        String strExecutionPoint13;
        boolean z16;
        int i18;
        String strExecutionPoint14;
        boolean z17;
        int i19;
        String strExecutionPoint15;
        boolean z18;
        int i20;
        String strExecutionPoint16;
        boolean z19;
        int i21;
        String strExecutionPoint17;
        boolean z20;
        boolean z21;
        int i22;
        char c;
        CardInfo[] cardInfoListToPermanentDelete;
        char c2;
        String strExecutionPoint18;
        OnFinishTaskParam onFinishTaskParam2;
        LogMgr.log(6, "%s", "000");
        CardInfo[] cardInfoArr = null;
        OnFinishTaskParam onFinishTaskParam3 = null;
        cardInfoListToDelete = null;
        CardInfo[] cardInfoListToDelete = null;
        map = null;
        Map<String, String> map = null;
        cardInfoWithSpStatusArr = null;
        CardInfoWithSpStatus[] cardInfoWithSpStatusArr = null;
        cardAdditionalInfoArr = null;
        CardAdditionalInfo[] cardAdditionalInfoArr = null;
        cardInfoArr = null;
        CardInfo[] cardInfoArr2 = null;
        CompleteCardInfo cardInfo = null;
        String[] result2 = null;
        CompleteCardInfo result22 = null;
        cachedCardInfo = null;
        CachedCardInfo cachedCardInfo2 = null;
        completeCardInfo = null;
        CompleteCardInfo completeCardInfo2 = null;
        completeCardInfo = null;
        CompleteCardInfo completeCardInfo3 = null;
        cardAdditionalInfoArr = null;
        CardAdditionalInfo[] cardAdditionalInfoArr2 = null;
        cardInfoArr = null;
        this.mCurrentTask = null;
        if (this.mListener == null) {
            LogMgr.log(1, "%s Listener is null.", "800");
            return null;
        }
        MfiChipHolder mfiChipHolder = this.mChipHolder;
        if (mfiChipHolder != null) {
            mfiChipHolder.reset();
        }
        int taskId = taskBase.getTaskId();
        if (taskId != 2) {
            this.mIsSilentStartStopped = false;
        }
        LogMgr.log(6, "%s : taskId=%d", "001", Integer.valueOf(taskId));
        if (this.mWaitingLogoutArgs != null) {
            LogMgr.log(6, "002 do logout");
            if (taskId == 1024 && !z) {
                try {
                    ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) taskBase.getResult();
                    if (parcelFileDescriptor2 != null) {
                        LogMgr.log(6, "004 calling closeWithError");
                        parcelFileDescriptor2.closeWithError(MfiClientConst.PIPE_IO_EXCEPTION_MESSAGE_STOPPED);
                    }
                } catch (Exception e) {
                    LogMgr.log(2, "%s : %s:%s", "701", e.getClass().getSimpleName(), e.getMessage());
                    LogMgr.printStackTrace(7, e);
                    onFinishTaskParam2 = new OnFinishTaskParam(2, false, new Object[]{Boolean.valueOf(this.mIsSilentStartStopped)}, 200, ObfuscatedMsgUtil.exExecutionPoint(e));
                }
            }
            if (Arrays.asList(TASK_SILENT_START).contains(Integer.valueOf(taskId))) {
                this.mIsSilentStartStopped = true;
            }
            doLogout(this.mWaitingLogoutArgs.autoMfiServerLogout, this.mWaitingLogoutArgs.appCallerInfo, this.mWaitingLogoutArgs.appIdInfo, this.mWaitingLogoutArgs.noLogin);
            onFinishTaskParam2 = null;
            this.mWaitingLogoutArgs = null;
            LogMgr.log(6, "003");
            return onFinishTaskParam2;
        }
        switch (taskId) {
            case 1:
                DeprecatedStartTask.Result result23 = ((DeprecatedStartTask) taskBase).getResult2();
                if (result23 != null) {
                    onFinishTaskParam = new OnFinishTaskParam(taskId, z, new Object[]{Boolean.valueOf(result23.noLogin), result23.intent}, i, str);
                    this.mParams.loginTokenId = result23.loginTokenId;
                    this.mParams.accountId = result23.accountId;
                    this.mJwsCreator = result23.jwsCreator;
                    setCardList(result23.cardInfoMap, result23.cardIdInfoMap);
                } else {
                    onFinishTaskParam = new OnFinishTaskParam(taskId, z, new Object[]{false, null}, i, str);
                    this.mParams.loginTokenId = null;
                    this.mParams.accountId = null;
                }
                onFinishTaskParam3 = onFinishTaskParam;
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 2:
                onFinishTaskParam = new OnFinishTaskParam(taskId, z, new Object[]{Boolean.valueOf(this.mIsSilentStartStopped)}, i, str);
                onFinishTaskParam3 = onFinishTaskParam;
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 4:
                if (z) {
                    GetCardListTask.CardList result24 = ((GetCardListTask) taskBase).getResult2();
                    if (result24 != null) {
                        setCardList(result24);
                        cardInfoArr = (CardInfo[]) result24.cardInfoMap.values().toArray(new CardInfo[0]);
                        z2 = z;
                        i2 = i;
                        strExecutionPoint = str;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z2, new Object[]{cardInfoArr}, i2, strExecutionPoint);
                        onFinishTaskParam3 = onFinishTaskParam;
                    } else {
                        LogMgr.log(2, "%s : Fail to get result.", "702");
                        strExecutionPoint = ObfuscatedMsgUtil.executionPoint();
                        z2 = false;
                        i2 = 200;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z2, new Object[]{cardInfoArr}, i2, strExecutionPoint);
                        onFinishTaskParam3 = onFinishTaskParam;
                    }
                } else {
                    z2 = z;
                    i2 = i;
                    strExecutionPoint = str;
                    onFinishTaskParam = new OnFinishTaskParam(taskId, z2, new Object[]{cardInfoArr}, i2, strExecutionPoint);
                    onFinishTaskParam3 = onFinishTaskParam;
                }
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 8:
                if (z) {
                    GetCardListTask.CardList result25 = ((GetCardListTask) taskBase).getResult2();
                    if (result25 != null) {
                        cardAdditionalInfoArr2 = (CardAdditionalInfo[]) result25.cardAdditionalInfoList.toArray(new CardAdditionalInfo[0]);
                        z3 = z;
                        i3 = i;
                        strExecutionPoint2 = str;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z3, new Object[]{cardAdditionalInfoArr2}, i3, strExecutionPoint2);
                        onFinishTaskParam3 = onFinishTaskParam;
                    } else {
                        LogMgr.log(2, "%s : Fail to get result.", "703");
                        strExecutionPoint2 = ObfuscatedMsgUtil.executionPoint();
                        z3 = false;
                        i3 = 200;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z3, new Object[]{cardAdditionalInfoArr2}, i3, strExecutionPoint2);
                        onFinishTaskParam3 = onFinishTaskParam;
                    }
                } else {
                    z3 = z;
                    i3 = i;
                    strExecutionPoint2 = str;
                    onFinishTaskParam = new OnFinishTaskParam(taskId, z3, new Object[]{cardAdditionalInfoArr2}, i3, strExecutionPoint2);
                    onFinishTaskParam3 = onFinishTaskParam;
                }
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 16:
                if (z) {
                    IssueCardTask.IssueCard result26 = ((IssueCardTask) taskBase).getResult2();
                    if (result26 != null && result26.cardInfo != null && result26.cardIdInfo != null) {
                        completeCardInfo3 = result26.cardInfo;
                        putCardInfoToMap(result26.cardInfo);
                        putCardIdInfoToMap(result26.cardIdInfo);
                        if (result26.isMigratedCard) {
                            removeCardInfoToMap("D00000000000000000000000000000000000000000000000000000000000001");
                        }
                        z4 = z;
                        i4 = i;
                        strExecutionPoint3 = str;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z4, new Object[]{completeCardInfo3}, i4, strExecutionPoint3);
                        onFinishTaskParam3 = onFinishTaskParam;
                    } else {
                        LogMgr.log(2, "%s : Fail to get result.", "704");
                        strExecutionPoint3 = ObfuscatedMsgUtil.executionPoint();
                        z4 = false;
                        i4 = 200;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z4, new Object[]{completeCardInfo3}, i4, strExecutionPoint3);
                        onFinishTaskParam3 = onFinishTaskParam;
                    }
                } else {
                    z4 = z;
                    i4 = i;
                    strExecutionPoint3 = str;
                    onFinishTaskParam = new OnFinishTaskParam(taskId, z4, new Object[]{completeCardInfo3}, i4, strExecutionPoint3);
                    onFinishTaskParam3 = onFinishTaskParam;
                }
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 32:
                if (z) {
                    String result27 = ((DeleteCardTask) taskBase).getResult2();
                    if (result27 != null) {
                        removeCardInfoToMap(result27);
                        z5 = z;
                        i5 = i;
                        strExecutionPoint4 = str;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z5, new Object[0], i5, strExecutionPoint4);
                        onFinishTaskParam3 = onFinishTaskParam;
                    } else {
                        LogMgr.log(2, "%s : Fail to get result.", "705");
                        strExecutionPoint4 = ObfuscatedMsgUtil.executionPoint();
                        z5 = false;
                        i5 = 200;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z5, new Object[0], i5, strExecutionPoint4);
                        onFinishTaskParam3 = onFinishTaskParam;
                    }
                } else {
                    z5 = z;
                    i5 = i;
                    strExecutionPoint4 = str;
                    onFinishTaskParam = new OnFinishTaskParam(taskId, z5, new Object[0], i5, strExecutionPoint4);
                    onFinishTaskParam3 = onFinishTaskParam;
                }
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 64:
                if (z) {
                    List<CompleteCardInfo> result28 = ((EnableCardTask) taskBase).getResult2();
                    if (result28 == null || result28.size() != 2) {
                        completeCardInfo = null;
                    } else {
                        CompleteCardInfo completeCardInfo4 = result28.get(0);
                        if (completeCardInfo4 != null) {
                            putCardInfoToMap(completeCardInfo4);
                            completeCardInfo = result28.get(1);
                            if (completeCardInfo != null) {
                                putCardInfoToMap(completeCardInfo);
                            }
                        } else {
                            completeCardInfo = null;
                        }
                        completeCardInfo2 = completeCardInfo4;
                    }
                    if (completeCardInfo2 == null) {
                        LogMgr.log(2, "%s : Fail to get result.", "706");
                        strExecutionPoint5 = ObfuscatedMsgUtil.executionPoint();
                        z6 = false;
                        i6 = 200;
                    } else {
                        z6 = z;
                        i6 = i;
                        strExecutionPoint5 = str;
                    }
                } else {
                    z6 = z;
                    i6 = i;
                    strExecutionPoint5 = str;
                    completeCardInfo = null;
                }
                onFinishTaskParam = new OnFinishTaskParam(taskId, z6, new Object[]{completeCardInfo2, completeCardInfo}, i6, strExecutionPoint5);
                onFinishTaskParam3 = onFinishTaskParam;
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 68:
            case 70:
                onFinishTaskParam = new OnFinishTaskParam(taskId, z, new Object[]{((GetCachedCardListTask) taskBase).getResult2()}, i, str);
                onFinishTaskParam3 = onFinishTaskParam;
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 69:
                if (z) {
                    EnableCachedCardTask.Result result29 = ((EnableCachedCardTask) taskBase).getResult2();
                    List<CompleteCardInfo.Cache> list = result29.cardInfoList;
                    long j = result29.cachedTime;
                    if (list == null || list.size() != 2) {
                        cache = null;
                        cache2 = null;
                    } else {
                        cache = list.get(0);
                        cache2 = list.get(1);
                    }
                    if (cache == null) {
                        LogMgr.log(2, "700 : Fail to get result.");
                        strExecutionPoint6 = ObfuscatedMsgUtil.executionPoint();
                        cachedCardInfo = null;
                        z7 = false;
                        i7 = 200;
                    } else {
                        CachedCardInfo cachedCardInfo3 = new CachedCardInfo(cache, j);
                        CachedCardInfo cachedCardInfo4 = cache2 != null ? new CachedCardInfo(cache2, j) : null;
                        z7 = z;
                        strExecutionPoint6 = str;
                        cachedCardInfo = cachedCardInfo4;
                        cachedCardInfo2 = cachedCardInfo3;
                        i7 = i;
                    }
                } else {
                    z7 = z;
                    i7 = i;
                    strExecutionPoint6 = str;
                    cachedCardInfo = null;
                }
                onFinishTaskParam = new OnFinishTaskParam(taskId, z7, new Object[]{cachedCardInfo2, cachedCardInfo}, i7, strExecutionPoint6);
                onFinishTaskParam3 = onFinishTaskParam;
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 128:
                if (z) {
                    result22 = ((DisableCardTask) taskBase).getResult2();
                    if (result22 != null) {
                        putCardInfoToMap(result22);
                        z8 = z;
                        i8 = i;
                        strExecutionPoint7 = str;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z8, new Object[]{result22}, i8, strExecutionPoint7);
                        onFinishTaskParam3 = onFinishTaskParam;
                    } else {
                        LogMgr.log(2, "%s : Fail to get result.", "707");
                        strExecutionPoint7 = ObfuscatedMsgUtil.executionPoint();
                        z8 = false;
                        i8 = 200;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z8, new Object[]{result22}, i8, strExecutionPoint7);
                        onFinishTaskParam3 = onFinishTaskParam;
                    }
                } else {
                    z8 = z;
                    i8 = i;
                    strExecutionPoint7 = str;
                    onFinishTaskParam = new OnFinishTaskParam(taskId, z8, new Object[]{result22}, i8, strExecutionPoint7);
                    onFinishTaskParam3 = onFinishTaskParam;
                }
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 160:
                CompleteCardInfo result210 = ((MigrateCardKeyTask) taskBase).getResult2();
                MigrateCardKeyTask.CardList cardListForCacheUpdate = ((MigrateCardKeyTask) taskBase).getCardListForCacheUpdate();
                if (cardListForCacheUpdate != null) {
                    GetCardListTask.CardList cardList = new GetCardListTask.CardList();
                    cardList.cardInfoMap = cardListForCacheUpdate.cardInfoMap;
                    cardList.cardIdInfoMap = cardListForCacheUpdate.cardIdInfoMap;
                    setCardList(cardList);
                }
                onFinishTaskParam = new OnFinishTaskParam(taskId, z, new Object[]{result210}, i, str);
                onFinishTaskParam3 = onFinishTaskParam;
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 256:
                IPipeEventCallback iPipeEventCallback = this.mIPipeEventCallback;
                this.mIPipeEventCallback = null;
                if (z) {
                    GetCardListTask.CardList result211 = ((GetCardListTask) taskBase).getResult2();
                    if (result211 != null) {
                        setCardList(result211);
                        try {
                            WriteCardListTask writeCardListTask = new WriteCardListTask(1024, this.mExecutor, this, (CardInfo[]) result211.cardInfoMap.values().toArray(new CardInfo[0]), iPipeEventCallback);
                            writeCardListTask.start();
                            this.mCurrentTask = writeCardListTask;
                            z9 = z;
                            i9 = i;
                            i10 = taskId;
                            str2 = str;
                        } catch (Exception unused) {
                            LogMgr.log(2, "%s : Fail to start WriteCardListTask.", "709");
                            strExecutionPoint8 = ObfuscatedMsgUtil.executionPoint();
                            i10 = taskId;
                            z9 = false;
                            i9 = 200;
                            str2 = strExecutionPoint8;
                        }
                        onFinishTaskParam = new OnFinishTaskParam(i10, z9, new Object[]{null}, i9, str2);
                        onFinishTaskParam3 = onFinishTaskParam;
                    } else {
                        LogMgr.log(2, "%s : Fail to get result.", "710");
                        strExecutionPoint8 = ObfuscatedMsgUtil.executionPoint();
                    }
                    i10 = taskId;
                    z9 = false;
                    i9 = 200;
                    str2 = strExecutionPoint8;
                    onFinishTaskParam = new OnFinishTaskParam(i10, z9, new Object[]{null}, i9, str2);
                    onFinishTaskParam3 = onFinishTaskParam;
                } else {
                    z9 = z;
                    i9 = i;
                    i10 = taskId;
                    str2 = str;
                    onFinishTaskParam = new OnFinishTaskParam(i10, z9, new Object[]{null}, i9, str2);
                    onFinishTaskParam3 = onFinishTaskParam;
                }
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 512:
                IPipeEventCallback iPipeEventCallback2 = this.mIPipeEventCallback;
                this.mIPipeEventCallback = null;
                if (z) {
                    GetCardListTask.CardList result212 = ((GetCardListTask) taskBase).getResult2();
                    if (result212 != null) {
                        try {
                            WriteCardExListTask writeCardExListTask = new WriteCardExListTask(1024, this.mExecutor, this, (CardAdditionalInfo[]) result212.cardAdditionalInfoList.toArray(new CardAdditionalInfo[0]), iPipeEventCallback2);
                            writeCardExListTask.start();
                            this.mCurrentTask = writeCardExListTask;
                            z10 = z;
                            i11 = i;
                            i12 = taskId;
                            str3 = str;
                        } catch (Exception unused2) {
                            LogMgr.log(2, "%s : Fail to start WriteCardExListTask.", "712");
                            strExecutionPoint9 = ObfuscatedMsgUtil.executionPoint();
                            i12 = taskId;
                            z10 = false;
                            i11 = 200;
                            str3 = strExecutionPoint9;
                        }
                        onFinishTaskParam = new OnFinishTaskParam(i12, z10, new Object[]{null}, i11, str3);
                        onFinishTaskParam3 = onFinishTaskParam;
                    } else {
                        LogMgr.log(2, "%s : Fail to get result.", "713");
                        strExecutionPoint9 = ObfuscatedMsgUtil.executionPoint();
                    }
                    i12 = taskId;
                    z10 = false;
                    i11 = 200;
                    str3 = strExecutionPoint9;
                    onFinishTaskParam = new OnFinishTaskParam(i12, z10, new Object[]{null}, i11, str3);
                    onFinishTaskParam3 = onFinishTaskParam;
                } else {
                    z10 = z;
                    i11 = i;
                    i12 = taskId;
                    str3 = str;
                    onFinishTaskParam = new OnFinishTaskParam(i12, z10, new Object[]{null}, i11, str3);
                    onFinishTaskParam3 = onFinishTaskParam;
                }
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 1024:
                if (taskBase instanceof WriteCardExListTask) {
                    mficApi = MfiClientExternalLogConst.MficApi.USER_GET_CARD_ADDITIONAL_INFO_LIST;
                } else if (taskBase instanceof WriteCardListWithSpStatusTask) {
                    mficApi = MfiClientExternalLogConst.MficApi.USER_GET_CARD_INFO_LIST_WITH_SP_STATUS;
                } else {
                    mficApi = MfiClientExternalLogConst.MficApi.USER_GET_CARD_LIST;
                }
                if (!z && (parcelFileDescriptor = (ParcelFileDescriptor) taskBase.getResult()) != null) {
                    try {
                        if (i == 215) {
                            parcelFileDescriptor.closeWithError(MfiClientConst.PIPE_IO_EXCEPTION_MESSAGE_INTERRUPTED);
                        } else {
                            parcelFileDescriptor.closeWithError(str);
                        }
                    } catch (IOException e2) {
                        LogMgr.log(2, "711 " + e2.getMessage());
                        LogMgr.printStackTrace(7, e2);
                    }
                    onFinishTaskParam = new OnFinishTaskParam(taskId, true, new Object[]{mficApi}, 0, null);
                    break;
                } else {
                    onFinishTaskParam = new OnFinishTaskParam(taskId, z, new Object[]{mficApi}, i, str);
                }
                onFinishTaskParam3 = onFinishTaskParam;
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 2048:
                onFinishTaskParam = new OnFinishTaskParam(taskId, z, new Object[0], i, str);
                onFinishTaskParam3 = onFinishTaskParam;
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 4096:
                if (z) {
                    result2 = ((GetLinkageDataListTask) taskBase).getResult2();
                    z11 = z;
                } else {
                    LogMgr.log(2, "%s : Fail to get result.", "708");
                    z11 = false;
                }
                onFinishTaskParam = new OnFinishTaskParam(taskId, z11, new Object[]{result2}, i, str);
                onFinishTaskParam3 = onFinishTaskParam;
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 8192:
            case 16384:
                StartTask.Result result213 = ((StartTask) taskBase).getResult2();
                if (result213 != null) {
                    onFinishTaskParam = new OnFinishTaskParam(taskId, z, new Object[]{Boolean.valueOf(result213.noLogin), result213.intent}, i, str);
                    this.mParams.loginTokenId = result213.loginTokenId;
                    this.mParams.accountId = result213.accountId;
                    this.mJwsCreator = result213.jwsCreator;
                    setCardList(result213.cardInfoMap, result213.cardIdInfoMap);
                } else {
                    onFinishTaskParam = new OnFinishTaskParam(taskId, z, new Object[]{false, null}, i, str);
                    this.mParams.loginTokenId = null;
                    this.mParams.accountId = null;
                }
                onFinishTaskParam3 = onFinishTaskParam;
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 32768:
                if (z) {
                    CompleteCardInfo result214 = ((SEAccessTask) taskBase).getResult2();
                    if (result214 != null) {
                        putCardInfoToMap(result214);
                        z12 = z;
                        i13 = i;
                        strExecutionPoint10 = str;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z12, new Object[0], i13, strExecutionPoint10);
                        onFinishTaskParam3 = onFinishTaskParam;
                    } else {
                        LogMgr.log(2, "%s : Fail to get result.", "707");
                        strExecutionPoint10 = ObfuscatedMsgUtil.executionPoint();
                        z12 = false;
                        i13 = 200;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z12, new Object[0], i13, strExecutionPoint10);
                        onFinishTaskParam3 = onFinishTaskParam;
                    }
                } else {
                    z12 = z;
                    i13 = i;
                    strExecutionPoint10 = str;
                    onFinishTaskParam = new OnFinishTaskParam(taskId, z12, new Object[0], i13, strExecutionPoint10);
                    onFinishTaskParam3 = onFinishTaskParam;
                }
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 65536:
                IPipeEventCallback iPipeEventCallback3 = this.mIPipeEventCallback;
                this.mIPipeEventCallback = null;
                if (z) {
                    GetCardListTask.CardList result215 = ((GetCardListTask) taskBase).getResult2();
                    if (result215 != null) {
                        try {
                            WriteCardListWithSpStatusTask writeCardListWithSpStatusTask = new WriteCardListWithSpStatusTask(1024, this.mExecutor, this, (CardInfoWithSpStatus[]) result215.cardInfoWithSpStatusList.toArray(new CardInfoWithSpStatus[0]), iPipeEventCallback3);
                            writeCardListWithSpStatusTask.start();
                            this.mCurrentTask = writeCardListWithSpStatusTask;
                            z13 = z;
                            i14 = i;
                            i15 = taskId;
                            str4 = str;
                        } catch (Exception unused3) {
                            LogMgr.log(2, "%s : Fail to start WriteCardListTask.", "709");
                            strExecutionPoint11 = ObfuscatedMsgUtil.executionPoint();
                            i15 = taskId;
                            z13 = false;
                            i14 = 200;
                            str4 = strExecutionPoint11;
                        }
                        onFinishTaskParam = new OnFinishTaskParam(i15, z13, new Object[]{null}, i14, str4);
                        onFinishTaskParam3 = onFinishTaskParam;
                    } else {
                        LogMgr.log(2, "%s : Fail to get result.", "710");
                        strExecutionPoint11 = ObfuscatedMsgUtil.executionPoint();
                    }
                    i15 = taskId;
                    z13 = false;
                    i14 = 200;
                    str4 = strExecutionPoint11;
                    onFinishTaskParam = new OnFinishTaskParam(i15, z13, new Object[]{null}, i14, str4);
                    onFinishTaskParam3 = onFinishTaskParam;
                } else {
                    z13 = z;
                    i14 = i;
                    i15 = taskId;
                    str4 = str;
                    onFinishTaskParam = new OnFinishTaskParam(i15, z13, new Object[]{null}, i14, str4);
                    onFinishTaskParam3 = onFinishTaskParam;
                }
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 131072:
                if (z) {
                    String result216 = ((DeleteCardTask) taskBase).getResult2();
                    cardInfo = ((DeleteCardTask) taskBase).getCardInfo();
                    if (result216 != null) {
                        removeCardInfoToMap(result216);
                        z14 = z;
                        i16 = i;
                        strExecutionPoint12 = str;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z14, new Object[]{cardInfo}, i16, strExecutionPoint12);
                        onFinishTaskParam3 = onFinishTaskParam;
                    } else {
                        LogMgr.log(2, "%s : Fail to get result.", "705");
                        strExecutionPoint12 = ObfuscatedMsgUtil.executionPoint();
                        z14 = false;
                        i16 = 200;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z14, new Object[]{cardInfo}, i16, strExecutionPoint12);
                        onFinishTaskParam3 = onFinishTaskParam;
                    }
                } else {
                    z14 = z;
                    i16 = i;
                    strExecutionPoint12 = str;
                    onFinishTaskParam = new OnFinishTaskParam(taskId, z14, new Object[]{cardInfo}, i16, strExecutionPoint12);
                    onFinishTaskParam3 = onFinishTaskParam;
                }
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 262144:
                if (z) {
                    GetCardListTask.CardList result217 = ((GetCardListTask) taskBase).getResult2();
                    if (result217 != null) {
                        setCardList(result217);
                        cardInfoArr2 = (CardInfo[]) result217.cardInfoMap.values().toArray(new CardInfo[0]);
                        z15 = z;
                        i17 = i;
                        strExecutionPoint13 = str;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z15, new Object[]{cardInfoArr2}, i17, strExecutionPoint13);
                        onFinishTaskParam3 = onFinishTaskParam;
                    } else {
                        LogMgr.log(2, "714 : Fail to get result.");
                        strExecutionPoint13 = ObfuscatedMsgUtil.executionPoint();
                        z15 = false;
                        i17 = 200;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z15, new Object[]{cardInfoArr2}, i17, strExecutionPoint13);
                        onFinishTaskParam3 = onFinishTaskParam;
                    }
                } else {
                    z15 = z;
                    i17 = i;
                    strExecutionPoint13 = str;
                    onFinishTaskParam = new OnFinishTaskParam(taskId, z15, new Object[]{cardInfoArr2}, i17, strExecutionPoint13);
                    onFinishTaskParam3 = onFinishTaskParam;
                }
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 524288:
                if (z) {
                    GetCardListTask.CardList result218 = ((GetCardListTask) taskBase).getResult2();
                    if (result218 != null) {
                        cardAdditionalInfoArr = (CardAdditionalInfo[]) result218.cardAdditionalInfoList.toArray(new CardAdditionalInfo[0]);
                        z16 = z;
                        i18 = i;
                        strExecutionPoint14 = str;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z16, new Object[]{cardAdditionalInfoArr}, i18, strExecutionPoint14);
                        onFinishTaskParam3 = onFinishTaskParam;
                    } else {
                        LogMgr.log(2, "715 : Fail to get result.");
                        strExecutionPoint14 = ObfuscatedMsgUtil.executionPoint();
                        z16 = false;
                        i18 = 200;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z16, new Object[]{cardAdditionalInfoArr}, i18, strExecutionPoint14);
                        onFinishTaskParam3 = onFinishTaskParam;
                    }
                } else {
                    z16 = z;
                    i18 = i;
                    strExecutionPoint14 = str;
                    onFinishTaskParam = new OnFinishTaskParam(taskId, z16, new Object[]{cardAdditionalInfoArr}, i18, strExecutionPoint14);
                    onFinishTaskParam3 = onFinishTaskParam;
                }
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 1048576:
                if (z) {
                    GetCardListTask.CardList result219 = ((GetCardListTask) taskBase).getResult2();
                    if (result219 != null) {
                        cardInfoWithSpStatusArr = (CardInfoWithSpStatus[]) result219.cardInfoWithSpStatusList.toArray(new CardInfoWithSpStatus[0]);
                        z17 = z;
                        i19 = i;
                        strExecutionPoint15 = str;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z17, new Object[]{cardInfoWithSpStatusArr}, i19, strExecutionPoint15);
                        onFinishTaskParam3 = onFinishTaskParam;
                    } else {
                        LogMgr.log(2, "716 : Fail to get result.");
                        strExecutionPoint15 = ObfuscatedMsgUtil.executionPoint();
                        z17 = false;
                        i19 = 200;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z17, new Object[]{cardInfoWithSpStatusArr}, i19, strExecutionPoint15);
                        onFinishTaskParam3 = onFinishTaskParam;
                    }
                } else {
                    z17 = z;
                    i19 = i;
                    strExecutionPoint15 = str;
                    onFinishTaskParam = new OnFinishTaskParam(taskId, z17, new Object[]{cardInfoWithSpStatusArr}, i19, strExecutionPoint15);
                    onFinishTaskParam3 = onFinishTaskParam;
                }
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 2097152:
                onFinishTaskParam = new OnFinishTaskParam(taskId, z, new Object[]{((ExecuteServerOperationTask) taskBase).getResult2()}, i, str);
                onFinishTaskParam3 = onFinishTaskParam;
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case TASK_ID_PROVISION_SERVER_OPERATION /* 2228224 */:
                onFinishTaskParam = new OnFinishTaskParam(taskId, z, new Object[0], i, str);
                onFinishTaskParam3 = onFinishTaskParam;
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case TASK_ID_LEGACY_CARD_EXISTANCE_TASK /* 2236416 */:
                CheckLegacyCardExistenceTask.Result result220 = ((CheckLegacyCardExistenceTask) taskBase).getResult2();
                onFinishTaskParam = new OnFinishTaskParam(taskId, z, new Object[]{Boolean.valueOf(result220.exist), result220.localPartialCardInfoJson}, i, str);
                onFinishTaskParam3 = onFinishTaskParam;
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case TASK_ID_DELETE_UNSUPPORT_MFI_SERVICE_1_CARD /* 2359296 */:
                if (z) {
                    String result221 = ((DeleteLegacyCardTask) taskBase).getResult2();
                    if (result221 != null) {
                        if (result221.equals(MfiClientConst.NO_CID_INSTANCE_KEY)) {
                            result221 = "D00000000000000000000000000000000000000000000000000000000000001";
                        }
                        LogMgr.log(6, "010 : remove card cache: cid = " + result221);
                        removeCardInfoToMap(result221);
                        z18 = z;
                        i20 = i;
                        strExecutionPoint16 = str;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z18, new Object[0], i20, strExecutionPoint16);
                        onFinishTaskParam3 = onFinishTaskParam;
                    } else {
                        LogMgr.log(2, "711 : Fail to get result.");
                        strExecutionPoint16 = ObfuscatedMsgUtil.executionPoint();
                        z18 = false;
                        i20 = 200;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z18, new Object[0], i20, strExecutionPoint16);
                        onFinishTaskParam3 = onFinishTaskParam;
                    }
                } else {
                    z18 = z;
                    i20 = i;
                    strExecutionPoint16 = str;
                    onFinishTaskParam = new OnFinishTaskParam(taskId, z18, new Object[0], i20, strExecutionPoint16);
                    onFinishTaskParam3 = onFinishTaskParam;
                }
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 4194304:
                if (z) {
                    GetMfiTosDataTask.Result result222 = ((GetMfiTosDataTask) taskBase).getResult2();
                    if (result222 != null) {
                        map = result222.mfiTosDataJsonMap;
                        z20 = result222.isMfiTosAgreed;
                        z19 = z;
                        i21 = i;
                        strExecutionPoint17 = str;
                    } else {
                        LogMgr.log(2, "717 : Fail to get result.");
                        strExecutionPoint17 = ObfuscatedMsgUtil.executionPoint();
                        z20 = false;
                        z19 = false;
                        i21 = 200;
                    }
                } else {
                    z19 = z;
                    i21 = i;
                    strExecutionPoint17 = str;
                    z20 = false;
                }
                onFinishTaskParam = new OnFinishTaskParam(taskId, z19, new Object[]{map, Boolean.valueOf(z20)}, i21, strExecutionPoint17);
                onFinishTaskParam3 = onFinishTaskParam;
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 8388608:
                onFinishTaskParam = new OnFinishTaskParam(taskId, z, new Object[]{z ? ((GetGoogleTosTask) taskBase).getResult2() : null}, i, str);
                onFinishTaskParam3 = onFinishTaskParam;
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case 16777216:
                if (z) {
                    RemainedCardsCache result223 = ((GetRemainedCardsTask) taskBase).getResult2();
                    if (result223 != null) {
                        this.mRemainedCardsCache = result223;
                        cardInfoListToDelete = result223.getCardInfoListToDelete();
                        cardInfoListToPermanentDelete = result223.getCardInfoListToPermanentDelete();
                        z21 = z;
                        i22 = i;
                        c = 1;
                    } else {
                        LogMgr.log(2, "718 : Fail to get result.");
                        c2 = 1;
                        z21 = false;
                        i22 = 200;
                        strExecutionPoint18 = ObfuscatedMsgUtil.executionPoint();
                        cardInfoListToPermanentDelete = null;
                        Object[] objArr = new Object[2];
                        objArr[0] = cardInfoListToDelete;
                        objArr[c2] = cardInfoListToPermanentDelete;
                        onFinishTaskParam = new OnFinishTaskParam(taskId, z21, objArr, i22, strExecutionPoint18);
                        onFinishTaskParam3 = onFinishTaskParam;
                        LogMgr.log(6, "%s", "999");
                        return onFinishTaskParam3;
                    }
                } else {
                    z21 = z;
                    i22 = i;
                    c = 1;
                    cardInfoListToPermanentDelete = null;
                }
                strExecutionPoint18 = str;
                c2 = c;
                Object[] objArr2 = new Object[2];
                objArr2[0] = cardInfoListToDelete;
                objArr2[c2] = cardInfoListToPermanentDelete;
                onFinishTaskParam = new OnFinishTaskParam(taskId, z21, objArr2, i22, strExecutionPoint18);
                onFinishTaskParam3 = onFinishTaskParam;
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case TASK_ID_UPLOAD_CARDS_TO_DELETE /* 18874368 */:
                String[] strArr = new String[0];
                if (z) {
                    this.mRemainedCardsCache.clearInfoToDelete();
                } else {
                    UploadCardsTask.Result result = ((UploadCardsToDeleteTask) taskBase).getResult2();
                    if (result != null) {
                        strArr = (String[]) result.uploadedCidList.toArray(new String[0]);
                    }
                }
                onFinishTaskParam = new OnFinishTaskParam(taskId, z, new Object[]{strArr}, i, str);
                onFinishTaskParam3 = onFinishTaskParam;
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case TASK_ID_UPLOAD_CARDS_TO_PERMANENT_DELETE /* 18939904 */:
                String[] strArr2 = new String[0];
                if (z) {
                    this.mRemainedCardsCache.clearInfoToPermanentDelete();
                } else {
                    UploadCardsTask.Result result224 = ((UploadCardsToPermanentDeleteTask) taskBase).getResult2();
                    if (result224 != null) {
                        strArr2 = (String[]) result224.uploadedCidList.toArray(new String[0]);
                    }
                }
                onFinishTaskParam = new OnFinishTaskParam(taskId, z, new Object[]{strArr2}, i, str);
                onFinishTaskParam3 = onFinishTaskParam;
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            case TASK_ID_CLEAR_MEMORY /* 20971520 */:
                onFinishTaskParam = new OnFinishTaskParam(taskId, z, null, i, str);
                onFinishTaskParam3 = onFinishTaskParam;
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
            default:
                LogMgr.log(1, "%s : Unknown task.", "801");
                LogMgr.log(6, "%s", "999");
                return onFinishTaskParam3;
        }
    }

    private synchronized void setCardList(GetCardListTask.CardList cardList) {
        if (cardList.cardInfoMap != null) {
            LogMgr.log(6, "%s Create Params.cardInfoMap", "001");
            this.mParams.cardInfoMap = new HashMap();
            for (Map.Entry<String, CompleteCardInfo> entry : cardList.cardInfoMap.entrySet()) {
                CompleteCardInfo value = entry.getValue();
                if (value.getCardStatus() != 3) {
                    this.mParams.cardInfoMap.put(entry.getKey(), value.getCacheableData());
                } else if (value.getCardPosition() == 2) {
                    this.mParams.cardInfoMap.put(entry.getKey(), value.getCacheableData());
                }
            }
        }
        if (cardList.cardIdInfoMap != null) {
            LogMgr.log(6, "%s Create Params.cardIdInfoMap", "002");
            this.mParams.cardIdInfoMap = new HashMap();
            for (Map.Entry<String, CardIdentifiableInfo> entry2 : cardList.cardIdInfoMap.entrySet()) {
                this.mParams.cardIdInfoMap.put(entry2.getKey(), entry2.getValue().getCacheableData());
            }
        }
    }

    private synchronized void setCardList(Map<String, CompleteCardInfo.Cache> cardInfoMap, Map<String, CardIdentifiableInfo.Cache> cardIdInfoMap) {
        LogMgr.log(6, "%s Set Params.cardInfoMap :%s", "001", cardInfoMap);
        LogMgr.log(6, "%s Set Params.cardIdInfoMap :%s", "002", cardIdInfoMap);
        this.mParams.cardInfoMap = cardInfoMap;
        this.mParams.cardIdInfoMap = cardIdInfoMap;
    }

    private synchronized void putCardInfoToMap(CompleteCardInfo cardInfo) {
        if (this.mParams.cardInfoMap == null) {
            LogMgr.log(6, "%s Create Params.cardInfoMap", "001");
            this.mParams.cardInfoMap = new HashMap();
        }
        this.mParams.cardInfoMap.put(cardInfo.getCid(), cardInfo.getCacheableData());
    }

    private synchronized void removeCardInfoToMap(String cid) {
        if (this.mParams.cardInfoMap != null) {
            this.mParams.cardInfoMap.remove(cid);
        }
    }

    private synchronized CompleteCardInfo.Cache getCardInfo(String cid) throws MfiClientException {
        if (this.mParams.cardInfoMap != null) {
            if (this.mParams.cardInfoMap.containsKey(cid)) {
            } else {
                LogMgr.log(1, "%s : CID is null.", "801");
                throw new MfiClientException(102, 158, null);
            }
        } else {
            LogMgr.log(1, "%s : cardInfoMap is null.", "800");
            throw new MfiClientException(102, 157, null);
        }
        return (CompleteCardInfo.Cache) this.mParams.cardInfoMap.get(cid);
    }

    private synchronized void putCardIdInfoToMap(CardIdentifiableInfo cardIdInfo) {
        if (this.mParams.cardIdInfoMap == null) {
            LogMgr.log(6, "%s Create Params.cardIdInfoMap", "001");
            this.mParams.cardIdInfoMap = new HashMap();
        }
        this.mParams.cardIdInfoMap.put(cardIdInfo.serviceId, cardIdInfo.getCacheableData());
    }

    private synchronized CardIdentifiableInfo.Cache getCardIdInfo(String serviceId) throws MfiClientException {
        if (this.mParams.cardIdInfoMap != null) {
            if (this.mParams.cardIdInfoMap.containsKey(serviceId)) {
            } else {
                LogMgr.log(1, "%s : Service is null.", "801");
                throw new MfiClientException(102, 158, null);
            }
        } else {
            LogMgr.log(1, "%s : cardIdInfoMap is null.", "800");
            throw new MfiClientException(102, 157, null);
        }
        return (CardIdentifiableInfo.Cache) this.mParams.cardIdInfoMap.get(serviceId);
    }

    private void checkSeid(CompleteCardInfo.Cache cardInfo) throws MfiClientException {
        try {
            if (cardInfo.seId.equalsIgnoreCase(this.mDataManager.getSeInfo().getSeId())) {
                return;
            }
            LogMgr.log(1, "800 SEID does not match.");
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        } catch (MfiClientException e) {
            throw e;
        } catch (Exception e2) {
            LogMgr.log(1, "801 " + e2.getClass().getSimpleName() + ":" + e2.getMessage());
            LogMgr.printStackTrace(7, e2);
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }
}
