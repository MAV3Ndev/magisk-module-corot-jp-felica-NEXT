package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class SMSChannelResponse implements Serializable {
    private String applicationId;
    private String creationDate;
    private Boolean enabled;
    private Boolean hasCredential;
    private String id;
    private Boolean isArchived;
    private String lastModifiedBy;
    private String lastModifiedDate;
    private String platform;
    private Integer promotionalMessagesPerSecond;
    private String senderId;
    private String shortCode;
    private Integer transactionalMessagesPerSecond;
    private Integer version;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public SMSChannelResponse withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(String str) {
        this.creationDate = str;
    }

    public SMSChannelResponse withCreationDate(String str) {
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

    public SMSChannelResponse withEnabled(Boolean bool) {
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

    public SMSChannelResponse withHasCredential(Boolean bool) {
        this.hasCredential = bool;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public SMSChannelResponse withId(String str) {
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

    public SMSChannelResponse withIsArchived(Boolean bool) {
        this.isArchived = bool;
        return this;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(String str) {
        this.lastModifiedBy = str;
    }

    public SMSChannelResponse withLastModifiedBy(String str) {
        this.lastModifiedBy = str;
        return this;
    }

    public String getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(String str) {
        this.lastModifiedDate = str;
    }

    public SMSChannelResponse withLastModifiedDate(String str) {
        this.lastModifiedDate = str;
        return this;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String str) {
        this.platform = str;
    }

    public SMSChannelResponse withPlatform(String str) {
        this.platform = str;
        return this;
    }

    public Integer getPromotionalMessagesPerSecond() {
        return this.promotionalMessagesPerSecond;
    }

    public void setPromotionalMessagesPerSecond(Integer num) {
        this.promotionalMessagesPerSecond = num;
    }

    public SMSChannelResponse withPromotionalMessagesPerSecond(Integer num) {
        this.promotionalMessagesPerSecond = num;
        return this;
    }

    public String getSenderId() {
        return this.senderId;
    }

    public void setSenderId(String str) {
        this.senderId = str;
    }

    public SMSChannelResponse withSenderId(String str) {
        this.senderId = str;
        return this;
    }

    public String getShortCode() {
        return this.shortCode;
    }

    public void setShortCode(String str) {
        this.shortCode = str;
    }

    public SMSChannelResponse withShortCode(String str) {
        this.shortCode = str;
        return this;
    }

    public Integer getTransactionalMessagesPerSecond() {
        return this.transactionalMessagesPerSecond;
    }

    public void setTransactionalMessagesPerSecond(Integer num) {
        this.transactionalMessagesPerSecond = num;
    }

    public SMSChannelResponse withTransactionalMessagesPerSecond(Integer num) {
        this.transactionalMessagesPerSecond = num;
        return this;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer num) {
        this.version = num;
    }

    public SMSChannelResponse withVersion(Integer num) {
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
        if (getPromotionalMessagesPerSecond() != null) {
            sb.append("PromotionalMessagesPerSecond: " + getPromotionalMessagesPerSecond() + ",");
        }
        if (getSenderId() != null) {
            sb.append("SenderId: " + getSenderId() + ",");
        }
        if (getShortCode() != null) {
            sb.append("ShortCode: " + getShortCode() + ",");
        }
        if (getTransactionalMessagesPerSecond() != null) {
            sb.append("TransactionalMessagesPerSecond: " + getTransactionalMessagesPerSecond() + ",");
        }
        if (getVersion() != null) {
            sb.append("Version: " + getVersion());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((((((((((((((((((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getCreationDate() == null ? 0 : getCreationDate().hashCode())) * 31) + (getEnabled() == null ? 0 : getEnabled().hashCode())) * 31) + (getHasCredential() == null ? 0 : getHasCredential().hashCode())) * 31) + (getId() == null ? 0 : getId().hashCode())) * 31) + (getIsArchived() == null ? 0 : getIsArchived().hashCode())) * 31) + (getLastModifiedBy() == null ? 0 : getLastModifiedBy().hashCode())) * 31) + (getLastModifiedDate() == null ? 0 : getLastModifiedDate().hashCode())) * 31) + (getPlatform() == null ? 0 : getPlatform().hashCode())) * 31) + (getPromotionalMessagesPerSecond() == null ? 0 : getPromotionalMessagesPerSecond().hashCode())) * 31) + (getSenderId() == null ? 0 : getSenderId().hashCode())) * 31) + (getShortCode() == null ? 0 : getShortCode().hashCode())) * 31) + (getTransactionalMessagesPerSecond() == null ? 0 : getTransactionalMessagesPerSecond().hashCode())) * 31) + (getVersion() != null ? getVersion().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SMSChannelResponse)) {
            return false;
        }
        SMSChannelResponse sMSChannelResponse = (SMSChannelResponse) obj;
        if ((sMSChannelResponse.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (sMSChannelResponse.getApplicationId() != null && !sMSChannelResponse.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((sMSChannelResponse.getCreationDate() == null) ^ (getCreationDate() == null)) {
            return false;
        }
        if (sMSChannelResponse.getCreationDate() != null && !sMSChannelResponse.getCreationDate().equals(getCreationDate())) {
            return false;
        }
        if ((sMSChannelResponse.getEnabled() == null) ^ (getEnabled() == null)) {
            return false;
        }
        if (sMSChannelResponse.getEnabled() != null && !sMSChannelResponse.getEnabled().equals(getEnabled())) {
            return false;
        }
        if ((sMSChannelResponse.getHasCredential() == null) ^ (getHasCredential() == null)) {
            return false;
        }
        if (sMSChannelResponse.getHasCredential() != null && !sMSChannelResponse.getHasCredential().equals(getHasCredential())) {
            return false;
        }
        if ((sMSChannelResponse.getId() == null) ^ (getId() == null)) {
            return false;
        }
        if (sMSChannelResponse.getId() != null && !sMSChannelResponse.getId().equals(getId())) {
            return false;
        }
        if ((sMSChannelResponse.getIsArchived() == null) ^ (getIsArchived() == null)) {
            return false;
        }
        if (sMSChannelResponse.getIsArchived() != null && !sMSChannelResponse.getIsArchived().equals(getIsArchived())) {
            return false;
        }
        if ((sMSChannelResponse.getLastModifiedBy() == null) ^ (getLastModifiedBy() == null)) {
            return false;
        }
        if (sMSChannelResponse.getLastModifiedBy() != null && !sMSChannelResponse.getLastModifiedBy().equals(getLastModifiedBy())) {
            return false;
        }
        if ((sMSChannelResponse.getLastModifiedDate() == null) ^ (getLastModifiedDate() == null)) {
            return false;
        }
        if (sMSChannelResponse.getLastModifiedDate() != null && !sMSChannelResponse.getLastModifiedDate().equals(getLastModifiedDate())) {
            return false;
        }
        if ((sMSChannelResponse.getPlatform() == null) ^ (getPlatform() == null)) {
            return false;
        }
        if (sMSChannelResponse.getPlatform() != null && !sMSChannelResponse.getPlatform().equals(getPlatform())) {
            return false;
        }
        if ((sMSChannelResponse.getPromotionalMessagesPerSecond() == null) ^ (getPromotionalMessagesPerSecond() == null)) {
            return false;
        }
        if (sMSChannelResponse.getPromotionalMessagesPerSecond() != null && !sMSChannelResponse.getPromotionalMessagesPerSecond().equals(getPromotionalMessagesPerSecond())) {
            return false;
        }
        if ((sMSChannelResponse.getSenderId() == null) ^ (getSenderId() == null)) {
            return false;
        }
        if (sMSChannelResponse.getSenderId() != null && !sMSChannelResponse.getSenderId().equals(getSenderId())) {
            return false;
        }
        if ((sMSChannelResponse.getShortCode() == null) ^ (getShortCode() == null)) {
            return false;
        }
        if (sMSChannelResponse.getShortCode() != null && !sMSChannelResponse.getShortCode().equals(getShortCode())) {
            return false;
        }
        if ((sMSChannelResponse.getTransactionalMessagesPerSecond() == null) ^ (getTransactionalMessagesPerSecond() == null)) {
            return false;
        }
        if (sMSChannelResponse.getTransactionalMessagesPerSecond() != null && !sMSChannelResponse.getTransactionalMessagesPerSecond().equals(getTransactionalMessagesPerSecond())) {
            return false;
        }
        if ((sMSChannelResponse.getVersion() == null) ^ (getVersion() == null)) {
            return false;
        }
        return sMSChannelResponse.getVersion() == null || sMSChannelResponse.getVersion().equals(getVersion());
    }
}
