package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.mfi.CardIdBlockInfo;
import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class CardIdentifiableInfoJson extends HasCardIdentifiableBlockInfoJson {
    private static final int LEN_BLOCK = 32;
    private static final int LEN_BLOCK_LIST_MAX = 32;
    private static final String NAME_BLOCK_LIST = "blockList";
    private static final String NAME_CID = "cid";
    private static final String NAME_CID_BLOCK_DATA_MAPPING_LIST = "cidBlockDataMappingList";
    private static final String NAME_MASKED_BLOCK_DATA_LIST = "maskedBlockDataList";
    private static final String NAME_SERVICE_ID = "serviceId";

    public CardIdentifiableInfoJson(String json) throws JSONException {
        super(json);
    }

    public String getServiceId() throws JSONException {
        return JsonUtil.checkString((JSONObject) this, "serviceId", true, 0);
    }

    public CardIdentifiableInfo getCardIdentifiableInfo() throws JSONException {
        CardIdentifiableInfo cardIdentifiableInfo = new CardIdentifiableInfo();
        cardIdentifiableInfo.serviceId = JsonUtil.checkString((JSONObject) this, "serviceId", true, 0);
        cardIdentifiableInfo.systemCode = getSystemCode();
        cardIdentifiableInfo.areaCode = getAreaCode();
        cardIdentifiableInfo.blockInfoList = getCiaBlockInfoList();
        cardIdentifiableInfo.maskedBlockListByCidMap = getMaskedBlockListByCidMap();
        return cardIdentifiableInfo;
    }

    private List<CardIdBlockInfo> getCiaBlockInfoList() throws JSONException {
        JSONArray jSONArrayCheckArray = JsonUtil.checkArray(this, NAME_BLOCK_LIST, false);
        if (jSONArrayCheckArray == null) {
            return null;
        }
        if (jSONArrayCheckArray.length() > 32) {
            throw new JSONException("blockList length is invalid.");
        }
        return getIdBlockList(jSONArrayCheckArray, true);
    }

    private Map<String, List<String>> getMaskedBlockListByCidMap() throws JSONException {
        JSONArray jSONArrayCheckArray = JsonUtil.checkArray(this, NAME_CID_BLOCK_DATA_MAPPING_LIST, false);
        if (jSONArrayCheckArray == null) {
            return null;
        }
        HashMap map = new HashMap();
        int length = jSONArrayCheckArray.length();
        for (int i = 0; i < length; i++) {
            JSONObject jSONObjectCheckObject = JsonUtil.checkObject(jSONArrayCheckArray, i, true);
            ArrayList arrayList = new ArrayList();
            String strCheckString = JsonUtil.checkString(jSONObjectCheckObject, NAME_CID, true, 0);
            JSONArray jSONArrayCheckArray2 = JsonUtil.checkArray(jSONObjectCheckObject, NAME_MASKED_BLOCK_DATA_LIST, true);
            if (jSONArrayCheckArray2 != null) {
                int length2 = jSONArrayCheckArray2.length();
                for (int i2 = 0; i2 < length2; i2++) {
                    arrayList.add(JsonUtil.checkString(jSONArrayCheckArray2, i2, true, 32));
                }
            }
            map.put(strCheckString, arrayList);
        }
        return map;
    }
}
