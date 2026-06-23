package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.Felica;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.MfiFelicaWrapper;
import com.felicanetworks.mfc.mfi.fws.AccessFwsTask;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.json.GetUploadTargetRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.GetUploadTargetResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.RequestJson;
import com.felicanetworks.mfc.mfi.fws.json.UploadTargetRequestTokenPayloadJson;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class GetRemainedCardsTask extends AsyncParentTaskBase<RemainedCardsCache> {
    private static final List<String> VALID_RESULT_CODE_LIST_GET_UPLOAD_TARGET;
    private final MfiChipHolder mChipHolder;
    private final DataManager mDataManager;
    private final FwsClient mFwsClient;
    private RemainedCardsCache mRemainedCardsCache;

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncParentTaskBase, com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    public /* bridge */ /* synthetic */ void stop() {
        super.stop();
    }

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_UPLOAD_TARGET = arrayList;
        arrayList.add("0000");
        arrayList.add(FwsConst.RESULT_INVALID_REQUEST_TOKEN);
    }

    GetRemainedCardsTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager) {
        super(taskId, executor, listener);
        this.mFwsClient = fwsClient;
        this.mChipHolder = chipHolder;
        this.mDataManager = dataManager;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() throws Throwable {
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
                LogMgr.log(2, "701 Already has stopped.");
                clearJwsCreator(aVarPrepareJwsCreator);
                onFinished(false, 215, null);
                return;
            }
            String strCreateUploadTargetRequestToken = createUploadTargetRequestToken(aVarPrepareJwsCreator);
            if (strCreateUploadTargetRequestToken == null) {
                LogMgr.log(2, "702 : Request token is null.");
                clearJwsCreator(aVarPrepareJwsCreator);
                onFinished(false, 200, "Unknown error.");
                return;
            }
            clearJwsCreator(aVarPrepareJwsCreator);
            if (isStopped()) {
                LogMgr.log(2, "703 Already has stopped.");
                onFinished(false, 215, null);
                return;
            }
            FwsGetUploadTargetSubTask fwsGetUploadTargetSubTask = new FwsGetUploadTargetSubTask(0, this.mFwsClient, strCreateUploadTargetRequestToken);
            setStoppableSubTask(fwsGetUploadTargetSubTask);
            fwsGetUploadTargetSubTask.start();
            AccessFwsTask.Result result = fwsGetUploadTargetSubTask.getResult2();
            if (result.isSuccess) {
                try {
                    RemainedCardsCache remainedCardsCache = new RemainedCardsCache(((GetUploadTargetResponseJson) result.response).optRemainedDeleteSimpleCardInfoList(), ((GetUploadTargetResponseJson) result.response).optRemainedDeleteCardIdentifiableInfoList(), ((GetUploadTargetResponseJson) result.response).optRemainedSimpleCardInfoList(), ((GetUploadTargetResponseJson) result.response).optRemainedCardIdentifiableInfoList(), this.mDataManager.getSeInfo());
                    remainedCardsCache.checkConsistencyWithChip(this.mChipHolder, this.mDataManager);
                    setResult(remainedCardsCache);
                    onFinished(true, 0, null);
                } catch (FwsException e) {
                    LogMgr.log(2, "706 FwsException");
                    onFinished(false, e.getType(), e.getMessage());
                    return;
                } catch (GpException e2) {
                    LogMgr.log(2, "705 GPException");
                    LogMgr.printStackTrace(7, e2);
                    onFinished(false, e2.getType(), e2.getMessage());
                    return;
                } catch (JSONException e3) {
                    LogMgr.log(2, "704 JSONException");
                    LogMgr.printStackTrace(7, e3);
                    onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
                    return;
                } catch (Exception e4) {
                    LogMgr.log(2, "707 Exception:" + e4.getClass().getSimpleName(), e4.getMessage());
                    LogMgr.printStackTrace(7, e4);
                    onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                    return;
                }
            } else {
                onFinished(false, result.errType, result.errMsg);
            }
            LogMgr.log(6, "999");
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
        LogMgr.log(6, "000");
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
            LogMgr.log(6, "999");
            return aVar;
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(2, e);
            onFinished(false, 200, "Unknown error.");
            LogMgr.log(6, "998");
            return null;
        }
    }

    private void clearJwsCreator(a jwsCreator) {
        LogMgr.log(6, "000");
        try {
            jwsCreator.a();
        } catch (Exception e) {
            LogMgr.log(2, "700 : Exception");
            LogMgr.printStackTrace(2, e);
        }
        LogMgr.log(6, "999");
    }

    private String createUploadTargetRequestToken(a jwsCreator) {
        LogMgr.log(6, "000");
        UploadTargetRequestTokenPayloadJson uploadTargetRequestTokenPayloadJson = new UploadTargetRequestTokenPayloadJson();
        try {
            uploadTargetRequestTokenPayloadJson.setClientNonce(FwsParamCreator.createRandomNumber());
            uploadTargetRequestTokenPayloadJson.setSeInfo(this.mDataManager.getSeInfo());
            LogMgr.log(6, "001");
            String strA = jwsCreator.a(uploadTargetRequestTokenPayloadJson);
            LogMgr.log(6, "999");
            return strA;
        } catch (JSONException e) {
            LogMgr.log(2, "700 : JSONException.");
            LogMgr.printStackTrace(2, e);
            return null;
        } catch (Exception e2) {
            LogMgr.log(2, "701 Exception:" + e2.getClass().getSimpleName(), e2.getMessage());
            LogMgr.printStackTrace(7, e2);
            return null;
        }
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public synchronized void setResult(RemainedCardsCache remainedCards) {
        this.mRemainedCardsCache = remainedCards;
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public synchronized RemainedCardsCache getResult2() {
        return this.mRemainedCardsCache;
    }

    private static class FwsGetUploadTargetSubTask extends AccessFwsTask<GetUploadTargetResponseJson> {
        private static final int FWS_SEQUENCE_NUMBER = 1;
        private final String mUploadTargetRequestToken;

        FwsGetUploadTargetSubTask(int taskId, FwsClient fwsClient, String uploadTargetRequestToken) {
            super(taskId, fwsClient);
            this.mUploadTargetRequestToken = uploadTargetRequestToken;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected RequestJson createRequestJson() throws JSONException {
            GetUploadTargetRequestJson getUploadTargetRequestJson = new GetUploadTargetRequestJson();
            getUploadTargetRequestJson.setRequestId(createRequestId());
            getUploadTargetRequestJson.setOperationId(FwsParamCreator.createOperationId());
            getUploadTargetRequestJson.setUploadTargetRequestToken(this.mUploadTargetRequestToken);
            return getUploadTargetRequestJson;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String request) throws ProtocolException, HttpException {
            return this.mFwsClient.getUploadTarget(request, 1);
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/mfc/mfi/fws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public GetUploadTargetResponseJson convertResponse(String response) throws JSONException {
            if (response != null) {
                return new GetUploadTargetResponseJson(response);
            }
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return GetRemainedCardsTask.VALID_RESULT_CODE_LIST_GET_UPLOAD_TARGET;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_GET_UPLOAD_TARGET.msg;
        }
    }
}
