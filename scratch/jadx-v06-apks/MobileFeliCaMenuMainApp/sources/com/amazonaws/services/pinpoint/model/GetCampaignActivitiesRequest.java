package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetCampaignActivitiesRequest extends AmazonWebServiceRequest implements Serializable {
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

    public GetCampaignActivitiesRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getCampaignId() {
        return this.campaignId;
    }

    public void setCampaignId(String str) {
        this.campaignId = str;
    }

    public GetCampaignActivitiesRequest withCampaignId(String str) {
        this.campaignId = str;
        return this;
    }

    public String getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(String str) {
        this.pageSize = str;
    }

    public GetCampaignActivitiesRequest withPageSize(String str) {
        this.pageSize = str;
        return this;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public GetCampaignActivitiesRequest withToken(String str) {
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
        if (obj == null || !(obj instanceof GetCampaignActivitiesRequest)) {
            return false;
        }
        GetCampaignActivitiesRequest getCampaignActivitiesRequest = (GetCampaignActivitiesRequest) obj;
        if ((getCampaignActivitiesRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (getCampaignActivitiesRequest.getApplicationId() != null && !getCampaignActivitiesRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((getCampaignActivitiesRequest.getCampaignId() == null) ^ (getCampaignId() == null)) {
            return false;
        }
        if (getCampaignActivitiesRequest.getCampaignId() != null && !getCampaignActivitiesRequest.getCampaignId().equals(getCampaignId())) {
            return false;
        }
        if ((getCampaignActivitiesRequest.getPageSize() == null) ^ (getPageSize() == null)) {
            return false;
        }
        if (getCampaignActivitiesRequest.getPageSize() != null && !getCampaignActivitiesRequest.getPageSize().equals(getPageSize())) {
            return false;
        }
        if ((getCampaignActivitiesRequest.getToken() == null) ^ (getToken() == null)) {
            return false;
        }
        return getCampaignActivitiesRequest.getToken() == null || getCampaignActivitiesRequest.getToken().equals(getToken());
    }
}
