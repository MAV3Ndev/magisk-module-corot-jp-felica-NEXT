package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class SendUsersMessagesResult implements Serializable {
    private SendUsersMessageResponse sendUsersMessageResponse;

    public SendUsersMessageResponse getSendUsersMessageResponse() {
        return this.sendUsersMessageResponse;
    }

    public void setSendUsersMessageResponse(SendUsersMessageResponse sendUsersMessageResponse) {
        this.sendUsersMessageResponse = sendUsersMessageResponse;
    }

    public SendUsersMessagesResult withSendUsersMessageResponse(SendUsersMessageResponse sendUsersMessageResponse) {
        this.sendUsersMessageResponse = sendUsersMessageResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getSendUsersMessageResponse() != null) {
            sb.append("SendUsersMessageResponse: " + getSendUsersMessageResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getSendUsersMessageResponse() == null ? 0 : getSendUsersMessageResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SendUsersMessagesResult)) {
            return false;
        }
        SendUsersMessagesResult sendUsersMessagesResult = (SendUsersMessagesResult) obj;
        if ((sendUsersMessagesResult.getSendUsersMessageResponse() == null) ^ (getSendUsersMessageResponse() == null)) {
            return false;
        }
        return sendUsersMessagesResult.getSendUsersMessageResponse() == null || sendUsersMessagesResult.getSendUsersMessageResponse().equals(getSendUsersMessageResponse());
    }
}
