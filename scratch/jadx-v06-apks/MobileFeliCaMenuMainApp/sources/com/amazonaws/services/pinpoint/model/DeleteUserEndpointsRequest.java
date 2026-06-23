package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class DeleteUserEndpointsRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private String userId;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public DeleteUserEndpointsRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public DeleteUserEndpointsRequest withUserId(String str) {
        this.userId = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getUserId() != null) {
            sb.append("UserId: " + getUserId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getUserId() != null ? getUserId().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DeleteUserEndpointsRequest)) {
            return false;
        }
        DeleteUserEndpointsRequest deleteUserEndpointsRequest = (DeleteUserEndpointsRequest) obj;
        if ((deleteUserEndpointsRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (deleteUserEndpointsRequest.getApplicationId() != null && !deleteUserEndpointsRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((deleteUserEndpointsRequest.getUserId() == null) ^ (getUserId() == null)) {
            return false;
        }
        return deleteUserEndpointsRequest.getUserId() == null || deleteUserEndpointsRequest.getUserId().equals(getUserId());
    }
}
