package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.Felica;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.MfiControlInfoCache;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.MfiFelicaWrapper;
import com.felicanetworks.mfc.mfi.MigratedServiceCache;
import com.felicanetworks.mfc.mfi.fws.AccessFwsTask;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.json.CheckExistsManagementCardRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.CheckExistsManagementCardResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.CheckExistsManagementCardTokenPayloadJson;
import com.felicanetworks.mfc.mfi.fws.json.GetMfiControlInfoResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.RequestJson;
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

/* JADX INFO: loaded from: classes3.dex */
public class CheckExistsManagementCardTask extends AsyncParentTaskBase<Result> {
    private static final List<String> VALID_RESULT_CODE_LIST;
    private final MfiChipHolder mChipHolder;
    private final DataManager mDataManager;
    private final FwsClient mFwsClient;
    private final a mJwsCreator;
    private Result mResult;
    private String mServiceId;

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncParentTaskBase, com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    public /* bridge */ /* synthetic */ void stop() {
        super.stop();
    }

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST = arrayList;
        arrayList.add("0000");
        arrayList.add("2000");
        arrayList.add(FwsConst.RESULT_ILLEGAL_URL);
        arrayList.add(FwsConst.RESULT_ILLEGAL_REQUEST_TOKEN);
        arrayList.add(FwsConst.RESULT_INVALID_REQUEST_TOKEN);
        arrayList.add("9001");
        arrayList.add("9005");
    }

    static class Result {
        boolean exist;
        String serviceId;

        Result() {
        }
    }

    CheckExistsManagementCardTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager, a jwsCreator, String serviceId) {
        super(taskId, executor, listener);
        Result result = new Result();
        this.mResult = result;
        this.mFwsClient = fwsClient;
        this.mChipHolder = chipHolder;
        this.mDataManager = dataManager;
        this.mJwsCreator = jwsCreator;
        this.mServiceId = serviceId;
        result.serviceId = serviceId;
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public Result getResult2() {
        return this.mResult;
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public void setResult(Result result) {
        this.mResult = result;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() {
        LogMgr.log(6, "000");
        if (MigratedServiceCache.isMigrated(FelicaAdapter.getInstance(), this.mServiceId)) {
            LogMgr.log(6, "998 ServiceId has already been migrated.");
            this.mResult.exist = true;
            onFinished(true, -1, null);
            return;
        }
        try {
            try {
                String infoCache = MfiControlInfoCache.getInstance().getInfoCache();
                if (infoCache != null) {
                    MfiControlInfoCache mfiControlInfoCache = MfiControlInfoCache.getInstance();
                    String datePattern = mfiControlInfoCache.getDatePattern();
                    String timeZone = mfiControlInfoCache.getTimeZone();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern, Locale.getDefault());
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
                    if (!mfiControlInfoCache.needUpdateContent(simpleDateFormat.format(new Date())) && !new GetMfiControlInfoResponseJson(infoCache).getService1ServiceIdList().contains(this.mServiceId)) {
                        LogMgr.log(2, "700 serviceId is invalid.");
                        onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                        return;
                    }
                }
            } catch (Exception e) {
                LogMgr.log(2, e.getClass().getSimpleName() + ":" + e.getMessage());
                LogMgr.printStackTrace(7, e);
                onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e));
            }
        } catch (JSONException e2) {
            LogMgr.log(2, e2.getClass().getSimpleName() + ":" + e2.getMessage());
            LogMgr.printStackTrace(7, e2);
            onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e2));
        }
        if (isStopped()) {
            LogMgr.log(2, "701 Already has stopped.");
            onFinished(false, 215, null);
            return;
        }
        if (createSeInfo()) {
            if (isStopped()) {
                LogMgr.log(2, "702 Already has stopped.");
                onFinished(false, 215, null);
                return;
            }
            a aVarPrepareJwsCreator = this.mJwsCreator;
            if (aVarPrepareJwsCreator == null) {
                aVarPrepareJwsCreator = prepareJwsCreator();
            }
            if (aVarPrepareJwsCreator == null) {
                return;
            }
            if (isStopped()) {
                LogMgr.log(2, "703 Already has stopped.");
                clearJwsCreator(aVarPrepareJwsCreator);
                onFinished(false, 215, null);
                return;
            }
            String strCreateCheckExistsManagementCardToken = createCheckExistsManagementCardToken(aVarPrepareJwsCreator);
            if (strCreateCheckExistsManagementCardToken == null) {
                clearJwsCreator(aVarPrepareJwsCreator);
                onFinished(false, 215, null);
                return;
            }
            if (isStopped()) {
                LogMgr.log(2, "704 Already has stopped.");
                clearJwsCreator(aVarPrepareJwsCreator);
                onFinished(false, 215, null);
                return;
            }
            clearJwsCreator(aVarPrepareJwsCreator);
            if (isStopped()) {
                LogMgr.log(2, "705 Already has stopped.");
                onFinished(false, 215, null);
                return;
            }
            FwsCheckExistsManagementCardResponseSubTask fwsCheckExistsManagementCardResponseSubTask = new FwsCheckExistsManagementCardResponseSubTask(0, this.mFwsClient, strCreateCheckExistsManagementCardToken);
            setStoppableSubTask(fwsCheckExistsManagementCardResponseSubTask);
            fwsCheckExistsManagementCardResponseSubTask.start();
            AccessFwsTask.Result result = fwsCheckExistsManagementCardResponseSubTask.getResult2();
            if (!result.isSuccess) {
                onFinished(false, result.errType, result.errMsg);
                return;
            }
            try {
                this.mResult.exist = ((CheckExistsManagementCardResponseJson) result.response).getExists().booleanValue();
                if (this.mResult.exist) {
                    MigratedServiceCache.cacheMigratedService(FelicaAdapter.getInstance(), this.mServiceId);
                }
                onFinished(true, -1, null);
                LogMgr.log(6, "999");
            } catch (JSONException unused) {
                onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            }
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
                mfiFelicaWrapper.closeSilently();
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
        if (this.mJwsCreator == jwsCreator) {
            return;
        }
        try {
            jwsCreator.a();
        } catch (Exception e) {
            LogMgr.log(2, "700 : Exception");
            LogMgr.printStackTrace(2, e);
        }
    }

    private String createCheckExistsManagementCardToken(a jwsCreator) {
        String strA;
        LogMgr.log(6, "000");
        try {
            CheckExistsManagementCardTokenPayloadJson checkExistsManagementCardTokenPayloadJson = new CheckExistsManagementCardTokenPayloadJson();
            checkExistsManagementCardTokenPayloadJson.setSeInfo(this.mDataManager.getSeInfo());
            checkExistsManagementCardTokenPayloadJson.setServiceId(this.mServiceId);
            LogMgr.log(6, "001");
            strA = jwsCreator.a(checkExistsManagementCardTokenPayloadJson);
        } catch (JSONException e) {
            LogMgr.log(2, "700 : JSONException");
            LogMgr.printStackTrace(2, e);
            strA = null;
        } catch (Exception e2) {
            LogMgr.log(2, "701 : Exception");
            LogMgr.printStackTrace(2, e2);
            strA = null;
        }
        if (strA == null) {
            LogMgr.log(2, "702 : Request token is null.");
            onFinished(false, 200, "Unknown error.");
        }
        LogMgr.log(6, "999");
        return strA;
    }

    private static class FwsCheckExistsManagementCardResponseSubTask extends AccessFwsTask<CheckExistsManagementCardResponseJson> {
        private static final int FWS_SEQUENCE_NUMBER = 1;
        private String mCheckExistsManagementCardToken;

        FwsCheckExistsManagementCardResponseSubTask(int taskId, FwsClient fwsClient, String checkExistsManagementCardToken) {
            super(taskId, fwsClient);
            this.mCheckExistsManagementCardToken = checkExistsManagementCardToken;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected RequestJson createRequestJson() throws JSONException {
            CheckExistsManagementCardRequestJson checkExistsManagementCardRequestJson = new CheckExistsManagementCardRequestJson();
            checkExistsManagementCardRequestJson.setRequestId(createRequestId());
            checkExistsManagementCardRequestJson.setOperationId(FwsParamCreator.createOperationId());
            checkExistsManagementCardRequestJson.setCheckExistsManagementCardToken(this.mCheckExistsManagementCardToken);
            return checkExistsManagementCardRequestJson;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String request) throws ProtocolException, HttpException {
            return this.mFwsClient.checkExistsManagementCard(request, 1);
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/mfc/mfi/fws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public CheckExistsManagementCardResponseJson convertResponse(String json) throws JSONException {
            if (json != null) {
                return new CheckExistsManagementCardResponseJson(json);
            }
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return CheckExistsManagementCardTask.VALID_RESULT_CODE_LIST;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_CHECK_EXISTS_MANAGEMENT_CARD.msg;
        }
    }
}
