package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfc.util.LogMgr;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CardInfoWithSpStatus extends CardInfo {
    public static final int SP_STATUS_NOT_APPLICABLE = 5;
    public static final int SP_STATUS_PENDING = 3;
    public static final int SP_STATUS_REISSUABLE = 1;
    public static final int SP_STATUS_UNKNOWN = 4;
    public static final int SP_STATUS_UNREISSUABLE = 2;
    protected JSONObject mSpAdditionalInfo;
    protected int mSpStatus;

    public CardInfoWithSpStatus(String str, String str2, String str3, int i, int i2, int i3, boolean z, String str4, String str5, String str6, int i4, JSONObject jSONObject) {
        super(str, str2, str3, i, i2, i3, z, str4, str5, str6);
        LogMgr.log(7, "000");
        this.mSpStatus = i4;
        this.mSpAdditionalInfo = jSONObject;
        LogMgr.log(7, "999");
    }

    public int getSpStatus() {
        return this.mSpStatus;
    }

    public JSONObject getSpAdditionalInfo() {
        return this.mSpAdditionalInfo;
    }
}
