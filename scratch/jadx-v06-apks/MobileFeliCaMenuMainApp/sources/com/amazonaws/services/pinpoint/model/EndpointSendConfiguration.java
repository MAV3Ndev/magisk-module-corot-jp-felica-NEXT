package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class EndpointSendConfiguration implements Serializable {
    private String bodyOverride;
    private Map<String, String> context;
    private String rawContent;
    private Map<String, List<String>> substitutions;
    private String titleOverride;

    public String getBodyOverride() {
        return this.bodyOverride;
    }

    public void setBodyOverride(String str) {
        this.bodyOverride = str;
    }

    public EndpointSendConfiguration withBodyOverride(String str) {
        this.bodyOverride = str;
        return this;
    }

    public Map<String, String> getContext() {
        return this.context;
    }

    public void setContext(Map<String, String> map) {
        this.context = map;
    }

    public EndpointSendConfiguration withContext(Map<String, String> map) {
        this.context = map;
        return this;
    }

    public EndpointSendConfiguration addContextEntry(String str, String str2) {
        if (this.context == null) {
            this.context = new HashMap();
        }
        if (this.context.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.context.put(str, str2);
        return this;
    }

    public EndpointSendConfiguration clearContextEntries() {
        this.context = null;
        return this;
    }

    public String getRawContent() {
        return this.rawContent;
    }

    public void setRawContent(String str) {
        this.rawContent = str;
    }

    public EndpointSendConfiguration withRawContent(String str) {
        this.rawContent = str;
        return this;
    }

    public Map<String, List<String>> getSubstitutions() {
        return this.substitutions;
    }

    public void setSubstitutions(Map<String, List<String>> map) {
        this.substitutions = map;
    }

    public EndpointSendConfiguration withSubstitutions(Map<String, List<String>> map) {
        this.substitutions = map;
        return this;
    }

    public EndpointSendConfiguration addSubstitutionsEntry(String str, List<String> list) {
        if (this.substitutions == null) {
            this.substitutions = new HashMap();
        }
        if (this.substitutions.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.substitutions.put(str, list);
        return this;
    }

    public EndpointSendConfiguration clearSubstitutionsEntries() {
        this.substitutions = null;
        return this;
    }

    public String getTitleOverride() {
        return this.titleOverride;
    }

    public void setTitleOverride(String str) {
        this.titleOverride = str;
    }

    public EndpointSendConfiguration withTitleOverride(String str) {
        this.titleOverride = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getBodyOverride() != null) {
            sb.append("BodyOverride: " + getBodyOverride() + ",");
        }
        if (getContext() != null) {
            sb.append("Context: " + getContext() + ",");
        }
        if (getRawContent() != null) {
            sb.append("RawContent: " + getRawContent() + ",");
        }
        if (getSubstitutions() != null) {
            sb.append("Substitutions: " + getSubstitutions() + ",");
        }
        if (getTitleOverride() != null) {
            sb.append("TitleOverride: " + getTitleOverride());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((getBodyOverride() == null ? 0 : getBodyOverride().hashCode()) + 31) * 31) + (getContext() == null ? 0 : getContext().hashCode())) * 31) + (getRawContent() == null ? 0 : getRawContent().hashCode())) * 31) + (getSubstitutions() == null ? 0 : getSubstitutions().hashCode())) * 31) + (getTitleOverride() != null ? getTitleOverride().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EndpointSendConfiguration)) {
            return false;
        }
        EndpointSendConfiguration endpointSendConfiguration = (EndpointSendConfiguration) obj;
        if ((endpointSendConfiguration.getBodyOverride() == null) ^ (getBodyOverride() == null)) {
            return false;
        }
        if (endpointSendConfiguration.getBodyOverride() != null && !endpointSendConfiguration.getBodyOverride().equals(getBodyOverride())) {
            return false;
        }
        if ((endpointSendConfiguration.getContext() == null) ^ (getContext() == null)) {
            return false;
        }
        if (endpointSendConfiguration.getContext() != null && !endpointSendConfiguration.getContext().equals(getContext())) {
            return false;
        }
        if ((endpointSendConfiguration.getRawContent() == null) ^ (getRawContent() == null)) {
            return false;
        }
        if (endpointSendConfiguration.getRawContent() != null && !endpointSendConfiguration.getRawContent().equals(getRawContent())) {
            return false;
        }
        if ((endpointSendConfiguration.getSubstitutions() == null) ^ (getSubstitutions() == null)) {
            return false;
        }
        if (endpointSendConfiguration.getSubstitutions() != null && !endpointSendConfiguration.getSubstitutions().equals(getSubstitutions())) {
            return false;
        }
        if ((endpointSendConfiguration.getTitleOverride() == null) ^ (getTitleOverride() == null)) {
            return false;
        }
        return endpointSendConfiguration.getTitleOverride() == null || endpointSendConfiguration.getTitleOverride().equals(getTitleOverride());
    }
}
