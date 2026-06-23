package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class RemoveAttributesRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private String attributeType;
    private UpdateAttributesRequest updateAttributesRequest;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public RemoveAttributesRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getAttributeType() {
        return this.attributeType;
    }

    public void setAttributeType(String str) {
        this.attributeType = str;
    }

    public RemoveAttributesRequest withAttributeType(String str) {
        this.attributeType = str;
        return this;
    }

    public UpdateAttributesRequest getUpdateAttributesRequest() {
        return this.updateAttributesRequest;
    }

    public void setUpdateAttributesRequest(UpdateAttributesRequest updateAttributesRequest) {
        this.updateAttributesRequest = updateAttributesRequest;
    }

    public RemoveAttributesRequest withUpdateAttributesRequest(UpdateAttributesRequest updateAttributesRequest) {
        this.updateAttributesRequest = updateAttributesRequest;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getAttributeType() != null) {
            sb.append("AttributeType: " + getAttributeType() + ",");
        }
        if (getUpdateAttributesRequest() != null) {
            sb.append("UpdateAttributesRequest: " + getUpdateAttributesRequest());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getAttributeType() == null ? 0 : getAttributeType().hashCode())) * 31) + (getUpdateAttributesRequest() != null ? getUpdateAttributesRequest().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof RemoveAttributesRequest)) {
            return false;
        }
        RemoveAttributesRequest removeAttributesRequest = (RemoveAttributesRequest) obj;
        if ((removeAttributesRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (removeAttributesRequest.getApplicationId() != null && !removeAttributesRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((removeAttributesRequest.getAttributeType() == null) ^ (getAttributeType() == null)) {
            return false;
        }
        if (removeAttributesRequest.getAttributeType() != null && !removeAttributesRequest.getAttributeType().equals(getAttributeType())) {
            return false;
        }
        if ((removeAttributesRequest.getUpdateAttributesRequest() == null) ^ (getUpdateAttributesRequest() == null)) {
            return false;
        }
        return removeAttributesRequest.getUpdateAttributesRequest() == null || removeAttributesRequest.getUpdateAttributesRequest().equals(getUpdateAttributesRequest());
    }
}
