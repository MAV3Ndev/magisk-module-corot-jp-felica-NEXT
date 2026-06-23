package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class EventStream implements Serializable {
    private String applicationId;
    private String destinationStreamArn;
    private String externalId;
    private String lastModifiedDate;
    private String lastUpdatedBy;
    private String roleArn;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public EventStream withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getDestinationStreamArn() {
        return this.destinationStreamArn;
    }

    public void setDestinationStreamArn(String str) {
        this.destinationStreamArn = str;
    }

    public EventStream withDestinationStreamArn(String str) {
        this.destinationStreamArn = str;
        return this;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public void setExternalId(String str) {
        this.externalId = str;
    }

    public EventStream withExternalId(String str) {
        this.externalId = str;
        return this;
    }

    public String getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(String str) {
        this.lastModifiedDate = str;
    }

    public EventStream withLastModifiedDate(String str) {
        this.lastModifiedDate = str;
        return this;
    }

    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(String str) {
        this.lastUpdatedBy = str;
    }

    public EventStream withLastUpdatedBy(String str) {
        this.lastUpdatedBy = str;
        return this;
    }

    public String getRoleArn() {
        return this.roleArn;
    }

    public void setRoleArn(String str) {
        this.roleArn = str;
    }

    public EventStream withRoleArn(String str) {
        this.roleArn = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getDestinationStreamArn() != null) {
            sb.append("DestinationStreamArn: " + getDestinationStreamArn() + ",");
        }
        if (getExternalId() != null) {
            sb.append("ExternalId: " + getExternalId() + ",");
        }
        if (getLastModifiedDate() != null) {
            sb.append("LastModifiedDate: " + getLastModifiedDate() + ",");
        }
        if (getLastUpdatedBy() != null) {
            sb.append("LastUpdatedBy: " + getLastUpdatedBy() + ",");
        }
        if (getRoleArn() != null) {
            sb.append("RoleArn: " + getRoleArn());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getDestinationStreamArn() == null ? 0 : getDestinationStreamArn().hashCode())) * 31) + (getExternalId() == null ? 0 : getExternalId().hashCode())) * 31) + (getLastModifiedDate() == null ? 0 : getLastModifiedDate().hashCode())) * 31) + (getLastUpdatedBy() == null ? 0 : getLastUpdatedBy().hashCode())) * 31) + (getRoleArn() != null ? getRoleArn().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EventStream)) {
            return false;
        }
        EventStream eventStream = (EventStream) obj;
        if ((eventStream.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (eventStream.getApplicationId() != null && !eventStream.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((eventStream.getDestinationStreamArn() == null) ^ (getDestinationStreamArn() == null)) {
            return false;
        }
        if (eventStream.getDestinationStreamArn() != null && !eventStream.getDestinationStreamArn().equals(getDestinationStreamArn())) {
            return false;
        }
        if ((eventStream.getExternalId() == null) ^ (getExternalId() == null)) {
            return false;
        }
        if (eventStream.getExternalId() != null && !eventStream.getExternalId().equals(getExternalId())) {
            return false;
        }
        if ((eventStream.getLastModifiedDate() == null) ^ (getLastModifiedDate() == null)) {
            return false;
        }
        if (eventStream.getLastModifiedDate() != null && !eventStream.getLastModifiedDate().equals(getLastModifiedDate())) {
            return false;
        }
        if ((eventStream.getLastUpdatedBy() == null) ^ (getLastUpdatedBy() == null)) {
            return false;
        }
        if (eventStream.getLastUpdatedBy() != null && !eventStream.getLastUpdatedBy().equals(getLastUpdatedBy())) {
            return false;
        }
        if ((eventStream.getRoleArn() == null) ^ (getRoleArn() == null)) {
            return false;
        }
        return eventStream.getRoleArn() == null || eventStream.getRoleArn().equals(getRoleArn());
    }
}
