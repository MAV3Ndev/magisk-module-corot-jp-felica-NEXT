package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetCampaignRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private String campaignId;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public GetCampaignRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getCampaignId() {
        return this.campaignId;
    }

    public void setCampaignId(String str) {
        this.campaignId = str;
    }

    public GetCampaignRequest withCampaignId(String str) {
        this.campaignId = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getCampaignId() != null) {
            sb.append("CampaignId: " + getCampaignId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getCampaignId() != null ? getCampaignId().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetCampaignRequest)) {
            return false;
        }
        GetCampaignRequest getCampaignRequest = (GetCampaignRequest) obj;
        if ((getCampaignRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (getCampaignRequest.getApplicationId() != null && !getCampaignRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((getCampaignRequest.getCampaignId() == null) ^ (getCampaignId() == null)) {
            return false;
        }
        return getCampaignRequest.getCampaignId() == null || getCampaignRequest.getCampaignId().equals(getCampaignId());
    }
}
