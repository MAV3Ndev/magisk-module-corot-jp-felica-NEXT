package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class DeleteAdmChannelResult implements Serializable {
    private ADMChannelResponse aDMChannelResponse;

    public ADMChannelResponse getADMChannelResponse() {
        return this.aDMChannelResponse;
    }

    public void setADMChannelResponse(ADMChannelResponse aDMChannelResponse) {
        this.aDMChannelResponse = aDMChannelResponse;
    }

    public DeleteAdmChannelResult withADMChannelResponse(ADMChannelResponse aDMChannelResponse) {
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
        if (obj == null || !(obj instanceof DeleteAdmChannelResult)) {
            return false;
        }
        DeleteAdmChannelResult deleteAdmChannelResult = (DeleteAdmChannelResult) obj;
        if ((deleteAdmChannelResult.getADMChannelResponse() == null) ^ (getADMChannelResponse() == null)) {
            return false;
        }
        return deleteAdmChannelResult.getADMChannelResponse() == null || deleteAdmChannelResult.getADMChannelResponse().equals(getADMChannelResponse());
    }
}
