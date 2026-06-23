package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateApnsChannelResult implements Serializable {
    private APNSChannelResponse aPNSChannelResponse;

    public APNSChannelResponse getAPNSChannelResponse() {
        return this.aPNSChannelResponse;
    }

    public void setAPNSChannelResponse(APNSChannelResponse aPNSChannelResponse) {
        this.aPNSChannelResponse = aPNSChannelResponse;
    }

    public UpdateApnsChannelResult withAPNSChannelResponse(APNSChannelResponse aPNSChannelResponse) {
        this.aPNSChannelResponse = aPNSChannelResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getAPNSChannelResponse() != null) {
            sb.append("APNSChannelResponse: " + getAPNSChannelResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getAPNSChannelResponse() == null ? 0 : getAPNSChannelResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateApnsChannelResult)) {
            return false;
        }
        UpdateApnsChannelResult updateApnsChannelResult = (UpdateApnsChannelResult) obj;
        if ((updateApnsChannelResult.getAPNSChannelResponse() == null) ^ (getAPNSChannelResponse() == null)) {
            return false;
        }
        return updateApnsChannelResult.getAPNSChannelResponse() == null || updateApnsChannelResult.getAPNSChannelResponse().equals(getAPNSChannelResponse());
    }
}
