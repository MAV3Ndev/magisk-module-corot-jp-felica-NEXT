package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateCampaignRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private String campaignId;
    private WriteCampaignRequest writeCampaignRequest;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public UpdateCampaignRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getCampaignId() {
        return this.campaignId;
    }

    public void setCampaignId(String str) {
        this.campaignId = str;
    }

    public UpdateCampaignRequest withCampaignId(String str) {
        this.campaignId = str;
        return this;
    }

    public WriteCampaignRequest getWriteCampaignRequest() {
        return this.writeCampaignRequest;
    }

    public void setWriteCampaignRequest(WriteCampaignRequest writeCampaignRequest) {
        this.writeCampaignRequest = writeCampaignRequest;
    }

    public UpdateCampaignRequest withWriteCampaignRequest(WriteCampaignRequest writeCampaignRequest) {
        this.writeCampaignRequest = writeCampaignRequest;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getCampaignId() != null) {
            sb.append("CampaignId: " + getCampaignId() + ",");
        }
        if (getWriteCampaignRequest() != null) {
            sb.append("WriteCampaignRequest: " + getWriteCampaignRequest());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getCampaignId() == null ? 0 : getCampaignId().hashCode())) * 31) + (getWriteCampaignRequest() != null ? getWriteCampaignRequest().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateCampaignRequest)) {
            return false;
        }
        UpdateCampaignRequest updateCampaignRequest = (UpdateCampaignRequest) obj;
        if ((updateCampaignRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (updateCampaignRequest.getApplicationId() != null && !updateCampaignRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((updateCampaignRequest.getCampaignId() == null) ^ (getCampaignId() == null)) {
            return false;
        }
        if (updateCampaignRequest.getCampaignId() != null && !updateCampaignRequest.getCampaignId().equals(getCampaignId())) {
            return false;
        }
        if ((updateCampaignRequest.getWriteCampaignRequest() == null) ^ (getWriteCampaignRequest() == null)) {
            return false;
        }
        return updateCampaignRequest.getWriteCampaignRequest() == null || updateCampaignRequest.getWriteCampaignRequest().equals(getWriteCampaignRequest());
    }
}
