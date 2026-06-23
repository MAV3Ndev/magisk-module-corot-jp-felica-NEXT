package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateCampaignResult implements Serializable {
    private CampaignResponse campaignResponse;

    public CampaignResponse getCampaignResponse() {
        return this.campaignResponse;
    }

    public void setCampaignResponse(CampaignResponse campaignResponse) {
        this.campaignResponse = campaignResponse;
    }

    public UpdateCampaignResult withCampaignResponse(CampaignResponse campaignResponse) {
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
        if (obj == null || !(obj instanceof UpdateCampaignResult)) {
            return false;
        }
        UpdateCampaignResult updateCampaignResult = (UpdateCampaignResult) obj;
        if ((updateCampaignResult.getCampaignResponse() == null) ^ (getCampaignResponse() == null)) {
            return false;
        }
        return updateCampaignResult.getCampaignResponse() == null || updateCampaignResult.getCampaignResponse().equals(getCampaignResponse());
    }
}
