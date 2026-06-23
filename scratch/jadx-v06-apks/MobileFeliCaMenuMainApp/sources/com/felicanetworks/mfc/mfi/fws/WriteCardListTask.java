package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.CardInfo;
import com.felicanetworks.mfc.mfi.CardInfoJson;
import com.felicanetworks.mfc.mfi.IPipeEventCallback;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
class WriteCardListTask extends WritePipeTask<CardInfo> {
    WriteCardListTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, CardInfo[] cardInfoArr, IPipeEventCallback iPipeEventCallback) {
        super(i, executorService, listener, cardInfoArr, iPipeEventCallback);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.felicanetworks.mfc.mfi.fws.WritePipeTask
    public String createJsonString(CardInfo cardInfo) throws JSONException {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return new CardInfoJson(cardInfo).toString();
    }
}
