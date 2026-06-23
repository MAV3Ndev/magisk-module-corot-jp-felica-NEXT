package com.felicanetworks.mfm.main.model.internal.main.net;

import com.felicanetworks.mfm.main.model.internal.main.ModelContext;

/* JADX INFO: loaded from: classes3.dex */
public abstract class Protocol {
    public static final String ACCEPT_TYPE_KEY = "Accept";
    protected static final int APP_VERSION_DATA_LENGTH = 8;
    protected static final String CHARCODE = "UTF-8";
    public static final String CLIENT_APPID_KEY = "applicationId";
    public static final String CLIENT_CODE_KEY = "code";
    public static final String CLIENT_TOKEN_KEY = "token";
    protected static final int COM_DATA_LENGTH = 8;
    protected static final String CONNECT_TYPE_GET = "GET";
    protected static final String CONNECT_TYPE_POST = "POST";
    protected static final String CONNECT_TYPE_PUT = "PUT";
    protected static final String CONTENT_LENGTH_KEY = "content-length";
    protected static final String CONTENT_TYPE_KEY = "content-type";
    public static final String CONTENT_TYPE_VALUE_JSON = "application/json";
    protected static final String CONTENT_TYPE_VALUE_URLENCODED = "application/x-www-form-urlencoded";
    protected static final int COUNTRYCODE_MAX_LENGTH = 3;
    protected static final String DEFAULT_COUNTRY_CODE = "JP";
    protected static final String DEFAULT_LANGUAGE_CODE = "ja";
    protected static final int LANGUAGECODE_MAX_LENGTH = 3;
    protected static final String USER_AGENT_KEY = "user-agent";
    private NetworkExpert _net;

    protected Protocol(NetworkExpert net) {
        this._net = net;
    }

    protected String getUserAgent() {
        return this._net.getUserAgent().getString();
    }

    protected ModelContext getModelContext() {
        return this._net.getModelContext();
    }
}
