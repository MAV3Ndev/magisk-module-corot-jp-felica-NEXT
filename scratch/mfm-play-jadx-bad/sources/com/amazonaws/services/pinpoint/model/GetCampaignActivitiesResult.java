package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetCampaignActivitiesResult implements Serializable {
    private ActivitiesResponse activitiesResponse;

    public ActivitiesResponse getActivitiesResponse() {
        return this.activitiesResponse;
    }

    public void setActivitiesResponse(ActivitiesResponse activitiesResponse) {
        this.activitiesResponse = activitiesResponse;
    }

    public GetCampaignActivitiesResult withActivitiesResponse(ActivitiesResponse activitiesResponse) {
        this.activitiesResponse = activitiesResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getActivitiesResponse() != null) {
            sb.append("ActivitiesResponse: " + getActivitiesResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getActivitiesResponse() == null ? 0 : getActivitiesResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetCampaignActivitiesResult)) {
            return false;
        }
        GetCampaignActivitiesResult getCampaignActivitiesResult = (GetCampaignActivitiesResult) obj;
        if ((getCampaignActivitiesResult.getActivitiesResponse() == null) ^ (getActivitiesResponse() == null)) {
            return false;
        }
        return getCampaignActivitiesResult.getActivitiesResponse() == null || getCampaignActivitiesResult.getActivitiesResponse().equals(getActivitiesResponse());
    }
}
