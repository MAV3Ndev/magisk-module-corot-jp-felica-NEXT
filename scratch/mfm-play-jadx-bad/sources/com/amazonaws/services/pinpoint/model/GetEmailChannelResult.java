package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetEmailChannelResult implements Serializable {
    private EmailChannelResponse emailChannelResponse;

    public EmailChannelResponse getEmailChannelResponse() {
        return this.emailChannelResponse;
    }

    public void setEmailChannelResponse(EmailChannelResponse emailChannelResponse) {
        this.emailChannelResponse = emailChannelResponse;
    }

    public GetEmailChannelResult withEmailChannelResponse(EmailChannelResponse emailChannelResponse) {
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
        if (obj == null || !(obj instanceof GetEmailChannelResult)) {
            return false;
        }
        GetEmailChannelResult getEmailChannelResult = (GetEmailChannelResult) obj;
        if ((getEmailChannelResult.getEmailChannelResponse() == null) ^ (getEmailChannelResponse() == null)) {
            return false;
        }
        return getEmailChannelResult.getEmailChannelResponse() == null || getEmailChannelResult.getEmailChannelResponse().equals(getEmailChannelResponse());
    }
}
