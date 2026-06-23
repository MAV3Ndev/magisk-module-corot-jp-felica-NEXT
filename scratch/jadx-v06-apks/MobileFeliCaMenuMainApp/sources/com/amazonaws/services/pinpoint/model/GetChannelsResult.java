package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetChannelsResult implements Serializable {
    private ChannelsResponse channelsResponse;

    public ChannelsResponse getChannelsResponse() {
        return this.channelsResponse;
    }

    public void setChannelsResponse(ChannelsResponse channelsResponse) {
        this.channelsResponse = channelsResponse;
    }

    public GetChannelsResult withChannelsResponse(ChannelsResponse channelsResponse) {
        this.channelsResponse = channelsResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getChannelsResponse() != null) {
            sb.append("ChannelsResponse: " + getChannelsResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getChannelsResponse() == null ? 0 : getChannelsResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetChannelsResult)) {
            return false;
        }
        GetChannelsResult getChannelsResult = (GetChannelsResult) obj;
        if ((getChannelsResult.getChannelsResponse() == null) ^ (getChannelsResponse() == null)) {
            return false;
        }
        return getChannelsResult.getChannelsResponse() == null || getChannelsResult.getChannelsResponse().equals(getChannelsResponse());
    }
}
