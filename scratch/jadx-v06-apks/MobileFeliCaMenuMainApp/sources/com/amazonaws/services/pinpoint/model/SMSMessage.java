package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class SMSMessage implements Serializable {
    private String body;
    private String keyword;
    private String messageType;
    private String originationNumber;
    private String senderId;
    private Map<String, List<String>> substitutions;

    public String getBody() {
        return this.body;
    }

    public void setBody(String str) {
        this.body = str;
    }

    public SMSMessage withBody(String str) {
        this.body = str;
        return this;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String str) {
        this.keyword = str;
    }

    public SMSMessage withKeyword(String str) {
        this.keyword = str;
        return this;
    }

    public String getMessageType() {
        return this.messageType;
    }

    public void setMessageType(String str) {
        this.messageType = str;
    }

    public SMSMessage withMessageType(String str) {
        this.messageType = str;
        return this;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType.toString();
    }

    public SMSMessage withMessageType(MessageType messageType) {
        this.messageType = messageType.toString();
        return this;
    }

    public String getOriginationNumber() {
        return this.originationNumber;
    }

    public void setOriginationNumber(String str) {
        this.originationNumber = str;
    }

    public SMSMessage withOriginationNumber(String str) {
        this.originationNumber = str;
        return this;
    }

    public String getSenderId() {
        return this.senderId;
    }

    public void setSenderId(String str) {
        this.senderId = str;
    }

    public SMSMessage withSenderId(String str) {
        this.senderId = str;
        return this;
    }

    public Map<String, List<String>> getSubstitutions() {
        return this.substitutions;
    }

    public void setSubstitutions(Map<String, List<String>> map) {
        this.substitutions = map;
    }

    public SMSMessage withSubstitutions(Map<String, List<String>> map) {
        this.substitutions = map;
        return this;
    }

    public SMSMessage addSubstitutionsEntry(String str, List<String> list) {
        if (this.substitutions == null) {
            this.substitutions = new HashMap();
        }
        if (this.substitutions.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.substitutions.put(str, list);
        return this;
    }

    public SMSMessage clearSubstitutionsEntries() {
        this.substitutions = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getBody() != null) {
            sb.append("Body: " + getBody() + ",");
        }
        if (getKeyword() != null) {
            sb.append("Keyword: " + getKeyword() + ",");
        }
        if (getMessageType() != null) {
            sb.append("MessageType: " + getMessageType() + ",");
        }
        if (getOriginationNumber() != null) {
            sb.append("OriginationNumber: " + getOriginationNumber() + ",");
        }
        if (getSenderId() != null) {
            sb.append("SenderId: " + getSenderId() + ",");
        }
        if (getSubstitutions() != null) {
            sb.append("Substitutions: " + getSubstitutions());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((getBody() == null ? 0 : getBody().hashCode()) + 31) * 31) + (getKeyword() == null ? 0 : getKeyword().hashCode())) * 31) + (getMessageType() == null ? 0 : getMessageType().hashCode())) * 31) + (getOriginationNumber() == null ? 0 : getOriginationNumber().hashCode())) * 31) + (getSenderId() == null ? 0 : getSenderId().hashCode())) * 31) + (getSubstitutions() != null ? getSubstitutions().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SMSMessage)) {
            return false;
        }
        SMSMessage sMSMessage = (SMSMessage) obj;
        if ((sMSMessage.getBody() == null) ^ (getBody() == null)) {
            return false;
        }
        if (sMSMessage.getBody() != null && !sMSMessage.getBody().equals(getBody())) {
            return false;
        }
        if ((sMSMessage.getKeyword() == null) ^ (getKeyword() == null)) {
            return false;
        }
        if (sMSMessage.getKeyword() != null && !sMSMessage.getKeyword().equals(getKeyword())) {
            return false;
        }
        if ((sMSMessage.getMessageType() == null) ^ (getMessageType() == null)) {
            return false;
        }
        if (sMSMessage.getMessageType() != null && !sMSMessage.getMessageType().equals(getMessageType())) {
            return false;
        }
        if ((sMSMessage.getOriginationNumber() == null) ^ (getOriginationNumber() == null)) {
            return false;
        }
        if (sMSMessage.getOriginationNumber() != null && !sMSMessage.getOriginationNumber().equals(getOriginationNumber())) {
            return false;
        }
        if ((sMSMessage.getSenderId() == null) ^ (getSenderId() == null)) {
            return false;
        }
        if (sMSMessage.getSenderId() != null && !sMSMessage.getSenderId().equals(getSenderId())) {
            return false;
        }
        if ((sMSMessage.getSubstitutions() == null) ^ (getSubstitutions() == null)) {
            return false;
        }
        return sMSMessage.getSubstitutions() == null || sMSMessage.getSubstitutions().equals(getSubstitutions());
    }
}
