package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetCampaignsResult implements Serializable {
    private CampaignsResponse campaignsResponse;

    public CampaignsResponse getCampaignsResponse() {
        return this.campaignsResponse;
    }

    public void setCampaignsResponse(CampaignsResponse campaignsResponse) {
        this.campaignsResponse = campaignsResponse;
    }

    public GetCampaignsResult withCampaignsResponse(CampaignsResponse campaignsResponse) {
        this.campaignsResponse = campaignsResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getCampaignsResponse() != null) {
            sb.append("CampaignsResponse: " + getCampaignsResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getCampaignsResponse() == null ? 0 : getCampaignsResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetCampaignsResult)) {
            return false;
        }
        GetCampaignsResult getCampaignsResult = (GetCampaignsResult) obj;
        if ((getCampaignsResult.getCampaignsResponse() == null) ^ (getCampaignsResponse() == null)) {
            return false;
        }
        return getCampaignsResult.getCampaignsResponse() == null || getCampaignsResult.getCampaignsResponse().equals(getCampaignsResponse());
    }
}
