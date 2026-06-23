package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.mfi.SeInfo;
import com.felicanetworks.mfc.mfi.SeInfoEx;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class SeInfoJson extends JSONObject implements IJsonMember {
    private static final String NAME = "seInfo";
    private static final String NAME_CONTAINER_ISSUE_INFO = "containerIssueInfo";
    private static final String NAME_IC_CODE = "icCode";
    private static final String NAME_IDM = "idm";
    private static final String NAME_SEP_ID = "sepId";
    private static final String NAME_SE_ID = "seId";
    private static final String NAME_SE_TYPE = "seType";

    @Override // com.felicanetworks.mfc.mfi.fws.json.IJsonMember
    public String getName() {
        return NAME;
    }

    public void setSeInfo(SeInfo seInfo, String str) throws JSONException {
        if (seInfo != null) {
            put(NAME_SEP_ID, seInfo.getSepId());
        }
        put(NAME_SE_TYPE, str);
        if (seInfo != null) {
            put(NAME_SE_ID, seInfo.getSeId());
        }
    }

    public void setSeInfo(SeInfo seInfo, SeInfoEx seInfoEx, String str, String str2) throws JSONException {
        setSeInfo(seInfo, str);
        put(NAME_IDM, str2);
        put(NAME_IC_CODE, seInfoEx.getIcCode());
        put(NAME_CONTAINER_ISSUE_INFO, seInfoEx.getContainerIssueInformation());
    }
}
