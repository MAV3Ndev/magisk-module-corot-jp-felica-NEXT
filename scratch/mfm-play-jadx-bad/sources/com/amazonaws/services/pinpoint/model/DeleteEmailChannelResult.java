package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class DeleteEmailChannelResult implements Serializable {
    private EmailChannelResponse emailChannelResponse;

    public EmailChannelResponse getEmailChannelResponse() {
        return this.emailChannelResponse;
    }

    public void setEmailChannelResponse(EmailChannelResponse emailChannelResponse) {
        this.emailChannelResponse = emailChannelResponse;
    }

    public DeleteEmailChannelResult withEmailChannelResponse(EmailChannelResponse emailChannelResponse) {
        this.emailChannelResponse = emailChannelResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getEmailChannelResponse() != null) {
            sb.append("EmailChannelResponse: " + getEmailChannelResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getEmailChannelResponse() == null ? 0 : getEmailChannelResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DeleteEmailChannelResult)) {
            return false;
        }
        DeleteEmailChannelResult deleteEmailChannelResult = (DeleteEmailChannelResult) obj;
        if ((deleteEmailChannelResult.getEmailChannelResponse() == null) ^ (getEmailChannelResponse() == null)) {
            return false;
        }
        return deleteEmailChannelResult.getEmailChannelResponse() == null || deleteEmailChannelResult.getEmailChannelResponse().equals(getEmailChannelResponse());
    }
}
