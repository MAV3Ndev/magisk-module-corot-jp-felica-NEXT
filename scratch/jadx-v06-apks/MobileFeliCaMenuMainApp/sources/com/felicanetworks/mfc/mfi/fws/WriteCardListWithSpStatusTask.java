package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.CardInfoWithSpStatus;
import com.felicanetworks.mfc.mfi.CardInfoWithSpStatusJson;
import com.felicanetworks.mfc.mfi.IPipeEventCallback;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
class WriteCardListWithSpStatusTask extends WritePipeTask<CardInfoWithSpStatus> {
    WriteCardListWithSpStatusTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, CardInfoWithSpStatus[] cardInfoWithSpStatusArr, IPipeEventCallback iPipeEventCallback) {
        super(i, executorService, listener, cardInfoWithSpStatusArr, iPipeEventCallback);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.felicanetworks.mfc.mfi.fws.WritePipeTask
    public String createJsonString(CardInfoWithSpStatus cardInfoWithSpStatus) throws JSONException {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return new CardInfoWithSpStatusJson(cardInfoWithSpStatus).toString();
    }
}
