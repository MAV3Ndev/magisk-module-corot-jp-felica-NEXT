package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GCMChannelResponse implements Serializable {
    private String applicationId;
    private String creationDate;
    private String credential;
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

    public GCMChannelResponse withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(String str) {
        this.creationDate = str;
    }

    public GCMChannelResponse withCreationDate(String str) {
        this.creationDate = str;
        return this;
    }

    public String getCredential() {
        return this.credential;
    }

    public void setCredential(String str) {
        this.credential = str;
    }

    public GCMChannelResponse withCredential(String str) {
        this.credential = str;
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

    public GCMChannelResponse withEnabled(Boolean bool) {
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

    public GCMChannelResponse withHasCredential(Boolean bool) {
        this.hasCredential = bool;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public GCMChannelResponse withId(String str) {
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

    public GCMChannelResponse withIsArchived(Boolean bool) {
        this.isArchived = bool;
        return this;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(String str) {
        this.lastModifiedBy = str;
    }

    public GCMChannelResponse withLastModifiedBy(String str) {
        this.lastModifiedBy = str;
        return this;
    }

    public String getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(String str) {
        this.lastModifiedDate = str;
    }

    public GCMChannelResponse withLastModifiedDate(String str) {
        this.lastModifiedDate = str;
        return this;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String str) {
        this.platform = str;
    }

    public GCMChannelResponse withPlatform(String str) {
        this.platform = str;
        return this;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer num) {
        this.version = num;
    }

    public GCMChannelResponse withVersion(Integer num) {
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
        if (getCredential() != null) {
            sb.append("Credential: " + getCredential() + ",");
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
        return (((((((((((((((((((((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getCreationDate() == null ? 0 : getCreationDate().hashCode())) * 31) + (getCredential() == null ? 0 : getCredential().hashCode())) * 31) + (getEnabled() == null ? 0 : getEnabled().hashCode())) * 31) + (getHasCredential() == null ? 0 : getHasCredential().hashCode())) * 31) + (getId() == null ? 0 : getId().hashCode())) * 31) + (getIsArchived() == null ? 0 : getIsArchived().hashCode())) * 31) + (getLastModifiedBy() == null ? 0 : getLastModifiedBy().hashCode())) * 31) + (getLastModifiedDate() == null ? 0 : getLastModifiedDate().hashCode())) * 31) + (getPlatform() == null ? 0 : getPlatform().hashCode())) * 31) + (getVersion() != null ? getVersion().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GCMChannelResponse)) {
            return false;
        }
        GCMChannelResponse gCMChannelResponse = (GCMChannelResponse) obj;
        if ((gCMChannelResponse.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (gCMChannelResponse.getApplicationId() != null && !gCMChannelResponse.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((gCMChannelResponse.getCreationDate() == null) ^ (getCreationDate() == null)) {
            return false;
        }
        if (gCMChannelResponse.getCreationDate() != null && !gCMChannelResponse.getCreationDate().equals(getCreationDate())) {
            return false;
        }
        if ((gCMChannelResponse.getCredential() == null) ^ (getCredential() == null)) {
            return false;
        }
        if (gCMChannelResponse.getCredential() != null && !gCMChannelResponse.getCredential().equals(getCredential())) {
            return false;
        }
        if ((gCMChannelResponse.getEnabled() == null) ^ (getEnabled() == null)) {
            return false;
        }
        if (gCMChannelResponse.getEnabled() != null && !gCMChannelResponse.getEnabled().equals(getEnabled())) {
            return false;
        }
        if ((gCMChannelResponse.getHasCredential() == null) ^ (getHasCredential() == null)) {
            return false;
        }
        if (gCMChannelResponse.getHasCredential() != null && !gCMChannelResponse.getHasCredential().equals(getHasCredential())) {
            return false;
        }
        if ((gCMChannelResponse.getId() == null) ^ (getId() == null)) {
            return false;
        }
        if (gCMChannelResponse.getId() != null && !gCMChannelResponse.getId().equals(getId())) {
            return false;
        }
        if ((gCMChannelResponse.getIsArchived() == null) ^ (getIsArchived() == null)) {
            return false;
        }
        if (gCMChannelResponse.getIsArchived() != null && !gCMChannelResponse.getIsArchived().equals(getIsArchived())) {
            return false;
        }
        if ((gCMChannelResponse.getLastModifiedBy() == null) ^ (getLastModifiedBy() == null)) {
            return false;
        }
        if (gCMChannelResponse.getLastModifiedBy() != null && !gCMChannelResponse.getLastModifiedBy().equals(getLastModifiedBy())) {
            return false;
        }
        if ((gCMChannelResponse.getLastModifiedDate() == null) ^ (getLastModifiedDate() == null)) {
            return false;
        }
        if (gCMChannelResponse.getLastModifiedDate() != null && !gCMChannelResponse.getLastModifiedDate().equals(getLastModifiedDate())) {
            return false;
        }
        if ((gCMChannelResponse.getPlatform() == null) ^ (getPlatform() == null)) {
            return false;
        }
        if (gCMChannelResponse.getPlatform() != null && !gCMChannelResponse.getPlatform().equals(getPlatform())) {
            return false;
        }
        if ((gCMChannelResponse.getVersion() == null) ^ (getVersion() == null)) {
            return false;
        }
        return gCMChannelResponse.getVersion() == null || gCMChannelResponse.getVersion().equals(getVersion());
    }
}
