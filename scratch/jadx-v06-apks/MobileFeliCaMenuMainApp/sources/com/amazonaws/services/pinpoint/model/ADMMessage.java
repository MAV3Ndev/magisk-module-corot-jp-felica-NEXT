package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ADMMessage implements Serializable {
    private String action;
    private String body;
    private String consolidationKey;
    private Map<String, String> data;
    private String expiresAfter;
    private String iconReference;
    private String imageIconUrl;
    private String imageUrl;
    private String mD5;
    private String rawContent;
    private Boolean silentPush;
    private String smallImageIconUrl;
    private String sound;
    private Map<String, List<String>> substitutions;
    private String title;
    private String url;

    public String getAction() {
        return this.action;
    }

    public void setAction(String str) {
        this.action = str;
    }

    public ADMMessage withAction(String str) {
        this.action = str;
        return this;
    }

    public void setAction(Action action) {
        this.action = action.toString();
    }

    public ADMMessage withAction(Action action) {
        this.action = action.toString();
        return this;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String str) {
        this.body = str;
    }

    public ADMMessage withBody(String str) {
        this.body = str;
        return this;
    }

    public String getConsolidationKey() {
        return this.consolidationKey;
    }

    public void setConsolidationKey(String str) {
        this.consolidationKey = str;
    }

    public ADMMessage withConsolidationKey(String str) {
        this.consolidationKey = str;
        return this;
    }

    public Map<String, String> getData() {
        return this.data;
    }

    public void setData(Map<String, String> map) {
        this.data = map;
    }

    public ADMMessage withData(Map<String, String> map) {
        this.data = map;
        return this;
    }

    public ADMMessage addDataEntry(String str, String str2) {
        if (this.data == null) {
            this.data = new HashMap();
        }
        if (this.data.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.data.put(str, str2);
        return this;
    }

    public ADMMessage clearDataEntries() {
        this.data = null;
        return this;
    }

    public String getExpiresAfter() {
        return this.expiresAfter;
    }

    public void setExpiresAfter(String str) {
        this.expiresAfter = str;
    }

    public ADMMessage withExpiresAfter(String str) {
        this.expiresAfter = str;
        return this;
    }

    public String getIconReference() {
        return this.iconReference;
    }

    public void setIconReference(String str) {
        this.iconReference = str;
    }

    public ADMMessage withIconReference(String str) {
        this.iconReference = str;
        return this;
    }

    public String getImageIconUrl() {
        return this.imageIconUrl;
    }

    public void setImageIconUrl(String str) {
        this.imageIconUrl = str;
    }

    public ADMMessage withImageIconUrl(String str) {
        this.imageIconUrl = str;
        return this;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String str) {
        this.imageUrl = str;
    }

    public ADMMessage withImageUrl(String str) {
        this.imageUrl = str;
        return this;
    }

    public String getMD5() {
        return this.mD5;
    }

    public void setMD5(String str) {
        this.mD5 = str;
    }

    public ADMMessage withMD5(String str) {
        this.mD5 = str;
        return this;
    }

    public String getRawContent() {
        return this.rawContent;
    }

    public void setRawContent(String str) {
        this.rawContent = str;
    }

    public ADMMessage withRawContent(String str) {
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

    public ADMMessage withSilentPush(Boolean bool) {
        this.silentPush = bool;
        return this;
    }

    public String getSmallImageIconUrl() {
        return this.smallImageIconUrl;
    }

    public void setSmallImageIconUrl(String str) {
        this.smallImageIconUrl = str;
    }

    public ADMMessage withSmallImageIconUrl(String str) {
        this.smallImageIconUrl = str;
        return this;
    }

    public String getSound() {
        return this.sound;
    }

    public void setSound(String str) {
        this.sound = str;
    }

    public ADMMessage withSound(String str) {
        this.sound = str;
        return this;
    }

    public Map<String, List<String>> getSubstitutions() {
        return this.substitutions;
    }

    public void setSubstitutions(Map<String, List<String>> map) {
        this.substitutions = map;
    }

    public ADMMessage withSubstitutions(Map<String, List<String>> map) {
        this.substitutions = map;
        return this;
    }

    public ADMMessage addSubstitutionsEntry(String str, List<String> list) {
        if (this.substitutions == null) {
            this.substitutions = new HashMap();
        }
        if (this.substitutions.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.substitutions.put(str, list);
        return this;
    }

    public ADMMessage clearSubstitutionsEntries() {
        this.substitutions = null;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public ADMMessage withTitle(String str) {
        this.title = str;
        return this;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public ADMMessage withUrl(String str) {
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
        if (getConsolidationKey() != null) {
            sb.append("ConsolidationKey: " + getConsolidationKey() + ",");
        }
        if (getData() != null) {
            sb.append("Data: " + getData() + ",");
        }
        if (getExpiresAfter() != null) {
            sb.append("ExpiresAfter: " + getExpiresAfter() + ",");
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
        if (getMD5() != null) {
            sb.append("MD5: " + getMD5() + ",");
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
        return (((((((((((((((((((((((((((((((getAction() == null ? 0 : getAction().hashCode()) + 31) * 31) + (getBody() == null ? 0 : getBody().hashCode())) * 31) + (getConsolidationKey() == null ? 0 : getConsolidationKey().hashCode())) * 31) + (getData() == null ? 0 : getData().hashCode())) * 31) + (getExpiresAfter() == null ? 0 : getExpiresAfter().hashCode())) * 31) + (getIconReference() == null ? 0 : getIconReference().hashCode())) * 31) + (getImageIconUrl() == null ? 0 : getImageIconUrl().hashCode())) * 31) + (getImageUrl() == null ? 0 : getImageUrl().hashCode())) * 31) + (getMD5() == null ? 0 : getMD5().hashCode())) * 31) + (getRawContent() == null ? 0 : getRawContent().hashCode())) * 31) + (getSilentPush() == null ? 0 : getSilentPush().hashCode())) * 31) + (getSmallImageIconUrl() == null ? 0 : getSmallImageIconUrl().hashCode())) * 31) + (getSound() == null ? 0 : getSound().hashCode())) * 31) + (getSubstitutions() == null ? 0 : getSubstitutions().hashCode())) * 31) + (getTitle() == null ? 0 : getTitle().hashCode())) * 31) + (getUrl() != null ? getUrl().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ADMMessage)) {
            return false;
        }
        ADMMessage aDMMessage = (ADMMessage) obj;
        if ((aDMMessage.getAction() == null) ^ (getAction() == null)) {
            return false;
        }
        if (aDMMessage.getAction() != null && !aDMMessage.getAction().equals(getAction())) {
            return false;
        }
        if ((aDMMessage.getBody() == null) ^ (getBody() == null)) {
            return false;
        }
        if (aDMMessage.getBody() != null && !aDMMessage.getBody().equals(getBody())) {
            return false;
        }
        if ((aDMMessage.getConsolidationKey() == null) ^ (getConsolidationKey() == null)) {
            return false;
        }
        if (aDMMessage.getConsolidationKey() != null && !aDMMessage.getConsolidationKey().equals(getConsolidationKey())) {
            return false;
        }
        if ((aDMMessage.getData() == null) ^ (getData() == null)) {
            return false;
        }
        if (aDMMessage.getData() != null && !aDMMessage.getData().equals(getData())) {
            return false;
        }
        if ((aDMMessage.getExpiresAfter() == null) ^ (getExpiresAfter() == null)) {
            return false;
        }
        if (aDMMessage.getExpiresAfter() != null && !aDMMessage.getExpiresAfter().equals(getExpiresAfter())) {
            return false;
        }
        if ((aDMMessage.getIconReference() == null) ^ (getIconReference() == null)) {
            return false;
        }
        if (aDMMessage.getIconReference() != null && !aDMMessage.getIconReference().equals(getIconReference())) {
            return false;
        }
        if ((aDMMessage.getImageIconUrl() == null) ^ (getImageIconUrl() == null)) {
            return false;
        }
        if (aDMMessage.getImageIconUrl() != null && !aDMMessage.getImageIconUrl().equals(getImageIconUrl())) {
            return false;
        }
        if ((aDMMessage.getImageUrl() == null) ^ (getImageUrl() == null)) {
            return false;
        }
        if (aDMMessage.getImageUrl() != null && !aDMMessage.getImageUrl().equals(getImageUrl())) {
            return false;
        }
        if ((aDMMessage.getMD5() == null) ^ (getMD5() == null)) {
            return false;
        }
        if (aDMMessage.getMD5() != null && !aDMMessage.getMD5().equals(getMD5())) {
            return false;
        }
        if ((aDMMessage.getRawContent() == null) ^ (getRawContent() == null)) {
            return false;
        }
        if (aDMMessage.getRawContent() != null && !aDMMessage.getRawContent().equals(getRawContent())) {
            return false;
        }
        if ((aDMMessage.getSilentPush() == null) ^ (getSilentPush() == null)) {
            return false;
        }
        if (aDMMessage.getSilentPush() != null && !aDMMessage.getSilentPush().equals(getSilentPush())) {
            return false;
        }
        if ((aDMMessage.getSmallImageIconUrl() == null) ^ (getSmallImageIconUrl() == null)) {
            return false;
        }
        if (aDMMessage.getSmallImageIconUrl() != null && !aDMMessage.getSmallImageIconUrl().equals(getSmallImageIconUrl())) {
            return false;
        }
        if ((aDMMessage.getSound() == null) ^ (getSound() == null)) {
            return false;
        }
        if (aDMMessage.getSound() != null && !aDMMessage.getSound().equals(getSound())) {
            return false;
        }
        if ((aDMMessage.getSubstitutions() == null) ^ (getSubstitutions() == null)) {
            return false;
        }
        if (aDMMessage.getSubstitutions() != null && !aDMMessage.getSubstitutions().equals(getSubstitutions())) {
            return false;
        }
        if ((aDMMessage.getTitle() == null) ^ (getTitle() == null)) {
            return false;
        }
        if (aDMMessage.getTitle() != null && !aDMMessage.getTitle().equals(getTitle())) {
            return false;
        }
        if ((aDMMessage.getUrl() == null) ^ (getUrl() == null)) {
            return false;
        }
        return aDMMessage.getUrl() == null || aDMMessage.getUrl().equals(getUrl());
    }
}
