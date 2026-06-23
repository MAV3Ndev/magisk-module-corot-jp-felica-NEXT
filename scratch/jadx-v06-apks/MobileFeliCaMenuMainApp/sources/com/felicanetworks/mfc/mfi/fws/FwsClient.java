package com.felicanetworks.mfc.mfi.fws;

import android.os.Build;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.MfiClientConst;
import com.felicanetworks.mfc.mfi.R;
import com.felicanetworks.mfc.mfi.SeInfo;
import com.felicanetworks.mfc.mfi.http.HttpCommunicationAgent;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.IRequest;
import com.felicanetworks.mfc.mfi.http.IResponse;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.util.Base64Util;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
class FwsClient {
    private static final String FWS_VERSION = "v1";
    private static final String URL_API_CHECK_CARD_INFORMATION = "checkCardInformation";
    private static final String URL_API_CHECK_EXISTS_MANAGEMENT_CARD = "checkExistsManagementCard";
    private static final String URL_API_CREATE_CARD = "createCard";
    private static final String URL_API_GET_ACCESS_SCRIPT = "getAccessScript";
    private static final String URL_API_GET_CARD_LIST = "getCardList";
    private static final String URL_API_GET_CONTENT = "getContent";
    private static final String URL_API_GET_DELETE_SCRIPT = "getDeleteScript";
    private static final String URL_API_GET_DISABLE_SCRIPT = "getDisableScript";
    private static final String URL_API_GET_ENABLE_SCRIPT = "getEnableScript";
    private static final String URL_API_GET_ISSUE_SCRIPT = "getIssueScript";
    private static final String URL_API_GET_LINKAGEDATA_LIST = "getLinkageDataList";
    private static final String URL_API_GET_PERMIT_LIST = "getPermitList";
    private static final String URL_API_LOGIN = "login";
    private static final String URL_API_LOGOUT = "logout";
    private static final String URL_API_REQUEST_PUSHED_OPERATION = "requestPushedOperation";
    private static final String URL_API_UPDATE_DEVICE_REGISTRATION_TOKEN = "updateDeviceRegistrationToken";
    private static final String URL_INITIALIZE_SCRIPT = "getInitializeScript";
    private static final String URL_SERVICE_ID_GENERAL = "general";
    private HttpCommunicationAgent mAgent;
    private DataManager mDataManager;
    private String mLastUri = null;
    private String mLastRequestData = null;
    private boolean mIsStopped = false;

    FwsClient() {
    }

    FwsClient(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    void stop() {
        synchronized (this) {
            this.mIsStopped = true;
            if (this.mAgent != null) {
                this.mAgent.abort();
            }
        }
    }

    DataManager getDataManager() {
        DataManager dataManager;
        synchronized (this) {
            dataManager = this.mDataManager;
        }
        return dataManager;
    }

    String login(String str, int i) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, "login", i), str);
    }

    String logout(String str, int i) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, URL_API_LOGOUT, i), str);
    }

    String getPermitList(String str) throws ProtocolException, HttpException {
        return runFwsGet(createGetUri(URL_API_GET_PERMIT_LIST, Base64Util.encode(str)));
    }

    String getCardList(String str, int i) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, URL_API_GET_CARD_LIST, i), str);
    }

    String checkCardInformation(String str, int i) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, URL_API_CHECK_CARD_INFORMATION, i), str);
    }

    String createCard(String str, int i) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, URL_API_CREATE_CARD, i), str);
    }

    String getIssueScript(String str, String str2, int i) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(str2, URL_API_GET_ISSUE_SCRIPT, i), str);
    }

    String getDeleteScript(String str, String str2, int i) throws ProtocolException, HttpException {
        String strCreatePostUri;
        if (str2 == null) {
            strCreatePostUri = createPostUri(URL_SERVICE_ID_GENERAL, URL_API_GET_DELETE_SCRIPT, i);
        } else {
            strCreatePostUri = createPostUri(str2, URL_API_GET_DELETE_SCRIPT, i);
        }
        return runFwsPost(strCreatePostUri, str);
    }

    String getAccessScript(String str, String str2, int i) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(str2, URL_API_GET_ACCESS_SCRIPT, i), str);
    }

    String getEnableScript(String str, String str2, int i) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(str2, URL_API_GET_ENABLE_SCRIPT, i), str);
    }

    String getDisableScript(String str, String str2, int i) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(str2, URL_API_GET_DISABLE_SCRIPT, i), str);
    }

    byte[] getContent(String str) throws ProtocolException, HttpException {
        return runFwsGetBinary(createGetUri(URL_API_GET_CONTENT, Base64Util.encode(str)));
    }

    String getInitializeScript(String str, int i) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, URL_INITIALIZE_SCRIPT, i), str);
    }

    String getLinkageDataScript(String str, int i) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, URL_API_GET_LINKAGEDATA_LIST, i), str);
    }

    String updateDeviceRegistrationToken(String str, int i) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, URL_API_UPDATE_DEVICE_REGISTRATION_TOKEN, i), str);
    }

    String requestPushedOperation(String str, int i) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, URL_API_REQUEST_PUSHED_OPERATION, i), str);
    }

    String checkExistsManagementCard(String str, int i) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, URL_API_CHECK_EXISTS_MANAGEMENT_CARD, i), str);
    }

    public String retryFwsPost(int i) throws ProtocolException, HttpException {
        try {
            return runFwsPostNoSave(this.mLastUri, this.mLastRequestData, i);
        } catch (Exception e) {
            throw e;
        }
    }

    private String runFwsPost(String str, String str2) throws ProtocolException, HttpException {
        this.mLastUri = str;
        this.mLastRequestData = str2;
        return runFwsPostNoSave(str, str2, 0);
    }

    private String runFwsPostNoSave(String str, String str2, int i) throws ProtocolException, HttpException {
        String strCreateUserAgent = createUserAgent();
        FwsPostRequest fwsPostRequest = new FwsPostRequest();
        fwsPostRequest.setPostData(str2);
        fwsPostRequest.setRetryCount(i);
        fwsPostRequest.setWalletAppId(this.mDataManager.getWalletAppId());
        return run(str, strCreateUserAgent, fwsPostRequest, new FwsResponse());
    }

    private String runFwsGet(String str) throws ProtocolException, HttpException {
        String strCreateUserAgent = createUserAgent();
        FwsGetRequest fwsGetRequest = new FwsGetRequest();
        DataManager dataManager = this.mDataManager;
        if (dataManager != null && dataManager.getWalletAppId() != null) {
            fwsGetRequest.setWalletAppId(this.mDataManager.getWalletAppId());
        }
        return run(str, strCreateUserAgent, fwsGetRequest, new FwsResponse());
    }

    private byte[] runFwsGetBinary(String str) throws ProtocolException, HttpException {
        String strCreateUserAgent = createUserAgent();
        FwsGetRequest fwsGetRequest = new FwsGetRequest();
        DataManager dataManager = this.mDataManager;
        if (dataManager != null && dataManager.getWalletAppId() != null) {
            fwsGetRequest.setWalletAppId(this.mDataManager.getWalletAppId());
        }
        FwsBinaryResponse fwsBinaryResponse = new FwsBinaryResponse();
        runCommon(str, strCreateUserAgent, fwsGetRequest, fwsBinaryResponse);
        return fwsBinaryResponse.getByteData();
    }

    private String createPostUri(String str, String str2, int i) {
        SeInfo seInfo = this.mDataManager.getSeInfo();
        return String.format("https://%s/fws/%s/%s/%s/%s/%s/%s/%s/%d", FlavorConst.FWS_HOST, FWS_VERSION, seInfo.getSepId(), seInfo.getSeType(), seInfo.getSeId(), seInfo.getPlatformType(), str, str2, Integer.valueOf(i));
    }

    private String createGetUri(String str, String str2) {
        return String.format("https://%s/fws/%s/%s?payload=%s", FlavorConst.FWS_HOST, FWS_VERSION, str, str2);
    }

    private String createUserAgent() {
        return String.format("%s/%s (%s %s; %s; %s; %s)", MfiClientConst.CLIENT_APP_NAME, FelicaAdapter.getInstance().getString(R.string.mfi_client_version), MfiClientConst.DEVICE_PLATFFORM_NAME, Build.VERSION.RELEASE, Property.getSeType(), Property.sChipIssuerId, Build.MODEL);
    }

    private String run(String str, String str2, IRequest iRequest, IResponse iResponse) throws ProtocolException, HttpException {
        runCommon(str, str2, iRequest, iResponse);
        return iResponse.getData();
    }

    private void runCommon(String str, String str2, IRequest iRequest, IResponse iResponse) throws ProtocolException, HttpException {
        HttpCommunicationAgent httpCommunicationAgent;
        LogMgr.log(4, "000");
        LogMgr.log(6, "001 uri= " + str);
        synchronized (this) {
            if (this.mIsStopped) {
                LogMgr.log(2, "700 Already has stopped.");
                throw new HttpException(215, null);
            }
            if (this.mAgent != null) {
                LogMgr.log(2, "701 Running operation.");
                throw new HttpException(200, ObfuscatedMsgUtil.executionPoint());
            }
            LogMgr.log(7, "001");
            httpCommunicationAgent = new HttpCommunicationAgent();
            this.mAgent = httpCommunicationAgent;
        }
        try {
            try {
                httpCommunicationAgent.setUserAgent(str2);
                this.mAgent.doTransaction(str, iRequest, iResponse);
                synchronized (this) {
                    this.mAgent = null;
                    LogMgr.log(7, "002");
                }
                LogMgr.log(4, "999");
            } catch (Exception e) {
                throw e;
            }
        } catch (Throwable th) {
            synchronized (this) {
                this.mAgent = null;
                LogMgr.log(7, "002");
                throw th;
            }
        }
    }
}
