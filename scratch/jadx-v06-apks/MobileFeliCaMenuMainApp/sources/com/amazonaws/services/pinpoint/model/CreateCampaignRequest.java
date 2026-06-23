package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class CreateCampaignRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private WriteCampaignRequest writeCampaignRequest;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public CreateCampaignRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public WriteCampaignRequest getWriteCampaignRequest() {
        return this.writeCampaignRequest;
    }

    public void setWriteCampaignRequest(WriteCampaignRequest writeCampaignRequest) {
        this.writeCampaignRequest = writeCampaignRequest;
    }

    public CreateCampaignRequest withWriteCampaignRequest(WriteCampaignRequest writeCampaignRequest) {
        this.writeCampaignRequest = writeCampaignRequest;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getWriteCampaignRequest() != null) {
            sb.append("WriteCampaignRequest: " + getWriteCampaignRequest());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getWriteCampaignRequest() != null ? getWriteCampaignRequest().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CreateCampaignRequest)) {
            return false;
        }
        CreateCampaignRequest createCampaignRequest = (CreateCampaignRequest) obj;
        if ((createCampaignRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (createCampaignRequest.getApplicationId() != null && !createCampaignRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((createCampaignRequest.getWriteCampaignRequest() == null) ^ (getWriteCampaignRequest() == null)) {
            return false;
        }
        return createCampaignRequest.getWriteCampaignRequest() == null || createCampaignRequest.getWriteCampaignRequest().equals(getWriteCampaignRequest());
    }
}
