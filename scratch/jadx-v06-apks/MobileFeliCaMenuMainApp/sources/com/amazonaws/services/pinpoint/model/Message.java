package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class Message implements Serializable {
    private String action;
    private String body;
    private String imageIconUrl;
    private String imageSmallIconUrl;
    private String imageUrl;
    private String jsonBody;
    private String mediaUrl;
    private String rawContent;
    private Boolean silentPush;
    private Integer timeToLive;
    private String title;
    private String url;

    public String getAction() {
        return this.action;
    }

    public void setAction(String str) {
        this.action = str;
    }

    public Message withAction(String str) {
        this.action = str;
        return this;
    }

    public void setAction(Action action) {
        this.action = action.toString();
    }

    public Message withAction(Action action) {
        this.action = action.toString();
        return this;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String str) {
        this.body = str;
    }

    public Message withBody(String str) {
        this.body = str;
        return this;
    }

    public String getImageIconUrl() {
        return this.imageIconUrl;
    }

    public void setImageIconUrl(String str) {
        this.imageIconUrl = str;
    }

    public Message withImageIconUrl(String str) {
        this.imageIconUrl = str;
        return this;
    }

    public String getImageSmallIconUrl() {
        return this.imageSmallIconUrl;
    }

    public void setImageSmallIconUrl(String str) {
        this.imageSmallIconUrl = str;
    }

    public Message withImageSmallIconUrl(String str) {
        this.imageSmallIconUrl = str;
        return this;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String str) {
        this.imageUrl = str;
    }

    public Message withImageUrl(String str) {
        this.imageUrl = str;
        return this;
    }

    public String getJsonBody() {
        return this.jsonBody;
    }

    public void setJsonBody(String str) {
        this.jsonBody = str;
    }

    public Message withJsonBody(String str) {
        this.jsonBody = str;
        return this;
    }

    public String getMediaUrl() {
        return this.mediaUrl;
    }

    public void setMediaUrl(String str) {
        this.mediaUrl = str;
    }

    public Message withMediaUrl(String str) {
        this.mediaUrl = str;
        return this;
    }

    public String getRawContent() {
        return this.rawContent;
    }

    public void setRawContent(String str) {
        this.rawContent = str;
    }

    public Message withRawContent(String str) {
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

    public Message withSilentPush(Boolean bool) {
        this.silentPush = bool;
        return this;
    }

    public Integer getTimeToLive() {
        return this.timeToLive;
    }

    public void setTimeToLive(Integer num) {
        this.timeToLive = num;
    }

    public Message withTimeToLive(Integer num) {
        this.timeToLive = num;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public Message withTitle(String str) {
        this.title = str;
        return this;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public Message withUrl(String str) {
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
        if (getImageIconUrl() != null) {
            sb.append("ImageIconUrl: " + getImageIconUrl() + ",");
        }
        if (getImageSmallIconUrl() != null) {
            sb.append("ImageSmallIconUrl: " + getImageSmallIconUrl() + ",");
        }
        if (getImageUrl() != null) {
            sb.append("ImageUrl: " + getImageUrl() + ",");
        }
        if (getJsonBody() != null) {
            sb.append("JsonBody: " + getJsonBody() + ",");
        }
        if (getMediaUrl() != null) {
            sb.append("MediaUrl: " + getMediaUrl() + ",");
        }
        if (getRawContent() != null) {
            sb.append("RawContent: " + getRawContent() + ",");
        }
        if (getSilentPush() != null) {
            sb.append("SilentPush: " + getSilentPush() + ",");
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
        return (((((((((((((((((((((((getAction() == null ? 0 : getAction().hashCode()) + 31) * 31) + (getBody() == null ? 0 : getBody().hashCode())) * 31) + (getImageIconUrl() == null ? 0 : getImageIconUrl().hashCode())) * 31) + (getImageSmallIconUrl() == null ? 0 : getImageSmallIconUrl().hashCode())) * 31) + (getImageUrl() == null ? 0 : getImageUrl().hashCode())) * 31) + (getJsonBody() == null ? 0 : getJsonBody().hashCode())) * 31) + (getMediaUrl() == null ? 0 : getMediaUrl().hashCode())) * 31) + (getRawContent() == null ? 0 : getRawContent().hashCode())) * 31) + (getSilentPush() == null ? 0 : getSilentPush().hashCode())) * 31) + (getTimeToLive() == null ? 0 : getTimeToLive().hashCode())) * 31) + (getTitle() == null ? 0 : getTitle().hashCode())) * 31) + (getUrl() != null ? getUrl().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Message)) {
            return false;
        }
        Message message = (Message) obj;
        if ((message.getAction() == null) ^ (getAction() == null)) {
            return false;
        }
        if (message.getAction() != null && !message.getAction().equals(getAction())) {
            return false;
        }
        if ((message.getBody() == null) ^ (getBody() == null)) {
            return false;
        }
        if (message.getBody() != null && !message.getBody().equals(getBody())) {
            return false;
        }
        if ((message.getImageIconUrl() == null) ^ (getImageIconUrl() == null)) {
            return false;
        }
        if (message.getImageIconUrl() != null && !message.getImageIconUrl().equals(getImageIconUrl())) {
            return false;
        }
        if ((message.getImageSmallIconUrl() == null) ^ (getImageSmallIconUrl() == null)) {
            return false;
        }
        if (message.getImageSmallIconUrl() != null && !message.getImageSmallIconUrl().equals(getImageSmallIconUrl())) {
            return false;
        }
        if ((message.getImageUrl() == null) ^ (getImageUrl() == null)) {
            return false;
        }
        if (message.getImageUrl() != null && !message.getImageUrl().equals(getImageUrl())) {
            return false;
        }
        if ((message.getJsonBody() == null) ^ (getJsonBody() == null)) {
            return false;
        }
        if (message.getJsonBody() != null && !message.getJsonBody().equals(getJsonBody())) {
            return false;
        }
        if ((message.getMediaUrl() == null) ^ (getMediaUrl() == null)) {
            return false;
        }
        if (message.getMediaUrl() != null && !message.getMediaUrl().equals(getMediaUrl())) {
            return false;
        }
        if ((message.getRawContent() == null) ^ (getRawContent() == null)) {
            return false;
        }
        if (message.getRawContent() != null && !message.getRawContent().equals(getRawContent())) {
            return false;
        }
        if ((message.getSilentPush() == null) ^ (getSilentPush() == null)) {
            return false;
        }
        if (message.getSilentPush() != null && !message.getSilentPush().equals(getSilentPush())) {
            return false;
        }
        if ((message.getTimeToLive() == null) ^ (getTimeToLive() == null)) {
            return false;
        }
        if (message.getTimeToLive() != null && !message.getTimeToLive().equals(getTimeToLive())) {
            return false;
        }
        if ((message.getTitle() == null) ^ (getTitle() == null)) {
            return false;
        }
        if (message.getTitle() != null && !message.getTitle().equals(getTitle())) {
            return false;
        }
        if ((message.getUrl() == null) ^ (getUrl() == null)) {
            return false;
        }
        return message.getUrl() == null || message.getUrl().equals(getUrl());
    }
}
