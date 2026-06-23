package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.CardAdditionalInfo;
import com.felicanetworks.mfc.mfi.CardAdditionalInfoJson;
import com.felicanetworks.mfc.mfi.IPipeEventCallback;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
class WriteCardExListTask extends WritePipeTask<CardAdditionalInfo> {
    WriteCardExListTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, CardAdditionalInfo[] cardAdditionalInfoArr, IPipeEventCallback iPipeEventCallback) {
        super(i, executorService, listener, cardAdditionalInfoArr, iPipeEventCallback);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.felicanetworks.mfc.mfi.fws.WritePipeTask
    public String createJsonString(CardAdditionalInfo cardAdditionalInfo) throws JSONException {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return new CardAdditionalInfoJson(cardAdditionalInfo).toString();
    }
}
