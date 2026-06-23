package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfc.util.LogMgr;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class CardInfoWithSpStatus extends CardInfo {
    public static final int SP_STATUS_NOT_APPLICABLE = 5;
    public static final int SP_STATUS_PENDING = 3;
    public static final int SP_STATUS_REISSUABLE = 1;
    public static final int SP_STATUS_UNKNOWN = 4;
    public static final int SP_STATUS_UNREISSUABLE = 2;
    protected JSONObject mSpAdditionalInfo;
    protected int mSpStatus;

    public CardInfoWithSpStatus(String cid, String serviceId, String walletAppId, int state, int position, int task, boolean reissuePossibility, String serviceType, String additionalInfoHash, String cardCategory, int spStatus, JSONObject spAdditionalInfo, String cardType) {
        super(cid, serviceId, walletAppId, state, position, task, reissuePossibility, serviceType, additionalInfoHash, cardCategory, cardType);
        LogMgr.log(7, "000");
        this.mSpStatus = spStatus;
        this.mSpAdditionalInfo = spAdditionalInfo;
        LogMgr.log(7, "999");
    }

    public int getSpStatus() {
        return this.mSpStatus;
    }

    public JSONObject getSpAdditionalInfo() {
        return this.mSpAdditionalInfo;
    }
}
