package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.mfi.CardIdBlockInfo;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class HasCardIdentifiableBlockInfoJson extends JSONObject {
    private static final int LEN_BLOCK = 32;
    private static final int LEN_BLOCK_NUMBER = 4;
    private static final int LEN_NODECODESIZE = 4;
    private static final String NAME_AREA_CODE = "areaCode";
    private static final String NAME_BLOCK_MASK = "blockMask";
    private static final String NAME_BLOCK_NUMBER = "blockNumber";
    private static final String NAME_SERVICE_CODE = "serviceCode";
    private static final String NAME_SYSTEM_CODE = "systemCode";

    public HasCardIdentifiableBlockInfoJson(String str) throws JSONException {
        super(str);
    }

    protected int getSystemCode() throws JSONException {
        try {
            return StringUtil.hexToInteger(JsonUtil.checkString((JSONObject) this, NAME_SYSTEM_CODE, true, 4));
        } catch (NumberFormatException unused) {
            LogMgr.log(2, "%s System code is invalid.", "700");
            throw new JSONException("System code is invalid.");
        }
    }

    protected int getAreaCode() throws JSONException {
        String strCheckString = JsonUtil.checkString((JSONObject) this, NAME_AREA_CODE, false, 4);
        if (strCheckString == null) {
            return -1;
        }
        try {
            return StringUtil.hexToInteger(strCheckString);
        } catch (NumberFormatException unused) {
            LogMgr.log(2, "%s Area code is invalid.", "700");
            throw new JSONException("Area code is invalid.");
        }
    }

    protected List<CardIdBlockInfo> getIdBlockList(JSONArray jSONArray, boolean z) throws JSONException {
        ArrayList arrayList = new ArrayList();
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            JSONObject jSONObjectCheckObject = JsonUtil.checkObject(jSONArray, i, true);
            CardIdBlockInfo cardIdBlockInfo = new CardIdBlockInfo();
            try {
                cardIdBlockInfo.serviceCode = StringUtil.hexToInteger(JsonUtil.checkString(jSONObjectCheckObject, NAME_SERVICE_CODE, true, 4));
                try {
                    cardIdBlockInfo.blockNumber = StringUtil.hexToInteger(JsonUtil.checkString(jSONObjectCheckObject, NAME_BLOCK_NUMBER, true, 4));
                    if (z) {
                        try {
                            cardIdBlockInfo.blockMask = StringUtil.hexToByteArray(JsonUtil.checkString(jSONObjectCheckObject, NAME_BLOCK_MASK, true, 32));
                        } catch (NumberFormatException unused) {
                            LogMgr.log(2, "%s Block mask is invalid.", "702");
                            throw new JSONException("Block mask is invalid.");
                        }
                    }
                    arrayList.add(cardIdBlockInfo);
                } catch (NumberFormatException unused2) {
                    LogMgr.log(2, "%s Block number is invalid.", "701");
                    throw new JSONException("Block number is invalid.");
                }
            } catch (NumberFormatException unused3) {
                LogMgr.log(2, "%s System code is invalid.", "700");
                throw new JSONException("System code is invalid.");
            }
        }
        return arrayList;
    }
}
