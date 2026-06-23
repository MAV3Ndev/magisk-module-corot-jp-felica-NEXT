package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateEmailChannelRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private EmailChannelRequest emailChannelRequest;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public UpdateEmailChannelRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public EmailChannelRequest getEmailChannelRequest() {
        return this.emailChannelRequest;
    }

    public void setEmailChannelRequest(EmailChannelRequest emailChannelRequest) {
        this.emailChannelRequest = emailChannelRequest;
    }

    public UpdateEmailChannelRequest withEmailChannelRequest(EmailChannelRequest emailChannelRequest) {
        this.emailChannelRequest = emailChannelRequest;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getEmailChannelRequest() != null) {
            sb.append("EmailChannelRequest: " + getEmailChannelRequest());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getEmailChannelRequest() != null ? getEmailChannelRequest().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateEmailChannelRequest)) {
            return false;
        }
        UpdateEmailChannelRequest updateEmailChannelRequest = (UpdateEmailChannelRequest) obj;
        if ((updateEmailChannelRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (updateEmailChannelRequest.getApplicationId() != null && !updateEmailChannelRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((updateEmailChannelRequest.getEmailChannelRequest() == null) ^ (getEmailChannelRequest() == null)) {
            return false;
        }
        return updateEmailChannelRequest.getEmailChannelRequest() == null || updateEmailChannelRequest.getEmailChannelRequest().equals(getEmailChannelRequest());
    }
}
