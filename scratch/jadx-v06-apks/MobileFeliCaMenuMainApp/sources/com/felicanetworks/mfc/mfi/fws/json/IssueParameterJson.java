package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.mfi.CardIdBlockInfo;
import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class IssueParameterJson extends HasCardIdentifiableBlockInfoJson {
    private static final String NAME_IDENTIFIABLE_BLOCK_LIST = "identifiableBlockList";

    public IssueParameterJson(String str) throws JSONException {
        super(str);
    }

    public CardIdentifiableInfo getCardIdentifiableInfo() throws JSONException {
        CardIdentifiableInfo cardIdentifiableInfo = new CardIdentifiableInfo();
        cardIdentifiableInfo.systemCode = getSystemCode();
        cardIdentifiableInfo.areaCode = getAreaCode();
        cardIdentifiableInfo.blockInfoList = getCiaBlockInfoList();
        return cardIdentifiableInfo;
    }

    private List<CardIdBlockInfo> getCiaBlockInfoList() throws JSONException {
        JSONArray jSONArrayCheckArray = JsonUtil.checkArray(this, NAME_IDENTIFIABLE_BLOCK_LIST, false);
        if (jSONArrayCheckArray == null) {
            return new ArrayList();
        }
        return getIdBlockList(jSONArrayCheckArray, false);
    }
}
