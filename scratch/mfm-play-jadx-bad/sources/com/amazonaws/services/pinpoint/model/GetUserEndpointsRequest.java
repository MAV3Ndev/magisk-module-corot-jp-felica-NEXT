package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetUserEndpointsRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private String userId;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public GetUserEndpointsRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public GetUserEndpointsRequest withUserId(String str) {
        this.userId = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
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
        if (obj == null || !(obj instanceof GetUserEndpointsRequest)) {
            return false;
        }
        GetUserEndpointsRequest getUserEndpointsRequest = (GetUserEndpointsRequest) obj;
        if ((getUserEndpointsRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (getUserEndpointsRequest.getApplicationId() != null && !getUserEndpointsRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((getUserEndpointsRequest.getUserId() == null) ^ (getUserId() == null)) {
            return false;
        }
        return getUserEndpointsRequest.getUserId() == null || getUserEndpointsRequest.getUserId().equals(getUserId());
    }
}
