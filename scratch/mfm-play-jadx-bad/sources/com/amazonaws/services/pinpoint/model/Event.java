package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class Event implements Serializable {
    private String appPackageName;
    private String appTitle;
    private String appVersionCode;
    private Map<String, String> attributes;
    private String clientSdkVersion;
    private String eventType;
    private Map<String, Double> metrics;
    private String sdkName;
    private Session session;
    private String timestamp;

    public String getAppPackageName() {
        return this.appPackageName;
    }

    public void setAppPackageName(String str) {
        this.appPackageName = str;
    }

    public Event withAppPackageName(String str) {
        this.appPackageName = str;
        return this;
    }

    public String getAppTitle() {
        return this.appTitle;
    }

    public void setAppTitle(String str) {
        this.appTitle = str;
    }

    public Event withAppTitle(String str) {
        this.appTitle = str;
        return this;
    }

    public String getAppVersionCode() {
        return this.appVersionCode;
    }

    public void setAppVersionCode(String str) {
        this.appVersionCode = str;
    }

    public Event withAppVersionCode(String str) {
        this.appVersionCode = str;
        return this;
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, String> map) {
        this.attributes = map;
    }

    public Event withAttributes(Map<String, String> map) {
        this.attributes = map;
        return this;
    }

    public Event addAttributesEntry(String str, String str2) {
        if (this.attributes == null) {
            this.attributes = new HashMap();
        }
        if (this.attributes.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.attributes.put(str, str2);
        return this;
    }

    public Event clearAttributesEntries() {
        this.attributes = null;
        return this;
    }

    public String getClientSdkVersion() {
        return this.clientSdkVersion;
    }

    public void setClientSdkVersion(String str) {
        this.clientSdkVersion = str;
    }

    public Event withClientSdkVersion(String str) {
        this.clientSdkVersion = str;
        return this;
    }

    public String getEventType() {
        return this.eventType;
    }

    public void setEventType(String str) {
        this.eventType = str;
    }

    public Event withEventType(String str) {
        this.eventType = str;
        return this;
    }

    public Map<String, Double> getMetrics() {
        return this.metrics;
    }

    public void setMetrics(Map<String, Double> map) {
        this.metrics = map;
    }

    public Event withMetrics(Map<String, Double> map) {
        this.metrics = map;
        return this;
    }

    public Event addMetricsEntry(String str, Double d) {
        if (this.metrics == null) {
            this.metrics = new HashMap();
        }
        if (this.metrics.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.metrics.put(str, d);
        return this;
    }

    public Event clearMetricsEntries() {
        this.metrics = null;
        return this;
    }

    public String getSdkName() {
        return this.sdkName;
    }

    public void setSdkName(String str) {
        this.sdkName = str;
    }

    public Event withSdkName(String str) {
        this.sdkName = str;
        return this;
    }

    public Session getSession() {
        return this.session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Event withSession(Session session) {
        this.session = session;
        return this;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String str) {
        this.timestamp = str;
    }

    public Event withTimestamp(String str) {
        this.timestamp = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getAppPackageName() != null) {
            sb.append("AppPackageName: " + getAppPackageName() + ",");
        }
        if (getAppTitle() != null) {
            sb.append("AppTitle: " + getAppTitle() + ",");
        }
        if (getAppVersionCode() != null) {
            sb.append("AppVersionCode: " + getAppVersionCode() + ",");
        }
        if (getAttributes() != null) {
            sb.append("Attributes: " + getAttributes() + ",");
        }
        if (getClientSdkVersion() != null) {
            sb.append("ClientSdkVersion: " + getClientSdkVersion() + ",");
        }
        if (getEventType() != null) {
            sb.append("EventType: " + getEventType() + ",");
        }
        if (getMetrics() != null) {
            sb.append("Metrics: " + getMetrics() + ",");
        }
        if (getSdkName() != null) {
            sb.append("SdkName: " + getSdkName() + ",");
        }
        if (getSession() != null) {
            sb.append("Session: " + getSession() + ",");
        }
        if (getTimestamp() != null) {
            sb.append("Timestamp: " + getTimestamp());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((((((((((getAppPackageName() == null ? 0 : getAppPackageName().hashCode()) + 31) * 31) + (getAppTitle() == null ? 0 : getAppTitle().hashCode())) * 31) + (getAppVersionCode() == null ? 0 : getAppVersionCode().hashCode())) * 31) + (getAttributes() == null ? 0 : getAttributes().hashCode())) * 31) + (getClientSdkVersion() == null ? 0 : getClientSdkVersion().hashCode())) * 31) + (getEventType() == null ? 0 : getEventType().hashCode())) * 31) + (getMetrics() == null ? 0 : getMetrics().hashCode())) * 31) + (getSdkName() == null ? 0 : getSdkName().hashCode())) * 31) + (getSession() == null ? 0 : getSession().hashCode())) * 31) + (getTimestamp() != null ? getTimestamp().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Event)) {
            return false;
        }
        Event event = (Event) obj;
        if ((event.getAppPackageName() == null) ^ (getAppPackageName() == null)) {
            return false;
        }
        if (event.getAppPackageName() != null && !event.getAppPackageName().equals(getAppPackageName())) {
            return false;
        }
        if ((event.getAppTitle() == null) ^ (getAppTitle() == null)) {
            return false;
        }
        if (event.getAppTitle() != null && !event.getAppTitle().equals(getAppTitle())) {
            return false;
        }
        if ((event.getAppVersionCode() == null) ^ (getAppVersionCode() == null)) {
            return false;
        }
        if (event.getAppVersionCode() != null && !event.getAppVersionCode().equals(getAppVersionCode())) {
            return false;
        }
        if ((event.getAttributes() == null) ^ (getAttributes() == null)) {
            return false;
        }
        if (event.getAttributes() != null && !event.getAttributes().equals(getAttributes())) {
            return false;
        }
        if ((event.getClientSdkVersion() == null) ^ (getClientSdkVersion() == null)) {
            return false;
        }
        if (event.getClientSdkVersion() != null && !event.getClientSdkVersion().equals(getClientSdkVersion())) {
            return false;
        }
        if ((event.getEventType() == null) ^ (getEventType() == null)) {
            return false;
        }
        if (event.getEventType() != null && !event.getEventType().equals(getEventType())) {
            return false;
        }
        if ((event.getMetrics() == null) ^ (getMetrics() == null)) {
            return false;
        }
        if (event.getMetrics() != null && !event.getMetrics().equals(getMetrics())) {
            return false;
        }
        if ((event.getSdkName() == null) ^ (getSdkName() == null)) {
            return false;
        }
        if (event.getSdkName() != null && !event.getSdkName().equals(getSdkName())) {
            return false;
        }
        if ((event.getSession() == null) ^ (getSession() == null)) {
            return false;
        }
        if (event.getSession() != null && !event.getSession().equals(getSession())) {
            return false;
        }
        if ((event.getTimestamp() == null) ^ (getTimestamp() == null)) {
            return false;
        }
        return event.getTimestamp() == null || event.getTimestamp().equals(getTimestamp());
    }
}
