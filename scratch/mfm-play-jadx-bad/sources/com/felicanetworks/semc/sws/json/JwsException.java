package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
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

    public JwsException(int i, String str) {
        super(str);
        LogMgr.log(8, "000");
        this.mType = i;
        LogMgr.log(8, "999");
    }

    public int getType() {
        LogMgr.log(8, "000");
        LogMgr.log(8, "999");
        return this.mType;
    }
}
