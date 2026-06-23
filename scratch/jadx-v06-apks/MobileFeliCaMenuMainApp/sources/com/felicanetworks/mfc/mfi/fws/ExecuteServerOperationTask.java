package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.Felica;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.MfiControlInfoCache;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.MfiFelicaWrapper;
import com.felicanetworks.mfc.mfi.fws.AccessFwsTask;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.FwsConst;
import com.felicanetworks.mfc.mfi.fws.json.CardIdentifiableInfoJson;
import com.felicanetworks.mfc.mfi.fws.json.CardJson;
import com.felicanetworks.mfc.mfi.fws.json.GetMfiControlInfoResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.RequestJson;
import com.felicanetworks.mfc.mfi.fws.json.RequestPushedOperationRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.RequestPushedOperationResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.StartOperationRequestTokenPayloadJson;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class ExecuteServerOperationTask extends AsyncParentTaskBase<Boolean> {
    private static final List<String> VALID_RESULT_CODE_LIST_REQUEST_PUSHED_OPERATION;
    private final MfiChipHolder mChipHolder;
    private final DataManager mDataManager;
    private final FwsClient mFwsClient;
    private Boolean mIsRequiredRetrying;
    private final String mMessageId;
    private final String mOperationId;

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncParentTaskBase, com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    public /* bridge */ /* synthetic */ void stop() {
        super.stop();
    }

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_REQUEST_PUSHED_OPERATION = arrayList;
        arrayList.add("0000");
        VALID_RESULT_CODE_LIST_REQUEST_PUSHED_OPERATION.add(FwsConst.RESULT_CODE_RETRYREQUEST);
        VALID_RESULT_CODE_LIST_REQUEST_PUSHED_OPERATION.add(FwsConst.RESULT_ILLEGAL_ARGUMENTS);
        VALID_RESULT_CODE_LIST_REQUEST_PUSHED_OPERATION.add(FwsConst.RESULT_ILLEGAL_URL);
        VALID_RESULT_CODE_LIST_REQUEST_PUSHED_OPERATION.add(FwsConst.RESULT_ILLEGAL_REQUEST_TOKEN);
        VALID_RESULT_CODE_LIST_REQUEST_PUSHED_OPERATION.add(FwsConst.RESULT_EXPIRED_LINKAGE_DATA);
        VALID_RESULT_CODE_LIST_REQUEST_PUSHED_OPERATION.add(FwsConst.RESULT_INVALID_REQUEST_TOKEN);
        VALID_RESULT_CODE_LIST_REQUEST_PUSHED_OPERATION.add(FwsConst.RESULT_INVALID_MESSAGE_ID);
        VALID_RESULT_CODE_LIST_REQUEST_PUSHED_OPERATION.add("4000");
        VALID_RESULT_CODE_LIST_REQUEST_PUSHED_OPERATION.add("9001");
    }

    ExecuteServerOperationTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, FwsClient fwsClient, MfiChipHolder mfiChipHolder, DataManager dataManager, String str, String str2) {
        super(i, executorService, listener);
        this.mIsRequiredRetrying = false;
        this.mFwsClient = fwsClient;
        this.mChipHolder = mfiChipHolder;
        this.mDataManager = dataManager;
        this.mOperationId = str;
        this.mMessageId = str2;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() {
        LogMgr.log(6, "000");
        if (createSeInfo()) {
            if (isStopped()) {
                LogMgr.log(2, "700 Already has stopped.");
                onFinished(false, 215, null);
                return;
            }
            a aVarPrepareJwsCreator = prepareJwsCreator();
            if (aVarPrepareJwsCreator == null) {
                return;
            }
            if (isStopped()) {
                LogMgr.log(2, "702 Already has stopped.");
                clearJwsCreator(aVarPrepareJwsCreator);
                onFinished(false, 215, null);
                return;
            }
            try {
                String strCreateStartOperationRequestToken = createStartOperationRequestToken(aVarPrepareJwsCreator, this.mMessageId);
                if (isStopped()) {
                    LogMgr.log(2, "704 Already has stopped.");
                    clearJwsCreator(aVarPrepareJwsCreator);
                    onFinished(false, 215, null);
                    return;
                }
                AccessFwsTask.Result resultStartGetMfiControlInfo = startGetMfiControlInfo(aVarPrepareJwsCreator);
                clearJwsCreator(aVarPrepareJwsCreator);
                if (resultStartGetMfiControlInfo != null && !resultStartGetMfiControlInfo.isSuccess) {
                    LogMgr.log(2, "705 GetMfiControlInfo failed.");
                    onFinished(false, resultStartGetMfiControlInfo.errType, resultStartGetMfiControlInfo.errMsg);
                    return;
                }
                if (isStopped()) {
                    LogMgr.log(2, "706 Already has stopped.");
                    onFinished(false, 215, null);
                    return;
                }
                FwsRequestPushedOperationSubTask fwsRequestPushedOperationSubTask = new FwsRequestPushedOperationSubTask(0, this.mFwsClient, this.mOperationId, strCreateStartOperationRequestToken);
                setStoppableSubTask(fwsRequestPushedOperationSubTask);
                fwsRequestPushedOperationSubTask.start();
                AccessFwsTask.Result result = fwsRequestPushedOperationSubTask.getResult2();
                if (!result.isSuccess) {
                    onFinished(false, result.errType, result.errMsg);
                } else {
                    try {
                        String strOptLinkageData = ((RequestPushedOperationResponseJson) result.response).optLinkageData();
                        String actionType = ((RequestPushedOperationResponseJson) result.response).getActionType();
                        CardJson accessedCard = ((RequestPushedOperationResponseJson) result.response).getAccessedCard();
                        CardIdentifiableInfoJson cardIdentifiableInfo = ((RequestPushedOperationResponseJson) result.response).getCardIdentifiableInfo();
                        byte b = -1;
                        int iHashCode = actionType.hashCode();
                        if (iHashCode != 530037771) {
                            if (iHashCode == 1018567725 && actionType.equals(FwsConst.ActionType.SE_ACCESS_FOR_PUSH)) {
                                b = 1;
                            }
                        } else if (actionType.equals(FwsConst.ActionType.DELETE_CARDS_FOR_PUSH)) {
                            b = 0;
                        }
                        if (b == 0) {
                            startDeleteCards(strOptLinkageData, accessedCard, cardIdentifiableInfo);
                        } else if (b == 1) {
                            startSeAccess(strOptLinkageData, accessedCard, cardIdentifiableInfo);
                        } else {
                            LogMgr.log(1, "800 ActionType is invalid. :" + actionType);
                            onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                        }
                    } catch (JSONException e) {
                        LogMgr.log(2, "705 JSONException:" + e.getMessage());
                        LogMgr.printStackTrace(7, e);
                        onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
                        return;
                    }
                }
                LogMgr.log(6, "999");
            } catch (Exception e2) {
                LogMgr.log(2, "703 : Exception.");
                LogMgr.printStackTrace(2, e2);
                clearJwsCreator(aVarPrepareJwsCreator);
                onFinished(false, 200, "Unknown error.");
            }
        }
    }

    private a prepareJwsCreator() {
        a aVar = new a();
        boolean z = false;
        try {
            if (Property.isChipGP()) {
                GpController gpController = this.mChipHolder.getGpController();
                LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "a", "a");
                aVar.a(gpController);
                LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "a", "a");
            } else {
                Felica felica = this.mChipHolder.getFelica();
                LogMgr.performanceIn(LogMgr.PERFORMANCE_MFC, "a", "a");
                aVar.a(felica);
                LogMgr.performanceOut(LogMgr.PERFORMANCE_MFC, "a", "a");
            }
            z = true;
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(2, e);
            onFinished(false, 200, "Unknown error.");
        }
        if (z) {
            return aVar;
        }
        return null;
    }

    private void clearJwsCreator(a aVar) {
        try {
            aVar.a();
        } catch (Exception e) {
            LogMgr.log(2, "700 : Exception");
            LogMgr.printStackTrace(2, e);
        }
    }

    private boolean createSeInfo() {
        LogMgr.log(6, "000");
        MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(this.mChipHolder);
        boolean z = false;
        try {
            try {
                this.mDataManager.createSeInfo(mfiFelicaWrapper);
                mfiFelicaWrapper.close();
                z = true;
            } catch (MfiFelicaException e) {
                LogMgr.log(2, "700 MfiFelicaException");
                LogMgr.printStackTrace(7, e);
                onFinished(false, e.getType(), e.getMessage());
            } catch (GpException e2) {
                LogMgr.log(2, "701 GpException");
                onFinished(false, e2.getType(), e2.getMessage());
            } catch (Exception e3) {
                LogMgr.log(2, "702 " + e3.getClass().getSimpleName() + ":" + e3.getMessage());
                LogMgr.printStackTrace(7, e3);
                onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e3));
            }
            LogMgr.log(6, "999");
            return z;
        } finally {
            mfiFelicaWrapper.closeSilently();
        }
    }

    private String createStartOperationRequestToken(a aVar, String str) throws JSONException {
        LogMgr.log(6, "000");
        StartOperationRequestTokenPayloadJson startOperationRequestTokenPayloadJson = new StartOperationRequestTokenPayloadJson();
        startOperationRequestTokenPayloadJson.setSeInfo(this.mDataManager.getSeInfo());
        startOperationRequestTokenPayloadJson.setMessageId(str);
        LogMgr.log(6, "001");
        String strA = aVar.a(startOperationRequestTokenPayloadJson);
        LogMgr.log(6, "999");
        return strA;
    }

    private void startSeAccess(String str, CardJson cardJson, CardIdentifiableInfoJson cardIdentifiableInfoJson) {
        try {
            SEAccessTask sEAccessTask = new SEAccessTask(0, this.mExecutor, new ServerOperationTaskListener(), null, cardIdentifiableInfoJson.getCardIdentifiableInfo().getCacheableData(), str, this.mFwsClient, this.mChipHolder, this.mDataManager, cardJson.getServiceType(), this.mOperationId);
            setStoppableSubTask(sEAccessTask);
            sEAccessTask.start();
        } catch (JSONException e) {
            LogMgr.log(2, "700 JSONException:" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
        }
    }

    private void startDeleteCards(String str, CardJson cardJson, CardIdentifiableInfoJson cardIdentifiableInfoJson) {
        try {
            DeleteCardTask deleteCardTask = new DeleteCardTask(0, this.mExecutor, new ServerOperationTaskListener(), (String) null, cardJson.getCid(), cardIdentifiableInfoJson.getCardIdentifiableInfo().getCacheableData(), str, this.mFwsClient, this.mChipHolder, this.mDataManager, cardJson.getServiceType(), this.mOperationId);
            setStoppableSubTask(deleteCardTask);
            deleteCardTask.start();
        } catch (JSONException e) {
            LogMgr.log(2, "700 JSONException:" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
        }
    }

    private AccessFwsTask.Result startGetMfiControlInfo(a aVar) {
        MfiControlInfoCache mfiControlInfoCache = MfiControlInfoCache.getInstance();
        String datePattern = mfiControlInfoCache.getDatePattern();
        String timeZone = mfiControlInfoCache.getTimeZone();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern, Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        String str = simpleDateFormat.format(new Date());
        if (!mfiControlInfoCache.needUpdateContent(str)) {
            return null;
        }
        GetMfiControlInfoTaskImplRetry getMfiControlInfoTaskImplRetry = new GetMfiControlInfoTaskImplRetry(0, this.mFwsClient, FlavorConst.CONTENT_ID, aVar, this.mChipHolder, this.mDataManager);
        setStoppableSubTask(getMfiControlInfoTaskImplRetry);
        getMfiControlInfoTaskImplRetry.start();
        AccessFwsTask.Result result = getMfiControlInfoTaskImplRetry.getResult2();
        if (!result.isSuccess) {
            return result;
        }
        MfiControlInfoCache.getInstance().cacheMfiControlInfo(((GetMfiControlInfoResponseJson) result.response).toString(), str);
        return result;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public void setResult(Boolean bool) {
        this.mIsRequiredRetrying = bool;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public Boolean getResult2() {
        return this.mIsRequiredRetrying;
    }

    private class GetMfiControlInfoTaskImplRetry extends GetMfiControlInfoTask {
        private int mRetryHttpAccessCount;

        GetMfiControlInfoTaskImplRetry(int i, FwsClient fwsClient, String str, a aVar, MfiChipHolder mfiChipHolder, DataManager dataManager) {
            super(i, fwsClient, str, aVar, mfiChipHolder, dataManager);
            this.mRetryHttpAccessCount = 0;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask, com.felicanetworks.mfc.mfi.fws.StoppableTaskBase, com.felicanetworks.mfc.mfi.fws.TaskBase
        public void start() {
            while (doStart()) {
            }
        }

        private boolean doStart() {
            super.start();
            AccessFwsTask.Result result = getResult2();
            if (result.isSuccess || 205 != result.errType) {
                return false;
            }
            if (this.mRetryHttpAccessCount < FlavorConst.RETRY_HTTP_ACCESS_DEFAULT_DELAY_MILLIS_LIST.length) {
                LogMgr.log(6, "001 Retry(" + (this.mRetryHttpAccessCount + 1) + ")");
                if (!ExecuteServerOperationTask.this.retryDelayedMillis(r0[this.mRetryHttpAccessCount])) {
                    return false;
                }
                this.mRetryHttpAccessCount++;
                return true;
            }
            LogMgr.log(6, "002 Retry limit exceeded.");
            return false;
        }
    }

    private class FwsRequestPushedOperationSubTask extends AccessFwsTask<RequestPushedOperationResponseJson> {
        private static final int FWS_SEQUENCE_NUMBER = 1;
        private boolean mIsRetry;
        private final String mOperationId;
        private int mRetryHttpAccessCount;
        private final String mStartOperationRequestToken;

        FwsRequestPushedOperationSubTask(int i, FwsClient fwsClient, String str, String str2) {
            super(i, fwsClient);
            this.mIsRetry = false;
            this.mRetryHttpAccessCount = 0;
            this.mOperationId = str;
            this.mStartOperationRequestToken = str2;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected RequestJson createRequestJson() throws JSONException {
            RequestPushedOperationRequestJson requestPushedOperationRequestJson = new RequestPushedOperationRequestJson();
            requestPushedOperationRequestJson.setRequestId(createRequestId());
            requestPushedOperationRequestJson.setOperationId(this.mOperationId);
            requestPushedOperationRequestJson.setStartOperationRequestToken(this.mStartOperationRequestToken);
            return requestPushedOperationRequestJson;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String str) throws ProtocolException, HttpException {
            return this.mFwsClient.requestPushedOperation(str, 1);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public RequestPushedOperationResponseJson convertResponse(String str) throws JSONException {
            if (str != null) {
                return new RequestPushedOperationResponseJson(str);
            }
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return ExecuteServerOperationTask.VALID_RESULT_CODE_LIST_REQUEST_PUSHED_OPERATION;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_REQUEST_PUSHED_OPERATION.msg;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask, com.felicanetworks.mfc.mfi.fws.StoppableTaskBase, com.felicanetworks.mfc.mfi.fws.TaskBase
        public void start() {
            while (doStart()) {
            }
        }

        boolean doStart() {
            if (this.mIsRetry) {
                this.mIsRetry = false;
                super.retryStart(this.mRetryHttpAccessCount);
            } else {
                super.start();
            }
            if (isStopped()) {
                LogMgr.log(2, "700 Already has stopped.");
                ExecuteServerOperationTask.this.onFinished(false, 215, null);
                return false;
            }
            AccessFwsTask.Result result = getResult2();
            if (!result.isSuccess && 205 == result.errType) {
                try {
                    int[] retryTimesDelayMillisList = new GetMfiControlInfoResponseJson(MfiControlInfoCache.getInstance().getInfoCache()).getRetryTimesDelayMillisList();
                    if (this.mRetryHttpAccessCount < retryTimesDelayMillisList.length) {
                        LogMgr.log(6, "001 Retry(" + (this.mRetryHttpAccessCount + 1) + ")");
                        boolean zRetryDelayedMillis = ExecuteServerOperationTask.this.retryDelayedMillis((long) retryTimesDelayMillisList[this.mRetryHttpAccessCount]);
                        this.mIsRetry = zRetryDelayedMillis;
                        if (zRetryDelayedMillis) {
                            this.mRetryHttpAccessCount++;
                            return true;
                        }
                    } else {
                        LogMgr.log(6, "002 Retry limit exceeded.");
                    }
                } catch (JSONException unused) {
                    LogMgr.log(1, "801 failed to parse MfiControlInfoCache data.");
                    ExecuteServerOperationTask.this.onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                }
            }
            return false;
        }
    }

    private class ServerOperationTaskListener implements AsyncTaskBase.Listener {
        private ServerOperationTaskListener() {
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase.Listener
        public void onFinishTask(TaskBase taskBase, boolean z, int i, String str) {
            LogMgr.log(4, "000 Is ServerOperation success ? " + z);
            ExecuteServerOperationTask.this.onFinished(z, i, str);
            LogMgr.log(4, "999");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean retryDelayedMillis(long j) {
        LogMgr.log(6, "000");
        LogMgr.log(6, "001 delay[ms]=" + j);
        if (j >= 0) {
            try {
                Thread.sleep(j);
            } catch (InterruptedException unused) {
            }
            LogMgr.log(6, "999");
            return true;
        }
        LogMgr.log(1, "delay time is invalid value.");
        onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
        return false;
    }
}
