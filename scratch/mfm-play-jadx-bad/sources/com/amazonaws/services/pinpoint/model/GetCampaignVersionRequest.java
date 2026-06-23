package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetCampaignVersionRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private String campaignId;
    private String version;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public GetCampaignVersionRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getCampaignId() {
        return this.campaignId;
    }

    public void setCampaignId(String str) {
        this.campaignId = str;
    }

    public GetCampaignVersionRequest withCampaignId(String str) {
        this.campaignId = str;
        return this;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public GetCampaignVersionRequest withVersion(String str) {
        this.version = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getCampaignId() != null) {
            sb.append("CampaignId: " + getCampaignId() + ",");
        }
        if (getVersion() != null) {
            sb.append("Version: " + getVersion());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getCampaignId() == null ? 0 : getCampaignId().hashCode())) * 31) + (getVersion() != null ? getVersion().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetCampaignVersionRequest)) {
            return false;
        }
        GetCampaignVersionRequest getCampaignVersionRequest = (GetCampaignVersionRequest) obj;
        if ((getCampaignVersionRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (getCampaignVersionRequest.getApplicationId() != null && !getCampaignVersionRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((getCampaignVersionRequest.getCampaignId() == null) ^ (getCampaignId() == null)) {
            return false;
        }
        if (getCampaignVersionRequest.getCampaignId() != null && !getCampaignVersionRequest.getCampaignId().equals(getCampaignId())) {
            return false;
        }
        if ((getCampaignVersionRequest.getVersion() == null) ^ (getVersion() == null)) {
            return false;
        }
        return getCampaignVersionRequest.getVersion() == null || getCampaignVersionRequest.getVersion().equals(getVersion());
    }
}
