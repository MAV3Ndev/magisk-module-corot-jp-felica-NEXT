package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class SendUsersMessagesRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private SendUsersMessageRequest sendUsersMessageRequest;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public SendUsersMessagesRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public SendUsersMessageRequest getSendUsersMessageRequest() {
        return this.sendUsersMessageRequest;
    }

    public void setSendUsersMessageRequest(SendUsersMessageRequest sendUsersMessageRequest) {
        this.sendUsersMessageRequest = sendUsersMessageRequest;
    }

    public SendUsersMessagesRequest withSendUsersMessageRequest(SendUsersMessageRequest sendUsersMessageRequest) {
        this.sendUsersMessageRequest = sendUsersMessageRequest;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getSendUsersMessageRequest() != null) {
            sb.append("SendUsersMessageRequest: " + getSendUsersMessageRequest());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getSendUsersMessageRequest() != null ? getSendUsersMessageRequest().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SendUsersMessagesRequest)) {
            return false;
        }
        SendUsersMessagesRequest sendUsersMessagesRequest = (SendUsersMessagesRequest) obj;
        if ((sendUsersMessagesRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (sendUsersMessagesRequest.getApplicationId() != null && !sendUsersMessagesRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((sendUsersMessagesRequest.getSendUsersMessageRequest() == null) ^ (getSendUsersMessageRequest() == null)) {
            return false;
        }
        return sendUsersMessagesRequest.getSendUsersMessageRequest() == null || sendUsersMessagesRequest.getSendUsersMessageRequest().equals(getSendUsersMessageRequest());
    }
}
