package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateEmailChannelResult implements Serializable {
    private EmailChannelResponse emailChannelResponse;

    public EmailChannelResponse getEmailChannelResponse() {
        return this.emailChannelResponse;
    }

    public void setEmailChannelResponse(EmailChannelResponse emailChannelResponse) {
        this.emailChannelResponse = emailChannelResponse;
    }

    public UpdateEmailChannelResult withEmailChannelResponse(EmailChannelResponse emailChannelResponse) {
        this.emailChannelResponse = emailChannelResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
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
        if (obj == null || !(obj instanceof UpdateEmailChannelResult)) {
            return false;
        }
        UpdateEmailChannelResult updateEmailChannelResult = (UpdateEmailChannelResult) obj;
        if ((updateEmailChannelResult.getEmailChannelResponse() == null) ^ (getEmailChannelResponse() == null)) {
            return false;
        }
        return updateEmailChannelResult.getEmailChannelResponse() == null || updateEmailChannelResult.getEmailChannelResponse().equals(getEmailChannelResponse());
    }
}
