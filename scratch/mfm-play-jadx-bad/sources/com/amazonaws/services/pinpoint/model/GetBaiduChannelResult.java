package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetBaiduChannelResult implements Serializable {
    private BaiduChannelResponse baiduChannelResponse;

    public BaiduChannelResponse getBaiduChannelResponse() {
        return this.baiduChannelResponse;
    }

    public void setBaiduChannelResponse(BaiduChannelResponse baiduChannelResponse) {
        this.baiduChannelResponse = baiduChannelResponse;
    }

    public GetBaiduChannelResult withBaiduChannelResponse(BaiduChannelResponse baiduChannelResponse) {
        this.baiduChannelResponse = baiduChannelResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getBaiduChannelResponse() != null) {
            sb.append("BaiduChannelResponse: " + getBaiduChannelResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getBaiduChannelResponse() == null ? 0 : getBaiduChannelResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetBaiduChannelResult)) {
            return false;
        }
        GetBaiduChannelResult getBaiduChannelResult = (GetBaiduChannelResult) obj;
        if ((getBaiduChannelResult.getBaiduChannelResponse() == null) ^ (getBaiduChannelResponse() == null)) {
            return false;
        }
        return getBaiduChannelResult.getBaiduChannelResponse() == null || getBaiduChannelResult.getBaiduChannelResponse().equals(getBaiduChannelResponse());
    }
}
