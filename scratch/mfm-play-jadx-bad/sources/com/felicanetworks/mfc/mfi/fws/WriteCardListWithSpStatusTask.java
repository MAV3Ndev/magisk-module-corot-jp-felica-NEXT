package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.CardInfoWithSpStatus;
import com.felicanetworks.mfc.mfi.CardInfoWithSpStatusJson;
import com.felicanetworks.mfc.mfi.IPipeEventCallback;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
class WriteCardListWithSpStatusTask extends WritePipeTask<CardInfoWithSpStatus> {
    WriteCardListWithSpStatusTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, CardInfoWithSpStatus[] cardInfoArray, IPipeEventCallback callback) {
        super(taskId, executor, listener, cardInfoArray, callback);
    }

    /* JADX DEBUG: Method merged with bridge method: createJsonString(Ljava/lang/Object;)Ljava/lang/String; */
    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.felicanetworks.mfc.mfi.fws.WritePipeTask
    public String createJsonString(CardInfoWithSpStatus obj) throws JSONException {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return new CardInfoWithSpStatusJson(obj).toString();
    }
}
