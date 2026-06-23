package com.felicanetworks.mfc.mfi.fws;

import android.content.Intent;
import com.felicanetworks.mfc.mfi.CachedCardInfo;
import com.felicanetworks.mfc.mfi.CardAdditionalInfo;
import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import com.felicanetworks.mfc.mfi.CardInfo;
import com.felicanetworks.mfc.mfi.CardInfoWithSpStatus;
import com.felicanetworks.mfc.mfi.CompleteCardInfo;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.IPipeEventCallback;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfc.mfi.MfiClientExternalLogConst;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.GetCardListTask;
import com.felicanetworks.mfc.mfi.fws.StartTask;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.util.ServiceTypeInfoUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes.dex */
public class FwsClientFacade implements AsyncTaskBase.Listener {
    private static final Integer[] TASK_CAN_NOT_FINISH_WITH_CANCEL_CARD_OPERATION;
    private static final Integer[] TASK_CAN_NOT_FINISH_WITH_STOP;
    private static final int TASK_ID_DELETE_CARD = 32;
    private static final int TASK_ID_DELETE_UNSUPPORT_MFI_SERVICE_1_CARD = 2359296;
    private static final int TASK_ID_DISABLE_CARD = 128;
    private static final int TASK_ID_ENABLE_CACHED_CARD = 69;
    private static final int TASK_ID_ENABLE_CARD = 64;
    private static final int TASK_ID_EXECUTE_SERVER_OPERATION = 2097152;
    private static final int TASK_ID_GET_CACHED_CARD_LIST = 68;
    private static final int TASK_ID_GET_CARD_EX_LIST = 8;
    private static final int TASK_ID_GET_CARD_EX_LIST_V3 = 524288;
    private static final int TASK_ID_GET_CARD_EX_LIST_WITH_PIPE = 512;
    private static final int TASK_ID_GET_CARD_LIST = 4;
    private static final int TASK_ID_GET_CARD_LIST_V3 = 262144;
    private static final int TASK_ID_GET_CARD_LIST_WITH_PIPE = 256;
    private static final int TASK_ID_GET_CARD_LIST_WITH_SP_STATUS = 65536;
    private static final int TASK_ID_GET_CARD_LIST_WITH_SP_STATUS_V3 = 1048576;
    private static final int TASK_ID_GET_LINKAGE_DATA_LIST = 4096;
    private static final int TASK_ID_INITIALIZE = 2048;
    private static final int TASK_ID_ISSUE_CARD = 16;
    private static final int TASK_ID_LEGACY_CARD_EXISTANCE_TASK = 2236416;
    private static final int TASK_ID_LOGIN = 1;
    private static final int TASK_ID_LOGOUT = 2;
    private static final int TASK_ID_PROVISION_SERVER_OPERATION = 2228224;
    private static final int TASK_ID_SAVE_DELETE_CARD = 131072;
    private static final int TASK_ID_SE_ACCESS = 32768;
    private static final int TASK_ID_SILENT_START = 16384;
    private static final int TASK_ID_SILENT_START_FOR_ADMIN = 8192;
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
    private a mJwsCreator = null;
    private boolean mIsStarted = false;
    private LogoutArguments mWaitingLogoutArgs = null;

    public interface OnFinishListener {
        void onCheckLegacyCardExistenceFinished(boolean z, boolean z2, String str, int i, String str2);

        void onDeleteCardFinished(boolean z, int i, String str);

        void onDeleteLegacyCardFinished(boolean z, int i, String str);

        void onDisableCardFinished(boolean z, CardInfo cardInfo, int i, String str);

        void onEnableCachedCardFinished(boolean z, CachedCardInfo cachedCardInfo, CachedCardInfo cachedCardInfo2, int i, String str);

        void onEnableCardFinished(boolean z, CardInfo cardInfo, CardInfo cardInfo2, int i, String str);

        void onExecuteServerOperationFinished(boolean z, boolean z2, int i, String str);

        void onGetAccessCardFinished(boolean z, int i, String str);

        void onGetCachedCardListFinished(boolean z, String[] strArr, int i, String str);

        void onGetCardAdditionalInfoListFinished(boolean z, CardAdditionalInfo[] cardAdditionalInfoArr, int i, String str);

        void onGetCardAdditionalInfoListV2Finished(boolean z, int i, String str);

        void onGetCardAdditionalInfoListV3Finished(boolean z, CardAdditionalInfo[] cardAdditionalInfoArr, int i, String str);

        void onGetCardInfoListWithSpStatusFinished(boolean z, int i, String str);

        void onGetCardInfoListWithSpStatusV3Finished(boolean z, CardInfoWithSpStatus[] cardInfoWithSpStatusArr, int i, String str);

        void onGetCardListFinished(boolean z, CardInfo[] cardInfoArr, int i, String str);

        void onGetCardListV2Finished(boolean z, int i, String str);

        void onGetCardListV3Finished(boolean z, CardInfo[] cardInfoArr, int i, String str);

        void onGetLinkageDataListFinished(boolean z, String[] strArr, int i, String str);

        void onInitializeFinished(boolean z, int i, String str);

        void onIssueCardFinished(boolean z, CardInfo cardInfo, int i, String str);

        void onLoginFinished(boolean z, int i, String str);

        void onLogoutFinished(boolean z, boolean z2, int i, String str);

        void onProvisionServerOperationFinished(boolean z, int i, String str);

        void onSaveDeleteCardV2Finished(boolean z, CardInfo cardInfo, int i, String str);

        void onSilentStartFinished(boolean z, Intent intent, int i, String str);

        void onSilentStartForMfiAdminFinished(boolean z, boolean z2, Intent intent, int i, String str);

        void onWritePipeFinished(boolean z, MfiClientExternalLogConst.MficApi mficApi, int i, String str);
    }

    static {
        Integer numValueOf = Integer.valueOf(TASK_ID_PROVISION_SERVER_OPERATION);
        Integer numValueOf2 = Integer.valueOf(TASK_ID_LEGACY_CARD_EXISTANCE_TASK);
        Integer numValueOf3 = Integer.valueOf(TASK_ID_DELETE_UNSUPPORT_MFI_SERVICE_1_CARD);
        TASK_CAN_NOT_FINISH_WITH_CANCEL_CARD_OPERATION = new Integer[]{1, 2, 16384, 8192, 4096, 2048, numValueOf, 2097152, numValueOf2, numValueOf3, 68, 69};
        TASK_CAN_NOT_FINISH_WITH_STOP = new Integer[]{1, 2};
        TASK_SILENT_START = new Integer[]{16384, 8192, numValueOf, 2097152, numValueOf2, numValueOf3, 68, 69};
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

        LogoutArguments(boolean z, String str, String str2, boolean z2) {
            this.autoMfiServerLogout = z;
            this.appCallerInfo = str;
            this.appIdInfo = str2;
            this.noLogin = z2;
        }
    }

    private class OnFinishTaskParam {
        Object[] args;
        String errMsg;
        int errType;
        boolean isSuccess;
        int taskId;

        OnFinishTaskParam(int i, boolean z, Object[] objArr, int i2, String str) {
            this.taskId = i;
            this.isSuccess = z;
            this.errType = i2;
            this.errMsg = str;
            this.args = objArr;
        }
    }

    public synchronized void start(OnFinishListener onFinishListener, DataManager dataManager, String str) throws MfiClientException {
        LogMgr.log(4, "%s", "000");
        if (this.mIsStarted) {
            LogMgr.log(1, "%s : Already running online task.", "800");
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
        if (onFinishListener == null) {
            LogMgr.log(1, "%s : Listener is null.", "801");
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
        this.mListener = onFinishListener;
        this.mDataManager = dataManager;
        dataManager.onStartMfiClient(str);
        this.mFwsClient = new FwsClient(dataManager);
        this.mChipHolder = new MfiChipHolder();
        this.mParams = new Parameters();
        if (this.mExecutor != null) {
            this.mExecutor.shutdown();
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
        if (this.mCurrentTask != null) {
            this.mCurrentTask.stop();
            this.mCurrentTask = null;
        }
        if (this.mFwsClient != null) {
            this.mFwsClient.stop();
            this.mFwsClient = null;
        }
        if (this.mChipHolder != null) {
            this.mChipHolder.discard();
            this.mChipHolder = null;
        }
        if (this.mExecutor != null) {
            this.mExecutor.shutdown();
            this.mExecutor = null;
        }
        if (this.mJwsCreator != null) {
            this.mJwsCreator.a();
            this.mJwsCreator = null;
        }
        if (this.mDataManager != null) {
            this.mDataManager.onStopMfiClient();
            this.mDataManager = null;
        }
        LogMgr.log(4, "%s", "999");
    }

    public synchronized void login(String str, String str2, boolean z, String str3, String str4) throws MfiClientException {
        LogMgr.log(4, "%s", "000");
        checkStarted();
        checkNotRunningTask();
        StartTask startTask = new StartTask(1, this.mExecutor, this, str, str2, 0, false, z, str3, str4, this.mFwsClient, this.mChipHolder, this.mDataManager);
        startTask.start();
        this.mCurrentTask = startTask;
        LogMgr.log(4, "%s", "999");
    }

    public synchronized void silentStart(String str, String str2, boolean z, String str3, String str4, int i) throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        StartTask startTask = new StartTask(16384, this.mExecutor, this, str, str2, i, true, z, str3, str4, this.mFwsClient, this.mChipHolder, this.mDataManager);
        startTask.start();
        this.mCurrentTask = startTask;
        LogMgr.log(4, "999");
    }

    public synchronized void silentStartForMfiAdmin(String str, String str2, boolean z, String str3, String str4, boolean z2, int i, int i2, boolean z3) throws Throwable {
        try {
            LogMgr.log(4, "000");
            checkStarted();
            checkNotRunningTask();
            StartTask.AdditionalParams additionalParams = new StartTask.AdditionalParams();
            additionalParams.noLogin = !z2;
            additionalParams.isAdmin = true;
            additionalParams.layoutType = i2;
            additionalParams.isPrivileged = z3;
            try {
                StartTask startTask = new StartTask(8192, this.mExecutor, this, str, str2, i, true, z, str3, str4, this.mFwsClient, this.mChipHolder, this.mDataManager, additionalParams);
                startTask.start();
                try {
                    this.mCurrentTask = startTask;
                    LogMgr.log(4, "999");
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public synchronized void logout(boolean z, String str, String str2, boolean z2) throws MfiClientException {
        LogMgr.log(4, "%s autoMfiServerLogout=%s", "000", Boolean.valueOf(z));
        checkStarted();
        checkNotRunningNonStoppableTask();
        if (this.mCurrentTask != null) {
            LogMgr.log(6, "001 call stopTask and wait");
            this.mWaitingLogoutArgs = new LogoutArguments(z, str, str2, z2);
            stopTask();
        } else {
            doLogout(z, str, str2, z2);
        }
        LogMgr.log(4, "%s", "999");
    }

    private void doLogout(boolean z, String str, String str2, boolean z2) {
        LogMgr.log(4, "000 autoMfiServerLogout=" + z + " noLogin=" + z2);
        StopTask stopTask = new StopTask(2, this.mExecutor, this, this.mParams.loginTokenId, this.mParams.accountId, z, str, str2, this.mParams.cardInfoMap, this.mParams.cardIdInfoMap, this.mFwsClient);
        if (z2) {
            stopTask.requestNoLogin();
        }
        stopTask.start();
        this.mCurrentTask = stopTask;
        LogMgr.log(6, "%s", "999");
    }

    public synchronized void initialize(String str) throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        InitializeTask initializeTask = new InitializeTask(2048, this.mExecutor, this, str, this.mFwsClient, this.mChipHolder);
        initializeTask.start();
        this.mCurrentTask = initializeTask;
        LogMgr.log(4, "999");
    }

    public synchronized void getCardList(JSONArray jSONArray, String str) throws MfiClientException {
        LogMgr.log(4, "%s", "000");
        checkStarted();
        checkNotRunningTask();
        GetCardListTask getCardListTask = new GetCardListTask(4, this.mExecutor, this, this.mParams.loginTokenId, jSONArray, this.mFwsClient, this.mChipHolder, this.mDataManager, GetCardListTask.Options.createCardListOptions(str), this.mJwsCreator);
        getCardListTask.start();
        this.mCurrentTask = getCardListTask;
        LogMgr.log(4, "%s", "999");
    }

    public synchronized void getCachedCardList(String str, String str2) throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        GetCachedCardListTask getCachedCardListTask = new GetCachedCardListTask(68, this.mExecutor, this, this.mChipHolder, this.mDataManager, str, str2);
        getCachedCardListTask.start();
        this.mCurrentTask = getCachedCardListTask;
        LogMgr.log(4, "999");
    }

    public synchronized void enableCachedCard(CachedCardInfo cachedCardInfo, String str, String str2) throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        EnableCachedCardTask enableCachedCardTask = new EnableCachedCardTask(69, this.mExecutor, this, cachedCardInfo, str, str2, this.mChipHolder, this.mDataManager);
        enableCachedCardTask.start();
        this.mCurrentTask = enableCachedCardTask;
        LogMgr.log(4, "999");
    }

    public synchronized void getCardListWithPipe(JSONArray jSONArray, String str, IPipeEventCallback iPipeEventCallback) throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        GetCardListTask getCardListTask = new GetCardListTask(256, this.mExecutor, this, this.mParams.loginTokenId, jSONArray, this.mFwsClient, this.mChipHolder, this.mDataManager, GetCardListTask.Options.createCardListOptions(str), this.mJwsCreator);
        getCardListTask.start();
        this.mCurrentTask = getCardListTask;
        this.mIPipeEventCallback = iPipeEventCallback;
        LogMgr.log(4, "999");
    }

    public synchronized void getCardListV3(JSONArray jSONArray, String str) throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        GetCardListTask getCardListTask = new GetCardListTask(262144, this.mExecutor, this, this.mParams.loginTokenId, jSONArray, this.mFwsClient, this.mChipHolder, this.mDataManager, GetCardListTask.Options.createCardListOptions(str), this.mJwsCreator);
        getCardListTask.start();
        this.mCurrentTask = getCardListTask;
        LogMgr.log(4, "999");
    }

    public synchronized void getCardAdditionalInfoList(JSONArray jSONArray) throws MfiClientException {
        LogMgr.log(4, "%s", "000");
        checkStarted();
        checkNotRunningTask();
        GetCardListTask getCardListTask = new GetCardListTask(8, this.mExecutor, this, this.mParams.loginTokenId, jSONArray, this.mFwsClient, this.mChipHolder, this.mDataManager, GetCardListTask.Options.createCardAdditionalInfoListOptions(null), this.mJwsCreator);
        getCardListTask.start();
        this.mCurrentTask = getCardListTask;
        LogMgr.log(4, "%s", "999");
    }

    public synchronized void getCardAdditionalInfoListWithPipe(String[] strArr, JSONArray jSONArray, IPipeEventCallback iPipeEventCallback) throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        GetCardListTask getCardListTask = new GetCardListTask(512, this.mExecutor, this, this.mParams.loginTokenId, jSONArray, this.mFwsClient, this.mChipHolder, this.mDataManager, GetCardListTask.Options.createCardAdditionalInfoListOptions(strArr), this.mJwsCreator);
        getCardListTask.start();
        this.mCurrentTask = getCardListTask;
        this.mIPipeEventCallback = iPipeEventCallback;
        LogMgr.log(4, "999");
    }

    public synchronized void getCardAdditionalInfoListV3(String[] strArr, JSONArray jSONArray) throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        GetCardListTask getCardListTask = new GetCardListTask(524288, this.mExecutor, this, this.mParams.loginTokenId, jSONArray, this.mFwsClient, this.mChipHolder, this.mDataManager, GetCardListTask.Options.createCardAdditionalInfoListOptions(strArr), this.mJwsCreator);
        getCardListTask.start();
        this.mCurrentTask = getCardListTask;
        LogMgr.log(4, "999");
    }

    public synchronized void issueCard(String str, String str2) throws MfiClientException {
        LogMgr.log(4, "%s", "000");
        checkStarted();
        checkNotRunningTask();
        IssueCardTask issueCardTask = new IssueCardTask(16, this.mExecutor, this, this.mParams.loginTokenId, str, str2, this.mFwsClient, this.mChipHolder, this.mDataManager, this.mJwsCreator);
        issueCardTask.start();
        this.mCurrentTask = issueCardTask;
        LogMgr.log(4, "%s", "999");
    }

    public synchronized void deleteCard(String str, String str2) throws MfiClientException {
        LogMgr.log(4, "%s", "000");
        checkStarted();
        checkNotRunningTask();
        CompleteCardInfo.Cache cardInfo = getCardInfo(str);
        CardIdentifiableInfo.Cache cardIdInfo = getCardIdInfo(cardInfo.serviceId);
        if (cardInfo.spType == CompleteCardInfo.SpType.INDIVIDUAL_SP) {
            LogMgr.log(1, "800 : Individual card is unavailable .");
            throw new MfiClientException(102, MfiClientException.TYPE_NOT_SUPPORTED, null);
        }
        ServiceTypeInfoUtil.deleteCardsSimlpleServiceTypeCheck(cardInfo.serviceType);
        checkSeid(cardInfo);
        DeleteCardTask deleteCardTask = new DeleteCardTask(32, this.mExecutor, (AsyncTaskBase.Listener) this, this.mParams.loginTokenId, str, cardIdInfo, str2, this.mFwsClient, this.mChipHolder, this.mDataManager, cardInfo.serviceType, true);
        deleteCardTask.start();
        this.mCurrentTask = deleteCardTask;
        LogMgr.log(4, "%s", "999");
    }

    public synchronized void saveDeleteCard(String str, String str2) throws MfiClientException {
        LogMgr.log(4, "%s", "000");
        checkStarted();
        checkNotRunningTask();
        CompleteCardInfo.Cache cardInfo = getCardInfo(str);
        CardIdentifiableInfo.Cache cardIdInfo = getCardIdInfo(cardInfo.serviceId);
        if (cardInfo.spType == CompleteCardInfo.SpType.INDIVIDUAL_SP) {
            LogMgr.log(1, "800 : Individual card is unavailable .");
            throw new MfiClientException(102, MfiClientException.TYPE_NOT_SUPPORTED, null);
        }
        ServiceTypeInfoUtil.deleteCardsServiceTypeCheck(cardInfo.serviceType);
        checkSeid(cardInfo);
        DeleteCardTask deleteCardTask = new DeleteCardTask(131072, this.mExecutor, this, this.mParams.loginTokenId, str, cardIdInfo, str2, this.mFwsClient, this.mChipHolder, this.mDataManager, cardInfo.serviceType, true, StringUtil.hexToByteArray(cardInfo.appletInstanceAid));
        deleteCardTask.start();
        this.mCurrentTask = deleteCardTask;
        LogMgr.log(4, "%s", "999");
    }

    public synchronized void enableCard(CardInfo cardInfo) throws MfiClientException {
        LogMgr.log(4, "%s", "000");
        checkStarted();
        checkNotRunningTask();
        CompleteCardInfo.Cache cardInfo2 = getCardInfo(cardInfo.getCid());
        CardIdentifiableInfo.Cache cardIdInfo = getCardIdInfo(cardInfo2.serviceId);
        if (cardInfo2.spType == CompleteCardInfo.SpType.INDIVIDUAL_SP && !Property.isChipGP()) {
            LogMgr.log(1, "800 : Individual card is unavailable .");
            throw new MfiClientException(102, MfiClientException.TYPE_NOT_SUPPORTED, null);
        }
        ServiceTypeInfoUtil.enablesServiceTypeCheck(cardInfo2.serviceType);
        checkSeid(cardInfo2);
        EnableCardTask enableCardTask = new EnableCardTask(64, this.mExecutor, this, this.mParams.loginTokenId, cardInfo, cardInfo2, cardIdInfo, this.mFwsClient, this.mChipHolder, this.mDataManager);
        enableCardTask.start();
        this.mCurrentTask = enableCardTask;
        LogMgr.log(4, "%s", "999");
    }

    public synchronized void disableCard(String str) throws MfiClientException {
        LogMgr.log(4, "%s", "000");
        checkStarted();
        checkNotRunningTask();
        CompleteCardInfo.Cache cardInfo = getCardInfo(str);
        CardIdentifiableInfo.Cache cardIdInfo = getCardIdInfo(cardInfo.serviceId);
        if (cardInfo.spType == CompleteCardInfo.SpType.INDIVIDUAL_SP) {
            LogMgr.log(1, "800 : Individual card is unavailable .");
            throw new MfiClientException(102, MfiClientException.TYPE_NOT_SUPPORTED, null);
        }
        ServiceTypeInfoUtil.disablesServiceTypeCheck(cardInfo.serviceType);
        checkSeid(cardInfo);
        DisableCardTask disableCardTask = new DisableCardTask(128, this.mExecutor, this, this.mParams.loginTokenId, str, cardIdInfo, this.mFwsClient, this.mChipHolder, this.mDataManager, true);
        disableCardTask.start();
        this.mCurrentTask = disableCardTask;
        LogMgr.log(4, "%s", "999");
    }

    public synchronized void accessCard(String str, String str2) throws MfiClientException {
        LogMgr.log(4, "%s", "000");
        checkStarted();
        checkNotRunningTask();
        CompleteCardInfo.Cache cardInfo = getCardInfo(str);
        CardIdentifiableInfo.Cache cardIdInfo = getCardIdInfo(cardInfo.serviceId);
        if (cardInfo.spType == CompleteCardInfo.SpType.INDIVIDUAL_SP) {
            LogMgr.log(1, "800 : Individual card is unavailable .");
            throw new MfiClientException(102, MfiClientException.TYPE_NOT_SUPPORTED, null);
        }
        ServiceTypeInfoUtil.accessCardsServiceTypeCheck(cardInfo.serviceType);
        checkSeid(cardInfo);
        SEAccessTask sEAccessTask = new SEAccessTask(32768, this.mExecutor, this, this.mParams.loginTokenId, cardIdInfo, str2, this.mFwsClient, this.mChipHolder, this.mDataManager, cardInfo.serviceType);
        sEAccessTask.start();
        this.mCurrentTask = sEAccessTask;
        LogMgr.log(4, "%s", "999");
    }

    public synchronized void cancelCardOperation() {
        LogMgr.log(4, "%s", "000");
        if (this.mCurrentTask != null) {
            int taskId = this.mCurrentTask.getTaskId();
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

    public synchronized void getLinkageDataList(int i, String[] strArr) throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        GetLinkageDataListTask getLinkageDataListTask = new GetLinkageDataListTask(4096, this.mExecutor, this, i, strArr, this.mDataManager, this.mJwsCreator, this.mFwsClient, this.mChipHolder);
        getLinkageDataListTask.start();
        this.mCurrentTask = getLinkageDataListTask;
        LogMgr.log(4, "999");
    }

    public synchronized void getCardInfoListWithSpStatus(String str, JSONArray jSONArray, String str2, IPipeEventCallback iPipeEventCallback) throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        GetCardListTask getCardListTask = new GetCardListTask(65536, this.mExecutor, this, this.mParams.loginTokenId, jSONArray, this.mFwsClient, this.mChipHolder, this.mDataManager, GetCardListTask.Options.createCardInfoListWithSpStatusOptions(str2, str), this.mJwsCreator);
        getCardListTask.start();
        this.mCurrentTask = getCardListTask;
        this.mIPipeEventCallback = iPipeEventCallback;
        LogMgr.log(4, "999");
    }

    public synchronized void getCardInfoListWithSpStatusV3(String str, JSONArray jSONArray, String str2) throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        GetCardListTask getCardListTask = new GetCardListTask(1048576, this.mExecutor, this, this.mParams.loginTokenId, jSONArray, this.mFwsClient, this.mChipHolder, this.mDataManager, GetCardListTask.Options.createCardInfoListWithSpStatusOptions(str2, str), this.mJwsCreator);
        getCardListTask.start();
        this.mCurrentTask = getCardListTask;
        LogMgr.log(4, "999");
    }

    public synchronized void executeServerOperation(String str, String str2) throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        ExecuteServerOperationTask executeServerOperationTask = new ExecuteServerOperationTask(2097152, this.mExecutor, this, this.mFwsClient, this.mChipHolder, this.mDataManager, str, str2);
        executeServerOperationTask.start();
        this.mCurrentTask = executeServerOperationTask;
        LogMgr.log(4, "999");
    }

    public synchronized void provisionServerOperation() throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        UpdateDeviceRegistrationTokenTask updateDeviceRegistrationTokenTask = new UpdateDeviceRegistrationTokenTask(TASK_ID_PROVISION_SERVER_OPERATION, this.mExecutor, this, this.mFwsClient, this.mChipHolder, this.mDataManager);
        updateDeviceRegistrationTokenTask.start();
        this.mCurrentTask = updateDeviceRegistrationTokenTask;
        LogMgr.log(4, "999");
    }

    public synchronized void checkExistsManagementCard(String str, boolean z) throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        CheckLegacyCardExistenceTask checkLegacyCardExistenceTask = new CheckLegacyCardExistenceTask(TASK_ID_LEGACY_CARD_EXISTANCE_TASK, this.mExecutor, this, this.mFwsClient, this.mChipHolder, this.mDataManager, str, z);
        checkLegacyCardExistenceTask.start();
        this.mCurrentTask = checkLegacyCardExistenceTask;
        LogMgr.log(4, "999");
    }

    public synchronized void deleteLegacyCard(String str) throws MfiClientException {
        LogMgr.log(4, "000");
        checkStarted();
        checkNotRunningTask();
        DeleteLegacyCardTask deleteLegacyCardTask = new DeleteLegacyCardTask(TASK_ID_DELETE_UNSUPPORT_MFI_SERVICE_1_CARD, this.mExecutor, this, str, this.mFwsClient, this.mChipHolder, this.mDataManager);
        deleteLegacyCardTask.start();
        this.mCurrentTask = deleteLegacyCardTask;
        LogMgr.log(4, "999");
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
        if (this.mCurrentTask != null) {
            if (Arrays.asList(TASK_CAN_NOT_FINISH_WITH_STOP).contains(Integer.valueOf(this.mCurrentTask.getTaskId()))) {
                LogMgr.log(1, "%s : Aleady running task.", "801");
                throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_CURRENTLY_ONLINE, null);
            }
        }
    }

    public synchronized boolean isNotRunningSilentStartTask() {
        if (this.mCurrentTask != null) {
            if (Arrays.asList(TASK_SILENT_START).contains(Integer.valueOf(this.mCurrentTask.getTaskId()))) {
                return false;
            }
        }
        return true;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase.Listener
    public void onFinishTask(TaskBase taskBase, boolean z, int i, String str) {
        LogMgr.log(6, "%s", "000");
        synchronized (this) {
            OnFinishTaskParam onFinishTaskParamOnFinishTaskInner = onFinishTaskInner(taskBase, z, i, str);
            if (onFinishTaskParamOnFinishTaskInner == null) {
                LogMgr.log(2, "%s OnFinishTaskParam is null.", "700");
                return;
            }
            OnFinishListener onFinishListener = this.mListener;
            int i2 = onFinishTaskParamOnFinishTaskInner.taskId;
            if (i2 == 1) {
                onFinishListener.onLoginFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
            } else if (i2 == 2) {
                onFinishListener.onLogoutFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, ((Boolean) onFinishTaskParamOnFinishTaskInner.args[0]).booleanValue(), onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
            } else if (i2 == 68) {
                onFinishListener.onGetCachedCardListFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, (String[]) onFinishTaskParamOnFinishTaskInner.args[0], onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
            } else if (i2 != 69) {
                switch (i2) {
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
                    case 128:
                        onFinishListener.onDisableCardFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, (CardInfo) onFinishTaskParamOnFinishTaskInner.args[0], onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
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
                    default:
                        LogMgr.log(1, "%s : Unknown task.", "801");
                        break;
                }
            } else {
                onFinishListener.onEnableCachedCardFinished(onFinishTaskParamOnFinishTaskInner.isSuccess, (CachedCardInfo) onFinishTaskParamOnFinishTaskInner.args[0], (CachedCardInfo) onFinishTaskParamOnFinishTaskInner.args[1], onFinishTaskParamOnFinishTaskInner.errType, onFinishTaskParamOnFinishTaskInner.errMsg);
            }
            LogMgr.log(6, "%s", "999");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x02f2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private synchronized com.felicanetworks.mfc.mfi.fws.FwsClientFacade.OnFinishTaskParam onFinishTaskInner(com.felicanetworks.mfc.mfi.fws.TaskBase r18, boolean r19, int r20, java.lang.String r21) {
        /*
            Method dump skipped, instruction units count: 1890
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.fws.FwsClientFacade.onFinishTaskInner(com.felicanetworks.mfc.mfi.fws.TaskBase, boolean, int, java.lang.String):com.felicanetworks.mfc.mfi.fws.FwsClientFacade$OnFinishTaskParam");
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

    private synchronized void setCardList(Map<String, CompleteCardInfo.Cache> map, Map<String, CardIdentifiableInfo.Cache> map2) {
        LogMgr.log(6, "%s Set Params.cardInfoMap :%s", "001", map);
        LogMgr.log(6, "%s Set Params.cardIdInfoMap :%s", "002", map2);
        this.mParams.cardInfoMap = map;
        this.mParams.cardIdInfoMap = map2;
    }

    private synchronized void putCardInfoToMap(CompleteCardInfo completeCardInfo) {
        if (this.mParams.cardInfoMap == null) {
            LogMgr.log(6, "%s Create Params.cardInfoMap", "001");
            this.mParams.cardInfoMap = new HashMap();
        }
        this.mParams.cardInfoMap.put(completeCardInfo.getCid(), completeCardInfo.getCacheableData());
    }

    private synchronized void removeCardInfoToMap(String str) {
        if (this.mParams.cardInfoMap != null) {
            this.mParams.cardInfoMap.remove(str);
        }
    }

    private synchronized CompleteCardInfo.Cache getCardInfo(String str) throws MfiClientException {
        if (this.mParams.cardInfoMap != null) {
            if (this.mParams.cardInfoMap.containsKey(str)) {
            } else {
                LogMgr.log(1, "%s : CID is null.", "801");
                throw new MfiClientException(102, 158, null);
            }
        } else {
            LogMgr.log(1, "%s : cardInfoMap is null.", "800");
            throw new MfiClientException(102, 157, null);
        }
        return (CompleteCardInfo.Cache) this.mParams.cardInfoMap.get(str);
    }

    private synchronized void putCardIdInfoToMap(CardIdentifiableInfo cardIdentifiableInfo) {
        if (this.mParams.cardIdInfoMap == null) {
            LogMgr.log(6, "%s Create Params.cardIdInfoMap", "001");
            this.mParams.cardIdInfoMap = new HashMap();
        }
        this.mParams.cardIdInfoMap.put(cardIdentifiableInfo.serviceId, cardIdentifiableInfo.getCacheableData());
    }

    private synchronized CardIdentifiableInfo.Cache getCardIdInfo(String str) throws MfiClientException {
        if (this.mParams.cardIdInfoMap != null) {
            if (this.mParams.cardIdInfoMap.containsKey(str)) {
            } else {
                LogMgr.log(1, "%s : Service is null.", "801");
                throw new MfiClientException(102, 158, null);
            }
        } else {
            LogMgr.log(1, "%s : cardIdInfoMap is null.", "800");
            throw new MfiClientException(102, 157, null);
        }
        return (CardIdentifiableInfo.Cache) this.mParams.cardIdInfoMap.get(str);
    }

    private void checkSeid(CompleteCardInfo.Cache cache) throws MfiClientException {
        try {
            if (cache.seId.equalsIgnoreCase(this.mDataManager.getSeInfo().getSeId())) {
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
