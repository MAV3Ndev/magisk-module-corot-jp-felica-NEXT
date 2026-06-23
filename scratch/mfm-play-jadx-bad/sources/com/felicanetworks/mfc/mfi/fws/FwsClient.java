package com.felicanetworks.mfc.mfi.fws;

import android.os.Build;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
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
import com.felicanetworks.mfc.mfi.util.SettingInfo;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
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
    private static final String URL_API_GET_RESET_SCRIPT = "getResetScript";
    private static final String URL_API_GET_UNIQUE_VALUE = "getUniqueValue";
    private static final String URL_API_GET_UPLOAD_TARGET = "getUploadTarget";
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
            HttpCommunicationAgent httpCommunicationAgent = this.mAgent;
            if (httpCommunicationAgent != null) {
                httpCommunicationAgent.abort();
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

    String login(String requestData, int seqNum) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, "login", seqNum), requestData);
    }

    String logout(String requestData, int seqNum) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, URL_API_LOGOUT, seqNum), requestData);
    }

    String getPermitList(String requestData) throws ProtocolException, HttpException {
        return runFwsGet(createGetUri(URL_API_GET_PERMIT_LIST, Base64Util.encode(requestData)));
    }

    String getCardList(String requestData, int seqNum) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, URL_API_GET_CARD_LIST, seqNum), requestData);
    }

    String checkCardInformation(String requestData, int seqNum) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, URL_API_CHECK_CARD_INFORMATION, seqNum), requestData);
    }

    String createCard(String requestData, int seqNum) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, URL_API_CREATE_CARD, seqNum), requestData);
    }

    String getIssueScript(String requestData, String serviceId, int seqNum) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(serviceId, URL_API_GET_ISSUE_SCRIPT, seqNum), requestData);
    }

    String getDeleteScript(String requestData, String serviceId, int seqNum) throws ProtocolException, HttpException {
        String strCreatePostUri;
        if (serviceId == null) {
            strCreatePostUri = createPostUri(URL_SERVICE_ID_GENERAL, URL_API_GET_DELETE_SCRIPT, seqNum);
        } else {
            strCreatePostUri = createPostUri(serviceId, URL_API_GET_DELETE_SCRIPT, seqNum);
        }
        return runFwsPost(strCreatePostUri, requestData);
    }

    String getAccessScript(String requestData, String serviceId, int seqNum) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(serviceId, URL_API_GET_ACCESS_SCRIPT, seqNum), requestData);
    }

    String getEnableScript(String requestData, String serviceId, int seqNum) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(serviceId, URL_API_GET_ENABLE_SCRIPT, seqNum), requestData);
    }

    String getDisableScript(String requestData, String serviceId, int seqNum) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(serviceId, URL_API_GET_DISABLE_SCRIPT, seqNum), requestData);
    }

    byte[] getContent(String requestData) throws ProtocolException, HttpException {
        return runFwsGetBinary(createGetUri(URL_API_GET_CONTENT, Base64Util.encode(requestData)));
    }

    String getInitializeScript(String requestData, int seqNum) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, URL_INITIALIZE_SCRIPT, seqNum), requestData);
    }

    String getLinkageDataScript(String requestData, int seqNum) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, URL_API_GET_LINKAGEDATA_LIST, seqNum), requestData);
    }

    String updateDeviceRegistrationToken(String requestData, int seqNum) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, URL_API_UPDATE_DEVICE_REGISTRATION_TOKEN, seqNum), requestData);
    }

    String requestPushedOperation(String requestData, int seqNum) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, URL_API_REQUEST_PUSHED_OPERATION, seqNum), requestData);
    }

    String checkExistsManagementCard(String requestData, int seqNum) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, URL_API_CHECK_EXISTS_MANAGEMENT_CARD, seqNum), requestData);
    }

    String getUploadTarget(String requestData, int seqNum) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, URL_API_GET_UPLOAD_TARGET, seqNum), requestData);
    }

    String getUniqueValue(String requestData, int seqNum) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, URL_API_GET_UNIQUE_VALUE, seqNum), requestData);
    }

    String getResetScript(String requestData, int seqNum) throws ProtocolException, HttpException {
        return runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, URL_API_GET_RESET_SCRIPT, seqNum), requestData);
    }

    public String retryFwsPost(int retryCount) throws ProtocolException, HttpException {
        return runFwsPostNoSave(this.mLastUri, this.mLastRequestData, retryCount);
    }

    private String runFwsPost(String uri, String requestData) throws ProtocolException, HttpException {
        this.mLastUri = uri;
        this.mLastRequestData = requestData;
        return runFwsPostNoSave(uri, requestData, 0);
    }

    private String runFwsPostNoSave(String uri, String requestData, int retryCount) throws ProtocolException, HttpException {
        String strCreateUserAgent = createUserAgent();
        FwsPostRequest fwsPostRequest = new FwsPostRequest();
        fwsPostRequest.setPostData(requestData);
        fwsPostRequest.setRetryCount(retryCount);
        fwsPostRequest.setWalletAppId(this.mDataManager.getWalletAppId());
        return run(uri, strCreateUserAgent, fwsPostRequest, new FwsResponse());
    }

    private String runFwsGet(String uri) throws ProtocolException, HttpException {
        String strCreateUserAgent = createUserAgent();
        FwsGetRequest fwsGetRequest = new FwsGetRequest();
        DataManager dataManager = this.mDataManager;
        if (dataManager != null && dataManager.getWalletAppId() != null) {
            fwsGetRequest.setWalletAppId(this.mDataManager.getWalletAppId());
        }
        return run(uri, strCreateUserAgent, fwsGetRequest, new FwsResponse());
    }

    private byte[] runFwsGetBinary(String uri) throws ProtocolException, HttpException {
        String strCreateUserAgent = createUserAgent();
        FwsGetRequest fwsGetRequest = new FwsGetRequest();
        DataManager dataManager = this.mDataManager;
        if (dataManager != null && dataManager.getWalletAppId() != null) {
            fwsGetRequest.setWalletAppId(this.mDataManager.getWalletAppId());
        }
        FwsBinaryResponse fwsBinaryResponse = new FwsBinaryResponse();
        runCommon(uri, strCreateUserAgent, fwsGetRequest, fwsBinaryResponse);
        return fwsBinaryResponse.getByteData();
    }

    private String createPostUri(String serviceId, String apiName, int seqNum) {
        SeInfo seInfo = this.mDataManager.getSeInfo();
        return String.format("https://%s/fws/%s/%s/%s/%s/%s/%s/%s/%d", SettingInfo.getFwsServerHost(), FWS_VERSION, seInfo.getSepId(), seInfo.getSeType(), seInfo.getSeId(), seInfo.getPlatformType(), serviceId, apiName, Integer.valueOf(seqNum));
    }

    private String createGetUri(String apiName, String payload) {
        return String.format("https://%s/fws/%s/%s?payload=%s", SettingInfo.getFwsServerHost(), FWS_VERSION, apiName, payload);
    }

    private String createUserAgent() {
        return String.format("%s/%s (%s %s; %s; %s; %s)", MfiClientConst.CLIENT_APP_NAME, FelicaAdapter.getInstance().getString(R.string.mfi_client_version), MfiClientConst.DEVICE_PLATFFORM_NAME, Build.VERSION.RELEASE, Property.getSeType(), Property.sChipIssuerId, Build.MODEL);
    }

    private String run(String uri, String userAgent, IRequest request, IResponse response) throws ProtocolException, HttpException {
        runCommon(uri, userAgent, request, response);
        return response.getData();
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [775=4] */
    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    private void runCommon(String uri, String userAgent, IRequest request, IResponse response) throws ProtocolException, HttpException {
        HttpCommunicationAgent httpCommunicationAgent;
        LogMgr.log(4, "000");
        LogMgr.log(6, "001 uri= " + uri);
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
                httpCommunicationAgent.setUserAgent(userAgent);
                this.mAgent.doTransaction(uri, request, response);
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
