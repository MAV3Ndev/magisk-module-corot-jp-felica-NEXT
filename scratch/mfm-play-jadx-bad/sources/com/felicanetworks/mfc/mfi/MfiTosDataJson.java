package com.felicanetworks.mfc.mfi;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class MfiTosDataJson extends JSONObject {
    private static final String NAME_HTML = "htmlText";

    public MfiTosDataJson(MfiTosData mfiTosData) throws JSONException {
        if (mfiTosData == null) {
            throw new JSONException("mfiTosData is null.");
        }
        put(NAME_HTML, mfiTosData.getHtmlText());
    }

    public MfiTosDataJson(String json) throws JSONException {
        super(json);
    }

    public String getHtmlText() {
        return optString(NAME_HTML);
    }
}
