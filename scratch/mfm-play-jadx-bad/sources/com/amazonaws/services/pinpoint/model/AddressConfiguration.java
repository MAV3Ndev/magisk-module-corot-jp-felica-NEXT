package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class AddressConfiguration implements Serializable {
    private String bodyOverride;
    private String channelType;
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

    public AddressConfiguration withBodyOverride(String str) {
        this.bodyOverride = str;
        return this;
    }

    public String getChannelType() {
        return this.channelType;
    }

    public void setChannelType(String str) {
        this.channelType = str;
    }

    public AddressConfiguration withChannelType(String str) {
        this.channelType = str;
        return this;
    }

    public void setChannelType(ChannelType channelType) {
        this.channelType = channelType.toString();
    }

    public AddressConfiguration withChannelType(ChannelType channelType) {
        this.channelType = channelType.toString();
        return this;
    }

    public Map<String, String> getContext() {
        return this.context;
    }

    public void setContext(Map<String, String> map) {
        this.context = map;
    }

    public AddressConfiguration withContext(Map<String, String> map) {
        this.context = map;
        return this;
    }

    public AddressConfiguration addContextEntry(String str, String str2) {
        if (this.context == null) {
            this.context = new HashMap();
        }
        if (this.context.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.context.put(str, str2);
        return this;
    }

    public AddressConfiguration clearContextEntries() {
        this.context = null;
        return this;
    }

    public String getRawContent() {
        return this.rawContent;
    }

    public void setRawContent(String str) {
        this.rawContent = str;
    }

    public AddressConfiguration withRawContent(String str) {
        this.rawContent = str;
        return this;
    }

    public Map<String, List<String>> getSubstitutions() {
        return this.substitutions;
    }

    public void setSubstitutions(Map<String, List<String>> map) {
        this.substitutions = map;
    }

    public AddressConfiguration withSubstitutions(Map<String, List<String>> map) {
        this.substitutions = map;
        return this;
    }

    public AddressConfiguration addSubstitutionsEntry(String str, List<String> list) {
        if (this.substitutions == null) {
            this.substitutions = new HashMap();
        }
        if (this.substitutions.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.substitutions.put(str, list);
        return this;
    }

    public AddressConfiguration clearSubstitutionsEntries() {
        this.substitutions = null;
        return this;
    }

    public String getTitleOverride() {
        return this.titleOverride;
    }

    public void setTitleOverride(String str) {
        this.titleOverride = str;
    }

    public AddressConfiguration withTitleOverride(String str) {
        this.titleOverride = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getBodyOverride() != null) {
            sb.append("BodyOverride: " + getBodyOverride() + ",");
        }
        if (getChannelType() != null) {
            sb.append("ChannelType: " + getChannelType() + ",");
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
        return (((((((((((getBodyOverride() == null ? 0 : getBodyOverride().hashCode()) + 31) * 31) + (getChannelType() == null ? 0 : getChannelType().hashCode())) * 31) + (getContext() == null ? 0 : getContext().hashCode())) * 31) + (getRawContent() == null ? 0 : getRawContent().hashCode())) * 31) + (getSubstitutions() == null ? 0 : getSubstitutions().hashCode())) * 31) + (getTitleOverride() != null ? getTitleOverride().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AddressConfiguration)) {
            return false;
        }
        AddressConfiguration addressConfiguration = (AddressConfiguration) obj;
        if ((addressConfiguration.getBodyOverride() == null) ^ (getBodyOverride() == null)) {
            return false;
        }
        if (addressConfiguration.getBodyOverride() != null && !addressConfiguration.getBodyOverride().equals(getBodyOverride())) {
            return false;
        }
        if ((addressConfiguration.getChannelType() == null) ^ (getChannelType() == null)) {
            return false;
        }
        if (addressConfiguration.getChannelType() != null && !addressConfiguration.getChannelType().equals(getChannelType())) {
            return false;
        }
        if ((addressConfiguration.getContext() == null) ^ (getContext() == null)) {
            return false;
        }
        if (addressConfiguration.getContext() != null && !addressConfiguration.getContext().equals(getContext())) {
            return false;
        }
        if ((addressConfiguration.getRawContent() == null) ^ (getRawContent() == null)) {
            return false;
        }
        if (addressConfiguration.getRawContent() != null && !addressConfiguration.getRawContent().equals(getRawContent())) {
            return false;
        }
        if ((addressConfiguration.getSubstitutions() == null) ^ (getSubstitutions() == null)) {
            return false;
        }
        if (addressConfiguration.getSubstitutions() != null && !addressConfiguration.getSubstitutions().equals(getSubstitutions())) {
            return false;
        }
        if ((addressConfiguration.getTitleOverride() == null) ^ (getTitleOverride() == null)) {
            return false;
        }
        return addressConfiguration.getTitleOverride() == null || addressConfiguration.getTitleOverride().equals(getTitleOverride());
    }
}
