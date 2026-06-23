package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class CreateAppRequest extends AmazonWebServiceRequest implements Serializable {
    private CreateApplicationRequest createApplicationRequest;

    public CreateApplicationRequest getCreateApplicationRequest() {
        return this.createApplicationRequest;
    }

    public void setCreateApplicationRequest(CreateApplicationRequest createApplicationRequest) {
        this.createApplicationRequest = createApplicationRequest;
    }

    public CreateAppRequest withCreateApplicationRequest(CreateApplicationRequest createApplicationRequest) {
        this.createApplicationRequest = createApplicationRequest;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getCreateApplicationRequest() != null) {
            sb.append("CreateApplicationRequest: " + getCreateApplicationRequest());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getCreateApplicationRequest() == null ? 0 : getCreateApplicationRequest().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CreateAppRequest)) {
            return false;
        }
        CreateAppRequest createAppRequest = (CreateAppRequest) obj;
        if ((createAppRequest.getCreateApplicationRequest() == null) ^ (getCreateApplicationRequest() == null)) {
            return false;
        }
        return createAppRequest.getCreateApplicationRequest() == null || createAppRequest.getCreateApplicationRequest().equals(getCreateApplicationRequest());
    }
}
