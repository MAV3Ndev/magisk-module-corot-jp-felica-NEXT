package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.util.LogMgr;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class ErrorMessageContainableComServInfo {
    private final boolean mIsValidFormat;
    private final ResponseErrorMessageJson mResponseErrorMessageJson;

    public ErrorMessageContainableComServInfo(String errMsg, ResponseJson response) {
        boolean z;
        ResponseErrorMessageJson responseErrorMessageJson = new ResponseErrorMessageJson();
        this.mResponseErrorMessageJson = responseErrorMessageJson;
        responseErrorMessageJson.putErrMsg(errMsg);
        if (response != null) {
            try {
                if (response.optCommunicationServerErrorInfo() != null) {
                    LogMgr.log(2, "700 SP Server Error.");
                    responseErrorMessageJson.putCommunicationServerErrorInfo(response.optCommunicationServerErrorInfo());
                }
                z = true;
            } catch (JSONException e) {
                LogMgr.log(2, "701 JSONException");
                LogMgr.printStackTrace(7, e);
                z = false;
            }
        } else {
            z = true;
        }
        this.mIsValidFormat = z;
    }

    private static class ResponseErrorMessageJson extends JSONObject {
        private static final int LEN_API_MAX = 64;
        private static final int LEN_API_MIN = 1;
        private static final int LEN_NO_CHECK = 0;
        private static final int LEN_RESULT_CODE = 4;
        private static final int LEN_SERVER_TYPE = 2;
        private static final String NAME_API = "api";
        private static final String NAME_COMMUNICATION_SERVER_ERROR_INFO = "communicationServerErrorInfo";
        private static final String NAME_ERROR_MESSAGE = "errorMessage";
        private static final String NAME_RESULT_CODE = "resultCode";
        private static final String NAME_RESULT_MESSAGE = "resultMessage";
        private static final String NAME_SERVER_TYPE = "serverType";

        public void putErrMsg(String errMsg) {
            try {
                put("errorMessage", errMsg);
            } catch (JSONException unused) {
            }
        }

        public void putCommunicationServerErrorInfo(JSONObject comServerErrorInfo) throws JSONException {
            checkCommunicationServerErrorInfoMembers(comServerErrorInfo);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(NAME_SERVER_TYPE, comServerErrorInfo.get(NAME_SERVER_TYPE));
            jSONObject.put(NAME_API, comServerErrorInfo.get(NAME_API));
            if (comServerErrorInfo.has(NAME_RESULT_CODE)) {
                jSONObject.put(NAME_RESULT_CODE, comServerErrorInfo.get(NAME_RESULT_CODE));
            }
            put(NAME_COMMUNICATION_SERVER_ERROR_INFO, jSONObject);
        }

        private void checkCommunicationServerErrorInfoMembers(JSONObject comServerErrorInfo) throws JSONException {
            JsonUtil.checkString(comServerErrorInfo, NAME_SERVER_TYPE, true, 2);
            JsonUtil.checkString(comServerErrorInfo, NAME_API, true, 1, 64);
            JsonUtil.checkString(comServerErrorInfo, NAME_RESULT_CODE, false, 4);
            JsonUtil.checkString(comServerErrorInfo, NAME_RESULT_MESSAGE, false, 0);
        }
    }

    public String getString() {
        return this.mResponseErrorMessageJson.toString();
    }

    public boolean isValidFormat() {
        return this.mIsValidFormat;
    }
}
