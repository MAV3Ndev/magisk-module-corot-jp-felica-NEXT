package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class DeleteApnsChannelRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public DeleteApnsChannelRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getApplicationId() == null ? 0 : getApplicationId().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DeleteApnsChannelRequest)) {
            return false;
        }
        DeleteApnsChannelRequest deleteApnsChannelRequest = (DeleteApnsChannelRequest) obj;
        if ((deleteApnsChannelRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        return deleteApnsChannelRequest.getApplicationId() == null || deleteApnsChannelRequest.getApplicationId().equals(getApplicationId());
    }
}
