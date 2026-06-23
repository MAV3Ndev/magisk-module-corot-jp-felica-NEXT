package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class PutEventStreamRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private WriteEventStream writeEventStream;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public PutEventStreamRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public WriteEventStream getWriteEventStream() {
        return this.writeEventStream;
    }

    public void setWriteEventStream(WriteEventStream writeEventStream) {
        this.writeEventStream = writeEventStream;
    }

    public PutEventStreamRequest withWriteEventStream(WriteEventStream writeEventStream) {
        this.writeEventStream = writeEventStream;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getWriteEventStream() != null) {
            sb.append("WriteEventStream: " + getWriteEventStream());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getWriteEventStream() != null ? getWriteEventStream().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof PutEventStreamRequest)) {
            return false;
        }
        PutEventStreamRequest putEventStreamRequest = (PutEventStreamRequest) obj;
        if ((putEventStreamRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (putEventStreamRequest.getApplicationId() != null && !putEventStreamRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((putEventStreamRequest.getWriteEventStream() == null) ^ (getWriteEventStream() == null)) {
            return false;
        }
        return putEventStreamRequest.getWriteEventStream() == null || putEventStreamRequest.getWriteEventStream().equals(getWriteEventStream());
    }
}
