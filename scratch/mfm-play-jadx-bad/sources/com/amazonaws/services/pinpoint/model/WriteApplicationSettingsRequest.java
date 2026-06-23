package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class WriteApplicationSettingsRequest implements Serializable {
    private CampaignHook campaignHook;
    private Boolean cloudWatchMetricsEnabled;
    private CampaignLimits limits;
    private QuietTime quietTime;

    public CampaignHook getCampaignHook() {
        return this.campaignHook;
    }

    public void setCampaignHook(CampaignHook campaignHook) {
        this.campaignHook = campaignHook;
    }

    public WriteApplicationSettingsRequest withCampaignHook(CampaignHook campaignHook) {
        this.campaignHook = campaignHook;
        return this;
    }

    public Boolean isCloudWatchMetricsEnabled() {
        return this.cloudWatchMetricsEnabled;
    }

    public Boolean getCloudWatchMetricsEnabled() {
        return this.cloudWatchMetricsEnabled;
    }

    public void setCloudWatchMetricsEnabled(Boolean bool) {
        this.cloudWatchMetricsEnabled = bool;
    }

    public WriteApplicationSettingsRequest withCloudWatchMetricsEnabled(Boolean bool) {
        this.cloudWatchMetricsEnabled = bool;
        return this;
    }

    public CampaignLimits getLimits() {
        return this.limits;
    }

    public void setLimits(CampaignLimits campaignLimits) {
        this.limits = campaignLimits;
    }

    public WriteApplicationSettingsRequest withLimits(CampaignLimits campaignLimits) {
        this.limits = campaignLimits;
        return this;
    }

    public QuietTime getQuietTime() {
        return this.quietTime;
    }

    public void setQuietTime(QuietTime quietTime) {
        this.quietTime = quietTime;
    }

    public WriteApplicationSettingsRequest withQuietTime(QuietTime quietTime) {
        this.quietTime = quietTime;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getCampaignHook() != null) {
            sb.append("CampaignHook: " + getCampaignHook() + ",");
        }
        if (getCloudWatchMetricsEnabled() != null) {
            sb.append("CloudWatchMetricsEnabled: " + getCloudWatchMetricsEnabled() + ",");
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
        return (((((((getCampaignHook() == null ? 0 : getCampaignHook().hashCode()) + 31) * 31) + (getCloudWatchMetricsEnabled() == null ? 0 : getCloudWatchMetricsEnabled().hashCode())) * 31) + (getLimits() == null ? 0 : getLimits().hashCode())) * 31) + (getQuietTime() != null ? getQuietTime().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof WriteApplicationSettingsRequest)) {
            return false;
        }
        WriteApplicationSettingsRequest writeApplicationSettingsRequest = (WriteApplicationSettingsRequest) obj;
        if ((writeApplicationSettingsRequest.getCampaignHook() == null) ^ (getCampaignHook() == null)) {
            return false;
        }
        if (writeApplicationSettingsRequest.getCampaignHook() != null && !writeApplicationSettingsRequest.getCampaignHook().equals(getCampaignHook())) {
            return false;
        }
        if ((writeApplicationSettingsRequest.getCloudWatchMetricsEnabled() == null) ^ (getCloudWatchMetricsEnabled() == null)) {
            return false;
        }
        if (writeApplicationSettingsRequest.getCloudWatchMetricsEnabled() != null && !writeApplicationSettingsRequest.getCloudWatchMetricsEnabled().equals(getCloudWatchMetricsEnabled())) {
            return false;
        }
        if ((writeApplicationSettingsRequest.getLimits() == null) ^ (getLimits() == null)) {
            return false;
        }
        if (writeApplicationSettingsRequest.getLimits() != null && !writeApplicationSettingsRequest.getLimits().equals(getLimits())) {
            return false;
        }
        if ((writeApplicationSettingsRequest.getQuietTime() == null) ^ (getQuietTime() == null)) {
            return false;
        }
        return writeApplicationSettingsRequest.getQuietTime() == null || writeApplicationSettingsRequest.getQuietTime().equals(getQuietTime());
    }
}
