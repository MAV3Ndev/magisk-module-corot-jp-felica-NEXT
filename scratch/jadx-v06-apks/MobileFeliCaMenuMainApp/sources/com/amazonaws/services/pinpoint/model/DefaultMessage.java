package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class DefaultMessage implements Serializable {
    private String body;
    private Map<String, List<String>> substitutions;

    public String getBody() {
        return this.body;
    }

    public void setBody(String str) {
        this.body = str;
    }

    public DefaultMessage withBody(String str) {
        this.body = str;
        return this;
    }

    public Map<String, List<String>> getSubstitutions() {
        return this.substitutions;
    }

    public void setSubstitutions(Map<String, List<String>> map) {
        this.substitutions = map;
    }

    public DefaultMessage withSubstitutions(Map<String, List<String>> map) {
        this.substitutions = map;
        return this;
    }

    public DefaultMessage addSubstitutionsEntry(String str, List<String> list) {
        if (this.substitutions == null) {
            this.substitutions = new HashMap();
        }
        if (this.substitutions.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.substitutions.put(str, list);
        return this;
    }

    public DefaultMessage clearSubstitutionsEntries() {
        this.substitutions = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getBody() != null) {
            sb.append("Body: " + getBody() + ",");
        }
        if (getSubstitutions() != null) {
            sb.append("Substitutions: " + getSubstitutions());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getBody() == null ? 0 : getBody().hashCode()) + 31) * 31) + (getSubstitutions() != null ? getSubstitutions().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DefaultMessage)) {
            return false;
        }
        DefaultMessage defaultMessage = (DefaultMessage) obj;
        if ((defaultMessage.getBody() == null) ^ (getBody() == null)) {
            return false;
        }
        if (defaultMessage.getBody() != null && !defaultMessage.getBody().equals(getBody())) {
            return false;
        }
        if ((defaultMessage.getSubstitutions() == null) ^ (getSubstitutions() == null)) {
            return false;
        }
        return defaultMessage.getSubstitutions() == null || defaultMessage.getSubstitutions().equals(getSubstitutions());
    }
}
