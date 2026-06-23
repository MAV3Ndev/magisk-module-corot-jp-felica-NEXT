package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class CampaignState implements Serializable {
    private String campaignStatus;

    public String getCampaignStatus() {
        return this.campaignStatus;
    }

    public void setCampaignStatus(String str) {
        this.campaignStatus = str;
    }

    public CampaignState withCampaignStatus(String str) {
        this.campaignStatus = str;
        return this;
    }

    public void setCampaignStatus(CampaignStatus campaignStatus) {
        this.campaignStatus = campaignStatus.toString();
    }

    public CampaignState withCampaignStatus(CampaignStatus campaignStatus) {
        this.campaignStatus = campaignStatus.toString();
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getCampaignStatus() != null) {
            sb.append("CampaignStatus: " + getCampaignStatus());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getCampaignStatus() == null ? 0 : getCampaignStatus().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CampaignState)) {
            return false;
        }
        CampaignState campaignState = (CampaignState) obj;
        if ((campaignState.getCampaignStatus() == null) ^ (getCampaignStatus() == null)) {
            return false;
        }
        return campaignState.getCampaignStatus() == null || campaignState.getCampaignStatus().equals(getCampaignStatus());
    }
}
