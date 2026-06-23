package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class CampaignEmailMessage implements Serializable {
    private String body;
    private String fromAddress;
    private String htmlBody;
    private String title;

    public String getBody() {
        return this.body;
    }

    public void setBody(String str) {
        this.body = str;
    }

    public CampaignEmailMessage withBody(String str) {
        this.body = str;
        return this;
    }

    public String getFromAddress() {
        return this.fromAddress;
    }

    public void setFromAddress(String str) {
        this.fromAddress = str;
    }

    public CampaignEmailMessage withFromAddress(String str) {
        this.fromAddress = str;
        return this;
    }

    public String getHtmlBody() {
        return this.htmlBody;
    }

    public void setHtmlBody(String str) {
        this.htmlBody = str;
    }

    public CampaignEmailMessage withHtmlBody(String str) {
        this.htmlBody = str;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public CampaignEmailMessage withTitle(String str) {
        this.title = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getBody() != null) {
            sb.append("Body: " + getBody() + ",");
        }
        if (getFromAddress() != null) {
            sb.append("FromAddress: " + getFromAddress() + ",");
        }
        if (getHtmlBody() != null) {
            sb.append("HtmlBody: " + getHtmlBody() + ",");
        }
        if (getTitle() != null) {
            sb.append("Title: " + getTitle());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((getBody() == null ? 0 : getBody().hashCode()) + 31) * 31) + (getFromAddress() == null ? 0 : getFromAddress().hashCode())) * 31) + (getHtmlBody() == null ? 0 : getHtmlBody().hashCode())) * 31) + (getTitle() != null ? getTitle().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CampaignEmailMessage)) {
            return false;
        }
        CampaignEmailMessage campaignEmailMessage = (CampaignEmailMessage) obj;
        if ((campaignEmailMessage.getBody() == null) ^ (getBody() == null)) {
            return false;
        }
        if (campaignEmailMessage.getBody() != null && !campaignEmailMessage.getBody().equals(getBody())) {
            return false;
        }
        if ((campaignEmailMessage.getFromAddress() == null) ^ (getFromAddress() == null)) {
            return false;
        }
        if (campaignEmailMessage.getFromAddress() != null && !campaignEmailMessage.getFromAddress().equals(getFromAddress())) {
            return false;
        }
        if ((campaignEmailMessage.getHtmlBody() == null) ^ (getHtmlBody() == null)) {
            return false;
        }
        if (campaignEmailMessage.getHtmlBody() != null && !campaignEmailMessage.getHtmlBody().equals(getHtmlBody())) {
            return false;
        }
        if ((campaignEmailMessage.getTitle() == null) ^ (getTitle() == null)) {
            return false;
        }
        return campaignEmailMessage.getTitle() == null || campaignEmailMessage.getTitle().equals(getTitle());
    }
}
