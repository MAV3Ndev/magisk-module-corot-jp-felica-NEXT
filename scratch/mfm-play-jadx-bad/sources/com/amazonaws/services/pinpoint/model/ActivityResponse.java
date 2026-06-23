package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class ActivityResponse implements Serializable {
    private String applicationId;
    private String campaignId;
    private String end;
    private String id;
    private String result;
    private String scheduledStart;
    private String start;
    private String state;
    private Integer successfulEndpointCount;
    private Integer timezonesCompletedCount;
    private Integer timezonesTotalCount;
    private Integer totalEndpointCount;
    private String treatmentId;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public ActivityResponse withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getCampaignId() {
        return this.campaignId;
    }

    public void setCampaignId(String str) {
        this.campaignId = str;
    }

    public ActivityResponse withCampaignId(String str) {
        this.campaignId = str;
        return this;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String str) {
        this.end = str;
    }

    public ActivityResponse withEnd(String str) {
        this.end = str;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public ActivityResponse withId(String str) {
        this.id = str;
        return this;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String str) {
        this.result = str;
    }

    public ActivityResponse withResult(String str) {
        this.result = str;
        return this;
    }

    public String getScheduledStart() {
        return this.scheduledStart;
    }

    public void setScheduledStart(String str) {
        this.scheduledStart = str;
    }

    public ActivityResponse withScheduledStart(String str) {
        this.scheduledStart = str;
        return this;
    }

    public String getStart() {
        return this.start;
    }

    public void setStart(String str) {
        this.start = str;
    }

    public ActivityResponse withStart(String str) {
        this.start = str;
        return this;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String str) {
        this.state = str;
    }

    public ActivityResponse withState(String str) {
        this.state = str;
        return this;
    }

    public Integer getSuccessfulEndpointCount() {
        return this.successfulEndpointCount;
    }

    public void setSuccessfulEndpointCount(Integer num) {
        this.successfulEndpointCount = num;
    }

    public ActivityResponse withSuccessfulEndpointCount(Integer num) {
        this.successfulEndpointCount = num;
        return this;
    }

    public Integer getTimezonesCompletedCount() {
        return this.timezonesCompletedCount;
    }

    public void setTimezonesCompletedCount(Integer num) {
        this.timezonesCompletedCount = num;
    }

    public ActivityResponse withTimezonesCompletedCount(Integer num) {
        this.timezonesCompletedCount = num;
        return this;
    }

    public Integer getTimezonesTotalCount() {
        return this.timezonesTotalCount;
    }

    public void setTimezonesTotalCount(Integer num) {
        this.timezonesTotalCount = num;
    }

    public ActivityResponse withTimezonesTotalCount(Integer num) {
        this.timezonesTotalCount = num;
        return this;
    }

    public Integer getTotalEndpointCount() {
        return this.totalEndpointCount;
    }

    public void setTotalEndpointCount(Integer num) {
        this.totalEndpointCount = num;
    }

    public ActivityResponse withTotalEndpointCount(Integer num) {
        this.totalEndpointCount = num;
        return this;
    }

    public String getTreatmentId() {
        return this.treatmentId;
    }

    public void setTreatmentId(String str) {
        this.treatmentId = str;
    }

    public ActivityResponse withTreatmentId(String str) {
        this.treatmentId = str;
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
        if (getEnd() != null) {
            sb.append("End: " + getEnd() + ",");
        }
        if (getId() != null) {
            sb.append("Id: " + getId() + ",");
        }
        if (getResult() != null) {
            sb.append("Result: " + getResult() + ",");
        }
        if (getScheduledStart() != null) {
            sb.append("ScheduledStart: " + getScheduledStart() + ",");
        }
        if (getStart() != null) {
            sb.append("Start: " + getStart() + ",");
        }
        if (getState() != null) {
            sb.append("State: " + getState() + ",");
        }
        if (getSuccessfulEndpointCount() != null) {
            sb.append("SuccessfulEndpointCount: " + getSuccessfulEndpointCount() + ",");
        }
        if (getTimezonesCompletedCount() != null) {
            sb.append("TimezonesCompletedCount: " + getTimezonesCompletedCount() + ",");
        }
        if (getTimezonesTotalCount() != null) {
            sb.append("TimezonesTotalCount: " + getTimezonesTotalCount() + ",");
        }
        if (getTotalEndpointCount() != null) {
            sb.append("TotalEndpointCount: " + getTotalEndpointCount() + ",");
        }
        if (getTreatmentId() != null) {
            sb.append("TreatmentId: " + getTreatmentId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((((((((((((((((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getCampaignId() == null ? 0 : getCampaignId().hashCode())) * 31) + (getEnd() == null ? 0 : getEnd().hashCode())) * 31) + (getId() == null ? 0 : getId().hashCode())) * 31) + (getResult() == null ? 0 : getResult().hashCode())) * 31) + (getScheduledStart() == null ? 0 : getScheduledStart().hashCode())) * 31) + (getStart() == null ? 0 : getStart().hashCode())) * 31) + (getState() == null ? 0 : getState().hashCode())) * 31) + (getSuccessfulEndpointCount() == null ? 0 : getSuccessfulEndpointCount().hashCode())) * 31) + (getTimezonesCompletedCount() == null ? 0 : getTimezonesCompletedCount().hashCode())) * 31) + (getTimezonesTotalCount() == null ? 0 : getTimezonesTotalCount().hashCode())) * 31) + (getTotalEndpointCount() == null ? 0 : getTotalEndpointCount().hashCode())) * 31) + (getTreatmentId() != null ? getTreatmentId().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ActivityResponse)) {
            return false;
        }
        ActivityResponse activityResponse = (ActivityResponse) obj;
        if ((activityResponse.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (activityResponse.getApplicationId() != null && !activityResponse.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((activityResponse.getCampaignId() == null) ^ (getCampaignId() == null)) {
            return false;
        }
        if (activityResponse.getCampaignId() != null && !activityResponse.getCampaignId().equals(getCampaignId())) {
            return false;
        }
        if ((activityResponse.getEnd() == null) ^ (getEnd() == null)) {
            return false;
        }
        if (activityResponse.getEnd() != null && !activityResponse.getEnd().equals(getEnd())) {
            return false;
        }
        if ((activityResponse.getId() == null) ^ (getId() == null)) {
            return false;
        }
        if (activityResponse.getId() != null && !activityResponse.getId().equals(getId())) {
            return false;
        }
        if ((activityResponse.getResult() == null) ^ (getResult() == null)) {
            return false;
        }
        if (activityResponse.getResult() != null && !activityResponse.getResult().equals(getResult())) {
            return false;
        }
        if ((activityResponse.getScheduledStart() == null) ^ (getScheduledStart() == null)) {
            return false;
        }
        if (activityResponse.getScheduledStart() != null && !activityResponse.getScheduledStart().equals(getScheduledStart())) {
            return false;
        }
        if ((activityResponse.getStart() == null) ^ (getStart() == null)) {
            return false;
        }
        if (activityResponse.getStart() != null && !activityResponse.getStart().equals(getStart())) {
            return false;
        }
        if ((activityResponse.getState() == null) ^ (getState() == null)) {
            return false;
        }
        if (activityResponse.getState() != null && !activityResponse.getState().equals(getState())) {
            return false;
        }
        if ((activityResponse.getSuccessfulEndpointCount() == null) ^ (getSuccessfulEndpointCount() == null)) {
            return false;
        }
        if (activityResponse.getSuccessfulEndpointCount() != null && !activityResponse.getSuccessfulEndpointCount().equals(getSuccessfulEndpointCount())) {
            return false;
        }
        if ((activityResponse.getTimezonesCompletedCount() == null) ^ (getTimezonesCompletedCount() == null)) {
            return false;
        }
        if (activityResponse.getTimezonesCompletedCount() != null && !activityResponse.getTimezonesCompletedCount().equals(getTimezonesCompletedCount())) {
            return false;
        }
        if ((activityResponse.getTimezonesTotalCount() == null) ^ (getTimezonesTotalCount() == null)) {
            return false;
        }
        if (activityResponse.getTimezonesTotalCount() != null && !activityResponse.getTimezonesTotalCount().equals(getTimezonesTotalCount())) {
            return false;
        }
        if ((activityResponse.getTotalEndpointCount() == null) ^ (getTotalEndpointCount() == null)) {
            return false;
        }
        if (activityResponse.getTotalEndpointCount() != null && !activityResponse.getTotalEndpointCount().equals(getTotalEndpointCount())) {
            return false;
        }
        if ((activityResponse.getTreatmentId() == null) ^ (getTreatmentId() == null)) {
            return false;
        }
        return activityResponse.getTreatmentId() == null || activityResponse.getTreatmentId().equals(getTreatmentId());
    }
}
