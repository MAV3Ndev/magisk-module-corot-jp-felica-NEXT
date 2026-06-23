package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class CampaignResponse implements Serializable {
    private List<TreatmentResource> additionalTreatments;
    private String applicationId;
    private String creationDate;
    private CampaignState defaultState;
    private String description;
    private Integer holdoutPercent;
    private CampaignHook hook;
    private String id;
    private Boolean isPaused;
    private String lastModifiedDate;
    private CampaignLimits limits;
    private MessageConfiguration messageConfiguration;
    private String name;
    private Schedule schedule;
    private String segmentId;
    private Integer segmentVersion;
    private CampaignState state;
    private String treatmentDescription;
    private String treatmentName;
    private Integer version;

    public List<TreatmentResource> getAdditionalTreatments() {
        return this.additionalTreatments;
    }

    public void setAdditionalTreatments(Collection<TreatmentResource> collection) {
        if (collection == null) {
            this.additionalTreatments = null;
        } else {
            this.additionalTreatments = new ArrayList(collection);
        }
    }

    public CampaignResponse withAdditionalTreatments(TreatmentResource... treatmentResourceArr) {
        if (getAdditionalTreatments() == null) {
            this.additionalTreatments = new ArrayList(treatmentResourceArr.length);
        }
        for (TreatmentResource treatmentResource : treatmentResourceArr) {
            this.additionalTreatments.add(treatmentResource);
        }
        return this;
    }

    public CampaignResponse withAdditionalTreatments(Collection<TreatmentResource> collection) {
        setAdditionalTreatments(collection);
        return this;
    }

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public CampaignResponse withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(String str) {
        this.creationDate = str;
    }

    public CampaignResponse withCreationDate(String str) {
        this.creationDate = str;
        return this;
    }

    public CampaignState getDefaultState() {
        return this.defaultState;
    }

    public void setDefaultState(CampaignState campaignState) {
        this.defaultState = campaignState;
    }

    public CampaignResponse withDefaultState(CampaignState campaignState) {
        this.defaultState = campaignState;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public CampaignResponse withDescription(String str) {
        this.description = str;
        return this;
    }

    public Integer getHoldoutPercent() {
        return this.holdoutPercent;
    }

    public void setHoldoutPercent(Integer num) {
        this.holdoutPercent = num;
    }

    public CampaignResponse withHoldoutPercent(Integer num) {
        this.holdoutPercent = num;
        return this;
    }

    public CampaignHook getHook() {
        return this.hook;
    }

    public void setHook(CampaignHook campaignHook) {
        this.hook = campaignHook;
    }

    public CampaignResponse withHook(CampaignHook campaignHook) {
        this.hook = campaignHook;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public CampaignResponse withId(String str) {
        this.id = str;
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

    public CampaignResponse withIsPaused(Boolean bool) {
        this.isPaused = bool;
        return this;
    }

    public String getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(String str) {
        this.lastModifiedDate = str;
    }

    public CampaignResponse withLastModifiedDate(String str) {
        this.lastModifiedDate = str;
        return this;
    }

    public CampaignLimits getLimits() {
        return this.limits;
    }

    public void setLimits(CampaignLimits campaignLimits) {
        this.limits = campaignLimits;
    }

    public CampaignResponse withLimits(CampaignLimits campaignLimits) {
        this.limits = campaignLimits;
        return this;
    }

    public MessageConfiguration getMessageConfiguration() {
        return this.messageConfiguration;
    }

    public void setMessageConfiguration(MessageConfiguration messageConfiguration) {
        this.messageConfiguration = messageConfiguration;
    }

    public CampaignResponse withMessageConfiguration(MessageConfiguration messageConfiguration) {
        this.messageConfiguration = messageConfiguration;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public CampaignResponse withName(String str) {
        this.name = str;
        return this;
    }

    public Schedule getSchedule() {
        return this.schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public CampaignResponse withSchedule(Schedule schedule) {
        this.schedule = schedule;
        return this;
    }

    public String getSegmentId() {
        return this.segmentId;
    }

    public void setSegmentId(String str) {
        this.segmentId = str;
    }

    public CampaignResponse withSegmentId(String str) {
        this.segmentId = str;
        return this;
    }

    public Integer getSegmentVersion() {
        return this.segmentVersion;
    }

    public void setSegmentVersion(Integer num) {
        this.segmentVersion = num;
    }

    public CampaignResponse withSegmentVersion(Integer num) {
        this.segmentVersion = num;
        return this;
    }

    public CampaignState getState() {
        return this.state;
    }

    public void setState(CampaignState campaignState) {
        this.state = campaignState;
    }

    public CampaignResponse withState(CampaignState campaignState) {
        this.state = campaignState;
        return this;
    }

    public String getTreatmentDescription() {
        return this.treatmentDescription;
    }

    public void setTreatmentDescription(String str) {
        this.treatmentDescription = str;
    }

    public CampaignResponse withTreatmentDescription(String str) {
        this.treatmentDescription = str;
        return this;
    }

    public String getTreatmentName() {
        return this.treatmentName;
    }

    public void setTreatmentName(String str) {
        this.treatmentName = str;
    }

    public CampaignResponse withTreatmentName(String str) {
        this.treatmentName = str;
        return this;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer num) {
        this.version = num;
    }

    public CampaignResponse withVersion(Integer num) {
        this.version = num;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getAdditionalTreatments() != null) {
            sb.append("AdditionalTreatments: " + getAdditionalTreatments() + ",");
        }
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getCreationDate() != null) {
            sb.append("CreationDate: " + getCreationDate() + ",");
        }
        if (getDefaultState() != null) {
            sb.append("DefaultState: " + getDefaultState() + ",");
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
        if (getId() != null) {
            sb.append("Id: " + getId() + ",");
        }
        if (getIsPaused() != null) {
            sb.append("IsPaused: " + getIsPaused() + ",");
        }
        if (getLastModifiedDate() != null) {
            sb.append("LastModifiedDate: " + getLastModifiedDate() + ",");
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
        if (getState() != null) {
            sb.append("State: " + getState() + ",");
        }
        if (getTreatmentDescription() != null) {
            sb.append("TreatmentDescription: " + getTreatmentDescription() + ",");
        }
        if (getTreatmentName() != null) {
            sb.append("TreatmentName: " + getTreatmentName() + ",");
        }
        if (getVersion() != null) {
            sb.append("Version: " + getVersion());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((((((((((((((((((((((((((((((getAdditionalTreatments() == null ? 0 : getAdditionalTreatments().hashCode()) + 31) * 31) + (getApplicationId() == null ? 0 : getApplicationId().hashCode())) * 31) + (getCreationDate() == null ? 0 : getCreationDate().hashCode())) * 31) + (getDefaultState() == null ? 0 : getDefaultState().hashCode())) * 31) + (getDescription() == null ? 0 : getDescription().hashCode())) * 31) + (getHoldoutPercent() == null ? 0 : getHoldoutPercent().hashCode())) * 31) + (getHook() == null ? 0 : getHook().hashCode())) * 31) + (getId() == null ? 0 : getId().hashCode())) * 31) + (getIsPaused() == null ? 0 : getIsPaused().hashCode())) * 31) + (getLastModifiedDate() == null ? 0 : getLastModifiedDate().hashCode())) * 31) + (getLimits() == null ? 0 : getLimits().hashCode())) * 31) + (getMessageConfiguration() == null ? 0 : getMessageConfiguration().hashCode())) * 31) + (getName() == null ? 0 : getName().hashCode())) * 31) + (getSchedule() == null ? 0 : getSchedule().hashCode())) * 31) + (getSegmentId() == null ? 0 : getSegmentId().hashCode())) * 31) + (getSegmentVersion() == null ? 0 : getSegmentVersion().hashCode())) * 31) + (getState() == null ? 0 : getState().hashCode())) * 31) + (getTreatmentDescription() == null ? 0 : getTreatmentDescription().hashCode())) * 31) + (getTreatmentName() == null ? 0 : getTreatmentName().hashCode())) * 31) + (getVersion() != null ? getVersion().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CampaignResponse)) {
            return false;
        }
        CampaignResponse campaignResponse = (CampaignResponse) obj;
        if ((campaignResponse.getAdditionalTreatments() == null) ^ (getAdditionalTreatments() == null)) {
            return false;
        }
        if (campaignResponse.getAdditionalTreatments() != null && !campaignResponse.getAdditionalTreatments().equals(getAdditionalTreatments())) {
            return false;
        }
        if ((campaignResponse.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (campaignResponse.getApplicationId() != null && !campaignResponse.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((campaignResponse.getCreationDate() == null) ^ (getCreationDate() == null)) {
            return false;
        }
        if (campaignResponse.getCreationDate() != null && !campaignResponse.getCreationDate().equals(getCreationDate())) {
            return false;
        }
        if ((campaignResponse.getDefaultState() == null) ^ (getDefaultState() == null)) {
            return false;
        }
        if (campaignResponse.getDefaultState() != null && !campaignResponse.getDefaultState().equals(getDefaultState())) {
            return false;
        }
        if ((campaignResponse.getDescription() == null) ^ (getDescription() == null)) {
            return false;
        }
        if (campaignResponse.getDescription() != null && !campaignResponse.getDescription().equals(getDescription())) {
            return false;
        }
        if ((campaignResponse.getHoldoutPercent() == null) ^ (getHoldoutPercent() == null)) {
            return false;
        }
        if (campaignResponse.getHoldoutPercent() != null && !campaignResponse.getHoldoutPercent().equals(getHoldoutPercent())) {
            return false;
        }
        if ((campaignResponse.getHook() == null) ^ (getHook() == null)) {
            return false;
        }
        if (campaignResponse.getHook() != null && !campaignResponse.getHook().equals(getHook())) {
            return false;
        }
        if ((campaignResponse.getId() == null) ^ (getId() == null)) {
            return false;
        }
        if (campaignResponse.getId() != null && !campaignResponse.getId().equals(getId())) {
            return false;
        }
        if ((campaignResponse.getIsPaused() == null) ^ (getIsPaused() == null)) {
            return false;
        }
        if (campaignResponse.getIsPaused() != null && !campaignResponse.getIsPaused().equals(getIsPaused())) {
            return false;
        }
        if ((campaignResponse.getLastModifiedDate() == null) ^ (getLastModifiedDate() == null)) {
            return false;
        }
        if (campaignResponse.getLastModifiedDate() != null && !campaignResponse.getLastModifiedDate().equals(getLastModifiedDate())) {
            return false;
        }
        if ((campaignResponse.getLimits() == null) ^ (getLimits() == null)) {
            return false;
        }
        if (campaignResponse.getLimits() != null && !campaignResponse.getLimits().equals(getLimits())) {
            return false;
        }
        if ((campaignResponse.getMessageConfiguration() == null) ^ (getMessageConfiguration() == null)) {
            return false;
        }
        if (campaignResponse.getMessageConfiguration() != null && !campaignResponse.getMessageConfiguration().equals(getMessageConfiguration())) {
            return false;
        }
        if ((campaignResponse.getName() == null) ^ (getName() == null)) {
            return false;
        }
        if (campaignResponse.getName() != null && !campaignResponse.getName().equals(getName())) {
            return false;
        }
        if ((campaignResponse.getSchedule() == null) ^ (getSchedule() == null)) {
            return false;
        }
        if (campaignResponse.getSchedule() != null && !campaignResponse.getSchedule().equals(getSchedule())) {
            return false;
        }
        if ((campaignResponse.getSegmentId() == null) ^ (getSegmentId() == null)) {
            return false;
        }
        if (campaignResponse.getSegmentId() != null && !campaignResponse.getSegmentId().equals(getSegmentId())) {
            return false;
        }
        if ((campaignResponse.getSegmentVersion() == null) ^ (getSegmentVersion() == null)) {
            return false;
        }
        if (campaignResponse.getSegmentVersion() != null && !campaignResponse.getSegmentVersion().equals(getSegmentVersion())) {
            return false;
        }
        if ((campaignResponse.getState() == null) ^ (getState() == null)) {
            return false;
        }
        if (campaignResponse.getState() != null && !campaignResponse.getState().equals(getState())) {
            return false;
        }
        if ((campaignResponse.getTreatmentDescription() == null) ^ (getTreatmentDescription() == null)) {
            return false;
        }
        if (campaignResponse.getTreatmentDescription() != null && !campaignResponse.getTreatmentDescription().equals(getTreatmentDescription())) {
            return false;
        }
        if ((campaignResponse.getTreatmentName() == null) ^ (getTreatmentName() == null)) {
            return false;
        }
        if (campaignResponse.getTreatmentName() != null && !campaignResponse.getTreatmentName().equals(getTreatmentName())) {
            return false;
        }
        if ((campaignResponse.getVersion() == null) ^ (getVersion() == null)) {
            return false;
        }
        return campaignResponse.getVersion() == null || campaignResponse.getVersion().equals(getVersion());
    }
}
