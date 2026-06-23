package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class WriteCampaignRequest implements Serializable {
    private List<WriteTreatmentResource> additionalTreatments;
    private String description;
    private Integer holdoutPercent;
    private CampaignHook hook;
    private Boolean isPaused;
    private CampaignLimits limits;
    private MessageConfiguration messageConfiguration;
    private String name;
    private Schedule schedule;
    private String segmentId;
    private Integer segmentVersion;
    private String treatmentDescription;
    private String treatmentName;

    public List<WriteTreatmentResource> getAdditionalTreatments() {
        return this.additionalTreatments;
    }

    public void setAdditionalTreatments(Collection<WriteTreatmentResource> collection) {
        if (collection == null) {
            this.additionalTreatments = null;
        } else {
            this.additionalTreatments = new ArrayList(collection);
        }
    }

    public WriteCampaignRequest withAdditionalTreatments(WriteTreatmentResource... writeTreatmentResourceArr) {
        if (getAdditionalTreatments() == null) {
            this.additionalTreatments = new ArrayList(writeTreatmentResourceArr.length);
        }
        for (WriteTreatmentResource writeTreatmentResource : writeTreatmentResourceArr) {
            this.additionalTreatments.add(writeTreatmentResource);
        }
        return this;
    }

    public WriteCampaignRequest withAdditionalTreatments(Collection<WriteTreatmentResource> collection) {
        setAdditionalTreatments(collection);
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public WriteCampaignRequest withDescription(String str) {
        this.description = str;
        return this;
    }

    public Integer getHoldoutPercent() {
        return this.holdoutPercent;
    }

    public void setHoldoutPercent(Integer num) {
        this.holdoutPercent = num;
    }

    public WriteCampaignRequest withHoldoutPercent(Integer num) {
        this.holdoutPercent = num;
        return this;
    }

    public CampaignHook getHook() {
        return this.hook;
    }

    public void setHook(CampaignHook campaignHook) {
        this.hook = campaignHook;
    }

    public WriteCampaignRequest withHook(CampaignHook campaignHook) {
        this.hook = campaignHook;
        return this;
    }

    public Boolean isIsPaused() {
        return this.isPaused;
    }

    public Boolean getIsPaused() {
        return this.isPaused;
    }

    public void setIsPaused(Boolean bool) {
        this.isPaused = bool;
    }

    public WriteCampaignRequest withIsPaused(Boolean bool) {
        this.isPaused = bool;
        return this;
    }

    public CampaignLimits getLimits() {
        return this.limits;
    }

    public void setLimits(CampaignLimits campaignLimits) {
        this.limits = campaignLimits;
    }

    public WriteCampaignRequest withLimits(CampaignLimits campaignLimits) {
        this.limits = campaignLimits;
        return this;
    }

    public MessageConfiguration getMessageConfiguration() {
        return this.messageConfiguration;
    }

    public void setMessageConfiguration(MessageConfiguration messageConfiguration) {
        this.messageConfiguration = messageConfiguration;
    }

    public WriteCampaignRequest withMessageConfiguration(MessageConfiguration messageConfiguration) {
        this.messageConfiguration = messageConfiguration;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public WriteCampaignRequest withName(String str) {
        this.name = str;
        return this;
    }

    public Schedule getSchedule() {
        return this.schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public WriteCampaignRequest withSchedule(Schedule schedule) {
        this.schedule = schedule;
        return this;
    }

    public String getSegmentId() {
        return this.segmentId;
    }

    public void setSegmentId(String str) {
        this.segmentId = str;
    }

    public WriteCampaignRequest withSegmentId(String str) {
        this.segmentId = str;
        return this;
    }

    public Integer getSegmentVersion() {
        return this.segmentVersion;
    }

    public void setSegmentVersion(Integer num) {
        this.segmentVersion = num;
    }

    public WriteCampaignRequest withSegmentVersion(Integer num) {
        this.segmentVersion = num;
        return this;
    }

    public String getTreatmentDescription() {
        return this.treatmentDescription;
    }

    public void setTreatmentDescription(String str) {
        this.treatmentDescription = str;
    }

    public WriteCampaignRequest withTreatmentDescription(String str) {
        this.treatmentDescription = str;
        return this;
    }

    public String getTreatmentName() {
        return this.treatmentName;
    }

    public void setTreatmentName(String str) {
        this.treatmentName = str;
    }

    public WriteCampaignRequest withTreatmentName(String str) {
        this.treatmentName = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAdditionalTreatments() != null) {
            sb.append("AdditionalTreatments: " + getAdditionalTreatments() + ",");
        }
        if (getDescription() != null) {
            sb.append("Description: " + getDescription() + ",");
        }
        if (getHoldoutPercent() != null) {
            sb.append("HoldoutPercent: " + getHoldoutPercent() + ",");
        }
        if (getHook() != null) {
            sb.append("Hook: " + getHook() + ",");
        }
        if (getIsPaused() != null) {
            sb.append("IsPaused: " + getIsPaused() + ",");
        }
        if (getLimits() != null) {
            sb.append("Limits: " + getLimits() + ",");
        }
        if (getMessageConfiguration() != null) {
            sb.append("MessageConfiguration: " + getMessageConfiguration() + ",");
        }
        if (getName() != null) {
            sb.append("Name: " + getName() + ",");
        }
        if (getSchedule() != null) {
            sb.append("Schedule: " + getSchedule() + ",");
        }
        if (getSegmentId() != null) {
            sb.append("SegmentId: " + getSegmentId() + ",");
        }
        if (getSegmentVersion() != null) {
            sb.append("SegmentVersion: " + getSegmentVersion() + ",");
        }
        if (getTreatmentDescription() != null) {
            sb.append("TreatmentDescription: " + getTreatmentDescription() + ",");
        }
        if (getTreatmentName() != null) {
            sb.append("TreatmentName: " + getTreatmentName());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((((((((((((((((getAdditionalTreatments() == null ? 0 : getAdditionalTreatments().hashCode()) + 31) * 31) + (getDescription() == null ? 0 : getDescription().hashCode())) * 31) + (getHoldoutPercent() == null ? 0 : getHoldoutPercent().hashCode())) * 31) + (getHook() == null ? 0 : getHook().hashCode())) * 31) + (getIsPaused() == null ? 0 : getIsPaused().hashCode())) * 31) + (getLimits() == null ? 0 : getLimits().hashCode())) * 31) + (getMessageConfiguration() == null ? 0 : getMessageConfiguration().hashCode())) * 31) + (getName() == null ? 0 : getName().hashCode())) * 31) + (getSchedule() == null ? 0 : getSchedule().hashCode())) * 31) + (getSegmentId() == null ? 0 : getSegmentId().hashCode())) * 31) + (getSegmentVersion() == null ? 0 : getSegmentVersion().hashCode())) * 31) + (getTreatmentDescription() == null ? 0 : getTreatmentDescription().hashCode())) * 31) + (getTreatmentName() != null ? getTreatmentName().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof WriteCampaignRequest)) {
            return false;
        }
        WriteCampaignRequest writeCampaignRequest = (WriteCampaignRequest) obj;
        if ((writeCampaignRequest.getAdditionalTreatments() == null) ^ (getAdditionalTreatments() == null)) {
            return false;
        }
        if (writeCampaignRequest.getAdditionalTreatments() != null && !writeCampaignRequest.getAdditionalTreatments().equals(getAdditionalTreatments())) {
            return false;
        }
        if ((writeCampaignRequest.getDescription() == null) ^ (getDescription() == null)) {
            return false;
        }
        if (writeCampaignRequest.getDescription() != null && !writeCampaignRequest.getDescription().equals(getDescription())) {
            return false;
        }
        if ((writeCampaignRequest.getHoldoutPercent() == null) ^ (getHoldoutPercent() == null)) {
            return false;
        }
        if (writeCampaignRequest.getHoldoutPercent() != null && !writeCampaignRequest.getHoldoutPercent().equals(getHoldoutPercent())) {
            return false;
        }
        if ((writeCampaignRequest.getHook() == null) ^ (getHook() == null)) {
            return false;
        }
        if (writeCampaignRequest.getHook() != null && !writeCampaignRequest.getHook().equals(getHook())) {
            return false;
        }
        if ((writeCampaignRequest.getIsPaused() == null) ^ (getIsPaused() == null)) {
            return false;
        }
        if (writeCampaignRequest.getIsPaused() != null && !writeCampaignRequest.getIsPaused().equals(getIsPaused())) {
            return false;
        }
        if ((writeCampaignRequest.getLimits() == null) ^ (getLimits() == null)) {
            return false;
        }
        if (writeCampaignRequest.getLimits() != null && !writeCampaignRequest.getLimits().equals(getLimits())) {
            return false;
        }
        if ((writeCampaignRequest.getMessageConfiguration() == null) ^ (getMessageConfiguration() == null)) {
            return false;
        }
        if (writeCampaignRequest.getMessageConfiguration() != null && !writeCampaignRequest.getMessageConfiguration().equals(getMessageConfiguration())) {
            return false;
        }
        if ((writeCampaignRequest.getName() == null) ^ (getName() == null)) {
            return false;
        }
        if (writeCampaignRequest.getName() != null && !writeCampaignRequest.getName().equals(getName())) {
            return false;
        }
        if ((writeCampaignRequest.getSchedule() == null) ^ (getSchedule() == null)) {
            return false;
        }
        if (writeCampaignRequest.getSchedule() != null && !writeCampaignRequest.getSchedule().equals(getSchedule())) {
            return false;
        }
        if ((writeCampaignRequest.getSegmentId() == null) ^ (getSegmentId() == null)) {
            return false;
        }
        if (writeCampaignRequest.getSegmentId() != null && !writeCampaignRequest.getSegmentId().equals(getSegmentId())) {
            return false;
        }
        if ((writeCampaignRequest.getSegmentVersion() == null) ^ (getSegmentVersion() == null)) {
            return false;
        }
        if (writeCampaignRequest.getSegmentVersion() != null && !writeCampaignRequest.getSegmentVersion().equals(getSegmentVersion())) {
            return false;
        }
        if ((writeCampaignRequest.getTreatmentDescription() == null) ^ (getTreatmentDescription() == null)) {
            return false;
        }
        if (writeCampaignRequest.getTreatmentDescription() != null && !writeCampaignRequest.getTreatmentDescription().equals(getTreatmentDescription())) {
            return false;
        }
        if ((writeCampaignRequest.getTreatmentName() == null) ^ (getTreatmentName() == null)) {
            return false;
        }
        return writeCampaignRequest.getTreatmentName() == null || writeCampaignRequest.getTreatmentName().equals(getTreatmentName());
    }
}
