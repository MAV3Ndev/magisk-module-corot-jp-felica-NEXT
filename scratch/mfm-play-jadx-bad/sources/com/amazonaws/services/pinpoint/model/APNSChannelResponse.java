package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class APNSChannelResponse implements Serializable {
    private String applicationId;
    private String creationDate;
    private String defaultAuthenticationMethod;
    private Boolean enabled;
    private Boolean hasCredential;
    private Boolean hasTokenKey;
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

    public APNSChannelResponse withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(String str) {
        this.creationDate = str;
    }

    public APNSChannelResponse withCreationDate(String str) {
        this.creationDate = str;
        return this;
    }

    public String getDefaultAuthenticationMethod() {
        return this.defaultAuthenticationMethod;
    }

    public void setDefaultAuthenticationMethod(String str) {
        this.defaultAuthenticationMethod = str;
    }

    public APNSChannelResponse withDefaultAuthenticationMethod(String str) {
        this.defaultAuthenticationMethod = str;
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

    public APNSChannelResponse withEnabled(Boolean bool) {
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

    public APNSChannelResponse withHasCredential(Boolean bool) {
        this.hasCredential = bool;
        return this;
    }

    public Boolean isHasTokenKey() {
        return this.hasTokenKey;
    }

    public Boolean getHasTokenKey() {
        return this.hasTokenKey;
    }

    public void setHasTokenKey(Boolean bool) {
        this.hasTokenKey = bool;
    }

    public APNSChannelResponse withHasTokenKey(Boolean bool) {
        this.hasTokenKey = bool;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public APNSChannelResponse withId(String str) {
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

    public APNSChannelResponse withIsArchived(Boolean bool) {
        this.isArchived = bool;
        return this;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(String str) {
        this.lastModifiedBy = str;
    }

    public APNSChannelResponse withLastModifiedBy(String str) {
        this.lastModifiedBy = str;
        return this;
    }

    public String getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(String str) {
        this.lastModifiedDate = str;
    }

    public APNSChannelResponse withLastModifiedDate(String str) {
        this.lastModifiedDate = str;
        return this;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String str) {
        this.platform = str;
    }

    public APNSChannelResponse withPlatform(String str) {
        this.platform = str;
        return this;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer num) {
        this.version = num;
    }

    public APNSChannelResponse withVersion(Integer num) {
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
        if (getDefaultAuthenticationMethod() != null) {
            sb.append("DefaultAuthenticationMethod: " + getDefaultAuthenticationMethod() + ",");
        }
        if (getEnabled() != null) {
            sb.append("Enabled: " + getEnabled() + ",");
        }
        if (getHasCredential() != null) {
            sb.append("HasCredential: " + getHasCredential() + ",");
        }
        if (getHasTokenKey() != null) {
            sb.append("HasTokenKey: " + getHasTokenKey() + ",");
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
        return (((((((((((((((((((((((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getCreationDate() == null ? 0 : getCreationDate().hashCode())) * 31) + (getDefaultAuthenticationMethod() == null ? 0 : getDefaultAuthenticationMethod().hashCode())) * 31) + (getEnabled() == null ? 0 : getEnabled().hashCode())) * 31) + (getHasCredential() == null ? 0 : getHasCredential().hashCode())) * 31) + (getHasTokenKey() == null ? 0 : getHasTokenKey().hashCode())) * 31) + (getId() == null ? 0 : getId().hashCode())) * 31) + (getIsArchived() == null ? 0 : getIsArchived().hashCode())) * 31) + (getLastModifiedBy() == null ? 0 : getLastModifiedBy().hashCode())) * 31) + (getLastModifiedDate() == null ? 0 : getLastModifiedDate().hashCode())) * 31) + (getPlatform() == null ? 0 : getPlatform().hashCode())) * 31) + (getVersion() != null ? getVersion().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof APNSChannelResponse)) {
            return false;
        }
        APNSChannelResponse aPNSChannelResponse = (APNSChannelResponse) obj;
        if ((aPNSChannelResponse.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (aPNSChannelResponse.getApplicationId() != null && !aPNSChannelResponse.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((aPNSChannelResponse.getCreationDate() == null) ^ (getCreationDate() == null)) {
            return false;
        }
        if (aPNSChannelResponse.getCreationDate() != null && !aPNSChannelResponse.getCreationDate().equals(getCreationDate())) {
            return false;
        }
        if ((aPNSChannelResponse.getDefaultAuthenticationMethod() == null) ^ (getDefaultAuthenticationMethod() == null)) {
            return false;
        }
        if (aPNSChannelResponse.getDefaultAuthenticationMethod() != null && !aPNSChannelResponse.getDefaultAuthenticationMethod().equals(getDefaultAuthenticationMethod())) {
            return false;
        }
        if ((aPNSChannelResponse.getEnabled() == null) ^ (getEnabled() == null)) {
            return false;
        }
        if (aPNSChannelResponse.getEnabled() != null && !aPNSChannelResponse.getEnabled().equals(getEnabled())) {
            return false;
        }
        if ((aPNSChannelResponse.getHasCredential() == null) ^ (getHasCredential() == null)) {
            return false;
        }
        if (aPNSChannelResponse.getHasCredential() != null && !aPNSChannelResponse.getHasCredential().equals(getHasCredential())) {
            return false;
        }
        if ((aPNSChannelResponse.getHasTokenKey() == null) ^ (getHasTokenKey() == null)) {
            return false;
        }
        if (aPNSChannelResponse.getHasTokenKey() != null && !aPNSChannelResponse.getHasTokenKey().equals(getHasTokenKey())) {
            return false;
        }
        if ((aPNSChannelResponse.getId() == null) ^ (getId() == null)) {
            return false;
        }
        if (aPNSChannelResponse.getId() != null && !aPNSChannelResponse.getId().equals(getId())) {
            return false;
        }
        if ((aPNSChannelResponse.getIsArchived() == null) ^ (getIsArchived() == null)) {
            return false;
        }
        if (aPNSChannelResponse.getIsArchived() != null && !aPNSChannelResponse.getIsArchived().equals(getIsArchived())) {
            return false;
        }
        if ((aPNSChannelResponse.getLastModifiedBy() == null) ^ (getLastModifiedBy() == null)) {
            return false;
        }
        if (aPNSChannelResponse.getLastModifiedBy() != null && !aPNSChannelResponse.getLastModifiedBy().equals(getLastModifiedBy())) {
            return false;
        }
        if ((aPNSChannelResponse.getLastModifiedDate() == null) ^ (getLastModifiedDate() == null)) {
            return false;
        }
        if (aPNSChannelResponse.getLastModifiedDate() != null && !aPNSChannelResponse.getLastModifiedDate().equals(getLastModifiedDate())) {
            return false;
        }
        if ((aPNSChannelResponse.getPlatform() == null) ^ (getPlatform() == null)) {
            return false;
        }
        if (aPNSChannelResponse.getPlatform() != null && !aPNSChannelResponse.getPlatform().equals(getPlatform())) {
            return false;
        }
        if ((aPNSChannelResponse.getVersion() == null) ^ (getVersion() == null)) {
            return false;
        }
        return aPNSChannelResponse.getVersion() == null || aPNSChannelResponse.getVersion().equals(getVersion());
    }
}
