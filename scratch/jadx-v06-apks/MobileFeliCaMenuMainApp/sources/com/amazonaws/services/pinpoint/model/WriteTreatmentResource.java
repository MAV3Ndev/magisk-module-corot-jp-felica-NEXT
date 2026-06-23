package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class WriteTreatmentResource implements Serializable {
    private MessageConfiguration messageConfiguration;
    private Schedule schedule;
    private Integer sizePercent;
    private String treatmentDescription;
    private String treatmentName;

    public MessageConfiguration getMessageConfiguration() {
        return this.messageConfiguration;
    }

    public void setMessageConfiguration(MessageConfiguration messageConfiguration) {
        this.messageConfiguration = messageConfiguration;
    }

    public WriteTreatmentResource withMessageConfiguration(MessageConfiguration messageConfiguration) {
        this.messageConfiguration = messageConfiguration;
        return this;
    }

    public Schedule getSchedule() {
        return this.schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public WriteTreatmentResource withSchedule(Schedule schedule) {
        this.schedule = schedule;
        return this;
    }

    public Integer getSizePercent() {
        return this.sizePercent;
    }

    public void setSizePercent(Integer num) {
        this.sizePercent = num;
    }

    public WriteTreatmentResource withSizePercent(Integer num) {
        this.sizePercent = num;
        return this;
    }

    public String getTreatmentDescription() {
        return this.treatmentDescription;
    }

    public void setTreatmentDescription(String str) {
        this.treatmentDescription = str;
    }

    public WriteTreatmentResource withTreatmentDescription(String str) {
        this.treatmentDescription = str;
        return this;
    }

    public String getTreatmentName() {
        return this.treatmentName;
    }

    public void setTreatmentName(String str) {
        this.treatmentName = str;
    }

    public WriteTreatmentResource withTreatmentName(String str) {
        this.treatmentName = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getMessageConfiguration() != null) {
            sb.append("MessageConfiguration: " + getMessageConfiguration() + ",");
        }
        if (getSchedule() != null) {
            sb.append("Schedule: " + getSchedule() + ",");
        }
        if (getSizePercent() != null) {
            sb.append("SizePercent: " + getSizePercent() + ",");
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
        return (((((((((getMessageConfiguration() == null ? 0 : getMessageConfiguration().hashCode()) + 31) * 31) + (getSchedule() == null ? 0 : getSchedule().hashCode())) * 31) + (getSizePercent() == null ? 0 : getSizePercent().hashCode())) * 31) + (getTreatmentDescription() == null ? 0 : getTreatmentDescription().hashCode())) * 31) + (getTreatmentName() != null ? getTreatmentName().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof WriteTreatmentResource)) {
            return false;
        }
        WriteTreatmentResource writeTreatmentResource = (WriteTreatmentResource) obj;
        if ((writeTreatmentResource.getMessageConfiguration() == null) ^ (getMessageConfiguration() == null)) {
            return false;
        }
        if (writeTreatmentResource.getMessageConfiguration() != null && !writeTreatmentResource.getMessageConfiguration().equals(getMessageConfiguration())) {
            return false;
        }
        if ((writeTreatmentResource.getSchedule() == null) ^ (getSchedule() == null)) {
            return false;
        }
        if (writeTreatmentResource.getSchedule() != null && !writeTreatmentResource.getSchedule().equals(getSchedule())) {
            return false;
        }
        if ((writeTreatmentResource.getSizePercent() == null) ^ (getSizePercent() == null)) {
            return false;
        }
        if (writeTreatmentResource.getSizePercent() != null && !writeTreatmentResource.getSizePercent().equals(getSizePercent())) {
            return false;
        }
        if ((writeTreatmentResource.getTreatmentDescription() == null) ^ (getTreatmentDescription() == null)) {
            return false;
        }
        if (writeTreatmentResource.getTreatmentDescription() != null && !writeTreatmentResource.getTreatmentDescription().equals(getTreatmentDescription())) {
            return false;
        }
        if ((writeTreatmentResource.getTreatmentName() == null) ^ (getTreatmentName() == null)) {
            return false;
        }
        return writeTreatmentResource.getTreatmentName() == null || writeTreatmentResource.getTreatmentName().equals(getTreatmentName());
    }
}
