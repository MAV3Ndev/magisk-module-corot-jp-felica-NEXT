package com.felicanetworks.semc.http;

import com.felicanetworks.semc.SemClientCallbackConst;
import com.felicanetworks.semc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class HttpException extends Exception {
    private int mType;

    public HttpException() {
        this.mType = SemClientCallbackConst.TYPE_HTTP_ERROR;
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
    }

    public HttpException(int i, String str) {
        super(str);
        this.mType = SemClientCallbackConst.TYPE_HTTP_ERROR;
        LogMgr.log(6, "000 type=[" + i + "] message=[" + str + "]");
        this.mType = i;
        LogMgr.log(6, "999");
    }

    public int getType() {
        return this.mType;
    }
}
