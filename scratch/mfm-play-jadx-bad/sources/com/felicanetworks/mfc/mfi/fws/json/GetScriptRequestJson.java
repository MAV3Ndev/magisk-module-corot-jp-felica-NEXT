package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.mfi.CardIdentifiableBlockData;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class GetScriptRequestJson extends RequestJson {
    private static final String APDU_PROC_RESPONSE_NAME = "responseApduInfo";
    private static final String NAME_CID = "cid";
    static final String NAME_LINKAGE_DATA = "linkageData";
    protected static final String NAME_PAYLOAD = "payload";
    protected static final String NAME_SEQUENCE_COUNTER = "sequenceCounter";

    public void setCid(String cid) throws JSONException {
        putPayloadMember(NAME_CID, cid);
    }

    public void setLinkageData(String linkageData) throws JSONException {
        putPayloadMember("linkageData", linkageData);
    }

    public void setReadSeResult(boolean availableArea, List<CardIdentifiableBlockData> list) throws JSONException {
        ReadSeResultJson readSeResultJson = new ReadSeResultJson();
        readSeResultJson.setAvailableArea(availableArea);
        if (availableArea) {
            readSeResultJson.setIdentifiableBlockDataList(list);
        }
        putPayloadMember(readSeResultJson.getName(), readSeResultJson);
    }

    public void setTcapResult(TcapResultJson tcapResultJson) throws JSONException {
        putPayloadMember(tcapResultJson.getName(), tcapResultJson);
    }

    public void setApduResult(JSONObject ApduResultJson) throws JSONException {
        putPayloadMember(APDU_PROC_RESPONSE_NAME, ApduResultJson);
    }
}
