package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class EndpointUser implements Serializable {
    private Map<String, List<String>> userAttributes;
    private String userId;

    public Map<String, List<String>> getUserAttributes() {
        return this.userAttributes;
    }

    public void setUserAttributes(Map<String, List<String>> map) {
        this.userAttributes = map;
    }

    public EndpointUser withUserAttributes(Map<String, List<String>> map) {
        this.userAttributes = map;
        return this;
    }

    public EndpointUser addUserAttributesEntry(String str, List<String> list) {
        if (this.userAttributes == null) {
            this.userAttributes = new HashMap();
        }
        if (this.userAttributes.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.userAttributes.put(str, list);
        return this;
    }

    public EndpointUser clearUserAttributesEntries() {
        this.userAttributes = null;
        return this;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public EndpointUser withUserId(String str) {
        this.userId = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getUserAttributes() != null) {
            sb.append("UserAttributes: " + getUserAttributes() + ",");
        }
        if (getUserId() != null) {
            sb.append("UserId: " + getUserId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getUserAttributes() == null ? 0 : getUserAttributes().hashCode()) + 31) * 31) + (getUserId() != null ? getUserId().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EndpointUser)) {
            return false;
        }
        EndpointUser endpointUser = (EndpointUser) obj;
        if ((endpointUser.getUserAttributes() == null) ^ (getUserAttributes() == null)) {
            return false;
        }
        if (endpointUser.getUserAttributes() != null && !endpointUser.getUserAttributes().equals(getUserAttributes())) {
            return false;
        }
        if ((endpointUser.getUserId() == null) ^ (getUserId() == null)) {
            return false;
        }
        return endpointUser.getUserId() == null || endpointUser.getUserId().equals(getUserId());
    }
}
