package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.CardAdditionalInfo;
import com.felicanetworks.mfc.mfi.CardAdditionalInfoJson;
import com.felicanetworks.mfc.mfi.IPipeEventCallback;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
class WriteCardExListTask extends WritePipeTask<CardAdditionalInfo> {
    WriteCardExListTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, CardAdditionalInfo[] cardInfoArray, IPipeEventCallback callback) {
        super(taskId, executor, listener, cardInfoArray, callback);
    }

    /* JADX DEBUG: Method merged with bridge method: createJsonString(Ljava/lang/Object;)Ljava/lang/String; */
    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.felicanetworks.mfc.mfi.fws.WritePipeTask
    public String createJsonString(CardAdditionalInfo obj) throws JSONException {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return new CardAdditionalInfoJson(obj).toString();
    }
}
