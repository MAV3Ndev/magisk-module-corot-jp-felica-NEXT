package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.mfi.CardIdentifiableBlockData;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class ReadSeResultJson extends JSONObject implements IJsonMember {
    private static final int BLOCK_NUMBER_LEN = 4;
    private static final String NAME = "readSeResult";
    private static final String NAME_AVAILABLE_AREA = "availableArea";
    private static final String NAME_BLOCK_DATA = "blockData";
    private static final String NAME_BLOCK_NUMBER = "blockNumber";
    private static final String NAME_IDENTIFIABLE_BLOCK_DATA_LIST = "identifiableBlockDataList";
    private static final String NAME_SERVICE_CODE = "serviceCode";
    private static final int SERVICE_CODE_LEN = 4;

    @Override // com.felicanetworks.mfc.mfi.fws.json.IJsonMember
    public String getName() {
        return NAME;
    }

    public void setAvailableArea(boolean availableArea) throws JSONException {
        put(NAME_AVAILABLE_AREA, availableArea);
    }

    public void setIdentifiableBlockDataList(List<CardIdentifiableBlockData> list) throws JSONException {
        JSONObject jSONObject;
        if (list == null) {
            return;
        }
        JSONArray jSONArray = new JSONArray();
        for (CardIdentifiableBlockData cardIdentifiableBlockData : list) {
            if (cardIdentifiableBlockData == null) {
                jSONObject = null;
            } else {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put(NAME_SERVICE_CODE, StringUtil.intToHexString(cardIdentifiableBlockData.serviceCode, 4));
                jSONObject2.put(NAME_BLOCK_NUMBER, StringUtil.intToHexString(cardIdentifiableBlockData.blockNumber, 4));
                jSONObject2.put(NAME_BLOCK_DATA, StringUtil.bytesToHexString(cardIdentifiableBlockData.data));
                jSONObject = jSONObject2;
            }
            jSONArray.put(jSONObject);
        }
        put(NAME_IDENTIFIABLE_BLOCK_DATA_LIST, jSONArray);
    }
}
