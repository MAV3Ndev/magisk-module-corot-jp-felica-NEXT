package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.http.AbstractHttpCommunicationAgent;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.Locale;

/* JADX INFO: loaded from: classes3.dex */
public class FwsBinaryResponse extends FwsResponse {
    @Override // com.felicanetworks.mfc.mfi.fws.FwsResponse, com.felicanetworks.mfc.mfi.http.IResponse
    public void checkContentType(String contentType) throws HttpException {
        if (contentType.toLowerCase(Locale.ENGLISH).contains(AbstractHttpCommunicationAgent.FWS_CONTENT_TYPE_STREAM)) {
            return;
        }
        String str = "Invalid content-type: " + contentType;
        LogMgr.log(2, "700 HttpException: " + str);
        throw new HttpException(203, str);
    }
}
