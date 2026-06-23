package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class EndpointMessageResult implements Serializable {
    private String address;
    private String deliveryStatus;
    private String messageId;
    private Integer statusCode;
    private String statusMessage;
    private String updatedToken;

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public EndpointMessageResult withAddress(String str) {
        this.address = str;
        return this;
    }

    public String getDeliveryStatus() {
        return this.deliveryStatus;
    }

    public void setDeliveryStatus(String str) {
        this.deliveryStatus = str;
    }

    public EndpointMessageResult withDeliveryStatus(String str) {
        this.deliveryStatus = str;
        return this;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus.toString();
    }

    public EndpointMessageResult withDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus.toString();
        return this;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String str) {
        this.messageId = str;
    }

    public EndpointMessageResult withMessageId(String str) {
        this.messageId = str;
        return this;
    }

    public Integer getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(Integer num) {
        this.statusCode = num;
    }

    public EndpointMessageResult withStatusCode(Integer num) {
        this.statusCode = num;
        return this;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public void setStatusMessage(String str) {
        this.statusMessage = str;
    }

    public EndpointMessageResult withStatusMessage(String str) {
        this.statusMessage = str;
        return this;
    }

    public String getUpdatedToken() {
        return this.updatedToken;
    }

    public void setUpdatedToken(String str) {
        this.updatedToken = str;
    }

    public EndpointMessageResult withUpdatedToken(String str) {
        this.updatedToken = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getAddress() != null) {
            sb.append("Address: " + getAddress() + ",");
        }
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
        return (((((((((((getAddress() == null ? 0 : getAddress().hashCode()) + 31) * 31) + (getDeliveryStatus() == null ? 0 : getDeliveryStatus().hashCode())) * 31) + (getMessageId() == null ? 0 : getMessageId().hashCode())) * 31) + (getStatusCode() == null ? 0 : getStatusCode().hashCode())) * 31) + (getStatusMessage() == null ? 0 : getStatusMessage().hashCode())) * 31) + (getUpdatedToken() != null ? getUpdatedToken().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EndpointMessageResult)) {
            return false;
        }
        EndpointMessageResult endpointMessageResult = (EndpointMessageResult) obj;
        if ((endpointMessageResult.getAddress() == null) ^ (getAddress() == null)) {
            return false;
        }
        if (endpointMessageResult.getAddress() != null && !endpointMessageResult.getAddress().equals(getAddress())) {
            return false;
        }
        if ((endpointMessageResult.getDeliveryStatus() == null) ^ (getDeliveryStatus() == null)) {
            return false;
        }
        if (endpointMessageResult.getDeliveryStatus() != null && !endpointMessageResult.getDeliveryStatus().equals(getDeliveryStatus())) {
            return false;
        }
        if ((endpointMessageResult.getMessageId() == null) ^ (getMessageId() == null)) {
            return false;
        }
        if (endpointMessageResult.getMessageId() != null && !endpointMessageResult.getMessageId().equals(getMessageId())) {
            return false;
        }
        if ((endpointMessageResult.getStatusCode() == null) ^ (getStatusCode() == null)) {
            return false;
        }
        if (endpointMessageResult.getStatusCode() != null && !endpointMessageResult.getStatusCode().equals(getStatusCode())) {
            return false;
        }
        if ((endpointMessageResult.getStatusMessage() == null) ^ (getStatusMessage() == null)) {
            return false;
        }
        if (endpointMessageResult.getStatusMessage() != null && !endpointMessageResult.getStatusMessage().equals(getStatusMessage())) {
            return false;
        }
        if ((endpointMessageResult.getUpdatedToken() == null) ^ (getUpdatedToken() == null)) {
            return false;
        }
        return endpointMessageResult.getUpdatedToken() == null || endpointMessageResult.getUpdatedToken().equals(getUpdatedToken());
    }
}
