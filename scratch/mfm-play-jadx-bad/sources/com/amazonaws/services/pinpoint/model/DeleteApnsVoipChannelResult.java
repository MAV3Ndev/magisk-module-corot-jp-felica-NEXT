package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class DeleteApnsVoipChannelResult implements Serializable {
    private APNSVoipChannelResponse aPNSVoipChannelResponse;

    public APNSVoipChannelResponse getAPNSVoipChannelResponse() {
        return this.aPNSVoipChannelResponse;
    }

    public void setAPNSVoipChannelResponse(APNSVoipChannelResponse aPNSVoipChannelResponse) {
        this.aPNSVoipChannelResponse = aPNSVoipChannelResponse;
    }

    public DeleteApnsVoipChannelResult withAPNSVoipChannelResponse(APNSVoipChannelResponse aPNSVoipChannelResponse) {
        this.aPNSVoipChannelResponse = aPNSVoipChannelResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getAPNSVoipChannelResponse() != null) {
            sb.append("APNSVoipChannelResponse: " + getAPNSVoipChannelResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getAPNSVoipChannelResponse() == null ? 0 : getAPNSVoipChannelResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DeleteApnsVoipChannelResult)) {
            return false;
        }
        DeleteApnsVoipChannelResult deleteApnsVoipChannelResult = (DeleteApnsVoipChannelResult) obj;
        if ((deleteApnsVoipChannelResult.getAPNSVoipChannelResponse() == null) ^ (getAPNSVoipChannelResponse() == null)) {
            return false;
        }
        return deleteApnsVoipChannelResult.getAPNSVoipChannelResponse() == null || deleteApnsVoipChannelResult.getAPNSVoipChannelResponse().equals(getAPNSVoipChannelResponse());
    }
}
