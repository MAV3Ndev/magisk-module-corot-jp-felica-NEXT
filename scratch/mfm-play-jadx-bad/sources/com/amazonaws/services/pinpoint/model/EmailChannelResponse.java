package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class EmailChannelResponse implements Serializable {
    private String applicationId;
    private String configurationSet;
    private String creationDate;
    private Boolean enabled;
    private String fromAddress;
    private Boolean hasCredential;
    private String id;
    private String identity;
    private Boolean isArchived;
    private String lastModifiedBy;
    private String lastModifiedDate;
    private Integer messagesPerSecond;
    private String platform;
    private String roleArn;
    private Integer version;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public EmailChannelResponse withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getConfigurationSet() {
        return this.configurationSet;
    }

    public void setConfigurationSet(String str) {
        this.configurationSet = str;
    }

    public EmailChannelResponse withConfigurationSet(String str) {
        this.configurationSet = str;
        return this;
    }

    public String getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(String str) {
        this.creationDate = str;
    }

    public EmailChannelResponse withCreationDate(String str) {
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

    public EmailChannelResponse withEnabled(Boolean bool) {
        this.enabled = bool;
        return this;
    }

    public String getFromAddress() {
        return this.fromAddress;
    }

    public void setFromAddress(String str) {
        this.fromAddress = str;
    }

    public EmailChannelResponse withFromAddress(String str) {
        this.fromAddress = str;
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

    public EmailChannelResponse withHasCredential(Boolean bool) {
        this.hasCredential = bool;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public EmailChannelResponse withId(String str) {
        this.id = str;
        return this;
    }

    public String getIdentity() {
        return this.identity;
    }

    public void setIdentity(String str) {
        this.identity = str;
    }

    public EmailChannelResponse withIdentity(String str) {
        this.identity = str;
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

    public EmailChannelResponse withIsArchived(Boolean bool) {
        this.isArchived = bool;
        return this;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(String str) {
        this.lastModifiedBy = str;
    }

    public EmailChannelResponse withLastModifiedBy(String str) {
        this.lastModifiedBy = str;
        return this;
    }

    public String getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(String str) {
        this.lastModifiedDate = str;
    }

    public EmailChannelResponse withLastModifiedDate(String str) {
        this.lastModifiedDate = str;
        return this;
    }

    public Integer getMessagesPerSecond() {
        return this.messagesPerSecond;
    }

    public void setMessagesPerSecond(Integer num) {
        this.messagesPerSecond = num;
    }

    public EmailChannelResponse withMessagesPerSecond(Integer num) {
        this.messagesPerSecond = num;
        return this;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String str) {
        this.platform = str;
    }

    public EmailChannelResponse withPlatform(String str) {
        this.platform = str;
        return this;
    }

    public String getRoleArn() {
        return this.roleArn;
    }

    public void setRoleArn(String str) {
        this.roleArn = str;
    }

    public EmailChannelResponse withRoleArn(String str) {
        this.roleArn = str;
        return this;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer num) {
        this.version = num;
    }

    public EmailChannelResponse withVersion(Integer num) {
        this.version = num;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getConfigurationSet() != null) {
            sb.append("ConfigurationSet: " + getConfigurationSet() + ",");
        }
        if (getCreationDate() != null) {
            sb.append("CreationDate: " + getCreationDate() + ",");
        }
        if (getEnabled() != null) {
            sb.append("Enabled: " + getEnabled() + ",");
        }
        if (getFromAddress() != null) {
            sb.append("FromAddress: " + getFromAddress() + ",");
        }
        if (getHasCredential() != null) {
            sb.append("HasCredential: " + getHasCredential() + ",");
        }
        if (getId() != null) {
            sb.append("Id: " + getId() + ",");
        }
        if (getIdentity() != null) {
            sb.append("Identity: " + getIdentity() + ",");
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
        if (getMessagesPerSecond() != null) {
            sb.append("MessagesPerSecond: " + getMessagesPerSecond() + ",");
        }
        if (getPlatform() != null) {
            sb.append("Platform: " + getPlatform() + ",");
        }
        if (getRoleArn() != null) {
            sb.append("RoleArn: " + getRoleArn() + ",");
        }
        if (getVersion() != null) {
            sb.append("Version: " + getVersion());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((((((((((((((((((((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getConfigurationSet() == null ? 0 : getConfigurationSet().hashCode())) * 31) + (getCreationDate() == null ? 0 : getCreationDate().hashCode())) * 31) + (getEnabled() == null ? 0 : getEnabled().hashCode())) * 31) + (getFromAddress() == null ? 0 : getFromAddress().hashCode())) * 31) + (getHasCredential() == null ? 0 : getHasCredential().hashCode())) * 31) + (getId() == null ? 0 : getId().hashCode())) * 31) + (getIdentity() == null ? 0 : getIdentity().hashCode())) * 31) + (getIsArchived() == null ? 0 : getIsArchived().hashCode())) * 31) + (getLastModifiedBy() == null ? 0 : getLastModifiedBy().hashCode())) * 31) + (getLastModifiedDate() == null ? 0 : getLastModifiedDate().hashCode())) * 31) + (getMessagesPerSecond() == null ? 0 : getMessagesPerSecond().hashCode())) * 31) + (getPlatform() == null ? 0 : getPlatform().hashCode())) * 31) + (getRoleArn() == null ? 0 : getRoleArn().hashCode())) * 31) + (getVersion() != null ? getVersion().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EmailChannelResponse)) {
            return false;
        }
        EmailChannelResponse emailChannelResponse = (EmailChannelResponse) obj;
        if ((emailChannelResponse.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (emailChannelResponse.getApplicationId() != null && !emailChannelResponse.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((emailChannelResponse.getConfigurationSet() == null) ^ (getConfigurationSet() == null)) {
            return false;
        }
        if (emailChannelResponse.getConfigurationSet() != null && !emailChannelResponse.getConfigurationSet().equals(getConfigurationSet())) {
            return false;
        }
        if ((emailChannelResponse.getCreationDate() == null) ^ (getCreationDate() == null)) {
            return false;
        }
        if (emailChannelResponse.getCreationDate() != null && !emailChannelResponse.getCreationDate().equals(getCreationDate())) {
            return false;
        }
        if ((emailChannelResponse.getEnabled() == null) ^ (getEnabled() == null)) {
            return false;
        }
        if (emailChannelResponse.getEnabled() != null && !emailChannelResponse.getEnabled().equals(getEnabled())) {
            return false;
        }
        if ((emailChannelResponse.getFromAddress() == null) ^ (getFromAddress() == null)) {
            return false;
        }
        if (emailChannelResponse.getFromAddress() != null && !emailChannelResponse.getFromAddress().equals(getFromAddress())) {
            return false;
        }
        if ((emailChannelResponse.getHasCredential() == null) ^ (getHasCredential() == null)) {
            return false;
        }
        if (emailChannelResponse.getHasCredential() != null && !emailChannelResponse.getHasCredential().equals(getHasCredential())) {
            return false;
        }
        if ((emailChannelResponse.getId() == null) ^ (getId() == null)) {
            return false;
        }
        if (emailChannelResponse.getId() != null && !emailChannelResponse.getId().equals(getId())) {
            return false;
        }
        if ((emailChannelResponse.getIdentity() == null) ^ (getIdentity() == null)) {
            return false;
        }
        if (emailChannelResponse.getIdentity() != null && !emailChannelResponse.getIdentity().equals(getIdentity())) {
            return false;
        }
        if ((emailChannelResponse.getIsArchived() == null) ^ (getIsArchived() == null)) {
            return false;
        }
        if (emailChannelResponse.getIsArchived() != null && !emailChannelResponse.getIsArchived().equals(getIsArchived())) {
            return false;
        }
        if ((emailChannelResponse.getLastModifiedBy() == null) ^ (getLastModifiedBy() == null)) {
            return false;
        }
        if (emailChannelResponse.getLastModifiedBy() != null && !emailChannelResponse.getLastModifiedBy().equals(getLastModifiedBy())) {
            return false;
        }
        if ((emailChannelResponse.getLastModifiedDate() == null) ^ (getLastModifiedDate() == null)) {
            return false;
        }
        if (emailChannelResponse.getLastModifiedDate() != null && !emailChannelResponse.getLastModifiedDate().equals(getLastModifiedDate())) {
            return false;
        }
        if ((emailChannelResponse.getMessagesPerSecond() == null) ^ (getMessagesPerSecond() == null)) {
            return false;
        }
        if (emailChannelResponse.getMessagesPerSecond() != null && !emailChannelResponse.getMessagesPerSecond().equals(getMessagesPerSecond())) {
            return false;
        }
        if ((emailChannelResponse.getPlatform() == null) ^ (getPlatform() == null)) {
            return false;
        }
        if (emailChannelResponse.getPlatform() != null && !emailChannelResponse.getPlatform().equals(getPlatform())) {
            return false;
        }
        if ((emailChannelResponse.getRoleArn() == null) ^ (getRoleArn() == null)) {
            return false;
        }
        if (emailChannelResponse.getRoleArn() != null && !emailChannelResponse.getRoleArn().equals(getRoleArn())) {
            return false;
        }
        if ((emailChannelResponse.getVersion() == null) ^ (getVersion() == null)) {
            return false;
        }
        return emailChannelResponse.getVersion() == null || emailChannelResponse.getVersion().equals(getVersion());
    }
}
