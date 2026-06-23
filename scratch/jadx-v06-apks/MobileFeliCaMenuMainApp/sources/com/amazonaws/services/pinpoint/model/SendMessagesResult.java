package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class SendMessagesResult implements Serializable {
    private MessageResponse messageResponse;

    public MessageResponse getMessageResponse() {
        return this.messageResponse;
    }

    public void setMessageResponse(MessageResponse messageResponse) {
        this.messageResponse = messageResponse;
    }

    public SendMessagesResult withMessageResponse(MessageResponse messageResponse) {
        this.messageResponse = messageResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getMessageResponse() != null) {
            sb.append("MessageResponse: " + getMessageResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getMessageResponse() == null ? 0 : getMessageResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SendMessagesResult)) {
            return false;
        }
        SendMessagesResult sendMessagesResult = (SendMessagesResult) obj;
        if ((sendMessagesResult.getMessageResponse() == null) ^ (getMessageResponse() == null)) {
            return false;
        }
        return sendMessagesResult.getMessageResponse() == null || sendMessagesResult.getMessageResponse().equals(getMessageResponse());
    }
}
