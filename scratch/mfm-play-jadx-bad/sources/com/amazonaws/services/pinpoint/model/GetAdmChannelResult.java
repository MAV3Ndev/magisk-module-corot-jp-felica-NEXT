package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetAdmChannelResult implements Serializable {
    private ADMChannelResponse aDMChannelResponse;

    public ADMChannelResponse getADMChannelResponse() {
        return this.aDMChannelResponse;
    }

    public void setADMChannelResponse(ADMChannelResponse aDMChannelResponse) {
        this.aDMChannelResponse = aDMChannelResponse;
    }

    public GetAdmChannelResult withADMChannelResponse(ADMChannelResponse aDMChannelResponse) {
        this.aDMChannelResponse = aDMChannelResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getADMChannelResponse() != null) {
            sb.append("ADMChannelResponse: " + getADMChannelResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getADMChannelResponse() == null ? 0 : getADMChannelResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetAdmChannelResult)) {
            return false;
        }
        GetAdmChannelResult getAdmChannelResult = (GetAdmChannelResult) obj;
        if ((getAdmChannelResult.getADMChannelResponse() == null) ^ (getADMChannelResponse() == null)) {
            return false;
        }
        return getAdmChannelResult.getADMChannelResponse() == null || getAdmChannelResult.getADMChannelResponse().equals(getADMChannelResponse());
    }
}
