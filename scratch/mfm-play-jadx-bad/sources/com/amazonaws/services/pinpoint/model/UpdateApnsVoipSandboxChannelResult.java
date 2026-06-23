package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateApnsVoipSandboxChannelResult implements Serializable {
    private APNSVoipSandboxChannelResponse aPNSVoipSandboxChannelResponse;

    public APNSVoipSandboxChannelResponse getAPNSVoipSandboxChannelResponse() {
        return this.aPNSVoipSandboxChannelResponse;
    }

    public void setAPNSVoipSandboxChannelResponse(APNSVoipSandboxChannelResponse aPNSVoipSandboxChannelResponse) {
        this.aPNSVoipSandboxChannelResponse = aPNSVoipSandboxChannelResponse;
    }

    public UpdateApnsVoipSandboxChannelResult withAPNSVoipSandboxChannelResponse(APNSVoipSandboxChannelResponse aPNSVoipSandboxChannelResponse) {
        this.aPNSVoipSandboxChannelResponse = aPNSVoipSandboxChannelResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getAPNSVoipSandboxChannelResponse() != null) {
            sb.append("APNSVoipSandboxChannelResponse: " + getAPNSVoipSandboxChannelResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getAPNSVoipSandboxChannelResponse() == null ? 0 : getAPNSVoipSandboxChannelResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateApnsVoipSandboxChannelResult)) {
            return false;
        }
        UpdateApnsVoipSandboxChannelResult updateApnsVoipSandboxChannelResult = (UpdateApnsVoipSandboxChannelResult) obj;
        if ((updateApnsVoipSandboxChannelResult.getAPNSVoipSandboxChannelResponse() == null) ^ (getAPNSVoipSandboxChannelResponse() == null)) {
            return false;
        }
        return updateApnsVoipSandboxChannelResult.getAPNSVoipSandboxChannelResponse() == null || updateApnsVoipSandboxChannelResult.getAPNSVoipSandboxChannelResponse().equals(getAPNSVoipSandboxChannelResponse());
    }
}
