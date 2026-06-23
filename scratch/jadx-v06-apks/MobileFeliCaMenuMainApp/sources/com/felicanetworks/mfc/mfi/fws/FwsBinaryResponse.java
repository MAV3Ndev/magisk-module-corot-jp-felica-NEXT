package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.http.HttpCommunicationAgent;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class FwsBinaryResponse extends FwsResponse {
    @Override // com.felicanetworks.mfc.mfi.fws.FwsResponse, com.felicanetworks.mfc.mfi.http.IResponse
    public void checkContentType(String str) throws HttpException {
        if (str.toLowerCase(Locale.ENGLISH).contains(HttpCommunicationAgent.FWS_CONTENT_TYPE_STREAM)) {
            return;
        }
        String str2 = MfiClientCallbackConst.MSG_INVALID_CONTENT_TYPE + str;
        LogMgr.log(2, "700 HttpException: " + str2);
        throw new HttpException(203, str2);
    }
}
