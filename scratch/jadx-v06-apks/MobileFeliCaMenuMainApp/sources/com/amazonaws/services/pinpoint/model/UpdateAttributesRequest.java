package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class UpdateAttributesRequest implements Serializable {
    private List<String> blacklist;

    public List<String> getBlacklist() {
        return this.blacklist;
    }

    public void setBlacklist(Collection<String> collection) {
        if (collection == null) {
            this.blacklist = null;
        } else {
            this.blacklist = new ArrayList(collection);
        }
    }

    public UpdateAttributesRequest withBlacklist(String... strArr) {
        if (getBlacklist() == null) {
            this.blacklist = new ArrayList(strArr.length);
        }
        for (String str : strArr) {
            this.blacklist.add(str);
        }
        return this;
    }

    public UpdateAttributesRequest withBlacklist(Collection<String> collection) {
        setBlacklist(collection);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getBlacklist() != null) {
            sb.append("Blacklist: " + getBlacklist());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getBlacklist() == null ? 0 : getBlacklist().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateAttributesRequest)) {
            return false;
        }
        UpdateAttributesRequest updateAttributesRequest = (UpdateAttributesRequest) obj;
        if ((updateAttributesRequest.getBlacklist() == null) ^ (getBlacklist() == null)) {
            return false;
        }
        return updateAttributesRequest.getBlacklist() == null || updateAttributesRequest.getBlacklist().equals(getBlacklist());
    }
}
