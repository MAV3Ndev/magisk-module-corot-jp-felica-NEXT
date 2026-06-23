package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateBaiduChannelResult implements Serializable {
    private BaiduChannelResponse baiduChannelResponse;

    public BaiduChannelResponse getBaiduChannelResponse() {
        return this.baiduChannelResponse;
    }

    public void setBaiduChannelResponse(BaiduChannelResponse baiduChannelResponse) {
        this.baiduChannelResponse = baiduChannelResponse;
    }

    public UpdateBaiduChannelResult withBaiduChannelResponse(BaiduChannelResponse baiduChannelResponse) {
        this.baiduChannelResponse = baiduChannelResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
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
        if (obj == null || !(obj instanceof UpdateBaiduChannelResult)) {
            return false;
        }
        UpdateBaiduChannelResult updateBaiduChannelResult = (UpdateBaiduChannelResult) obj;
        if ((updateBaiduChannelResult.getBaiduChannelResponse() == null) ^ (getBaiduChannelResponse() == null)) {
            return false;
        }
        return updateBaiduChannelResult.getBaiduChannelResponse() == null || updateBaiduChannelResult.getBaiduChannelResponse().equals(getBaiduChannelResponse());
    }
}
