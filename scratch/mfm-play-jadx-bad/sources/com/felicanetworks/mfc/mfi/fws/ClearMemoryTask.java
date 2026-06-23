package com.felicanetworks.mfc.mfi.fws;

import android.content.Context;
import com.felicanetworks.mfc.AreaInformation;
import com.felicanetworks.mfc.Felica;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.MfiFelicaWrapper;
import com.felicanetworks.mfc.mfi.WalletAppIdentifiableInfo;
import com.felicanetworks.mfc.mfi.fws.AccessFwsTask;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.json.GetResetScriptRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.GetResetScriptResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.GetUniqueValueRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.GetUniqueValueResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.LinkageDataRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.LinkageDataResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.RequestJson;
import com.felicanetworks.mfc.mfi.fws.json.UniqueValueRequestTokenPayloadJson;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityAgent;
import com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityAgentErrorType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class ClearMemoryTask extends AsyncParentTaskBase<String> {
    private static final List<String> VALID_RESULT_CODE_LIST_GET_LINKAGE_DATA_LIST;
    private static final List<String> VALID_RESULT_CODE_LIST_GET_RESET_SCRIPT;
    private static final List<String> VALID_RESULT_CODE_LIST_GET_UNIQUE_VALUE;
    private final MfiChipHolder mChipHolder;
    private final DataManager mDataManager;
    private final FwsClient mFwsClient;

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public Void getResult2() {
        return null;
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public void setResult(String result) {
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
        ArrayList arrayList3 = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_RESET_SCRIPT = arrayList3;
        arrayList3.add("0000");
        arrayList3.add(FwsConst.RESULT_CODE_CONTINUE);
        arrayList3.add("2000");
        arrayList3.add(FwsConst.RESULT_ILLEGAL_URL);
        arrayList3.add(FwsConst.RESULT_ILLEGAL_ACCESS_TOKEN);
        arrayList3.add(FwsConst.RESULT_ILLEGAL_LINKAGE_DATA);
        arrayList3.add("3000");
        arrayList3.add(FwsConst.RESULT_EXPIRED_LINKAGE_DATA);
        arrayList3.add("4000");
        arrayList3.add(FwsConst.RESULT_CANNOT_MEMORY_CLEAR_BY_NOT_INITIALIZE);
        arrayList3.add(FwsConst.RESUL_CANNOT_MEMORY_CLEAR_BY_OTHER);
        arrayList3.add("9000");
        arrayList3.add("9001");
        arrayList3.add("9005");
    }

    ClearMemoryTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager) {
        super(taskId, executor, listener);
        this.mFwsClient = fwsClient;
        this.mChipHolder = chipHolder;
        this.mDataManager = dataManager;
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
                        GetPlayIntegrityTokenSubTask getPlayIntegrityTokenSubTask = new GetPlayIntegrityTokenSubTask(0, uniqueValue, strCreateGetLinkageDataListRequestToken, new GetPlayIntegrityTokenSubTask.Listener() { // from class: com.felicanetworks.mfc.mfi.fws.ClearMemoryTask.1
                            @Override // com.felicanetworks.mfc.mfi.fws.ClearMemoryTask.GetPlayIntegrityTokenSubTask.Listener
                            public void onSuccess(final String token) {
                                ClearMemoryTask.this.doResetChip(strCreateGetLinkageDataListRequestToken, uniqueValue, token);
                            }

                            @Override // com.felicanetworks.mfc.mfi.fws.ClearMemoryTask.GetPlayIntegrityTokenSubTask.Listener
                            public void onError(final int type, final String msg) {
                                ClearMemoryTask.this.onFinished(false, type, msg);
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

    private String createGetLinkageDataListRequestToken() throws MfiFelicaException {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return signJws(LinkageDataListTokenCreateHelper.createPayloadForResetChip(this.mChipHolder, this.mDataManager));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doResetChip(final String linkageDataListRequestToken, final String uniqueValue, final String integrityApiToken) {
        LogMgr.log(6, "000");
        try {
            String linkageData = getLinkageData(linkageDataListRequestToken, uniqueValue, integrityApiToken);
            if (isStopped()) {
                LogMgr.log(2, "700 Already has stopped.");
                onFinished(false, 215, null);
            } else {
                FwsGetResetScriptSubTask fwsGetResetScriptSubTask = new FwsGetResetScriptSubTask(0, this.mFwsClient, this.mChipHolder, this.mExecutor, linkageData, new FwsGetResetScriptSubTask.Listener() { // from class: com.felicanetworks.mfc.mfi.fws.ClearMemoryTask.2
                    @Override // com.felicanetworks.mfc.mfi.fws.ClearMemoryTask.FwsGetResetScriptSubTask.Listener
                    public void onSuccess() {
                        ClearMemoryTask.this.onFinished(true, 0, null);
                    }

                    @Override // com.felicanetworks.mfc.mfi.fws.ClearMemoryTask.FwsGetResetScriptSubTask.Listener
                    public void onError(final int type, final String msg) {
                        ClearMemoryTask.this.onFinished(false, type, msg);
                    }
                });
                setStoppableSubTask(fwsGetResetScriptSubTask);
                fwsGetResetScriptSubTask.start();
                LogMgr.log(6, "999");
            }
        } catch (MfiFelicaException e) {
            onFinished(false, e.getType(), e.getMessage());
        }
    }

    private String getLinkageData(final String getLinkageDataRequestToken, final String uniqueValue, final String integrityApiToken) throws MfiFelicaException {
        LogMgr.log(6, "000");
        FwsGetLinkageDataSubTask fwsGetLinkageDataSubTask = new FwsGetLinkageDataSubTask(0, this.mFwsClient, getLinkageDataRequestToken, uniqueValue, integrityApiToken);
        setStoppableSubTask(fwsGetLinkageDataSubTask);
        fwsGetLinkageDataSubTask.start();
        AccessFwsTask.Result result = fwsGetLinkageDataSubTask.getResult2();
        if (result.isSuccess) {
            try {
                String[] strArrOptFirstLinkageDataList = ((LinkageDataResponseJson) result.response).optFirstLinkageDataList();
                if (strArrOptFirstLinkageDataList != null && strArrOptFirstLinkageDataList.length != 0) {
                    LogMgr.log(6, "999");
                    return strArrOptFirstLinkageDataList[0];
                }
                LogMgr.log(2, "700");
                throw new MfiFelicaException(200, "Unknown error.");
            } catch (JSONException unused) {
                LogMgr.log(2, "701 JSONException");
                throw new MfiFelicaException(202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            }
        }
        LogMgr.log(2, "702");
        throw new MfiFelicaException(result.errType, result.errMsg);
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

    private class FwsGetUniqueValueSubTask extends AccessFwsTask<GetUniqueValueResponseJson> {
        private static final int FWS_SEQUENCE_NUMBER = 1;
        private String mUniqueValueRequestToken;

        FwsGetUniqueValueSubTask(int taskId, FwsClient fwsClient) {
            super(taskId, fwsClient);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected RequestJson createRequestJson() throws JSONException {
            GetUniqueValueRequestJson getUniqueValueRequestJson = new GetUniqueValueRequestJson();
            getUniqueValueRequestJson.setRequestId(createRequestId());
            getUniqueValueRequestJson.setOperationId(FwsParamCreator.createOperationId());
            getUniqueValueRequestJson.setUniqueValueRequestToken(this.mUniqueValueRequestToken);
            return getUniqueValueRequestJson;
        }

        public void createToken() throws MfiFelicaException {
            LogMgr.log(6, "000");
            UniqueValueRequestTokenPayloadJson uniqueValueRequestTokenPayloadJson = new UniqueValueRequestTokenPayloadJson();
            try {
                uniqueValueRequestTokenPayloadJson.setClientNonce(FwsParamCreator.createRandomNumber());
                uniqueValueRequestTokenPayloadJson.setSeInfo(ClearMemoryTask.this.mDataManager.getSeInfo());
                String strSignJws = ClearMemoryTask.this.signJws(uniqueValueRequestTokenPayloadJson);
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
                LogMgr.log(2, "701 : Exception." + e2.getClass().getSimpleName(), e2.getMessage());
                LogMgr.printStackTrace(2, e2);
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
            return ClearMemoryTask.VALID_RESULT_CODE_LIST_GET_UNIQUE_VALUE;
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
                PlayIntegrityAgent.getPlayIntegrityToken(applicationContext, this.mNonceSource, new PlayIntegrityAgent.PlayIntegrityTokenEventListener() { // from class: com.felicanetworks.mfc.mfi.fws.ClearMemoryTask.GetPlayIntegrityTokenSubTask.1
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
            LinkageDataRequestJson linkageDataRequestJson = new LinkageDataRequestJson();
            linkageDataRequestJson.setRequestId(createRequestId());
            linkageDataRequestJson.setOperationId(FwsParamCreator.createOperationId());
            linkageDataRequestJson.setLinkageDataListRequestToken(this.mRequestToken);
            WalletAppIdentifiableInfo walletAppIdentifiableInfo = WalletAppIdentifiableInfo.getInstance();
            if (!walletAppIdentifiableInfo.hasPackageName()) {
                throw new JSONException("packageName is null");
            }
            linkageDataRequestJson.setIntegrityInfo(this.mUniqueValue, walletAppIdentifiableInfo.getPackageName(), this.mIntegrityApiToken);
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
            return ClearMemoryTask.VALID_RESULT_CODE_LIST_GET_LINKAGE_DATA_LIST;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_GET_LINKAGEDATA_SCRIPT.msg;
        }
    }

    private static class FwsGetResetScriptSubTask extends DoScriptTask<GetResetScriptResponseJson> {
        private final String mLinkageData;
        private final Listener mListener;
        private final JSONArray mSystemAndAreaCodeList;

        interface Listener {
            void onError(final int errType, final String errMsg);

            void onSuccess();
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected boolean isTimedRetrievable() {
            return false;
        }

        FwsGetResetScriptSubTask(final int taskId, final FwsClient fwsClient, final MfiChipHolder chipHolder, final ExecutorService executor, final String linkageData, final Listener listener) {
            super(taskId, fwsClient, chipHolder, executor, FwsParamCreator.createOperationId());
            this.mLinkageData = linkageData;
            this.mListener = listener;
            this.mSystemAndAreaCodeList = getSystemAndAreaCodeList();
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected RequestJson createRequestJson() throws JSONException {
            GetResetScriptRequestJson getResetScriptRequestJson = new GetResetScriptRequestJson();
            getResetScriptRequestJson.setRequestId(createRequestId());
            getResetScriptRequestJson.setOperationId(this.mOperationId);
            if (this.mSeqNum == 1) {
                getResetScriptRequestJson.setLinkageData(this.mLinkageData);
                getResetScriptRequestJson.setSystemAndAreaCodeList(this.mSystemAndAreaCodeList);
                return getResetScriptRequestJson;
            }
            if (this.mAccessToken != null) {
                getResetScriptRequestJson.setAccessToken(this.mAccessToken);
                this.mAccessToken = null;
            }
            if (this.mTcapResult != null) {
                getResetScriptRequestJson.setTcapResult(this.mTcapResult);
                this.mTcapResult = null;
            }
            return getResetScriptRequestJson;
        }

        private JSONArray getSystemAndAreaCodeList() {
            JSONArray jSONArray;
            LogMgr.log(6, "000");
            MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(this.mChipHolder);
            try {
                try {
                    mfiFelicaWrapper.open();
                    mfiFelicaWrapper.select(65039);
                    int[] systemCodeList = mfiFelicaWrapper.getSystemCodeList();
                    if (systemCodeList == null) {
                        jSONArray = new JSONArray();
                    } else {
                        JSONArray jSONArray2 = new JSONArray();
                        for (int i : systemCodeList) {
                            mfiFelicaWrapper.select(i);
                            AreaInformation[] areaInformationList = mfiFelicaWrapper.getNodeInformation(0).getAreaInformationList();
                            if (areaInformationList.length == 0) {
                                jSONArray = new JSONArray();
                            } else {
                                SimpleNodeInfo simpleNodeInfo = new SimpleNodeInfo(i, areaInformationList);
                                if (!simpleNodeInfo.validFirstNode()) {
                                    jSONArray = new JSONArray();
                                } else {
                                    Iterator<String> it = simpleNodeInfo.getAreaCodeStrListDirectlyUnderArea0().iterator();
                                    while (it.hasNext()) {
                                        jSONArray2.put(simpleNodeInfo.getSystemCodeStr() + "," + it.next());
                                    }
                                }
                            }
                        }
                        return jSONArray2;
                    }
                } catch (MfiFelicaException e) {
                    LogMgr.log(2, "700 : " + e.getClass().getSimpleName() + " : " + e.getMessage());
                    jSONArray = new JSONArray();
                }
                return jSONArray;
            } finally {
                mfiFelicaWrapper.closeSilently();
                LogMgr.log(6, "999");
            }
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String request) throws ProtocolException, HttpException {
            return this.mFwsClient.getResetScript(request, this.mSeqNum);
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/mfc/mfi/fws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public GetResetScriptResponseJson convertResponse(String response) throws JSONException {
            return new GetResetScriptResponseJson(response);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return ClearMemoryTask.VALID_RESULT_CODE_LIST_GET_RESET_SCRIPT;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_GET_RESET_SCRIPT.msg;
        }

        /* JADX DEBUG: Method merged with bridge method: onSuccessScript(Lcom/felicanetworks/mfc/mfi/fws/json/GetScriptResponseJson;)V */
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        public void onSuccessScript(GetResetScriptResponseJson getResetScriptResponseJson) {
            this.mListener.onSuccess();
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        void onErrorScript(int type, String msg) {
            this.mListener.onError(type, msg);
        }
    }
}
