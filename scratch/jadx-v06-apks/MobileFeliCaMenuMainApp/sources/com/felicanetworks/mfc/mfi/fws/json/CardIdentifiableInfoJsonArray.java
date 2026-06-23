package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class CardIdentifiableInfoJsonArray extends JSONArray {
    public CardIdentifiableInfoJsonArray(String str) throws JSONException {
        super(str);
    }

    public List<CardIdentifiableInfoJson> getCardIdentifiableInfoList() throws JSONException {
        ArrayList arrayList = new ArrayList();
        int length = length();
        for (int i = 0; i < length; i++) {
            try {
                String string = getString(i);
                if (string == null) {
                    throw new JSONException("Card identifiable info is null. index=" + i);
                }
                arrayList.add(new CardIdentifiableInfoJson(string));
            } catch (JSONException e) {
                LogMgr.log(2, "%s Fail to parse card identifiable info json. index=%d", "700", Integer.valueOf(i));
                throw e;
            }
        }
        return arrayList;
    }
}
