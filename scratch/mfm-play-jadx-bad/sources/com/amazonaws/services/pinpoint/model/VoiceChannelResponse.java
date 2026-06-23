package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class VoiceChannelResponse implements Serializable {
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

    public VoiceChannelResponse withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(String str) {
        this.creationDate = str;
    }

    public VoiceChannelResponse withCreationDate(String str) {
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

    public VoiceChannelResponse withEnabled(Boolean bool) {
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

    public VoiceChannelResponse withHasCredential(Boolean bool) {
        this.hasCredential = bool;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public VoiceChannelResponse withId(String str) {
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

    public VoiceChannelResponse withIsArchived(Boolean bool) {
        this.isArchived = bool;
        return this;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(String str) {
        this.lastModifiedBy = str;
    }

    public VoiceChannelResponse withLastModifiedBy(String str) {
        this.lastModifiedBy = str;
        return this;
    }

    public String getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(String str) {
        this.lastModifiedDate = str;
    }

    public VoiceChannelResponse withLastModifiedDate(String str) {
        this.lastModifiedDate = str;
        return this;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String str) {
        this.platform = str;
    }

    public VoiceChannelResponse withPlatform(String str) {
        this.platform = str;
        return this;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer num) {
        this.version = num;
    }

    public VoiceChannelResponse withVersion(Integer num) {
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
        if (obj == null || !(obj instanceof VoiceChannelResponse)) {
            return false;
        }
        VoiceChannelResponse voiceChannelResponse = (VoiceChannelResponse) obj;
        if ((voiceChannelResponse.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (voiceChannelResponse.getApplicationId() != null && !voiceChannelResponse.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((voiceChannelResponse.getCreationDate() == null) ^ (getCreationDate() == null)) {
            return false;
        }
        if (voiceChannelResponse.getCreationDate() != null && !voiceChannelResponse.getCreationDate().equals(getCreationDate())) {
            return false;
        }
        if ((voiceChannelResponse.getEnabled() == null) ^ (getEnabled() == null)) {
            return false;
        }
        if (voiceChannelResponse.getEnabled() != null && !voiceChannelResponse.getEnabled().equals(getEnabled())) {
            return false;
        }
        if ((voiceChannelResponse.getHasCredential() == null) ^ (getHasCredential() == null)) {
            return false;
        }
        if (voiceChannelResponse.getHasCredential() != null && !voiceChannelResponse.getHasCredential().equals(getHasCredential())) {
            return false;
        }
        if ((voiceChannelResponse.getId() == null) ^ (getId() == null)) {
            return false;
        }
        if (voiceChannelResponse.getId() != null && !voiceChannelResponse.getId().equals(getId())) {
            return false;
        }
        if ((voiceChannelResponse.getIsArchived() == null) ^ (getIsArchived() == null)) {
            return false;
        }
        if (voiceChannelResponse.getIsArchived() != null && !voiceChannelResponse.getIsArchived().equals(getIsArchived())) {
            return false;
        }
        if ((voiceChannelResponse.getLastModifiedBy() == null) ^ (getLastModifiedBy() == null)) {
            return false;
        }
        if (voiceChannelResponse.getLastModifiedBy() != null && !voiceChannelResponse.getLastModifiedBy().equals(getLastModifiedBy())) {
            return false;
        }
        if ((voiceChannelResponse.getLastModifiedDate() == null) ^ (getLastModifiedDate() == null)) {
            return false;
        }
        if (voiceChannelResponse.getLastModifiedDate() != null && !voiceChannelResponse.getLastModifiedDate().equals(getLastModifiedDate())) {
            return false;
        }
        if ((voiceChannelResponse.getPlatform() == null) ^ (getPlatform() == null)) {
            return false;
        }
        if (voiceChannelResponse.getPlatform() != null && !voiceChannelResponse.getPlatform().equals(getPlatform())) {
            return false;
        }
        if ((voiceChannelResponse.getVersion() == null) ^ (getVersion() == null)) {
            return false;
        }
        return voiceChannelResponse.getVersion() == null || voiceChannelResponse.getVersion().equals(getVersion());
    }
}
