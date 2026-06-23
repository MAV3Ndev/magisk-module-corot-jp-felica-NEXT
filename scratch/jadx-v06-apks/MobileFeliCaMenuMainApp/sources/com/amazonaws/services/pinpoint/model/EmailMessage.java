package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class EmailMessage implements Serializable {
    private String body;
    private String feedbackForwardingAddress;
    private String fromAddress;
    private RawEmail rawEmail;
    private List<String> replyToAddresses;
    private SimpleEmail simpleEmail;
    private Map<String, List<String>> substitutions;

    public String getBody() {
        return this.body;
    }

    public void setBody(String str) {
        this.body = str;
    }

    public EmailMessage withBody(String str) {
        this.body = str;
        return this;
    }

    public String getFeedbackForwardingAddress() {
        return this.feedbackForwardingAddress;
    }

    public void setFeedbackForwardingAddress(String str) {
        this.feedbackForwardingAddress = str;
    }

    public EmailMessage withFeedbackForwardingAddress(String str) {
        this.feedbackForwardingAddress = str;
        return this;
    }

    public String getFromAddress() {
        return this.fromAddress;
    }

    public void setFromAddress(String str) {
        this.fromAddress = str;
    }

    public EmailMessage withFromAddress(String str) {
        this.fromAddress = str;
        return this;
    }

    public RawEmail getRawEmail() {
        return this.rawEmail;
    }

    public void setRawEmail(RawEmail rawEmail) {
        this.rawEmail = rawEmail;
    }

    public EmailMessage withRawEmail(RawEmail rawEmail) {
        this.rawEmail = rawEmail;
        return this;
    }

    public List<String> getReplyToAddresses() {
        return this.replyToAddresses;
    }

    public void setReplyToAddresses(Collection<String> collection) {
        if (collection == null) {
            this.replyToAddresses = null;
        } else {
            this.replyToAddresses = new ArrayList(collection);
        }
    }

    public EmailMessage withReplyToAddresses(String... strArr) {
        if (getReplyToAddresses() == null) {
            this.replyToAddresses = new ArrayList(strArr.length);
        }
        for (String str : strArr) {
            this.replyToAddresses.add(str);
        }
        return this;
    }

    public EmailMessage withReplyToAddresses(Collection<String> collection) {
        setReplyToAddresses(collection);
        return this;
    }

    public SimpleEmail getSimpleEmail() {
        return this.simpleEmail;
    }

    public void setSimpleEmail(SimpleEmail simpleEmail) {
        this.simpleEmail = simpleEmail;
    }

    public EmailMessage withSimpleEmail(SimpleEmail simpleEmail) {
        this.simpleEmail = simpleEmail;
        return this;
    }

    public Map<String, List<String>> getSubstitutions() {
        return this.substitutions;
    }

    public void setSubstitutions(Map<String, List<String>> map) {
        this.substitutions = map;
    }

    public EmailMessage withSubstitutions(Map<String, List<String>> map) {
        this.substitutions = map;
        return this;
    }

    public EmailMessage addSubstitutionsEntry(String str, List<String> list) {
        if (this.substitutions == null) {
            this.substitutions = new HashMap();
        }
        if (this.substitutions.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.substitutions.put(str, list);
        return this;
    }

    public EmailMessage clearSubstitutionsEntries() {
        this.substitutions = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getBody() != null) {
            sb.append("Body: " + getBody() + ",");
        }
        if (getFeedbackForwardingAddress() != null) {
            sb.append("FeedbackForwardingAddress: " + getFeedbackForwardingAddress() + ",");
        }
        if (getFromAddress() != null) {
            sb.append("FromAddress: " + getFromAddress() + ",");
        }
        if (getRawEmail() != null) {
            sb.append("RawEmail: " + getRawEmail() + ",");
        }
        if (getReplyToAddresses() != null) {
            sb.append("ReplyToAddresses: " + getReplyToAddresses() + ",");
        }
        if (getSimpleEmail() != null) {
            sb.append("SimpleEmail: " + getSimpleEmail() + ",");
        }
        if (getSubstitutions() != null) {
            sb.append("Substitutions: " + getSubstitutions());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((((getBody() == null ? 0 : getBody().hashCode()) + 31) * 31) + (getFeedbackForwardingAddress() == null ? 0 : getFeedbackForwardingAddress().hashCode())) * 31) + (getFromAddress() == null ? 0 : getFromAddress().hashCode())) * 31) + (getRawEmail() == null ? 0 : getRawEmail().hashCode())) * 31) + (getReplyToAddresses() == null ? 0 : getReplyToAddresses().hashCode())) * 31) + (getSimpleEmail() == null ? 0 : getSimpleEmail().hashCode())) * 31) + (getSubstitutions() != null ? getSubstitutions().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EmailMessage)) {
            return false;
        }
        EmailMessage emailMessage = (EmailMessage) obj;
        if ((emailMessage.getBody() == null) ^ (getBody() == null)) {
            return false;
        }
        if (emailMessage.getBody() != null && !emailMessage.getBody().equals(getBody())) {
            return false;
        }
        if ((emailMessage.getFeedbackForwardingAddress() == null) ^ (getFeedbackForwardingAddress() == null)) {
            return false;
        }
        if (emailMessage.getFeedbackForwardingAddress() != null && !emailMessage.getFeedbackForwardingAddress().equals(getFeedbackForwardingAddress())) {
            return false;
        }
        if ((emailMessage.getFromAddress() == null) ^ (getFromAddress() == null)) {
            return false;
        }
        if (emailMessage.getFromAddress() != null && !emailMessage.getFromAddress().equals(getFromAddress())) {
            return false;
        }
        if ((emailMessage.getRawEmail() == null) ^ (getRawEmail() == null)) {
            return false;
        }
        if (emailMessage.getRawEmail() != null && !emailMessage.getRawEmail().equals(getRawEmail())) {
            return false;
        }
        if ((emailMessage.getReplyToAddresses() == null) ^ (getReplyToAddresses() == null)) {
            return false;
        }
        if (emailMessage.getReplyToAddresses() != null && !emailMessage.getReplyToAddresses().equals(getReplyToAddresses())) {
            return false;
        }
        if ((emailMessage.getSimpleEmail() == null) ^ (getSimpleEmail() == null)) {
            return false;
        }
        if (emailMessage.getSimpleEmail() != null && !emailMessage.getSimpleEmail().equals(getSimpleEmail())) {
            return false;
        }
        if ((emailMessage.getSubstitutions() == null) ^ (getSubstitutions() == null)) {
            return false;
        }
        return emailMessage.getSubstitutions() == null || emailMessage.getSubstitutions().equals(getSubstitutions());
    }
}
