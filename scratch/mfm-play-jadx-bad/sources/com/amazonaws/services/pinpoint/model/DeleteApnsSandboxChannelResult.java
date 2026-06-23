package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class DeleteApnsSandboxChannelResult implements Serializable {
    private APNSSandboxChannelResponse aPNSSandboxChannelResponse;

    public APNSSandboxChannelResponse getAPNSSandboxChannelResponse() {
        return this.aPNSSandboxChannelResponse;
    }

    public void setAPNSSandboxChannelResponse(APNSSandboxChannelResponse aPNSSandboxChannelResponse) {
        this.aPNSSandboxChannelResponse = aPNSSandboxChannelResponse;
    }

    public DeleteApnsSandboxChannelResult withAPNSSandboxChannelResponse(APNSSandboxChannelResponse aPNSSandboxChannelResponse) {
        this.aPNSSandboxChannelResponse = aPNSSandboxChannelResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
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
        if (obj == null || !(obj instanceof DeleteApnsSandboxChannelResult)) {
            return false;
        }
        DeleteApnsSandboxChannelResult deleteApnsSandboxChannelResult = (DeleteApnsSandboxChannelResult) obj;
        if ((deleteApnsSandboxChannelResult.getAPNSSandboxChannelResponse() == null) ^ (getAPNSSandboxChannelResponse() == null)) {
            return false;
        }
        return deleteApnsSandboxChannelResult.getAPNSSandboxChannelResponse() == null || deleteApnsSandboxChannelResult.getAPNSSandboxChannelResponse().equals(getAPNSSandboxChannelResponse());
    }
}
