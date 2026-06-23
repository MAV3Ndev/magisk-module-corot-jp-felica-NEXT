package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class DeleteGcmChannelResult implements Serializable {
    private GCMChannelResponse gCMChannelResponse;

    public GCMChannelResponse getGCMChannelResponse() {
        return this.gCMChannelResponse;
    }

    public void setGCMChannelResponse(GCMChannelResponse gCMChannelResponse) {
        this.gCMChannelResponse = gCMChannelResponse;
    }

    public DeleteGcmChannelResult withGCMChannelResponse(GCMChannelResponse gCMChannelResponse) {
        this.gCMChannelResponse = gCMChannelResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getGCMChannelResponse() != null) {
            sb.append("GCMChannelResponse: " + getGCMChannelResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getGCMChannelResponse() == null ? 0 : getGCMChannelResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DeleteGcmChannelResult)) {
            return false;
        }
        DeleteGcmChannelResult deleteGcmChannelResult = (DeleteGcmChannelResult) obj;
        if ((deleteGcmChannelResult.getGCMChannelResponse() == null) ^ (getGCMChannelResponse() == null)) {
            return false;
        }
        return deleteGcmChannelResult.getGCMChannelResponse() == null || deleteGcmChannelResult.getGCMChannelResponse().equals(getGCMChannelResponse());
    }
}
