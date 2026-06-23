package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class LinkageDataJson extends JSONObject {
    private static final int LEN_SERVICE_TYPE = 8;
    private static final String NAME_ACTION_TYPE = "actionType";
    private static final String NAME_CID = "cid";
    private static final String NAME_ISSUE_PARAMETER = "issueParameter";
    private static final String NAME_MIGRATE_CARD_IDM = "migrateCardIdm";
    private static final String NAME_NOTIFICATION_TYPE = "notificationType";
    private static final String NAME_REISSUE_CID = "reissueCid";
    private static final String NAME_SERVICE_ID = "serviceId";
    private static final String NAME_SERVICE_TYPE = "serviceType";

    public LinkageDataJson(String str) throws JSONException {
        super(str);
    }

    public CardIdentifiableInfo getCardIdentifiableInfo() throws JSONException {
        return new IssueParameterJson(JsonUtil.checkString((JSONObject) this, NAME_ISSUE_PARAMETER, true, 0)).getCardIdentifiableInfo();
    }

    public String getServiceType() throws JSONException {
        return JsonUtil.checkString((JSONObject) this, NAME_SERVICE_TYPE, true, 8);
    }

    public String getNotificationType() throws JSONException {
        return JsonUtil.checkString((JSONObject) this, NAME_NOTIFICATION_TYPE, true, 0);
    }

    public String getCid() throws JSONException {
        return JsonUtil.checkString((JSONObject) this, NAME_CID, true, 63);
    }

    public String getCidWithNoCheck() throws JSONException {
        return JsonUtil.checkString((JSONObject) this, NAME_CID, false, 63);
    }

    public String getActionType() throws JSONException {
        return JsonUtil.checkString((JSONObject) this, NAME_ACTION_TYPE, false, 0);
    }

    public String getMigrateCardIdm() throws JSONException {
        String strCheckString = JsonUtil.checkString((JSONObject) this, NAME_MIGRATE_CARD_IDM, false, 16);
        if (strCheckString != null) {
            try {
                StringUtil.hexToByteArray(strCheckString);
            } catch (NumberFormatException unused) {
                LogMgr.log(2, "700 : Not hex string.");
                throw new JSONException((String) null);
            }
        }
        return strCheckString;
    }

    public String getServiceId() throws JSONException {
        return JsonUtil.checkString((JSONObject) this, NAME_SERVICE_ID, false, 0);
    }
}
