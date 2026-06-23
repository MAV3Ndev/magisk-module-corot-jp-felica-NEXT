package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class BaiduMessage implements Serializable {
    private String action;
    private String body;
    private Map<String, String> data;
    private String iconReference;
    private String imageIconUrl;
    private String imageUrl;
    private String rawContent;
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

    public BaiduMessage withAction(String str) {
        this.action = str;
        return this;
    }

    public void setAction(Action action) {
        this.action = action.toString();
    }

    public BaiduMessage withAction(Action action) {
        this.action = action.toString();
        return this;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String str) {
        this.body = str;
    }

    public BaiduMessage withBody(String str) {
        this.body = str;
        return this;
    }

    public Map<String, String> getData() {
        return this.data;
    }

    public void setData(Map<String, String> map) {
        this.data = map;
    }

    public BaiduMessage withData(Map<String, String> map) {
        this.data = map;
        return this;
    }

    public BaiduMessage addDataEntry(String str, String str2) {
        if (this.data == null) {
            this.data = new HashMap();
        }
        if (this.data.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.data.put(str, str2);
        return this;
    }

    public BaiduMessage clearDataEntries() {
        this.data = null;
        return this;
    }

    public String getIconReference() {
        return this.iconReference;
    }

    public void setIconReference(String str) {
        this.iconReference = str;
    }

    public BaiduMessage withIconReference(String str) {
        this.iconReference = str;
        return this;
    }

    public String getImageIconUrl() {
        return this.imageIconUrl;
    }

    public void setImageIconUrl(String str) {
        this.imageIconUrl = str;
    }

    public BaiduMessage withImageIconUrl(String str) {
        this.imageIconUrl = str;
        return this;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String str) {
        this.imageUrl = str;
    }

    public BaiduMessage withImageUrl(String str) {
        this.imageUrl = str;
        return this;
    }

    public String getRawContent() {
        return this.rawContent;
    }

    public void setRawContent(String str) {
        this.rawContent = str;
    }

    public BaiduMessage withRawContent(String str) {
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

    public BaiduMessage withSilentPush(Boolean bool) {
        this.silentPush = bool;
        return this;
    }

    public String getSmallImageIconUrl() {
        return this.smallImageIconUrl;
    }

    public void setSmallImageIconUrl(String str) {
        this.smallImageIconUrl = str;
    }

    public BaiduMessage withSmallImageIconUrl(String str) {
        this.smallImageIconUrl = str;
        return this;
    }

    public String getSound() {
        return this.sound;
    }

    public void setSound(String str) {
        this.sound = str;
    }

    public BaiduMessage withSound(String str) {
        this.sound = str;
        return this;
    }

    public Map<String, List<String>> getSubstitutions() {
        return this.substitutions;
    }

    public void setSubstitutions(Map<String, List<String>> map) {
        this.substitutions = map;
    }

    public BaiduMessage withSubstitutions(Map<String, List<String>> map) {
        this.substitutions = map;
        return this;
    }

    public BaiduMessage addSubstitutionsEntry(String str, List<String> list) {
        if (this.substitutions == null) {
            this.substitutions = new HashMap();
        }
        if (this.substitutions.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.substitutions.put(str, list);
        return this;
    }

    public BaiduMessage clearSubstitutionsEntries() {
        this.substitutions = null;
        return this;
    }

    public Integer getTimeToLive() {
        return this.timeToLive;
    }

    public void setTimeToLive(Integer num) {
        this.timeToLive = num;
    }

    public BaiduMessage withTimeToLive(Integer num) {
        this.timeToLive = num;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public BaiduMessage withTitle(String str) {
        this.title = str;
        return this;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public BaiduMessage withUrl(String str) {
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
        if (getIconReference() != null) {
            sb.append("IconReference: " + getIconReference() + ",");
        }
        if (getImageIconUrl() != null) {
            sb.append("ImageIconUrl: " + getImageIconUrl() + ",");
        }
        if (getImageUrl() != null) {
            sb.append("ImageUrl: " + getImageUrl() + ",");
        }
        if (getRawContent() != null) {
            sb.append("RawContent: " + getRawContent() + ",");
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
        return (((((((((((((((((((((((((((getAction() == null ? 0 : getAction().hashCode()) + 31) * 31) + (getBody() == null ? 0 : getBody().hashCode())) * 31) + (getData() == null ? 0 : getData().hashCode())) * 31) + (getIconReference() == null ? 0 : getIconReference().hashCode())) * 31) + (getImageIconUrl() == null ? 0 : getImageIconUrl().hashCode())) * 31) + (getImageUrl() == null ? 0 : getImageUrl().hashCode())) * 31) + (getRawContent() == null ? 0 : getRawContent().hashCode())) * 31) + (getSilentPush() == null ? 0 : getSilentPush().hashCode())) * 31) + (getSmallImageIconUrl() == null ? 0 : getSmallImageIconUrl().hashCode())) * 31) + (getSound() == null ? 0 : getSound().hashCode())) * 31) + (getSubstitutions() == null ? 0 : getSubstitutions().hashCode())) * 31) + (getTimeToLive() == null ? 0 : getTimeToLive().hashCode())) * 31) + (getTitle() == null ? 0 : getTitle().hashCode())) * 31) + (getUrl() != null ? getUrl().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof BaiduMessage)) {
            return false;
        }
        BaiduMessage baiduMessage = (BaiduMessage) obj;
        if ((baiduMessage.getAction() == null) ^ (getAction() == null)) {
            return false;
        }
        if (baiduMessage.getAction() != null && !baiduMessage.getAction().equals(getAction())) {
            return false;
        }
        if ((baiduMessage.getBody() == null) ^ (getBody() == null)) {
            return false;
        }
        if (baiduMessage.getBody() != null && !baiduMessage.getBody().equals(getBody())) {
            return false;
        }
        if ((baiduMessage.getData() == null) ^ (getData() == null)) {
            return false;
        }
        if (baiduMessage.getData() != null && !baiduMessage.getData().equals(getData())) {
            return false;
        }
        if ((baiduMessage.getIconReference() == null) ^ (getIconReference() == null)) {
            return false;
        }
        if (baiduMessage.getIconReference() != null && !baiduMessage.getIconReference().equals(getIconReference())) {
            return false;
        }
        if ((baiduMessage.getImageIconUrl() == null) ^ (getImageIconUrl() == null)) {
            return false;
        }
        if (baiduMessage.getImageIconUrl() != null && !baiduMessage.getImageIconUrl().equals(getImageIconUrl())) {
            return false;
        }
        if ((baiduMessage.getImageUrl() == null) ^ (getImageUrl() == null)) {
            return false;
        }
        if (baiduMessage.getImageUrl() != null && !baiduMessage.getImageUrl().equals(getImageUrl())) {
            return false;
        }
        if ((baiduMessage.getRawContent() == null) ^ (getRawContent() == null)) {
            return false;
        }
        if (baiduMessage.getRawContent() != null && !baiduMessage.getRawContent().equals(getRawContent())) {
            return false;
        }
        if ((baiduMessage.getSilentPush() == null) ^ (getSilentPush() == null)) {
            return false;
        }
        if (baiduMessage.getSilentPush() != null && !baiduMessage.getSilentPush().equals(getSilentPush())) {
            return false;
        }
        if ((baiduMessage.getSmallImageIconUrl() == null) ^ (getSmallImageIconUrl() == null)) {
            return false;
        }
        if (baiduMessage.getSmallImageIconUrl() != null && !baiduMessage.getSmallImageIconUrl().equals(getSmallImageIconUrl())) {
            return false;
        }
        if ((baiduMessage.getSound() == null) ^ (getSound() == null)) {
            return false;
        }
        if (baiduMessage.getSound() != null && !baiduMessage.getSound().equals(getSound())) {
            return false;
        }
        if ((baiduMessage.getSubstitutions() == null) ^ (getSubstitutions() == null)) {
            return false;
        }
        if (baiduMessage.getSubstitutions() != null && !baiduMessage.getSubstitutions().equals(getSubstitutions())) {
            return false;
        }
        if ((baiduMessage.getTimeToLive() == null) ^ (getTimeToLive() == null)) {
            return false;
        }
        if (baiduMessage.getTimeToLive() != null && !baiduMessage.getTimeToLive().equals(getTimeToLive())) {
            return false;
        }
        if ((baiduMessage.getTitle() == null) ^ (getTitle() == null)) {
            return false;
        }
        if (baiduMessage.getTitle() != null && !baiduMessage.getTitle().equals(getTitle())) {
            return false;
        }
        if ((baiduMessage.getUrl() == null) ^ (getUrl() == null)) {
            return false;
        }
        return baiduMessage.getUrl() == null || baiduMessage.getUrl().equals(getUrl());
    }
}
