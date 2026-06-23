package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class TreatmentResource implements Serializable {
    private String id;
    private MessageConfiguration messageConfiguration;
    private Schedule schedule;
    private Integer sizePercent;
    private CampaignState state;
    private String treatmentDescription;
    private String treatmentName;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public TreatmentResource withId(String str) {
        this.id = str;
        return this;
    }

    public MessageConfiguration getMessageConfiguration() {
        return this.messageConfiguration;
    }

    public void setMessageConfiguration(MessageConfiguration messageConfiguration) {
        this.messageConfiguration = messageConfiguration;
    }

    public TreatmentResource withMessageConfiguration(MessageConfiguration messageConfiguration) {
        this.messageConfiguration = messageConfiguration;
        return this;
    }

    public Schedule getSchedule() {
        return this.schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public TreatmentResource withSchedule(Schedule schedule) {
        this.schedule = schedule;
        return this;
    }

    public Integer getSizePercent() {
        return this.sizePercent;
    }

    public void setSizePercent(Integer num) {
        this.sizePercent = num;
    }

    public TreatmentResource withSizePercent(Integer num) {
        this.sizePercent = num;
        return this;
    }

    public CampaignState getState() {
        return this.state;
    }

    public void setState(CampaignState campaignState) {
        this.state = campaignState;
    }

    public TreatmentResource withState(CampaignState campaignState) {
        this.state = campaignState;
        return this;
    }

    public String getTreatmentDescription() {
        return this.treatmentDescription;
    }

    public void setTreatmentDescription(String str) {
        this.treatmentDescription = str;
    }

    public TreatmentResource withTreatmentDescription(String str) {
        this.treatmentDescription = str;
        return this;
    }

    public String getTreatmentName() {
        return this.treatmentName;
    }

    public void setTreatmentName(String str) {
        this.treatmentName = str;
    }

    public TreatmentResource withTreatmentName(String str) {
        this.treatmentName = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getId() != null) {
            sb.append("Id: " + getId() + ",");
        }
        if (getMessageConfiguration() != null) {
            sb.append("MessageConfiguration: " + getMessageConfiguration() + ",");
        }
        if (getSchedule() != null) {
            sb.append("Schedule: " + getSchedule() + ",");
        }
        if (getSizePercent() != null) {
            sb.append("SizePercent: " + getSizePercent() + ",");
        }
        if (getState() != null) {
            sb.append("State: " + getState() + ",");
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
        return (((((((((((((getId() == null ? 0 : getId().hashCode()) + 31) * 31) + (getMessageConfiguration() == null ? 0 : getMessageConfiguration().hashCode())) * 31) + (getSchedule() == null ? 0 : getSchedule().hashCode())) * 31) + (getSizePercent() == null ? 0 : getSizePercent().hashCode())) * 31) + (getState() == null ? 0 : getState().hashCode())) * 31) + (getTreatmentDescription() == null ? 0 : getTreatmentDescription().hashCode())) * 31) + (getTreatmentName() != null ? getTreatmentName().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof TreatmentResource)) {
            return false;
        }
        TreatmentResource treatmentResource = (TreatmentResource) obj;
        if ((treatmentResource.getId() == null) ^ (getId() == null)) {
            return false;
        }
        if (treatmentResource.getId() != null && !treatmentResource.getId().equals(getId())) {
            return false;
        }
        if ((treatmentResource.getMessageConfiguration() == null) ^ (getMessageConfiguration() == null)) {
            return false;
        }
        if (treatmentResource.getMessageConfiguration() != null && !treatmentResource.getMessageConfiguration().equals(getMessageConfiguration())) {
            return false;
        }
        if ((treatmentResource.getSchedule() == null) ^ (getSchedule() == null)) {
            return false;
        }
        if (treatmentResource.getSchedule() != null && !treatmentResource.getSchedule().equals(getSchedule())) {
            return false;
        }
        if ((treatmentResource.getSizePercent() == null) ^ (getSizePercent() == null)) {
            return false;
        }
        if (treatmentResource.getSizePercent() != null && !treatmentResource.getSizePercent().equals(getSizePercent())) {
            return false;
        }
        if ((treatmentResource.getState() == null) ^ (getState() == null)) {
            return false;
        }
        if (treatmentResource.getState() != null && !treatmentResource.getState().equals(getState())) {
            return false;
        }
        if ((treatmentResource.getTreatmentDescription() == null) ^ (getTreatmentDescription() == null)) {
            return false;
        }
        if (treatmentResource.getTreatmentDescription() != null && !treatmentResource.getTreatmentDescription().equals(getTreatmentDescription())) {
            return false;
        }
        if ((treatmentResource.getTreatmentName() == null) ^ (getTreatmentName() == null)) {
            return false;
        }
        return treatmentResource.getTreatmentName() == null || treatmentResource.getTreatmentName().equals(getTreatmentName());
    }
}
