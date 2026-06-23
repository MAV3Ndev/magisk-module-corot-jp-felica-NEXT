package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class ADMChannelResponse implements Serializable {
    private String applicationId;
    private String creationDate;
    private Boolean enabled;
    private Boolean hasCredential;
    private String id;
    private Boolean isArchived;
    private String lastModifiedBy;
    private String lastModifiedDate;
    private String platform;
    private Integer version;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public ADMChannelResponse withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(String str) {
        this.creationDate = str;
    }

    public ADMChannelResponse withCreationDate(String str) {
        this.creationDate = str;
        return this;
    }

    public Boolean isEnabled() {
        return this.enabled;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean bool) {
        this.enabled = bool;
    }

    public ADMChannelResponse withEnabled(Boolean bool) {
        this.enabled = bool;
        return this;
    }

    public Boolean isHasCredential() {
        return this.hasCredential;
    }

    public Boolean getHasCredential() {
        return this.hasCredential;
    }

    public void setHasCredential(Boolean bool) {
        this.hasCredential = bool;
    }

    public ADMChannelResponse withHasCredential(Boolean bool) {
        this.hasCredential = bool;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public ADMChannelResponse withId(String str) {
        this.id = str;
        return this;
    }

    public Boolean isIsArchived() {
        return this.isArchived;
    }

    public Boolean getIsArchived() {
        return this.isArchived;
    }

    public void setIsArchived(Boolean bool) {
        this.isArchived = bool;
    }

    public ADMChannelResponse withIsArchived(Boolean bool) {
        this.isArchived = bool;
        return this;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(String str) {
        this.lastModifiedBy = str;
    }

    public ADMChannelResponse withLastModifiedBy(String str) {
        this.lastModifiedBy = str;
        return this;
    }

    public String getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(String str) {
        this.lastModifiedDate = str;
    }

    public ADMChannelResponse withLastModifiedDate(String str) {
        this.lastModifiedDate = str;
        return this;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String str) {
        this.platform = str;
    }

    public ADMChannelResponse withPlatform(String str) {
        this.platform = str;
        return this;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer num) {
        this.version = num;
    }

    public ADMChannelResponse withVersion(Integer num) {
        this.version = num;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getCreationDate() != null) {
            sb.append("CreationDate: " + getCreationDate() + ",");
        }
        if (getEnabled() != null) {
            sb.append("Enabled: " + getEnabled() + ",");
        }
        if (getHasCredential() != null) {
            sb.append("HasCredential: " + getHasCredential() + ",");
        }
        if (getId() != null) {
            sb.append("Id: " + getId() + ",");
        }
        if (getIsArchived() != null) {
            sb.append("IsArchived: " + getIsArchived() + ",");
        }
        if (getLastModifiedBy() != null) {
            sb.append("LastModifiedBy: " + getLastModifiedBy() + ",");
        }
        if (getLastModifiedDate() != null) {
            sb.append("LastModifiedDate: " + getLastModifiedDate() + ",");
        }
        if (getPlatform() != null) {
            sb.append("Platform: " + getPlatform() + ",");
        }
        if (getVersion() != null) {
            sb.append("Version: " + getVersion());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((((((((((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getCreationDate() == null ? 0 : getCreationDate().hashCode())) * 31) + (getEnabled() == null ? 0 : getEnabled().hashCode())) * 31) + (getHasCredential() == null ? 0 : getHasCredential().hashCode())) * 31) + (getId() == null ? 0 : getId().hashCode())) * 31) + (getIsArchived() == null ? 0 : getIsArchived().hashCode())) * 31) + (getLastModifiedBy() == null ? 0 : getLastModifiedBy().hashCode())) * 31) + (getLastModifiedDate() == null ? 0 : getLastModifiedDate().hashCode())) * 31) + (getPlatform() == null ? 0 : getPlatform().hashCode())) * 31) + (getVersion() != null ? getVersion().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ADMChannelResponse)) {
            return false;
        }
        ADMChannelResponse aDMChannelResponse = (ADMChannelResponse) obj;
        if ((aDMChannelResponse.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (aDMChannelResponse.getApplicationId() != null && !aDMChannelResponse.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((aDMChannelResponse.getCreationDate() == null) ^ (getCreationDate() == null)) {
            return false;
        }
        if (aDMChannelResponse.getCreationDate() != null && !aDMChannelResponse.getCreationDate().equals(getCreationDate())) {
            return false;
        }
        if ((aDMChannelResponse.getEnabled() == null) ^ (getEnabled() == null)) {
            return false;
        }
        if (aDMChannelResponse.getEnabled() != null && !aDMChannelResponse.getEnabled().equals(getEnabled())) {
            return false;
        }
        if ((aDMChannelResponse.getHasCredential() == null) ^ (getHasCredential() == null)) {
            return false;
        }
        if (aDMChannelResponse.getHasCredential() != null && !aDMChannelResponse.getHasCredential().equals(getHasCredential())) {
            return false;
        }
        if ((aDMChannelResponse.getId() == null) ^ (getId() == null)) {
            return false;
        }
        if (aDMChannelResponse.getId() != null && !aDMChannelResponse.getId().equals(getId())) {
            return false;
        }
        if ((aDMChannelResponse.getIsArchived() == null) ^ (getIsArchived() == null)) {
            return false;
        }
        if (aDMChannelResponse.getIsArchived() != null && !aDMChannelResponse.getIsArchived().equals(getIsArchived())) {
            return false;
        }
        if ((aDMChannelResponse.getLastModifiedBy() == null) ^ (getLastModifiedBy() == null)) {
            return false;
        }
        if (aDMChannelResponse.getLastModifiedBy() != null && !aDMChannelResponse.getLastModifiedBy().equals(getLastModifiedBy())) {
            return false;
        }
        if ((aDMChannelResponse.getLastModifiedDate() == null) ^ (getLastModifiedDate() == null)) {
            return false;
        }
        if (aDMChannelResponse.getLastModifiedDate() != null && !aDMChannelResponse.getLastModifiedDate().equals(getLastModifiedDate())) {
            return false;
        }
        if ((aDMChannelResponse.getPlatform() == null) ^ (getPlatform() == null)) {
            return false;
        }
        if (aDMChannelResponse.getPlatform() != null && !aDMChannelResponse.getPlatform().equals(getPlatform())) {
            return false;
        }
        if ((aDMChannelResponse.getVersion() == null) ^ (getVersion() == null)) {
            return false;
        }
        return aDMChannelResponse.getVersion() == null || aDMChannelResponse.getVersion().equals(getVersion());
    }
}
