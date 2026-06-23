package com.felicanetworks.semc.sws.json;

import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class NotifyClientLogRequestJson extends RequestJson {
    private static final int LOG_MAX_LENGTH = 256;
    private static final String NAME_ADDITIONAL_INFO = "additionalInfo";
    private static final String NAME_DATETIME = "datetime";
    private static final String NAME_MESSAGE = "message";
    private static final String NAME_MESSAGE_CODE = "messageCode";

    public void setCurrentTime(String str) throws JSONException {
        putPayloadMember(NAME_DATETIME, str);
    }

    public void setMessageCode(String str) throws JSONException {
        putPayloadMember(NAME_MESSAGE_CODE, str);
    }

    public void setMessage(String str) throws JSONException {
        putPayloadMember(NAME_MESSAGE, String.format("%.256s", str));
    }

    public void setAdditionalInfo(String str) throws JSONException {
        putPayloadMember(NAME_ADDITIONAL_INFO, String.format("%.256s", str));
    }
}
