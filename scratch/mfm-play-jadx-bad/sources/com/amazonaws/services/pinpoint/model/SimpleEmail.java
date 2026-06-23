package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class SimpleEmail implements Serializable {
    private SimpleEmailPart htmlPart;
    private SimpleEmailPart subject;
    private SimpleEmailPart textPart;

    public SimpleEmailPart getHtmlPart() {
        return this.htmlPart;
    }

    public void setHtmlPart(SimpleEmailPart simpleEmailPart) {
        this.htmlPart = simpleEmailPart;
    }

    public SimpleEmail withHtmlPart(SimpleEmailPart simpleEmailPart) {
        this.htmlPart = simpleEmailPart;
        return this;
    }

    public SimpleEmailPart getSubject() {
        return this.subject;
    }

    public void setSubject(SimpleEmailPart simpleEmailPart) {
        this.subject = simpleEmailPart;
    }

    public SimpleEmail withSubject(SimpleEmailPart simpleEmailPart) {
        this.subject = simpleEmailPart;
        return this;
    }

    public SimpleEmailPart getTextPart() {
        return this.textPart;
    }

    public void setTextPart(SimpleEmailPart simpleEmailPart) {
        this.textPart = simpleEmailPart;
    }

    public SimpleEmail withTextPart(SimpleEmailPart simpleEmailPart) {
        this.textPart = simpleEmailPart;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getHtmlPart() != null) {
            sb.append("HtmlPart: " + getHtmlPart() + ",");
        }
        if (getSubject() != null) {
            sb.append("Subject: " + getSubject() + ",");
        }
        if (getTextPart() != null) {
            sb.append("TextPart: " + getTextPart());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((getHtmlPart() == null ? 0 : getHtmlPart().hashCode()) + 31) * 31) + (getSubject() == null ? 0 : getSubject().hashCode())) * 31) + (getTextPart() != null ? getTextPart().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SimpleEmail)) {
            return false;
        }
        SimpleEmail simpleEmail = (SimpleEmail) obj;
        if ((simpleEmail.getHtmlPart() == null) ^ (getHtmlPart() == null)) {
            return false;
        }
        if (simpleEmail.getHtmlPart() != null && !simpleEmail.getHtmlPart().equals(getHtmlPart())) {
            return false;
        }
        if ((simpleEmail.getSubject() == null) ^ (getSubject() == null)) {
            return false;
        }
        if (simpleEmail.getSubject() != null && !simpleEmail.getSubject().equals(getSubject())) {
            return false;
        }
        if ((simpleEmail.getTextPart() == null) ^ (getTextPart() == null)) {
            return false;
        }
        return simpleEmail.getTextPart() == null || simpleEmail.getTextPart().equals(getTextPart());
    }
}
