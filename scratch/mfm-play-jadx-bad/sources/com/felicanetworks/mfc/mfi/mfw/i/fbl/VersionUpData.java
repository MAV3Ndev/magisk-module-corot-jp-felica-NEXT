package com.felicanetworks.mfc.mfi.mfw.i.fbl;

import com.felicanetworks.mfc.util.LogMgr;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class VersionUpData extends JSONObject {
    protected static final String KEY_COUNT = "count";
    protected static final String KEY_COUNT_LIMIT = "countLimit";
    protected static final String KEY_PERIOD = "period";
    protected static final String KEY_PLATFORM_VERSION = "platformVersion";
    protected static final String KEY_REQ_DATE = "reqDate";
    protected static final String KEY_REQ_DATE_LIMIT = "reqDateLimit";
    private int mCount;
    private int mCountLimit;
    private int mPeriod;
    private String mPlatformVersion;
    private long mReqDate;
    private long mReqDateLimit;

    public VersionUpData() {
        this.mCount = -1;
        this.mCountLimit = -1;
        this.mPeriod = -1;
        this.mReqDate = -1L;
        this.mReqDateLimit = -1L;
        this.mPlatformVersion = null;
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
    }

    public VersionUpData(String json) throws JSONException {
        super(json);
        this.mCount = -1;
        this.mCountLimit = -1;
        this.mPeriod = -1;
        this.mReqDate = -1L;
        this.mReqDateLimit = -1L;
        this.mPlatformVersion = null;
        LogMgr.log(5, "000 %s", json);
        this.mCount = getInt(KEY_COUNT);
        this.mCountLimit = getInt(KEY_COUNT_LIMIT);
        this.mPeriod = getInt("period");
        this.mReqDate = getLong(KEY_REQ_DATE);
        this.mReqDateLimit = getLong(KEY_REQ_DATE_LIMIT);
        this.mPlatformVersion = getString(KEY_PLATFORM_VERSION);
        LogMgr.log(5, "999");
    }

    public int optCount() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return optInt(KEY_COUNT);
    }

    public void putCount(int count) throws JSONException {
        LogMgr.log(6, "000");
        put(KEY_COUNT, count);
        LogMgr.log(6, "999");
    }

    public int optCountLimit() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return optInt(KEY_COUNT_LIMIT);
    }

    public void putCountLimit(int countLimit) throws JSONException {
        LogMgr.log(6, "000");
        put(KEY_COUNT_LIMIT, countLimit);
        LogMgr.log(6, "999");
    }

    public int optPeriod() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return optInt("period");
    }

    public void putPeriod(int period) throws JSONException {
        LogMgr.log(6, "000");
        put("period", period);
        LogMgr.log(6, "999");
    }

    public long optReqDate() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return optLong(KEY_REQ_DATE);
    }

    public void putReqDate(long reqDate) throws JSONException {
        LogMgr.log(6, "000");
        put(KEY_REQ_DATE, reqDate);
        LogMgr.log(6, "999");
    }

    public long optReqDateLimit() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return optLong(KEY_REQ_DATE_LIMIT);
    }

    public void putReqDateLimit(long reqDateLimit) throws JSONException {
        LogMgr.log(6, "000");
        put(KEY_REQ_DATE_LIMIT, reqDateLimit);
        LogMgr.log(6, "999");
    }

    public String optPlatformVersion() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return optString(KEY_PLATFORM_VERSION);
    }

    public void putPlatformVersion(String platformVersion) throws JSONException {
        LogMgr.log(6, "000");
        put(KEY_PLATFORM_VERSION, platformVersion);
        LogMgr.log(6, "999");
    }
}
