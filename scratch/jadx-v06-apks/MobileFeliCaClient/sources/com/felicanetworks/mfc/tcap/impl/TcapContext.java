package com.felicanetworks.mfc.tcap.impl;

/* JADX INFO: loaded from: classes.dex */
public class TcapContext {
    private String mCookie;
    private InternalDeviceList mDeviceList;
    private TcapRequest mRequest;
    private TcapResponse mResponse;
    private int mReturnCode;
    private TcapSession mSession;
    private TcapState mState;
    private boolean mStopped;
    private String mUrl;
    private int mVersion;

    TcapContext(TcapSession tcapSession, TcapRequest tcapRequest, TcapResponse tcapResponse) {
        this.mSession = tcapSession;
        this.mRequest = tcapRequest;
        this.mResponse = tcapResponse;
    }

    public TcapSession getSession() {
        return this.mSession;
    }

    public void setVersion(int i) {
        this.mVersion = i;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public void setState(TcapState tcapState) {
        this.mState = tcapState;
    }

    public TcapState getState() {
        return this.mState;
    }

    public void setUrl(String str) {
        this.mUrl = str;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public void setReturnCode(int i) {
        this.mReturnCode = i;
    }

    public int getReturnCode() {
        return this.mReturnCode;
    }

    public void setDeviceList(InternalDeviceList internalDeviceList) {
        this.mDeviceList = internalDeviceList;
    }

    public InternalDeviceList getDeviceList() {
        return this.mDeviceList;
    }

    public void setCookie(String str) {
        this.mCookie = str;
    }

    public String getCookie() {
        return this.mCookie;
    }

    public void setStopFlag(boolean z) {
        this.mStopped = z;
    }

    public boolean getStopFlag() {
        return this.mStopped;
    }

    public TcapRequest getCurrentRequest() {
        return this.mRequest;
    }

    public TcapResponse getCurrentResponse() {
        return this.mResponse;
    }
}
