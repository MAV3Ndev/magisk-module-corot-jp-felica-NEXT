package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetCampaignVersionsRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private String campaignId;
    private String pageSize;
    private String token;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public GetCampaignVersionsRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getCampaignId() {
        return this.campaignId;
    }

    public void setCampaignId(String str) {
        this.campaignId = str;
    }

    public GetCampaignVersionsRequest withCampaignId(String str) {
        this.campaignId = str;
        return this;
    }

    public String getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(String str) {
        this.pageSize = str;
    }

    public GetCampaignVersionsRequest withPageSize(String str) {
        this.pageSize = str;
        return this;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public GetCampaignVersionsRequest withToken(String str) {
        this.token = str;
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
        if (getPageSize() != null) {
            sb.append("PageSize: " + getPageSize() + ",");
        }
        if (getToken() != null) {
            sb.append("Token: " + getToken());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getCampaignId() == null ? 0 : getCampaignId().hashCode())) * 31) + (getPageSize() == null ? 0 : getPageSize().hashCode())) * 31) + (getToken() != null ? getToken().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetCampaignVersionsRequest)) {
            return false;
        }
        GetCampaignVersionsRequest getCampaignVersionsRequest = (GetCampaignVersionsRequest) obj;
        if ((getCampaignVersionsRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (getCampaignVersionsRequest.getApplicationId() != null && !getCampaignVersionsRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((getCampaignVersionsRequest.getCampaignId() == null) ^ (getCampaignId() == null)) {
            return false;
        }
        if (getCampaignVersionsRequest.getCampaignId() != null && !getCampaignVersionsRequest.getCampaignId().equals(getCampaignId())) {
            return false;
        }
        if ((getCampaignVersionsRequest.getPageSize() == null) ^ (getPageSize() == null)) {
            return false;
        }
        if (getCampaignVersionsRequest.getPageSize() != null && !getCampaignVersionsRequest.getPageSize().equals(getPageSize())) {
            return false;
        }
        if ((getCampaignVersionsRequest.getToken() == null) ^ (getToken() == null)) {
            return false;
        }
        return getCampaignVersionsRequest.getToken() == null || getCampaignVersionsRequest.getToken().equals(getToken());
    }
}
