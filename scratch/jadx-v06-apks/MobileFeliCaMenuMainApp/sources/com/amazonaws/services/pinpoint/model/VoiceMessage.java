package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class VoiceMessage implements Serializable {
    private String body;
    private String languageCode;
    private String originationNumber;
    private Map<String, List<String>> substitutions;
    private String voiceId;

    public String getBody() {
        return this.body;
    }

    public void setBody(String str) {
        this.body = str;
    }

    public VoiceMessage withBody(String str) {
        this.body = str;
        return this;
    }

    public String getLanguageCode() {
        return this.languageCode;
    }

    public void setLanguageCode(String str) {
        this.languageCode = str;
    }

    public VoiceMessage withLanguageCode(String str) {
        this.languageCode = str;
        return this;
    }

    public String getOriginationNumber() {
        return this.originationNumber;
    }

    public void setOriginationNumber(String str) {
        this.originationNumber = str;
    }

    public VoiceMessage withOriginationNumber(String str) {
        this.originationNumber = str;
        return this;
    }

    public Map<String, List<String>> getSubstitutions() {
        return this.substitutions;
    }

    public void setSubstitutions(Map<String, List<String>> map) {
        this.substitutions = map;
    }

    public VoiceMessage withSubstitutions(Map<String, List<String>> map) {
        this.substitutions = map;
        return this;
    }

    public VoiceMessage addSubstitutionsEntry(String str, List<String> list) {
        if (this.substitutions == null) {
            this.substitutions = new HashMap();
        }
        if (this.substitutions.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.substitutions.put(str, list);
        return this;
    }

    public VoiceMessage clearSubstitutionsEntries() {
        this.substitutions = null;
        return this;
    }

    public String getVoiceId() {
        return this.voiceId;
    }

    public void setVoiceId(String str) {
        this.voiceId = str;
    }

    public VoiceMessage withVoiceId(String str) {
        this.voiceId = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getBody() != null) {
            sb.append("Body: " + getBody() + ",");
        }
        if (getLanguageCode() != null) {
            sb.append("LanguageCode: " + getLanguageCode() + ",");
        }
        if (getOriginationNumber() != null) {
            sb.append("OriginationNumber: " + getOriginationNumber() + ",");
        }
        if (getSubstitutions() != null) {
            sb.append("Substitutions: " + getSubstitutions() + ",");
        }
        if (getVoiceId() != null) {
            sb.append("VoiceId: " + getVoiceId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((getBody() == null ? 0 : getBody().hashCode()) + 31) * 31) + (getLanguageCode() == null ? 0 : getLanguageCode().hashCode())) * 31) + (getOriginationNumber() == null ? 0 : getOriginationNumber().hashCode())) * 31) + (getSubstitutions() == null ? 0 : getSubstitutions().hashCode())) * 31) + (getVoiceId() != null ? getVoiceId().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof VoiceMessage)) {
            return false;
        }
        VoiceMessage voiceMessage = (VoiceMessage) obj;
        if ((voiceMessage.getBody() == null) ^ (getBody() == null)) {
            return false;
        }
        if (voiceMessage.getBody() != null && !voiceMessage.getBody().equals(getBody())) {
            return false;
        }
        if ((voiceMessage.getLanguageCode() == null) ^ (getLanguageCode() == null)) {
            return false;
        }
        if (voiceMessage.getLanguageCode() != null && !voiceMessage.getLanguageCode().equals(getLanguageCode())) {
            return false;
        }
        if ((voiceMessage.getOriginationNumber() == null) ^ (getOriginationNumber() == null)) {
            return false;
        }
        if (voiceMessage.getOriginationNumber() != null && !voiceMessage.getOriginationNumber().equals(getOriginationNumber())) {
            return false;
        }
        if ((voiceMessage.getSubstitutions() == null) ^ (getSubstitutions() == null)) {
            return false;
        }
        if (voiceMessage.getSubstitutions() != null && !voiceMessage.getSubstitutions().equals(getSubstitutions())) {
            return false;
        }
        if ((voiceMessage.getVoiceId() == null) ^ (getVoiceId() == null)) {
            return false;
        }
        return voiceMessage.getVoiceId() == null || voiceMessage.getVoiceId().equals(getVoiceId());
    }
}
