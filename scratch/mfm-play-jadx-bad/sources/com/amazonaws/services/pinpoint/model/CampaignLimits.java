package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class CampaignLimits implements Serializable {
    private Integer daily;
    private Integer maximumDuration;
    private Integer messagesPerSecond;
    private Integer total;

    public Integer getDaily() {
        return this.daily;
    }

    public void setDaily(Integer num) {
        this.daily = num;
    }

    public CampaignLimits withDaily(Integer num) {
        this.daily = num;
        return this;
    }

    public Integer getMaximumDuration() {
        return this.maximumDuration;
    }

    public void setMaximumDuration(Integer num) {
        this.maximumDuration = num;
    }

    public CampaignLimits withMaximumDuration(Integer num) {
        this.maximumDuration = num;
        return this;
    }

    public Integer getMessagesPerSecond() {
        return this.messagesPerSecond;
    }

    public void setMessagesPerSecond(Integer num) {
        this.messagesPerSecond = num;
    }

    public CampaignLimits withMessagesPerSecond(Integer num) {
        this.messagesPerSecond = num;
        return this;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer num) {
        this.total = num;
    }

    public CampaignLimits withTotal(Integer num) {
        this.total = num;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getDaily() != null) {
            sb.append("Daily: " + getDaily() + ",");
        }
        if (getMaximumDuration() != null) {
            sb.append("MaximumDuration: " + getMaximumDuration() + ",");
        }
        if (getMessagesPerSecond() != null) {
            sb.append("MessagesPerSecond: " + getMessagesPerSecond() + ",");
        }
        if (getTotal() != null) {
            sb.append("Total: " + getTotal());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((getDaily() == null ? 0 : getDaily().hashCode()) + 31) * 31) + (getMaximumDuration() == null ? 0 : getMaximumDuration().hashCode())) * 31) + (getMessagesPerSecond() == null ? 0 : getMessagesPerSecond().hashCode())) * 31) + (getTotal() != null ? getTotal().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CampaignLimits)) {
            return false;
        }
        CampaignLimits campaignLimits = (CampaignLimits) obj;
        if ((campaignLimits.getDaily() == null) ^ (getDaily() == null)) {
            return false;
        }
        if (campaignLimits.getDaily() != null && !campaignLimits.getDaily().equals(getDaily())) {
            return false;
        }
        if ((campaignLimits.getMaximumDuration() == null) ^ (getMaximumDuration() == null)) {
            return false;
        }
        if (campaignLimits.getMaximumDuration() != null && !campaignLimits.getMaximumDuration().equals(getMaximumDuration())) {
            return false;
        }
        if ((campaignLimits.getMessagesPerSecond() == null) ^ (getMessagesPerSecond() == null)) {
            return false;
        }
        if (campaignLimits.getMessagesPerSecond() != null && !campaignLimits.getMessagesPerSecond().equals(getMessagesPerSecond())) {
            return false;
        }
        if ((campaignLimits.getTotal() == null) ^ (getTotal() == null)) {
            return false;
        }
        return campaignLimits.getTotal() == null || campaignLimits.getTotal().equals(getTotal());
    }
}
