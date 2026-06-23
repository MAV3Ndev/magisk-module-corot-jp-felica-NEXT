package com.felicanetworks.mfc.mfi.fws;

import android.content.Context;
import com.felicanetworks.mfc.Felica;
import com.felicanetworks.mfc.mfi.CardIdentifiableBlockData;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.WalletAppIdentifiableInfo;
import com.felicanetworks.mfc.mfi.fws.AccessFwsTask;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.ReadCiaBlockListTask;
import com.felicanetworks.mfc.mfi.fws.RemainedCardsCache;
import com.felicanetworks.mfc.mfi.fws.UploadCardsTask;
import com.felicanetworks.mfc.mfi.fws.json.GetUniqueValueRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.GetUniqueValueResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.LinkageDataRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.LinkageDataResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.ReadSeResultJson;
import com.felicanetworks.mfc.mfi.fws.json.RequestJson;
import com.felicanetworks.mfc.mfi.fws.json.UniqueValueRequestTokenPayloadJson;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.ServiceTypeInfoUtil;
import com.felicanetworks.mfc.util.LogMgr;
import com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityAgent;
import com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityAgentErrorType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class UploadCardsToPermanentDeleteTask extends UploadCardsTask {
    private static final int TASK_ID_PERMANENT_DELETE_CARD = 1;
    private static final List<String> VALID_RESULT_CODE_LIST_GET_LINKAGE_DATA_LIST;
    private static final List<String> VALID_RESULT_CODE_LIST_GET_UNIQUE_VALUE;

    @Override // com.felicanetworks.mfc.mfi.fws.UploadCardsTask, com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public /* bridge */ /* synthetic */ UploadCardsTask.Result getResult2() {
        return super.getResult2();
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncParentTaskBase, com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    public /* bridge */ /* synthetic */ void stop() {
        super.stop();
    }

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_UNIQUE_VALUE = arrayList;
        arrayList.add("0000");
        arrayList.add("2000");
        arrayList.add(FwsConst.RESULT_ILLEGAL_URL);
        arrayList.add(FwsConst.RESULT_ILLEGAL_REQUEST_TOKEN);
        arrayList.add(FwsConst.RESULT_INVALID_REQUEST_TOKEN);
        arrayList.add("4000");
        arrayList.add("9001");
        ArrayList arrayList2 = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_LINKAGE_DATA_LIST = arrayList2;
        arrayList2.add("0000");
        arrayList2.add("1000");
        arrayList2.add("2000");
        arrayList2.add(FwsConst.RESULT_ILLEGAL_URL);
        arrayList2.add(FwsConst.RESULT_ILLEGAL_REQUEST_TOKEN);
        arrayList2.add(FwsConst.RESULT_ILLEGAL_PLAY_INTEGRITY_APP_TOKEN);
        arrayList2.add(FwsConst.RESULT_INVALID_REQUEST_TOKEN);
        arrayList2.add(FwsConst.RESULT_INVALID_PLAY_INTEGRITY_APP_TOKEN);
        arrayList2.add(FwsConst.RESULT_INVALID_PLAY_INTEGRITY_API);
        arrayList2.add("4000");
        arrayList2.add("4001");
        arrayList2.add(FwsConst.RESULT_NOT_ACTIVE_CARD);
        arrayList2.add(FwsConst.RESULT_NOT_EXIST_CARD);
        arrayList2.add(FwsConst.RESULT_NOT_APPLICABLE_DEVICE_FOR_MEMORY_CLEAR);
        arrayList2.add("9000");
        arrayList2.add("9001");
        arrayList2.add("9005");
    }

    UploadCardsToPermanentDeleteTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager, RemainedCardsCache remainedCards) {
        super(taskId, executor, listener, fwsClient, chipHolder, dataManager, remainedCards, 5);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.UploadCardsTask, com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() {
        LogMgr.log(6, "000");
        super.run();
        if (this.mIsContinue) {
            try {
                final String uniqueValue = getUniqueValue();
                if (isStopped()) {
                    LogMgr.log(2, "701 Already has stopped.");
                    onFinished(false, 215, null);
                    return;
                }
                try {
                    final String strCreateGetLinkageDataListRequestToken = createGetLinkageDataListRequestToken();
                    if (strCreateGetLinkageDataListRequestToken == null) {
                        LogMgr.log(2, "702 : Request token is null.");
                        onFinished(false, 200, "Unknown error.");
                    } else if (isStopped()) {
                        LogMgr.log(2, "703 Already has stopped.");
                        onFinished(false, 215, null);
                    } else {
                        GetPlayIntegrityTokenSubTask getPlayIntegrityTokenSubTask = new GetPlayIntegrityTokenSubTask(0, uniqueValue, strCreateGetLinkageDataListRequestToken, new GetPlayIntegrityTokenSubTask.Listener() { // from class: com.felicanetworks.mfc.mfi.fws.UploadCardsToPermanentDeleteTask.1
                            @Override // com.felicanetworks.mfc.mfi.fws.UploadCardsToPermanentDeleteTask.GetPlayIntegrityTokenSubTask.Listener
                            public void onSuccess(final String token) {
                                UploadCardsToPermanentDeleteTask.this.doUploadRemainedCards(strCreateGetLinkageDataListRequestToken, uniqueValue, token);
                            }

                            @Override // com.felicanetworks.mfc.mfi.fws.UploadCardsToPermanentDeleteTask.GetPlayIntegrityTokenSubTask.Listener
                            public void onError(final int type, final String msg) {
                                UploadCardsToPermanentDeleteTask.this.onFinished(false, type, msg);
                            }
                        });
                        setStoppableSubTask(getPlayIntegrityTokenSubTask);
                        getPlayIntegrityTokenSubTask.start();
                        LogMgr.log(6, "999");
                    }
                } catch (MfiFelicaException e) {
                    onFinished(false, e.getType(), e.getMessage());
                }
            } catch (MfiFelicaException e2) {
                onFinished(false, e2.getType(), e2.getMessage());
            }
        }
    }

    @Override // com.felicanetworks.mfc.mfi.fws.UploadCardsTask
    void createUploadCardInfoList() throws FwsException {
        LogMgr.log(6, "000");
        this.mUploadCardInfoList.addAll(this.mRemainedCards.createPermanentDeleteCardInfoList());
        LogMgr.log(6, "999");
    }

    private String createGetLinkageDataListRequestToken() throws MfiFelicaException {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return signJws(LinkageDataListTokenCreateHelper.createPayloadForPermanentDeleteCards(this.mChipHolder, this.mDataManager, createUploadCardCidList(), createReadSeResultList()));
    }

    private List<ReadSeResultJson> createReadSeResultList() throws MfiFelicaException {
        LogMgr.log(6, "000");
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (RemainedCardsCache.UploadCardInfo uploadCardInfo : this.mUploadCardInfoList) {
            ReadCiaBlockListTask readCiaBlockListTask = new ReadCiaBlockListTask(0, uploadCardInfo.cardIdInfo, this.mChipHolder);
            setStoppableSubTask(readCiaBlockListTask);
            readCiaBlockListTask.start();
            ReadCiaBlockListTask.Result result2 = readCiaBlockListTask.getResult2();
            if (!result2.isSuccess) {
                LogMgr.log(2, "701 ReadCiaBlockListTask[" + i + "] failed.");
                throw new MfiFelicaException(result2.errType, result2.errMsg);
            }
            LogMgr.log(6, "001 ReadCiaBlockListTask[" + i + "] success.");
            ReadSeResultJson readSeResultJson = new ReadSeResultJson();
            boolean zIsAvailableArea = result2.readSeResult.isAvailableArea();
            LogMgr.log(6, "002 readSeResult[" + i + "]");
            List<CardIdentifiableBlockData> readCiaBlockList = result2.readSeResult.getReadCiaBlockList();
            try {
                LogMgr.log(6, "availableArea = " + zIsAvailableArea);
                readSeResultJson.setAvailableArea(zIsAvailableArea);
                LogMgr.log(6, "serviceType = " + uploadCardInfo.serviceType);
                if (ServiceTypeInfoUtil.MultiCardType.isServersMultiple(uploadCardInfo.serviceType) && zIsAvailableArea) {
                    LogMgr.log(6, "blockDataList = " + readCiaBlockList);
                    readSeResultJson.setIdentifiableBlockDataList(readCiaBlockList);
                }
                arrayList.add(readSeResultJson);
                i++;
            } catch (JSONException unused) {
                LogMgr.log(2, "702 JSONException");
                throw new MfiFelicaException(200, "Unknown error.");
            }
        }
        LogMgr.log(6, "999");
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doUploadRemainedCards(final String linkageDataListRequestToken, final String uniqueValue, final String integrityApiToken) {
        LogMgr.log(6, "000");
        try {
            List<String> linkageDataList = getLinkageDataList(linkageDataListRequestToken, uniqueValue, integrityApiToken);
            if (this.mUploadCardInfoList.size() != linkageDataList.size()) {
                LogMgr.log(2, "700 LinkageData count is valid.");
                onFinished(false, 200, "Unknown error.");
            } else if (isStopped()) {
                LogMgr.log(2, "701 Already has stopped.");
                onFinished(false, 215, null);
            } else {
                startUploadRemainedCardsSubTask(linkageDataList);
                LogMgr.log(6, "999");
            }
        } catch (MfiFelicaException e) {
            onFinished(false, e.getType(), e.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.fws.UploadCardsTask
    void startUploadRemainedCardsSubTask(List<String> linkageDataList) {
        PermanentDeleteRemainedCardsSubTask permanentDeleteRemainedCardsSubTask = new PermanentDeleteRemainedCardsSubTask(1, this.mUploadCardInfoList, linkageDataList, new UploadCardsTask.UploadRemainedCardsSubTaskFinishListener() { // from class: com.felicanetworks.mfc.mfi.fws.UploadCardsToPermanentDeleteTask.2
            @Override // com.felicanetworks.mfc.mfi.fws.UploadCardsTask.UploadRemainedCardsSubTaskFinishListener
            public void onSuccess() {
                LogMgr.log(6, "000");
                UploadCardsToPermanentDeleteTask.this.onFinished(true, 0, null);
                LogMgr.log(6, "999");
            }

            @Override // com.felicanetworks.mfc.mfi.fws.UploadCardsTask.UploadRemainedCardsSubTaskFinishListener
            public void onError(final List<String> uploadedCardCidList, final int type, final String msg) {
                LogMgr.log(6, "000");
                UploadCardsToPermanentDeleteTask.this.setResult(new UploadCardsTask.Result(uploadedCardCidList));
                UploadCardsToPermanentDeleteTask.this.onFinished(false, type, msg);
                LogMgr.log(6, "999");
            }
        });
        setStoppableSubTask(permanentDeleteRemainedCardsSubTask);
        permanentDeleteRemainedCardsSubTask.start();
    }

    private List<String> getLinkageDataList(final String getLinkageDataRequestToken, final String uniqueValue, final String integrityApiToken) throws MfiFelicaException {
        LogMgr.log(6, "000");
        FwsGetLinkageDataSubTask fwsGetLinkageDataSubTask = new FwsGetLinkageDataSubTask(0, this.mFwsClient, getLinkageDataRequestToken, uniqueValue, integrityApiToken);
        setStoppableSubTask(fwsGetLinkageDataSubTask);
        fwsGetLinkageDataSubTask.start();
        AccessFwsTask.Result result = fwsGetLinkageDataSubTask.getResult2();
        if (result.isSuccess) {
            try {
                LogMgr.log(6, "999");
                return Arrays.asList(((LinkageDataResponseJson) result.response).optFirstLinkageDataList());
            } catch (JSONException unused) {
                LogMgr.log(2, "701 JSONException");
                throw new MfiFelicaException(202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            }
        }
        LogMgr.log(2, "702");
        throw new MfiFelicaException(result.errType, result.errMsg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String signJws(final JSONObject srcJson) {
        LogMgr.log(6, "000");
        a aVarPrepareJwsCreator = prepareJwsCreator();
        if (aVarPrepareJwsCreator == null) {
            return null;
        }
        String strA = aVarPrepareJwsCreator.a(srcJson);
        clearJwsCreator(aVarPrepareJwsCreator);
        LogMgr.log(6, "999");
        return strA;
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

    private static class FwsGetLinkageDataSubTask extends AccessFwsTask<LinkageDataResponseJson> {
        private static final int FWS_SEQUENCE_NUMBER = 1;
        private final String mIntegrityApiToken;
        private final String mRequestToken;
        private final String mUniqueValue;

        FwsGetLinkageDataSubTask(final int taskId, final FwsClient fwsClient, final String requestToken, final String uniqueValue, final String integrityApiToken) {
            super(taskId, fwsClient);
            this.mRequestToken = requestToken;
            this.mUniqueValue = uniqueValue;
            this.mIntegrityApiToken = integrityApiToken;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected RequestJson createRequestJson() throws JSONException {
            LogMgr.log(6, "000");
            LinkageDataRequestJson linkageDataRequestJson = new LinkageDataRequestJson();
            linkageDataRequestJson.setRequestId(createRequestId());
            linkageDataRequestJson.setOperationId(FwsParamCreator.createOperationId());
            linkageDataRequestJson.setLinkageDataListRequestToken(this.mRequestToken);
            WalletAppIdentifiableInfo walletAppIdentifiableInfo = WalletAppIdentifiableInfo.getInstance();
            if (!walletAppIdentifiableInfo.hasPackageName()) {
                throw new JSONException("packageName is null");
            }
            linkageDataRequestJson.setIntegrityInfo(this.mUniqueValue, walletAppIdentifiableInfo.getPackageName(), this.mIntegrityApiToken);
            LogMgr.log(6, "999");
            return linkageDataRequestJson;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String request) throws ProtocolException, HttpException {
            return this.mFwsClient.getLinkageDataScript(request, 1);
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/mfc/mfi/fws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public LinkageDataResponseJson convertResponse(String response) throws JSONException {
            return new LinkageDataResponseJson(response);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return UploadCardsToPermanentDeleteTask.VALID_RESULT_CODE_LIST_GET_LINKAGE_DATA_LIST;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_GET_LINKAGEDATA_SCRIPT.msg;
        }
    }

    private String getUniqueValue() throws MfiFelicaException {
        LogMgr.log(6, "000");
        FwsGetUniqueValueSubTask fwsGetUniqueValueSubTask = new FwsGetUniqueValueSubTask(0, this.mFwsClient);
        setStoppableSubTask(fwsGetUniqueValueSubTask);
        fwsGetUniqueValueSubTask.createToken();
        fwsGetUniqueValueSubTask.start();
        AccessFwsTask.Result result = fwsGetUniqueValueSubTask.getResult2();
        if (result.isSuccess) {
            try {
                LogMgr.log(6, "999");
                return ((GetUniqueValueResponseJson) result.response).getUniqueValue();
            } catch (JSONException unused) {
                LogMgr.log(2, "700 JSONException");
                throw new MfiFelicaException(202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            }
        }
        LogMgr.log(2, "701");
        throw new MfiFelicaException(result.errType, result.errMsg);
    }

    private class PermanentDeleteRemainedCardsSubTask extends UploadCardsTask.UploadRemainedCardsSubTask {
        PermanentDeleteRemainedCardsSubTask(int taskId, List<RemainedCardsCache.UploadCardInfo> cardInfoList, List<String> linkageDataList, UploadCardsTask.UploadRemainedCardsSubTaskFinishListener listener) {
            super(taskId, cardInfoList, linkageDataList, listener);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.UploadCardsTask.UploadRemainedCardsSubTask
        void uploadNextCard() {
            PermanentDeleteRemainedCardsSubTask permanentDeleteRemainedCardsSubTask;
            LogMgr.log(6, "000");
            try {
                this.mCardInfoInUploadProcess = this.mUploadCardInfoIterator.next();
                permanentDeleteRemainedCardsSubTask = this;
            } catch (Exception e) {
                e = e;
                permanentDeleteRemainedCardsSubTask = this;
            }
            try {
                DeleteCardTask deleteCardTask = new DeleteCardTask(1, UploadCardsToPermanentDeleteTask.this.mExecutor, permanentDeleteRemainedCardsSubTask, this.mCardInfoInUploadProcess.cid, this.mCardInfoInUploadProcess.cardIdInfo, this.mLinkageDataIterator.next(), UploadCardsToPermanentDeleteTask.this.mFwsClient, UploadCardsToPermanentDeleteTask.this.mChipHolder, UploadCardsToPermanentDeleteTask.this.mDataManager, this.mCardInfoInUploadProcess.serviceType);
                UploadCardsToPermanentDeleteTask.this.setStoppableSubTask(deleteCardTask);
                deleteCardTask.start();
            } catch (Exception e2) {
                e = e2;
                LogMgr.log(1, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
                LogMgr.printStackTrace(7, e);
                permanentDeleteRemainedCardsSubTask.mListener.onError(getResult2(), 200, ObfuscatedMsgUtil.exExecutionPoint(e));
            }
            LogMgr.log(6, "999");
        }
    }

    private class FwsGetUniqueValueSubTask extends AccessFwsTask<GetUniqueValueResponseJson> {
        private static final int FWS_SEQUENCE_NUMBER = 1;
        private String mUniqueValueRequestToken;

        FwsGetUniqueValueSubTask(int taskId, FwsClient fwsClient) {
            super(taskId, fwsClient);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected RequestJson createRequestJson() throws JSONException {
            LogMgr.log(6, "000");
            GetUniqueValueRequestJson getUniqueValueRequestJson = new GetUniqueValueRequestJson();
            getUniqueValueRequestJson.setRequestId(createRequestId());
            getUniqueValueRequestJson.setOperationId(FwsParamCreator.createOperationId());
            getUniqueValueRequestJson.setUniqueValueRequestToken(this.mUniqueValueRequestToken);
            LogMgr.log(6, "999");
            return getUniqueValueRequestJson;
        }

        public void createToken() throws MfiFelicaException {
            LogMgr.log(6, "000");
            UniqueValueRequestTokenPayloadJson uniqueValueRequestTokenPayloadJson = new UniqueValueRequestTokenPayloadJson();
            try {
                uniqueValueRequestTokenPayloadJson.setClientNonce(FwsParamCreator.createRandomNumber());
                uniqueValueRequestTokenPayloadJson.setSeInfo(UploadCardsToPermanentDeleteTask.this.mDataManager.getSeInfo());
                String strSignJws = UploadCardsToPermanentDeleteTask.this.signJws(uniqueValueRequestTokenPayloadJson);
                this.mUniqueValueRequestToken = strSignJws;
                if (strSignJws == null) {
                    LogMgr.log(2, "702 : Request token is null.");
                    throw new MfiFelicaException(200, "Unknown error.");
                }
                LogMgr.log(6, "999");
            } catch (JSONException e) {
                LogMgr.log(2, "700 : JSONException.");
                LogMgr.printStackTrace(2, e);
                throw new MfiFelicaException(202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            } catch (Exception e2) {
                LogMgr.log(2, "701 Exception:" + e2.getClass().getSimpleName(), e2.getMessage());
                LogMgr.printStackTrace(7, e2);
                throw new MfiFelicaException(200, "Unknown error.");
            }
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String request) throws ProtocolException, HttpException {
            return this.mFwsClient.getUniqueValue(request, 1);
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/mfc/mfi/fws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public GetUniqueValueResponseJson convertResponse(String response) throws JSONException {
            return new GetUniqueValueResponseJson(response);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return UploadCardsToPermanentDeleteTask.VALID_RESULT_CODE_LIST_GET_UNIQUE_VALUE;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_GET_UNIQUE_VALUE.msg;
        }
    }

    private static class GetPlayIntegrityTokenSubTask extends StoppableTaskBase<Void> {
        private static final Map<PlayIntegrityAgentErrorType.Error, Integer> CONVERT_PIA_ERROR_MAP;
        private boolean mFinish;
        private final Listener mListener;
        private final String mNonceSource;

        interface Listener {
            void onError(final int type, final String msg);

            void onSuccess(final String token);
        }

        /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
        @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
        /* JADX INFO: renamed from: getResult, reason: avoid collision after fix types in other method */
        public Void getResult2() {
            return null;
        }

        static {
            HashMap map = new HashMap();
            CONVERT_PIA_ERROR_MAP = map;
            map.put(PlayIntegrityAgentErrorType.Error.TYPE_PLAY_SERVICE_ERROR, 248);
            map.put(PlayIntegrityAgentErrorType.Error.TYPE_PLAY_STORE_ERROR, 249);
            map.put(PlayIntegrityAgentErrorType.Error.TYPE_NETWORK_UNAVAILABLE, 205);
            map.put(PlayIntegrityAgentErrorType.Error.TYPE_PLAY_INTEGRITY_NONRECOVERABLE_FAILED, 251);
            map.put(PlayIntegrityAgentErrorType.Error.TYPE_PLAY_INTEGRITY_RECOVERABLE_FAILED, 250);
            map.put(PlayIntegrityAgentErrorType.Error.TYPE_UNSUPPORTED_OS_VERSION, 200);
            map.put(PlayIntegrityAgentErrorType.Error.TYPE_INTERRUPTED_ERROR, 200);
            map.put(PlayIntegrityAgentErrorType.Error.TYPE_UNKNOWN_ERROR, 200);
        }

        GetPlayIntegrityTokenSubTask(final int taskId, final String uniqueValue, final String linkageDataRequestToken, final Listener listener) {
            super(taskId);
            this.mNonceSource = uniqueValue + linkageDataRequestToken;
            this.mListener = listener;
            this.mFinish = false;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase, com.felicanetworks.mfc.mfi.fws.TaskBase
        public void start() {
            LogMgr.log(6, "000");
            try {
                Context applicationContext = FelicaAdapter.getInstance().getApplicationContext();
                LogMgr.log(7, "001");
                PlayIntegrityAgent.getPlayIntegrityToken(applicationContext, this.mNonceSource, new PlayIntegrityAgent.PlayIntegrityTokenEventListener() { // from class: com.felicanetworks.mfc.mfi.fws.UploadCardsToPermanentDeleteTask.GetPlayIntegrityTokenSubTask.1
                    @Override // com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityAgent.PlayIntegrityTokenEventListener
                    public void onSuccess(String token) {
                        LogMgr.log(6, "000");
                        if (GetPlayIntegrityTokenSubTask.this.isStopped()) {
                            LogMgr.log(2, "700 Already has stopped.");
                            GetPlayIntegrityTokenSubTask.this.mListener.onError(215, null);
                            return;
                        }
                        synchronized (this) {
                            if (!GetPlayIntegrityTokenSubTask.this.mFinish) {
                                GetPlayIntegrityTokenSubTask.this.mFinish = true;
                                GetPlayIntegrityTokenSubTask.this.mListener.onSuccess(token);
                                LogMgr.log(6, "999");
                                return;
                            }
                            LogMgr.log(2, "701 Already finished.");
                        }
                    }

                    @Override // com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityAgent.PlayIntegrityTokenEventListener
                    public void onError(PlayIntegrityAgentErrorType.Error type, String msg) {
                        LogMgr.log(6, "000");
                        if (GetPlayIntegrityTokenSubTask.this.isStopped()) {
                            LogMgr.log(2, "700 Already has stopped.");
                            GetPlayIntegrityTokenSubTask.this.mListener.onError(215, null);
                            return;
                        }
                        synchronized (this) {
                            if (!GetPlayIntegrityTokenSubTask.this.mFinish) {
                                GetPlayIntegrityTokenSubTask.this.mFinish = true;
                                if (type == PlayIntegrityAgentErrorType.Error.TYPE_UNSUPPORTED_OS_VERSION) {
                                    LogMgr.log(7, "001.");
                                    msg = ObfuscatedMsgUtil.executionPoint();
                                }
                                GetPlayIntegrityTokenSubTask.this.mListener.onError(GetPlayIntegrityTokenSubTask.this.convertPiaErrorType(type), msg);
                                LogMgr.log(6, "999");
                                return;
                            }
                            LogMgr.log(2, "701 Already finished.");
                        }
                    }
                });
            } catch (IllegalArgumentException unused) {
                LogMgr.log(1, "800 Parameter is Invalid.");
                this.mListener.onError(200, ObfuscatedMsgUtil.executionPoint());
            } catch (Exception unused2) {
                LogMgr.log(1, "801 Unknown error occurred.");
                this.mListener.onError(200, ObfuscatedMsgUtil.executionPoint());
            }
            LogMgr.log(6, "999");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int convertPiaErrorType(PlayIntegrityAgentErrorType.Error type) {
            LogMgr.log(6, "000");
            Integer num = CONVERT_PIA_ERROR_MAP.get(type);
            if (num == null) {
                LogMgr.log(7, "001");
                num = 200;
            }
            LogMgr.log(6, "999 type = " + num);
            return num.intValue();
        }
    }
}
