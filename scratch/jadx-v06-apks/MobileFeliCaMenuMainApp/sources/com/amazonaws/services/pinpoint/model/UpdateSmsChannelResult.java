package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateSmsChannelResult implements Serializable {
    private SMSChannelResponse sMSChannelResponse;

    public SMSChannelResponse getSMSChannelResponse() {
        return this.sMSChannelResponse;
    }

    public void setSMSChannelResponse(SMSChannelResponse sMSChannelResponse) {
        this.sMSChannelResponse = sMSChannelResponse;
    }

    public UpdateSmsChannelResult withSMSChannelResponse(SMSChannelResponse sMSChannelResponse) {
        this.sMSChannelResponse = sMSChannelResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getSMSChannelResponse() != null) {
            sb.append("SMSChannelResponse: " + getSMSChannelResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getSMSChannelResponse() == null ? 0 : getSMSChannelResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateSmsChannelResult)) {
            return false;
        }
        UpdateSmsChannelResult updateSmsChannelResult = (UpdateSmsChannelResult) obj;
        if ((updateSmsChannelResult.getSMSChannelResponse() == null) ^ (getSMSChannelResponse() == null)) {
            return false;
        }
        return updateSmsChannelResult.getSMSChannelResponse() == null || updateSmsChannelResult.getSMSChannelResponse().equals(getSMSChannelResponse());
    }
}
