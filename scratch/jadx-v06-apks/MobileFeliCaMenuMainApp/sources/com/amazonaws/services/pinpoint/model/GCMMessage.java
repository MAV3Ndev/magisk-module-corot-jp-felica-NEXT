package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class GCMMessage implements Serializable {
    private String action;
    private String body;
    private String collapseKey;
    private Map<String, String> data;
    private String iconReference;
    private String imageIconUrl;
    private String imageUrl;
    private String priority;
    private String rawContent;
    private String restrictedPackageName;
    private Boolean silentPush;
    private String smallImageIconUrl;
    private String sound;
    private Map<String, List<String>> substitutions;
    private Integer timeToLive;
    private String title;
    private String url;

    public String getAction() {
        return this.action;
    }

    public void setAction(String str) {
        this.action = str;
    }

    public GCMMessage withAction(String str) {
        this.action = str;
        return this;
    }

    public void setAction(Action action) {
        this.action = action.toString();
    }

    public GCMMessage withAction(Action action) {
        this.action = action.toString();
        return this;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String str) {
        this.body = str;
    }

    public GCMMessage withBody(String str) {
        this.body = str;
        return this;
    }

    public String getCollapseKey() {
        return this.collapseKey;
    }

    public void setCollapseKey(String str) {
        this.collapseKey = str;
    }

    public GCMMessage withCollapseKey(String str) {
        this.collapseKey = str;
        return this;
    }

    public Map<String, String> getData() {
        return this.data;
    }

    public void setData(Map<String, String> map) {
        this.data = map;
    }

    public GCMMessage withData(Map<String, String> map) {
        this.data = map;
        return this;
    }

    public GCMMessage addDataEntry(String str, String str2) {
        if (this.data == null) {
            this.data = new HashMap();
        }
        if (this.data.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.data.put(str, str2);
        return this;
    }

    public GCMMessage clearDataEntries() {
        this.data = null;
        return this;
    }

    public String getIconReference() {
        return this.iconReference;
    }

    public void setIconReference(String str) {
        this.iconReference = str;
    }

    public GCMMessage withIconReference(String str) {
        this.iconReference = str;
        return this;
    }

    public String getImageIconUrl() {
        return this.imageIconUrl;
    }

    public void setImageIconUrl(String str) {
        this.imageIconUrl = str;
    }

    public GCMMessage withImageIconUrl(String str) {
        this.imageIconUrl = str;
        return this;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String str) {
        this.imageUrl = str;
    }

    public GCMMessage withImageUrl(String str) {
        this.imageUrl = str;
        return this;
    }

    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String str) {
        this.priority = str;
    }

    public GCMMessage withPriority(String str) {
        this.priority = str;
        return this;
    }

    public String getRawContent() {
        return this.rawContent;
    }

    public void setRawContent(String str) {
        this.rawContent = str;
    }

    public GCMMessage withRawContent(String str) {
        this.rawContent = str;
        return this;
    }

    public String getRestrictedPackageName() {
        return this.restrictedPackageName;
    }

    public void setRestrictedPackageName(String str) {
        this.restrictedPackageName = str;
    }

    public GCMMessage withRestrictedPackageName(String str) {
        this.restrictedPackageName = str;
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

    public GCMMessage withSilentPush(Boolean bool) {
        this.silentPush = bool;
        return this;
    }

    public String getSmallImageIconUrl() {
        return this.smallImageIconUrl;
    }

    public void setSmallImageIconUrl(String str) {
        this.smallImageIconUrl = str;
    }

    public GCMMessage withSmallImageIconUrl(String str) {
        this.smallImageIconUrl = str;
        return this;
    }

    public String getSound() {
        return this.sound;
    }

    public void setSound(String str) {
        this.sound = str;
    }

    public GCMMessage withSound(String str) {
        this.sound = str;
        return this;
    }

    public Map<String, List<String>> getSubstitutions() {
        return this.substitutions;
    }

    public void setSubstitutions(Map<String, List<String>> map) {
        this.substitutions = map;
    }

    public GCMMessage withSubstitutions(Map<String, List<String>> map) {
        this.substitutions = map;
        return this;
    }

    public GCMMessage addSubstitutionsEntry(String str, List<String> list) {
        if (this.substitutions == null) {
            this.substitutions = new HashMap();
        }
        if (this.substitutions.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.substitutions.put(str, list);
        return this;
    }

    public GCMMessage clearSubstitutionsEntries() {
        this.substitutions = null;
        return this;
    }

    public Integer getTimeToLive() {
        return this.timeToLive;
    }

    public void setTimeToLive(Integer num) {
        this.timeToLive = num;
    }

    public GCMMessage withTimeToLive(Integer num) {
        this.timeToLive = num;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public GCMMessage withTitle(String str) {
        this.title = str;
        return this;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public GCMMessage withUrl(String str) {
        this.url = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAction() != null) {
            sb.append("Action: " + getAction() + ",");
        }
        if (getBody() != null) {
            sb.append("Body: " + getBody() + ",");
        }
        if (getCollapseKey() != null) {
            sb.append("CollapseKey: " + getCollapseKey() + ",");
        }
        if (getData() != null) {
            sb.append("Data: " + getData() + ",");
        }
        if (getIconReference() != null) {
            sb.append("IconReference: " + getIconReference() + ",");
        }
        if (getImageIconUrl() != null) {
            sb.append("ImageIconUrl: " + getImageIconUrl() + ",");
        }
        if (getImageUrl() != null) {
            sb.append("ImageUrl: " + getImageUrl() + ",");
        }
        if (getPriority() != null) {
            sb.append("Priority: " + getPriority() + ",");
        }
        if (getRawContent() != null) {
            sb.append("RawContent: " + getRawContent() + ",");
        }
        if (getRestrictedPackageName() != null) {
            sb.append("RestrictedPackageName: " + getRestrictedPackageName() + ",");
        }
        if (getSilentPush() != null) {
            sb.append("SilentPush: " + getSilentPush() + ",");
        }
        if (getSmallImageIconUrl() != null) {
            sb.append("SmallImageIconUrl: " + getSmallImageIconUrl() + ",");
        }
        if (getSound() != null) {
            sb.append("Sound: " + getSound() + ",");
        }
        if (getSubstitutions() != null) {
            sb.append("Substitutions: " + getSubstitutions() + ",");
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
        return (((((((((((((((((((((((((((((((((getAction() == null ? 0 : getAction().hashCode()) + 31) * 31) + (getBody() == null ? 0 : getBody().hashCode())) * 31) + (getCollapseKey() == null ? 0 : getCollapseKey().hashCode())) * 31) + (getData() == null ? 0 : getData().hashCode())) * 31) + (getIconReference() == null ? 0 : getIconReference().hashCode())) * 31) + (getImageIconUrl() == null ? 0 : getImageIconUrl().hashCode())) * 31) + (getImageUrl() == null ? 0 : getImageUrl().hashCode())) * 31) + (getPriority() == null ? 0 : getPriority().hashCode())) * 31) + (getRawContent() == null ? 0 : getRawContent().hashCode())) * 31) + (getRestrictedPackageName() == null ? 0 : getRestrictedPackageName().hashCode())) * 31) + (getSilentPush() == null ? 0 : getSilentPush().hashCode())) * 31) + (getSmallImageIconUrl() == null ? 0 : getSmallImageIconUrl().hashCode())) * 31) + (getSound() == null ? 0 : getSound().hashCode())) * 31) + (getSubstitutions() == null ? 0 : getSubstitutions().hashCode())) * 31) + (getTimeToLive() == null ? 0 : getTimeToLive().hashCode())) * 31) + (getTitle() == null ? 0 : getTitle().hashCode())) * 31) + (getUrl() != null ? getUrl().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GCMMessage)) {
            return false;
        }
        GCMMessage gCMMessage = (GCMMessage) obj;
        if ((gCMMessage.getAction() == null) ^ (getAction() == null)) {
            return false;
        }
        if (gCMMessage.getAction() != null && !gCMMessage.getAction().equals(getAction())) {
            return false;
        }
        if ((gCMMessage.getBody() == null) ^ (getBody() == null)) {
            return false;
        }
        if (gCMMessage.getBody() != null && !gCMMessage.getBody().equals(getBody())) {
            return false;
        }
        if ((gCMMessage.getCollapseKey() == null) ^ (getCollapseKey() == null)) {
            return false;
        }
        if (gCMMessage.getCollapseKey() != null && !gCMMessage.getCollapseKey().equals(getCollapseKey())) {
            return false;
        }
        if ((gCMMessage.getData() == null) ^ (getData() == null)) {
            return false;
        }
        if (gCMMessage.getData() != null && !gCMMessage.getData().equals(getData())) {
            return false;
        }
        if ((gCMMessage.getIconReference() == null) ^ (getIconReference() == null)) {
            return false;
        }
        if (gCMMessage.getIconReference() != null && !gCMMessage.getIconReference().equals(getIconReference())) {
            return false;
        }
        if ((gCMMessage.getImageIconUrl() == null) ^ (getImageIconUrl() == null)) {
            return false;
        }
        if (gCMMessage.getImageIconUrl() != null && !gCMMessage.getImageIconUrl().equals(getImageIconUrl())) {
            return false;
        }
        if ((gCMMessage.getImageUrl() == null) ^ (getImageUrl() == null)) {
            return false;
        }
        if (gCMMessage.getImageUrl() != null && !gCMMessage.getImageUrl().equals(getImageUrl())) {
            return false;
        }
        if ((gCMMessage.getPriority() == null) ^ (getPriority() == null)) {
            return false;
        }
        if (gCMMessage.getPriority() != null && !gCMMessage.getPriority().equals(getPriority())) {
            return false;
        }
        if ((gCMMessage.getRawContent() == null) ^ (getRawContent() == null)) {
            return false;
        }
        if (gCMMessage.getRawContent() != null && !gCMMessage.getRawContent().equals(getRawContent())) {
            return false;
        }
        if ((gCMMessage.getRestrictedPackageName() == null) ^ (getRestrictedPackageName() == null)) {
            return false;
        }
        if (gCMMessage.getRestrictedPackageName() != null && !gCMMessage.getRestrictedPackageName().equals(getRestrictedPackageName())) {
            return false;
        }
        if ((gCMMessage.getSilentPush() == null) ^ (getSilentPush() == null)) {
            return false;
        }
        if (gCMMessage.getSilentPush() != null && !gCMMessage.getSilentPush().equals(getSilentPush())) {
            return false;
        }
        if ((gCMMessage.getSmallImageIconUrl() == null) ^ (getSmallImageIconUrl() == null)) {
            return false;
        }
        if (gCMMessage.getSmallImageIconUrl() != null && !gCMMessage.getSmallImageIconUrl().equals(getSmallImageIconUrl())) {
            return false;
        }
        if ((gCMMessage.getSound() == null) ^ (getSound() == null)) {
            return false;
        }
        if (gCMMessage.getSound() != null && !gCMMessage.getSound().equals(getSound())) {
            return false;
        }
        if ((gCMMessage.getSubstitutions() == null) ^ (getSubstitutions() == null)) {
            return false;
        }
        if (gCMMessage.getSubstitutions() != null && !gCMMessage.getSubstitutions().equals(getSubstitutions())) {
            return false;
        }
        if ((gCMMessage.getTimeToLive() == null) ^ (getTimeToLive() == null)) {
            return false;
        }
        if (gCMMessage.getTimeToLive() != null && !gCMMessage.getTimeToLive().equals(getTimeToLive())) {
            return false;
        }
        if ((gCMMessage.getTitle() == null) ^ (getTitle() == null)) {
            return false;
        }
        if (gCMMessage.getTitle() != null && !gCMMessage.getTitle().equals(getTitle())) {
            return false;
        }
        if ((gCMMessage.getUrl() == null) ^ (getUrl() == null)) {
            return false;
        }
        return gCMMessage.getUrl() == null || gCMMessage.getUrl().equals(getUrl());
    }
}
