package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class ApplicationSettingsResource implements Serializable {
    private String applicationId;
    private CampaignHook campaignHook;
    private String lastModifiedDate;
    private CampaignLimits limits;
    private QuietTime quietTime;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public ApplicationSettingsResource withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public CampaignHook getCampaignHook() {
        return this.campaignHook;
    }

    public void setCampaignHook(CampaignHook campaignHook) {
        this.campaignHook = campaignHook;
    }

    public ApplicationSettingsResource withCampaignHook(CampaignHook campaignHook) {
        this.campaignHook = campaignHook;
        return this;
    }

    public String getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(String str) {
        this.lastModifiedDate = str;
    }

    public ApplicationSettingsResource withLastModifiedDate(String str) {
        this.lastModifiedDate = str;
        return this;
    }

    public CampaignLimits getLimits() {
        return this.limits;
    }

    public void setLimits(CampaignLimits campaignLimits) {
        this.limits = campaignLimits;
    }

    public ApplicationSettingsResource withLimits(CampaignLimits campaignLimits) {
        this.limits = campaignLimits;
        return this;
    }

    public QuietTime getQuietTime() {
        return this.quietTime;
    }

    public void setQuietTime(QuietTime quietTime) {
        this.quietTime = quietTime;
    }

    public ApplicationSettingsResource withQuietTime(QuietTime quietTime) {
        this.quietTime = quietTime;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getCampaignHook() != null) {
            sb.append("CampaignHook: " + getCampaignHook() + ",");
        }
        if (getLastModifiedDate() != null) {
            sb.append("LastModifiedDate: " + getLastModifiedDate() + ",");
        }
        if (getLimits() != null) {
            sb.append("Limits: " + getLimits() + ",");
        }
        if (getQuietTime() != null) {
            sb.append("QuietTime: " + getQuietTime());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getCampaignHook() == null ? 0 : getCampaignHook().hashCode())) * 31) + (getLastModifiedDate() == null ? 0 : getLastModifiedDate().hashCode())) * 31) + (getLimits() == null ? 0 : getLimits().hashCode())) * 31) + (getQuietTime() != null ? getQuietTime().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ApplicationSettingsResource)) {
            return false;
        }
        ApplicationSettingsResource applicationSettingsResource = (ApplicationSettingsResource) obj;
        if ((applicationSettingsResource.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (applicationSettingsResource.getApplicationId() != null && !applicationSettingsResource.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((applicationSettingsResource.getCampaignHook() == null) ^ (getCampaignHook() == null)) {
            return false;
        }
        if (applicationSettingsResource.getCampaignHook() != null && !applicationSettingsResource.getCampaignHook().equals(getCampaignHook())) {
            return false;
        }
        if ((applicationSettingsResource.getLastModifiedDate() == null) ^ (getLastModifiedDate() == null)) {
            return false;
        }
        if (applicationSettingsResource.getLastModifiedDate() != null && !applicationSettingsResource.getLastModifiedDate().equals(getLastModifiedDate())) {
            return false;
        }
        if ((applicationSettingsResource.getLimits() == null) ^ (getLimits() == null)) {
            return false;
        }
        if (applicationSettingsResource.getLimits() != null && !applicationSettingsResource.getLimits().equals(getLimits())) {
            return false;
        }
        if ((applicationSettingsResource.getQuietTime() == null) ^ (getQuietTime() == null)) {
            return false;
        }
        return applicationSettingsResource.getQuietTime() == null || applicationSettingsResource.getQuietTime().equals(getQuietTime());
    }
}
