package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class EndpointsResponse implements Serializable {
    private List<EndpointResponse> item;

    public List<EndpointResponse> getItem() {
        return this.item;
    }

    public void setItem(Collection<EndpointResponse> collection) {
        if (collection == null) {
            this.item = null;
        } else {
            this.item = new ArrayList(collection);
        }
    }

    public EndpointsResponse withItem(EndpointResponse... endpointResponseArr) {
        if (getItem() == null) {
            this.item = new ArrayList(endpointResponseArr.length);
        }
        for (EndpointResponse endpointResponse : endpointResponseArr) {
            this.item.add(endpointResponse);
        }
        return this;
    }

    public EndpointsResponse withItem(Collection<EndpointResponse> collection) {
        setItem(collection);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getItem() != null) {
            sb.append("Item: " + getItem());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getItem() == null ? 0 : getItem().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EndpointsResponse)) {
            return false;
        }
        EndpointsResponse endpointsResponse = (EndpointsResponse) obj;
        if ((endpointsResponse.getItem() == null) ^ (getItem() == null)) {
            return false;
        }
        return endpointsResponse.getItem() == null || endpointsResponse.getItem().equals(getItem());
    }
}
