package com.felicanetworks.semc.sws;

import com.felicanetworks.semc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class SwsRequestHeader {
    private int mRetryCount;
    private String mUserAgent = null;
    private String mContentType = null;
    private String mClientId = null;
    private String mClientVersion = null;
    private String mSemClientVersionAdditionalInfo = null;
    private String mProfileId = null;
    private boolean mRemotelyStarted = true;
    private String mIntegrityVerificationToken = null;
    private String mIntegrityVerificationUniqueValue = null;
    private String mPackageName = null;

    public SwsRequestHeader() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
    }

    public void setUserAgent(String str) {
        LogMgr.log(6, "000 agent=[" + str + "]");
        this.mUserAgent = str;
        LogMgr.log(6, "999");
    }

    public void setContentType(String str) {
        LogMgr.log(6, "000 type=[" + str + "]");
        this.mContentType = str;
        LogMgr.log(6, "999");
    }

    public void setClientId(String str) {
        LogMgr.log(6, "000 id=[" + str + "]");
        this.mClientId = str;
        LogMgr.log(6, "999");
    }

    public void setClientVersion(String str) {
        LogMgr.log(6, "000 version=[" + str + "]");
        this.mClientVersion = str;
        LogMgr.log(6, "999");
    }

    public void setSemClientVersionAdditionalInfo(String str) {
        LogMgr.log(6, "000 SemClientVersionAdditionalInfo=[" + str + "]");
        this.mSemClientVersionAdditionalInfo = str;
        LogMgr.log(6, "999");
    }

    public void setProfileId(String str) {
        LogMgr.log(6, "000 profileID=[" + str + "]");
        this.mProfileId = str;
        LogMgr.log(6, "999");
    }

    public void setRetryCount(int i) {
        LogMgr.log(6, "000 retryCount=[" + i + "]");
        this.mRetryCount = i;
        LogMgr.log(6, "999");
    }

    public void setRemotelyStarted(boolean z) {
        LogMgr.log(6, "000 remotelyStarted=[" + z + "]");
        this.mRemotelyStarted = z;
        LogMgr.log(6, "999");
    }

    public void setIntegrityVerificationToken(String str) {
        LogMgr.log(6, "000 integrityVerificationToken=[" + str + "]");
        this.mIntegrityVerificationToken = str;
        LogMgr.log(6, "999");
    }

    public synchronized void setIntegrityVerificationUniqueValue(String str) {
        LogMgr.log(6, "000 integrityVerificationUniqueValue=[" + str + "]");
        this.mIntegrityVerificationUniqueValue = str;
        LogMgr.log(6, "999");
    }

    public synchronized void setPackageName(String str) {
        LogMgr.log(6, "000 packageName=[" + str + "]");
        this.mPackageName = str;
        LogMgr.log(6, "999");
    }

    public String getUserAgent() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999 userAgent=" + this.mUserAgent);
        return this.mUserAgent;
    }

    public String getContentType() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999 contentType=" + this.mContentType);
        return this.mContentType;
    }

    public String getClientId() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999 clientId=" + this.mClientId);
        return this.mClientId;
    }

    public String getClientVersion() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999 clientVersion=" + this.mClientVersion);
        return this.mClientVersion;
    }

    public String getSemClientVersionAdditionalInfo() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999 semClientVersionAdditionalInfo=" + this.mSemClientVersionAdditionalInfo);
        return this.mSemClientVersionAdditionalInfo;
    }

    public String getProfileId() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999 profileId=" + this.mProfileId);
        return this.mProfileId;
    }

    public int getRetryCount() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999 retryCount=" + this.mRetryCount);
        return this.mRetryCount;
    }

    public boolean getRemotelyStarted() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999 remotelyStarted=" + this.mRemotelyStarted);
        return this.mRemotelyStarted;
    }

    public String getIntegrityVerificationToken() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999 integrityVerificationToken=" + this.mIntegrityVerificationToken);
        return this.mIntegrityVerificationToken;
    }

    public synchronized String getIntegrityVerificationUniqueValue() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999 integrityVerificationUniqueValue=" + this.mIntegrityVerificationUniqueValue);
        return this.mIntegrityVerificationUniqueValue;
    }

    public synchronized String getPackageName() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999 packageName=" + this.mPackageName);
        return this.mPackageName;
    }
}
