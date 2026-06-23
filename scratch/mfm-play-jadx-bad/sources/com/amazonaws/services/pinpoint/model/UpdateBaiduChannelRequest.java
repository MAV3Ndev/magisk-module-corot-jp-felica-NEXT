package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateBaiduChannelRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private BaiduChannelRequest baiduChannelRequest;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public UpdateBaiduChannelRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public BaiduChannelRequest getBaiduChannelRequest() {
        return this.baiduChannelRequest;
    }

    public void setBaiduChannelRequest(BaiduChannelRequest baiduChannelRequest) {
        this.baiduChannelRequest = baiduChannelRequest;
    }

    public UpdateBaiduChannelRequest withBaiduChannelRequest(BaiduChannelRequest baiduChannelRequest) {
        this.baiduChannelRequest = baiduChannelRequest;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getBaiduChannelRequest() != null) {
            sb.append("BaiduChannelRequest: " + getBaiduChannelRequest());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getBaiduChannelRequest() != null ? getBaiduChannelRequest().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateBaiduChannelRequest)) {
            return false;
        }
        UpdateBaiduChannelRequest updateBaiduChannelRequest = (UpdateBaiduChannelRequest) obj;
        if ((updateBaiduChannelRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (updateBaiduChannelRequest.getApplicationId() != null && !updateBaiduChannelRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((updateBaiduChannelRequest.getBaiduChannelRequest() == null) ^ (getBaiduChannelRequest() == null)) {
            return false;
        }
        return updateBaiduChannelRequest.getBaiduChannelRequest() == null || updateBaiduChannelRequest.getBaiduChannelRequest().equals(getBaiduChannelRequest());
    }
}
