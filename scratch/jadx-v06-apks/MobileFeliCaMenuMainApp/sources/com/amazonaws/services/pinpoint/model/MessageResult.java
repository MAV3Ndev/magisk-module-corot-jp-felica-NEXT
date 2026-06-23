package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class MessageResult implements Serializable {
    private String deliveryStatus;
    private String messageId;
    private Integer statusCode;
    private String statusMessage;
    private String updatedToken;

    public String getDeliveryStatus() {
        return this.deliveryStatus;
    }

    public void setDeliveryStatus(String str) {
        this.deliveryStatus = str;
    }

    public MessageResult withDeliveryStatus(String str) {
        this.deliveryStatus = str;
        return this;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus.toString();
    }

    public MessageResult withDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus.toString();
        return this;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String str) {
        this.messageId = str;
    }

    public MessageResult withMessageId(String str) {
        this.messageId = str;
        return this;
    }

    public Integer getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(Integer num) {
        this.statusCode = num;
    }

    public MessageResult withStatusCode(Integer num) {
        this.statusCode = num;
        return this;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public void setStatusMessage(String str) {
        this.statusMessage = str;
    }

    public MessageResult withStatusMessage(String str) {
        this.statusMessage = str;
        return this;
    }

    public String getUpdatedToken() {
        return this.updatedToken;
    }

    public void setUpdatedToken(String str) {
        this.updatedToken = str;
    }

    public MessageResult withUpdatedToken(String str) {
        this.updatedToken = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getDeliveryStatus() != null) {
            sb.append("DeliveryStatus: " + getDeliveryStatus() + ",");
        }
        if (getMessageId() != null) {
            sb.append("MessageId: " + getMessageId() + ",");
        }
        if (getStatusCode() != null) {
            sb.append("StatusCode: " + getStatusCode() + ",");
        }
        if (getStatusMessage() != null) {
            sb.append("StatusMessage: " + getStatusMessage() + ",");
        }
        if (getUpdatedToken() != null) {
            sb.append("UpdatedToken: " + getUpdatedToken());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((getDeliveryStatus() == null ? 0 : getDeliveryStatus().hashCode()) + 31) * 31) + (getMessageId() == null ? 0 : getMessageId().hashCode())) * 31) + (getStatusCode() == null ? 0 : getStatusCode().hashCode())) * 31) + (getStatusMessage() == null ? 0 : getStatusMessage().hashCode())) * 31) + (getUpdatedToken() != null ? getUpdatedToken().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof MessageResult)) {
            return false;
        }
        MessageResult messageResult = (MessageResult) obj;
        if ((messageResult.getDeliveryStatus() == null) ^ (getDeliveryStatus() == null)) {
            return false;
        }
        if (messageResult.getDeliveryStatus() != null && !messageResult.getDeliveryStatus().equals(getDeliveryStatus())) {
            return false;
        }
        if ((messageResult.getMessageId() == null) ^ (getMessageId() == null)) {
            return false;
        }
        if (messageResult.getMessageId() != null && !messageResult.getMessageId().equals(getMessageId())) {
            return false;
        }
        if ((messageResult.getStatusCode() == null) ^ (getStatusCode() == null)) {
            return false;
        }
        if (messageResult.getStatusCode() != null && !messageResult.getStatusCode().equals(getStatusCode())) {
            return false;
        }
        if ((messageResult.getStatusMessage() == null) ^ (getStatusMessage() == null)) {
            return false;
        }
        if (messageResult.getStatusMessage() != null && !messageResult.getStatusMessage().equals(getStatusMessage())) {
            return false;
        }
        if ((messageResult.getUpdatedToken() == null) ^ (getUpdatedToken() == null)) {
            return false;
        }
        return messageResult.getUpdatedToken() == null || messageResult.getUpdatedToken().equals(getUpdatedToken());
    }
}
