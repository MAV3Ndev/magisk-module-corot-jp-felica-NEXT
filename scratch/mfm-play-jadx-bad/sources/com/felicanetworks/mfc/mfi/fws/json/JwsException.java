package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class JwsException extends JSONException {
    public static final int TYPE_HEADER_ALG_NOT_SUPPORTED = 5;
    public static final int TYPE_HEADER_EXP_EXPIRED = 6;
    public static final int TYPE_HEADER_KID_NOT_SUPPORTED = 4;
    public static final int TYPE_HEADER_TYP_NOT_SUPPORTED = 3;
    public static final int TYPE_INVALID_PUBLIC_KEY = 1;
    public static final int TYPE_JWS_FORMAT_ERROR = 2;
    public static final int TYPE_SIGNATURE_VERIFY_FAILED = 6;
    public static final int TYPE_UNKNOWN_ERROR = 0;
    private static final long serialVersionUID = -9117007009410183346L;
    protected int mType;

    public JwsException(int type, String message) {
        super(message);
        this.mType = type;
    }

    public int getType() {
        return this.mType;
    }
}
