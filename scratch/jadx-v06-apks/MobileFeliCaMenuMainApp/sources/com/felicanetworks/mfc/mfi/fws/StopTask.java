package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.AccountCache;
import com.felicanetworks.mfc.mfi.CardCache;
import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import com.felicanetworks.mfc.mfi.CompleteCardInfo;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.fws.AccessFwsTask;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.json.LogoutRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.LogoutResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.RequestJson;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
class StopTask extends AsyncParentTaskBase<Void> {
    private static final List<String> VALID_RESULT_CODE_LIST_LOGOUT;
    private final String mAccountId;
    private final String mAppCallerInfo;
    private final String mAppIdInfo;
    private final boolean mAutoMfiServerLogout;
    private Map<String, CardIdentifiableInfo.Cache> mCardIdInfoMap;
    private Map<String, CompleteCardInfo.Cache> mCardInfoMap;
    private final FwsClient mFwsClient;
    private final String mLoginTokenId;
    private boolean mNoLogin;

    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public Void getResult2() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public void setResult(Void r1) {
    }

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_LOGOUT = arrayList;
        arrayList.add("0000");
        VALID_RESULT_CODE_LIST_LOGOUT.add(FwsConst.RESULT_CODE_RETRYREQUEST);
        VALID_RESULT_CODE_LIST_LOGOUT.add(FwsConst.RESULT_ILLEGAL_ARGUMENTS);
        VALID_RESULT_CODE_LIST_LOGOUT.add(FwsConst.RESULT_ILLEGAL_URL);
        VALID_RESULT_CODE_LIST_LOGOUT.add("9001");
    }

    StopTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, String str, String str2, boolean z, String str3, String str4, Map<String, CompleteCardInfo.Cache> map, Map<String, CardIdentifiableInfo.Cache> map2, FwsClient fwsClient) {
        super(i, executorService, listener);
        this.mNoLogin = false;
        this.mLoginTokenId = str;
        this.mAccountId = str2;
        this.mAutoMfiServerLogout = z;
        this.mAppCallerInfo = str3;
        this.mAppIdInfo = str4;
        this.mCardInfoMap = map;
        this.mCardIdInfoMap = map2;
        this.mFwsClient = fwsClient;
    }

    void requestNoLogin() {
        this.mNoLogin = true;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() {
        String str = null;
        boolean z = true;
        int i = 0;
        if (this.mNoLogin) {
            onFinished(true, 0, null);
            return;
        }
        if (this.mAutoMfiServerLogout) {
            FwsLogoutSubTask fwsLogoutSubTask = new FwsLogoutSubTask(0, this.mFwsClient);
            setStoppableSubTask(fwsLogoutSubTask);
            fwsLogoutSubTask.start();
            AccessFwsTask.Result result = fwsLogoutSubTask.getResult2();
            z = result.isSuccess;
            i = result.errType;
            str = result.errMsg;
            if (z) {
                AccountCache.getInstance().clearLoginTokenCache();
            }
        }
        if (z) {
            CardCache.cache(this.mAppCallerInfo, this.mAppIdInfo, this.mAccountId, this.mCardInfoMap, this.mCardIdInfoMap);
        }
        onFinished(z, i, str);
    }

    private class FwsLogoutSubTask extends AccessFwsTask<LogoutResponseJson> {
        private static final int FWS_SEQUENCE_NUMBER = 1;

        FwsLogoutSubTask(int i, FwsClient fwsClient) {
            super(i, fwsClient);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected RequestJson createRequestJson() throws JSONException {
            LogoutRequestJson logoutRequestJson = new LogoutRequestJson();
            logoutRequestJson.setRequestId(createRequestId());
            logoutRequestJson.setOperationId(FwsParamCreator.createOperationId());
            logoutRequestJson.setLoginTokenId(StopTask.this.mLoginTokenId);
            return logoutRequestJson;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String str) throws ProtocolException, HttpException {
            return this.mFwsClient.logout(str, 1);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public LogoutResponseJson convertResponse(String str) throws JSONException {
            return new LogoutResponseJson(str);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return StopTask.VALID_RESULT_CODE_LIST_LOGOUT;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_LOGOUT.msg;
        }
    }
}
