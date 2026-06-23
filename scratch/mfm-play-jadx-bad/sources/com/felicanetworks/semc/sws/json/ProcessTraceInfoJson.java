package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class ProcessTraceInfoJson extends JSONObject {
    private static final String NAME_OPERATION_ID = "operationId";
    private static final String NAME_OPERATION_TYPE = "operationType";
    private static final String NAME_PROCESS_ID = "processId";

    public ProcessTraceInfoJson(String str) throws JSONException {
        super(str);
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
    }

    public void checkOperationType() throws JSONException {
        LogMgr.log(6, "000");
        JsonUtil.checkString(this, "operationType", true, 8);
        LogMgr.log(6, "999");
    }

    public void checkOperationId() throws JSONException {
        LogMgr.log(6, "000");
        JsonUtil.checkString(this, "operationId", true, 32);
        LogMgr.log(6, "999");
    }

    public void checkProcessId() throws JSONException {
        LogMgr.log(6, "000");
        JsonUtil.checkString(this, NAME_PROCESS_ID, true, 32);
        LogMgr.log(6, "999");
    }
}
