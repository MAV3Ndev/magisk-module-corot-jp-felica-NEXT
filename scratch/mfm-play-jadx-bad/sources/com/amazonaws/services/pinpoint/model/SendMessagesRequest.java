package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class SendMessagesRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private MessageRequest messageRequest;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public SendMessagesRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public MessageRequest getMessageRequest() {
        return this.messageRequest;
    }

    public void setMessageRequest(MessageRequest messageRequest) {
        this.messageRequest = messageRequest;
    }

    public SendMessagesRequest withMessageRequest(MessageRequest messageRequest) {
        this.messageRequest = messageRequest;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getMessageRequest() != null) {
            sb.append("MessageRequest: " + getMessageRequest());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getMessageRequest() != null ? getMessageRequest().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SendMessagesRequest)) {
            return false;
        }
        SendMessagesRequest sendMessagesRequest = (SendMessagesRequest) obj;
        if ((sendMessagesRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (sendMessagesRequest.getApplicationId() != null && !sendMessagesRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((sendMessagesRequest.getMessageRequest() == null) ^ (getMessageRequest() == null)) {
            return false;
        }
        return sendMessagesRequest.getMessageRequest() == null || sendMessagesRequest.getMessageRequest().equals(getMessageRequest());
    }
}
