package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class DeleteApnsVoipChannelRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public DeleteApnsVoipChannelRequest withApplicationId(String str) {
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
        if (obj == null || !(obj instanceof DeleteApnsVoipChannelRequest)) {
            return false;
        }
        DeleteApnsVoipChannelRequest deleteApnsVoipChannelRequest = (DeleteApnsVoipChannelRequest) obj;
        if ((deleteApnsVoipChannelRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        return deleteApnsVoipChannelRequest.getApplicationId() == null || deleteApnsVoipChannelRequest.getApplicationId().equals(getApplicationId());
    }
}
