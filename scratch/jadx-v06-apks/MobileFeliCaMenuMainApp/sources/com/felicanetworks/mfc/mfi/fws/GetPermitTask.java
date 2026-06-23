package com.felicanetworks.mfc.mfi.fws;

import android.content.Context;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.MfiClientConst;
import com.felicanetworks.mfc.mfi.R;
import com.felicanetworks.mfc.mfi.felica.access_control.AccessControlManager;
import com.felicanetworks.mfc.mfi.fws.AccessFwsTask;
import com.felicanetworks.mfc.mfi.fws.json.GetPermitListRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.GetPermitListResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.JwsException;
import com.felicanetworks.mfc.mfi.fws.json.JwsObject;
import com.felicanetworks.mfc.mfi.fws.json.RequestJson;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Permit;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.PermitCache;
import com.felicanetworks.mfc.mfi.util.CacheUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class GetPermitTask extends StoppableTaskBase<Result> {
    private static final int TASK_ID_GET_PERMIT_LIST = 5;
    private static final List<String> VALID_RESULT_CODE_LIST_GET_PERMIT_LIST;
    private final FwsClient mFwsClient;
    private Result mResult;
    private final String mSepId;
    private final JSONArray mWalletAppCertHashList;
    private final String mWalletAppIdentifiableInfo;

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_PERMIT_LIST = arrayList;
        arrayList.add("0000");
        VALID_RESULT_CODE_LIST_GET_PERMIT_LIST.add(FwsConst.RESULT_CODE_RETRYREQUEST);
        VALID_RESULT_CODE_LIST_GET_PERMIT_LIST.add(FwsConst.RESULT_ILLEGAL_ARGUMENTS);
        VALID_RESULT_CODE_LIST_GET_PERMIT_LIST.add(FwsConst.RESULT_ILLEGAL_URL);
        VALID_RESULT_CODE_LIST_GET_PERMIT_LIST.add("4000");
        VALID_RESULT_CODE_LIST_GET_PERMIT_LIST.add("9001");
    }

    public class Result {
        public boolean isSuccess = false;
        public Permit permit = null;
        public int errType = 100;
        public String errMsg = null;

        public Result() {
        }
    }

    public GetPermitTask(JSONArray jSONArray, String str, String str2) {
        super(5);
        this.mWalletAppCertHashList = jSONArray;
        this.mWalletAppIdentifiableInfo = str;
        this.mSepId = str2;
        this.mFwsClient = new FwsClient();
    }

    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase, com.felicanetworks.mfc.mfi.fws.TaskBase
    public void start() throws Throwable {
        this.mResult = new Result();
        if (isStopped()) {
            this.mResult.isSuccess = false;
            this.mResult.errType = 2;
            this.mResult.errMsg = AccessControlManager.ERROR_MSG_INTERRUPTED;
            return;
        }
        GetPermitListFromCacheSubTask getPermitListFromCacheSubTask = new GetPermitListFromCacheSubTask(0);
        getPermitListFromCacheSubTask.start();
        Permit result = getPermitListFromCacheSubTask.getResult();
        if (result != null) {
            this.mResult.isSuccess = true;
            this.mResult.permit = result;
            return;
        }
        if (isStopped()) {
            this.mResult.isSuccess = false;
            this.mResult.errType = 2;
            this.mResult.errMsg = AccessControlManager.ERROR_MSG_INTERRUPTED;
            return;
        }
        FwsGetPermitListSubTask fwsGetPermitListSubTask = new FwsGetPermitListSubTask(0, this.mFwsClient);
        fwsGetPermitListSubTask.start();
        AccessFwsTask.Result result2 = fwsGetPermitListSubTask.getResult2();
        if (!result2.isSuccess) {
            setErrorOrExpiredCache(getPermitListFromCacheSubTask, 3, AccessControlManager.ERROR_MSG_HTTP_COMMUNICATION_ERROR);
            return;
        }
        if (!((GetPermitListResponseJson) result2.response).isSuccess()) {
            setErrorOrExpiredCache(getPermitListFromCacheSubTask, 3, AccessControlManager.ERROR_MSG_HTTP_COMMUNICATION_ERROR);
        }
        JSONArray jSONArrayOptPermitList = ((GetPermitListResponseJson) result2.response).optPermitList();
        if (jSONArrayOptPermitList == null) {
            setErrorOrExpiredCache(getPermitListFromCacheSubTask, 101, MfiClientConst.EVENT_MSG_PERMIT_LIST_PARSE_ERROR);
            return;
        }
        if (jSONArrayOptPermitList.length() == 0) {
            setErrorOrExpiredCache(getPermitListFromCacheSubTask, 101, MfiClientConst.EVENT_MSG_EMPTY_PERMIT_LIST);
            return;
        }
        if (isStopped()) {
            this.mResult.isSuccess = false;
            this.mResult.errType = 2;
            this.mResult.errMsg = AccessControlManager.ERROR_MSG_INTERRUPTED;
            return;
        }
        for (int i = 0; i < jSONArrayOptPermitList.length(); i++) {
            try {
                if (isStopped()) {
                    this.mResult.isSuccess = false;
                    this.mResult.errType = 2;
                    this.mResult.errMsg = AccessControlManager.ERROR_MSG_INTERRUPTED;
                    return;
                } else {
                    result = verifyPermit(jSONArrayOptPermitList.optString(i), 0);
                    if (result != null) {
                        break;
                    }
                }
            } catch (JSONException unused) {
                setErrorOrExpiredCache(getPermitListFromCacheSubTask, 101, MfiClientConst.EVENT_MSG_PERMIT_LIST_PARSE_ERROR);
                return;
            }
        }
        if (result == null) {
            setErrorOrExpiredCache(getPermitListFromCacheSubTask, 101, MfiClientConst.EVENT_MSG_INVALID_PERMIT_LIST);
        } else {
            this.mResult.isSuccess = true;
            this.mResult.permit = result;
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public synchronized Result getResult2() {
        return this.mResult;
    }

    class FwsGetPermitListSubTask extends AccessFwsTask<GetPermitListResponseJson> {
        FwsGetPermitListSubTask(int i, FwsClient fwsClient) {
            super(i, fwsClient);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected RequestJson createRequestJson() throws JSONException {
            GetPermitListRequestJson getPermitListRequestJson = new GetPermitListRequestJson();
            getPermitListRequestJson.setPlatformType("02");
            getPermitListRequestJson.setWalletAppCallerInfoList(GetPermitTask.this.mWalletAppCertHashList);
            getPermitListRequestJson.setWalletAppIdentifiableInfo(GetPermitTask.this.mWalletAppIdentifiableInfo);
            getPermitListRequestJson.setSepId(GetPermitTask.this.mSepId);
            return getPermitListRequestJson;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String str) throws ProtocolException, HttpException {
            return this.mFwsClient.getPermitList(str);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public GetPermitListResponseJson convertResponse(String str) throws JSONException {
            return new GetPermitListResponseJson(str);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return GetPermitTask.VALID_RESULT_CODE_LIST_GET_PERMIT_LIST;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_GET_PERMIT_LIST.msg;
        }
    }

    class GetPermitListFromCacheSubTask extends TaskBase<Permit> {
        PermitCache mExpiredCache;
        Permit mPermit;

        GetPermitListFromCacheSubTask(int i) {
            super(i);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.TaskBase
        protected void start() throws Throwable {
            String file;
            PermitCache permitCache;
            LogMgr.log(5, "000");
            Context applicationContext = FelicaAdapter.getInstance().getApplicationContext();
            int i = 0;
            while (true) {
                if (i >= GetPermitTask.this.mWalletAppCertHashList.length()) {
                    break;
                }
                GetPermitTask getPermitTask = GetPermitTask.this;
                File file2 = new File(applicationContext.getFilesDir(), getPermitTask.getCacheFileName(getPermitTask.mWalletAppIdentifiableInfo, GetPermitTask.this.mWalletAppCertHashList.optString(i)));
                PermitCache permitCache2 = null;
                if (file2.exists()) {
                    LogMgr.log(6, "001");
                    file = CacheUtil.readFile(file2);
                } else {
                    LogMgr.log(6, "003 File does not exists.");
                    file = null;
                }
                File cacheDir = applicationContext.getCacheDir();
                GetPermitTask getPermitTask2 = GetPermitTask.this;
                CacheUtil.deleteFiles(cacheDir, getPermitTask2.getCacheFilePrefix(getPermitTask2.mWalletAppIdentifiableInfo, GetPermitTask.this.mWalletAppCertHashList.optString(i)), null);
                File filesDir = applicationContext.getFilesDir();
                GetPermitTask getPermitTask3 = GetPermitTask.this;
                CacheUtil.deleteFiles(filesDir, getPermitTask3.getCacheFilePrefix(getPermitTask3.mWalletAppIdentifiableInfo, GetPermitTask.this.mWalletAppCertHashList.optString(i)), null);
                if (file != null) {
                    try {
                        try {
                            permitCache = new PermitCache(file);
                        } catch (JwsException e) {
                            e = e;
                        }
                        try {
                            Permit permitVerifyPermit = GetPermitTask.this.verifyPermit(permitCache.getPermit(), permitCache.getUsageCount());
                            this.mPermit = permitVerifyPermit;
                            if (permitVerifyPermit == null) {
                                LogMgr.log(2, "700 Cache file is invalid. Deleting cache file.");
                            } else {
                                LogMgr.log(6, "002 Created Permit from cache file.");
                                break;
                            }
                        } catch (JwsException e2) {
                            e = e2;
                            permitCache2 = permitCache;
                            if (e.getType() == 6) {
                                this.mExpiredCache = permitCache2;
                                LogMgr.log(5, "999");
                            }
                        }
                    } catch (JSONException unused) {
                        LogMgr.log(2, "701 Failed to parse permit cache file. Deleting file.");
                    }
                }
                i++;
            }
            LogMgr.log(5, "999");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.felicanetworks.mfc.mfi.fws.TaskBase
        public Permit getResult() {
            return this.mPermit;
        }

        PermitCache getExpiredCache() {
            return this.mExpiredCache;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getCacheFileName(String str, String str2) {
        return getCacheFilePrefix(str, str2) + CacheUtil.DELIMITER + FelicaAdapter.getInstance().getString(R.string.mfi_client_version);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getCacheFilePrefix(String str, String str2) {
        return str + CacheUtil.DELIMITER + str2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Permit verifyPermit(String str, int i) throws Throwable {
        LogMgr.log(5, "%s jwsPermit=%s usageCount=%d", "000", str, Integer.valueOf(i));
        JwsObject jwsObject = new JwsObject(str);
        Permit permit = new Permit(jwsObject.getPayload());
        if (i >= permit.getUsageCountUpperLimit()) {
            LogMgr.log(2, "%s usageCount reached Upper Limit.", "800");
            return null;
        }
        if (!this.mSepId.equals(permit.getSepId())) {
            LogMgr.log(2, "%s chipIssureId does not match. mSepId = %s permit.getSepId() = %s", "801", this.mSepId, permit.getSepId());
            return null;
        }
        if (!this.mWalletAppIdentifiableInfo.equals(permit.getWalletAppIdentifiableInfo())) {
            LogMgr.log(2, "%s walletAppIdentifiableInfo does not match. mWalletAppIdentifiableInfo = %s permit.getWalletAppIdentifiableInfo() = %s", "802", this.mWalletAppIdentifiableInfo, permit.getWalletAppIdentifiableInfo());
            return null;
        }
        boolean z = false;
        int i2 = 0;
        while (true) {
            if (i2 < this.mWalletAppCertHashList.length()) {
                String strOptString = this.mWalletAppCertHashList.optString(i2);
                if (strOptString != null && strOptString.equals(permit.getWalletAppCallerInfo())) {
                    z = true;
                    break;
                }
                i2++;
            } else {
                break;
            }
        }
        if (!z) {
            LogMgr.log(2, "%s mWalletAppCertHashList does not match. mWalletAppCertHashList = %s permit.getWalletAppCallerInfo() = %s", "803", this.mWalletAppCertHashList, permit.getWalletAppCallerInfo());
            return null;
        }
        jwsObject.verify(FlavorConst.SERVER_PUBLIC_KEYS);
        jwsObject.verifyExp();
        saveCache(permit.getWalletAppIdentifiableInfo(), permit.getWalletAppCallerInfo(), str, i + 1);
        LogMgr.log(5, "%s", "999");
        return permit;
    }

    private void saveCache(String str, String str2, String str3, int i) throws Throwable {
        File file = new File(FelicaAdapter.getInstance().getFilesDir(), getCacheFileName(str, str2));
        PermitCache permitCache = new PermitCache();
        permitCache.putPermit(str3);
        permitCache.putUsageCount(i);
        CacheUtil.writeFile(file, permitCache.toString());
    }

    private void setErrorOrExpiredCache(GetPermitListFromCacheSubTask getPermitListFromCacheSubTask, int i, String str) throws Throwable {
        PermitCache expiredCache = getPermitListFromCacheSubTask.getExpiredCache();
        if (i == 3 && expiredCache != null) {
            try {
                Permit permit = new Permit(new JwsObject(expiredCache.getPermit()).getPayload());
                saveCache(permit.getWalletAppIdentifiableInfo(), permit.getWalletAppCallerInfo(), expiredCache.getPermit(), expiredCache.getUsageCount() + 1);
                this.mResult.isSuccess = true;
                this.mResult.permit = permit;
                return;
            } catch (JSONException unused) {
                this.mResult.isSuccess = false;
                this.mResult.errType = i;
                this.mResult.errMsg = str;
                return;
            }
        }
        this.mResult.isSuccess = false;
        this.mResult.errType = i;
        this.mResult.errMsg = str;
    }
}
