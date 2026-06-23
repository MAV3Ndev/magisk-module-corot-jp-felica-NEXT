package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateApnsSandboxChannelResult implements Serializable {
    private APNSSandboxChannelResponse aPNSSandboxChannelResponse;

    public APNSSandboxChannelResponse getAPNSSandboxChannelResponse() {
        return this.aPNSSandboxChannelResponse;
    }

    public void setAPNSSandboxChannelResponse(APNSSandboxChannelResponse aPNSSandboxChannelResponse) {
        this.aPNSSandboxChannelResponse = aPNSSandboxChannelResponse;
    }

    public UpdateApnsSandboxChannelResult withAPNSSandboxChannelResponse(APNSSandboxChannelResponse aPNSSandboxChannelResponse) {
        this.aPNSSandboxChannelResponse = aPNSSandboxChannelResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAPNSSandboxChannelResponse() != null) {
            sb.append("APNSSandboxChannelResponse: " + getAPNSSandboxChannelResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getAPNSSandboxChannelResponse() == null ? 0 : getAPNSSandboxChannelResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateApnsSandboxChannelResult)) {
            return false;
        }
        UpdateApnsSandboxChannelResult updateApnsSandboxChannelResult = (UpdateApnsSandboxChannelResult) obj;
        if ((updateApnsSandboxChannelResult.getAPNSSandboxChannelResponse() == null) ^ (getAPNSSandboxChannelResponse() == null)) {
            return false;
        }
        return updateApnsSandboxChannelResult.getAPNSSandboxChannelResponse() == null || updateApnsSandboxChannelResult.getAPNSSandboxChannelResponse().equals(getAPNSSandboxChannelResponse());
    }
}
