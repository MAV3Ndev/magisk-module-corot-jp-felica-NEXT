package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateEndpointResult implements Serializable {
    private MessageBody messageBody;

    public MessageBody getMessageBody() {
        return this.messageBody;
    }

    public void setMessageBody(MessageBody messageBody) {
        this.messageBody = messageBody;
    }

    public UpdateEndpointResult withMessageBody(MessageBody messageBody) {
        this.messageBody = messageBody;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getMessageBody() != null) {
            sb.append("MessageBody: " + getMessageBody());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getMessageBody() == null ? 0 : getMessageBody().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateEndpointResult)) {
            return false;
        }
        UpdateEndpointResult updateEndpointResult = (UpdateEndpointResult) obj;
        if ((updateEndpointResult.getMessageBody() == null) ^ (getMessageBody() == null)) {
            return false;
        }
        return updateEndpointResult.getMessageBody() == null || updateEndpointResult.getMessageBody().equals(getMessageBody());
    }
}
