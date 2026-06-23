package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateEndpointsBatchResult implements Serializable {
    private MessageBody messageBody;

    public MessageBody getMessageBody() {
        return this.messageBody;
    }

    public void setMessageBody(MessageBody messageBody) {
        this.messageBody = messageBody;
    }

    public UpdateEndpointsBatchResult withMessageBody(MessageBody messageBody) {
        this.messageBody = messageBody;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
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
        if (obj == null || !(obj instanceof UpdateEndpointsBatchResult)) {
            return false;
        }
        UpdateEndpointsBatchResult updateEndpointsBatchResult = (UpdateEndpointsBatchResult) obj;
        if ((updateEndpointsBatchResult.getMessageBody() == null) ^ (getMessageBody() == null)) {
            return false;
        }
        return updateEndpointsBatchResult.getMessageBody() == null || updateEndpointsBatchResult.getMessageBody().equals(getMessageBody());
    }
}
