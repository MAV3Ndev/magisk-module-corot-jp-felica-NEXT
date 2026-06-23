package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class DefaultPushNotificationMessage implements Serializable {
    private String action;
    private String body;
    private Map<String, String> data;
    private Boolean silentPush;
    private Map<String, List<String>> substitutions;
    private String title;
    private String url;

    public String getAction() {
        return this.action;
    }

    public void setAction(String str) {
        this.action = str;
    }

    public DefaultPushNotificationMessage withAction(String str) {
        this.action = str;
        return this;
    }

    public void setAction(Action action) {
        this.action = action.toString();
    }

    public DefaultPushNotificationMessage withAction(Action action) {
        this.action = action.toString();
        return this;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String str) {
        this.body = str;
    }

    public DefaultPushNotificationMessage withBody(String str) {
        this.body = str;
        return this;
    }

    public Map<String, String> getData() {
        return this.data;
    }

    public void setData(Map<String, String> map) {
        this.data = map;
    }

    public DefaultPushNotificationMessage withData(Map<String, String> map) {
        this.data = map;
        return this;
    }

    public DefaultPushNotificationMessage addDataEntry(String str, String str2) {
        if (this.data == null) {
            this.data = new HashMap();
        }
        if (this.data.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.data.put(str, str2);
        return this;
    }

    public DefaultPushNotificationMessage clearDataEntries() {
        this.data = null;
        return this;
    }

    public Boolean isSilentPush() {
        return this.silentPush;
    }

    public Boolean getSilentPush() {
        return this.silentPush;
    }

    public void setSilentPush(Boolean bool) {
        this.silentPush = bool;
    }

    public DefaultPushNotificationMessage withSilentPush(Boolean bool) {
        this.silentPush = bool;
        return this;
    }

    public Map<String, List<String>> getSubstitutions() {
        return this.substitutions;
    }

    public void setSubstitutions(Map<String, List<String>> map) {
        this.substitutions = map;
    }

    public DefaultPushNotificationMessage withSubstitutions(Map<String, List<String>> map) {
        this.substitutions = map;
        return this;
    }

    public DefaultPushNotificationMessage addSubstitutionsEntry(String str, List<String> list) {
        if (this.substitutions == null) {
            this.substitutions = new HashMap();
        }
        if (this.substitutions.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.substitutions.put(str, list);
        return this;
    }

    public DefaultPushNotificationMessage clearSubstitutionsEntries() {
        this.substitutions = null;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public DefaultPushNotificationMessage withTitle(String str) {
        this.title = str;
        return this;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public DefaultPushNotificationMessage withUrl(String str) {
        this.url = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getAction() != null) {
            sb.append("Action: " + getAction() + ",");
        }
        if (getBody() != null) {
            sb.append("Body: " + getBody() + ",");
        }
        if (getData() != null) {
            sb.append("Data: " + getData() + ",");
        }
        if (getSilentPush() != null) {
            sb.append("SilentPush: " + getSilentPush() + ",");
        }
        if (getSubstitutions() != null) {
            sb.append("Substitutions: " + getSubstitutions() + ",");
        }
        if (getTitle() != null) {
            sb.append("Title: " + getTitle() + ",");
        }
        if (getUrl() != null) {
            sb.append("Url: " + getUrl());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((((getAction() == null ? 0 : getAction().hashCode()) + 31) * 31) + (getBody() == null ? 0 : getBody().hashCode())) * 31) + (getData() == null ? 0 : getData().hashCode())) * 31) + (getSilentPush() == null ? 0 : getSilentPush().hashCode())) * 31) + (getSubstitutions() == null ? 0 : getSubstitutions().hashCode())) * 31) + (getTitle() == null ? 0 : getTitle().hashCode())) * 31) + (getUrl() != null ? getUrl().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DefaultPushNotificationMessage)) {
            return false;
        }
        DefaultPushNotificationMessage defaultPushNotificationMessage = (DefaultPushNotificationMessage) obj;
        if ((defaultPushNotificationMessage.getAction() == null) ^ (getAction() == null)) {
            return false;
        }
        if (defaultPushNotificationMessage.getAction() != null && !defaultPushNotificationMessage.getAction().equals(getAction())) {
            return false;
        }
        if ((defaultPushNotificationMessage.getBody() == null) ^ (getBody() == null)) {
            return false;
        }
        if (defaultPushNotificationMessage.getBody() != null && !defaultPushNotificationMessage.getBody().equals(getBody())) {
            return false;
        }
        if ((defaultPushNotificationMessage.getData() == null) ^ (getData() == null)) {
            return false;
        }
        if (defaultPushNotificationMessage.getData() != null && !defaultPushNotificationMessage.getData().equals(getData())) {
            return false;
        }
        if ((defaultPushNotificationMessage.getSilentPush() == null) ^ (getSilentPush() == null)) {
            return false;
        }
        if (defaultPushNotificationMessage.getSilentPush() != null && !defaultPushNotificationMessage.getSilentPush().equals(getSilentPush())) {
            return false;
        }
        if ((defaultPushNotificationMessage.getSubstitutions() == null) ^ (getSubstitutions() == null)) {
            return false;
        }
        if (defaultPushNotificationMessage.getSubstitutions() != null && !defaultPushNotificationMessage.getSubstitutions().equals(getSubstitutions())) {
            return false;
        }
        if ((defaultPushNotificationMessage.getTitle() == null) ^ (getTitle() == null)) {
            return false;
        }
        if (defaultPushNotificationMessage.getTitle() != null && !defaultPushNotificationMessage.getTitle().equals(getTitle())) {
            return false;
        }
        if ((defaultPushNotificationMessage.getUrl() == null) ^ (getUrl() == null)) {
            return false;
        }
        return defaultPushNotificationMessage.getUrl() == null || defaultPushNotificationMessage.getUrl().equals(getUrl());
    }
}
