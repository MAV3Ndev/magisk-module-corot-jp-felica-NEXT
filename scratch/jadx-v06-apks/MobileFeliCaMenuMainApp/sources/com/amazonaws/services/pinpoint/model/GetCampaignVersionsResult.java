package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetCampaignVersionsResult implements Serializable {
    private CampaignsResponse campaignsResponse;

    public CampaignsResponse getCampaignsResponse() {
        return this.campaignsResponse;
    }

    public void setCampaignsResponse(CampaignsResponse campaignsResponse) {
        this.campaignsResponse = campaignsResponse;
    }

    public GetCampaignVersionsResult withCampaignsResponse(CampaignsResponse campaignsResponse) {
        this.campaignsResponse = campaignsResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
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
        if (obj == null || !(obj instanceof GetCampaignVersionsResult)) {
            return false;
        }
        GetCampaignVersionsResult getCampaignVersionsResult = (GetCampaignVersionsResult) obj;
        if ((getCampaignVersionsResult.getCampaignsResponse() == null) ^ (getCampaignsResponse() == null)) {
            return false;
        }
        return getCampaignVersionsResult.getCampaignsResponse() == null || getCampaignVersionsResult.getCampaignsResponse().equals(getCampaignsResponse());
    }
}
