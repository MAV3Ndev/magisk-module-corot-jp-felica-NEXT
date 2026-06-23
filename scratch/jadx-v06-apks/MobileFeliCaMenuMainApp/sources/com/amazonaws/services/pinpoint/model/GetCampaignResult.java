package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetCampaignResult implements Serializable {
    private CampaignResponse campaignResponse;

    public CampaignResponse getCampaignResponse() {
        return this.campaignResponse;
    }

    public void setCampaignResponse(CampaignResponse campaignResponse) {
        this.campaignResponse = campaignResponse;
    }

    public GetCampaignResult withCampaignResponse(CampaignResponse campaignResponse) {
        this.campaignResponse = campaignResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getCampaignResponse() != null) {
            sb.append("CampaignResponse: " + getCampaignResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getCampaignResponse() == null ? 0 : getCampaignResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetCampaignResult)) {
            return false;
        }
        GetCampaignResult getCampaignResult = (GetCampaignResult) obj;
        if ((getCampaignResult.getCampaignResponse() == null) ^ (getCampaignResponse() == null)) {
            return false;
        }
        return getCampaignResult.getCampaignResponse() == null || getCampaignResult.getCampaignResponse().equals(getCampaignResponse());
    }
}
