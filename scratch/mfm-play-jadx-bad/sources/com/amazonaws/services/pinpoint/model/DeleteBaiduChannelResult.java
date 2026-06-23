package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class DeleteBaiduChannelResult implements Serializable {
    private BaiduChannelResponse baiduChannelResponse;

    public BaiduChannelResponse getBaiduChannelResponse() {
        return this.baiduChannelResponse;
    }

    public void setBaiduChannelResponse(BaiduChannelResponse baiduChannelResponse) {
        this.baiduChannelResponse = baiduChannelResponse;
    }

    public DeleteBaiduChannelResult withBaiduChannelResponse(BaiduChannelResponse baiduChannelResponse) {
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
        if (obj == null || !(obj instanceof DeleteBaiduChannelResult)) {
            return false;
        }
        DeleteBaiduChannelResult deleteBaiduChannelResult = (DeleteBaiduChannelResult) obj;
        if ((deleteBaiduChannelResult.getBaiduChannelResponse() == null) ^ (getBaiduChannelResponse() == null)) {
            return false;
        }
        return deleteBaiduChannelResult.getBaiduChannelResponse() == null || deleteBaiduChannelResult.getBaiduChannelResponse().equals(getBaiduChannelResponse());
    }
}
