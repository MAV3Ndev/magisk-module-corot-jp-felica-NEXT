package com.felicanetworks.mfc.mfi.mfw.i.fbl;

import com.felicanetworks.mfc.util.LogMgr;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class CommandCategory {
    private static final String KEY_OFFLINE = "offline";
    private static final String KEY_ONLINE = "online";
    private static final String KEY_PRIVILEGE_1 = "privilege1";
    private static final String KEY_PRIVILEGE_2 = "privilege2";
    private static final String KEY_READER_WRITER = "readerWriter";
    private boolean mOffline;
    private boolean mOnline;
    private boolean mPrivilege1;
    private boolean mPrivilege2;
    private boolean mReaderWriter;

    public CommandCategory() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
    }

    CommandCategory(JSONObject jSONObject) throws JSONException {
        LogMgr.log(5, "000 %s", jSONObject);
        this.mOffline = jSONObject.getBoolean(KEY_OFFLINE);
        this.mOnline = jSONObject.getBoolean(KEY_ONLINE);
        this.mReaderWriter = jSONObject.getBoolean(KEY_READER_WRITER);
        this.mPrivilege1 = jSONObject.getBoolean(KEY_PRIVILEGE_1);
        this.mPrivilege2 = jSONObject.getBoolean(KEY_PRIVILEGE_2);
        LogMgr.log(5, "999");
    }

    boolean isOffline() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mOffline;
    }

    boolean isOnline() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mOnline;
    }

    boolean isReaderWriter() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mReaderWriter;
    }

    boolean isPrivilege1() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mPrivilege1;
    }

    boolean isPrivilege2() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mPrivilege2;
    }
}
