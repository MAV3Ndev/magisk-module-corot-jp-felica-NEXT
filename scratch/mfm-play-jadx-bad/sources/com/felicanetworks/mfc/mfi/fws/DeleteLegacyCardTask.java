package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.Felica;
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
import com.felicanetworks.mfc.mfi.UnsupportedMfiService1CardCache;
import com.felicanetworks.mfc.mfi.fws.AccessFwsTask;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.json.GetDeleteScriptRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.GetDeleteScriptResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.GetMfiControlInfoResponseJson;
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
import com.felicanetworks.mfc.mfi.omapi.SsdAppletInfo;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class DeleteLegacyCardTask extends AsyncParentTaskBase<String> {
    private static final List<String> VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT;
    private EventBroadcastSender.CardDeleteEvent mCardDeleteEvent;
    private final MfiChipHolder mChipHolder;
    private String mCid;
    private final DataManager mDataManager;
    private final FwsClient mFwsClient;
    private final String mLinkageData;
    private String mSequenceCounter;
    private String mServiceId;

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public void setResult(String cid) {
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncParentTaskBase, com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    public /* bridge */ /* synthetic */ void stop() {
        super.stop();
    }

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT = arrayList;
        arrayList.add("0000");
        arrayList.add(FwsConst.RESULT_CODE_CONTINUE);
        arrayList.add("1000");
        arrayList.add("2000");
        arrayList.add(FwsConst.RESULT_ILLEGAL_URL);
        arrayList.add(FwsConst.RESULT_ILLEGAL_ACCESS_TOKEN);
        arrayList.add("3001");
        arrayList.add(FwsConst.RESULT_ILLEGAL_LINKAGE_DATA);
        arrayList.add("3000");
        arrayList.add(FwsConst.RESULT_EXPIRED_LINKAGE_DATA);
        arrayList.add("4000");
        arrayList.add("4001");
        arrayList.add(FwsConst.RESULT_NOT_ACTIVE_CARD);
        arrayList.add(FwsConst.RESULT_NOT_EXIST_CARD);
        arrayList.add("9000");
        arrayList.add("9001");
        arrayList.add("9005");
    }

    DeleteLegacyCardTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, String linkageData, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager) {
        super(taskId, executor, listener);
        this.mLinkageData = linkageData;
        this.mFwsClient = fwsClient;
        this.mChipHolder = chipHolder;
        this.mDataManager = dataManager;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() throws Throwable {
        LogMgr.log(6, "000");
        try {
            try {
                LinkageDataJson linkageDataJson = new LinkageDataJson(new JwsObject(this.mLinkageData).getPayload());
                this.mCid = linkageDataJson.getCidWithNoCheck() == null ? MfiClientConst.NO_CID_INSTANCE_KEY : linkageDataJson.getCidWithNoCheck();
                this.mServiceId = linkageDataJson.getServiceId();
                this.mCardDeleteEvent = new EventBroadcastSender.CardDeleteEvent(this.mServiceId);
                EventBroadcastSender.sendStartEventBroadcast(FelicaAdapter.getInstance(), this.mCardDeleteEvent);
                if (isStopped()) {
                    LogMgr.log(2, "700 Already has stopped.");
                    onFinished(false, 215, null);
                    return;
                }
                if (createSeInfo()) {
                    if (isStopped()) {
                        LogMgr.log(2, "701 Already has stopped.");
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
                    AccessFwsTask.Result resultStartGetMfiControlInfo = startGetMfiControlInfo(aVarPrepareJwsCreator);
                    clearJwsCreator(aVarPrepareJwsCreator);
                    if (resultStartGetMfiControlInfo != null && !resultStartGetMfiControlInfo.isSuccess) {
                        LogMgr.log(2, "703 GetMfiControlInfo failed.");
                        onFinished(false, resultStartGetMfiControlInfo.errType, resultStartGetMfiControlInfo.errMsg);
                        return;
                    }
                    if (isStopped()) {
                        LogMgr.log(2, "704 Already has stopped.");
                        onFinished(false, 215, null);
                        return;
                    }
                    if (Property.isChipGP()) {
                        LogMgr.log(6, "001");
                        deactivate(this.mCid);
                    }
                    if (Property.isChipGP() && !this.mCid.equals(MfiClientConst.NO_CID_INSTANCE_KEY)) {
                        this.mSequenceCounter = getSequenceCounter();
                    }
                    FwsDeleteLegacySubTask fwsDeleteLegacySubTask = new FwsDeleteLegacySubTask(0, this.mFwsClient, this.mChipHolder);
                    setStoppableSubTask(fwsDeleteLegacySubTask);
                    fwsDeleteLegacySubTask.start();
                    LogMgr.log(6, "999");
                }
            } catch (JSONException e) {
                LogMgr.log(2, "705 : FelicaException:" + e.getMessage());
                throw new MfiClientException(102, MfiClientException.TYPE_ILLEGAL_LINKAGE_DATA, null);
            }
        } catch (MfiClientException e2) {
            LogMgr.log(2, "803 MfiClientException");
            onFinished(false, e2.getType(), e2.getMessage());
        } catch (GpException e3) {
            LogMgr.log(1, "801 : GpException");
            onFinished(false, e3.getType(), e3.getMessage());
        } catch (InterruptedException unused) {
            LogMgr.log(1, "800 : InterruptedException");
            onFinished(false, 215, null);
        } catch (Exception unused2) {
            LogMgr.log(1, "802 : Exception");
            onFinished(false, 200, "Unknown error.");
        }
    }

    private a prepareJwsCreator() {
        a aVar = new a();
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
            return aVar;
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(2, e);
            onFinished(false, 200, "Unknown error.");
            return null;
        }
    }

    private void clearJwsCreator(a jwsCreator) {
        try {
            jwsCreator.a();
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
                try {
                    try {
                        mfiFelicaWrapper.open();
                    } catch (GpException e) {
                        LogMgr.log(2, "701 GpException");
                        onFinished(false, e.getType(), e.getMessage());
                        LogMgr.log(6, "999");
                        return z;
                    }
                } catch (MfiFelicaException e2) {
                    LogMgr.log(2, "700 MfiFelicaException");
                    LogMgr.printStackTrace(7, e2);
                    onFinished(false, e2.getType(), e2.getMessage());
                    LogMgr.log(6, "999");
                    return z;
                }
            } catch (Exception e3) {
                LogMgr.log(2, "702 " + e3.getClass().getSimpleName() + ":" + e3.getMessage());
                LogMgr.printStackTrace(7, e3);
                onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e3));
                LogMgr.log(6, "999");
                return z;
            }
            if (this.mDataManager.getInitStatus(mfiFelicaWrapper) != 1) {
                throw new MfiFelicaException(31, null);
            }
            this.mDataManager.createSeInfo(mfiFelicaWrapper);
            mfiFelicaWrapper.closeSilently();
            z = true;
            LogMgr.log(6, "999");
            return z;
        } finally {
            mfiFelicaWrapper.closeSilently();
        }
    }

    private AccessFwsTask.Result startGetMfiControlInfo(a jwsCreator) {
        MfiControlInfoCache mfiControlInfoCache = MfiControlInfoCache.getInstance();
        String datePattern = mfiControlInfoCache.getDatePattern();
        String timeZone = mfiControlInfoCache.getTimeZone();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern, Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        String str = simpleDateFormat.format(new Date());
        if (!mfiControlInfoCache.needUpdateContent(str)) {
            return null;
        }
        GetMfiControlInfoTaskImplRetry getMfiControlInfoTaskImplRetry = new GetMfiControlInfoTaskImplRetry(0, this.mFwsClient, FlavorConst.CONTENT_ID, jwsCreator, this.mChipHolder, this.mDataManager);
        setStoppableSubTask(getMfiControlInfoTaskImplRetry);
        getMfiControlInfoTaskImplRetry.start();
        AccessFwsTask.Result result = getMfiControlInfoTaskImplRetry.getResult2();
        if (result.isSuccess) {
            MfiControlInfoCache.getInstance().cacheMfiControlInfo(((GetMfiControlInfoResponseJson) result.response).toString(), str);
        }
        return result;
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

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public synchronized String getResult2() {
        return this.mCid;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void onFinished(boolean isSuccess, int errType, String errMsg) {
        LogMgr.log(6, "000");
        EventBroadcastSender.sendFinishEventBroadcast(FelicaAdapter.getInstance(), this.mCardDeleteEvent, isSuccess);
        super.onFinished(isSuccess, errType, errMsg);
        LogMgr.log(6, "999");
    }

    private class GetMfiControlInfoTaskImplRetry extends GetMfiControlInfoTask {
        private int mRetryHttpAccessCount;

        GetMfiControlInfoTaskImplRetry(int taskId, FwsClient fwsClient, String contentId, a jwsCreator, MfiChipHolder mfiChipHolder, DataManager dataManager) {
            super(taskId, fwsClient, contentId, jwsCreator, mfiChipHolder, dataManager);
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
                if (!DeleteLegacyCardTask.this.retryDelayedMillis(r0[this.mRetryHttpAccessCount])) {
                    return false;
                }
                this.mRetryHttpAccessCount++;
                return true;
            }
            LogMgr.log(6, "002 Retry limit exceeded.");
            return false;
        }
    }

    private class FwsDeleteLegacySubTask extends DoScriptTask<GetDeleteScriptResponseJson> {
        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected boolean isEnableRetryHttpAccess() {
            return true;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected boolean isTimedRetrievable() {
            return false;
        }

        FwsDeleteLegacySubTask(int taskId, FwsClient fwsClient, MfiChipHolder chipHolder) {
            super(taskId, fwsClient, chipHolder, DeleteLegacyCardTask.this.mExecutor);
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
            if (this.mSeqNum == 1) {
                getDeleteScriptRequestJson.setCid(DeleteLegacyCardTask.this.mCid.equals(MfiClientConst.NO_CID_INSTANCE_KEY) ? null : DeleteLegacyCardTask.this.mCid);
                getDeleteScriptRequestJson.setLinkageData(DeleteLegacyCardTask.this.mLinkageData);
                if (DeleteLegacyCardTask.this.mSequenceCounter != null && Property.isChipGP()) {
                    getDeleteScriptRequestJson.setSequenceCounter(DeleteLegacyCardTask.this.mSequenceCounter);
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
        protected String callFws(String request) throws ProtocolException, HttpException {
            return this.mFwsClient.getDeleteScript(request, DeleteLegacyCardTask.this.mServiceId, this.mSeqNum);
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/mfc/mfi/fws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public GetDeleteScriptResponseJson convertResponse(String json) throws JSONException {
            return new GetDeleteScriptResponseJson(json);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return DeleteLegacyCardTask.VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT;
        }

        /* JADX DEBUG: Method merged with bridge method: onSuccessScript(Lcom/felicanetworks/mfc/mfi/fws/json/GetScriptResponseJson;)V */
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        public void onSuccessScript(GetDeleteScriptResponseJson response) {
            UnsupportedMfiService1CardCache.getInstance().cacheNotExistUnsupportedMfiService1Card();
            DeleteLegacyCardTask.this.onFinished(true, 0, null);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        void onErrorScript(int type, String msg) {
            if (207 == type) {
                LogMgr.log(2, "701 TYPE=" + type);
                msg = ObfuscatedMsgUtil.executionPoint();
            } else if (211 == type) {
                LogMgr.log(2, "702 TYPE=" + type);
                msg = ObfuscatedMsgUtil.executionPoint();
            } else {
                if (208 == type) {
                    LogMgr.log(2, "703 TYPE=" + type);
                    msg = ObfuscatedMsgUtil.executionPoint();
                }
                DeleteLegacyCardTask.this.onFinished(false, type, msg);
            }
            type = 200;
            DeleteLegacyCardTask.this.onFinished(false, type, msg);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_GET_DELETE_SCRIPT.msg;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean retryDelayedMillis(long delay) {
        LogMgr.log(6, "000");
        LogMgr.log(6, "001 delay[ms]=" + delay);
        if (delay >= 0) {
            try {
                Thread.sleep(delay);
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
