package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class APNSMessage implements Serializable {
    private String action;
    private Integer badge;
    private String body;
    private String category;
    private String collapseId;
    private Map<String, String> data;
    private String mediaUrl;
    private String preferredAuthenticationMethod;
    private String priority;
    private String rawContent;
    private Boolean silentPush;
    private String sound;
    private Map<String, List<String>> substitutions;
    private String threadId;
    private Integer timeToLive;
    private String title;
    private String url;

    public String getAction() {
        return this.action;
    }

    public void setAction(String str) {
        this.action = str;
    }

    public APNSMessage withAction(String str) {
        this.action = str;
        return this;
    }

    public void setAction(Action action) {
        this.action = action.toString();
    }

    public APNSMessage withAction(Action action) {
        this.action = action.toString();
        return this;
    }

    public Integer getBadge() {
        return this.badge;
    }

    public void setBadge(Integer num) {
        this.badge = num;
    }

    public APNSMessage withBadge(Integer num) {
        this.badge = num;
        return this;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String str) {
        this.body = str;
    }

    public APNSMessage withBody(String str) {
        this.body = str;
        return this;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String str) {
        this.category = str;
    }

    public APNSMessage withCategory(String str) {
        this.category = str;
        return this;
    }

    public String getCollapseId() {
        return this.collapseId;
    }

    public void setCollapseId(String str) {
        this.collapseId = str;
    }

    public APNSMessage withCollapseId(String str) {
        this.collapseId = str;
        return this;
    }

    public Map<String, String> getData() {
        return this.data;
    }

    public void setData(Map<String, String> map) {
        this.data = map;
    }

    public APNSMessage withData(Map<String, String> map) {
        this.data = map;
        return this;
    }

    public APNSMessage addDataEntry(String str, String str2) {
        if (this.data == null) {
            this.data = new HashMap();
        }
        if (this.data.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.data.put(str, str2);
        return this;
    }

    public APNSMessage clearDataEntries() {
        this.data = null;
        return this;
    }

    public String getMediaUrl() {
        return this.mediaUrl;
    }

    public void setMediaUrl(String str) {
        this.mediaUrl = str;
    }

    public APNSMessage withMediaUrl(String str) {
        this.mediaUrl = str;
        return this;
    }

    public String getPreferredAuthenticationMethod() {
        return this.preferredAuthenticationMethod;
    }

    public void setPreferredAuthenticationMethod(String str) {
        this.preferredAuthenticationMethod = str;
    }

    public APNSMessage withPreferredAuthenticationMethod(String str) {
        this.preferredAuthenticationMethod = str;
        return this;
    }

    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String str) {
        this.priority = str;
    }

    public APNSMessage withPriority(String str) {
        this.priority = str;
        return this;
    }

    public String getRawContent() {
        return this.rawContent;
    }

    public void setRawContent(String str) {
        this.rawContent = str;
    }

    public APNSMessage withRawContent(String str) {
        this.rawContent = str;
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

    public APNSMessage withSilentPush(Boolean bool) {
        this.silentPush = bool;
        return this;
    }

    public String getSound() {
        return this.sound;
    }

    public void setSound(String str) {
        this.sound = str;
    }

    public APNSMessage withSound(String str) {
        this.sound = str;
        return this;
    }

    public Map<String, List<String>> getSubstitutions() {
        return this.substitutions;
    }

    public void setSubstitutions(Map<String, List<String>> map) {
        this.substitutions = map;
    }

    public APNSMessage withSubstitutions(Map<String, List<String>> map) {
        this.substitutions = map;
        return this;
    }

    public APNSMessage addSubstitutionsEntry(String str, List<String> list) {
        if (this.substitutions == null) {
            this.substitutions = new HashMap();
        }
        if (this.substitutions.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.substitutions.put(str, list);
        return this;
    }

    public APNSMessage clearSubstitutionsEntries() {
        this.substitutions = null;
        return this;
    }

    public String getThreadId() {
        return this.threadId;
    }

    public void setThreadId(String str) {
        this.threadId = str;
    }

    public APNSMessage withThreadId(String str) {
        this.threadId = str;
        return this;
    }

    public Integer getTimeToLive() {
        return this.timeToLive;
    }

    public void setTimeToLive(Integer num) {
        this.timeToLive = num;
    }

    public APNSMessage withTimeToLive(Integer num) {
        this.timeToLive = num;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public APNSMessage withTitle(String str) {
        this.title = str;
        return this;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public APNSMessage withUrl(String str) {
        this.url = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAction() != null) {
            sb.append("Action: " + getAction() + ",");
        }
        if (getBadge() != null) {
            sb.append("Badge: " + getBadge() + ",");
        }
        if (getBody() != null) {
            sb.append("Body: " + getBody() + ",");
        }
        if (getCategory() != null) {
            sb.append("Category: " + getCategory() + ",");
        }
        if (getCollapseId() != null) {
            sb.append("CollapseId: " + getCollapseId() + ",");
        }
        if (getData() != null) {
            sb.append("Data: " + getData() + ",");
        }
        if (getMediaUrl() != null) {
            sb.append("MediaUrl: " + getMediaUrl() + ",");
        }
        if (getPreferredAuthenticationMethod() != null) {
            sb.append("PreferredAuthenticationMethod: " + getPreferredAuthenticationMethod() + ",");
        }
        if (getPriority() != null) {
            sb.append("Priority: " + getPriority() + ",");
        }
        if (getRawContent() != null) {
            sb.append("RawContent: " + getRawContent() + ",");
        }
        if (getSilentPush() != null) {
            sb.append("SilentPush: " + getSilentPush() + ",");
        }
        if (getSound() != null) {
            sb.append("Sound: " + getSound() + ",");
        }
        if (getSubstitutions() != null) {
            sb.append("Substitutions: " + getSubstitutions() + ",");
        }
        if (getThreadId() != null) {
            sb.append("ThreadId: " + getThreadId() + ",");
        }
        if (getTimeToLive() != null) {
            sb.append("TimeToLive: " + getTimeToLive() + ",");
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
        return (((((((((((((((((((((((((((((((((getAction() == null ? 0 : getAction().hashCode()) + 31) * 31) + (getBadge() == null ? 0 : getBadge().hashCode())) * 31) + (getBody() == null ? 0 : getBody().hashCode())) * 31) + (getCategory() == null ? 0 : getCategory().hashCode())) * 31) + (getCollapseId() == null ? 0 : getCollapseId().hashCode())) * 31) + (getData() == null ? 0 : getData().hashCode())) * 31) + (getMediaUrl() == null ? 0 : getMediaUrl().hashCode())) * 31) + (getPreferredAuthenticationMethod() == null ? 0 : getPreferredAuthenticationMethod().hashCode())) * 31) + (getPriority() == null ? 0 : getPriority().hashCode())) * 31) + (getRawContent() == null ? 0 : getRawContent().hashCode())) * 31) + (getSilentPush() == null ? 0 : getSilentPush().hashCode())) * 31) + (getSound() == null ? 0 : getSound().hashCode())) * 31) + (getSubstitutions() == null ? 0 : getSubstitutions().hashCode())) * 31) + (getThreadId() == null ? 0 : getThreadId().hashCode())) * 31) + (getTimeToLive() == null ? 0 : getTimeToLive().hashCode())) * 31) + (getTitle() == null ? 0 : getTitle().hashCode())) * 31) + (getUrl() != null ? getUrl().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof APNSMessage)) {
            return false;
        }
        APNSMessage aPNSMessage = (APNSMessage) obj;
        if ((aPNSMessage.getAction() == null) ^ (getAction() == null)) {
            return false;
        }
        if (aPNSMessage.getAction() != null && !aPNSMessage.getAction().equals(getAction())) {
            return false;
        }
        if ((aPNSMessage.getBadge() == null) ^ (getBadge() == null)) {
            return false;
        }
        if (aPNSMessage.getBadge() != null && !aPNSMessage.getBadge().equals(getBadge())) {
            return false;
        }
        if ((aPNSMessage.getBody() == null) ^ (getBody() == null)) {
            return false;
        }
        if (aPNSMessage.getBody() != null && !aPNSMessage.getBody().equals(getBody())) {
            return false;
        }
        if ((aPNSMessage.getCategory() == null) ^ (getCategory() == null)) {
            return false;
        }
        if (aPNSMessage.getCategory() != null && !aPNSMessage.getCategory().equals(getCategory())) {
            return false;
        }
        if ((aPNSMessage.getCollapseId() == null) ^ (getCollapseId() == null)) {
            return false;
        }
        if (aPNSMessage.getCollapseId() != null && !aPNSMessage.getCollapseId().equals(getCollapseId())) {
            return false;
        }
        if ((aPNSMessage.getData() == null) ^ (getData() == null)) {
            return false;
        }
        if (aPNSMessage.getData() != null && !aPNSMessage.getData().equals(getData())) {
            return false;
        }
        if ((aPNSMessage.getMediaUrl() == null) ^ (getMediaUrl() == null)) {
            return false;
        }
        if (aPNSMessage.getMediaUrl() != null && !aPNSMessage.getMediaUrl().equals(getMediaUrl())) {
            return false;
        }
        if ((aPNSMessage.getPreferredAuthenticationMethod() == null) ^ (getPreferredAuthenticationMethod() == null)) {
            return false;
        }
        if (aPNSMessage.getPreferredAuthenticationMethod() != null && !aPNSMessage.getPreferredAuthenticationMethod().equals(getPreferredAuthenticationMethod())) {
            return false;
        }
        if ((aPNSMessage.getPriority() == null) ^ (getPriority() == null)) {
            return false;
        }
        if (aPNSMessage.getPriority() != null && !aPNSMessage.getPriority().equals(getPriority())) {
            return false;
        }
        if ((aPNSMessage.getRawContent() == null) ^ (getRawContent() == null)) {
            return false;
        }
        if (aPNSMessage.getRawContent() != null && !aPNSMessage.getRawContent().equals(getRawContent())) {
            return false;
        }
        if ((aPNSMessage.getSilentPush() == null) ^ (getSilentPush() == null)) {
            return false;
        }
        if (aPNSMessage.getSilentPush() != null && !aPNSMessage.getSilentPush().equals(getSilentPush())) {
            return false;
        }
        if ((aPNSMessage.getSound() == null) ^ (getSound() == null)) {
            return false;
        }
        if (aPNSMessage.getSound() != null && !aPNSMessage.getSound().equals(getSound())) {
            return false;
        }
        if ((aPNSMessage.getSubstitutions() == null) ^ (getSubstitutions() == null)) {
            return false;
        }
        if (aPNSMessage.getSubstitutions() != null && !aPNSMessage.getSubstitutions().equals(getSubstitutions())) {
            return false;
        }
        if ((aPNSMessage.getThreadId() == null) ^ (getThreadId() == null)) {
            return false;
        }
        if (aPNSMessage.getThreadId() != null && !aPNSMessage.getThreadId().equals(getThreadId())) {
            return false;
        }
        if ((aPNSMessage.getTimeToLive() == null) ^ (getTimeToLive() == null)) {
            return false;
        }
        if (aPNSMessage.getTimeToLive() != null && !aPNSMessage.getTimeToLive().equals(getTimeToLive())) {
            return false;
        }
        if ((aPNSMessage.getTitle() == null) ^ (getTitle() == null)) {
            return false;
        }
        if (aPNSMessage.getTitle() != null && !aPNSMessage.getTitle().equals(getTitle())) {
            return false;
        }
        if ((aPNSMessage.getUrl() == null) ^ (getUrl() == null)) {
            return false;
        }
        return aPNSMessage.getUrl() == null || aPNSMessage.getUrl().equals(getUrl());
    }
}
