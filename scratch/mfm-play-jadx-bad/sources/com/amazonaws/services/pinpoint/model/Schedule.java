package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class Schedule implements Serializable {
    private String endTime;
    private CampaignEventFilter eventFilter;
    private String frequency;
    private Boolean isLocalTime;
    private QuietTime quietTime;
    private String startTime;
    private String timezone;

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String str) {
        this.endTime = str;
    }

    public Schedule withEndTime(String str) {
        this.endTime = str;
        return this;
    }

    public CampaignEventFilter getEventFilter() {
        return this.eventFilter;
    }

    public void setEventFilter(CampaignEventFilter campaignEventFilter) {
        this.eventFilter = campaignEventFilter;
    }

    public Schedule withEventFilter(CampaignEventFilter campaignEventFilter) {
        this.eventFilter = campaignEventFilter;
        return this;
    }

    public String getFrequency() {
        return this.frequency;
    }

    public void setFrequency(String str) {
        this.frequency = str;
    }

    public Schedule withFrequency(String str) {
        this.frequency = str;
        return this;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency.toString();
    }

    public Schedule withFrequency(Frequency frequency) {
        this.frequency = frequency.toString();
        return this;
    }

    public Boolean isIsLocalTime() {
        return this.isLocalTime;
    }

    public Boolean getIsLocalTime() {
        return this.isLocalTime;
    }

    public void setIsLocalTime(Boolean bool) {
        this.isLocalTime = bool;
    }

    public Schedule withIsLocalTime(Boolean bool) {
        this.isLocalTime = bool;
        return this;
    }

    public QuietTime getQuietTime() {
        return this.quietTime;
    }

    public void setQuietTime(QuietTime quietTime) {
        this.quietTime = quietTime;
    }

    public Schedule withQuietTime(QuietTime quietTime) {
        this.quietTime = quietTime;
        return this;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String str) {
        this.startTime = str;
    }

    public Schedule withStartTime(String str) {
        this.startTime = str;
        return this;
    }

    public String getTimezone() {
        return this.timezone;
    }

    public void setTimezone(String str) {
        this.timezone = str;
    }

    public Schedule withTimezone(String str) {
        this.timezone = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getEndTime() != null) {
            sb.append("EndTime: " + getEndTime() + ",");
        }
        if (getEventFilter() != null) {
            sb.append("EventFilter: " + getEventFilter() + ",");
        }
        if (getFrequency() != null) {
            sb.append("Frequency: " + getFrequency() + ",");
        }
        if (getIsLocalTime() != null) {
            sb.append("IsLocalTime: " + getIsLocalTime() + ",");
        }
        if (getQuietTime() != null) {
            sb.append("QuietTime: " + getQuietTime() + ",");
        }
        if (getStartTime() != null) {
            sb.append("StartTime: " + getStartTime() + ",");
        }
        if (getTimezone() != null) {
            sb.append("Timezone: " + getTimezone());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((((getEndTime() == null ? 0 : getEndTime().hashCode()) + 31) * 31) + (getEventFilter() == null ? 0 : getEventFilter().hashCode())) * 31) + (getFrequency() == null ? 0 : getFrequency().hashCode())) * 31) + (getIsLocalTime() == null ? 0 : getIsLocalTime().hashCode())) * 31) + (getQuietTime() == null ? 0 : getQuietTime().hashCode())) * 31) + (getStartTime() == null ? 0 : getStartTime().hashCode())) * 31) + (getTimezone() != null ? getTimezone().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Schedule)) {
            return false;
        }
        Schedule schedule = (Schedule) obj;
        if ((schedule.getEndTime() == null) ^ (getEndTime() == null)) {
            return false;
        }
        if (schedule.getEndTime() != null && !schedule.getEndTime().equals(getEndTime())) {
            return false;
        }
        if ((schedule.getEventFilter() == null) ^ (getEventFilter() == null)) {
            return false;
        }
        if (schedule.getEventFilter() != null && !schedule.getEventFilter().equals(getEventFilter())) {
            return false;
        }
        if ((schedule.getFrequency() == null) ^ (getFrequency() == null)) {
            return false;
        }
        if (schedule.getFrequency() != null && !schedule.getFrequency().equals(getFrequency())) {
            return false;
        }
        if ((schedule.getIsLocalTime() == null) ^ (getIsLocalTime() == null)) {
            return false;
        }
        if (schedule.getIsLocalTime() != null && !schedule.getIsLocalTime().equals(getIsLocalTime())) {
            return false;
        }
        if ((schedule.getQuietTime() == null) ^ (getQuietTime() == null)) {
            return false;
        }
        if (schedule.getQuietTime() != null && !schedule.getQuietTime().equals(getQuietTime())) {
            return false;
        }
        if ((schedule.getStartTime() == null) ^ (getStartTime() == null)) {
            return false;
        }
        if (schedule.getStartTime() != null && !schedule.getStartTime().equals(getStartTime())) {
            return false;
        }
        if ((schedule.getTimezone() == null) ^ (getTimezone() == null)) {
            return false;
        }
        return schedule.getTimezone() == null || schedule.getTimezone().equals(getTimezone());
    }
}
